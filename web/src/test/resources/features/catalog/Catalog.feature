@CatalogList
Feature: Catalog List
  As a user
  I want to see the catalog list
  So that I can see the products

  Background:
    Given I am on the catalog page

  Rule: I have logged in
    Scenario: I can see the catalog list
      When I am on the catalog page
      Then I can see the catalog list
      And I can see the product names
      And I can see the product prices
      And I can see the product descriptions

    Scenario: I can use sort by name descending
      When I use sort by name descending
      Then I can see the products sorted by name descending

    Scenario: I can use sort by name ascending
      When I use sort by name ascending
      Then I can see the products sorted by name ascending

    Scenario: I can use sort by price descending
      When I use sort by price descending
      Then I can see the products sorted by price descending

    Scenario: I can use sort by price ascending
      When I use sort by price ascending
      Then I can see the products sorted by price ascending

    Scenario: I can see the product details by clicking on the product name
      When I click on the product name
      Then I can see the product details

    Scenario: I can see the product details by clicking on the product image
      When I click on the product image
      Then I can see the product details


  Rule: I do not have a product in the cart
    Scenario: I want to add a product to the cart
      When I add a product to the cart
      Then I can see the product in the cart

    Scenario: I want to remove a product from the cart
      When I remove a product from the cart
      Then I cannot see the product in the cart

    Scenario: I want to see the cart total
      When I am on the catalog page
      Then I can see the cart total with value 0

    Scenario: I want to see the cart total after deleting a product
      When I delete a product from the cart
      Then I can see the cart total with value 0

    Scenario: I want to see the cart total after adding a product
      When I add a product to the cart
      Then I can see the cart total with value 1

    Scenario: I want to add multiple products to the cart
      When I add multiple products to the cart
      Then I can see the cart total with value 2

    Scenario: I want to delete multiple products from the cart
      When I delete multiple products from the cart
      Then I can see the cart total with value 0

