package api.Utilities;

import api.Helpers.TimeHelper;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.IOException;

public class ExtentReport extends BaseTest {
    private static String pathExtentReport;
    private final static String ROOT_PATH = System.getProperty("user.dir");

    public static ExtentReports getReportObject() throws IOException {
        String dateTime = TimeHelper.getDateTimeNow();
        String dateNow = TimeHelper.getDateNow();
        String reportName = "Report_" + TEST_NAME.replace(" ", "_") + "_" + dateTime + ".html";
        pathExtentReport = ROOT_PATH + "/reports/html/" + dateNow + "/" + reportName;
        ExtentSparkReporter reporter = new ExtentSparkReporter(pathExtentReport);
        reporter.config().setReportName("Example - " + TEST_NAME);
        reporter.config().setDocumentTitle("Test Results | Example - " + TEST_NAME);

        ExtentReports extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Tester", "Ferdy Ciputra");
        extent.setSystemInfo("Base Url", BASE_URL);
        extent.setSystemInfo("Operating System", System.getProperty("os.name"));
        extent.setSystemInfo("Operating System Version", System.getProperty("os.version"));

        PropertiesReader reader = new PropertiesReader();
        extent.setSystemInfo("Rest Assured Version", reader.getProperty("rest-assured.version"));

        return extent;
    }

    public static String getPathExtentReport() {
        return pathExtentReport;
    }
}
