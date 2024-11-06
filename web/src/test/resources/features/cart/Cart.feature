@Cart
Feature: Cart Page
  As a user
  I want to see the cart page
  So that I can see the products in the cart

  Background:
    Given I am on the catalog page

  Rule: I have a product in the cart and have logged in
    Scenario: I can see the cart page
      When I am on the cart page
      Then I can see the cart page

    Scenario: I can see the product in the cart with the correct product name
      When I add a product to the cart
      And I am on the cart page
      Then I can see the product in the cart with the correct product name

    Scenario: I can see the product in the cart with the correct product price
      When I add a product to the cart
      And I am on the cart page
      Then I can see the product in the cart with the correct product price

    Scenario: I can remove a product from the cart
      When I remove a product from the cart
      Then I cannot see the product in the cart

    Scenario: I can see the cart total with value 0
      When I am on the cart page
      Then I can see the cart total with value 0

    Scenario: I can see the cart total after removing a product
      When I remove a product from the cart
      Then I can see the cart total with value 0

    Scenario: I can go back to the catalog page with the continue shopping button
      When I am on the cart page
      And I click continue shopping
      Then I am redirected to the catalog page

    Scenario: I can go back to the catalog page with the back button
      When I am on the cart page
      And I click back
      Then I am redirected to the catalog page

  Rule: I have a product in the cart and want to checkout
    Scenario: I can checkout with the checkout button
      When I am on the cart page
      And I click checkout
      Then I am redirected to the checkout page

    Scenario: I can see the checkout page
      When I am on the checkout page
      Then I can see the checkout page

    Scenario: I can input the correct checkout information
      When I am on the cart page
      And I click checkout
      Then I can see the checkout page
      When I input the correct checkout information
      Then I can see the checkout overview

    Scenario: I can see the total price correctly
      When I add multiple products to the cart
      Then I can see the cart total with value 2
      When I am on the cart page
      Then I can see the cart page
      When I click checkout
      Then I can see the checkout page
      When I input the correct checkout information
      Then I can see the total price correctly

    Scenario: I can successfully checkout
      When I add a product to the cart
      When I am on the cart page
      And I click checkout
      Then I can see the checkout page
      When I input the correct checkout information
      And I click finish
      Then I can see success checkout message

    @Negative
    Scenario: I cannot checkout with empty checkout information
      When I am on the cart page
      And I click checkout
      Then I can see the checkout page
      When I input empty checkout information
      Then I cannot see the checkout complete page
      And I see the error message

    @Negative
    Scenario: I cannot checkout with empty first name
      When I am on the cart page
      And I click checkout
      Then I can see the checkout page
      When I input empty first name
      Then I cannot see the checkout complete page
      And I see the error message

    @Negative
    Scenario: I cannot checkout with empty last name
      When I am on the cart page
      And I click checkout
      Then I can see the checkout page
      When I input empty last name
      Then I cannot see the checkout complete page
      And I see the error message

    @Negative
    Scenario: I cannot checkout with empty postal code
      When I am on the cart page
      And I click checkout
      Then I can see the checkout page
      When I input empty postal code
      Then I cannot see the checkout complete page
      And I see the error message

  Rule: I do not have a product in the cart
    Scenario: I can see the cart total after adding a product
      When I add a product to the cart
      Then I can see the cart total with value 1
