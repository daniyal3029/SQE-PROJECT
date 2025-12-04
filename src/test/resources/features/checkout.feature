# Purpose: Checkout Feature File
# This file contains BDD scenarios for testing the checkout process
# Uses Scenario Outline for data-driven testing with Excel data

@Checkout @Regression
Feature: Checkout Process
  As a user with items in cart
  I want to complete the checkout process
  So that I can place my order

  Background:
    Given I am on the SauceDemo login page
    When I login with username "standard_user" and password "secret_sauce"
    And I add product "Sauce Labs Backpack" to cart
    And I navigate to cart page
    And I click checkout button

  @Positive @DataDriven
  Scenario Outline: Complete checkout with valid information from Excel
    When I enter first name "<firstName>"
    And I enter last name "<lastName>"
    And I enter zip code "<zipCode>"
    And I click continue button
    Then I should be on checkout overview page
    When I click finish button
    Then I should see order confirmation
    And the confirmation message should contain "Thank you for your order"

    # Data will be read from Excel file: testdata.xlsx, Sheet: CheckoutData
    Examples:
      | firstName | lastName | zipCode |
      | John      | Doe      | 12345   |
      | Jane      | Smith    | 67890   |

  @Negative
  Scenario: Checkout with missing first name
    When I enter first name ""
    And I enter last name "Doe"
    And I enter zip code "12345"
    And I click continue button
    Then I should see checkout error message
    And the error message should contain "First Name is required"

  @Negative
  Scenario: Checkout with missing last name
    When I enter first name "John"
    And I enter last name ""
    And I enter zip code "12345"
    And I click continue button
    Then I should see checkout error message
    And the error message should contain "Last Name is required"

  @Negative
  Scenario: Checkout with missing zip code
    When I enter first name "John"
    And I enter last name "Doe"
    And I enter zip code ""
    And I click continue button
    Then I should see checkout error message
    And the error message should contain "Postal Code is required"

  @Negative
  Scenario: Checkout with all empty fields
    When I enter first name ""
    And I enter last name ""
    And I enter zip code ""
    And I click continue button
    Then I should see checkout error message
    And the error message should contain "First Name is required"

  @Positive
  Scenario: Verify checkout overview displays correct information
    When I fill checkout information with "John" "Doe" "12345"
    And I click continue button
    Then I should be on checkout overview page
    And I should see the subtotal
    And I should see the tax amount
    And I should see the total amount
    And I should see "1" item in checkout

  @Positive
  Scenario: Cancel checkout and return to cart
    When I click cancel button on checkout page
    Then I should be redirected to cart page
    And I should see "1" item in cart
