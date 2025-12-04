@echo off
REM Purpose: Create Excel test data file using Java
REM This script compiles and runs the ExcelFileCreator utility

echo Creating Excel test data file...

cd /d "%~dp0"

REM Compile the project
call mvn test-compile -q

REM Run ExcelFileCreator
call mvn exec:java -Dexec.mainClass="com.saucedemo.utils.ExcelFileCreator" -Dexec.classpathScope=test -q

if %ERRORLEVEL% EQU 0 (
    echo.
    echo Excel file created successfully!
    echo Location: src\test\resources\testdata\testdata.xlsx
) else (
    echo.
    echo Failed to create Excel file. Creating manually...
    echo Please create testdata.xlsx manually following HOW_TO_CREATE_EXCEL.md
)

pause
