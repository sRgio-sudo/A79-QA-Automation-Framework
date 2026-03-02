package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    private By avatarIcon = By.xpath("//a[@data-testid='view-profile-link']");
    private By createPlayListButton = By.cssSelector("i[data-testid='sidebar-create-playlist-btn']");
    private By simplePlayListOption = By.cssSelector("li[data-testid='playlist-context-menu-create-simple']");
    private By smartPlayListOption = By.cssSelector("li[data-testid='playlist-context-menu-create-smart']");
    private By playListNameInputFiled = By.cssSelector(".create input[name='name']");
    private By renamePlayListInputFiled = By.cssSelector("[data-testid='inline-playlist-name-input']");
    private By succesShow = By.xpath("//div[@class='success show']");
    private By allSongLink = By.cssSelector("a[href='#!/songs']");
    private By firstSongElement = By.xpath("//section[@id='songsWrapper']//table[@class='items']//tr[1]");
    private By firstSongFromSearch = By.xpath("//section[@id='songResultsWrapper']//table[@class='items']//tr[1]");
    private By contextPlaySong = By.xpath("//li[@class='playback']");
    private By searchField = By.xpath("//input[@name='q']");
    private By viewAllButton = By.cssSelector("button[data-test='view-all-songs-btn']");
    private By addToButton = By.cssSelector("button[data-test='add-to-btn']");
    private By smartPLNameField = By.cssSelector(".form-row input[name='name']");
    private By saveButton = By.cssSelector("footer [type='submit']");


    public HomePage(WebDriver driver) {
        super(driver);
    }

    public boolean isAvatarDisplayed() {
        return findElement(avatarIcon).isDisplayed();
    }

    public HomePage createPlaylist(String playListName) {
        click(createPlayListButton);
        click(simplePlayListOption);
        typeAndSubmit(playListNameInputFiled, playListName);
        return this;
    }

    public HomePage createSmartPlaylist(
            String playListName, String sortCriteria, String sortEquals, String sortBy) {
        click(createPlayListButton);
        click(smartPlayListOption);
        clearAndType(smartPLNameField, playListName);
        selectDropDown(By.cssSelector("select[name='model[]']"), sortCriteria);
        selectDropDown(By.cssSelector("select[name='operator[]']"), sortEquals);
        clearAndType(By.cssSelector("input[name='value[]']"), sortBy);
        click(saveButton);
        return this;
    }

    public HomePage deleteSmartPL(String playListName) {
        waitInvisibilityOfSuccess();
        contextClick(By
                .xpath("//a[contains(text(), '" + playListName + "')]"));
        findElement(By
                .xpath("//li[contains(text(), 'Delete')]")).click();
        waitClickable(By.cssSelector("div [class='ok']")).click();
        waitVisibility(succesShow);
        return this;
    }

    public HomePage renamePlaylist(String playListName, String updatedPlayListName) {
        By createdPlayList = By.xpath("//section[@id='playlists']" +
                "//a[contains(text(), '" + playListName + "')]");
        actions.doubleClick(findElement(createdPlayList)).perform();
        typeAndSubmit(renamePlayListInputFiled, updatedPlayListName);
        return this;
    }

    public HomePage deletePlaylist(String playListName) {
        waitInvisibilityOfSuccess();
        actions.contextClick(findElement(By
                .xpath("//a[contains(text(), '" + playListName + "')]"))).perform();
        findElement(By
                .xpath("//li[contains(text(), 'Delete')]")).click();
        visibilityWait(succesShow);
        return this;
    }

    public HomePage selectAllSongList() {
        waitClickable(allSongLink).click();
        return this;
    }

    public HomePage contextClickFirstSong() {
        contextClick(firstSongElement);
        return this;
    }

    public HomePage contextClickPlaySong() { //select song and context click on it
        waitClickable(contextPlaySong).click();
        return this;
    }

    public HomePage songSearch(String songName) {
        waitVisibility(searchField);
        clearAndType(searchField, songName);
        return this;
    }

    public HomePage clickViewAllButton() {
        click(viewAllButton);
        return this;
    }

    public HomePage clickFirstSong() {
        click(firstSongElement);
        return this;
    }

    public HomePage selectFirstSongFromSearch() {
        click(firstSongFromSearch);
        return this;
    }

    public HomePage clickAddToButton() {
        click(addToButton);
        return this;
    }

    public HomePage selectPlaylist(String playListName) {
        click(By.xpath("//section[@id='songResultsWrapper']" +
                "//div[@data-test='add-to-menu']//li[contains(text(), '" + playListName + "')]"));
        return this;
    }

    public HomePage deleteAddedSong(String playListName, String songName) { //after method to clean playlist
        click(By.xpath("//section[@id='playlists']//a[contains(text(), '" + playListName + "')]"));
        delete(By.xpath("//section[@id='playlistWrapper']" +
                "//table[@class='items']//td[contains(text(), '" + songName + "')]"));
        return this;
    }

    public boolean isPlayListDisplayed(String name) {
        try {
            waitVisibility(By
                    .xpath("//a[contains(text(),'" + name + "')]"));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public PlayerComponent getPlayer() {
        visibilityWait(avatarIcon);
        return new PlayerComponent(driver);
    }

    public ProfilePage getProfile() {
        click(avatarIcon);
        return new ProfilePage(driver);
    }

    public HomePage addSongToPlaylist(String song, String playlist) {
        contextClick(By.xpath("//section[@data-testid='song-excerpts']" +
                "//span[contains(text(), '" + song + "')]"));
        hoverTo(By.cssSelector(".has-sub"));
        click(By.xpath("//ul[@class='menu submenu menu-add-to']" +
                "//li[contains(text(), '" + playlist + "')]"));
        return this;
    }
}
