package sketch.hmi;

import com.google.gson.stream.JsonReader;
import sketch.common.*;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class HMIJsonDataParse_new {
    public ArrayList<JsonSketchInfo_new> screenInfoList = new ArrayList<>();

    public ArrayList<JsonSketchInfo_new> parseFile(String jsonFileName) throws Exception {
        JsonReader jsonReader = new JsonReader(new FileReader(jsonFileName));
        parseJsonObject(jsonReader);
        return this.screenInfoList;
    }

    private void parseJsonObject(JsonReader jsonReader) throws Exception {
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String sketchInfoId = jsonReader.nextName();
            JsonSketchInfo_new oneSketchInfo = new JsonSketchInfo_new();
            oneSketchInfo.setSketchInfoId(sketchInfoId);
            oneSketchInfo.screenInfo = new JsonScreenImageInfo();
            ArrayList<JsonPartInfo_new> jsonPartInfoList = new ArrayList<>();
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String uuidName = jsonReader.nextName();
                JsonDestGradeInfo destGradeInfo = new JsonDestGradeInfo();
                JsonDisplayRefInfo displayRefInfo = new JsonDisplayRefInfo();        // 1
                JsonImageInfo displayRectInfo = new JsonImageInfo();
                JsonActiveRefInfo activeRefInfo = new JsonActiveRefInfo();         // 2
                JsonActionRefInfo actionRefInfo = new JsonActionRefInfo();         // 3
                JsonHKRefInfo hkRefInfo = new JsonHKRefInfo();                // 4
                JsonInitRefInfo initRefInfo = new JsonInitRefInfo();           // 5
                JsonStatusChangeRefInfo statusChangeRefInfo = new JsonStatusChangeRefInfo();  // 6
                JsonTransitionRefInfo transitionRefInfo = new JsonTransitionRefInfo();    // 7
                JsonTrigRefInfo trigRefInfo = new JsonTrigRefInfo();            // 8
                if (uuidName.equalsIgnoreCase("ScreenName")) {
                    oneSketchInfo.setScreenName(jsonReader.nextString());
                } else if (uuidName.equalsIgnoreCase("Outline")) {
                    oneSketchInfo.setOutLine(jsonReader.nextString());
                } else if (uuidName.equalsIgnoreCase("DisplayRefInfo")) {
                    oneSketchInfo.setDisplayRefInfoStr(jsonReader.nextString());
                } else if (uuidName.equalsIgnoreCase("HKRefInfo")) {
                    oneSketchInfo.setHKRefInfoStr(jsonReader.nextString());
                } else if (uuidName.equalsIgnoreCase("InitRefInfo")) {
                    oneSketchInfo.setInitRefInfoStr(jsonReader.nextString());
                } else if (uuidName.equalsIgnoreCase("TrigRefInfo")) {
                    oneSketchInfo.setTrigRefInfoStr(jsonReader.nextString());
                } else if (uuidName.equalsIgnoreCase("ActionRefInfo")) {
                    oneSketchInfo.setActionRefInfoStr(jsonReader.nextString());
                } else if (uuidName.equalsIgnoreCase("StatusChangeRefInfo")) {
                    oneSketchInfo.setStatusChangeRefInfoStr(jsonReader.nextString());
                } else if (uuidName.equalsIgnoreCase("TransitionRefInfo")) {
                    oneSketchInfo.setTransitionRefInfoStr(jsonReader.nextString());
                } else if (uuidName.equalsIgnoreCase("ActiveRefInfo")) {
                    oneSketchInfo.setActiveRefInfoStr(jsonReader.nextString());
                } else if (uuidName.equalsIgnoreCase("ScreenDispPic")) {
                    oneSketchInfo.screenInfo.imagePath = jsonReader.nextString();
                } else if (uuidName.equalsIgnoreCase("ScreenDispRect")) {
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        String paramName = jsonReader.nextName();
                        if (paramName.equalsIgnoreCase("x")) {               // for displayRefInfo
                            oneSketchInfo.screenInfo.x = jsonReader.nextString();
                        } else if (paramName.equalsIgnoreCase("y")) {
                            oneSketchInfo.screenInfo.y = jsonReader.nextString();
                        } else if (paramName.equalsIgnoreCase("width")) {
                            oneSketchInfo.screenInfo.width = jsonReader.nextString();
                        } else if (paramName.equalsIgnoreCase("height")) {
                            oneSketchInfo.screenInfo.height = jsonReader.nextString();
                        } else {
                            jsonReader.skipValue();
                        }
                    }
                    jsonReader.endObject();
                } else if (uuidName.equalsIgnoreCase("ScreenUUID")) {
                    oneSketchInfo.setScreenUUID(jsonReader.nextString());
                } else if (uuidName.equalsIgnoreCase("ScreenID")) {
                    oneSketchInfo.setScreenID(jsonReader.nextString());
                } else if (uuidName.equalsIgnoreCase("TrigUUIDArray")) {
                    ArrayList<String> trigUUIDArray = new ArrayList<>();
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        trigUUIDArray.add(jsonReader.nextString());
                    }
                    jsonReader.endArray();
                    oneSketchInfo.setTrigUUIDArray(trigUUIDArray);
                } else if (uuidName.equalsIgnoreCase("StatusChangeUUIDArray")) {
                    ArrayList<String> statusChangeUUIDArray = new ArrayList<>();
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        statusChangeUUIDArray.add(jsonReader.nextString());
                    }
                    jsonReader.endArray();
                    oneSketchInfo.setStatusChangeUUIDArray(statusChangeUUIDArray);
                } else if (uuidName.equalsIgnoreCase("InitUUIDArray")) {
                    ArrayList<String> initUUIDArray = new ArrayList<>();
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        initUUIDArray.add(jsonReader.nextString());
                    }
                    jsonReader.endArray();
                    oneSketchInfo.setInitUUIDArray(initUUIDArray);
                } else if (uuidName.equalsIgnoreCase("TransitionUUIDArray")) {
                    ArrayList<String> transitionUUIDArray = new ArrayList<>();
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        transitionUUIDArray.add(jsonReader.nextString());
                    }
                    jsonReader.endArray();
                    oneSketchInfo.setTransitionUUIDArray(transitionUUIDArray);
                } else if (uuidName.equalsIgnoreCase("AvailableModelUUIDArray")) {
                    ArrayList<String> availableModelUUIDArray = new ArrayList<>();
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        availableModelUUIDArray.add(jsonReader.nextString());
                    }
                    jsonReader.endArray();
                    oneSketchInfo.setAvailableModelUUIDArray(availableModelUUIDArray);
                } else if (uuidName.equalsIgnoreCase("HKUUIDArray")) {
                    ArrayList<String> hkUUIDArray = new ArrayList<>();
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        hkUUIDArray.add(jsonReader.nextString());
                    }
                    jsonReader.endArray();
                    oneSketchInfo.setHKUUIDArray(hkUUIDArray);
                } else {
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        String partName = jsonReader.nextName();
                        if (partName.equalsIgnoreCase("Display")) {               // for displayRefInfo
                            jsonReader.beginObject();
                            while (jsonReader.hasNext()) {
                                String paramName = jsonReader.nextName();
                                if (paramName.equalsIgnoreCase("DisplayChapter")) {               // for displayRefInfo
                                    displayRefInfo.setDisplayChapter(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("DisplaySubChapter")) {
                                    displayRefInfo.setDisplaySubChapter(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("HasDispInfo")) {
                                    displayRefInfo.setHasDispInfo(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("DisplayStates")) {
                                    ArrayList<String> displayStates = new ArrayList<>();
                                    jsonReader.beginArray();
                                    while (jsonReader.hasNext()) {
                                        displayStates.add(jsonReader.nextString());
                                    }
                                    jsonReader.endArray();
                                    displayRefInfo.setDisplayStates(displayStates);
                                } else if (paramName.equalsIgnoreCase("PartsNumber")) {
                                    displayRefInfo.setPartsNumber(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("DisplayStateNo")) {
                                    displayRefInfo.setDisplayStateNo(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("PartsName")) {
                                    displayRefInfo.setPartsName(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("DisplayContent")) {
                                    displayRefInfo.setDisplayContent(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("DisplayFormula")) {
                                    displayRefInfo.setDisplayFormula(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("DisplayCondition")) {
                                    jsonReader.beginObject();
                                    HashMap<String, String> displayCondition = displayRefInfo.getDisplayCondition();
                                    while (jsonReader.hasNext()) {
                                        displayCondition.put(jsonReader.nextName(), jsonReader.nextString());
                                    }
                                    jsonReader.endObject();
                                    displayRefInfo.setDisplayCondition(displayCondition);
                                } else if (paramName.equalsIgnoreCase("DisplayAction")) {
                                    displayRefInfo.setDispalyAction(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("PartsID")) {
                                    displayRefInfo.setPartsID(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("DataRange")) {
                                    displayRefInfo.setDataRange(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("DisplayRemark")) {
                                    displayRefInfo.setDisplayRemark(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("DisplayUUID")) {
                                    displayRefInfo.setDisplayUUID(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("DisplayPartsType")) {
                                    displayRefInfo.setDisplayPartsType(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("DisplayProperty")) {
                                    displayRefInfo.setDisplayProperty(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("DisplayActionModel")) {
                                    displayRefInfo.setDisplayActionModel(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("DisplayConditionModel")) {
                                    jsonReader.beginObject();
                                    HashMap<String, String> displayConditionModel = displayRefInfo.getDisplayConditionModel();
                                    while (jsonReader.hasNext()) {
                                        displayConditionModel.put(jsonReader.nextName(), jsonReader.nextString());
                                    }
                                    jsonReader.endObject();
                                    displayRefInfo.setDisplayConditionModel(displayConditionModel);
                                } else if (paramName.equalsIgnoreCase("StringID")) {
                                    displayRefInfo.setStringID(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("Visible")) {
                                    displayRefInfo.setVisible(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("DisplayPropertyType")) {
                                    displayRefInfo.setDisplayPropertyType(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("DisplayDefaultValue")) {
                                    displayRefInfo.setDisplayDefaultValue(jsonReader.nextString());
                                } else {
                                    jsonReader.skipValue();
                                }
                            }
                            jsonReader.endObject();
                        }
                        else if (partName.equalsIgnoreCase("DisplayRect")) {
                            jsonReader.beginObject();
                            while (jsonReader.hasNext()) {
                                String paramName = jsonReader.nextName();
                                if (paramName.equalsIgnoreCase("x")) {               // for displayRefInfo
                                    displayRectInfo.x =  Integer.parseInt(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("y")) {
                                    displayRectInfo.y = Integer.parseInt(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("width")) {
                                    displayRectInfo.width = Integer.parseInt(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("height")) {
                                    displayRectInfo.height = Integer.parseInt(jsonReader.nextString());
                                } else {
                                    jsonReader.skipValue();
                                }
                            }
                            jsonReader.endObject();
                        }
                        else if (partName.equalsIgnoreCase("Active")) {
                            jsonReader.beginObject();
                            while (jsonReader.hasNext()) {
                                String paramName = jsonReader.nextName();
                                if (paramName.equalsIgnoreCase("ActiveChapter")) {
                                    activeRefInfo.setActiveChapter(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("ActiveSubChapter")) {
                                    activeRefInfo.setActiveSubChapter(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("ActiveNo")) {
                                    activeRefInfo.setActiveNo(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("ActiveStateNo")) {
                                    activeRefInfo.setActiveStateNo(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("ActiveStatus")) {
                                    ArrayList<String> activeStatus = new ArrayList<>();
                                    jsonReader.beginArray();
                                    while (jsonReader.hasNext()) {
                                        activeStatus.add(jsonReader.nextString());
                                    }
                                    jsonReader.beginArray();
                                    activeRefInfo.setActiveStates(activeStatus);
                                } else if (paramName.equalsIgnoreCase("HasActiveInfo")) {
                                    activeRefInfo.setHasActiveInfo(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("ActiveBtnName")) {     // for activeRefInfo
                                    activeRefInfo.setActiveBtnName(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("ActiveFormula")) {
                                    activeRefInfo.setActiveFormula(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("ActiveCondition")) {
                                    jsonReader.beginObject();
                                    HashMap<String, String> activeCondition = activeRefInfo.getActiveCondition();
                                    while (jsonReader.hasNext()) {
                                        activeCondition.put(jsonReader.nextName(), jsonReader.nextString());
                                    }
                                    jsonReader.endObject();
                                    activeRefInfo.setActiveCondition(activeCondition);
                                } else if (paramName.equalsIgnoreCase("ActiveAction")) {
                                    activeRefInfo.setActiveAction(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("ActiveDuringDriving")) {
                                    activeRefInfo.setActiveDuringDriving(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("ActiveRemark")) {
                                    activeRefInfo.setActiveRemark(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("ActiveUUID")) {
                                    activeRefInfo.setActiveUUID(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("ActivePartsType")) {
                                    activeRefInfo.setActivePartsType(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("ActiveProperty")) {
                                    activeRefInfo.setActiveProperty(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("ActiveActionModel")) {
                                    activeRefInfo.setActiveActionModel(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("activeConditionModel")) {
                                    jsonReader.beginObject();
                                    HashMap<String, String> activeConditionModel = activeRefInfo.getActiveConditionModel();
                                    while (jsonReader.hasNext()) {
                                        activeConditionModel.put(jsonReader.nextName(), jsonReader.nextString());
                                    }
                                    jsonReader.endObject();
                                    activeRefInfo.setActiveConditionModel(activeConditionModel);
                                } else if (paramName.equalsIgnoreCase("Active")) {
                                    activeRefInfo.setActive(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("ActivePropertyType")) {
                                    activeRefInfo.setActivePropertyType(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("ActiveDefaultValue")) {
                                    activeRefInfo.setActiveDefaultValue(jsonReader.nextString());
                                } else {
                                    jsonReader.skipValue();
                                }
                            }
                            jsonReader.endObject();
                        } else if (partName.equalsIgnoreCase("Action")) {
                            jsonReader.beginObject();
                            while (jsonReader.hasNext()) {
                                String paramName = jsonReader.nextName();
                                if (paramName.equalsIgnoreCase("ActionChapter")) {
                                    actionRefInfo.setActionChapter(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("ActionSubChapter")) {
                                    actionRefInfo.setActionSubChapter(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("HasActionInfo")) {
                                    actionRefInfo.setHasActionInfo(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("ActionNo")) {
                                    actionRefInfo.setActionNo(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("ActionStateNo")) {
                                    actionRefInfo.setActionStateNo(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("ActionBtnName")) {     // for actionRefInfo
                                    actionRefInfo.setActionBtnName(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("ActionOpeType")) {
                                    actionRefInfo.setActionOpeType(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("ActionFormula")) {
                                    actionRefInfo.setActionFormula(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("ActionCondition")) {
                                    jsonReader.beginObject();
                                    HashMap<String, String> actionCondition = actionRefInfo.getActionCondition();
                                    while (jsonReader.hasNext()) {
                                        actionCondition.put(jsonReader.nextName(), jsonReader.nextString());
                                    }
                                    jsonReader.endObject();
                                    actionRefInfo.setActionCondition(actionCondition);
                                } else if (paramName.equalsIgnoreCase("ActionAction")) {
                                    actionRefInfo.setActionAction(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("ActionTrans")) {
                                    actionRefInfo.setActionTrans(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("ActionSound")) {
                                    actionRefInfo.setActionSound(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("ActionRemark")) {
                                    actionRefInfo.setActionRemark(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("ActionUUID")) {
                                    actionRefInfo.setActionUUID(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("ActionPartsType")) {
                                    actionRefInfo.setActionPartsType(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("ActionEvent")) {
                                    actionRefInfo.setActionEvent(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("ActionActionModel")) {
                                    actionRefInfo.setActionActionModel(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("ActionConditionModel")) {
                                    jsonReader.beginObject();
                                    HashMap<String, String> actionConditionModel = actionRefInfo.getActionConditionModel();
                                    while (jsonReader.hasNext()) {
                                        actionConditionModel.put(jsonReader.nextName(), jsonReader.nextString());
                                    }
                                    jsonReader.endObject();
                                    actionRefInfo.setActionConditionModel(actionConditionModel);
                                } else if (paramName.equalsIgnoreCase("ActionObserver")) {
                                    actionRefInfo.setActionObserver(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("ActionReply")) {
                                    actionRefInfo.setActionReply(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("ActionTransType")) {
                                    actionRefInfo.setActionTransType(jsonReader.nextString());
                                } else if (paramName.equalsIgnoreCase("ActionTransID")) {
                                    actionRefInfo.setActionTransID(jsonReader.nextString());
                                } else {
                                    jsonReader.skipValue();
                                }
                            }
                            jsonReader.endObject();
                        } else if (partName.equalsIgnoreCase("HKChapter")) {     // 4
                            hkRefInfo.setHKChapter(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("HKSubChapter")) {
                            hkRefInfo.setHKSubChapter(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("HKDevName")) {
                            hkRefInfo.setHKDevName(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("HKDevType")) {
                            hkRefInfo.setHKDevType(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("HKNo")) {
                            hkRefInfo.setHKNo(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("HKStateNo")) {
                            hkRefInfo.setHKStateNo(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("HKName")) {
                            hkRefInfo.setHKName(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("HKOpeType")) {
                            hkRefInfo.setHKOpeType(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("HKFormula")) {
                            hkRefInfo.setHKFormula(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("HKCondition")) {
                            jsonReader.beginObject();
                            HashMap<String, String> hkCondition = hkRefInfo.getHKCondition();
                            while (jsonReader.hasNext()) {
                                hkCondition.put(jsonReader.nextName(), jsonReader.nextString());
                            }
                            jsonReader.endObject();
                            hkRefInfo.setHKCondition(hkCondition);
                        } else if (partName.equalsIgnoreCase("HKAction")) {
                            hkRefInfo.setHKAction(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("HKTrans")) {
                            hkRefInfo.setHKTrans(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("HKSound")) {
                            hkRefInfo.setHKSound(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("HKDuringDriving")) {
                            hkRefInfo.setHKDuringDriving(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("HKRemark")){
                            hkRefInfo.setHKRemark(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("HKUUID")) {
                            hkRefInfo.setHKUUID(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("HKEvent")) {
                            hkRefInfo.setHKEvent(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("HKConditionModel")) {
                            jsonReader.beginObject();
                            HashMap<String, String> hkConditionModel = hkRefInfo.getHKConditionModel();
                            while (jsonReader.hasNext()) {
                                hkConditionModel.put(jsonReader.nextName(), jsonReader.nextString());
                            }
                            jsonReader.endObject();
                            hkRefInfo.setHKConditionModel(hkConditionModel);
                        } else if (partName.equalsIgnoreCase("HKActionModel")) {
                            hkRefInfo.setHKActionModel(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("HKObserver")) {
                            hkRefInfo.setHKObserver(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("HKReply")) {
                            hkRefInfo.setHKReply(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("HKTransType")) {
                            hkRefInfo.setHKTransType(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("HKTransID")) {
                            hkRefInfo.setHKTransID(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("InitChapter")) {   // 5
                            initRefInfo.setInitChapter(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("InitSubChapter")) {
                            initRefInfo.setInitSubChapter(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("InitNo")) {
                            initRefInfo.setInitNo(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("InitStateNo")) {
                            initRefInfo.setInitStateNo(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("InitStatus")) {
                            initRefInfo.setInitStatus(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("InitFormula")) {
                            initRefInfo.setInitFormula(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("InitCondition")) {
                            jsonReader.beginObject();
                            HashMap<String, String> initCondition = initRefInfo.getInitCondition();
                            while (jsonReader.hasNext()) {
                                initCondition.put(jsonReader.nextName(), jsonReader.nextString());
                            }
                            jsonReader.endObject();
                            initRefInfo.setInitCondition(initCondition);
                        } else if (partName.equalsIgnoreCase("InitAction")) {
                            initRefInfo.setInitAction(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("InitTrans")) {
                            initRefInfo.setInitTrans(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("InitRemark")) {
                            initRefInfo.setInitRemark(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("InitUUID")) {
                            initRefInfo.setInitUUID(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("InitEvent")) {
                            initRefInfo.setInitEvent(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("InitConditionModel")) {
                            jsonReader.beginObject();
                            HashMap<String, String> initConditionModel = initRefInfo.getInitConditionModel();
                            while (jsonReader.hasNext()) {
                                initConditionModel.put(jsonReader.nextName(), jsonReader.nextString());
                            }
                            jsonReader.endObject();
                            initRefInfo.setInitConditionModel(initConditionModel);
                        } else if (partName.equalsIgnoreCase("InitActionModel")) {
                            initRefInfo.setInitActionModel(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("InitObserver")) {
                            initRefInfo.setInitObserver(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("InitReply")) {
                            initRefInfo.setInitReplay(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("InitTransType")) {
                            initRefInfo.setInitTransType(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("InitTransID")) {
                            initRefInfo.setInitTransID(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("StatusChangeChapter")) {     // 6
                            statusChangeRefInfo.setStatusChangeChapter(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("StatusChangeSubChapter")) {
                            statusChangeRefInfo.setStatusChangeSubChapter(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("StatusChangeNo")) {
                            statusChangeRefInfo.setStatusChangeNo(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("StatusChangeStateNo")) {
                            statusChangeRefInfo.setStatusChangeStateNo(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("StatusChangeName")) {
                            statusChangeRefInfo.setStatusChangeName(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("StatusChangeFFormula")) {
                            statusChangeRefInfo.setStatusChangeFFormula(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("StatusChangeFCondition")) {
                            jsonReader.beginObject();
                            HashMap<String, String> condition = statusChangeRefInfo.getStatusChangeFCondition();
                            while (jsonReader.hasNext()) {
                                condition.put(jsonReader.nextName(), jsonReader.nextString());
                            }
                            jsonReader.endObject();
                            statusChangeRefInfo.setStatusChangeFCondition(condition);

                        } else if (partName.equalsIgnoreCase("StatusChangeFAction")) {
                            statusChangeRefInfo.setStatusChangeFAction(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("StatusChangeFTrans")) {
                            statusChangeRefInfo.setStatusChangeFTrans(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("StatusChangeBFormula")) {
                            statusChangeRefInfo.setStatusChangeBFormula(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("StatusChangeBCondition")) {
                            jsonReader.beginObject();
                            HashMap<String, String> condition = statusChangeRefInfo.getStatusChangeBCondition();
                            while (jsonReader.hasNext()) {
                                condition.put(jsonReader.nextName(), jsonReader.nextString());
                            }
                            jsonReader.endObject();
                            statusChangeRefInfo.setStatusChangeBCondition(condition);
                        }
                        else if (partName.equalsIgnoreCase("StatusChangeBAction")) {
                            statusChangeRefInfo.setStatusChangeBAction(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("StatusChangeBTrans")) {
                            statusChangeRefInfo.setStatusChangeBTrans(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("StatusChangeIFormula")) {
                            statusChangeRefInfo.setStatusChangeIFormula(jsonReader.nextString());
                        }  else if (partName.equalsIgnoreCase("StatusChangeICondition")) {
                            jsonReader.beginObject();
                            HashMap<String, String> condition = statusChangeRefInfo.getStatusChangeICondition();
                            while (jsonReader.hasNext()) {
                                condition.put(jsonReader.nextName(), jsonReader.nextString());
                            }
                            jsonReader.endObject();
                            statusChangeRefInfo.setStatusChangeICondition(condition);
                        }
                        else if (partName.equalsIgnoreCase("StatusChangeIAction")) {
                            statusChangeRefInfo.setStatusChangeIAction(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("StatusChangeITrans")){
                            statusChangeRefInfo.setStatusChangeITrans(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("StatusChangeUUID")) {
                            statusChangeRefInfo.setStatusChangeUUID(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("StatusChangeEvent")) {
                            statusChangeRefInfo.setStatusChangeEvent(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("StatusChangeFConditionModel")) {
                            jsonReader.beginObject();
                            HashMap<String, String> conditionModel = statusChangeRefInfo.getStatusChangeFConditionModel();
                            while (jsonReader.hasNext()) {
                                conditionModel.put(jsonReader.nextName(), jsonReader.nextString());
                            }
                            jsonReader.endObject();
                            statusChangeRefInfo.setStatusChangeFConditionModel(conditionModel);
                        } else if (partName.equalsIgnoreCase("StatusChangeFActionModel")) {
                            statusChangeRefInfo.setStatusChangeFActionModel(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("StatusChangeFObserver")) {
                            statusChangeRefInfo.setStatusChangeFObserver(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("StatusChangeFReply")) {
                            statusChangeRefInfo.setStatusChangeFReply(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("StatusChangeBConditionModel")) {
                            jsonReader.beginObject();
                            HashMap<String, String> conditionModel = statusChangeRefInfo.getStatusChangeBConditionModel();
                            while (jsonReader.hasNext()) {
                                conditionModel.put(jsonReader.nextName(), jsonReader.nextString());
                            }
                            jsonReader.endObject();
                            statusChangeRefInfo.setStatusChangeBConditionModel(conditionModel);
                        } else if (partName.equalsIgnoreCase("StatusChangeBActionModel")) {
                            statusChangeRefInfo.setStatusChangeBActionModel(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("StatusChangeBObserver")) {
                            statusChangeRefInfo.setStatusChangeBObserver(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("StatusChangeBReply")) {
                            statusChangeRefInfo.setStatusChangeBReply(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("StatusChangeIConditionModel")) {
                            jsonReader.beginObject();
                            HashMap<String, String> conditionModel = statusChangeRefInfo.getStatusChangeIConditionModel();
                            while (jsonReader.hasNext()) {
                                conditionModel.put(jsonReader.nextName(), jsonReader.nextString());
                            }
                            jsonReader.endObject();
                            statusChangeRefInfo.setStatusChangeIConditionModel(conditionModel);
                        } else if (partName.equalsIgnoreCase("StatusChangeIActionModel")) {
                            statusChangeRefInfo.setStatusChangeIActionModel(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("StatusChangeIObserver")) {
                            statusChangeRefInfo.setStatusChangeIObserver(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("StatusChangeIReply")) {
                            statusChangeRefInfo.setStatusChangeIReply(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("TransitionChapter")) {     // 7
                            transitionRefInfo.setTransitionChapter(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("TransitionSubChapter")) {
                            transitionRefInfo.setTransitionSubChapter(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("TransitionNo")) {
                            transitionRefInfo.setTransitionNo(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("TransitionStateNo")) {
                            transitionRefInfo.setTransitionStateNo(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("TransitionName")) {
                            transitionRefInfo.setTransitionName(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("TransitionFFormula")) {
                            transitionRefInfo.setTransitionFFormula(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("TransitionFCondition")) {
                            jsonReader.beginObject();
                            HashMap<String, String> condition = transitionRefInfo.getTransitionFCondition();
                            while (jsonReader.hasNext()) {
                                condition.put(jsonReader.nextName(), jsonReader.nextString());
                            }
                            jsonReader.endObject();
                            transitionRefInfo.setTransitionFCondition(condition);
                        } else if (partName.equalsIgnoreCase("TransitionFAction")) {
                            transitionRefInfo.setTransitionFAction(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("TransitionFTrans")) {
                            transitionRefInfo.setTransitionFTrans(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("TransitionBFormula")) {
                            transitionRefInfo.setTransitionBFormula(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("TransitionBCondition")) {
                            jsonReader.beginObject();
                            HashMap<String, String> condition = transitionRefInfo.getTransitionBCondition();
                            while (jsonReader.hasNext()) {
                                condition.put(jsonReader.nextName(), jsonReader.nextString());
                            }
                            jsonReader.endObject();
                            transitionRefInfo.setTransitionBCondition(condition);
                        }
                        else if (partName.equalsIgnoreCase("TransitionBAction")) {
                            transitionRefInfo.setTransitionBAction(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("TransitionBTrans")) {
                            transitionRefInfo.setTransitionBTrans(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("TransitionIFormula")) {
                            transitionRefInfo.setTransitionIFormula(jsonReader.nextString());
                        }  else if (partName.equalsIgnoreCase("TransitionICondition")) {
                            jsonReader.beginObject();
                            HashMap<String, String> condition = transitionRefInfo.getTransitionICondition();
                            while (jsonReader.hasNext()) {
                                condition.put(jsonReader.nextName(), jsonReader.nextString());
                            }
                            jsonReader.endObject();
                            transitionRefInfo.setTransitionICondition(condition);
                        }
                        else if (partName.equalsIgnoreCase("TransitionIAction")) {
                            transitionRefInfo.setTransitionIAction(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("TransitionITrans")){
                            transitionRefInfo.setTransitionITrans(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("TransitionUUID")) {
                            transitionRefInfo.setTransitionUUID(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("TransitionEvent")) {
                            transitionRefInfo.setTransitionEvent(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("TransitionFConditionModel")) {
                            jsonReader.beginObject();
                            HashMap<String, String> conditionModel = transitionRefInfo.getTransitionFConditionModel();
                            while (jsonReader.hasNext()) {
                                conditionModel.put(jsonReader.nextName(), jsonReader.nextString());
                            }
                            jsonReader.endObject();
                            transitionRefInfo.setTransitionFConditionModel(conditionModel);
                        } else if (partName.equalsIgnoreCase("TransitionFActionModel")) {
                            transitionRefInfo.setTransitionFActionModel(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("TransitionFObserver")) {
                            transitionRefInfo.setTransitionFObserver(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("TransitionFReply")) {
                            transitionRefInfo.setTransitionFReply(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("TransitionBConditionModel")) {
                            jsonReader.beginObject();
                            HashMap<String, String> conditionModel = transitionRefInfo.getTransitionBConditionModel();
                            while (jsonReader.hasNext()) {
                                conditionModel.put(jsonReader.nextName(), jsonReader.nextString());
                            }
                            jsonReader.endObject();
                            transitionRefInfo.setTransitionBConditionModel(conditionModel);
                        } else if (partName.equalsIgnoreCase("TransitionBActionModel")) {
                            transitionRefInfo.setTransitionBActionModel(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("TransitionBObserver")) {
                            transitionRefInfo.setTransitionBObserver(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("TransitionBReply")) {
                            transitionRefInfo.setTransitionBReply(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("TransitionIConditionModel")) {
                            jsonReader.beginObject();
                            HashMap<String, String> conditionModel = transitionRefInfo.getTransitionIConditionModel();
                            while (jsonReader.hasNext()) {
                                conditionModel.put(jsonReader.nextName(), jsonReader.nextString());
                            }
                            jsonReader.endObject();
                            transitionRefInfo.setTransitionIConditionModel(conditionModel);
                        } else if (partName.equalsIgnoreCase("TransitionIActionModel")) {
                            transitionRefInfo.setTransitionIActionModel(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("TransitionIObserver")) {
                            transitionRefInfo.setTransitionIObserver(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("TransitionIReply")) {
                            transitionRefInfo.setTransitionIReply(jsonReader.nextString());


                        } else if (partName.equalsIgnoreCase("TrigChapter")) {     // 8
                            trigRefInfo.setTrigChapter(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("TrigSubChapter")) {
                            trigRefInfo.setTrigSubChapter(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("TrigNo")) {
                            trigRefInfo.setTrigNo(jsonReader.nextString());


                        } else if (partName.equalsIgnoreCase("TrigStateNo")) {
                            trigRefInfo.setTrigStateNo(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("TrigName")) {
                            trigRefInfo.setTrigName(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("TrigFormula")) {
                            trigRefInfo.setTrigFormula(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("TrigCondition")) {
                            jsonReader.beginObject();
                            HashMap<String, String> trigCondition = trigRefInfo.getTrigCondition();
                            while (jsonReader.hasNext()) {
                                trigCondition.put(jsonReader.nextName(), jsonReader.nextString());
                            }
                            jsonReader.endObject();
                            trigRefInfo.setTrigCondition(trigCondition);
                        } else if (partName.equalsIgnoreCase("TrigAction")) {
                            trigRefInfo.setTrigAction(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("TrigTrans")) {
                            trigRefInfo.setTrigTrans(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("TrigSound")) {
                            trigRefInfo.setTrigSound(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("TrigTimer")) {
                            trigRefInfo.setTrigTimer(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("TrigRemark")) {
                            trigRefInfo.setTrigRemark(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("TrigUUID")) {
                            trigRefInfo.setTrigUUID(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("TrigSignal")) {
                            trigRefInfo.setTrigSignal(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("TrigEvent")) {
                            trigRefInfo.setTrigEvent(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("TrigConditionModel")) {
                            jsonReader.beginObject();
                            HashMap<String, String> trigConditionModel = trigRefInfo.getTrigConditionModel();
                            while (jsonReader.hasNext()) {
                                trigConditionModel.put(jsonReader.nextName(), jsonReader.nextString());
                            }
                            jsonReader.endObject();
                            trigRefInfo.setTrigConditionModel(trigConditionModel);
                        } else if (partName.equalsIgnoreCase("TrigActionModel")) {
                            trigRefInfo.setTrigActionModel(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("TrigObserver")) {
                            trigRefInfo.setTrigObserver(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("TrigReply")) {
                            trigRefInfo.setTrigReply(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("TrigTransType")) {
                            trigRefInfo.setTrigTransType(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("TrigTransID")) {
                            trigRefInfo.setTrigTransID(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("AvailableModelChapter")) {
                            destGradeInfo.setAvailableModelChapter(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("AvailableModelSubChapter")) {
                            destGradeInfo.setAvailableModelSubChapter(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("AvailableModelName")) {
                            destGradeInfo.setAvailableModelName(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("AvailableModelRemark")) {
                            destGradeInfo.setAvailableModelRemark(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("AvailableModelUUID")) {
                            destGradeInfo.setAvailableModelUUID(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("AvailableModelBandType")) {
                            destGradeInfo.setAvailableModelBandType(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("Japan")) {
                            destGradeInfo.setIsAdaptJapan(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("NorthAmerica")) {
                            destGradeInfo.setIsAdaptNorthAmerica(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("Europe")) {
                            destGradeInfo.setIsAdaptEurope(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("Korea")) {
                            destGradeInfo.setIsAdaptKorea(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("HongKongMacao")) {
                            destGradeInfo.setIsAdaptHongKongMacao(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("Thailand")) {
                            destGradeInfo.setIsAdaptThailand(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("Oceania")) {
                            destGradeInfo.setIsAdaptOceania(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("SouthAfrica")) {
                            destGradeInfo.setIsAdaptSouthAfrica(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("China")) {
                            destGradeInfo.setIsAdaptChina(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("MiddleEast")) {
                            destGradeInfo.setIsAdaptMiddleEast(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("Taiwan")) {
                            destGradeInfo.setIsAdaptTaiwan(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("SoutheastAsia")) {
                            destGradeInfo.setIsAdaptSoutheastAsia(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("CSOrBR")) {
                            destGradeInfo.setIsAdaptCSOrBR(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("LatinAmerica(AR)")) {
                            destGradeInfo.setIsAdaptLatinAmericaAR(jsonReader.nextString());
                        } else if (partName.equalsIgnoreCase("India")) {
                            destGradeInfo.setIsAdaptIndia(jsonReader.nextString());
                        } else {
                            jsonReader.skipValue();
                        }
                    }
                    jsonReader.endObject();
                    JsonPartInfo_new jsonPartInfo = new JsonPartInfo_new();
                    jsonPartInfo.setUuid(uuidName);
                    jsonPartInfo.setDestGradeInfo(destGradeInfo);
                    jsonPartInfo.setDisplayRefInfo(displayRefInfo);
                    jsonPartInfo.setDisplayRect(displayRectInfo);
                    jsonPartInfo.setActiveRefInfo(activeRefInfo);
                    jsonPartInfo.setActionRefInfo(actionRefInfo);
                    jsonPartInfo.setHkRefInfo(hkRefInfo);
                    jsonPartInfo.setInitRefInfo(initRefInfo);
                    jsonPartInfo.setStatusChangeRefInfo(statusChangeRefInfo);
                    jsonPartInfo.setTransitionRefInfo(transitionRefInfo);
                    jsonPartInfo.setTrigRefInfo(trigRefInfo);
                    jsonPartInfoList.add(jsonPartInfo);
                }
            }
            jsonReader.endObject();
            oneSketchInfo.setJsonPartInfoList(jsonPartInfoList);
            ArrayList<String> HKDevNameList = new ArrayList<>();
            ArrayList<String> HKDevTypeList = new ArrayList<>();
            ArrayList<String> BindTypeList = new ArrayList<>();
            for (int i = 0; i < jsonPartInfoList.size(); ++i) {
                JsonPartInfo_new jsonPartInfo = jsonPartInfoList.get(i);
                JsonHKRefInfo hkRefInfo = jsonPartInfo.getHkRefInfo();
                JsonDestGradeInfo destGradeInfo = jsonPartInfo.getDestGradeInfo();
                if (hkRefInfo.getHKDevName() != null && !HKDevNameList.contains(hkRefInfo.getHKDevName())) {
                    HKDevNameList.add(hkRefInfo.getHKDevName());
                }
                if (hkRefInfo.getHKDevType() != null && !HKDevTypeList.contains(hkRefInfo.getHKDevType())) {
                    HKDevTypeList.add(hkRefInfo.getHKDevType());
                }
                if (destGradeInfo.getAvailableModelBandType() != null && !BindTypeList.contains(destGradeInfo.getAvailableModelBandType())) {
                    BindTypeList.add(destGradeInfo.getAvailableModelBandType());
                }
            }
            oneSketchInfo.setHkDevNameList(HKDevNameList);
            oneSketchInfo.setHkDevTypeList(HKDevTypeList);
            oneSketchInfo.setBindTypeList(BindTypeList);
            this.screenInfoList.add(oneSketchInfo);
        }
        jsonReader.endObject();
    }

}