package hooks;

import drivers.DriverFactory;
import drivers.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import drivers.BaseTest;
import utils.ConfigReader;

public class Hooks extends BaseTest {
    @Before
    public void setUp() throws Exception {
        DriverManager.setDriver(DriverFactory.createDriver());
        DriverManager.getDriver().get(ConfigReader.getProperty("base.url"));
    }

    @After
    public void closeBrowser() {
        tearDown();
    }
}
