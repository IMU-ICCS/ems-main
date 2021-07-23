@echo off
curl -k --request POST "http%1://localhost:8092/updateSolution" --header "Content-Type: application/json" --data "@bin\rest\2_update_solution_payload.txt"