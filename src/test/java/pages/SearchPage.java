package pages;

import drivers.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchPage extends BasePage {
    private By searchField = By.cssSelector("input[name='q']");
    private By firstSongFromSearch = By.xpath("//section[@data-testid='song-excerpts']//article[1]");
    private By songResultsList = By.cssSelector("section[data-testid='song-excerpts'] ul");
    private By searchResultsSongSection = By.cssSelector("section[data-testid='song-excerpts']");
    private By searchResultArtistSection = By.cssSelector("section[data-testid='artist-excerpts']");
    private By searchResultAlbumSection = By.cssSelector("[data-testid='album-excerpts']");
    private By songsSearchResult = By.cssSelector("[data-testid='song-excerpts'] p");
    private By artistsSearchResult = By.cssSelector("[data-testid='artist-excerpts'] p");
    private By albumsSearchResult = By.cssSelector("[data-testid='album-excerpts'] p");

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public SearchPage clearSearchField() {
        forceClear(searchField);
        return this;
    }

    public boolean isSearchFieldEmpty() {
        return findElement(searchField).getAttribute("value").isEmpty();
    }

    public SearchPage selectFirstSongFromSearch() {
        click(firstSongFromSearch);
        return this;
    }

    public SearchPage enterSearchQuery(String searchQuery) {
        clearAndType(searchField, searchQuery);
        return this;
    }

    public HomePage getHomePage() {
        return new HomePage(driver);
    }

    public boolean checkSongResults() {
        try {
            return findElement(songResultsList).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getSongSearchMessage() {
        return findElement(songsSearchResult).getText();
    }

    public String getArtistsSearchMessage() {
        return findElement(artistsSearchResult).getText();
    }

    public String getAlbumsSearchMessage() {
        return findElement(albumsSearchResult).getText();
    }

    private boolean isSectionEmpty(By sectionLocator) {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(3));
        try {
            wait.until(d -> {
                WebElement section = d.findElement(sectionLocator);
                return !section.findElements(By.tagName("p")).isEmpty() ||
                        !section.findElements(By.tagName("ul")).isEmpty();
            });
            return !findElement(sectionLocator).findElements(By.tagName("p")).isEmpty();
        } catch (Exception e) {
            return true;
        }
    }

    public boolean isSongResultEmpty() {
        return isSectionEmpty(searchResultsSongSection);
    }

    public boolean isArtistResultEmpty() {
        return isSectionEmpty(searchResultArtistSection);
    }

    public boolean isAlbumResultEmpty() {
        return isSectionEmpty(searchResultAlbumSection);
    }

}
