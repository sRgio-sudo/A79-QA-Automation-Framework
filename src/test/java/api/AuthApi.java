package api;

import java.util.Map;

public class AuthApi extends BaseApiClient {

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
}
