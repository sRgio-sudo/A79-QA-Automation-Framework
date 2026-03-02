package drivers;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    @BeforeMethod
    public void setUpBrowser() throws Exception {
        WebDriver driver = DriverFactory.createDriver();
        DriverManager.setDriver(driver);

    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }
}


