# Feature: Product Catalog
#   As a user
#   I want to browse products
#   So that I can view details and add items to cart

#   Scenario: I can see the product list
#     Given I am on the products page
#     Then I should see the following products:
#       | name                     | price  |
#       | Sauce Labs Backpack      | $29.99 |
#       | Sauce Labs Bike Light    | $9.99  |
#       | Sauce Labs Bolt T-Shirt  | $15.99 |
#       | Sauce Labs Fleece Jacket | $49.99 |

#   Scenario: I can see the product details
#     Given I am on the products page
#     When I tap on "Sauce Labs Backpack"
#     Then I should see the product details:
#       | price  | $29.99 |
#       | rating | 5      |

#   Scenario: I can see the sort modal
#     Given I am on the products page
#     When I click on sort button
#     Then I should see the sort modal

#   Scenario: I can see the sort default is by name ascending
#     Given I am on the products page
#     Then I should see the products sorted by "name" with "asc"

#   Scenario: I can sort the product with name descending
#     Given I am on the products page
#     When I click on sort button
#     Then I should see the sort modal
#     When I choose sort by "name" with "desc"
#     Then I should see the products sorted by "name" with "desc"

#   Scenario: I can sort the product with price descending
#     Given I am on the products page
#     When I click on sort button
#     Then I should see the sort modal
#     When I choose sort by "price" with "desc"
#     Then I should see the products sorted by "price" with "desc"

#   Scenario: I can sort the product with price ascending
#     Given I am on the products page
#     When I click on sort button
#     Then I should see the sort modal
#     When I choose sort by "price" with "asc"
#     Then I should see the products sorted by "price" with "asc"

#   Scenario: I can see product ratings
#     Given I am on the products page
#     Then I should see all products have ratings

#   Scenario: Product images load correctly
#     Given I am on the products page
#     Then I should see all product images displayed