package sketch.settings;

import sketch.common.CellStyleInfo;
import sketch.common.SheetPosValue;

import java.util.ArrayList;

public class HeaderTableStatic {
    public ArrayList<SheetPosValue> colWidthList = new ArrayList<SheetPosValue>();
    public ArrayList<SheetPosValue> rowHeightList = new ArrayList<SheetPosValue>();
    public ArrayList<CellStyleInfo> styleList = new ArrayList<CellStyleInfo>();
    public ArrayList<SheetPosValue> lableList = new ArrayList<SheetPosValue>();

    public HeaderTableStatic() {
        initColWidthList();
        initRowHeightList();
        styleList.add(new CellStyleInfo(
                "A1:B1","Meiryo UI", 11,true, false,
                "","bottom","","","",
                "",false, new int[]{}));
        styleList.add(new CellStyleInfo(
                "A2:B2","Meiryo UI", 11,true, true,
                "","center","thin","thin","thin",
                "thin",false, new int[]{}));
        styleList.add(new CellStyleInfo(
                "C2:I2","Meiryo UI", 11,true, false,
                "","center","thin","thin","thin",
                "thin",false, new int[]{}));
        styleList.add(new CellStyleInfo(
                "A3:B3","Meiryo UI", 11,true, true,
                "","center","thin","thin","thin",
                "thin",false, new int[]{}));
        styleList.add(new CellStyleInfo(
                "C3:I3","Meiryo UI", 11,true, false,
                "","center","thin","thin","thin",
                "thin",false, new int[]{}));
        styleList.add(new CellStyleInfo(
                "A4:B4","Meiryo UI", 11,true, true,
                "","center","thin","thin","thin",
                "thin",false, new int[]{}));
        styleList.add(new CellStyleInfo(
                "C4:I4","Meiryo UI", 11,true, false,
                "","center","thin","thin","thin",
                "thin",false, new int[]{}));
        styleList.add(new CellStyleInfo(
                "A5:A5","Meiryo UI", 11,false, true,
                "","top","thin","thin","thin",
                "thin",false, new int[]{}));
        styleList.add(new CellStyleInfo(
                "B5:I5","Meiryo UI", 11,true, false,
                "","top","thin","thin","thin",
                "thin",false, new int[]{}));

        lableList.add(new SheetPosValue("A1", "Screen Spec"));
        lableList.add(new SheetPosValue("A2", "Screen ID:"));
        lableList.add(new SheetPosValue("A3", "Basic Screen ID:"));
        lableList.add(new SheetPosValue("A4", "Screen Name:"));
        lableList.add(new SheetPosValue("A5", "Note:"));
    }

    private void initColWidthList() {
        // initialize sheet's column width list
        colWidthList.add(new SheetPosValue("A:BO", "5"));
//        colWidthList.add(new SheetPosValue("B", "5"));
//        colWidthList.add(new SheetPosValue("C", "5"));
//        colWidthList.add(new SheetPosValue("D", "5"));
//        colWidthList.add(new SheetPosValue("E", "5"));
//        colWidthList.add(new SheetPosValue("F", "5"));
//        colWidthList.add(new SheetPosValue("G", "40.0"));
//        colWidthList.add(new SheetPosValue("H", "9.75"));
//        colWidthList.add(new SheetPosValue("I", "14.75"));
//        colWidthList.add(new SheetPosValue("J", "6.88"));
//        colWidthList.add(new SheetPosValue("K:Q", "13.63"));
//        colWidthList.add(new SheetPosValue("R", "8.5"));
//        colWidthList.add(new SheetPosValue("S", "3.25"));
//        colWidthList.add(new SheetPosValue("T", "40"));
//        colWidthList.add(new SheetPosValue("U", "9.75"));
//        colWidthList.add(new SheetPosValue("V", "14.75"));
//        colWidthList.add(new SheetPosValue("W:Z", "30.0"));
//        colWidthList.add(new SheetPosValue("AA", "8.5"));
//        colWidthList.add(new SheetPosValue("AB", "3.25"));
//        colWidthList.add(new SheetPosValue("AC", "40"));
//        colWidthList.add(new SheetPosValue("AD", "9.75"));
//        colWidthList.add(new SheetPosValue("AE", "14.75"));
//        colWidthList.add(new SheetPosValue("AF", "8.5"));
//        colWidthList.add(new SheetPosValue("AG", "4.0"));
//        colWidthList.add(new SheetPosValue("AH", "40.0"));
//        colWidthList.add(new SheetPosValue("AI", "9.75"));
//        colWidthList.add(new SheetPosValue("AJ", "14.75"));
//        colWidthList.add(new SheetPosValue("AK", "8.5"));
//        colWidthList.add(new SheetPosValue("AL", "4"));
//        colWidthList.add(new SheetPosValue("AM", "40"));
//        colWidthList.add(new SheetPosValue("AN", "9.75"));
//        colWidthList.add(new SheetPosValue("AO", "14.75"));
//        colWidthList.add(new SheetPosValue("AP", "8.25"));
//        colWidthList.add(new SheetPosValue("AQ", "17.25"));
//        colWidthList.add(new SheetPosValue("AR", "33.13"));
//        colWidthList.add(new SheetPosValue("AS:AU", "17.25"));
//        colWidthList.add(new SheetPosValue("AV", "35.0"));
//        colWidthList.add(new SheetPosValue("AW", "35.0"));
    }

    private void initRowHeightList() {
        //initialize sheet's row height list
        rowHeightList.add(new SheetPosValue("2:4", "33.5"));
        rowHeightList.add(new SheetPosValue("5", "175.25"));
    }
}
