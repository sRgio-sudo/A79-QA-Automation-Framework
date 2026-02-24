import org.testng.Assert;
import org.testng.annotations.Test;
import pages.BasePage;
import pages.HomePage;
import pages.LoginPage;
import pages.PlayerComponent;

public class PlayListTests extends BaseTest {
    @Test
    public void playSongFromAllSongList() {
        PlayerComponent player = new PlayerComponent(getDriver());
        HomePage homePage = new LoginPage(getDriver())
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
        HomePage homePage = new LoginPage(getDriver())
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
        HomePage homePage = new LoginPage(getDriver())
                .openPage()
                .loginAsValidUser()
                .songSearch(songToAdd)
                .clickViewAllButton()
                .selectFirstSongFromSearch()
                .clickAddToButton()
                .selectPlaylist(playList);
        String getSuccessMessage = homePage
                .successMessage()
                .getText();
        Assert.assertTrue(getSuccessMessage.contains("Added"));
        Assert.assertTrue(getSuccessMessage.contains(playList));
        homePage.deleteAddedSong(playList,songToAdd)
                .successMessage();
    }

    //    @Test
//    public void checkPlaylist() {
//        String playListName = "PlayListToDelete";
////        navigatingToPage();
////        provideEmail(validEmail);
////        providePassword(validPassword);
////        clickSubmit();
//        createPlaylist(playListName);
//        deletePlaylist(playListName);
//
//        WebElement playListDeleted = wait.until(ExpectedConditions.visibilityOfElementLocated
//                (By.xpath("//div[@class='success show']")));
//        String playListDeletedText = playListDeleted.getText().toLowerCase();
//
//        Assert.assertTrue(playListDeletedText.contains("deleted playlist"));
//        Assert.assertTrue(playListDeletedText.contains(playListName.toLowerCase()));
//    }
//
//    @Test
//    public void renamePlaylist() { //needs to speed-up (22sec)
//        String playListName = generateRandomName();
//        String updatedPlayListName = "playListToRename";
////
////        navigatingToPage();
////        provideEmail(validEmail);
////        providePassword(validPassword);
////        clickSubmit();
//        createPlaylist(playListName);
//        waitInvisibilityOfSuccess();
//        doubleClickOnPlaylist(playListName);
//        enterNewPlayListName(updatedPlayListName);
//
//        Assert.assertTrue(checkSuccess().getText().contains(updatedPlayListName));
//        deletePlaylist(updatedPlayListName);
//    }
//
//    private void enterNewPlayListName(String newPlayListName) {
//        WebElement playListInputField = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.cssSelector("[data-testid='inline-playlist-name-input']")));
//        playListInputField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
//        playListInputField.sendKeys(newPlayListName);
//        playListInputField.sendKeys(Keys.ENTER);
//    }
//
//    @Test
//    public void countSongInPlaylist() {
////        navigatingToPage();
////        provideEmail(validEmail);
////        providePassword(validPassword);
////        clickSubmit();
//        choosePlayListByName("Playlist1");
//        displayAllSongs();
//        Assert.assertTrue(getPlayListDetails().contains(String.valueOf(countSongs())));
//    }
//
//    private void choosePlayListByName(String Playlist1) {
//        wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("//a[contains(text(),'" + Playlist1 + "')]"))).click();
//    }
//
//    public int countSongs() {
//        return driver.findElements(By.xpath(
//                "//section[@id='playlistWrapper']//td[@class='title']")).size();
//    }
//
//    public String getPlayListDetails() {
//        return driver.findElement(By.xpath(
//                "//span[@class='meta text-secondary']/span[@class='meta']")).getText();
//    }
//
//    public void displayAllSongs() {
//        List<WebElement> songsList = driver.findElements(
//                By.xpath("//section[@id='playListWrapper']//td[@class='title']"));
////            By.xpath("//section[@id='songsWrapper']//td[@class='title']"));
//        System.out.println("Number of songs" + countSongs());
//        for (WebElement e : songsList) {
//            System.out.println(e.getText());
//        }
//    }
}


