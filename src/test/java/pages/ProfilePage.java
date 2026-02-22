package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProfilePage extends BasePage {
    //Selectors put here:
    private By saveButton = By.xpath("//button[@class='btn-submit']");
    private By newNameField = By.cssSelector("#inputProfileName");
    private By currentPasswordField = By.cssSelector("#inputProfileCurrentPassword");
    private By profileNameField = By.cssSelector(".view-profile .name");
    private By catThemeSetter = By.cssSelector("div [data-testid='theme-card-cat']");
    private By classicThemeSetter = By.cssSelector("div [data-testid='theme-card-classic']");

    public ProfilePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    //Methods put here:

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
        return this;
    }
    public String getProfileName() {
     return wait.until(ExpectedConditions
             .visibilityOfElementLocated(profileNameField))
             .getText();
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
        WebElement themeCard = wait.until(ExpectedConditions.visibilityOfElementLocated(classicThemeSetter));
        String classValue = themeCard.getAttribute("class");
        return classValue.contains("selected");
    }
}
