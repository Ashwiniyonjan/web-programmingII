@echo off

call mvn clean package
if %ERRORLEVEL% neq 0 (
    echo Build failed!
    pause
    exit /b 1
)

copy /Y "target\SpringMvcHelloWorld.war" "C:\Users\ashwi\OneDrive\Documents\SpringMvcHelloWorld1\apache-tomcat-10.1.52\webapps\SpringMvcHelloWorld.war"


