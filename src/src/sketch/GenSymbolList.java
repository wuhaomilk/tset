package sketch;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sketch.symbollist.JsonSymbolParse;
import sketch.symbollist.SymbolData;
import sketch.symbollist.XlsSymbolSheet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class GenSymbolList {
    public static void main(String[] args) throws Exception {
        String jsonFileName = args[0];
        String outputFileName = args[1];
//        String jsonFileName = "/Users/huangyp/workspace/temp/symbolexpand.json";
//        String outputFileName = "/Users/huangyp/workspace/temp/symbollist.xlsx";

        ArrayList<SymbolData> symbolList = new JsonSymbolParse().parseFile(jsonFileName);

        XSSFWorkbook outWorkbook = new XSSFWorkbook();
        XSSFSheet outWorkSheet = outWorkbook.createSheet("SymbolList");
        new XlsSymbolSheet(outWorkbook, outWorkSheet).genSymbolData(symbolList);

        OutputStream os = new FileOutputStream(new File(outputFileName));
        outWorkbook.write(os);
        os.close();
    }
}
