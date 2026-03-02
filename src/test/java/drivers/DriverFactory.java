package drivers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.ConfigReader;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;

public class DriverFactory {
    public static WebDriver createDriver() throws Exception {
        String executionType = ConfigReader.getProperty("execution.type");
        String browser = ConfigReader.getProperty("browser");
        switch (executionType.toLowerCase()) {
            case "grid":
                return createRemoteDriver(browser);
            case "cloud":
                return createLambdaDriver();
            case "local":
            default:
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

    public static WebDriver createLambdaDriver() throws MalformedURLException {
        String hubURL = "hub.lambdatest.com/wd/hub";
        String username = ConfigReader.getProperty("lambda.user");
        String password = ConfigReader.getProperty("lambda.key");
        String browser = ConfigReader.getProperty("browser");
        MutableCapabilities browserOptions;
        switch (browser.toLowerCase()) {
            case "firefox":
                browserOptions = getFirefoxOptions();
                break;
            case "edge":
                browserOptions = getEdgeOptions();
                break;
            case "chrome":
            default:
                browserOptions = getChromeOptions();
        }

        browserOptions.setCapability("platformName", "Windows 10");
        browserOptions.setCapability("browserVersion", "latest");
        browserOptions.setCapability("browserName", browser);
        HashMap<String, Object> ltOptions = new HashMap<>();
        ltOptions.put("username", username);
        ltOptions.put("accessKey", password);
        ltOptions.put("project", "KoelApp");
        ltOptions.put("build", "MyLambdaTest");
        ltOptions.put("name", "LambdaTestRun");
        ltOptions.put("w3c", true);
        ltOptions.put("video", true);
        ltOptions.put("visual", true);

        browserOptions.setCapability("LT:Options", ltOptions);
        return new RemoteWebDriver(new URL("https://hub.lambdatest.com/wd/hub"), browserOptions);
    }
}

