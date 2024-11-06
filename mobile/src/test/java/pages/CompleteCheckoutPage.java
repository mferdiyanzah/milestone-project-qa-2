package pages;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class CompleteCheckoutPage {
  public AndroidDriver driver;

  public CompleteCheckoutPage(AndroidDriver driver) {
    this.driver = driver;
    PageFactory.initElements(new AppiumFieldDecorator(driver), this);
  }

  public boolean isCompleteCheckoutDisplayed() {
    return driver.findElement(AppiumBy.androidUIAutomator("\t\r\n" + //
        "new UiSelector().text(\"Checkout Complete\")")).isDisplayed();
  }
}
