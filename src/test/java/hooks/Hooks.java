package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import utils.BaseTest;

public class Hooks extends BaseTest {
    @Before
    public void setUp() throws Exception {
        setUpBrowser();
    }

    @After
    public void closeBrowser() {
        tearDown();
    }
}
