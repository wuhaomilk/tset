package sketch.common;

import java.util.ArrayList;

public class JsonPartCondition {
    private String formulaInfo;
    private ArrayList<String[]> conditionList = new ArrayList<>();
    private String sameConPartID;
    private String sameConScreenID;
    private String elseInfo;

    public String getFormulaInfo() {
        return formulaInfo;
    }

    public void setFormulaInfo(String formulaInfo) {
        this.formulaInfo = formulaInfo;
    }

    public ArrayList<String[]> getConditionList() {
        return conditionList;
    }

    public String getSameConPartID() {
        return sameConPartID;
    }

    public void setSameConPartID(String sameConPartID) {
        this.sameConPartID = sameConPartID;
    }

    public String getSameConScreenID() {
        return sameConScreenID;
    }

    public void setSameConScreenID(String sameConScreenID) {
        this.sameConScreenID = sameConScreenID;
    }

    public String getElseInfo() {
        return elseInfo;
    }

    public void setElseInfo(String elseInfo) {
        this.elseInfo = elseInfo;
    }
}
