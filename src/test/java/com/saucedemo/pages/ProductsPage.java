package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * Purpose: Products Page Object Class
 * This class represents the SauceDemo products/inventory page.
 * Contains all elements and actions for product listing, filtering, and cart
 * operations.
 * 
 * Page URL: https://www.saucedemo.com/inventory.html
 * 
 * Elements:
 * - Product items
 * - Add to cart buttons
 * - Remove from cart buttons
 * - Shopping cart badge
 * - Product sort dropdown
 * - Hamburger menu
 * 
 * Actions:
 * - Add product to cart
 * - Remove product from cart
 * - Sort products
 * - Navigate to cart
 * - Get product details
 */
public class ProductsPage extends BasePage {

    // ============================================================================
    // Page Elements using @FindBy annotations
    // ============================================================================

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(className = "shopping_cart_link")
    private WebElement shoppingCartLink;

    @FindBy(className = "shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(className = "product_sort_container")
    private WebElement sortDropdown;

    @FindBy(className = "inventory_item")
    private List<WebElement> inventoryItems;

    @FindBy(id = "react-burger-menu-btn")
    private WebElement hamburgerMenu;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    @FindBy(className = "app_logo")
    private WebElement appLogo;

    // ============================================================================
    // Constructor
    // ============================================================================

    /**
     * Constructor to initialize ProductsPage
     * 
     * @param driver WebDriver instance
     */
    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    // ============================================================================
    // Page Actions
    // ============================================================================

    /**
     * Get page title text
     * 
     * @return Page title
     */
    public String getProductsPageTitle() {
        return getText(pageTitle);
    }

    /**
     * Check if products page is displayed
     * 
     * @return true if on products page, false otherwise
     */
    public boolean isProductsPageDisplayed() {
        return isDisplayed(pageTitle) && getCurrentUrl().contains("inventory.html");
    }

    /**
     * Add product to cart by product name
     * 
     * @param productName Name of the product to add
     */
    public void addProductToCart(String productName) {
        String buttonId = getAddToCartButtonId(productName);
        WebElement addButton = driver.findElement(By.id(buttonId));
        click(addButton);
        System.out.println("Added product to cart: " + productName);
    }

    /**
     * Remove product from cart by product name
     * 
     * @param productName Name of the product to remove
     */
    public void removeProductFromCart(String productName) {
        String buttonId = getRemoveButtonId(productName);
        WebElement removeButton = driver.findElement(By.id(buttonId));
        click(removeButton);
        System.out.println("Removed product from cart: " + productName);
    }

    /**
     * Click on product to view details
     * 
     * @param productName Name of the product to click
     */
    public void clickProduct(String productName) {
        WebElement productLink = driver.findElement(
                By.xpath("//div[@class='inventory_item_name ' and text()='" + productName + "']"));
        click(productLink);
        System.out.println("Clicked on product: " + productName);
    }

    /**
     * Get shopping cart badge count
     * 
     * @return Number of items in cart, 0 if badge not displayed
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
     * Check if cart badge is displayed
     * 
     * @return true if badge is displayed, false otherwise
     */
    public boolean isCartBadgeDisplayed() {
        return isDisplayed(cartBadge);
    }

    /**
     * Click shopping cart to navigate to cart page
     */
    public void clickShoppingCart() {
        click(shoppingCartLink);
        System.out.println("Clicked shopping cart");
    }

    /**
     * Sort products by option
     * 
     * @param sortOption Sort option (Name (A to Z), Name (Z to A), Price (low to
     *                   high), Price (high to low))
     */
    public void sortProducts(String sortOption) {
        Select select = new Select(sortDropdown);
        select.selectByVisibleText(sortOption);
        System.out.println("Sorted products by: " + sortOption);
    }

    /**
     * Get current sort option
     * 
     * @return Current sort option text
     */
    public String getCurrentSortOption() {
        Select select = new Select(sortDropdown);
        return select.getFirstSelectedOption().getText();
    }

    /**
     * Get number of products displayed
     * 
     * @return Number of products
     */
    public int getProductCount() {
        return inventoryItems.size();
    }

    /**
     * Get product price by product name
     * 
     * @param productName Name of the product
     * @return Product price as string
     */
    public String getProductPrice(String productName) {
        WebElement priceElement = driver.findElement(
                By.xpath("//div[text()='" + productName
                        + "']/ancestor::div[@class='inventory_item_description']//div[@class='inventory_item_price']"));
        return getText(priceElement);
    }

    /**
     * Check if product is displayed
     * 
     * @param productName Name of the product
     * @return true if product is displayed, false otherwise
     */
    public boolean isProductDisplayed(String productName) {
        try {
            WebElement product = driver.findElement(
                    By.xpath("//div[@class='inventory_item_name ' and text()='" + productName + "']"));
            return isDisplayed(product);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Open hamburger menu
     */
    public void openHamburgerMenu() {
        click(hamburgerMenu);
        System.out.println("Opened hamburger menu");
    }

    /**
     * Logout from application
     */
    public void logout() {
        openHamburgerMenu();
        waitHelper.hardWait(500); // Small wait for menu animation
        click(logoutLink);
        System.out.println("Logged out");
    }

    /**
     * Check if app logo is displayed
     * 
     * @return true if logo is displayed, false otherwise
     */
    public boolean isAppLogoDisplayed() {
        return isDisplayed(appLogo);
    }

    // ============================================================================
    // Helper Methods
    // ============================================================================

    /**
     * Get Add to Cart button ID for a product
     * Converts product name to button ID format
     * 
     * @param productName Product name
     * @return Button ID
     */
    private String getAddToCartButtonId(String productName) {
        String formattedName = productName.toLowerCase()
                .replace(" ", "-")
                .replace("(", "")
                .replace(")", "");
        return "add-to-cart-" + formattedName;
    }

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
