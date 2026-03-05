package api;

import io.restassured.response.Response;

public class UserApi extends BaseApiClient {

    public Response getProfile(String token) {
        return request
                .header("Authorization", "Bearer " + token)
                .get("/api/me");
    }
}