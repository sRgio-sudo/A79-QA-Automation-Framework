package models;

import utils.ConfigReader;

public class UserFactory {
    public static User mainUser() {
        return new User(
                ConfigReader.getProperty("user.email"),
                ConfigReader.getProperty("user.password")
        );
    }
    public static User testUser() {
        return new User(
                ConfigReader.getProperty("user.test.email"),
                ConfigReader.getProperty("user.test.password")
        );
    }
}
