package com.saucedemo.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/**
 * Purpose: Cucumber TestNG Test Runner
 * This class configures and runs Cucumber tests with TestNG.
 * Integrates with Allure for reporting.
 * 
 * Configuration:
 * - Feature files location
 * - Step definitions package
 * - Plugins (Allure, pretty console output)
 * - Tags for selective execution
 * - Report formatting
 * 
 * Execution:
 * Run this class to execute all Cucumber scenarios
 */
@CucumberOptions(features = "src/test/resources/features", // Path to feature files
        glue = { "com.saucedemo.steps" }, // Package containing step definitions
        plugin = {
                "pretty", // Pretty console output
                "html:target/cucumber-reports/cucumber.html", // HTML report
                "json:target/cucumber-reports/cucumber.json", // JSON report
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm" // Allure report plugin
        }, monochrome = true, // Readable console output
        dryRun = false, // Set to true to check step definitions without execution
        tags = "@Regression" // Run scenarios with @Regression tag
)
public class TestRunner extends AbstractTestNGCucumberTests {

    /**
     * Enable parallel execution of scenarios
     * Override this method to run scenarios in parallel
     * 
     * @return Scenarios data provider
     */
    @Override
    @DataProvider(parallel = false) // Set to true for parallel execution
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
