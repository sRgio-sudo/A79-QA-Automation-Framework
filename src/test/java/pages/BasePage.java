package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.UUID;

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

    public String getPageUrl () {
        return driver.getCurrentUrl();
    }

    public static String generateRandomName() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 5);
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
    protected void waitInvisibilityOfSuccess() {
        try {
            WebElement clickToClose = driver.findElement(By.xpath("//div[@class='success show']"));
            clickToClose.click();
        } catch (Exception e) {
        }
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//div[@class='success show']")));
    }
//    protected WebElement checkSuccess() {
//
//        WebElement noticeMessage = wait.until(ExpectedConditions.visibilityOfElementLocated
//                (By.xpath("//div[@class='success show']")));
//        Assert.assertTrue(noticeMessage.isDisplayed());
//        return noticeMessage;
//    }

}

