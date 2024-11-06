package context;

import io.cucumber.java.Scenario;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class TestContext {
  private Response response;
  private RequestSpecification spec;
  private boolean isNegativeTestCase;
  private final Map<String, Object> testData;
  private final Map<Integer, List<Response>> concurrentResponses;
  private Scenario scenario;

  public TestContext() {
    this.testData = new HashMap<>();
    this.concurrentResponses = new ConcurrentHashMap<>();
  }

  public void setScenario(Scenario scenario) {
    this.scenario = scenario;
  }

  public Scenario getScenario() {
    return scenario;
  }

  public boolean isPartialUpdate() {
    return scenario != null &&
        scenario.getSourceTagNames() != null &&
        scenario.getSourceTagNames().contains("@PartialBooking");
  }

  public String getHttpMethod() {
    return isPartialUpdate() ? "PATCH" : "PUT";
  }

  public Response getResponse() {
    return response;
  }

  public void setResponse(Response response) {
    this.response = response;
    if (scenario != null) {
      scenario.log("Response Status Code: " + response.getStatusCode());
      scenario.log("Response Body: " + response.getBody().asString());
    }
  }

  public int getResponseStatus() {
    return response != null ? response.getStatusCode() : 0;
  }

  public RequestSpecification getSpec() {
    if (spec == null) {
      throw new IllegalStateException("RequestSpecification has not been initialized");
    }
    return spec;
  }

  public void setSpec(RequestSpecification spec) {
    this.spec = spec;
  }

  public boolean isNegativeTestCase() {
    return isNegativeTestCase;
  }

  public void setNegativeTestCase(boolean isNegative) {
    this.isNegativeTestCase = isNegative;
  }

  public void setAttribute(String key, Object value) {
    testData.put(key, value);
  }

  public Object getAttribute(String key) {
    return testData.get(key);
  }

  @SuppressWarnings("unchecked")
  public <T> T getAttribute(String key, Class<T> type) {
    Object value = testData.get(key);
    if (value != null && type.isAssignableFrom(value.getClass())) {
      return (T) value;
    }
    return null;
  }

  public void addConcurrentResponse(int requestId, Response response) {
    concurrentResponses.computeIfAbsent(requestId, k -> new ArrayList<>())
        .add(response);
    if (scenario != null) {
      scenario.log("Concurrent Request ID: " + requestId);
      scenario.log("Response Status Code: " + response.getStatusCode());
    }
  }

  public List<Response> getConcurrentResponses(int requestId) {
    return concurrentResponses.getOrDefault(requestId, new ArrayList<>());
  }

  public void clearConcurrentResponses() {
    concurrentResponses.clear();
  }

  public void clearTestData() {
    testData.clear();
  }

  public void reset() {
    response = null;
    isNegativeTestCase = false;
    clearTestData();
    clearConcurrentResponses();
  }

  public void storeRequestDetails(String requestType, Object requestBody) {
    setAttribute("lastRequestType", requestType);
    setAttribute("lastRequestBody", requestBody);
  }

  public Object getLastRequestBody() {
    return getAttribute("lastRequestBody");
  }

  public String getLastRequestType() {
    return getAttribute("lastRequestType", String.class);
  }

  public void setToken(String token) {
    setAttribute("token", token);
  }

  public String getToken() {
    return getAttribute("token", String.class);
  }
}
