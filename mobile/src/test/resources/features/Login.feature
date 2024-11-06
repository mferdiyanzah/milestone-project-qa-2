Feature: Login Screen
  As a user
  I want to be able login to the app
  So that i can checkout later

  Scenario: I can access the login screen
    Given I am on the products page
    When I open the side menu
    Then I can see the side menu
    When I open the "Login" menu
    Then I can see the login screen

  Rule: I have valid credentials
    Scenario: I can login succesfully
      Given I am on the products page
      When I open the side menu
      Then I can see the side menu
      When I open the "Login" menu
      Then I can see the login screen
      When I login with following credentials:
        | username        | password |
        | bob@example.com | 10203040 |
      When I tap on "Sauce Labs Backpack"
      And I click add to cart button
      And I go to cart screen
      Then I can see the cart screen
      And I can see the "Sauce Labs Backpack" in the cart
      And I go to checkout screen

    Scenario: I cannot login with empty username and password
      Given I am on the products page
      When I open the side menu
      Then I can see the side menu
      When I open the "Login" menu
      Then I can see the login screen
      When I login with following credentials:
        | username | password |
        |          |          |


  Rule: I dont have any valid credentials
    Scenario: I can login succesfully
      Given I am on the products page
      When I open the side menu
      Then I can see the side menu
      When I open the "Login" menu
      Then I can see the login screen
      When I login with following credentials:
        | username   | password   |
        | bob@dw.com | 10dw203040 |
      Then I can see an error message