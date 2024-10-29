package steps;

import context.TestContext;
import io.cucumber.java.en.Then;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CommonSteps {
  private final TestContext context;

  public CommonSteps(TestContext context) {
    this.context = context;
  }

  @Then("the response status code should be {int}")
  public void verifyResponseStatusCode(int expectedStatus) {
    assertEquals(expectedStatus, context.getResponseStatus());
  }

  public void verifyResponseStatusCodeNotEqual(int expectedStatus) {
    assertNotEquals(expectedStatus, context.getResponseStatus());
  }

  @Then("the response reason should be {string}")
  public void verifyResponseReason(String expectedReason) {
    context.getResponse().then().body("reason", equalTo(expectedReason));
  }
}