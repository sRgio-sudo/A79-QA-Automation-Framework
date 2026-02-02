package locators;

import org.openqa.selenium.By;

public class KoelSelectors {

    // 1) Login page
    public static By emailField = By.xpath("//input[@type='email']");
    public static By passwordField = By.cssSelector("[type='password']");
    public static By loginButton = By.xpath("//button[@type='submit']");
    public static By registrationLink = By.cssSelector("a[href='registration']");

    // 2) Registration page
    public static By submitButton = By.xpath("//input[@type='submit']");

    // 2) Main page
    String userProfileLinkLocator = "[data-testid='view-profile-link']";
    String homeLinkLocator = "a[href='#!/home']";
    String albumsLinkLocator = "a[href='#!/albums']";
    String artistsLinkLocator = "a[href='#!/artists']";
    String createNewPlaylistButtonLocator = "[data-testid='sidebar-create-playlist-btn']";
    String playPauseButtonLocator = ".album-thumb-wrapper [role='button']"; //*
    String playPreviousSongButtonLocator = "[data-testid='play-prev-btn']";
    String playNextSongButtonLocator = "[data-testid='play-next-btn']";
    String playlistShuffleAllSongsButtonLocator = "#playlistWrapper [data-test*='-all']"; //*
    String deletePlayListButtonLocator = ".del.btn-delete-playlist";
    String changeRepeatModeButtonLocator = "[data-testid='repeat-mode-switch']";
    String currentPasswordFieldLocator = "#inputProfileCurrentPassword";
    String newPasswordFieldLocator = "#inputProfileNewPassword";
    String inputProfileNameFieldLocator = "#inputProfileName";
    String saveChangesButtonLocator = ".btn-submit";
    String selectCatThemeLocator = ".themes [data-testid='theme-card-cat']";


    String logoutButtonLocator = "[data-testid='btn-logout']";

    //        String logoutButtonLocator = "[class='fa fa-sign-out']"; // locator 4 small button
    String searchFieldLocator = "[name='q']";
    //        String favoritesLocator = ".playlist.favorites"; // not unique (1 of 2 in console)
    String favoritesLocator = "a[href='#!/favorites']";

    String allSongsMenuItemLocator = "a[href='#!/songs']";
    String firstUserPlaylistLocator = "#playlistWrapper .song-item:nth-child(1)";
    String soundBarPlayIconLocator = "[data-testid='toggle-visualizer-btn']";
    String newPlaylistButtonLocator = "//section[@id='playlists']//i[@role='button']";
    String allSongsButtonLocator = "//a[@href='#!/songs']";
    String firstMostPlayedSongLocator = "(//ol[@class='top-song-list']//article[@data-test='song-card'])[1]";
    String allSongsMenuLocator = "//a[@href='#!/songs']";
    String albumsViewAsThumbnails = "//section[@id='albumsWrapper']//label[@title='View as thumbnails']";
    String albumsViewAsList = "//section[@id='albumsWrapper']//label[@title='View as list']";
    String songsQueueCounterLocator = "//section[@id='queueWrapper']//span[@data-test='list-meta']";
    String firstSongHeartBtnLocator = "(//section[@id='songsWrapper']//i[@data-test='btn-like-unliked'])[1]";
    String mainShuffleBtnLocator = "//section[@id='songsWrapper']//button[@data-test='btn-shuffle-all']";
    String playlistNameLocator = "//section[@id='playlistWrapper']//h1";
    String cancelCreateSmartPLlBtnLocator = "//form[@data-testid='create-smart-playlist-form']//button[@class='btn-cancel']";
    String playerSeekLine = "//*[@class='plyr__progress--seek']";

    // Homework #13 - Xpath locators


//    String logoutButtonLocator = "//a[@data-testid='btn-logout']"; //div[@class='header-right']
//    String searchFieldLocator = "//input[@name='q']"; //input[@type='search']
//    String favoritesLocator = "//a[@href='#!/favorites']"; //section[@id='playlists']//li[@class='playlist favorites']


}
