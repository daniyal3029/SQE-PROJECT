# Purpose: Product Feature File
# This file contains BDD scenarios for testing product browsing and cart operations
# Uses data from Database for product information

@Product @Regression
Feature: Product Browsing and Cart Operations
  As a logged-in user
  I want to browse products and manage my shopping cart
  So that I can select items for purchase

  Background:
    Given I am on the SauceDemo login page
    When I login with username "standard_user" and password "secret_sauce"
    Then I should be redirected to the products page

  @Positive
  Scenario: Sort products by price low to high
    When I sort products by "Price (low to high)"
    Then the products should be sorted by price in ascending order

  @Positive
  Scenario: Sort products by name A to Z
    When I sort products by "Name (A to Z)"
    Then the products should be sorted alphabetically

  @Positive @Database
  Scenario: View product details from database
    When I click on product "Sauce Labs Backpack" from database
    Then I should be on the product details page
    And I should see the product name "Sauce Labs Backpack"
    And I should see the product price
    And I should see the product image

  @Positive
  Scenario: Add single product to cart
    When I add product "Sauce Labs Backpack" to cart
    Then the cart badge should show "1" item
    And the product should have a Remove button

  @Positive @Database
  Scenario: Add multiple products to cart from database
    When I add the following products to cart from database:
      | Sauce Labs Backpack   |
      | Sauce Labs Bike Light |
      | Sauce Labs Bolt T-Shirt |
    Then the cart badge should show "3" items

  @Positive
  Scenario: Remove product from cart on products page
    Given I have added "Sauce Labs Backpack" to cart
    When I remove product "Sauce Labs Backpack" from cart
    Then the cart badge should not be displayed
    And the product should have an Add to cart button

  @Positive
  Scenario: Add product from product details page
    When I click on product "Sauce Labs Fleece Jacket"
    And I am on the product details page
    And I click Add to Cart button on details page
    Then the cart badge should show "1" item
    And I should see the Remove button on details page

  @Positive
  Scenario: Navigate back to products from details page
    When I click on product "Sauce Labs Onesie"
    And I am on the product details page
    And I click Back to Products button
    Then I should be redirected to the products page
