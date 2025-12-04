# Purpose: Form Validation Feature File
# This file contains BDD scenarios for testing form validation and error handling

@Form @Regression
Feature: Form Validation
  As a user
  I want proper form validation
  So that I can receive helpful error messages when I make mistakes

  Background:
    Given I am on the SauceDemo login page

  @Negative
  Scenario: Verify login form validation with empty username
    When I leave username field empty
    And I enter password "secret_sauce"
    And I submit the login form
    Then I should see a validation error
    And the error should indicate username is required

  @Negative
  Scenario: Verify login form validation with empty password
    When I enter username "standard_user"
    And I leave password field empty
    And I submit the login form
    Then I should see a validation error
    And the error should indicate password is required

  @Negative
  Scenario: Verify checkout form validation for all required fields
    Given I am logged in as "standard_user"
    And I have items in my cart
    And I am on the checkout information page
    When I try to continue without filling any information
    Then I should see error for missing first name
    When I enter first name "John"
    And I try to continue without last name and zip code
    Then I should see error for missing last name
    When I enter last name "Doe"
    And I try to continue without zip code
    Then I should see error for missing zip code

  @Positive
  Scenario: Verify error message can be dismissed
    When I enter invalid credentials
    And I click the login button
    Then I should see an error message
    When I clear the username field
    And I clear the password field
    Then the error message should still be displayed
