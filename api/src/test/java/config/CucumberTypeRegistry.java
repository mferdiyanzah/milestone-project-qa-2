package config;

import io.cucumber.java.ParameterType;

public class CucumberTypeRegistry {

  @ParameterType(".*")
  public Object anyValue(String value) {
    if (value.equals("null")) {
      return null;
    }

    // Try parsing as integer
    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException ignored) {
    }

    // Try parsing as double
    try {
      return Double.parseDouble(value);
    } catch (NumberFormatException ignored) {
    }

    // Remove quotes if present
    if (value.startsWith("\"") && value.endsWith("\"")) {
      return value.substring(1, value.length() - 1);
    }

    // Return as string if no other type matches
    return value;
  }
}