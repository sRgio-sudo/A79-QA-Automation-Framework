import pages.BasePage;
import pages.HomePage;
import pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PlayListTests extends BaseTest {
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
    //    @Test
//    public void playSongFromAllSongList() {

    /// /        navigatingToPage();
    /// /        provideEmail(validEmail);
    /// /        providePassword(validPassword);
    /// /        clickSubmit();
//        chooseAllSongList();
//        contextClickFirstSong();
//        clickPlaySong();
//        Assert.assertTrue(songPlayingCheck());
//    }
    //
//    private void clickPlaySong() { //select song and context click on it
//        wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//li[@class='playback']"))).click();
//    }
//
//    private void contextClickFirstSong() {
//        WebElement firstSongElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath
//                ("//section[@id='songsWrapper']//table[@class='items']//tr[1]")));
//        actions.contextClick(firstSongElement).perform();
//    }
    //    @Test
//    public void addSongToPlaylist() {
////        navigatingToPage();
////        provideEmail(validEmail);
////        providePassword(validPassword);
////        clickSubmit();
//        songSearch("Samurai");
//        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector
//                ("button[data-test='view-all-songs-btn']"))).click();
//        wait.until(ExpectedConditions.elementToBeClickable(By.xpath
//                ("//section[@id='songResultsWrapper']//table[@class='items']//tr[1]"))).click();
//        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector
//                ("button[data-test='add-to-btn']"))).click();
//        wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//section[@id='songResultsWrapper']" +
//                "//div[@data-test='add-to-menu']//li[contains(text(), 'Playlist2')]"))).click();
//        WebElement succesIcon = driver.findElement(By.xpath("//div[@class='success show']"));
//        String succesMessage = succesIcon.getText();
//        Assert.assertEquals(succesMessage, "Added 1 song into \"Playlist2.\"");
//
//    }
//    @AfterMethod
//    public void cleanUp() {
//        deleteAddedSong();
//    }
//
//    private void songSearch(String songName) {
//        WebElement searchField = driver.findElement(By.xpath("//input[@name='q']"));
//        searchField.click();
//        searchField.clear();
//        searchField.sendKeys(songName);
//    }
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


