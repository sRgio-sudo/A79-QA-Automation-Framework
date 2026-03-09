package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class ProfilePage extends BasePage {
    private By saveButton = By.xpath("//button[@class='btn-submit']");
    private By newNameField = By.cssSelector("#inputProfileName");
    private By currentPasswordField = By.cssSelector("#inputProfileCurrentPassword");
    private By profileNameField = By.cssSelector(".view-profile .name");
    private By catThemeSetter = By.cssSelector("div [data-testid='theme-card-cat']");
    private By classicThemeSetter = By.cssSelector("div [data-testid='theme-card-classic']");
    private By newPasswordField = By.cssSelector("#inputProfileNewPassword");
    private By errorMessage = By.xpath("//div[@class='error show']");
    private By successMessage = By.xpath("//div[@class='success show']");
    private By logOutButton = By.cssSelector("a[data-testid='btn-logout']");


    public ProfilePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public ProfilePage provideNewProfileName(String newName) {
        click(newNameField);
        typeAndSubmit(newNameField, newName);
        return this;
    }

    public ProfilePage currentPass(String pass) {
        click(currentPasswordField);
        clearAndType(currentPasswordField, pass);
        return this;
    }

    public ProfilePage clickSaveButton() {
        click(saveButton);
        successMessage();
        return this;
    }

    public String getProfileName() {
        return waitVisibility(profileNameField).getText();
    }

    public ProfilePage selectClassisTheme() {
        click(classicThemeSetter);
        return this;
    }

    public ProfilePage selectCatTheme() {
        click(catThemeSetter);
        return this;
    }

    public boolean isClassicThemeSelected() {
        WebElement themeCard = waitVisibility(classicThemeSetter);
        String classValue = themeCard.getAttribute("class");
        return classValue.contains("selected");
    }

    public ProfilePage setNewPassword(String newPassword) {
        clearAndType(newPasswordField, newPassword);
        click(saveButton);
        return this;
    }

    public String getSuccessMessage() {
        return waitVisibility(successMessage).getText();
    }

    public boolean isErrorMessageDisplayed() {
       try {
           return waitVisibility(errorMessage).isDisplayed();
       }catch (Exception e){
           return false;
       }
    }

    public LoginPage getLoginPage() {
        click(logOutButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='email']")));
        return new LoginPage(driver);
    }

    public void waitClearExitButton() {
        By successToast = By.cssSelector("div.success.show");
        List<WebElement> toasts = driver.findElements(successToast);
        if (!toasts.isEmpty()) {
            try {
                toasts.get(0).click();
            } catch (Exception ignored) {}
        }
        waitInvisibility(successToast);
    }
}
