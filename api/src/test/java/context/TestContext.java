package context;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestContext {
  private Response response;
  private RequestSpecification spec;
  private boolean isNegativeTestCase;

  public Response getResponse() {
    return response;
  }

  public void setResponse(Response response) {
    this.response = response;
  }

  public int getResponseStatus() {
    return response != null ? response.getStatusCode() : 0;
  }

  public RequestSpecification getSpec() {
    return spec;
  }

  public void setSpec(RequestSpecification spec) {
    this.spec = spec;
    System.out.println(this.spec.toString());
  }

  public boolean isNegativeTestCase() {
    return isNegativeTestCase;
  }

  public void setNegativeTestCase(boolean isNegative) {
    this.isNegativeTestCase = isNegative;
  }
}
