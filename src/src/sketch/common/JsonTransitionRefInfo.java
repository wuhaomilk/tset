package sketch.common;

import java.util.ArrayList;
import java.util.HashMap;

public class JsonTransitionRefInfo {
    private String TransitionChapter;
    private String TransitionSubChapter;
    private String TransitionNo;
    private String TransitionStateNo;
    private String TransitionName;
    private String TransitionFFormula;
    private HashMap<String, String> TransitionFCondition = new HashMap<String, String>();
    private String TransitionFAction;
    private String TransitionFTrans;
    private String TransitionBFormula;
    private HashMap<String, String> TransitionBCondition = new HashMap<String, String>();
    private String TransitionBAction;
    private String TransitionBTrans;
    private String TransitionIFormula;
    private HashMap<String, String> TransitionICondition = new HashMap<String, String>();
    private String TransitionIAction;
    private String TransitionITrans;
    private String TransitionUUID;
    private String TransitionEvent;
    private HashMap<String, String> TransitionFConditionModel = new HashMap<String, String>();
    private String TransitionFActionModel;
    private String TransitionFObserver;
    private String TransitionFReply;
    private HashMap<String, String> TransitionBConditionModel = new HashMap<String, String>();
    private String TransitionBActionModel;
    private String TransitionBObserver;
    private String TransitionBReply;
    private HashMap<String, String> TransitionIConditionModel = new HashMap<String, String>();
    private String TransitionIActionModel;
    private String TransitionIObserver;
    private String TransitionIReply;

    public void setTransitionChapter(String transitionChapter) {
        TransitionChapter = transitionChapter;
    }

    public String getTransitionChapter() {
        return TransitionChapter;
    }

    public void setTransitionSubChapter(String transitionSubChapter) {
        TransitionSubChapter = transitionSubChapter;
    }

    public String getTransitionSubChapter() {
        return TransitionSubChapter;
    }

    public String getTransitionNo() {
        return TransitionNo;
    }

    public void setTransitionNo(String transitionNo) {
        TransitionNo = transitionNo;
    }

    public String getTransitionStateNo() {
        return TransitionStateNo;
    }

    public void setTransitionStateNo(String transitioStateNo) {
        TransitionStateNo = transitioStateNo;
    }

    public void setTransitionName(String transitionName) {
        TransitionName = transitionName;
    }

    public String getTransitionName() {
        return TransitionName;
    }

    public void setTransitionFFormula(String transitionFFormula) {
        TransitionFFormula = transitionFFormula;
    }

    public String getTransitionFFormula() {
        return TransitionFFormula;
    }

    public void setTransitionFCondition(HashMap<String, String> transitionFCondition) {
        TransitionFCondition = transitionFCondition;
    }

    public HashMap<String, String> getTransitionFCondition() {
        return TransitionFCondition;
    }

    public void setTransitionFAction(String transitionFAction) {
        TransitionFAction = transitionFAction;
    }

    public String getTransitionFAction() {
        return TransitionFAction;
    }

    public void setTransitionFTrans(String transitionFTrans) {
        TransitionFTrans = transitionFTrans;
    }

    public String getTransitionFTrans() {
        return TransitionFTrans;
    }

    public void setTransitionBFormula(String transitionBFormula) {
        TransitionBFormula = transitionBFormula;
    }

    public String getTransitionBFormula() {
        return TransitionBFormula;
    }

    public void setTransitionBCondition(HashMap<String, String> transitionBCondition) {
        TransitionBCondition = transitionBCondition;
    }

    public HashMap<String, String> getTransitionBCondition() {
        return TransitionBCondition;
    }

    public void setTransitionBAction(String transitionBAction) {
        TransitionBAction = transitionBAction;
    }

    public String getTransitionBAction() {
        return TransitionBAction;
    }

    public void setTransitionBTrans(String transitionBTrans) {
        TransitionBTrans = transitionBTrans;
    }

    public String getTransitionBTrans() {
        return TransitionBTrans;
    }

    public void setTransitionIFormula(String transitionIFormula) {
        TransitionIFormula = transitionIFormula;
    }

    public String getTransitionIFormula() {
        return TransitionIFormula;
    }

    public void setTransitionICondition(HashMap<String, String> transitionICondition) {
        TransitionICondition = transitionICondition;
    }

    public HashMap<String, String> getTransitionICondition() {
        return TransitionICondition;
    }

    public void setTransitionIAction(String transitionIAction) {
        TransitionIAction = transitionIAction;
    }

    public String getTransitionIAction() {
        return TransitionIAction;
    }

    public void setTransitionITrans(String transitionITrans) {
        TransitionITrans = transitionITrans;
    }

    public String getTransitionITrans() {
        return TransitionITrans;
    }

    public void setTransitionUUID(String transitionUUID) {
        TransitionUUID = transitionUUID;
    }

    public String getTransitionUUID() {
        return TransitionUUID;
    }

    public void setTransitionEvent(String transitionEvent) {
        TransitionEvent = transitionEvent;
    }

    public String getTransitionEvent() {
        return TransitionEvent;
    }

    public void setTransitionFConditionModel(HashMap<String, String> transitionFConditionModel) {
        TransitionFConditionModel = transitionFConditionModel;
    }

    public HashMap<String, String> getTransitionFConditionModel() {
        return TransitionFConditionModel;
    }

    public void setTransitionFActionModel(String transitionFActionModel) {
        TransitionFActionModel = transitionFActionModel;
    }

    public String getTransitionFActionModel() {
        return TransitionFActionModel;
    }

    public void setTransitionFObserver(String transitionFObserver) {
        TransitionFObserver = transitionFObserver;
    }

    public String getTransitionFObserver() {
        return TransitionFObserver;
    }

    public void setTransitionFReply(String transitionFReply) {
        TransitionFReply = transitionFReply;
    }

    public String getTransitionFReply() {
        return TransitionFReply;
    }

    public void setTransitionBConditionModel(HashMap<String, String> transitionBConditionModel) {
        TransitionBConditionModel = transitionBConditionModel;
    }

    public HashMap<String, String> getTransitionBConditionModel() {
        return TransitionBConditionModel;
    }

    public void setTransitionBActionModel(String transitionBActionModel) {
        TransitionBActionModel = transitionBActionModel;
    }

    public String getTransitionBActionModel() {
        return TransitionBActionModel;
    }

    public void setTransitionBObserver(String transitionBObserver) {
        TransitionBObserver = transitionBObserver;
    }

    public String getTransitionBObserver() {
        return TransitionBObserver;
    }

    public void setTransitionBReply(String transitionBReply) {
        TransitionBReply = transitionBReply;
    }

    public String getTransitionBReply() {
        return TransitionBReply;
    }

    public void setTransitionIConditionModel(HashMap<String, String> transitionIConditionModel) {
        TransitionIConditionModel = transitionIConditionModel;
    }

    public HashMap<String, String> getTransitionIConditionModel() {
        return TransitionIConditionModel;
    }

    public void setTransitionIActionModel(String transitionIActionModel) {
        TransitionIActionModel = transitionIActionModel;
    }

    public String getTransitionIActionModel() {
        return TransitionIActionModel;
    }

    public void setTransitionIObserver(String transitionIObserver) {
        TransitionIObserver = transitionIObserver;
    }

    public String getTransitionIObserver() {
        return TransitionIObserver;
    }

    public void setTransitionIReply(String transitionIReply) {
        TransitionIReply = transitionIReply;
    }

    public String getTransitionIReply() {
        return TransitionIReply;
    }
}
