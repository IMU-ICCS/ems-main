#!/bin/sh

curl -XPOST 'http://ui-influxdb:8086/query' --data-urlencode 'q=CREATE DATABASE melodic_ui'

sleep 30

curl --user admin:admin -XPUT -H "Content-Type: application/json" -d '{
  "oldPassword": "admin",
  "newPassword": "melodic",
  "confirmNew": "melodic"
}' http://ui-grafana:3000/api/user/password

sleep 5

curl --user admin:melodic -i -XPOST -H "Content-Type: application/json" "http://ui-grafana:3000/api/datasources" -d '
{
        "name": "InfluxDB",
        "type": "influxdb",
        "url": "http://ui-influxdb:8086",
        "database": "melodic_ui",
        "isDefault": true,
        "access": "proxy"
}'


java -jar mq-http-adapter.jar -Dagentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=1986
