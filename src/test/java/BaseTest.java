import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.time.Duration;
import java.util.UUID;

public class BaseTest {
    public WebDriver driver;
    public String url = "https://qa.koel.app/";
    protected String validEmail = "sergei.trofimov@testpro.io";
    protected String validPassword = "uIIgWoYu";
    protected

    @BeforeSuite
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    public void launchBrowser() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
//        options.addArguments("--headless=new");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterMethod
    public void closeBrowser() {
        driver.quit();
    }

    protected void clickSubmit() {
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();
    }

    protected void providePassword(String password) {
        WebElement passwordField = driver.findElement(By.cssSelector("input[type='password']"));
        passwordField.click();
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    protected void provideEmail(String email) {
        WebElement emailField = driver.findElement(By.cssSelector("input[type='email']"));
        emailField.click();
        emailField.clear();
        emailField.sendKeys(email);
    }

    protected void clickOnAvatarIcon() {
        WebElement avatarIcon = driver.findElement(By.xpath("//a[@data-testid='view-profile-link']"));
        avatarIcon.click();

    }

    protected void navigatingToPage() {
        driver.get(url);
    }

    public String generateRandomName() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 5);

    }

    public void deleteAddedSong() {
        driver.findElement(By.xpath("//section[@id='playlists']//a[contains(text(), 'Playlist2')]")).click();
        WebElement addedSong = driver.findElement(By.xpath("//section[@id='playlistWrapper']//table[@class='items']//td[contains(text(), 'HoliznaCC0 - Way Of The Samurai')]"));
        addedSong.click();
        Actions action = new Actions(driver);
        action.sendKeys(Keys.DELETE).perform();
    }

    protected void selectNextSong() {
        WebElement nextSong = driver.findElement(By.cssSelector("[data-testid='play-next-btn']"));
        nextSong.click();
    }

    protected void clickPlayButton() {
        WebElement playButton = driver.findElement(By.cssSelector(".album-thumb-wrapper [role='button']"));
        playButton.click();
    }
}