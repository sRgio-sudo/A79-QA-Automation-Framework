import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class Homework17 extends BaseTest {
    @Test
    public void addSongToPlaylist() {
        navigatingToPage();
        provideEmail("sergei.trofimov@testpro.io");
        providePassword("uIIgWoYu");
        clickSubmit();
        songSearch("Samurai");
        driver.findElement(By.cssSelector("button[data-test='view-all-songs-btn']")).click();
        driver.findElement(By.xpath("//section[@id='songResultsWrapper']//table[@class='items']//tr[1]")).click();
        driver.findElement(By.cssSelector("button[data-test='add-to-btn']")).click();
        driver.findElement(By.xpath("//section[@id='songResultsWrapper']//div[@data-test='add-to-menu']//li[contains(text(), 'Playlist2')]")).click();
        WebElement succesIcon = driver.findElement(By.xpath("//div[@class='success show']"));
        String succesMessage = succesIcon.getText();
        Assert.assertEquals(succesMessage, "Added 1 song into \"Playlist2.\"");

    }
    @AfterMethod
    public void cleanUp() throws InterruptedException {
        Thread.sleep(3000);
        deleteAddedSong();
    }

    private void songSearch(String songName) {
        WebElement searchField = driver.findElement(By.xpath("//input[@name='q']"));
        searchField.clear();
        searchField.sendKeys(songName);
        searchField.click();
    }
}
