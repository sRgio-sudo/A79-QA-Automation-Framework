import org.testng.Assert;
import org.testng.annotations.Test;
import pages.BasePage;
import pages.LoginPage;
import pages.ProfilePage;
import utils.BaseTest;
import utils.ConfigReader;

public class ProfilePageTest extends BaseTest {
    @Test
    public void updateProfileName() {
        String newName = BasePage.generateRandomName();
        ProfilePage profilePage = new LoginPage(getDriver())
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
        ProfilePage profilePage = new LoginPage(getDriver())
                .openPage()
                .loginAsValidUser()
                .getProfile()
                .currentPass(ConfigReader.getProperty("user.password"))
                .selectClassisTheme();
                Assert.assertTrue(profilePage.isClassicThemeSelected());

                profilePage.selectCatTheme(); //return theme
    }
}
