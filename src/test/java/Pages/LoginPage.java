package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private String url = "https://qa.koel.app/";
    private By emailField = By.cssSelector("input[type='email']");
    private By passwordField = By.cssSelector("input[type='password']");
    private By submitButton = By.cssSelector("button[type='submit']");
    private By registrationButton = By.cssSelector("a[href='registration']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage openPage() {
        driver.get(url);
        return this;
    }

    public void provideEmail(String email) {
        findElement(emailField).sendKeys(email);
    }

    public void providePassword(String password) {
        findElement(passwordField).sendKeys(password);
    }

    public void clickSubmitButton() {
        findElement(submitButton).click();
    }

    public void clickRegistrationButton() {
        findElement(registrationButton).click();
    }

    public HomePage login(String email, String password) {
        findElement(emailField).sendKeys(email);
        findElement(passwordField).sendKeys(password);
        findElement(submitButton).click();
        return new HomePage(driver);
    }

}
