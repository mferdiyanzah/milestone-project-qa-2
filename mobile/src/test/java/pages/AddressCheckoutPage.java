package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class AddressCheckoutPage {
  public AndroidDriver driver;

  public AddressCheckoutPage(AndroidDriver driver) {
    this.driver = driver;
    PageFactory.initElements(new AppiumFieldDecorator(driver), driver);
  }

  public boolean isShippingAddressDisplayed() {
    System.out.println("shippingAddress: " + driver);
    WebElement shippingAddress = driver
        .findElement(AppiumBy.androidUIAutomator("\t\r\n" + //
            "new UiSelector().text(\"Enter a shipping address\")"));
    return shippingAddress.isDisplayed();
  }

  public void fillCheckoutForm(String fullName, String address, String city, String state, String zipCode,
      String country) {
    var fullNameField = driver
        .findElement(AppiumBy.androidUIAutomator("\t\r\n" + //
            "new UiSelector().text(\"Rebecca Winter\")"));
    fullNameField.sendKeys(fullName);

    var addressField = driver
        .findElement(AppiumBy.androidUIAutomator("\t\r\n" + //
            "new UiSelector().text(\"Mandorley 112\")"));
    addressField.sendKeys(address);

    var cityField = driver
        .findElement(AppiumBy.accessibilityId("City* input field"));
    cityField.sendKeys(city);

    var stateField = driver
        .findElement(AppiumBy.accessibilityId("State/Region input field"));
    stateField.sendKeys(state);

    var zipCodeField = driver
        .findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"89750\")"));
    zipCodeField.sendKeys(zipCode);

    var countryField = driver
        .findElement(AppiumBy.accessibilityId("Country* input field"));
    countryField.sendKeys(country);

    var toPaymentButton = driver
        .findElement(AppiumBy.accessibilityId("To Payment button"));
    toPaymentButton.click();
  }
}
