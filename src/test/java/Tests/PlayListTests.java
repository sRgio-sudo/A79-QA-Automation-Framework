package Tests;

import db.DbService;
import drivers.BaseTest;
import drivers.DriverManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.BasePage;
import pages.HomePage;
import pages.LoginPage;
import pages.PlayerComponent;
import utils.TestDataProviders;

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

    @Test(description = "TC05 Koel | PlayList | " +
            "Check create Playlist and verify in Database")
    public void checkPlaylist() {
        String playListName = "Automation_PL_Check";
        HomePage homePage = new LoginPage(DriverManager.getDriver())
                .openPage()
                .loginAsValidUser()
                .createPlaylist(playListName);
        Assert.assertTrue(homePage.isPlayListDisplayed(playListName));
        // DB Check
        DbService dbService = new DbService();
        String playlist = dbService.checkPlaylistInBase(playListName);
        Assert.assertNotNull(playlist);
        Assert.assertEquals(playlist, "Automation_PL_Check");
        //Cleanup
        homePage.deletePlaylist(playListName);
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

    @Test(description = "TC06 Koel | PlayList | " +
            "Verify system prevents creating a playlist with a duplicate name")
    public void checkPlayListSameName() {
        String playListName = "Automation_Test";
        HomePage homePage = new LoginPage(DriverManager.getDriver())
                .openPage()
                .loginAsValidUser()
                .createPlaylist(playListName);
        Assert.assertTrue(homePage.isPlayListDisplayed(playListName));
        // create same playlist
        homePage.createPlaylist(playListName);
        Assert.assertTrue(homePage.isPlayListDisplayed(playListName));
        Assert.assertEquals(homePage.countPlayLists(playListName), 1);
        // cleanup
        homePage.deleteAllPlaylistsByName(playListName);
    }

    @Test(dataProvider = "playListNames", dataProviderClass = TestDataProviders.class,
            description = "TC07 Koel | PlayList |" +
                    " Check playlist name parameters")
    public void checkPlayListNameParameters(String name, boolean valid) {
        HomePage homePage = new LoginPage(DriverManager.getDriver())
                .openPage()
                .loginAsValidUser();
        try {
            homePage.createPlaylist(name);
            if (valid) {
                Assert.assertTrue(homePage.isSuccessToastPresent());
                String toast = homePage.getSuccessToastText();
                Assert.assertTrue(toast.contains("Created playlist"));
            } else {
                Assert.assertFalse(homePage.isSuccessToastPresent(),
                        "Playlist should NOT be created for name: " + name);
                Assert
                .assertTrue(homePage.isRedFramePresent(),
                "Validation border expected");
            }
        } finally {
            try {
                if (homePage.isPlayListDisplayed(name)) {
                    homePage.deletePlaylist(name);
                }
            } catch (Exception ignored) {
            }
        }
    }
}


