package steps;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

import io.appium.java_client.android.AndroidDriver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.CartPage;
import utils.BaseTest;

public class CartStep {
  private CartPage cartPage;
  private static AndroidDriver driver;

  public CartStep() throws MalformedURLException {
    driver = BaseTest.getDriver();
    cartPage = new CartPage(driver);
  }

  @When("I go to checkout screen")
  public void iGoToCheckoutScreen() {
    cartPage.clickCheckout();
  }

  @Then("I can see the cart screen")
  public void iCanSeeTheCartScreen() {
    Assert.assertTrue(cartPage.isDisplayed());
  }

  @Then("I can see the {string} in the cart")
  public void iCanSeeTheProductInTheCart(String productName) {
    Assert.assertTrue(cartPage.isProductDisplayed(productName));
  }

  // @Before
  // public void setUp() throws MalformedURLException {
  // BaseTest.setUp();
  // driver = BaseTest.getDriver();
  // cartPage = new CartPage(driver);
  // }

  // @When("I click the cart")
  // public void iClickTheCart() {
  // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
  // WebElement cartButton =
  // wait.until(ExpectedConditions.visibilityOfElementLocated(
  // AppiumBy.androidUIAutomator("new UiSelector().description(\"Cart
  // button\")")));
  // cartButton.click();
  // cartPage = new CartPage(driver);
  // }

  // @Then("I can see the {string} in the cart")
  // public void iCanSeeTheProductInTheCart(String productName) {
  // var wait = new WebDriverWait(driver, Duration.ofSeconds(10));
  // var product = AppiumBy.xpath("//android.widget.TextView[@text='" +
  // productName + "']");
  // wait.until(
  // ExpectedConditions.visibilityOfElementLocated(product));

  // Assert.assertTrue(driver.findElement(product).isDisplayed());
  // }
}
