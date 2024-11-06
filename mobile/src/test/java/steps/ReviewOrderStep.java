package steps;

import java.net.MalformedURLException;

import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.ReviewOrderPage;
import utils.BaseTest;

public class ReviewOrderStep {
  public ReviewOrderPage reviewOrderPage;
  private static AndroidDriver driver;

  public ReviewOrderStep() throws MalformedURLException {
    driver = BaseTest.getDriver();
    reviewOrderPage = new ReviewOrderPage(driver);
  }

  @Then("I can see the review order screen")
  public void iCanSeeTheReviewOrderScreen() {
    reviewOrderPage.isReviewOrderDisplayed();
  }

  @Then("I can see the product {string} in the review order screen")
  public void iCanSeeTheProductInTheReviewOrderScreen(String productName) {
    reviewOrderPage.isProductDisplayed(productName);
  }

  @When("I go to place order screen")
  public void goToReviewOrderScreen() {
    reviewOrderPage.clickPlaceOrder();
  }
}
