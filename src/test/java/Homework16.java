import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class Homework16 extends BaseTest{
    @Test
    public void registrationNavigation(){

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
//        options.addArguments("--headless=new");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        String url = "https://qa.koel.app/";
        driver.get(url);

        WebElement registrationLink = driver.findElement(By.cssSelector("a[href='registration']"));
        registrationLink.click(); // can also use var2
        WebElement submitButton = driver.findElement(By.xpath("//input[@id='button']"));
        String buttonText = submitButton.getAttribute("value");
        Assert.assertEquals(buttonText, "Submit");
        //Var 2 (verify via URL):
//        String currentUrl = driver.getCurrentUrl();
//        Assert.assertEquals(currentUrl, "https://qa.koel.app/registration");

        driver.quit();
    }
}

