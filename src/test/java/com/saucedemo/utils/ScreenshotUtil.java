package com.saucedemo.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.saucedemo.config.ConfigReader;

/**
 * Purpose: Screenshot Utility Class
 * This class provides methods to capture screenshots and attach them to Allure
 * reports.
 * Screenshots are automatically captured on test failures and can be manually
 * triggered.
 * 
 * Features:
 * - Captures full page screenshots
 * - Attaches screenshots to Allure reports
 * - Saves screenshots to configured directory
 * - Generates unique filenames with timestamps
 */
public class ScreenshotUtil {

    private static ConfigReader config = ConfigReader.getInstance();

    /**
     * Capture screenshot and attach to Allure report
     * 
     * @param driver         WebDriver instance
     * @param screenshotName Name for the screenshot
     * @return Path to saved screenshot file
     */
    public static String captureScreenshot(WebDriver driver, String screenshotName) {
        try {
            // Take screenshot as bytes
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            byte[] screenshotBytes = screenshot.getScreenshotAs(OutputType.BYTES);

            // Attach to Allure report
            Allure.addAttachment(screenshotName, "image/png",
                    new ByteArrayInputStream(screenshotBytes), "png");

            // Save to file
            String filePath = saveScreenshotToFile(screenshotBytes, screenshotName);

            System.out.println("Screenshot captured: " + filePath);
            return filePath;

        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }

    /**
     * Save screenshot bytes to file
     * 
     * @param screenshotBytes Screenshot as byte array
     * @param screenshotName  Name for the screenshot
     * @return Path to saved file
     */
    private static String saveScreenshotToFile(byte[] screenshotBytes, String screenshotName) {
        try {
            // Create screenshot directory if it doesn't exist
            String screenshotDir = config.getScreenshotDirectory();
            Files.createDirectories(Paths.get(screenshotDir));

            // Generate unique filename with timestamp
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = screenshotName + "_" + timestamp + ".png";
            String filePath = screenshotDir + File.separator + fileName;

            // Write bytes to file
            Files.write(Paths.get(filePath), screenshotBytes);

            return filePath;

        } catch (IOException e) {
            System.err.println("Failed to save screenshot to file: " + e.getMessage());
            return null;
        }
    }

    /**
     * Capture screenshot on test failure
     * This method is typically called from Cucumber hooks
     * 
     * @param driver       WebDriver instance
     * @param scenarioName Name of the failed scenario
     */
    public static void captureScreenshotOnFailure(WebDriver driver, String scenarioName) {
        if (config.isScreenshotOnFailure()) {
            captureScreenshot(driver, "FAILED_" + scenarioName);
        }
    }

    /**
     * Capture screenshot with default name
     * 
     * @param driver WebDriver instance
     * @return Path to saved screenshot
     */
    public static String captureScreenshot(WebDriver driver) {
        String defaultName = "screenshot_" + System.currentTimeMillis();
        return captureScreenshot(driver, defaultName);
    }
}
