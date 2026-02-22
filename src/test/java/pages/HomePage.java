package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {
    private By avatarIcon = By.xpath("//a[@data-testid='view-profile-link']");
    private By createPlayListButton = By.cssSelector("i[data-testid='sidebar-create-playlist-btn']");
    private By simplePlayListOption = By.cssSelector("li[data-testid='playlist-context-menu-create-simple']");
    private By playListNameInputFiled = By.cssSelector(".create input[name='name']");
    private By renamePlayListInputFiled = By.cssSelector("[data-testid='inline-playlist-name-input']");
    private By succesShow = By.xpath("//div[@class='success show']");
    private By allSongLink = By.cssSelector("a[href='#!/songs']");
    private By firstSongElement = By.xpath("//section[@id='songsWrapper']//table[@class='items']//tr[1]");
    private By contextPlaySong = By.xpath("//li[@class='playback']");

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
        wait.until(ExpectedConditions.visibilityOfElementLocated(succesShow));

        return this;
    }

    public HomePage selectAllSongList() {
        wait.until(ExpectedConditions
                        .elementToBeClickable(allSongLink))
                .click();
        return this;
    }

    public HomePage contextClickFirstSong() {
        contextClick(firstSongElement);
        return this;
    }

    public HomePage contextClickPlaySong() { //select song and context click on it
        wait.until(ExpectedConditions
                        .elementToBeClickable(contextPlaySong))
                .click();
        return this;
    }

//    public void deleteAddedSong() {
//        wait.until(ExpectedConditions.elementToBeClickable
//                        (By.xpath("//section[@id='playlists']//a[contains(text(), 'Playlist2')]")))
//                .click();
//        WebElement addedSong = wait.until
//                (ExpectedConditions.elementToBeClickable(
//                        By.xpath("//section[@id='playlistWrapper']" +
//                                "//table[@class='items']//td[contains(text(), 'HoliznaCC0 - Way Of The Samurai')]")));
//        addedSong.click();
//        Actions action = new Actions(driver);
//        action.sendKeys(Keys.DELETE).perform();
//        wait.until(ExpectedConditions.invisibilityOfElementLocated
//                (By.xpath("//div[@class='success show']")));
//    }
//protected void createPlaylist(String playListName) {
//    wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector
//                    ("i[data-testid='sidebar-create-playlist-btn']")))
//            .click();
//    wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector
//                    ("li[data-testid='playlist-context-menu-create-simple']")))
//            .click();
//
//    WebElement inputNewPlayListName = wait.until(ExpectedConditions.elementToBeClickable
//            (By.cssSelector(".create input[name='name']")));
//    inputNewPlayListName.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
//    inputNewPlayListName.sendKeys(playListName);
//    inputNewPlayListName.sendKeys(Keys.ENTER);
//}

    public boolean isPlayListDisplayed(String name) {
        By playlist = By.xpath("//a[contains(text(),'" + name + "')]");
        return findElement(playlist).isDisplayed();
    }

    public PlayerComponent getPlayer() {
        return new PlayerComponent(driver);
    }

    public ProfilePage getProfile() {
        click(avatarIcon);
        return new ProfilePage(driver);
    }


//    protected void deletePlaylist(String playListName) {
//        waitInvisibilityOfSuccess();
//        WebElement playListContext = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//section[@id='playlists']" +
//                "//a[contains(text(), '" + playListName + "')]")));
//        actions.contextClick(playListContext).perform();
//        wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//li[contains(text(), 'Delete')]"))).click();
//    }
//
//
//    protected void doubleClickOnPlaylist(String playListName) {
//        WebElement playListRenamer = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("//a[contains(text(),'" + playListName + "')]")));
//        actions.doubleClick(playListRenamer).perform();
//    }
//protected void clickOnAvatarIcon() {
//
//    WebElement avatarIcon = wait.until(ExpectedConditions
//            .visibilityOfElementLocated(By.xpath("//a[@data-testid='view-profile-link']")));
//    avatarIcon.click();
//
//}
}
