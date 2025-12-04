@echo off
REM ============================================================================
REM Purpose: Run All Tests and Generate Allure Report
REM This script runs the complete test suite, generates Allure report,
REM and cleans up unwanted files
REM ============================================================================

echo.
echo ========================================
echo  SauceDemo Test Automation Framework
echo  Running All Tests and Generating Report
echo ========================================
echo.

REM Set the project directory
cd /d "%~dp0"

REM Step 1: Clean previous build artifacts
echo [1/5] Cleaning previous build artifacts...
call mvn clean -q
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Failed to clean project
    pause
    exit /b 1
)
echo       Cleanup complete!
echo.

REM Step 2: Compile the project
echo [2/5] Compiling project...
call mvn compile -q
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Compilation failed
    pause
    exit /b 1
)
echo       Compilation successful!
echo.

REM Step 3: Run all tests
echo [3/5] Running all test cases...
echo       This may take several minutes...
call mvn test
if %ERRORLEVEL% NEQ 0 (
    echo WARNING: Some tests failed (this is normal)
    echo          Continuing to generate report...
) else (
    echo       All tests passed!
)
echo.

REM Step 4: Generate Allure report
echo [4/5] Generating Allure report...
call mvn allure:report -q
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Failed to generate Allure report
    pause
    exit /b 1
)
echo       Allure report generated successfully!
echo.

REM Step 5: Clean up unwanted files
echo [5/5] Cleaning up unwanted files...

REM Remove temporary files
if exist "test-output.txt" del /q "test-output.txt"
if exist "compile-log.txt" del /q "compile-log.txt"

REM Remove Maven wrapper files if not needed
if exist ".mvn" rmdir /s /q ".mvn" 2>nul

REM Remove IDE-specific files
if exist ".vscode\settings.json" del /q ".vscode\settings.json" 2>nul
if exist ".idea" rmdir /s /q ".idea" 2>nul
if exist "*.iml" del /q "*.iml" 2>nul

REM Remove log files
if exist "*.log" del /q "*.log" 2>nul

REM Remove temporary test data
if exist "testdb\*.tmp" del /q "testdb\*.tmp" 2>nul

REM Remove Redis dump if exists
if exist "dump.rdb" del /q "dump.rdb" 2>nul

echo       Cleanup complete!
echo.

REM Display summary
echo ========================================
echo  Test Execution Complete!
echo ========================================
echo.
echo Reports Generated:
echo   - Allure Report: target\site\allure-maven-plugin\index.html
echo   - TestNG Report: target\surefire-reports\index.html
echo.
echo To view Allure report in browser, run:
echo   mvn allure:serve
echo.
echo Or open manually:
echo   start target\site\allure-maven-plugin\index.html
echo.

REM Ask if user wants to open report
set /p OPEN_REPORT="Do you want to open the Allure report now? (Y/N): "
if /i "%OPEN_REPORT%"=="Y" (
    echo.
    echo Opening Allure report in browser...
    start target\site\allure-maven-plugin\index.html
    echo.
    echo If the report doesn't open, you can also run: mvn allure:serve
)

echo.
echo ========================================
echo  Process Complete!
echo ========================================
echo.
pause
