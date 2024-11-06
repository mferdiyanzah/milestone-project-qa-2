package steps;

import java.net.MalformedURLException;

import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.Then;
import pages.CompleteCheckoutPage;
import utils.BaseTest;

public class CompleteCheckoutStep {
  public CompleteCheckoutPage completeCheckoutPage;
  private static AndroidDriver driver;

  public CompleteCheckoutStep() throws MalformedURLException {
    driver = BaseTest.getDriver();
    completeCheckoutPage = new CompleteCheckoutPage(driver);
  }

  @Then("I can see the complete checkout screen")
  public void iCanSeeTheCompleteCheckoutScreen() {
    completeCheckoutPage.isCompleteCheckoutDisplayed();
  }
}
