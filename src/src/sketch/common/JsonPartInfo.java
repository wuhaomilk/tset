package sketch.common;

import java.util.ArrayList;

public class JsonPartInfo {
    public JsonImageInfo partImageInfo;
    public String partsID = "";
    public boolean isTouchSymbol = false;
    public String displayImage = "";
    public String partsType = "";
    public String partsName = "";
    public boolean isGlobalItem = false;
    public JsonPartCondition displayCondition;
    public JsonTxtImgInfo textImgInfo;
    public JsonPartSWInfo SWInfo;
    public ArrayList<JsonPartInfo> subSymbolList = new ArrayList<JsonPartInfo>();

    public JsonImageInfo getPartImageInfo() {
        return partImageInfo;
    }

    public void setPartImageInfo(JsonImageInfo partImageInfo) {
        this.partImageInfo = partImageInfo;
    }

    public String getPartsID() {
        return partsID;
    }

    public void setPartsID(String partsID) {
        this.partsID = partsID;
    }

    public void setIsTouchSymbol(String touchSymbolStr) {
        if (touchSymbolStr.equalsIgnoreCase("1")) {
            this.isTouchSymbol = true;
        }
    }

    public  boolean getIsTouchSymbol() {
        return this.isTouchSymbol;
    }

    public String getDisplayImage() {
        return displayImage;
    }

    public void setDisplayImage(String displayImage) {
        this.displayImage = displayImage;
    }

    public String getPartsType() {
        return partsType;
    }

    public void setPartsType(String partsType) {
        this.partsType = partsType;
    }

    public String getPartsName() {
        return partsName;
    }

    public void setPartsName(String partsName) {
        this.partsName = partsName;
    }

    public JsonPartCondition getDisplayCondition() {
        return displayCondition;
    }

    public void setDisplayCondition(JsonPartCondition displayCondition) {
        this.displayCondition = displayCondition;
    }

    public JsonTxtImgInfo getTextImgInfo() {
        return textImgInfo;
    }

    public void setTextImgInfo(JsonTxtImgInfo textImgInfo) {
        this.textImgInfo = textImgInfo;
    }

    public JsonPartSWInfo getSWInfo() {
        return SWInfo;
    }

    public void setSWInfo(JsonPartSWInfo SWInfo) {
        this.SWInfo = SWInfo;
    }
}
