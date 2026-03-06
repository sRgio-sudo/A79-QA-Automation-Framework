package Tests;

import db.DbService;
import drivers.DriverManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.BasePage;
import pages.HomePage;
import pages.LoginPage;
import pages.PlayerComponent;
import drivers.BaseTest;

public class PlayListTests extends BaseTest {
    @Test
    public void playSongFromAllSongList() {
        PlayerComponent player = new PlayerComponent(DriverManager.getDriver());
        HomePage homePage = new LoginPage(DriverManager.getDriver())
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
        HomePage homePage = new LoginPage(DriverManager.getDriver())
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
        HomePage homePage = new LoginPage(DriverManager.getDriver())
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
        String playListName = "Automation_PL_Check";
        HomePage homePage = new LoginPage(DriverManager.getDriver())
                .openPage()
                .loginAsValidUser()
                .createPlaylist(playListName);
        Assert.assertTrue(homePage.isPlayListDisplayed(playListName));
        homePage.deletePlaylist(playListName);
//        DB Check
        DbService dbService = new DbService();

    }

    @Test
    public void checkSmartPlayList() {
        String playListName = "SmartPlayListToDelete";
        String sortCriteria = "Album";
        String sortEquals = "contains";
        String sortBy = "Unknown";

        HomePage homePage = new LoginPage(DriverManager.getDriver())
                .openPage()
                .loginAsValidUser()
                .createSmartPlaylist(playListName, sortCriteria, sortEquals, sortBy);
        Assert.assertTrue(homePage.isPlayListDisplayed(playListName));
        homePage.deleteSmartPL(playListName);
    }
}


