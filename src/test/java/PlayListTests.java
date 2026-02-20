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
        HomePage homePage = new LoginPage(driver)
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
}


