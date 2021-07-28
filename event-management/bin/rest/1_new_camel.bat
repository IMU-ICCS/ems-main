@echo off
curl -v -k --request POST "http%1://localhost:8111/camelModel" --header "Content-Type: application/json" --data "@reqs\1_new_camel_payload.txt"