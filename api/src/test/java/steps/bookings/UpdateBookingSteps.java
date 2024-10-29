package steps.bookings;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

import org.json.simple.JSONObject;

import context.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import java.util.Map;

public class UpdateBookingSteps {
  private final TestContext context;
  private JSONObject requestBody;
  private String token;

  public UpdateBookingSteps(TestContext context) {
    this.context = context;
  }

  @Before("@UpdateBooking")
  public void setUp() {
    context.setSpec(new RequestSpecBuilder()
        .setBaseUri("https://restful-booker.herokuapp.com")
        .setContentType("application/json")
        .build());
  }

  @Given("I have a valid authentication token")
  public void getValidAuthenticationToken() {
    token = given()
        .spec(context.getSpec())
        .when()
        .post("/auth")
        .then()
        .statusCode(200)
        .extract().path("token");
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

  @SuppressWarnings("unchecked")
  @When("the user requests to update a booking with id {int} and booking details:")
  public void requestUpdateBooking(int id, DataTable bookingDetails) {
    Map<String, String> bookingDetailsMap = bookingDetails.asMaps().get(0);
    requestBody = new JSONObject();
    requestBody.put("firstname", bookingDetailsMap.get("firstname"));
    requestBody.put("lastname", bookingDetailsMap.get("lastname"));
    given()
        .spec(context.getSpec())
        .body(requestBody.toJSONString())
        .when()
        .put("/booking/{id}", id)
        .then()
        .statusCode(anyOf(is(200), is(401)));
  }

}
