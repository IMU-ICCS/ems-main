@echo off
curl -k --request POST "http%1://localhost:8111/baguette/registerNode" --header "Content-Type: application/json" --data "@reqs\3_install_ems_client_payload.txt"
