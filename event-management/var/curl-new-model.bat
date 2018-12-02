@echo off
curl -X POST http://localhost:8111/camelModelJson -H "Content-Type: application/json" -d "{ 'camel-model-id': '/camel-new' }"
