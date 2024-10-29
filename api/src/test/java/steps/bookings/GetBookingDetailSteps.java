package steps.bookings;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import context.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.restassured.builder.RequestSpecBuilder;
import org.junit.Assert;

public class GetBookingDetailSteps {
  private final TestContext context;
  private static final String DATE_PATTERN = "yyyy-MM-dd";

  public GetBookingDetailSteps(TestContext context) {
    this.context = context;
  }

  @Before("@BookingDetail")
  public void setUp() {
    context.setSpec(new RequestSpecBuilder()
        .setBaseUri("https://restful-booker.herokuapp.com")
        .setContentType("application/json")
        .build());
  }

  @Then("the booking details should contain:")
  public void verifyBookingDetailsFields(DataTable expectedFields) {
    var response = context.getResponse().then();

    for (Map<String, String> field : expectedFields.asMaps()) {
      String fieldName = field.get("Field").toLowerCase().replace(" ", "");
      String expectedPresence = field.get("Value");

      switch (fieldName) {
        case "firstname":
          response.body("firstname", expectedPresence.equals("Present") ? notNullValue() : nullValue());
          break;
        case "lastname":
          response.body("lastname", expectedPresence.equals("Present") ? notNullValue() : nullValue());
          break;
        case "totalprice":
          response.body("totalprice", expectedPresence.equals("Present") ? notNullValue() : nullValue());
          break;
        case "depositpaid":
          response.body("depositpaid", expectedPresence.equals("Present") ? notNullValue() : nullValue());
          break;
        case "checkindate":
          response.body("bookingdates.checkin", expectedPresence.equals("Present") ? notNullValue() : nullValue());
          break;
        case "checkoutdate":
          response.body("bookingdates.checkout", expectedPresence.equals("Present") ? notNullValue() : nullValue());
          break;
        case "additionalneeds":
          if (expectedPresence.equals("Optional")) {
            break;
          }
          response.body("additionalneeds", expectedPresence.equals("Present") ? notNullValue() : nullValue());
          break;
        default:
          throw new IllegalArgumentException("Unknown field: " + fieldName);
      }
    }
  }

  @Then("the booking dates should be in {string} format")
  public void verifyBookingDatesFormat(String format) {
    var response = context.getResponse();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);

    String checkinDate = response.path("bookingdates.checkin");
    String checkoutDate = response.path("bookingdates.checkout");

    try {
      LocalDate.parse(checkinDate, formatter);
      LocalDate.parse(checkoutDate, formatter);
    } catch (Exception e) {
      throw new AssertionError("Dates are not in correct format. Expected: " + format);
    }
  }

  @Then("the total price should be a positive number")
  public void verifyTotalPriceIsPositive() {
    context.getResponse().then().body("totalprice", greaterThan(0));
  }

  @Then("the detail response should be {string}")
  public void verifyDetailResponseReason(String expectedReason) {
    String actualReason = context.getResponse().asString();
    Assert.assertEquals(actualReason, expectedReason);
  }
}
