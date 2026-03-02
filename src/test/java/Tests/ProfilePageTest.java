package Tests;

import drivers.BaseTest;
import drivers.DriverManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.BasePage;
import pages.LoginPage;
import pages.ProfilePage;
import utils.ConfigReader;

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
}
