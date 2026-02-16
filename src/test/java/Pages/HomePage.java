package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {
    private By avatarIcon = By.xpath("//img[@class='avatar']");
    public By createPlayListButton = By.cssSelector("i[data-testid='sidebar-create-playlist-btn']");
    public By simplePlayListOption = By.cssSelector("li[data-testid='playlist-context-menu-create-simple']");
    public By playListNameInputFiled = By.cssSelector(".create input[name='name']");
    public By renamePlayListInputFiled = By.cssSelector("[data-testid='inline-playlist-name-input']");
    public By succesShow = By.xpath("//div[@class='success show']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public boolean isAvatarDisplayed() {
        return findElement(avatarIcon).isDisplayed();
    }

    public HomePage createPlaylist(String playListName) {
        findElement(createPlayListButton).click();
        findElement(simplePlayListOption).click();
        typeAndSubmit(playListNameInputFiled, playListName);
        return this;
    }

    public HomePage renamePlaylist(String playListName, String updatedPlayListName) {
        By createdPlayList = By.xpath("//section[@id='playlists']" +
                "//a[contains(text(), '" + playListName + "')]");
        actions.doubleClick(findElement(createdPlayList)).perform();
        typeAndSubmit(renamePlayListInputFiled, updatedPlayListName);
        return this;
    }

    public HomePage deletePlaylist(String playListName) {
        waitInvisibilityOfSuccess();
        actions.contextClick(findElement(By
                .xpath("//a[contains(text(), '" + playListName + "')]"))).perform();
        findElement(By
                .xpath("//li[contains(text(), 'Delete')]")).click();

        return this;
    }

    private void waitInvisibilityOfSuccess() {

        try {
            WebElement clickToClose = driver.findElement(By.xpath("//div[@class='success show']"));
            clickToClose.click();
        } catch (Exception e) {
        }
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//div[@class='success show']")));
    }

    public boolean isPlayListDisplayed(String name) {
        By playlist = By.xpath("//a[contains(text(),'" + name + "')]");
        return findElement(playlist).isDisplayed();
    }
}
