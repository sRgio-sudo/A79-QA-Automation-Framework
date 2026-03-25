package utils;

import api.AuthApiClient;

public class TokenManager {

    private static String token;

    public static String getToken() {

        if (token == null) {
            AuthApiClient authApi = new AuthApiClient();

            token = authApi.login(
                    ConfigReader.getProperty("api.user.email"),
                    ConfigReader.getProperty("api.user.password")
            );
        }
        return token;
    }

    public static void resetToken(){
        token=null;
    }
}
