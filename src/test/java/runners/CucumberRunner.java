package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = {"src/test/resources/feature"},
        glue = {"stepDefenition", "hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports.html",
        },
        monochrome = true
)

public class CucumberRunner extends AbstractTestNGCucumberTests { }