package sketch.symbollist;

import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.*;
import sketch.common.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class XlsSymbolSheet {
    private XSSFWorkbook workBook = null;
    private XSSFSheet sheetInfo = null;

    public ArrayList<SheetPosValue> colWidthList = new ArrayList<SheetPosValue>();
    public ArrayList<SheetPosValue> lableList = new ArrayList<SheetPosValue>();

    public XlsSymbolSheet(XSSFWorkbook _workBook, XSSFSheet _xssfSheet) {
        this.workBook = _workBook;
        this.sheetInfo = _xssfSheet;

        this.colWidthList.add(new SheetPosValue("A", "4.0"));
        this.colWidthList.add(new SheetPosValue("B", "4.0"));
        this.colWidthList.add(new SheetPosValue("C", "4.0"));
        this.colWidthList.add(new SheetPosValue("D", "40.0"));
        this.colWidthList.add(new SheetPosValue("E", "40.0"));
        this.colWidthList.add(new SheetPosValue("F", "40.0"));
        this.colWidthList.add(new SheetPosValue("G", "60.0"));
        this.colWidthList.add(new SheetPosValue("H", "10.0"));
        this.colWidthList.add(new SheetPosValue("I", "10.0"));

        CellSizeSet cellSizeSet = new CellSizeSet(this.workBook, this.sheetInfo);
        cellSizeSet.doSetColWidthList(this.colWidthList);

        this.lableList.add(new SheetPosValue("A1", "PageID"));
        this.lableList.add(new SheetPosValue("B1", "ArtboardID"));
        this.lableList.add(new SheetPosValue("C1", "SymbolID"));
        this.lableList.add(new SheetPosValue("D1", "Page名"));
        this.lableList.add(new SheetPosValue("E1", "Artboard名"));
        this.lableList.add(new SheetPosValue("F1", "Symbol名"));
        this.lableList.add(new SheetPosValue("G1", "Symbol图片"));
        this.lableList.add(new SheetPosValue("H1", "是否需要展开"));
        this.lableList.add(new SheetPosValue("I1", "展开层数"));

        CellValueSet cellValueSet = new CellValueSet(this.workBook, this.sheetInfo);
        cellValueSet.doCellValueList(this.lableList);
    }

    public int[] calCellInfoByPixel(int[] startCell, int[] cellOffset, double[] pxInfo, XSSFSheet sheetInfo) {
        int[] retPixelInfo = new int[] {0,0,0,0};

        int iStartCol = startCell[0];
        int iStartColOffset = cellOffset[0];
        int iStartColRest = (int)(sheetInfo.getColumnWidthInPixels(iStartCol)*0.859649 - iStartColOffset);

        //如果第一个column的值就够了，则返回
        if (iStartColRest >= pxInfo[0]) {
            retPixelInfo[0] = iStartCol;
            retPixelInfo[2] = (int)(iStartColOffset+pxInfo[0]);
        }
        else {
            double restPxInfo = pxInfo[0] - iStartColRest;
            int iFindCol = iStartCol+1;
            while(true) {
                double nowColWidth = sheetInfo.getColumnWidthInPixels(iFindCol)*0.859649;
                if (nowColWidth >= restPxInfo){
                    retPixelInfo[0] = iFindCol;
                    retPixelInfo[2] = (int)(restPxInfo);
                    break;
                }
                restPxInfo = restPxInfo-nowColWidth;
                iFindCol += 1;
            }
        }

        int iStartRow = startCell[1];
        int iStartRowOffset = cellOffset[1];
        double iStartRowRest = sheetInfo.getRow(iStartRow).getHeightInPoints()-iStartRowOffset;
        if (iStartRowRest >= pxInfo[1]) {
            retPixelInfo[1] = iStartRow;
            retPixelInfo[3] = (int)(iStartRowOffset+pxInfo[1]);
        }
        else {
            double restPxInfo = pxInfo[1] - iStartRowRest;
            int iFindRow = iStartRow + 1;
            while(true) {
                double nowRowHeight = sheetInfo.getRow(iFindRow).getHeightInPoints();
                if (nowRowHeight >= restPxInfo){
                    retPixelInfo[1] = iFindRow;
                    retPixelInfo[3] = (int)(restPxInfo);
                    break;
                }
                restPxInfo = restPxInfo-nowRowHeight;
                iFindRow += 1;
            }
        }

        return retPixelInfo;
    }

    public void genSymbolData(ArrayList<SymbolData> symbolDataList) throws Exception {
        CellSizeSet cellSizeSet = new CellSizeSet(this.workBook, this.sheetInfo);
        CellStyleSet cellStyleSet = new CellStyleSet(this.workBook, this.sheetInfo);
        CellValueSet cellValueSet = new CellValueSet(this.workBook, this.sheetInfo);

        CellStyleInfo xlsSymbolSheetStyle = new CellStyleInfo(
                "A1:I1",
                "Meiryo UI", 10,false, false,
                "left","center","thin","thin","thin",
                "thin",true, new int[]{});
        cellStyleSet.doOneCellStyle(xlsSymbolSheetStyle);

        if (symbolDataList.size() > 0) {
            SheetPosValue rowHeightInfo = new SheetPosValue(
                    String.format("2:%d", 1+symbolDataList.size()), String.format("%f", 95.7142857f));
            cellSizeSet.doSetOneRowHeight(rowHeightInfo);

            XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(this.sheetInfo);
            String[] expandFlagOpt = {"Y","N"};
            XSSFDataValidationConstraint expandFlagConstraint=(XSSFDataValidationConstraint)dvHelper.createExplicitListConstraint(expandFlagOpt);
            CellRangeAddressList expandFlagRegion = new CellRangeAddressList(1, 1+symbolDataList.size(),
                    CellReference.convertColStringToIndex("H"), CellReference.convertColStringToIndex("H"));
            XSSFDataValidation expandFlagValidation = (XSSFDataValidation)dvHelper.createValidation(expandFlagConstraint, expandFlagRegion);
            this.sheetInfo.addValidationData(expandFlagValidation);
        }

        int symbolStartRow = 2;

        XSSFDrawing drawing = this.sheetInfo.createDrawingPatriarch();

        for (int iIndex = 0; iIndex < symbolDataList.size(); iIndex++) {
            cellStyleSet.doOneCellStyle(new CellStyleInfo(
                    String.format("A%d:I%d",symbolStartRow+iIndex,symbolStartRow+iIndex),
                    "Meiryo UI", 10,false, false,
                    "left","center","thin","thin","thin",
                    "thin",true, new int[]{}));

            SymbolData oneSymbolData = symbolDataList.get(iIndex);

            cellValueSet.doOneCellValue(new SheetPosValue(String.format("A%d", symbolStartRow+iIndex),
                    oneSymbolData.pageUUID));
            cellValueSet.doOneCellValue(new SheetPosValue(String.format("B%d", symbolStartRow+iIndex),
                    oneSymbolData.artboardUUID));
            cellValueSet.doOneCellValue(new SheetPosValue(String.format("C%d", symbolStartRow+iIndex),
                    oneSymbolData.symbolUUID));
            cellValueSet.doOneCellValue(new SheetPosValue(String.format("D%d", symbolStartRow+iIndex),
                    oneSymbolData.pageName));
            cellValueSet.doOneCellValue(new SheetPosValue(String.format("E%d", symbolStartRow+iIndex),
                    oneSymbolData.artboardName));
            cellValueSet.doOneCellValue(new SheetPosValue(String.format("F%d", symbolStartRow+iIndex),
                    oneSymbolData.symbolName));
            cellValueSet.doOneCellValue(new SheetPosValue(String.format("H%d", symbolStartRow+iIndex),
                    oneSymbolData.symbolExpandFlag));
            cellValueSet.doOneCellValue(new SheetPosValue(String.format("I%d", symbolStartRow+iIndex),
                    oneSymbolData.symbolExpandLevel));

            if (oneSymbolData.symbolImage.length() == 0) {
                continue;
            }

            String symbolImageVal = oneSymbolData.symbolImage;
            int cellMaxHeight = (int)(this.sheetInfo.getRow(symbolStartRow+iIndex-1).getHeightInPoints());
            double imgMaxHeight = cellMaxHeight * 0.95;
            double imgRatio = 1.0;
            File imgFile = new File(symbolImageVal);
            if (imgFile.exists() == true) {
                BufferedImage bufferImg = ImageIO.read(new FileInputStream(imgFile.getPath()));
                ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
                String imgfileName = imgFile.getName();
                ImageIO.write(bufferImg, imgfileName.substring(imgfileName.lastIndexOf(".") + 1), byteArrayOut);

                if (bufferImg.getHeight() > imgMaxHeight) {
                    imgRatio = imgMaxHeight / bufferImg.getHeight();
                }
                if (bufferImg.getWidth() * imgRatio > 240) {
                    imgRatio = 240.0 / bufferImg.getWidth();
                }

                double scaleImgWidth = bufferImg.getWidth() * imgRatio;
                double scaleImgHeight = bufferImg.getHeight() * imgRatio;
                double centerOffsetX = 0;
                double centerOffsetY = ((cellMaxHeight + 2.0) - scaleImgHeight) / 2.0;

                int[] startCell = new int[]{CellReference.convertColStringToIndex("G"), symbolStartRow + iIndex - 1};
                int[] cellOffset = new int[]{10, 0};

                int[] startImgPos = calCellInfoByPixel(startCell, cellOffset, new double[]{centerOffsetX, centerOffsetY}, this.sheetInfo);
                int[] endImgPos = calCellInfoByPixel(startCell, cellOffset,
                        new double[]{centerOffsetX + scaleImgWidth, centerOffsetY + scaleImgHeight}, this.sheetInfo);

                XSSFClientAnchor imageAnchor = new XSSFClientAnchor(
                        XSSFShape.EMU_PER_POINT * startImgPos[2],
                        XSSFShape.EMU_PER_POINT * startImgPos[3],
                        XSSFShape.EMU_PER_POINT * endImgPos[2],
                        XSSFShape.EMU_PER_POINT * endImgPos[3],
                        startImgPos[0], startImgPos[1], endImgPos[0], endImgPos[1]);

                int pictureIdx = workBook.addPicture(byteArrayOut.toByteArray(), workBook.PICTURE_TYPE_PNG);

                drawing.createPicture(imageAnchor, pictureIdx);
            }
        }

        this.sheetInfo.setColumnHidden(0, true);
        this.sheetInfo.setColumnHidden(1, true);
        this.sheetInfo.setColumnHidden(2, true);
    }
}
