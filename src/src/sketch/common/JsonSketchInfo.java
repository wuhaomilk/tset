package sketch.common;

import java.util.ArrayList;
import java.util.HashMap;

public class JsonSketchInfo {
    public JsonScreenInfo screenInfo;
    public HashMap<String, String> destGradeMap = new HashMap<String, String>();
    public ArrayList<JsonPartInfo> partInfoList = new ArrayList<>();
}
