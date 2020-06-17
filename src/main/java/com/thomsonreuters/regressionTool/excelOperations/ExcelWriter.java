package com.thomsonreuters.regressionTool.excelOperations;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class ExcelWriter {
    String fileName;
    String sheetName;
    int rowNum = 1;
    Workbook workbook;
    Sheet sheet;
    List<String> columnNames;
    Row row;

    public ExcelWriter() {
        ExcelDelete.deleteExcelWorkbook();
    }

    public void createExcelFileAndSheet(String fileName, String sheetName, List<String> columnNames) throws IOException,
            InvalidFormatException {
        this.fileName = fileName;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet(sheetName);

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.BLACK.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < columnNames.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnNames.get(i));
            cell.setCellStyle(headerCellStyle);
        }

    }

    public void completeExcelFileAndSheet() throws IOException {

        FileOutputStream fileOut = new FileOutputStream(System.getProperty("user.dir") + "\\src\\main\\resources\\excelSheets\\" + fileName + ".xlsx");
        workbook.write(fileOut);
        fileOut.close();
    }

    public void createRow(int rowNo) {
        row = sheet.createRow(rowNo);
    }

    public void writeInExcelSheet(int cellNo, int cellValue) {
        row.createCell(cellNo).setCellValue(cellValue);
    }

    public void writeInExcelSheet(int cellNo, String cellValue) {
        row.createCell(cellNo).setCellValue(cellValue);
    }

    public void writeInExcelSheet(int cellNo, Double cellValue) {
        if (cellValue == null)
            cellValue = 0.0;
        row.createCell(cellNo).setCellValue(cellValue);
    }

    public void writeInExcelSheet(int cellNo, Long cellValue) {
        row.createCell(cellNo).setCellValue(cellValue);
    }


}