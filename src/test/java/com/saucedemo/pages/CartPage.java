package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

/**
 * Purpose: Cart Page Object Class
 * This class represents the shopping cart page on SauceDemo.
 * Contains elements and actions for managing cart items and proceeding to
 * checkout.
 * 
 * Page URL: https://www.saucedemo.com/cart.html
 * 
 * Elements:
 * - Cart items
 * - Remove buttons
 * - Continue shopping button
 * - Checkout button
 * - Cart quantity
 * 
 * Actions:
 * - Get cart items
 * - Remove items from cart
 * - Continue shopping
 * - Proceed to checkout
 */
public class CartPage extends BasePage {

    // ============================================================================
    // Page Elements using @FindBy annotations
    // ============================================================================

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(className = "cart_item")
    private List<WebElement> cartItems;

    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(className = "cart_quantity")
    private List<WebElement> cartQuantities;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> itemNames;

    @FindBy(className = "inventory_item_price")
    private List<WebElement> itemPrices;

    // ============================================================================
    // Constructor
    // ============================================================================

    /**
     * Constructor to initialize CartPage
     * 
     * @param driver WebDriver instance
     */
    public CartPage(WebDriver driver) {
        super(driver);
    }

    // ============================================================================
    // Page Actions
    // ============================================================================

    /**
     * Get page title
     * 
     * @return Page title text
     */
    public String getCartPageTitle() {
        return getText(pageTitle);
    }

    /**
     * Check if cart page is displayed
     * 
     * @return true if on cart page, false otherwise
     */
    public boolean isCartPageDisplayed() {
        return getCurrentUrl().contains("cart.html") && isDisplayed(pageTitle);
    }

    /**
     * Get list of cart item names
     * 
     * @return List of product names in cart
     */
    public List<String> getCartItemNames() {
        List<String> names = new ArrayList<>();
        for (WebElement item : itemNames) {
            names.add(getText(item));
        }
        System.out.println("Cart contains " + names.size() + " items");
        return names;
    }

    /**
     * Get number of items in cart
     * 
     * @return Number of cart items
     */
    public int getCartItemCount() {
        return cartItems.size();
    }

    /**
     * Check if cart is empty
     * 
     * @return true if cart is empty, false otherwise
     */
    public boolean isCartEmpty() {
        return cartItems.isEmpty();
    }

    /**
     * Remove item from cart by product name
     * 
     * @param productName Name of product to remove
     */
    public void removeItem(String productName) {
        String buttonId = getRemoveButtonId(productName);
        WebElement removeButton = driver.findElement(By.id(buttonId));
        click(removeButton);
        System.out.println("Removed item from cart: " + productName);
    }

    /**
     * Click continue shopping button
     */
    public void clickContinueShopping() {
        click(continueShoppingButton);
        System.out.println("Clicked continue shopping");
    }

    /**
     * Click checkout button to proceed to checkout
     */
    public void clickCheckout() {
        click(checkoutButton);
        System.out.println("Clicked checkout button");
    }

    /**
     * Check if product is in cart
     * 
     * @param productName Product name to check
     * @return true if product is in cart, false otherwise
     */
    public boolean isProductInCart(String productName) {
        return getCartItemNames().contains(productName);
    }

    /**
     * Get price of item in cart
     * 
     * @param productName Product name
     * @return Price as string
     */
    public String getItemPrice(String productName) {
        List<String> names = getCartItemNames();
        int index = names.indexOf(productName);

        if (index >= 0 && index < itemPrices.size()) {
            return getText(itemPrices.get(index));
        }

        return "";
    }

    /**
     * Remove all items from cart
     */
    public void removeAllItems() {
        List<String> itemNames = getCartItemNames();
        for (String itemName : itemNames) {
            removeItem(itemName);
        }
        System.out.println("Removed all items from cart");
    }

    /**
     * Check if checkout button is displayed
     * 
     * @return true if button is displayed, false otherwise
     */
    public boolean isCheckoutButtonDisplayed() {
        return isDisplayed(checkoutButton);
    }

    /**
     * Check if continue shopping button is displayed
     * 
     * @return true if button is displayed, false otherwise
     */
    public boolean isContinueShoppingButtonDisplayed() {
        return isDisplayed(continueShoppingButton);
    }

    // ============================================================================
    // Helper Methods
    // ============================================================================

    /**
     * Get Remove button ID for a product
     * Converts product name to button ID format
     * 
     * @param productName Product name
     * @return Button ID
     */
    private String getRemoveButtonId(String productName) {
        String formattedName = productName.toLowerCase()
                .replace(" ", "-")
                .replace("(", "")
                .replace(")", "");
        return "remove-" + formattedName;
    }
}
