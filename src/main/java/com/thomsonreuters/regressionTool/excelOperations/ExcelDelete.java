package com.thomsonreuters.regressionTool.excelOperations;

import java.io.File;


public class ExcelDelete {
    public static void deleteExcelWorkbook() {

        for (File file : new File(System.getProperty("user.dir") + "\\src\\main\\resources\\excelSheets").listFiles()) {
            //file.delete();
        }
    }
}
