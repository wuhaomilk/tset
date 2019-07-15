package sketch.settings;

import sketch.common.CellStyleInfo;
import sketch.common.SheetPosValue;

import java.util.ArrayList;

public class HMIPartsSpecStatic {
    public ArrayList<SheetPosValue> rowHeightList = new ArrayList<SheetPosValue>();
    public ArrayList<CellStyleInfo> styleList = new ArrayList<CellStyleInfo>();
    public ArrayList<SheetPosValue> lableList = new ArrayList<SheetPosValue>();

    public HMIPartsSpecStatic() {
        initRowHeightList();
        initLableList();
        initStyleList();
    }

    private void initRowHeightList() {
        rowHeightList.add(new SheetPosValue("28", "28"));
        rowHeightList.add(new SheetPosValue("45", "13.5"));
        rowHeightList.add(new SheetPosValue("46:48", "27"));

    }

    private void initLableList() {
        lableList.add(new SheetPosValue("B28", "1"));
        lableList.add(new SheetPosValue("C28", "1"));
        lableList.add(new SheetPosValue("D28", "No."));
        lableList.add(new SheetPosValue("E28", "Parts Name"));
        lableList.add(new SheetPosValue("J28", "Display Content"));
        lableList.add(new SheetPosValue("P28", "Formula"));
        lableList.add(new SheetPosValue("S28", "Display Condition"));
        lableList.add(new SheetPosValue("Z28", "Display in Such Condition"));
        lableList.add(new SheetPosValue("AF28", "PartsID"));
        lableList.add(new SheetPosValue("AL28", "Data Range"));
        lableList.add(new SheetPosValue("AO28", "Remark"));
        lableList.add(new SheetPosValue("AU28", "UUID"));
        lableList.add(new SheetPosValue("AV28", "Parts Type"));
        lableList.add(new SheetPosValue("AY28", "Property"));
        lableList.add(new SheetPosValue("BB28", "Add Display Condition In View Model"));
        lableList.add(new SheetPosValue("BG28", "Display Condition In View Model"));
        lableList.add(new SheetPosValue("BL28", "StringID"));
        lableList.add(new SheetPosValue("BN28", "Visible"));
//        lableList.add(new SheetPosValue("P48", "Fixed words"));
//        lableList.add(new SheetPosValue("Q46", "Fixed Image"));
//        lableList.add(new SheetPosValue("R46", "Delete condition in motion"));
//        lableList.add(new SheetPosValue("R47", "Formula"));
//        lableList.add(new SheetPosValue("S47", "Condition"));
//        lableList.add(new SheetPosValue("U47", "Same Condition"));
//        lableList.add(new SheetPosValue("U48", "Parts ID"));
//        lableList.add(new SheetPosValue("V48", "Screen ID"));
//        lableList.add(new SheetPosValue("W46", "Outside Input"));
//        lableList.add(new SheetPosValue("W47", "Display contents"));
//        lableList.add(new SheetPosValue("X47", "Format"));
//        lableList.add(new SheetPosValue("Y47", "Range"));
//        lableList.add(new SheetPosValue("Z46", "Validation"));
//        lableList.add(new SheetPosValue("AA45", "SW Information"));
//        lableList.add(new SheetPosValue("AA46", "Tonedown condition in motion"));
//        lableList.add(new SheetPosValue("AA47", "Formula"));
//        lableList.add(new SheetPosValue("AB47", "Condition"));
//        lableList.add(new SheetPosValue("AD47", "Same Condition"));
//        lableList.add(new SheetPosValue("AD48", "Parts ID"));
//        lableList.add(new SheetPosValue("AE48", "Screen ID"));
//        lableList.add(new SheetPosValue("AF46", "Tonedown condition except in motion"));
//        lableList.add(new SheetPosValue("AF47", "Formula"));
//        lableList.add(new SheetPosValue("AG47", "Condition"));
//        lableList.add(new SheetPosValue("AI47", "Same Condition"));
//        lableList.add(new SheetPosValue("AI48", "Parts ID"));
//        lableList.add(new SheetPosValue("AJ48", "Screen ID"));
//        lableList.add(new SheetPosValue("AK46", "Selected condition"));
//        lableList.add(new SheetPosValue("AK47", "Formula"));
//        lableList.add(new SheetPosValue("AL47", "Condition"));
//        lableList.add(new SheetPosValue("AN47", "Same Condition"));
//        lableList.add(new SheetPosValue("AN48", "Parts ID"));
//        lableList.add(new SheetPosValue("AO48", "Screen ID"));
//        lableList.add(new SheetPosValue("AP46", "SW operation Pattern"));
//
//        lableList.add(new SheetPosValue("AQ46", "Operation result"));
//        lableList.add(new SheetPosValue("AQ47", "Screen Transion"));
//        lableList.add(new SheetPosValue("AR47", "Start Function"));
//        lableList.add(new SheetPosValue("AS47", "Setting Value Change"));
//        lableList.add(new SheetPosValue("AT47", "Other"));
//        lableList.add(new SheetPosValue("AU46", "Beep"));
//        lableList.add(new SheetPosValue("AV45", "UUID"));
//        lableList.add(new SheetPosValue("AW45", "RealFullID"));

    }

    private void initStyleList() {
        styleList.add(new CellStyleInfo(
                "B28:B28","Meiryo UI", 11,false, true,
                "center","center","thin","thin","thin",
                "thin",false, new int[]{201,255,255}));
        styleList.add(new CellStyleInfo(
                "C28:C28","Meiryo UI", 11,false, true,
                "center","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "D28:D28","Meiryo UI", 11,false, true,
                "center","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "E28:I28","Meiryo UI", 11,true, true,
                "center","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "J28:O28","Meiryo UI", 11,true, true,
                "center","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "P28:R28","Meiryo UI", 11,true, true,
                "center","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "S28:Y28","Meiryo UI", 11,true, true,
                "center","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "Z28:AE28","Meiryo UI", 11,true, true,
                "center","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AF28:AK28","Meiryo UI", 11,true, true,
                "center","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AL28:AN28","Meiryo UI", 11,true, true,
                "center","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AO28:AR28","Meiryo UI", 11,true, true,
                "center","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AU28:AU28","Meiryo UI", 11,false, true,
                "center","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AV28:AX28","Meiryo UI", 11,true, true,
                "center","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AY28:BA28","Meiryo UI", 11,true, true,
                "center","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "BB28:BF28","Meiryo UI", 11,true, true,
                "center","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "BG28:BK28","Meiryo UI", 11,true, true,
                "center","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "BL28:BM28","Meiryo UI", 11,true, true,
                "center","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "BN28:BO28","Meiryo UI", 11,true, true,
                "center","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
//        styleList.add(new CellStyleInfo(
//                "O48:O48","Meiryo UI", 11,false, false,
//                "center","center","thin","","thin",
//                "medium",false, new int[]{255,192,0}));
//        styleList.add(new CellStyleInfo(
//                "P48:P48","Meiryo UI", 11,false, false,
//                "center","center","thin","thin","thin",
//                "medium",false, new int[]{255,192,0}));
//        styleList.add(new CellStyleInfo(
//                "Q46:Q48","Meiryo UI", 11,true, false,
//                "center","center","thin","thin","thin",
//                "medium",false, new int[]{255,192,0}));
//        styleList.add(new CellStyleInfo(
//                "R46:V46","Meiryo UI", 11,true, false,
//                "center","center","thin","thin","thin",
//                "thin",false, new int[]{255,192,0}));
//        styleList.add(new CellStyleInfo(
//                "R47:R48","Meiryo UI", 11,true, false,
//                "center","center","thin","thin","thin",
//                "medium",false, new int[]{255,192,0}));
//        styleList.add(new CellStyleInfo(
//                "S47:T48","Meiryo UI", 11,true, false,
//                "center","center","thin","thin","thin",
//                "medium",false, new int[]{255,192,0}));
//        styleList.add(new CellStyleInfo(
//                "U47:V47","Meiryo UI", 11,true, false,
//                "center","center","thin","thin","thin",
//                "thin",false, new int[]{255,192,0}));
//        styleList.add(new CellStyleInfo(
//                "U48:U48","Meiryo UI", 11,false, false,
//                "center","center","thin","thin","thin",
//                "medium",false, new int[]{255,192,0}));
//        styleList.add(new CellStyleInfo(
//                "V48:V48","Meiryo UI", 11,false, false,
//                "center","center","thin","thin","thin",
//                "medium",false, new int[]{255,192,0}));
//        styleList.add(new CellStyleInfo(
//                "W46:Y46","Meiryo UI", 11,true, false,
//                "center","center","thin","thin","thin",
//                "thin",false, new int[]{255,192,0}));
//        styleList.add(new CellStyleInfo(
//                "W47:W48","Meiryo UI", 11,true, false,
//                "center","center","thin","thin","thin",
//                "medium",false, new int[]{255,192,0}));
//        styleList.add(new CellStyleInfo(
//                "X47:X48","Meiryo UI", 11,true, false,
//                "center","center","thin","thin","thin",
//                "medium",false, new int[]{255,192,0}));
//        styleList.add(new CellStyleInfo(
//                "Y47:Y48","Meiryo UI", 11,true, false,
//                "center","center","thin","thin","thin",
//                "medium",false, new int[]{255,192,0}));
//        styleList.add(new CellStyleInfo(
//                "Z46:Z48","Meiryo UI", 11,true, false,
//                "center","center","thin","thin","medium",
//                "medium",false, new int[]{255,192,0}));
//        styleList.add(new CellStyleInfo(
//                "AA45:AU45","Meiryo UI", 11,true, false,
//                "center","center","medium","medium","medium",
//                "thin",false, new int[]{204,192,218}));
//        styleList.add(new CellStyleInfo(
//                "AA46:AE46","Meiryo UI", 11,true, false,
//                "center","center","medium","thin","thin",
//                "thin",false, new int[]{204,192,218}));
//        styleList.add(new CellStyleInfo(
//                "AA47:AA48","Meiryo UI", 11,true, false,
//                "center","center","medium","thin","thin",
//                "medium",false, new int[]{204,192,218}));
//        styleList.add(new CellStyleInfo(
//                "AB47:AC48","Meiryo UI", 11,true, false,
//                "center","center","thin","thin","thin",
//                "medium",false, new int[]{204,192,218}));
//        styleList.add(new CellStyleInfo(
//                "AD47:AE47","Meiryo UI", 11,true, false,
//                "center","center","thin","thin","thin",
//                "thin",false, new int[]{204,192,218}));
//        styleList.add(new CellStyleInfo(
//                "AD48:AD48","Meiryo UI", 11,false, false,
//                "center","center","thin","thin","thin",
//                "medium",false, new int[]{204,192,218}));
//        styleList.add(new CellStyleInfo(
//                "AE48:AE48","Meiryo UI", 11,false, false,
//                "center","center","thin","thin","thin",
//                "medium",false, new int[]{204,192,218}));
//        styleList.add(new CellStyleInfo(
//                "AF46:AJ46","Meiryo UI", 11,true, false,
//                "center","center","thin","thin","thin",
//                "thin",false, new int[]{204,192,218}));
//        styleList.add(new CellStyleInfo(
//                "AF47:AF48","Meiryo UI", 11,true, false,
//                "center","center","thin","thin","thin",
//                "medium",false, new int[]{204,192,218}));
//        styleList.add(new CellStyleInfo(
//                "AG47:AH48","Meiryo UI", 11,true, false,
//                "center","center","thin","thin","thin",
//                "medium",false, new int[]{204,192,218}));
//        styleList.add(new CellStyleInfo(
//                "AI47:AJ47","Meiryo UI", 11,true, false,
//                "center","center","thin","thin","thin",
//                "thin",false, new int[]{204,192,218}));
//        styleList.add(new CellStyleInfo(
//                "AI48:AI48","Meiryo UI", 11,false, false,
//                "center","center","thin","thin","thin",
//                "medium",false, new int[]{204,192,218}));
//        styleList.add(new CellStyleInfo(
//                "AJ48:AJ48","Meiryo UI", 11,false, false,
//                "center","center","thin","thin","thin",
//                "medium",false, new int[]{204,192,218}));
//        styleList.add(new CellStyleInfo(
//                "AK46:AO46","Meiryo UI", 11,true, false,
//                "center","center","thin","thin","thin",
//                "thin",false, new int[]{204,192,218}));
//        styleList.add(new CellStyleInfo(
//                "AK47:AK48","Meiryo UI", 11,true, false,
//                "center","center","thin","thin","thin",
//                "medium",false, new int[]{204,192,218}));
//        styleList.add(new CellStyleInfo(
//                "AL47:AM48","Meiryo UI", 11,true, false,
//                "center","center","thin","thin","thin",
//                "medium",false, new int[]{204,192,218}));
//        styleList.add(new CellStyleInfo(
//                "AN47:AO47","Meiryo UI", 11,true, false,
//                "center","center","thin","thin","thin",
//                "thin",false, new int[]{204,192,218}));
//        styleList.add(new CellStyleInfo(
//                "AN48:AN48","Meiryo UI", 11,false, false,
//                "center","center","thin","thin","thin",
//                "medium",false, new int[]{204,192,218}));
//        styleList.add(new CellStyleInfo(
//                "AO48:AO48","Meiryo UI", 11,false, false,
//                "center","center","thin","thin","thin",
//                "medium",false, new int[]{204,192,218}));
//        styleList.add(new CellStyleInfo(
//                "AP46:AP48","Meiryo UI", 11,true, false,
//                "center","center","thin","thin","thin",
//                "medium",true, new int[]{204,192,218}));
//
//        styleList.add(new CellStyleInfo(
//                "AQ46:AT46","Meiryo UI", 11,true, false,
//                "center","center","thin","thin","thin",
//                "thin",false, new int[]{204,192,218}));
//        styleList.add(new CellStyleInfo(
//                "AQ47:AQ48","Meiryo UI", 11,true, false,
//                "center","center","thin","thin","thin",
//                "medium",false, new int[]{204,192,218}));
//        styleList.add(new CellStyleInfo(
//                "AR47:AR48","Meiryo UI", 11,true, false,
//                "center","center","thin","thin","thin",
//                "medium",false, new int[]{204,192,218}));
//        styleList.add(new CellStyleInfo(
//                "AS47:AS48","Meiryo UI", 11,true, false,
//                "center","center","thin","thin","thin",
//                "medium",true, new int[]{204,192,218}));
//        styleList.add(new CellStyleInfo(
//                "AT47:AT48","Meiryo UI", 11,true, false,
//                "center","center","thin","thin","thin",
//                "medium",true, new int[]{204,192,218}));
//        styleList.add(new CellStyleInfo(
//                "AU46:AU48","Meiryo UI", 11,true, false,
//                "center","center","thin","thin","medium",
//                "medium",true, new int[]{204,192,218}));
//        styleList.add(new CellStyleInfo(
//                "AV45:AV48","Meiryo UI", 11,true, false,
//                "center","center","medium","medium","medium",
//                "thin",true, new int[]{}));
//        styleList.add(new CellStyleInfo(
//                "AW45:AW48","Meiryo UI", 11,true, false,
//                "center","center","medium","medium","medium",
//                "thin",true, new int[]{}));

    }
}
