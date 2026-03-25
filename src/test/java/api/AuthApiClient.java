package api;

import io.restassured.response.Response;

import java.util.Map;

public class AuthApiClient extends BaseApiClient {

    public String login(String email, String password) {

        return request
                .body(Map.of(
                        "email", email,
                        "password", password
                ))
                .post("/api/me")
                .then()
                .statusCode(200)
                .extract()
                .path("token");
    }

    public Response loginRaw(String email, String password) {
        return request.body(Map.of(
                "email", email,
                "password", password
        ))
                .post("/api/me");
    }
}
