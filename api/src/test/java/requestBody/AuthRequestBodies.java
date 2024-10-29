package requestBody;

import org.json.simple.JSONObject;

public class AuthRequestBodies {
  @SuppressWarnings("unchecked")
  public static JSONObject createAuthRequestBody(String username, String password) {
    JSONObject requestBody = new JSONObject();
    requestBody.put("username", username);
    requestBody.put("password", password);
    return requestBody;
  }
}
