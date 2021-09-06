@echo off
setlocal
set s=
if not "%1x"=="x" if "%1x"=="sx" set s=s
if not "%2x"=="x" if not "%2x"=="-x" set token=--header "Authorization: Bearer %2"
curl -v -k --request POST "http%s%://localhost:8092/updateSolution" %token% --header "Content-Type: application/json" --data "@bin\rest\2_update_solution_payload.txt"
endlocal