#!/bin/sh

curl -XPOST 'http://ui-influxdb:8086/query' --data-urlencode 'q=CREATE DATABASE melodic_ui'

curl --user admin:admin -i -XPOST -H "Content-Type: application/json" "http://ui-grafana:3000/api/datasources" -d '
{
        "name": "InfluxDB",
        "type": "influxdb",
        "url": "http://ui-influxdb:8086",
        "database": "melodic_ui",
        "isDefault": true,
        "access": "proxy"
}'


java -jar mq-http-adapter.jar
