package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.ConfigReader;

public class LoginPage extends BasePage {
    private String url = ConfigReader.getProperty("base.url");
    @FindBy(css = "input[type='email']")
    private WebElement emailField;
    @FindBy(css = "input[type='password']")
    private WebElement passwordField;
    @FindBy(css = "button[type='submit']")
    private WebElement submitButton;
    @FindBy(css = "a[href='registration']")
    private WebElement registrationButton;

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
        emailField.sendKeys(email);
        return this;
    }

    public LoginPage providePassword(String password) {
        passwordField.sendKeys(password);
        return this;
    }

    public void clickSubmitButton() {
        submitButton.click();
    }

    public void clickRegistrationButton() {
        registrationButton.click();
    }

    public HomePage login(String email, String password) {
        provideEmail(email);
        providePassword(password);
        clickSubmitButton();

        return new HomePage(driver);
    }

    public boolean isLoginPageDisplayed() {
        return driver.getCurrentUrl()
                .equals(ConfigReader.getProperty("base.url"));
    }
}
