package Tests;

import db.DbService;
import drivers.BaseTest;
import drivers.DriverManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import utils.ConfigReader;
import utils.EmailGenerator;
import utils.TestDataProviders;

public class LoginTests extends BaseTest {
    @Test //Log-in positive test
    public void loginValidData() {
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        HomePage homePage = loginPage
                .openPage()
                .login(ConfigReader.getProperty("user.email"),
                        ConfigReader.getProperty("user.password"));

        Assert.assertTrue(homePage.isAvatarDisplayed());
    }

    @Test(dataProvider = "InvalidEmailData", dataProviderClass = TestDataProviders.class)
    public void loginWithIncorrectEmail(String email, String password) {
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.openPage()
                .provideEmail(email)
                .providePassword(password)
                .clickSubmitButton();
        Assert.assertTrue(loginPage.isLoginPageDisplayed());
    }

    @Test(dataProvider = "InvalidPasswordData", dataProviderClass = TestDataProviders.class)
    public void loginWithIncorrectPassword(String email, String password) {
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.openPage()
                .provideEmail(email)
                .providePassword(password)
                .clickSubmitButton();
        Assert.assertTrue(loginPage.isLoginPageDisplayed());
    }

    @Test
    public void registrationLinkCheck() {
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.openPage()
                .clickRegistrationButton();
        Assert.assertEquals(loginPage.getPageUrl(), "https://qa.koel.app/registration");
    }

    @Test
    public void succesfulRegistrationAndVerifyDb() {
        String email = EmailGenerator.generateTestioEmail();
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.openPage()
                .clickRegistrationButton();
        loginPage.provideEmail(email)
                .clickSubmitRegistrationButton();
        String message = loginPage.getNotificationMessageText();
        Assert.assertTrue(message.contains("sent a confirmation link to the email"));
        //Verification in DB
        DbService dbService = new DbService();
        String hash = dbService.getUserPasswordHash(email);
        Assert.assertNotNull(hash);
        Assert.assertTrue(hash.startsWith("$2"));
        Assert.assertEquals(hash.length(), 60);
    }
    @Test
    public void registerWithExistedEmail() {
        String email = EmailGenerator.generateTestioEmail();
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.openPage()
                .clickRegistrationButton();
        loginPage.provideEmail(email)
                .clickSubmitRegistrationButton();
        DbService dbService = new DbService();
        String hash = dbService.getUserPasswordHash(email);
        Assert.assertNotNull(hash);
        Assert.assertTrue(hash.startsWith("$2"));
        loginPage.openPage()
                .clickRegistrationButton();
        loginPage.provideEmail(email)
                .clickSubmitRegistrationButton();
        String errorMessage = loginPage.getNotificationMessageText();
        Assert.assertTrue(errorMessage.toLowerCase().contains("user already registered"));
    }
}
