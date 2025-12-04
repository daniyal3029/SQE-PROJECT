package com.saucedemo.steps;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Scenario;

import com.saucedemo.utils.DriverManager;
import com.saucedemo.utils.ScreenshotUtil;
import com.saucedemo.utils.DatabaseUtil;
import com.saucedemo.utils.RedisUtil;

/**
 * Purpose: Cucumber Hooks Class
 * This class contains Cucumber hooks that run before and after
 * scenarios/suites.
 * Handles WebDriver initialization, cleanup, screenshot capture, and data
 * source setup.
 * 
 * Hooks:
 * - @BeforeAll: Initialize database and Redis (once per suite)
 * - @Before: Initialize WebDriver (before each scenario)
 * - @After: Capture screenshot on failure and quit WebDriver (after each
 * scenario)
 * - @AfterAll: Close database and Redis connections (once per suite)
 */
public class Hooks {

    /**
     * Setup method that runs once before all scenarios
     * Initializes database and Redis connections
     */
    @BeforeAll
    public static void beforeAll() {
        System.out.println("========================================");
        System.out.println("Starting Test Suite Execution");
        System.out.println("========================================");

        // Initialize H2 Database
        try {
            DatabaseUtil.initializeDatabase();
            System.out.println("Database initialized successfully");
        } catch (Exception e) {
            System.err.println("Failed to initialize database: " + e.getMessage());
        }

        // Initialize Redis
        try {
            RedisUtil.initializeRedis();
            if (RedisUtil.isRedisAvailable()) {
                System.out.println("Redis initialized successfully");

                // Store some test data in Redis
                RedisUtil.set("test:framework", "Selenium-Cucumber-Java");
                RedisUtil.set("test:website", "SauceDemo");
                RedisUtil.storeSessionToken("test-session-1", "token-12345", 3600);
            }
        } catch (Exception e) {
            System.err.println("Failed to initialize Redis: " + e.getMessage());
        }
    }

    /**
     * Setup method that runs before each scenario
     * Initializes WebDriver
     * 
     * @param scenario Current scenario
     */
    @Before
    public void before(Scenario scenario) {
        System.out.println("----------------------------------------");
        System.out.println("Starting Scenario: " + scenario.getName());
        System.out.println("Tags: " + scenario.getSourceTagNames());
        System.out.println("----------------------------------------");

        // Initialize WebDriver
        DriverManager.initializeDriver();
    }

    /**
     * Teardown method that runs after each scenario
     * Captures screenshot on failure and quits WebDriver
     * 
     * @param scenario Current scenario
     */
    @After
    public void after(Scenario scenario) {
        // Capture screenshot if scenario failed
        if (scenario.isFailed()) {
            System.out.println("Scenario FAILED: " + scenario.getName());
            try {
                ScreenshotUtil.captureScreenshotOnFailure(
                        DriverManager.getDriver(),
                        scenario.getName().replaceAll(" ", "_"));
            } catch (Exception e) {
                System.err.println("Failed to capture screenshot: " + e.getMessage());
            }
        } else {
            System.out.println("Scenario PASSED: " + scenario.getName());
        }

        // Quit WebDriver
        DriverManager.quitDriver();

        System.out.println("----------------------------------------");
    }

    /**
     * Cleanup method that runs once after all scenarios
     * Closes database and Redis connections
     */
    @AfterAll
    public static void afterAll() {
        System.out.println("========================================");
        System.out.println("Test Suite Execution Completed");
        System.out.println("========================================");

        // Close database connection
        try {
            DatabaseUtil.closeConnection();
            System.out.println("Database connection closed");
        } catch (Exception e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }

        // Close Redis connection
        try {
            if (RedisUtil.isRedisAvailable()) {
                RedisUtil.closeRedis();
                System.out.println("Redis connection closed");
            }
        } catch (Exception e) {
            System.err.println("Error closing Redis connection: " + e.getMessage());
        }
    }
}
