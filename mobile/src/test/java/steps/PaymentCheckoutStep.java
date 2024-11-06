package steps;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import io.appium.java_client.android.AndroidDriver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.PaymentCheckoutPage;
import utils.BaseTest;

public class PaymentCheckoutStep {
  private PaymentCheckoutPage paymentCheckoutPage;
  private static AndroidDriver driver;

  public PaymentCheckoutStep() throws MalformedURLException {
    driver = BaseTest.getDriver();
    paymentCheckoutPage = new PaymentCheckoutPage(driver);
  }

  @Then("I can see the payment checkout screen")
  public void iCanSeeThePaymentCheckoutScreen() {
    paymentCheckoutPage.isPaymentCheckoutDisplayed();
  }

  @When("I fill payment method information with following:")
  public void fillPaymentMethodInformation(DataTable dataTable) {
    List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);

    String fullName = data.get(0).get("fullName");
    String cardNumber = data.get(0).get("cardNumber");
    String expirationDate = data.get(0).get("expirationDate");
    String securityCode = data.get(0).get("securityCode");

    paymentCheckoutPage.fillPaymentMethod(fullName, cardNumber, expirationDate, securityCode);
  }

  @When("I go to review order screen")
  public void goToReviewOrderScreen() {
    paymentCheckoutPage.goToReviewOrder();
  }
}
