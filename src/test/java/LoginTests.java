import Pages.HomePage;
import Pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {
//@Test (dataProvider = "IncorrectLoginData", dataProviderClass = TestDataProviders.class)
//public void loginWithIncorrectData(String email, String password) {
//    navigatingToPage();
//    provideEmail(email);
//    providePassword(password);
//    clickSubmit();
//    Assert.assertEquals(driver.getCurrentUrl(), url);
//}
    @Test
    public void loginValidData() {
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = loginPage
                .openPage()
                .login(validEmail,validPassword);

        Assert.assertTrue(homePage.isAvatarDisplayed());
    }
}
