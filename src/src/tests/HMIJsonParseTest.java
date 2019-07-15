package tests;

import com.google.gson.stream.JsonReader;
import sketch.hmi.HMIJsonParse;

import java.io.FileReader;

public class HMIJsonParseTest {
    public static void main(String[] args) throws Exception {
        new HMIJsonParse().parseFile("/Users/hcz/workspace/USB001_01.json");
    }
}
