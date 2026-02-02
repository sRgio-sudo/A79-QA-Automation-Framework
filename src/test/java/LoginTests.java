import locators.KoelSelectors;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import static locators.KoelSelectors.submitButton;

public class LoginTests extends BaseTest {
    @Test
    public void loginEmptyEmailPassword() {

        driver.get(url);
        Assert.assertEquals(driver.getCurrentUrl(), url);
    }

    @Test
    public void registrationNavigation() {
        driver.get(url);

        driver.findElement(KoelSelectors.registrationLink).click(); // can also use var2
        WebElement buttonSubmit = driver.findElement(submitButton);
        String buttonText = buttonSubmit.getAttribute("value");
        Assert.assertEquals(buttonText, "Submit");
        }
}
//WebElement emailField = driver.findElement(By.cssSelector("input[type='email']"));
//        emailField.click();
//        emailField.clear();
//        emailField.sendKeys("sergei.trofimov@testpro.io");
//WebElement passwordField = driver.findElement(By.cssSelector("input[type='password']"));
//        passwordField.click();
//        passwordField.clear();
//        passwordField.sendKeys("uIIgWoYu");
//WebElement submit = driver.findElement(By.cssSelector("button[type='submit']"));
//        submit.click();


