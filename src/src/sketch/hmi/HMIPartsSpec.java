package sketch.hmi;

import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.*;
import sketch.common.*;
import sketch.settings.HMIPartsSpecStatic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class HMIPartsSpec {
    private XSSFWorkbook workBook = null;
    private XSSFSheet sheetInfo = null;
    private float DefaultRowHeight = 24;
    private float DefaultColWidth = 5;
    private int StartRowIndex = 28;

    public HMIPartsSpec(XSSFWorkbook _workBook, XSSFSheet _xssfSheet) {
        this.workBook = _workBook;
        this.sheetInfo = _xssfSheet;

        HMIPartsSpecStatic staticInfo = new HMIPartsSpecStatic();

        CellSizeSet cellSizeSet = new CellSizeSet(this.workBook, this.sheetInfo);
        cellSizeSet.doSetRowHeightList(staticInfo.rowHeightList);

        CellStyleSet cellStyleSet = new CellStyleSet(this.workBook, this.sheetInfo);
        cellStyleSet.doCellStyleList(staticInfo.styleList);

        CellValueSet cellValueSet = new CellValueSet(this.workBook, this.sheetInfo);
        cellValueSet.doCellValueList(staticInfo.lableList);
    }

    public void doExportInfo(JsonSketchInfo_ecock jsonSketchInfo) throws Exception {
        int iStartPartsIndex = StartRowIndex;
        XSSFDrawing drawing = this.sheetInfo.createDrawingPatriarch();

        CellSizeSet cellSizeSet = new CellSizeSet(this.workBook, this.sheetInfo);
        CellStyleSet cellStyleSet = new CellStyleSet(this.workBook, this.sheetInfo);
        CellValueSet cellValueSet = new CellValueSet(this.workBook, this.sheetInfo);

        //设置row的高度
        SheetPosValue rowHeightInfo = new SheetPosValue(String.format("%d:%d", iStartPartsIndex+1, iStartPartsIndex+200),
                String.format("%f", DefaultRowHeight));
        cellSizeSet.doSetOneRowHeight(rowHeightInfo);
        //设置col的宽度
        SheetPosValue colWidthInfo = new SheetPosValue("A:BO", String.format("%f", DefaultColWidth));
        cellSizeSet.doSetOneColWidth(colWidthInfo);

        // 1
        for (JsonPartInfo_ecock partInfo : jsonSketchInfo.partInfoList) {
            int rowCount = getPartRowCount(partInfo);
            getDisplayInfo(cellStyleSet, cellValueSet, iStartPartsIndex, rowCount, partInfo);
            iStartPartsIndex = iStartPartsIndex + rowCount;
        }

        // 2
        iStartPartsIndex += 1;
        for (JsonPartInfo_ecock partInfo : jsonSketchInfo.partInfoList) {
            int rowCount = getPartRowCount(partInfo);
            getActiveInfo(cellStyleSet, cellValueSet, iStartPartsIndex, rowCount, partInfo);
            iStartPartsIndex = iStartPartsIndex + rowCount;
        }

        // 3
        iStartPartsIndex += 1;
        for (JsonPartInfo_ecock partInfo : jsonSketchInfo.partInfoList) {
            int rowCount = getPartRowCount(partInfo);
            getActionInfo(cellStyleSet, cellValueSet, iStartPartsIndex, rowCount, partInfo);
            iStartPartsIndex = iStartPartsIndex + rowCount;
        }
        // 4~8

    }

    //写入第3部分 Active Info
    private void getActionInfo(CellStyleSet cellStyleSet, CellValueSet cellValueSet, int startRowIndex, int rowCount, JsonPartInfo_ecock partInfo) throws Exception{

        //demo
        JsonDisplayInfo jsonDisplayInfo = partInfo.getDisplayInfo();
        if(jsonDisplayInfo != null) {
            //写入Chapter
            CellStyleInfo displayChapterCellStyle = new CellStyleInfo(
                    String.format("B%d:B%d", startRowIndex + 1, startRowIndex + rowCount),
                    "Meiryo UI", 11, true, false,
                    "center", "center", "thin", "thin", "thin",
                    "thin", false, new int[]{201, 255, 255});
            cellStyleSet.doOneCellStyle(displayChapterCellStyle);

            if (jsonDisplayInfo.displayChapter != null) {
                SheetPosValue partsTypeCellVal = new SheetPosValue(String.format("B%d", startRowIndex + 1),
                        "demo");
                cellValueSet.doOneCellValue(partsTypeCellVal);
            }
        }
    }

    //写入第2部分 Active Info
    private void getActiveInfo(CellStyleSet cellStyleSet, CellValueSet cellValueSet, int startRowIndex, int rowCount, JsonPartInfo_ecock partInfo) throws Exception{

        //demo
        JsonDisplayInfo jsonDisplayInfo = partInfo.getDisplayInfo();
        if(jsonDisplayInfo != null) {
            //写入Chapter
            CellStyleInfo displayChapterCellStyle = new CellStyleInfo(
                    String.format("B%d:B%d", startRowIndex + 1, startRowIndex + rowCount),
                    "Meiryo UI", 11, true, false,
                    "center", "center", "thin", "thin", "thin",
                    "thin", false, new int[]{201, 255, 255});
            cellStyleSet.doOneCellStyle(displayChapterCellStyle);

            if (jsonDisplayInfo.displayChapter != null) {
                SheetPosValue partsTypeCellVal = new SheetPosValue(String.format("B%d", startRowIndex + 1),
                        "demo");
                cellValueSet.doOneCellValue(partsTypeCellVal);
            }
        }
    }

    //写入Display Info
    private void getDisplayInfo(CellStyleSet cellStyleSet, CellValueSet cellValueSet, int startRowIndex, int rowCount, JsonPartInfo_ecock partInfo) throws Exception{
        JsonDisplayInfo jsonDisplayInfo = partInfo.getDisplayInfo();
        if(jsonDisplayInfo != null){
            //写入Chapter
            CellStyleInfo displayChapterCellStyle = new CellStyleInfo(
                    String.format("B%d:B%d", startRowIndex+1, startRowIndex+rowCount),
                    "Meiryo UI", 11,true, false,
                    "center","center","thin","thin","thin",
                    "thin",false, new int[]{201,255,255});
            cellStyleSet.doOneCellStyle(displayChapterCellStyle);

            if ( jsonDisplayInfo.displayChapter != null ) {
                SheetPosValue partsTypeCellVal = new SheetPosValue(String.format("B%d", startRowIndex + 1),
                        jsonDisplayInfo.displayChapter );
                cellValueSet.doOneCellValue(partsTypeCellVal);
            }

            //写入sub Chapter
            CellStyleInfo displaySubChapterCellStyle = new CellStyleInfo(
                    String.format("C%d:C%d", startRowIndex+1, startRowIndex+rowCount),
                    "Meiryo UI", 11,true, false,
                    "center","center","thin","thin","thin",
                    "thin",false, new int[]{255,205,156});
            cellStyleSet.doOneCellStyle(displaySubChapterCellStyle);

            if (jsonDisplayInfo.displaySubChapter != null ) {
                SheetPosValue partsTypeCellVal = new SheetPosValue(String.format("C%d", startRowIndex + 1),
                        jsonDisplayInfo.displaySubChapter );
                cellValueSet.doOneCellValue(partsTypeCellVal);
            }

            //写入No
            CellStyleInfo displayNoCellStyle = new CellStyleInfo(
                    String.format("D%d:D%d", startRowIndex+1, startRowIndex+rowCount),
                    "Meiryo UI", 11,true, false,
                    "center","center","thin","thin","thin",
                    "thin",false, new int[]{255,205,156});
            cellStyleSet.doOneCellStyle(displayNoCellStyle);

            if (jsonDisplayInfo.displayPartsNo != null ) {
                SheetPosValue partsTypeCellVal = new SheetPosValue(String.format("D%d", startRowIndex + 1),
                        jsonDisplayInfo.displayPartsNo );
                cellValueSet.doOneCellValue(partsTypeCellVal);
            }

            //写入Parts Name
            CellStyleInfo displayPartsNameNoCellStyle = new CellStyleInfo(
                    String.format("E%d:E%d", startRowIndex+1, startRowIndex+rowCount),
                    "Meiryo UI", 11,true, false,
                    "center","center","thin","thin","thin",
                    "thin",false, new int[]{172,255,196});
            cellStyleSet.doOneCellStyle(displayPartsNameNoCellStyle);

            if (jsonDisplayInfo.displayPartsNo != null ) {
                SheetPosValue partsTypeCellVal = new SheetPosValue(String.format("E%d", startRowIndex + 1), "1" );
                cellValueSet.doOneCellValue(partsTypeCellVal);
            }
            CellStyleInfo displayPartsNameCellStyle = new CellStyleInfo(
                    String.format("F%d:I%d", startRowIndex+1, startRowIndex+rowCount),
                    "Meiryo UI", 11,true, false,
                    "center","center","thin","thin","thin",
                    "thin",false, new int[]{});
            cellStyleSet.doOneCellStyle(displayPartsNameCellStyle);

            if (jsonDisplayInfo.partsName != null ) {
                SheetPosValue partsTypeCellVal = new SheetPosValue(String.format("F%d", startRowIndex + 1),
                        jsonDisplayInfo.partsName );
                cellValueSet.doOneCellValue(partsTypeCellVal);
            }

            //写入Display Content
            CellStyleInfo displayDisplayContentCellStyle = new CellStyleInfo(
                    String.format("J%d:O%d", startRowIndex+1, startRowIndex+rowCount),
                    "Meiryo UI", 11,true, false,
                    "center","center","thin","thin","thin",
                    "thin",false, new int[]{});
            cellStyleSet.doOneCellStyle(displayDisplayContentCellStyle);

            if (jsonDisplayInfo.displayContent != null ) {
                SheetPosValue partsTypeCellVal = new SheetPosValue(String.format("J%d", startRowIndex + 1),
                        jsonDisplayInfo.displayContent );
                cellValueSet.doOneCellValue(partsTypeCellVal);
            }

            //写入Formula
            CellStyleInfo displayFormulaCellStyle = new CellStyleInfo(
                    String.format("P%d:R%d", startRowIndex+1, startRowIndex+rowCount),
                    "Meiryo UI", 11,true, false,
                    "center","center","thin","thin","thin",
                    "thin",false, new int[]{});
            cellStyleSet.doOneCellStyle(displayFormulaCellStyle);

            if (jsonDisplayInfo.displayPartsNo != null ) {
                SheetPosValue partsTypeCellVal = new SheetPosValue(String.format("P%d", startRowIndex + 1),
                        jsonDisplayInfo.displayPartsNo );
                cellValueSet.doOneCellValue(partsTypeCellVal);
            }

            //写入Display Condition
            if(jsonDisplayInfo.displayCondition != null){
                for(int i = 0; i < jsonDisplayInfo.displayCondition.size(); i++){
                    JsonConditionModel oneSCondition = jsonDisplayInfo.displayCondition.get(i);
                    CellStyleInfo displayDisplayConditionKeyCellStyle = new CellStyleInfo(
                            String.format("S%d:S%d", startRowIndex + i +1, startRowIndex + i +1),
                            "Meiryo UI", 11,false, false,
                            "center","center","thin","thin","thin",
                            "thin",false, new int[]{});
                    cellStyleSet.doOneCellStyle(displayDisplayConditionKeyCellStyle);

                    if (oneSCondition.DisplayConditionModelKey != null ) {
                        SheetPosValue partsTypeCellVal = new SheetPosValue(String.format("S%d", startRowIndex + i  + 1),
                                oneSCondition.DisplayConditionModelKey );
                        cellValueSet.doOneCellValue(partsTypeCellVal);
                    }

                    CellStyleInfo displayDisplayConditionValueCellStyle = new CellStyleInfo(
                            String.format("T%d:Y%d", startRowIndex + i +1, startRowIndex + i +1),
                            "Meiryo UI", 11,true, false,
                            "center","center","thin","thin","thin",
                            "thin",false, new int[]{});
                    cellStyleSet.doOneCellStyle(displayDisplayConditionValueCellStyle);

                    if (oneSCondition.DisplayConditionModelContent != null ) {
                        SheetPosValue partsTypeCellVal = new SheetPosValue(String.format("T%d", startRowIndex + i  + 1),
                                oneSCondition.DisplayConditionModelContent );
                        cellValueSet.doOneCellValue(partsTypeCellVal);
                    }
                }
            }

            //写入Display in Such Condition
            CellStyleInfo displayDisplayInSuchConditionCellStyle = new CellStyleInfo(
                    String.format("Z%d:AE%d", startRowIndex+1, startRowIndex+rowCount),
                    "Meiryo UI", 11,true, false,
                    "center","center","thin","thin","thin",
                    "thin",false, new int[]{});
            cellStyleSet.doOneCellStyle(displayDisplayInSuchConditionCellStyle);

            if (jsonDisplayInfo.displayInSuchCondition != null ) {
                SheetPosValue partsTypeCellVal = new SheetPosValue(String.format("Z%d", startRowIndex + 1),
                        jsonDisplayInfo.displayInSuchCondition );
                cellValueSet.doOneCellValue(partsTypeCellVal);
            }

            //写入PartsID
            CellStyleInfo displayPartsIDCellStyle = new CellStyleInfo(
                    String.format("AF%d:AK%d", startRowIndex+1, startRowIndex+rowCount),
                    "Meiryo UI", 11,true, false,
                    "center","center","thin","thin","thin",
                    "thin",false, new int[]{});
            cellStyleSet.doOneCellStyle(displayPartsIDCellStyle);

            if (jsonDisplayInfo.partsID != null ) {
                SheetPosValue partsTypeCellVal = new SheetPosValue(String.format("AF%d", startRowIndex + 1),
                        jsonDisplayInfo.partsID );
                cellValueSet.doOneCellValue(partsTypeCellVal);
            }

            //写入Data Range
            CellStyleInfo displayDataRangeCellStyle = new CellStyleInfo(
                    String.format("AL%d:AN%d", startRowIndex+1, startRowIndex+rowCount),
                    "Meiryo UI", 11,true, false,
                    "center","center","thin","thin","thin",
                    "thin",false, new int[]{});
            cellStyleSet.doOneCellStyle(displayDataRangeCellStyle);

            if (jsonDisplayInfo.dataRange != null ) {
                SheetPosValue partsTypeCellVal = new SheetPosValue(String.format("AL%d", startRowIndex + 1),
                        jsonDisplayInfo.dataRange );
                cellValueSet.doOneCellValue(partsTypeCellVal);
            }

            //写入Remark
            CellStyleInfo displayRemarkCellStyle = new CellStyleInfo(
                    String.format("AO%d:AR%d", startRowIndex+1, startRowIndex+rowCount),
                    "Meiryo UI", 11,true, false,
                    "center","center","thin","thin","thin",
                    "thin",false, new int[]{});
            cellStyleSet.doOneCellStyle(displayRemarkCellStyle);

            if (jsonDisplayInfo.remark != null ) {
                SheetPosValue partsTypeCellVal = new SheetPosValue(String.format("AO%d", startRowIndex + 1),
                        jsonDisplayInfo.remark );
                cellValueSet.doOneCellValue(partsTypeCellVal);
            }

            //写入Parts Type
            CellStyleInfo displayPartsTypeCellStyle = new CellStyleInfo(
                    String.format("AV%d:AX%d", startRowIndex+1, startRowIndex+rowCount),
                    "Meiryo UI", 11,true, false,
                    "center","center","thin","thin","thin",
                    "thin",false, new int[]{});
            cellStyleSet.doOneCellStyle(displayPartsTypeCellStyle);

            if (jsonDisplayInfo.partsType != null ) {
                SheetPosValue partsTypeCellVal = new SheetPosValue(String.format("AV%d", startRowIndex + 1),
                        jsonDisplayInfo.partsType );
                cellValueSet.doOneCellValue(partsTypeCellVal);
            }

            //写入Property
            CellStyleInfo displayPropertyCellStyle = new CellStyleInfo(
                    String.format("AY%d:BA%d", startRowIndex+1, startRowIndex+rowCount),
                    "Meiryo UI", 11,true, false,
                    "center","center","thin","thin","thin",
                    "thin",false, new int[]{});
            cellStyleSet.doOneCellStyle(displayPropertyCellStyle);

            if (jsonDisplayInfo.property != null ) {
                SheetPosValue partsTypeCellVal = new SheetPosValue(String.format("AY%d", startRowIndex + 1),
                        jsonDisplayInfo.property );
                cellValueSet.doOneCellValue(partsTypeCellVal);
            }

            //写入Add Display Condition In View Model
            CellStyleInfo displayAddDisplayConditionInViewModelCellStyle = new CellStyleInfo(
                    String.format("BB%d:BF%d", startRowIndex+1, startRowIndex+rowCount),
                    "Meiryo UI", 11,true, false,
                    "center","center","thin","thin","thin",
                    "thin",false, new int[]{});
            cellStyleSet.doOneCellStyle(displayAddDisplayConditionInViewModelCellStyle);

            if (jsonDisplayInfo.addDisplayConditionInViewModel != null ) {
                SheetPosValue partsTypeCellVal = new SheetPosValue(String.format("BB%d", startRowIndex + 1),
                        jsonDisplayInfo.addDisplayConditionInViewModel );
                cellValueSet.doOneCellValue(partsTypeCellVal);
            }

            //写入Display Condition In View Model
            if(jsonDisplayInfo.displayConditionInViewModel != null){
                for(int i = 0; i < jsonDisplayInfo.displayConditionInViewModel.size(); i++){
                    JsonConditionModel oneSCondition = jsonDisplayInfo.displayConditionInViewModel.get(i);
                    CellStyleInfo displayDisplayConditionKeyCellStyle = new CellStyleInfo(
                            String.format("BG%d:BG%d", startRowIndex + i +1, startRowIndex + i +1),
                            "Meiryo UI", 11,false, false,
                            "center","center","thin","thin","thin",
                            "thin",false, new int[]{});
                    cellStyleSet.doOneCellStyle(displayDisplayConditionKeyCellStyle);

                    if (oneSCondition.DisplayConditionModelKey != null ) {
                        SheetPosValue partsTypeCellVal = new SheetPosValue(String.format("BG%d", startRowIndex + i + 1),
                                oneSCondition.DisplayConditionModelKey );
                        cellValueSet.doOneCellValue(partsTypeCellVal);
                    }

                    CellStyleInfo displayDisplayConditionValueCellStyle = new CellStyleInfo(
                            String.format("BH%d:BK%d", startRowIndex + i +1, startRowIndex + i +1),
                            "Meiryo UI", 11,true, false,
                            "center","center","thin","thin","thin",
                            "thin",false, new int[]{});
                    cellStyleSet.doOneCellStyle(displayDisplayConditionValueCellStyle);

                    if (oneSCondition.DisplayConditionModelContent != null ) {
                        SheetPosValue partsTypeCellVal = new SheetPosValue(String.format("BH%d", startRowIndex + i + 1),
                                oneSCondition.DisplayConditionModelContent );
                        cellValueSet.doOneCellValue(partsTypeCellVal);
                    }
                }
            }

            //写入StringID
            CellStyleInfo displayStringIDCellStyle = new CellStyleInfo(
                    String.format("BL%d:BM%d", startRowIndex+1, startRowIndex+rowCount),
                    "Meiryo UI", 11,true, false,
                    "center","center","thin","thin","thin",
                    "thin",false, new int[]{});
            cellStyleSet.doOneCellStyle(displayStringIDCellStyle);

            if (jsonDisplayInfo.stringID != null ) {
                SheetPosValue partsTypeCellVal = new SheetPosValue(String.format("BL%d", startRowIndex + 1),
                        jsonDisplayInfo.stringID );
                cellValueSet.doOneCellValue(partsTypeCellVal);
            }

            //写入Visible
            CellStyleInfo displayVisibleCellStyle = new CellStyleInfo(
                    String.format("BN%d:BO%d", startRowIndex+1, startRowIndex+rowCount),
                    "Meiryo UI", 11,true, false,
                    "center","center","thin","thin","thin",
                    "thin",false, new int[]{});
            cellStyleSet.doOneCellStyle(displayVisibleCellStyle);

            if (jsonDisplayInfo.visible != null ) {
                SheetPosValue partsTypeCellVal = new SheetPosValue(String.format("BN%d", startRowIndex + 1),
                        jsonDisplayInfo.visible );
                cellValueSet.doOneCellValue(partsTypeCellVal);
            }

        }
    }
    private int getPartRowCount(JsonPartInfo_ecock partInfo) {
        int retRowCount = 3;

        ArrayList<JsonPartCondition> onePartConList = new ArrayList<>();

        onePartConList.add(partInfo.getDisplayCondition());

        JsonTxtImgInfo txtImgInfo = partInfo.getTextImgInfo();
        if (txtImgInfo != null) {
            onePartConList.add(txtImgInfo.getDelConditionInMotion());
        }

        JsonPartSWInfo swInfo = partInfo.getSWInfo();
        if (swInfo != null) {
            onePartConList.add(swInfo.getSelectedCon());
            onePartConList.add(swInfo.getTonedownConExceptInMotion());
            onePartConList.add(swInfo.getTonedownConInMotion());
        }

        for (JsonPartCondition iPartCon : onePartConList) {
            if (iPartCon == null){
                continue;
            }
            if (iPartCon.getConditionList().size() > retRowCount) {
                retRowCount = iPartCon.getConditionList().size();
            }
        }

        return retRowCount;
    }

    public int[] calCellInfoByPixel(int[] startCell, int[] cellOffset, double[] pxInfo) {
        int[] retPixelInfo = new int[] {0,0,0,0};

        int iStartCol = startCell[0];
        int iStartColOffset = cellOffset[0];
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

}
