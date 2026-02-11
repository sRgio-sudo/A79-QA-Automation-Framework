import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PlayListTest extends BaseTest {

    @Test
    public void checkPlaylist() {
        String playListName = "PlayListToDelete";

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
}
