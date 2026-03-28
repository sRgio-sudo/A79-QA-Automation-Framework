package assertions;

import io.restassured.response.Response;

public class LoginAssertions extends ApiAssertions {
    public static void loginSuccess(Response response) {
        assertStatusCode(response, 200);
        assertBodyContent(response, "token");
    }

    public static void loginFail(Response response) {
        assertStatusCode(response, 401);
        assertBodyContent(response, "Invalid credentials");
    }

    public static void loginEmpty(Response response) {
        assertStatusCode(response, 422);
        assertBodyContent(response, "field is required");
    }

    public static void profileReturned(Response response) {
        assertStatusCode(response, 200);
        assertBodyContent(response, "id");
    }

    public static void checkCookies(Response response) {
        assertCookieNotEmpty(response, "koel_session");
    }

    public static void loginCorrectProfile(Response response, String expectedEmail) {
        assertStatusCode(response, 200);
        assertJsonValue(response, "email", expectedEmail);
    }
}
