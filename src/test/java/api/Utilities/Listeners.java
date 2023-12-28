package api.Utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;

public class Listeners extends BaseTest implements ITestListener {
    ExtentReports extent;
    private final Logger log = LogManager.getLogger(Listeners.class.getName());

    public void loadReportObject() throws IOException {
        if (extent == null) {
            extent = ExtentReport.getReportObject();
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        try {
            TEST_NAME = context.getName();
            this.log.info("Finished Running Test Name: " + TEST_NAME);
            extent.flush();
            this.log.info("Successfully Generate Report Test and Located in: " + ExtentReport.getPathExtentReport());
        } catch (Exception e) {
            this.log.error("Failed Generate Report Test: " + e.getMessage());
        }
    }

    @Override
    public void onStart(ITestContext context) {
        TEST_NAME = context.getName();
        this.log.info("Started Running Test Name: " + TEST_NAME);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        Logger log = LogManager.getLogger(result.getMethod().getTestClass().getName() + "." + methodName);

        log.error("Test Failed: " + result.getThrowable());
        extentTest.fail(result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        Logger log = LogManager.getLogger(result.getMethod().getTestClass().getName() + "." + methodName);

        log.error("Test Skip: " + result.getThrowable());
        extentTest.log(Status.SKIP, MarkupHelper.createLabel("Test Skip", ExtentColor.ORANGE));
    }

    @Override
    public void onTestStart(ITestResult result) {
        try {
            loadReportObject();
            String[] arrClassName = result.getMethod().getTestClass().getName().split("\\.");
            String className = arrClassName[arrClassName.length - 1];
            String methodName = result.getMethod().getMethodName();
            String testCaseName = className + " - " + methodName;

            extentTest = extent.createTest(testCaseName);
            extentTest.assignCategory(className);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        Logger log = LogManager.getLogger(result.getMethod().getTestClass().getName() + "." + methodName);

        log.info("Test Successfully Completed and Passed");
        extentTest.log(Status.PASS, MarkupHelper.createLabel("Test Passed", ExtentColor.GREEN));
    }

}
