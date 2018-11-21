@echo off
curl -X POST http://localhost:8111/monitors -H "Content-Type: application/json" -d "{ \"applicationId\" : \"/mytest2\", \"watermark\" : { \"user\" : \"ESB\", \"system\" : \"ESB\", \"date\" : 1247218648, \"uuid\": \"222222222222\" } }"
