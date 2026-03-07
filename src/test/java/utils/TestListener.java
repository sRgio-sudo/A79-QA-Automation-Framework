package utils;

import drivers.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = DriverManager.getDriver();
        if (driver != null) {
            String testName = result.getMethod().getMethodName() + "_FAILED";
            ScreenshotUtils.captureScreenshot(driver, testName);
        }
    }
    @Override
    public void onTestSuccess(ITestResult result) {
        WebDriver driver = DriverManager.getDriver();
        if (driver != null) {
            String testName = result.getMethod().getMethodName() + "_PASSED";
            ScreenshotUtils.captureScreenshot(driver, testName);
        }
    }

}