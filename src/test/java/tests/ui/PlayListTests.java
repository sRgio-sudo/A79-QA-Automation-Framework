package tests.ui;

import api.ApiNetworkInterceptor;
import db.DbService;
import drivers.BaseTest;
import drivers.DriverManager;
import models.User;
import models.UserFactory;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.BasePage;
import pages.HomePage;
import pages.LoginPage;
import pages.PlayerComponent;

public class PlayListTests extends BaseTest {
    @Test (enabled = false, description = "Will be refactored in Playwright framework")
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


    @Test(description = "Koel | New Playlist | Add song to a playlist and verify in Database")
    public void addSongToPlaylistAndVerifyWithDB() {
        SoftAssert softAssert = new SoftAssert();
        User user = UserFactory.mainUser();
        String songToAdd = "Samurai";
        String playList = "Playlist2";
        String email = user.getEmail();
        DbService dbService = new DbService();
        int countSongsBefore = dbService.countSongsInPlaylist(playList, email);
        System.out.println(playList + " has " + countSongsBefore + " songs before test");
        HomePage homePage = new LoginPage(DriverManager.getDriver())
                .openPage()
                .loginAs(user)
                .songSearch(songToAdd)
                .addSongToPlaylist(songToAdd, playList);
        String getSuccessMessage = homePage
                .successMessage()
                .getText();
        softAssert.assertTrue(getSuccessMessage.contains("Added"));
        softAssert.assertTrue(getSuccessMessage.contains(playList));
        int countSongsAfter = dbService.countSongsInPlaylist(playList, email);
        System.out.println(playList + " has " + countSongsAfter + " songs after test");
        softAssert.assertEquals(countSongsAfter, countSongsBefore + 1, "Song count not changed");
        softAssert.assertAll();
        //cleanup
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

    @Test (enabled = false, description = "Will be refactored in Playwright framework")
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

    @Test(enabled = false, description = "Will be refactored in Playwright framework")//(dataProvider = "playListNames", dataProviderClass = TestDataProviders.class,
//            description = "Koel | Create New Playlist | Boundary Testing")
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

    @Test(description = " Koel | Favorites | User is able to add song and delete songs from the Favorites playlist")
    public void checkFavoritesPlaylist() {
        User user = UserFactory.mainUser();
        String songA = "Pluto";
        String songB = "Lament";
        HomePage homePage = new LoginPage(DriverManager.getDriver())
                .openPage()
                .loginAs(user)
                .songSearch(songA)
                .likeSelectedSongFromSearch(songA);
        Assert.assertTrue(homePage.isSongLiked(songA));
        homePage.songSearch(songB)
                .likeSelectedSongFromSearch(songB);
        Assert.assertTrue(homePage.isSongLiked(songB));
        homePage.clickFavorites()
                .dislikeSongFromFavorites(songA);
        Assert.assertFalse(homePage.isSongPresentFavorites(songA), "Song should not present in list");
        homePage.dislikeSongFromFavorites(songB);
        Assert.assertFalse(homePage.isSongPresentFavorites(songB), "Song should not present in list");
    }

    @Test(description = "Koel | Favorites | Playlist page display empty state")
    public void checkFavoritesEmptyState() {
        User user = UserFactory.mainUser();
        HomePage homePage = new LoginPage(DriverManager.getDriver())
                .openPage()
                .loginAs(user)
                .clickFavorites()
                .unlikeAllFavorites();
        Assert.assertTrue(homePage.isNoFavoritesPresent(), " 'No favorites yet' message not displayed");
    }

    @Test(enabled = false, description = "Will be refactored in Playwright framework")//(description = "Koel | Favorites | " +
//            "User should be able to download songs from the Favorites playlist page")
    public void checkFavoritesDownload() {
        ChromeDriver driver = (ChromeDriver) DriverManager.getDriver();
        ApiNetworkInterceptor interceptor = new ApiNetworkInterceptor(driver);
        interceptor.listenForEndpoint("download/songs");
        User user = UserFactory.mainUser();
        HomePage homePage = new LoginPage(driver)
                .openPage()
                .loginAs(user)
                .clickFavorites()
                .downloadFirstSong();
        Assert.assertTrue(interceptor.whaitAndCheckStatus(2), "Download request was not sent to server");
    }

}


