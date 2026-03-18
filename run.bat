@echo off
REM ============================================================
REM run.bat - Fully self-contained: Downloads Tomcat, Builds,
REM           Deploys, and Runs Spring MVC app
REM ============================================================
REM Usage:
REM   run.bat              Download Tomcat (if needed) + Build + Start
REM   run.bat stop         Stop Tomcat
REM   run.bat restart      Rebuild + Restart Tomcat
REM   run.bat clean        Remove downloaded Tomcat and build artifacts
REM ============================================================

setlocal enabledelayedexpansion

set "PROJECT_DIR=%~dp0"
set "TOMCAT_VERSION=10.1.52"
set "TOMCAT_FOLDER=apache-tomcat-%TOMCAT_VERSION%"
set "TOMCAT_DIR=C:\Users\ashwi\Downloads\apache-tomcat-11.0.18-windows-x64\apache-tomcat-11.0.18"
set "TOMCAT_ZIP=%PROJECT_DIR%apache-tomcat-%TOMCAT_VERSION%.zip"
set "TOMCAT_URL=https://archive.apache.org/dist/tomcat/tomcat-10/v%TOMCAT_VERSION%/bin/apache-tomcat-%TOMCAT_VERSION%-windows-x64.zip"
set "CATALINA_HOME=%TOMCAT_DIR%"
set "WAR_NAME=SpringMvcHelloWorld"

set "MAVEN_VERSION=3.9.9"
set "MAVEN_FOLDER=apache-maven-%MAVEN_VERSION%"
set "MAVEN_DIR=%PROJECT_DIR%%MAVEN_FOLDER%"
set "MAVEN_ZIP=%PROJECT_DIR%apache-maven-%MAVEN_VERSION%-bin.zip"
set "MAVEN_URL=https://archive.apache.org/dist/maven/maven-3/%MAVEN_VERSION%/binaries/apache-maven-%MAVEN_VERSION%-bin.zip"

REM =========================================
REM  Handle 'clean' command
REM =========================================
if "%1"=="clean" (
    echo Cleaning up...
    if exist "%TOMCAT_DIR%" rmdir /s /q "%TOMCAT_DIR%"
    if exist "%TOMCAT_ZIP%" del /f "%TOMCAT_ZIP%"
    if exist "%MAVEN_DIR%" rmdir /s /q "%MAVEN_DIR%"
    if exist "%MAVEN_ZIP%" del /f "%MAVEN_ZIP%"
    if exist "%PROJECT_DIR%target" rmdir /s /q "%PROJECT_DIR%target"
    echo Done. Run "run.bat" to re-download and rebuild everything.
    exit /b 0
)

REM =========================================
REM  Handle 'stop' command
REM =========================================
if "%1"=="stop" (
    if exist "%TOMCAT_DIR%\bin\catalina.bat" (
        echo Stopping Tomcat...
        call "%TOMCAT_DIR%\bin\catalina.bat" stop
        timeout /t 3 /nobreak >nul
    )
    echo Tomcat stopped.
    exit /b 0
)

REM =========================================
REM  Handle 'restart' command
REM =========================================
if "%1"=="restart" (
    if exist "%TOMCAT_DIR%\bin\catalina.bat" (
        echo Stopping Tomcat...
        call "%TOMCAT_DIR%\bin\catalina.bat" stop
        timeout /t 3 /nobreak >nul
    )
)

REM =========================================
REM  Step 1: Check Java
REM =========================================
echo =========================================
echo  Step 1: Checking Java installation
echo =========================================

if "%JAVA_HOME%"=="" (
    echo JAVA_HOME is not set. Attempting to detect...
    for /f "tokens=*" %%i in ('where java 2^>nul') do (
        for %%j in ("%%~dpi..") do set "JAVA_HOME=%%~fj"
    )
    if "!JAVA_HOME!"=="" (
        echo ERROR: Could not detect JAVA_HOME. Please install Java 17+.
        echo Download from: https://adoptium.net/
        exit /b 1
    )
    echo Detected JAVA_HOME: !JAVA_HOME!
)

java -version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Java is not installed or not in PATH.
    echo Please install Java 17+ and add it to your PATH.
    echo Download from: https://adoptium.net/
    exit /b 1
)
echo Java found.

REM =========================================
REM  Step 2: Check / Download Maven
REM =========================================
echo.
echo =========================================
echo  Step 2: Setting up Maven
echo =========================================

REM Check if Maven is already on PATH
mvn -version >nul 2>&1
if not errorlevel 1 (
    echo Maven found on PATH.
    goto :maven_ready
)

REM Check if we have a local Maven already downloaded
if exist "%MAVEN_DIR%\bin\mvn.cmd" (
    echo Using local Maven at: %MAVEN_DIR%
    set "PATH=%MAVEN_DIR%\bin;%PATH%"
    goto :maven_ready
)

echo Maven not found. Downloading Maven %MAVEN_VERSION%...
echo Destination: "%MAVEN_ZIP%"
echo.

REM Try curl first (built into Windows 10+)
echo Trying download with curl...
curl -L -o "%MAVEN_ZIP%" "%MAVEN_URL%" 2>nul
if not errorlevel 1 if exist "%MAVEN_ZIP%" goto :maven_downloaded

echo curl failed or not available. Trying PowerShell...
powershell -ExecutionPolicy Bypass -Command "[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; $ProgressPreference = 'SilentlyContinue'; Invoke-WebRequest -Uri '%MAVEN_URL%' -OutFile '%MAVEN_ZIP%' -UseBasicParsing"
if not errorlevel 1 if exist "%MAVEN_ZIP%" goto :maven_downloaded

echo First URL failed. Trying alternative URL...
set "MAVEN_URL_ALT=https://dlcdn.apache.org/maven/maven-3/%MAVEN_VERSION%/binaries/apache-maven-%MAVEN_VERSION%-bin.zip"

curl -L -o "%MAVEN_ZIP%" "!MAVEN_URL_ALT!" 2>nul
if not errorlevel 1 if exist "%MAVEN_ZIP%" goto :maven_downloaded

powershell -ExecutionPolicy Bypass -Command "[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; $ProgressPreference = 'SilentlyContinue'; Invoke-WebRequest -Uri '!MAVEN_URL_ALT!' -OutFile '%MAVEN_ZIP%' -UseBasicParsing"
if not errorlevel 1 if exist "%MAVEN_ZIP%" goto :maven_downloaded

echo.
echo ERROR: Could not download Maven automatically.
echo Please download Maven manually:
echo   1. Go to https://maven.apache.org/download.cgi
echo   2. Download "apache-maven-%MAVEN_VERSION%-bin.zip"
echo   3. Place the zip file here: "%MAVEN_ZIP%"
echo   4. Run this script again.
echo.
echo OR install Maven system-wide and add it to your PATH.
pause
exit /b 1

:maven_downloaded
echo Download complete. Extracting...

powershell -ExecutionPolicy Bypass -Command "Expand-Archive -Path '%MAVEN_ZIP%' -DestinationPath '%PROJECT_DIR%' -Force"
if errorlevel 1 (
    echo ERROR: Failed to extract Maven zip. Trying tar...
    tar -xf "%MAVEN_ZIP%" -C "%PROJECT_DIR%" 2>nul
    if errorlevel 1 (
        echo ERROR: Could not extract Maven zip.
        echo Please extract "%MAVEN_ZIP%" manually into "%PROJECT_DIR%"
        pause
        exit /b 1
    )
)

REM Clean up the zip file
del /f "%MAVEN_ZIP%" 2>nul

REM Verify extraction worked
if not exist "%MAVEN_DIR%\bin\mvn.cmd" (
    echo ERROR: Extraction succeeded but mvn.cmd not found.
    echo Expected: %MAVEN_DIR%\bin\mvn.cmd
    echo.
    echo Checking what was extracted...
    dir "%PROJECT_DIR%apache-maven*" 2>nul
    pause
    exit /b 1
)

echo Maven extracted successfully to: %MAVEN_DIR%
set "PATH=%MAVEN_DIR%\bin;%PATH%"

:maven_ready

REM =========================================
REM  Step 3: Download and Extract Tomcat
REM =========================================
echo.
echo =========================================
echo  Step 3: Setting up Apache Tomcat %TOMCAT_VERSION%
echo =========================================

if exist "%TOMCAT_DIR%\bin\catalina.bat" (
    echo Tomcat already exists at: %TOMCAT_DIR%
    goto :tomcat_ready
)

echo Tomcat not found locally. Downloading...
echo.

REM Try curl first (built into Windows 10+)
echo Trying download with curl...
curl -L -o "%TOMCAT_ZIP%" "%TOMCAT_URL%" 2>nul
if not errorlevel 1 if exist "%TOMCAT_ZIP%" goto :tomcat_downloaded

echo curl failed. Trying PowerShell...
powershell -ExecutionPolicy Bypass -Command "[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; $ProgressPreference = 'SilentlyContinue'; Invoke-WebRequest -Uri '%TOMCAT_URL%' -OutFile '%TOMCAT_ZIP%' -UseBasicParsing"
if not errorlevel 1 if exist "%TOMCAT_ZIP%" goto :tomcat_downloaded

echo First URL failed. Trying alternative URL...
set "TOMCAT_URL_ALT=https://dlcdn.apache.org/tomcat/tomcat-10/v%TOMCAT_VERSION%/bin/apache-tomcat-%TOMCAT_VERSION%-windows-x64.zip"

curl -L -o "%TOMCAT_ZIP%" "!TOMCAT_URL_ALT!" 2>nul
if not errorlevel 1 if exist "%TOMCAT_ZIP%" goto :tomcat_downloaded

powershell -ExecutionPolicy Bypass -Command "[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; $ProgressPreference = 'SilentlyContinue'; Invoke-WebRequest -Uri '!TOMCAT_URL_ALT!' -OutFile '%TOMCAT_ZIP%' -UseBasicParsing"
if not errorlevel 1 if exist "%TOMCAT_ZIP%" goto :tomcat_downloaded

echo.
echo ERROR: Could not download Tomcat automatically.
echo Please download it manually:
echo   1. Go to https://tomcat.apache.org/download-10.cgi
echo   2. Download the "64-bit Windows zip"
echo   3. Place the zip here: "%TOMCAT_ZIP%"
echo   4. Run this script again.
pause
exit /b 1

:tomcat_downloaded

echo Download complete. Extracting...

powershell -ExecutionPolicy Bypass -Command "Expand-Archive -Path '%TOMCAT_ZIP%' -DestinationPath '%PROJECT_DIR%' -Force"
if errorlevel 1 (
    echo PowerShell extract failed. Trying tar...
    tar -xf "%TOMCAT_ZIP%" -C "%PROJECT_DIR%" 2>nul
    if errorlevel 1 (
        echo ERROR: Could not extract Tomcat zip.
        pause
        exit /b 1
    )
)

REM Clean up the zip file
del /f "%TOMCAT_ZIP%" 2>nul

REM Verify extraction worked
if not exist "%TOMCAT_DIR%\bin\catalina.bat" (
    echo ERROR: Extraction succeeded but catalina.bat not found.
    echo Expected location: %TOMCAT_DIR%\bin\catalina.bat
    echo The zip may have a different folder structure. Please check %PROJECT_DIR% and rename the folder.
    exit /b 1
)

echo Tomcat extracted successfully to: %TOMCAT_DIR%

:tomcat_ready

REM =========================================
REM  Step 4: Make Tomcat scripts executable
REM =========================================
REM On Windows, .bat files are already executable, but we need
REM to ensure the shutdown port isn't conflicting
echo Tomcat is ready at: %TOMCAT_DIR%

REM =========================================
REM  Step 5: Build the project
REM =========================================
echo.
echo =========================================
echo  Step 5: Building the project with Maven
echo =========================================
cd /d "%PROJECT_DIR%"
call mvn clean package
if errorlevel 1 (
    echo.
    echo BUILD FAILED. Fix the errors and try again.
    exit /b 1
)
echo Build successful: target\%WAR_NAME%.war

REM =========================================
REM  Step 6: Stop any running Tomcat
REM =========================================
call "%TOMCAT_DIR%\bin\catalina.bat" stop >nul 2>&1
timeout /t 2 /nobreak >nul

REM =========================================
REM  Step 7: Deploy the WAR file
REM =========================================
echo.
echo =========================================
echo  Step 7: Deploying WAR to Tomcat
echo =========================================

REM Clean old deployment
if exist "%TOMCAT_DIR%\webapps\%WAR_NAME%" rmdir /s /q "%TOMCAT_DIR%\webapps\%WAR_NAME%"
if exist "%TOMCAT_DIR%\webapps\%WAR_NAME%.war" del /f "%TOMCAT_DIR%\webapps\%WAR_NAME%.war"

REM Copy new WAR
copy /y "%PROJECT_DIR%target\%WAR_NAME%.war" "%TOMCAT_DIR%\webapps\" >nul 2>&1
if errorlevel 1 (
    echo ERROR: Failed to copy WAR file to Tomcat webapps.
    exit /b 1
)
echo Deployed %WAR_NAME%.war to Tomcat webapps.

REM =========================================
REM  Step 8: Start Tomcat
REM =========================================
echo.
echo =========================================
echo  Step 8: Starting Tomcat
echo =========================================
call "%TOMCAT_DIR%\bin\catalina.bat" start
if errorlevel 1 (
    echo ERROR: Failed to start Tomcat. Check the logs at:
    echo   %TOMCAT_DIR%\logs\catalina.out
    exit /b 1
)

echo.
echo =========================================
echo  SUCCESS! Tomcat is starting...
echo.
echo  Wait a few seconds, then open:
echo.
echo  App URL:  http://localhost:8080/%WAR_NAME%/hello
echo  Try:      http://localhost:8080/%WAR_NAME%/hello?name=Abiral
echo.
echo  Commands:
echo    .\run.bat stop       Stop Tomcat
echo    .\run.bat restart    Rebuild and restart
echo    .\run.bat clean      Remove Tomcat and build files
echo =========================================
echo.

REM Wait and open browser automatically
timeout /t 5 /nobreak >nul
start "" "http://localhost:8080/%WAR_NAME%/hello"

endlocal
