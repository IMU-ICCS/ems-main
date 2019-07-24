#!/bin/sh

curl -XPOST 'http://ui-influxdb:8086/query' --data-urlencode 'q=CREATE DATABASE melodic_ui'
java -jar mq-http-adapter.jar
