package api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.PlaylistRequest;
import utils.TokenManager;

public class PlaylistApiClient extends BaseApiClient {
    private static final String PlaylistEndpoint = "/api/playlist";

    public Response getPlaylists(){
        return withAuth()
                .get(PlaylistEndpoint);
    }

    public Response getPlaylistAsSecondUser(){
        return withCustomToken(TokenManager.getTokenSecondAccount())
                .get(PlaylistEndpoint);
    }

    public Response getPlaylistWithToken(String token){
        return withCustomToken(token)
                .get(PlaylistEndpoint);
    }

    public Response createPlaylist(PlaylistRequest body){
        return withAuth()
                .contentType(ContentType.JSON)
                .log().all()
                .body(body)
                .post(PlaylistEndpoint);
    }
}
