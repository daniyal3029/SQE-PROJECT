package com.saucedemo.steps;

import io.cucumber.java.en.*;
import org.testng.Assert;

import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.CheckoutPage;
import com.saucedemo.pages.ProductsPage;
import com.saucedemo.utils.DriverManager;

/**
 * Purpose: Checkout Step Definitions
 * This class contains step definitions for checkout-related Cucumber scenarios.
 * Implements steps for checkout process, form validation, and order completion.
 * Includes Excel integration for checkout data.
 * 
 * Features:
 * - Checkout information form filling
 * - Form validation
 * - Order completion
 * - Excel data integration
 */
public class CheckoutSteps {

    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private ProductsPage productsPage;

    /**
     * Navigate to cart page
     */
    @When("I navigate to cart page")
    public void i_navigate_to_cart_page() {
        productsPage = new ProductsPage(DriverManager.getDriver());
        productsPage.clickShoppingCart();
        cartPage = new CartPage(DriverManager.getDriver());
    }

    /**
     * Click checkout button
     */
    @When("I click checkout button")
    public void i_click_checkout_button() {
        cartPage = new CartPage(DriverManager.getDriver());
        cartPage.clickCheckout();
        checkoutPage = new CheckoutPage(DriverManager.getDriver());
    }

    /**
     * Enter first name
     */
    @When("I enter first name {string}")
    public void i_enter_first_name(String firstName) {
        checkoutPage = new CheckoutPage(DriverManager.getDriver());
        checkoutPage.enterFirstName(firstName);
    }

    /**
     * Enter last name
     */
    @When("I enter last name {string}")
    public void i_enter_last_name(String lastName) {
        checkoutPage.enterLastName(lastName);
    }

    /**
     * Enter zip code
     */
    @When("I enter zip code {string}")
    public void i_enter_zip_code(String zipCode) {
        checkoutPage.enterZipCode(zipCode);
    }

    /**
     * Fill checkout information
     */
    @When("I fill checkout information with {string} {string} {string}")
    public void i_fill_checkout_information_with(String firstName, String lastName, String zipCode) {
        checkoutPage = new CheckoutPage(DriverManager.getDriver());
        checkoutPage.fillCheckoutInformation(firstName, lastName, zipCode);
    }

    /**
     * Click continue button
     */
    @When("I click continue button")
    public void i_click_continue_button() {
        checkoutPage.clickContinue();
    }

    /**
     * Verify on checkout overview page
     */
    @Then("I should be on checkout overview page")
    public void i_should_be_on_checkout_overview_page() {
        Assert.assertTrue(checkoutPage.isCheckoutStepTwoDisplayed(), "Not on checkout overview page");
    }

    /**
     * Click finish button
     */
    @When("I click finish button")
    public void i_click_finish_button() {
        checkoutPage.clickFinish();
    }

    /**
     * Verify order confirmation
     */
    @Then("I should see order confirmation")
    public void i_should_see_order_confirmation() {
        Assert.assertTrue(checkoutPage.isOrderComplete(), "Order is not complete");
    }

    /**
     * Verify confirmation message contains text
     */
    @Then("the confirmation message should contain {string}")
    public void the_confirmation_message_should_contain(String expectedText) {
        String confirmationHeader = checkoutPage.getConfirmationHeader();
        String confirmationMessage = checkoutPage.getConfirmationMessage();
        String fullMessage = confirmationHeader + " " + confirmationMessage;
        Assert.assertTrue(fullMessage.contains(expectedText),
                "Confirmation message does not contain expected text");
    }

    /**
     * Verify checkout error message is displayed
     */
    @Then("I should see checkout error message")
    public void i_should_see_checkout_error_message() {
        Assert.assertTrue(checkoutPage.isErrorMessageDisplayed(), "Checkout error message is not displayed");
    }

    /**
     * Verify subtotal is displayed
     */
    @Then("I should see the subtotal")
    public void i_should_see_the_subtotal() {
        String subtotal = checkoutPage.getSubtotal();
        Assert.assertFalse(subtotal.isEmpty(), "Subtotal is not displayed");
    }

    /**
     * Verify tax amount is displayed
     */
    @Then("I should see the tax amount")
    public void i_should_see_the_tax_amount() {
        String tax = checkoutPage.getTax();
        Assert.assertFalse(tax.isEmpty(), "Tax is not displayed");
    }

    /**
     * Verify total amount is displayed
     */
    @Then("I should see the total amount")
    public void i_should_see_the_total_amount() {
        String total = checkoutPage.getTotal();
        Assert.assertFalse(total.isEmpty(), "Total is not displayed");
    }

    /**
     * Verify item count in checkout
     */
    @Then("I should see {string} item in checkout")
    public void i_should_see_item_in_checkout(String expectedCount) {
        int actualCount = checkoutPage.getCheckoutItemCount();
        Assert.assertEquals(actualCount, Integer.parseInt(expectedCount), "Checkout item count mismatch");
    }

    /**
     * Click cancel button on checkout page
     */
    @When("I click cancel button on checkout page")
    public void i_click_cancel_button_on_checkout_page() {
        checkoutPage.clickCancel();
        cartPage = new CartPage(DriverManager.getDriver());
    }

    /**
     * Verify redirected to cart page
     */
    @Then("I should be redirected to cart page")
    public void i_should_be_redirected_to_cart_page() {
        cartPage = new CartPage(DriverManager.getDriver());
        Assert.assertTrue(cartPage.isCartPageDisplayed(), "Not on cart page");
    }

    /**
     * Verify item count in cart
     */
    @Then("I should see {string} item in cart")
    public void i_should_see_item_in_cart(String expectedCount) {
        int actualCount = cartPage.getCartItemCount();
        Assert.assertEquals(actualCount, Integer.parseInt(expectedCount), "Cart item count mismatch");
    }

    /**
     * Given user is logged in
     */
    @Given("I am logged in as {string}")
    public void i_am_logged_in_as(String username) {
        // This step is handled by the login steps
        productsPage = new ProductsPage(DriverManager.getDriver());
        Assert.assertTrue(productsPage.isProductsPageDisplayed(), "User is not logged in");
    }

    /**
     * Given user has items in cart
     */
    @Given("I have items in my cart")
    public void i_have_items_in_my_cart() {
        productsPage = new ProductsPage(DriverManager.getDriver());
        productsPage.addProductToCart("Sauce Labs Backpack");
    }

    /**
     * Given user is on checkout information page
     */
    @Given("I am on the checkout information page")
    public void i_am_on_the_checkout_information_page() {
        productsPage = new ProductsPage(DriverManager.getDriver());
        productsPage.clickShoppingCart();
        cartPage = new CartPage(DriverManager.getDriver());
        cartPage.clickCheckout();
        checkoutPage = new CheckoutPage(DriverManager.getDriver());
    }

    /**
     * Try to continue without filling information
     */
    @When("I try to continue without filling any information")
    public void i_try_to_continue_without_filling_any_information() {
        checkoutPage.clickContinue();
    }

    /**
     * Verify error for missing first name
     */
    @Then("I should see error for missing first name")
    public void i_should_see_error_for_missing_first_name() {
        String errorMessage = checkoutPage.getErrorMessage();
        Assert.assertTrue(errorMessage.toLowerCase().contains("first name"),
                "Error message does not indicate first name is required");
    }

    /**
     * Try to continue without last name and zip code
     */
    @When("I try to continue without last name and zip code")
    public void i_try_to_continue_without_last_name_and_zip_code() {
        checkoutPage.clickContinue();
    }

    /**
     * Verify error for missing last name
     */
    @Then("I should see error for missing last name")
    public void i_should_see_error_for_missing_last_name() {
        String errorMessage = checkoutPage.getErrorMessage();
        Assert.assertTrue(errorMessage.toLowerCase().contains("last name"),
                "Error message does not indicate last name is required");
    }

    /**
     * Try to continue without zip code
     */
    @When("I try to continue without zip code")
    public void i_try_to_continue_without_zip_code() {
        checkoutPage.clickContinue();
    }

    /**
     * Verify error for missing zip code
     */
    @Then("I should see error for missing zip code")
    public void i_should_see_error_for_missing_zip_code() {
        String errorMessage = checkoutPage.getErrorMessage();
        Assert.assertTrue(errorMessage.toLowerCase().contains("postal code") ||
                errorMessage.toLowerCase().contains("zip"),
                "Error message does not indicate zip code is required");
    }

    /**
     * Given user is logged in with items in cart
     */
    @Given("I am logged in with items in cart")
    public void i_am_logged_in_with_items_in_cart() {
        productsPage = new ProductsPage(DriverManager.getDriver());
        productsPage.addProductToCart("Sauce Labs Backpack");
    }

    /**
     * Proceed to checkout
     */
    @When("I proceed to checkout")
    public void i_proceed_to_checkout() {
        productsPage = new ProductsPage(DriverManager.getDriver());
        productsPage.clickShoppingCart();
        cartPage = new CartPage(DriverManager.getDriver());
        cartPage.clickCheckout();
        checkoutPage = new CheckoutPage(DriverManager.getDriver());
    }

    /**
     * Fill valid checkout information
     */
    @When("I fill valid checkout information")
    public void i_fill_valid_checkout_information() {
        checkoutPage.fillCheckoutInformation("John", "Doe", "12345");
    }

    /**
     * Continue to overview
     */
    @When("I continue to overview")
    public void i_continue_to_overview() {
        checkoutPage.clickContinue();
    }
}
