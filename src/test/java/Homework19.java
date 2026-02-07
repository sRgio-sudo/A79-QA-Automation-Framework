import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Homework19 extends BaseTest {
    @Test
    public void deletePlaylist() throws InterruptedException {
        navigatingToPage();
        provideEmail(validEmail);
        providePassword(validPassword);
        clickSubmit();
        createPlaylist();
        Thread.sleep(5000);
        driver.findElement(By.xpath("//section[@id='playlists']//a[contains(text(), 'playListToDelete')]")).click();
        driver.findElement(By.xpath("//button[@class='del btn-delete-playlist']")).click();
        Thread.sleep(2000);
        WebElement playListDeleted = driver.findElement(By.xpath("//div[@class='success show']"));
        String playListDeletedText = playListDeleted.getText();
        Assert.assertEquals(playListDeletedText.toLowerCase(), "deleted playlist \"playlisttodelete.\"");

    }

    private void createPlaylist() {
        driver.findElement(By.cssSelector("i[data-testid='sidebar-create-playlist-btn']")).click();
        driver.findElement(By.cssSelector("li[data-testid='playlist-context-menu-create-simple']")).click();
        WebElement newPlayListName = driver.findElement(By.cssSelector(".create input[name='name']"));
        newPlayListName.click();
        newPlayListName.clear();
        newPlayListName.sendKeys("playListToDelete");
        newPlayListName.sendKeys(Keys.ENTER);
    }

}
