package pages;

import org.checkerframework.checker.units.qual.s;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class MenuPage {
  private AndroidDriver driver;

  public MenuPage(AndroidDriver driver) {
    this.driver = driver;
    PageFactory.initElements(new AppiumFieldDecorator(driver), this);
  }

  public void openSidebar() {
    driver
        .findElement(
            AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.ImageView\").instance(0)"))
        .click();
  }

  public void openMenu(String menu) {
    switch (menu) {
      case "Login":
        driver.findElement(AppiumBy.accessibilityId("menu item log in")).click();
        break;
      case "Cart":
        driver.findElement(AppiumBy.accessibilityId("Cart")).click();
        break;
      default:
        throw new RuntimeException("Unknown menu: " + menu);
    }
  }

  public void isSidebarOpen() {
    driver.findElement(AppiumBy.accessibilityId("menu item catalog")).isDisplayed();
  }
}
