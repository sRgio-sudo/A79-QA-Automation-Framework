package tests.ui;

import drivers.BaseTest;
import drivers.DriverManager;
import models.User;
import models.UserFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import pages.SearchPage;

public class SearchTests extends BaseTest {

    @Test(description = "Koel | Search | Verify clearing search query")
    public void searchClearingTest() {
        User user = UserFactory.mainUser();
        String query = "Dark Days";
        SearchPage searchPage = new LoginPage(DriverManager.getDriver())
                .openPage()
                .loginAs(user)
                .getSearchPage()
                .enterSearchQuery(query);
        Assert.assertTrue(searchPage.checkSongResults(), "No results found");
        searchPage.clearSearchField();
        Assert.assertTrue(searchPage.isSearchFieldEmpty(), "Search field not empty");
        Assert.assertFalse(searchPage.checkSongResults(), "Search result is not cleared");
    }

    @Test(description = "Koel | Search | Verify 'no results' message for non-existing query")
    public void searchNonExistingQueryTest() {
        SoftAssert soft = new SoftAssert();
        User user = UserFactory.mainUser();
        String query = "NonExistent123!@#";
        SearchPage searchPage = new LoginPage(DriverManager.getDriver())
                .openPage()
                .loginAs(user)
                .getSearchPage()
                .enterSearchQuery(query);
        soft.assertTrue(searchPage.getSongSearchMessage().contains("None"), "Song field not empty");
        soft.assertTrue(searchPage.getArtistsSearchMessage().contains("None"), "Artist field not empty");
        soft.assertTrue(searchPage.getAlbumsSearchMessage().contains("None"), "Album field not empty");
        soft.assertAll();
    }

    @Test(description = "Koel | Search | " +
            "Verify results are populated in all sections (Songs, Artists, Albums)")
    public void searchResultsTest() {
        SoftAssert soft = new SoftAssert();
        User user = UserFactory.mainUser();
        String song = "Dark Days";
        String artist = "Grav";
        String album = "Airbit";
        SearchPage searchPage = new LoginPage(DriverManager.getDriver())
                .openPage()
                .loginAs(user)
                .getSearchPage()
                .enterSearchQuery(song);
        soft.assertFalse(searchPage.isSongResultEmpty(), "Song should present in results");
        searchPage.clearSearchField()
                        .enterSearchQuery(artist);
        soft.assertFalse(searchPage.isArtistResultEmpty(), "Artist should present in results");
        searchPage.clearSearchField()
                .enterSearchQuery(album);
        soft.assertFalse(searchPage.isAlbumResultEmpty(), "Album should present in results");
        soft.assertAll();
    }

    @Test(description = "Koel | Search | Verify leading and trailing white spaces are ignored")
    public void searchWithSpacesTest() {
        SoftAssert soft = new SoftAssert();
        User user = UserFactory.mainUser();
        String queryA = "  Pluto";
        String queryB = "Pluto  ";
        SearchPage searchPage = new LoginPage(DriverManager.getDriver())
                .openPage()
                .loginAs(user)
                .getSearchPage()
                .enterSearchQuery(queryA);
        soft.assertFalse(searchPage
                .isSongResultEmpty(), "Song should present in results for [" + queryA + "]");
        searchPage.clearSearchField()
                .enterSearchQuery(queryB);
        soft.assertFalse(searchPage
                .isSongResultEmpty(), "Song should present in results for [" + queryB + "]");
        soft.assertAll();
    }

    @Test(description = "Koel | Search | Verify search is case sensitive")
    public void searchCaseSensitiveTest() {
        SoftAssert soft = new SoftAssert();
        User user = UserFactory.mainUser();
        String queryUpper = "Pluto";
        String queryLower = "pluto";
        SearchPage searchPage = new LoginPage(DriverManager.getDriver())
                .openPage()
                .loginAs(user)
                .getSearchPage()
                .enterSearchQuery(queryUpper);
        soft.assertFalse(searchPage.isSongResultEmpty(), "Song should present in results for [" + queryUpper + "]");
        searchPage.clearSearchField()
                .enterSearchQuery(queryLower);
        soft.assertTrue(searchPage.isSongResultEmpty(), "Song should not present in results for [" + queryLower + "]");
        soft.assertAll();
    }
}
