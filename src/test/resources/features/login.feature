# Purpose: Login Feature File
# This file contains BDD scenarios for testing login functionality on SauceDemo
# Uses Gherkin syntax with Scenario and Scenario Outline
# Data-driven tests using Excel data source

@Login @Regression
Feature: User Login Functionality
  As a user of SauceDemo
  I want to be able to login with my credentials
  So that I can access the product catalog

  Background:
    Given I am on the SauceDemo login page

  @Positive @DataDriven
  Scenario Outline: Login with valid credentials from Excel
    When I enter username "<username>" and password "<password>"
    And I click the login button
    Then I should be redirected to the products page
    And I should see the products page title "Products"

    # Data will be read from Excel file: testdata.xlsx, Sheet: LoginData
    Examples:
      | username                | password     |
      | standard_user           | secret_sauce |
      | performance_glitch_user | secret_sauce |

  @Negative
  Scenario: Login with invalid credentials
    When I enter username "invalid_user" and password "wrong_password"
    And I click the login button
    Then I should see an error message
    And I should remain on the login page
    And the error message should contain "Username and password do not match"

  @Negative
  Scenario: Login with empty username field
    When I enter username "" and password "secret_sauce"
    And I click the login button
    Then I should see an error message
    And the error message should contain "Username is required"

  @Negative
  Scenario: Login with empty password field
    When I enter username "standard_user" and password ""
    And I click the login button
    Then I should see an error message
    And the error message should contain "Password is required"

  @Negative
  Scenario: Login with empty fields
    When I enter username "" and password ""
    And I click the login button
    Then I should see an error message
    And the error message should contain "Username is required"

  @Positive
  Scenario: Successful logout after login
    When I login with username "standard_user" and password "secret_sauce"
    Then I should be redirected to the products page
    When I click on the hamburger menu
    And I click on the logout link
    Then I should be redirected to the login page
    And I should see the login logo

  @Negative
  Scenario: Login with locked out user
    When I enter username "locked_out_user" and password "secret_sauce"
    And I click the login button
    Then I should see an error message
    And the error message should contain "Sorry, this user has been locked out"
