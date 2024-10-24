package com.milestone;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

public class BaseTest {
  protected static AndroidDriver driver;

  @BeforeClass
  public void setUp() throws MalformedURLException {
    UiAutomator2Options options = new UiAutomator2Options()
        .setApp("D:\\Course\\QA\\milestone-project-qa-2\\mobile\\Android-MyDemoAppRN.1.3.0.build-244.apk")
        .setAutomationName("UiAutomator2")
        .setAppPackage("com.saucelabs.mydemoapp.rn") // Replace with your app's package
        .setAppActivity(".MainActivity"); // Replace with your app's main activity

    driver = new AndroidDriver(
        new URL("http://127.0.0.1:4723"),
        options);
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
  }

  @AfterTest
  public void tearDown() {
    if (driver != null) {
      driver.quit();
    }
  }

  public static AndroidDriver getDriver() {
    System.out.println(driver);
    return driver;
  }

  // Helper method to handle app reset between tests if needed
  protected void resetApp() {
    if (driver != null) {
      driver.terminateApp("com.saucelabs.mydemoapp.rn");
      driver.activateApp("com.saucelabs.mydemoapp.rn");
    }
  }
}
