package steps;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

import io.appium.java_client.android.AndroidDriver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.AddressCheckoutPage;
import utils.BaseTest;

public class AddressCheckoutStep {
  private AddressCheckoutPage addressCheckoutPage;
  private static AndroidDriver driver;

  public AddressCheckoutStep() throws MalformedURLException {
    driver = BaseTest.getDriver();
    addressCheckoutPage = new AddressCheckoutPage(driver);
  }

  @Then("I can see the shipping address")
  public void iCanSeeTheShippingAddress() {
    Assert.assertTrue(addressCheckoutPage.isShippingAddressDisplayed());
  }

  @When("I fill the checkout information with following information:")
  public void fillCheckoutForm(DataTable dataTable) {
    List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
    String fullName = data.get(0).get("fullName");
    String address = data.get(0).get("address");
    String city = data.get(0).get("city");
    String state = data.get(0).get("state");
    String zipCode = data.get(0).get("zipCode");
    String country = data.get(0).get("country");
    System.out.println(fullName + " " + address + " " + city + " " + state + " " + zipCode + " " + country);

    addressCheckoutPage.fillCheckoutForm(fullName, address, city, state, zipCode, country);
  }
}
