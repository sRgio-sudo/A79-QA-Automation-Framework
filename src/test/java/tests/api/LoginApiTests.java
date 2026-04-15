package tests.api;

import api.AuthApiClient;
import api.UserApiClient;
import assertions.LoginAssertions;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ConfigReader;

public class LoginApiTests {
    private AuthApiClient authApiClient;
    private UserApiClient userApiClient;

    @BeforeClass
    public void setUp() {
        authApiClient = new AuthApiClient();
        userApiClient = new UserApiClient();
    }

    @Test(description = "Koel | API | Login with empty password field")
    public void loginWithEmptyPassword() {
        Response response = authApiClient.loginRaw(
                "  ",
                ConfigReader.getProperty("user.password")
        );
        LoginAssertions.loginEmpty(response);
    }

    @Test(description = "Koel | API | Login with invalid password")
    public void loginWithInvalidPassword() {
        Response response = authApiClient.loginRaw(
                ConfigReader.getProperty("user.email"),
                "wR0NgPaSs1!"
        );
        LoginAssertions.loginFail(response);
    }

    @Test(description = "Koel | API | Login with invalid email")
    public void loginWithInvalidEmail() {
        Response response = authApiClient.loginRaw(
                "fake.user@email.com",
                ConfigReader.getProperty("user.password")
        );
        LoginAssertions.loginFail(response);
    }

    @Test (description = "Koel | API | POST Login TO /api/me with valid credentials")
    public void apiValidLogin() {
        Response response = authApiClient.loginRaw(
                ConfigReader.getProperty("user.email"),
                ConfigReader.getProperty("user.password")
        );
        LoginAssertions.loginSuccess(response);
        LoginAssertions.checkCookies(response);
    }
    @Test(description = "Koel | API | Post-Login Check")
    public void apiValidLoginCheck() {
        String expectedEmail = ConfigReader.getProperty("user.email");
        Response response = userApiClient.getProfile();
        LoginAssertions.loginCorrectProfile(response, expectedEmail);
    }
}
