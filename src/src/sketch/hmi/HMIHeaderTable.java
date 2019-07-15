package sketch.hmi;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import sketch.common.*;
import sketch.settings.HeaderTableStatic;

public class HMIHeaderTable {
    private XSSFWorkbook workBook = null;
    private XSSFSheet sheetInfo = null;

    public HMIHeaderTable(XSSFWorkbook _workBook, XSSFSheet _xssfSheet) {
        this.workBook = _workBook;
        this.sheetInfo = _xssfSheet;
        HeaderTableStatic staticInfo = new HeaderTableStatic();

        CellSizeSet cellSizeSet = new CellSizeSet(this.workBook, this.sheetInfo);
        cellSizeSet.doSetColWidthList(staticInfo.colWidthList);
        cellSizeSet.doSetRowHeightList(staticInfo.rowHeightList);

        CellStyleSet cellStyleSet = new CellStyleSet(this.workBook, this.sheetInfo);
        cellStyleSet.doCellStyleList(staticInfo.styleList);

        CellValueSet cellValueSet = new CellValueSet(this.workBook, this.sheetInfo);
        cellValueSet.doCellValueList(staticInfo.lableList);
    }

    public void doExportInfo(JsonSketchInfo jsonSketchInfo) {
        JsonScreenInfo screenInfo = jsonSketchInfo.screenInfo;

        CellValueSet cellValueSet = new CellValueSet(this.workBook, this.sheetInfo);
//        if (screenInfo.screenID != null) {
//            cellValueSet.doOneCellValue(new SheetPosValue("C2", screenInfo.screenID));
//        }
//        if (screenInfo.baseScreenID != null) {
//            cellValueSet.doOneCellValue(new SheetPosValue("C3", screenInfo.baseScreenID));
//        }
//        if (screenInfo.screenName != null) {
//            cellValueSet.doOneCellValue(new SheetPosValue("C4", screenInfo.screenName));
//        }
//        if (screenInfo.screenNote != null) {
//            cellValueSet.doOneCellValue(new SheetPosValue("B5", screenInfo.screenNote));
//        }
    }

}
