package sketch.hmi;

import javafx.scene.control.Cell;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.*;
import org.omg.PortableInterceptor.DISCARDING;
import sketch.common.*;
import sketch.settings.HMIDropDownData;
import sketch.settings.HMIPartsSpecStatic;
import sketch.settings.HMIPartsSpecStatic_new;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class HMIPartsSpec_new {
    private XSSFWorkbook workBook = null;
    private XSSFSheet sheetInfo = null;
    private int StartRowIndex = 2;
    private int DefaultTableSpace = 1;
    private ArrayList<Integer> indexDevNameList = new ArrayList<>();

    public HMIPartsSpec_new(XSSFWorkbook _workBook, XSSFSheet _xssfSheet) {
        this.workBook = _workBook;
        this.sheetInfo = _xssfSheet;
    }

    public int doExportInfo(JsonSketchInfo_new jsonSketchInfo) throws Exception {
        sheetInfo.setDisplayGridlines(false);
        int imageStartRow = 0;
        XSSFDrawing drawing = this.sheetInfo.createDrawingPatriarch();
        // Title
        StartRowIndex = doExportScreenTitle(jsonSketchInfo, StartRowIndex);
        // Outline
        StartRowIndex = doExportOutline(jsonSketchInfo, StartRowIndex);
        imageStartRow = StartRowIndex;
        // 1
        StartRowIndex = doExportDisplayRefInfo(jsonSketchInfo, StartRowIndex, drawing);
        // 2
        StartRowIndex = doExportActiveRefInfo(jsonSketchInfo, StartRowIndex);
        // 3
        StartRowIndex = doExportActionRefInfo(jsonSketchInfo, StartRowIndex);
        // 4
        StartRowIndex = doExportHKRefInfo(jsonSketchInfo, StartRowIndex);
        // 5
        StartRowIndex = doExportInitRefInfo(jsonSketchInfo, StartRowIndex);
        // 6
        StartRowIndex = doExportStatusChangeRefInfo(jsonSketchInfo, StartRowIndex);
        // 7
        StartRowIndex = doExportTransitionRefInfo(jsonSketchInfo, StartRowIndex);
        // 8
        StartRowIndex = doExportTrigRefInfo(jsonSketchInfo, StartRowIndex);

        return imageStartRow;
    }

    private int doExportScreenTitle(JsonSketchInfo_new jsonSketchInfo, int StartRowIndex) throws Exception {
        // 表头设置
        HMIPartsSpecStatic_new staticInfo = new HMIPartsSpecStatic_new();
        CellSizeSet cellSizeSet = new CellSizeSet(this.workBook, this.sheetInfo);
        ArrayList<SheetPosValue> rowHeightList = staticInfo.rowHeightLists.get("rowHeight");
        cellSizeSet.doSetRowHeightList(rowHeightList);

        CellStyleSet cellStyleSet = new CellStyleSet(this.workBook, this.sheetInfo);
        ArrayList<CellStyleInfo> styleList = staticInfo.styleLists.get("Screen");
        cellStyleSet.doCellStyleList(styleList);

        CellValueSet cellValueSet = new CellValueSet(this.workBook, this.sheetInfo);
        ArrayList<SheetPosValue> lableList = staticInfo.lableLists.get("Screen");
        ArrayList<SheetPosValue> newlableList = new ArrayList<>();
        for (int i = 0; i < lableList.size(); ++i) {
            SheetPosValue lable = lableList.get(i);
            if (lable.posInfo.equals("B2")) {
                if (jsonSketchInfo.getScreenID() != null) {
                    lable.strValue = jsonSketchInfo.getScreenID();
                }
            } else if (lable.posInfo.equals("K2")) {
                if (jsonSketchInfo.getScreenName() != null) {
                    lable.strValue = jsonSketchInfo.getScreenName();
                }
            } else if (lable.posInfo.equals("AU2")) {
                if (jsonSketchInfo.getScreenName() != null) {
                    lable.strValue = jsonSketchInfo.getScreenUUID();
                }
            }
            newlableList.add(lable);
        }
        cellValueSet.doCellValueList(newlableList);

        return StartRowIndex + 2;
    }

    private int doExportOutline(JsonSketchInfo_new jsonSketchInfo, int StartRowIndex) throws Exception {
        ArrayList<String> bindTypeList = jsonSketchInfo.getBindTypeList();
        ArrayList<JsonPartInfo_new> jsonPartInfoList = jsonSketchInfo.getJsonPartInfoList();

        // 表头设置
        HMIPartsSpecStatic_new staticInfo = new HMIPartsSpecStatic_new();

        CellStyleSet cellStyleSet = new CellStyleSet(this.workBook, this.sheetInfo);
        ArrayList<CellStyleInfo> styleList = getNewStyleList(staticInfo.styleLists.get("Outline"), StartRowIndex, 0);
        ArrayList<CellStyleInfo> styleList_1 = new ArrayList<>();
        ArrayList<CellStyleInfo> styleList_2 = new ArrayList<>();

        for (int i = 0; i < styleList.size(); ++i) {
            CellStyleInfo style = styleList.get(i);
            int[] row = getPosRow(style.posInfo);
            if (row[0] == 4 || row[0] == 6) {
                styleList_1.add(style);
            } else if (row[0] == 7) {
                styleList_2.add(style);
            }
        }
        cellStyleSet.doCellStyleList(styleList_1);

        CellValueSet cellValueSet = new CellValueSet(this.workBook, this.sheetInfo);
        ArrayList<SheetPosValue> lableList = getNewLableList(staticInfo.lableLists.get("Outline"), StartRowIndex, 0);
        ArrayList<SheetPosValue> lableList_1 = new ArrayList<>();
        ArrayList<SheetPosValue> lableList_2 = new ArrayList<>();

        for (int j = 0; j < lableList.size(); ++j) {
            SheetPosValue lable = lableList.get(j);
            int row = getPosRowSingle(lable.posInfo);
            if (row == 4 || row == 6) {
                lableList_1.add(lable);
            } else if (row == 7) {
                lableList_2.add(lable);
            }
        }
        cellValueSet.doCellValueList(lableList_1);

        HashMap<String, String> posList = new HashMap<String, String>();
        for (int k = 0; k < lableList_2.size() && k < styleList_2.size(); ++k) {
            SheetPosValue lable = lableList_2.get(k);
            CellStyleInfo style = styleList_2.get(k);
            if (lable.strValue.equals("0")) {
                lable.strValue = "Chapter";
            } else if (lable.strValue.equals("1")) {
                lable.strValue = "SubChapter";
            } else if (lable.strValue.equals("Honda")) {
                lable.strValue = "BindType";
            }
            posList.put(lable.strValue, style.posInfo);
        }

        int startRowCount = 0;
        for (int bindTypeIndex = 0; bindTypeIndex < bindTypeList.size(); ++bindTypeIndex) {
            startRowCount += bindTypeIndex;
            String bindType = bindTypeList.get(bindTypeIndex);
            ArrayList<SheetPosValue> oldPosInfoList = new ArrayList<>();
            oldPosInfoList.add(new SheetPosValue( posList.get("日本"), "日本"));
            oldPosInfoList.add(new SheetPosValue( posList.get("北米"), "北米"));
            oldPosInfoList.add(new SheetPosValue( posList.get("欧州"), "欧州"));
            oldPosInfoList.add(new SheetPosValue( posList.get("韓国"), "韓国"));
            oldPosInfoList.add(new SheetPosValue( posList.get("香港マカオ"), "香港マカオ"));
            oldPosInfoList.add(new SheetPosValue( posList.get("タイ"), "タイ"));
            oldPosInfoList.add(new SheetPosValue( posList.get("オセアニア"), "オセアニア"));
            oldPosInfoList.add(new SheetPosValue( posList.get("南ア"), "南ア"));
            oldPosInfoList.add(new SheetPosValue( posList.get("中国"), "中国"));
            oldPosInfoList.add(new SheetPosValue( posList.get("中近東"), "中近東"));
            oldPosInfoList.add(new SheetPosValue( posList.get("台湾"), "台湾"));
            oldPosInfoList.add(new SheetPosValue( posList.get("東南アジア"), "東南アジア"));
            oldPosInfoList.add(new SheetPosValue( posList.get("中南米(CS/BR)"), "中南米(CS/BR)"));
            oldPosInfoList.add(new SheetPosValue( posList.get("中南米(AR)"), "中南米(AR)"));
            oldPosInfoList.add(new SheetPosValue( posList.get("インド"), "インド"));
            // 写入Chapter
            {
                String oldChapterPosInfo = posList.get("Chapter");
                int[] posRow = getPosRow(oldChapterPosInfo);
                String[] posCol = getPosCol(oldChapterPosInfo);
                String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - 1));
                String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                String newChapterPosInfo = pos_0 + ":" + pos_1;

                CellStyleInfo chapterCellStyle = new CellStyleInfo(
                        String.format("%s", newChapterPosInfo),
                        "Meiryo UI", 11, false, true,
                        "center", "center", "thin", "thin", "thin",
                        "thin", false, new int[]{201,255,255});
                cellStyleSet.doOneCellStyle(chapterCellStyle);
                SheetPosValue chapterCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue("0"));
                cellValueSet.doOneCellValue(chapterCellVal);
            }

            // 写入SubChapter
            {
                String oldSubChapterPosInfo = posList.get("SubChapter");
                int[] posRow = getPosRow(oldSubChapterPosInfo);
                String[] posCol = getPosCol(oldSubChapterPosInfo);
                String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - 1));
                String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                String newSubChapterPosInfo = pos_0 + ":" + pos_1;

                CellStyleInfo subChapterCellStyle = new CellStyleInfo(
                        String.format("%s", newSubChapterPosInfo),
                        "Meiryo UI", 11, false, true,
                        "center", "center", "thin", "thin", "thin",
                        "thin", false, new int[]{255,205,156});
                cellStyleSet.doOneCellStyle(subChapterCellStyle);
                SheetPosValue subChapterCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(String.valueOf(bindTypeIndex + 1)));
                cellValueSet.doOneCellValue(subChapterCellVal);
            }

            // 写入BandType
            {
                String oldBindTypePosInfo = posList.get("BindType");
                int[] posRow = getPosRow(oldBindTypePosInfo);
                String[] posCol = getPosCol(oldBindTypePosInfo);
                String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - 1));
                String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                String newBindTypePosInfo = pos_0 + ":" + pos_1;

                CellStyleInfo bindTypeCellStyle = new CellStyleInfo(
                        String.format("%s", newBindTypePosInfo),
                        "Meiryo UI", 11, true, true,
                        "center", "center", "thin", "thin", "thin",
                        "thin", false, new int[]{255,205,156});
                cellStyleSet.doOneCellStyle(bindTypeCellStyle);
                SheetPosValue bindTypeCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(bindType));
                cellValueSet.doOneCellValue(bindTypeCellVal);
            }

            // 写入各仕向地
            {
                for (int posInfoIndex = 0; posInfoIndex < oldPosInfoList.size(); ++posInfoIndex) {
                    SheetPosValue oldPosInfo = oldPosInfoList.get(posInfoIndex);
                    int[] posRow = getPosRow(oldPosInfo.posInfo);
                    String[] posCol = getPosCol(oldPosInfo.posInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - 1));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newPosInfo = pos_0 + ":" + pos_1;
                    boolean isMerge = posCol[0].equals(posCol[1]) ? false : true;
                    CellStyleInfo cellStyle = new CellStyleInfo(
                            String.format("%s", newPosInfo),
                            "Meiryo UI", 11, isMerge, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255,255,153});
                    cellStyleSet.doOneCellStyle(cellStyle);
                    SheetPosValue cellVal = new SheetPosValue(String.format("%s", pos_0), oldPosInfo.strValue);
                    cellValueSet.doOneCellValue(cellVal);
                }
            }

            // 写入Remark
            {
                String oldChapterPosInfo = posList.get("Remark");
                int[] posRow = getPosRow(oldChapterPosInfo);
                String[] posCol = getPosCol(oldChapterPosInfo);
                String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - 1));
                String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                String newChapterPosInfo = pos_0 + ":" + pos_1;

                CellStyleInfo chapterCellStyle = new CellStyleInfo(
                        String.format("%s", newChapterPosInfo),
                        "Meiryo UI", 11, true, true,
                        "center", "center", "thin", "thin", "thin",
                        "thin", false, new int[]{201,255,255});
                cellStyleSet.doOneCellStyle(chapterCellStyle);
                SheetPosValue chapterCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue("Remark"));
                cellValueSet.doOneCellValue(chapterCellVal);
            }

            // 写入UUID
            {
                String oldChapterPosInfo = posList.get("UUID");
                int[] posRow = getPosRow(oldChapterPosInfo);
                String[] posCol = getPosCol(oldChapterPosInfo);
                String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - 1));
                String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                String newChapterPosInfo = pos_0 + ":" + pos_1;

                CellStyleInfo chapterCellStyle = new CellStyleInfo(
                        String.format("%s", newChapterPosInfo),
                        "Meiryo UI", 11, false, true,
                        "center", "center", "thin", "thin", "thin",
                        "thin", false, new int[]{201,255,255});
                cellStyleSet.doOneCellStyle(chapterCellStyle);
                SheetPosValue chapterCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue("UUID"));
                cellValueSet.doOneCellValue(chapterCellVal);
            }
            int stateNo = 0;
            for (int k = 0; k < jsonPartInfoList.size(); ++k) {
                JsonPartInfo_new jsonPartInfo = jsonPartInfoList.get(k);
                JsonDestGradeInfo destGradeInfo = jsonPartInfo.getDestGradeInfo();
                String str1 = destGradeInfo.getAvailableModelBandType();
                String str2 = bindTypeList.get(bindTypeIndex);
                boolean isSameBindType = false;
                if (str1 != null) {
                    isSameBindType = str1.equals(str2);
                }
                if (isSameBindType && checkDestGradeInfo(destGradeInfo)) {
                    stateNo += 1;
                    startRowCount += 1;
                    boolean isMergeRow = isMergeRow(1);

                    // 写入Chapter
                    {
                        String oldChapterPosInfo = posList.get("Chapter");
                        int[] posRow = getPosRow(oldChapterPosInfo);
                        String[] posCol = getPosCol(oldChapterPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - 1));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newChapterPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo chapterCellStyle = new CellStyleInfo(
                                String.format("%s", newChapterPosInfo),
                                "Meiryo UI", 11, isMergeRow, true,
                                "center", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{201,255,255});
                        cellStyleSet.doOneCellStyle(chapterCellStyle);
                        SheetPosValue chapterCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(destGradeInfo.getAvailableModelChapter()));
                        cellValueSet.doOneCellValue(chapterCellVal);
                    }

                    // 写入SubChapter
                    {
                        String oldSubChapterPosInfo = posList.get("SubChapter");
                        int[] posRow = getPosRow(oldSubChapterPosInfo);
                        String[] posCol = getPosCol(oldSubChapterPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - 1));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newSubChapterPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo subChapterCellStyle = new CellStyleInfo(
                                String.format("%s", newSubChapterPosInfo),
                                "Meiryo UI", 11, false, true,
                                "center", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255,205,156});
                        cellStyleSet.doOneCellStyle(subChapterCellStyle);
                        SheetPosValue subChapterCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(destGradeInfo.getAvailableModelSubChapter()));
                        cellValueSet.doOneCellValue(subChapterCellVal);
                    }

                    // 写入BandType
                    {
                        String oldBindTypePosInfo = posList.get("BindType");
                        int[] posRow = getPosRow(oldBindTypePosInfo);
                        String[] posCol = getPosCol(oldBindTypePosInfo);
                        // 写入StateNo
                        {
                            String pos_0 = getNewCol(posCol[0], 0) + String.valueOf(posRow[0] + 1 + (startRowCount - 1));
                            String pos_1 = getNewCol(posCol[0], 0) + String.valueOf(posRow[0] + startRowCount);
                            String newNOPosInfo = pos_0 + ":" + pos_1;

                            CellStyleInfo noCellStyle = new CellStyleInfo(
                                    String.format("%s", newNOPosInfo),
                                    "Meiryo UI", 11, isMergeRow, true,
                                    "center", "center", "thin", "thin", "thin",
                                    "thin", false, new int[]{204,255,204});
                            cellStyleSet.doOneCellStyle(noCellStyle);
                            SheetPosValue noCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(String.valueOf(stateNo)));
                            cellValueSet.doOneCellValue(noCellVal);
                        }

                        // 写入Name
                        {
                            String pos_0 = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + (startRowCount - 1));
                            String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                            String newNOPosInfo = pos_0 + ":" + pos_1;

                            CellStyleInfo noCellStyle = new CellStyleInfo(
                                    String.format("%s", newNOPosInfo),
                                    "Meiryo UI", 11, true, true,
                                    "left", "center", "thin", "thin", "thin",
                                    "thin", false, new int[]{204,255,204});
                            cellStyleSet.doOneCellStyle(noCellStyle);
                            SheetPosValue noCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(destGradeInfo.getAvailableModelName()));
                            cellValueSet.doOneCellValue(noCellVal);
                        }
                    }

                    // 写入各仕向地
                    {
                        ArrayList<String> isAdaptRegionList = new ArrayList<>();
                        isAdaptRegionList.add(destGradeInfo.getIsAdaptJapan());
                        isAdaptRegionList.add(destGradeInfo.getIsAdaptNorthAmerica());
                        isAdaptRegionList.add(destGradeInfo.getIsAdaptEurope());
                        isAdaptRegionList.add(destGradeInfo.getIsAdaptKorea());
                        isAdaptRegionList.add(destGradeInfo.getIsAdaptHongKongMacao());
                        isAdaptRegionList.add(destGradeInfo.getIsAdaptThailand());
                        isAdaptRegionList.add(destGradeInfo.getIsAdaptOceania());
                        isAdaptRegionList.add(destGradeInfo.getIsAdaptSouthAfrica());
                        isAdaptRegionList.add(destGradeInfo.getIsAdaptChina());
                        isAdaptRegionList.add(destGradeInfo.getIsAdaptMiddleEast());
                        isAdaptRegionList.add(destGradeInfo.getIsAdaptTaiwan());
                        isAdaptRegionList.add(destGradeInfo.getIsAdaptSoutheastAsia());
                        isAdaptRegionList.add(destGradeInfo.getIsAdaptCSOrBR());
                        isAdaptRegionList.add(destGradeInfo.getIsAdaptLatinAmericaAR());
                        isAdaptRegionList.add(destGradeInfo.getIsAdaptIndia());

                        for (int posInfoIndex = 0; posInfoIndex < oldPosInfoList.size(); ++posInfoIndex) {
                            SheetPosValue oldPosInfo = oldPosInfoList.get(posInfoIndex);
                            int[] posRow = getPosRow(oldPosInfo.posInfo);
                            String[] posCol = getPosCol(oldPosInfo.posInfo);
                            String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - 1));
                            String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                            String newPosInfo = pos_0 + ":" + pos_1;
                            boolean isMerge = posCol[0].equals(posCol[1]) ? false : true;
                            CellStyleInfo cellStyle = new CellStyleInfo(
                                    String.format("%s", newPosInfo),
                                    "Meiryo UI", 11, isMerge, true,
                                    "left", "center", "thin", "thin", "thin",
                                    "thin", false, new int[]{255,255,153});
                            cellStyleSet.doOneCellStyle(cellStyle);
                            SheetPosValue cellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(isAdaptRegionList.get(posInfoIndex)));
                            cellValueSet.doOneCellValue(cellVal);
                        }
                    }

                    // 写入Remark
                    {
                        String oldChapterPosInfo = posList.get("Remark");
                        int[] posRow = getPosRow(oldChapterPosInfo);
                        String[] posCol = getPosCol(oldChapterPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - 1));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newChapterPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo chapterCellStyle = new CellStyleInfo(
                                String.format("%s", newChapterPosInfo),
                                "Meiryo UI", 11, true, true,
                                "center", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{201,255,255});
                        cellStyleSet.doOneCellStyle(chapterCellStyle);
                        SheetPosValue chapterCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(destGradeInfo.getAvailableModelRemark()));
                        cellValueSet.doOneCellValue(chapterCellVal);
                    }

                    // 写入UUID
                    {
                        String oldChapterPosInfo = posList.get("UUID");
                        int[] posRow = getPosRow(oldChapterPosInfo);
                        String[] posCol = getPosCol(oldChapterPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - 1));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newChapterPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo chapterCellStyle = new CellStyleInfo(
                                String.format("%s", newChapterPosInfo),
                                "Meiryo UI", 11, false, true,
                                "center", "center", "thin", "thin", "thin",
                                "thin", true, new int[]{201,255,255});
                        cellStyleSet.doOneCellStyle(chapterCellStyle);
                        SheetPosValue chapterCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(jsonSketchInfo.getSketchInfoId() + "->" + jsonPartInfo.getUuid()));
                        cellValueSet.doOneCellValue(chapterCellVal);
                    }
                }
            }
        }

        StartRowIndex += 4 + startRowCount;
        return StartRowIndex;
    }

    private int doExportDisplayRefInfo(JsonSketchInfo_new jsonSketchInfo, int StartRowIndex, XSSFDrawing drawing) throws Exception {
        // 表头设置
        HMIPartsSpecStatic_new staticInfo = new HMIPartsSpecStatic_new();

        CellStyleSet cellStyleSet = new CellStyleSet(this.workBook, this.sheetInfo);
        ArrayList<CellStyleInfo> styleList = getNewStyleList(staticInfo.styleLists.get("DisplayRefInfo"), StartRowIndex, 1);
        cellStyleSet.doCellStyleList(styleList);

        CellValueSet cellValueSet = new CellValueSet(this.workBook, this.sheetInfo);
        ArrayList<SheetPosValue> lableList = getNewLableList(staticInfo.lableLists.get("DisplayRefInfo"), StartRowIndex, 1);
        cellValueSet.doCellValueList(lableList);
        SheetPosValue lable_4 = lableList.get(4);
        int lable_4_row = getPosRowSingle(lable_4.posInfo);
        SheetPosValue lable_5 = lableList.get(5);
        int lable_5_row = getPosRowSingle(lable_5.posInfo);

        String newPosInfo_1 = "C" + String.valueOf(lable_4_row + 1) + ":" + "C" + String.valueOf(lable_5_row - 1);
        String newPosInfo_2 = "AR" + String.valueOf(lable_4_row + 1) + ":" + "AR" + String.valueOf(lable_5_row - 1);
        CellStyleInfo newPosInfo1CellStyle = new CellStyleInfo(
                String.format("%s", newPosInfo_1),
                "Meiryo UI", 11, true, true,
                "center", "center", "", "", "thin",
                "", false, new int[]{255,255,255});
        cellStyleSet.doOneCellStyle(newPosInfo1CellStyle);
        CellStyleInfo newPosInfo2CellStyle = new CellStyleInfo(
                String.format("%s", newPosInfo_2),
                "Meiryo UI", 11, true, true,
                "center", "center", "", "", "thin",
                "", false, new int[]{255,255,255});
        cellStyleSet.doOneCellStyle(newPosInfo2CellStyle);

        HashMap<String, String> posList = getPosList(lableList, styleList, 1);
        ArrayList<JsonPartInfo_new> jsonPartInfoList = sortByNo(jsonSketchInfo.getJsonPartInfoList(), 1);
        HMIDropDownData dropDownData = HMIDropDownData.getInstance();
        HMIDropDownData.DisplayDropDownData displayDropDownData = dropDownData.getDisplayDropDownDataInstance();
        int startRowCount = 0;
        for (int k = 0; k < jsonPartInfoList.size(); ++k) {
            JsonPartInfo_new jsonPartInfo = jsonPartInfoList.get(k);
            JsonDisplayRefInfo displayRefInfo = jsonPartInfo.getDisplayRefInfo();
            if (checkDisplayRefInfo(displayRefInfo)) {
                int rowCount = getDisplayRefInfoRowCount(displayRefInfo);
                startRowCount += rowCount;
                boolean isMergeRow = isMergeRow(rowCount);

                // 写入Chapter
                {
                    String oldChapterPosInfo = posList.get("Chapter");
                    int[] posRow = getPosRow(oldChapterPosInfo);
                    String[] posCol = getPosCol(oldChapterPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newChapterPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo chapterCellStyle = new CellStyleInfo(
                            String.format("%s", newChapterPosInfo),
                            "Meiryo UI", 11, isMergeRow, true,
                            "center", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{201,255,255});
                    cellStyleSet.doOneCellStyle(chapterCellStyle);
                    SheetPosValue chapterCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(displayRefInfo.getDisplayChapter()));
                    cellValueSet.doOneCellValue(chapterCellVal);
                }

                // 写入SubChapter
                {
                    String oldSubChapterPosInfo = posList.get("SubChapter");
                    int[] posRow = getPosRow(oldSubChapterPosInfo);
                    String[] posCol = getPosCol(oldSubChapterPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newSubChapterPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo subChapterCellStyle = new CellStyleInfo(
                            String.format("%s", newSubChapterPosInfo),
                            "Meiryo UI", 11, isMergeRow, true,
                            "center", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255,205,156});
                    cellStyleSet.doOneCellStyle(subChapterCellStyle);
                    SheetPosValue subChapterCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(displayRefInfo.getDisplaySubChapter()));
                    cellValueSet.doOneCellValue(subChapterCellVal);
                }

                // 写入NO.
                {
                    String oldNOPosInfo = posList.get("NO.");
                    int[] posRow = getPosRow(oldNOPosInfo);
                    String[] posCol = getPosCol(oldNOPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newNOPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo noCellStyle = new CellStyleInfo(
                            String.format("%s", newNOPosInfo),
                            "Meiryo UI", 11, isMergeRow, true,
                            "center", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255,205,156});
                    cellStyleSet.doOneCellStyle(noCellStyle);
                    SheetPosValue noCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(displayRefInfo.getPartsNumber()));
                    cellValueSet.doOneCellValue(noCellVal);
                }

                // 写入Parts Name
                {
                    String oldPartsNamePosInfo = posList.get("Parts Name");
                    int[] posRow = getPosRow(oldPartsNamePosInfo);
                    String[] posCol = getPosCol(oldPartsNamePosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_0_0 = posCol[0] + String.valueOf(posRow[0] + startRowCount);
                    String newPartsNamePosInfo_1 = pos_0 + ":" + pos_0_0;

                    CellStyleInfo partsNameCellStyle_1 = new CellStyleInfo(
                            String.format("%s", newPartsNamePosInfo_1),
                            "Meiryo UI", 11, isMergeRow, true,
                            "center", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{172, 255, 196});
                    cellStyleSet.doOneCellStyle(partsNameCellStyle_1);
                    SheetPosValue partsNameCellVal_1 = new SheetPosValue(String.format("%s", pos_0), checkCellValue(displayRefInfo.getDisplayStateNo()));
                    cellValueSet.doOneCellValue(partsNameCellVal_1);

                    String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newPartsNamePosInfo_2 = pos_0_new + ":" + pos_1;

                    CellStyleInfo partsNameCellStyle_2 = new CellStyleInfo(
                            String.format("%s", newPartsNamePosInfo_2),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(partsNameCellStyle_2);

                    SheetPosValue partsNameCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), checkCellValue(displayRefInfo.getPartsName()));
                    cellValueSet.doOneCellValue(partsNameCellVal_2);
                }

                // 写入DisplayContent
                {
                    String oldDisplayContentPosInfo = posList.get("Display Content");
                    int[] posRow = getPosRow(oldDisplayContentPosInfo);
                    String[] posCol = getPosCol(oldDisplayContentPosInfo);
                    int startLineNumber = posRow[0] + 1 + (startRowCount - rowCount);
                    String pos_0 = posCol[0] + String.valueOf(startLineNumber);
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newDisplayContentPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo displayContentCellStyle = new CellStyleInfo(
                            String.format("%s", newDisplayContentPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
//                    cellStyleSet.doOneCellStyle(displayContentCellStyle);
//                    SheetPosValue displayContentCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(displayRefInfo.getDisplayContent()));
//                    cellValueSet.doOneCellValue(displayContentCellVal);


                    cellStyleSet.doOneCellStyle(displayContentCellStyle);
                    String content = displayRefInfo.getDisplayContent();

                    if (content != null) {

                        if(content.startsWith("image:")){
                            String imagePath = content.replace("image:","");
                            File imgFile = new File(imagePath);

                            if (imgFile.exists() == true) {
                                int cellMaxHeight = 0;
                                for (int ii = startLineNumber; ii < startLineNumber + rowCount; ii++) {
                                    cellMaxHeight += (int) (this.sheetInfo.getRow(ii).getHeightInPoints());
                                }
                                double imgMaxHeight = cellMaxHeight * 0.95;
                                double imgRatio = 1.0;

                                BufferedImage bufferImg = ImageIO.read(new FileInputStream(imgFile.getPath()));
                                ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
                                String imgfileName = imgFile.getName();
                                ImageIO.write(bufferImg,imgfileName.substring(imgfileName.lastIndexOf(".")+1), byteArrayOut);

                                if (bufferImg.getHeight() > imgMaxHeight) {
                                    imgRatio = imgMaxHeight / bufferImg.getHeight();
                                }
                                if (bufferImg.getWidth() * imgRatio > 287.0) {
                                    imgRatio = 287.0/bufferImg.getWidth();
                                }

                                double scaleImgWidth = bufferImg.getWidth()*imgRatio;
                                double scaleImgHeight = bufferImg.getHeight()*imgRatio;
                                double centerOffsetX = ((287.0+4.0)-scaleImgWidth)/2.0;
                                double centerOffsetY = ((cellMaxHeight+2.0) - scaleImgHeight)/2.0;

                                int[] startCell = new int[]{9, startLineNumber-1};
                                int[] cellOffset = new int[]{0, 0};

                                int[] startImgPos = calCellInfoByPixel(startCell, cellOffset, new double[]{centerOffsetX, centerOffsetY});
                                int[] endImgPos = calCellInfoByPixel(startCell, cellOffset,
                                        new double[]{centerOffsetX+scaleImgWidth, centerOffsetY+scaleImgHeight});

                                XSSFClientAnchor imageAnchor = new XSSFClientAnchor(
                                        XSSFShape.EMU_PER_POINT*startImgPos[2],
                                        XSSFShape.EMU_PER_POINT*startImgPos[3],
                                        XSSFShape.EMU_PER_POINT*endImgPos[2],
                                        XSSFShape.EMU_PER_POINT*endImgPos[3],
                                        startImgPos[0],startImgPos[1], endImgPos[0],endImgPos[1]);

                                int pictureIdx = this.workBook.addPicture(byteArrayOut.toByteArray(), this.workBook.PICTURE_TYPE_PNG);

                                drawing.createPicture(imageAnchor, pictureIdx);
                            }
                            else {
                                cellValueSet.doOneCellValue(new SheetPosValue(String.format("%s", pos_0), imagePath));
                            }
                        } else if(content.startsWith("text:")){
                            String text = content.replace("text:","");
                            cellValueSet.doOneCellValue(new SheetPosValue(String.format("%s", pos_0), text));
                        } else {
                            cellValueSet.doOneCellValue(new SheetPosValue(String.format("%s", pos_0), content));
                        }

                    }
                }




                // 写入Formula
                {
                    String oldFormulaPosInfo = posList.get("Formula");
                    int[] posRow = getPosRow(oldFormulaPosInfo);
                    String[] posCol = getPosCol(oldFormulaPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newFormulaPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo formulaCellStyle = new CellStyleInfo(
                            String.format("%s", newFormulaPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(formulaCellStyle);
                    SheetPosValue formulaCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(displayRefInfo.getDisplayFormula()));
                    cellValueSet.doOneCellValue(formulaCellVal);
                }

                // 写入DisplayCondition
                {
                    String oldDisplayConditionPosInfo = posList.get("Display Condition");
                    int[] posRow = getPosRow(oldDisplayConditionPosInfo);
                    String[] posCol = getPosCol(oldDisplayConditionPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_0_0 = posCol[0] + String.valueOf(posRow[0] + startRowCount);
                    String newDisplayConditionPosInfo_1 = pos_0 + ":" + pos_0_0;

                    CellStyleInfo displayConditionCellStyle_1 = new CellStyleInfo(
                            String.format("%s", newDisplayConditionPosInfo_1),
                            "Meiryo UI", 11, false, isMergeRow,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(displayConditionCellStyle_1);

                    HashMap<String, String> displayCondition = displayRefInfo.getDisplayCondition();
                    Set<String> displayConditionKey = displayCondition.keySet();
                    if (displayConditionKey.size() != 0) {
                        int index = 0;
                        for (String key : displayConditionKey) {
                            String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                            SheetPosValue displayConditionCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue(key));
                            cellValueSet.doOneCellValue(displayConditionCellVal_1);

                            String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                            String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + index + (startRowCount - rowCount));
                            String newDisplayConditionPosInfo_2 = pos_0_new + ":" + pos_1;

                            CellStyleInfo displayConditionCellStyle_2 = new CellStyleInfo(
                                    String.format("%s", newDisplayConditionPosInfo_2),
                                    "Meiryo UI", 11, true, true,
                                    "left", "center", "thin", "thin", "thin",
                                    "thin", false, new int[]{255, 255, 255});
                            cellStyleSet.doOneCellStyle(displayConditionCellStyle_2);
                            SheetPosValue displayConditionCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), checkCellValue(displayCondition.get(key)));
                            cellValueSet.doOneCellValue(displayConditionCellVal_2);
                            // TODO
                            String[] displayConditionData = displayDropDownData.getDisplayConditionData();
                            setDropDownList(this.sheetInfo, newDisplayConditionPosInfo_2, displayConditionData);
                            index++;
                        }
                    } else {
                        String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        SheetPosValue displayConditionCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue("A"));
                        cellValueSet.doOneCellValue(displayConditionCellVal_1);

                        String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + (startRowCount - rowCount));
                        String newDisplayConditionPosInfo_2 = pos_0_new + ":" + pos_1;

                        CellStyleInfo displayConditionCellStyle_2 = new CellStyleInfo(
                                String.format("%s", newDisplayConditionPosInfo_2),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(displayConditionCellStyle_2);
                        SheetPosValue displayConditionCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), checkCellValue("-"));
                        cellValueSet.doOneCellValue(displayConditionCellVal_2);
                        // TODO
                        String[] displayConditionData = displayDropDownData.getDisplayConditionData();
                        setDropDownList(this.sheetInfo, newDisplayConditionPosInfo_2, displayConditionData);
                    }
                }

                // 写入Display in Such Condition(DisplayAction)
                {
                    String oldDisplayActionPosInfo = posList.get("Display in Such Condition");
                    int[] posRow = getPosRow(oldDisplayActionPosInfo);
                    String[] posCol = getPosCol(oldDisplayActionPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newDisplayActionPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo displayActionCellStyle = new CellStyleInfo(
                            String.format("%s", newDisplayActionPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(displayActionCellStyle);
                    SheetPosValue displayActionCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(displayRefInfo.getDispalyAction()));
                    cellValueSet.doOneCellValue(displayActionCellVal);
                    // TODO
                    String[] displayInSuchConditionData = displayDropDownData.getDisplayInSuchConditionData();
                    setDropDownList(this.sheetInfo, newDisplayActionPosInfo, displayInSuchConditionData);
                }

                // 写入Property Type
                {
                    String oldPropertyTypePosInfo = posList.get("Property Type");
                    int[] posRow = getPosRow(oldPropertyTypePosInfo);
                    String[] posCol = getPosCol(oldPropertyTypePosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newPropertyTypePosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo propertyTypeCellStyle = new CellStyleInfo(
                            String.format("%s", newPropertyTypePosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(propertyTypeCellStyle);
                    SheetPosValue propertyTypeCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(displayRefInfo.getDisplayPropertyType()));
                    cellValueSet.doOneCellValue(propertyTypeCellVal);
                    // TODO
                    String[] propertyTypeData = displayDropDownData.getPropertyTypeData();
                    setDropDownList(this.sheetInfo, newPropertyTypePosInfo, propertyTypeData);
                }

                // 写入Data Range
                {
                    String oldDataRangePosInfo = posList.get("Data Range");
                    int[] posRow = getPosRow(oldDataRangePosInfo);
                    String[] posCol = getPosCol(oldDataRangePosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newDataRangePosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo dataRangeCellStyle = new CellStyleInfo(
                            String.format("%s", newDataRangePosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(dataRangeCellStyle);
                    SheetPosValue dataRangeCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(displayRefInfo.getDataRange()));
                    cellValueSet.doOneCellValue(dataRangeCellVal);
                }

                // 写入Remark
                {
                    String oldRemarkPosInfo = posList.get("Remark");
                    int[] posRow = getPosRow(oldRemarkPosInfo);
                    String[] posCol = getPosCol(oldRemarkPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newRemarkPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo remarkCellStyle = new CellStyleInfo(
                            String.format("%s", newRemarkPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(remarkCellStyle);
                    SheetPosValue remarkCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(displayRefInfo.getDisplayRemark()));
                    cellValueSet.doOneCellValue(remarkCellVal);
                }

                // 写入UUID
                {
                    String oldDisplayUUIDPosInfo = posList.get("UUID");
                    int[] posRow = getPosRow(oldDisplayUUIDPosInfo);
                    String[] posCol = getPosCol(oldDisplayUUIDPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newDisplayUUIDPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo displayUUIDCellStyle = new CellStyleInfo(
                            String.format("%s", newDisplayUUIDPosInfo),
                            "Meiryo UI", 11, isMergeRow, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(displayUUIDCellStyle);
                    SheetPosValue displayUUIDCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(jsonSketchInfo.getSketchInfoId() + "->" + jsonPartInfo.getUuid()));
                    cellValueSet.doOneCellValue(displayUUIDCellVal);
                }

                // 写入Parts Type
                {
                    String oldPartsTypePosInfo = posList.get("Parts Type");
                    int[] posRow = getPosRow(oldPartsTypePosInfo);
                    String[] posCol = getPosCol(oldPartsTypePosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newPartsTypePosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo partsTypeCellStyle = new CellStyleInfo(
                            String.format("%s", newPartsTypePosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(partsTypeCellStyle);
                    SheetPosValue partsTypeCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(displayRefInfo.getDisplayPartsType()));
                    cellValueSet.doOneCellValue(partsTypeCellVal);
                }

                // 写入Property
                {
                    String oldPropertyPosInfo = posList.get("Property");
                    int[] posRow = getPosRow(oldPropertyPosInfo);
                    String[] posCol = getPosCol(oldPropertyPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newPropertyPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo propertyCellStyle = new CellStyleInfo(
                            String.format("%s", newPropertyPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(propertyCellStyle);
                    // TODO
                    int row = getPosRowSingle(pos_0);
                    String col = getPosColSingle(pos_0);
                    int iCol = excelColStrToNum(col, col.length());
                    String formulaString = displayDropDownData.getPropertyDataFormulaString(row);
                    setLinkage(row, iCol, formulaString);
                }

                // 写入Add Display Condition In View Model
                {
                    String oldAddDisplayConditionModelPosInfo = posList.get("Add Display Condition In View Model");
                    int[] posRow = getPosRow(oldAddDisplayConditionModelPosInfo);
                    String[] posCol = getPosCol(oldAddDisplayConditionModelPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newAddDisplayConditionModelPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo addDisplayConditionModelCellStyle = new CellStyleInfo(
                            String.format("%s", newAddDisplayConditionModelPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(addDisplayConditionModelCellStyle);
                    // TODO
                    int row = getPosRowSingle(pos_0);
                    String col = getPosColSingle(pos_0);
                    int iCol = excelColStrToNum(col, col.length());
                    String formulaString = displayDropDownData.getAddDisplayConditionInViewModelFormulaString(row);
                    setLinkage(row, iCol, formulaString);
                }

                // 写入Display Condition In View Model
                {
                    String oldDisplayConditionModelPosInfo = posList.get("Display Condition In View Model");
                    int[] posRow = getPosRow(oldDisplayConditionModelPosInfo);
                    String[] posCol = getPosCol(oldDisplayConditionModelPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_0_0 = posCol[0] + String.valueOf(posRow[0] + startRowCount);
                    String newDisplayConditionPosInfo_1 = pos_0 + ":" + pos_0_0;

                    CellStyleInfo displayConditionModelCellStyle_1 = new CellStyleInfo(
                            String.format("%s", newDisplayConditionPosInfo_1),
                            "Meiryo UI", 11, false, isMergeRow,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(displayConditionModelCellStyle_1);

                    HashMap<String, String> displayConditionModel = displayRefInfo.getDisplayConditionModel();
                    Set<String> displayConditionModelKey = displayConditionModel.keySet();
                    if (displayConditionModelKey.size() != 0) {
                        int index = 0;
                        for (String key : displayConditionModelKey) {
                            String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                            SheetPosValue displayConditionModelCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue(key));
                            cellValueSet.doOneCellValue(displayConditionModelCellVal_1);

                            String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                            String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + index + (startRowCount - rowCount));
                            String newDisplayConditionModelPosInfo_2 = pos_0_new + ":" + pos_1;

                            CellStyleInfo displayConditionModelCellStyle_2 = new CellStyleInfo(
                                    String.format("%s", newDisplayConditionModelPosInfo_2),
                                    "Meiryo UI", 11, true, true,
                                    "left", "center", "thin", "thin", "thin",
                                    "thin", false, new int[]{255, 255, 255});
                            cellStyleSet.doOneCellStyle(displayConditionModelCellStyle_2);
                            // TODO
                            int row = getPosRowSingle(pos_0_new);
                            String col = getPosColSingle(pos_0_new);
                            int iCol = excelColStrToNum(col, col.length());
                            String formulaString = displayDropDownData.getDisplayConditionInViewModelFormulaString(row);
                            setLinkage(row, iCol, formulaString);
                            index++;
                        }
                    } else {
                        String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        SheetPosValue displayConditionModelCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue("A"));
                        cellValueSet.doOneCellValue(displayConditionModelCellVal_1);

                        String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + (startRowCount - rowCount));
                        String newDisplayConditionModelPosInfo_2 = pos_0_new + ":" + pos_1;

                        CellStyleInfo displayConditionModelCellStyle_2 = new CellStyleInfo(
                                String.format("%s", newDisplayConditionModelPosInfo_2),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(displayConditionModelCellStyle_2);
                        // TODO
                        int row = getPosRowSingle(pos_0_new);
                        String col = getPosColSingle(pos_0_new);
                        int iCol = excelColStrToNum(col, col.length());
                        String formulaString = displayDropDownData.getDisplayConditionInViewModelFormulaString(row);
                        setLinkage(row, iCol, formulaString);
                    }
                }

                // 写入StringID
                {
                    String oldStringIDPosInfo = posList.get("String ID");
                    int[] posRow = getPosRow(oldStringIDPosInfo);
                    String[] posCol = getPosCol(oldStringIDPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newStringIDPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo stringIDCellStyle = new CellStyleInfo(
                            String.format("%s", newStringIDPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(stringIDCellStyle);
                    SheetPosValue stringIDCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(displayRefInfo.getStringID()));
                    cellValueSet.doOneCellValue(stringIDCellVal);
                }

                // 写入DefaultValue
                {
                    String oldDefaultValuePosInfo = posList.get("DefaultValue");
                    int[] posRow = getPosRow(oldDefaultValuePosInfo);
                    String[] posCol = getPosCol(oldDefaultValuePosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newDefaultValuePosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo defaultValueCellStyle = new CellStyleInfo(
                            String.format("%s", newDefaultValuePosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(defaultValueCellStyle);
                    SheetPosValue defaultValueCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(displayRefInfo.getDisplayDefaultValue()));
                    cellValueSet.doOneCellValue(defaultValueCellVal);
                }
            }
        }
        StartRowIndex += 3 + startRowCount + DefaultTableSpace + 16;

        // DisplayRefInfoStr
        {
            String displayRefInfoPosInfo1 = "B" + String.valueOf(StartRowIndex - 1) + ":" + "B" + String.valueOf(StartRowIndex - 1);
            CellStyleInfo displayRefInfoCellStyle = new CellStyleInfo(
                    String.format("%s", displayRefInfoPosInfo1),
                    "Meiryo UI", 11, false, true,
                    "left", "center", "", "thin", "thin",
                    "thin", false, new int[]{255, 255, 255});
            cellStyleSet.doOneCellStyle(displayRefInfoCellStyle);
            String displayRefInfoPosInfo2 = "C" + String.valueOf(StartRowIndex - 1);
            SheetPosValue displayRefInfoCellVal = new SheetPosValue(String.format("%s", displayRefInfoPosInfo2), checkCellValue(jsonSketchInfo.getDisplayRefInfoStr()));
            cellValueSet.doOneCellValue(displayRefInfoCellVal);
        }

        return StartRowIndex;
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
    private int doExportActiveRefInfo(JsonSketchInfo_new jsonSketchInfo, int StartRowIndex) throws Exception {
        // 表头设置
        HMIPartsSpecStatic_new staticInfo = new HMIPartsSpecStatic_new();

        CellStyleSet cellStyleSet = new CellStyleSet(this.workBook, this.sheetInfo);
        ArrayList<CellStyleInfo> styleList = getNewStyleList(staticInfo.styleLists.get("ActiveRefInfo"), StartRowIndex, 2);
        cellStyleSet.doCellStyleList(styleList);

        CellValueSet cellValueSet = new CellValueSet(this.workBook, this.sheetInfo);
        ArrayList<SheetPosValue> lableList = getNewLableList(staticInfo.lableLists.get("ActiveRefInfo"), StartRowIndex, 2);
        cellValueSet.doCellValueList(lableList);

        HashMap<String, String> posList = getPosList(lableList, styleList, 2);
        ArrayList<JsonPartInfo_new> jsonPartInfoList = sortByNo(jsonSketchInfo.getJsonPartInfoList(), 2);
        HMIDropDownData dropDownData = HMIDropDownData.getInstance();
        HMIDropDownData.ActiveDropDownData activeDropDownData = dropDownData.getActiveDropDownDataInstance();
        int startRowCount = 0;
        for (int k = 0; k < jsonPartInfoList.size(); ++k) {
            JsonPartInfo_new jsonPartInfo = jsonPartInfoList.get(k);
            JsonActiveRefInfo activeRefInfo = jsonPartInfo.getActiveRefInfo();
            if (checkActiveRefInfo(activeRefInfo)) {
                int rowCount = getActiveRefInfoRowCount(activeRefInfo);
                startRowCount += rowCount;
                boolean isMergeRow = isMergeRow(rowCount);

                // 写入Chapter
                {
                    String oldChapterPosInfo = posList.get("Chapter");
                    int[] posRow = getPosRow(oldChapterPosInfo);
                    String[] posCol = getPosCol(oldChapterPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newChapterPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo chapterCellStyle = new CellStyleInfo(
                            String.format("%s", newChapterPosInfo),
                            "Meiryo UI", 11, isMergeRow, true,
                            "center", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{201,255,255});
                    cellStyleSet.doOneCellStyle(chapterCellStyle);
                    SheetPosValue chapterCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(activeRefInfo.getActiveChapter()));
                    cellValueSet.doOneCellValue(chapterCellVal);
                }

                // 写入SubChapter
                {
                    String oldSubChapterPosInfo = posList.get("SubChapter");
                    int[] posRow = getPosRow(oldSubChapterPosInfo);
                    String[] posCol = getPosCol(oldSubChapterPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newSubChapterPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo subChapterCellStyle = new CellStyleInfo(
                            String.format("%s", newSubChapterPosInfo),
                            "Meiryo UI", 11, isMergeRow, true,
                            "center", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255,205,156});
                    cellStyleSet.doOneCellStyle(subChapterCellStyle);
                    SheetPosValue subChapterCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(activeRefInfo.getActiveSubChapter()));
                    cellValueSet.doOneCellValue(subChapterCellVal);
                }

                // 写入NO.
                {
                    String oldNOPosInfo = posList.get("NO.");
                    int[] posRow = getPosRow(oldNOPosInfo);
                    String[] posCol = getPosCol(oldNOPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newNOPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo noCellStyle = new CellStyleInfo(
                            String.format("%s", newNOPosInfo),
                            "Meiryo UI", 11, isMergeRow, true,
                            "center", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255,205,156});
                    cellStyleSet.doOneCellStyle(noCellStyle);
                    SheetPosValue noCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(activeRefInfo.getActiveNo()));
                    cellValueSet.doOneCellValue(noCellVal);
                }

                // 写入BtnName
                {
                    String oldBtnNamePosInfo = posList.get("Button Name");
                    int[] posRow = getPosRow(oldBtnNamePosInfo);
                    String[] posCol = getPosCol(oldBtnNamePosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_0_0 = posCol[0] + String.valueOf(posRow[0] + startRowCount);
                    String newBtnNamePosInfo_1 = pos_0 + ":" + pos_0_0;

                    CellStyleInfo activeBtnNameCellStyle_1 = new CellStyleInfo(
                            String.format("%s", newBtnNamePosInfo_1),
                            "Meiryo UI", 11, isMergeRow, true,
                            "center", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{172, 255, 196});
                    cellStyleSet.doOneCellStyle(activeBtnNameCellStyle_1);
                    SheetPosValue btnNameCellVal_1 = new SheetPosValue(String.format("%s", pos_0), checkCellValue(activeRefInfo.getActiveStateNo()));
                    cellValueSet.doOneCellValue(btnNameCellVal_1);

                    String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newBtnNamePosInfo_2 = pos_0_new + ":" + pos_1;

                    CellStyleInfo activeBtnNameCellStyle_2 = new CellStyleInfo(
                            String.format("%s", newBtnNamePosInfo_2),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(activeBtnNameCellStyle_2);

                    SheetPosValue btnNameCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), checkCellValue(activeRefInfo.getActiveBtnName()));
                    cellValueSet.doOneCellValue(btnNameCellVal_2);
                }

                // 写入Formula
                {
                    String oldFormulaPosInfo = posList.get("Formula");
                    int[] posRow = getPosRow(oldFormulaPosInfo);
                    String[] posCol = getPosCol(oldFormulaPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newFormulaPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo formulaCellStyle = new CellStyleInfo(
                            String.format("%s", newFormulaPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(formulaCellStyle);
                    SheetPosValue formulaCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(activeRefInfo.getActiveFormula()));
                    cellValueSet.doOneCellValue(formulaCellVal);
                }

                // 写入Active Condition
                {
                    String oldActiveConditionPosInfo = posList.get("Condition");
                    int[] posRow = getPosRow(oldActiveConditionPosInfo);
                    String[] posCol = getPosCol(oldActiveConditionPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_0_0 = posCol[0] + String.valueOf(posRow[0] + startRowCount);
                    String newActiveConditionPosInfo_1 = pos_0 + ":" + pos_0_0;

                    CellStyleInfo activeConditionCellStyle_1 = new CellStyleInfo(
                            String.format("%s", newActiveConditionPosInfo_1),
                            "Meiryo UI", 11, false, isMergeRow,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(activeConditionCellStyle_1);

                    HashMap<String, String> activeCondition = activeRefInfo.getActiveCondition();
                    Set<String> activeConditionKey = activeCondition.keySet();
                    if (activeConditionKey.size() != 0) {
                        int index = 0;
                        for (String key : activeConditionKey) {
                            String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                            SheetPosValue activeConditionCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue(key));
                            cellValueSet.doOneCellValue(activeConditionCellVal_1);

                            String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                            String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + index + (startRowCount - rowCount));
                            String newActiveConditionPosInfo_2 = pos_0_new + ":" + pos_1;

                            CellStyleInfo activeConditionCellStyle_2 = new CellStyleInfo(
                                    String.format("%s", newActiveConditionPosInfo_2),
                                    "Meiryo UI", 11, true, true,
                                    "left", "center", "thin", "thin", "thin",
                                    "thin", false, new int[]{255, 255, 255});
                            cellStyleSet.doOneCellStyle(activeConditionCellStyle_2);

                            SheetPosValue activeConditionCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), checkCellValue(activeCondition.get(key)));
                            cellValueSet.doOneCellValue(activeConditionCellVal_2);
                            // TODO
                            String[] conditionData = activeDropDownData.getConditionData();
                            setDropDownList(this.sheetInfo, newActiveConditionPosInfo_2, conditionData);
                            index++;
                        }
                    } else {
                        String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        SheetPosValue activeConditionCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue("A"));
                        cellValueSet.doOneCellValue(activeConditionCellVal_1);

                        String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + (startRowCount - rowCount));
                        String newActiveConditionPosInfo_2 = pos_0_new + ":" + pos_1;

                        CellStyleInfo activeConditionCellStyle_2 = new CellStyleInfo(
                                String.format("%s", newActiveConditionPosInfo_2),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(activeConditionCellStyle_2);

                        SheetPosValue activeConditionCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), "-");
                        cellValueSet.doOneCellValue(activeConditionCellVal_2);
                        // TODO
                        String[] conditionData = activeDropDownData.getConditionData();
                        setDropDownList(this.sheetInfo, newActiveConditionPosInfo_2, conditionData);
                    }
                }

                // 写入Display in Such Condition (ActiveAction)
                {
                    String oldActiveActionPosInfo = posList.get("Display in Such Condition");
                    int[] posRow = getPosRow(oldActiveActionPosInfo);
                    String[] posCol = getPosCol(oldActiveActionPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newActiveActionPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo activeActionCellStyle = new CellStyleInfo(
                            String.format("%s", newActiveActionPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(activeActionCellStyle);
                    SheetPosValue activeActionCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(activeRefInfo.getActiveAction()));
                    cellValueSet.doOneCellValue(activeActionCellVal);
                    // TODO
                    String[] displayInSuchConditionData = activeDropDownData.getDisplayInSuchConditionData();
                    setDropDownList(this.sheetInfo, newActiveActionPosInfo, displayInSuchConditionData);
                }

                // 写入Property Type
                {
                    String oldPropertyTypePosInfo = posList.get("Property Type");
                    int[] posRow = getPosRow(oldPropertyTypePosInfo);
                    String[] posCol = getPosCol(oldPropertyTypePosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newPropertyTypePosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo propertyTypeCellStyle = new CellStyleInfo(
                            String.format("%s", newPropertyTypePosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(propertyTypeCellStyle);
                    SheetPosValue propertyTypeCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(activeRefInfo.getActivePropertyType()));
                    cellValueSet.doOneCellValue(propertyTypeCellVal);
                    // TODO
                    String[] propertyTypeData = activeDropDownData.getPropertyTypeData();
                    setDropDownList(this.sheetInfo, newPropertyTypePosInfo, propertyTypeData);
                }

                // 写入DuringDriving
                {
                    String oldDuringDrivingPosInfo = posList.get("DuringDriving");
                    int[] posRow = getPosRow(oldDuringDrivingPosInfo);
                    String[] posCol = getPosCol(oldDuringDrivingPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newDuringDrivingPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo duringDrivingCellStyle = new CellStyleInfo(
                            String.format("%s", newDuringDrivingPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(duringDrivingCellStyle);
                    SheetPosValue duringDrivingCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(activeRefInfo.getActiveDuringDriving()));
                    cellValueSet.doOneCellValue(duringDrivingCellVal);
                    // TODO
                    String[] duringDriving = activeDropDownData.getDuringDriving();
                    setDropDownList(this.sheetInfo, newDuringDrivingPosInfo, duringDriving);
                }

                // 写入Remark
                {
                    String oldRemarkPosInfo = posList.get("Remark");
                    int[] posRow = getPosRow(oldRemarkPosInfo);
                    String[] posCol = getPosCol(oldRemarkPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newRemarkPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo remarkCellStyle = new CellStyleInfo(
                            String.format("%s", newRemarkPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(remarkCellStyle);
                    SheetPosValue remarkCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(activeRefInfo.getActiveRemark()));
                    cellValueSet.doOneCellValue(remarkCellVal);
                }

                // 写入UUID
                {
                    String oldActiveUUIDPosInfo = posList.get("UUID");
                    int[] posRow = getPosRow(oldActiveUUIDPosInfo);
                    String[] posCol = getPosCol(oldActiveUUIDPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newActiveUUIDPosInfo_1 = pos_0 + ":" + pos_1;

                    CellStyleInfo activeUUIDCellStyle = new CellStyleInfo(
                            String.format("%s", newActiveUUIDPosInfo_1),
                            "Meiryo UI", 11, isMergeRow, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(activeUUIDCellStyle);
                    SheetPosValue activeUUIDCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(jsonSketchInfo.getSketchInfoId() + "->" + jsonPartInfo.getUuid()));
                    cellValueSet.doOneCellValue(activeUUIDCellVal);
                }

                // 写入Parts Type
                {
                    String oldPartsTypePosInfo = posList.get("Parts Type");
                    int[] posRow = getPosRow(oldPartsTypePosInfo);
                    String[] posCol = getPosCol(oldPartsTypePosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newPartsTypePosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo partsTypeCellStyle = new CellStyleInfo(
                            String.format("%s", newPartsTypePosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(partsTypeCellStyle);
                    SheetPosValue partsTypeCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(activeRefInfo.getActivePartsType()));
                    cellValueSet.doOneCellValue(partsTypeCellVal);
                }

                // 写入Property
                {
                    String oldPropertyPosInfo = posList.get("Property");
                    int[] posRow = getPosRow(oldPropertyPosInfo);
                    String[] posCol = getPosCol(oldPropertyPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newPropertyPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo propertyCellStyle = new CellStyleInfo(
                            String.format("%s", newPropertyPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(propertyCellStyle);
                    // TODO
                    int row = getPosRowSingle(pos_0);
                    String col = getPosColSingle(pos_0);
                    int iCol = excelColStrToNum(col, col.length());
                    String formulaString = activeDropDownData.getPropertyDataFormulaString(row);
                    setLinkage(row, iCol, formulaString);
                }

                // 写入Add Display Condition In View Model
                {
                    String oldAddDisplayConditionModelPosInfo = posList.get("Add Display Condition In View Model");
                    int[] posRow = getPosRow(oldAddDisplayConditionModelPosInfo);
                    String[] posCol = getPosCol(oldAddDisplayConditionModelPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newAddDisplayConditionModelPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo addDisplayConditionModelCellStyle = new CellStyleInfo(
                            String.format("%s", newAddDisplayConditionModelPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(addDisplayConditionModelCellStyle);
                    SheetPosValue addDisplayConditionModelCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(activeRefInfo.getActiveActionModel()));
                    cellValueSet.doOneCellValue(addDisplayConditionModelCellVal);
                    // TODO
                    int row = getPosRowSingle(pos_0);
                    String col = getPosColSingle(pos_0);
                    int iCol = excelColStrToNum(col, col.length());
                    String formulaString = activeDropDownData.getAddDisplayConditionInViewModelFormulaString(row);
                    setLinkage(row, iCol, formulaString);
                }

                // 写入Display Condition In View Model
                {
                    String oldActiveConditionModelPosInfo = posList.get("Display Condition In View Model");
                    int[] posRow = getPosRow(oldActiveConditionModelPosInfo);
                    String[] posCol = getPosCol(oldActiveConditionModelPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_0_0 = posCol[0] + String.valueOf(posRow[0] + startRowCount);
                    String newActiveConditionModelPosInfo_1 = pos_0 + ":" + pos_0_0;

                    CellStyleInfo activeConditionModelCellStyle_1 = new CellStyleInfo(
                            String.format("%s", newActiveConditionModelPosInfo_1),
                            "Meiryo UI", 11, false, isMergeRow,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(activeConditionModelCellStyle_1);

                    HashMap<String, String> activeConditionModel = activeRefInfo.getActiveConditionModel();
                    Set<String> activeConditionModelKey = activeConditionModel.keySet();
                    if (activeConditionModelKey.size() != 0) {
                        int index = 0;
                        for (String key : activeConditionModelKey) {
                            String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                            SheetPosValue activeConditionModelCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue(key));
                            cellValueSet.doOneCellValue(activeConditionModelCellVal_1);

                            String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                            String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + index + (startRowCount - rowCount));
                            String newActiveConditionModelPosInfo_2 = pos_0_new + ":" + pos_1;

                            CellStyleInfo activeConditionModelCellStyle_2 = new CellStyleInfo(
                                    String.format("%s", newActiveConditionModelPosInfo_2),
                                    "Meiryo UI", 11, true, true,
                                    "left", "center", "thin", "thin", "thin",
                                    "thin", false, new int[]{255, 255, 255});
                            cellStyleSet.doOneCellStyle(activeConditionModelCellStyle_2);

                            SheetPosValue activeConditionModelCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), checkCellValue(activeConditionModel.get(key)));
                            cellValueSet.doOneCellValue(activeConditionModelCellVal_2);
                            // TODO
                            int row = getPosRowSingle(pos_0_new);
                            String col = getPosColSingle(pos_0_new);
                            int iCol = excelColStrToNum(col, col.length());
                            String formulaString = activeDropDownData.getDisplayConditionInViewModelFormulaString(row);
                            setLinkage(row, iCol, formulaString);
                            index++;
                        }
                    }
                    else {
                        String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        SheetPosValue activeConditionModelCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue("A"));
                        cellValueSet.doOneCellValue(activeConditionModelCellVal_1);

                        String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + (startRowCount - rowCount));
                        String newActiveConditionModelPosInfo_2 = pos_0_new + ":" + pos_1;

                        CellStyleInfo activeConditionModelCellStyle_2 = new CellStyleInfo(
                                String.format("%s", newActiveConditionModelPosInfo_2),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(activeConditionModelCellStyle_2);

                        SheetPosValue activeConditionModelCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), "-");
                        cellValueSet.doOneCellValue(activeConditionModelCellVal_2);
                        // TODO
                        int row = getPosRowSingle(pos_0_new);
                        String col = getPosColSingle(pos_0_new);
                        int iCol = excelColStrToNum(col, col.length());
                        String formulaString = activeDropDownData.getDisplayConditionInViewModelFormulaString(row);
                        setLinkage(row, iCol, formulaString);
                    }
                }

                // 写入空白 (Blank_0)
                {
                    String oldBlank_0PosInfo = posList.get("Blank_0");
                    int[] posRow = getPosRow(oldBlank_0PosInfo);
                    String[] posCol = getPosCol(oldBlank_0PosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newBlank_0PosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo blank_0CellStyle = new CellStyleInfo(
                            String.format("%s", newBlank_0PosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(blank_0CellStyle);
                    SheetPosValue blank_0CellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue("-"));
                    cellValueSet.doOneCellValue(blank_0CellVal);
                }

                // 写入DefaultValue
                {
                    String oldDefaultValuePosInfo = posList.get("DefaultValue");
                    int[] posRow = getPosRow(oldDefaultValuePosInfo);
                    String[] posCol = getPosCol(oldDefaultValuePosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newDefaultValuePosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo defaultValueCellStyle = new CellStyleInfo(
                            String.format("%s", newDefaultValuePosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(defaultValueCellStyle);
                    SheetPosValue defaultValueCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(activeRefInfo.getActiveDefaultValue()));
                    cellValueSet.doOneCellValue(defaultValueCellVal);
                }
            }
        }

        StartRowIndex += 3 + startRowCount + DefaultTableSpace;
        // ActiveRefInfoStr
        {
            String activeRefInfoPosInfo1 = "B" + String.valueOf(StartRowIndex - 1) + ":" + "B" + String.valueOf(StartRowIndex - 1);
            CellStyleInfo activeRefInfoCellStyle = new CellStyleInfo(
                    String.format("%s", activeRefInfoPosInfo1),
                    "Meiryo UI", 11, false, true,
                    "left", "center", "", "thin", "thin",
                    "thin", false, new int[]{255, 255, 255});
            cellStyleSet.doOneCellStyle(activeRefInfoCellStyle);
            String activeRefInfoPosInfo2 = "C" + String.valueOf(StartRowIndex - 1);
            SheetPosValue activeRefInfoCellVal = new SheetPosValue(String.format("%s", activeRefInfoPosInfo2), checkCellValue(jsonSketchInfo.getActiveRefInfoStr()));
            cellValueSet.doOneCellValue(activeRefInfoCellVal);
        }
        return StartRowIndex;
    }

    private int doExportActionRefInfo(JsonSketchInfo_new jsonSketchInfo, int StartRowIndex) throws Exception {
        // 表头设置
        HMIPartsSpecStatic_new staticInfo = new HMIPartsSpecStatic_new();

        CellStyleSet cellStyleSet = new CellStyleSet(this.workBook, this.sheetInfo);
        ArrayList<CellStyleInfo> styleList = getNewStyleList(staticInfo.styleLists.get("ActionRefInfo"), StartRowIndex, 3);
        cellStyleSet.doCellStyleList(styleList);

        CellValueSet cellValueSet = new CellValueSet(this.workBook, this.sheetInfo);
        ArrayList<SheetPosValue> lableList = getNewLableList(staticInfo.lableLists.get("ActionRefInfo"), StartRowIndex, 3);
        cellValueSet.doCellValueList(lableList);

        HashMap<String, String> posList = getPosList(lableList, styleList, 3);
        ArrayList<JsonPartInfo_new> jsonPartInfoList = sortByNo(jsonSketchInfo.getJsonPartInfoList(), 3);
        HMIDropDownData dropDownData = HMIDropDownData.getInstance();
        HMIDropDownData.ActionDropDownData actionDropDownData = dropDownData.getActionDropDownDataInstance();
        int startRowCount = 0;
        for (int k = 0; k < jsonPartInfoList.size(); ++k) {
            // 3
            JsonPartInfo_new jsonPartInfo = jsonPartInfoList.get(k);
            JsonActionRefInfo actionRefInfo = jsonPartInfo.getActionRefInfo();
            if (checkActionRefInfo(actionRefInfo)) {
                int rowCount = getActionRefInfoRowCount(actionRefInfo);
                startRowCount += rowCount;
                boolean isMergeRow = isMergeRow(rowCount);

                // 写入Chapter
                {
                    String oldChapterPosInfo = posList.get("Chapter");
                    int[] posRow = getPosRow(oldChapterPosInfo);
                    String[] posCol = getPosCol(oldChapterPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newChapterPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo chapterCellStyle = new CellStyleInfo(
                            String.format("%s", newChapterPosInfo),
                            "Meiryo UI", 11, isMergeRow, true,
                            "center", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{201,255,255});
                    cellStyleSet.doOneCellStyle(chapterCellStyle);
                    SheetPosValue chapterCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(actionRefInfo.getActionChapter()));
                    cellValueSet.doOneCellValue(chapterCellVal);
                }

                // 写入SubChapter
                {
                    String oldSubChapterPosInfo = posList.get("SubChapter");
                    int[] posRow = getPosRow(oldSubChapterPosInfo);
                    String[] posCol = getPosCol(oldSubChapterPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newSubChapterPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo subChapterCellStyle = new CellStyleInfo(
                            String.format("%s", newSubChapterPosInfo),
                            "Meiryo UI", 11, isMergeRow, true,
                            "center", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255,205,156});
                    cellStyleSet.doOneCellStyle(subChapterCellStyle);
                    SheetPosValue subChapterCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(actionRefInfo.getActionSubChapter()));
                    cellValueSet.doOneCellValue(subChapterCellVal);
                }

                // 写入NO.
                {
                    String oldNOPosInfo = posList.get("NO.");
                    int[] posRow = getPosRow(oldNOPosInfo);
                    String[] posCol = getPosCol(oldNOPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newNOPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo noCellStyle = new CellStyleInfo(
                            String.format("%s", newNOPosInfo),
                            "Meiryo UI", 11, isMergeRow, true,
                            "center", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255,205,156});
                    cellStyleSet.doOneCellStyle(noCellStyle);
                    SheetPosValue noCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(actionRefInfo.getActionNo()));
                    cellValueSet.doOneCellValue(noCellVal);
                }

                //写入Btn Name
                {
                    String oldBtnNamePosInfo = posList.get("Button Name");
                    int[] posRow = getPosRow(oldBtnNamePosInfo);
                    String[] posCol = getPosCol(oldBtnNamePosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_0_0 = posCol[0] + String.valueOf(posRow[0] + startRowCount);
                    String newBtnNamePosInfo_1 = pos_0 + ":" + pos_0_0;

                    CellStyleInfo actionBtnNameCellStyle_1 = new CellStyleInfo(
                            String.format("%s", newBtnNamePosInfo_1),
                            "Meiryo UI", 11, isMergeRow, true,
                            "center", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{172, 255, 196});
                    cellStyleSet.doOneCellStyle(actionBtnNameCellStyle_1);
                    SheetPosValue stateNoCellVal_1 = new SheetPosValue(String.format("%s", pos_0), checkCellValue(actionRefInfo.getActionStateNo()));
                    cellValueSet.doOneCellValue(stateNoCellVal_1);

                    String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newBtnNamePosInfo_2 = pos_0_new + ":" + pos_1;

                    CellStyleInfo actionBtnNameCellStyle_2 = new CellStyleInfo(
                            String.format("%s", newBtnNamePosInfo_2),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(actionBtnNameCellStyle_2);

                    SheetPosValue BtnName_2 = new SheetPosValue(String.format("%s", pos_0_new), checkCellValue(actionRefInfo.getActionBtnName()));
                    cellValueSet.doOneCellValue(BtnName_2);

                }

                // 写入Ope Type
                {
                    String oldOpeTypePosInfo = posList.get("Ope Type");
                    int[] posRow = getPosRow(oldOpeTypePosInfo);
                    String[] posCol = getPosCol(oldOpeTypePosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newOpeTypePosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo opeTypeCellStyle = new CellStyleInfo(
                            String.format("%s", newOpeTypePosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(opeTypeCellStyle);
                    SheetPosValue opeTypeCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(actionRefInfo.getActionOpeType()));
                    cellValueSet.doOneCellValue(opeTypeCellVal);
                    // TODO
                    String[] opeTypeData = actionDropDownData.getOpeTypeData();
                    setDropDownList(this.sheetInfo, newOpeTypePosInfo, opeTypeData);
                }

                // 写入Formula
                {
                    String oldFormulaPosInfo = posList.get("Formula");
                    int[] posRow = getPosRow(oldFormulaPosInfo);
                    String[] posCol = getPosCol(oldFormulaPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newFormulaPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo formulaCellStyle = new CellStyleInfo(
                            String.format("%s", newFormulaPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(formulaCellStyle);
                    SheetPosValue formulaCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(actionRefInfo.getActionFormula()));
                    cellValueSet.doOneCellValue(formulaCellVal);
                }

                // 写入Condition of Action
                {
                    String oldActionConditionPosInfo = posList.get("Condition of Action");
                    int[] posRow = getPosRow(oldActionConditionPosInfo);
                    String[] posCol = getPosCol(oldActionConditionPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_0_0 = posCol[0] + String.valueOf(posRow[0] + startRowCount);
                    String newActionConditionPosInfo_1 = pos_0 + ":" + pos_0_0;

                    CellStyleInfo actionConditionCellStyle_1 = new CellStyleInfo(
                            String.format("%s", newActionConditionPosInfo_1),
                            "Meiryo UI", 11, false, isMergeRow,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(actionConditionCellStyle_1);

                    HashMap<String, String> actionCondition = actionRefInfo.getActionCondition();
                    Set<String> actionConditionKey = actionCondition.keySet();
                    if (actionConditionKey.size() != 0) {
                        int index = 0;
                        for (String key : actionConditionKey) {
                            String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                            SheetPosValue actionConditionCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue(key));
                            cellValueSet.doOneCellValue(actionConditionCellVal_1);

                            String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                            String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + index + (startRowCount - rowCount));
                            String newActionConditionPosInfo_2 = pos_0_new + ":" + pos_1;

                            CellStyleInfo actionConditionCellStyle_2 = new CellStyleInfo(
                                    String.format("%s", newActionConditionPosInfo_2),
                                    "Meiryo UI", 11, true, true,
                                    "left", "center", "thin", "thin", "thin",
                                    "thin", false, new int[]{255, 255, 255});
                            cellStyleSet.doOneCellStyle(actionConditionCellStyle_2);

                            SheetPosValue actionConditionCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), checkCellValue(actionCondition.get(key)));
                            cellValueSet.doOneCellValue(actionConditionCellVal_2);
                            // TODO
                            String[] conditionOfActionData = actionDropDownData.getConditionOfActionData();
                            setDropDownList(this.sheetInfo, newActionConditionPosInfo_2, conditionOfActionData);
                            index++;
                        }
                    } else {
                        String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        SheetPosValue actionConditionCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue("A"));
                        cellValueSet.doOneCellValue(actionConditionCellVal_1);

                        String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + (startRowCount - rowCount));
                        String newActionConditionPosInfo_2 = pos_0_new + ":" + pos_1;

                        CellStyleInfo actionConditionCellStyle_2 = new CellStyleInfo(
                                String.format("%s", newActionConditionPosInfo_2),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(actionConditionCellStyle_2);

                        SheetPosValue actionConditionCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), "-");
                        cellValueSet.doOneCellValue(actionConditionCellVal_2);
                        // TODO
                        String[] conditionOfActionData = actionDropDownData.getConditionOfActionData();
                        setDropDownList(this.sheetInfo, newActionConditionPosInfo_2, conditionOfActionData);
                    }
                }

                // 写入Action in Such Condition (ActionAction)
                {
                    String oldActionActionPosInfo = posList.get("Action in Such Condition");
                    int[] posRow = getPosRow(oldActionActionPosInfo);
                    String[] posCol = getPosCol(oldActionActionPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newActionActionPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo actionActionCellStyle = new CellStyleInfo(
                            String.format("%s", newActionActionPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(actionActionCellStyle);
                    SheetPosValue actionActionCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(actionRefInfo.getActionAction()));
                    cellValueSet.doOneCellValue(actionActionCellVal);
                    // TODO
                    String[] actionInSuchConditionData = actionDropDownData.getActionInSuchConditionData();
                    setDropDownList(this.sheetInfo, newActionActionPosInfo, actionInSuchConditionData);
                }

                // 写入Transition
                {
                    String oldTransitionPosInfo = posList.get("Transition");
                    int[] posRow = getPosRow(oldTransitionPosInfo);
                    String[] posCol = getPosCol(oldTransitionPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newTransitionPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo transitionCellStyle = new CellStyleInfo(
                            String.format("%s", newTransitionPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(transitionCellStyle);
                    SheetPosValue transitionCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(actionRefInfo.getActionTrans()));
                    cellValueSet.doOneCellValue(transitionCellVal);
                }

                // 写入Sound
                {
                    String oldSoundPosInfo = posList.get("Sound");
                    int[] posRow = getPosRow(oldSoundPosInfo);
                    String[] posCol = getPosCol(oldSoundPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newSoundPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo soundCellStyle = new CellStyleInfo(
                            String.format("%s", newSoundPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(soundCellStyle);
                    SheetPosValue soundCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(actionRefInfo.getActionSound()));
                    cellValueSet.doOneCellValue(soundCellVal);
                    // TODO
                    String[] soundData = actionDropDownData.getSoundData();
                    setDropDownList(this.sheetInfo, newSoundPosInfo, soundData);
                }

                // 写入空白 (Blank_0)
                {
                    String oldBlank_0PosInfo = posList.get("Blank_0");
                    int[] posRow = getPosRow(oldBlank_0PosInfo);
                    String[] posCol = getPosCol(oldBlank_0PosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newBlank_0PosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo blank_0CellStyle = new CellStyleInfo(
                            String.format("%s", newBlank_0PosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(blank_0CellStyle);
                    SheetPosValue blank_0CellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue("-"));
                    cellValueSet.doOneCellValue(blank_0CellVal);
                }

                // 写入Remark
                {
                    String oldRemarkPosInfo = posList.get("Remark");
                    int[] posRow = getPosRow(oldRemarkPosInfo);
                    String[] posCol = getPosCol(oldRemarkPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newRemarkPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo remarkCellStyle = new CellStyleInfo(
                            String.format("%s", newRemarkPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(remarkCellStyle);
                    SheetPosValue remarkCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(actionRefInfo.getActionRemark()));
                    cellValueSet.doOneCellValue(remarkCellVal);
                }

                // 写入UUID
                {
                    String oldActionUUIDPosInfo = posList.get("UUID");
                    int[] posRow = getPosRow(oldActionUUIDPosInfo);
                    String[] posCol = getPosCol(oldActionUUIDPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newActionUUIDPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo actionUUIDCellStyle = new CellStyleInfo(
                            String.format("%s", newActionUUIDPosInfo),
                            "Meiryo UI", 11, isMergeRow, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(actionUUIDCellStyle);
                    SheetPosValue actionUUIDCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(jsonSketchInfo.getSketchInfoId() + "->" + jsonPartInfo.getUuid()));
                    cellValueSet.doOneCellValue(actionUUIDCellVal);
                }

                // 写入Parts Type
                {
                    String oldPartsTypePosInfo = posList.get("Parts Type");
                    int[] posRow = getPosRow(oldPartsTypePosInfo);
                    String[] posCol = getPosCol(oldPartsTypePosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newPartsTypePosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo partsTypeCellStyle = new CellStyleInfo(
                            String.format("%s", newPartsTypePosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(partsTypeCellStyle);
                    SheetPosValue partsTypeCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(actionRefInfo.getActionPartsType()));
                    cellValueSet.doOneCellValue(partsTypeCellVal);
                }

                // 写入Event
                {
                    String oldEventPosInfo = posList.get("Event");
                    int[] posRow = getPosRow(oldEventPosInfo);
                    String[] posCol = getPosCol(oldEventPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newEventPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo eventCellStyle = new CellStyleInfo(
                            String.format("%s", newEventPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(eventCellStyle);
                    // TODO
                    int row = getPosRowSingle(pos_0);
                    String col = getPosColSingle(pos_0);
                    int iCol = excelColStrToNum(col, col.length());
                    String formulaString = actionDropDownData.getEventDataFormulaString(row);
                    setLinkage(row, iCol, formulaString);
                }

                // 写入View Model
                {
                    String oldActionConditionModelPosInfo = posList.get("View Model");
                    int[] posRow = getPosRow(oldActionConditionModelPosInfo);
                    String[] posCol = getPosCol(oldActionConditionModelPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_0_0 = posCol[0] + String.valueOf(posRow[0] + startRowCount);
                    String newActionConditionModelPosInfo_1 = pos_0 + ":" + pos_0_0;

                    CellStyleInfo actionConditionModelCellStyle_1 = new CellStyleInfo(
                            String.format("%s", newActionConditionModelPosInfo_1),
                            "Meiryo UI", 11, false, isMergeRow,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(actionConditionModelCellStyle_1);

                    HashMap<String, String> actionConditionModel = actionRefInfo.getActionConditionModel();
                    Set<String> actionConditionModelKey = actionConditionModel.keySet();
                    if (actionConditionModelKey.size() != 0) {
                        int index = 0;
                        for (String key : actionConditionModelKey) {
                            String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                            SheetPosValue actionConditionModelCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue(key));
                            cellValueSet.doOneCellValue(actionConditionModelCellVal_1);

                            String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                            String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + index + (startRowCount - rowCount));
                            String newActionConditionModelPosInfo_2 = pos_0_new + ":" + pos_1;

                            CellStyleInfo actionConditionModelCellStyle_2 = new CellStyleInfo(
                                    String.format("%s", newActionConditionModelPosInfo_2),
                                    "Meiryo UI", 11, true, true,
                                    "left", "center", "thin", "thin", "thin",
                                    "thin", false, new int[]{255, 255, 255});
                            cellStyleSet.doOneCellStyle(actionConditionModelCellStyle_2);

                            SheetPosValue actionConditionModelCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), checkCellValue(actionConditionModel.get(key)));
                            cellValueSet.doOneCellValue(actionConditionModelCellVal_2);
                            // TODO
                            int row = getPosRowSingle(pos_0_new);
                            String col = getPosColSingle(pos_0_new);
                            int iCol = excelColStrToNum(col, col.length());
                            String formulaString = actionDropDownData.getViewModelDataFormulaString(row);
                            setLinkage(row, iCol, formulaString);
                            index++;
                        }
                    } else {
                        String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        SheetPosValue actionConditionModelCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue("A"));
                        cellValueSet.doOneCellValue(actionConditionModelCellVal_1);

                        String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + (startRowCount - rowCount));
                        String newActionConditionModelPosInfo_2 = pos_0_new + ":" + pos_1;

                        CellStyleInfo actionConditionModelCellStyle_2 = new CellStyleInfo(
                                String.format("%s", newActionConditionModelPosInfo_2),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(actionConditionModelCellStyle_2);

                        SheetPosValue actionConditionModelCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), "-");
                        cellValueSet.doOneCellValue(actionConditionModelCellVal_2);
                        // TODO
                        int row = getPosRowSingle(pos_0_new);
                        String col = getPosColSingle(pos_0_new);
                        int iCol = excelColStrToNum(col, col.length());
                        String formulaString = actionDropDownData.getViewModelDataFormulaString(row);
                        setLinkage(row, iCol, formulaString);
                    }
                }

                // 写入Func of Model
                {
                    String oldFuncModelPosInfo = posList.get("Func of Model");
                    int[] posRow = getPosRow(oldFuncModelPosInfo);
                    String[] posCol = getPosCol(oldFuncModelPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newFuncModelPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo funcModelCellStyle = new CellStyleInfo(
                            String.format("%s", newFuncModelPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(funcModelCellStyle);
                    SheetPosValue funcModelCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(actionRefInfo.getActionActionModel()));
                    cellValueSet.doOneCellValue(funcModelCellVal);
                    // TODO
                    int row = getPosRowSingle(pos_0);
                    String col = getPosColSingle(pos_0);
                    int iCol = excelColStrToNum(col, col.length());
                    String formulaString = actionDropDownData.getFuncOfModelDataFormulaString(row);
                    setLinkage(row, iCol, formulaString);
                }

                // 写入Observer
                {
                    String oldObserverPosInfo = posList.get("Observer");
                    int[] posRow = getPosRow(oldObserverPosInfo);
                    String[] posCol = getPosCol(oldObserverPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newObserverPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo observerCellStyle = new CellStyleInfo(
                            String.format("%s", newObserverPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(observerCellStyle);
                    SheetPosValue observerCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(actionRefInfo.getActionObserver()));
                    cellValueSet.doOneCellValue(observerCellVal);
                }

                // 写入Reply
                {
                    String oldReplyPosInfo = posList.get("Reply");
                    int[] posRow = getPosRow(oldReplyPosInfo);
                    String[] posCol = getPosCol(oldReplyPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newReplyPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo replyCellStyle = new CellStyleInfo(
                            String.format("%s", newReplyPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(replyCellStyle);
                    SheetPosValue replyCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(actionRefInfo.getActionReply()));
                    cellValueSet.doOneCellValue(replyCellVal);
                }

                // 写入TransType
                {
                    String oldTransTypePosInfo = posList.get("TransType");
                    int[] posRow = getPosRow(oldTransTypePosInfo);
                    String[] posCol = getPosCol(oldTransTypePosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newTransTypePosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo transTypeCellStyle = new CellStyleInfo(
                            String.format("%s", newTransTypePosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(transTypeCellStyle);
                    SheetPosValue transTypeCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(actionRefInfo.getActionTransType()));
                    cellValueSet.doOneCellValue(transTypeCellVal);
                }

                // 写入TransID
                {
                    String oldTransIDPosInfo = posList.get("TransID");
                    int[] posRow = getPosRow(oldTransIDPosInfo);
                    String[] posCol = getPosCol(oldTransIDPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newTransIDPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo transIDCellStyle = new CellStyleInfo(
                            String.format("%s", newTransIDPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(transIDCellStyle);
                    SheetPosValue transIDCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(actionRefInfo.getActionTransID()));
                    cellValueSet.doOneCellValue(transIDCellVal);
                }
            }
        }

        StartRowIndex += 3 + startRowCount + DefaultTableSpace;
        // ActionRefInfoStr
        {
            String actionRefInfoPosInfo1 = "B" + String.valueOf(StartRowIndex - 1) + ":" + "B" + String.valueOf(StartRowIndex - 1);
            CellStyleInfo actionRefInfoCellStyle = new CellStyleInfo(
                    String.format("%s", actionRefInfoPosInfo1),
                    "Meiryo UI", 11, false, true,
                    "left", "center", "", "thin", "thin",
                    "thin", false, new int[]{255, 255, 255});
            cellStyleSet.doOneCellStyle(actionRefInfoCellStyle);
            String actionRefInfoPosInfo2 = "C" + String.valueOf(StartRowIndex - 1);
            SheetPosValue actionRefInfoCellVal = new SheetPosValue(String.format("%s", actionRefInfoPosInfo2), checkCellValue(jsonSketchInfo.getActionRefInfoStr()));
            cellValueSet.doOneCellValue(actionRefInfoCellVal);
        }

        return StartRowIndex;
    }

    private int doExportHKRefInfo(JsonSketchInfo_new jsonSketchInfo, int StartRowIndex) throws Exception {
        // 表头设置
        HMIPartsSpecStatic_new staticInfo = new HMIPartsSpecStatic_new();

        CellStyleSet cellStyleSet = new CellStyleSet(this.workBook, this.sheetInfo);
        ArrayList<CellStyleInfo> styleList = getNewStyleList(staticInfo.styleLists.get("HKRefInfo"), StartRowIndex, 4);
        cellStyleSet.doCellStyleList(styleList);

        CellValueSet cellValueSet = new CellValueSet(this.workBook, this.sheetInfo);
        ArrayList<SheetPosValue> lableList = getNewLableList(staticInfo.lableLists.get("HKRefInfo"), StartRowIndex, 4);
        cellValueSet.doCellValueList(lableList);

        HashMap<String, String> posList = getPosList(lableList, styleList, 4);
        HMIDropDownData dropDownData = HMIDropDownData.getInstance();
        HMIDropDownData.HKDropDownData hkDropDownData = dropDownData.getHkDropDownDataInstance();
        int startRowCount = 0;
        ArrayList<String> hkDevNameList = sortDevName(jsonSketchInfo.getHkDevNameList());
        ArrayList<String> hkDevTypeList = sortDevType(jsonSketchInfo.getHkDevTypeList());
        ArrayList<JsonPartInfo_new> jsonPartInfoList = sortByNo(jsonSketchInfo.getJsonPartInfoList(), 4);
        for (int devNameIndex = 0; devNameIndex < hkDevNameList.size(); ++devNameIndex) {
            // TODO do DevName
            startRowCount += 1;
            // 写入Chapter
            {
                String oldChapterPosInfo = posList.get("Chapter");
                int[] posRow = getPosRow(oldChapterPosInfo);
                String[] posCol = getPosCol(oldChapterPosInfo);
                String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - 1));
                String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                String newChapterPosInfo = pos_0 + ":" + pos_1;

                CellStyleInfo chapterCellStyle = new CellStyleInfo(
                        String.format("%s", newChapterPosInfo),
                        "Meiryo UI", 11, false, true,
                        "center", "center", "thin", "thin", "thin",
                        "thin", false, new int[]{201,255,255});
                cellStyleSet.doOneCellStyle(chapterCellStyle);
                SheetPosValue chapterCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue("4"));
                cellValueSet.doOneCellValue(chapterCellVal);
            }

            // 写入SubChapter
            {
                String oldSubChapterPosInfo = posList.get("SubChapter");
                int[] posRow = getPosRow(oldSubChapterPosInfo);
                String[] posCol = getPosCol(oldSubChapterPosInfo);
                String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - 1));
                String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                String newSubChapterPosInfo = pos_0 + ":" + pos_1;

                CellStyleInfo subChapterCellStyle = new CellStyleInfo(
                        String.format("%s", newSubChapterPosInfo),
                        "Meiryo UI", 11, false, true,
                        "center", "center", "thin", "thin", "thin",
                        "thin", false, new int[]{255,205,156});
                cellStyleSet.doOneCellStyle(subChapterCellStyle);
                SheetPosValue subChapterCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue("1"));
                cellValueSet.doOneCellValue(subChapterCellVal);
            }

            // 写入HKDevName
            {
                String oldHKDevNamePosInfo = posList.get("Normal");
                int[] posRow = getPosRow(oldHKDevNamePosInfo);
                String[] posCol = getPosCol(oldHKDevNamePosInfo);
                String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - 1));
                String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                String newHKDevNamePosInfo = pos_0 + ":" + pos_1;

                CellStyleInfo hkDevNameCellStyle = new CellStyleInfo(
                        String.format("%s", newHKDevNamePosInfo),
                        "Meiryo UI", 11, true, true,
                        "left", "center", "thin", "thin", "thin",
                        "thin", false, new int[]{255,255,153});
                cellStyleSet.doOneCellStyle(hkDevNameCellStyle);
                SheetPosValue hkDevNameCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue("＜" + hkDevNameList.get(devNameIndex) + "＞"));
                cellValueSet.doOneCellValue(hkDevNameCellVal);
            }

            // 写入HKDevType
            {
                String oldHKDevTypePosInfo = posList.get("UUID");
                int[] posRow = getPosRow(oldHKDevTypePosInfo);
                String[] posCol = getPosCol(oldHKDevTypePosInfo);
                String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - 1));
                String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                String newHKDevTypePosInfo = pos_0 + ":" + pos_1;

                CellStyleInfo hkDevTypeCellStyle = new CellStyleInfo(
                        String.format("%s", newHKDevTypePosInfo),
                        "Meiryo UI", 11, false, true,
                        "left", "center", "thin", "thin", "thin",
                        "thin", false, new int[]{255,255,153});
                cellStyleSet.doOneCellStyle(hkDevTypeCellStyle);
                if (devNameIndex >= hkDevTypeList.size()) {
                    SheetPosValue hkDevTypeCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue("-"));
                    cellValueSet.doOneCellValue(hkDevTypeCellVal);
                } else {
                    SheetPosValue hkDevTypeCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(hkDevTypeList.get(devNameIndex)));
                    cellValueSet.doOneCellValue(hkDevTypeCellVal);
                }


            }

            // 写入空白
            {
                ArrayList<String> oldPosInfoList = new ArrayList<>();
                oldPosInfoList.add(posList.get("Ope Type"));
                oldPosInfoList.add(posList.get("Formula"));
                oldPosInfoList.add(posList.get("Condition of Action"));
                oldPosInfoList.add(posList.get("Action in Such Condition"));
                oldPosInfoList.add(posList.get("Transition"));
                oldPosInfoList.add(posList.get("Sound"));
                oldPosInfoList.add(posList.get("DuringDriving"));
                oldPosInfoList.add(posList.get("Remark"));
                oldPosInfoList.add(posList.get("HardKey Event"));
                oldPosInfoList.add(posList.get("View Model"));
                oldPosInfoList.add(posList.get("Func of Model"));
                oldPosInfoList.add(posList.get("Observer"));
                oldPosInfoList.add(posList.get("Reply"));
                oldPosInfoList.add(posList.get("TransType"));
                oldPosInfoList.add(posList.get("TransID"));

                for (int posInfoIndex = 0; posInfoIndex < oldPosInfoList.size(); ++posInfoIndex) {
                    String oldPosInfo = oldPosInfoList.get(posInfoIndex);
                    int[] posRow = getPosRow(oldPosInfo);
                    String[] posCol = getPosCol(oldPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - 1));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newPosInfo = pos_0 + ":" + pos_1;
                    boolean isMerge = posCol[0].equals(posCol[1]) ? false : true;
                    CellStyleInfo cellStyle = new CellStyleInfo(
                            String.format("%s", newPosInfo),
                            "Meiryo UI", 11, isMerge, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255,255,153});
                    cellStyleSet.doOneCellStyle(cellStyle);
                    SheetPosValue cellVal = new SheetPosValue(String.format("%s", pos_0), "");
                    cellValueSet.doOneCellValue(cellVal);
                }
            }
            for (int k = 0; k < jsonPartInfoList.size(); ++k) {
                JsonPartInfo_new jsonPartInfo = jsonPartInfoList.get(k);
                JsonHKRefInfo hkRefInfo = jsonPartInfo.getHkRefInfo();
                String str1 = hkRefInfo.getHKDevName();
                String str2 = hkDevNameList.get(devNameIndex);
                boolean isSameDevName = false;
                if (str1 != null) {
                     isSameDevName = str1.equals(str2);
                }
                if (isSameDevName && checkHKRefInfo(hkRefInfo)) {
                    int rowCount = getHKRefInfoRowCount(hkRefInfo);
                    startRowCount += rowCount;
                    boolean isMergeRow = isMergeRow(rowCount);

                    // 写入Chapter
                    {
                        String oldChapterPosInfo = posList.get("Chapter");
                        int[] posRow = getPosRow(oldChapterPosInfo);
                        String[] posCol = getPosCol(oldChapterPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newChapterPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo chapterCellStyle = new CellStyleInfo(
                                String.format("%s", newChapterPosInfo),
                                "Meiryo UI", 11, isMergeRow, true,
                                "center", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{201,255,255});
                        cellStyleSet.doOneCellStyle(chapterCellStyle);
                        SheetPosValue chapterCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(hkRefInfo.getHKChapter()));
                        cellValueSet.doOneCellValue(chapterCellVal);
                    }

                    // 写入SubChapter
                    {
                        String oldSubChapterPosInfo = posList.get("SubChapter");
                        int[] posRow = getPosRow(oldSubChapterPosInfo);
                        String[] posCol = getPosCol(oldSubChapterPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newSubChapterPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo subChapterCellStyle = new CellStyleInfo(
                                String.format("%s", newSubChapterPosInfo),
                                "Meiryo UI", 11, isMergeRow, true,
                                "center", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255,205,156});
                        cellStyleSet.doOneCellStyle(subChapterCellStyle);
                        SheetPosValue subChapterCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(hkRefInfo.getHKSubChapter()));
                        cellValueSet.doOneCellValue(subChapterCellVal);
                    }

                    // 写入Normal
                    {
                        String oldNOPosInfo = posList.get("Normal");
                        int[] posRow = getPosRow(oldNOPosInfo);
                        String[] posCol = getPosCol(oldNOPosInfo);
                        // 写入NO.
                        {
                            String pos_0 = getNewCol(posCol[0], 0) + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                            String pos_1 = getNewCol(posCol[0], 0) + String.valueOf(posRow[0] + startRowCount);
                            String newNOPosInfo = pos_0 + ":" + pos_1;
                            CellStyleInfo noCellStyle = new CellStyleInfo(
                                    String.format("%s", newNOPosInfo),
                                    "Meiryo UI", 11, isMergeRow, true,
                                    "center", "center", "thin", "thin", "thin",
                                    "thin", false, new int[]{255,205,156});
                            cellStyleSet.doOneCellStyle(noCellStyle);
                            SheetPosValue noCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(hkRefInfo.getHKNo()));
                            cellValueSet.doOneCellValue(noCellVal);
                        }

                        // 写入StateNo
                        {
                            String pos_0 = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                            String pos_1 = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + startRowCount);
                            String newNOPosInfo = pos_0 + ":" + pos_1;

                            CellStyleInfo noCellStyle = new CellStyleInfo(
                                    String.format("%s", newNOPosInfo),
                                    "Meiryo UI", 11, isMergeRow, true,
                                    "center", "center", "thin", "thin", "thin",
                                    "thin", false, new int[]{204,255,204});
                            cellStyleSet.doOneCellStyle(noCellStyle);
                            SheetPosValue noCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(hkRefInfo.getHKStateNo()));
                            cellValueSet.doOneCellValue(noCellVal);
                        }

                        // 写入HKName_2
                        {
                            String pos_0 = getNewCol(posCol[0], 2) + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                            String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                            String newNOPosInfo = pos_0 + ":" + pos_1;

                            CellStyleInfo noCellStyle = new CellStyleInfo(
                                    String.format("%s", newNOPosInfo),
                                    "Meiryo UI", 11, true, true,
                                    "left", "center", "thin", "thin", "thin",
                                    "thin", false, new int[]{204,255,204});
                            cellStyleSet.doOneCellStyle(noCellStyle);
                            SheetPosValue noCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(hkRefInfo.getHKName()));
                            cellValueSet.doOneCellValue(noCellVal);
                        }
                    }

                    // 写入Ope type
                    {
                        String oldOpeTypePosInfo = posList.get("Ope Type");
                        int[] posRow = getPosRow(oldOpeTypePosInfo);
                        String[] posCol = getPosCol(oldOpeTypePosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newOpeTypePosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo opeTypeCellStyle = new CellStyleInfo(
                                String.format("%s", newOpeTypePosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(opeTypeCellStyle);
                        SheetPosValue opeTypeCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(hkRefInfo.getHKOpeType()));
                        cellValueSet.doOneCellValue(opeTypeCellVal);
                        // TODO
                        String[] opeTypeData = hkDropDownData.getOpeTypeData();
                        setDropDownList(this.sheetInfo, newOpeTypePosInfo, opeTypeData);
                    }

                    // 写入Formula
                    {
                        String oldFormulaPosInfo = posList.get("Formula");
                        int[] posRow = getPosRow(oldFormulaPosInfo);
                        String[] posCol = getPosCol(oldFormulaPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newFormulaPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo formulaCellStyle = new CellStyleInfo(
                                String.format("%s", newFormulaPosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(formulaCellStyle);
                        SheetPosValue formulaCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(hkRefInfo.getHKFormula()));
                        cellValueSet.doOneCellValue(formulaCellVal);
                    }

                    // 写入Condition of Action
                    {
                        String oldActionConditionPosInfo = posList.get("Condition of Action");
                        int[] posRow = getPosRow(oldActionConditionPosInfo);
                        String[] posCol = getPosCol(oldActionConditionPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_0_0 = posCol[0] + String.valueOf(posRow[0] + startRowCount);
                        String newActionConditionPosInfo_1 = pos_0 + ":" + pos_0_0;

                        CellStyleInfo actionConditionCellStyle_1 = new CellStyleInfo(
                                String.format("%s", newActionConditionPosInfo_1),
                                "Meiryo UI", 11, false, isMergeRow,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(actionConditionCellStyle_1);

                        HashMap<String, String> hkCondition = hkRefInfo.getHKCondition();
                        Set<String> hkConditionKey = hkCondition.keySet();
                        if (hkConditionKey.size() != 0) {
                            int index = 0;
                            for (String key : hkConditionKey) {
                                String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                                SheetPosValue hkConditionCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue(key));
                                cellValueSet.doOneCellValue(hkConditionCellVal_1);

                                String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                                String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + index + (startRowCount - rowCount));
                                String newHKConditionPosInfo_2 = pos_0_new + ":" + pos_1;

                                CellStyleInfo hkConditionCellStyle_2 = new CellStyleInfo(
                                        String.format("%s", newHKConditionPosInfo_2),
                                        "Meiryo UI", 11, true, true,
                                        "left", "center", "thin", "thin", "thin",
                                        "thin", false, new int[]{255, 255, 255});
                                cellStyleSet.doOneCellStyle(hkConditionCellStyle_2);

                                SheetPosValue hkConditionCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), checkCellValue(hkCondition.get(key)));
                                cellValueSet.doOneCellValue(hkConditionCellVal_2);
                                // TODO
                                String[] conditionOfActionData = hkDropDownData.getConditionOfActionData();
                                setDropDownList(this.sheetInfo, newHKConditionPosInfo_2, conditionOfActionData);
                                index++;
                            }
                        } else {
                            String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                            SheetPosValue hkConditionCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue("A"));
                            cellValueSet.doOneCellValue(hkConditionCellVal_1);

                            String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                            String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + (startRowCount - rowCount));
                            String newHKConditionPosInfo_2 = pos_0_new + ":" + pos_1;

                            CellStyleInfo hkConditionCellStyle_2 = new CellStyleInfo(
                                    String.format("%s", newHKConditionPosInfo_2),
                                    "Meiryo UI", 11, true, true,
                                    "left", "center", "thin", "thin", "thin",
                                    "thin", false, new int[]{255, 255, 255});
                            cellStyleSet.doOneCellStyle(hkConditionCellStyle_2);

                            SheetPosValue hkConditionCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), "-");
                            cellValueSet.doOneCellValue(hkConditionCellVal_2);
                            // TODO
                            String[] conditionOfActionData = hkDropDownData.getConditionOfActionData();
                            setDropDownList(this.sheetInfo, newHKConditionPosInfo_2, conditionOfActionData);
                        }
                    }

                    // 写入Action in Such Condition
                    {
                        String oldHKActionPosInfo = posList.get("Action in Such Condition");
                        int[] posRow = getPosRow(oldHKActionPosInfo);
                        String[] posCol = getPosCol(oldHKActionPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newHKActionPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo hkActionCellStyle = new CellStyleInfo(
                                String.format("%s", newHKActionPosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(hkActionCellStyle);
                        SheetPosValue hkActionCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(hkRefInfo.getHKAction()));
                        cellValueSet.doOneCellValue(hkActionCellVal);
                        // TODO
                        String[] actionInSuchConditionData = hkDropDownData.getActionInSuchConditionData();
                        setDropDownList(this.sheetInfo, newHKActionPosInfo, actionInSuchConditionData);
                    }

                    // 写入Transition
                    {
                        String oldTransitionPosInfo = posList.get("Transition");
                        int[] posRow = getPosRow(oldTransitionPosInfo);
                        String[] posCol = getPosCol(oldTransitionPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newTransitionPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo transitionCellStyle = new CellStyleInfo(
                                String.format("%s", newTransitionPosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(transitionCellStyle);
                        SheetPosValue transitionCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(hkRefInfo.getHKTrans()));
                        cellValueSet.doOneCellValue(transitionCellVal);
                    }

                    // 写入Sound
                    {
                        String oldSoundPosInfo = posList.get("Sound");
                        int[] posRow = getPosRow(oldSoundPosInfo);
                        String[] posCol = getPosCol(oldSoundPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newSoundPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo soundCellStyle = new CellStyleInfo(
                                String.format("%s", newSoundPosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(soundCellStyle);
                        SheetPosValue soundCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(hkRefInfo.getHKSound()));
                        cellValueSet.doOneCellValue(soundCellVal);
                        // TODO
                        String[] soundData = hkDropDownData.getSoundData();
                        setDropDownList(this.sheetInfo, newSoundPosInfo, soundData);
                    }

                    // 写入DuringDriving
                    {
                        String oldDuringDrivingPosInfo = posList.get("DuringDriving");
                        int[] posRow = getPosRow(oldDuringDrivingPosInfo);
                        String[] posCol = getPosCol(oldDuringDrivingPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newDuringDrivingPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo duringDrivingCellStyle = new CellStyleInfo(
                                String.format("%s", newDuringDrivingPosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(duringDrivingCellStyle);
                        SheetPosValue duringDrivingCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(hkRefInfo.getHKDuringDriving()));
                        cellValueSet.doOneCellValue(duringDrivingCellVal);
                        // TODO
                        String[] duringDriving = hkDropDownData.getDuringDriving();
                        setDropDownList(this.sheetInfo, newDuringDrivingPosInfo, duringDriving);
                    }

                    // 写入Remark
                    {
                        String oldRemarkPosInfo = posList.get("Remark");
                        int[] posRow = getPosRow(oldRemarkPosInfo);
                        String[] posCol = getPosCol(oldRemarkPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newRemarkPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo remarkCellStyle = new CellStyleInfo(
                                String.format("%s", newRemarkPosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(remarkCellStyle);
                        SheetPosValue remarkCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(hkRefInfo.getHKRemark()));
                        cellValueSet.doOneCellValue(remarkCellVal);
                    }

                    // 写入UUID
                    {
                        String oldUUIDPosInfo = posList.get("UUID");
                        int[] posRow = getPosRow(oldUUIDPosInfo);
                        String[] posCol = getPosCol(oldUUIDPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newUUIDPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo UUIDCellStyle = new CellStyleInfo(
                                String.format("%s", newUUIDPosInfo),
                                "Meiryo UI", 11, isMergeRow, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(UUIDCellStyle);
                        SheetPosValue UUIDCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(jsonSketchInfo.getSketchInfoId() + "->" + jsonPartInfo.getUuid()));
                        cellValueSet.doOneCellValue(UUIDCellVal);
                    }

                    // 写入HardKey Event
                    {
                        String oldEventPosInfo = posList.get("HardKey Event");
                        int[] posRow = getPosRow(oldEventPosInfo);
                        String[] posCol = getPosCol(oldEventPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newEventPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo eventCellStyle = new CellStyleInfo(
                                String.format("%s", newEventPosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(eventCellStyle);
                        // TODO
                        int row = getPosRowSingle(pos_0);
                        String col = getPosColSingle(pos_0);
                        int iCol = excelColStrToNum(col, col.length());
                        String formulaString = hkDropDownData.getEventDataFormulaString(row);
                        setLinkage(row, iCol, formulaString);
                    }

                    // 写入View Model
                    {
                        String oldHKConditionModelPosInfo = posList.get("View Model");
                        int[] posRow = getPosRow(oldHKConditionModelPosInfo);
                        String[] posCol = getPosCol(oldHKConditionModelPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_0_0 = posCol[0] + String.valueOf(posRow[0] + startRowCount);
                        String newHKConditionModelPosInfo_1 = pos_0 + ":" + pos_0_0;

                        CellStyleInfo hkConditionModelCellStyle_1 = new CellStyleInfo(
                                String.format("%s", newHKConditionModelPosInfo_1),
                                "Meiryo UI", 11, false, isMergeRow,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(hkConditionModelCellStyle_1);

                        HashMap<String, String> hkConditionModel = hkRefInfo.getHKConditionModel();
                        Set<String> hkConditionModelKey = hkConditionModel.keySet();
                        if (hkConditionModelKey.size() != 0) {
                            int index = 0;
                            for (String key : hkConditionModelKey) {
                                String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                                SheetPosValue hkConditionModelCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue(key));
                                cellValueSet.doOneCellValue(hkConditionModelCellVal_1);

                                String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                                String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + index + (startRowCount - rowCount));
                                String newHKConditionModelPosInfo_2 = pos_0_new + ":" + pos_1;

                                CellStyleInfo hkConditionModelCellStyle_2 = new CellStyleInfo(
                                        String.format("%s", newHKConditionModelPosInfo_2),
                                        "Meiryo UI", 11, true, true,
                                        "left", "center", "thin", "thin", "thin",
                                        "thin", false, new int[]{255, 255, 255});
                                cellStyleSet.doOneCellStyle(hkConditionModelCellStyle_2);

                                SheetPosValue hkConditionModelCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), checkCellValue(hkConditionModel.get(key)));
                                cellValueSet.doOneCellValue(hkConditionModelCellVal_2);
                                // TODO
                                int row = getPosRowSingle(pos_0_new);
                                String col = getPosColSingle(pos_0_new);
                                int iCol = excelColStrToNum(col, col.length());
                                String formulaString = hkDropDownData.getViewModelDataFormulaString(row);
                                setLinkage(row, iCol, formulaString);
                                index++;
                            }
                        } else {
                            char ch = 'A';
                            for (int i = 0; i < rowCount; ++i) {
                                String key = String.valueOf(ch);
                                ch += 1;
                                String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + i + (startRowCount - rowCount));
                                SheetPosValue hkConditionModelCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue(key));
                                cellValueSet.doOneCellValue(hkConditionModelCellVal_1);

                                String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + i + (startRowCount - rowCount));
                                String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + i + (startRowCount - rowCount));
                                String newHKConditionModelPosInfo_2 = pos_0_new + ":" + pos_1;

                                CellStyleInfo hkConditionModelCellStyle_2 = new CellStyleInfo(
                                        String.format("%s", newHKConditionModelPosInfo_2),
                                        "Meiryo UI", 11, true, true,
                                        "left", "center", "thin", "thin", "thin",
                                        "thin", false, new int[]{255, 255, 255});
                                cellStyleSet.doOneCellStyle(hkConditionModelCellStyle_2);

                                SheetPosValue hkConditionModelCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), "-");
                                cellValueSet.doOneCellValue(hkConditionModelCellVal_2);
                                // TODO
                                int row = getPosRowSingle(pos_0_new);
                                String col = getPosColSingle(pos_0_new);
                                int iCol = excelColStrToNum(col, col.length());
                                String formulaString = hkDropDownData.getViewModelDataFormulaString(row);
                                setLinkage(row, iCol, formulaString);
                            }
                        }
                    }

                    // 写入Func of Model
                    {
                        String oldFuncModelPosInfo = posList.get("Func of Model");
                        int[] posRow = getPosRow(oldFuncModelPosInfo);
                        String[] posCol = getPosCol(oldFuncModelPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newFuncModelPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo funcModelCellStyle = new CellStyleInfo(
                                String.format("%s", newFuncModelPosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(funcModelCellStyle);
                        SheetPosValue funcModelCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(hkRefInfo.getHKActionModel()));
                        cellValueSet.doOneCellValue(funcModelCellVal);
                        // TODO
                        int row = getPosRowSingle(pos_0);
                        String col = getPosColSingle(pos_0);
                        int iCol = excelColStrToNum(col, col.length());
                        String formulaString = hkDropDownData.getFuncOfModelDataFormulaString(row);
                        setLinkage(row, iCol, formulaString);
                    }

                    // 写入Observer
                    {
                        String oldObserverPosInfo = posList.get("Observer");
                        int[] posRow = getPosRow(oldObserverPosInfo);
                        String[] posCol = getPosCol(oldObserverPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newObserverPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo observerCellStyle = new CellStyleInfo(
                                String.format("%s", newObserverPosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(observerCellStyle);
                        SheetPosValue observerCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(hkRefInfo.getHKObserver()));
                        cellValueSet.doOneCellValue(observerCellVal);
                    }

                    // 写入Reply
                    {
                        String oldReplyPosInfo = posList.get("Reply");
                        int[] posRow = getPosRow(oldReplyPosInfo);
                        String[] posCol = getPosCol(oldReplyPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newReplyPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo replyCellStyle = new CellStyleInfo(
                                String.format("%s", newReplyPosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(replyCellStyle);
                        SheetPosValue replyCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(hkRefInfo.getHKReply()));
                        cellValueSet.doOneCellValue(replyCellVal);
                    }

                    // 写入TransType
                    {
                        String oldTransTypePosInfo = posList.get("TransType");
                        int[] posRow = getPosRow(oldTransTypePosInfo);
                        String[] posCol = getPosCol(oldTransTypePosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newTransTypePosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo transTypeCellStyle = new CellStyleInfo(
                                String.format("%s", newTransTypePosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(transTypeCellStyle);
                        SheetPosValue transTypeCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(hkRefInfo.getHKTransType()));
                        cellValueSet.doOneCellValue(transTypeCellVal);
                    }

                    // 写入TransID
                    {
                        String oldTransIDPosInfo = posList.get("TransID");
                        int[] posRow = getPosRow(oldTransIDPosInfo);
                        String[] posCol = getPosCol(oldTransIDPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newTransIDPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo transIDCellStyle = new CellStyleInfo(
                                String.format("%s", newTransIDPosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(transIDCellStyle);
                        SheetPosValue transIDCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(hkRefInfo.getHKTransID()));
                        cellValueSet.doOneCellValue(transIDCellVal);
                    }
                }
            }
        }

        StartRowIndex += 2 + startRowCount + DefaultTableSpace;
        // HKRefInfoStr
        {
            String hkRefInfoPosInfo1 = "B" + String.valueOf(StartRowIndex - 1) + ":" + "B" + String.valueOf(StartRowIndex - 1);
            CellStyleInfo hkRefInfoCellStyle = new CellStyleInfo(
                    String.format("%s", hkRefInfoPosInfo1),
                    "Meiryo UI", 11, false, true,
                    "left", "center", "", "thin", "thin",
                    "thin", false, new int[]{255, 255, 255});
            cellStyleSet.doOneCellStyle(hkRefInfoCellStyle);
            String hkRefInfoPosInfo2 = "C" + String.valueOf(StartRowIndex - 1);
            SheetPosValue hkRefInfoCellVal = new SheetPosValue(String.format("%s", hkRefInfoPosInfo2), checkCellValue(jsonSketchInfo.getHKRefInfoStr()));
            cellValueSet.doOneCellValue(hkRefInfoCellVal);
        }
        return StartRowIndex;
    }

    private int doExportInitRefInfo(JsonSketchInfo_new jsonSketchInfo, int StartRowIndex) throws Exception {
        // 表头设置
        HMIPartsSpecStatic_new staticInfo = new HMIPartsSpecStatic_new();

        CellStyleSet cellStyleSet = new CellStyleSet(this.workBook, this.sheetInfo);
        ArrayList<CellStyleInfo> styleList = getNewStyleList(staticInfo.styleLists.get("InitRefInfo"), StartRowIndex, 5);
        cellStyleSet.doCellStyleList(styleList);

        CellValueSet cellValueSet = new CellValueSet(this.workBook, this.sheetInfo);
        ArrayList<SheetPosValue> lableList = getNewLableList(staticInfo.lableLists.get("InitRefInfo"), StartRowIndex, 5);
        cellValueSet.doCellValueList(lableList);

        HashMap<String, String> posList = getPosList(lableList, styleList, 5);
        ArrayList<JsonPartInfo_new> jsonPartInfoList = sortByNo(jsonSketchInfo.getJsonPartInfoList(), 5);
        HMIDropDownData dropDownData = HMIDropDownData.getInstance();
        HMIDropDownData.InitDropDownData initDropDownData = dropDownData.getInitDropDownDataInstance();
        int startRowCount = 0;
        for (int k = 0; k < jsonPartInfoList.size(); ++k) {
            JsonPartInfo_new jsonPartInfo = jsonPartInfoList.get(k);
            JsonInitRefInfo initRefInfo = jsonPartInfo.getInitRefInfo();
            if (checkInitRefInfo(initRefInfo)) {
                int rowCount = getInitRefInfoRowCount(initRefInfo);
                startRowCount += rowCount;
                boolean isMergeRow = isMergeRow(rowCount);

                // 写入Chapter
                {
                    String oldChapterPosInfo = posList.get("Chapter");
                    int[] posRow = getPosRow(oldChapterPosInfo);
                    String[] posCol = getPosCol(oldChapterPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newChapterPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo chapterCellStyle = new CellStyleInfo(
                            String.format("%s", newChapterPosInfo),
                            "Meiryo UI", 11, isMergeRow, true,
                            "center", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{201,255,255});
                    cellStyleSet.doOneCellStyle(chapterCellStyle);
                    SheetPosValue chapterCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(initRefInfo.getInitChapter()));
                    cellValueSet.doOneCellValue(chapterCellVal);
                }

                // 写入Initialized Status
                {
                    String oldInitializedStatusPosInfo = posList.get("Initialized Status");
                    int[] posRow = getPosRow(oldInitializedStatusPosInfo);
                    String[] posCol = getPosCol(oldInitializedStatusPosInfo);
                    // 写入NO.
                    {
                        String pos_0 = getNewCol(posCol[0], 0) + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = getNewCol(posCol[0], 0) + String.valueOf(posRow[0] + startRowCount);
                        String newNoPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo noCellStyle = new CellStyleInfo(
                                String.format("%s", newNoPosInfo),
                                "Meiryo UI", 11, isMergeRow, true,
                                "center", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255,205,156});
                        cellStyleSet.doOneCellStyle(noCellStyle);
                        SheetPosValue noCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(initRefInfo.getInitNo()));
                        cellValueSet.doOneCellValue(noCellVal);
                    }

                    // 写入StateNo
                    {
                        String pos_0 = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + startRowCount);
                        String newStateNoPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo stateNoCellStyle = new CellStyleInfo(
                                String.format("%s", newStateNoPosInfo),
                                "Meiryo UI", 11, isMergeRow, true,
                                "center", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{204,255,204});
                        cellStyleSet.doOneCellStyle(stateNoCellStyle);
                        SheetPosValue stateNoCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(initRefInfo.getInitStateNo()));
                        cellValueSet.doOneCellValue(stateNoCellVal);
                    }

                    // 写入Name
                    {
                        String pos_0 = getNewCol(posCol[0], 2) + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newNamePosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo nameCellStyle = new CellStyleInfo(
                                String.format("%s", newNamePosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{204,255,204});
                        cellStyleSet.doOneCellStyle(nameCellStyle);
                        SheetPosValue nameCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(initRefInfo.getInitStatus()));
                        cellValueSet.doOneCellValue(nameCellVal);
                    }
                }

                // 写入Formula
                {
                    String oldFormulaPosInfo = posList.get("Formula");
                    int[] posRow = getPosRow(oldFormulaPosInfo);
                    String[] posCol = getPosCol(oldFormulaPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newFormulaPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo formulaCellStyle = new CellStyleInfo(
                            String.format("%s", newFormulaPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(formulaCellStyle);
                    SheetPosValue formulaCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(initRefInfo.getInitFormula()));
                    cellValueSet.doOneCellValue(formulaCellVal);
                }

                // 写入DisplayCondition
                {
                    String oldInitConditionPosInfo = posList.get("Condition of Action");
                    int[] posRow = getPosRow(oldInitConditionPosInfo);
                    String[] posCol = getPosCol(oldInitConditionPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_0_0 = posCol[0] + String.valueOf(posRow[0] + startRowCount);
                    String newInitConditionPosInfo_1 = pos_0 + ":" + pos_0_0;

                    CellStyleInfo initConditionCellStyle_1 = new CellStyleInfo(
                            String.format("%s", newInitConditionPosInfo_1),
                            "Meiryo UI", 11, false, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(initConditionCellStyle_1);

                    HashMap<String, String> initCondition = initRefInfo.getInitCondition();
                    Set<String> initConditionKey = initCondition.keySet();
                    if (initConditionKey.size() != 0) {
                        int index = 0;
                        for (String key : initConditionKey) {
                            String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                            SheetPosValue initConditionCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue(key));
                            cellValueSet.doOneCellValue(initConditionCellVal_1);

                            String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                            String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + index + (startRowCount - rowCount));
                            String newInitConditionPosInfo_2 = pos_0_new + ":" + pos_1;

                            CellStyleInfo initConditionCellStyle_2 = new CellStyleInfo(
                                    String.format("%s", newInitConditionPosInfo_2),
                                    "Meiryo UI", 11, true, true,
                                    "left", "center", "thin", "thin", "thin",
                                    "thin", false, new int[]{255, 255, 255});
                            cellStyleSet.doOneCellStyle(initConditionCellStyle_2);

                            SheetPosValue initConditionCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), checkCellValue(initCondition.get(key)));
                            cellValueSet.doOneCellValue(initConditionCellVal_2);
                            // TODO
                            String[] conditionOfActionData = initDropDownData.getConditionOfActionData();
                            setDropDownList(this.sheetInfo, newInitConditionPosInfo_2, conditionOfActionData);
                            index++;
                        }
                    } else {
                        String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        SheetPosValue initConditionCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue("A"));
                        cellValueSet.doOneCellValue(initConditionCellVal_1);

                        String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + (startRowCount - rowCount));
                        String newInitConditionPosInfo_2 = pos_0_new + ":" + pos_1;

                        CellStyleInfo initConditionCellStyle_2 = new CellStyleInfo(
                                String.format("%s", newInitConditionPosInfo_2),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(initConditionCellStyle_2);

                        SheetPosValue initConditionCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), "-");
                        cellValueSet.doOneCellValue(initConditionCellVal_2);
                        // TODO
                        String[] conditionOfActionData = initDropDownData.getConditionOfActionData();
                        setDropDownList(this.sheetInfo, newInitConditionPosInfo_2, conditionOfActionData);
                    }
                }

                // 写入Display in Such Condition(InitAction)
                {
                    String oldInitActionPosInfo = posList.get("Action in Such Condition");
                    int[] posRow = getPosRow(oldInitActionPosInfo);
                    String[] posCol = getPosCol(oldInitActionPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newInitActionPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo initActionCellStyle = new CellStyleInfo(
                            String.format("%s", newInitActionPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(initActionCellStyle);
                    SheetPosValue initActionCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(initRefInfo.getInitAction()));
                    cellValueSet.doOneCellValue(initActionCellVal);
                    // TODO
                    String[] actionInSuchConditionData = initDropDownData.getActionInSuchConditionData();
                    setDropDownList(this.sheetInfo, newInitActionPosInfo, actionInSuchConditionData);
                }

                // 写入Transition
                {
                    String oldTransitionPosInfo = posList.get("Transition");
                    int[] posRow = getPosRow(oldTransitionPosInfo);
                    String[] posCol = getPosCol(oldTransitionPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newTransitionPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo transitionCellStyle = new CellStyleInfo(
                            String.format("%s", newTransitionPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(transitionCellStyle);
                    SheetPosValue transitionCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(initRefInfo.getInitTrans()));
                    cellValueSet.doOneCellValue(transitionCellVal);
                }

                // 写入空白 (Blank_0)
                {
                    String oldBlank_0PosInfo = posList.get("Blank_0");
                    int[] posRow = getPosRow(oldBlank_0PosInfo);
                    String[] posCol = getPosCol(oldBlank_0PosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newBlank_0PosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo blank_0CellStyle = new CellStyleInfo(
                            String.format("%s", newBlank_0PosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(blank_0CellStyle);
                    SheetPosValue blank_0CellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue("-"));
                    cellValueSet.doOneCellValue(blank_0CellVal);
                }

                // 写入Remark
                {
                    String oldRemarkPosInfo = posList.get("Remark");
                    int[] posRow = getPosRow(oldRemarkPosInfo);
                    String[] posCol = getPosCol(oldRemarkPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newRemarkPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo remarkCellStyle = new CellStyleInfo(
                            String.format("%s", newRemarkPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(remarkCellStyle);
                    SheetPosValue remarkCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(initRefInfo.getInitRemark()));
                    cellValueSet.doOneCellValue(remarkCellVal);
                }

                // 写入UUID
                {
                    String oldUUIDPosInfo = posList.get("UUID");
                    int[] posRow = getPosRow(oldUUIDPosInfo);
                    String[] posCol = getPosCol(oldUUIDPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newUUIDPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo UUIDCellStyle = new CellStyleInfo(
                            String.format("%s", newUUIDPosInfo),
                            "Meiryo UI", 11, isMergeRow, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(UUIDCellStyle);
                    SheetPosValue UUIDCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(jsonSketchInfo.getSketchInfoId() + "->" + jsonPartInfo.getUuid()));
                    cellValueSet.doOneCellValue(UUIDCellVal);
                }

                // 写入Event
                {
                    String oldEventPosInfo = posList.get("Event");
                    int[] posRow = getPosRow(oldEventPosInfo);
                    String[] posCol = getPosCol(oldEventPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newEventPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo eventCellStyle = new CellStyleInfo(
                            String.format("%s", newEventPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(eventCellStyle);
                    SheetPosValue eventCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(initRefInfo.getInitEvent()));
                    cellValueSet.doOneCellValue(eventCellVal);
                }

                // 写入View Model
                {
                    String oldInitConditionModelPosInfo = posList.get("View Model");
                    int[] posRow = getPosRow(oldInitConditionModelPosInfo);
                    String[] posCol = getPosCol(oldInitConditionModelPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_0_0 = posCol[0] + String.valueOf(posRow[0] + startRowCount);
                    String newinitConditionModelPosInfo_1 = pos_0 + ":" + pos_0_0;

                    CellStyleInfo initConditionModelCellStyle_1 = new CellStyleInfo(
                            String.format("%s", newinitConditionModelPosInfo_1),
                            "Meiryo UI", 11, false, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(initConditionModelCellStyle_1);

                    HashMap<String, String> initConditionModel = initRefInfo.getInitConditionModel();
                    Set<String> initConditionModelKey = initConditionModel.keySet();
                    if (initConditionModelKey.size() != 0) {
                        int index = 0;
                        for (String key : initConditionModelKey) {
                            String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                            SheetPosValue initConditionModelCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue(key));
                            cellValueSet.doOneCellValue(initConditionModelCellVal_1);

                            String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                            String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + index + (startRowCount - rowCount));
                            String newInitConditionModelPosInfo_2 = pos_0_new + ":" + pos_1;

                            CellStyleInfo initConditionModelCellStyle_2 = new CellStyleInfo(
                                    String.format("%s", newInitConditionModelPosInfo_2),
                                    "Meiryo UI", 11, true, true,
                                    "left", "center", "thin", "thin", "thin",
                                    "thin", false, new int[]{255, 255, 255});
                            cellStyleSet.doOneCellStyle(initConditionModelCellStyle_2);

                            SheetPosValue initConditionModelCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), checkCellValue(initConditionModel.get(key)));
                            cellValueSet.doOneCellValue(initConditionModelCellVal_2);
                            // TODO
                            int row = getPosRowSingle(pos_0_new);
                            String col = getPosColSingle(pos_0_new);
                            int iCol = excelColStrToNum(col, col.length());
                            String formulaString = initDropDownData.getViewModelDataFormulaString(row);
                            setLinkage(row, iCol, formulaString);
                            index++;
                        }
                    } else {
                        String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        SheetPosValue initConditionModelCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue("A"));
                        cellValueSet.doOneCellValue(initConditionModelCellVal_1);

                        String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + (startRowCount - rowCount));
                        String newInitConditionModelPosInfo_2 = pos_0_new + ":" + pos_1;

                        CellStyleInfo initConditionModelCellStyle_2 = new CellStyleInfo(
                                String.format("%s", newInitConditionModelPosInfo_2),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(initConditionModelCellStyle_2);

                        SheetPosValue initConditionModelCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), "-");
                        cellValueSet.doOneCellValue(initConditionModelCellVal_2);
                        // TODO
                        int row = getPosRowSingle(pos_0_new);
                        String col = getPosColSingle(pos_0_new);
                        int iCol = excelColStrToNum(col, col.length());
                        String formulaString = initDropDownData.getViewModelDataFormulaString(row);
                        setLinkage(row, iCol, formulaString);
                    }
                }

                // 写入Func of Model
                {
                    String oldFuncModelPosInfo = posList.get("Func of Model");
                    int[] posRow = getPosRow(oldFuncModelPosInfo);
                    String[] posCol = getPosCol(oldFuncModelPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newFuncModelPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo funcModelCellStyle = new CellStyleInfo(
                            String.format("%s", newFuncModelPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(funcModelCellStyle);
                    SheetPosValue funcModelCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(initRefInfo.getInitActionModel()));
                    cellValueSet.doOneCellValue(funcModelCellVal);
                    // TODO
                    int row = getPosRowSingle(pos_0);
                    String col = getPosColSingle(pos_0);
                    int iCol = excelColStrToNum(col, col.length());
                    String formulaString = initDropDownData.getFuncOfModelDataFormulaString(row);
                    setLinkage(row, iCol, formulaString);
                }

                // 写入Observer
                {
                    String oldObserverPosInfo = posList.get("Observer");
                    int[] posRow = getPosRow(oldObserverPosInfo);
                    String[] posCol = getPosCol(oldObserverPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newObserverPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo observerCellStyle = new CellStyleInfo(
                            String.format("%s", newObserverPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(observerCellStyle);
                    SheetPosValue observerCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(initRefInfo.getInitObserver()));
                    cellValueSet.doOneCellValue(observerCellVal);
                }

                // 写入Reply
                {
                    String oldReplyPosInfo = posList.get("Reply");
                    int[] posRow = getPosRow(oldReplyPosInfo);
                    String[] posCol = getPosCol(oldReplyPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newReplyPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo replyCellStyle = new CellStyleInfo(
                            String.format("%s", newReplyPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(replyCellStyle);
                    SheetPosValue replyCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(initRefInfo.getInitReplay()));
                    cellValueSet.doOneCellValue(replyCellVal);
                }

                // 写入TransType
                {
                    String oldTransTypePosInfo = posList.get("TransType");
                    int[] posRow = getPosRow(oldTransTypePosInfo);
                    String[] posCol = getPosCol(oldTransTypePosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newTransTypePosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo transTypeCellStyle = new CellStyleInfo(
                            String.format("%s", newTransTypePosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(transTypeCellStyle);
                    SheetPosValue transTypeCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(initRefInfo.getInitTransType()));
                    cellValueSet.doOneCellValue(transTypeCellVal);
                }

                // 写入TransID
                {
                    String oldTransIDPosInfo = posList.get("TransID");
                    int[] posRow = getPosRow(oldTransIDPosInfo);
                    String[] posCol = getPosCol(oldTransIDPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newTransIDPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo transIDCellStyle = new CellStyleInfo(
                            String.format("%s", newTransIDPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(transIDCellStyle);
                    SheetPosValue transIDCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(initRefInfo.getInitTransID()));
                    cellValueSet.doOneCellValue(transIDCellVal);
                }
            }
        }

        StartRowIndex += 1 + startRowCount + DefaultTableSpace;
        // HKRefInfoStr
        {
            String initRefInfoPosInfo1 = "B" + String.valueOf(StartRowIndex - 1) + ":" + "B" + String.valueOf(StartRowIndex - 1);
            CellStyleInfo initRefInfoCellStyle = new CellStyleInfo(
                    String.format("%s", initRefInfoPosInfo1),
                    "Meiryo UI", 11, false, true,
                    "left", "center", "", "thin", "thin",
                    "thin", false, new int[]{255, 255, 255});
            cellStyleSet.doOneCellStyle(initRefInfoCellStyle);
            String initRefInfoPosInfo2 = "C" + String.valueOf(StartRowIndex - 1);
            SheetPosValue initRefInfoCellVal = new SheetPosValue(String.format("%s", initRefInfoPosInfo2), checkCellValue(jsonSketchInfo.getInitRefInfoStr()));
            cellValueSet.doOneCellValue(initRefInfoCellVal);
        }
        return StartRowIndex;
    }

    private int doExportStatusChangeRefInfo(JsonSketchInfo_new jsonSketchInfo, int StartRowIndex) throws Exception {
        // 表头设置
        HMIPartsSpecStatic_new staticInfo = new HMIPartsSpecStatic_new();

        CellStyleSet cellStyleSet = new CellStyleSet(this.workBook, this.sheetInfo);
        ArrayList<CellStyleInfo> styleList = getNewStyleList(staticInfo.styleLists.get("StatusChangeRefInfo"), StartRowIndex, 6);
        cellStyleSet.doCellStyleList(styleList);

        CellValueSet cellValueSet = new CellValueSet(this.workBook, this.sheetInfo);
        ArrayList<SheetPosValue> lableList = getNewLableList(staticInfo.lableLists.get("StatusChangeRefInfo"), StartRowIndex, 6);
        cellValueSet.doCellValueList(lableList);

        HashMap<String, String> posList = getPosList(lableList, styleList, 6);
        ArrayList<JsonPartInfo_new> jsonPartInfoList = sortByNo(jsonSketchInfo.getJsonPartInfoList(), 6);
        HMIDropDownData dropDownData = HMIDropDownData.getInstance();
        HMIDropDownData.StatusChangeDropDownData statusChangeDropDownData = dropDownData.getStatusChangeDropDownDataInstance();
        int startRowCount = 0;
        for (int k = 0; k < jsonPartInfoList.size(); ++k) {
            JsonPartInfo_new jsonPartInfo = jsonPartInfoList.get(k);
            JsonStatusChangeRefInfo statusChangeRefInfo = jsonPartInfo.getStatusChangeRefInfo();
            if (checkStatusChangeRefInfo(statusChangeRefInfo)) {
                int rowCount = getStatusChangeRefInfoRowCount(statusChangeRefInfo);
                startRowCount += rowCount;
                boolean isMergeRow = isMergeRow(rowCount);

                // 写入Chapter
                {
                    String oldChapterPosInfo = posList.get("Chapter");
                    int[] posRow = getPosRow(oldChapterPosInfo);
                    String[] posCol = getPosCol(oldChapterPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newChapterPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo chapterCellStyle = new CellStyleInfo(
                            String.format("%s", newChapterPosInfo),
                            "Meiryo UI", 11, isMergeRow, true,
                            "center", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{201,255,255});
                    cellStyleSet.doOneCellStyle(chapterCellStyle);
                    SheetPosValue chapterCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(statusChangeRefInfo.getStatusChangeChapter()));
                    cellValueSet.doOneCellValue(chapterCellVal);
                }

                // 写入SubChapter
                {
                    String oldSubChapterPosInfo = posList.get("SubChapter");
                    int[] posRow = getPosRow(oldSubChapterPosInfo);
                    String[] posCol = getPosCol(oldSubChapterPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newSubChapterPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo subChapterCellStyle = new CellStyleInfo(
                            String.format("%s", newSubChapterPosInfo),
                            "Meiryo UI", 11, isMergeRow, true,
                            "center", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255,205,156});
                    cellStyleSet.doOneCellStyle(subChapterCellStyle);
                    SheetPosValue subChapterCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(statusChangeRefInfo.getStatusChangeSubChapter()));
                    cellValueSet.doOneCellValue(subChapterCellVal);
                }

                // 写入Normal
                {
                    String oldNormalPosInfo = posList.get("Normal");
                    int[] posRow = getPosRow(oldNormalPosInfo);
                    String[] posCol = getPosCol(oldNormalPosInfo);
                    // 写入NO.
                    {
                        String pos_0 = getNewCol(posCol[0], 0) + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = getNewCol(posCol[0], 0) + String.valueOf(posRow[0] + startRowCount);
                        String newNOPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo noCellStyle = new CellStyleInfo(
                                String.format("%s", newNOPosInfo),
                                "Meiryo UI", 11, isMergeRow, true,
                                "center", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255,205,156});
                        cellStyleSet.doOneCellStyle(noCellStyle);
                        SheetPosValue noCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(statusChangeRefInfo.getStatusChangeNo()));
                        cellValueSet.doOneCellValue(noCellVal);
                    }

                    // 写入StateNo
                    {
                        String pos_0 = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + startRowCount);
                        String newStateNoPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo stateNoCellStyle = new CellStyleInfo(
                                String.format("%s", newStateNoPosInfo),
                                "Meiryo UI", 11, isMergeRow, true,
                                "center", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{204,255,204});
                        cellStyleSet.doOneCellStyle(stateNoCellStyle);
                        SheetPosValue stateNoCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(statusChangeRefInfo.getStatusChangeStateNo()));
                        cellValueSet.doOneCellValue(stateNoCellVal);
                    }

                    // 写入Name
                    {
                        String pos_0 = getNewCol(posCol[0], 2) + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newNamePosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo nameCellStyle = new CellStyleInfo(
                                String.format("%s", newNamePosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{204,255,204});
                        cellStyleSet.doOneCellStyle(nameCellStyle);
                        SheetPosValue nameCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(statusChangeRefInfo.getStatusChangeName()));
                        cellValueSet.doOneCellValue(nameCellVal);
                    }
                }

                // 写入Forward from
                {
                    // 写入Formula_H
                    {
                        String oldFormulaHPosInfo = posList.get("Formula_H");
                        int[] posRow = getPosRow(oldFormulaHPosInfo);
                        String[] posCol = getPosCol(oldFormulaHPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newFormulaHPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo formulaHCellStyle = new CellStyleInfo(
                                String.format("%s", newFormulaHPosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(formulaHCellStyle);
                        SheetPosValue formulaHCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(statusChangeRefInfo.getStatusChangeFFormula()));
                        cellValueSet.doOneCellValue(formulaHCellVal);
                    }

                    // 写入Condition_I
                    {
                        String oldConditionIPosInfo = posList.get("Condition_I");
                        int[] posRow = getPosRow(oldConditionIPosInfo);
                        String[] posCol = getPosCol(oldConditionIPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_0_0 = posCol[0] + String.valueOf(posRow[0] + startRowCount);
                        String newConditionIPosInfo_1 = pos_0 + ":" + pos_0_0;

                        CellStyleInfo conditionICellStyle_1 = new CellStyleInfo(
                                String.format("%s", newConditionIPosInfo_1),
                                "Meiryo UI", 11, false, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(conditionICellStyle_1);

                        HashMap<String, String> conditionI = statusChangeRefInfo.getStatusChangeFCondition();
                        Set<String> conditionIKey = conditionI.keySet();
                        if (conditionIKey.size() != 0) {
                            int index = 0;
                            for (String key : conditionIKey) {
                                String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                                SheetPosValue conditionICellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue(key));
                                cellValueSet.doOneCellValue(conditionICellVal_1);

                                String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                                String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + index + (startRowCount - rowCount));
                                String newConditionIPosInfo_2 = pos_0_new + ":" + pos_1;

                                CellStyleInfo conditionICellStyle_2 = new CellStyleInfo(
                                        String.format("%s", newConditionIPosInfo_2),
                                        "Meiryo UI", 11, true, true,
                                        "left", "center", "thin", "thin", "thin",
                                        "thin", false, new int[]{255, 255, 255});
                                cellStyleSet.doOneCellStyle(conditionICellStyle_2);

                                SheetPosValue conditionICellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), checkCellValue(conditionI.get(key)));
                                cellValueSet.doOneCellValue(conditionICellVal_2);
                                // TODO
                                String[] fConditionData = statusChangeDropDownData.getfConditionData();
                                setDropDownList(this.sheetInfo, newConditionIPosInfo_2, fConditionData);
                                index++;
                            }
                        } else {
                            String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                            SheetPosValue conditionICellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue("A"));
                            cellValueSet.doOneCellValue(conditionICellVal_1);

                            String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                            String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + (startRowCount - rowCount));
                            String newConditionIPosInfo_2 = pos_0_new + ":" + pos_1;

                            CellStyleInfo conditionICellStyle_2 = new CellStyleInfo(
                                    String.format("%s", newConditionIPosInfo_2),
                                    "Meiryo UI", 11, true, true,
                                    "left", "center", "thin", "thin", "thin",
                                    "thin", false, new int[]{255, 255, 255});
                            cellStyleSet.doOneCellStyle(conditionICellStyle_2);

                            SheetPosValue conditionICellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), "-");
                            cellValueSet.doOneCellValue(conditionICellVal_2);
                            // TODO
                            String[] fConditionData = statusChangeDropDownData.getfConditionData();
                            setDropDownList(this.sheetInfo, newConditionIPosInfo_2, fConditionData);
                        }
                    }

                    // 写入Action_N
                    {
                        String oldActionNPosInfo = posList.get("Action_N");
                        int[] posRow = getPosRow(oldActionNPosInfo);
                        String[] posCol = getPosCol(oldActionNPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newActionNPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo actionNCellStyle = new CellStyleInfo(
                                String.format("%s", newActionNPosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(actionNCellStyle);
                        SheetPosValue actionNCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(statusChangeRefInfo.getStatusChangeFAction()));
                        cellValueSet.doOneCellValue(actionNCellVal);
                        // TODO
                        String[] fActionData = statusChangeDropDownData.getfActionData();
                        setDropDownList(this.sheetInfo, newActionNPosInfo, fActionData);
                    }

                    // 写入Transition_R
                    {
//                        String oldTransitionRPosInfo = posList.get("Transition_R");
//                        int[] posRow = getPosRow(oldTransitionRPosInfo);
//                        String[] posCol = getPosCol(oldTransitionRPosInfo);
//                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
//                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
//                        String newTransitionRPosInfo = pos_0 + ":" + pos_1;
//
//                        CellStyleInfo transitionRCellStyle = new CellStyleInfo(
//                                String.format("%s", newTransitionRPosInfo),
//                                "Meiryo UI", 11, true, true,
//                                "left", "center", "thin", "thin", "thin",
//                                "thin", false, new int[]{255, 255, 255});
//                        cellStyleSet.doOneCellStyle(transitionRCellStyle);
//                        SheetPosValue transitionRCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(statusChangeRefInfo.getStatusChangeFTrans()));
//                        cellValueSet.doOneCellValue(transitionRCellVal);
//                        setDropDownList(this.sheetInfo, newTransitionRPosInfo);
                    }
                }

                // 写入Back from
                {
                    // 写入Formula_T
                    {
                        String oldFormulaTPosInfo = posList.get("Formula_T");
                        int[] posRow = getPosRow(oldFormulaTPosInfo);
                        String[] posCol = getPosCol(oldFormulaTPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newFormulaTPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo formulaTCellStyle = new CellStyleInfo(
                                String.format("%s", newFormulaTPosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(formulaTCellStyle);
                        SheetPosValue formulaTCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(statusChangeRefInfo.getStatusChangeBFormula()));
                        cellValueSet.doOneCellValue(formulaTCellVal);
                    }

                    // 写入Condition_U
                    {
                        String oldConditionUPosInfo = posList.get("Condition_U");
                        int[] posRow = getPosRow(oldConditionUPosInfo);
                        String[] posCol = getPosCol(oldConditionUPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_0_0 = posCol[0] + String.valueOf(posRow[0] + startRowCount);
                        String newConditionUPosInfo_1 = pos_0 + ":" + pos_0_0;

                        CellStyleInfo conditionUCellStyle_1 = new CellStyleInfo(
                                String.format("%s", newConditionUPosInfo_1),
                                "Meiryo UI", 11, false, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(conditionUCellStyle_1);

                        HashMap<String, String> conditionU = statusChangeRefInfo.getStatusChangeBCondition();
                        Set<String> conditionUKey = conditionU.keySet();
                        if (conditionUKey.size() != 0) {
                            int index = 0;
                            for (String key : conditionUKey) {
                                String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                                SheetPosValue conditionUCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue(key));
                                cellValueSet.doOneCellValue(conditionUCellVal_1);

                                String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                                String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + index + (startRowCount - rowCount));
                                String newConditionUPosInfo_2 = pos_0_new + ":" + pos_1;

                                CellStyleInfo conditionUCellStyle_2 = new CellStyleInfo(
                                        String.format("%s", newConditionUPosInfo_2),
                                        "Meiryo UI", 11, true, true,
                                        "left", "center", "thin", "thin", "thin",
                                        "thin", false, new int[]{255, 255, 255});
                                cellStyleSet.doOneCellStyle(conditionUCellStyle_2);

                                SheetPosValue conditionUCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), checkCellValue(conditionU.get(key)));
                                cellValueSet.doOneCellValue(conditionUCellVal_2);
                                // TODO
                                String[] bConditionData = statusChangeDropDownData.getbConditionData();
                                setDropDownList(this.sheetInfo, newConditionUPosInfo_2, bConditionData);
                                index++;
                            }
                        } else {
                            String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                            SheetPosValue conditionUCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue("A"));
                            cellValueSet.doOneCellValue(conditionUCellVal_1);

                            String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                            String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + (startRowCount - rowCount));
                            String newConditionUPosInfo_2 = pos_0_new + ":" + pos_1;

                            CellStyleInfo conditionUCellStyle_2 = new CellStyleInfo(
                                    String.format("%s", newConditionUPosInfo_2),
                                    "Meiryo UI", 11, true, true,
                                    "left", "center", "thin", "thin", "thin",
                                    "thin", false, new int[]{255, 255, 255});
                            cellStyleSet.doOneCellStyle(conditionUCellStyle_2);

                            SheetPosValue conditionUCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), "-");
                            cellValueSet.doOneCellValue(conditionUCellVal_2);
                            // TODO
                            String[] bConditionData = statusChangeDropDownData.getbConditionData();
                            setDropDownList(this.sheetInfo, newConditionUPosInfo_2, bConditionData);
                        }
                    }

                    // 写入Action_Z
                    {
                        String oldActionZPosInfo = posList.get("Action_Z");
                        int[] posRow = getPosRow(oldActionZPosInfo);
                        String[] posCol = getPosCol(oldActionZPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newActionZPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo actionZCellStyle = new CellStyleInfo(
                                String.format("%s", newActionZPosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(actionZCellStyle);
                        SheetPosValue actionZCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(statusChangeRefInfo.getStatusChangeBAction()));
                        cellValueSet.doOneCellValue(actionZCellVal);
                        // TODO
                        String[] bActionData = statusChangeDropDownData.getbActionData();
                        setDropDownList(this.sheetInfo, newActionZPosInfo, bActionData);
                    }

                    // 写入Transition_AD
                    {
//                        String oldTransitionADPosInfo = posList.get("Transition_AD");
//                        int[] posRow = getPosRow(oldTransitionADPosInfo);
//                        String[] posCol = getPosCol(oldTransitionADPosInfo);
//                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
//                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
//                        String newTransitionADPosInfo = pos_0 + ":" + pos_1;
//
//                        CellStyleInfo transitionADCellStyle = new CellStyleInfo(
//                                String.format("%s", newTransitionADPosInfo),
//                                "Meiryo UI", 11, true, true,
//                                "left", "center", "thin", "thin", "thin",
//                                "thin", false, new int[]{255, 255, 255});
//                        cellStyleSet.doOneCellStyle(transitionADCellStyle);
//                        SheetPosValue transitionADCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(statusChangeRefInfo.getStatusChangeBTrans()));
//                        cellValueSet.doOneCellValue(transitionADCellVal);
//                        setDropDownList(this.sheetInfo, newTransitionADPosInfo);
                    }
                }

                // 写入Interrupt
                {
                    // 写入Formula_AF
                    {
                        String oldFormulaAFPosInfo = posList.get("Formula_AF");
                        int[] posRow = getPosRow(oldFormulaAFPosInfo);
                        String[] posCol = getPosCol(oldFormulaAFPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newFormulaAFPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo formulaAFCellStyle = new CellStyleInfo(
                                String.format("%s", newFormulaAFPosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(formulaAFCellStyle);
                        SheetPosValue formulaAFCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(statusChangeRefInfo.getStatusChangeIFormula()));
                        cellValueSet.doOneCellValue(formulaAFCellVal);
                    }

                    // 写入Condition_AG
                    {
                        String oldConditionAGPosInfo = posList.get("Condition_AG");
                        int[] posRow = getPosRow(oldConditionAGPosInfo);
                        String[] posCol = getPosCol(oldConditionAGPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_0_0 = posCol[0] + String.valueOf(posRow[0] + startRowCount);
                        String newConditionAGPosInfo_1 = pos_0 + ":" + pos_0_0;

                        CellStyleInfo conditionAGCellStyle_1 = new CellStyleInfo(
                                String.format("%s", newConditionAGPosInfo_1),
                                "Meiryo UI", 11, false, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(conditionAGCellStyle_1);

                        HashMap<String, String> conditionAG = statusChangeRefInfo.getStatusChangeICondition();
                        Set<String> conditionAGKey = conditionAG.keySet();
                        if (conditionAGKey.size() != 0) {
                            int index = 0;
                            for (String key : conditionAGKey) {
                                String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                                SheetPosValue conditionAGCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue(key));
                                cellValueSet.doOneCellValue(conditionAGCellVal_1);

                                String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                                String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + index + (startRowCount - rowCount));
                                String newConditionAGPosInfo_2 = pos_0_new + ":" + pos_1;

                                CellStyleInfo conditionAGCellStyle_2 = new CellStyleInfo(
                                        String.format("%s", newConditionAGPosInfo_2),
                                        "Meiryo UI", 11, true, true,
                                        "left", "center", "thin", "thin", "thin",
                                        "thin", false, new int[]{255, 255, 255});
                                cellStyleSet.doOneCellStyle(conditionAGCellStyle_2);

                                SheetPosValue conditionAGCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), checkCellValue(conditionAG.get(key)));
                                cellValueSet.doOneCellValue(conditionAGCellVal_2);
                                // TODO
                                String[] iConditionData = statusChangeDropDownData.getiConditionData();
                                setDropDownList(this.sheetInfo, newConditionAGPosInfo_2, iConditionData);
                                index++;
                            }
                        } else {
                            String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                            SheetPosValue conditionAGCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue("A"));
                            cellValueSet.doOneCellValue(conditionAGCellVal_1);

                            String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                            String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + (startRowCount - rowCount));
                            String newConditionAGPosInfo_2 = pos_0_new + ":" + pos_1;

                            CellStyleInfo conditionAGCellStyle_2 = new CellStyleInfo(
                                    String.format("%s", newConditionAGPosInfo_2),
                                    "Meiryo UI", 11, true, true,
                                    "left", "center", "thin", "thin", "thin",
                                    "thin", false, new int[]{255, 255, 255});
                            cellStyleSet.doOneCellStyle(conditionAGCellStyle_2);

                            SheetPosValue conditionAGCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), "-");
                            cellValueSet.doOneCellValue(conditionAGCellVal_2);
                            // TODO
                            String[] iConditionData = statusChangeDropDownData.getiConditionData();
                            setDropDownList(this.sheetInfo, newConditionAGPosInfo_2, iConditionData);
                        }
                    }

                    // 写入Action_AL
                    {
                        String oldActionALPosInfo = posList.get("Action_AL");
                        int[] posRow = getPosRow(oldActionALPosInfo);
                        String[] posCol = getPosCol(oldActionALPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newActionALPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo actionALCellStyle = new CellStyleInfo(
                                String.format("%s", newActionALPosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(actionALCellStyle);
                        SheetPosValue actionALCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(statusChangeRefInfo.getStatusChangeIAction()));
                        cellValueSet.doOneCellValue(actionALCellVal);
                        // TODO
                        String[] iActionData = statusChangeDropDownData.getiActionData();
                        setDropDownList(this.sheetInfo, newActionALPosInfo, iActionData);
                    }

                    // 写入Transition_AQ
                    {
//                        String oldTransitionAQPosInfo = posList.get("Transition_AQ");
//                        int[] posRow = getPosRow(oldTransitionAQPosInfo);
//                        String[] posCol = getPosCol(oldTransitionAQPosInfo);
//                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
//                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
//                        String newTransitionAQPosInfo = pos_0 + ":" + pos_1;
//
//                        CellStyleInfo transitionAQCellStyle = new CellStyleInfo(
//                                String.format("%s", newTransitionAQPosInfo),
//                                "Meiryo UI", 11, true, true,
//                                "left", "center", "thin", "thin", "thin",
//                                "thin", false, new int[]{255, 255, 255});
//                        cellStyleSet.doOneCellStyle(transitionAQCellStyle);
//                        SheetPosValue transitionAQCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(statusChangeRefInfo.getStatusChangeITrans()));
//                        cellValueSet.doOneCellValue(transitionAQCellVal);
//                        setDropDownList(this.sheetInfo, newTransitionAQPosInfo);
                    }
                }

                // 写入UUID
                {
                    String oldUUIDPosInfo = posList.get("UUID");
                    int[] posRow = getPosRow(oldUUIDPosInfo);
                    String[] posCol = getPosCol(oldUUIDPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newUUIDPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo UUIDCellStyle = new CellStyleInfo(
                            String.format("%s", newUUIDPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(UUIDCellStyle);
                    SheetPosValue UUIDCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(jsonSketchInfo.getSketchInfoId() + "->" + jsonPartInfo.getUuid()));
                    cellValueSet.doOneCellValue(UUIDCellVal);
                }

                // 写入Event
                {
                    String oldEventPosInfo = posList.get("Event");
                    int[] posRow = getPosRow(oldEventPosInfo);
                    String[] posCol = getPosCol(oldEventPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newEventPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo eventCellStyle = new CellStyleInfo(
                            String.format("%s", newEventPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(eventCellStyle);
                    SheetPosValue eventCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(statusChangeRefInfo.getStatusChangeEvent()));
                    cellValueSet.doOneCellValue(eventCellVal);
                }

                // 写入Forward from
                {
                    // 写入View Model_AY
                    {
                        String oldViewModelAYPosInfo = posList.get("View Model_AY");
                        int[] posRow = getPosRow(oldViewModelAYPosInfo);
                        String[] posCol = getPosCol(oldViewModelAYPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_0_0 = posCol[0] + String.valueOf(posRow[0] + startRowCount);
                        String newViewModelAYPosInfo_1 = pos_0 + ":" + pos_0_0;

                        CellStyleInfo viewModelAYCellStyle_1 = new CellStyleInfo(
                                String.format("%s", newViewModelAYPosInfo_1),
                                "Meiryo UI", 11, false, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(viewModelAYCellStyle_1);

                        HashMap<String, String> viewModelAY = statusChangeRefInfo.getStatusChangeFConditionModel();
                        Set<String> viewModelAYKey = viewModelAY.keySet();
                        if (viewModelAYKey.size() != 0) {
                            int index = 0;
                            for (String key : viewModelAYKey) {
                                String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                                SheetPosValue viewModelAYCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue(key));
                                cellValueSet.doOneCellValue(viewModelAYCellVal_1);

                                String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                                String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + index + (startRowCount - rowCount));
                                String newViewModelAYPosInfo_2 = pos_0_new + ":" + pos_1;

                                CellStyleInfo viewModelAYCellStyle_2 = new CellStyleInfo(
                                        String.format("%s", newViewModelAYPosInfo_2),
                                        "Meiryo UI", 11, true, true,
                                        "left", "center", "thin", "thin", "thin",
                                        "thin", false, new int[]{255, 255, 255});
                                cellStyleSet.doOneCellStyle(viewModelAYCellStyle_2);

                                SheetPosValue viewModelAYCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), checkCellValue(viewModelAY.get(key)));
                                cellValueSet.doOneCellValue(viewModelAYCellVal_2);
                                // TODO
                                int row = getPosRowSingle(pos_0_new);
                                String col = getPosColSingle(pos_0_new);
                                int iCol = excelColStrToNum(col, col.length());
                                String formulaString = statusChangeDropDownData.getfViewModelDataFormulaString(row);
                                setLinkage(row, iCol, formulaString);
                                index++;
                            }
                        } else {
                            String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                            SheetPosValue viewModelAYCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue("A"));
                            cellValueSet.doOneCellValue(viewModelAYCellVal_1);

                            String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                            String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + (startRowCount - rowCount));
                            String newViewModelAYPosInfo_2 = pos_0_new + ":" + pos_1;

                            CellStyleInfo viewModelAYCellStyle_2 = new CellStyleInfo(
                                    String.format("%s", newViewModelAYPosInfo_2),
                                    "Meiryo UI", 11, true, true,
                                    "left", "center", "thin", "thin", "thin",
                                    "thin", false, new int[]{255, 255, 255});
                            cellStyleSet.doOneCellStyle(viewModelAYCellStyle_2);

                            SheetPosValue viewModelAYCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), "-");
                            cellValueSet.doOneCellValue(viewModelAYCellVal_2);
                            // TODO
                            int row = getPosRowSingle(pos_0_new);
                            String col = getPosColSingle(pos_0_new);
                            int iCol = excelColStrToNum(col, col.length());
                            String formulaString = statusChangeDropDownData.getfViewModelDataFormulaString(row);
                            setLinkage(row, iCol, formulaString);
                        }
                    }

                    // 写入Func of Model_BD
                    {
                        String oldFuncModelBDPosInfo = posList.get("Func of Model_BD");
                        int[] posRow = getPosRow(oldFuncModelBDPosInfo);
                        String[] posCol = getPosCol(oldFuncModelBDPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newFuncModelBDPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo funcModelBDCellStyle = new CellStyleInfo(
                                String.format("%s", newFuncModelBDPosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(funcModelBDCellStyle);
                        SheetPosValue funcModelBDCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(statusChangeRefInfo.getStatusChangeFActionModel()));
                        cellValueSet.doOneCellValue(funcModelBDCellVal);
                        // TODO
                        int row = getPosRowSingle(pos_0);
                        String col = getPosColSingle(pos_0);
                        int iCol = excelColStrToNum(col, col.length());
                        String formulaString = statusChangeDropDownData.getfFuncOfModelDataFormulaString(row);
                        setLinkage(row, iCol, formulaString);
                    }

                    // 写入Observer_BG
                    {
                        String oldObserverBGPosInfo = posList.get("Observer_BG");
                        int[] posRow = getPosRow(oldObserverBGPosInfo);
                        String[] posCol = getPosCol(oldObserverBGPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newObserverBGPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo observerBGCellStyle = new CellStyleInfo(
                                String.format("%s", newObserverBGPosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(observerBGCellStyle);
                        SheetPosValue observerBGCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(statusChangeRefInfo.getStatusChangeFObserver()));
                        cellValueSet.doOneCellValue(observerBGCellVal);
                    }

                    // 写入Reply_BJ
                    {
                        String oldReplyBJPosInfo = posList.get("Reply_BJ");
                        int[] posRow = getPosRow(oldReplyBJPosInfo);
                        String[] posCol = getPosCol(oldReplyBJPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newReplyBJPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo replyBGCellStyle = new CellStyleInfo(
                                String.format("%s", newReplyBJPosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(replyBGCellStyle);
                        SheetPosValue replyBGCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(statusChangeRefInfo.getStatusChangeFReply()));
                        cellValueSet.doOneCellValue(replyBGCellVal);
                    }
                }

                // 写入Back from
                {
                    // 写入View Model_BN
                    {
                        String oldViewModelBNPosInfo = posList.get("View Model_BN");
                        int[] posRow = getPosRow(oldViewModelBNPosInfo);
                        String[] posCol = getPosCol(oldViewModelBNPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_0_0 = posCol[0] + String.valueOf(posRow[0] + startRowCount);
                        String newViewModelBNPosInfo_1 = pos_0 + ":" + pos_0_0;

                        CellStyleInfo viewModelBNCellStyle_1 = new CellStyleInfo(
                                String.format("%s", newViewModelBNPosInfo_1),
                                "Meiryo UI", 11, false, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(viewModelBNCellStyle_1);

                        HashMap<String, String> viewModelBN = statusChangeRefInfo.getStatusChangeBConditionModel();
                        Set<String> viewModelBNKey = viewModelBN.keySet();
                        if (viewModelBNKey.size() != 0) {
                            int index = 0;
                            for (String key : viewModelBNKey) {
                                String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                                SheetPosValue viewModelBNCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue(key));
                                cellValueSet.doOneCellValue(viewModelBNCellVal_1);

                                String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                                String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + index + (startRowCount - rowCount));
                                String newViewModelBNPosInfo_2 = pos_0_new + ":" + pos_1;

                                CellStyleInfo viewModelBNCellStyle_2 = new CellStyleInfo(
                                        String.format("%s", newViewModelBNPosInfo_2),
                                        "Meiryo UI", 11, true, true,
                                        "left", "center", "thin", "thin", "thin",
                                        "thin", false, new int[]{255, 255, 255});
                                cellStyleSet.doOneCellStyle(viewModelBNCellStyle_2);

                                SheetPosValue viewModelBNCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), checkCellValue(viewModelBN.get(key)));
                                cellValueSet.doOneCellValue(viewModelBNCellVal_2);
                                // TODO
                                int row = getPosRowSingle(pos_0_new);
                                String col = getPosColSingle(pos_0_new);
                                int iCol = excelColStrToNum(col, col.length());
                                String formulaString = statusChangeDropDownData.getbViewModelDataFormulaString(row);
                                setLinkage(row, iCol, formulaString);
                                index++;
                            }
                        } else {
                            String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                            SheetPosValue viewModelBNCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue("A"));
                            cellValueSet.doOneCellValue(viewModelBNCellVal_1);

                            String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                            String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + (startRowCount - rowCount));
                            String newViewModelBNPosInfo_2 = pos_0_new + ":" + pos_1;

                            CellStyleInfo viewModelBNCellStyle_2 = new CellStyleInfo(
                                    String.format("%s", newViewModelBNPosInfo_2),
                                    "Meiryo UI", 11, true, true,
                                    "left", "center", "thin", "thin", "thin",
                                    "thin", false, new int[]{255, 255, 255});
                            cellStyleSet.doOneCellStyle(viewModelBNCellStyle_2);

                            SheetPosValue viewModelBNCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), "-");
                            cellValueSet.doOneCellValue(viewModelBNCellVal_2);
                            // TODO
                            int row = getPosRowSingle(pos_0_new);
                            String col = getPosColSingle(pos_0_new);
                            int iCol = excelColStrToNum(col, col.length());
                            String formulaString = statusChangeDropDownData.getbViewModelDataFormulaString(row);
                            setLinkage(row, iCol, formulaString);
                        }
                    }

                    // 写入Func of Model_BS
                    {
                        String oldFuncModelBSPosInfo = posList.get("Func of Model_BS");
                        int[] posRow = getPosRow(oldFuncModelBSPosInfo);
                        String[] posCol = getPosCol(oldFuncModelBSPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newFuncModelBSPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo funcModelBSCellStyle = new CellStyleInfo(
                                String.format("%s", newFuncModelBSPosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(funcModelBSCellStyle);
                        SheetPosValue funcModelBSCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(statusChangeRefInfo.getStatusChangeBActionModel()));
                        cellValueSet.doOneCellValue(funcModelBSCellVal);
                        // TODO
                        int row = getPosRowSingle(pos_0);
                        String col = getPosColSingle(pos_0);
                        int iCol = excelColStrToNum(col, col.length());
                        String formulaString = statusChangeDropDownData.getbFuncOfModelDataFormulaString(row);
                        setLinkage(row, iCol, formulaString);
                    }

                    // 写入Observer_BV
                    {
                        String oldObserverBVPosInfo = posList.get("Observer_BV");
                        int[] posRow = getPosRow(oldObserverBVPosInfo);
                        String[] posCol = getPosCol(oldObserverBVPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newObserverBVPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo observerBVCellStyle = new CellStyleInfo(
                                String.format("%s", newObserverBVPosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(observerBVCellStyle);
                        SheetPosValue observerBVCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(statusChangeRefInfo.getStatusChangeBObserver()));
                        cellValueSet.doOneCellValue(observerBVCellVal);
                    }

                    // 写入Reply_BY
                    {
                        String oldReplyBYPosInfo = posList.get("Reply_BY");
                        int[] posRow = getPosRow(oldReplyBYPosInfo);
                        String[] posCol = getPosCol(oldReplyBYPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newReplyBYPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo replyBYCellStyle = new CellStyleInfo(
                                String.format("%s", newReplyBYPosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(replyBYCellStyle);
                        SheetPosValue replyBYCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(statusChangeRefInfo.getStatusChangeBReply()));
                        cellValueSet.doOneCellValue(replyBYCellVal);
                    }
                }

                // 写入Interrupt
                {
                    // 写入View Model_CC
                    {
                        String oldViewModelCCPosInfo = posList.get("View Model_CC");
                        int[] posRow = getPosRow(oldViewModelCCPosInfo);
                        String[] posCol = getPosCol(oldViewModelCCPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_0_0 = posCol[0] + String.valueOf(posRow[0] + startRowCount);
                        String newViewModelCCPosInfo_1 = pos_0 + ":" + pos_0_0;

                        CellStyleInfo viewModelCCCellStyle_1 = new CellStyleInfo(
                                String.format("%s", newViewModelCCPosInfo_1),
                                "Meiryo UI", 11, false, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(viewModelCCCellStyle_1);

                        HashMap<String, String> viewModelCC = statusChangeRefInfo.getStatusChangeIConditionModel();
                        Set<String> viewModelCCKey = viewModelCC.keySet();
                        if (viewModelCCKey.size() != 0) {
                            int index = 0;
                            for (String key : viewModelCCKey) {
                                String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                                SheetPosValue viewModelCCCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue(key));
                                cellValueSet.doOneCellValue(viewModelCCCellVal_1);

                                String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                                String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + index + (startRowCount - rowCount));
                                String newViewModelCCPosInfo_2 = pos_0_new + ":" + pos_1;

                                CellStyleInfo viewModelCCCellStyle_2 = new CellStyleInfo(
                                        String.format("%s", newViewModelCCPosInfo_2),
                                        "Meiryo UI", 11, true, true,
                                        "left", "center", "thin", "thin", "thin",
                                        "thin", false, new int[]{255, 255, 255});
                                cellStyleSet.doOneCellStyle(viewModelCCCellStyle_2);

                                SheetPosValue viewModelCCCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), checkCellValue(viewModelCC.get(key)));
                                cellValueSet.doOneCellValue(viewModelCCCellVal_2);
                                // TODO
                                int row = getPosRowSingle(pos_0_new);
                                String col = getPosColSingle(pos_0_new);
                                int iCol = excelColStrToNum(col, col.length());
                                String formulaString = statusChangeDropDownData.getiViewModelDataFormulaString(row);
                                setLinkage(row, iCol, formulaString);
                                index++;
                            }
                        } else {
                            String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                            SheetPosValue viewModelCCCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue("A"));
                            cellValueSet.doOneCellValue(viewModelCCCellVal_1);

                            String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                            String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + (startRowCount - rowCount));
                            String newViewModelCCPosInfo_2 = pos_0_new + ":" + pos_1;

                            CellStyleInfo viewModelCCCellStyle_2 = new CellStyleInfo(
                                    String.format("%s", newViewModelCCPosInfo_2),
                                    "Meiryo UI", 11, true, true,
                                    "left", "center", "thin", "thin", "thin",
                                    "thin", false, new int[]{255, 255, 255});
                            cellStyleSet.doOneCellStyle(viewModelCCCellStyle_2);

                            SheetPosValue viewModelCCCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), "-");
                            cellValueSet.doOneCellValue(viewModelCCCellVal_2);
                            // TODO
                            int row = getPosRowSingle(pos_0_new);
                            String col = getPosColSingle(pos_0_new);
                            int iCol = excelColStrToNum(col, col.length());
                            String formulaString = statusChangeDropDownData.getiViewModelDataFormulaString(row);
                            setLinkage(row, iCol, formulaString);
                        }
                    }

                    // 写入Func of Model_CH
                    {
                        String oldFuncModelCHPosInfo = posList.get("Func of Model_CH");
                        int[] posRow = getPosRow(oldFuncModelCHPosInfo);
                        String[] posCol = getPosCol(oldFuncModelCHPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newFuncModelCHPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo funcModelCHCellStyle = new CellStyleInfo(
                                String.format("%s", newFuncModelCHPosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(funcModelCHCellStyle);
                        SheetPosValue funcModelCHCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(statusChangeRefInfo.getStatusChangeIActionModel()));
                        cellValueSet.doOneCellValue(funcModelCHCellVal);
                        // TODO
                        int row = getPosRowSingle(pos_0);
                        String col = getPosColSingle(pos_0);
                        int iCol = excelColStrToNum(col, col.length());
                        String formulaString = statusChangeDropDownData.getiFuncOfModelDataFormulaString(row);
                        setLinkage(row, iCol, formulaString);
                    }

                    // 写入Observer_CK
                    {
                        String oldObserverCKPosInfo = posList.get("Observer_CK");
                        int[] posRow = getPosRow(oldObserverCKPosInfo);
                        String[] posCol = getPosCol(oldObserverCKPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newObserverCKPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo observerCKCellStyle = new CellStyleInfo(
                                String.format("%s", newObserverCKPosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(observerCKCellStyle);
                        SheetPosValue observerCKCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(statusChangeRefInfo.getStatusChangeIObserver()));
                        cellValueSet.doOneCellValue(observerCKCellVal);
                    }

                    // 写入Reply_CN
                    {
                        String oldReplyCNPosInfo = posList.get("Reply_CN");
                        int[] posRow = getPosRow(oldReplyCNPosInfo);
                        String[] posCol = getPosCol(oldReplyCNPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newReplyCNPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo replyCNCellStyle = new CellStyleInfo(
                                String.format("%s", newReplyCNPosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(replyCNCellStyle);
                        SheetPosValue replyCNCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(statusChangeRefInfo.getStatusChangeIReply()));
                        cellValueSet.doOneCellValue(replyCNCellVal);
                    }
                }
            }
        }

        StartRowIndex += 3 + startRowCount + DefaultTableSpace;
        // StatusChangeRefInfoStr
        {
            String statusChangeRefInfoPosInfo1= "B" + String.valueOf(StartRowIndex - 1) + ":" + "B" + String.valueOf(StartRowIndex - 1);
            CellStyleInfo statusChangeRefInfoCellStyle = new CellStyleInfo(
                    String.format("%s", statusChangeRefInfoPosInfo1),
                    "Meiryo UI", 11, false, true,
                    "left", "center", "", "thin", "thin",
                    "thin", false, new int[]{255, 255, 255});
            cellStyleSet.doOneCellStyle(statusChangeRefInfoCellStyle);
            String statusRefInfoPosInfo2 = "C" + String.valueOf(StartRowIndex - 1);
            SheetPosValue statusChangeRefInfoCellVal = new SheetPosValue(String.format("%s", statusRefInfoPosInfo2), checkCellValue(jsonSketchInfo.getStatusChangeRefInfoStr()));
            cellValueSet.doOneCellValue(statusChangeRefInfoCellVal);
        }

        return StartRowIndex;
    }

    private int doExportTransitionRefInfo(JsonSketchInfo_new jsonSketchInfo, int StartRowIndex) throws Exception {
        // 表头设置
        HMIPartsSpecStatic_new staticInfo = new HMIPartsSpecStatic_new();

        CellStyleSet cellStyleSet = new CellStyleSet(this.workBook, this.sheetInfo);
        ArrayList<CellStyleInfo> styleList = getNewStyleList(staticInfo.styleLists.get("TransitionRefInfo"), StartRowIndex, 7);
        cellStyleSet.doCellStyleList(styleList);

        CellValueSet cellValueSet = new CellValueSet(this.workBook, this.sheetInfo);
        ArrayList<SheetPosValue> lableList = getNewLableList(staticInfo.lableLists.get("TransitionRefInfo"), StartRowIndex, 7);
        cellValueSet.doCellValueList(lableList);

        HashMap<String, String> posList = getPosList(lableList, styleList, 7);
        ArrayList<JsonPartInfo_new> jsonPartInfoList = sortByNo(jsonSketchInfo.getJsonPartInfoList(), 7);
        HMIDropDownData dropDownData = HMIDropDownData.getInstance();
        HMIDropDownData.TransitionDropDownData transitionDropDownData = dropDownData.getTransitionDropDownDataInstance();
        int startRowCount = 0;
        for (int k = 0; k < jsonPartInfoList.size(); ++k) {
            JsonPartInfo_new jsonPartInfo = jsonPartInfoList.get(k);
            JsonTransitionRefInfo transitionRefInfo = jsonPartInfo.getTransitionRefInfo();
            if (checkTransitionRefInfo(transitionRefInfo)) {
                int rowCount = getTransitionRefInfoRowCount(transitionRefInfo);
                startRowCount += rowCount;
                boolean isMergeRow = isMergeRow(rowCount);

                // 写入Chapter
                {
                    String oldChapterPosInfo = posList.get("Chapter");
                    int[] posRow = getPosRow(oldChapterPosInfo);
                    String[] posCol = getPosCol(oldChapterPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newChapterPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo chapterCellStyle = new CellStyleInfo(
                            String.format("%s", newChapterPosInfo),
                            "Meiryo UI", 11, isMergeRow, true,
                            "center", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{201,255,255});
                    cellStyleSet.doOneCellStyle(chapterCellStyle);
                    SheetPosValue chapterCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(transitionRefInfo.getTransitionChapter()));
                    cellValueSet.doOneCellValue(chapterCellVal);
                }

                // 写入SubChapter
                {
                    String oldSubChapterPosInfo = posList.get("SubChapter");
                    int[] posRow = getPosRow(oldSubChapterPosInfo);
                    String[] posCol = getPosCol(oldSubChapterPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newSubChapterPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo subChapterCellStyle = new CellStyleInfo(
                            String.format("%s", newSubChapterPosInfo),
                            "Meiryo UI", 11, isMergeRow, true,
                            "center", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255,205,156});
                    cellStyleSet.doOneCellStyle(subChapterCellStyle);
                    SheetPosValue subChapterCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(transitionRefInfo.getTransitionSubChapter()));
                    cellValueSet.doOneCellValue(subChapterCellVal);
                }

                // 写入Normal
                {
                    String oldNormalPosInfo = posList.get("Normal");
                    int[] posRow = getPosRow(oldNormalPosInfo);
                    String[] posCol = getPosCol(oldNormalPosInfo);
                    // 写入NO.
                    {
                        String pos_0 = getNewCol(posCol[0], 0) + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = getNewCol(posCol[0], 0) + String.valueOf(posRow[0] + startRowCount);
                        String newNOPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo noCellStyle = new CellStyleInfo(
                                String.format("%s", newNOPosInfo),
                                "Meiryo UI", 11, isMergeRow, true,
                                "center", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255,205,156});
                        cellStyleSet.doOneCellStyle(noCellStyle);
                        SheetPosValue noCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(transitionRefInfo.getTransitionNo()));
                        cellValueSet.doOneCellValue(noCellVal);
                    }

                    // 写入StateNo
                    {
                        String pos_0 = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + startRowCount);
                        String newStateNoPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo stateNoCellStyle = new CellStyleInfo(
                                String.format("%s", newStateNoPosInfo),
                                "Meiryo UI", 11, isMergeRow, true,
                                "center", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{204,255,204});
                        cellStyleSet.doOneCellStyle(stateNoCellStyle);
                        SheetPosValue stateNoCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(transitionRefInfo.getTransitionStateNo()));
                        cellValueSet.doOneCellValue(stateNoCellVal);
                    }

                    // 写入Name
                    {
                        String pos_0 = getNewCol(posCol[0], 2) + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newNamePosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo nameCellStyle = new CellStyleInfo(
                                String.format("%s", newNamePosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{204,255,204});
                        cellStyleSet.doOneCellStyle(nameCellStyle);
                        SheetPosValue nameCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(transitionRefInfo.getTransitionName()));
                        cellValueSet.doOneCellValue(nameCellVal);
                    }
                }

                // 写入Back to Other Screen
                {
                    // 写入Formula_H
                    {
                        String oldFormulaHPosInfo = posList.get("Formula_H");
                        int[] posRow = getPosRow(oldFormulaHPosInfo);
                        String[] posCol = getPosCol(oldFormulaHPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newFormulaHPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo formulaHCellStyle = new CellStyleInfo(
                                String.format("%s", newFormulaHPosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(formulaHCellStyle);
                        SheetPosValue formulaHCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(transitionRefInfo.getTransitionBFormula()));
                        cellValueSet.doOneCellValue(formulaHCellVal);
                    }

                    // 写入Condition_I
                    {
                        String oldConditionIPosInfo = posList.get("Condition_I");
                        int[] posRow = getPosRow(oldConditionIPosInfo);
                        String[] posCol = getPosCol(oldConditionIPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_0_0 = posCol[0] + String.valueOf(posRow[0] + startRowCount);
                        String newConditionIPosInfo_1 = pos_0 + ":" + pos_0_0;

                        CellStyleInfo conditionICellStyle_1 = new CellStyleInfo(
                                String.format("%s", newConditionIPosInfo_1),
                                "Meiryo UI", 11, false, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(conditionICellStyle_1);

                        HashMap<String, String> conditionI = transitionRefInfo.getTransitionBCondition();
                        Set<String> conditionIKey = conditionI.keySet();
                        if (conditionIKey.size() != 0) {
                            int index = 0;
                            for (String key : conditionIKey) {
                                String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                                SheetPosValue conditionICellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue(key));
                                cellValueSet.doOneCellValue(conditionICellVal_1);

                                String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                                String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + index + (startRowCount - rowCount));
                                String newConditionIPosInfo_2 = pos_0_new + ":" + pos_1;

                                CellStyleInfo conditionICellStyle_2 = new CellStyleInfo(
                                        String.format("%s", newConditionIPosInfo_2),
                                        "Meiryo UI", 11, true, true,
                                        "left", "center", "thin", "thin", "thin",
                                        "thin", false, new int[]{255, 255, 255});
                                cellStyleSet.doOneCellStyle(conditionICellStyle_2);

                                SheetPosValue conditionICellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), checkCellValue(conditionI.get(key)));
                                cellValueSet.doOneCellValue(conditionICellVal_2);
                                // TODO
                                String[] bConditionData = transitionDropDownData.getbConditionData();
                                setDropDownList(this.sheetInfo, newConditionIPosInfo_2, bConditionData);
                                index++;
                            }
                        } else {
                            String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                            SheetPosValue conditionICellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue("A"));
                            cellValueSet.doOneCellValue(conditionICellVal_1);

                            String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                            String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + (startRowCount - rowCount));
                            String newConditionIPosInfo_2 = pos_0_new + ":" + pos_1;

                            CellStyleInfo conditionICellStyle_2 = new CellStyleInfo(
                                    String.format("%s", newConditionIPosInfo_2),
                                    "Meiryo UI", 11, true, true,
                                    "left", "center", "thin", "thin", "thin",
                                    "thin", false, new int[]{255, 255, 255});
                            cellStyleSet.doOneCellStyle(conditionICellStyle_2);

                            SheetPosValue conditionICellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), "-");
                            cellValueSet.doOneCellValue(conditionICellVal_2);
                            // TODO
                            String[] bConditionData = transitionDropDownData.getbConditionData();
                            setDropDownList(this.sheetInfo, newConditionIPosInfo_2, bConditionData);
                        }
                    }

                    // 写入Action_N
                    {
                        String oldActionNPosInfo = posList.get("Action_N");
                        int[] posRow = getPosRow(oldActionNPosInfo);
                        String[] posCol = getPosCol(oldActionNPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newActionNPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo actionNCellStyle = new CellStyleInfo(
                                String.format("%s", newActionNPosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(actionNCellStyle);
                        SheetPosValue actionNCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(transitionRefInfo.getTransitionBAction()));
                        cellValueSet.doOneCellValue(actionNCellVal);
                        // TODO
                        String[] bActionData = transitionDropDownData.getbActionData();
                        setDropDownList(this.sheetInfo, newActionNPosInfo, bActionData);
                    }

                    // 写入Transition_R
                    {
//                        String oldTransitionRPosInfo = posList.get("Transition_R");
//                        int[] posRow = getPosRow(oldTransitionRPosInfo);
//                        String[] posCol = getPosCol(oldTransitionRPosInfo);
//                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
//                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
//                        String newTransitionRPosInfo = pos_0 + ":" + pos_1;
//
//                        CellStyleInfo transitionRCellStyle = new CellStyleInfo(
//                                String.format("%s", newTransitionRPosInfo),
//                                "Meiryo UI", 11, true, true,
//                                "left", "center", "thin", "thin", "thin",
//                                "thin", false, new int[]{255, 255, 255});
//                        cellStyleSet.doOneCellStyle(transitionRCellStyle);
//                        SheetPosValue transitionRCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(transitionRefInfo.getTransitionBTrans()));
//                        cellValueSet.doOneCellValue(transitionRCellVal);
//                        setDropDownList(this.sheetInfo, newTransitionRPosInfo);
                    }
                }

                // 写入Forward to Other Screen
                {
                    // 写入Formula_T
                    {
                        String oldFormulaTPosInfo = posList.get("Formula_T");
                        int[] posRow = getPosRow(oldFormulaTPosInfo);
                        String[] posCol = getPosCol(oldFormulaTPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newFormulaTPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo formulaTCellStyle = new CellStyleInfo(
                                String.format("%s", newFormulaTPosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(formulaTCellStyle);
                        SheetPosValue formulaTCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(transitionRefInfo.getTransitionFFormula()));
                        cellValueSet.doOneCellValue(formulaTCellVal);
                    }

                    // 写入Condition_U
                    {
                        String oldConditionUPosInfo = posList.get("Condition_U");
                        int[] posRow = getPosRow(oldConditionUPosInfo);
                        String[] posCol = getPosCol(oldConditionUPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_0_0 = posCol[0] + String.valueOf(posRow[0] + startRowCount);
                        String newConditionUPosInfo_1 = pos_0 + ":" + pos_0_0;

                        CellStyleInfo conditionUCellStyle_1 = new CellStyleInfo(
                                String.format("%s", newConditionUPosInfo_1),
                                "Meiryo UI", 11, false, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(conditionUCellStyle_1);

                        HashMap<String, String> conditionU = transitionRefInfo.getTransitionFCondition();
                        Set<String> conditionUKey = conditionU.keySet();
                        if (conditionUKey.size() != 0) {
                            int index = 0;
                            for (String key : conditionUKey) {
                                String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                                SheetPosValue conditionUCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue(key));
                                cellValueSet.doOneCellValue(conditionUCellVal_1);

                                String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                                String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + index + (startRowCount - rowCount));
                                String newConditionUPosInfo_2 = pos_0_new + ":" + pos_1;

                                CellStyleInfo conditionUCellStyle_2 = new CellStyleInfo(
                                        String.format("%s", newConditionUPosInfo_2),
                                        "Meiryo UI", 11, true, true,
                                        "left", "center", "thin", "thin", "thin",
                                        "thin", false, new int[]{255, 255, 255});
                                cellStyleSet.doOneCellStyle(conditionUCellStyle_2);

                                SheetPosValue conditionUCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), checkCellValue(conditionU.get(key)));
                                cellValueSet.doOneCellValue(conditionUCellVal_2);
                                // TODO
                                String[] fConditionData = transitionDropDownData.getfConditionData();
                                setDropDownList(this.sheetInfo, newConditionUPosInfo_2, fConditionData);
                                index++;
                            }
                        } else {
                            String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                            SheetPosValue conditionUCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue("A"));
                            cellValueSet.doOneCellValue(conditionUCellVal_1);

                            String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                            String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + (startRowCount - rowCount));
                            String newConditionUPosInfo_2 = pos_0_new + ":" + pos_1;

                            CellStyleInfo conditionUCellStyle_2 = new CellStyleInfo(
                                    String.format("%s", newConditionUPosInfo_2),
                                    "Meiryo UI", 11, true, true,
                                    "left", "center", "thin", "thin", "thin",
                                    "thin", false, new int[]{255, 255, 255});
                            cellStyleSet.doOneCellStyle(conditionUCellStyle_2);

                            SheetPosValue conditionUCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), "-");
                            cellValueSet.doOneCellValue(conditionUCellVal_2);
                            // TODO
                            String[] fConditionData = transitionDropDownData.getfConditionData();
                            setDropDownList(this.sheetInfo, newConditionUPosInfo_2, fConditionData);
                        }
                    }

                    // 写入Action_Z
                    {
                        String oldActionZPosInfo = posList.get("Action_Z");
                        int[] posRow = getPosRow(oldActionZPosInfo);
                        String[] posCol = getPosCol(oldActionZPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newActionZPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo actionZCellStyle = new CellStyleInfo(
                                String.format("%s", newActionZPosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(actionZCellStyle);
                        SheetPosValue actionZCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(transitionRefInfo.getTransitionFAction()));
                        cellValueSet.doOneCellValue(actionZCellVal);
                        // TODO
                        String[] fActionData = transitionDropDownData.getfActionData();
                        setDropDownList(this.sheetInfo, newActionZPosInfo, fActionData);
                    }

                    // 写入Transition_AD
                    {
//                        String oldTransitionADPosInfo = posList.get("Transition_AD");
//                        int[] posRow = getPosRow(oldTransitionADPosInfo);
//                        String[] posCol = getPosCol(oldTransitionADPosInfo);
//                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
//                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
//                        String newTransitionADPosInfo = pos_0 + ":" + pos_1;
//
//                        CellStyleInfo transitionADCellStyle = new CellStyleInfo(
//                                String.format("%s", newTransitionADPosInfo),
//                                "Meiryo UI", 11, true, true,
//                                "left", "center", "thin", "thin", "thin",
//                                "thin", false, new int[]{255, 255, 255});
//                        cellStyleSet.doOneCellStyle(transitionADCellStyle);
//                        SheetPosValue transitionADCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(transitionRefInfo.getTransitionFTrans()));
//                        cellValueSet.doOneCellValue(transitionADCellVal);
//                        setDropDownList(this.sheetInfo, newTransitionADPosInfo);
                    }
                }

                // 写入Interrupt Transition
                {
                    // 写入Formula_AF
                    {
                        String oldFormulaAFPosInfo = posList.get("Formula_AF");
                        int[] posRow = getPosRow(oldFormulaAFPosInfo);
                        String[] posCol = getPosCol(oldFormulaAFPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newFormulaAFPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo formulaAFCellStyle = new CellStyleInfo(
                                String.format("%s", newFormulaAFPosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(formulaAFCellStyle);
                        SheetPosValue formulaAFCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(transitionRefInfo.getTransitionIFormula()));
                        cellValueSet.doOneCellValue(formulaAFCellVal);
                    }

                    // 写入Condition_AG
                    {
                        String oldConditionAGPosInfo = posList.get("Condition_AG");
                        int[] posRow = getPosRow(oldConditionAGPosInfo);
                        String[] posCol = getPosCol(oldConditionAGPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_0_0 = posCol[0] + String.valueOf(posRow[0] + startRowCount);
                        String newConditionAGPosInfo_1 = pos_0 + ":" + pos_0_0;

                        CellStyleInfo conditionAGCellStyle_1 = new CellStyleInfo(
                                String.format("%s", newConditionAGPosInfo_1),
                                "Meiryo UI", 11, false, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(conditionAGCellStyle_1);

                        HashMap<String, String> conditionAG = transitionRefInfo.getTransitionICondition();
                        Set<String> conditionAGKey = conditionAG.keySet();
                        if (conditionAGKey.size() != 0) {
                            int index = 0;
                            for (String key : conditionAGKey) {
                                String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                                SheetPosValue conditionAGCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue(key));
                                cellValueSet.doOneCellValue(conditionAGCellVal_1);

                                String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                                String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + index + (startRowCount - rowCount));
                                String newConditionAGPosInfo_2 = pos_0_new + ":" + pos_1;

                                CellStyleInfo conditionAGCellStyle_2 = new CellStyleInfo(
                                        String.format("%s", newConditionAGPosInfo_2),
                                        "Meiryo UI", 11, true, true,
                                        "left", "center", "thin", "thin", "thin",
                                        "thin", false, new int[]{255, 255, 255});
                                cellStyleSet.doOneCellStyle(conditionAGCellStyle_2);

                                SheetPosValue conditionAGCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), checkCellValue(conditionAG.get(key)));
                                cellValueSet.doOneCellValue(conditionAGCellVal_2);
                                // TODO
                                String[] iConditionData = transitionDropDownData.getiConditionData();
                                setDropDownList(this.sheetInfo, newConditionAGPosInfo_2, iConditionData);
                                index++;
                            }
                        } else {
                            String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                            SheetPosValue conditionAGCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue("A"));
                            cellValueSet.doOneCellValue(conditionAGCellVal_1);

                            String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                            String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + (startRowCount - rowCount));
                            String newConditionAGPosInfo_2 = pos_0_new + ":" + pos_1;

                            CellStyleInfo conditionAGCellStyle_2 = new CellStyleInfo(
                                    String.format("%s", newConditionAGPosInfo_2),
                                    "Meiryo UI", 11, true, true,
                                    "left", "center", "thin", "thin", "thin",
                                    "thin", false, new int[]{255, 255, 255});
                            cellStyleSet.doOneCellStyle(conditionAGCellStyle_2);

                            SheetPosValue conditionAGCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), "-");
                            cellValueSet.doOneCellValue(conditionAGCellVal_2);
                            // TODO
                            String[] iConditionData = transitionDropDownData.getiConditionData();
                            setDropDownList(this.sheetInfo, newConditionAGPosInfo_2, iConditionData);
                        }
                    }

                    // 写入Action_AL
                    {
                        String oldActionALPosInfo = posList.get("Action_AL");
                        int[] posRow = getPosRow(oldActionALPosInfo);
                        String[] posCol = getPosCol(oldActionALPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newActionALPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo actionALCellStyle = new CellStyleInfo(
                                String.format("%s", newActionALPosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(actionALCellStyle);
                        SheetPosValue actionALCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(transitionRefInfo.getTransitionIAction()));
                        cellValueSet.doOneCellValue(actionALCellVal);
                        // TODO
                        String[] iActionData = transitionDropDownData.getiActionData();
                        setDropDownList(this.sheetInfo, newActionALPosInfo, iActionData);
                    }

                    // 写入Transition_AQ
                    {
//                        String oldTransitionAQPosInfo = posList.get("Transition_AQ");
//                        int[] posRow = getPosRow(oldTransitionAQPosInfo);
//                        String[] posCol = getPosCol(oldTransitionAQPosInfo);
//                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
//                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
//                        String newTransitionAQPosInfo = pos_0 + ":" + pos_1;
//
//                        CellStyleInfo transitionAQCellStyle = new CellStyleInfo(
//                                String.format("%s", newTransitionAQPosInfo),
//                                "Meiryo UI", 11, true, true,
//                                "left", "center", "thin", "thin", "thin",
//                                "thin", false, new int[]{255, 255, 255});
//                        cellStyleSet.doOneCellStyle(transitionAQCellStyle);
//                        SheetPosValue transitionAQCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(transitionRefInfo.getTransitionITrans()));
//                        cellValueSet.doOneCellValue(transitionAQCellVal);
//                        setDropDownList(this.sheetInfo, newTransitionAQPosInfo);
                    }
                }

                // 写入UUID
                {
                    String oldUUIDPosInfo = posList.get("UUID");
                    int[] posRow = getPosRow(oldUUIDPosInfo);
                    String[] posCol = getPosCol(oldUUIDPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newUUIDPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo UUIDCellStyle = new CellStyleInfo(
                            String.format("%s", newUUIDPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(UUIDCellStyle);
                    SheetPosValue UUIDCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(jsonSketchInfo.getSketchInfoId() + "->" + jsonPartInfo.getUuid()));
                    cellValueSet.doOneCellValue(UUIDCellVal);
                }

                // 写入Event
                {
                    String oldEventPosInfo = posList.get("Event");
                    int[] posRow = getPosRow(oldEventPosInfo);
                    String[] posCol = getPosCol(oldEventPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newEventPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo eventCellStyle = new CellStyleInfo(
                            String.format("%s", newEventPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(eventCellStyle);
                    SheetPosValue eventCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(transitionRefInfo.getTransitionEvent()));
                    cellValueSet.doOneCellValue(eventCellVal);
                }

                // 写入Back to Other Screen
                {
                    // 写入View Model_AY
                    {
                        String oldViewModelAYPosInfo = posList.get("View Model_AY");
                        int[] posRow = getPosRow(oldViewModelAYPosInfo);
                        String[] posCol = getPosCol(oldViewModelAYPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_0_0 = posCol[0] + String.valueOf(posRow[0] + startRowCount);
                        String newViewModelAYPosInfo_1 = pos_0 + ":" + pos_0_0;

                        CellStyleInfo viewModelAYCellStyle_1 = new CellStyleInfo(
                                String.format("%s", newViewModelAYPosInfo_1),
                                "Meiryo UI", 11, false, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(viewModelAYCellStyle_1);

                        HashMap<String, String> viewModelAY = transitionRefInfo.getTransitionBConditionModel();
                        Set<String> viewModelAYKey = viewModelAY.keySet();
                        if (viewModelAYKey.size() != 0) {
                            int index = 0;
                            for (String key : viewModelAYKey) {
                                String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                                SheetPosValue viewModelAYCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue(key));
                                cellValueSet.doOneCellValue(viewModelAYCellVal_1);

                                String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                                String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + index + (startRowCount - rowCount));
                                String newViewModelAYPosInfo_2 = pos_0_new + ":" + pos_1;

                                CellStyleInfo viewModelAYCellStyle_2 = new CellStyleInfo(
                                        String.format("%s", newViewModelAYPosInfo_2),
                                        "Meiryo UI", 11, true, true,
                                        "left", "center", "thin", "thin", "thin",
                                        "thin", false, new int[]{255, 255, 255});
                                cellStyleSet.doOneCellStyle(viewModelAYCellStyle_2);

                                SheetPosValue viewModelAYCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), checkCellValue(viewModelAY.get(key)));
                                cellValueSet.doOneCellValue(viewModelAYCellVal_2);
                                // TODO
                                int row = getPosRowSingle(pos_0_new);
                                String col = getPosColSingle(pos_0_new);
                                int iCol = excelColStrToNum(col, col.length());
                                String formulaString = transitionDropDownData.getbViewModelDataFormulaString(row);
                                setLinkage(row, iCol, formulaString);
                                index++;
                            }
                        } else {
                            String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                            SheetPosValue viewModelAYCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue("A"));
                            cellValueSet.doOneCellValue(viewModelAYCellVal_1);

                            String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                            String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + (startRowCount - rowCount));
                            String newViewModelAYPosInfo_2 = pos_0_new + ":" + pos_1;

                            CellStyleInfo viewModelAYCellStyle_2 = new CellStyleInfo(
                                    String.format("%s", newViewModelAYPosInfo_2),
                                    "Meiryo UI", 11, true, true,
                                    "left", "center", "thin", "thin", "thin",
                                    "thin", false, new int[]{255, 255, 255});
                            cellStyleSet.doOneCellStyle(viewModelAYCellStyle_2);

                            SheetPosValue viewModelAYCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), "-");
                            cellValueSet.doOneCellValue(viewModelAYCellVal_2);
                            // TODO
                            int row = getPosRowSingle(pos_0_new);
                            String col = getPosColSingle(pos_0_new);
                            int iCol = excelColStrToNum(col, col.length());
                            String formulaString = transitionDropDownData.getbViewModelDataFormulaString(row);
                            setLinkage(row, iCol, formulaString);
                        }
                    }

                    // 写入Func of Model_BD
                    {
                        String oldFuncModelBDPosInfo = posList.get("Func of Model_BD");
                        int[] posRow = getPosRow(oldFuncModelBDPosInfo);
                        String[] posCol = getPosCol(oldFuncModelBDPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newFuncModelBDPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo funcModelBDCellStyle = new CellStyleInfo(
                                String.format("%s", newFuncModelBDPosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(funcModelBDCellStyle);
                        SheetPosValue funcModelBDCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(transitionRefInfo.getTransitionBActionModel()));
                        cellValueSet.doOneCellValue(funcModelBDCellVal);
                        // TODO
                        int row = getPosRowSingle(pos_0);
                        String col = getPosColSingle(pos_0);
                        int iCol = excelColStrToNum(col, col.length());
                        String formulaString = transitionDropDownData.getbFuncOfModelDataFormulaString(row);
                        setLinkage(row, iCol, formulaString);
                    }

                    // 写入Observer_BG
                    {
                        String oldObserverBGPosInfo = posList.get("Observer_BG");
                        int[] posRow = getPosRow(oldObserverBGPosInfo);
                        String[] posCol = getPosCol(oldObserverBGPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newObserverBGPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo observerBGCellStyle = new CellStyleInfo(
                                String.format("%s", newObserverBGPosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(observerBGCellStyle);
                        SheetPosValue observerBGCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(transitionRefInfo.getTransitionBObserver()));
                        cellValueSet.doOneCellValue(observerBGCellVal);
                    }

                    // 写入Reply_BJ
                    {
                        String oldReplyBJPosInfo = posList.get("Reply_BJ");
                        int[] posRow = getPosRow(oldReplyBJPosInfo);
                        String[] posCol = getPosCol(oldReplyBJPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newReplyBJPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo replyBGCellStyle = new CellStyleInfo(
                                String.format("%s", newReplyBJPosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(replyBGCellStyle);
                        SheetPosValue replyBGCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(transitionRefInfo.getTransitionBReply()));
                        cellValueSet.doOneCellValue(replyBGCellVal);
                    }
                }

                // 写入Forward to Other Screen
                {
                    // 写入View Model_BN
                    {
                        String oldViewModelBNPosInfo = posList.get("View Model_BN");
                        int[] posRow = getPosRow(oldViewModelBNPosInfo);
                        String[] posCol = getPosCol(oldViewModelBNPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_0_0 = posCol[0] + String.valueOf(posRow[0] + startRowCount);
                        String newViewModelBNPosInfo_1 = pos_0 + ":" + pos_0_0;

                        CellStyleInfo viewModelBNCellStyle_1 = new CellStyleInfo(
                                String.format("%s", newViewModelBNPosInfo_1),
                                "Meiryo UI", 11, false, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(viewModelBNCellStyle_1);

                        HashMap<String, String> viewModelBN = transitionRefInfo.getTransitionFConditionModel();
                        Set<String> viewModelBNKey = viewModelBN.keySet();
                        if (viewModelBNKey.size() != 0) {
                            int index = 0;
                            for (String key : viewModelBNKey) {
                                String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                                SheetPosValue viewModelBNCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue(key));
                                cellValueSet.doOneCellValue(viewModelBNCellVal_1);

                                String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                                String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + index + (startRowCount - rowCount));
                                String newViewModelBNPosInfo_2 = pos_0_new + ":" + pos_1;

                                CellStyleInfo viewModelBNCellStyle_2 = new CellStyleInfo(
                                        String.format("%s", newViewModelBNPosInfo_2),
                                        "Meiryo UI", 11, true, true,
                                        "left", "center", "thin", "thin", "thin",
                                        "thin", false, new int[]{255, 255, 255});
                                cellStyleSet.doOneCellStyle(viewModelBNCellStyle_2);

                                SheetPosValue viewModelBNCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), checkCellValue(viewModelBN.get(key)));
                                cellValueSet.doOneCellValue(viewModelBNCellVal_2);
                                // TODO
                                int row = getPosRowSingle(pos_0_new);
                                String col = getPosColSingle(pos_0_new);
                                int iCol = excelColStrToNum(col, col.length());
                                String formulaString = transitionDropDownData.getfViewModelDataFormulaString(row);
                                setLinkage(row, iCol, formulaString);
                                index++;
                            }
                        } else {
                            String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                            SheetPosValue viewModelBNCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue("A"));
                            cellValueSet.doOneCellValue(viewModelBNCellVal_1);

                            String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                            String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + (startRowCount - rowCount));
                            String newViewModelBNPosInfo_2 = pos_0_new + ":" + pos_1;

                            CellStyleInfo viewModelBNCellStyle_2 = new CellStyleInfo(
                                    String.format("%s", newViewModelBNPosInfo_2),
                                    "Meiryo UI", 11, true, true,
                                    "left", "center", "thin", "thin", "thin",
                                    "thin", false, new int[]{255, 255, 255});
                            cellStyleSet.doOneCellStyle(viewModelBNCellStyle_2);

                            SheetPosValue viewModelBNCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), "-");
                            cellValueSet.doOneCellValue(viewModelBNCellVal_2);
                            // TODO
                            int row = getPosRowSingle(pos_0_new);
                            String col = getPosColSingle(pos_0_new);
                            int iCol = excelColStrToNum(col, col.length());
                            String formulaString = transitionDropDownData.getfViewModelDataFormulaString(row);
                            setLinkage(row, iCol, formulaString);
                        }
                    }

                    // 写入Func of Model_BS
                    {
                        String oldFuncModelBSPosInfo = posList.get("Func of Model_BS");
                        int[] posRow = getPosRow(oldFuncModelBSPosInfo);
                        String[] posCol = getPosCol(oldFuncModelBSPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newFuncModelBSPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo funcModelBSCellStyle = new CellStyleInfo(
                                String.format("%s", newFuncModelBSPosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(funcModelBSCellStyle);
                        SheetPosValue funcModelBSCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(transitionRefInfo.getTransitionFActionModel()));
                        cellValueSet.doOneCellValue(funcModelBSCellVal);
                        // TODO
                        int row = getPosRowSingle(pos_0);
                        String col = getPosColSingle(pos_0);
                        int iCol = excelColStrToNum(col, col.length());
                        String formulaString = transitionDropDownData.getfFuncOfModelDataFormulaString(row);
                        setLinkage(row, iCol, formulaString);
                    }

                    // 写入Observer_BV
                    {
                        String oldObserverBVPosInfo = posList.get("Observer_BV");
                        int[] posRow = getPosRow(oldObserverBVPosInfo);
                        String[] posCol = getPosCol(oldObserverBVPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newObserverBVPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo observerBVCellStyle = new CellStyleInfo(
                                String.format("%s", newObserverBVPosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(observerBVCellStyle);
                        SheetPosValue observerBVCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(transitionRefInfo.getTransitionFObserver()));
                        cellValueSet.doOneCellValue(observerBVCellVal);
                    }

                    // 写入Reply_BY
                    {
                        String oldReplyBYPosInfo = posList.get("Reply_BY");
                        int[] posRow = getPosRow(oldReplyBYPosInfo);
                        String[] posCol = getPosCol(oldReplyBYPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newReplyBYPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo replyBYCellStyle = new CellStyleInfo(
                                String.format("%s", newReplyBYPosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(replyBYCellStyle);
                        SheetPosValue replyBYCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(transitionRefInfo.getTransitionFReply()));
                        cellValueSet.doOneCellValue(replyBYCellVal);
                    }
                }

                // 写入Interrupt
                {
                    // 写入View Model_CC
                    {
                        String oldViewModelCCPosInfo = posList.get("View Model_CC");
                        int[] posRow = getPosRow(oldViewModelCCPosInfo);
                        String[] posCol = getPosCol(oldViewModelCCPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_0_0 = posCol[0] + String.valueOf(posRow[0] + startRowCount);
                        String newViewModelCCPosInfo_1 = pos_0 + ":" + pos_0_0;

                        CellStyleInfo viewModelCCCellStyle_1 = new CellStyleInfo(
                                String.format("%s", newViewModelCCPosInfo_1),
                                "Meiryo UI", 11, false, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(viewModelCCCellStyle_1);

                        HashMap<String, String> viewModelCC = transitionRefInfo.getTransitionIConditionModel();
                        Set<String> viewModelCCKey = viewModelCC.keySet();
                        if (viewModelCCKey.size() != 0) {
                            int index = 0;
                            for (String key : viewModelCCKey) {
                                String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                                SheetPosValue viewModelCCCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue(key));
                                cellValueSet.doOneCellValue(viewModelCCCellVal_1);

                                String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                                String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + index + (startRowCount - rowCount));
                                String newViewModelCCPosInfo_2 = pos_0_new + ":" + pos_1;

                                CellStyleInfo viewModelCCCellStyle_2 = new CellStyleInfo(
                                        String.format("%s", newViewModelCCPosInfo_2),
                                        "Meiryo UI", 11, true, true,
                                        "left", "center", "thin", "thin", "thin",
                                        "thin", false, new int[]{255, 255, 255});
                                cellStyleSet.doOneCellStyle(viewModelCCCellStyle_2);

                                SheetPosValue viewModelCCCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), checkCellValue(viewModelCC.get(key)));
                                cellValueSet.doOneCellValue(viewModelCCCellVal_2);
                                // TODO
                                int row = getPosRowSingle(pos_0_new);
                                String col = getPosColSingle(pos_0_new);
                                int iCol = excelColStrToNum(col, col.length());
                                String formulaString = transitionDropDownData.getiViewModelDataFormulaString(row);
                                setLinkage(row, iCol, formulaString);
                                index++;
                            }
                        } else {
                            String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                            SheetPosValue viewModelCCCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue("A"));
                            cellValueSet.doOneCellValue(viewModelCCCellVal_1);

                            String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                            String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + (startRowCount - rowCount));
                            String newViewModelCCPosInfo_2 = pos_0_new + ":" + pos_1;

                            CellStyleInfo viewModelCCCellStyle_2 = new CellStyleInfo(
                                    String.format("%s", newViewModelCCPosInfo_2),
                                    "Meiryo UI", 11, true, true,
                                    "left", "center", "thin", "thin", "thin",
                                    "thin", false, new int[]{255, 255, 255});
                            cellStyleSet.doOneCellStyle(viewModelCCCellStyle_2);

                            SheetPosValue viewModelCCCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), "-");
                            cellValueSet.doOneCellValue(viewModelCCCellVal_2);
                            // TODO
                            int row = getPosRowSingle(pos_0_new);
                            String col = getPosColSingle(pos_0_new);
                            int iCol = excelColStrToNum(col, col.length());
                            String formulaString = transitionDropDownData.getiViewModelDataFormulaString(row);
                            setLinkage(row, iCol, formulaString);
                        }
                    }

                    // 写入Func of Model_CH
                    {
                        String oldFuncModelCHPosInfo = posList.get("Func of Model_CH");
                        int[] posRow = getPosRow(oldFuncModelCHPosInfo);
                        String[] posCol = getPosCol(oldFuncModelCHPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newFuncModelCHPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo funcModelCHCellStyle = new CellStyleInfo(
                                String.format("%s", newFuncModelCHPosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(funcModelCHCellStyle);
                        SheetPosValue funcModelCHCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(transitionRefInfo.getTransitionIActionModel()));
                        cellValueSet.doOneCellValue(funcModelCHCellVal);
                        // TODO
                        int row = getPosRowSingle(pos_0);
                        String col = getPosColSingle(pos_0);
                        int iCol = excelColStrToNum(col, col.length());
                        String formulaString = transitionDropDownData.getiFuncOfModelDataFormulaString(row);
                        setLinkage(row, iCol, formulaString);
                    }

                    // 写入Observer_CK
                    {
                        String oldObserverCKPosInfo = posList.get("Observer_CK");
                        int[] posRow = getPosRow(oldObserverCKPosInfo);
                        String[] posCol = getPosCol(oldObserverCKPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newObserverCKPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo observerCKCellStyle = new CellStyleInfo(
                                String.format("%s", newObserverCKPosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(observerCKCellStyle);
                        SheetPosValue observerCKCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(transitionRefInfo.getTransitionIObserver()));
                        cellValueSet.doOneCellValue(observerCKCellVal);
                    }

                    // 写入Reply_CN
                    {
                        String oldReplyCNPosInfo = posList.get("Reply_CN");
                        int[] posRow = getPosRow(oldReplyCNPosInfo);
                        String[] posCol = getPosCol(oldReplyCNPosInfo);
                        String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newReplyCNPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo replyCNCellStyle = new CellStyleInfo(
                                String.format("%s", newReplyCNPosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(replyCNCellStyle);
                        SheetPosValue replyCNCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(transitionRefInfo.getTransitionIReply()));
                        cellValueSet.doOneCellValue(replyCNCellVal);
                    }
                }
            }
        }

        StartRowIndex += 3 + startRowCount + DefaultTableSpace;
        // TransitionRefInfoStr
        {
            String transitionRefInfoPosInfo1= "B" + String.valueOf(StartRowIndex - 1) + ":" + "B" + String.valueOf(StartRowIndex - 1);
            CellStyleInfo transitionRefInfoCellStyle = new CellStyleInfo(
                    String.format("%s", transitionRefInfoPosInfo1),
                    "Meiryo UI", 11, false, true,
                    "left", "center", "", "thin", "thin",
                    "thin", false, new int[]{255, 255, 255});
            cellStyleSet.doOneCellStyle(transitionRefInfoCellStyle);
            String transitionRefInfoPosInfo2 = "C" + String.valueOf(StartRowIndex - 1);
            SheetPosValue transitionRefInfoCellVal = new SheetPosValue(String.format("%s", transitionRefInfoPosInfo2), checkCellValue(jsonSketchInfo.getTransitionRefInfoStr()));
            cellValueSet.doOneCellValue(transitionRefInfoCellVal);
        }
        return StartRowIndex;
    }

    private int doExportTrigRefInfo(JsonSketchInfo_new jsonSketchInfo, int StartRowIndex) throws Exception {
        // 表头设置
        HMIPartsSpecStatic_new staticInfo = new HMIPartsSpecStatic_new();

        CellStyleSet cellStyleSet = new CellStyleSet(this.workBook, this.sheetInfo);
        ArrayList<CellStyleInfo> styleList = getNewStyleList(staticInfo.styleLists.get("TrigRefInfo"), StartRowIndex, 8);
        cellStyleSet.doCellStyleList(styleList);

        CellValueSet cellValueSet = new CellValueSet(this.workBook, this.sheetInfo);
        ArrayList<SheetPosValue> lableList = getNewLableList(staticInfo.lableLists.get("TrigRefInfo"), StartRowIndex, 8);
        cellValueSet.doCellValueList(lableList);

        HashMap<String, String> posList = getPosList(lableList, styleList, 8);
        ArrayList<JsonPartInfo_new> jsonPartInfoList = sortByNo(jsonSketchInfo.getJsonPartInfoList(), 8);
        HMIDropDownData dropDownData = HMIDropDownData.getInstance();
        HMIDropDownData.TrigDropDownData trigDropDownData = dropDownData.getTrigDropDownDataInstance();
        int startRowCount = 0;
        for (int k = 0; k < jsonPartInfoList.size(); ++k) {
            JsonPartInfo_new jsonPartInfo = jsonPartInfoList.get(k);
            JsonTrigRefInfo trigRefInfo = jsonPartInfo.getTrigRefInfo();
            if (checkTrigRefInfo(trigRefInfo)) {
                int rowCount = getTrigRefInfoRowCount(trigRefInfo);
                startRowCount += rowCount;
                boolean isMergeRow = isMergeRow(rowCount);

                // 写入Chapter
                {
                    String oldChapterPosInfo = posList.get("Chapter");
                    int[] posRow = getPosRow(oldChapterPosInfo);
                    String[] posCol = getPosCol(oldChapterPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newChapterPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo chapterCellStyle = new CellStyleInfo(
                            String.format("%s", newChapterPosInfo),
                            "Meiryo UI", 11, isMergeRow, true,
                            "center", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{201,255,255});
                    cellStyleSet.doOneCellStyle(chapterCellStyle);
                    SheetPosValue chapterCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue("8"));
                    cellValueSet.doOneCellValue(chapterCellVal);
                }

                // 写入SubChapter
                {
                    String oldSubChapterPosInfo = posList.get("SubChapter");
                    int[] posRow = getPosRow(oldSubChapterPosInfo);
                    String[] posCol = getPosCol(oldSubChapterPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newSubChapterPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo subChapterCellStyle = new CellStyleInfo(
                            String.format("%s", newSubChapterPosInfo),
                            "Meiryo UI", 11, isMergeRow, true,
                            "center", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255,205,156});
                    cellStyleSet.doOneCellStyle(subChapterCellStyle);
                    SheetPosValue subChapterCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue("1"));
                    cellValueSet.doOneCellValue(subChapterCellVal);
                }

                // 写入Normal
                {
                    String oldNOPosInfo = posList.get("Normal");
                    int[] posRow = getPosRow(oldNOPosInfo);
                    String[] posCol = getPosCol(oldNOPosInfo);
                    // 写入NO.
                    {
                        String pos_0 = getNewCol(posCol[0], 0) + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = getNewCol(posCol[0], 0) + String.valueOf(posRow[0] + startRowCount);
                        String newNOPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo noCellStyle = new CellStyleInfo(
                                String.format("%s", newNOPosInfo),
                                "Meiryo UI", 11, isMergeRow, true,
                                "center", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255,205,156});
                        cellStyleSet.doOneCellStyle(noCellStyle);
                        SheetPosValue noCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(trigRefInfo.getTrigNo()));
                        cellValueSet.doOneCellValue(noCellVal);
                    }

                    // 写入StateNo
                    {
                        String pos_0 = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + startRowCount);
                        String newStateNoPosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo noCellStyle = new CellStyleInfo(
                                String.format("%s", newStateNoPosInfo),
                                "Meiryo UI", 11, isMergeRow, true,
                                "center", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{204,255,204});
                        cellStyleSet.doOneCellStyle(noCellStyle);
                        SheetPosValue stateNoCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(trigRefInfo.getTrigStateNo()));
                        cellValueSet.doOneCellValue(stateNoCellVal);
                    }

                    // 写入Name
                    {
                        String pos_0 = getNewCol(posCol[0], 2) + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                        String newNamePosInfo = pos_0 + ":" + pos_1;

                        CellStyleInfo nameCellStyle = new CellStyleInfo(
                                String.format("%s", newNamePosInfo),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{204,255,204});
                        cellStyleSet.doOneCellStyle(nameCellStyle);
                        SheetPosValue nameCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(trigRefInfo.getTrigName()));
                        cellValueSet.doOneCellValue(nameCellVal);
                        // TODO
                        String[] nameData = trigDropDownData.getNameData();
                        setDropDownList(this.sheetInfo, newNamePosInfo, nameData);
                    }
                }

                // 写入Formula
                {
                    String oldFormulaPosInfo = posList.get("Formula");
                    int[] posRow = getPosRow(oldFormulaPosInfo);
                    String[] posCol = getPosCol(oldFormulaPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newFormulaPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo formulaCellStyle = new CellStyleInfo(
                            String.format("%s", newFormulaPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(formulaCellStyle);
                    SheetPosValue formulaCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(trigRefInfo.getTrigFormula()));
                    cellValueSet.doOneCellValue(formulaCellVal);
                }

                // 写入Condition of Action
                {
                    String oldTrigConditionPosInfo = posList.get("Condition of Action");
                    int[] posRow = getPosRow(oldTrigConditionPosInfo);
                    String[] posCol = getPosCol(oldTrigConditionPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_0_0 = posCol[0] + String.valueOf(posRow[0] + startRowCount);
                    String newTrigConditionPosInfo_1 = pos_0 + ":" + pos_0_0;

                    CellStyleInfo trigConditionCellStyle_1 = new CellStyleInfo(
                            String.format("%s", newTrigConditionPosInfo_1),
                            "Meiryo UI", 11, false, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(trigConditionCellStyle_1);

                    HashMap<String, String> trigCondition = trigRefInfo.getTrigCondition();
                    Set<String> trigConditionKey = trigCondition.keySet();
                    if (trigConditionKey.size() != 0) {
                        int index = 0;
                        for (String key : trigConditionKey) {
                            String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                            SheetPosValue trigConditionCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue(key));
                            cellValueSet.doOneCellValue(trigConditionCellVal_1);

                            String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                            String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + index + (startRowCount - rowCount));
                            String newTrigConditionPosInfo_2 = pos_0_new + ":" + pos_1;

                            CellStyleInfo tirgConditionCellStyle_2 = new CellStyleInfo(
                                    String.format("%s", newTrigConditionPosInfo_2),
                                    "Meiryo UI", 11, true, true,
                                    "left", "center", "thin", "thin", "thin",
                                    "thin", false, new int[]{255, 255, 255});
                            cellStyleSet.doOneCellStyle(tirgConditionCellStyle_2);

                            SheetPosValue trigConditionCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), checkCellValue(trigCondition.get(key)));
                            cellValueSet.doOneCellValue(trigConditionCellVal_2);
                            // TODO
                            String[] conditionOfActionData = trigDropDownData.getConditionOfActionData();
                            setDropDownList(this.sheetInfo, newTrigConditionPosInfo_2, conditionOfActionData);
                            index++;
                        }
                    } else {
                        String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        SheetPosValue trigConditionCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue("A"));
                        cellValueSet.doOneCellValue(trigConditionCellVal_1);

                        String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + (startRowCount - rowCount));
                        String newTrigConditionPosInfo_2 = pos_0_new + ":" + pos_1;

                        CellStyleInfo trigConditionCellStyle_2 = new CellStyleInfo(
                                String.format("%s", newTrigConditionPosInfo_2),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(trigConditionCellStyle_2);

                        SheetPosValue trigConditionCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), "-");
                        cellValueSet.doOneCellValue(trigConditionCellVal_2);
                        // TODO
                        String[] conditionOfActionData = trigDropDownData.getConditionOfActionData();
                        setDropDownList(this.sheetInfo, newTrigConditionPosInfo_2, conditionOfActionData);
                    }
                }

                // 写入Action in Such Condition
                {
                    String oldTrigActionPosInfo = posList.get("Action in Such Condition");
                    int[] posRow = getPosRow(oldTrigActionPosInfo);
                    String[] posCol = getPosCol(oldTrigActionPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newTrigActionPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo trigActionCellStyle = new CellStyleInfo(
                            String.format("%s", newTrigActionPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(trigActionCellStyle);
                    SheetPosValue trigActionCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(trigRefInfo.getTrigAction()));
                    cellValueSet.doOneCellValue(trigActionCellVal);
                    // TODO
                    String[] actionInSuchConditionData = trigDropDownData.getActionInSuchConditionData();
                    setDropDownList(this.sheetInfo, newTrigActionPosInfo, actionInSuchConditionData);
                }

                // 写入Transition
                {
                    String oldTransitionPosInfo = posList.get("Transition");
                    int[] posRow = getPosRow(oldTransitionPosInfo);
                    String[] posCol = getPosCol(oldTransitionPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newTransitionPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo transitionCellStyle = new CellStyleInfo(
                            String.format("%s", newTransitionPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(transitionCellStyle);
                    SheetPosValue transitionCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(trigRefInfo.getTrigTrans()));
                    cellValueSet.doOneCellValue(transitionCellVal);
                }

                // 写入Sound
                {
                    String oldSoundPosInfo = posList.get("Sound");
                    int[] posRow = getPosRow(oldSoundPosInfo);
                    String[] posCol = getPosCol(oldSoundPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newSoundPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo soundCellStyle = new CellStyleInfo(
                            String.format("%s", newSoundPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(soundCellStyle);
                    SheetPosValue soundCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(trigRefInfo.getTrigSound()));
                    cellValueSet.doOneCellValue(soundCellVal);
                    // TODO
                    String[] soundData = trigDropDownData.getSoundData();
                    setDropDownList(this.sheetInfo, newSoundPosInfo, soundData);
                }

                // 写入Timer
                {
                    String oldTimerPosInfo = posList.get("Timer");
                    int[] posRow = getPosRow(oldTimerPosInfo);
                    String[] posCol = getPosCol(oldTimerPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newTimerPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo timerCellStyle = new CellStyleInfo(
                            String.format("%s", newTimerPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(timerCellStyle);
                    SheetPosValue timerCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(trigRefInfo.getTrigTimer()));
                    cellValueSet.doOneCellValue(timerCellVal);
                }

                // 写入Remark
                {
                    String oldRemarkPosInfo = posList.get("Remark");
                    int[] posRow = getPosRow(oldRemarkPosInfo);
                    String[] posCol = getPosCol(oldRemarkPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newRemarkPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo remarkCellStyle = new CellStyleInfo(
                            String.format("%s", newRemarkPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(remarkCellStyle);
                    SheetPosValue remarkCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(trigRefInfo.getTrigRemark()));
                    cellValueSet.doOneCellValue(remarkCellVal);
                }

                // 写入UUID
                {
                    String oldUUIDPosInfo = posList.get("UUID");
                    int[] posRow = getPosRow(oldUUIDPosInfo);
                    String[] posCol = getPosCol(oldUUIDPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newUUIDPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo UUIDCellStyle = new CellStyleInfo(
                            String.format("%s", newUUIDPosInfo),
                            "Meiryo UI", 11, isMergeRow, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(UUIDCellStyle);
                    SheetPosValue UUIDCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(jsonSketchInfo.getSketchInfoId() + "->" + jsonPartInfo.getUuid()));
                    cellValueSet.doOneCellValue(UUIDCellVal);
                }

                // 写入Signal
                {
                    String oldSignalPosInfo = posList.get("Trigger");
                    int[] posRow = getPosRow(oldSignalPosInfo);
                    String[] posCol = getPosCol(oldSignalPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newSignalPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo signalCellStyle = new CellStyleInfo(
                            String.format("%s", newSignalPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(signalCellStyle);
                    // TODO
                    int row = getPosRowSingle(pos_0);
                    String col = getPosColSingle(pos_0);
                    int iCol = excelColStrToNum(col, col.length());
                    String formulaString = trigDropDownData.getTriggerDataFormulaString(row);
                    setLinkage(row, iCol, formulaString);
                }

                // 写入Event
                {
                    String oldEventPosInfo = posList.get("ONSID");
                    int[] posRow = getPosRow(oldEventPosInfo);
                    String[] posCol = getPosCol(oldEventPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newEventPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo eventCellStyle = new CellStyleInfo(
                            String.format("%s", newEventPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(eventCellStyle);
                    SheetPosValue eventCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(trigRefInfo.getTrigEvent()));
                    cellValueSet.doOneCellValue(eventCellVal);
                }

                // 写入View Model
                {
                    String oldTrigConditionModelPosInfo = posList.get("View Model");
                    int[] posRow = getPosRow(oldTrigConditionModelPosInfo);
                    String[] posCol = getPosCol(oldTrigConditionModelPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_0_0 = posCol[0] + String.valueOf(posRow[0] + startRowCount);
                    String newTrigConditionModelPosInfo_1 = pos_0 + ":" + pos_0_0;

                    CellStyleInfo trigConditionModelCellStyle_1 = new CellStyleInfo(
                            String.format("%s", newTrigConditionModelPosInfo_1),
                            "Meiryo UI", 11, false, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(trigConditionModelCellStyle_1);

                    HashMap<String, String> trigConditionModel = trigRefInfo.getTrigConditionModel();
                    Set<String> trigConditionModelKey = trigConditionModel.keySet();
                    if (trigConditionModelKey.size() != 0) {
                        int index = 0;
                        for (String key : trigConditionModelKey) {
                            String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                            SheetPosValue trigConditionModelCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue(key));
                            cellValueSet.doOneCellValue(trigConditionModelCellVal_1);

                            String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + index + (startRowCount - rowCount));
                            String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + index + (startRowCount - rowCount));
                            String newTrigConditionModelPosInfo_2 = pos_0_new + ":" + pos_1;

                            CellStyleInfo trigConditionModelCellStyle_2 = new CellStyleInfo(
                                    String.format("%s", newTrigConditionModelPosInfo_2),
                                    "Meiryo UI", 11, true, true,
                                    "left", "center", "thin", "thin", "thin",
                                    "thin", false, new int[]{255, 255, 255});
                            cellStyleSet.doOneCellStyle(trigConditionModelCellStyle_2);

                            SheetPosValue trigConditionModelCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), checkCellValue(trigConditionModel.get(key)));
                            cellValueSet.doOneCellValue(trigConditionModelCellVal_2);
                            // TODO
                            int row = getPosRowSingle(pos_0_new);
                            String col = getPosColSingle(pos_0_new);
                            int iCol = excelColStrToNum(col, col.length());
                            String formulaString = trigDropDownData.getViewModelDataFormulaString(row);
                            setLinkage(row, iCol, formulaString);
                            index++;
                        }
                    } else {
                        String valPos = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        SheetPosValue trigConditionModelCellVal_1 = new SheetPosValue(String.format("%s", valPos), checkCellValue("A"));
                        cellValueSet.doOneCellValue(trigConditionModelCellVal_1);

                        String pos_0_new = getNewCol(posCol[0], 1) + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                        String pos_1 = posCol[1] + String.valueOf(posRow[1] + 1 + (startRowCount - rowCount));
                        String newTrigConditionModelPosInfo_2 = pos_0_new + ":" + pos_1;

                        CellStyleInfo trigConditionModelCellStyle_2 = new CellStyleInfo(
                                String.format("%s", newTrigConditionModelPosInfo_2),
                                "Meiryo UI", 11, true, true,
                                "left", "center", "thin", "thin", "thin",
                                "thin", false, new int[]{255, 255, 255});
                        cellStyleSet.doOneCellStyle(trigConditionModelCellStyle_2);

                        SheetPosValue trigConditionModelCellVal_2 = new SheetPosValue(String.format("%s", pos_0_new), "-");
                        cellValueSet.doOneCellValue(trigConditionModelCellVal_2);
                        // TODO
                        int row = getPosRowSingle(pos_0_new);
                        String col = getPosColSingle(pos_0_new);
                        int iCol = excelColStrToNum(col, col.length());
                        String formulaString = trigDropDownData.getViewModelDataFormulaString(row);
                        setLinkage(row, iCol, formulaString);
                    }
                }

                // 写入Func of Model
                {
                    String oldFuncModelPosInfo = posList.get("Func of Model");
                    int[] posRow = getPosRow(oldFuncModelPosInfo);
                    String[] posCol = getPosCol(oldFuncModelPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newFuncModelPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo funcModelCellStyle = new CellStyleInfo(
                            String.format("%s", newFuncModelPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(funcModelCellStyle);
                    SheetPosValue funcModelCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(trigRefInfo.getTrigActionModel()));
                    cellValueSet.doOneCellValue(funcModelCellVal);
                    // TODO
                    int row = getPosRowSingle(pos_0);
                    String col = getPosColSingle(pos_0);
                    int iCol = excelColStrToNum(col, col.length());
                    String formulaString = trigDropDownData.getFuncOfModelDataFormulaString(row);
                    setLinkage(row, iCol, formulaString);
                }

                // 写入Observer
                {
                    String oldObserverPosInfo = posList.get("Observer");
                    int[] posRow = getPosRow(oldObserverPosInfo);
                    String[] posCol = getPosCol(oldObserverPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newObserverPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo observerCellStyle = new CellStyleInfo(
                            String.format("%s", newObserverPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(observerCellStyle);
                    // TODO
                    int row = getPosRowSingle(pos_0);
                    String col = getPosColSingle(pos_0);
                    int iCol = excelColStrToNum(col, col.length());
                    String formulaString = trigDropDownData.getObserverDataFormulaString(row);
                    setLinkage(row, iCol, formulaString);
                }

                // 写入Reply
                {
                    String oldReplyPosInfo = posList.get("Reply");
                    int[] posRow = getPosRow(oldReplyPosInfo);
                    String[] posCol = getPosCol(oldReplyPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newReplyPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo replyCellStyle = new CellStyleInfo(
                            String.format("%s", newReplyPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(replyCellStyle);
                    // TODO
                    int row = getPosRowSingle(pos_0);
                    String col = getPosColSingle(pos_0);
                    int iCol = excelColStrToNum(col, col.length());
                    String formulaString = trigDropDownData.getReplyDataFormulaString(row);
                    setLinkage(row, iCol, formulaString);
                }

                // 写入TransType
                {
                    String oldTransTypePosInfo = posList.get("TransType");
                    int[] posRow = getPosRow(oldTransTypePosInfo);
                    String[] posCol = getPosCol(oldTransTypePosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newTransTypePosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo transTypeCellStyle = new CellStyleInfo(
                            String.format("%s", newTransTypePosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(transTypeCellStyle);
                    SheetPosValue transTypeCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(trigRefInfo.getTrigTransType()));
                    cellValueSet.doOneCellValue(transTypeCellVal);
                }

                // 写入TransID
                {
                    String oldTransIDPosInfo = posList.get("TransID");
                    int[] posRow = getPosRow(oldTransIDPosInfo);
                    String[] posCol = getPosCol(oldTransIDPosInfo);
                    String pos_0 = posCol[0] + String.valueOf(posRow[0] + 1 + (startRowCount - rowCount));
                    String pos_1 = posCol[1] + String.valueOf(posRow[1] + startRowCount);
                    String newTransIDPosInfo = pos_0 + ":" + pos_1;

                    CellStyleInfo transIDCellStyle = new CellStyleInfo(
                            String.format("%s", newTransIDPosInfo),
                            "Meiryo UI", 11, true, true,
                            "left", "center", "thin", "thin", "thin",
                            "thin", false, new int[]{255, 255, 255});
                    cellStyleSet.doOneCellStyle(transIDCellStyle);
                    SheetPosValue transIDCellVal = new SheetPosValue(String.format("%s", pos_0), checkCellValue(trigRefInfo.getTrigTransID()));
                    cellValueSet.doOneCellValue(transIDCellVal);
                }
            }
        }

        StartRowIndex += 2 + startRowCount + DefaultTableSpace;
        // TrigRefInfoStr
        {
            String trigRefInfoPosInfo1= "C" + String.valueOf(StartRowIndex - 1) + ":" + "AR" + String.valueOf(StartRowIndex - 1);
            CellStyleInfo trigRefInfoCellStyle = new CellStyleInfo(
                    String.format("%s", trigRefInfoPosInfo1),
                    "Meiryo UI", 11, true, true,
                    "left", "center", "thin", "thin", "thin",
                    "thin", false, new int[]{255, 255, 255});
            cellStyleSet.doOneCellStyle(trigRefInfoCellStyle);
            String trigRefInfoPosInfo2 = "C" + String.valueOf(StartRowIndex - 1);
            SheetPosValue trigRefInfoCellVal = new SheetPosValue(String.format("%s", trigRefInfoPosInfo2), checkCellValue(jsonSketchInfo.getTrigRefInfoStr()));
            cellValueSet.doOneCellValue(trigRefInfoCellVal);
        }

        return StartRowIndex;
    }

    private ArrayList<JsonPartInfo_new> sortByNo(ArrayList<JsonPartInfo_new> jsonPartInfoList, int tableNumber) {
        switch (tableNumber) {
            case 1:
                return sortByDisplayNo(jsonPartInfoList);
            case 2:
                return sortByActiveNo(jsonPartInfoList);
            case 3:
                return sortByActionNo(jsonPartInfoList);
            case 4:
                return sortByHKNo(jsonPartInfoList);
            case 5:
                return sortByInitNo(jsonPartInfoList);
            case 6:
                return sortByStatusChangeNo(jsonPartInfoList);
            case 7:
                return sortByTransitionNo(jsonPartInfoList);
            case 8:
                return sortByTrigNo(jsonPartInfoList);
            default:
                return null;
        }
    }

    private ArrayList<JsonPartInfo_new> sortByDisplayNo(ArrayList<JsonPartInfo_new> jsonPartInfoList) {
        ArrayList<JsonPartInfo_new> newJsonPartInfoList = new ArrayList<>();
        ArrayList<Integer> indexList = new ArrayList<>();
        int insertIndex = 0;
        boolean isInsert = false;
        boolean isSameHead = false;
        for (int i = 0; i < jsonPartInfoList.size(); ++i) {
            JsonPartInfo_new jsonPartInfo = jsonPartInfoList.get(i);
            JsonDisplayRefInfo displayRefInfo = jsonPartInfo.getDisplayRefInfo();
            String displayNo = displayRefInfo.getPartsNumber();
            if (displayNo == null) {
                continue;
            }
            char ch1 = displayNo.charAt(0);
            String str1 = "";
            String otherStr1 = "";
            if (isNumeric(String.valueOf(ch1))) {
                str1 = displayNo.substring(0, displayNo.indexOf('-'));
                otherStr1 = displayNo.substring(displayNo.indexOf('-') + 1);
            } else {
                str1 = String.valueOf(displayNo.charAt(0));
                otherStr1 = displayNo.substring(1);
            }

            if (indexList.size() == 0) {
                indexList.add(i);
            } else {
                for (int j = 0; j < indexList.size(); ++j) {
                    JsonPartInfo_new jsonPartInfo1 = jsonPartInfoList.get(indexList.get(j));
                    JsonDisplayRefInfo displayRefInfo1 = jsonPartInfo1.getDisplayRefInfo();
                    String displayNo1 = displayRefInfo1.getPartsNumber();
                    char ch2 = displayNo1.charAt(0);
                    String str2 = "";
                    String otherStr2 = "";
                    if (isNumeric(String.valueOf(ch2))) {
                        str2 = displayNo1.substring(0, displayNo1.indexOf('-'));
                        otherStr2 = displayNo1.substring(displayNo1.indexOf('-') + 1);
                    } else {
                        str2 = String.valueOf(displayNo1.charAt(0));
                        otherStr2 = displayNo1.substring(1);
                    }

                    if (str1.equals(str2)) {
                        isSameHead = true;
                        if (otherStr1.compareTo(otherStr2) < 0 || otherStr1.compareTo(otherStr2) == 0) {
                            isInsert = true;
                            insertIndex = j;
                            isSameHead = false;
                            break;
                        } else {
                            isSameHead = false;
                            continue;
                        }
                    } else if (str1.equals("S") && str2.equals("P") && !isSameHead) {
                        isInsert = true;
                        insertIndex = j;
                        break;
                    } else if (str1.equals("S") && isNumeric(str2) && !isSameHead) {
                        isInsert = true;
                        insertIndex = j;
                        break;
                    } else if (str1.equals("P") && isNumeric(str2) && !isSameHead) {
                        isInsert = true;
                        insertIndex = j;
                        break;
                    } else if (isNumeric(str1) && isNumeric(str2)) {
                        int iStr1 = Integer.parseInt(str1);
                        int iStr2 = Integer.parseInt(str2);

                        if (iStr1 - iStr2 < 0) {
                            isInsert = true;
                            insertIndex = j;
                            break;
                        }
                    } else if (isSameHead)  {
                        isSameHead = false;
                        break;
                    }
                }
                if (isInsert) {
                    indexList.add(insertIndex, i);
                    isInsert = false;
                    isSameHead = false;
                } else {
                    indexList.add(i);
                    isSameHead = false;
                }
            }
        }

        for (int iIndexList = 0; iIndexList < indexList.size(); ++iIndexList) {
            newJsonPartInfoList.add(jsonPartInfoList.get(indexList.get(iIndexList)));
        }
        return sortByDisplayStateNo(newJsonPartInfoList);
    }

    private ArrayList<JsonPartInfo_new> sortByDisplayStateNo(ArrayList<JsonPartInfo_new> jsonPartInfoList) {
        ArrayList<JsonPartInfo_new> newJsonPartInfoList = new ArrayList<>();
        ArrayList<Integer> indexList = new ArrayList<>();
        HashMap<String, Integer> displayNOSameNumMap = new HashMap<>();
        for (int i = 0; i < jsonPartInfoList.size(); ++i) {
            JsonPartInfo_new jsonPartInfo = jsonPartInfoList.get(i);
            JsonDisplayRefInfo jsonDisplayRefInfo = jsonPartInfo.getDisplayRefInfo();
            String displayNo = jsonDisplayRefInfo.getPartsNumber();
            int sameNoCount = 0;
            for (int j = 0; j < jsonPartInfoList.size(); ++j) {
                JsonPartInfo_new jsonPartInfo1 = jsonPartInfoList.get(j);
                JsonDisplayRefInfo jsonDisplayRefInfo1 = jsonPartInfo1.getDisplayRefInfo();
                String displayNo1 = jsonDisplayRefInfo1.getPartsNumber();
                if (displayNo.equals(displayNo1)) {
                    sameNoCount++;
                } else if (!displayNo.equals(displayNo1) && sameNoCount != 0) {
                    break;
                }
            }
            displayNOSameNumMap.put(displayNo, sameNoCount);
        }

        for (int k = 0; k < jsonPartInfoList.size();) {
            JsonPartInfo_new jsonPartInfo = jsonPartInfoList.get(k);
            JsonDisplayRefInfo jsonDisplayRefInfo = jsonPartInfo.getDisplayRefInfo();
            String displayNo = jsonDisplayRefInfo.getPartsNumber();
            Integer value = displayNOSameNumMap.get(displayNo);
            int sameNoCount = value.intValue();
            if (sameNoCount > 1) {
                ArrayList<Integer> subIndexList = new ArrayList<>();
                boolean isInsert = false;
                for (int num = 0; num < sameNoCount; ++num) {
                    JsonPartInfo_new jsonPartInfo1 = jsonPartInfoList.get(k + num);
                    JsonDisplayRefInfo jsonDisplayRefInfo1 = jsonPartInfo1.getDisplayRefInfo();
                    int displayStateNo1 = Integer.valueOf(jsonDisplayRefInfo1.getDisplayStateNo());
                    if (subIndexList.size() == 0) {
                        subIndexList.add(k + num);
                    } else {
                        for (int iSubIndex = 0; iSubIndex < subIndexList.size(); ++iSubIndex) {
                            JsonPartInfo_new jsonPartInfo2 = jsonPartInfoList.get(subIndexList.get(iSubIndex));
                            JsonDisplayRefInfo jsonDisplayRefInfo2 = jsonPartInfo2.getDisplayRefInfo();
                            int displayStateNo2 = Integer.valueOf(jsonDisplayRefInfo2.getDisplayStateNo());
                            if (displayStateNo1 < displayStateNo2) {
                                subIndexList.add(iSubIndex, k + num);
                                isInsert = true;
                                break;
                            } else {
                                isInsert = false;
                            }
                        }
                        if (!isInsert) {
                            subIndexList.add(k + num);
                        }
                    }
                }

                for (int iSubIndex = 0; iSubIndex < subIndexList.size(); ++iSubIndex) {
                    indexList.add(subIndexList.get(iSubIndex));
                }
                k += sameNoCount;
            } else {
                indexList.add(k++);
            }
        }

        for (int iIndexList = 0; iIndexList < indexList.size(); ++iIndexList) {
            newJsonPartInfoList.add(jsonPartInfoList.get(indexList.get(iIndexList)));
        }
        return newJsonPartInfoList;
    }

    private ArrayList<JsonPartInfo_new> sortByActiveNo(ArrayList<JsonPartInfo_new> jsonPartInfoList) {
        ArrayList<JsonPartInfo_new> newJsonPartInfoList = new ArrayList<>();
        ArrayList<Integer> indexList = new ArrayList<>();
        boolean isInsert = false;
        for (int i = 0; i < jsonPartInfoList.size(); ++i) {
            JsonPartInfo_new jsonPartInfo = jsonPartInfoList.get(i);
            JsonActiveRefInfo jsonActiveRefInfo = jsonPartInfo.getActiveRefInfo();
            String activeNo = jsonActiveRefInfo.getActiveNo();
            if (activeNo == null) {
                continue;
            }
            int iActiveNo = Integer.parseInt(activeNo);
            if (indexList.size() == 0) {
                indexList.add(i);
            } else {
                for (int j = 0; j < indexList.size(); ++j) {
                    JsonPartInfo_new jsonPartInfo1 = jsonPartInfoList.get(indexList.get(j));
                    JsonActiveRefInfo jsonActiveRefInfo1 = jsonPartInfo1.getActiveRefInfo();
                    int iActiveNo1 = Integer.parseInt(jsonActiveRefInfo1.getActiveNo());
                    if (iActiveNo < iActiveNo1) {
                        indexList.add(j, i);
                        isInsert = true;
                        break;
                    } else {
                        isInsert = false;
                    }
                }
                if (!isInsert) {
                    indexList.add(i);
                }
            }
        }
        for (int iIndexList = 0; iIndexList < indexList.size(); ++iIndexList) {
            newJsonPartInfoList.add(jsonPartInfoList.get(indexList.get(iIndexList)));
        }
        return sortByActiveStateNo(newJsonPartInfoList);
    }

    private ArrayList<JsonPartInfo_new> sortByActiveStateNo(ArrayList<JsonPartInfo_new> jsonPartInfoList) {
        ArrayList<JsonPartInfo_new> newJsonPartInfoList = new ArrayList<>();
        ArrayList<Integer> indexList = new ArrayList<>();
        HashMap<String, Integer> activeNOSameNumMap = new HashMap<>();
        for (int i = 0; i < jsonPartInfoList.size(); ++i) {
            JsonPartInfo_new jsonPartInfo = jsonPartInfoList.get(i);
            JsonActiveRefInfo jsonActiveRefInfo = jsonPartInfo.getActiveRefInfo();
            String activeNo = jsonActiveRefInfo.getActiveNo();
            int sameNoCount = 0;
            for (int j = 0; j < jsonPartInfoList.size(); ++j) {
                JsonPartInfo_new jsonPartInfo1 = jsonPartInfoList.get(j);
                JsonActiveRefInfo jsonActiveRefInfo1 = jsonPartInfo1.getActiveRefInfo();
                String activeNo1 = jsonActiveRefInfo1.getActiveNo();
                if (activeNo.equals(activeNo1)) {
                    sameNoCount++;
                } else if (!activeNo.equals(activeNo1) && sameNoCount != 0) {
                    break;
                }
            }
            activeNOSameNumMap.put(activeNo, sameNoCount);
        }

        for (int k = 0; k < jsonPartInfoList.size();) {
            JsonPartInfo_new jsonPartInfo = jsonPartInfoList.get(k);
            JsonActiveRefInfo jsonActiveRefInfo = jsonPartInfo.getActiveRefInfo();
            String activeNo = jsonActiveRefInfo.getActiveNo();
            Integer value = activeNOSameNumMap.get(activeNo);
            int sameNoCount = value.intValue();
            if (sameNoCount > 1) {
                ArrayList<Integer> subIndexList = new ArrayList<>();
                boolean isInsert = false;
                for (int num = 0; num < sameNoCount; ++num) {
                    JsonPartInfo_new jsonPartInfo1 = jsonPartInfoList.get(k + num);
                    JsonActiveRefInfo jsonActiveRefInfo1 = jsonPartInfo1.getActiveRefInfo();
                    int activeStateNo1 = Integer.valueOf(jsonActiveRefInfo1.getActiveStateNo());
                    if (subIndexList.size() == 0) {
                        subIndexList.add(k + num);
                    } else {
                        for (int iSubIndex = 0; iSubIndex < subIndexList.size(); ++iSubIndex) {
                            JsonPartInfo_new jsonPartInfo2 = jsonPartInfoList.get(subIndexList.get(iSubIndex));
                            JsonActiveRefInfo jsonActiveRefInfo2 = jsonPartInfo2.getActiveRefInfo();
                            int activeStateNo2 = Integer.valueOf(jsonActiveRefInfo2.getActiveStateNo());
                            if (activeStateNo1 < activeStateNo2) {
                                subIndexList.add(iSubIndex, k + num);
                                isInsert = true;
                                break;
                            } else {
                                isInsert = false;
                            }
                        }
                        if (!isInsert) {
                            subIndexList.add(k + num);
                        }
                    }
                }

                for (int iSubIndex = 0; iSubIndex < subIndexList.size(); ++iSubIndex) {
                    indexList.add(subIndexList.get(iSubIndex));
                }
                k += sameNoCount;
            } else {
                indexList.add(k++);
            }
        }

        for (int iIndexList = 0; iIndexList < indexList.size(); ++iIndexList) {
            newJsonPartInfoList.add(jsonPartInfoList.get(indexList.get(iIndexList)));
        }
        return newJsonPartInfoList;
    }

    private ArrayList<JsonPartInfo_new> sortByActionNo(ArrayList<JsonPartInfo_new> jsonPartInfoList) {
        ArrayList<JsonPartInfo_new> newJsonPartInfoList = new ArrayList<>();
        ArrayList<Integer> indexList = new ArrayList<>();
        boolean isInsert = false;
        for (int i = 0; i < jsonPartInfoList.size(); ++i) {
            JsonPartInfo_new jsonPartInfo = jsonPartInfoList.get(i);
            JsonActionRefInfo jsonActionRefInfo = jsonPartInfo.getActionRefInfo();
            String actionNo = jsonActionRefInfo.getActionNo();
            if (actionNo == null) {
                continue;
            }
            int iActionNo = Integer.parseInt(actionNo);
            if (indexList.size() == 0) {
                indexList.add(i);
            } else {
                for (int j = 0; j < indexList.size(); ++j) {
                    JsonPartInfo_new jsonPartInfo1 = jsonPartInfoList.get(indexList.get(j));
                    JsonActionRefInfo jsonActionRefInfo1 = jsonPartInfo1.getActionRefInfo();
                    int iActionNo1 = Integer.parseInt(jsonActionRefInfo1.getActionNo());
                    if (iActionNo < iActionNo1) {
                        indexList.add(j, i);
                        isInsert = true;
                        break;
                    } else {
                        isInsert = false;
                    }
                }
                if (!isInsert) {
                    indexList.add(i);
                }
            }
        }
        for (int iIndexList = 0; iIndexList < indexList.size(); ++iIndexList) {
            newJsonPartInfoList.add(jsonPartInfoList.get(indexList.get(iIndexList)));
        }
        return sortByActionStateNo(newJsonPartInfoList);
    }

    private ArrayList<JsonPartInfo_new> sortByActionStateNo(ArrayList<JsonPartInfo_new> jsonPartInfoList) {
        ArrayList<JsonPartInfo_new> newJsonPartInfoList = new ArrayList<>();
        ArrayList<Integer> indexList = new ArrayList<>();
        HashMap<String, Integer> actionNOSameNumMap = new HashMap<>();
        for (int i = 0; i < jsonPartInfoList.size(); ++i) {
            JsonPartInfo_new jsonPartInfo = jsonPartInfoList.get(i);
            JsonActionRefInfo jsonActionRefInfo = jsonPartInfo.getActionRefInfo();
            String actionNo = jsonActionRefInfo.getActionNo();
            int sameNoCount = 0;
            for (int j = 0; j < jsonPartInfoList.size(); ++j) {
                JsonPartInfo_new jsonPartInfo1 = jsonPartInfoList.get(j);
                JsonActionRefInfo jsonActionRefInfo1 = jsonPartInfo1.getActionRefInfo();
                String actionNo1 = jsonActionRefInfo1.getActionNo();
                if (actionNo.equals(actionNo1)) {
                    sameNoCount++;
                } else if (!actionNo.equals(actionNo1) && sameNoCount != 0) {
                    break;
                }
            }
            actionNOSameNumMap.put(actionNo, sameNoCount);
        }

        for (int k = 0; k < jsonPartInfoList.size();) {
            JsonPartInfo_new jsonPartInfo = jsonPartInfoList.get(k);
            JsonActionRefInfo jsonActionRefInfo = jsonPartInfo.getActionRefInfo();
            String actionNo = jsonActionRefInfo.getActionNo();
            Integer value = actionNOSameNumMap.get(actionNo);
            int sameNoCount = value.intValue();
            if (sameNoCount > 1) {
                ArrayList<Integer> subIndexList = new ArrayList<>();
                boolean isInsert = false;
                for (int num = 0; num < sameNoCount; ++num) {
                    JsonPartInfo_new jsonPartInfo1 = jsonPartInfoList.get(k + num);
                    JsonActionRefInfo jsonActionRefInfo1 = jsonPartInfo1.getActionRefInfo();
                    int actionStateNo1 = Integer.valueOf(jsonActionRefInfo1.getActionStateNo());
                    if (subIndexList.size() == 0) {
                        subIndexList.add(k + num);
                    } else {
                        for (int iSubIndex = 0; iSubIndex < subIndexList.size(); ++iSubIndex) {
                            JsonPartInfo_new jsonPartInfo2 = jsonPartInfoList.get(subIndexList.get(iSubIndex));
                            JsonActionRefInfo jsonActionRefInfo2 = jsonPartInfo2.getActionRefInfo();
                            int actionStateNo2 = Integer.valueOf(jsonActionRefInfo2.getActionStateNo());
                            if (actionStateNo1 < actionStateNo2) {
                                subIndexList.add(iSubIndex, k + num);
                                isInsert = true;
                                break;
                            } else {
                                isInsert = false;
                            }
                        }
                        if (!isInsert) {
                            subIndexList.add(k + num);
                        }
                    }
                }

                for (int iSubIndex = 0; iSubIndex < subIndexList.size(); ++iSubIndex) {
                    indexList.add(subIndexList.get(iSubIndex));
                }
                k += sameNoCount;
            } else {
                indexList.add(k++);
            }
        }

        for (int iIndexList = 0; iIndexList < indexList.size(); ++iIndexList) {
            newJsonPartInfoList.add(jsonPartInfoList.get(indexList.get(iIndexList)));
        }
        return newJsonPartInfoList;
    }

    private ArrayList<JsonPartInfo_new> sortByHKNo(ArrayList<JsonPartInfo_new> jsonPartInfoList) {
        ArrayList<JsonPartInfo_new> newJsonPartInfoList = new ArrayList<>();
        ArrayList<Integer> indexList = new ArrayList<>();
        boolean isInsert = false;
        for (int i = 0; i < jsonPartInfoList.size(); ++i) {
            JsonPartInfo_new jsonPartInfo = jsonPartInfoList.get(i);
            JsonHKRefInfo jsonHKRefInfo = jsonPartInfo.getHkRefInfo();
            String HKNo = jsonHKRefInfo.getHKNo();
            if (HKNo == null) {
                continue;
            }
            int iHKNo = Integer.parseInt(HKNo);
            if (indexList.size() == 0) {
                indexList.add(i);
            } else {
                for (int j = 0; j < indexList.size(); ++j) {
                    JsonPartInfo_new jsonPartInfo1 = jsonPartInfoList.get(indexList.get(j));
                    JsonHKRefInfo jsonHKRefInfo1 = jsonPartInfo1.getHkRefInfo();
                    int iHKNo1 = Integer.parseInt(jsonHKRefInfo1.getHKNo());
                    if (iHKNo < iHKNo1) {
                        indexList.add(j, i);
                        isInsert = true;
                        break;
                    } else {
                        isInsert = false;
                    }
                }
                if (!isInsert) {
                    indexList.add(i);
                }
            }
        }
        for (int iIndexList = 0; iIndexList < indexList.size(); ++iIndexList) {
            newJsonPartInfoList.add(jsonPartInfoList.get(indexList.get(iIndexList)));
        }
        return sortByHKStateNo(newJsonPartInfoList);
    }

    private ArrayList<JsonPartInfo_new> sortByHKStateNo(ArrayList<JsonPartInfo_new> jsonPartInfoList) {
        ArrayList<JsonPartInfo_new> newJsonPartInfoList = new ArrayList<>();
        ArrayList<Integer> indexList = new ArrayList<>();
        HashMap<String, Integer> HKNOSameNumMap = new HashMap<>();
        for (int i = 0; i < jsonPartInfoList.size(); ++i) {
            JsonPartInfo_new jsonPartInfo = jsonPartInfoList.get(i);
            JsonHKRefInfo jsonHKRefInfo = jsonPartInfo.getHkRefInfo();
            String HKNo = jsonHKRefInfo.getHKNo();
            int sameNoCount = 0;
            for (int j = 0; j < jsonPartInfoList.size(); ++j) {
                JsonPartInfo_new jsonPartInfo1 = jsonPartInfoList.get(j);
                JsonHKRefInfo jsonHKRefInfo1 = jsonPartInfo1.getHkRefInfo();
                String HKNo1 = jsonHKRefInfo1.getHKNo();
                if (HKNo.equals(HKNo1)) {
                    sameNoCount++;
                } else if (!HKNo.equals(HKNo1) && sameNoCount != 0) {
                    break;
                }
            }
            HKNOSameNumMap.put(HKNo, sameNoCount);
        }

        for (int k = 0; k < jsonPartInfoList.size();) {
            JsonPartInfo_new jsonPartInfo = jsonPartInfoList.get(k);
            JsonHKRefInfo jsonHKRefInfo = jsonPartInfo.getHkRefInfo();
            String HKNo = jsonHKRefInfo.getHKNo();
            Integer value = HKNOSameNumMap.get(HKNo);
            int sameNoCount = value.intValue();
            if (sameNoCount > 1) {
                ArrayList<Integer> subIndexList = new ArrayList<>();
                boolean isInsert = false;
                for (int num = 0; num < sameNoCount; ++num) {
                    JsonPartInfo_new jsonPartInfo1 = jsonPartInfoList.get(k + num);
                    JsonHKRefInfo jsonHKRefInfo1 = jsonPartInfo1.getHkRefInfo();
                    int HKStateNo1 = Integer.valueOf(jsonHKRefInfo1.getHKStateNo());
                    if (subIndexList.size() == 0) {
                        subIndexList.add(k + num);
                    } else {
                        for (int iSubIndex = 0; iSubIndex < subIndexList.size(); ++iSubIndex) {
                            JsonPartInfo_new jsonPartInfo2 = jsonPartInfoList.get(subIndexList.get(iSubIndex));
                            JsonHKRefInfo jsonHKRefInfo2 = jsonPartInfo2.getHkRefInfo();
                            int HKStateNo2 = Integer.valueOf(jsonHKRefInfo2.getHKStateNo());
                            if (HKStateNo1 < HKStateNo2) {
                                subIndexList.add(iSubIndex, k + num);
                                isInsert = true;
                                break;
                            } else {
                                isInsert = false;
                            }
                        }
                        if (!isInsert) {
                            subIndexList.add(k + num);
                        }
                    }
                }

                for (int iSubIndex = 0; iSubIndex < subIndexList.size(); ++iSubIndex) {
                    indexList.add(subIndexList.get(iSubIndex));
                }
                k += sameNoCount;
            } else {
                indexList.add(k++);
            }
        }

        for (int iIndexList = 0; iIndexList < indexList.size(); ++iIndexList) {
            newJsonPartInfoList.add(jsonPartInfoList.get(indexList.get(iIndexList)));
        }
        return newJsonPartInfoList;
    }

    private ArrayList<JsonPartInfo_new> sortByInitNo(ArrayList<JsonPartInfo_new> jsonPartInfoList) {
        ArrayList<JsonPartInfo_new> newJsonPartInfoList = new ArrayList<>();
        ArrayList<Integer> indexList = new ArrayList<>();
        boolean isInsert = false;
        for (int i = 0; i < jsonPartInfoList.size(); ++i) {
            JsonPartInfo_new jsonPartInfo = jsonPartInfoList.get(i);
            JsonInitRefInfo jsonInitRefInfo = jsonPartInfo.getInitRefInfo();
            String initNo = jsonInitRefInfo.getInitNo();
            if (initNo == null) {
                continue;
            }
            int iInitNo = Integer.parseInt(initNo);
            if (indexList.size() == 0) {
                indexList.add(i);
            } else {
                for (int j = 0; j < indexList.size(); ++j) {
                    JsonPartInfo_new jsonPartInfo1 = jsonPartInfoList.get(indexList.get(j));
                    JsonInitRefInfo jsonInitRefInfo1 = jsonPartInfo1.getInitRefInfo();
                    int iInitNo1 = Integer.parseInt(jsonInitRefInfo1.getInitNo());
                    if (iInitNo < iInitNo1) {
                        indexList.add(j, i);
                        isInsert = true;
                        break;
                    } else {
                        isInsert = false;
                    }
                }
                if (!isInsert) {
                    indexList.add(i);
                }
            }
        }
        for (int iIndexList = 0; iIndexList < indexList.size(); ++iIndexList) {
            newJsonPartInfoList.add(jsonPartInfoList.get(indexList.get(iIndexList)));
        }
        return sortByInitStateNo(newJsonPartInfoList);
    }

    private ArrayList<JsonPartInfo_new> sortByInitStateNo(ArrayList<JsonPartInfo_new> jsonPartInfoList) {
        ArrayList<JsonPartInfo_new> newJsonPartInfoList = new ArrayList<>();
        ArrayList<Integer> indexList = new ArrayList<>();
        HashMap<String, Integer> initNOSameNumMap = new HashMap<>();
        for (int i = 0; i < jsonPartInfoList.size(); ++i) {
            JsonPartInfo_new jsonPartInfo = jsonPartInfoList.get(i);
            JsonInitRefInfo jsonInitRefInfo = jsonPartInfo.getInitRefInfo();
            String initNo = jsonInitRefInfo.getInitNo();
            int sameNoCount = 0;
            for (int j = 0; j < jsonPartInfoList.size(); ++j) {
                JsonPartInfo_new jsonPartInfo1 = jsonPartInfoList.get(j);
                JsonInitRefInfo jsonInitRefInfo1 = jsonPartInfo1.getInitRefInfo();
                String initNo1 = jsonInitRefInfo1.getInitNo();
                if (initNo.equals(initNo1)) {
                    sameNoCount++;
                } else if (!initNo.equals(initNo1) && sameNoCount != 0) {
                    break;
                }
            }
            initNOSameNumMap.put(initNo, sameNoCount);
        }

        for (int k = 0; k < jsonPartInfoList.size();) {
            JsonPartInfo_new jsonPartInfo = jsonPartInfoList.get(k);
            JsonInitRefInfo jsonInitRefInfo = jsonPartInfo.getInitRefInfo();
            String initNo = jsonInitRefInfo.getInitNo();
            Integer value = initNOSameNumMap.get(initNo);
            int sameNoCount = value.intValue();
            if (sameNoCount > 1) {
                ArrayList<Integer> subIndexList = new ArrayList<>();
                boolean isInsert = false;
                for (int num = 0; num < sameNoCount; ++num) {
                    JsonPartInfo_new jsonPartInfo1 = jsonPartInfoList.get(k + num);
                    JsonInitRefInfo jsonInitRefInfo1 = jsonPartInfo1.getInitRefInfo();
                    int initStateNo1 = Integer.valueOf(jsonInitRefInfo1.getInitStateNo());
                    if (subIndexList.size() == 0) {
                        subIndexList.add(k + num);
                    } else {
                        for (int iSubIndex = 0; iSubIndex < subIndexList.size(); ++iSubIndex) {
                            JsonPartInfo_new jsonPartInfo2 = jsonPartInfoList.get(subIndexList.get(iSubIndex));
                            JsonInitRefInfo jsonInitRefInfo2 = jsonPartInfo2.getInitRefInfo();
                            int initStateNo2 = Integer.valueOf(jsonInitRefInfo2.getInitStateNo());
                            if (initStateNo1 < initStateNo2) {
                                subIndexList.add(iSubIndex, k + num);
                                isInsert = true;
                                break;
                            } else {
                                isInsert = false;
                            }
                        }
                        if (!isInsert) {
                            subIndexList.add(k + num);
                        }
                    }
                }

                for (int iSubIndex = 0; iSubIndex < subIndexList.size(); ++iSubIndex) {
                    indexList.add(subIndexList.get(iSubIndex));
                }
                k += sameNoCount;
            } else {
                indexList.add(k++);
            }
        }

        for (int iIndexList = 0; iIndexList < indexList.size(); ++iIndexList) {
            newJsonPartInfoList.add(jsonPartInfoList.get(indexList.get(iIndexList)));
        }
        return newJsonPartInfoList;
    }

    private ArrayList<JsonPartInfo_new> sortByStatusChangeNo(ArrayList<JsonPartInfo_new> jsonPartInfoList) {
        ArrayList<JsonPartInfo_new> newJsonPartInfoList = new ArrayList<>();
        ArrayList<Integer> indexList = new ArrayList<>();
        boolean isInsert = false;
        for (int i = 0; i < jsonPartInfoList.size(); ++i) {
            JsonPartInfo_new jsonPartInfo = jsonPartInfoList.get(i);
            JsonStatusChangeRefInfo jsonStatusChangeRefInfo = jsonPartInfo.getStatusChangeRefInfo();
            String statusChangeNo = jsonStatusChangeRefInfo.getStatusChangeNo();
            if (statusChangeNo == null) {
                continue;
            }
            int iStatusChangeNo = Integer.parseInt(statusChangeNo);
            if (indexList.size() == 0) {
                indexList.add(i);
            } else {
                for (int j = 0; j < indexList.size(); ++j) {
                    JsonPartInfo_new jsonPartInfo1 = jsonPartInfoList.get(indexList.get(j));
                    JsonStatusChangeRefInfo jsonStatusChangeRefInfo1 = jsonPartInfo1.getStatusChangeRefInfo();
                    int iStatusChangeNo1 = Integer.parseInt(jsonStatusChangeRefInfo1.getStatusChangeNo());
                    if (iStatusChangeNo < iStatusChangeNo1) {
                        indexList.add(j, i);
                        isInsert = true;
                        break;
                    } else {
                        isInsert = false;
                    }
                }
                if (!isInsert) {
                    indexList.add(i);
                }
            }
        }
        for (int iIndexList = 0; iIndexList < indexList.size(); ++iIndexList) {
            newJsonPartInfoList.add(jsonPartInfoList.get(indexList.get(iIndexList)));
        }
        return sortByStatusChangeStateNo(newJsonPartInfoList);
    }

    private ArrayList<JsonPartInfo_new> sortByStatusChangeStateNo(ArrayList<JsonPartInfo_new> jsonPartInfoList) {
        ArrayList<JsonPartInfo_new> newJsonPartInfoList = new ArrayList<>();
        ArrayList<Integer> indexList = new ArrayList<>();
        HashMap<String, Integer> statusChangeNOSameNumMap = new HashMap<>();
        for (int i = 0; i < jsonPartInfoList.size(); ++i) {
            JsonPartInfo_new jsonPartInfo = jsonPartInfoList.get(i);
            JsonStatusChangeRefInfo jsonStatusChangeRefInfo = jsonPartInfo.getStatusChangeRefInfo();
            String statusChangeNo = jsonStatusChangeRefInfo.getStatusChangeNo();
            int sameNoCount = 0;
            for (int j = 0; j < jsonPartInfoList.size(); ++j) {
                JsonPartInfo_new jsonPartInfo1 = jsonPartInfoList.get(j);
                JsonStatusChangeRefInfo jsonStatusChangeRefInfo1 = jsonPartInfo1.getStatusChangeRefInfo();
                String statusChangeNo1 = jsonStatusChangeRefInfo1.getStatusChangeNo();
                if (statusChangeNo.equals(statusChangeNo1)) {
                    sameNoCount++;
                } else if (!statusChangeNo.equals(statusChangeNo1) && sameNoCount != 0) {
                    break;
                }
            }
            statusChangeNOSameNumMap.put(statusChangeNo, sameNoCount);
        }

        for (int k = 0; k < jsonPartInfoList.size();) {
            JsonPartInfo_new jsonPartInfo = jsonPartInfoList.get(k);
            JsonStatusChangeRefInfo jsonStatusChangeRefInfo = jsonPartInfo.getStatusChangeRefInfo();
            String statusChangeNo = jsonStatusChangeRefInfo.getStatusChangeNo();
            Integer value = statusChangeNOSameNumMap.get(statusChangeNo);
            int sameNoCount = value.intValue();
            if (sameNoCount > 1) {
                ArrayList<Integer> subIndexList = new ArrayList<>();
                boolean isInsert = false;
                for (int num = 0; num < sameNoCount; ++num) {
                    JsonPartInfo_new jsonPartInfo1 = jsonPartInfoList.get(k + num);
                    JsonStatusChangeRefInfo jsonStatusChangeRefInfo1 = jsonPartInfo1.getStatusChangeRefInfo();
                    int statusChangeNo1 = Integer.valueOf(jsonStatusChangeRefInfo1.getStatusChangeStateNo());
                    if (subIndexList.size() == 0) {
                        subIndexList.add(k + num);
                    } else {
                        for (int iSubIndex = 0; iSubIndex < subIndexList.size(); ++iSubIndex) {
                            JsonPartInfo_new jsonPartInfo2 = jsonPartInfoList.get(subIndexList.get(iSubIndex));
                            JsonStatusChangeRefInfo jsonStatusChangeRefInfo2 = jsonPartInfo2.getStatusChangeRefInfo();
                            int statusChangeNo2 = Integer.valueOf(jsonStatusChangeRefInfo2.getStatusChangeStateNo());
                            if (statusChangeNo1 < statusChangeNo2) {
                                subIndexList.add(iSubIndex, k + num);
                                isInsert = true;
                                break;
                            } else {
                                isInsert = false;
                            }
                        }
                        if (!isInsert) {
                            subIndexList.add(k + num);
                        }
                    }
                }

                for (int iSubIndex = 0; iSubIndex < subIndexList.size(); ++iSubIndex) {
                    indexList.add(subIndexList.get(iSubIndex));
                }
                k += sameNoCount;
            } else {
                indexList.add(k++);
            }
        }

        for (int iIndexList = 0; iIndexList < indexList.size(); ++iIndexList) {
            newJsonPartInfoList.add(jsonPartInfoList.get(indexList.get(iIndexList)));
        }
        return newJsonPartInfoList;
    }

    private ArrayList<JsonPartInfo_new> sortByTransitionNo(ArrayList<JsonPartInfo_new> jsonPartInfoList) {
        ArrayList<JsonPartInfo_new> newJsonPartInfoList = new ArrayList<>();
        ArrayList<Integer> indexList = new ArrayList<>();
        boolean isInsert = false;
        for (int i = 0; i < jsonPartInfoList.size(); ++i) {
            JsonPartInfo_new jsonPartInfo = jsonPartInfoList.get(i);
            JsonTransitionRefInfo jsonTransitionRefInfo = jsonPartInfo.getTransitionRefInfo();
            String transitionNo = jsonTransitionRefInfo.getTransitionNo();
            if (transitionNo == null) {
                continue;
            }
            int iTransitionNo = Integer.parseInt(transitionNo);
            if (indexList.size() == 0) {
                indexList.add(i);
            } else {
                for (int j = 0; j < indexList.size(); ++j) {
                    JsonPartInfo_new jsonPartInfo1 = jsonPartInfoList.get(indexList.get(j));
                    JsonTransitionRefInfo jsonTransitionRefInfo1 = jsonPartInfo1.getTransitionRefInfo();
                    int iTransitionNo1 = Integer.parseInt(jsonTransitionRefInfo1.getTransitionNo());
                    if (iTransitionNo < iTransitionNo1) {
                        indexList.add(j, i);
                        isInsert = true;
                        break;
                    } else {
                        isInsert = false;
                    }
                }
                if (!isInsert) {
                    indexList.add(i);
                }
            }
        }
        for (int iIndexList = 0; iIndexList < indexList.size(); ++iIndexList) {
            newJsonPartInfoList.add(jsonPartInfoList.get(indexList.get(iIndexList)));
        }
        return sortByTransitionStateNo(newJsonPartInfoList);
    }

    private ArrayList<JsonPartInfo_new> sortByTransitionStateNo(ArrayList<JsonPartInfo_new> jsonPartInfoList) {
        ArrayList<JsonPartInfo_new> newJsonPartInfoList = new ArrayList<>();
        ArrayList<Integer> indexList = new ArrayList<>();
        HashMap<String, Integer> transitionNOSameNumMap = new HashMap<>();
        for (int i = 0; i < jsonPartInfoList.size(); ++i) {
            JsonPartInfo_new jsonPartInfo = jsonPartInfoList.get(i);
            JsonTransitionRefInfo jsonTransitionRefInfo = jsonPartInfo.getTransitionRefInfo();
            String transitionNo = jsonTransitionRefInfo.getTransitionNo();
            int sameNoCount = 0;
            for (int j = 0; j < jsonPartInfoList.size(); ++j) {
                JsonPartInfo_new jsonPartInfo1 = jsonPartInfoList.get(j);
                JsonTransitionRefInfo jsonTransitionRefInfo1 = jsonPartInfo1.getTransitionRefInfo();
                String transitionNo1 = jsonTransitionRefInfo1.getTransitionNo();
                if (transitionNo.equals(transitionNo1)) {
                    sameNoCount++;
                } else if (!transitionNo.equals(transitionNo1) && sameNoCount != 0) {
                    break;
                }
            }
            transitionNOSameNumMap.put(transitionNo, sameNoCount);
        }

        for (int k = 0; k < jsonPartInfoList.size();) {
            JsonPartInfo_new jsonPartInfo = jsonPartInfoList.get(k);
            JsonTransitionRefInfo jsonTransitionRefInfo = jsonPartInfo.getTransitionRefInfo();
            String transitionNo = jsonTransitionRefInfo.getTransitionNo();
            Integer value = transitionNOSameNumMap.get(transitionNo);
            int sameNoCount = value.intValue();
            if (sameNoCount > 1) {
                ArrayList<Integer> subIndexList = new ArrayList<>();
                boolean isInsert = false;
                for (int num = 0; num < sameNoCount; ++num) {
                    JsonPartInfo_new jsonPartInfo1 = jsonPartInfoList.get(k + num);
                    JsonTransitionRefInfo jsonTransitionRefInfo1 = jsonPartInfo1.getTransitionRefInfo();
                    int transitionNo1 = Integer.valueOf(jsonTransitionRefInfo1.getTransitionStateNo());
                    if (subIndexList.size() == 0) {
                        subIndexList.add(k + num);
                    } else {
                        for (int iSubIndex = 0; iSubIndex < subIndexList.size(); ++iSubIndex) {
                            JsonPartInfo_new jsonPartInfo2 = jsonPartInfoList.get(subIndexList.get(iSubIndex));
                            JsonTransitionRefInfo jsonTransitionRefInfo2 = jsonPartInfo2.getTransitionRefInfo();
                            int transitionNo2 = Integer.valueOf(jsonTransitionRefInfo2.getTransitionStateNo());
                            if (transitionNo1 < transitionNo2) {
                                subIndexList.add(iSubIndex, k + num);
                                isInsert = true;
                                break;
                            } else {
                                isInsert = false;
                            }
                        }
                        if (!isInsert) {
                            subIndexList.add(k + num);
                        }
                    }
                }

                for (int iSubIndex = 0; iSubIndex < subIndexList.size(); ++iSubIndex) {
                    indexList.add(subIndexList.get(iSubIndex));
                }
                k += sameNoCount;
            } else {
                indexList.add(k++);
            }
        }

        for (int iIndexList = 0; iIndexList < indexList.size(); ++iIndexList) {
            newJsonPartInfoList.add(jsonPartInfoList.get(indexList.get(iIndexList)));
        }
        return newJsonPartInfoList;
    }

    private ArrayList<JsonPartInfo_new> sortByTrigNo(ArrayList<JsonPartInfo_new> jsonPartInfoList) {
        ArrayList<JsonPartInfo_new> newJsonPartInfoList = new ArrayList<>();
        ArrayList<Integer> indexList = new ArrayList<>();
        boolean isInsert = false;
        for (int i = 0; i < jsonPartInfoList.size(); ++i) {
            JsonPartInfo_new jsonPartInfo = jsonPartInfoList.get(i);
            JsonTrigRefInfo jsonTrigRefInfo = jsonPartInfo.getTrigRefInfo();
            String trigNo = jsonTrigRefInfo.getTrigNo();
            if (trigNo == null) {
                continue;
            }
            int iTrigNo = Integer.parseInt(trigNo);
            if (indexList.size() == 0) {
                indexList.add(i);
            } else {
                for (int j = 0; j < indexList.size(); ++j) {
                    JsonPartInfo_new jsonPartInfo1 = jsonPartInfoList.get(indexList.get(j));
                    JsonTrigRefInfo jsonTrigRefInfo1 = jsonPartInfo1.getTrigRefInfo();
                    int iTrigNo1 = Integer.parseInt(jsonTrigRefInfo1.getTrigNo());
                    if (iTrigNo < iTrigNo1) {
                        indexList.add(j, i);
                        isInsert = true;
                        break;
                    } else {
                        isInsert = false;
                    }
                }
                if (!isInsert) {
                    indexList.add(i);
                }
            }
        }
        for (int iIndexList = 0; iIndexList < indexList.size(); ++iIndexList) {
            newJsonPartInfoList.add(jsonPartInfoList.get(indexList.get(iIndexList)));
        }
        return sortByTrigStateNo(newJsonPartInfoList);
    }

    private ArrayList<JsonPartInfo_new> sortByTrigStateNo(ArrayList<JsonPartInfo_new> jsonPartInfoList) {
        ArrayList<JsonPartInfo_new> newJsonPartInfoList = new ArrayList<>();
        ArrayList<Integer> indexList = new ArrayList<>();
        HashMap<String, Integer> trigNOSameNumMap = new HashMap<>();
        for (int i = 0; i < jsonPartInfoList.size(); ++i) {
            JsonPartInfo_new jsonPartInfo = jsonPartInfoList.get(i);
            JsonTrigRefInfo jsonTrigRefInfo = jsonPartInfo.getTrigRefInfo();
            String trigNo = jsonTrigRefInfo.getTrigNo();
            int sameNoCount = 0;
            for (int j = 0; j < jsonPartInfoList.size(); ++j) {
                JsonPartInfo_new jsonPartInfo1 = jsonPartInfoList.get(j);
                JsonTrigRefInfo jsonTrigRefInfo1 = jsonPartInfo1.getTrigRefInfo();
                String trigNo1 = jsonTrigRefInfo1.getTrigNo();
                if (trigNo.equals(trigNo1)) {
                    sameNoCount++;
                } else if (!trigNo.equals(trigNo1) && sameNoCount != 0) {
                    break;
                }
            }
            trigNOSameNumMap.put(trigNo, sameNoCount);
        }

        for (int k = 0; k < jsonPartInfoList.size();) {
            JsonPartInfo_new jsonPartInfo = jsonPartInfoList.get(k);
            JsonTrigRefInfo jsonTrigRefInfo = jsonPartInfo.getTrigRefInfo();
            String trigNo = jsonTrigRefInfo.getTrigNo();
            Integer value = trigNOSameNumMap.get(trigNo);
            int sameNoCount = value.intValue();
            if (sameNoCount > 1) {
                ArrayList<Integer> subIndexList = new ArrayList<>();
                boolean isInsert = false;
                for (int num = 0; num < sameNoCount; ++num) {
                    JsonPartInfo_new jsonPartInfo1 = jsonPartInfoList.get(k + num);
                    JsonTrigRefInfo jsonTrigRefInfo1 = jsonPartInfo1.getTrigRefInfo();
                    int trigStateNo1 = Integer.valueOf(jsonTrigRefInfo1.getTrigStateNo());
                    if (subIndexList.size() == 0) {
                        subIndexList.add(k + num);
                    } else {
                        for (int iSubIndex = 0; iSubIndex < subIndexList.size(); ++iSubIndex) {
                            JsonPartInfo_new jsonPartInfo2 = jsonPartInfoList.get(subIndexList.get(iSubIndex));
                            JsonTrigRefInfo jsonTrigRefInfo2 = jsonPartInfo2.getTrigRefInfo();
                            int trigStateNo2 = Integer.valueOf(jsonTrigRefInfo2.getTrigStateNo());
                            if (trigStateNo1 < trigStateNo2) {
                                subIndexList.add(iSubIndex, k + num);
                                isInsert = true;
                                break;
                            } else {
                                isInsert = false;
                            }
                        }
                        if (!isInsert) {
                            subIndexList.add(k + num);
                        }
                    }
                }

                for (int iSubIndex = 0; iSubIndex < subIndexList.size(); ++iSubIndex) {
                    indexList.add(subIndexList.get(iSubIndex));
                }
                k += sameNoCount;
            } else {
                indexList.add(k++);
            }
        }

        for (int iIndexList = 0; iIndexList < indexList.size(); ++iIndexList) {
            newJsonPartInfoList.add(jsonPartInfoList.get(indexList.get(iIndexList)));
        }
        return newJsonPartInfoList;
    }

    private ArrayList<String> sortDevName(ArrayList<String> hkDevNameList) {
        ArrayList<String> newHKDevNameList = new ArrayList<>();
        boolean isInsert = false;
        for (int i = 0; i < hkDevNameList.size(); ++i) {
            String devName = hkDevNameList.get(i).toUpperCase();
            if (indexDevNameList.size() == 0) {
                indexDevNameList.add(i);
            } else {
                for (int j = 0; j < indexDevNameList.size(); ++j) {
                    String devNameIndex = hkDevNameList.get(indexDevNameList.get(j)).toUpperCase();
                    if (devName.compareTo(devNameIndex) < 0 || devName.compareTo(devNameIndex) == 0) {
                        indexDevNameList.add(j, i);
                        isInsert = true;
                        break;
                    } else {
                        isInsert = false;
                    }
                }
                if (!isInsert) {
                    indexDevNameList.add(i);
                }
            }
        }
        for (int iIndexList = 0; iIndexList < indexDevNameList.size(); ++iIndexList) {
            newHKDevNameList.add(hkDevNameList.get(indexDevNameList.get(iIndexList)));
        }
        return newHKDevNameList;
    }

    private ArrayList<String> sortDevType(ArrayList<String> hkDevTypeList) {
        ArrayList<String> newHKDevTypeList = new ArrayList<>();
        if (hkDevTypeList.size() == indexDevNameList.size()) {
            for (int i = 0; i < indexDevNameList.size(); ++i) {
                newHKDevTypeList.add(hkDevTypeList.get(indexDevNameList.get(i)));
            }
        } else {
            for (int j = 0; j < indexDevNameList.size(); ++j) {
                int index = indexDevNameList.get(j);
                if (index >= hkDevTypeList.size()) {
                    String str = "Invalud_" + index;
                    newHKDevTypeList.add(str);
                } else {
                    newHKDevTypeList.add(hkDevTypeList.get(index));
                }
            }
        }

        return newHKDevTypeList;
    }

    private void setDropDownList(XSSFSheet xssfSheet, String posInfo, String[] data) {
        // 设置下拉框
        XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(xssfSheet);
        XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint)dvHelper.createExplicitListConstraint(data);
        CellRangeAddressList addressList = null;
        XSSFDataValidation validation = null;

        int[] iRows = getPosRow(posInfo);
        String[] sCols = getPosCol(posInfo);
        int iCol1 = excelColStrToNum(sCols[0], sCols[0].length());
        int iCol2 = excelColStrToNum(sCols[1], sCols[1].length());

        addressList = new CellRangeAddressList(iRows[0] - 1, iRows[1] - 1, iCol1 - 1, iCol2 - 1);
        validation = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
        xssfSheet.addValidationData(validation);
    }

    private void setLinkage(int row, int col, String formulaString) {
        // 设置联动
        XSSFRow xssfRow = this.sheetInfo.getRow(row - 1);
        if (xssfRow.getCell(col - 1) == null) {
            xssfRow.createCell(col - 1).setCellFormula(formulaString);
        } else {
            xssfRow.getCell(col - 1).setCellFormula(formulaString);
        }
    }

    private int excelColStrToNum(String colStr, int length) {
        int num = 0;
        int result = 0;
        for(int i = 0; i < length; i++) {
            char ch = colStr.charAt(length - i - 1);
            num = (int)(ch - 'A' + 1) ;
            num *= Math.pow(26, i);
            result += num;
        }
        return result;
    }

    private String getPosColSingle(String posInfo) {
        String col = "";
        for (int i = 0; i < posInfo.length(); ++i) {
            char ch = posInfo.charAt(i);
            if ((ch >= 65 && ch <= 90) || (ch >= 97 && ch <= 122)) {
                col += String.valueOf(ch);
            } else {
                break;
            }
        }
        return col;
    }

    private int getPosRowSingle(String posInfo) {
        int row = 0;
        for (int i = 0; i < posInfo.length(); ++i) {
            char ch = posInfo.charAt(i);
            if (ch < 48 || ch >57) {
                // do nothing
            } else {
                row = Integer.parseInt(posInfo.substring(i));
                break;
            }
        }
        return row;
    }


    private int[] getPosRow(String posInfo) {
        String[] pos = posInfo.split(":");
        int[] posRow = new int[2];
        for (int i = 0; i < pos[0].length(); ++i) {
            char ch = pos[0].charAt(i);
            if (ch < 48 || ch >57) {
                // do nothing
            } else {
                posRow[0] = Integer.parseInt(pos[0].substring(i));
                break;
            }
        }

        for (int j = 0; j < pos[1].length(); ++j) {
            char ch = pos[1].charAt(j);
            if (ch < 48 || ch >57) {
                // do nothing
            } else {
                posRow[1] = Integer.parseInt(pos[1].substring(j));
                break;
            }
        }

        return posRow;
    }

    private String[] getPosCol(String posInfo) {
        String[] pos = posInfo.split(":");
        String pos_0 = "";
        String pos_1 = "";
        int i = 0;
        int j = 0;
        while (i < pos[0].length()) {
            char ch = pos[0].charAt(i++);
            if ((ch >= 65 && ch <= 90) || (ch >= 97 && ch <= 122)) {
                pos_0 += String.valueOf(ch);
            }
            else {
                break;
            }
        }

        while (j < pos[1].length()) {
            char ch = pos[1].charAt(j++);
            if ((ch >= 65 && ch <= 90) || (ch >= 97 && ch <= 122)) {
                pos_1 += String.valueOf(ch);
            }
            else {
                break;
            }
        }

        String[] posCol = new String[2];
        posCol[0] = pos_0;
        posCol[1] = pos_1;

        return posCol;
    }

    private String getNewCol(String pos, int count) {
        char[] newPos = new char[pos.length()];
        int i = 0;
        while (i < pos.length()) {
            char ch = pos.charAt(i);
            if (++i < pos.length()) {
                newPos[i - 1] = ch;
            }
            else {
                ch += count;
                newPos[i - 1] = ch;
                break;
            }
        }
        String newPosStr = String.valueOf(newPos);
        return newPosStr;
    }

    // condition除外
    private boolean isMergeRow(int rowCount) {
        return rowCount > 1 ? true : false;
    }

    private int getDisplayRefInfoRowCount(JsonDisplayRefInfo displayRefInfo) {
        int rowCount = 1;
        HashMap<String, String> displayCondition = displayRefInfo.getDisplayCondition();
        if (displayCondition != null) {
            rowCount = displayCondition.size() == 0 ? 1 : displayCondition.size();
        }
        return rowCount;
    }

    private int getActiveRefInfoRowCount(JsonActiveRefInfo activeRefInfo) {
        int rowCount = 1;
        HashMap<String, String> activeCondition = activeRefInfo.getActiveCondition();
        if (activeCondition != null) {
            rowCount = activeCondition.size() == 0 ? 1 : activeCondition.size();
        }
        return rowCount;
    }

    private int getActionRefInfoRowCount(JsonActionRefInfo actionRefInfo) {
        int rowCount = 1;
        HashMap<String, String> actionCondition = actionRefInfo.getActionCondition();
        if (actionCondition != null) {
            rowCount = actionCondition.size() == 0 ? 1 : actionCondition.size();
        }
        return rowCount;
    }

    private int getHKRefInfoRowCount(JsonHKRefInfo hkRefInfo) {
        int rowCount = 1;
        HashMap<String, String> hkCondition = hkRefInfo.getHKCondition();
        if (hkCondition != null) {
            rowCount = hkCondition.size() == 0 ? 1 : hkCondition.size();
        }
        return rowCount;
    }

    private int getInitRefInfoRowCount(JsonInitRefInfo initRefInfo) {
        int rowCount = 1;
        HashMap<String, String> initCondition = initRefInfo.getInitCondition();
        if (initCondition != null) {
            rowCount = initCondition.size() == 0 ? 1 : initCondition.size();
        }
        return rowCount;
    }

    private int getStatusChangeRefInfoRowCount(JsonStatusChangeRefInfo statusChangeRefInfo) {
        int rowCount = 1;
        HashMap<String, String> statusChangeBCondition = statusChangeRefInfo.getStatusChangeBCondition();
        if (statusChangeBCondition != null) {
            rowCount = statusChangeBCondition.size() == 0 ? 1 : statusChangeBCondition.size();
        }
        return rowCount;
    }

    private int getTransitionRefInfoRowCount(JsonTransitionRefInfo transitionRefInfo) {
        int rowCount = 1;
        HashMap<String, String> transitionBCondition = transitionRefInfo.getTransitionBCondition();
        if (transitionBCondition != null) {
            rowCount = transitionBCondition.size() == 0 ? 1 : transitionBCondition.size();
        }
        return rowCount;
    }

    private int getTrigRefInfoRowCount(JsonTrigRefInfo trigRefInfo) {
        int rowCount = 1;
        HashMap<String, String> trigCondition = trigRefInfo.getTrigCondition();
        if (trigCondition != null) {
            rowCount = trigCondition.size() == 0 ? 1 : trigCondition.size();
        }
        return rowCount;
    }

    private boolean checkDestGradeInfo(JsonDestGradeInfo destGradeInfo) {
        boolean isValid = false;
        if (destGradeInfo.getAvailableModelBandType() != null ||
            destGradeInfo.getAvailableModelChapter() != null ||
            destGradeInfo.getAvailableModelName() != null ||
            destGradeInfo.getAvailableModelRemark() != null ||
            destGradeInfo.getAvailableModelSubChapter() != null ||
            destGradeInfo.getAvailableModelUUID() != null) {
            isValid = true;
        }
        return isValid;
    }


    private boolean checkDisplayRefInfo(JsonDisplayRefInfo displayRefInfo) {
        boolean isValid = false;
        if (displayRefInfo.getDisplayChapter() != null ||
            displayRefInfo.getDisplaySubChapter() != null ||
            displayRefInfo.getPartsNumber() != null ||
            displayRefInfo.getPartsName() != null ||
            displayRefInfo.getDisplayContent() != null ||
            displayRefInfo.getDisplayFormula() != null ||
            displayRefInfo.getDisplayCondition().size() != 0 ||
            displayRefInfo.getDispalyAction() != null ||
            displayRefInfo.getPartsID() != null ||
            displayRefInfo.getDataRange() != null ||
            displayRefInfo.getDisplayRemark() != null ||
            displayRefInfo.getDisplayUUID() != null ||
            displayRefInfo.getDisplayPartsType() != null ||
            displayRefInfo.getDisplayProperty() != null ||
            displayRefInfo.getDisplayActionModel() != null ||
            displayRefInfo.getDisplayConditionModel().size() != 0 ||
            displayRefInfo.getStringID() != null ||
            displayRefInfo.getVisible() != null) {
            isValid = true;
        }
        return isValid;
    }

    private boolean checkActiveRefInfo(JsonActiveRefInfo activeRefInfo) {
        boolean isValid = false;
        if (activeRefInfo.getActiveBtnName() != null ||
            activeRefInfo.getActive() != null ||
            activeRefInfo.getActiveCondition().size() != 0 ||
            activeRefInfo.getActiveAction() != null ||
            activeRefInfo.getActiveActionModel() != null ||
            activeRefInfo.getActiveConditionModel().size() != 0 ||
            activeRefInfo.getActiveDuringDriving() != null ||
            activeRefInfo.getActiveFormula() != null ||
            activeRefInfo.getActivePartsType() != null ||
            activeRefInfo.getActiveProperty() != null ||
            activeRefInfo.getActiveRemark() != null ||
            activeRefInfo.getActiveUUID() != null) {
            isValid = true;
        }
        return isValid;
    }

    private boolean checkActionRefInfo(JsonActionRefInfo actionRefInfo) {
        boolean isValid = false;
        if (actionRefInfo.getActionBtnName() != null ||
            actionRefInfo.getActionOpeType() != null ||
            actionRefInfo.getActionFormula() != null ||
            actionRefInfo.getActionCondition().size() != 0 ||
            actionRefInfo.getActionAction() != null ||
            actionRefInfo.getActionTrans() != null ||
            actionRefInfo.getActionSound() != null ||
            actionRefInfo.getActionRemark() != null ||
            actionRefInfo.getActionUUID() != null ||
            actionRefInfo.getActionPartsType() != null ||
            actionRefInfo.getActionEvent() != null ||
            actionRefInfo.getActionActionModel() != null ||
            actionRefInfo.getActionConditionModel().size() != 0 ||
            actionRefInfo.getActionObserver() != null ||
            actionRefInfo.getActionReply() != null) {
            isValid = true;
        }
        return isValid;
    }

    private boolean checkHKRefInfo(JsonHKRefInfo hkRefInfo) {
        boolean isValid = false;
        if (hkRefInfo.getHKChapter() != null ||
            hkRefInfo.getHKSubChapter() != null ||
            hkRefInfo.getHKDevName() != null ||
            hkRefInfo.getHKDevType() != null ||
            hkRefInfo.getHKName() != null ||
            hkRefInfo.getHKOpeType() != null ||
            hkRefInfo.getHKFormula() != null ||
            hkRefInfo.getHKCondition().size() != 0 ||
            hkRefInfo.getHKAction() != null ||
            hkRefInfo.getHKTrans() != null ||
            hkRefInfo.getHKSound() != null ||
            hkRefInfo.getHKDuringDriving() != null ||
            hkRefInfo.getHKRemark() != null ||
            hkRefInfo.getHKUUID() != null ||
            hkRefInfo.getHKEvent() != null ||
            hkRefInfo.getHKConditionModel().size() != 0 ||
            hkRefInfo.getHKActionModel() != null ||
            hkRefInfo.getHKObserver() != null ||
            hkRefInfo.getHKReply() != null) {
            isValid = true;
        }
        return isValid;
    }

    private boolean checkInitRefInfo(JsonInitRefInfo initRefInfo) {
        boolean isValid = false;
        if (initRefInfo.getInitCondition().size() != 0 ||
            initRefInfo.getInitConditionModel().size() != 0 ||
            initRefInfo.getInitAction() != null ||
            initRefInfo.getInitActionModel() != null ||
            initRefInfo.getInitChapter() != null ||
            initRefInfo.getInitEvent() != null ||
            initRefInfo.getInitFormula() != null ||
            initRefInfo.getInitObserver() != null ||
            initRefInfo.getInitRemark() != null ||
            initRefInfo.getInitReplay() != null ||
            initRefInfo.getInitStatus() != null ||
            initRefInfo.getInitSubChapter() != null ||
            initRefInfo.getInitTrans() != null ||
            initRefInfo.getInitUUID() != null) {
            isValid = true;
        }
        return isValid;
    }

    private boolean checkStatusChangeRefInfo(JsonStatusChangeRefInfo statusChangeRefInfo) {
        boolean isValid = false;
        if (statusChangeRefInfo.getStatusChangeChapter() != null ||
            statusChangeRefInfo.getStatusChangeSubChapter() != null ||
            statusChangeRefInfo.getStatusChangeNo() != null ||
            statusChangeRefInfo.getStatusChangeStateNo() != null ||
            statusChangeRefInfo.getStatusChangeName() != null ||
            statusChangeRefInfo.getStatusChangeFFormula() != null ||
            statusChangeRefInfo.getStatusChangeFCondition().size() != 0 ||
            statusChangeRefInfo.getStatusChangeFAction() != null ||
            statusChangeRefInfo.getStatusChangeFTrans() != null ||
            statusChangeRefInfo.getStatusChangeBFormula() != null ||
            statusChangeRefInfo.getStatusChangeBCondition().size() != 0 ||
            statusChangeRefInfo.getStatusChangeBAction() != null ||
            statusChangeRefInfo.getStatusChangeBTrans() != null ||
            statusChangeRefInfo.getStatusChangeIFormula() != null ||
            statusChangeRefInfo.getStatusChangeICondition().size() != 0 ||
            statusChangeRefInfo.getStatusChangeIAction() != null ||
            statusChangeRefInfo.getStatusChangeITrans() != null ||
            statusChangeRefInfo.getStatusChangeUUID() != null ||
            statusChangeRefInfo.getStatusChangeEvent() != null ||
            statusChangeRefInfo.getStatusChangeFConditionModel().size() != 0 ||
            statusChangeRefInfo.getStatusChangeFActionModel() != null ||
            statusChangeRefInfo.getStatusChangeFObserver() != null ||
            statusChangeRefInfo.getStatusChangeFReply() != null ||
            statusChangeRefInfo.getStatusChangeBConditionModel().size() != 0 ||
            statusChangeRefInfo.getStatusChangeBActionModel() != null ||
            statusChangeRefInfo.getStatusChangeBObserver() != null ||
            statusChangeRefInfo.getStatusChangeBReply() != null ||
            statusChangeRefInfo.getStatusChangeIConditionModel().size() != 0 ||
            statusChangeRefInfo.getStatusChangeIActionModel() != null ||
            statusChangeRefInfo.getStatusChangeIObserver() != null ||
            statusChangeRefInfo.getStatusChangeIReply() != null) {
            isValid = true;
        }
        return isValid;
    }

    private boolean checkTransitionRefInfo(JsonTransitionRefInfo transitionRefInfo) {
        boolean isValid = false;
        if (transitionRefInfo.getTransitionChapter() != null ||
            transitionRefInfo.getTransitionSubChapter() != null ||
            transitionRefInfo.getTransitionNo() != null ||
            transitionRefInfo.getTransitionStateNo() != null ||
            transitionRefInfo.getTransitionName() != null ||
            transitionRefInfo.getTransitionBFormula() != null ||
            transitionRefInfo.getTransitionBCondition().size() != 0 ||
            transitionRefInfo.getTransitionBAction() != null ||
            transitionRefInfo.getTransitionBTrans() != null ||
            transitionRefInfo.getTransitionFFormula() != null ||
            transitionRefInfo.getTransitionFCondition().size() != 0 ||
            transitionRefInfo.getTransitionFAction() != null ||
            transitionRefInfo.getTransitionFTrans() != null ||
            transitionRefInfo.getTransitionIFormula() != null ||
            transitionRefInfo.getTransitionICondition().size() != 0 ||
            transitionRefInfo.getTransitionIAction() != null ||
            transitionRefInfo.getTransitionITrans() != null ||
            transitionRefInfo.getTransitionUUID() != null ||
            transitionRefInfo.getTransitionEvent() != null ||
            transitionRefInfo.getTransitionBConditionModel().size() != 0 ||
            transitionRefInfo.getTransitionBActionModel() != null ||
            transitionRefInfo.getTransitionBObserver() != null ||
            transitionRefInfo.getTransitionBReply() != null ||
            transitionRefInfo.getTransitionFConditionModel().size() != 0 ||
            transitionRefInfo.getTransitionFActionModel() != null ||
            transitionRefInfo.getTransitionFObserver() != null ||
            transitionRefInfo.getTransitionFReply() != null ||
            transitionRefInfo.getTransitionIConditionModel().size() != 0 ||
            transitionRefInfo.getTransitionIActionModel() != null ||
            transitionRefInfo.getTransitionIObserver() != null ||
            transitionRefInfo.getTransitionIReply() != null) {
            isValid = true;
        }

        return isValid;
    }

    private boolean checkTrigRefInfo(JsonTrigRefInfo trigRefInfo) {
        boolean isValid = false;
        if (trigRefInfo.getTrigAction() != null ||
            trigRefInfo.getTrigActionModel() != null ||
            trigRefInfo.getTrigChapter() != null ||
            trigRefInfo.getTrigCondition().size() != 0 ||
            trigRefInfo.getTrigConditionModel().size() != 0 ||
            trigRefInfo.getTrigEvent() != null ||
            trigRefInfo.getTrigFormula() != null ||
            trigRefInfo.getTrigName() != null ||
            trigRefInfo.getTrigNo() != null ||
            trigRefInfo.getTrigObserver() != null ||
            trigRefInfo.getTrigReply() != null ||
            trigRefInfo.getTrigRemark() != null ||
            trigRefInfo.getTrigSignal() != null ||
            trigRefInfo.getTrigStateNo() != null ||
            trigRefInfo.getTrigSubChapter() != null ||
            trigRefInfo.getTrigTimer() != null ||
            trigRefInfo.getTrigTrans() != null ||
            trigRefInfo.getTrigUUID() != null) {
            isValid = true;
        }
        return  isValid;
    }

    private ArrayList<SheetPosValue> getNewLableList(ArrayList<SheetPosValue> oldLableList, int startRow, int tableNumber) {
        ArrayList<SheetPosValue> newLableList = new ArrayList<>();
        int oldRow = 0;
        for (int i = 0; i < oldLableList.size(); ++i) {
             SheetPosValue lable = oldLableList.get(i);
             String posInfo = lable.posInfo;
             String newPosInfo = "";
             int row = getPosRowSingle(posInfo);
             String col = getPosColSingle(posInfo);
             if (i != 0 && oldRow != row) {
                 if (0 == tableNumber && 6 == row) {
                    startRow += 2;
                 } else if (1 == tableNumber && 28 == row) {
                    startRow += 17;
                 } else {
                     startRow += 1;
                 }
                 oldRow = row;
             } else {
                 oldRow = row;
             }
             newPosInfo = col + String.valueOf(startRow);
             lable.posInfo = newPosInfo;
             newLableList.add(lable);
        }
        return newLableList;
    }

    private ArrayList<CellStyleInfo> getNewStyleList(ArrayList<CellStyleInfo> oldStyleLis, int startRow, int tableNumber) {
        ArrayList<CellStyleInfo> newStyleList = new ArrayList<>();
        int oldRow_1 = 0;
        int oldRow_2 = 0;
        int newStartRow_1 = startRow;
        int newStartRow_2 = startRow;
        int specialFixedRow = startRow + 1;
        for (int i = 0; i < oldStyleLis.size(); ++i) {
            CellStyleInfo style = oldStyleLis.get(i);
            String posInfo = style.posInfo;
            String newPosInfo = "";
            int[] posRow = getPosRow(posInfo);
            String[] posCol = getPosCol(posInfo);
            if (posRow[0] == posRow[1]) {
                if (i != 0 && oldRow_1 != posRow[0]) {
                    if (0 == tableNumber && 6 == posRow[0]) {
                        newStartRow_1 += 2;
                    } else if (1 == tableNumber && 28 == posRow[0]) {
                        newStartRow_1 += 17;
                    } else {
                        newStartRow_1 += 1;
                    }
                    oldRow_1 = posRow[0];
                } else {
                    oldRow_1 = posRow[0];
                }

                if (i != 0 && oldRow_2 != posRow[1]) {
                    if (0 == tableNumber && 6 == posRow[1]) {
                        newStartRow_2 += 2;
                    } else if (1 == tableNumber && 28 == posRow[1]) {
                        newStartRow_2 += 17;
                    } else {
                        newStartRow_2 += 1;
                    }
                    oldRow_2 = posRow[1];
                } else {
                    oldRow_2 = posRow[1];
                }
            }

            if ((tableNumber == 6 || tableNumber == 7) && posRow[0] != posRow[1]) {
                newPosInfo = posCol[0] + String.valueOf(specialFixedRow) + ":" + posCol[1] + String.valueOf(specialFixedRow + (posRow[1] - posRow[0]));
            } else {
                newPosInfo = posCol[0] + newStartRow_1 + ":" + posCol[1] + newStartRow_2;
            }

            style.posInfo = newPosInfo;
            newStyleList.add(style);
        }
        return newStyleList;
    }

    private boolean isFilter(SheetPosValue lableInfo, CellStyleInfo styleInfo, int StartRowIndex, int tableNumber) {
        boolean isFilter = false;
        String posInfo = lableInfo.posInfo;
        int[] posRow = getPosRow(styleInfo.posInfo);
        int row = getPosRowSingle(posInfo);

        if (tableNumber == 8) {
            if (row - StartRowIndex == 1) {
                isFilter = false;
            } else {
                isFilter = true;
            }

        } else if ((tableNumber == 6 || tableNumber == 7) && (posRow[0] != posRow[1])) {
            isFilter = false;
        } else {
            if (row - StartRowIndex == 0 && tableNumber == 4) {
                isFilter = true;
            } else if (row - StartRowIndex == 0 && tableNumber == 5) {
                isFilter = false;
            } else if (row - StartRowIndex < 2 && tableNumber != 4) {
                isFilter = true;
            } else {
                isFilter = false;
            }
        }
        return isFilter;
    }

    private HashMap<String, String> getPosList(ArrayList<SheetPosValue> lableList, ArrayList<CellStyleInfo> styleList, int tableNumber) {
        HashMap<String, String> posList = new HashMap<>();
        boolean isExisted = false;
        int index = 0;
        for (int i = 0; i < styleList.size() && i < lableList.size(); ++i) {
            CellStyleInfo styleInfo = styleList.get(i);
            SheetPosValue lableInfo = lableList.get(i);
            int[] posRow = getPosRow(styleInfo.posInfo);
            String[] posCol = getPosCol(styleInfo.posInfo);
            if (isFilter(lableInfo, styleInfo, StartRowIndex, tableNumber)) {
                continue;
            }

            if (isNumeric(lableInfo.strValue)) {
                if (lableInfo.strValue != "") {
                    if (!isExisted) {
                        lableInfo.strValue = "Chapter";
                        isExisted = true;
                    } else {
                        lableInfo.strValue = "SubChapter";
                    }
                } else {
                    lableInfo.strValue = "Blank_" + String.valueOf(index++);
                }
            }

            if (tableNumber == 6 || tableNumber == 7) {
                if (posRow[0] == posRow[1]) {
                    lableInfo.strValue = lableInfo.strValue + "_" + posCol[0];
                } else {
                    styleInfo.posInfo = posCol[0] + String.valueOf(posRow[1]) +  ":" + posCol[1] + String.valueOf(posRow[1]);
                }
            }
            posList.put(lableInfo.strValue, styleInfo.posInfo);
        }

        return posList;
    }

    private String checkCellValue(String cellValue) {
        if (cellValue != null && cellValue != "") {
            return cellValue;
        } else {
            return "-";
        }
    }

    private boolean isNumeric(String str) {
        for(int i = str.length(); --i >= 0;) {
            int chr=str.charAt(i);
            if(chr<48 || chr>57) {
                return false;
            }
        }
        return true;
    }

    private HashMap<String, String> getNewPosListForDestGrade(HashMap<String, String> posList) {
        HashMap<String, String> newPosList = new HashMap<>();
        newPosList.put("Japan", posList.get("日本"));
        newPosList.put("NorthAmerica", posList.get("北米"));
        newPosList.put("Europe", posList.get("欧州"));
        newPosList.put("Korea", posList.get("韓国"));
        newPosList.put("HongKongMacao", posList.get("香港マカオ"));
        newPosList.put("Thailand", posList.get("タイ"));
        newPosList.put("Oceania", posList.get("オセアニア"));
        newPosList.put("SouthAfrica", posList.get("南ア"));
        newPosList.put("China", posList.get("中国"));
        newPosList.put("MiddleEast", posList.get("中近東"));
        newPosList.put("Taiwan", posList.get("台湾"));
        newPosList.put("SoutheastAsia", posList.get("東南アジア"));
        newPosList.put("CSOrBR", posList.get("中南米(CS/BR)"));
        newPosList.put("LatinAmerica(AR)", posList.get("中南米(AR)"));
        newPosList.put("India", posList.get("インド"));
        newPosList.put("Remark", posList.get("Remark"));
        return newPosList;
    }
}
