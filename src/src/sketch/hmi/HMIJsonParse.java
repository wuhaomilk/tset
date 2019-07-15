package sketch.hmi;

import com.google.gson.stream.JsonReader;
import java.util.ArrayList;
import sketch.common.*;

import java.io.FileReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class HMIJsonParse {
    public ArrayList<JsonSketchInfo> screenInfoList = new ArrayList<JsonSketchInfo>();

    private void parseJsonObject(JsonReader jsonReader) throws Exception {
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String no_use_artboardid = jsonReader.nextName();
            JsonSketchInfo oneSketchInfo = new JsonSketchInfo();
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String nowTagName = jsonReader.nextName();
                if (nowTagName.equalsIgnoreCase("8xxx")) {
//                    oneSketchInfo.displayInfo = this.parseScreenInfo(jsonReader);
                }
                else if (nowTagName.equalsIgnoreCase("PartsList")) {
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        String no_use_partid = jsonReader.nextName();
                        oneSketchInfo.partInfoList.add(getJsonPartInfo(jsonReader));
                    }
                    jsonReader.endObject();

                    Collections.sort(oneSketchInfo.partInfoList, new Comparator<JsonPartInfo>(){
                        @Override
                        public int compare(JsonPartInfo b1, JsonPartInfo b2) {
                            String b1_part_id = b1.partsID;
                            String b2_part_id = b2.partsID;
                            String[] b1PartIDList = b1_part_id.split("_");
                            String[] b2PartIDList = b2_part_id.split("_");

                            String regexIncludeWord = ".*[a-zA-z].*";

                            int partIDMinLen = Math.min(b1PartIDList.length, b2PartIDList.length);
                            for (int i=0; i < partIDMinLen; i++) {
                                if (b1PartIDList[i].equalsIgnoreCase(b2PartIDList[i])) {
                                    continue;
                                }

                                if (b1PartIDList[i].matches(regexIncludeWord)
                                        || b2PartIDList[i].matches(regexIncludeWord)) {
                                    return b1PartIDList[i].compareTo(b2PartIDList[i]);
                                }

                                if (b1PartIDList[i].length() == b2PartIDList[i].length()) {
                                    return b1PartIDList[i].compareTo(b2PartIDList[i]);
                                }

                                return b1PartIDList[i].length()-b2PartIDList[i].length();
                            }

                            return b1.partsID.length()-b2.partsID.length();


//                            if (b1.partsID.length() == b2.partsID.length()) {
//                                return b1.partsID.compareTo(b2.partsID);
//                            }
//                            return b1.partsID.length()-b2.partsID.length();
                        }
                    });
                }
                else if (nowTagName.equalsIgnoreCase("DestGradeInfo")) {
//                    oneSketchInfo.destGradeMap = this.getDestGradeInfo(jsonReader);
                }
            }
            jsonReader.endObject();

            this.screenInfoList.add(oneSketchInfo);
        }

        jsonReader.endObject();
    }

    private HashMap<String, String> getDestGradeInfo(JsonReader jsonReader) throws Exception {
        HashMap<String, String> retInfo = new HashMap<String, String>();

        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String regionInfo = jsonReader.nextName();
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String cartype = jsonReader.nextName();
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    String enablestr = jsonReader.nextName();
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        String destGradeKey = regionInfo+"_"+cartype+"_"+enablestr+"_"+jsonReader.nextName();
                        retInfo.put(destGradeKey, jsonReader.nextString());
                    }
                    jsonReader.endObject();
                }
                jsonReader.endObject();
            }

            jsonReader.endObject();

        }

        jsonReader.endObject();


        return retInfo;
    }

    private JsonDisplayInfo parseScreenInfo(JsonReader jsonReader) throws Exception {
        jsonReader.beginObject();

        JsonDisplayInfo retDisplayInfo = new JsonDisplayInfo();

        while (jsonReader.hasNext()) {
            String tagName = jsonReader.nextName();
            if (tagName.equalsIgnoreCase("TrigEvent")) {
                retDisplayInfo.partsName = jsonReader.nextString();
                continue;
            }else{
                continue;
            }
//            if (tagName.equalsIgnoreCase("excelInfo")) {
//                jsonReader.beginObject();
//                while (jsonReader.hasNext()) {
//                    String nowTagName = jsonReader.nextName();
//                    if (nowTagName.equalsIgnoreCase("Screen ID")) {
//                        retScreenInfo.screenID = jsonReader.nextString();
//                    }
//                    else if (nowTagName.equalsIgnoreCase("Basic Screen ID")) {
//                        retScreenInfo.baseScreenID = jsonReader.nextString();
//                    }
//                    else if (nowTagName.equalsIgnoreCase("Screen Name")) {
//                        retScreenInfo.screenName = jsonReader.nextString();
//                    }
//                    else if (nowTagName.equalsIgnoreCase("Note")) {
//                        retScreenInfo.screenNote = jsonReader.nextString();
//                    }
//                }
//                jsonReader.endObject();
//            }
//            else if (tagName.equalsIgnoreCase("sketchInfo")) {
//                retScreenInfo.partImageInfo = getJsonImageInfo(jsonReader);
//            }
        }

        jsonReader.endObject();

        return retDisplayInfo;
    }

    private JsonPartInfo getJsonPartInfo(JsonReader jsonReader) throws Exception {
        JsonPartInfo retPartInfo = new JsonPartInfo();

        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String tagName = jsonReader.nextName();
            if (tagName.equalsIgnoreCase("Parts ID")) {
                retPartInfo.setPartsID(jsonReader.nextString());
            }
            else if (tagName.equalsIgnoreCase("touch symbol")) {
                retPartInfo.setIsTouchSymbol(jsonReader.nextString());
            }
            else if (tagName.equalsIgnoreCase("Display image")) {
                retPartInfo.setDisplayImage(jsonReader.nextString());
            }
            else if (tagName.equalsIgnoreCase("Parts Type")) {
                retPartInfo.setPartsType(jsonReader.nextString());
            }
            else if (tagName.equalsIgnoreCase("Parts Name")) {
                retPartInfo.setPartsName(jsonReader.nextString());
            }
            else if (tagName.equalsIgnoreCase("Display Condition")) {
                retPartInfo.setDisplayCondition(getJsonPartCondition(jsonReader));
            }
            else if (tagName.equalsIgnoreCase("Text or Image information")) {
                retPartInfo.setTextImgInfo(getTextImgInformation(jsonReader));
            }
            else if (tagName.equalsIgnoreCase("SW Information")) {
                retPartInfo.setSWInfo(getSWInformation(jsonReader));
            }
            else if (tagName.equalsIgnoreCase("sketchInfo")) {
                retPartInfo.setPartImageInfo(getJsonImageInfo(jsonReader));
            }
            else if (tagName.equalsIgnoreCase("common symbol")) {
                int symbolFlagInt = jsonReader.nextInt();
                if (symbolFlagInt > 0) {
                    retPartInfo.isGlobalItem = true;
                }
            }
//            else if (tagName.equalsIgnoreCase("subsymbols")) {
//                jsonReader.beginObject();
//                while(jsonReader.hasNext()) {
//                    String subsymbolID = jsonReader.nextName();
//                    retPartInfo.subSymbolList.add(getJsonPartInfo(jsonReader));
//                }
//                jsonReader.endObject();
//                Collections.sort(retPartInfo.subSymbolList, new Comparator<JsonPartInfo>() {
//                    @Override
//                    public int compare(JsonPartInfo b1, JsonPartInfo b2) {
//                        if (b1.partsID.length() == b2.partsID.length()) {
//                            return b1.partsID.compareTo(b2.partsID);
//                        }
//                        return b1.partsID.length()-b2.partsID.length();
//                    }
//                });
//            }
            else {
//                System.out.println("!!!!unknown tagname:::"+tagName);
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();

        return retPartInfo;
    }

    private JsonPartSWInfo getSWInformation(JsonReader jsonReader) throws Exception {
        JsonPartSWInfo retSWInfo = new JsonPartSWInfo();

        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String tagName = jsonReader.nextName();
            if (tagName.equalsIgnoreCase("BEEP")) {
                retSWInfo.setSWInfoBeep(jsonReader.nextString());
            }
            else if (tagName.equalsIgnoreCase("Operation result")) {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    String opeResultTagName = jsonReader.nextName();
                    if (opeResultTagName.equalsIgnoreCase("Screen Transion")) {
                        retSWInfo.setOpeResultScreenTrans(jsonReader.nextString());
                    }
                    else if (opeResultTagName.equalsIgnoreCase("Start Function")) {
                        retSWInfo.setOpeResultStartFunc(jsonReader.nextString());
                    }
                    else if (opeResultTagName.equalsIgnoreCase("Setting Value Change")) {
                        retSWInfo.setOpeResultSettingValue(jsonReader.nextString());
                    }
                    else if (opeResultTagName.equalsIgnoreCase("Other")) {
                        retSWInfo.setOpeResultOther(jsonReader.nextString());
                    }
                }
                jsonReader.endObject();
            }
            else if (tagName.equalsIgnoreCase("SW operation Pattern")) {
                retSWInfo.setSWOpePattern(jsonReader.nextString());
            }
            else if (tagName.equalsIgnoreCase("Tonedown condition in motion")) {
                retSWInfo.setTonedownConInMotion(getJsonPartCondition(jsonReader));
            }
            else if (tagName.equalsIgnoreCase("Tonedown condition except in motion")) {
                retSWInfo.setTonedownConExceptInMotion(getJsonPartCondition(jsonReader));
            }
            else if(tagName.equalsIgnoreCase("Selected condition")) {
                retSWInfo.setSelectedCon(getJsonPartCondition(jsonReader));
            }
            else {
//                System.out.println("!!SW Information skip key" + tagName);
                jsonReader.skipValue();
            }

        }

        jsonReader.endObject();

        return retSWInfo;
    }

    private JsonTxtImgInfo getTextImgInformation(JsonReader jsonReader) throws Exception {
        JsonTxtImgInfo retTxtImgInfo = new JsonTxtImgInfo();

        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            String tagName = jsonReader.nextName();
            if (tagName.equalsIgnoreCase("Japanese")) {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    String japTagName = jsonReader.nextName();
                    if (japTagName.equalsIgnoreCase("Fixed words")) {
                        retTxtImgInfo.setJapFixedWords(jsonReader.nextString());
                    }
                    else if (japTagName.equalsIgnoreCase("Japanese")) {
                        retTxtImgInfo.setJapInfo(jsonReader.nextString());
                    }
                    else if (japTagName.equalsIgnoreCase("VUI recognition word")) {
                        retTxtImgInfo.setJapVUIRecogWord(jsonReader.nextString());
                    }
                }
                jsonReader.endObject();
            }
            else if (tagName.equalsIgnoreCase("U.S.English")) {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    String usTagName = jsonReader.nextName();
                    if (usTagName.equalsIgnoreCase("Fixed words")) {
                        String USFixedWords = jsonReader.nextString();
                        retTxtImgInfo.setUSEngFixedWords(USFixedWords);
                    }
                    else if (usTagName.equalsIgnoreCase("U.S.English")) {
                        retTxtImgInfo.setUSEngInfo(jsonReader.nextString());
                    }
                    else if (usTagName.equalsIgnoreCase("VUI recognition word")) {
                        retTxtImgInfo.setUSEngVUIRecogWord(jsonReader.nextString());
                    }
                }
                jsonReader.endObject();
            }
            else if (tagName.equalsIgnoreCase("U.K.English")) {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    String ukTagName = jsonReader.nextName();
                    if (ukTagName.equalsIgnoreCase("Fixed words")) {
                        String UKFixedWords = jsonReader.nextString();
                        retTxtImgInfo.setUKEngFixedWords(UKFixedWords);
                    }
                    else if (ukTagName.equalsIgnoreCase("U.K.English")) {
                        retTxtImgInfo.setUKEngInfo(jsonReader.nextString());
                    }
                    else if (ukTagName.equalsIgnoreCase("VUI recognition word")) {
                        retTxtImgInfo.setUKEngVUIRecogWord(jsonReader.nextString());
                    }
                }
                jsonReader.endObject();
            }
            else if (tagName.equalsIgnoreCase("Fixed Image")) {
                retTxtImgInfo.setFixedImage(jsonReader.nextString());
            }
            else if(tagName.equalsIgnoreCase("Delete condition in motion")) {
                retTxtImgInfo.setDelConditionInMotion(this.getJsonPartCondition(jsonReader));
            }
            else if (tagName.equalsIgnoreCase("Validation")) {
                retTxtImgInfo.setValidationInfo(jsonReader.nextString());
            }
            else if (tagName.equalsIgnoreCase("Outside Input")) {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    String outsideTagName = jsonReader.nextName();
                    if (outsideTagName.equalsIgnoreCase("Display contents")) {
                        retTxtImgInfo.setOutInputDispContent(jsonReader.nextString());
                    }
                    else if (outsideTagName.equalsIgnoreCase("Format")) {
                        retTxtImgInfo.setOutInputFormat(jsonReader.nextString());
                    }
                    else if (outsideTagName.equalsIgnoreCase("Range")) {
                        retTxtImgInfo.setOutInputRange(jsonReader.nextString());
                    }
                }
                jsonReader.endObject();
            }
            else {
                jsonReader.skipValue();
//                System.out.println("!!Text or Image skip key:"+tagName);
            }
        }

        jsonReader.endObject();

        return retTxtImgInfo;
    }

    private JsonPartCondition getJsonPartCondition(JsonReader jsonReader) throws Exception {
        JsonPartCondition retCondition = new JsonPartCondition();

        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String tagName = jsonReader.nextName();
            if (tagName.equalsIgnoreCase("Formula")) {
                retCondition.setFormulaInfo(jsonReader.nextString());
            }
            else if (tagName.equalsIgnoreCase("Condition")) {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    String[] conStrArray = new String[]{"",""};
                    conStrArray[0] = jsonReader.nextName();
                    conStrArray[1] = jsonReader.nextString();
                    retCondition.getConditionList().add(conStrArray);
                }
                jsonReader.endObject();
            }
            else if (tagName.equalsIgnoreCase("Same Condition")) {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    String sameConTagName = jsonReader.nextName();
                    if (sameConTagName.equalsIgnoreCase("Parts ID")) {
                        retCondition.setSameConPartID(jsonReader.nextString());
                    }
                    else if (sameConTagName.equalsIgnoreCase("Screen ID")) {
                        retCondition.setSameConScreenID(jsonReader.nextString());
                    }
                }
                jsonReader.endObject();
            }
            else if (tagName.equalsIgnoreCase("else")) {
                retCondition.setElseInfo(jsonReader.nextString());
            }
            else {
                jsonReader.skipValue();
//                System.out.println("!!Pass key in Parse Part Condition:"+tagName);
            }
        }

        jsonReader.endObject();

        return retCondition;
    }

    private JsonImageInfo getJsonImageInfo(JsonReader jsonReader) throws Exception {
        JsonImageInfo retImageInfo = new JsonImageInfo();
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String tagName = jsonReader.nextName();
            if (tagName.equalsIgnoreCase("Rect")) {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    String rectTagName = jsonReader.nextName();
                    if (rectTagName.equalsIgnoreCase("x")) {
                        retImageInfo.x = Integer.parseInt(jsonReader.nextString());
                    }
                    else if (rectTagName.equalsIgnoreCase("y")) {
                        retImageInfo.y = Integer.parseInt(jsonReader.nextString());
                    }
                    else if (rectTagName.equalsIgnoreCase("w")) {
                        retImageInfo.width = Integer.parseInt(jsonReader.nextString());
                    }
                    else if (rectTagName.equalsIgnoreCase("h")) {
                        retImageInfo.height = Integer.parseInt(jsonReader.nextString());
                    }
                }

                jsonReader.endObject();
            }
            else if (tagName.equalsIgnoreCase("imgPath")) {
                retImageInfo.imgPath = jsonReader.nextString();
            }
            else if (tagName.equalsIgnoreCase("uuid")) {
                retImageInfo.objUUID = jsonReader.nextString();
            }
            else if (tagName.equalsIgnoreCase("realFullID")) {
                retImageInfo.realFullID = jsonReader.nextString();
            }
            else {
//                System.out.println("!!Parse Json Image Skip Key:" + tagName);
                jsonReader.skipValue();
            }
        }

        jsonReader.endObject();

        return retImageInfo;
    }

    public ArrayList<JsonSketchInfo> parseFile(String jsonFileName) throws Exception {
        JsonReader jsonReader = new JsonReader(new FileReader(jsonFileName));
        parseJsonObject(jsonReader);

        return this.screenInfoList;
    }
}
