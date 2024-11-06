package steps.bookings;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Map;

import org.json.simple.JSONObject;

import context.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;

public class CreateBookingSteps {
  private final TestContext context;
  private JSONObject requestBody;

  public CreateBookingSteps(TestContext context) {
    this.context = context;
  }

  @Before("@CreateBooking")
  public void setUp() {
    context.setSpec(new RequestSpecBuilder()
        .setBaseUri("https://restful-booker.herokuapp.com")
        .setContentType("application/json")
        .build());
  }

  @Given("the create booking service is available")
  public void verifyBookingServiceAvailable() {
    given()
        .spec(context.getSpec())
        .when()
        .post("/booking")
        .then()
        .statusCode(anyOf(is(200), is(401)));
  }

  @SuppressWarnings("unchecked")
  @Given("the user provides booking details:")
  public void provideBookingDetails(DataTable bookingDetails) {
    Map<String, String> bookingDetailsMap = bookingDetails.asMaps().get(0);
    requestBody = new JSONObject();

    // Main booking details
    requestBody.put("firstname", bookingDetailsMap.get("firstname"));
    requestBody.put("lastname", bookingDetailsMap.get("lastname"));
    requestBody.put("totalprice", Integer.parseInt(bookingDetailsMap.get("totalprice")));
    requestBody.put("depositpaid", Boolean.parseBoolean(bookingDetailsMap.get("depositpaid")));

    // Create bookingdates object
    JSONObject bookingDates = new JSONObject();
    bookingDates.put("checkin", bookingDetailsMap.get("checkin"));
    bookingDates.put("checkout", bookingDetailsMap.get("checkout"));
    requestBody.put("bookingdates", bookingDates);

    requestBody.put("additionalneeds", bookingDetailsMap.get("additionalneeds"));
  }

  @When("the user requests to create a booking")
  public void requestCreateBooking() {
    var response = given()
        .spec(context.getSpec())
        .body(requestBody.toJSONString())
        .when()
        .post("/booking");

    context.setResponse(response);
    context.setStoredBookingId(response.jsonPath().getInt("bookingid"));
  }

  @Then("the user get the booking id")
  public void userGetBookingId() {
    context.getResponse().then()
        .body("bookingid", notNullValue());
  }
}
