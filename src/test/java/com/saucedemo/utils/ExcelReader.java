package com.saucedemo.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.saucedemo.config.ConfigReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Purpose: Excel Reader Utility Class
 * This class provides methods to read test data from Excel files using Apache
 * POI library.
 * Supports reading data as maps, lists, and individual cell values.
 * 
 * Supported Format: .xlsx (Excel 2007+)
 * 
 * Features:
 * - Read entire sheet as list of maps
 * - Read specific row data
 * - Read specific cell value
 * - Support for multiple data types (String, Number, Boolean)
 */
public class ExcelReader {

    private String filePath;
    private Workbook workbook;
    private static ConfigReader config = ConfigReader.getInstance();

    /**
     * Constructor with default Excel file path from configuration
     */
    public ExcelReader() {
        this.filePath = config.getExcelFilePath();
    }

    /**
     * Constructor with custom file path
     * 
     * @param filePath Path to Excel file
     */
    public ExcelReader(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Open Excel workbook
     * 
     * @throws IOException if file cannot be opened
     */
    private void openWorkbook() throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        workbook = new XSSFWorkbook(fis);
    }

    /**
     * Close Excel workbook
     */
    private void closeWorkbook() {
        if (workbook != null) {
            try {
                workbook.close();
            } catch (IOException e) {
                System.err.println("Error closing workbook: " + e.getMessage());
            }
        }
    }

    /**
     * Read all data from a sheet as list of maps
     * First row is treated as header (column names)
     * 
     * @param sheetName Name of the sheet to read
     * @return List of maps, each map represents a row with column name as key
     */
    public List<Map<String, String>> readSheetData(String sheetName) {
        List<Map<String, String>> data = new ArrayList<>();

        try {
            openWorkbook();
            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new IllegalArgumentException("Sheet not found: " + sheetName);
            }

            // Get header row (first row)
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new IllegalStateException("Header row is empty in sheet: " + sheetName);
            }

            // Read data rows
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    Map<String, String> rowData = new HashMap<>();

                    for (int j = 0; j < headerRow.getLastCellNum(); j++) {
                        Cell headerCell = headerRow.getCell(j);
                        Cell dataCell = row.getCell(j);

                        String columnName = getCellValueAsString(headerCell);
                        String cellValue = getCellValueAsString(dataCell);

                        rowData.put(columnName, cellValue);
                    }

                    data.add(rowData);
                }
            }

            System.out.println("Read " + data.size() + " rows from sheet: " + sheetName);

        } catch (IOException e) {
            System.err.println("Error reading Excel file: " + e.getMessage());
            throw new RuntimeException("Failed to read Excel data", e);
        } finally {
            closeWorkbook();
        }

        return data;
    }

    /**
     * Read specific row data from sheet
     * 
     * @param sheetName Name of the sheet
     * @param rowIndex  Row index (0-based, excluding header)
     * @return Map of column name to cell value
     */
    public Map<String, String> readRowData(String sheetName, int rowIndex) {
        Map<String, String> rowData = new HashMap<>();

        try {
            openWorkbook();
            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new IllegalArgumentException("Sheet not found: " + sheetName);
            }

            Row headerRow = sheet.getRow(0);
            Row dataRow = sheet.getRow(rowIndex + 1); // +1 because header is row 0

            if (dataRow != null && headerRow != null) {
                for (int j = 0; j < headerRow.getLastCellNum(); j++) {
                    Cell headerCell = headerRow.getCell(j);
                    Cell dataCell = dataRow.getCell(j);

                    String columnName = getCellValueAsString(headerCell);
                    String cellValue = getCellValueAsString(dataCell);

                    rowData.put(columnName, cellValue);
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading Excel file: " + e.getMessage());
            throw new RuntimeException("Failed to read Excel data", e);
        } finally {
            closeWorkbook();
        }

        return rowData;
    }

    /**
     * Read specific cell value
     * 
     * @param sheetName  Name of the sheet
     * @param rowIndex   Row index (0-based, excluding header)
     * @param columnName Column name from header
     * @return Cell value as String
     */
    public String readCellData(String sheetName, int rowIndex, String columnName) {
        String cellValue = "";

        try {
            openWorkbook();
            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new IllegalArgumentException("Sheet not found: " + sheetName);
            }

            Row headerRow = sheet.getRow(0);
            Row dataRow = sheet.getRow(rowIndex + 1);

            // Find column index by name
            int columnIndex = -1;
            for (int j = 0; j < headerRow.getLastCellNum(); j++) {
                if (getCellValueAsString(headerRow.getCell(j)).equals(columnName)) {
                    columnIndex = j;
                    break;
                }
            }

            if (columnIndex == -1) {
                throw new IllegalArgumentException("Column not found: " + columnName);
            }

            if (dataRow != null) {
                Cell cell = dataRow.getCell(columnIndex);
                cellValue = getCellValueAsString(cell);
            }

        } catch (IOException e) {
            System.err.println("Error reading Excel file: " + e.getMessage());
            throw new RuntimeException("Failed to read Excel data", e);
        } finally {
            closeWorkbook();
        }

        return cellValue;
    }

    /**
     * Get cell value as String, handling different cell types
     * 
     * @param cell Cell to read
     * @return Cell value as String
     */
    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();

            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    // Return number without decimal if it's a whole number
                    double numValue = cell.getNumericCellValue();
                    if (numValue == (long) numValue) {
                        return String.valueOf((long) numValue);
                    } else {
                        return String.valueOf(numValue);
                    }
                }

            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());

            case FORMULA:
                return cell.getCellFormula();

            case BLANK:
                return "";

            default:
                return "";
        }
    }

    /**
     * Get row count from sheet (excluding header)
     * 
     * @param sheetName Name of the sheet
     * @return Number of data rows
     */
    public int getRowCount(String sheetName) {
        int rowCount = 0;

        try {
            openWorkbook();
            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet != null) {
                rowCount = sheet.getLastRowNum(); // Excludes header row
            }

        } catch (IOException e) {
            System.err.println("Error reading Excel file: " + e.getMessage());
        } finally {
            closeWorkbook();
        }

        return rowCount;
    }
}
