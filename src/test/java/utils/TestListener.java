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
            String testName = result.getMethod().getMethodName();
            ScreenshotUtils.captureScreenshot(driver, testName);
        }
    }
}