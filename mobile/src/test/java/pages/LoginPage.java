package pages;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class LoginPage {
  private AndroidDriver driver;

  public LoginPage(AndroidDriver driver) {
    this.driver = driver;
    PageFactory.initElements(new AppiumFieldDecorator(driver), this);
  }

  public boolean isDisplayed() {
    var loginText = AppiumBy.androidUIAutomator("new UiSelector().text(\"Login\").instance(0)");

    return driver.findElement(loginText).isDisplayed();
  }

  public void login(String username, String password) {
    var usernameField = driver
        .findElement(AppiumBy.androidUIAutomator("new UiSelector().description(\"Username input field\")"));
    usernameField.sendKeys(username);

    var passwordField = driver
        .findElement(AppiumBy.androidUIAutomator("\t\r\n" + //
            "new UiSelector().description(\"Password input field\")"));
    passwordField.sendKeys(password);
    driver.findElement(AppiumBy.accessibilityId("Login button")).click();
  }

  public boolean iCanSeeErrorMessage() {
    var errorMessage = driver.findElement(AppiumBy
        .androidUIAutomator("new UiSelector().text(\"Provided credentials do not match any user in this service.\")"));

    return errorMessage.isDisplayed();
  }

  public boolean iCanSeeUsernameError() {
    var errorMessage = driver.findElement(AppiumBy
        .androidUIAutomator("\t\r\n" + //
            "new UiSelector().description(\"Username-error-message\")"));

    return errorMessage.isDisplayed();
  }

  public boolean iCanSeePasswordError() {
    var errorMessage = driver.findElement(AppiumBy
        .androidUIAutomator("\t\r\n" + //
            "new UiSelector().text(\"Password is required\")"));

    return errorMessage.isDisplayed();
  }
}
