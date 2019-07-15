package sketch.common;

import java.util.ArrayList;
import java.util.HashMap;

public class JsonActiveRefInfo {
    private String ActiveChapter;
    private String ActiveSubChapter;
    private String ActiveNo;
    private String ActiveStateNo;
    private String ActiveBtnName;
    private String ActiveFormula;
    private HashMap<String, String> ActiveCondition = new HashMap<String, String>();
    private String ActiveAction;
    private String ActiveDuringDriving;
    private String ActiveRemark;
    private String ActiveUUID;
    private String ActivePartsType;
    private String ActiveProperty;
    private String ActiveActionModel;
    private HashMap<String, String> ActiveConditionModel = new HashMap<String, String>();
    private String Active;
    private ArrayList<String> ActiveStates = new ArrayList<>();
    private String HasActiveInfo;
    private String ActivePropertyType;
    private String ActiveDefaultValue;

    public void setActiveChapter(String activeChapter) {
        ActiveChapter = activeChapter;
    }

    public String getActiveChapter() {
        return ActiveChapter;
    }

    public void setActiveSubChapter(String activeSubChapter) {
        ActiveSubChapter = activeSubChapter;
    }

    public String getActiveSubChapter() {
        return ActiveSubChapter;
    }

    public void setActiveNo(String activeNo) {
        ActiveNo = activeNo;
    }

    public String getActiveNo() {
        return ActiveNo;
    }

    public void setActiveStateNo(String activeStateNo) {
        ActiveStateNo = activeStateNo;
    }

    public String getActiveStateNo() {
        return ActiveStateNo;
    }

    public void setActiveBtnName(String activeBtnName) {
        ActiveBtnName = activeBtnName;
    }

    public String getActiveBtnName() {
        return ActiveBtnName;
    }

    public void setActiveFormula(String activeFormula) {
        ActiveFormula = activeFormula;
    }

    public String getActiveFormula() {
        return ActiveFormula;
    }

    public void setActiveCondition(HashMap<String, String> activeCondition) {
        ActiveCondition = activeCondition;
    }

    public HashMap<String, String> getActiveCondition() {
        return ActiveCondition;
    }

    public void setActiveAction(String activeAction) {
        ActiveAction = activeAction;
    }

    public String getActiveAction() {
        return ActiveAction;
    }

    public void setActiveDuringDriving(String activeDuringDriving) {
        ActiveDuringDriving = activeDuringDriving;
    }

    public String getActiveDuringDriving() {
        return ActiveDuringDriving;
    }

    public void setActiveRemark(String activeRemark) {
        ActiveRemark = activeRemark;
    }

    public String getActiveRemark() {
        return ActiveRemark;
    }

    public void setActiveUUID(String activeUUID) {
        ActiveUUID = activeUUID;
    }

    public String getActiveUUID() {
        return ActiveUUID;
    }

    public void setActivePartsType(String activePartsType) {
        ActivePartsType = activePartsType;
    }

    public String getActivePartsType() {
        return ActivePartsType;
    }

    public void setActiveProperty(String activeProperty) {
        ActiveProperty = activeProperty;
    }

    public String getActiveProperty() {
        return ActiveProperty;
    }

    public void setActiveActionModel(String activeActionModel) {
        ActiveActionModel = activeActionModel;
    }

    public String getActiveActionModel() {
        return ActiveActionModel;
    }

    public void setActiveConditionModel(HashMap<String, String> activeConditionModel) {
        ActiveConditionModel = activeConditionModel;
    }

    public HashMap<String, String> getActiveConditionModel() {
        return ActiveConditionModel;
    }

    public void setActive(String active) {
        Active = active;
    }

    public String getActive() {
        return Active;
    }

    public void setActiveStates(ArrayList<String> activeStates) {
        ActiveStates = activeStates;
    }

    public ArrayList<String> getActiveStates() {
        return ActiveStates;
    }

    public void setHasActiveInfo(String hasActiveInfo) {
        HasActiveInfo = hasActiveInfo;
    }

    public String getHasActiveInfo() {
        return HasActiveInfo;
    }

    public void setActivePropertyType(String activePropertyType) {
        ActivePropertyType = activePropertyType;
    }

    public String getActivePropertyType() {
        return ActivePropertyType;
    }

    public void setActiveDefaultValue(String activeDefaultValue) {
        ActiveDefaultValue = activeDefaultValue;
    }

    public String getActiveDefaultValue() {
        return ActiveDefaultValue;
    }
}
