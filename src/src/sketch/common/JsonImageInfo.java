package sketch.common;

public class JsonImageInfo {
    public int x;
    public int y;
    public int width;
    public int height;
    public String imgPath;
    public String objUUID;
    public String realFullID;

    public JsonImageInfo() {
    }

    public int getRectX() {
        return x;
    }

    public void setRectX(int rect_x) {
        x = rect_x;
    }

    public int getRectY() {
        return y;
    }

    public void setRectY(int rect_y) {
        y = rect_y;
    }

    public int getRectW() {
        return width;
    }

    public void setRectW(int rect_w) {
        width = rect_w;
    }

    public int getRectH() {
        return height;
    }

    public void setRectH(int rect_h) {
        height = rect_h;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String img_path) {
        imgPath = img_path;
    }

}
