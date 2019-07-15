package sketch.common;

public class JsonPartInfo_new {
    private JsonDestGradeInfo destGradeInfo;
    private JsonDisplayRefInfo displayRefInfo;        // 1
    private JsonImageInfo displayRect;
    private JsonActiveRefInfo activeRefInfo;         // 2
    private JsonActionRefInfo actionRefInfo;         // 3
    private JsonHKRefInfo hkRefInfo;                // 4
    private JsonInitRefInfo initRefInfo;           // 5
    private JsonStatusChangeRefInfo statusChangeRefInfo;  // 6
    private JsonTransitionRefInfo transitionRefInfo;    // 7
    private JsonTrigRefInfo trigRefInfo;            // 8
    private String uuid;

    public void setDestGradeInfo(JsonDestGradeInfo destGradeInfo) {
        this.destGradeInfo = destGradeInfo;
    }

    public JsonDestGradeInfo getDestGradeInfo() {
        return destGradeInfo;
    }

    public void setDisplayRefInfo(JsonDisplayRefInfo displayRefInfo) {
        this.displayRefInfo = displayRefInfo;
    }

    public JsonDisplayRefInfo getDisplayRefInfo() {
        return displayRefInfo;
    }

    public void setDisplayRect(JsonImageInfo displayRect) {
        this.displayRect = displayRect;
    }

    public JsonImageInfo getDisplayRect() {
        return displayRect;
    }

    public void setActionRefInfo(JsonActionRefInfo actionRefInfo) {
        this.actionRefInfo = actionRefInfo;
    }

    public JsonActionRefInfo getActionRefInfo() {
        return actionRefInfo;
    }

    public void setActiveRefInfo(JsonActiveRefInfo activeRefInfo) {
        this.activeRefInfo = activeRefInfo;
    }

    public JsonActiveRefInfo getActiveRefInfo() {
        return activeRefInfo;
    }

    public void setTrigRefInfo(JsonTrigRefInfo trigRefInfo) {
        this.trigRefInfo = trigRefInfo;
    }

    public JsonTrigRefInfo getTrigRefInfo() {
        return trigRefInfo;
    }

    public void setHkRefInfo(JsonHKRefInfo hkRefInfo) {
        this.hkRefInfo = hkRefInfo;
    }

    public JsonHKRefInfo getHkRefInfo() {
        return hkRefInfo;
    }

    public void setInitRefInfo(JsonInitRefInfo initRefInfo) {
        this.initRefInfo = initRefInfo;
    }

    public JsonInitRefInfo getInitRefInfo() {
        return initRefInfo;
    }

    public void setTransitionRefInfo(JsonTransitionRefInfo transitionRefInfo) {
        this.transitionRefInfo = transitionRefInfo;
    }

    public JsonTransitionRefInfo getTransitionRefInfo() {
        return transitionRefInfo;
    }

    public void setStatusChangeRefInfo(JsonStatusChangeRefInfo statusChangeRefInfo) {
        this.statusChangeRefInfo = statusChangeRefInfo;
    }

    public JsonStatusChangeRefInfo getStatusChangeRefInfo() {
        return statusChangeRefInfo;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }
}
