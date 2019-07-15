package sketch.common;

public class JsonPartSWInfo {
    private JsonPartCondition tonedownConInMotion;
    private JsonPartCondition tonedownConExceptInMotion;
    private JsonPartCondition selectedCon;
    private String SWOpePattern;
    private String OpeResultScreenTrans;
    private String OpeResultStartFunc;
    private String OpeResultSettingValue;
    private String OpeResultOther;
    private String SWInfoBeep;

    public JsonPartCondition getTonedownConInMotion() {
        return tonedownConInMotion;
    }

    public void setTonedownConInMotion(JsonPartCondition tonedownConInMotion) {
        this.tonedownConInMotion = tonedownConInMotion;
    }

    public JsonPartCondition getTonedownConExceptInMotion() {
        return tonedownConExceptInMotion;
    }

    public void setTonedownConExceptInMotion(JsonPartCondition tonedownConExceptInMotion) {
        this.tonedownConExceptInMotion = tonedownConExceptInMotion;
    }

    public JsonPartCondition getSelectedCon() {
        return selectedCon;
    }

    public void setSelectedCon(JsonPartCondition selectedCon) {
        this.selectedCon = selectedCon;
    }

    public String getSWOpePattern() {
        return SWOpePattern;
    }

    public void setSWOpePattern(String SWOpePattern) {
        this.SWOpePattern = SWOpePattern;
    }

    public String getOpeResultScreenTrans() {
        return OpeResultScreenTrans;
    }

    public void setOpeResultScreenTrans(String opeResultScreenTrans) {
        OpeResultScreenTrans = opeResultScreenTrans;
    }

    public String getOpeResultStartFunc() {
        return OpeResultStartFunc;
    }

    public void setOpeResultStartFunc(String opeResultStartFunc) {
        OpeResultStartFunc = opeResultStartFunc;
    }

    public String getOpeResultSettingValue() {
        return OpeResultSettingValue;
    }

    public void setOpeResultSettingValue(String opeResultSettingValue) {
        OpeResultSettingValue = opeResultSettingValue;
    }

    public String getOpeResultOther() {
        return OpeResultOther;
    }

    public void setOpeResultOther(String opeResultOther) {
        OpeResultOther = opeResultOther;
    }

    public String getSWInfoBeep() {
        return SWInfoBeep;
    }

    public void setSWInfoBeep(String SWInfoBeep) {
        this.SWInfoBeep = SWInfoBeep;
    }
}
