package tests.ui;

import db.DbService;
import drivers.BaseTest;
import drivers.DriverManager;
import models.User;
import models.UserFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.BasePage;
import pages.LoginPage;
import pages.ProfilePage;
import utils.ConfigReader;
import utils.TestDataProviders;

public class ProfilePageTest extends BaseTest {
    @Test
    public void updateProfileName() {
        String newName = BasePage.generateRandomName();
        ProfilePage profilePage = new LoginPage(DriverManager.getDriver())
                .openPage()
                .loginAsValidUser()
                .getProfile()
                .currentPass(ConfigReader.getProperty("user.password"))
                .provideNewProfileName(newName)
                .clickSaveButton();
        String actualName = profilePage.getProfileName();
        Assert.assertEquals(actualName, newName);
    }

    @Test (enabled = false, description = "Will be refactored in Playwright framework")
    public void changeCurrentTheme() {
        ProfilePage profilePage = new LoginPage(DriverManager.getDriver())
                .openPage()
                .loginAsValidUser()
                .getProfile()
                .currentPass(ConfigReader.getProperty("user.password"))
                .selectClassisTheme();
        Assert.assertTrue(profilePage.isClassicThemeSelected());

        profilePage.selectCatTheme(); //return theme
    }

    @Test(description = "Koel | Update password | " +
            "Check password update, check login with old and new password ")
    public void changePassword() {
        User user = UserFactory.testUser();
        String oldPassword = user.getPassword();
        String newPassword = "NewP@ss!234";
        ProfilePage profilePage = new LoginPage(DriverManager.getDriver())
                .openPage()
                .loginAs(user)
                .getProfile()
                .currentPass(oldPassword)
                .setNewPassword(newPassword);
        Assert.assertTrue(profilePage.getSuccessMessage().contains("updated"));
        profilePage.waitClearExitButton();
        //Returning to the Login Page and check old password
        profilePage.getLoginPage();
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        Assert.assertTrue(loginPage.isLogoDisplayed());
        loginPage.provideEmail(user.getEmail())
                .providePassword(oldPassword)
                .clickSubmitButton();
        Assert.assertTrue(loginPage.isRedFramePresent());
        //Login with new credential and change password back
        loginPage.login(user.getEmail(), newPassword)
                .getProfile()
                .currentPass(newPassword)
                .setNewPassword(oldPassword);
        //double check
        Assert.assertTrue(profilePage.getSuccessMessage().contains("updated"));
    }

    @Test(dataProvider = "newPasswordsSet", dataProviderClass = TestDataProviders.class,
            description = "Koel | Update password | " +
                    "Profile & Preferences | Password Validation Scenarios")
    public void checkPasswordValidation(String password, boolean valid) {
        User user = UserFactory.testUser();
        String oldPassword = user.getPassword();
        ProfilePage profilePage = new LoginPage(DriverManager.getDriver())
                .openPage()
                .loginAs(user)
                .getProfile()
                .currentPass(oldPassword)
                .setNewPassword(password);

        try {
            if (valid) {
                Assert.assertTrue(profilePage.getSuccessMessage().contains("updated"));
            } else {
                Assert.assertTrue(profilePage.isErrorMessageDisplayed(),
                        "Invalid password was accepted: " + password);
            }
        } finally {
            if (valid) {
                profilePage.currentPass(password)
                        .setNewPassword(oldPassword);
            } else {
                if (!profilePage.isErrorMessageDisplayed()) {
                    profilePage.currentPass(password)
                            .setNewPassword(oldPassword);
                }
            }
        }
    }

    @Test(description = "Koel | Update password | " +
            "Verify password was actually updated in DB")
    public void verifyPasswordHashChangedInDb() {
        User user = UserFactory.testUser();
        DbService dbService = new DbService();
        String oldPassword = user.getPassword();
        String newPassword = "NewP@ss!234";

        String oldHash = dbService.getUserPasswordHash(user.getEmail());
        ProfilePage profilePage = new LoginPage(DriverManager.getDriver())
                .openPage()
                .loginAs(user)
                .getProfile()
                .currentPass(oldPassword)
                .setNewPassword(newPassword);
        profilePage.waitClearExitButton();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String newHash = dbService.getUserPasswordHash(user.getEmail());
        try {
            Assert.assertNotEquals(oldHash, newHash, "Hash in DB did not change!");
        } finally {
            profilePage.currentPass(newPassword)
                    .setNewPassword(oldPassword);
        }
    }

    @Test(description = "Koel | Profile | Update email |" +
            " User is able to update account email")
    public void changeEmail() {
        User user = UserFactory.mainUser();
        String password = user.getPassword();
        String oldEmail = user.getEmail();
        String newEmail = "internstudent@testpro.io";
        ProfilePage profilePage = new LoginPage(DriverManager.getDriver())
                .openPage()
                .loginAs(user)
                .getProfile()
                .currentPass(password)
                .setNewEmail(newEmail);
        Assert.assertTrue(profilePage.getSuccessMessage().contains("updated"), "No success toast");
        profilePage.waitClearExitButton();
        //Returning to the Login Page and check old email
        profilePage.getLoginPage();
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        Assert.assertTrue(loginPage.isLogoDisplayed());
        loginPage.provideEmail(oldEmail)
                .providePassword(password)
                .clickSubmitButton();
        Assert.assertTrue(loginPage.isRedFramePresent(), "Red-frame is not present");
        //Login with new email
        loginPage.login(newEmail, password)
                .getProfile()
                .currentPass(password)
                .setNewEmail(oldEmail);
        Assert.assertTrue(profilePage.getSuccessMessage().contains("updated"));
    }

    @Test(description = "Koel | Update email | Verify email was updated correctly in DB")
    public void verifyEmailChangedInDB() throws InterruptedException {
        DbService dbService = new DbService();
        User user = UserFactory.mainUser();
        String password = user.getPassword();
        String oldEmail = user.getEmail();
        String newEmail = "automation.internstudent@testpro.io";
        ProfilePage profilePage = new LoginPage(DriverManager.getDriver())
                .openPage()
                .loginAs(user)
                .getProfile();
        String dbOldEmail = dbService.getUserEmail(oldEmail);
        Assert.assertEquals(dbOldEmail, oldEmail, "Email not present in DataBase");
        profilePage.currentPass(password)
                .setNewEmail(newEmail);
        Thread.sleep(3000);
        String dbNewEmail = dbService.getUserEmail(newEmail);
        Assert.assertEquals(dbNewEmail, newEmail, "Email not updated in DataBase");
        profilePage.currentPass(password)
                .setNewEmail(oldEmail);
    }

    @Test(description = "Koel | Profile | Update email | " +
            "User is unable to update email to an already existing")
    public void checkEmailAlreadyExists() {
        User user = UserFactory.mainUser();
        User userTest = UserFactory.testUser();
        String oldEmail = user.getEmail();
        String password = user.getPassword();
        String newEmail = userTest.getEmail();
        ProfilePage profilePage = new LoginPage(DriverManager.getDriver())
                .openPage()
                .loginAs(user)
                .getProfile()
                .currentPass(password)
                .setNewEmail(newEmail);
        Assert.assertTrue(profilePage.getErrorMessage()
                .contains("already been taken"), "Existed email has been accepted");
        profilePage.currentPass(password)
                .setNewEmail(oldEmail);
        Assert.assertTrue(profilePage.getSuccessMessage().contains("updated"));
    }

    @Test(enabled = false, description = "Will be refactored in Playwright framework") // dataProvider = "EmailChangeNegativeScenarios", dataProviderClass = TestDataProviders.class,
//            description = "Koel | Update email | Change email validation scenarios")
    public void changeEmailValidation(String email, String scenarioDescription) {
        SoftAssert soft = new SoftAssert();
        User user = UserFactory.mainUser();
        String password = user.getPassword();
        String oldEmail = user.getEmail();
        String invalidEmail = email;
        ProfilePage profilePage = new LoginPage(DriverManager.getDriver())
                .openPage()
                .loginAs(user)
                .getProfile()
                .currentPass(password)
                .disableHtml5Validation();
        profilePage.setNewEmail(invalidEmail);
        soft.assertFalse(profilePage.qucickSuccessCheck()
                .contains("update"), "Invalid Email "
                + scenarioDescription + " has been accepted");
        soft.assertTrue(profilePage
                        .isErrorMessageDisplayed(),
                "Error message not displayed for" + scenarioDescription);
        profilePage.currentPass(password)
                .setNewEmail(oldEmail);
        Assert.assertTrue(profilePage.getSuccessMessage().contains("updated"));
        soft.assertAll();
    }

    @Test(description = "Regression| Update email | " +
            "User is not able to update email with already registered email")
    public void checkEmailAlreadyExistsInDb() {
        DbService dbService = new DbService();
        User user = UserFactory.mainUser();
        User userTest = UserFactory.testUser();
        String oldEmail = user.getEmail();
        String password = user.getPassword();
        String newEmail = userTest.getEmail();
        ProfilePage profilePage = new LoginPage(DriverManager.getDriver())
                .openPage()
                .loginAs(user)
                .getProfile();
        String dbEmail = dbService.getUserEmail(oldEmail);
        Assert.assertEquals(dbEmail, oldEmail, "Email not present in DataBase");
        profilePage.currentPass(password)
                .setNewEmail(newEmail);
        Assert.assertTrue(profilePage.getErrorMessage()
                .contains("already been taken"), "Existed email has been accepted");
        profilePage.currentPass(password)
                .setNewEmail(oldEmail);
        Assert.assertTrue(profilePage.getSuccessMessage().contains("updated"));
    }
}
