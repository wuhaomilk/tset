package sketch.common;

import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;

public class CellSizeSet {
    private XSSFWorkbook workBook = null;
    private XSSFSheet sheetInfo = null;

    public CellSizeSet(XSSFWorkbook _workBook, XSSFSheet _sheetInfo) {
        this.workBook = _workBook;
        this.sheetInfo = _sheetInfo;
    }

    public void doSetColWidthList(ArrayList<SheetPosValue> posValueList) {
        for (int i=0; i< posValueList.size(); i++) {
            if (posValueList.get(i) == null) {
                continue;
            }

            this.doSetOneColWidth(posValueList.get(i));
        }
    }

    public void doSetOneColWidth(SheetPosValue posValue) {
        int iStartCol = 0;
        int iEndCol = 0;
        if (posValue.posInfo.indexOf(":") >= 0) {
            String[] splitResult = posValue.posInfo.split(":");
            iStartCol = CellReference.convertColStringToIndex(splitResult[0]);
            iEndCol = CellReference.convertColStringToIndex(splitResult[1]);
        }
        else {
            iStartCol = CellReference.convertColStringToIndex(posValue.posInfo);
            iEndCol = iStartCol;
        }

        for (int j = iStartCol; j <= iEndCol; j++) {
            this.sheetInfo.setColumnWidth(j, (int)((Float.parseFloat(posValue.strValue)+0.72)*256));
        }
    }

    public void doSetRowHeightList(ArrayList<SheetPosValue> posValueList) {
        for (int i = 0; i < posValueList.size(); i++) {
            if (posValueList.get(i) == null) {
                continue;
            }

            this.doSetOneRowHeight(posValueList.get(i));
        }
    }

    public void doSetOneRowHeight(SheetPosValue posValue) {
        int iStartRow = 0;
        int iEndRow = 0;

        if (posValue.posInfo.indexOf(":") >= 0) {
            String[] splitResult = posValue.posInfo.split(":");
            iStartRow = Integer.parseInt(splitResult[0])-1;
            iEndRow = Integer.parseInt(splitResult[1])-1;
        }
        else {
            iStartRow = Integer.parseInt(posValue.posInfo)-1;
            iEndRow = iStartRow;
        }

        for (int j = iStartRow; j <= iEndRow; j++) {
            this.sheetInfo.createRow(j).setHeightInPoints(Float.parseFloat(posValue.strValue));
        }
    }

}
