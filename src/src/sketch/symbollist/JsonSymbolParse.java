package sketch.symbollist;

import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.util.ArrayList;

public class JsonSymbolParse {
    private  ArrayList<SymbolData> symbolList = new ArrayList<SymbolData>();

    private void parseJsonObject(JsonReader jsonReader) throws Exception {
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            SymbolData oneData = new SymbolData();

            oneData.pageUUID = jsonReader.nextName();

            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String pageTagName = jsonReader.nextName();
                if (pageTagName.equalsIgnoreCase("name")) {
                    oneData.pageName = jsonReader.nextString();
                }
                else if(pageTagName.equalsIgnoreCase("layers")) {
                    parseArtboard(jsonReader, oneData);
                }
            }
            jsonReader.endObject();
        }

        jsonReader.endObject();
    }

    private void parseArtboard(JsonReader jsonReader, SymbolData oneData) throws Exception {
        SymbolData retData = oneData;

        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            retData.artboardUUID = jsonReader.nextName();

            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String artboardTagName = jsonReader.nextName();
                if (artboardTagName.equalsIgnoreCase("name")) {
                    retData.artboardName = jsonReader.nextString();
                }
                else if(artboardTagName.equalsIgnoreCase("layers")) {
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        SymbolData oneSymbolData = new SymbolData();
                        oneSymbolData.pageUUID = retData.pageUUID;
                        oneSymbolData.pageName = retData.pageName;
                        oneSymbolData.artboardUUID = retData.artboardUUID;
                        oneSymbolData.artboardName = retData.artboardName;

                        oneSymbolData.symbolUUID = jsonReader.nextName();
                        jsonReader.beginObject();
                        while (jsonReader.hasNext()) {
                            String symbolTagName = jsonReader.nextName();
                            if (symbolTagName.equalsIgnoreCase("name")) {
                                oneSymbolData.symbolName = jsonReader.nextString();
                            }
                            else if(symbolTagName.equalsIgnoreCase("imgPath")) {
                                oneSymbolData.symbolImage = jsonReader.nextString();
                            }
                            else if(symbolTagName.equalsIgnoreCase("isExpaned")) {
                                int expandflag = jsonReader.nextInt();
                                if (expandflag > 0) {
                                    oneSymbolData.symbolExpandFlag = "Y";
                                }
                            }
                            else if (symbolTagName.equalsIgnoreCase("expandLevel")) {
                                int expandlevel = jsonReader.nextInt();
                                if (expandlevel > 0) {
                                    oneSymbolData.symbolExpandLevel = String.format("%d", expandlevel);
                                }
                            }

                        }
                        jsonReader.endObject();

                        this.symbolList.add(oneSymbolData);
                    }

                    jsonReader.endObject();
                }
            }

            jsonReader.endObject();


        }

        jsonReader.endObject();

    }

    public ArrayList<SymbolData> parseFile(String jsonFileName) throws Exception {
        JsonReader jsonReader = new JsonReader(new FileReader(jsonFileName));
        parseJsonObject(jsonReader);

        return this.symbolList;
    }
}
