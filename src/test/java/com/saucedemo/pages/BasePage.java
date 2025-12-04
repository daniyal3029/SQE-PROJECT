package com.saucedemo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.saucedemo.utils.WaitHelper;

/**
 * Purpose: Base Page Class
 * This is the parent class for all Page Object classes.
 * Contains common methods and utilities that can be used across all pages.
 * 
 * Design Pattern: Page Object Model (POM)
 * 
 * Features:
 * - WebDriver instance management
 * - WaitHelper instance for explicit waits
 * - Common page operations
 * - Page initialization with PageFactory
 */
public class BasePage {

    protected WebDriver driver;
    protected WaitHelper waitHelper;

    /**
     * Constructor to initialize BasePage
     * 
     * @param driver WebDriver instance
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.waitHelper = new WaitHelper(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Get current page title
     * 
     * @return Page title
     */
    public String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Get current page URL
     * 
     * @return Current URL
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Click on element with wait
     * 
     * @param element WebElement to click
     */
    protected void click(WebElement element) {
        waitHelper.waitForElementToBeClickable(element);
        element.click();
    }

    /**
     * Enter text in element with wait
     * 
     * @param element WebElement to type in
     * @param text    Text to enter
     */
    protected void type(WebElement element, String text) {
        waitHelper.waitForElementToBeVisible(element);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Get text from element with wait
     * 
     * @param element WebElement to get text from
     * @return Element text
     */
    protected String getText(WebElement element) {
        waitHelper.waitForElementToBeVisible(element);
        return element.getText();
    }

    /**
     * Check if element is displayed
     * 
     * @param element WebElement to check
     * @return true if displayed, false otherwise
     */
    protected boolean isDisplayed(WebElement element) {
        try {
            waitHelper.waitForElementToBeVisible(element);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if element is enabled
     * 
     * @param element WebElement to check
     * @return true if enabled, false otherwise
     */
    protected boolean isEnabled(WebElement element) {
        try {
            return element.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Wait for page to load (wait for URL to contain specific text)
     * 
     * @param urlFragment URL fragment to wait for
     */
    protected void waitForPageToLoad(String urlFragment) {
        waitHelper.waitForUrlToContain(urlFragment);
    }

    /**
     * Refresh current page
     */
    protected void refreshPage() {
        driver.navigate().refresh();
    }

    /**
     * Navigate back
     */
    protected void navigateBack() {
        driver.navigate().back();
    }
}
