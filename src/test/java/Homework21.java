import pages.HomePage;
import pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Homework21 extends BaseTest {
    @Test
    public void renamePlaylist() {

        String playListName = generateRandomName();
        String updatedPlayListName = "playListRenamed";
        HomePage homePage = new LoginPage(driver)
                .openPage()
                .loginAsValidUser();
        homePage
                .createPlaylist(playListName)
                .renamePlaylist(playListName, updatedPlayListName);

        Assert.assertTrue(homePage.isPlayListDisplayed(updatedPlayListName));

        homePage.deletePlaylist(updatedPlayListName);
    }
}


