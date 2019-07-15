package tests;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sketch.common.CellStyleInfo;
import sketch.common.CellStyleSet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class CellStyleSetTest {
    public static void main(String[] args) throws Exception{
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet hmiSheet = wb.createSheet("HMISpec");
        CellStyleSet cellStyleSet = new CellStyleSet(wb, hmiSheet);



//        hmiSheet.getRow(0).getCell(0).setCellValue("X");
        cellStyleSet.doOneCellStyle(new CellStyleInfo(
                "B2:C2","", 16,true, true,
                "center","bottom","medium","medium","medium",
                "medium",false, new int[]{146,208,80}));
        hmiSheet.getRow(1).getCell(1).setCellValue("X");



        OutputStream os = new FileOutputStream(new File("/Users/hcz/workspace/test_cellstyle.xlsx"));
        wb.write(os);
        os.close();




    }
}
