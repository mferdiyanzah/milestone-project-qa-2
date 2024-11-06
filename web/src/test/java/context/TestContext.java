package context;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TestContext {
  private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
  private static final String SELENIUM_HOST = System.getProperty("selenium.host",
      System.getenv("SELENIUM_HOST") != null ? System.getenv("SELENIUM_HOST") : "localhost");
  private static final String SELENIUM_URL = String.format("http://%s:4444/wd/hub", SELENIUM_HOST);
  private static final Duration TIMEOUT = Duration.ofSeconds(30);

  public WebDriver getDriver() throws MalformedURLException {
    if (driverThreadLocal.get() == null) {
      initializeDriver();
    }
    return driverThreadLocal.get();
  }

  private void initializeDriver() throws MalformedURLException {
    try {
      ChromeOptions options = new ChromeOptions();
      options.addArguments(
          "--no-sandbox",
          "--disable-dev-shm-usage",
          // "--headless=new",
          "--disable-gpu",
          "--window-size=1920,1080",
          "--remote-allow-origins=*",
          "--disable-extensions",
          "--disable-logging");

      WebDriver driver = new RemoteWebDriver(new URL(SELENIUM_URL), options);

      // Set timeouts
      driver.manage().timeouts().implicitlyWait(TIMEOUT);
      driver.manage().timeouts().pageLoadTimeout(TIMEOUT);
      driver.manage().timeouts().scriptTimeout(TIMEOUT);
      driver.manage().window().maximize();

      driverThreadLocal.set(driver);
    } catch (Exception e) {
      System.err.println("Failed to initialize WebDriver: " + e.getMessage());
      throw e;
    }
  }

  public void quitDriver() {
    WebDriver driver = driverThreadLocal.get();
    if (driver != null) {
      try {
        driver.quit();
      } catch (Exception e) {
        System.err.println("Error quitting driver: " + e.getMessage());
      } finally {
        driverThreadLocal.remove();
      }
    }
  }

  // Add a runtime shutdown hook
  static {
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      WebDriver driver = driverThreadLocal.get();
      if (driver != null) {
        try {
          driver.quit();
        } catch (Exception e) {
          System.err.println("Error in shutdown hook: " + e.getMessage());
        } finally {
          driverThreadLocal.remove();
        }
      }
    }));
  }
}
