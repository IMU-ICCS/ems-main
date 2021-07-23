@echo off
curl -k --request POST "http%1://localhost:8111/cpModelJson" --header "Content-Type: application/json" --data "@bin\rest\2b_update_solution_payload.txt"