package steps;

import java.util.List;
import java.util.Map;

import org.junit.Assert;

import io.appium.java_client.android.AndroidDriver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.LoginPage;
import utils.BaseTest;

public class LoginStep {
  private static AndroidDriver driver;
  private LoginPage loginPage;

  public LoginStep() {
    driver = BaseTest.getDriver();
    loginPage = new LoginPage(driver);
  }

  @Then("I can see the login screen")
  public void iCanSeeTheLoginScreen() {
    Assert.assertTrue(loginPage.isDisplayed());
  }

  @When("I login with following credentials:")
  public void iLoginWithFollowingCredentials(DataTable dataTable) {
    List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
    loginPage.login(data.get(0).get("username"), data.get(0).get("password"));
  }

  @Then("I can see an error message")
  public void iCanSeeAnErrorMessage() {
    Assert.assertTrue(loginPage.iCanSeeErrorMessage());
  }
}
