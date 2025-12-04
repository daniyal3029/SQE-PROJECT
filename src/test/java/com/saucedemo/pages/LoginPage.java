package com.saucedemo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Purpose: Login Page Object Class
 * This class represents the SauceDemo login page and contains all elements and
 * actions
 * that can be performed on the login page.
 * 
 * Page URL: https://www.saucedemo.com/
 * 
 * Elements:
 * - Username input field
 * - Password input field
 * - Login button
 * - Error message container
 * 
 * Actions:
 * - Enter username
 * - Enter password
 * - Click login button
 * - Get error message
 * - Perform complete login
 */
public class LoginPage extends BasePage {

    // ============================================================================
    // Page Elements using @FindBy annotations
    // ============================================================================

    @FindBy(id = "user-name")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "h3[data-test='error']")
    private WebElement errorMessage;

    @FindBy(className = "login_logo")
    private WebElement loginLogo;

    // ============================================================================
    // Constructor
    // ============================================================================

    /**
     * Constructor to initialize LoginPage
     * 
     * @param driver WebDriver instance
     */
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // ============================================================================
    // Page Actions
    // ============================================================================

    /**
     * Enter username in the username field
     * 
     * @param username Username to enter
     */
    public void enterUsername(String username) {
        type(usernameField, username);
        System.out.println("Entered username: " + username);
    }

    /**
     * Enter password in the password field
     * 
     * @param password Password to enter
     */
    public void enterPassword(String password) {
        type(passwordField, password);
        System.out.println("Entered password: " + password);
    }

    /**
     * Click the login button
     */
    public void clickLoginButton() {
        click(loginButton);
        System.out.println("Clicked login button");
    }

    /**
     * Get error message text
     * 
     * @return Error message text or empty string if no error
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
     * @return true if error message is displayed, false otherwise
     */
    public boolean isErrorMessageDisplayed() {
        return isDisplayed(errorMessage);
    }

    /**
     * Check if login logo is displayed
     * 
     * @return true if logo is displayed, false otherwise
     */
    public boolean isLoginLogoDisplayed() {
        return isDisplayed(loginLogo);
    }

    /**
     * Perform complete login action
     * This is a convenience method that combines username, password, and login
     * click
     * 
     * @param username Username to login with
     * @param password Password to login with
     */
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        System.out.println("Performed login with username: " + username);
    }

    /**
     * Check if login was successful by checking URL
     * 
     * @return true if redirected to inventory page, false otherwise
     */
    public boolean isLoginSuccessful() {
        try {
            waitHelper.waitForUrlToContain("inventory.html");
            return getCurrentUrl().contains("inventory.html");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Clear username field
     */
    public void clearUsername() {
        usernameField.clear();
        System.out.println("Cleared username field");
    }

    /**
     * Clear password field
     */
    public void clearPassword() {
        passwordField.clear();
        System.out.println("Cleared password field");
    }

    /**
     * Clear all login fields
     */
    public void clearAllFields() {
        clearUsername();
        clearPassword();
    }

    /**
     * Check if login button is enabled
     * 
     * @return true if enabled, false otherwise
     */
    public boolean isLoginButtonEnabled() {
        return isEnabled(loginButton);
    }
}
