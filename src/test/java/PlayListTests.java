import org.testng.Assert;
import org.testng.annotations.Test;
import pages.BasePage;
import pages.HomePage;
import pages.LoginPage;
import pages.PlayerComponent;
import utils.BaseTest;

public class PlayListTests extends BaseTest {
    @Test
    public void playSongFromAllSongList() {
        PlayerComponent player = new PlayerComponent(getDriver());
        HomePage homePage = new LoginPage(getDriver())
                .openPage()
                .loginAsValidUser()
                .selectAllSongList()
                .contextClickFirstSong()
                .contextClickPlaySong();

        Assert.assertTrue(player.soundbarCheck());
    }

    @Test
    public void renamePlaylist() {

        String playListName = BasePage.generateRandomName();
        String updatedPlayListName = "playListRenamed";
        HomePage homePage = new LoginPage(getDriver())
                .openPage()
                .loginAsValidUser();
        homePage
                .createPlaylist(playListName)
                .renamePlaylist(playListName, updatedPlayListName);

        Assert.assertTrue(homePage.isPlayListDisplayed(updatedPlayListName));

        homePage.deletePlaylist(updatedPlayListName);
    }


    @Test
    public void addSongToPlaylist() {
        String songToAdd = "Samurai";
        String playList = "Playlist2";
        HomePage homePage = new LoginPage(getDriver())
                .openPage()
                .loginAsValidUser()
                .songSearch(songToAdd)
                .addSongToPlaylist(songToAdd, playList);
        String getSuccessMessage = homePage
                .successMessage()
                .getText();
        Assert.assertTrue(getSuccessMessage.contains("Added"));
        Assert.assertTrue(getSuccessMessage.contains(playList));
        homePage.deleteAddedSong(playList, songToAdd)
                .successMessage();
    }

    @Test
    public void checkPlaylist() {
        String playListName = "PlayListToDelete";
        HomePage homePage = new LoginPage(getDriver())
                .openPage()
                .loginAsValidUser()
                .createPlaylist(playListName);
        Assert.assertTrue(homePage.isPlayListDisplayed(playListName));
        homePage.deletePlaylist(playListName);
    }

    @Test
    public void checkSmartPlayList() {
        String playListName = "SmartPlayListToDelete";
        String sortCriteria = "Album";
        String sortEquals = "contains";
        String sortBy = "Unknown";

        HomePage homePage = new LoginPage(getDriver())
                .openPage()
                .loginAsValidUser()
                .createSmartPlaylist(playListName, sortCriteria, sortEquals, sortBy);
        Assert.assertTrue(homePage.isPlayListDisplayed(playListName));
        homePage.deleteSmartPL(playListName);
    }
}


