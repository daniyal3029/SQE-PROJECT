package com.saucedemo.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Purpose: Configuration Reader Utility Class
 * This singleton class reads configuration properties from config.properties
 * file
 * and provides methods to access configuration values throughout the framework.
 * 
 * Design Pattern: Singleton
 * Thread-Safe: Yes
 */
public class ConfigReader {

    // Singleton instance
    private static ConfigReader instance;

    // Properties object to store configuration
    private Properties properties;

    // Configuration file path
    private static final String CONFIG_FILE_PATH = "src/test/resources/config.properties";

    /**
     * Private constructor to prevent instantiation
     * Loads properties from config.properties file
     */
    private ConfigReader() {
        properties = new Properties();
        loadProperties();
    }

    /**
     * Get singleton instance of ConfigReader
     * Thread-safe implementation using synchronized block
     * 
     * @return ConfigReader instance
     */
    public static synchronized ConfigReader getInstance() {
        if (instance == null) {
            instance = new ConfigReader();
        }
        return instance;
    }

    /**
     * Load properties from configuration file
     * Handles IOException if file is not found
     */
    private void loadProperties() {
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(fis);
            System.out.println("Configuration loaded successfully from: " + CONFIG_FILE_PATH);
        } catch (IOException e) {
            System.err.println("Error loading configuration file: " + e.getMessage());
            throw new RuntimeException("Failed to load configuration file", e);
        }
    }

    /**
     * Get property value by key
     * 
     * @param key Property key
     * @return Property value as String
     */
    public String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property not found: " + key);
        }
        return value;
    }

    /**
     * Get property value with default fallback
     * 
     * @param key          Property key
     * @param defaultValue Default value if key not found
     * @return Property value or default value
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    // ============================================================================
    // Browser Configuration Methods
    // ============================================================================

    public String getBrowser() {
        return getProperty("browser");
    }

    public boolean isWindowMaximize() {
        return Boolean.parseBoolean(getProperty("window.maximize", "true"));
    }

    public boolean isHeadlessMode() {
        return Boolean.parseBoolean(getProperty("headless.mode", "false"));
    }

    // ============================================================================
    // URL Configuration Methods
    // ============================================================================

    public String getBaseUrl() {
        return getProperty("base.url");
    }

    // ============================================================================
    // Timeout Configuration Methods
    // ============================================================================

    public int getImplicitWait() {
        return Integer.parseInt(getProperty("implicit.wait", "10"));
    }

    public int getExplicitWait() {
        return Integer.parseInt(getProperty("explicit.wait", "20"));
    }

    public int getPageLoadTimeout() {
        return Integer.parseInt(getProperty("page.load.timeout", "30"));
    }

    public int getScriptTimeout() {
        return Integer.parseInt(getProperty("script.timeout", "30"));
    }

    // ============================================================================
    // Screenshot Configuration Methods
    // ============================================================================

    public boolean isScreenshotOnFailure() {
        return Boolean.parseBoolean(getProperty("screenshot.on.failure", "true"));
    }

    public String getScreenshotDirectory() {
        return getProperty("screenshot.directory", "target/screenshots");
    }

    // ============================================================================
    // Excel Configuration Methods
    // ============================================================================

    public String getExcelFilePath() {
        return getProperty("excel.file.path");
    }

    public String getExcelSheetLogin() {
        return getProperty("excel.sheet.login");
    }

    public String getExcelSheetProduct() {
        return getProperty("excel.sheet.product");
    }

    public String getExcelSheetCheckout() {
        return getProperty("excel.sheet.checkout");
    }

    // ============================================================================
    // Database Configuration Methods
    // ============================================================================

    public String getDbUrl() {
        return getProperty("db.url");
    }

    public String getDbUsername() {
        return getProperty("db.username");
    }

    public String getDbPassword() {
        return getProperty("db.password", "");
    }

    public String getDbDriver() {
        return getProperty("db.driver");
    }

    public boolean isDbInitialize() {
        return Boolean.parseBoolean(getProperty("db.initialize", "true"));
    }

    // ============================================================================
    // Redis Configuration Methods
    // ============================================================================

    public String getRedisHost() {
        return getProperty("redis.host");
    }

    public int getRedisPort() {
        return Integer.parseInt(getProperty("redis.port", "6379"));
    }

    public int getRedisTimeout() {
        return Integer.parseInt(getProperty("redis.timeout", "2000"));
    }

    public String getRedisPassword() {
        return getProperty("redis.password", "");
    }

    public boolean isRedisEnabled() {
        return Boolean.parseBoolean(getProperty("redis.enabled", "true"));
    }

    // ============================================================================
    // Test Data Configuration Methods
    // ============================================================================

    public String getStandardUser() {
        return getProperty("test.user.standard");
    }

    public String getLockedUser() {
        return getProperty("test.user.locked");
    }

    public String getProblemUser() {
        return getProperty("test.user.problem");
    }

    public String getPerformanceUser() {
        return getProperty("test.user.performance");
    }

    public String getTestPassword() {
        return getProperty("test.password");
    }
}
