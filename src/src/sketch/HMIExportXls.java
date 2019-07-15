package sketch;

import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import sketch.common.JsonSketchInfo_new;
import sketch.hmi.HMIJsonDataParse_new;
import sketch.hmi.HMIPartsSpec_new;
import sketch.hmi.HMIScreenImage;
import sketch.settings.HMIDropDownData;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class HMIExportXls {
    public static void main(String[] args) throws Exception {
//        if (args.length != 2) {
//            System.out.println("Usage:HMIExportXls <InputJsonFile> <OutputExcelFile>");
//            for (String iArgs : args) {
//                System.out.println(iArgs);
//            }
//            throw new Exception("Parameter is error");
//        }
//
//        if (checkFileExist(args[0]) == false) {
//            System.out.println("[ERROR]Input JsonFile can not be found."+args[0]);
//            throw new Exception("Input JsonFile does not exist");
//        }

        String jsonFilePath = args[0];
        String outputPath = args[1]+"/";
//        String jsonFilePath = "/Users/AirMachen/Desktop/test/";
//        String outputPath = "/Users/AirMachen/Desktop/test/";
//        String dropDownDataPath1 = "/Users/AirMachen/Desktop/test/demo/DropDwonData.xlsx";
//        String dropDownDataPath2 = "/Users/AirMachen/Desktop/test/demo/iAuto_Spec_Config.xlsx";
        String dropDownDataPath1 = args[2];
        String dropDownDataPath2 = args[3];
        resolveDropDownData(dropDownDataPath1);
        resolveDropDownData(dropDownDataPath2);
        tranveseDir(jsonFilePath, outputPath);
    }

    private static void resolveDropDownData(String dropDownDataPath) {
        File excelFile = new File(dropDownDataPath);
        XSSFWorkbook wb = null;
        try {
            wb = new XSSFWorkbook(new FileInputStream(excelFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        HMIDropDownData dropDownData = HMIDropDownData.getInstance();
        HMIDropDownData.DisplayDropDownData displayDropDownData = dropDownData.getDisplayDropDownDataInstance();
        HMIDropDownData.ActiveDropDownData activeDropDownData = dropDownData.getActiveDropDownDataInstance();
        HMIDropDownData.ActionDropDownData actionDropDownData = dropDownData.getActionDropDownDataInstance();
        HMIDropDownData.HKDropDownData hkDropDownData = dropDownData.getHkDropDownDataInstance();
        HMIDropDownData.InitDropDownData initDropDownData = dropDownData.getInitDropDownDataInstance();
        HMIDropDownData.StatusChangeDropDownData statusChangeDropDownData = dropDownData.getStatusChangeDropDownDataInstance();
        HMIDropDownData.TransitionDropDownData transitionDropDownData = dropDownData.getTransitionDropDownDataInstance();
        HMIDropDownData.TrigDropDownData trigDropDownData = dropDownData.getTrigDropDownDataInstance();
        int numberOfSheets = wb.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; ++i) {
            XSSFSheet sheet = wb.getSheetAt(i);

            if (sheet.getSheetName().equals("LiveData")) {
                ArrayList<String> displayInSuchCondition = new ArrayList<>();
                ArrayList<String> addDisplayConditionInViewModel = new ArrayList<>();
                int columnNum = 0;
                if (sheet.getRow(0) != null) {
                    columnNum = sheet.getRow(0).getLastCellNum() - sheet.getRow(0).getFirstCellNum();
                }
                if (columnNum > 0) {
                    boolean isEnd = false;
                    for (Row row : sheet) {
                        if (row.getRowNum() == 0) {
                            continue;
                        }
                        for (int j = 0; j < columnNum; ++j) {
                            Cell cell = row.getCell(j);
                            String value = cell.getStringCellValue();
                            if (value.isEmpty()) {
                                isEnd = true;
                                break;
                            }
                            if (j == 0) {
                                displayInSuchCondition.add(value);
                            } else if (j == 1) {
                                addDisplayConditionInViewModel.add(value);
                            } else {

                            }
                        }
                        if (isEnd) {
                            break;
                        }
                    }
                }
                displayDropDownData.setDisplayInSucnConditionData(displayInSuchCondition);
                displayDropDownData.setAddDisplayConditionInViewModelData(addDisplayConditionInViewModel);
                activeDropDownData.setDisplayInSucnConditionData(displayInSuchCondition);
                activeDropDownData.setAddDisplayConditionInViewModelData(addDisplayConditionInViewModel);
            } else if (sheet.getSheetName().equals("Condition")) {
                ArrayList<String> displayCondition = new ArrayList<>();
                ArrayList<String> displayConditionInViewModel = new ArrayList<>();
                int columnNum = 0;
                if (sheet.getRow(0) != null) {
                    columnNum = sheet.getRow(0).getLastCellNum() - sheet.getRow(0).getFirstCellNum();
                }
                if (columnNum > 0) {
                    boolean isEnd = false;
                    for (Row row : sheet) {
                        if (row.getRowNum() == 0) {
                            continue;
                        }
                        for (int j = 0; j < columnNum; ++j) {
                            Cell cell = row.getCell(j);
                            String value = cell.getStringCellValue();
                            if (value.isEmpty()) {
                                isEnd = true;
                                break;
                            }
                            if (j == 0) {
                                displayCondition.add(value);
                            } else if (j == 1) {
                                displayConditionInViewModel.add(value);
                            } else {

                            }
                        }
                        if (isEnd) {
                            break;
                        }
                    }
                }
                displayDropDownData.setDisplayConditionData(displayCondition);
                displayDropDownData.setDisplayConditionInViewModel(displayConditionInViewModel);
                activeDropDownData.setConditionData(displayCondition);
                activeDropDownData.setDisplayConditionInViewModelData(displayConditionInViewModel);
                actionDropDownData.setConditionOfActionData(displayCondition);
                actionDropDownData.setViewModelData(displayConditionInViewModel);
                hkDropDownData.setConditionOfActionData(displayCondition);
                hkDropDownData.setViewModelData(displayConditionInViewModel);
                initDropDownData.setConditionOfActionData(displayCondition);
                initDropDownData.setViewModelData(displayConditionInViewModel);
                statusChangeDropDownData.setbConditionData(displayCondition);
                statusChangeDropDownData.setfConditionData(displayCondition);
                statusChangeDropDownData.setiConditionData(displayCondition);
                statusChangeDropDownData.setbViewModelData(displayConditionInViewModel);
                statusChangeDropDownData.setfViewModelData(displayConditionInViewModel);
                statusChangeDropDownData.setiViewModelData(displayConditionInViewModel);
                transitionDropDownData.setbConditionData(displayCondition);
                transitionDropDownData.setfConditionData(displayCondition);
                transitionDropDownData.setiConditionData(displayCondition);
                transitionDropDownData.setbViewModelData(displayConditionInViewModel);
                transitionDropDownData.setfViewModelData(displayConditionInViewModel);
                transitionDropDownData.setiViewModelData(displayConditionInViewModel);
                trigDropDownData.setConditionOfActionData(displayCondition);
                trigDropDownData.setViewModelData(displayConditionInViewModel);
            } else if (sheet.getSheetName().equals("Request")) {
                ArrayList<String> actionInSuchCondition = new ArrayList<>();
                ArrayList<String> funcOfModel = new ArrayList<>();
                int columnNum = 0;
                if (sheet.getRow(0) != null) {
                    columnNum = sheet.getRow(0).getLastCellNum() - sheet.getRow(0).getFirstCellNum();
                }
                if (columnNum > 0) {
                    boolean isEnd = false;
                    for (Row row : sheet) {
                        if (row.getRowNum() == 0) {
                            continue;
                        }
                        String request = "";
                        String paramType = "";
                        String paramValue = "";
                        for (int j = 0; j < columnNum; ++j) {
                            Cell cell = row.getCell(j);
                            String value = cell.getStringCellValue();
                            if (value.isEmpty()) {
                                isEnd = true;
                                break;
                            }
                            if (j == 0) {
                                actionInSuchCondition.add(value);
                            } else if (j == 1) {
                                request = value;
                            } else if (j == 2) {
                                paramType = value;
                            } else if (j == 3) {
                                paramValue = value;
                            } else {

                            }
                        }
                        String[] paramTypeArr = paramType.split(",");
                        String[] paramValueArr = paramValue.split(",");
                        String tmpFuncOfModel = request + "(";
                        for (int k = 0; k < paramTypeArr.length; ++k) {
                            tmpFuncOfModel += "<" + paramTypeArr[k].trim() + "> " + paramValueArr[k].trim();
                            if (k + 1 == paramTypeArr.length) {
                                tmpFuncOfModel += ")";
                            } else {
                                tmpFuncOfModel += ", ";
                            }
                        }
                        funcOfModel.add(tmpFuncOfModel);
                        if (isEnd) {
                            break;
                        }
                    }
                }
                actionDropDownData.setActionInSuchConditionData(actionInSuchCondition);
                actionDropDownData.setFuncOfModelData(funcOfModel);
                hkDropDownData.setActionInSuchConditionData(actionInSuchCondition);
                hkDropDownData.setFuncOfModelData(funcOfModel);
                initDropDownData.setActionInSuchConditionData(actionInSuchCondition);
                initDropDownData.setFuncOfModelData(funcOfModel);
                statusChangeDropDownData.setfActionData(actionInSuchCondition);
                statusChangeDropDownData.setbActionData(actionInSuchCondition);
                statusChangeDropDownData.setiActionData(actionInSuchCondition);
                statusChangeDropDownData.setfFuncOfModelData(funcOfModel);
                statusChangeDropDownData.setbFuncOfModelData(funcOfModel);
                statusChangeDropDownData.setiFuncOfModelData(funcOfModel);
                transitionDropDownData.setbActionData(actionInSuchCondition);
                transitionDropDownData.setfActionData(actionInSuchCondition);
                transitionDropDownData.setiActionData(actionInSuchCondition);
                transitionDropDownData.setbFuncOfModelData(funcOfModel);
                transitionDropDownData.setfFuncOfModelData(funcOfModel);
                transitionDropDownData.setiFuncOfModelData(funcOfModel);
                trigDropDownData.setActionInSuchConditionData(actionInSuchCondition);
                trigDropDownData.setFuncOfModelData(funcOfModel);
            } else if (sheet.getSheetName().equals("Notify")) {
                ArrayList<String> name = new ArrayList<>();
                ArrayList<String> trigger = new ArrayList<>();
                ArrayList<String> observer = new ArrayList<>();
                ArrayList<String> reply = new ArrayList<>();
                int columnNum = 0;
                if (sheet.getRow(0) != null) {
                    columnNum = sheet.getRow(0).getLastCellNum() - sheet.getRow(0).getFirstCellNum();
                }
                if (columnNum > 0) {
                    boolean isEnd = false;
                    for (Row row : sheet) {
                        if (row.getRowNum() == 0) {
                            continue;
                        }
                        for (int j = 0; j < columnNum; ++j) {
                            Cell cell = row.getCell(j);
                            String value = cell.getStringCellValue();
                            if (value.isEmpty()) {
                                isEnd = true;
                                break;
                            }
                            if (j == 0) {
                                name.add(value);
                            } else if (j == 1) {
                                trigger.add(value);
                            } else if (j == 2) {
                                observer.add(value);
                            } else if (j == 3) {
                                reply.add(value);
                            } else {

                            }
                        }
                        if (isEnd) {
                            break;
                        }
                    }
                }
                trigDropDownData.setNameData(name);
                trigDropDownData.setTriggerData(trigger);
                trigDropDownData.setObserverData(observer);
                trigDropDownData.setReplyData(reply);
            } else if (sheet.getSheetName().equals("OpeType")) {
                ArrayList<String> opeType = new ArrayList<>();
                ArrayList<String> event = new ArrayList<>();
                int columnNum = 0;
                if (sheet.getRow(0) != null) {
                    columnNum = sheet.getRow(0).getLastCellNum() - sheet.getRow(0).getFirstCellNum();
                }
                if (columnNum > 0) {
                    boolean isEnd = false;
                    for (Row row : sheet) {
                        if (row.getRowNum() == 0) {
                            continue;
                        }
                        for (int j = 0; j < columnNum; ++j) {
                            Cell cell = row.getCell(j);
                            String value = cell.getStringCellValue();
                            if (value.isEmpty()) {
                                isEnd = true;
                                break;
                            }
                            if (j == 0) {
                                opeType.add(value);
                            } else if (j == 1) {
                                event.add(value);
                            } else {

                            }
                        }
                        if (isEnd) {
                            break;
                        }
                    }
                }
                actionDropDownData.setOpeTypeData(opeType);
                actionDropDownData.setEventData(event);
                hkDropDownData.setOpeTypeData(opeType);
                hkDropDownData.setEventData(event);
            } else if (sheet.getSheetName().equals("Beep")) {
                ArrayList<String> sound = new ArrayList<>();
                int columnNum = 0;
                if (sheet.getRow(0) != null) {
                    columnNum = sheet.getRow(0).getLastCellNum() - sheet.getRow(0).getFirstCellNum();
                }
                if (columnNum > 0) {
                    boolean isEnd = false;
                    for (Row row : sheet) {
                        if (row.getRowNum() == 0) {
                            continue;
                        }
                        for (int j = 0; j < columnNum; ++j) {
                            Cell cell = row.getCell(j);
                            String value = cell.getStringCellValue();
                            if (value.isEmpty()) {
                                isEnd = true;
                                break;
                            }
                            if (j == 0) {
                                sound.add(value);
                            } else {

                            }
                        }
                        if (isEnd) {
                            break;
                        }
                    }
                }
                actionDropDownData.setSoundData(sound);
                hkDropDownData.setSoundData(sound);
                trigDropDownData.setSoundData(sound);
            } else if (sheet.getSheetName().equals("DuringDriving")) {
                ArrayList<String> duringDriving = new ArrayList<>();
                int columnNum = 0;
                if (sheet.getRow(0) != null) {
                    columnNum = sheet.getRow(0).getLastCellNum() - sheet.getRow(0).getFirstCellNum();
                }
                if (columnNum > 0) {
                    boolean isEnd = false;
                    for (Row row : sheet) {
                        if (row.getRowNum() == 0) {
                            continue;
                        }
                        for (int j = 0; j < columnNum; ++j) {
                            Cell cell = row.getCell(j);
                            String value = cell.getStringCellValue();
                            if (value.isEmpty()) {
                                isEnd = true;
                                break;
                            }
                            if (j == 0) {
                                duringDriving.add(value);
                            } else {

                            }
                        }
                        if (isEnd) {
                            break;
                        }
                    }
                }
                activeDropDownData.setDuringDriving(duringDriving);
                hkDropDownData.setDuringDriving(duringDriving);
            } else if (sheet.getSheetName().equals("Property")) {
                ArrayList<String> propertyType = new ArrayList<>();
                ArrayList<String> property = new ArrayList<>();
                int columnNum = 0;
                if (sheet.getRow(0) != null) {
                    columnNum = sheet.getRow(0).getLastCellNum() - sheet.getRow(0).getFirstCellNum();
                }
                if (columnNum > 0) {
                    boolean isEnd = false;
                    for (Row row : sheet) {
                        if (row.getRowNum() == 0) {
                            continue;
                        }
                        for (int j = 0; j < columnNum; ++j) {
                            Cell cell = row.getCell(j);
                            String value = cell.getStringCellValue();
                            if (value.isEmpty()) {
                                isEnd = true;
                                break;
                            }
                            if (j == 0) {
                                propertyType.add(value);
                            } else if (j == 1) {
                                property.add(value);
                            } else {

                            }
                        }
                        if (isEnd) {
                            break;
                        }
                    }
                }
                displayDropDownData.setPropertyTypeData(propertyType);
                displayDropDownData.setPropertyData(property);
                activeDropDownData.setPropertyTypeData(propertyType);
                activeDropDownData.setPropertyData(property);
            } else {

            }
        }


    }

    private static void doOneJsonFile(String resultPath, String jsonFileName, String outputPath) throws Exception {
        String result_dir = jsonFileName.substring(0, jsonFileName.lastIndexOf("."));

        File result_dir_info = new File(outputPath+result_dir);
        if (result_dir_info.exists() == false) {
            result_dir_info.mkdir();
        }

        ArrayList<JsonSketchInfo_new> sketchInfoList = new HMIJsonDataParse_new().parseFile(resultPath+jsonFileName);

        HashMap<String, Integer> doneScreenNameMap = new HashMap<String, Integer>();
        int imageStartRow = 0;

        for (int iindex = 0; iindex < sketchInfoList.size(); iindex++) {
            XSSFWorkbook outWorkbook = new XSSFWorkbook();

            JsonSketchInfo_new jsonSketchInfo = sketchInfoList.get(iindex);

            XSSFSheet outWorkSheet = outWorkbook.createSheet(toSlugFilename(jsonSketchInfo.getScreenID()));
            imageStartRow = new HMIPartsSpec_new(outWorkbook, outWorkSheet).doExportInfo(jsonSketchInfo);
            new HMIScreenImage(outWorkbook, outWorkSheet, imageStartRow + 1).doExportInfo(jsonSketchInfo);

            String nowScreenName = jsonSketchInfo.getScreenID() + " " + toSlugFilename(jsonSketchInfo.getScreenName());
            String outXls = outputPath+result_dir+"/"+nowScreenName+".xlsx";
            if (doneScreenNameMap.containsKey(nowScreenName)==true) {
                int fileIndex = doneScreenNameMap.get(nowScreenName).intValue();
                fileIndex = fileIndex+1;
                outXls = outputPath+result_dir+"/"+nowScreenName+"_"+fileIndex+".xlsx";
                doneScreenNameMap.replace(nowScreenName, fileIndex);
            }
            else {
                doneScreenNameMap.put(nowScreenName, 0);
            }

            OutputStream os = new FileOutputStream(new File(outXls));
            outWorkbook.write(os);
            os.close();
        }
    }

    private static void tranveseDir(String resultPath, String outputPath) throws Exception {
        File file = new File(resultPath);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null == files || files.length == 0) {
                return;
            }
            else {
                for (File iOneFile : files)  {
                    if (iOneFile.isDirectory()) {
                        continue;
                    }
                    else {
                        String iOneFileName = iOneFile.getName();
                        String iOneFileType = iOneFileName.substring(iOneFileName.lastIndexOf("."),
                                iOneFileName.length());
                        if (iOneFileType.equalsIgnoreCase(".json")) {
                            doOneJsonFile(resultPath, iOneFileName, outputPath);
                        }
                    }
                }
            }
        }
    }

    private static String toSlugFilename(String fileName) {
        return fileName.replace(" ", "")
                .replace("/", "")
                .replace(".", "_");
    }

    private static boolean checkFileExist(String fileFullPath) {
        File checkFile = new File(fileFullPath);
        if (checkFile.exists() && checkFile.isFile()) {
            return true;
        }

        return false;
    }

    private static boolean checkDirectoryExist(String dirFullPath) {
        File checkDir = new File(dirFullPath);
        if (checkDir.exists() && checkDir.isDirectory()) {
            return true;
        }

        return false;
    }
}
