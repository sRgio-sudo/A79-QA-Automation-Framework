package Tests;

import db.DbService;
import drivers.BaseTest;
import drivers.DriverManager;
import models.User;
import models.UserFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
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

    @Test
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
        }catch (InterruptedException e){
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
}
