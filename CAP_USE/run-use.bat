@echo off
SETLOCAL ENABLEDELAYEDEXPANSION
TITLE USE - One Click Run

set ROOT_DIR=/home/levanvinh/vinh-thesis-sourcecode/sourcecode-local/CAP-Tool/CAP_UDML/CAP_UDML-hung
set VERSION=7.1.1

set PLUGIN_NAME=example-plugin-%VERSION%.jar
set USE_ZIP=use-%VERSION%.zip

set PLUGIN_JAR=%ROOT_DIR%\example-plugin\target\%PLUGIN_NAME%
set PLUGIN_DEST=%ROOT_DIR%\use-assembly\src\main\resources\plugins

set ASSEMBLY_TARGET=%ROOT_DIR%\use-assembly\target
set USE_DIR=%ASSEMBLY_TARGET%\use-%VERSION%
set BIN_DIR=%USE_DIR%\bin
REM ==========================

echo =====================================
echo STEP 1 - BUILD example-plugin
echo =====================================

cd /d %ROOT_DIR%
call mvn clean package -pl example-plugin -am
IF %ERRORLEVEL% NEQ 0 (
    echo Build example-plugin FAILED
    pause
    exit /b 1
)

echo =====================================
echo STEP 2 - COPY PLUGIN JAR
echo =====================================

if not exist "%PLUGIN_JAR%" (
    echo Plugin jar not found: %PLUGIN_JAR%
    pause
    exit /b 1
)

copy /Y "%PLUGIN_JAR%" "%PLUGIN_DEST%"

echo =====================================
echo STEP 3 - BUILD use-assembly
echo =====================================

call mvn package -pl use-assembly
IF %ERRORLEVEL% NEQ 0 (
    echo Build use-assembly FAILED
    pause
    exit /b 1
)

echo =====================================
echo STEP 4 - CLEAN OLD USE FOLDER
echo =====================================

if exist "%USE_DIR%" (
    echo Deleting old folder: %USE_DIR%
    rmdir /S /Q "%USE_DIR%"
)

echo =====================================
echo STEP 5 - UNZIP PACKAGE
echo =====================================

if not exist "%ASSEMBLY_TARGET%\%USE_ZIP%" (
    echo Zip file not found
    pause
    exit /b 1
)

powershell -Command ^
"Expand-Archive -Force '%ASSEMBLY_TARGET%\%USE_ZIP%' '%ASSEMBLY_TARGET%'"

echo =====================================
echo STEP 6 - START USE APP
echo =====================================

if not exist "%BIN_DIR%\start_use.bat" (
    echo start_use.bat not found
    pause
    exit /b 1
)

cd /d "%BIN_DIR%"
call start_use.bat

echo =====================================
echo USE STARTED SUCCESSFULLY
echo =====================================

ENDLOCAL
pause
