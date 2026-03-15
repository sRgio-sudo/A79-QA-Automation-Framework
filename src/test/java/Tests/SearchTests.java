package Tests;

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
        String query = "Dark";
        SearchPage searchPage = new LoginPage(DriverManager.getDriver())
                .openPage()
                .loginAs(user)
                .getSearchPage()
                .enterSearchQuery(query);
        soft.assertFalse(searchPage.isSongResultEmpty(), "Song should present in results");
        soft.assertTrue(searchPage.isArtistResultEmpty(), "Artist should not be present in results");
        soft.assertFalse(searchPage.isAlbumResultEmpty(), "Album should present in results");
        soft.assertAll();
    }

    @Test(description = "Koel | Search | Verify leading and trailing white spaces are ignored")
    public void searchWithSpacesTest() {
        User user = UserFactory.mainUser();
        String query = "  Pluto  ";
        SearchPage searchPage = new LoginPage(DriverManager.getDriver())
                .openPage()
                .loginAs(user)
                .getSearchPage()
                .enterSearchQuery(query);
        Assert.assertFalse(searchPage
                .isSongResultEmpty(), "Song should present in results for [" + query + "]");
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
