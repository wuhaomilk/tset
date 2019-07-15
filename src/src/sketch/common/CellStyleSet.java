package sketch.common;

import java.util.ArrayList;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.util.CellReference;

public class CellStyleSet {
    private XSSFWorkbook workBook = null;
    private XSSFSheet sheetInfo = null;

    public CellStyleSet(XSSFWorkbook _workBook, XSSFSheet _sheetInfo) {
        this.workBook = _workBook;
        this.sheetInfo = _sheetInfo;
    }

    public void doCellStyleList(ArrayList<CellStyleInfo> styleList) {
        for (int i = 0; i < styleList.size(); i++) {
            if (styleList.get(i) == null) {
                continue;
            }

            this.doOneCellStyle(styleList.get(i));
        }
    }

    public void doOneCellStyle(CellStyleInfo styleInfo) {
        if (this.sheetInfo == null){
            return;
        }

        int[] colRangeArray = this.getPosColRange(styleInfo.posInfo);
        int[] rowRangeArray = this.getPosRowRange(styleInfo.posInfo);

        if (styleInfo.isMerge) {
            this.sheetInfo.addMergedRegionUnsafe(new CellRangeAddress(rowRangeArray[0],
                    rowRangeArray[1],colRangeArray[0],colRangeArray[1]));
        }

        //create cellInfo by styeinfo
        XSSFCellStyle cellStyle = this.getCellStyleByInfo(styleInfo);

        for (int iRow = rowRangeArray[0]; iRow <= rowRangeArray[1]; iRow++) {
            for (int iCol = colRangeArray[0]; iCol <= colRangeArray[1]; iCol++) {
                //Create or get the XSSFRow Information
                if (this.sheetInfo.getRow(iRow) == null) {
                    this.sheetInfo.createRow(iRow);
                }
                XSSFRow iXssfRow = this.sheetInfo.getRow(iRow);

                //Create or get the XSSFCell Information
                if (iXssfRow.getCell(iCol) == null) {
                    iXssfRow.createCell(iCol);
                }
                XSSFCell iXssfCell = iXssfRow.getCell(iCol);

                iXssfCell.setCellStyle(cellStyle);
            }
        }

    }

    private int[] getPosColRange(String posInfo) {
        int[] retColRange = new int[]{-1,-1};

        if (posInfo.indexOf(":")<=0) {
            return retColRange;
        }

        String[] splitStrList = posInfo.split(":");
        if (splitStrList.length !=2 ){
            return retColRange;
        }

        String startColTxt = splitStrList[0].split("\\d+")[0];
        String endColTxt = splitStrList[1].split("\\d+")[0];

        int startRowIndex= CellReference.convertColStringToIndex(startColTxt);
        int endRowIndex = CellReference.convertColStringToIndex(endColTxt);

        retColRange[0] = Math.min(startRowIndex, endRowIndex);
        retColRange[1] = Math.max(startRowIndex, endRowIndex);

        return retColRange;
    }

    private int[] getPosRowRange(String posInfo) {
        int[] retRowRange = new int[]{-1,-1};

        if (posInfo.indexOf(":")<=0) {
            return retRowRange;
        }

        String[] splitStrList = posInfo.split(":");
        if (splitStrList.length !=2 ){
            return retRowRange;
        }

        String startRowTxt = "";
        if (splitStrList[0].matches("^\\d+")) {
            startRowTxt = splitStrList[0].split("\\D+")[0];
        }
        else {
            startRowTxt = splitStrList[0].split("\\D+")[1];
        }

        String endRowTxt="";
        if (splitStrList[1].matches("^\\d+")) {
            endRowTxt = splitStrList[1].split("\\D+")[0];
        }
        else {
            endRowTxt = splitStrList[1].split("\\D+")[1];
        }

        int startRowIndex= Integer.parseInt(startRowTxt)-1;
        int endRowIndex = Integer.parseInt(endRowTxt)-1;

        retRowRange[0] = Math.min(startRowIndex, endRowIndex);
        retRowRange[1] = Math.max(startRowIndex, endRowIndex);

        return retRowRange;
    }

    private XSSFCellStyle getCellStyleByInfo(CellStyleInfo cellInfo) {
        XSSFCellStyle cellStyle = this.workBook.createCellStyle();

        //如果不创建新的font对象，fontname设置无效
        if (cellInfo.fontName.length() > 0) {
            XSSFFont xssfFontInfo = this.workBook.createFont();
            xssfFontInfo.setFontName(cellInfo.fontName);
            if (cellInfo.fontSize > 0) {
                xssfFontInfo.setFontHeight(cellInfo.fontSize);
            }
            xssfFontInfo.setBold(cellInfo.isBold);
            cellStyle.setFont(xssfFontInfo);
        }
        else {
            if (cellInfo.fontSize > 0) {
                XSSFFont xssfFontInfo = this.workBook.createFont();
                xssfFontInfo.setFontHeight(cellInfo.fontSize);
                xssfFontInfo.setBold(cellInfo.isBold);
                cellStyle.setFont(xssfFontInfo);
            }
        }


        if (cellInfo.hAlignment.length() > 0) {
            if (cellInfo.hAlignment.equalsIgnoreCase("left")) {
                cellStyle.setAlignment(HorizontalAlignment.LEFT);
            }
            else if (cellInfo.hAlignment.equalsIgnoreCase("center")) {
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
            }
            else if (cellInfo.hAlignment.equalsIgnoreCase("right")) {
                cellStyle.setAlignment(HorizontalAlignment.RIGHT);
            }
        }

        if (cellInfo.vAlignment.length() > 0) {
            if (cellInfo.vAlignment.equalsIgnoreCase("top")) {
                cellStyle.setVerticalAlignment(VerticalAlignment.TOP);
            }
            else if (cellInfo.vAlignment.equalsIgnoreCase("center")) {
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            }
            else if (cellInfo.vAlignment.equalsIgnoreCase("bottom")) {
                cellStyle.setVerticalAlignment(VerticalAlignment.BOTTOM);
            }
        }

        if (cellInfo.borderLeft.length() > 0) {
            if (cellInfo.borderLeft.equalsIgnoreCase("thin")) {
                cellStyle.setBorderLeft(BorderStyle.THIN);
            }
            else if (cellInfo.borderLeft.equalsIgnoreCase("medium")) {
                cellStyle.setBorderLeft(BorderStyle.MEDIUM);
            }
            else if (cellInfo.borderLeft.equalsIgnoreCase("dashed")) {
                cellStyle.setBorderLeft(BorderStyle.DASHED);
            }
            else if (cellInfo.borderLeft.equalsIgnoreCase("dotted")) {
                cellStyle.setBorderLeft(BorderStyle.DOTTED);
            }
        }

        if (cellInfo.borderTop.length() > 0) {
            if (cellInfo.borderTop.equalsIgnoreCase("thin")) {
                cellStyle.setBorderTop(BorderStyle.THIN);
            }
            else if (cellInfo.borderTop.equalsIgnoreCase("medium")) {
                cellStyle.setBorderTop(BorderStyle.MEDIUM);
            }
            else if (cellInfo.borderTop.equalsIgnoreCase("dashed")) {
                cellStyle.setBorderTop(BorderStyle.DASHED);
            }
            else if (cellInfo.borderTop.equalsIgnoreCase("dotted")) {
                cellStyle.setBorderTop(BorderStyle.DOTTED);
            }
        }

        if (cellInfo.borderBottom.length() > 0) {
            if (cellInfo.borderBottom.equalsIgnoreCase("thin")) {
                cellStyle.setBorderBottom(BorderStyle.THIN);
            }
            else if (cellInfo.borderBottom.equalsIgnoreCase("medium")) {
                cellStyle.setBorderBottom(BorderStyle.MEDIUM);
            }
            else if (cellInfo.borderBottom.equalsIgnoreCase("dashed")) {
                cellStyle.setBorderBottom(BorderStyle.DASHED);
            }
            else if (cellInfo.borderBottom.equalsIgnoreCase("dotted")) {
                cellStyle.setBorderBottom(BorderStyle.DOTTED);
            }
        }

        if (cellInfo.borderRight.length() > 0) {
            if (cellInfo.borderRight.equalsIgnoreCase("thin")) {
                cellStyle.setBorderRight(BorderStyle.THIN);
            }
            else if (cellInfo.borderRight.equalsIgnoreCase("medium")) {
                cellStyle.setBorderRight(BorderStyle.MEDIUM);
            }
            else if (cellInfo.borderRight.equalsIgnoreCase("dashed")) {
                cellStyle.setBorderRight(BorderStyle.DASHED);
            }
            else if (cellInfo.borderRight.equalsIgnoreCase("dotted")) {
                cellStyle.setBorderRight(BorderStyle.DOTTED);
            }
        }

        cellStyle.setWrapText(cellInfo.isCellWrap);

        if (cellInfo.backColorRGB.length > 0) {
            XSSFColor fillColor = new XSSFColor(new java.awt.Color(cellInfo.backColorRGB[0],
                    cellInfo.backColorRGB[1], cellInfo.backColorRGB[2]));
            cellStyle.setFillForegroundColor(fillColor);
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }

        return cellStyle;
    }


}
