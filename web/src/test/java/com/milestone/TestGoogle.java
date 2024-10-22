package com.milestone;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestGoogle {
  private WebDriver driver;
  private WebDriverWait wait;

  // Get Selenium URL from environment variable or use localhost as default
  private static final String SELENIUM_HOST = System.getProperty("selenium.host",
      System.getenv("SELENIUM_HOST") != null ? System.getenv("SELENIUM_HOST") : "localhost");
  private static final String SELENIUM_URL = String.format("http://%s:4444/wd/hub", SELENIUM_HOST);

  @BeforeTest
  public void setup() throws MalformedURLException {
    System.out.println("Connecting to Selenium at: " + SELENIUM_URL);
    System.out.println(SELENIUM_HOST);

    ChromeOptions options = new ChromeOptions();
    options.addArguments("--no-sandbox");
    options.addArguments("--headless");
    options.addArguments("--disable-dev-shm-usage");
    options.addArguments("--disable-gpu");
    options.addArguments("--window-size=1920,1080");
    options.addArguments("--remote-allow-origins=*");

    try {
      driver = new RemoteWebDriver(new URL(SELENIUM_URL), options);
      wait = new WebDriverWait(driver, Duration.ofSeconds(10));
      driver.manage().window().maximize();
      driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
      driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

    } catch (Exception e) {
      System.err.println("Failed to initialize WebDriver: " + e.getMessage());
      throw e;
    }
  }

  @Test
  public void googleSearch() {
    try {
      driver.get("https://www.google.com");

      // Handle cookie consent if present
      try {
        WebElement consent = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("button[id='L2AGLb'], button[aria-label='Accept all']")));
        consent.click();
      } catch (Exception e) {
        System.out.println("No consent button found, continuing...");
      }

      WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(
          By.name("q")));
      searchBox.clear();
      searchBox.sendKeys("Selenium WebDriver");

      searchBox.submit();

      wait.until(ExpectedConditions.titleContains("Selenium WebDriver"));
      String actualTitle = driver.getTitle();
      Assert.assertTrue(actualTitle.contains("Selenium WebDriver"),
          "Expected title to contain 'Selenium WebDriver' but was: " + actualTitle);

    } catch (Exception e) {
      System.err.println("Test failed with error: " + e.getMessage());
      throw e;
    }
  }

  @AfterTest
  public void teardown() {
    if (driver != null) {
      try {
        driver.quit();
      } catch (Exception e) {
        System.err.println("Error during driver cleanup: " + e.getMessage());
      }
    }
  }
}