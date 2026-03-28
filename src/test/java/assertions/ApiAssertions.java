package assertions;

import io.restassured.response.Response;
import org.testng.Assert;

public class ApiAssertions {
    public static void assertStatusCode(Response response, int expectedStatusCode) {
        Assert.assertEquals(response.statusCode(), expectedStatusCode, "Unexpected status code");
    }
    public static void assertBodyContent(Response response, String expectedText) {
        String body = response.asString();
        Assert.assertTrue(body.contains(expectedText), "Unexpected body content");
    }
    public static void assertCookieNotEmpty(Response response, String cookieName) {
        String cookieValue = response.getCookie(cookieName);
        Assert.assertTrue(cookieValue != null && !cookieValue.isEmpty(),
                "Cookie " + cookieName + " is empty");
    }
    public static void assertJsonValue(Response response, String jsonPath, String expectedValue) {
        String actualValue = response.jsonPath().getString(jsonPath);
        Assert.assertEquals(actualValue, expectedValue, "Unexpected json path value");
    }
}
