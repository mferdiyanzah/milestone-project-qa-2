package com.milestone;

import org.json.simple.JSONObject;

public class General {
  @SuppressWarnings("unchecked")
  public JSONObject createAuthRequestBody(String username, String password) {
    JSONObject requestBody = new JSONObject();
    requestBody.put("username", username);
    requestBody.put("password", password);
    return requestBody;
  }
}
