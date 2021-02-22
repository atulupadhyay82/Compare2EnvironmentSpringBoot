package com.thomsonreuters.listener;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {


    public void onFinish(ITestContext Result)
    {

    }


    public void onStart(ITestContext Result)
    {

    }


    public void onTestFailedButWithinSuccessPercentage(ITestResult Result)
    {

    }

    /**
     * Invoked each time a test fails due to a timeout.
     *
     * @param result <code>ITestResult</code> containing information about the run test
     */

    public void onTestFailedWithTimeout(ITestResult result) {
        System.out.println("The name of the testcase failed with timeout is :"+result.getName());
    }
    // When Test case get failed, this method is called.

    public void onTestFailure(ITestResult Result)
    {
        System.out.println("The name of the testcase failed is :"+Result.getName());
    }

    // When Test case get Skipped, this method is called.

    public void onTestSkipped(ITestResult Result)
    {
        System.out.println("The name of the testcase Skipped is :"+Result.getName());
    }

    // When Test case get Started, this method is called.

    public void onTestStart(ITestResult Result)
    {
        System.out.println(Result.getName()+" test case started");
    }

    // When Test case get passed, this method is called.
    public void onTestSuccess(ITestResult Result)
    {
        System.out.println("The name of the testcase passed is :"+Result.getName());
    }
}
