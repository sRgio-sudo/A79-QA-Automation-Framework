package utils;

import api.AuthApi;

public class TokenManager {

    private static String token;

    public static String getToken() {

        if (token == null) {
            AuthApi authApi = new AuthApi();

            token = authApi.login(
                    ConfigReader.getProperty("api.user.email"),
                    ConfigReader.getProperty("api.user.password")
            );
        }

        return token;
    }
}
