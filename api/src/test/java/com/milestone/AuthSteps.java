package com.milestone;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

import org.json.simple.JSONObject;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class AuthSteps {
  private String baseURI;
  private JSONObject requestBody;
  private int responseStatus;
  private String responseReason;
  private RequestSpecification spec;

  @Before
  public void setUp() {
    baseURI = "https://restful-booker.herokuapp.com/auth";
    spec = new RequestSpecBuilder().setBaseUri(baseURI).build();
  }

  @Given("the user has username {string} and password {string}")
  public void theUserHasUsernameAndPassword(String username, String password) {
    requestBody = createAuthRequestBody(username, password);
  }

  @Given("the user has no username and password {string}")
  public void theUserHasNoUsernameAndPassword(String password) {
    requestBody = createAuthRequestBody(null, password);
  }

  @Given("the user has username {string} and no password")
  public void theUserHasUsernameAndNoPassword(String username) {
    requestBody = createAuthRequestBody(username, null);
  }

  @Given("the user has a long username and password {string}")
  public void theUserHasALongUsernameAndPassword(String password) {
    String longUsername = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque cursus eget lorem non aliquet. Vivamus nec odio sed sapien tempor fringilla. Etiam ac vulputate nisi, ac consectetur felis. Pellentesque pharetra ut felis nec elementum. Sed non mi sed velit accumsan scelerisque. Sed tincidunt tellus sit amet sapien ultricies, et dignissim tellus aliquam. Praesent odio sem, maximus vel metus a, sagittis feugiat enim. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Aenean interdum porta velit at mattis. Etiam vitae scelerisque ex. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Cras euismod in risus sit amet dignissim. Donec scelerisque dictum molestie. Nunc imperdiet tempor felis a rhoncus. Ut at tincidunt nisl, mattis congue risus. Vestibulum sit amet viverra enim, ac lobortis dui.";
    requestBody = createAuthRequestBody(longUsername, password);
  }

  @Given("the user requests a token")
  public void theUserRequestsAToken() {
    System.out.println(RestAssured.baseURI);
    responseStatus = given()
        .spec(spec)
        .header("Content-Type", "application/json")
        .body(requestBody.toJSONString())
        .when()
        .post()
        .statusCode();
  }

  @Given("the user requests a token with content type {string}")
  public void theUserRequestsATokenWithContentType(String contentType) {
    responseStatus = given()
        .spec(spec)
        .header("Content-Type", contentType)
        .body(requestBody.toJSONString())
        .when()
        .post()
        .statusCode();
  }

  @Then("the login's API response status code should be {int}")
  public void theResponseStatusCodeShouldBe(int expectedStatus) {
    System.out.println(responseStatus);
    assert responseStatus == expectedStatus;
    // Assume.assumeTrue(expectedStatus == responseStatus);
  }

  @Then("the response should contain a token")
  public void theResponseShouldContainAToken() {
    given()
        .spec(spec)
        .header("Content-Type", "application/json")
        .body(requestBody.toJSONString())
        .when()
        .post()
        .then()
        .body("token", notNullValue());
  }

  @Then("the login's API response reason should be {string}")
  public void theResponseReasonShouldBe(String expectedReason) {
    responseReason = given()
        .spec(spec)
        .header("Content-Type", "application/json")
        .body(requestBody.toJSONString())
        .when()
        .post()
        .then()
        .extract().path("reason");
    // Assume.assumeTrue(responseReason.equals(expectedReason));
    assert responseReason.equals(expectedReason);
  }

  @SuppressWarnings("unchecked")
  private JSONObject createAuthRequestBody(String username, String password) {
    JSONObject requestBody = new JSONObject();
    requestBody.put("username", username);
    requestBody.put("password", password);
    return requestBody;
  }
}
