@echo off
echo *********************
echo stop.bat Begins...
echo *********************

echo Stopping all Quarkus services...

REM Kill processes by their window titles
echo Stopping by window titles...
taskkill /FI "WindowTitle eq quarkus-camel-sftp" /F
taskkill /FI "WindowTitle eq quarkus-rest-service-6060" /F
taskkill /FI "WindowTitle eq quarkus-rest-service-7070" /F
taskkill /FI "WindowTitle eq quarkus-rest-service-9090" /F
taskkill /FI "WindowTitle eq quarkus-email-integration" /F
taskkill /FI "WindowTitle eq quarkus-soap-integration" /F
taskkill /FI "WindowTitle eq quarkus-swagger-integration" /F

REM Kill by specific port numbers
echo.
echo Stopping processes on specific ports...
FOR /F "tokens=5" %%P IN ('netstat -ano ^| findstr ":6060 "') DO taskkill /F /PID %%P
FOR /F "tokens=5" %%P IN ('netstat -ano ^| findstr ":7070 "') DO taskkill /F /PID %%P
FOR /F "tokens=5" %%P IN ('netstat -ano ^| findstr ":9090 "') DO taskkill /F /PID %%P
FOR /F "tokens=5" %%P IN ('netstat -ano ^| findstr ":8080 "') DO taskkill /F /PID %%P
FOR /F "tokens=5" %%P IN ('netstat -ano ^| findstr ":7272 "') DO taskkill /F /PID %%P
FOR /F "tokens=5" %%P IN ('netstat -ano ^| findstr ":9090 "') DO taskkill /F /PID %%P
FOR /F "tokens=5" %%P IN ('netstat -ano ^| findstr ":9393 "') DO taskkill /F /PID %%P
FOR /F "tokens=5" %%P IN ('netstat -ano ^| findstr ":8686 "') DO taskkill /F /PID %%P

REM Kill any remaining Java processes containing "quarkus" in their command line
echo.
echo Stopping any remaining Quarkus processes...
FOR /F "tokens=2 delims=," %%P IN ('wmic process where "commandline like '%%quarkus%%' OR commandline like '%%email-integration%%'" get processid /format:csv') DO (
    IF NOT "%%P"=="" IF NOT "%%P"=="ProcessId" (
        echo Stopping process %%P
        taskkill /F /PID %%P
    )
)

REM Kill any java.exe processes running quarkus-run.jar
echo.
echo Stopping any remaining Java processes running quarkus-run.jar...
FOR /F "tokens=2 delims=," %%P IN ('wmic process where "commandline like '%%quarkus-run.jar%%'" get processid /format:csv') DO (
    IF NOT "%%P"=="" IF NOT "%%P"=="ProcessId" (
        echo Stopping process %%P
        taskkill /F /PID %%P
    )
)

REM Final check for any lingering CMD windows related to Quarkus
echo.
echo Stopping any remaining command windows...
taskkill /F /FI "WINDOWTITLE eq C:\Windows\system32\cmd.exe - java *" /T

REM Clear any error messages from the last command since it's just a final safety check
cls

echo.
echo *********************
echo Cleanup completed
echo *********************
echo.
echo If you still see any Quarkus-related processes, here they are:
echo.
wmic process where "commandline like '%%quarkus%%' OR commandline like '%%email-integration%%'" get processid, commandline
echo.
echo You can manually kill a specific process using: taskkill /F /PID ProcessID
echo.

pause