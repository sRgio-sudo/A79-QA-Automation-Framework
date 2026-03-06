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
}
