# Excel File Creation - Manual Instructions

Since automated Excel creation encountered classpath issues, please create the Excel file manually:

## Quick Method

1. Open Excel or LibreOffice Calc
2. Create a new workbook
3. Save as: `src\test\resources\testdata\testdata.xlsx`

## Create 3 Sheets:

### Sheet 1: LoginData
```
username                | password      | expected_result
standard_user           | secret_sauce  | success
performance_glitch_user | secret_sauce  | success
invalid_user            | wrong_password| failure
locked_out_user         | secret_sauce  | locked
```

### Sheet 2: ProductData
```
product_name                          | expected_price
Sauce Labs Backpack                   | 29.99
Sauce Labs Bike Light                 | 9.99
Sauce Labs Bolt T-Shirt               | 15.99
Sauce Labs Fleece Jacket              | 49.99
Sauce Labs Onesie                     | 7.99
Test.allTheThings() T-Shirt (Red)     | 15.99
```

### Sheet 3: CheckoutData
```
firstName | lastName  | zipCode
John      | Doe       | 12345
Jane      | Smith     | 67890
Bob       | Johnson   | 54321
Alice     | Williams  | 98765
```

## Alternative: CSV Files Available

CSV backup files are already created in `src\test\resources\testdata\`:
- LoginData.csv
- ProductData.csv
- CheckoutData.csv

The tests can run with these CSV files if Excel is not available.

## After Creating Excel

Run tests with:
```bash
mvn clean test
```
