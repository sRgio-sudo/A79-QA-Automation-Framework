import org.testng.annotations.DataProvider;
import utils.BaseTest;
import utils.ConfigReader;

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
    }
