package com.milestone;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.datatable.DataTable;
import org.testng.Assert;

import com.milestone.Pages.ProductsPage;
import com.milestone.Pages.ProductDetailsPage;

import java.net.MalformedURLException;
import java.util.*;

public class ProductCatalogTest {
  private ProductsPage productsPage;
  private ProductDetailsPage productDetailsPage;

  @Before
  public void seUp() {
    BaseTest baseTest = new BaseTest();
    try {
      baseTest.setUp();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
  }

  @Given("I am on the products page")
  public void iAmOnTheProductsPage() {
    AndroidDriver driver = BaseTest.getDriver();
    productsPage = new ProductsPage(driver);
    Assert.assertTrue(productsPage.isDisplayed());
  }

  @Then("I should see the following products:")
  public void iShouldSeeTheFollowingProducts(DataTable dataTable) {
    List<Map<String, String>> products = dataTable.asMaps();
    for (Map<String, String> product : products) {
      String name = product.get("name");
      String price = product.get("price");
      Assert.assertTrue(productsPage.isProductDisplayed(name, price));
    }
  }

  @When("I tap on {string}")
  public void iTapOn(String productName) {
    productDetailsPage = productsPage.tapProduct(productName);
  }

  @Then("I should see the product details:")
  public void iShouldSeeTheProductDetails(DataTable dataTable) {
    Map<String, String> details = dataTable.asMap();
    Assert.assertEquals(productDetailsPage.getPrice(), details.get("price"));
    Assert.assertEquals(productDetailsPage.getRating(), details.get("rating"));
  }
}
