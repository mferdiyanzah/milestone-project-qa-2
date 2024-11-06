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

  private static final String APPIUM_HOST = System.getProperty("appium.host",
      System.getenv("APPIUM_HOST") != null ? System.getenv("APPIUM_HOST") : "localhost");
  private static final String APPIUM_URL = String.format("http://%s:4723", APPIUM_HOST);

  @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc=\"store itemtext\"]")
  private List<WebElement> productNames;

  @AndroidFindBy(xpath = "//android.widget.Button[contains(@resource-id,'addToCartBtn')]")
  private WebElement addToCartButton;

  public static void setUp() throws MalformedURLException {

    UiAutomator2Options options = new UiAutomator2Options()
        .setApp("mobile\\Android-MyDemoAppRN.1.3.0.build-244.apk")
        .setAutomationName("UiAutomator2")
        .setAppPackage("com.saucelabs.mydemoapp.rn")
        .setAppActivity(".MainActivity")
        .setAutoGrantPermissions(true) // Automatically grant app permissions
        .setNoReset(false) // Clear app state before each test
        .setNewCommandTimeout(Duration.ofSeconds(60));

    try {
      driver = new AndroidDriver(new URL(APPIUM_URL), options);
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
      driver = new AndroidDriver(new URL(APPIUM_URL), options); //
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