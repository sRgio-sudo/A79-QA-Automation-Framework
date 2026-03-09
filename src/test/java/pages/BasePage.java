package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.NoSuchElementException;
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

    public void visibilityWait(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected boolean waitInvisibility(By locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public void click(By locator) {
        waitClickable(locator).click();
    }

    public void contextClick(By locator) {
        actions.contextClick(findElement(locator)).perform();
    }

    public void delete(By locator) {
        waitClickable(locator).click();
        actions.sendKeys(Keys.DELETE).perform();
    }

    public void hoverTo(By locator) {
        actions.moveToElement(findElement(locator)).perform();
    }

    protected WebElement findElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public String getPageUrl() {
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
        } catch (NoSuchElementException ignored) {
        }
        waitInvisibility(By.xpath("//div[@class='success show']"));
    }

    public WebElement successMessage() {
        WebElement successMessage = waitVisibility(By
                        .xpath("//div[@class='success show']"));
        return successMessage;
    }

    public void selectDropDown(By locator, String text) {
        WebElement selectField = waitClickable(locator);
        Select select = new Select(selectField);
        select.selectByVisibleText(text);
    }

    public void doubleClick(By locator) {
        actions.doubleClick(findElement(locator)).perform();
    }

    public void safeClick(By locator) {
        try {
            waitClickable(locator).click();
        } catch (StaleElementReferenceException e) {
            waitClickable(locator).click();
        }
    }

    public void jsClick(By locator) {
        WebElement element = waitVisibility(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    public void forceClear(By locator) {
        WebElement element = waitClickable(locator);
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
    }
}

