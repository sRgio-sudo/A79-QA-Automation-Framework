package assertions;

import io.restassured.response.Response;
import org.testng.Assert;

import java.util.List;

import static org.hamcrest.Matchers.hasKey;

public class PlaylistAssertions extends ApiAssertions {

    public static void checkPlaylistStructure(Response response) {
        assertStatusCode(response, 200);
        response.then().body("[0]", hasKey("id"))
                .body("[0]", hasKey("name"));
    }

    public static void checkPlaylistIsEmpty(Response response) {
        List<Object> list = response.jsonPath().getList("");
        Assert.assertTrue(list.isEmpty(), "Playlist is not empty");
    }

    public static void checkPlaylistName(Response response, String expectedName) {
        String actualName = response.jsonPath().getString("name");
        Assert.assertEquals(actualName, expectedName, "Playlist name is incorrect");
    }

    public static void checkIdExist(Response response) {
        Object playlistId = response.jsonPath().get("id");
        Assert.assertNotNull(playlistId, "Playlist id is empty");
    }

    public static void checkPlaylistId(Response response, String expectedId) {
        String actualId = response.jsonPath().getString("id");
        Assert.assertNotEquals(actualId, expectedId, "Playlist id is equal");
    }
}
