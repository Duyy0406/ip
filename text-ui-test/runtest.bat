@ECHO OFF

REM create bin directory if it doesn't exist
if not exist ..\bin mkdir ..\bin

REM delete output from previous run
if exist ACTUAL.TXT del ACTUAL.TXT

REM create a temporary file with all java files
dir /s /B ..\src\main\java\*.java > sources.txt

REM compile the code into the bin folder
javac -Xlint:none -d ..\bin @sources.txt
IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
    del sources.txt
    exit /b 1
)
REM no error here, errorlevel == 0

REM clean up sources file
del sources.txt

REM run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ..\bin snow.model.Snow < input.txt > ACTUAL.TXT

REM compare the output to the expected output
FC ACTUAL.TXT EXPECTED.TXT > nul
IF ERRORLEVEL 1 (
    echo Test result: FAILED
    FC ACTUAL.TXT EXPECTED.TXT
    exit /b 1
) ELSE (
    echo Test result: PASSED
    exit /b 0
)
