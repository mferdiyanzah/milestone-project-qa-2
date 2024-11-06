package pages;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class PaymentCheckoutPage {
  public AndroidDriver driver;

  public PaymentCheckoutPage(AndroidDriver driver) {
    this.driver = driver;
    PageFactory.initElements(new AppiumFieldDecorator(driver), this);
  }

  public boolean isPaymentCheckoutDisplayed() {
    var paymentMethod = driver
        .findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Enter a payment method\")"));
    return paymentMethod.isDisplayed();
  }

  public void fillPaymentMethod(String fullName, String cardNumber, String expirationDate, String securityCode) {
    var fullNameField = driver
        .findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Rebecca Winter\")"));
    fullNameField.sendKeys(fullName);

    var cardNumberField = driver
        .findElement(AppiumBy.androidUIAutomator("\t\r\n" + //
            "new UiSelector().text(\"3258 1265 7568 789\")"));
    cardNumberField.sendKeys(cardNumber);

    var expirationDateField = driver
        .findElement(AppiumBy.androidUIAutomator("\t\r\n" + //
            "new UiSelector().text(\"03/25\")"));
    expirationDateField.sendKeys(expirationDate);

    var securityCodeField = driver
        .findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"123\")"));
    securityCodeField.sendKeys(securityCode);
  }

  public void goToReviewOrder() {
    driver.findElement(AppiumBy.androidUIAutomator("\t\r\n" + //
        "new UiSelector().description(\"Review Order button\")")).click();
  }
}
