import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {
@Test (dataProvider = "IncorrectLoginData", dataProviderClass = TestDataProviders.class)
public void loginWithIncorrectData(String email, String password) {
    navigatingToPage();
    provideEmail(email);
    providePassword(password);
    clickSubmit();
    Assert.assertEquals(driver.getCurrentUrl(), url);
}
    @Test
    public void loginValidData() {
        navigatingToPage();
        provideEmail(validEmail);
        providePassword(validPassword);
        clickSubmit();
        WebElement avatarIcon = driver.findElement(By.xpath("//img[@class='avatar']"));
        Assert.assertTrue(avatarIcon.isDisplayed());
    }
}
