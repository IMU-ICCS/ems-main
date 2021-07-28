@echo off
set VM=%1
set PAYLOAD={ 'id': '%VM%', 'name': '%VM%', 'operatingSystem': 'UBUNTU', 'address': '%VM%', 'ssh': { 'port': '22', 'username': 'ubuntu', 'password': 'ubuntu' }, 'type': 'VM', 'provider': 'AWS' }
curl -v --request POST "http://localhost:8111/baguette/registerNode" --header "Content-Type: application/json" --data "%PAYLOAD%"
