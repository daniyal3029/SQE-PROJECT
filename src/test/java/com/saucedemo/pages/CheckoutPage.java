package com.saucedemo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Purpose: Checkout Page Object Class
 * This class represents the checkout process pages on SauceDemo.
 * Handles checkout information form, overview, and completion.
 * 
 * Page URLs:
 * - Step 1: https://www.saucedemo.com/checkout-step-one.html
 * - Step 2: https://www.saucedemo.com/checkout-step-two.html
 * - Complete: https://www.saucedemo.com/checkout-complete.html
 * 
 * Elements:
 * - First name, last name, zip code fields
 * - Continue and finish buttons
 * - Error messages
 * - Order confirmation
 * 
 * Actions:
 * - Fill checkout information
 * - Complete checkout process
 * - Get confirmation message
 */
public class CheckoutPage extends BasePage {

    // ============================================================================
    // Checkout Step One Elements (Information Form)
    // ============================================================================

    @FindBy(id = "first-name")
    private WebElement firstNameField;

    @FindBy(id = "last-name")
    private WebElement lastNameField;

    @FindBy(id = "postal-code")
    private WebElement zipCodeField;

    @FindBy(id = "continue")
    private WebElement continueButton;

    @FindBy(id = "cancel")
    private WebElement cancelButton;

    @FindBy(css = "h3[data-test='error']")
    private WebElement errorMessage;

    // ============================================================================
    // Checkout Step Two Elements (Overview)
    // ============================================================================

    @FindBy(id = "finish")
    private WebElement finishButton;

    @FindBy(className = "summary_subtotal_label")
    private WebElement subtotalLabel;

    @FindBy(className = "summary_tax_label")
    private WebElement taxLabel;

    @FindBy(className = "summary_total_label")
    private WebElement totalLabel;

    @FindBy(className = "cart_item")
    private java.util.List<WebElement> checkoutItems;

    // ============================================================================
    // Checkout Complete Elements
    // ============================================================================

    @FindBy(className = "complete-header")
    private WebElement confirmationHeader;

    @FindBy(className = "complete-text")
    private WebElement confirmationText;

    @FindBy(id = "back-to-products")
    private WebElement backToProductsButton;

    @FindBy(className = "title")
    private WebElement pageTitle;

    // ============================================================================
    // Constructor
    // ============================================================================

    /**
     * Constructor to initialize CheckoutPage
     * 
     * @param driver WebDriver instance
     */
    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    // ============================================================================
    // Checkout Step One Actions (Information Form)
    // ============================================================================

    /**
     * Enter first name
     * 
     * @param firstName First name to enter
     */
    public void enterFirstName(String firstName) {
        type(firstNameField, firstName);
        System.out.println("Entered first name: " + firstName);
    }

    /**
     * Enter last name
     * 
     * @param lastName Last name to enter
     */
    public void enterLastName(String lastName) {
        type(lastNameField, lastName);
        System.out.println("Entered last name: " + lastName);
    }

    /**
     * Enter zip/postal code
     * 
     * @param zipCode Zip code to enter
     */
    public void enterZipCode(String zipCode) {
        type(zipCodeField, zipCode);
        System.out.println("Entered zip code: " + zipCode);
    }

    /**
     * Fill complete checkout information form
     * 
     * @param firstName First name
     * @param lastName  Last name
     * @param zipCode   Zip code
     */
    public void fillCheckoutInformation(String firstName, String lastName, String zipCode) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterZipCode(zipCode);
        System.out.println("Filled checkout information");
    }

    /**
     * Click continue button to proceed to overview
     */
    public void clickContinue() {
        click(continueButton);
        System.out.println("Clicked continue button");
    }

    /**
     * Click cancel button
     */
    public void clickCancel() {
        click(cancelButton);
        System.out.println("Clicked cancel button");
    }

    /**
     * Get error message text
     * 
     * @return Error message or empty string
     */
    public String getErrorMessage() {
        try {
            return getText(errorMessage);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Check if error message is displayed
     * 
     * @return true if error is displayed, false otherwise
     */
    public boolean isErrorMessageDisplayed() {
        return isDisplayed(errorMessage);
    }

    /**
     * Check if on checkout step one page
     * 
     * @return true if on step one, false otherwise
     */
    public boolean isCheckoutStepOneDisplayed() {
        return getCurrentUrl().contains("checkout-step-one.html");
    }

    // ============================================================================
    // Checkout Step Two Actions (Overview)
    // ============================================================================

    /**
     * Click finish button to complete order
     */
    public void clickFinish() {
        click(finishButton);
        System.out.println("Clicked finish button");
    }

    /**
     * Get subtotal amount
     * 
     * @return Subtotal as string
     */
    public String getSubtotal() {
        return getText(subtotalLabel);
    }

    /**
     * Get tax amount
     * 
     * @return Tax as string
     */
    public String getTax() {
        return getText(taxLabel);
    }

    /**
     * Get total amount
     * 
     * @return Total as string
     */
    public String getTotal() {
        return getText(totalLabel);
    }

    /**
     * Get number of items in checkout
     * 
     * @return Number of items
     */
    public int getCheckoutItemCount() {
        return checkoutItems.size();
    }

    /**
     * Check if on checkout step two page
     * 
     * @return true if on step two, false otherwise
     */
    public boolean isCheckoutStepTwoDisplayed() {
        return getCurrentUrl().contains("checkout-step-two.html");
    }

    // ============================================================================
    // Checkout Complete Actions
    // ============================================================================

    /**
     * Get confirmation header text
     * 
     * @return Confirmation header text
     */
    public String getConfirmationHeader() {
        return getText(confirmationHeader);
    }

    /**
     * Get confirmation message text
     * 
     * @return Confirmation message text
     */
    public String getConfirmationMessage() {
        return getText(confirmationText);
    }

    /**
     * Check if order is complete
     * 
     * @return true if on complete page, false otherwise
     */
    public boolean isOrderComplete() {
        return getCurrentUrl().contains("checkout-complete.html") &&
                isDisplayed(confirmationHeader);
    }

    /**
     * Click back to products button
     */
    public void clickBackToProducts() {
        click(backToProductsButton);
        System.out.println("Clicked back to products");
    }

    /**
     * Get page title
     * 
     * @return Page title text
     */
    public String getCheckoutPageTitle() {
        return getText(pageTitle);
    }

    // ============================================================================
    // Combined Actions for Complete Checkout Flow
    // ============================================================================

    /**
     * Complete entire checkout process
     * 
     * @param firstName First name
     * @param lastName  Last name
     * @param zipCode   Zip code
     */
    public void completeCheckout(String firstName, String lastName, String zipCode) {
        fillCheckoutInformation(firstName, lastName, zipCode);
        clickContinue();

        // Wait for overview page to load
        waitHelper.waitForUrlToContain("checkout-step-two.html");

        clickFinish();

        // Wait for completion page
        waitHelper.waitForUrlToContain("checkout-complete.html");

        System.out.println("Completed checkout process");
    }
}
