package com.milestone.Pages;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class ProductDetailsPage {

  @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc=\"product price\"]")
  private WebElement priceElement;

  @AndroidFindBy(xpath = "//android.widget.TextView")
  private WebElement titleElement;

  @AndroidFindBy(xpath = "//android.widget.ScrollView[@content-desc=\"product screen\"]/android.view.ViewGroup/android.widget.ImageView\r\n"
      + //
      "")
  private WebElement productImage;

  @AndroidFindBy(xpath = "//android.widget.Button[contains(@resource-id, 'addToCartBtn')]")
  private WebElement addToCartButton;

  @AndroidFindBy(xpath = "//android.widget.TextView[contains(@resource-id, 'productDescription')]")
  private WebElement descriptionElement;

  private AndroidDriver driver;

  public ProductDetailsPage(AndroidDriver driver) {
    this.driver = driver;
    PageFactory.initElements(new AppiumFieldDecorator(driver), this);
  }

  // Get product price
  public String getPrice() {
    return priceElement.getText();
  }

  // Get product title
  public String getTitle() {
    return titleElement.getText();
  }

  // Get product rating (returns rating value as string)
  public String getRating() {
    List<WebElement> reviewStar = driver
        .findElements(By.xpath("//android.view.ViewGroup[contains(@content-desc, 'review star')]"));
    System.out.println(reviewStar.size());
    return String.valueOf(reviewStar.size());
  }

  // Check if product image is displayed
  public boolean isImageDisplayed() {
    return productImage.isDisplayed();
  }

  // Get product description
  public String getDescription() {
    return descriptionElement.getText();
  }

  // Add product to cart
  public void addToCart() {
    addToCartButton.click();
  }

  // Check if we're on the product details page
  public boolean isDisplayed() {
    return productImage.isDisplayed() && titleElement.isDisplayed();
  }

  // Scroll to product description (useful for longer product pages)
  public void scrollToDescription() {
    String uiAutomatorCommand = "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView("
        + "new UiSelector().resourceId(\"productDescription\"))";

    HashMap<String, String> scrollObject = new HashMap<>();
    scrollObject.put("command", "findElement");
    scrollObject.put("strategy", "-android uiautomator");
    scrollObject.put("selector", uiAutomatorCommand);

    driver.executeScript("mobile: scroll", scrollObject);
  }

  // Navigate back to products page
  public ProductsPage navigateBack() {
    driver.navigate().back();
    return new ProductsPage(driver);
  }
}