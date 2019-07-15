package sketch.hmi;

import org.apache.poi.ss.usermodel.ShapeTypes;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.*;
import org.openxmlformats.schemas.drawingml.x2006.main.CTLineEndProperties;
import org.openxmlformats.schemas.drawingml.x2006.main.CTLineProperties;
import org.openxmlformats.schemas.drawingml.x2006.main.CTShapeProperties;
import org.openxmlformats.schemas.drawingml.x2006.main.STLineEndType;
import org.apache.poi.ss.usermodel.*;

import sketch.common.*;
import sketch.settings.ScreenImageStatic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class HMIScreenImage {
    private static int IMG_START_ROW_INDEX = 9;
    private static int IMG_END_ROW_INDEX = 25;
    private static int IMG_ROW_COUNTS = 16; // total height
    private static int IMG_START_COL = 3;
    private static int IMG_START_ROW_OFFSET_PIX = 5;
    private static int EACHPAGE_COL_OFFSET_PIX = 170;

    private XSSFWorkbook workBook = null;
    private XSSFSheet sheetInfo = null;
    private int m_StartRow = 0;
    private int m_EndRow = 0;

    public HMIScreenImage(XSSFWorkbook _workBook, XSSFSheet _xssfSheet, int startRow) {
        this.workBook = _workBook;
        this.sheetInfo = _xssfSheet;
        this.m_StartRow = startRow;
        this.m_EndRow = startRow + IMG_ROW_COUNTS;

        ScreenImageStatic staticInfo = new ScreenImageStatic();

        CellSizeSet cellSizeSet = new CellSizeSet(this.workBook, this.sheetInfo);
        cellSizeSet.doSetRowHeightList(staticInfo.rowHeightList);

        CellStyleSet cellStyleSet = new CellStyleSet(this.workBook, this.sheetInfo);
        cellStyleSet.doCellStyleList(staticInfo.styleList);

        CellValueSet cellValueSet = new CellValueSet(this.workBook, this.sheetInfo);
        cellValueSet.doCellValueList(staticInfo.lableList);
    }

    public void doExportInfo(JsonSketchInfo_new jsonSketchInfo) throws Exception {
        int maxHeightPix = 0;
        for (int iRowIndex =this. m_StartRow; iRowIndex <= this.m_EndRow; iRowIndex++){
            maxHeightPix += (int)(this.sheetInfo.getRow(iRowIndex).getHeightInPoints());
        }

        XSSFDrawing drawing = this.sheetInfo.createDrawingPatriarch();
        int pageOffset = 10;
        drawOnePageScreen(drawing, jsonSketchInfo, pageOffset, maxHeightPix);
    }

    private int drawOnePageScreen(XSSFDrawing drawing, JsonSketchInfo_new jsonSketchInfo, int xPageOffSet, int maxHeightPix) throws Exception {
        HMIScreenImgScene scrImgScene = new HMIScreenImgScene(maxHeightPix-IMG_START_ROW_OFFSET_PIX);
//        JsonDisplayRefInfo displayRefInfo = new JsonDisplayRefInfo();
        scrImgScene.setImageInfo(jsonSketchInfo.screenInfo);

        for (JsonPartInfo_new partInfo : jsonSketchInfo.getJsonPartInfoList()) {
            if (partInfo.getDisplayRefInfo().getPartsNumber() == null ) {
                continue;
            }
            scrImgScene.addJsonPartInfo(partInfo);
//            if (partInfo.partImageInfo.imgPath.indexOf(".png") > 0) {
//                scrImgScene.addJsonPartInfo(partInfo);
//            }


        }


        scrImgScene.makeDrawParts();
        scrImgScene.layoutToVCenter();

        int retSceneWidth = scrImgScene.getScenetWidth();
        ArrayList<HMIDrawPartInfo> drawPartList = scrImgScene.getDrawPartsList();
        for (HMIDrawPartInfo iDrawPart : drawPartList) {
            drawImage(drawing, iDrawPart, xPageOffSet);
            drawShape(drawing, iDrawPart, xPageOffSet);
        }

        return retSceneWidth;

    }

    private void drawImage(XSSFDrawing drawing, HMIDrawPartInfo drawPartInfo, int xOffset) throws Exception {
        if (drawPartInfo.partType != "IMAGE") {
            return;
        }

        File imgFile = new File(drawPartInfo.partText);
        if (imgFile.exists() == false) {
            return;
        }

        String imgfileName = imgFile.getName();
        BufferedImage bufferImg = ImageIO.read(new FileInputStream(imgFile.getPath()));
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        ImageIO.write(bufferImg,imgfileName.substring(imgfileName.lastIndexOf(".")+1), byteArrayOut);

        int[] startCell = new int[]{this.IMG_START_COL, this.m_StartRow};
        int[] cellOffset = new int[]{xOffset, this.IMG_START_ROW_OFFSET_PIX};


        int[] startImgPos = calCellInfoByPixel(startCell, cellOffset, new double[]{drawPartInfo.startX, drawPartInfo.startY});
        int[] endImgPos = calCellInfoByPixel(startCell, cellOffset,
                new double[]{drawPartInfo.endX, drawPartInfo.endY});
        XSSFClientAnchor imageAnchor = new XSSFClientAnchor(
                XSSFShape.EMU_PER_POINT*startImgPos[2],
                XSSFShape.EMU_PER_POINT*startImgPos[3],
                XSSFShape.EMU_PER_POINT*endImgPos[2],
                XSSFShape.EMU_PER_POINT*endImgPos[3],
                startImgPos[0],startImgPos[1], endImgPos[0],endImgPos[1]);

        int pictureIdx = this.workBook.addPicture(byteArrayOut.toByteArray(), this.workBook.PICTURE_TYPE_PNG);

        drawing.createPicture(imageAnchor, pictureIdx);
    }

    private void drawShape(XSSFDrawing drawing, HMIDrawPartInfo drawPartInfo, int xOffset) throws Exception {
        if (drawPartInfo.partType == "IMAGE") {
            return;
        }

        int[] startCell = new int[]{this.IMG_START_COL, this.m_StartRow};
        int[] cellOffset = new int[]{xOffset, this.IMG_START_ROW_OFFSET_PIX};

        boolean isXFlip = false;
        boolean isYFlip = false;

        if (drawPartInfo.endX < drawPartInfo.startX) {
            isXFlip = true;
            int tmp = drawPartInfo.startX;
            drawPartInfo.startX = drawPartInfo.endX;
            drawPartInfo.endX = tmp;
        }

        if (drawPartInfo.endY < drawPartInfo.startY) {
            isYFlip = true;
            int tmp = drawPartInfo.startY;
            drawPartInfo.startY = drawPartInfo.endY;
            drawPartInfo.endY = tmp;
        }

        int[] startImgPos = calCellInfoByPixel(startCell, cellOffset, new double[]{drawPartInfo.startX, drawPartInfo.startY});
        int[] endImgPos = calCellInfoByPixel(startCell, cellOffset,
                new double[]{drawPartInfo.endX, drawPartInfo.endY});

        XSSFClientAnchor shapeAnchor = new XSSFClientAnchor(
                XSSFShape.EMU_PER_POINT*startImgPos[2],
                XSSFShape.EMU_PER_POINT*startImgPos[3],
                XSSFShape.EMU_PER_POINT*endImgPos[2],
                XSSFShape.EMU_PER_POINT*endImgPos[3],
                startImgPos[0],startImgPos[1], endImgPos[0],endImgPos[1]);

        XSSFSimpleShape drawShape = drawing.createSimpleShape(shapeAnchor);
        if (drawPartInfo.partType.equalsIgnoreCase("LINE")) {
            drawShape.setShapeType(ShapeTypes.LINE);
        }
        else if (drawPartInfo.partType.equalsIgnoreCase("RECT")) {
            drawShape.setShapeType(ShapeTypes.RECT);
        }
        else if (drawPartInfo.partType.equalsIgnoreCase("ELLIPSE")) {
            drawShape.setShapeType(ShapeTypes.ELLIPSE);
        }
        drawShape.setLineWidth(2);
        if (drawPartInfo.isEllipse) {
            drawShape.setLineStyleColor(250,51,54);
        } else {
            drawShape.setLineStyleColor(0,176,80);
        }
        if (isXFlip == true) {
            drawShape.getCTShape().getSpPr().getXfrm().setFlipH(true);
        }
        if (isYFlip == true) {
            drawShape.getCTShape().getSpPr().getXfrm().setFlipV(true);
        }
        if (drawPartInfo.partType.equalsIgnoreCase("LINE")) {
            if (drawPartInfo.isReverse == true) {
                setLineArrow(drawShape, "HEAD");
            }
            else {
                setLineArrow(drawShape, "TAIL");
            }
        }

        if (drawPartInfo.partText != null) {
            //drawShape.setText(drawPartInfo.partText);
            XSSFRichTextString seq = new XSSFRichTextString(drawPartInfo.partText);
            //seq.applyFont(Font.COLOR_RED);
            drawShape.setText(seq);


            drawShape.setVerticalAlignment(VerticalAlignment.CENTER);

            int textLength = drawPartInfo.partText.length();
            if (!drawPartInfo.isEllipse) {
                if (textLength < 6) {
                    drawShape.setLeftInset(8 / 5.5 * (6 - textLength + 1));
                }
            }
            else {
                if (textLength == 1 || textLength == 2) {
                    drawShape.setLeftInset(19.35 / 5.5 * (5 - textLength));
                }
                else if (textLength == 5) {
                    drawShape.setRightInset(19.35 / 5.5 * (4 - textLength));
                }
            }
        }
    }

    //返回一个坐标值关于指定单元格的偏移
    //返回值类型为[col,row,coloffset,rowoffset]
    //startcel[col, row]起始单元格
    //celloffset[coloffset,rowoffset]起始位置相对于起始单元格的偏移
    public int[] calCellInfoByPixel(int[] startCell, int[] cellOffset, double[] pxInfo) {
        int[] retPixelInfo = new int[] {0,0,0,0};

        int iStartCol = startCell[0];
        int iStartColOffset = cellOffset[0];
        //0.859649是一个误差校准值，通过接口取到的值会大于实际的单元格宽度，通过（单元格的截图高度/取到的值）得到
        int iStartColRest = (int)(this.sheetInfo.getColumnWidthInPixels(iStartCol)*0.859649 - iStartColOffset);

        //如果第一个column的值就够了，则返回
        if (iStartColRest >= pxInfo[0]) {
            retPixelInfo[0] = iStartCol;
            retPixelInfo[2] = (int)(iStartColOffset+pxInfo[0]);
        }
        else {
            double restPxInfo = pxInfo[0] - iStartColRest;
            int iFindCol = iStartCol+1;
            while(true) {
                //0.859649是一个误差校准值，通过接口取到的值会大于实际的单元格宽度，通过（单元格的截图高度/取到的值）得到
                double nowColWidth = this.sheetInfo.getColumnWidthInPixels(iFindCol)*0.859649;
                if (nowColWidth >= restPxInfo){
                    retPixelInfo[0] = iFindCol;
                    retPixelInfo[2] = (int)(restPxInfo);
                    break;
                }
                restPxInfo = restPxInfo-nowColWidth;
                iFindCol += 1;
            }
        }

        int iStartRow = startCell[1];
        int iStartRowOffset = cellOffset[1];
        double iStartRowRest = this.sheetInfo.getRow(iStartRow).getHeightInPoints()-iStartRowOffset;
        if (iStartRowRest >= pxInfo[1]) {
            retPixelInfo[1] = iStartRow;
            retPixelInfo[3] = (int)(iStartRowOffset+pxInfo[1]);
        }
        else {
            double restPxInfo = pxInfo[1] - iStartRowRest;
            int iFindRow = iStartRow + 1;
            while(true) {
                if (this.sheetInfo.getRow(iFindRow) == null) {
                    this.sheetInfo.createRow(iFindRow);
                }
                double nowRowHeight = this.sheetInfo.getRow(iFindRow).getHeightInPoints();
                if (nowRowHeight >= restPxInfo){
                    retPixelInfo[1] = iFindRow;
                    retPixelInfo[3] = (int)(restPxInfo);
                    break;
                }
                restPxInfo = restPxInfo-nowRowHeight;
                iFindRow += 1;
            }
        }

        return retPixelInfo;
    }

    private void setLineArrow(XSSFSimpleShape lineShape, String arrowType) {
        CTShapeProperties shapeProp = lineShape.getCTShape().getSpPr();
        CTLineProperties shapeLp = shapeProp.getLn();
        if (arrowType.equalsIgnoreCase("TAIL")) {
            CTLineEndProperties shapeLep = shapeLp.addNewTailEnd();
            shapeLep.setType(STLineEndType.TRIANGLE);
        }
        else if (arrowType.equalsIgnoreCase("HEAD")) {
            CTLineEndProperties shapeLep = shapeLp.addNewHeadEnd();
            shapeLep.setType(STLineEndType.TRIANGLE);
        }
    }




}
