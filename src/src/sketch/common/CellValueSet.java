package sketch.common;

import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;

public class CellValueSet {
    private XSSFWorkbook workBook = null;
    private XSSFSheet sheetInfo = null;

    public CellValueSet(XSSFWorkbook _workBook, XSSFSheet _sheetInfo) {
        this.workBook = _workBook;
        this.sheetInfo = _sheetInfo;
    }

    public void doCellValueList(ArrayList<SheetPosValue> posValueList) {
        for (int i = 0; i < posValueList.size(); i++) {
            if (posValueList.get(i) == null) {
                continue;
            }

            this.doOneCellValue(posValueList.get(i));
        }
    }

    public void doOneCellValue(SheetPosValue cellValue) {
        String colTxt = cellValue.posInfo.split("\\d+")[0];
        String rowTxt = cellValue.posInfo.split("\\D+")[1];

        int colIndex= CellReference.convertColStringToIndex(colTxt);
        int rowIndex = Integer.parseInt(rowTxt)-1;

        if (this.sheetInfo.getRow(rowIndex) == null){
            this.sheetInfo.createRow(rowIndex);
        }
        XSSFRow rowInfo = this.sheetInfo.getRow(rowIndex);

        if (rowInfo.getCell(colIndex) == null) {
            rowInfo.createCell(colIndex);
        }

        rowInfo.getCell(colIndex).setCellValue(cellValue.strValue);

    }

}
