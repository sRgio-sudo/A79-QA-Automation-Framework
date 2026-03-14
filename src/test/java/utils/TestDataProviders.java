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

    @DataProvider(name = "playListNames")
    public static Object[][] getPlayListNames() {
        return new Object[][]{
                {"Ab", false},
                {"AbC", true},
                {"123456789A", true},
                {"1234567890A", false},
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

    @DataProvider(name = "RegistrationNegativeScenarios")
    public Object[][] registrationNegativeScenarios() {
        return new Object[][] {
                { "testtestpro.io", "Email without @ symbol" },
                { "test@testproio", "Email without dot symbol" },
                { "test@gmail.com", "Email without @testpro.io domain" },
                { "test+@testpro.io", "Email with + symbol before @" }
        };
    }

    @DataProvider(name = "EmailChangeNegativeScenarios")
    public Object[][] emailChangeNegativeScenarios() {
        return new Object[][] {
                { "sergei.trofimov1testpro.io", "Email without @ symbol" },
                { "sergei.trofimov1@testpro", "Email without dot symbol" },
                { "sergei.trofimov1@", "Email without @testpro.io domain" },
                { "sergei.trofimov+test1@testpro.io", "Email with + symbol before @" }
        };
    }
}
