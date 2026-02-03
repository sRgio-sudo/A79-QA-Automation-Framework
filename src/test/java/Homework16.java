import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Homework16 extends BaseTest {
    @Test
    public void registrationNavigation() {

        navigatingToPage();

        WebElement registrationLink = driver.findElement(By.cssSelector("a[href='registration']"));
        registrationLink.click();
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, "https://qa.koel.app/registration");


    }
}

