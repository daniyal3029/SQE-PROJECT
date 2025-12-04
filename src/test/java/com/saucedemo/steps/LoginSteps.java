package com.saucedemo.steps;

import io.cucumber.java.en.*;
import org.testng.Assert;

import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import com.saucedemo.utils.DriverManager;
import com.saucedemo.config.ConfigReader;

/**
 * Purpose: Login Step Definitions
 * This class contains step definitions for login-related Cucumber scenarios.
 * Implements steps for login page interactions, validation, and data-driven
 * tests.
 * 
 * Features:
 * - Login with valid/invalid credentials
 * - Error message validation
 * - Excel data integration
 * - Logout functionality
 */
public class LoginSteps {

    private LoginPage loginPage;
    private ProductsPage productsPage;
    private ConfigReader config = ConfigReader.getInstance();

    /**
     * Navigate to SauceDemo login page
     */
    @Given("I am on the SauceDemo login page")
    public void i_am_on_the_saucedemo_login_page() {
        DriverManager.navigateToBaseUrl();
        loginPage = new LoginPage(DriverManager.getDriver());
        Assert.assertTrue(loginPage.isLoginLogoDisplayed(), "Login page is not displayed");
        System.out.println("Navigated to SauceDemo login page");
    }

    /**
     * Enter username and password
     */
    @When("I enter username {string} and password {string}")
    public void i_enter_username_and_password(String username, String password) {
        if (loginPage == null) {
            loginPage = new LoginPage(DriverManager.getDriver());
        }
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    /**
     * Enter username only
     */
    @When("I enter username {string}")
    public void i_enter_username(String username) {
        if (loginPage == null) {
            loginPage = new LoginPage(DriverManager.getDriver());
        }
        loginPage.enterUsername(username);
    }

    /**
     * Enter password only
     */
    @When("I enter password {string}")
    public void i_enter_password(String password) {
        if (loginPage == null) {
            loginPage = new LoginPage(DriverManager.getDriver());
        }
        loginPage.enterPassword(password);
    }

    /**
     * Click login button
     */
    @When("I click the login button")
    public void i_click_the_login_button() {
        loginPage.clickLoginButton();
    }

    /**
     * Perform complete login
     */
    @When("I login with username {string} and password {string}")
    public void i_login_with_username_and_password(String username, String password) {
        loginPage.login(username, password);
        productsPage = new ProductsPage(DriverManager.getDriver());
    }

    /**
     * Login with valid credentials (using default from config)
     */
    @When("I login with valid credentials")
    public void i_login_with_valid_credentials() {
        String username = config.getStandardUser();
        String password = config.getTestPassword();
        loginPage.login(username, password);
        productsPage = new ProductsPage(DriverManager.getDriver());
    }

    /**
     * Verify redirect to products page
     */
    @Then("I should be redirected to the products page")
    public void i_should_be_redirected_to_the_products_page() {
        Assert.assertTrue(loginPage.isLoginSuccessful(), "Login was not successful");
        productsPage = new ProductsPage(DriverManager.getDriver());
        Assert.assertTrue(productsPage.isProductsPageDisplayed(), "Products page is not displayed");
    }

    /**
     * Verify products page title
     */
    @Then("I should see the products page title {string}")
    public void i_should_see_the_products_page_title(String expectedTitle) {
        String actualTitle = productsPage.getProductsPageTitle();
        Assert.assertEquals(actualTitle, expectedTitle, "Products page title mismatch");
    }

    /**
     * Verify error message is displayed
     */
    @Then("I should see an error message")
    public void i_should_see_an_error_message() {
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message is not displayed");
    }

    /**
     * Verify error message contains specific text
     */
    @Then("the error message should contain {string}")
    public void the_error_message_should_contain(String expectedText) {
        String actualMessage = loginPage.getErrorMessage();
        Assert.assertTrue(actualMessage.contains(expectedText),
                "Error message does not contain expected text. Expected: " + expectedText + ", Actual: "
                        + actualMessage);
    }

    /**
     * Verify user remains on login page
     */
    @Then("I should remain on the login page")
    public void i_should_remain_on_the_login_page() {
        Assert.assertTrue(loginPage.isLoginLogoDisplayed(), "Not on login page");
        String currentUrl = loginPage.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("saucedemo.com") && !currentUrl.contains("inventory"),
                "User was redirected from login page");
    }

    /**
     * Click hamburger menu
     */
    @When("I click on the hamburger menu")
    public void i_click_on_the_hamburger_menu() {
        productsPage.openHamburgerMenu();
    }

    /**
     * Click logout link
     */
    @When("I click on the logout link")
    public void i_click_on_the_logout_link() {
        productsPage.logout();
        loginPage = new LoginPage(DriverManager.getDriver());
    }

    /**
     * Verify redirect to login page
     */
    @Then("I should be redirected to the login page")
    public void i_should_be_redirected_to_the_login_page() {
        String currentUrl = loginPage.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("saucedemo.com") && !currentUrl.contains("inventory"),
                "Not redirected to login page");
    }

    /**
     * Verify login logo is displayed
     */
    @Then("I should see the login logo")
    public void i_should_see_the_login_logo() {
        Assert.assertTrue(loginPage.isLoginLogoDisplayed(), "Login logo is not displayed");
    }

    /**
     * Leave username field empty
     */
    @When("I leave username field empty")
    public void i_leave_username_field_empty() {
        loginPage.clearUsername();
    }

    /**
     * Leave password field empty
     */
    @When("I leave password field empty")
    public void i_leave_password_field_empty() {
        loginPage.clearPassword();
    }

    /**
     * Submit login form
     */
    @When("I submit the login form")
    public void i_submit_the_login_form() {
        loginPage.clickLoginButton();
    }

    /**
     * Verify validation error is displayed
     */
    @Then("I should see a validation error")
    public void i_should_see_a_validation_error() {
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Validation error is not displayed");
    }

    /**
     * Verify error indicates username is required
     */
    @Then("the error should indicate username is required")
    public void the_error_should_indicate_username_is_required() {
        String errorMessage = loginPage.getErrorMessage();
        Assert.assertTrue(errorMessage.toLowerCase().contains("username"),
                "Error message does not indicate username is required");
    }

    /**
     * Verify error indicates password is required
     */
    @Then("the error should indicate password is required")
    public void the_error_should_indicate_password_is_required() {
        String errorMessage = loginPage.getErrorMessage();
        Assert.assertTrue(errorMessage.toLowerCase().contains("password"),
                "Error message does not indicate password is required");
    }

    /**
     * Enter invalid credentials
     */
    @When("I enter invalid credentials")
    public void i_enter_invalid_credentials() {
        loginPage.enterUsername("invalid_user");
        loginPage.enterPassword("wrong_password");
    }

    /**
     * Clear username field
     */
    @When("I clear the username field")
    public void i_clear_the_username_field() {
        loginPage.clearUsername();
    }

    /**
     * Clear password field
     */
    @When("I clear the password field")
    public void i_clear_the_password_field() {
        loginPage.clearPassword();
    }

    /**
     * Verify error message is still displayed
     */
    @Then("the error message should still be displayed")
    public void the_error_message_should_still_be_displayed() {
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message disappeared");
    }
}
