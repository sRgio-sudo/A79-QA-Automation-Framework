package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PlayerComponent extends BasePage {
    private By playPauseButton = By.xpath("//footer[@id='mainFooter']//span[@role='button']");
    private By playNextButton = By.cssSelector("[data-testid='play-next-btn']");
    private By soundbarComponent = By.cssSelector("div[data-testid='sound-bar-play']");

    public PlayerComponent(WebDriver driver) {
        super(driver);
    }

    public PlayerComponent hoverPlayButton() {
        WebElement playButton = driver.findElement(playPauseButton);
        actions.moveToElement(playButton).perform();
        return this;
    }

    public String getPlayButtonTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(playPauseButton))
                .getAttribute("title");
    }

    public PlayerComponent clickPlayButton() {
        waitVisibility(playPauseButton).click();
        return this;
    }

    public PlayerComponent selectNextSong() {
        waitVisibility(playNextButton).click();
        return this;
    }

    public boolean soundbarCheck() {
        return waitVisibility(soundbarComponent).isDisplayed();
    }

}
