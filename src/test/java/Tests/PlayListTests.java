package Tests;

import db.DbService;
import drivers.BaseTest;
import drivers.DriverManager;
import models.User;
import models.UserFactory;
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

    @Test(description = "Koel | Create New Playlist | " +
            "User is able to create a playlist and verify in Database")
    public void createPlayListAndVerifyInDb() {
        User user = UserFactory.mainUser();
        String playListName = "Automation_PL_Check";
        DbService dbService = new DbService();
        HomePage homePage = new LoginPage(DriverManager.getDriver())
                .openPage()
                .loginAs(user)
                .createPlaylist(playListName);
        Assert.assertTrue(homePage.isPlayListDisplayed(playListName));
        String playlist = dbService.checkPlaylistInBase(playListName);
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

    @Test(description = "Koel | Create New Playlist |" +
            " Verify system prevents creating a playlist with a duplicate name")
    public void checkPlayListDuplicateName() {
        User user = UserFactory.mainUser();
        String playListName = "Duplicate_Test";
        HomePage homePage = null;
        try {
            homePage = new LoginPage(DriverManager.getDriver())
                    .openPage()
                    .loginAs(user)
                    .createPlaylist(playListName);
            Assert.assertTrue(homePage.isPlayListDisplayed(playListName));
            // create same playlist
            homePage.createPlaylist(playListName);
            Assert.assertEquals(homePage
                    .countPlayLists(playListName), 1, "Duplicate playlist was created");
            Assert.assertTrue(homePage
                    .isRedFramePresent(), "Red border expected for duplicate playlist name");
            // cleanup
        } finally {
            homePage.deleteAllPlaylistsByName(playListName);
        }
    }

    @Test(dataProvider = "playListNames", dataProviderClass = TestDataProviders.class,
            description = "Koel | Create New Playlist | Boundary Testing")
    public void checkPlayListNameParameters(String name, boolean valid) {
        User user = UserFactory.mainUser();
        HomePage homePage = new LoginPage(DriverManager.getDriver())
                .openPage()
                .loginAs(user);
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


