package stepDefenition;

import drivers.DriverManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.HomePage;
import pages.LoginPage;
import drivers.BaseTest;
import utils.ConfigReader;

public class LoginStepDefinition extends BaseTest {
    LoginPage loginPage;
    HomePage homePage;

    @Given("I open login page")
    public void iOpenLoginPage() {
        loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.openPage();
    }

    @When("I enter email {string}")
    public void iEnterEmail(String email) {
        String actualEmail = ConfigReader.getProperty(email);
        loginPage.provideEmail(actualEmail);
    }

    @And("I enter password {string}")
    public void iEnterPassword(String password) {
        String actualPassword = ConfigReader.getProperty(password);
        loginPage.providePassword(actualPassword);
    }

    @And("I click submit")
    public void iClickSubmit() {
        loginPage.clickSubmitButton();
    }

    @Then("I am logged in")
    public void iAmLoggedIn() {
        homePage = new HomePage(DriverManager.getDriver());
        Assert.assertTrue(homePage.isAvatarDisplayed());
    }
}
