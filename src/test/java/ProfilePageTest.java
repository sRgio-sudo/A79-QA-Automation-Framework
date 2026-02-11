import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProfilePageTest extends BaseTest {
    @Test
    public void updateProfileName() {
        navigatingToPage();
        provideEmail(validEmail);
        providePassword(validPassword);
        clickSubmit();

        clickOnAvatarIcon();
        currentPass(validPassword);
        String newName = generateRandomName();
        provideNewProfileName(newName);
        clickSaveButton();
        checkSuccess();

        WebElement actualProfileName = driver.findElement(By.xpath("//span[@class='name']"));
        Assert.assertEquals(actualProfileName.getText(), newName);
    }

    private void clickSaveButton() {
        wait.until(ExpectedConditions.elementToBeClickable
                (By.xpath("//button[@class='btn-submit']"))).click();

    }

    private void provideNewProfileName(String newName) {
        WebElement newProfileNameField = driver.findElement(By.cssSelector("#inputProfileName"));
        newProfileNameField.clear();
        newProfileNameField.sendKeys(newName);
    }

    private void currentPass(String pass) {
        WebElement currentPassField = driver.findElement(By.cssSelector("#inputProfileCurrentPassword"));
        currentPassField.clear();
        currentPassField.sendKeys(pass);
    }

}
