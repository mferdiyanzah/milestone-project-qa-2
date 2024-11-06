package steps.auth;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import static org.junit.Assert.*;

import context.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.datatable.DataTable;

import java.util.Map;

public class LoginSteps {
  private WebDriver driver;
  private final TestContext testContext;

  public LoginSteps() {
    testContext = new TestContext();
  }

  @Before
  public void setup() throws Exception {
    driver = testContext.getDriver();
  }

  @After
  public void tearDown() {
    testContext.quitDriver();
  }

  @Given("I am on the login page")
  public void iAmOnTheLoginPage() {
    driver.get("https://www.saucedemo.com/");
  }

  @When("I enter my username and password:")
  public void iEnterMyUsernameAndPassword(DataTable dataTable) {
    Map<String, String> credentials = dataTable.asMaps().get(0);

    WebElement usernameField = driver.findElement(By.id("user-name"));
    WebElement passwordField = driver.findElement(By.id("password"));

    String username = credentials.get("username") != null ? credentials.get("username") : "";
    String password = credentials.get("password") != null ? credentials.get("password") : "";

    usernameField.clear();
    passwordField.clear();

    usernameField.sendKeys(username);
    passwordField.sendKeys(password);
  }

  @When("I click the login button")
  public void iClickTheLoginButton() {
    driver.findElement(By.id("login-button")).click();
  }

  @Then("I get an error message")
  public void iGetAnErrorMessage() {
    WebElement errorElement = driver.findElement(By.cssSelector("[data-test='error']"));
    assertTrue(errorElement.isDisplayed());
  }

  @Then("I get an error message with text {string}")
  public void iGetAnErrorMessageWithText(String text) {
    WebElement errorElement = driver.findElement(By.cssSelector("[data-test='error']"));
    assertEquals(errorElement.getText(), text);
  }
}
