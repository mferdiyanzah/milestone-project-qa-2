package steps.bookings;

import static io.restassured.RestAssured.given;

import context.TestContext;
import io.cucumber.java.Before;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;

public class DeleteBookingSteps {
  private final TestContext context;

  public DeleteBookingSteps(TestContext context) {
    this.context = context;
  }

  @Before("@DeleteBooking")
  public void setUp() {
    context.setSpec(new RequestSpecBuilder()
        .setBaseUri("https://restful-booker.herokuapp.com")
        .setContentType("application/json")
        .build());
  }

  @When("I delete booking with id {anyValue}")
  public void deleteBooking(Object id) {
    String token = context.getToken();

    var response = given()
        .spec(context.getSpec())
        .header("Cookie", "token=" + token)
        .when()
        .delete("/booking/" + id);
    context.setResponse(response);
  }
}
