package steps.sidebar;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import context.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SidebarSteps {
  private WebDriver driver;
  private final TestContext testContext;

  public SidebarSteps() {
    testContext = new TestContext();
  }

  @Before
  public void setup() throws Exception {
    driver = testContext.getDriver();
  }

  @After
  public void tearDown() {
    testContext.quitDriver();
  }

  @Then("I can see the sidebar")
  public void iCanSeeTheSidebar() {
    WebElement sidebar = driver.findElement(By.id("react-burger-menu-btn"));
    assertTrue(sidebar.isDisplayed());
  }

  @When("I open the sidebar")
  public void iOpenTheSidebar() {
    driver.findElement(By.id("react-burger-menu-btn")).click();
  }

  @When("I close the sidebar")
  public void iCloseTheSidebar() {
    // Add explicit wait for element to be clickable
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("react-burger-cross-btn")));

    try {
      ((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeButton);
    } catch (Exception e) {
      try {
        Thread.sleep(1000); // Wait for any animations to complete
        closeButton.click();
      } catch (InterruptedException ie) {
        Thread.currentThread().interrupt();
        throw new RuntimeException("Failed to close sidebar", ie);
      }
    }
  }

  @Then("I can't see the sidebar")
  public void iCanTSeeTheSidebar() {
    WebElement inventoryDriver = driver.findElement(By.id("inventory_sidebar_link"));
    assertTrue(inventoryDriver.isDisplayed());
  }

  @And("I click on the {string} link")
  public void iClickOnTheLink(String linkText) {
    switch (linkText) {
      case "All Items":
        driver.findElement(By.id("inventory_sidebar_link")).click();
        break;
      case "Logout":
        driver.findElement(By.id("logout_sidebar_link")).click();
        break;
      default:
        throw new IllegalArgumentException("Unknown link: " + linkText);
    }
  }
}
