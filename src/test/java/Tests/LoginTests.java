package Tests;

import db.DbService;
import drivers.BaseTest;
import drivers.DriverManager;
import models.User;
import models.UserFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.EmailGenerator;
import utils.TestDataProviders;

public class LoginTests extends BaseTest {
    @Test
    public void loginValidUser() {
        User user = UserFactory.mainUser();
        pages.HomePage homePage = new LoginPage(DriverManager.getDriver())
                .openPage()
                .loginAs(user);

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

    @Test(description = "Koel | Account creation | Register new account |" +
            " Verify user's data is correctly saved in DB")
    public void successfulRegistrationAndVerifyDb() {
        String email = EmailGenerator.generateTestioEmail();
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.openPage()
                .clickRegistrationButton();
        loginPage.provideEmail(email)
                .clickSubmitRegistrationButton();
        String message = loginPage.getNotificationMessageText();
        Assert.assertTrue(message.contains("sent a confirmation link to the email"));
        //Verify user's data is correctly saved in DB
        DbService dbService = new DbService();
        String hash = dbService.getUserPasswordHash(email);
        Assert.assertNotNull(hash);
        Assert.assertTrue(hash.startsWith("$2"));
        Assert.assertEquals(hash.length(), 60);
    }

    @Test(description = "Koel | Account creation | Register new account |" +
            " User can't register if email already exists in DB")
    public void registerWithExistedEmail() {
        String email = EmailGenerator.generateTestioEmail();
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        // Execute precondition: Create user first
        loginPage.openPage()
                .clickRegistrationButton();
        loginPage.provideEmail(email)
                .clickSubmitRegistrationButton();
        //Test action. Try to register with the same email again
        loginPage.openPage()
                .clickRegistrationButton();
        loginPage.provideEmail(email)
                .clickSubmitRegistrationButton();
        String errorMessage = loginPage.getNotificationMessageText();
        Assert.assertTrue(errorMessage.toLowerCase().contains("user already registered"));
    }

    @Test(dataProvider = "RegistrationNegativeScenarios", dataProviderClass = TestDataProviders.class,
            description = "Koel | Account creation | Register new account | Negative validation scenarios")
    public void registerWithInvalidEmailScenarios(String email, String scenarioDescription) {
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.openPage()
                .clickRegistrationButton();
        loginPage.disableHtml5Validation();
        loginPage.provideEmail(email)
                .clickSubmitRegistrationButton();
        String successMessage = loginPage.getQuickNotificationText();
        Assert.assertEquals(successMessage, "", "Success message should not appear for: " + scenarioDescription);
        String errorMessage = loginPage.getErrorMessageText();
        Assert.assertFalse(errorMessage.isEmpty(), "Error message should be displayed for: " + scenarioDescription);
        Assert.assertTrue(errorMessage.toLowerCase().contains("only certain emails are allowed"),
                "Unexpected error message for: " + scenarioDescription);
    }
}
