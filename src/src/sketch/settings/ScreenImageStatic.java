package sketch.settings;

import sketch.common.CellStyleInfo;
import sketch.common.SheetPosValue;

import java.util.ArrayList;

public class ScreenImageStatic {
    public ArrayList<SheetPosValue> rowHeightList = new ArrayList<SheetPosValue>();
    public ArrayList<CellStyleInfo> styleList = new ArrayList<CellStyleInfo>();
    public ArrayList<SheetPosValue> lableList = new ArrayList<SheetPosValue>();

    public ScreenImageStatic() {
        initRowHeightList();
        initLableList();
        initStyleList();
    }

    private void initRowHeightList() {
//        rowHeightList.add(new SheetPosValue("41:42", "350"));
    }

    private void initLableList() {
//        lableList.add(new SheetPosValue("A41", "Screen Image"));
    }

    private void initStyleList() {
//        styleList.add(new CellStyleInfo(
//                "A41:AU41","Meiryo UI", 11,false, false,
//                "","top","","medium","",
//                "",false, new int[]{204,255,204}));
//        styleList.add(new CellStyleInfo(
//                "A42:AU42","Meiryo UI", 11,false, false,
//                "","top","","","",
//                "medium",false, new int[]{204,255,204}));
    }
}
