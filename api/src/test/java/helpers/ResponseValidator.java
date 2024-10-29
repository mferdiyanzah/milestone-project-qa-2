package helpers;

import io.restassured.response.Response;
import static org.hamcrest.Matchers.equalTo;
import org.junit.Assert;

public class ResponseValidator {
  public static void verifyStatusCode(int actualStatus, int expectedStatus) {
    Assert.assertEquals(expectedStatus, actualStatus);
  }

  public static void verifyResponseReason(Response response, String expectedReason) {
    response.then().body("reason", equalTo(expectedReason));
  }
}
