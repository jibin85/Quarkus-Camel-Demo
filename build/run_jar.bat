@echo off
echo *********************
echo run_jar.bat Begins...
echo *********************

REM Store the original directory
SET "ORIGINAL_DIR=%CD%"
SET "WORK_DIR=D:\HandsOn\quarkus-camel-demo\quarkus-camel-sftp"

REM Debug: Print current directory
echo Current Directory: %CD%

REM Change to the parent directory of 'build'
CD /D "%ORIGINAL_DIR%\.." || (
    echo Error: Failed to change to parent directory
    exit /b 1
)

REM Debug: Print directory after moving up
echo Directory after moving up: %CD%

REM Change to the directories containing the JAR files and set directories
CD /D "%CD%\quarkus-camel-sftp\target" || (
    echo Error: Failed to change directory to quarkus-camel-sftp/target
    exit /b 1
)
SET "quarkus_camel_sftp=%CD%"

REM Change back to parent directory before next change
CD /D "%ORIGINAL_DIR%\.."

CD /D "%CD%\quarkus-rest-service-6060\target" || (
    echo Error: Failed to change directory to quarkus-rest-service-6060/target
    exit /b 1
)
SET "quarkus_rest_service_6060=%CD%"

REM Change back to parent directory before next change
CD /D "%ORIGINAL_DIR%\.."

CD /D "%CD%\quarkus-rest-service-7070\target" || (
    echo Error: Failed to change directory to quarkus-rest-service-7070/target
    exit /b 1
)
SET "quarkus_rest_service_7070=%CD%"

REM Change back to parent directory before next change
CD /D "%ORIGINAL_DIR%\.."

CD /D "%CD%\quarkus-rest-service-9090\target" || (
    echo Error: Failed to change directory to quarkus-rest-service-9090/target
    exit /b 1
)
SET "quarkus_rest_service_9090=%CD%"

REM Add email integration module directory
CD /D "%ORIGINAL_DIR%\.."

CD /D "%CD%\quarkus-email-integration\target" || (
    echo Error: Failed to change directory to quarkus-email-integration/target
    exit /b 1
)
SET "quarkus_email_integration=%CD%"

REM Change back to parent directory before next change
CD /D "%ORIGINAL_DIR%\.."

CD /D "%CD%\quarkus-soap-integration\target" || (
    echo Error: Failed to change directory to quarkus-soap-integration/target
    exit /b 1
)
SET "quarkus-soap-integration=%CD%"

REM Change back to the original directory before running the JAR files
CD /D "%ORIGINAL_DIR%" || (
    echo Error: Failed to return to original directory
    exit /b 1
)

REM Check if the JAR files exist before trying to run them
if not exist "%quarkus_camel_sftp%\quarkus-app\quarkus-run.jar" (
    echo Error: JAR file not found: %quarkus_camel_sftp%\quarkus-app\quarkus-run.jar
    exit /b 1
)

if not exist "%quarkus_rest_service_6060%\quarkus-app\quarkus-run.jar" (
    echo Error: JAR file not found: %quarkus_rest_service_6060%\quarkus-app\quarkus-run.jar
    exit /b 1
)

if not exist "%quarkus_rest_service_7070%\quarkus-app\quarkus-run.jar" (
    echo Error: JAR file not found: %quarkus_rest_service_7070%\quarkus-app\quarkus-run.jar
    exit /b 1
)

if not exist "%quarkus_rest_service_9090%\quarkus-app\quarkus-run.jar" (
    echo Error: JAR file not found: %quarkus_rest_service_9090%\quarkus-app\quarkus-run.jar
    exit /b 1
)

if not exist "%quarkus_email_integration%\quarkus-app\quarkus-run.jar" (
    echo Error: JAR file not found: %quarkus_email_integration%\quarkus-app\quarkus-run.jar
    exit /b 1
)

if not exist "%quarkus-soap-integration%\quarkus-app\quarkus-run.jar" (
    echo Error: JAR file not found: %quarkus-soap-integration%\quarkus-app\quarkus-run.jar
    exit /b 1
)

REM Create necessary directories if they don't exist
mkdir "%WORK_DIR%\src\main\resources\localIdempotetnRepository" 2>nul
echo Created/Verified idempotent repository directory

REM Run the JAR files with working directory set
START "quarkus-camel-sftp"        CMD /c "cd /d "%WORK_DIR%" && java -Duser.dir="%WORK_DIR%" -jar "%quarkus_camel_sftp%\quarkus-app\quarkus-run.jar""
START "quarkus-rest-service-6060" CMD /c java -jar "%quarkus_rest_service_6060%\quarkus-app\quarkus-run.jar"
START "quarkus-rest-service-7070" CMD /c java -jar "%quarkus_rest_service_7070%\quarkus-app\quarkus-run.jar"
START "quarkus-rest-service-9090" CMD /c java -jar "%quarkus_rest_service_9090%\quarkus-app\quarkus-run.jar"
START "quarkus-email-integration" CMD /c java -jar "%quarkus_email_integration%\quarkus-app\quarkus-run.jar"
START "quarkus-soap-integration"  CMD /c java -jar "%quarkus-soap-integration%\quarkus-app\quarkus-run.jar"

REM Check the error level of the last command
if %ERRORLEVEL% neq 0 (
    echo Error: JAR file execution failed with error code %ERRORLEVEL%.
    exit /b %ERRORLEVEL%
)

REM Return to the original directory
CD /D "%ORIGINAL_DIR%" || (
    echo Error: Failed to return to original directory
    exit /b 1
)

echo *********************
echo run_jar.bat Completed
echo *********************