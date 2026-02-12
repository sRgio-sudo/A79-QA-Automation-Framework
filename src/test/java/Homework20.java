import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class Homework20 extends BaseTest {

    @Test
    public void checkPlaylist() {
        String playListName = "PlayListToDelete";
        navigatingToPage();
        provideEmail(validEmail);
        providePassword(validPassword);
        clickSubmit();
        createPlaylist(playListName);
        deletePlaylist(playListName);

        WebElement playListDeleted = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//div[@class='success show']")));
        String playListDeletedText = playListDeleted.getText().toLowerCase();

        Assert.assertTrue(playListDeletedText.contains("deleted playlist"));
        Assert.assertTrue(playListDeletedText.contains(playListName.toLowerCase()));
    }

    @Test
    public void renamePlaylist() { //needs to speed-up (22sec)
        String playListName = generateRandomName();
        String updatedPlayListName = "playListToRename";

        navigatingToPage();
        provideEmail(validEmail);
        providePassword(validPassword);
        clickSubmit();
        createPlaylist(playListName);
        waitInvisibilityOfSuccess();
        doubleClickOnPlaylist(playListName);
        enterNewPlayListName(updatedPlayListName);

        Assert.assertTrue(checkSuccess().getText().contains(updatedPlayListName));
        deletePlaylist(updatedPlayListName);
    }

    private void enterNewPlayListName(String newPlayListName) {
        WebElement playListInputField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-testid='inline-playlist-name-input']")));
        playListInputField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        playListInputField.sendKeys(newPlayListName);
        playListInputField.sendKeys(Keys.ENTER);
    }

    @Test
    public void countSongInPlaylist() {
        navigatingToPage();
        provideEmail(validEmail);
        providePassword(validPassword);
        clickSubmit();
        choosePlayListByName("Playlist1");
        displayAllSongs();
        Assert.assertTrue(getPlayListDetails().contains(String.valueOf(countSongs())));
    }

    private void choosePlayListByName(String Playlist1) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(text(),'" + Playlist1 + "')]"))).click();
    }

    public int countSongs() {
        return driver.findElements(By.xpath(
                "//section[@id='playlistWrapper']//td[@class='title']")).size();
    }

    public String getPlayListDetails() {
        return driver.findElement(By.xpath(
                "//span[@class='meta text-secondary']/span[@class='meta']")).getText();
    }

    public void displayAllSongs() {
        List<WebElement> songsList = driver.findElements(
                By.xpath("//section[@id='playListWrapper']//td[@class='title']"));
//            By.xpath("//section[@id='songsWrapper']//td[@class='title']"));
        System.out.println("Number of songs" + countSongs());
        for (WebElement e : songsList) {
            System.out.println(e.getText());
        }
    }
}
