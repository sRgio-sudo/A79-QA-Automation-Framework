package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

public class BaseTest {
    private static final ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();
    public WebDriverWait wait;
    protected Actions actions;

    public static WebDriver getDriver() {
        return threadDriver.get();
    }

    @BeforeMethod
    public void setUpBrowser() throws MalformedURLException {
        String browserProp = System.getProperty("browser");
        if (browserProp == null) {
            browserProp = "chrome";
        }
        threadDriver.set(pickBrowser(browserProp));
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        actions = new Actions(getDriver());
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        navigateToPage();
    }

    public void navigateToPage() {
        String url = ConfigReader.getProperty("base.url");
        getDriver().get(url);
    }

    @AfterMethod
    public void tearDown() {
        threadDriver.get().quit();
        threadDriver.remove();
    }

    public WebDriver pickBrowser(String browser) throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        String gridURL = ConfigReader.getProperty("grid.url");
        switch (browser) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions ffOptions = new FirefoxOptions();
                ffOptions.addArguments("--width=1920");
                ffOptions.addArguments("--height=1080");
                ffOptions.addPreference("dom.webnotifications.enabled", false);
                return new FirefoxDriver(ffOptions);
            case "MicrosoftEdge":
                WebDriverManager.edgedriver().setup();
                return new EdgeDriver(addChromiumArguments(new EdgeOptions()));
            case "grid-edge":
                caps.setCapability("browserName", "MicrosoftEdge");
                EdgeOptions edgeOptions = addChromiumArguments(new EdgeOptions());
                edgeOptions.merge(caps);
                return new RemoteWebDriver(URI.create(gridURL).toURL(), edgeOptions);
            case "grid-firefox":
                caps.setCapability("browserName", "firefox");
                FirefoxOptions ffGridOptions = new FirefoxOptions();
                ffGridOptions.addArguments("--width=1920", "--height=1080");
                ffGridOptions.merge(caps);
                return new RemoteWebDriver(URI.create(gridURL).toURL(), ffGridOptions);
            case "grid-chrome":
                caps.setCapability("browserName", "chrome");
                ChromeOptions chromeOptions = addChromiumArguments(new ChromeOptions());
                chromeOptions.merge(caps);
                return new RemoteWebDriver(URI.create(gridURL).toURL(), chromeOptions);
            case "cloud":
                return lambdaTest();
            default:
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver(addChromiumArguments(new ChromeOptions()));
        }
    }

    public WebDriver lambdaTest() throws MalformedURLException {
        String hubURL = "hub.lambdatest.com/wd/hub";
        String username = ConfigReader.getProperty("lambda.user");
        String password = ConfigReader.getProperty("lambda.key");
        ChromeOptions browserOptions = new ChromeOptions();
        browserOptions.setPlatformName("Windows 10");
        browserOptions.setBrowserVersion("latest");
        HashMap<String, Object> ltOptions = new HashMap<>();
        ltOptions.put("username", username);
        ltOptions.put("accessKey", password);
        ltOptions.put("project", "KoelApp");
        ltOptions.put("build", "Homework-25");
        ltOptions.put("name", this.getClass().getSimpleName());
        ltOptions.put("w3c", true);
        ltOptions.put("video", true);
        ltOptions.put("visual", true);

        browserOptions.setCapability("LT:Options", ltOptions);
        String fullURL = "https://"+username+":"+password+"@"+hubURL;
        return new RemoteWebDriver(new URL(fullURL), browserOptions);
    }
    private <T extends ChromiumOptions<?>> T addChromiumArguments(T options) {
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");
        return options;
    }
}


