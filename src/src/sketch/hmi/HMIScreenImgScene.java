package sketch.hmi;
import sketch.common.JsonImageInfo;
import sketch.common.JsonPartInfo;
import sketch.common.JsonPartInfo_new;
import sketch.common.JsonScreenImageInfo;

import java.util.ArrayList;
import java.awt.Rectangle;
import java.util.Collections;
import java.util.Comparator;

public class HMIScreenImgScene {
    private static int MARK_SIZE = 32;
    private static int MARK_INTERVAL = 6;
    private static int MARK_PIC_INTERVAL = 18;
    private static int MARK_LINE_INTERVAL = 16;
    private static int LINE_CONNECT_IMG_MARGIN = 5;

    public double maxHeightPix = 0.0;
    //图片大小的信息(width，height)
    public double[] imgSizePix = new double[] {0.0, 0.0, 1.0};
    //需要描画的Part的大小信息，(x,y,width,height)
    private ArrayList<JsonPartInfo_new> partInfoList = new ArrayList<>();
    private ArrayList<JsonPartInfo_new> donePartInfoList = new ArrayList<>();
    //计算出的描画mark列表
    public ArrayList<HMIDrawPartInfo> retDrawInfo = new ArrayList<>();

    public HMIScreenImgScene(double maxHeightPix) {
        this.maxHeightPix = maxHeightPix;
    }

    public void setImageInfo(JsonScreenImageInfo imageInfo) {
        if (imageInfo != null && imageInfo.imagePath!= null && imageInfo.imagePath.length()>0) {
            this.imgSizePix[0] = Double.parseDouble(imageInfo.width);
            this.imgSizePix[1] = Double.parseDouble(imageInfo.height);

            calImgScaleRatioByMaxHeight();

            HMIDrawPartInfo imgPartInfo = new HMIDrawPartInfo();
            imgPartInfo.startX = 0;
            imgPartInfo.startY = 0;
            imgPartInfo.endX = (int)Math.round(this.imgSizePix[0]*this.imgSizePix[2]);
            imgPartInfo.endY = (int)Math.round(this.imgSizePix[1]*this.imgSizePix[2]);
            imgPartInfo.isReverse = false;
            imgPartInfo.partType = "IMAGE";
            imgPartInfo.partText = imageInfo.imagePath;

            this.retDrawInfo.add(imgPartInfo);
        }
        else {
            this.imgSizePix[0] = 0.0;
            this.imgSizePix[1] = 0.0;
            this.imgSizePix[2] = 1.0;
        }
    }

    public void calImgScaleRatioByMaxHeight() {
        double maxImgHeight = this.maxHeightPix/2;

        if (this.imgSizePix[1] > maxImgHeight) {
            this.imgSizePix[2] = maxImgHeight/this.imgSizePix[1];
        }
    }

    public void addJsonPartInfo(JsonPartInfo_new jsonPartinfo) {
        if (jsonPartinfo.getDisplayRect() != null) {
            this.partInfoList.add(jsonPartinfo);
        }
    }

    public ArrayList<HMIDrawPartInfo> getDrawPartsList() {
        return this.retDrawInfo;
    }

    public void makeDrawParts() {
        if (this.imgSizePix[0] == 0 || this.imgSizePix[1] == 0) {
            return;
        }

        //按照先上面再下面的顺序，从左往右扫描，最后查是否有遗漏的，最后给遗漏的找一个最近的位置。

        //以X为单位进行竖线扫描，分别在Parts的上面和下面添加Mark
        double scaleImgHeight = imgSizePix[1]*imgSizePix[2];
        double[] upLineY = new double[]{0-this.MARK_PIC_INTERVAL-MARK_SIZE-MARK_INTERVAL, 0-this.MARK_PIC_INTERVAL};
        double[] downLineY = new double[]{scaleImgHeight+this.MARK_PIC_INTERVAL+MARK_SIZE+MARK_INTERVAL, scaleImgHeight+this.MARK_PIC_INTERVAL};
        int lineYindex = 0;
        ArrayList<double[]> emptyRectList = new ArrayList<double[]>();

        for (double iScanX = 1.0; iScanX < imgSizePix[0]*imgSizePix[2]; iScanX = iScanX+this.MARK_LINE_INTERVAL+(this.MARK_SIZE/2))
        {
            //找上半部分
            JsonPartInfo_new findPartInfo = FindNearestPartAccordingX(iScanX, upLineY[0], scaleImgHeight/2.0, "MIN");
            genPartFromLineUp(iScanX, upLineY[lineYindex%2], findPartInfo);
            if (findPartInfo == null) {
                emptyRectList.add(new double[]{iScanX, upLineY[lineYindex%2], 1.0});
            }

            //再找一个，在下面画个mark
            findPartInfo = FindNearestPartAccordingX(iScanX, scaleImgHeight/2.0, downLineY[0], "MAX");
            genPartFromLineDown(iScanX, downLineY[lineYindex%2], findPartInfo);
            if (findPartInfo == null) {
                emptyRectList.add(new double[]{iScanX, downLineY[lineYindex%2], 2.0});
            }

            lineYindex+=1;
        }

        //以Y为单位进行横线扫描，分别在Parts的左面和右面添加Mark
        double scaleImgWidth = imgSizePix[0]*imgSizePix[2];
        double[] leftLineX = new double[]{0-this.MARK_PIC_INTERVAL-MARK_SIZE-MARK_INTERVAL, 0-this.MARK_PIC_INTERVAL};
        double[] rightLineX = new double[] {scaleImgWidth+this.MARK_PIC_INTERVAL+MARK_SIZE+MARK_INTERVAL, scaleImgWidth+this.MARK_PIC_INTERVAL};
        int lineXindex = 0;
        for (double iScanY = 1.0; iScanY < imgSizePix[1]*imgSizePix[2]; iScanY = iScanY + this.MARK_LINE_INTERVAL+(this.MARK_SIZE/2))
        {
            //先找一个放在左边
            JsonPartInfo_new findPartInfo = FindNearestPartAccordingY(iScanY, leftLineX[0], scaleImgWidth/2.0, "MIN");
            genPartFromRowLeft(iScanY, leftLineX[lineXindex%2], findPartInfo);
            if (findPartInfo == null) {
                emptyRectList.add(new double[]{leftLineX[lineXindex%2], iScanY, 3.0});
            }

            //再找一个，在右边画个mark
            findPartInfo = FindNearestPartAccordingY(iScanY, scaleImgWidth/2.0, rightLineX[0], "MAX");
            genPartFromRowRight(iScanY, rightLineX[lineXindex%2], findPartInfo);
            if (findPartInfo == null) {
                emptyRectList.add(new double[]{rightLineX[lineXindex%2], iScanY, 4.0});
            }

            lineXindex+=1;
        }

        //查找还有哪些Parts的Mark没有描画
        ArrayList<JsonPartInfo_new> restPartInfoList = new ArrayList<>();
        for (JsonPartInfo_new iPartInfo : this.partInfoList) {
            if (this.donePartInfoList.contains(iPartInfo) == false) {
                restPartInfoList.add(iPartInfo);
            }
        }

        if (restPartInfoList.size() > emptyRectList.size()) {
//            System.out.println("[Rest PartInfo List Size:]"+restPartInfoList.size());
//            System.out.println("[Empty Rect List Size:]"+emptyRectList.size());
            int addRectCount = (restPartInfoList.size()-emptyRectList.size())/4+1;
            int interval = this.MARK_LINE_INTERVAL+(this.MARK_SIZE/2);
            int interIndex = 1;
            int interCount = 1;
            while (addRectCount > 0) {
                emptyRectList.add(new double[]{1.0-(interCount*interval), upLineY[interIndex%2], 1.0});
                emptyRectList.add(new double[]{(imgSizePix[0]*imgSizePix[2])+(interCount*interval), upLineY[interIndex%2], 1.0});
                emptyRectList.add(new double[]{1.0-(interCount*interval), downLineY[interIndex%2], 2.0});
                emptyRectList.add(new double[]{(imgSizePix[0]*imgSizePix[2])+(interCount*interval), downLineY[interIndex%2], 2.0});

                interIndex = interIndex+1;
                interCount = interCount+1;
                addRectCount = addRectCount-1;
            }
        }

        while (restPartInfoList.size() > 0) {
            JsonPartInfo_new iPartInfo = restPartInfoList.get(0);

            int iNearestIndex = this.getNearestRectMark(emptyRectList, iPartInfo);
            double[] nearestInfo = emptyRectList.get(iNearestIndex);

            if (nearestInfo[2] == 1.0) {
                genPartFromLineUp(nearestInfo[0], nearestInfo[1], iPartInfo);
            }
            else if (nearestInfo[2] == 2.0) {
                genPartFromLineDown(nearestInfo[0], nearestInfo[1], iPartInfo);
            }
            else if (nearestInfo[2] == 3.0) {
                genPartFromRowLeft(nearestInfo[1], nearestInfo[0], iPartInfo);
            }
            else if (nearestInfo[2] == 4.0) {
                genPartFromRowRight(nearestInfo[1], nearestInfo[0], iPartInfo);
            }

            emptyRectList.remove(iNearestIndex);
            restPartInfoList.remove(0);
        }
    }

    private int getNearestRectMark(ArrayList<double[]> emptyRectList, JsonPartInfo_new onePartInfo) {
        if (emptyRectList.size() <= 0) {
            return -1;
        }

        int minIndex = 0;
        double minDistance = 0.0;

        for (int ii = 0; ii < emptyRectList.size(); ii++) {
            double[] infoArray = emptyRectList.get(ii);
            double infox = infoArray[0];
            double infoy = infoArray[1];
            double infoDir = infoArray[2];

            double nowDistance = 0.0;
            JsonImageInfo nowPartImgInfo = onePartInfo.getDisplayRect();

            if (infoArray[2] == 1.0) {
                nowDistance = (nowPartImgInfo.y*this.imgSizePix[2]-infoy)*(nowPartImgInfo.y*this.imgSizePix[2]-infoy)+
                        (nowPartImgInfo.x*this.imgSizePix[2]-infox)*(nowPartImgInfo.x*this.imgSizePix[2]-infox);
            }
            else if (infoArray[2] == 2.0) {
                nowDistance = ((nowPartImgInfo.y+nowPartImgInfo.height)*this.imgSizePix[2]-infoy)*((nowPartImgInfo.y+nowPartImgInfo.height)*this.imgSizePix[2]-infoy)+
                        (nowPartImgInfo.x*this.imgSizePix[2]-infox)*(nowPartImgInfo.x*this.imgSizePix[2]-infox);
            }
            else if (infoArray[2] == 3.0) {
                nowDistance = (nowPartImgInfo.y*this.imgSizePix[2]-infoy)*((nowPartImgInfo.y)*this.imgSizePix[2]-infoy)+
                        (nowPartImgInfo.x*this.imgSizePix[2]-infox)*(nowPartImgInfo.x*this.imgSizePix[2]-infox);
            }
            else if (infoArray[2] == 4.0) {
                nowDistance = (nowPartImgInfo.y*this.imgSizePix[2]-infoy)*(nowPartImgInfo.y*this.imgSizePix[2]-infoy)+
                        ((nowPartImgInfo.x+nowPartImgInfo.width)*this.imgSizePix[2]-infox)*((nowPartImgInfo.x+nowPartImgInfo.width)*this.imgSizePix[2]-infox);
            }

            if (ii == 0) {
                minDistance = nowDistance;
                continue;
            }
            if (nowDistance < minDistance) {
                minDistance = nowDistance;
                minIndex = ii;
            }
        }

        return minIndex;
    }


    private void genPartFromLineUp(double xPos, double upMarkLineStartY, JsonPartInfo_new partInfo) {
        if (partInfo == null) {
            return;
        }

        JsonImageInfo findPartImgInfo = partInfo.getDisplayRect();
        double leftupX = xPos - this.MARK_SIZE/2;
        double leftupY = upMarkLineStartY - this.MARK_SIZE;

        HMIDrawPartInfo drawLinePartInfo = new HMIDrawPartInfo();
        drawLinePartInfo.startX = (int)Math.round(leftupX+this.MARK_SIZE/2);
        drawLinePartInfo.startY = (int)Math.round(leftupY+this.MARK_SIZE);


        if (findPartImgInfo.getRectW()*this.imgSizePix[2] < this.LINE_CONNECT_IMG_MARGIN*2) {
            drawLinePartInfo.endX = (int)Math.round((findPartImgInfo.getRectX()+findPartImgInfo.getRectW()/2)*this.imgSizePix[2]);
        }
        else if (drawLinePartInfo.startX < findPartImgInfo.getRectX()*this.imgSizePix[2]+this.LINE_CONNECT_IMG_MARGIN) {
            drawLinePartInfo.endX = (int)Math.round(findPartImgInfo.getRectX()*this.imgSizePix[2]+this.LINE_CONNECT_IMG_MARGIN);
        }
        else if (drawLinePartInfo.startX > (findPartImgInfo.getRectX()+findPartImgInfo.getRectW())*this.imgSizePix[2]-this.LINE_CONNECT_IMG_MARGIN) {
            drawLinePartInfo.endX = (int)Math.round((findPartImgInfo.getRectX()+findPartImgInfo.getRectW())*this.imgSizePix[2]-this.LINE_CONNECT_IMG_MARGIN);
        }
        else {
            drawLinePartInfo.endX = drawLinePartInfo.startX;
        }
        drawLinePartInfo.endY = (int)Math.round(findPartImgInfo.getRectY()*this.imgSizePix[2]+this.LINE_CONNECT_IMG_MARGIN);
        drawLinePartInfo.isReverse = false;
        drawLinePartInfo.partType = "LINE";
//        if (drawLinePartInfo.isEllipse = partInfo.getIsTouchSymbol()) {
//            drawLinePartInfo.isEllipse = true;
//        }
//        else {
//            drawLinePartInfo.isEllipse = false;
//        }
        this.retDrawInfo.add(drawLinePartInfo);

        HMIDrawPartInfo drawRectPartInfo = new HMIDrawPartInfo();
        drawRectPartInfo.startX = (int)Math.round(leftupX);
        drawRectPartInfo.startY = (int)Math.round(leftupY);
        drawRectPartInfo.endX = drawRectPartInfo.startX+this.MARK_SIZE;
        drawRectPartInfo.endY = drawRectPartInfo.startY+this.MARK_SIZE;
        drawRectPartInfo.partText = partInfo.getDisplayRefInfo().getPartsNumber();
//        drawLinePartInfo.isReverse = false;
//        if (drawRectPartInfo.isEllipse = partInfo.getIsTouchSymbol()) {
//            drawRectPartInfo.partType = "ELLIPSE";
//        }
//        else {
            drawRectPartInfo.partType = "RECT";
//        }
        this.retDrawInfo.add(drawRectPartInfo);
    }

    private void genPartFromLineDown(double xPos, double downMarkLineStartY, JsonPartInfo_new partInfo) {
        if (partInfo == null) {
            return;
        }

        JsonImageInfo findPartImgInfo = partInfo.getDisplayRect();
        double leftupX = xPos - this.MARK_SIZE/2;
        double leftupY = downMarkLineStartY;

        HMIDrawPartInfo drawLinePartInfo = new HMIDrawPartInfo();

        drawLinePartInfo.endX = (int)Math.round(leftupX+this.MARK_SIZE/2);
        drawLinePartInfo.endY = (int)Math.round(leftupY);

        if (findPartImgInfo.getRectW()*this.imgSizePix[2] < this.LINE_CONNECT_IMG_MARGIN*2) {
            drawLinePartInfo.startX = (int)Math.round((findPartImgInfo.getRectX()+findPartImgInfo.getRectW()/2)*this.imgSizePix[2]);
        }
        else if (drawLinePartInfo.endX < findPartImgInfo.getRectX()*this.imgSizePix[2]+this.LINE_CONNECT_IMG_MARGIN) {
            drawLinePartInfo.startX = (int)Math.round(findPartImgInfo.getRectX()*this.imgSizePix[2]+this.LINE_CONNECT_IMG_MARGIN);
        }
        else if (drawLinePartInfo.endX > (findPartImgInfo.getRectX()+findPartImgInfo.getRectW())*this.imgSizePix[2]-this.LINE_CONNECT_IMG_MARGIN) {
            drawLinePartInfo.startX = (int)Math.round((findPartImgInfo.getRectX()+findPartImgInfo.getRectW())*this.imgSizePix[2]-this.LINE_CONNECT_IMG_MARGIN);
        }
        else {
            drawLinePartInfo.startX = drawLinePartInfo.endX;
        }

        drawLinePartInfo.startY = (int)Math.round((findPartImgInfo.getRectY()+findPartImgInfo.getRectH())*this.imgSizePix[2]-this.LINE_CONNECT_IMG_MARGIN);
        if (drawLinePartInfo.startY > (int)Math.round(imgSizePix[1]*imgSizePix[2])) {
            drawLinePartInfo.startY = (int)Math.round(findPartImgInfo.getRectY()*this.imgSizePix[2]);
        }

        drawLinePartInfo.isReverse = true;
        drawLinePartInfo.partType = "LINE";
//        if (drawLinePartInfo.isEllipse = partInfo.getIsTouchSymbol()) {
//            drawLinePartInfo.isEllipse = true;
//        }
//        else {
//            drawLinePartInfo.isEllipse = false;
//        }
        this.retDrawInfo.add(drawLinePartInfo);

        HMIDrawPartInfo drawRectPartInfo = new HMIDrawPartInfo();
        drawRectPartInfo.startX = (int)Math.round(leftupX);
        drawRectPartInfo.startY = (int)Math.round(leftupY);
        drawRectPartInfo.endX = drawRectPartInfo.startX+this.MARK_SIZE;
        drawRectPartInfo.endY = drawRectPartInfo.startY+this.MARK_SIZE;
        drawRectPartInfo.isReverse = false;
        drawRectPartInfo.partText = partInfo.getDisplayRefInfo().getPartsNumber();
//        if (drawRectPartInfo.isEllipse = partInfo.getIsTouchSymbol()) {
//            drawRectPartInfo.partType = "ELLIPSE";
//        }
//        else {
            drawRectPartInfo.partType = "RECT";
//        }
        this.retDrawInfo.add(drawRectPartInfo);
    }

    private void genPartFromRowLeft(double yPos, double downMarkLineStartX, JsonPartInfo_new partInfo) {
        if (partInfo == null) {
            return;
        }

        JsonImageInfo findPartImgInfo = partInfo.getDisplayRect();
        double leftupX = downMarkLineStartX - this.MARK_SIZE;
        double leftupY = yPos - this.MARK_SIZE/2;

        HMIDrawPartInfo drawLinePartInfo = new HMIDrawPartInfo();
        drawLinePartInfo.startX = (int)Math.round(leftupX+this.MARK_SIZE);
        drawLinePartInfo.startY = (int)Math.round(leftupY+this.MARK_SIZE/2);
        drawLinePartInfo.endX = (int)Math.round(findPartImgInfo.getRectX()*this.imgSizePix[2]+this.LINE_CONNECT_IMG_MARGIN);
        if (findPartImgInfo.getRectH()*this.imgSizePix[2] < this.LINE_CONNECT_IMG_MARGIN*2) {
            drawLinePartInfo.endY = (int)Math.round((findPartImgInfo.getRectY()+findPartImgInfo.getRectH()/2)*this.imgSizePix[2]);
        }
        else if (drawLinePartInfo.startY < findPartImgInfo.getRectY()*this.imgSizePix[2]+this.LINE_CONNECT_IMG_MARGIN) {
            drawLinePartInfo.endY = (int)Math.round(findPartImgInfo.getRectY()*this.imgSizePix[2]+this.LINE_CONNECT_IMG_MARGIN);
        }
        else if (drawLinePartInfo.startY > (findPartImgInfo.getRectY()+findPartImgInfo.getRectH())*this.imgSizePix[2]-this.LINE_CONNECT_IMG_MARGIN) {
            drawLinePartInfo.endY = (int)Math.round((findPartImgInfo.getRectY()+findPartImgInfo.getRectH())*this.imgSizePix[2]-this.LINE_CONNECT_IMG_MARGIN);
        }
        else {
            drawLinePartInfo.endY = drawLinePartInfo.startY;
        }
        drawLinePartInfo.isReverse = false;
        drawLinePartInfo.partType = "LINE";
//        if (drawLinePartInfo.isEllipse = partInfo.getIsTouchSymbol()) {
//            drawLinePartInfo.isEllipse = true;
//        }
//        else {
//            drawLinePartInfo.isEllipse = false;
//        }
        this.retDrawInfo.add(drawLinePartInfo);

        HMIDrawPartInfo drawRectPartInfo = new HMIDrawPartInfo();
        drawRectPartInfo.startX = (int)Math.round(leftupX);
        drawRectPartInfo.startY = (int)Math.round(leftupY);
        drawRectPartInfo.endX = drawRectPartInfo.startX + this.MARK_SIZE;
        drawRectPartInfo.endY = drawRectPartInfo.startY + this.MARK_SIZE;
        drawRectPartInfo.isReverse = false;
        drawRectPartInfo.partText = partInfo.getDisplayRefInfo().getPartsNumber();
//        if (drawRectPartInfo.isEllipse = partInfo.getIsTouchSymbol()) {
//            drawRectPartInfo.partType = "ELLIPSE";
//        }
//        else {
            drawRectPartInfo.partType = "RECT";
//        }
        this.retDrawInfo.add(drawRectPartInfo);

    }

    private void genPartFromRowRight(double yPos, double downMarkLineStartX, JsonPartInfo_new partInfo) {
        if (partInfo == null) {
            return;
        }

        JsonImageInfo findPartImgInfo = partInfo.getDisplayRect();
        double leftupX = downMarkLineStartX;
        double leftupY = yPos - this.MARK_SIZE/2;

        HMIDrawPartInfo drawLinePartInfo = new HMIDrawPartInfo();
        drawLinePartInfo.endX = (int)Math.round(leftupX);
        drawLinePartInfo.endY = (int)Math.round(leftupY+this.MARK_SIZE/2);
        drawLinePartInfo.startX = (int)Math.round((findPartImgInfo.getRectX()+findPartImgInfo.getRectW())*this.imgSizePix[2]-this.LINE_CONNECT_IMG_MARGIN);
        if (findPartImgInfo.getRectH()*this.imgSizePix[2] < this.LINE_CONNECT_IMG_MARGIN*2) {
            drawLinePartInfo.startY = (int)Math.round((findPartImgInfo.getRectY()+findPartImgInfo.getRectH()/2)*this.imgSizePix[2]);
        }
        else if (drawLinePartInfo.endY < findPartImgInfo.getRectY()*this.imgSizePix[2]+this.LINE_CONNECT_IMG_MARGIN) {
            drawLinePartInfo.startY = (int)Math.round(findPartImgInfo.getRectY()*this.imgSizePix[2]+this.LINE_CONNECT_IMG_MARGIN);
        }
        else if (drawLinePartInfo.endY > (findPartImgInfo.getRectY()+findPartImgInfo.getRectH())*this.imgSizePix[2]-this.LINE_CONNECT_IMG_MARGIN) {
            drawLinePartInfo.startY = (int)Math.round((findPartImgInfo.getRectY()+findPartImgInfo.getRectH())*this.imgSizePix[2]-this.LINE_CONNECT_IMG_MARGIN);
        }
        else {
            drawLinePartInfo.startY = drawLinePartInfo.endY;
        }
        drawLinePartInfo.isReverse = true;
        drawLinePartInfo.partType = "LINE";
//        if (drawLinePartInfo.isEllipse = partInfo.getIsTouchSymbol()) {
//            drawLinePartInfo.isEllipse = true;
//        }
//        else {
//            drawLinePartInfo.isEllipse = false;
//        }
        this.retDrawInfo.add(drawLinePartInfo);

        HMIDrawPartInfo drawRectPartInfo = new HMIDrawPartInfo();
        drawRectPartInfo.startX = (int)Math.round(leftupX);
        drawRectPartInfo.startY = (int)Math.round(leftupY);
        drawRectPartInfo.endX = drawRectPartInfo.startX + this.MARK_SIZE;
        drawRectPartInfo.endY = drawRectPartInfo.startY + this.MARK_SIZE;
        drawRectPartInfo.isReverse = false;
        drawRectPartInfo.partText = partInfo.getDisplayRefInfo().getPartsNumber();
//        if (drawRectPartInfo.isEllipse = partInfo.getIsTouchSymbol()) {
//            drawRectPartInfo.partType = "ELLIPSE";
//        }
//        else {
            drawRectPartInfo.partType = "RECT";
//        }
        this.retDrawInfo.add(drawRectPartInfo);

    }

    private JsonPartInfo_new FindNearestPartAccordingX(double xpos, double ymin, double ymax, String retType) {
        ArrayList<JsonPartInfo_new> findInfoList = new ArrayList<>();

        for (JsonPartInfo_new iPartInfo : this.partInfoList) {
            if (this.donePartInfoList.contains(iPartInfo) == true) {
                continue;
            }

            JsonImageInfo iPartImgInfo = iPartInfo.getDisplayRect();
            int partScaleX = (int)Math.round(iPartImgInfo.getRectX()*this.imgSizePix[2]);
            int partScaleWidth = (int)Math.round(iPartImgInfo.getRectW()*this.imgSizePix[2]);
            int partScaleY = (int)Math.round(iPartImgInfo.getRectY()*this.imgSizePix[2]);
            int partScaleHeitht = (int)Math.round(iPartImgInfo.getRectH()*this.imgSizePix[2]);
            Rectangle partRect = new Rectangle(partScaleX, partScaleY, partScaleWidth, partScaleHeitht);

            if (partRect.intersectsLine(xpos, ymin, xpos, ymax) == true) {
                findInfoList.add(iPartInfo);
            }
        }

        if (findInfoList.size() == 0) {
            return null;
        }

        Collections.sort(findInfoList, new Comparator<JsonPartInfo_new>(){
            @Override
            public int compare(JsonPartInfo_new b1, JsonPartInfo_new b2) {
                return b1.getDisplayRect().getRectY() - b2.getDisplayRect().getRectY();
            }

        });

        if (retType.equalsIgnoreCase("MIN")) {
            this.donePartInfoList.add(findInfoList.get(0));
            return findInfoList.get(0);
        }
        else if (retType.equalsIgnoreCase("MAX")) {
            this.donePartInfoList.add(findInfoList.get(findInfoList.size()-1));
            return findInfoList.get(findInfoList.size()-1);
        }


        return null;
    }

    private JsonPartInfo_new FindNearestPartAccordingY(double ypos, double xmin, double xmax, String retType) {
        ArrayList<JsonPartInfo_new> findInfoList = new ArrayList<>();

        for (JsonPartInfo_new iPartInfo : this.partInfoList) {
            if (this.donePartInfoList.contains(iPartInfo) == true) {
                continue;
            }

            JsonImageInfo iPartImgInfo = iPartInfo.getDisplayRect();
            int partScaleX = (int)Math.round(iPartImgInfo.getRectX()*this.imgSizePix[2]);
            int partScaleWidth = (int)Math.round(iPartImgInfo.getRectW()*this.imgSizePix[2]);
            int partScaleY = (int)Math.round(iPartImgInfo.getRectY()*this.imgSizePix[2]);
            int partScaleHeitht = (int)Math.round(iPartImgInfo.getRectH()*this.imgSizePix[2]);
            Rectangle partRect = new Rectangle(partScaleX, partScaleY, partScaleWidth, partScaleHeitht);

            if (partRect.intersectsLine(xmin, ypos, xmax, ypos) == true) {
                findInfoList.add(iPartInfo);
            }
        }

        if (findInfoList.size() == 0) {
            return null;
        }

        Collections.sort(findInfoList, new Comparator<JsonPartInfo_new>(){
            @Override
            public int compare(JsonPartInfo_new b1, JsonPartInfo_new b2) {
                return b1.getDisplayRect().getRectX() - b2.getDisplayRect().getRectX();
            }

        });

        if (retType.equalsIgnoreCase("MIN")) {
            this.donePartInfoList.add(findInfoList.get(0));
            return findInfoList.get(0);
        }
        else if (retType.equalsIgnoreCase("MAX")) {
            this.donePartInfoList.add(findInfoList.get(findInfoList.size()-1));
            return findInfoList.get(findInfoList.size()-1);
        }

        return null;
    }

    public void layoutToVCenter() {
        int find_min_x = 0;
        int find_min_y = 0;
        int find_max_y = 0;

        for (HMIDrawPartInfo iDrawPart : this.retDrawInfo) {
            if (iDrawPart.startX < find_min_x) {
                find_min_x = iDrawPart.startX;
            }
            if (iDrawPart.startY < find_min_y) {
                find_min_y = iDrawPart.startY;
            }
            if (iDrawPart.endY > find_max_y) {
                find_max_y = iDrawPart.endY;
            }
        }

//        if (Math.abs(find_min_y) < 1) {
//            find_min_y = 1;
//        }

        for (int ii = 0; ii < this.retDrawInfo.size(); ii++) {
            HMIDrawPartInfo iPartInfo = retDrawInfo.get(ii);
            iPartInfo.startX = iPartInfo.startX+Math.abs(find_min_x);
            iPartInfo.endX = iPartInfo.endX+Math.abs(find_min_x);
            iPartInfo.startY = iPartInfo.startY+Math.abs(find_min_y);
            iPartInfo.endY = iPartInfo.endY+Math.abs(find_min_y);
        }

    }

    public int getScenetWidth() {
        int find_min_x = 0;
        int find_max_x = 0;

        for (HMIDrawPartInfo iDrawPart : this.retDrawInfo) {
            if (iDrawPart.startX < find_min_x) {
                find_min_x = iDrawPart.startX;
            }
            if (iDrawPart.endX > find_max_x) {
                find_max_x = iDrawPart.endX;
            }
        }

        return find_max_x-find_min_x;
    }

}
