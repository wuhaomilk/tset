package sketch.hmi;

import javafx.scene.control.Cell;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sketch.common.*;
import sketch.settings.DestGradeStatic;

import java.util.ArrayList;
import java.util.HashMap;

public class HMIDestGrade {
    private XSSFWorkbook workBook = null;
    private XSSFSheet sheetInfo = null;

    public HMIDestGrade(XSSFWorkbook _workBook, XSSFSheet _xssfSheet) {
        this.workBook = _workBook;
        this.sheetInfo = _xssfSheet;
        DestGradeStatic staticInfo = new DestGradeStatic();

//        for (int i = 10; i<=36; i=i+2) {
//            staticInfo.styleList.add(new CellStyleInfo(
//                    String.format("G%d:G%d", i, i+1),"Meiryo UI", 11,true, false,
//                    "center","center","thin","thin","thin",
//                    "thin",true, new int[]{}));
//            staticInfo.styleList.add(new CellStyleInfo(
//                    String.format("H%d:J%d", i, i),"Meiryo UI", 11,true, false,
//                    "center","center","thin","thin","thin",
//                    "dotted",true, new int[]{}));
//            staticInfo.styleList.add(new CellStyleInfo(
//                    String.format("H%d:J%d", i+1, i+1),"Meiryo UI", 11,true, false,
//                    "center","center","thin","dotted","thin",
//                    "thin",true, new int[]{}));
//            staticInfo.styleList.add(new CellStyleInfo(
//                    String.format("K%d:K%d", i,i),"Meiryo UI", 11,false, false,
//                    "center","center","thin","thin","dotted",
//                    "dotted",true, new int[]{234, 241, 221}));
//            staticInfo.styleList.add(new CellStyleInfo(
//                    String.format("K%d:K%d",i+1, i+1),"Meiryo UI", 11,false, false,
//                    "center","center","thin","dotted","dotted",
//                    "thin",true, new int[]{234, 241, 221}));
//            staticInfo.styleList.add(new CellStyleInfo(
//                    String.format("L%d:M%d", i, i),"Meiryo UI", 11,false, false,
//                    "center","center","dotted","thin","dotted",
//                    "dotted",true, new int[]{234, 241, 221}));
//            staticInfo.styleList.add(new CellStyleInfo(
//                    String.format("L%d:M%d", i+1, i+1),"Meiryo UI", 11,false, false,
//                    "center","center","dotted","dotted","dotted",
//                    "thin",true, new int[]{234, 241, 221}));
//            staticInfo.styleList.add(new CellStyleInfo(
//                    String.format("N%d:N%d", i, i),"Meiryo UI", 11,false, false,
//                    "center","center","dotted","thin","thin",
//                    "dotted",true, new int[]{234, 241, 221}));
//            staticInfo.styleList.add(new CellStyleInfo(
//                    String.format("N%d:N%d", i+1, i+1),"Meiryo UI", 11,false, false,
//                    "center","center","dotted","dotted","thin",
//                    "thin",true, new int[]{234, 241, 221}));
//            staticInfo.styleList.add(new CellStyleInfo(
//                    String.format("O%d:O%d",i,i),"Meiryo UI", 11,false, false,
//                    "center","center","thin","thin","dotted",
//                    "dotted",true, new int[]{219, 238, 243}));
//            staticInfo.styleList.add(new CellStyleInfo(
//                    String.format("O%d:O%d",i+1,i+1),"Meiryo UI", 11,false, false,
//                    "center","center","thin","dotted","dotted",
//                    "thin",true, new int[]{219, 238, 243}));
//            staticInfo.styleList.add(new CellStyleInfo(
//                    String.format("P%d:P%d",i,i),"Meiryo UI", 11,false, false,
//                    "center","center","dotted","thin","dotted",
//                    "dotted",true, new int[]{219, 238, 243}));
//            staticInfo.styleList.add(new CellStyleInfo(
//                    String.format("P%d:P%d", i+1, i+1),"Meiryo UI", 11,false, false,
//                    "center","center","dotted","dotted","dotted",
//                    "thin",true, new int[]{219, 238, 243}));
//            staticInfo.styleList.add(new CellStyleInfo(
//                    String.format("Q%d:Q%d",i, i),"Meiryo UI", 11,false, false,
//                    "center","center","dotted","thin","thin",
//                    "dotted",true, new int[]{219, 238, 243}));
//            staticInfo.styleList.add(new CellStyleInfo(
//                    String.format("Q%d:Q%d", i+1, i+1),"Meiryo UI", 11,false, false,
//                    "center","center","dotted","dotted","thin",
//                    "thin",true, new int[]{219, 238, 243}));
//        }


        CellSizeSet cellSizeSet = new CellSizeSet(this.workBook, this.sheetInfo);
        cellSizeSet.doSetRowHeightList(staticInfo.rowHeightList);

        CellStyleSet cellStyleSet = new CellStyleSet(this.workBook, this.sheetInfo);
        cellStyleSet.doCellStyleList(staticInfo.styleList);

        CellValueSet cellValueSet = new CellValueSet(this.workBook, this.sheetInfo);
        cellValueSet.doCellValueList(staticInfo.lableList);
    }

    public void doExportInfo(HashMap<String,String> destGradeMap) {
        int startLine = 10;
        String startColStr = "K";

        CellValueSet cellValueSet = new CellValueSet(this.workBook, this.sheetInfo);

        ArrayList<String> countryList = new ArrayList<String>();
        countryList.add("Japan");
        countryList.add("Europe/Russia");
        countryList.add("China");
        countryList.add("Oceania");
        countryList.add("South Africa");
        countryList.add("HongKong / Macao");
        countryList.add("South East Asia");
        countryList.add("India");
        countryList.add("Taiwan");
        countryList.add("Middle East");
        countryList.add("South and Central (Brazil & Argentina)");
        countryList.add("South and Central");
        countryList.add("Korea");
        countryList.add("North America");

        ArrayList<String> aisList = new ArrayList<String>();
        aisList.add("Available");
        aisList.add("Unavailable");

        HashMap<String, ArrayList<String>> carMachineTypeMap = new HashMap<String, ArrayList<String>>();
        ArrayList<String> toyotaMachineTypeList = new ArrayList<String>();
        toyotaMachineTypeList.add("Entry DA");
        toyotaMachineTypeList.add("T1");
        toyotaMachineTypeList.add("T2");
        toyotaMachineTypeList.add("T-EMVN");
        carMachineTypeMap.put("Toyota", toyotaMachineTypeList);

        ArrayList<String> lexusMachineTypeList = new ArrayList<String>();
        lexusMachineTypeList.add("L1");
        lexusMachineTypeList.add("L1.5");
        lexusMachineTypeList.add("L2");
        carMachineTypeMap.put("Lexus", lexusMachineTypeList);

        int iLine = startLine;
        for (int iCountry = 0; iCountry<countryList.size(); iCountry++) {
            for (int iAis = 0; iAis < aisList.size(); iAis++) {
                ArrayList<String> toyotaCarMachineList = carMachineTypeMap.get("Toyota");
                ArrayList<String> lexusCarMachineList = carMachineTypeMap.get("Lexus");

                int startColIndex = CellReference.convertColStringToIndex(startColStr);

                for (int imachine = 0; imachine < toyotaCarMachineList.size(); imachine++) {
                    String posStr = String.format("%s%d", CellReference.convertNumToColString(startColIndex), iLine);

                    String valKey = countryList.get(iCountry)+"_Toyota_"+aisList.get(iAis)+"_"+toyotaCarMachineList.get(imachine);
                    String destGradeVal = "";
                    if (destGradeMap.containsKey(valKey)) {
                        destGradeVal = destGradeMap.get(valKey);
                    }
                    if (destGradeVal.length() > 0) {
                        cellValueSet.doOneCellValue(new SheetPosValue(posStr, destGradeVal));
                    }
                    startColIndex = startColIndex+1;
                }

                for (int imachine = 0; imachine < lexusCarMachineList.size(); imachine++) {
                    String posStr = String.format("%s%d", CellReference.convertNumToColString(startColIndex), iLine);

                    String valKey = countryList.get(iCountry)+"_Lexus_"+aisList.get(iAis)+"_"+lexusCarMachineList.get(imachine);
                    String destGradeVal = "";
                    if (destGradeMap.containsKey(valKey)) {
                        destGradeVal = destGradeMap.get(valKey);
                    }
                    if (destGradeVal.length() > 0) {
                        cellValueSet.doOneCellValue(new SheetPosValue(posStr, destGradeVal));
                    }
                    startColIndex = startColIndex+1;
                }


                iLine = iLine+1;
            }
        }
    }
}
