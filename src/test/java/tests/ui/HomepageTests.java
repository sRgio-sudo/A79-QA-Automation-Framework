package tests.ui;

import drivers.BaseTest;
import drivers.DriverManager;
import models.User;
import models.UserFactory;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import pages.LoginPage;

import java.util.List;

public class HomepageTests extends BaseTest {

    @Test(description = "Koel | Albums | Album cover should be displayed if exist")
    public void checkAlbumCover() {
        SoftAssert softAssert = new SoftAssert();
        User user = UserFactory.mainUser();
        HomePage homePage = new LoginPage(DriverManager.getDriver())
                .openPage()
                .loginAs(user)
                .clickAlbumsButton();
        List<String> allStyles = homePage.getAlbumCoverStyles();
        for (int i = 0; i < allStyles.size(); i++) {
            String style = allStyles.get(i);
            int albumNum = i + 1;
            softAssert.assertNotNull(style, "Album number " + (i + 1) + "hasn`t attribute 'style'");
            softAssert.assertTrue(style.contains(".png"), "Album number " + (i + 1) + "don`t contain *.png");
        }
        softAssert.assertAll();
    }

    @Test(enabled = false, description = "Will be refactored in Playwright framework")//(description = "Koel | Albums | Check albums contain name and artist")
    public void checkAlbumCoverName() {
        SoftAssert softAssert = new SoftAssert();
        User user = UserFactory.mainUser();
        HomePage homePage = new LoginPage(DriverManager.getDriver())
                .openPage()
                .loginAs(user)
                .clickAlbumsButton();
        List<String> allAlbums = homePage.getAllAlbumNames();
        List<String> allArtists = homePage.getAllArtistNames();
        for (int i = 0; i < allAlbums.size(); i++) {
            String albumName = allAlbums.get(i);
            softAssert.assertFalse(albumName
                    .isEmpty(), "Album # " + (i + 1) + " has an empty name");
        }
        for (int i = 0; i < allArtists.size(); i++) {
            String artistName = allArtists.get(i);
            softAssert.assertFalse(artistName
                    .isEmpty(), "Album # " + (i + 1) + " has an empty artist name");
        }
        softAssert.assertAll();
    }

    @Test(description = "Koel | Albums | Verify presence of Download and Shuffle icons")
    public void checkAlbumIcons() {
        SoftAssert softAssert = new SoftAssert();
        User user = UserFactory.mainUser();
        HomePage homePage = new LoginPage(DriverManager.getDriver())
                .openPage()
                .loginAs(user)
                .clickAlbumsButton();
        List<String> albums = homePage.getAllAlbumNames();
        for (int i = 0; i < albums.size(); i++) {
            homePage.hoverOverAlbum(i);
            int num = i + 1;
            softAssert.assertTrue(homePage.isDownloadIconDisplayed(i),
                    "Album # " + num + "hasn't download icon");
            softAssert.assertTrue(homePage.isShuffleIconDisplayed(i),
                    "Album # " + num + "hasn't shuffle icon");
        }
        softAssert.assertAll();
    }
}