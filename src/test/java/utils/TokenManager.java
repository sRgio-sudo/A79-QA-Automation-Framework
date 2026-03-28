package utils;

import api.AuthApiClient;

public class TokenManager {

    private static String token;
    private static String tokenSecondUser;

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

    public static String getTokenSecondAccount() {

        if (tokenSecondUser == null) {
            AuthApiClient authApi = new AuthApiClient();

            tokenSecondUser = authApi.login(
                                        ConfigReader.getProperty("user.test.email"),
                    ConfigReader.getProperty("user.test.password")
            );
        }
        return tokenSecondUser;
    }
}
