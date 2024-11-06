package steps.cart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import static org.junit.Assert.*;

import java.util.List;

import context.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;

public class CartSteps {
  private WebDriver driver;
  private final TestContext testContext;
  private static final String FIRST_NAME = "John";
  private static final String LAST_NAME = "Doe";
  private static final String POSTAL_CODE = "12345";

  public CartSteps() {
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

  @When("I am on the cart page")
  public void iAmOnTheCartPage() {
    driver.findElement(By.className("shopping_cart_link")).click();
    assertTrue(driver.getCurrentUrl().contains("/cart.html"));
  }

  @Then("I can see the cart page")
  public void iCanSeeTheCartPage() {
    assertTrue(driver.findElement(By.cssSelector("[data-test='title']")).getText().equals("Your Cart"));
  }

  @Then("I can see the product in the cart with the correct product {word}")
  public void iCanSeeTheProductInTheCartWithCorrectDetails(String detail) {
    WebElement cartItem = driver.findElement(By.className("cart_item"));
    if (detail.equals("name")) {
      assertTrue(cartItem.findElement(By.className("inventory_item_name")).isDisplayed());
    } else if (detail.equals("price")) {
      assertTrue(cartItem.findElement(By.className("inventory_item_price")).isDisplayed());
    }
  }

  @When("I click {word}")
  public void iClick(String button) {
    switch (button) {
      case "continue shopping":
        driver.findElement(By.id("continue-shopping")).click();
        break;
      case "back":
        driver.navigate().back();
        break;
      case "checkout":
        driver.findElement(By.id("checkout")).click();
        break;
      case "finish":
        driver.findElement(By.id("finish")).click();
        break;
      default:
        break;
    }
  }

  @Then("I am redirected to the {word} page")
  public void iAmRedirectedToPage(String page) {
    switch (page) {
      case "catalog":
        assertTrue(driver.getCurrentUrl().contains("/inventory.html"));
        break;
      case "checkout":
        assertTrue(driver.getCurrentUrl().contains("/checkout-step-one.html"));
        break;
      case "login":
        assertTrue(driver.getCurrentUrl().contains("/"));
        break;
      case "home":
        assertTrue(driver.getCurrentUrl().contains("/inventory.html"));
        break;
      default:
        break;
    }
  }

  @When("I am on the checkout page")
  public void iAmOnTheCheckoutPage() {
    driver.findElement(By.className("shopping_cart_link")).click();
    driver.findElement(By.id("checkout")).click();
    assertTrue(driver.getCurrentUrl().contains("/checkout-step-one.html"));
  }

  @Then("I can see the checkout page")
  public void iCanSeeTheCheckoutPage() {
    assertTrue(driver.findElement(By.id("checkout_info_container")).isDisplayed());
  }

  @When("I input the correct checkout information")
  public void iInputTheCorrectCheckoutInformation() {
    fillCheckoutForm(FIRST_NAME, LAST_NAME, POSTAL_CODE);
    driver.findElement(By.id("continue")).click();
  }

  @When("I input empty first name")
  public void iInputEmptyFirstName() {
    fillCheckoutForm("", LAST_NAME, POSTAL_CODE);
    driver.findElement(By.id("continue")).click();
  }

  @When("I input empty last name")
  public void iInputEmptyLastName() {
    fillCheckoutForm(FIRST_NAME, "", POSTAL_CODE);
    driver.findElement(By.id("continue")).click();
  }

  @When("I input empty postal code")
  public void iInputEmptyPostalCode() {
    fillCheckoutForm(FIRST_NAME, LAST_NAME, "");
    driver.findElement(By.id("continue")).click();
  }

  @When("I input empty checkout information")
  public void iInputEmptyCheckoutInformation() {
    fillCheckoutForm("", "", "");
    driver.findElement(By.id("continue")).click();
  }

  @When("I click continue shopping")
  public void iClickContinueShopping() {
    driver.findElement(By.id("continue-shopping")).click();
  }

  @Then("I can see the checkout complete page")
  public void iCanSeeTheCheckoutCompletePage() {
    assertTrue(driver.getCurrentUrl().contains("/checkout-complete.html"));
    assertTrue(driver.findElement(By.id("checkout_complete_container")).isDisplayed());
  }

  @Then("I cannot see the checkout complete page")
  public void iCannotSeeTheCheckoutCompletePage() {
    assertFalse(driver.getCurrentUrl().contains("/checkout-complete.html"));
  }

  @And("I see the error message")
  public void iSeeTheErrorMessage() {
    assertTrue(driver.findElement(By.cssSelector("[data-test='error']")).isDisplayed());
  }

  @Then("I can see the total price correctly")
  public void iCanSeeTheTheTotalPriceCorrectly() {
    // find the each item price and sum them up and compare with the total price
    List<WebElement> itemPrices = driver.findElements(By.className("inventory_item_price"));
    double total = itemPrices.stream()
        .mapToDouble(e -> Double.parseDouble(e.getText().replace("$", "")))
        .sum();

    // convert total to string with 2 decimal places
    String totalString = String.format("%.2f", total);
    assertEquals(driver.findElement(By.className("summary_subtotal_label")).getText(), "Item total: $" + totalString);
  }

  @Then("I can see success checkout message")
  public void iCanSeeSuccessCheckoutMessage() {
    assertTrue(driver.findElement(By.cssSelector("[data-test='complete-header']")).isDisplayed());
  }

  @Then("I can see the checkout overview")
  public void iCanSeeTheCheckoutOverview() {
    assertTrue(driver.findElement(By.cssSelector("[data-test='checkout-summary-container']")).isDisplayed());
  }

  private void fillCheckoutForm(String firstName, String lastName, String postalCode) {
    driver.findElement(By.id("first-name")).sendKeys(firstName);
    driver.findElement(By.id("last-name")).sendKeys(lastName);
    driver.findElement(By.id("postal-code")).sendKeys(postalCode);
  }
}