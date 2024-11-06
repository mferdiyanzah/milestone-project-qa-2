package steps.catalog;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import static org.junit.Assert.*;

import context.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.util.List;
import java.util.stream.Collectors;

public class CatalogSteps {
  private WebDriver driver;
  private final TestContext testContext;

  public CatalogSteps() {
    testContext = new TestContext();
  }

  @Before
  public void setup() throws Exception {
    driver = testContext.getDriver();
  }

  @After
  public void tearDown() {
    testContext.quitDriver();
  }

  @Given("I am on the catalog page")
  public void iAmOnTheCatalogPage() {
    // First login since we need to be authenticated
    driver.get("https://www.saucedemo.com/");
    driver.findElement(By.id("user-name")).sendKeys("standard_user");
    driver.findElement(By.id("password")).sendKeys("secret_sauce");
    driver.findElement(By.id("login-button")).click();

    assertTrue(driver.getCurrentUrl().contains("/inventory.html"));
  }

  @Then("I can see the catalog list")
  public void iCanSeeTheCatalogList() {
    WebElement inventoryList = driver.findElement(By.className("inventory_list"));
    assertTrue(inventoryList.isDisplayed());
  }

  @Then("I can see the product names")
  public void iCanSeeTheProductNames() {
    List<WebElement> productNames = driver.findElements(By.className("inventory_item_name"));
    assertFalse(productNames.isEmpty());
    productNames.forEach(name -> assertTrue(name.isDisplayed()));
  }

  @Then("I can see the product prices")
  public void iCanSeeTheProductPrices() {
    List<WebElement> productPrices = driver.findElements(By.className("inventory_item_price"));
    assertFalse(productPrices.isEmpty());
    productPrices.forEach(price -> assertTrue(price.isDisplayed()));
  }

  @Then("I can see the product descriptions")
  public void iCanSeeTheProductDescriptions() {
    List<WebElement> productDescs = driver.findElements(By.className("inventory_item_desc"));
    assertFalse(productDescs.isEmpty());
    productDescs.forEach(desc -> assertTrue(desc.isDisplayed()));
  }

  @When("I use sort by {word} {word}")
  public void iUseSortBy(String field, String direction) {
    Select sortDropdown = new Select(driver.findElement(By.className("product_sort_container")));
    System.out.println(sortDropdown.getOptions());
    switch (field) {
      case "name":
        if (direction.equals("descending")) {
          sortDropdown.selectByValue("za");
        } else {
          sortDropdown.selectByValue("az");
        }
        break;
      case "price":
        if (direction.equals("descending")) {
          sortDropdown.selectByValue("hilo");
        } else {
          sortDropdown.selectByValue("lohi");
        }
        break;
      default:
        break;
    }
  }

  @Then("I can see the products sorted by {word} {word}")
  public void iCanSeeTheProductsSortedBy(String field, String direction) {
    List<WebElement> elements;
    List<String> actualOrder;

    if (field.equals("name")) {
      elements = driver.findElements(By.className("inventory_item_name"));
      actualOrder = elements.stream()
          .map(WebElement::getText)
          .collect(Collectors.toList());
    } else {
      elements = driver.findElements(By.className("inventory_item_price"));
      actualOrder = elements.stream()
          .map(e -> e.getText().replace("$", ""))
          .collect(Collectors.toList());
    }

    List<String> sortedOrder = actualOrder.stream()
        .sorted((a, b) -> {
          if (field.equals("price")) {
            Double priceA = Double.parseDouble(a);
            Double priceB = Double.parseDouble(b);
            return direction.equals("ascending") ? priceA.compareTo(priceB) : priceB.compareTo(priceA);
          } else {
            return direction.equals("ascending") ? a.compareTo(b) : b.compareTo(a);
          }
        })
        .collect(Collectors.toList());

    assertEquals(sortedOrder, actualOrder);
  }

  @When("I click on the product {word}")
  public void iClickOnTheProduct(String element) {
    if (element.equals("name")) {
      driver.findElement(By.className("inventory_item_name")).click();
    } else if (element.equals("image")) {
      driver.findElement(By.className("inventory_item_img")).click();
    }
  }

  @Then("I can see the product details")
  public void iCanSeeTheProductDetails() {
    assertTrue(driver.findElement(By.className("inventory_details_container")).isDisplayed());
  }

  @When("I {word} a product {word} the cart")
  public void iManageProductInCart(String action, String preposition) {
    if (action.equals("add")) {
      List<WebElement> addButtons = driver.findElements(By.cssSelector("[data-test^='add-to-cart-sauce-labs']"));
      if (!addButtons.isEmpty()) {
        addButtons.get(0).click();
      }
    } else if (action.equals("remove")) {
      List<WebElement> removeButtons = driver.findElements(By.cssSelector("[data-test^='remove-sauce-labs']"));
      if (!removeButtons.isEmpty()) {
        removeButtons.get(0).click();
      }
    }
  }

  @Then("I {word} see the product in the cart")
  public void iVerifyProductInCart(String visibility) {
    int cartItems = driver.findElements(By.className("shopping_cart_badge")).size();
    if (visibility.equals("can")) {
      assertEquals(1, cartItems);
    } else {
      assertEquals(0, cartItems);
    }
  }

  @When("I {word} multiple products {word} the cart")
  public void iManageMultipleProductsInCart(String action, String preposition) {
    List<WebElement> buttons = driver.findElements(
        By.cssSelector(
            action.equals("add") ? "[data-test^='add-to-cart-sauce-labs']" : "[data-test^='remove-sauce-labs']"));
    buttons.stream()
        .limit(2)
        .forEach(WebElement::click);
  }

  @Then("I can see the cart total with value {int}")
  public void iCanSeeTheCartTotalWithValue(Integer value) {
    if (value == 0) {
      assertTrue(driver.findElements(By.className("shopping_cart_badge")).isEmpty());
    } else {
      WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
      assertEquals(value.toString(), cartBadge.getText());
    }
  }
}
