# Purpose: UI Validation Feature File
# This file contains BDD scenarios for testing UI elements, page titles, URLs, and visual elements

@UIValidation @Regression
Feature: UI Validation and Page Verification
  As a user
  I want to ensure all UI elements are displayed correctly
  So that I have a good user experience

  @Positive
  Scenario: Verify login page UI elements
    Given I am on the SauceDemo login page
    Then I should see the login logo
    And I should see the username field
    And I should see the password field
    And I should see the login button
    And the page title should be "Swag Labs"
    And the page URL should contain "saucedemo.com"

  @Positive
  Scenario: Verify products page UI elements after login
    Given I am on the SauceDemo login page
    When I login with valid credentials
    Then I should see the products page title "Products"
    And I should see the app logo
    And I should see the shopping cart icon
    And I should see the hamburger menu
    And I should see the product sort dropdown
    And the page URL should contain "inventory.html"

  @Positive
  Scenario: Verify cart page UI elements
    Given I am logged in as "standard_user"
    When I navigate to cart page
    Then the page title should contain "Your Cart"
    And I should see the continue shopping button
    And I should see the checkout button
    And the page URL should contain "cart.html"

  @Positive
  Scenario: Verify checkout page titles and URLs
    Given I am logged in with items in cart
    When I proceed to checkout
    Then the page title should contain "Checkout: Your Information"
    And the page URL should contain "checkout-step-one.html"
    When I fill valid checkout information
    And I continue to overview
    Then the page title should contain "Checkout: Overview"
    And the page URL should contain "checkout-step-two.html"

  @Positive
  Scenario: Verify product images are displayed
    Given I am logged in as "standard_user"
    Then all product images should be displayed
    And all product images should have valid src attributes

  @Positive
  Scenario: Verify product details page elements
    Given I am logged in as "standard_user"
    When I click on any product
    Then I should see the product image
    And I should see the product name
    And I should see the product description
    And I should see the product price
    And I should see the Add to cart button
    And I should see the Back to products button

  @Positive
  Scenario: Verify all products have required information
    Given I am logged in as "standard_user"
    Then each product should display a name
    And each product should display a price
    And each product should display an image
    And each product should have an Add to cart button

  @Positive
  Scenario: Verify navigation consistency
    Given I am logged in as "standard_user"
    When I navigate through different pages
    Then the shopping cart icon should be visible on all pages
    And the hamburger menu should be visible on all pages
    And the app logo should be visible on all pages
