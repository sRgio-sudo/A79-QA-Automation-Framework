package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URI;

public class DriverFactory {
    private static WebDriver driver;


    public static void initDriver() throws Exception {          //entrance point to initialise WebDriver
        driver = createDriver();
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            System.out.println("Closing driver...");
            driver.quit();
            driver = null;
        }
    }

    public static WebDriver createDriver() throws Exception {
        String executionType = ConfigReader.getProperty("execution.type");
        String browser = ConfigReader.getProperty("browser");

        if (executionType.equalsIgnoreCase("remote")) {
            return createRemoteDriver(browser);
        } else {
            return createLocalDriver(browser);
        }
    }

    private static WebDriver createLocalDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver(getFirefoxOptions());

            case "edge":
                WebDriverManager.edgedriver().setup();
                return new EdgeDriver(getEdgeOptions());

            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver(getChromeOptions());
        }
    }

    private static WebDriver createRemoteDriver(String browser) throws Exception {
        String gridUrl = ConfigReader.getProperty("grid.url");

        switch (browser.toLowerCase()) {

            case "firefox":
                return new RemoteWebDriver(
                        URI.create(gridUrl).toURL(),
                        getFirefoxOptions());
            case "edge":
                return new RemoteWebDriver(
                        URI.create(gridUrl).toURL(),
                        getEdgeOptions());

            case "chrome":
            default:
                return new RemoteWebDriver(
                        URI.create(gridUrl).toURL(),
                        getChromeOptions());
        }
    }

    private static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addArguments("--start-maximized");
        return firefoxOptions;
    }

    private static EdgeOptions getEdgeOptions() {
        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.addArguments("--start-maximized");
        return edgeOptions;
    }

    private static ChromeOptions getChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("--disable-infobars");
        return chromeOptions;
    }
}
