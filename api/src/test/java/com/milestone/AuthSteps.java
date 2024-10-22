package com.milestone;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import org.json.simple.JSONObject;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class AuthSteps {
  private String baseURI;
  private JSONObject requestBody;
  private int responseStatus;
  private String responseReason;

  @Before
  public void setUp() {
    baseURI = "https://restful-booker.herokuapp.com/auth";
    RestAssured.baseURI = baseURI;
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
        .header("Content-Type", "application/json")
        .body(requestBody.toJSONString())
        .when()
        .post()
        .statusCode();
  }

  @Given("the user requests a token with content type {string}")
  public void theUserRequestsATokenWithContentType(String contentType) {
    responseStatus = given()
        .header("Content-Type", contentType)
        .body(requestBody.toJSONString())
        .when()
        .post()
        .statusCode();
  }

  @Then("the response status code should be {int}")
  public void theResponseStatusCodeShouldBe(int expectedStatus) {
    System.out.println(responseStatus);
    assert responseStatus == expectedStatus;
  }

  @Then("the response should contain a token")
  public void theResponseShouldContainAToken() {
    given()
        .header("Content-Type", "application/json")
        .body(requestBody.toJSONString())
        .when()
        .post()
        .then()
        .body("token", notNullValue());
  }

  @Then("the response reason should be {string}")
  public void theResponseReasonShouldBe(String expectedReason) {
    responseReason = given()
        .header("Content-Type", "application/json")
        .body(requestBody.toJSONString())
        .when()
        .post()
        .then()
        .extract().path("reason");
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
