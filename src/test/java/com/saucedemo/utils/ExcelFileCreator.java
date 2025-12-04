package com.saucedemo.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Purpose: Excel File Creator Utility
 * This utility class creates the testdata.xlsx file with test data for the
 * framework.
 * Run this class once to generate the Excel file with all required sheets and
 * data.
 * 
 * Sheets Created:
 * - LoginData: User credentials for login tests
 * - ProductData: Product information for product tests
 * - CheckoutData: Checkout form data for checkout tests
 */
public class ExcelFileCreator {

    private static final String FILE_PATH = "src/test/resources/testdata/testdata.xlsx";

    public static void main(String[] args) {
        createExcelFile();
    }

    /**
     * Create Excel file with all test data sheets
     */
    public static void createExcelFile() {
        Workbook workbook = new XSSFWorkbook();

        try {
            // Create LoginData sheet
            createLoginDataSheet(workbook);

            // Create ProductData sheet
            createProductDataSheet(workbook);

            // Create CheckoutData sheet
            createCheckoutDataSheet(workbook);

            // Write to file
            try (FileOutputStream fileOut = new FileOutputStream(FILE_PATH)) {
                workbook.write(fileOut);
                System.out.println("Excel file created successfully: " + FILE_PATH);
            }

            workbook.close();

        } catch (IOException e) {
            System.err.println("Error creating Excel file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Create LoginData sheet with user credentials
     */
    private static void createLoginDataSheet(Workbook workbook) {
        Sheet sheet = workbook.createSheet("LoginData");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("username");
        headerRow.createCell(1).setCellValue("password");
        headerRow.createCell(2).setCellValue("expected_result");

        // Add data rows
        String[][] data = {
                { "standard_user", "secret_sauce", "success" },
                { "performance_glitch_user", "secret_sauce", "success" },
                { "invalid_user", "wrong_password", "failure" },
                { "locked_out_user", "secret_sauce", "locked" }
        };

        for (int i = 0; i < data.length; i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < data[i].length; j++) {
                row.createCell(j).setCellValue(data[i][j]);
            }
        }

        // Auto-size columns
        for (int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
        }

        System.out.println("Created LoginData sheet with " + data.length + " rows");
    }

    /**
     * Create ProductData sheet with product information
     */
    private static void createProductDataSheet(Workbook workbook) {
        Sheet sheet = workbook.createSheet("ProductData");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("product_name");
        headerRow.createCell(1).setCellValue("expected_price");

        // Add data rows
        String[][] data = {
                { "Sauce Labs Backpack", "29.99" },
                { "Sauce Labs Bike Light", "9.99" },
                { "Sauce Labs Bolt T-Shirt", "15.99" },
                { "Sauce Labs Fleece Jacket", "49.99" },
                { "Sauce Labs Onesie", "7.99" },
                { "Test.allTheThings() T-Shirt (Red)", "15.99" }
        };

        for (int i = 0; i < data.length; i++) {
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(data[i][0]);
            row.createCell(1).setCellValue(data[i][1]);
        }

        // Auto-size columns
        for (int i = 0; i < 2; i++) {
            sheet.autoSizeColumn(i);
        }

        System.out.println("Created ProductData sheet with " + data.length + " rows");
    }

    /**
     * Create CheckoutData sheet with checkout form information
     */
    private static void createCheckoutDataSheet(Workbook workbook) {
        Sheet sheet = workbook.createSheet("CheckoutData");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("firstName");
        headerRow.createCell(1).setCellValue("lastName");
        headerRow.createCell(2).setCellValue("zipCode");

        // Add data rows
        String[][] data = {
                { "John", "Doe", "12345" },
                { "Jane", "Smith", "67890" },
                { "Bob", "Johnson", "54321" },
                { "Alice", "Williams", "98765" }
        };

        for (int i = 0; i < data.length; i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < data[i].length; j++) {
                row.createCell(j).setCellValue(data[i][j]);
            }
        }

        // Auto-size columns
        for (int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
        }

        System.out.println("Created CheckoutData sheet with " + data.length + " rows");
    }
}
