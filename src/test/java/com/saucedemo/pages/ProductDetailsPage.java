package com.saucedemo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Purpose: Product Details Page Object Class
 * This class represents the individual product details page on SauceDemo.
 * Contains elements and actions for viewing product details and adding to cart.
 * 
 * Page URL: https://www.saucedemo.com/inventory-item.html?id=X
 * 
 * Elements:
 * - Product name
 * - Product description
 * - Product price
 * - Product image
 * - Add to cart button
 * - Back to products button
 * 
 * Actions:
 * - Get product details
 * - Add to cart
 * - Navigate back to products
 */
public class ProductDetailsPage extends BasePage {

    // ============================================================================
    // Page Elements using @FindBy annotations
    // ============================================================================

    @FindBy(className = "inventory_details_name")
    private WebElement productName;

    @FindBy(className = "inventory_details_desc")
    private WebElement productDescription;

    @FindBy(className = "inventory_details_price")
    private WebElement productPrice;

    @FindBy(className = "inventory_details_img")
    private WebElement productImage;

    @FindBy(css = "button[id^='add-to-cart']")
    private WebElement addToCartButton;

    @FindBy(css = "button[id^='remove']")
    private WebElement removeButton;

    @FindBy(id = "back-to-products")
    private WebElement backToProductsButton;

    @FindBy(className = "shopping_cart_link")
    private WebElement shoppingCartLink;

    @FindBy(className = "shopping_cart_badge")
    private WebElement cartBadge;

    // ============================================================================
    // Constructor
    // ============================================================================

    /**
     * Constructor to initialize ProductDetailsPage
     * 
     * @param driver WebDriver instance
     */
    public ProductDetailsPage(WebDriver driver) {
        super(driver);
    }

    // ============================================================================
    // Page Actions
    // ============================================================================

    /**
     * Get product name
     * 
     * @return Product name text
     */
    public String getProductName() {
        return getText(productName);
    }

    /**
     * Get product description
     * 
     * @return Product description text
     */
    public String getProductDescription() {
        return getText(productDescription);
    }

    /**
     * Get product price
     * 
     * @return Product price text
     */
    public String getProductPrice() {
        return getText(productPrice);
    }

    /**
     * Check if product image is displayed
     * 
     * @return true if image is displayed, false otherwise
     */
    public boolean isProductImageDisplayed() {
        return isDisplayed(productImage);
    }

    /**
     * Add product to cart
     */
    public void addToCart() {
        click(addToCartButton);
        System.out.println("Added product to cart from details page");
    }

    /**
     * Remove product from cart
     */
    public void removeFromCart() {
        click(removeButton);
        System.out.println("Removed product from cart on details page");
    }

    /**
     * Check if Add to Cart button is displayed
     * 
     * @return true if button is displayed, false otherwise
     */
    public boolean isAddToCartButtonDisplayed() {
        return isDisplayed(addToCartButton);
    }

    /**
     * Check if Remove button is displayed
     * 
     * @return true if button is displayed, false otherwise
     */
    public boolean isRemoveButtonDisplayed() {
        return isDisplayed(removeButton);
    }

    /**
     * Click back to products button
     */
    public void clickBackToProducts() {
        click(backToProductsButton);
        System.out.println("Clicked back to products");
    }

    /**
     * Navigate to shopping cart
     */
    public void clickShoppingCart() {
        click(shoppingCartLink);
        System.out.println("Clicked shopping cart from details page");
    }

    /**
     * Get cart badge count
     * 
     * @return Number of items in cart
     */
    public int getCartBadgeCount() {
        try {
            String badgeText = getText(cartBadge);
            return Integer.parseInt(badgeText);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Check if on product details page
     * 
     * @return true if on details page, false otherwise
     */
    public boolean isProductDetailsPageDisplayed() {
        return getCurrentUrl().contains("inventory-item.html") && isDisplayed(productName);
    }
}
