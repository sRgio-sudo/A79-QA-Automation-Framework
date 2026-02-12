import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PlayerTest extends BaseTest {
    @Test
    public void playAnySong() {
        navigatingToPage();
        provideEmail(validEmail);
        providePassword(validPassword);
        clickSubmit();
        selectNextSong();
        clickPlayButton();
        Assert.assertTrue(songPlayingCheck());
    }

    @Test
    public void playSongFromAllSongList() {
        navigatingToPage();
        provideEmail(validEmail);
        providePassword(validPassword);
        clickSubmit();
        chooseAllSongList();
        contextClickFirstSong();
        clickPlaySong();
        Assert.assertTrue(songPlayingCheck());
    }

    @Test
    public void hoverCheck() {
        navigatingToPage();
        provideEmail(validEmail);
        providePassword(validPassword);
        clickSubmit();
        Assert.assertTrue(hoverPlayButton().isDisplayed());
    }

    public WebElement hoverPlayButton() {
        WebElement playButton = driver.findElement(By.cssSelector("span[data-testid='play-btn']"));
        actions.moveToElement(playButton).perform();
        return wait.until(ExpectedConditions.visibilityOf(playButton));
    }

    private void clickPlaySong() {
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[@class='playback']"))).click();
    }

    private void contextClickFirstSong() {
        WebElement firstSongElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath
                ("//section[@id='songsWrapper']//table[@class='items']//tr[1]")));
        actions.contextClick(firstSongElement).perform();
    }
}
