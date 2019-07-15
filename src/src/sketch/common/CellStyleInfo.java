package sketch.common;

public class CellStyleInfo {
    public String posInfo;
    public String fontName;
    public int fontSize;
    public boolean isMerge;
    public boolean isBold;
    public String hAlignment;
    public String vAlignment;
    public String borderLeft;
    public String borderTop;
    public String borderRight;
    public String borderBottom;
    public boolean isCellWrap;
    public int[] backColorRGB;

    public CellStyleInfo(String _posInfo, String _fontName, int _fontSize,
                         boolean _isMerge, boolean _isBold,
                         String _hAlignment, String _vAlignment,
                         String _borderLeft, String _borderTop,
                         String _borderRight, String _borderBottom,
                         boolean _isCellWrap, int[] _backColorRGB) {
        this.posInfo = _posInfo;
        this.fontName = _fontName;
        this.fontSize = _fontSize;
        this.isMerge = _isMerge;
        this.isBold = _isBold;
        this.hAlignment = _hAlignment;
        this.vAlignment = _vAlignment;
        this.borderLeft = _borderLeft;
        this.borderTop = _borderTop;
        this.borderRight = _borderRight;
        this.borderBottom = _borderBottom;
        this.isCellWrap = _isCellWrap;
        this.backColorRGB = _backColorRGB;
    }

}
