package utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class BaseTest {
  protected static AndroidDriver driver;
  protected static WebDriverWait wait;

  @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc=\"store itemtext\"]")
  private List<WebElement> productNames;

  @AndroidFindBy(xpath = "//android.widget.Button[contains(@resource-id,'addToCartBtn')]")
  private WebElement addToCartButton;

  private static void resetUiAutomator2() {
    try {
      String[] commands = {
          "adb shell pm clear io.appium.uiautomator2.server",
          "adb shell pm clear io.appium.uiautomator2.server.test",
          "adb shell am force-stop io.appium.uiautomator2.server",
          "adb shell am force-stop io.appium.uiautomator2.server.test"
      };

      for (String command : commands) {
        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();
      }
      Thread.sleep(2000); // Give some time for the services to reset
    } catch (Exception e) {
      System.out.println("Warning: Failed to reset UiAutomator2: " +
          e.getMessage());
    }
  }

  public static void setUp() throws MalformedURLException {

    UiAutomator2Options options = new UiAutomator2Options()
        .setApp("D:\\Course\\QA\\milestone-project-qa-2\\mobile\\Android-MyDemoAppRN.1.3.0.build-244.apk")
        .setAutomationName("UiAutomator2")
        .setAppPackage("com.saucelabs.mydemoapp.rn")
        .setAppActivity(".MainActivity")
        .setAutoGrantPermissions(true) // Automatically grant app permissions
        .setNoReset(false) // Clear app state before each test
        .setNewCommandTimeout(Duration.ofSeconds(60));

    try {
      driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
      wait = new WebDriverWait(driver, Duration.ofSeconds(10));

      // Wait for app to be fully loaded
      wait.until(driver -> {
        try {
          return driver.getPageSource().contains("ViewGroup");
        } catch (Exception e) {
          return false;
        }
      });

    } catch (Exception e) {
      System.out.println("Failed to initialize driver: " + e.getMessage());
      resetUiAutomator2(); // Try resetting UiAutomator2 if initialization fails
      driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options); //
    }

    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
  }

  public void tearDown() {
    if (driver != null) {
      try {
        driver.terminateApp("com.saucelabs.mydemoapp.rn");
        driver.quit();
      } catch (Exception e) {
        System.out.println("Warning: Error during teardown: " + e.getMessage());
      }
    }
  }

  public static AndroidDriver getDriver() {
    return driver;
  }

  public static WebDriverWait getWait() {
    return wait;
  }
}