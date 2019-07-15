package sketch.common;

public class JsonConditionStuct {
    private String ConditionKey;
    private String ConditionContent;

    public void setConditionKey(String conditionKey) {
        ConditionKey = conditionKey;
    }

    public String getConditionKey() {
        return ConditionKey;
    }

    public void setConditionContent(String conditionContent) {
        ConditionContent = conditionContent;
    }

    public String getConditionContent() {
        return ConditionContent;
    }
}
