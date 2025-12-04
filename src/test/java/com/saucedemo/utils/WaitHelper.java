package com.saucedemo.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;

import com.saucedemo.config.ConfigReader;

import java.time.Duration;

/**
 * Purpose: Wait Helper Utility Class
 * This class provides reusable explicit wait methods for various conditions
 * to ensure elements are in the expected state before interaction.
 * 
 * Benefits:
 * - Reduces flakiness in tests
 * - Provides consistent wait strategy across framework
 * - Centralizes wait logic for easy maintenance
 */
public class WaitHelper {

    private WebDriver driver;
    private WebDriverWait wait;
    private static ConfigReader config = ConfigReader.getInstance();

    /**
     * Constructor to initialize WaitHelper with WebDriver
     * 
     * @param driver WebDriver instance
     */
    public WaitHelper(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(config.getExplicitWait()));
    }

    /**
     * Wait for element to be visible
     * 
     * @param element WebElement to wait for
     * @return WebElement once visible
     */
    public WebElement waitForElementToBeVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Wait for element to be visible by locator
     * 
     * @param locator By locator
     * @return WebElement once visible
     */
    public WebElement waitForElementToBeVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait for element to be clickable
     * 
     * @param element WebElement to wait for
     * @return WebElement once clickable
     */
    public WebElement waitForElementToBeClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Wait for element to be clickable by locator
     * 
     * @param locator By locator
     * @return WebElement once clickable
     */
    public WebElement waitForElementToBeClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Wait for element to be invisible
     * 
     * @param locator By locator
     * @return true if element becomes invisible
     */
    public boolean waitForElementToBeInvisible(By locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Wait for element to be present in DOM
     * 
     * @param locator By locator
     * @return WebElement once present
     */
    public WebElement waitForElementToBePresent(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Wait for text to be present in element
     * 
     * @param locator By locator
     * @param text    Expected text
     * @return true if text is present
     */
    public boolean waitForTextToBePresentInElement(By locator, String text) {
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    /**
     * Wait for URL to contain specific text
     * 
     * @param urlFragment URL fragment to wait for
     * @return true if URL contains fragment
     */
    public boolean waitForUrlToContain(String urlFragment) {
        return wait.until(ExpectedConditions.urlContains(urlFragment));
    }

    /**
     * Wait for URL to be specific value
     * 
     * @param url Expected URL
     * @return true if URL matches
     */
    public boolean waitForUrlToBe(String url) {
        return wait.until(ExpectedConditions.urlToBe(url));
    }

    /**
     * Wait for title to contain specific text
     * 
     * @param title Title fragment to wait for
     * @return true if title contains text
     */
    public boolean waitForTitleToContain(String title) {
        return wait.until(ExpectedConditions.titleContains(title));
    }

    /**
     * Wait for alert to be present
     * 
     * @return Alert once present
     */
    public org.openqa.selenium.Alert waitForAlertToBePresent() {
        return wait.until(ExpectedConditions.alertIsPresent());
    }

    /**
     * Custom wait with specific timeout
     * 
     * @param timeoutInSeconds Custom timeout in seconds
     * @return WebDriverWait instance with custom timeout
     */
    public WebDriverWait getWaitWithCustomTimeout(int timeoutInSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
    }

    /**
     * Hard wait (Thread.sleep) - Use sparingly, prefer explicit waits
     * 
     * @param milliseconds Time to wait in milliseconds
     */
    public void hardWait(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Wait interrupted: " + e.getMessage());
        }
    }
}
