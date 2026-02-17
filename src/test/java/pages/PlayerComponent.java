package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PlayerComponent extends BasePage {
    private By playPauseButton = By.cssSelector("span[data-testid='play-btn']");
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

}
