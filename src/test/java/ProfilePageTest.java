import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProfilePageTest extends BaseTest {
    @Test
    public void updateProfileName() throws InterruptedException {
        navigatingToPage();
        provideEmail("sergei.trofimov@testpro.io");
        providePassword("uIIgWoYu");
        clickSubmit();
        Thread.sleep(3000);
        clickOnAvatarIcon();
        currentPass("uIIgWoYu");
        String newName = generateRandomName();
        provideNewProfileName(newName);
        Thread.sleep(5000);
        clickSaveButton();
        Thread.sleep(3000);
        WebElement actualProfileName = driver.findElement(By.xpath("//span[@class='name']"));
        Assert.assertEquals(actualProfileName.getText(), newName);
    }

    private void clickSaveButton() {
        WebElement saveButtonClick = driver.findElement(By.xpath("//button[@class='btn-submit']"));
        saveButtonClick.click();
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
