package sketch.common;

import java.util.ArrayList;
import java.util.HashMap;

public class JsonInitRefInfo {
    private String InitChapter;
    private String InitSubChapter;
    private String InitNo;
    private String InitStateNo;
    private String InitStatus;
    private String InitFormula;
    private HashMap<String, String> InitCondition = new HashMap<String, String>();
    private String InitAction;
    private String InitTrans;
    private String InitRemark;
    private String InitUUID;
    private String InitEvent;
    private HashMap<String, String> InitConditionModel = new HashMap<String, String>();
    private String InitActionModel;
    private String InitObserver;
    private String InitReplay;
    private String InitTransType;
    private String InitTransID;

    public void setInitChapter(String initChapter) {
        InitChapter = initChapter;
    }

    public String getInitChapter() {
        return InitChapter;
    }

    public void setInitSubChapter(String initSubChapter) {
        InitSubChapter = initSubChapter;
    }

    public String getInitSubChapter() {
        return InitSubChapter;
    }

    public void setInitNo(String initNo) {
        InitNo = initNo;
    }

    public String getInitNo() {
        return InitNo;
    }

    public void setInitStateNo(String initStateNo) {
        InitStateNo = initStateNo;
    }

    public String getInitStateNo() {
        return InitStateNo;
    }

    public void setInitStatus(String initStatus) {
        InitStatus = initStatus;
    }

    public String getInitStatus() {
        return InitStatus;
    }

    public void setInitFormula(String initFormula) {
        InitFormula = initFormula;
    }

    public String getInitFormula() {
        return InitFormula;
    }

    public void setInitCondition(HashMap<String, String> initCondition) {
        InitCondition = initCondition;
    }

    public HashMap<String, String> getInitCondition() {
        return InitCondition;
    }

    public void setInitAction(String initAction) {
        InitAction = initAction;
    }

    public String getInitAction() {
        return InitAction;
    }

    public void setInitTrans(String initTrans) {
        InitTrans = initTrans;
    }

    public String getInitTrans() {
        return InitTrans;
    }

    public void setInitRemark(String initRemark) {
        InitRemark = initRemark;
    }

    public String getInitRemark() {
        return InitRemark;
    }

    public void setInitUUID(String initUUID) {
        InitUUID = initUUID;
    }

    public String getInitUUID() {
        return InitUUID;
    }

    public void setInitEvent(String initEvent) {
        InitEvent = initEvent;
    }

    public String getInitEvent() {
        return InitEvent;
    }

    public void setInitConditionModel(HashMap<String, String> initConditionModel) {
        InitConditionModel = initConditionModel;
    }

    public HashMap<String, String> getInitConditionModel() {
        return InitConditionModel;
    }

    public void setInitActionModel(String initActionModel) {
        InitActionModel = initActionModel;
    }

    public String getInitActionModel() {
        return InitActionModel;
    }

    public void setInitObserver(String initObserver) {
        InitObserver = initObserver;
    }

    public String getInitObserver() {
        return InitObserver;
    }

    public void setInitReplay(String initReplay) {
        InitReplay = initReplay;
    }

    public String getInitReplay() {
        return InitReplay;
    }

    public void setInitTransType(String initTransType) {
        InitTransType = initTransType;
    }

    public String getInitTransType() {
        return InitTransType;
    }

    public void setInitTransID(String initTransID) {
        InitTransID = initTransID;
    }

    public String getInitTransID() {
        return InitTransID;
    }
}
