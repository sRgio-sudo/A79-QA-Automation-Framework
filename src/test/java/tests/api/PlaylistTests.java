package tests.api;

import api.PlaylistApiClient;
import api.UserApiClient;
import assertions.ApiAssertions;
import assertions.PlaylistAssertions;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import models.PlaylistRequest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import utils.TestDataProviders;
import utils.TokenManager;

public class PlaylistTests {
    private UserApiClient userApiClient;
    private PlaylistApiClient playlistApiClient;

    @BeforeSuite
    public void globalSetup() {
        TokenManager.resetToken();
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @BeforeClass
    public void setUp() {
        userApiClient = new UserApiClient();
        playlistApiClient = new PlaylistApiClient();
    }

    @Test(description = "Koel | API | Get user playlists list")
    public void getPlayilstFromUser() {
        Response response = playlistApiClient.getPlaylists();
        ApiAssertions.assertStatusCode(response, 200);
        PlaylistAssertions.checkPlaylistStructure(response);
    }

    @Test(description = "Koel | API | User do not have created playlists")
    public void getEmptyPlaylistFromUser() {
        Response response = playlistApiClient.getPlaylistAsSecondUser();
        ApiAssertions.assertStatusCode(response, 200);
        PlaylistAssertions.checkPlaylistIsEmpty(response);
    }

    @Test(description = "Koel | API | Get users playlist with incorrect token")
    public void getPlaylistWithIncorrectToken() {
        Response response = playlistApiClient.getPlaylistWithToken("this_is_a_fake_token_123");
        ApiAssertions.assertStatusCode(response, 401);
        ApiAssertions.assertBodyContent(response, "Unauthorized");
    }

    @Test(description = "Koel | API | Create Playlist with valid name")
    public void createPlaylistWithValidName() {
        String playlistName = "API Test Playlist" + System.currentTimeMillis();
        PlaylistRequest playlistBody = new PlaylistRequest(playlistName);
        Response response = playlistApiClient.createPlaylist(playlistBody);
        ApiAssertions.assertStatusCode(response, 200);
        PlaylistAssertions.checkPlaylistName(response, playlistName);
    }

    @Test(description = "Koel | API | User is able to create playlist with duplicate name")
    public void createPlaylistWithDuplicateName() {
        String playlistName = "API Test Duplicate";
        String secondPlaylistName = "API Test Duplicate";
        //create 1st Playlist
        PlaylistRequest playlistBody = new PlaylistRequest(playlistName);
        Response response1 = playlistApiClient.createPlaylist(playlistBody);
        ApiAssertions.assertStatusCode(response1, 200);
        PlaylistAssertions.checkIdExist(response1);
        String firstId = response1.jsonPath().getString("id");
        //create 2nd playlist
        PlaylistRequest secondPlaylistBody = new PlaylistRequest(secondPlaylistName);
        Response response2 = playlistApiClient.createPlaylist(secondPlaylistBody);
        ApiAssertions.assertStatusCode(response2, 200);
        PlaylistAssertions.checkIdExist(response2);
        //Check Id is not same
        PlaylistAssertions.checkPlaylistId(response2, firstId);
    }

    @Test(dataProvider = "ApiPlaylistName", dataProviderClass = TestDataProviders.class,
            description = "Koel | API | Playlist names validation")
    public void validatePlaylistName(String playlistName, int expectedStatusCode) {
        PlaylistRequest body = new PlaylistRequest(playlistName);
        Response response = playlistApiClient.createPlaylist(body);
        ApiAssertions.assertStatusCode(response, expectedStatusCode);
        if (response.getStatusCode() == 200) {
            PlaylistAssertions.checkPlaylistName(response, playlistName);
            PlaylistAssertions.checkIdExist(response);
        }
    }
}
