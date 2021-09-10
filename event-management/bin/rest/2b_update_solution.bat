@echo off
setlocal
set s=
if not "%1x"=="x" if "%1x"=="sx" set s=s
if not "%2x"=="x" if not "%2x"=="-x" set apikey=--header "EMS-API-KEY: %2"
if not "%3x"=="x" if not "%3x"=="-x" set token=--header "Authorization: Bearer %3"
curl -v -k --request POST "http%s%://localhost:8111/cpModelJson" %apikey% %token% --header "Content-Type: application/json" --data "@bin\rest\2b_update_solution_payload.txt"
endlocal