package sketch.common;

import java.util.ArrayList;

public class JsonSketchInfo_new {
    private String sketchInfoId;
    private String ScreenID;
    private String ScreenName;
    private String OutLine;
    private String DisplayRefInfoStr;
    private String ActiveRefInfoStr;
    private String ActionRefInfoStr;
    private String HKRefInfoStr;
    private String InitRefInfoStr;
    private String StatusChangeRefInfoStr;
    private String TransitionRefInfoStr;
    private String TrigRefInfoStr;
    private String ScreenDispPic;
    private String ScreenUUID;
    public JsonScreenImageInfo screenInfo ;
    private ArrayList<String> TransitionUUIDArray = new ArrayList<>();
    private ArrayList<String> AvailableModelUUIDArray = new ArrayList<>();
    private ArrayList<String> InitUUIDArray = new ArrayList<>();
    private ArrayList<String> HKUUIDArray = new ArrayList<>();
    private ArrayList<String> TrigUUIDArray = new ArrayList<>();
    private ArrayList<String> StatusChangeUUIDArray = new ArrayList<>();
    private ArrayList<String> hkDevNameList = new ArrayList<>();
    private ArrayList<String> hkDevTypeList = new ArrayList<>();
    private ArrayList<String> bindTypeList = new ArrayList<>();

    ArrayList<JsonPartInfo_new> jsonPartInfoList = new ArrayList<>();

    public void setSketchInfoId(String sketchInfoId) {
        this.sketchInfoId = sketchInfoId;
    }

    public String getSketchInfoId() {
        return sketchInfoId;
    }

    public void setScreenID(String screenID) {
        ScreenID = screenID;
    }

    public String getScreenID() {
        return ScreenID;
    }

    public void setScreenName(String screenName) {
        ScreenName = screenName;
    }

    public String getScreenName() {
        return ScreenName;
    }

    public void setOutLine(String outLine) {
        OutLine = outLine;
    }

    public String getOutLine() {
        return OutLine;
    }

    public void setDisplayRefInfoStr(String displayRefInfoStr) {
        DisplayRefInfoStr = displayRefInfoStr;
    }

    public String getDisplayRefInfoStr() {
        return DisplayRefInfoStr;
    }

    public void setActiveRefInfoStr(String activeRefInfoStr) {
        ActiveRefInfoStr = activeRefInfoStr;
    }

    public String getActiveRefInfoStr() {
        return ActiveRefInfoStr;
    }

    public void setActionRefInfoStr(String actionRefInfoStr) {
        ActionRefInfoStr = actionRefInfoStr;
    }

    public String getActionRefInfoStr() {
        return ActionRefInfoStr;
    }

    public void setHKRefInfoStr(String HKRefInfoStr) {
        this.HKRefInfoStr = HKRefInfoStr;
    }

    public String getHKRefInfoStr() {
        return HKRefInfoStr;
    }

    public void setInitRefInfoStr(String initRefInfoStr) {
        InitRefInfoStr = initRefInfoStr;
    }

    public String getInitRefInfoStr() {
        return InitRefInfoStr;
    }

    public void setStatusChangeRefInfoStr(String statusChangeRefInfoStr) {
        StatusChangeRefInfoStr = statusChangeRefInfoStr;
    }

    public String getStatusChangeRefInfoStr() {
        return StatusChangeRefInfoStr;
    }

    public void setTransitionRefInfoStr(String transitionRefInfoStr) {
        TransitionRefInfoStr = transitionRefInfoStr;
    }

    public String getTransitionRefInfoStr() {
        return TransitionRefInfoStr;
    }

    public void setTrigRefInfoStr(String trigRefInfoStr) {
        TrigRefInfoStr = trigRefInfoStr;
    }

    public String getTrigRefInfoStr() {
        return TrigRefInfoStr;
    }

    public void setTransitionUUIDArray(ArrayList<String> transitionUUIDArray) {
        TransitionUUIDArray = transitionUUIDArray;
    }

    public void setScreenDispPic(String screenDispPic) {
        ScreenDispPic = screenDispPic;
    }

    public String getScreenDispPic() {
        return ScreenDispPic;
    }

    public void setScreenUUID(String screenUUID) {
        ScreenUUID = screenUUID;
    }

    public String getScreenUUID() {
        return ScreenUUID;
    }

    public ArrayList<String> getTransitionUUIDArray() {
        return TransitionUUIDArray;
    }

    public void setAvailableModelUUIDArray(ArrayList<String> availableModelUUIDArray) {
        AvailableModelUUIDArray = availableModelUUIDArray;
    }

    public ArrayList<String> getAvailableModelUUIDArray() {
        return AvailableModelUUIDArray;
    }

    public void setInitUUIDArray(ArrayList<String> initUUIDArray) {
        InitUUIDArray = initUUIDArray;
    }

    public ArrayList<String> getInitUUIDArray() {
        return InitUUIDArray;
    }

    public void setHKUUIDArray(ArrayList<String> HKUUIDArray) {
        this.HKUUIDArray = HKUUIDArray;
    }

    public ArrayList<String> getHKUUIDArray() {
        return HKUUIDArray;
    }

    public void setTrigUUIDArray(ArrayList<String> trigUUIDArray) {
        TrigUUIDArray = trigUUIDArray;
    }

    public ArrayList<String> getTrigUUIDArray() {
        return TrigUUIDArray;
    }

    public void setStatusChangeUUIDArray(ArrayList<String> statusChangeUUIDArray) {
        StatusChangeUUIDArray = statusChangeUUIDArray;
    }

    public ArrayList<String> getStatusChangeUUIDArray() {
        return StatusChangeUUIDArray;
    }

    public void setJsonPartInfoList(ArrayList<JsonPartInfo_new> jsonPartInfoList) {
        this.jsonPartInfoList = jsonPartInfoList;
    }

    public ArrayList<JsonPartInfo_new> getJsonPartInfoList() {
        return jsonPartInfoList;
    }

    public void setHkDevNameList(ArrayList<String> hkDevNameList) {
        this.hkDevNameList = hkDevNameList;
    }

    public ArrayList<String> getHkDevNameList() {
        return hkDevNameList;
    }

    public void setHkDevTypeList(ArrayList<String> hkDevTypeList) {
        this.hkDevTypeList = hkDevTypeList;
    }

    public ArrayList<String> getHkDevTypeList() {
        return hkDevTypeList;
    }

    public void setBindTypeList(ArrayList<String> bindTypeList) {
        this.bindTypeList = bindTypeList;
    }

    public ArrayList<String> getBindTypeList() {
        return bindTypeList;
    }
}
