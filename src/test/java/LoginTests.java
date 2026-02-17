import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import utils.ConfigReader;

public class LoginTests extends BaseTest {
    @Test //Log-in positive test
    public void loginValidData() {
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = loginPage
                .openPage()
                .login(ConfigReader.getProperty("user.email"),
                        ConfigReader.getProperty("user.password"));

        Assert.assertTrue(homePage.isAvatarDisplayed());
    }

    @Test(dataProvider = "InvalidEmailData", dataProviderClass = TestDataProviders.class)
    public void loginWithIncorrectEmail(String email, String password) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openPage()
                .provideEmail(email)
                .providePassword(password)
                .clickSubmitButton();
        Assert.assertTrue(loginPage.isLoginPageDisplayed());
    }

    @Test(dataProvider = "InvalidPasswordData", dataProviderClass = TestDataProviders.class)
    public void loginWithIncorrectPassword(String email, String password) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openPage()
                .provideEmail(email)
                .providePassword(password)
                .clickSubmitButton();
        Assert.assertTrue(loginPage.isLoginPageDisplayed());
    }

}
