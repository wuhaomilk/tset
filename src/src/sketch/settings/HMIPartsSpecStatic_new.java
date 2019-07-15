package sketch.settings;

import javafx.scene.control.Cell;
import sketch.common.CellStyleInfo;
import sketch.common.SheetPosValue;

import java.util.ArrayList;
import java.util.HashMap;

public class HMIPartsSpecStatic_new {
    public HashMap<String, ArrayList<SheetPosValue>> rowHeightLists = new HashMap<>();
    public HashMap<String, ArrayList<CellStyleInfo>> styleLists = new HashMap<>();
    public HashMap<String, ArrayList<SheetPosValue>> lableLists = new HashMap<>();

    public HMIPartsSpecStatic_new() {

        rowHeightLists.put("rowHeight", initRowHeightList());

        styleLists.put("Screen", initStyleList_Screen());
        lableLists.put("Screen", initLableList_Screen());

        styleLists.put("Outline", initStyleList_Outline());
        lableLists.put("Outline", initLableList_Outline());

        styleLists.put("DisplayRefInfo", initStyleList_1());
        lableLists.put("DisplayRefInfo", initLableList_1());

        styleLists.put("ActiveRefInfo", initStyleList_2());
        lableLists.put("ActiveRefInfo", initLableList_2());

        styleLists.put("ActionRefInfo", initStyleList_3());
        lableLists.put("ActionRefInfo", initLableList_3());

        styleLists.put("HKRefInfo", initStyleList_4());
        lableLists.put("HKRefInfo", initLableList_4());

        styleLists.put("InitRefInfo", initStyleList_5());
        lableLists.put("InitRefInfo", initLableList_5());

        styleLists.put("StatusChangeRefInfo", initStyleList_6());
        lableLists.put("StatusChangeRefInfo", initLableList_6());

        styleLists.put("TransitionRefInfo", initStyleList_7());
        lableLists.put("TransitionRefInfo", initLableList_7());

        styleLists.put("TrigRefInfo", initStyleList_8());
        lableLists.put("TrigRefInfo", initLableList_8());
    }

    private ArrayList<SheetPosValue> initRowHeightList() {
        ArrayList<SheetPosValue> rowHeightList = new ArrayList<>();
        rowHeightList.add(new SheetPosValue("1:1000", "24"));
        return rowHeightList;
    }

    private ArrayList<SheetPosValue> initLableList_Screen() {
        ArrayList<SheetPosValue> lableList = new ArrayList<>();
        lableList.add(new SheetPosValue("B2", "MM_XX_XX_XX"));
        lableList.add(new SheetPosValue("K2", "Sample画面"));
        lableList.add(new SheetPosValue("C5", "-"));
        lableList.add(new SheetPosValue("AU2", "-"));
        lableList.add(new SheetPosValue("AU1", "UUID"));
        return lableList;
    }

    private ArrayList<SheetPosValue> initLableList_Outline() {
        ArrayList<SheetPosValue> lableList = new ArrayList<>();
        lableList.add(new SheetPosValue("B4", "0"));
        lableList.add(new SheetPosValue("C4", "Outline"));
        lableList.add(new SheetPosValue("B6", "0"));
        lableList.add(new SheetPosValue("C6", "Available Model"));
        lableList.add(new SheetPosValue("B7", "0"));
        lableList.add(new SheetPosValue("C7", "1"));
        lableList.add(new SheetPosValue("D7", "Honda"));
        lableList.add(new SheetPosValue("L7", "日本"));
        lableList.add(new SheetPosValue("N7", "北米"));
        lableList.add(new SheetPosValue("P7", "欧州"));
        lableList.add(new SheetPosValue("R7", "韓国"));
        lableList.add(new SheetPosValue("T7", "香港マカオ"));
        lableList.add(new SheetPosValue("V7", "タイ"));
        lableList.add(new SheetPosValue("X7", "オセアニア"));
        lableList.add(new SheetPosValue("Z7", "南ア"));
        lableList.add(new SheetPosValue("AB7", "中国"));
        lableList.add(new SheetPosValue("AD7", "中近東"));
        lableList.add(new SheetPosValue("AF7", "台湾"));
        lableList.add(new SheetPosValue("AH7", "東南アジア"));
        lableList.add(new SheetPosValue("AJ7", "中南米(CS/BR)"));
        lableList.add(new SheetPosValue("AL7", "中南米(AR)"));
        lableList.add(new SheetPosValue("AN7", "インド"));
        lableList.add(new SheetPosValue("AP7", "Remark"));
        lableList.add(new SheetPosValue("AU7", "UUID"));
        return lableList;
    }

    private ArrayList<SheetPosValue> initLableList_1() {
        ArrayList<SheetPosValue> lableList = new ArrayList<>();
        lableList.add(new SheetPosValue("B10", "1"));
        lableList.add(new SheetPosValue("C10", "View of Screen"));
        lableList.add(new SheetPosValue("B11", "1"));
        lableList.add(new SheetPosValue("C11", "1"));
        lableList.add(new SheetPosValue("D11", "Normal"));
        lableList.add(new SheetPosValue("B28", "1"));
        lableList.add(new SheetPosValue("C28", "1"));
        lableList.add(new SheetPosValue("D28", "NO."));
        lableList.add(new SheetPosValue("E28", "Parts Name"));
        lableList.add(new SheetPosValue("J28", "Display Content"));
        lableList.add(new SheetPosValue("P28", "Formula"));
        lableList.add(new SheetPosValue("S28", "Display Condition"));
        lableList.add(new SheetPosValue("Z28", "Display in Such Condition"));
        lableList.add(new SheetPosValue("AF28", "Property Type"));
        lableList.add(new SheetPosValue("AL28", "Data Range"));
        lableList.add(new SheetPosValue("AO28", "Remark"));
        lableList.add(new SheetPosValue("AU28", "UUID"));
        lableList.add(new SheetPosValue("AV28", "Parts Type"));
        lableList.add(new SheetPosValue("AY28", "Property"));
        lableList.add(new SheetPosValue("BB28", "Add Display Condition In View Model"));
        lableList.add(new SheetPosValue("BG28", "Display Condition In View Model"));
        lableList.add(new SheetPosValue("BL28", "String ID"));
        lableList.add(new SheetPosValue("BN28", "DefaultValue"));
        return lableList;
    }

    private ArrayList<SheetPosValue> initLableList_2() {
        ArrayList<SheetPosValue> lableList = new ArrayList<>();
        lableList.add(new SheetPosValue("B45", "2"));
        lableList.add(new SheetPosValue("C45", "View of Soft Button"));
        lableList.add(new SheetPosValue("B46", "2"));
        lableList.add(new SheetPosValue("C46", "1"));
        lableList.add(new SheetPosValue("D46", "Normal"));
        lableList.add(new SheetPosValue("B47", "2"));
        lableList.add(new SheetPosValue("C47", "1"));
        lableList.add(new SheetPosValue("D47", "NO."));
        lableList.add(new SheetPosValue("E47", "Button Name"));
        lableList.add(new SheetPosValue("N47", "Formula"));
        lableList.add(new SheetPosValue("Q47", "Condition"));
        lableList.add(new SheetPosValue("X47", "Display in Such Condition"));
        lableList.add(new SheetPosValue("AF47", "Property Type"));
        lableList.add(new SheetPosValue("AL47", "DuringDriving"));
        lableList.add(new SheetPosValue("AO47", "Remark"));
        lableList.add(new SheetPosValue("AU47", "UUID"));
        lableList.add(new SheetPosValue("AV47", "Parts Type"));
        lableList.add(new SheetPosValue("AY47", "Property"));
        lableList.add(new SheetPosValue("BB47", "Add Display Condition In View Model"));
        lableList.add(new SheetPosValue("BG47", "Display Condition In View Model"));
        lableList.add(new SheetPosValue("BL47", ""));
        lableList.add(new SheetPosValue("BN47", "DefaultValue"));
        return lableList;
    }

    private ArrayList<SheetPosValue> initLableList_3() {
        ArrayList<SheetPosValue> lableList = new ArrayList<>();
        lableList.add(new SheetPosValue("B52", "3"));
        lableList.add(new SheetPosValue("C52", "Soft Button Action"));
        lableList.add(new SheetPosValue("B53", "3"));
        lableList.add(new SheetPosValue("C53", "1"));
        lableList.add(new SheetPosValue("D53", "Normal"));
        lableList.add(new SheetPosValue("B54", "3"));
        lableList.add(new SheetPosValue("C54", "1"));
        lableList.add(new SheetPosValue("D54", "NO."));
        lableList.add(new SheetPosValue("E54", "Button Name"));
        lableList.add(new SheetPosValue("L54", "Ope Type"));
        lableList.add(new SheetPosValue("N54", "Formula"));
        lableList.add(new SheetPosValue("Q54", "Condition of Action"));
        lableList.add(new SheetPosValue("X54", "Action in Such Condition"));
        lableList.add(new SheetPosValue("AF54", "Transition"));
        lableList.add(new SheetPosValue("AI54", "Sound"));
        lableList.add(new SheetPosValue("AL54", ""));
        lableList.add(new SheetPosValue("AO54", "Remark"));
        lableList.add(new SheetPosValue("AU54", "UUID"));
        lableList.add(new SheetPosValue("AV54", "Parts Type"));
        lableList.add(new SheetPosValue("AY54", "Event"));
        lableList.add(new SheetPosValue("BB54", "View Model"));
        lableList.add(new SheetPosValue("BG54", "Func of Model"));
        lableList.add(new SheetPosValue("BI54", "Observer"));
        lableList.add(new SheetPosValue("BL54", "Reply"));
        lableList.add(new SheetPosValue("BN54", "TransType"));
        lableList.add(new SheetPosValue("BP54", "TransID"));
        return lableList;
}

    private ArrayList<SheetPosValue> initLableList_4() {
        ArrayList<SheetPosValue> lableList = new ArrayList<>();
        lableList.add(new SheetPosValue("B89", "4"));
        lableList.add(new SheetPosValue("C89", "Hard Key Action"));
        lableList.add(new SheetPosValue("B90", "4"));
        lableList.add(new SheetPosValue("C90", "1"));
        lableList.add(new SheetPosValue("D90", "Normal"));
        lableList.add(new SheetPosValue("L90", "Ope Type"));
        lableList.add(new SheetPosValue("N90", "Formula"));
        lableList.add(new SheetPosValue("Q90", "Condition of Action"));
        lableList.add(new SheetPosValue("X90", "Action in Such Condition"));
        lableList.add(new SheetPosValue("AF90", "Transition"));
        lableList.add(new SheetPosValue("AI90", "Sound"));
        lableList.add(new SheetPosValue("AL90", "DuringDriving"));
        lableList.add(new SheetPosValue("AO90", "Remark"));
        lableList.add(new SheetPosValue("AU90", "UUID"));
        lableList.add(new SheetPosValue("AV90", "HardKey Event"));
        lableList.add(new SheetPosValue("AY90", "View Model"));
        lableList.add(new SheetPosValue("BD90", "Func of Model"));
        lableList.add(new SheetPosValue("BG90", "Observer"));
        lableList.add(new SheetPosValue("BJ90", "Reply"));
        lableList.add(new SheetPosValue("BN90", "TransType"));
        lableList.add(new SheetPosValue("BP90", "TransID"));
        return lableList;
    }

    private ArrayList<SheetPosValue> initLableList_5() {
        ArrayList<SheetPosValue> lableList = new ArrayList<>();
        lableList.add(new SheetPosValue("B66", "5"));
        lableList.add(new SheetPosValue("C66", "Initialized Status"));
        lableList.add(new SheetPosValue("L66", "Formula"));
        lableList.add(new SheetPosValue("N66", "Condition of Action"));
        lableList.add(new SheetPosValue("X66", "Action in Such Condition"));
        lableList.add(new SheetPosValue("AF66", "Transition"));
        lableList.add(new SheetPosValue("AL66", ""));
        lableList.add(new SheetPosValue("AO66", "Remark"));
        lableList.add(new SheetPosValue("AU66", "UUID"));
        lableList.add(new SheetPosValue("AV66", "Event"));
        lableList.add(new SheetPosValue("AY66", "View Model"));
        lableList.add(new SheetPosValue("BD66", "Func of Model"));
        lableList.add(new SheetPosValue("BG66", "Observer"));
        lableList.add(new SheetPosValue("BJ66", "Reply"));
        lableList.add(new SheetPosValue("BN66", "TransType"));
        lableList.add(new SheetPosValue("BP66", "TransID"));
        return lableList;
    }

    private ArrayList<SheetPosValue> initLableList_6() {
        ArrayList<SheetPosValue> lableList = new ArrayList<>();
        lableList.add(new SheetPosValue("B65", "6"));
        lableList.add(new SheetPosValue("C65", "Action on Status change"));
        lableList.add(new SheetPosValue("B66", "6"));
        lableList.add(new SheetPosValue("C66", "1"));
        lableList.add(new SheetPosValue("D66", "Normal"));
        lableList.add(new SheetPosValue("H66", "Forward from"));
        lableList.add(new SheetPosValue("T66", "Back from"));
        lableList.add(new SheetPosValue("AF66", "Interrupt"));
        lableList.add(new SheetPosValue("AU66", "UUID"));
        lableList.add(new SheetPosValue("AV66", "Event"));
        lableList.add(new SheetPosValue("AY66", "Forward from"));
        lableList.add(new SheetPosValue("BN66", "Back from"));
        lableList.add(new SheetPosValue("CC66", "Interrupt"));
        lableList.add(new SheetPosValue("H67", "Formula"));
        lableList.add(new SheetPosValue("I67", "Condition"));
        lableList.add(new SheetPosValue("N67", "Action"));
        lableList.add(new SheetPosValue("R67", ""));
        lableList.add(new SheetPosValue("T67", "Formula"));
        lableList.add(new SheetPosValue("U67", "Condition"));
        lableList.add(new SheetPosValue("Z67", "Action"));
        lableList.add(new SheetPosValue("AD67", ""));
        lableList.add(new SheetPosValue("AF67", "Formula"));
        lableList.add(new SheetPosValue("AG67", "Condition"));
        lableList.add(new SheetPosValue("AL67", "Action"));
        lableList.add(new SheetPosValue("AQ67", ""));
        lableList.add(new SheetPosValue("AY67", "View Model"));
        lableList.add(new SheetPosValue("BD67", "Func of Model"));
        lableList.add(new SheetPosValue("BG67", "Observer"));
        lableList.add(new SheetPosValue("Bj67", "Reply"));
        lableList.add(new SheetPosValue("BN67", "View Model"));
        lableList.add(new SheetPosValue("BS67", "Func of Model"));
        lableList.add(new SheetPosValue("BV67", "Observer"));
        lableList.add(new SheetPosValue("BY67", "Reply"));
        lableList.add(new SheetPosValue("CC67", "View Model"));
        lableList.add(new SheetPosValue("CH67", "Func of Model"));
        lableList.add(new SheetPosValue("CK67", "Observer"));
        lableList.add(new SheetPosValue("CN67", "Reply"));
        return lableList;
    }

    private ArrayList<SheetPosValue> initLableList_7() {
        ArrayList<SheetPosValue> lableList = new ArrayList<>();
        lableList.add(new SheetPosValue("B72", "7"));
        lableList.add(new SheetPosValue("C72", "Action on Transition"));
        lableList.add(new SheetPosValue("B73", "7"));
        lableList.add(new SheetPosValue("C73", "1"));
        lableList.add(new SheetPosValue("D73", "Normal"));
        lableList.add(new SheetPosValue("H73", "Back to Other Screen"));
        lableList.add(new SheetPosValue("T73", "Forward to Other Screen"));
        lableList.add(new SheetPosValue("AF73", "Interrupt Transition"));
        lableList.add(new SheetPosValue("AU73", "UUID"));
        lableList.add(new SheetPosValue("AV73", "Event"));
        lableList.add(new SheetPosValue("AY73", "Back to Other Screen"));
        lableList.add(new SheetPosValue("BN73", "Forward to Other Screen"));
        lableList.add(new SheetPosValue("CC73", "Interrupt Transition"));
        lableList.add(new SheetPosValue("H74", "Formula"));
        lableList.add(new SheetPosValue("I74", "Condition"));
        lableList.add(new SheetPosValue("N74", "Action"));
        lableList.add(new SheetPosValue("R74", ""));
        lableList.add(new SheetPosValue("T74", "Formula"));
        lableList.add(new SheetPosValue("U74", "Condition"));
        lableList.add(new SheetPosValue("Z74", "Action"));
        lableList.add(new SheetPosValue("AD74", ""));
        lableList.add(new SheetPosValue("AF74", "Formula"));
        lableList.add(new SheetPosValue("AG74", "Condition"));
        lableList.add(new SheetPosValue("AL74", "Action"));
        lableList.add(new SheetPosValue("AQ74", ""));
        lableList.add(new SheetPosValue("AY74", "View Model"));
        lableList.add(new SheetPosValue("BD74", "Func of Model"));
        lableList.add(new SheetPosValue("BG74", "Observer"));
        lableList.add(new SheetPosValue("Bj74", "Reply"));
        lableList.add(new SheetPosValue("BN74", "View Model"));
        lableList.add(new SheetPosValue("BS74", "Func of Model"));
        lableList.add(new SheetPosValue("BV74", "Observer"));
        lableList.add(new SheetPosValue("BY74", "Reply"));
        lableList.add(new SheetPosValue("CC74", "View Model"));
        lableList.add(new SheetPosValue("CH74", "Func of Model"));
        lableList.add(new SheetPosValue("CK74", "Observer"));
        lableList.add(new SheetPosValue("CN74", "Reply"));
        return lableList;
    }

    private ArrayList<SheetPosValue> initLableList_8() {
        ArrayList<SheetPosValue> lableList = new ArrayList<>();
        lableList.add(new SheetPosValue("B103", "8"));
        lableList.add(new SheetPosValue("C103", "Trigger Action"));
        lableList.add(new SheetPosValue("B104", "8"));
        lableList.add(new SheetPosValue("C104", "1"));
        lableList.add(new SheetPosValue("D104", "Normal"));
        lableList.add(new SheetPosValue("L104", "Formula"));
        lableList.add(new SheetPosValue("N104", "Condition of Action"));
        lableList.add(new SheetPosValue("V104", "Action in Such Condition"));
        lableList.add(new SheetPosValue("AF104", "Transition"));
        lableList.add(new SheetPosValue("AI104", "Sound"));
        lableList.add(new SheetPosValue("AL104", "Timer"));
        lableList.add(new SheetPosValue("AO104", "Remark"));
        lableList.add(new SheetPosValue("AU104", "UUID"));
        lableList.add(new SheetPosValue("AV104", "Trigger"));
        lableList.add(new SheetPosValue("AY104", "ONSID"));
        lableList.add(new SheetPosValue("BA104", "View Model"));
        lableList.add(new SheetPosValue("BF104", "Func of Model"));
        lableList.add(new SheetPosValue("BH104", "Observer"));
        lableList.add(new SheetPosValue("BJ104", "Reply"));
        lableList.add(new SheetPosValue("BN104", "TransType"));
        lableList.add(new SheetPosValue("BP104", "TransID"));
        return lableList;
    }


    private ArrayList<CellStyleInfo> initStyleList_Screen() {
        ArrayList<CellStyleInfo> styleList = new ArrayList<>();
        styleList.add(new CellStyleInfo(
                "A2:AR2","Meiryo UI", 22,false, true,
                "left","center","","","",
                "",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "B5:B5","Meiryo UI", 22,false, true,
                "left","center","","","thin",
                "",false, new int[]{255,255,255}));
        styleList.add(new CellStyleInfo(
                "AR5:AR5","Meiryo UI", 22,false, true,
                "left","center","","","thin",
                "",false, new int[]{255,255,255}));
        styleList.add(new CellStyleInfo(
                "C6:C6","Meiryo UI", 24,false, true,
                "left","center","thin","","",
                "",false, new int[]{255,255,255}));
        styleList.add(new CellStyleInfo(
                "AU1:AU1","Meiryo UI", 11,false, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,193,46}));
        styleList.add(new CellStyleInfo(
                "AU2:AU2","Meiryo UI", 11,false, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,255,255}));
        return styleList;
    }

    private ArrayList<CellStyleInfo> initStyleList_Outline() {
        ArrayList<CellStyleInfo> styleList = new ArrayList<>();
        styleList.add(new CellStyleInfo(
                "B4:B4","Meiryo UI", 11,false, true,
                "center","center","thin","thin","thin",
                "thin",false, new int[]{201,255,255}));
        styleList.add(new CellStyleInfo(
                "C4:AR4","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{201,255,255}));
        styleList.add(new CellStyleInfo(
                "B6:B6","Meiryo UI", 11,false, true,
                "center","center","thin","thin","thin",
                "thin",false, new int[]{201,255,255}));
        styleList.add(new CellStyleInfo(
                "C6:AR6","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{201,255,255}));
        styleList.add(new CellStyleInfo(
                "B7:B7","Meiryo UI", 11,false, true,
                "center","center","thin","thin","thin",
                "thin",false, new int[]{201,255,255}));
        styleList.add(new CellStyleInfo(
                "C7:C7","Meiryo UI", 11,false, true,
                "center","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "D7:K7","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "L7:M7","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "N7:O7","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "P7:Q7","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "R7:S7","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "T7:U7","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "V7:W7","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "X7:Y7","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "Z7:AA7","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AB7:AC7","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AD7:AE7","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AF7:AG7","Meiryo UI", 11,true, false,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AH7:AI7","Meiryo UI", 11,true, false,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AJ7:AK7","Meiryo UI", 11,true, false,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AL7:AM7","Meiryo UI", 11,true, false,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AN7:AO7","Meiryo UI", 11,true, false,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AP7:AR7","Meiryo UI", 11,true, false,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AU7:AU7","Meiryo UI", 11,false, false,
                "center","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        return styleList;
    }

    private ArrayList<CellStyleInfo> initStyleList_1() {
        ArrayList<CellStyleInfo> styleList = new ArrayList<>();
        styleList.add(new CellStyleInfo(
                "B10:B10","Meiryo UI", 11,false, true,
                "center","center","thin","thin","thin",
                "thin",false, new int[]{201,255,255}));
        styleList.add(new CellStyleInfo(
                "C10:AR10","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{201,255,255}));
        styleList.add(new CellStyleInfo(
                "B11:B11","Meiryo UI", 11,false, true,
                "center","center","thin","thin","thin",
                "thin",false, new int[]{201,255,255}));
        styleList.add(new CellStyleInfo(
                "C11:C11","Meiryo UI", 11,false, true,
                "center","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "D11:AR11","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
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
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "E28:I28","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "J28:O28","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "P28:R28","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "S28:Y28","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "Z28:AE28","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AF28:AK28","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AL28:AN28","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AO28:AR28","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AU28:AU28","Meiryo UI", 11,false, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,192,0}));
        styleList.add(new CellStyleInfo(
                "AV28:AX28","Meiryo UI", 11,true, false,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,192,0}));
        styleList.add(new CellStyleInfo(
                "AY28:BA28","Meiryo UI", 11,true, false,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,192,0}));
        styleList.add(new CellStyleInfo(
                "BB28:BF28","Meiryo UI", 11,true, false,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,192,0}));
        styleList.add(new CellStyleInfo(
                "BG28:BK28","Meiryo UI", 11,true, false,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,192,0}));
        styleList.add(new CellStyleInfo(
                "BL28:BM28","Meiryo UI", 11,true, false,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,192,0}));
        styleList.add(new CellStyleInfo(
                "BN28:BO28","Meiryo UI", 11,true, false,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,192,0}));
        return styleList;
    }

    private ArrayList<CellStyleInfo> initStyleList_2() {
        ArrayList<CellStyleInfo> styleList = new ArrayList<>();
        styleList.add(new CellStyleInfo(
                "B45:B45","Meiryo UI", 11,false, true,
                "center","center","thin","thin","thin",
                "thin",false, new int[]{201,255,255}));
        styleList.add(new CellStyleInfo(
                "C45:AR45","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{201,255,255}));
        styleList.add(new CellStyleInfo(
                "B46:B46","Meiryo UI", 11,false, true,
                "center","center","thin","thin","thin",
                "thin",false, new int[]{201,255,255}));
        styleList.add(new CellStyleInfo(
                "C46:C46","Meiryo UI", 11,false, true,
                "center","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "D46:AR46","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "B47:B47","Meiryo UI", 11,false, true,
                "center","center","thin","thin","thin",
                "thin",false, new int[]{201,255,255}));
        styleList.add(new CellStyleInfo(
                "C47:C47","Meiryo UI", 11,false, true,
                "center","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "D47:D47","Meiryo UI", 11,false, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "E47:M47","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "N47:P47","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "Q47:W47","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "X47:AE47","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AF47:AK47","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AL47:AN47","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AO47:AR47","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AU47:AU47","Meiryo UI", 11,false, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AV47:AX47","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,192,0}));
        styleList.add(new CellStyleInfo(
                "AY47:BA47","Meiryo UI", 11,true, false,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,192,0}));
        styleList.add(new CellStyleInfo(
                "BB47:BF47","Meiryo UI", 11,true, false,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,192,0}));
        styleList.add(new CellStyleInfo(
                "BG47:BK47","Meiryo UI", 11,true, false,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,192,0}));
        styleList.add(new CellStyleInfo(
                "BL47:BM47","Meiryo UI", 11,true, false,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,192,0}));
        styleList.add(new CellStyleInfo(
                "BN47:BO47","Meiryo UI", 11,true, false,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,192,0}));
        return styleList;
    }

    private ArrayList<CellStyleInfo> initStyleList_3() {
        ArrayList<CellStyleInfo> styleList = new ArrayList<>();
        styleList.add(new CellStyleInfo(
                "B52:B52","Meiryo UI", 11,false, true,
                "center","center","thin","thin","thin",
                "thin",false, new int[]{201,255,255}));
        styleList.add(new CellStyleInfo(
                "C52:AR52","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{201,255,255}));
        styleList.add(new CellStyleInfo(
                "B53:B53","Meiryo UI", 11,false, true,
                "center","center","thin","thin","thin",
                "thin",false, new int[]{201,255,255}));
        styleList.add(new CellStyleInfo(
                "C53:C53","Meiryo UI", 11,false, true,
                "center","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "D53:AR53","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "B54:B54","Meiryo UI", 11,false, true,
                "center","center","thin","thin","thin",
                "thin",false, new int[]{201,255,255}));
        styleList.add(new CellStyleInfo(
                "C54:C54","Meiryo UI", 11,false, true,
                "center","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "D54:D54","Meiryo UI", 11,false, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "E54:K54","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "L54:M54","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "N54:P54","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "Q54:W54","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "X54:AE54","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AF54:AH54","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AI54:AK54","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AL54:AN54","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AO54:AR54","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AU54:AU54","Meiryo UI", 11,false, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AV54:AX54","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,192,0}));
        styleList.add(new CellStyleInfo(
                "AY54:BA54","Meiryo UI", 11,true, false,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,192,0}));
        styleList.add(new CellStyleInfo(
                "BB54:BF54","Meiryo UI", 11,true, false,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,192,0}));
        styleList.add(new CellStyleInfo(
                "BG54:BH54","Meiryo UI", 11,true, false,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,192,0}));
        styleList.add(new CellStyleInfo(
                "BI54:BK54","Meiryo UI", 11,true, false,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,192,0}));
        styleList.add(new CellStyleInfo(
                "BL54:BM54","Meiryo UI", 11,true, false,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,192,0}));
        styleList.add(new CellStyleInfo(
                "BN54:BO54","Meiryo UI", 11,true, false,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,192,0}));
        styleList.add(new CellStyleInfo(
                "BP54:BQ54","Meiryo UI", 11,true, false,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,192,0}));
        return styleList;
    }

    private ArrayList<CellStyleInfo> initStyleList_4() {
        ArrayList<CellStyleInfo> styleList = new ArrayList<>();
        styleList.add(new CellStyleInfo(
                "B89:B89","Meiryo UI", 11,false, true,
                "center","center","thin","thin","thin",
                "thin",false, new int[]{201,255,255}));
        styleList.add(new CellStyleInfo(
                "C89:AR89","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{201,255,255}));
        styleList.add(new CellStyleInfo(
                "B90:B90","Meiryo UI", 11,false, true,
                "center","center","thin","thin","thin",
                "thin",false, new int[]{201,255,255}));
        styleList.add(new CellStyleInfo(
                "C90:C90","Meiryo UI", 11,false, true,
                "center","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "D90:K90","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "L90:M90","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "N90:P90","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "Q90:W90","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "X90:AE90","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AF90:AH90","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AI90:AK90","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AL90:AN90","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AO90:AR90","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AU90:AU90","Meiryo UI", 11,false, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AV90:AX90","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AY90:BC90","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "BD90:BF90","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "BG90:BI90","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "BJ90:BM90","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,192,0}));
        styleList.add(new CellStyleInfo(
                "BN90:BO90","Meiryo UI", 11,true, false,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,192,0}));
        styleList.add(new CellStyleInfo(
                "BP90:BQ90","Meiryo UI", 11,true, false,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,192,0}));
        return styleList;
    }

    private ArrayList<CellStyleInfo> initStyleList_5() {
        ArrayList<CellStyleInfo> styleList = new ArrayList<>();
        styleList.add(new CellStyleInfo(
                "B66:B66","Meiryo UI", 11,false, true,
                "center","center","thin","thin","thin",
                "thin",false, new int[]{201,255,255}));
        styleList.add(new CellStyleInfo(
                "C66:K66","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{201,255,255}));
        styleList.add(new CellStyleInfo(
                "L66:M66","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{201,255,255}));
        styleList.add(new CellStyleInfo(
                "N66:W66","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{201,255,255}));
        styleList.add(new CellStyleInfo(
                "X66:AE66","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{201,255,255}));
        styleList.add(new CellStyleInfo(
                "AF66:AK66","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{201,255,255}));
        styleList.add(new CellStyleInfo(
                "AL66:AN66","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{201,255,255}));
        styleList.add(new CellStyleInfo(
                "AO66:AR66","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{201,255,255}));
        styleList.add(new CellStyleInfo(
                "AU66:AU66","Meiryo UI", 11,false, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AV66:AX66","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AY66:BC66","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "BD66:BF66","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "BG66:BI66","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "BJ66:BM66","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "BN66:BO66","Meiryo UI", 11,true, false,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,192,0}));
        styleList.add(new CellStyleInfo(
                "BP66:BQ66","Meiryo UI", 11,true, false,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,192,0}));
        return styleList;
    }


    private ArrayList<CellStyleInfo> initStyleList_6() {
        ArrayList<CellStyleInfo> styleList = new ArrayList<>();
        styleList.add(new CellStyleInfo(
                "B65:B65","Meiryo UI", 11,false, true,
                "center","center","thin","thin","thin",
                "thin",false, new int[]{201,255,255}));
        styleList.add(new CellStyleInfo(
                "C65:AR65","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{201,255,255}));

        styleList.add(new CellStyleInfo(
                "B66:B67","Meiryo UI", 11,true, true,
                "center","center","thin","thin","thin",
                "thin",false, new int[]{201,255,255}));
        styleList.add(new CellStyleInfo(
                "C66:C67","Meiryo UI", 11,true, true,
                "center","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "D66:G67","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));

        styleList.add(new CellStyleInfo(
                "H66:S66","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "T66:AE66","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AF66:AR66","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));

        styleList.add(new CellStyleInfo(
                "AU66:AU67","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AV66:AX67","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));

        styleList.add(new CellStyleInfo(
                "AY66:BM66","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "BN66:CB66","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));

        styleList.add(new CellStyleInfo(
                "CC66:CQ66","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));

        styleList.add(new CellStyleInfo(
                "H67:H67","Meiryo UI", 11,false, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "I67:M67","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "N67:S67","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "R67:S67","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));

        styleList.add(new CellStyleInfo(
                "T67:T67","Meiryo UI", 11,false, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "U67:Y67","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "Z67:AE67","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AD67:AE67","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));

        styleList.add(new CellStyleInfo(
                "AF67:AF67","Meiryo UI", 11,false, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AG67:AK67","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AL67:AR67","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AQ67:AR67","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));

        styleList.add(new CellStyleInfo(
                "AY67:BC67","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "BD67:BF67","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "BG67:BI67","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "BJ67:BM67","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));

        styleList.add(new CellStyleInfo(
                "BN67:BR67","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "BS67:BU67","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "BV67:BX67","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "BY67:CB67","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));

        styleList.add(new CellStyleInfo(
                "CC67:CG67","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "CH67:CJ67","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "CK67:CM67","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "CN67:CQ67","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));

        return styleList;
    }

    private ArrayList<CellStyleInfo> initStyleList_7() {
        ArrayList<CellStyleInfo> styleList = new ArrayList<>();
        styleList.add(new CellStyleInfo(
                "B72:B72","Meiryo UI", 11,false, true,
                "center","center","thin","thin","thin",
                "thin",false, new int[]{201,255,255}));
        styleList.add(new CellStyleInfo(
                "C72:AR72","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{201,255,255}));

        styleList.add(new CellStyleInfo(
                "B73:B74","Meiryo UI", 11,true, true,
                "center","center","thin","thin","thin",
                "thin",false, new int[]{201,255,255}));
        styleList.add(new CellStyleInfo(
                "C73:C74","Meiryo UI", 11,true, true,
                "center","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "D73:G74","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));

        styleList.add(new CellStyleInfo(
                "H73:S73","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "T73:AE73","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AF73:AR73","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));

        styleList.add(new CellStyleInfo(
                "AU73:AU74","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AV73:AX74","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));

        styleList.add(new CellStyleInfo(
                "AY73:BM73","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "BN73:CB73","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "CC73:CQ73","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));

        styleList.add(new CellStyleInfo(
                "H74:H74","Meiryo UI", 11,false, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "I74:M74","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "N74:S74","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "R74:S74","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));

        styleList.add(new CellStyleInfo(
                "T74:T74","Meiryo UI", 11,false, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "U74:Y74","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "Z74:AE74","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AD74:AE74","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));

        styleList.add(new CellStyleInfo(
                "AF74:AF74","Meiryo UI", 11,false, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AG74:AK74","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AL74:AR74","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AQ74:AR74","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));

        styleList.add(new CellStyleInfo(
                "AY74:BC74","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "BD74:BF74","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "BG74:BI74","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "BJ74:BM74","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));

        styleList.add(new CellStyleInfo(
                "BN74:BR74","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "BS74:BU74","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "BV74:BX74","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "BY74:CB74","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));

        styleList.add(new CellStyleInfo(
                "CC74:CG74","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "CH74:CJ74","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "CK74:CM74","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "CN74:CQ74","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        return styleList;
    }

    private ArrayList<CellStyleInfo> initStyleList_8() {
        ArrayList<CellStyleInfo> styleList = new ArrayList<>();
        styleList.add(new CellStyleInfo(
                "B103:B103","Meiryo UI", 11,false, true,
                "center","center","thin","thin","thin",
                "thin",false, new int[]{201,255,255}));
        styleList.add(new CellStyleInfo(
                "C103:AR103","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{201,255,255}));
        styleList.add(new CellStyleInfo(
                "B104:B104","Meiryo UI", 11,false, true,
                "center","center","thin","thin","thin",
                "thin",false, new int[]{201,255,255}));
        styleList.add(new CellStyleInfo(
                "C104:C104","Meiryo UI", 11,false, true,
                "center","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "D104:K104","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "L104:M104","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "N104:U104","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "V104:AE104","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AF104:AH104","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AI104:AK104","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AL104:AN104","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AO104:AR104","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AU104:AU104","Meiryo UI", 11,false, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AV104:AX104","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "AY104:AZ104","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "BA104:BE104","Meiryo UI", 11,true, true,
                "left","center","","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "BF104:BG104","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "BH104:BI104","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "BJ104:BM104","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "BN104:BO104","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        styleList.add(new CellStyleInfo(
                "BP104:BQ104","Meiryo UI", 11,true, true,
                "left","center","thin","thin","thin",
                "thin",false, new int[]{255,205,156}));
        return styleList;
    }
}
