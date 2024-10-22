package com.milestone;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class BookingSteps {
  private String baseURI;
  private int responseStatus;
  private RequestSpecification spec;

  @Before
  public void setUp() {
    baseURI = "https://restful-booker.herokuapp.com";
    spec = new RequestSpecBuilder().setBaseUri(baseURI).build();
  }

  @When("the user sends a GET request to {string}")
  public void theUserSendsAGETRequestTo(String endpoint) {
    responseStatus = given()
        .spec(spec)
        .when()
        .get(endpoint)
        .getStatusCode();
  }

  @Given("the user filters bookings by firstname {string}")
  public void theUserFiltersBookingsByFirstname(String firstname) {
    responseStatus = given()
        .spec(spec)
        .queryParam("firstname", firstname)
        .when()
        .get("/booking")
        .getStatusCode();
  }

  @Given("the user filters bookings by lastname {string}")
  public void theUserFiltersBookingsByLastname(String lastname) {
    responseStatus = given()
        .spec(spec)
        .queryParam("lastname", lastname)
        .when()
        .get("/booking")
        .getStatusCode();
  }

  @Given("the user filters bookings by checkin date {string}")
  public void theUserFiltersBookingsByCheckinDate(String checkin) {
    responseStatus = given()
        .spec(spec)
        .queryParam("checkin", checkin)
        .when()
        .get("/booking")
        .getStatusCode();
  }

  @Given("the user filters bookings by checkout date {string}")
  public void theUserFiltersBookingsByCheckoutDate(String checkout) {
    responseStatus = given()
        .spec(spec)
        .queryParam("checkout", checkout)
        .when()
        .get("/booking")
        .getStatusCode();
  }

  @Given("the user filters bookings by checkin date {string} and checkout date {string}")
  public void theUserFiltersBookingsByCheckinAndCheckoutDates(String checkin, String checkout) {
    responseStatus = given()
        .spec(spec)
        .queryParam("checkin", checkin)
        .queryParam("checkout", checkout)
        .when()
        .get("/booking")
        .getStatusCode();
  }

  @Given("the user filters bookings with an invalid checkin date {string}")
  public void theUserFiltersBookingsByCheckinAndCheckoutDates(String checkin) {
    responseStatus = given()
        .spec(spec)
        .queryParam("checkin", checkin)
        .when()
        .get("/booking")
        .getStatusCode();
  }

  @Then("the response status code should be {int}")
  public void theResponseStatusCodeShouldBe(int expectedStatus) {
    assert responseStatus == expectedStatus;
  }

  @Then("the response should contain a list of booking IDs")
  public void theResponseShouldContainAListOfBookingIDs() {
    given()
        .spec(spec)
        .when()
        .get("/booking")
        .then()
        .body("bookingid", notNullValue());
  }

  @Then("the response should contain an empty list")
  public void theResponseShouldContainAnEmptyList() {
    given()
        .spec(spec)
        .when()
        .get("/booking")
        .then()
        .body("$", empty());
  }

  @Then("the response reason should be {string}")
  public void theResponseReasonShouldBe(String expectedReason) {
    String responseReason = given()
        .spec(spec)
        .when()
        .get("/booking")
        .then()
        .extract().path("reason");
    assert responseReason.equals(expectedReason);
  }
}
