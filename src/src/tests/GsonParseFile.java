package tests;
import java.io.FileReader;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

public class GsonParseFile {
    public static void main(String[] args) throws Exception{
        JsonReader jsonReader = new JsonReader(new FileReader("/Users/hcz/workspace/USB001_01.json"));
        Gson gson = new Gson();
        jsonReader.beginObject();
        while (jsonReader.hasNext()){
            System.out.println(jsonReader.nextName());
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                System.out.println(jsonReader.nextName());
                System.out.println(jsonReader.nextString());
            }
            jsonReader.endObject();
        }
        jsonReader.endObject();


//        List<StyleInfo>  styleList = (List<StyleInfo>)gson.fromJson(
//                jsonReader,new TypeToken<List<StyleInfo>>() {}.getType()
//        );
//        System.out.println(styleList.size());

//        System.out.println(CellReference.convertColStringToIndex("AA"));
//        System.out.println(CellReference.convertColStringToIndex("aa"));

    }
}
