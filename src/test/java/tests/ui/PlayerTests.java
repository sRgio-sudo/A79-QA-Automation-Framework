package tests.ui;

import drivers.DriverManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.PlayerComponent;
import drivers.BaseTest;

public class PlayerTests extends BaseTest {
    @Test
    public void playAnySong() {
        PlayerComponent playerComponent = new LoginPage(DriverManager.getDriver())
                .openPage()
                .loginAsValidUser()
                .getPlayer();
        playerComponent
                .hoverPlayButton()
                .selectNextSong()
                .clickPlayButton();
        Assert.assertTrue(playerComponent.soundbarCheck());
    }
    @Test
    public void hoverCheck() {
        PlayerComponent playerComponent = new LoginPage(DriverManager.getDriver())
                .openPage()
                .loginAsValidUser()
                .getPlayer();
        playerComponent
                .hoverPlayButton();
        String playButtonTooltip = playerComponent.getPlayButtonTitle();
        Assert.assertEquals(playButtonTooltip, "Play or resume");
    }
}