package tests;

import org.apache.poi.util.IOUtils;
import org.apache.poi.util.Units;
import org.apache.poi.xssf.usermodel.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.List;

public class XlsImagePosTest {
    public static void main(String[] args) throws Exception {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet imgSheet = wb.createSheet("TestImage");

        initColWidth(imgSheet);
        initRowHeight(imgSheet);

        XSSFDrawing drawing = imgSheet.createDrawingPatriarch();

        BufferedImage bufferImg = ImageIO.read(new FileInputStream("/Users/hcz/workspace/SpecTools/hmipic1.png"));
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        ImageIO.write(bufferImg,"png", byteArrayOut);

        System.out.println(bufferImg.getHeight());
        System.out.println(bufferImg.getWidth());

        int imgMaxHeight = (int)imgSheet.getRow(1).getHeightInPoints()/72*96;
        float imgScaleRatio = (float)(imgMaxHeight*1.0)/bufferImg.getHeight();
        int imgScaleWidth = (int)(bufferImg.getWidth()*imgScaleRatio);
        System.out.println(imgMaxHeight);
        System.out.println(imgScaleWidth);

        int destcol = 2;
        float restWidth = (float)(imgScaleWidth-imgSheet.getColumnWidthInPixels(1)*0.5);
        for (int ii = 2; ii < 100; ii++) {
            float nowColmnWidth = imgSheet.getColumnWidthInPixels(ii);
            destcol = ii;
            if (restWidth > nowColmnWidth) {
                restWidth -= nowColmnWidth;
                continue;
            }
            break;
        }


        XSSFClientAnchor L11EndAnchor = new XSSFClientAnchor(
                (int)(Units.EMU_PER_PIXEL*imgSheet.getColumnWidthInPixels(1)*0.5),
                Units.EMU_PER_POINT*175,
                (int)(Units.EMU_PER_PIXEL*restWidth), Units.EMU_PER_POINT*175,
                1,1,destcol,2);
//        InputStream is = new FileInputStream("/Users/hcz/workspace/SpecTools/hmipic1.png");
//        byte[] bytes = IOUtils.toByteArray(is);
        int pictureIdx = wb.addPicture(byteArrayOut.toByteArray(), wb.PICTURE_TYPE_PNG);
//        is.close();
        XSSFPicture pict = drawing.createPicture(L11EndAnchor, pictureIdx);
//        pict.resize();
//        System.out.println(pict.getImageDimension().height);
//        System.out.println(pict.getImageDimension().width);


        OutputStream os = new FileOutputStream(new File("/Users/hcz/workspace/opeimg.xlsx"));
        wb.write(os);
        os.close();

    }


    private static void initColWidth(XSSFSheet sheetInfo) {
        List<Float> colWidthList = Arrays.asList(
                7.5f, 40f, 10.63f, 26f, 8.13f, 4f, 40f, 9.75f, 14.75f, 6.88f, 13.63f, 13.63f,
                13.63f);

        for (int i_index = 0; i_index < colWidthList.size(); i_index++) {
            sheetInfo.setColumnWidth(i_index, (int)((colWidthList.get(i_index)+0.72)*256));
        }
    }

    private static void initRowHeight(XSSFSheet sheetInfo) {
        sheetInfo.createRow(1).setHeightInPoints(350);
        sheetInfo.createRow(2).setHeightInPoints(350);
    }
}
