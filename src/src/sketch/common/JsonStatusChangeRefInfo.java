package sketch.common;

import java.util.ArrayList;
import java.util.HashMap;

public class JsonStatusChangeRefInfo {
    private String StatusChangeChapter;
    private String StatusChangeSubChapter;
    private String StatusChangeNo;
    private String StatusChangeStateNo;
    private String StatusChangeName;
    private String StatusChangeFFormula;
    private HashMap<String, String> StatusChangeFCondition = new HashMap<String, String>();
    private String StatusChangeFAction;
    private String StatusChangeFTrans;
    private String StatusChangeBFormula;
    private HashMap<String, String> StatusChangeBCondition = new HashMap<String, String>();
    private String StatusChangeBAction;
    private String StatusChangeBTrans;
    private String StatusChangeIFormula;
    private HashMap<String, String> StatusChangeICondition = new HashMap<String, String>();
    private String StatusChangeIAction;
    private String StatusChangeITrans;
    private String StatusChangeUUID;
    private String StatusChangeEvent;
    private HashMap<String, String> StatusChangeFConditionModel = new HashMap<String, String>();
    private String StatusChangeFActionModel;
    private String StatusChangeFObserver;
    private String StatusChangeFReply;
    private HashMap<String, String> StatusChangeBConditionModel = new HashMap<String, String>();
    private String StatusChangeBActionModel;
    private String StatusChangeBObserver;
    private String StatusChangeBReply;
    private HashMap<String, String> StatusChangeIConditionModel = new HashMap<String, String>();
    private String StatusChangeIActionModel;
    private String StatusChangeIObserver;
    private String StatusChangeIReply;

    public void setStatusChangeChapter(String statusChangeChapter) {
        StatusChangeChapter = statusChangeChapter;
    }

    public String getStatusChangeChapter() {
        return StatusChangeChapter;
    }

    public void setStatusChangeSubChapter(String statusChangeSubChapter) {
        StatusChangeSubChapter = statusChangeSubChapter;
    }

    public String getStatusChangeSubChapter() {
        return StatusChangeSubChapter;
    }

    public String getStatusChangeNo() {
        return StatusChangeNo;
    }

    public void setStatusChangeNo(String statusChangeNo) {
        StatusChangeNo = statusChangeNo;
    }

    public String getStatusChangeStateNo() {
        return StatusChangeStateNo;
    }

    public void setStatusChangeStateNo(String statusChangeStateNo) {
        StatusChangeStateNo = statusChangeStateNo;
    }

    public void setStatusChangeName(String statusChangeName) {
        StatusChangeName = statusChangeName;
    }

    public String getStatusChangeName() {
        return StatusChangeName;
    }

    public void setStatusChangeFFormula(String statusChangeFFormula) {
        StatusChangeFFormula = statusChangeFFormula;
    }

    public String getStatusChangeFFormula() {
        return StatusChangeFFormula;
    }

    public void setStatusChangeFCondition(HashMap<String, String> statusChangeFCondition) {
        StatusChangeFCondition = statusChangeFCondition;
    }

    public HashMap<String, String> getStatusChangeFCondition() {
        return StatusChangeFCondition;
    }

    public void setStatusChangeFAction(String statusChangeFAction) {
        StatusChangeFAction = statusChangeFAction;
    }

    public String getStatusChangeFAction() {
        return StatusChangeFAction;
    }

    public void setStatusChangeFTrans(String statusChangeFTrans) {
        StatusChangeFTrans = statusChangeFTrans;
    }

    public String getStatusChangeFTrans() {
        return StatusChangeFTrans;
    }

    public void setStatusChangeBFormula(String statusChangeBFormula) {
        StatusChangeBFormula = statusChangeBFormula;
    }

    public String getStatusChangeBFormula() {
        return StatusChangeBFormula;
    }

    public void setStatusChangeBCondition(HashMap<String, String> statusChangeBCondition) {
        StatusChangeBCondition = statusChangeBCondition;
    }

    public HashMap<String, String> getStatusChangeBCondition() {
        return StatusChangeBCondition;
    }

    public void setStatusChangeBAction(String statusChangeBAction) {
        StatusChangeBAction = statusChangeBAction;
    }

    public String getStatusChangeBAction() {
        return StatusChangeBAction;
    }

    public void setStatusChangeBTrans(String statusChangeBTrans) {
        StatusChangeBTrans = statusChangeBTrans;
    }

    public String getStatusChangeBTrans() {
        return StatusChangeBTrans;
    }

    public void setStatusChangeIFormula(String statusChangeIFormula) {
        StatusChangeIFormula = statusChangeIFormula;
    }

    public String getStatusChangeIFormula() {
        return StatusChangeIFormula;
    }

    public void setStatusChangeICondition(HashMap<String, String> statusChangeICondition) {
        StatusChangeICondition = statusChangeICondition;
    }

    public HashMap<String, String> getStatusChangeICondition() {
        return StatusChangeICondition;
    }

    public void setStatusChangeIAction(String statusChangeIAction) {
        StatusChangeIAction = statusChangeIAction;
    }

    public String getStatusChangeIAction() {
        return StatusChangeIAction;
    }

    public void setStatusChangeITrans(String statusChangeITrans) {
        StatusChangeITrans = statusChangeITrans;
    }

    public String getStatusChangeITrans() {
        return StatusChangeITrans;
    }

    public void setStatusChangeUUID(String statusChangeUUID) {
        StatusChangeUUID = statusChangeUUID;
    }

    public String getStatusChangeUUID() {
        return StatusChangeUUID;
    }

    public void setStatusChangeEvent(String statusChangeEvent) {
        StatusChangeEvent = statusChangeEvent;
    }

    public String getStatusChangeEvent() {
        return StatusChangeEvent;
    }

    public void setStatusChangeFConditionModel(HashMap<String, String> statusChangeFConditionModel) {
        StatusChangeFConditionModel = statusChangeFConditionModel;
    }

    public HashMap<String, String> getStatusChangeFConditionModel() {
        return StatusChangeFConditionModel;
    }

    public void setStatusChangeFActionModel(String statusChangeFActionModel) {
        StatusChangeFActionModel = statusChangeFActionModel;
    }

    public String getStatusChangeFActionModel() {
        return StatusChangeFActionModel;
    }

    public void setStatusChangeFObserver(String statusChangeFObserver) {
        StatusChangeFObserver = statusChangeFObserver;
    }

    public String getStatusChangeFObserver() {
        return StatusChangeFObserver;
    }

    public void setStatusChangeFReply(String statusChangeFReply) {
        StatusChangeFReply = statusChangeFReply;
    }

    public String getStatusChangeFReply() {
        return StatusChangeFReply;
    }

    public void setStatusChangeBConditionModel(HashMap<String, String> statusChangeBConditionModel) {
        StatusChangeBConditionModel = statusChangeBConditionModel;
    }

    public HashMap<String, String> getStatusChangeBConditionModel() {
        return StatusChangeBConditionModel;
    }

    public void setStatusChangeBActionModel(String statusChangeBActionModel) {
        StatusChangeBActionModel = statusChangeBActionModel;
    }

    public String getStatusChangeBActionModel() {
        return StatusChangeBActionModel;
    }

    public void setStatusChangeBObserver(String statusChangeBObserver) {
        StatusChangeBObserver = statusChangeBObserver;
    }

    public String getStatusChangeBObserver() {
        return StatusChangeBObserver;
    }

    public void setStatusChangeBReply(String statusChangeBReply) {
        StatusChangeBReply = statusChangeBReply;
    }

    public String getStatusChangeBReply() {
        return StatusChangeBReply;
    }

    public void setStatusChangeIConditionModel(HashMap<String, String> statusChangeIConditionModel) {
        StatusChangeIConditionModel = statusChangeIConditionModel;
    }

    public HashMap<String, String> getStatusChangeIConditionModel() {
        return StatusChangeIConditionModel;
    }

    public void setStatusChangeIActionModel(String statusChangeIActionModel) {
        StatusChangeIActionModel = statusChangeIActionModel;
    }

    public String getStatusChangeIActionModel() {
        return StatusChangeIActionModel;
    }

    public void setStatusChangeIObserver(String statusChangeIObserver) {
        StatusChangeIObserver = statusChangeIObserver;
    }

    public String getStatusChangeIObserver() {
        return StatusChangeIObserver;
    }

    public void setStatusChangeIReply(String statusChangeIReply) {
        StatusChangeIReply = statusChangeIReply;
    }

    public String getStatusChangeIReply() {
        return StatusChangeIReply;
    }
}
