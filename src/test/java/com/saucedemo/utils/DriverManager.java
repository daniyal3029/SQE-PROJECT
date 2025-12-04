package com.saucedemo.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.saucedemo.config.ConfigReader;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

/**
 * Purpose: WebDriver Manager Utility Class
 * This class manages WebDriver instances using ThreadLocal for thread-safe
 * parallel execution.
 * It provides methods to initialize, get, and quit WebDriver instances.
 * 
 * Design Pattern: Factory Pattern with ThreadLocal
 * Thread-Safe: Yes (using ThreadLocal)
 */
public class DriverManager {

    // ThreadLocal to store WebDriver instance for each thread
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    // Configuration reader instance
    private static ConfigReader config = ConfigReader.getInstance();

    /**
     * Initialize WebDriver based on browser configuration
     * Sets up browser options, timeouts, and window size
     * 
     * @return WebDriver instance
     */
    public static WebDriver initializeDriver() {
        String browser = config.getBrowser().toLowerCase();
        WebDriver webDriver = null;

        System.out.println("Initializing WebDriver for browser: " + browser);

        switch (browser) {
            case "chrome":
                webDriver = initializeChromeDriver();
                break;

            case "firefox":
                webDriver = initializeFirefoxDriver();
                break;

            case "edge":
                webDriver = initializeEdgeDriver();
                break;

            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        // Set timeouts
        webDriver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(config.getImplicitWait()));
        webDriver.manage().timeouts()
                .pageLoadTimeout(Duration.ofSeconds(config.getPageLoadTimeout()));
        webDriver.manage().timeouts()
                .scriptTimeout(Duration.ofSeconds(config.getScriptTimeout()));

        // Maximize window if configured
        if (config.isWindowMaximize()) {
            webDriver.manage().window().maximize();
        }

        // Store driver in ThreadLocal
        driver.set(webDriver);

        System.out.println("WebDriver initialized successfully");
        return webDriver;
    }

    /**
     * Initialize Chrome WebDriver with options
     * 
     * @return ChromeDriver instance
     */
    private static WebDriver initializeChromeDriver() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        // Add headless mode if configured
        if (config.isHeadlessMode()) {
            options.addArguments("--headless=new");
        }

        // Add common Chrome options for stability
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.addArguments("--remote-allow-origins=*");

        return new ChromeDriver(options);
    }

    /**
     * Initialize Firefox WebDriver with options
     * 
     * @return FirefoxDriver instance
     */
    private static WebDriver initializeFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();

        FirefoxOptions options = new FirefoxOptions();

        // Add headless mode if configured
        if (config.isHeadlessMode()) {
            options.addArguments("--headless");
        }

        return new FirefoxDriver(options);
    }

    /**
     * Initialize Edge WebDriver with options
     * 
     * @return EdgeDriver instance
     */
    private static WebDriver initializeEdgeDriver() {
        WebDriverManager.edgedriver().setup();

        EdgeOptions options = new EdgeOptions();

        // Add headless mode if configured
        if (config.isHeadlessMode()) {
            options.addArguments("--headless");
        }

        return new EdgeDriver(options);
    }

    /**
     * Get current WebDriver instance from ThreadLocal
     * 
     * @return WebDriver instance
     */
    public static WebDriver getDriver() {
        WebDriver webDriver = driver.get();
        if (webDriver == null) {
            throw new IllegalStateException("WebDriver is not initialized. Call initializeDriver() first.");
        }
        return webDriver;
    }

    /**
     * Quit WebDriver and remove from ThreadLocal
     * This method should be called after test execution
     */
    public static void quitDriver() {
        WebDriver webDriver = driver.get();
        if (webDriver != null) {
            try {
                webDriver.quit();
                System.out.println("WebDriver quit successfully");
            } catch (Exception e) {
                System.err.println("Error quitting WebDriver: " + e.getMessage());
            } finally {
                driver.remove();
            }
        }
    }

    /**
     * Navigate to URL
     * 
     * @param url URL to navigate to
     */
    public static void navigateToUrl(String url) {
        getDriver().get(url);
        System.out.println("Navigated to URL: " + url);
    }

    /**
     * Navigate to base URL from configuration
     */
    public static void navigateToBaseUrl() {
        navigateToUrl(config.getBaseUrl());
    }
}
