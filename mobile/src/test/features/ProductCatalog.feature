Feature: Product Catalog
  As a user
  I want to browse products
  So that I can view details and add items to cart

  Scenario: View product list
    Given I am on the products page
    Then I should see the following products:
      | name                     | price  |
      | Sauce Labs Backpack      | $29.99 |
      | Sauce Labs Bike Light    | $9.99  |
      | Sauce Labs Bolt T-Shirt  | $15.99 |
      | Sauce Labs Fleece Jacket | $49.99 |

  Scenario: View product details
    Given I am on the products page
    When I tap on "Sauce Labs Backpack"
    Then I should see the product details:
      | price  | $29.99 |
      | rating | 5      |
