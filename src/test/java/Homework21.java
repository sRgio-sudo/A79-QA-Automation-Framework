import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Homework21 extends BaseTest {
    @Test
    public void renamePlaylist() {

        String playListName = generateRandomName();
        String updatedPlayListName = "playListToRename";

        navigatingToPage();
        provideEmail(validEmail);
        providePassword(validPassword);
        clickSubmit();
        createPlaylist(playListName);
        doubleClickOnPlaylist(playListName);
        enterNewPlayListName(updatedPlayListName);

        WebElement renamedPlaylist = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(text(),'" + updatedPlayListName + "')]")));
        Assert.assertTrue(renamedPlaylist.isDisplayed());

        deletePlaylist(updatedPlayListName);
    }

    private void enterNewPlayListName(String newPlayListName) {
        WebElement playListInputField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-testid='inline-playlist-name-input']")));
        playListInputField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        playListInputField.sendKeys(newPlayListName);
        playListInputField.sendKeys(Keys.ENTER);
    }

}

