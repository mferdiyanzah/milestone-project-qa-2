package com.milestone;

import io.restassured.RestAssured;

import org.junit.Before;
import org.junit.Test;

import org.json.simple.JSONObject;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class AuthTest {
  @Before
  public void setUp() {
    RestAssured.baseURI = "https://restful-booker.herokuapp.com/auth";
  }

  @SuppressWarnings("unchecked")
  private JSONObject createAuthRequestBody(String username, String password) {
    JSONObject requestBody = new JSONObject();
    requestBody.put("username", username);
    requestBody.put("password", password);
    return requestBody;
  }

  @Test
  public void TestCreateNewToken() {
    JSONObject requestBody = createAuthRequestBody("admin", "password123");

    given()
        .when()
        .header("Content-Type", "application/json")
        .body(requestBody.toJSONString())
        .post()
        .then()
        .statusCode(200)
        .body("token", notNullValue());
  }

  @Test
  public void TestInvalidCredentials() {
    JSONObject requestBody = createAuthRequestBody("admin1", "password123");

    given()
        .header("Content-Type", "application/json")
        .body(requestBody.toJSONString())
        .when()
        .post()
        .then()
        .statusCode(200)
        .body("reason", equalTo("Bad credentials"));
  }

  @Test
  public void TestMissingUsername() {
    JSONObject requestBody = createAuthRequestBody(null, "password123");

    given()
        .header("Content-Type", "application/json")
        .body(requestBody.toJSONString())
        .when()
        .post()
        .then()
        .statusCode(200)
        .body("reason", equalTo("Bad credentials"));
  }

  @Test
  public void TestMissingPassword() {
    JSONObject requestBody = createAuthRequestBody("admin", null);

    given()
        .header("Content-Type", "application/json")
        .body(requestBody.toJSONString())
        .when()
        .post()
        .then()
        .statusCode(200)
        .body("reason", equalTo("Bad credentials"));
  }

  @Test
  public void TestInvalidContentType() {
    JSONObject requestBody = createAuthRequestBody("admin", "password123");

    given()
        .header("Content-Type", "text/plain")
        .body(requestBody.toJSONString())
        .when()
        .post()
        .then()
        .statusCode(200)
        .body("reason", equalTo("Bad credentials"));
  }
}
