import org.testng.annotations.DataProvider;

public class TestDataProviders extends BaseTest {
    @DataProvider(name = "IncorrectLoginData")
    public static Object[][] getDataFromDataProviders() {
        return new Object[][]{
                {"invalid@testpro.io", "invalidPassword"},
                {validEmail, "invalidPassword"},
                {"invalidEmail", validPassword},
                {"", validPassword},
                {validEmail, ""},
                {validEmail, "uIIgWoYu "},
                {validEmail, "UIIgWoYu"},
        };
    }
}
