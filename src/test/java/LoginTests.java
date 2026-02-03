import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {

    @Test
    public void loginEmptyEmailPassword() {
        navigatingToPage();
        provideEmail("");
        providePassword("uIIgWoYu");
        clickSubmit();
        Assert.assertEquals(driver.getCurrentUrl(), url);
    }

    @Test
    public void loginValidEmailPassord() {
        navigatingToPage();
        provideEmail("sergei.trofimov@testpro.io");
        providePassword("uIIgWoYu");
        clickSubmit();
        WebElement avatarIcon = driver.findElement(By.xpath("//img[@class='avatar']"));
        Assert.assertTrue(avatarIcon.isDisplayed());
    }

    @Test
    public void loginInvalidEmail() {
        navigatingToPage();
        provideEmail("@testpro.io");
        providePassword("uIIgWoYu");
        clickSubmit();
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, url);
    }

    @Test
    public void loginInvalidPassord() {
        navigatingToPage();
        provideEmail("sergei.trofimov@testpro.io");
        providePassword("uIIgWoYu");
        clickSubmit();
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, url);
    }
}
