package steps.auth;

import context.TestContext;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.cucumber.java.en.Then;
import io.cucumber.datatable.DataTable;
import static io.restassured.RestAssured.given;
import org.json.simple.JSONObject;
import java.util.Map;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

public class AuthSteps {
  private final TestContext context;
  private JSONObject requestBody;

  public AuthSteps(TestContext context) {
    this.context = context;
  }

  @Before("@Authentication")
  public void setUp() {
    context.setSpec(new RequestSpecBuilder()
        .setBaseUri("https://restful-booker.herokuapp.com")
        .setBasePath("/auth")
        .setContentType("application/json")
        .build());
  }

  @Given("the authentication service is available")
  public void verifyAuthServiceAvailable() {
    given()
        .spec(context.getSpec())
        .when()
        .post()
        .then()
        .statusCode(anyOf(is(200), is(401)));
  }

  @Given("this is a negative test case")
  public void setNegativeTestCase() {
    context.setNegativeTestCase(true);
  }

  @SuppressWarnings("unchecked")
  @Given("the user provides credentials:")
  public void setCredentials(DataTable credentials) {
    // Get the first row after headers (index 1)
    Map<String, String> creds = credentials.asMaps().get(0);
    requestBody = new JSONObject();
    requestBody.put("username", creds.get("username"));
    requestBody.put("password", creds.get("password"));
  }

  @When("the user requests authentication token")
  public void requestAuthToken() {
    var response = given()
        .spec(context.getSpec())
        .body(requestBody.toJSONString())
        .when()
        .post();
    System.out.println(response.asString());

    context.setResponse(response);
  }

  @Then("the response should contain a valid token")
  public void verifyTokenPresent() {
    context.getResponse().then()
        .body("token", notNullValue());
  }
}
