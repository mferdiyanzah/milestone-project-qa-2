package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class CartPage {
  private AndroidDriver driver;

  public CartPage(AndroidDriver driver) {
    this.driver = driver;
    PageFactory.initElements(new AppiumFieldDecorator(driver), this);
  }

  public void addProductToCart() {
    WebElement addToCartButton = driver
        .findElement(By.xpath("//android.view.ViewGroup[@content-desc='Add To Cart button']"));
    System.out.println("Add to cart button: " + addToCartButton);
    addToCartButton.click();
  }

  public void clickCart() {
    WebElement cartButton = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc='Cart button']"));
    System.out.println("Cart button: " + cartButton);
    cartButton.click();
  }

  public Boolean isDisplayed() {
    WebElement cartButton = driver.findElement(AppiumBy.androidUIAutomator("\t\r\n" + //
        "new UiSelector().text(\"My Cart\")"));
    System.out.println("Cart button: " + cartButton);
    return cartButton.isDisplayed();
  }

  public Boolean isProductDisplayed(String productName) {
    WebElement product = driver.findElements(AppiumBy.accessibilityId("product label")).stream()
        .filter(e -> e.getText().equals(productName)).findFirst().orElse(null);
    return product.isDisplayed();
  }

  public void clickCheckout() {
    WebElement checkoutButton = driver
        .findElement(AppiumBy.accessibilityId("Proceed To Checkout button"));
    checkoutButton.click();
  }

}