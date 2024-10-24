package com.milestone.Pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

public class ProductsPage {
  @AndroidFindBy(xpath = "//android.widget.TextView")
  private WebElement productsTitle;

  @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc=\"store item text\"]")
  private List<WebElement> productNames;

  @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc=\"store item price\"]")
  private List<WebElement> productPrices;

  private AndroidDriver driver;

  public ProductsPage(AndroidDriver driver) {
    this.driver = driver;
    PageFactory.initElements(new AppiumFieldDecorator(driver), this);
  }

  public boolean isDisplayed() {
    return productsTitle.isDisplayed();
  }

  public boolean isProductDisplayed(String name, String price) {
    boolean nameFound = false;
    boolean priceFound = false;
    System.out.println(productNames);

    for (WebElement productName : productNames) {
      if (productName.getText().equals(name)) {
        nameFound = true;
        break;
      }
    }

    for (WebElement productPrice : productPrices) {
      if (productPrice.getText().equals(price)) {
        priceFound = true;
        break;
      }
    }

    return nameFound && priceFound;
  }

  public ProductDetailsPage tapProduct(String productName) {
    for (WebElement element : productNames) {
      if (element.getText().equals(productName)) {
        element.click();
        return new ProductDetailsPage(driver);
      }
    }
    throw new RuntimeException("Product not found: " + productName);
  }
}