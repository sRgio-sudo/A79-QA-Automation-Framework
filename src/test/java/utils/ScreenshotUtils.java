package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {

    private static final String SCREENSHOT_DIR = "screenshots/";

    public static void captureScreenshot(WebDriver driver, String testName) {

        try {

            new File(SCREENSHOT_DIR).mkdirs();

            String timestamp = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss")
                    .format(new Date());

            String fileName = testName + "_" + timestamp + ".png";

            File srcFile = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);

            BufferedImage image = ImageIO.read(srcFile);

            Graphics2D g2d = image.createGraphics();

            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            g2d.setColor(Color.RED);

            String text = "Test: " + testName + " | " + timestamp;

            g2d.drawString(text, 20, 40);

            g2d.dispose();

            File destFile = new File(SCREENSHOT_DIR + fileName);

            ImageIO.write(image, "png", destFile);

            System.out.println(">>> Screenshot saved: " + destFile.getAbsolutePath());
            System.out.println(">>> URL at failure: " + driver.getCurrentUrl());

        } catch (Exception e) {
            System.out.println(">>> Failed to capture screenshot: " + e.getMessage());
        }
    }
}
