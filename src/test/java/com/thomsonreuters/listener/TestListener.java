package com.thomsonreuters.listener;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.thomsonreuters.utils.ExtentManager;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Set;


public class TestListener implements ITestListener {

    public static ExtentReports extentReports ;
    public static ExtentTest extentTest;

    /**
     * Invoked each time before a test will be invoked. The <code>ITestResult</code> is only partially
     * filled with the references to class, method, start millis and status.
     *
     * @param result the partially filled <code>ITestResult</code>
     * @see ITestResult#STARTED
     */
    public void onTestStart(ITestResult result) {
        // not implemented
//        Object[] params= result.getParameters();
//        String testName= params[0]+" -> "+params[1];
//        result.setTestName(testName);

        String testName= result.getTestContext().getAttribute("testName").toString();
        extentTest=extentReports.startTest(testName);

        extentTest.log(LogStatus.INFO, testName+ " started..");
    }

    /**
     * Invoked each time a test succeeds.
     *
     * @param result <code>ITestResult</code> containing information about the run test
     * @see ITestResult#SUCCESS
     */
    public void onTestSuccess(ITestResult result) {
        // not implemented
        extentTest.log(LogStatus.INFO, result.getName()+" passed..");
        System.out.println("The name of the testcase passed is: "+result.getName());
    }

    /**
     * Invoked each time a test fails.
     *
     * @param result <code>ITestResult</code> containing information about the run test
     * @see ITestResult#FAILURE
     */
    public void onTestFailure(ITestResult result) {
        // not implemented
        extentTest.log(LogStatus.FAIL, result.getThrowable());

    }

    /**
     * Invoked each time a test is skipped.
     *
     * @param result <code>ITestResult</code> containing information about the run test
     * @see ITestResult#SKIP
     */
    public  void onTestSkipped(ITestResult result) {
        // not implemented
        extentTest.log(LogStatus.SKIP, result.getThrowable());
    }

    /**
     * Invoked each time a method fails but has been annotated with successPercentage and this failure
     * still keeps it within the success percentage requested.
     *
     * @param result <code>ITestResult</code> containing information about the run test
     * @see ITestResult#SUCCESS_PERCENTAGE_FAILURE
     */
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // not implemented

    }

    /**
     * Invoked each time a test fails due to a timeout.
     *
     * @param result <code>ITestResult</code> containing information about the run test
     */
    public void onTestFailedWithTimeout(ITestResult result) {
        extentTest.log(LogStatus.FAIL, result.getThrowable());
        System.out.println("The name of the testcase failed with timeout is: "+result.getName());
    }

    /**
     * Invoked before running all the test methods belonging to the classes inside the &lt;test&gt; tag
     * and calling all their Configuration methods.
     */
    public void onStart(ITestContext context) {
        // not implemented
        extentTest.log(LogStatus.INFO, "Initiating an extent test report instance");
        extentReports= ExtentManager.getReporter();

    }

    /**
     * Invoked after all the test methods belonging to the classes inside the &lt;test&gt; tag have run
     * and all their Configuration methods have been called.
     */
    public void onFinish(ITestContext context) {
        // not implemented
        extentTest.log(LogStatus.INFO, "Creating an extent test report");
        extentReports.endTest(extentTest);
        extentReports.flush();
    }
}
