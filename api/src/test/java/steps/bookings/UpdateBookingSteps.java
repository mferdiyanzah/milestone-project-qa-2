package steps.bookings;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

import org.json.simple.JSONObject;
import org.junit.Assert;

import context.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

public class UpdateBookingSteps {
  private final TestContext context;
  private JSONObject requestBody;
  private String token;
  private String httpMethod; // To store the HTTP method

  public UpdateBookingSteps(TestContext context) {
    this.context = context;
  }

  @Before("@PartialUpdateBooking or @UpdateBooking")
  public void setUp(Scenario scenario) {
    context.setScenario(scenario);
    context.setSpec(new RequestSpecBuilder()
        .setBaseUri("https://restful-booker.herokuapp.com")
        .setContentType("application/json")
        .build());

    // Set default HTTP method based on the tag
    httpMethod = context.getScenario().getSourceTagNames().contains("@PartialUpdateBooking") ? "PATCH" : "PUT";
  }

  @SuppressWarnings("unchecked")
  @Given("I have a valid authentication token")
  public void getValidAuthenticationToken() {
    JSONObject authRequest = new JSONObject();
    authRequest.put("username", "admin");
    authRequest.put("password", "password123");

    token = given()
        .spec(context.getSpec())
        .body(authRequest.toJSONString())
        .when()
        .post("/auth")
        .then()
        .statusCode(200)
        .extract().path("token");

    context.setToken(token);
  }

  @Given("the update booking service is available")
  public void verifyUpdateBookingServiceAvailable() {
    given()
        .spec(context.getSpec())
        .when()
        .post("/booking")
        .then()
        .statusCode(anyOf(is(200), is(401)));
  }

  @Given("I have an invalid authentication token {string}")
  public void setInvalidAuthenticationToken(String invalidToken) {
    this.token = invalidToken;
  }

  @SuppressWarnings("unchecked")
  @When("I update booking with id {int} with the following details:")
  public void updateBookingWithDetails(int id, DataTable bookingDetails) {
    requestBody = new JSONObject();

    if (bookingDetails != null && !bookingDetails.asMaps().isEmpty()) {
      Map<String, String> bookingDetailsMap = bookingDetails.asMaps().get(0);

      // Add fields only if they exist in the DataTable
      addFieldIfPresent(bookingDetailsMap, "firstname");
      addFieldIfPresent(bookingDetailsMap, "lastname");
      addFieldIfPresent(bookingDetailsMap, "totalprice", true);
      addFieldIfPresent(bookingDetailsMap, "depositpaid", false);

      // Handle booking dates if either is present
      if (bookingDetailsMap.containsKey("checkin") || bookingDetailsMap.containsKey("checkout")) {
        JSONObject bookingDates = new JSONObject();
        if (bookingDetailsMap.containsKey("checkin")) {
          bookingDates.put("checkin", bookingDetailsMap.get("checkin"));
        }
        if (bookingDetailsMap.containsKey("checkout")) {
          bookingDates.put("checkout", bookingDetailsMap.get("checkout"));
        }
        requestBody.put("bookingdates", bookingDates);
      }

      addFieldIfPresent(bookingDetailsMap, "additionalneeds");
    }

    var response = given()
        .spec(context.getSpec())
        .header("Cookie", "token=" + token)
        .header("Accept", "application/json")
        .body(requestBody.toJSONString())
        .when()
        .request(httpMethod, "/booking/{id}", id);
    context.setResponse(response);
  }

  // Add new method for empty payload scenario
  @When("I update booking with id {int} with empty payload")
  public void updateBookingWithEmptyPayload(int id) {
    requestBody = new JSONObject();

    var response = given()
        .spec(context.getSpec())
        .header("Cookie", "token=" + token)
        .header("Accept", "application/json")
        .body(requestBody.toJSONString())
        .when()
        .request(httpMethod, "/booking/{id}", id);
    context.setResponse(response);
  }

  private void addFieldIfPresent(Map<String, String> map, String fieldName) {
    addFieldIfPresent(map, fieldName, false);
  }

  @SuppressWarnings("unchecked")
  private void addFieldIfPresent(Map<String, String> map, String fieldName, boolean isNumeric) {
    if (map.containsKey(fieldName)) {
      String value = map.get(fieldName);
      // Allow empty strings to be sent
      if (value == null) {
        requestBody.put(fieldName, "");
      } else if (isNumeric && !value.trim().isEmpty()) {
        try {
          requestBody.put(fieldName, Integer.parseInt(value));
        } catch (NumberFormatException e) {
          requestBody.put(fieldName, 0);
        }
      } else if (fieldName.equals("depositpaid") && !value.trim().isEmpty()) {
        requestBody.put(fieldName, Boolean.parseBoolean(value));
      } else {
        requestBody.put(fieldName, value);
      }
    }
  }

  @SuppressWarnings("unchecked")
  @When("I update booking with id {int} with valid booking details")
  public void updateBookingWithValidDetails(int id) {
    requestBody = new JSONObject();
    requestBody.put("firstname", "James");
    requestBody.put("lastname", "Brown");

    // Only add these fields for full updates
    if (httpMethod.equals("PUT")) {
      requestBody.put("totalprice", 111);
      requestBody.put("depositpaid", true);

      JSONObject bookingDates = new JSONObject();
      bookingDates.put("checkin", "2018-01-01");
      bookingDates.put("checkout", "2019-01-01");
      requestBody.put("bookingdates", bookingDates);
    }

    var response = given()
        .spec(context.getSpec())
        .header("Cookie", "token=" + token)
        .body(requestBody.toJSONString())
        .when()
        .request(httpMethod, "/booking/{id}", id);
    context.setResponse(response);
  }

  @When("I update booking with id {int} with missing required fields:")
  public void updateBookingWithMissingFields(int id, DataTable bookingDetails) {
    updateBookingWithDetails(id, bookingDetails);
  }

  @Given("I have {int} concurrent update requests for booking id {int}")
  public void setupConcurrentRequests(int numRequests, int bookingId) {
    context.setAttribute("numRequests", numRequests);
    context.setAttribute("bookingId", bookingId);
  }

  @When("I send the update requests simultaneously")
  public void sendConcurrentRequests() {
    int numRequests = (int) context.getAttribute("numRequests");
    int bookingId = (int) context.getAttribute("bookingId");

    // Implement concurrent requests logic here
    // You might want to use CompletableFuture or ExecutorService
    // Store responses in context for verification
    for (int i = 0; i < numRequests; i++) {
      updateBookingWithDetails(bookingId, null);
    }
  }

  @Then("one request should succeed with status code {int}")
  public void verifySuccessfulRequest(int expectedStatus) {
    // Verify successful request from concurrent updates
    // Implementation depends on how concurrent responses are stored
    int bookingId = (int) context.getAttribute("bookingId");
    List<Response> responses = context.getConcurrentResponses(bookingId);
    Assert.assertEquals(1, responses.size());
    Assert.assertEquals(expectedStatus, responses.get(0).getStatusCode());
  }

  @Then("one request should fail with status code {int}")
  public void verifyFailedRequest(int expectedStatus) {
    // Verify failed request from concurrent updates
    // Implementation depends on how concurrent responses are stored
    int bookingId = (int) context.getAttribute("bookingId");
    List<Response> responses = context.getConcurrentResponses(bookingId);
    Assert.assertEquals(1, responses.size());
    Assert.assertEquals(expectedStatus, responses.get(0).getStatusCode());
  }

  @SuppressWarnings("unchecked")
  @Then("the response should contain the updated booking details")
  public void verifyUpdatedBookingDetails() {
    var response = context.getResponse();

    // Verify only the fields that were in the request
    requestBody.forEach((key, value) -> {
      if (key.equals("bookingdates")) {
        JSONObject dates = (JSONObject) value;
        dates.forEach((dateKey, dateValue) -> response.then().body("bookingdates." + dateKey, is(dateValue)));
      } else {
        response.then().body(key.toString(), is(value));
      }
    });
  }

  @Then("the error message should contain {string}")
  public void verifyErrorMessage(String errorMessage) {
    var response = context.getResponse();
    Assert.assertTrue(response.body().asString().contains(errorMessage));
  }

  @Then("the response should contain {string}")
  public void verifyResponseContains(String expectedMessage) {
    var response = context.getResponse();
    Assert.assertTrue(response.body().asString().contains(expectedMessage));
  }

}
