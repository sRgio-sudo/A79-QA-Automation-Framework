import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Homework19 extends BaseTest {
    @Test (enabled = false, description = "TurnedOff because using Thread.sleep method. " +
            "Explicit wait logic were applied in 'Homework20.java'")
    public void checkPlaylist() throws InterruptedException {
        navigatingToPage();
        provideEmail(validEmail);
        providePassword(validPassword);
        clickSubmit();
        createPlaylist("PlayListToDelete");
            Thread.sleep(5000);
        driver.findElement(By.xpath("//section[@id='playlists']//a[contains(text(), 'playListToDelete')]")).click();
            Thread.sleep(2000);
        deletePlaylist("PlayListToDelete");

        WebElement playListDeleted = driver.findElement(By.xpath("//div[@class='success show']"));
        String playListDeletedText = playListDeleted.getText();
        Assert.assertEquals(playListDeletedText.toLowerCase(), "deleted playlist \"playlisttodelete.\"");

    }

}
