Feature: Cart
  As a user
  I want to add the product to cart
  So that I can checkout

  Scenario: I can add a product to cart
    Given I am on the products page
    When I tap on "Sauce Labs Backpack"
    And I click add to cart button
    And I go to cart screen
    Then I can see the cart screen
    And I can see the "Sauce Labs Backpack" in the cart

  Scenario: I can add multiple products to cart
    Given I am on the products page
    When I tap on "Sauce Labs Backpack"
    And I click add to cart button
    And I click back button
    And I go to cart screen
    Then I can see the "Sauce Labs Backpack" in the cart
    When I click back button
    And I tap on "Sauce Labs Bike Light"
    When I click add to cart button
    And I go to cart screen
    Then I can see the "Sauce Labs Bike Light" in the cart

  Rule: I have not logged in yet
    Scenario: I can not checkout a product if not logged in yet
      Given I am on the products page
      When I tap on "Sauce Labs Backpack"
      And I click add to cart button
      And I go to cart screen
      Then I can see the cart screen
      And I can see the "Sauce Labs Backpack" in the cart
      When I go to checkout screen
      Then I can see the login screen

  Rule: I have logged in
    Scenario: I can checkout a product succesfully
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
      Then I can see the shipping address
      When I fill the checkout information with following information:
        | fullName | address   | city            | state  | zipCode | country   |
        | John Doe | Boulevard | South Tangerang | Banten | 123124  | Indonesia |
      Then I can see the payment checkout screen
      When I fill payment method information with following:
        | fullName | cardNumber      | expirationDate | securityCode |
        | John Doe | 123412341234123 | 12/25          | 123          |
      And I go to review order screen
      Then I can see the review order screen
      When I go to review order screen
      Then I can see the complete checkout screen