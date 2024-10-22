package com.milestone;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestGoogle {

  WebDriver driver;

  @BeforeTest
  public void setup() {
    // Set ChromeDriver path
    driver = new ChromeDriver();
  }

  @Test
  public void googleSearch() {
    System.out.println("Starting Google search test...");
    driver.get("https://www.google.com");
    System.out.println("Navigated to Google homepage");

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
    System.out.println("Search box found");

    searchBox.sendKeys("Selenium WebDriver");
    searchBox.submit();
    System.out.println("Search submitted");

    String title = driver.getTitle();
    System.out.println("Page title: " + title);
    assert title.contains("Selenium WebDriver");
  }

  @AfterTest
  public void teardown() {
    // Close browser
    driver.quit();
  }
}
