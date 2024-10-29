package steps.bookings;

import context.TestContext;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.cucumber.java.en.Then;
import io.cucumber.datatable.DataTable;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import java.util.Map;

public class GetBookingsStep {
  private final TestContext context;

  public GetBookingsStep(TestContext context) {
    this.context = context;
  }

  @Before("@Booking")
  public void setUp() {
    context.setSpec(new RequestSpecBuilder()
        .setBaseUri("https://restful-booker.herokuapp.com")
        .setContentType("application/json")
        .build());
  }

  @Given("the booking service is available")
  public void verifyBookingServiceAvailable() {
    given()
        .spec(context.getSpec())
        .when()
        .get("/booking")
        .then()
        .statusCode(200);
  }

  @When("the user requests all bookings")
  public void requestAllBookings() {
    var response = given()
        .spec(context.getSpec())
        .when()
        .get("/booking");

    context.setResponse(response);
  }

  @When("the user filters bookings by {string} with value {string}")
  public void filterBookings(String filterType, String value) {
    var response = given()
        .spec(context.getSpec())
        .queryParam(filterType, value)
        .when()
        .get("/booking");

    context.setResponse(response);
  }

  @When("the user filters bookings by date range:")
  public void filterBookingsByDateRange(DataTable dateRange) {
    Map<String, String> dates = dateRange.asMap(String.class, String.class);
    var response = given()
        .spec(context.getSpec())
        .queryParams(dates)
        .when()
        .get("/booking");

    context.setResponse(response);
  }

  @When("the user requests booking details for ID {string}")
  public void getBookingDetails(String bookingId) {
    var response = given()
        .spec(context.getSpec())
        .when()
        .get("/booking/" + bookingId);

    // Set response regardless of status code
    context.setResponse(response);
  }

  @Then("the response should contain booking IDs")
  public void verifyBookingIds() {
    context.getResponse().then()
        .body("bookingid", notNullValue())
        .body("bookingid.size()", greaterThan(0));
  }

  @Then("the response should contain filtered booking IDs")
  public void verifyFilteredBookingIds() {
    context.getResponse().then()
        .body("bookingid", notNullValue());
  }

  @Then("the booking details should be complete")
  public void verifyBookingDetails() {
    context.getResponse().then()
        .body("firstname", notNullValue())
        .body("lastname", notNullValue())
        .body("totalprice", notNullValue())
        .body("depositpaid", notNullValue())
        .body("bookingdates.checkin", notNullValue())
        .body("bookingdates.checkout", notNullValue());
  }
}
