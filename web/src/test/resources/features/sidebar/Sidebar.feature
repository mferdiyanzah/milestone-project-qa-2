@Sidebar
Feature: Sidebar
  As a user
  I want to see the sidebar
  So that I can navigate to different pages

  Background:
    When I am on the catalog page

  @Positive
  Scenario: I can see the sidebar
    When I am on the catalog page
    Then I can see the sidebar

  Scenario: I can see the sidebar on the cart page
    When I am on the cart page
    Then I can see the sidebar

  Scenario: I can see the sidebar on the checkout page
    When I am on the checkout page
    Then I can see the sidebar

  Scenario: I can use the sidebar to navigate to the catalog page
    When I am on the cart page
    And I open the sidebar
    And I click on the "All Items" link
    Then I am redirected to the catalog page

  Scenario: I can logout from the sidebar
    When I am on the catalog page
    And I open the sidebar
    And I click on the "Logout" link
    Then I am redirected to the login page

  Scenario: I can close the sidebar
    When I am on the catalog page
    And I open the sidebar
    And I close the sidebar
    Then I can't see the sidebar
