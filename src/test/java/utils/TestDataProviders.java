package utils;

import drivers.BaseTest;
import org.testng.annotations.DataProvider;

public class TestDataProviders extends BaseTest {
    private static final String validEmail = ConfigReader.getProperty("user.email");
    private static final String validPassword = ConfigReader.getProperty("user.password");

    @DataProvider(name = "InvalidEmailData")
    public static Object[][] getInvalidEmailData() {
        return new Object[][]{
                {"invalidEmail", validPassword},
                {"invalid@email.io", validPassword},
                {"", validPassword},
        };
    }

    @DataProvider(name = "InvalidPasswordData")
    public static Object[][] getInvalidPasswordData() {
        return new Object[][]{
                {validEmail, "invalidPassword"},
                {validEmail, ""},
                {validEmail, "uIIgWoYu "},
        };
    }

    @DataProvider(name = "InvalidEmailRegistration")
    public static Object[][] getInvalidLoginEmails() {
        return new Object[][]{
                {"invalidDomain@yahoo.com", "Yahoo domain"},
                {EmailGenerator.generateGmailEmail(), "Gmail domain + alias"},
                {EmailGenerator.generatePlusEmail(), "+digits alias"},
                {EmailGenerator.generatePlusTextEmail(), "+text alias"},
                {"user@test.io", "Domain without 'pro'"},
                {"user@pro.io", "Domain without 'test'"},
                {"user@testproio", "Domain without dot"},
        };
    }

    @DataProvider(name = "CheckIncorrectCharachters")
    public static Object[][] getInvalidEmailCharachters() {
        return new Object[][]{
                {"user@ ", "Missing local part after @"},
                {"user@.io", "Domain without 'testpro'"},
                {"email@domain..com", "Domain with double dots"},
                {"automation_test testpro.io", "Missing @"},
                {"automation_test@testpro>io", "Using > instead ."},
        };
    }

    @DataProvider(name = "playListNames")
    public static Object[][] getPlayListNames() {
        return new Object[][]{
                {"Ab", false},
                {"AbC", true},
                {"1234567890", true},
                {"ABCDEFGHIJK", false},
                {"  ", false},
                {"Playlist 1", true},
                {"!@#$%^&*()", true},
                {"Плейлист1", true},
        };
    }
    @DataProvider(name = "newPasswordsSet")
    public static Object[][] getNewPasswordsSet() {
        return new Object[][]{
                {"Ab1!cd2#ef", true}, //10 symb. lower boundary
                {"Ab1!cd2#e", false}, //9 symb. below bound
                {"Ab1!cd2#ef3$gh4", true}, //15 symb. Upper bond
                {"Ab1!cd2#ef3$gh4z", false}, //16. Above bond
                {"newp@ss!234",false}, //all lower case
                {"NEWP@SS!234", false}, //all upper case
                {"NewPAssThre", false}, //no numbers
                {"82!23456789091", false}, //no letters
                {"12345678909821", false}, //only numbers
                {"A1!qwertyui", true}, // 1 spec character
        };
    }
}
