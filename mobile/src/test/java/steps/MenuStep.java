package steps;

import java.net.MalformedURLException;

import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.MenuPage;
import utils.BaseTest;

public class MenuStep {
  private MenuPage menuPage;
  private static AndroidDriver driver;

  public MenuStep() throws MalformedURLException {
    driver = BaseTest.getDriver();
    menuPage = new MenuPage(driver);
  }

  @When("I open the side menu")
  public void iOpenTheSideMenu() {
    menuPage.openSidebar();
  }

  @Then("I can see the side menu")
  public void iCanSeeTheSideMenu() {
    menuPage.isSidebarOpen();
  }

  @When("I open the {string} menu")
  public void iOpenTheMenu(String menu) {
    menuPage.openMenu(menu);
  }
}
