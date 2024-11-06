package steps;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.CartPage;
import pages.ProductDetailsPage;
import pages.ProductsPage;
import utils.BaseTest;
import utils.Util;

public class ProductCatalogStep {
  private ProductsPage productsPage;
  private ProductDetailsPage productDetailsPage;
  private CartPage cartPage;
  private static AndroidDriver driver;

  @Before
  public void seUp() {
    try {
      BaseTest.setUp();
      driver = BaseTest.getDriver();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
  }

  @Given("I am on the products page")
  public void iAmOnTheProductsPage() {
    productsPage = new ProductsPage(driver);
    Assert.assertTrue(productsPage.isDisplayed());
  }

  @When("I click on sort button")
  public void iClickTheSortButton() {
    productsPage.clickSort();
  }

  @Then("I should see the sort modal")
  public void iShouldSeeTheSortModal() {
    var sortModal = driver
        .findElement(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.view.ViewGroup\").instance(3)"));

    Assert.assertTrue(sortModal.isDisplayed());
  }

  @When("I choose sort by {string} with {string}")
  public void iChooseSortBy(String sortBy, String order) {
    String accesbilityId = sortBy + Util.capitalizeFirstLetter(order);
    driver.findElement(AppiumBy.accessibilityId(accesbilityId)).click();
  }

  @Then("I should see the products sorted by {string} with {string}")
  public void iShouldSeeTheProductsSortedBy(String sortBy, String order) {
    String accessibilityId = sortBy + Util.capitalizeFirstLetter(order);
    List<WebElement> products = driver
        .findElements(AppiumBy.androidUIAutomator("new UiSelector().description(\"store item\")"));

    List<WebElement> productPriceList = driver
        .findElements(AppiumBy.androidUIAutomator("new UiSelector().description(\"store item price\")"));

    List<String> productTexts = products.stream()
        .map(WebElement::getText)
        .collect(Collectors.toList());

    List<String> productPrices = productPriceList.stream()
        .map(WebElement::getText)
        .collect(Collectors.toList());

    // Sort product texts based on the criteria
    List<String> sortedProductTexts = new ArrayList<>(productTexts); // Make a copy for sorting
    List<String> sortedProductPrices = new ArrayList<>(productPrices); // Make a copy for sorting
    switch (accessibilityId) {
      case "nameDesc":
        sortedProductTexts.sort(Comparator.naturalOrder());
        Collections.reverse(sortedProductTexts); // For descending
        break;
      case "nameAsc":
        sortedProductTexts.sort(Comparator.naturalOrder());
        break;
      case "priceDesc":
        sortedProductPrices.sort(Comparator.comparingDouble(this::parsePrice));
        Collections.reverse(sortedProductPrices);
        break;
      case "priceAsc":
        sortedProductPrices.sort(Comparator.comparingDouble(this::parsePrice));
        break;
      default:
        throw new IllegalArgumentException("Invalid sort parameters: " + accessibilityId);
    }

    // Collect the actual displayed order after sorting
    List<WebElement> displayedProducts = driver
        .findElements(AppiumBy.androidUIAutomator("new UiSelector().description(\"store item\")"));
    List<String> displayedProductTexts = displayedProducts.stream()
        .map(WebElement::getText)
        .collect(Collectors.toList());

    // Validate that the displayed products match the expected sorted order
    Assert.assertEquals(displayedProductTexts, sortedProductTexts);
  }

  private double parsePrice(String priceText) {
    try {
      String cleanedPrice = priceText.replace("$", "").trim();
      System.out.println(priceText + " -> " + cleanedPrice);
      if (cleanedPrice.isEmpty()) {
        throw new NumberFormatException("Price is empty");
      }
      return Double.parseDouble(cleanedPrice);
    } catch (NumberFormatException e) {
      System.err.println("Unable to parse price: " + priceText);
      return Double.NaN;
    }
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

  @Then("I should see all products have ratings")
  public void iShouldSeeAllProductsHaveRatings() {
    List<WebElement> reviewStar = driver
        .findElements(By.xpath("//android.view.ViewGroup[contains(@content-desc, 'review star')]"));
    Assert.assertTrue(reviewStar.size() > 0);
  }

  @Then("I should see all product images displayed")
  public void iShouldSeeAllProductImagesDisplayed() {
    List<WebElement> productImages = driver
        .findElements(AppiumBy.androidUIAutomator("\t\r\n" + //
            "new UiSelector().className(\"android.widget.ImageView\")"));
    for (WebElement productImage : productImages) {
      Assert.assertTrue(productImage.isDisplayed());
    }
  }

  @Then("I click add to cart button")
  public void iClickAddToCartButton() {
    productDetailsPage.addToCart();
  }

  @When("I click back button")
  public void iClickBackButton() {
    productDetailsPage.navigateBack();
  }

  @When("I go to cart screen")
  public void iGoToCartScreen() {
    cartPage = productDetailsPage.goToCart();
  }
}
