package pages;

import models.User;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

import java.time.Duration;

public class LoginPage extends BasePage {
    private String url = ConfigReader.getProperty("base.url");
    private By mainLogo = By.cssSelector("div.logo");
    private By emailField = By.cssSelector("input[type='email']");
    private By passwordField = By.cssSelector("input[type='password']");
    private By submitButton = By.cssSelector("button[type='submit']");
    @FindBy(css = "a[href='registration']")
    private WebElement registrationButton;
    @FindBy(css = "p #button")
    private WebElement submitRegistrationButton;
    private By infoMessage = By.cssSelector("div .messages");
    private By errorMessage = By.cssSelector("div.errors");
    private By redFrame = By.cssSelector("form.error");

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public LoginPage openPage() {
        driver.get(url);
        return this;
    }

    public HomePage loginAsValidUser() {
        return login(ConfigReader.getProperty("user.email"),
                ConfigReader.getProperty("user.password"));
    }

    public LoginPage provideEmail(String email) {
        forceClear(emailField);
       findElement(emailField)
                .sendKeys(email);
        return this;
    }

    public LoginPage providePassword(String password) {
        forceClear(passwordField);
        findElement(passwordField)
                .sendKeys(password);
        return this;
    }

    public void clickSubmitButton() {
        waitClickable(submitButton).click();
    }

    public void clickRegistrationButton() {
        registrationButton.click();
    }

    public void clickSubmitRegistrationButton() {
        submitRegistrationButton.click();
    }

    public HomePage loginAs(User user) {
        return login(user.getEmail(), user.getPassword());
    }

    public HomePage login(String email, String password) {
        forceClear(emailField);
        provideEmail(email);
        forceClear(passwordField);
        providePassword(password);
        clickSubmitButton();

        return new HomePage(driver);
    }

    public boolean isLoginPageDisplayed() {
        return driver.getCurrentUrl()
                .equals(ConfigReader.getProperty("base.url"));
    }

    public String getNotificationMessageText() {
        return waitVisibility(infoMessage).getText();
    }

    public String getErrorMessageText() {
        return waitVisibility(errorMessage).getText();
    }

    public String getQuickNotificationText() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            return shortWait.until(ExpectedConditions.visibilityOfElementLocated(infoMessage)).getText();
        } catch (TimeoutException e) {
            return "";
        }
    }

    public LoginPage disableHtml5Validation() {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].setAttribute('novalidate', 'novalidate')",
                driver.findElement(By.cssSelector("form"))
        );
        return this;
    }

    public boolean isRedFramePresent() {
        try {
            return wait.until(ExpectedConditions.attributeContains(redFrame, "class", "error"));
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLogoDisplayed() {
        WebElement logo = waitVisibility(mainLogo);
        return logo.isDisplayed();
    }
}
