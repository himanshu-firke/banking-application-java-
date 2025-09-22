@echo off
echo ===============================================
echo    Banking Application - Compile and Run
echo ===============================================

REM Create build directory if it doesn't exist
if not exist "build" mkdir build

REM Compile all Java files
echo Compiling Java files...
javac -d build src/*.java

REM Check if compilation was successful
if %errorlevel% neq 0 (
    echo.
    echo ❌ Compilation failed! Please check for errors.
    pause
    exit /b 1
)

echo ✅ Compilation successful!
echo.

REM Run the application
echo Starting Banking Application...
echo.
cd build
java BankingApp

REM Return to original directory
cd ..

echo.
echo Application closed.
pause
