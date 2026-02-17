package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.actions = new Actions(driver);
    }
    public void click(By locator) {
        findElement(locator).click();
    }
    public void doubleClick(By locator) {
        actions.doubleClick(findElement(locator)).perform();
    }

    protected WebElement findElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void clearAndType(By locator, String text) {
        WebElement element = findElement(locator);
        element.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        element.sendKeys(text);
    }

    protected void typeAndSubmit(By locator, String text) {
        WebElement element = findElement(locator);
        element.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        element.sendKeys(text);
        element.sendKeys(Keys.ENTER);
    }
}

