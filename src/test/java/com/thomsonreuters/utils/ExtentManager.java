package com.thomsonreuters.utils;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {


    /*
     * to get the object of com.thomsonreuters.ExtentReports. Currently being saved in following location- "test-output/extent.html"
     */
    private static ExtentReports extent;

    private static String fileSeperator = System.getProperty("file.separator");

    public  static ExtentReports getReporter() {
        if (extent == null) {
            //Set HTML reporting file location
            String workingDir = System.getProperty("user.dir");
            extent = new ExtentReports(workingDir + fileSeperator + "com.thomsonreuters.ExtentReports" + fileSeperator + "ExtentReportResults.html", true);
        }
        return extent;
    }
}

