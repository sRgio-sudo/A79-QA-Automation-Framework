package api;

import io.restassured.response.Response;

public class UserApiClient extends BaseApiClient {

    public Response getProfile() {
        return withAuth()
                .get("/api/me");
    }
}