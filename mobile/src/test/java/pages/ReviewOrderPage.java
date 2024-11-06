package pages;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class ReviewOrderPage {
  public AndroidDriver driver;

  public ReviewOrderPage(AndroidDriver driver) {
    this.driver = driver;
    PageFactory.initElements(new AppiumFieldDecorator(driver), this);
  }

  public boolean isReviewOrderDisplayed() {
    var wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.visibilityOfElementLocated(
        AppiumBy.androidUIAutomator("\t\r\n" + //
            "new UiSelector().text(\"Review your order\")")));
    var reviewOrder = driver.findElement(AppiumBy.androidUIAutomator("\t\r\n" + //
        "new UiSelector().text(\"Review your order\")"));
    return reviewOrder.isDisplayed();
  }

  public boolean isProductDisplayed(String productName) {
    WebElement product = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(" + productName
        + ")"));
    return product.isDisplayed();
  }

  public void clickPlaceOrder() {
    WebElement placeOrder = driver.findElement(AppiumBy.androidUIAutomator("\t\r\n" + //
        "new UiSelector().text(\"Place Order\")"));
    placeOrder.click();
  }
}
