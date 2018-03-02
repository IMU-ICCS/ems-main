#!/bin/bash

echo 'Stopping Mule engine and 3rd level CEP service'
cd /opt/mule-standalone-3.9.0/bin
#sudo 
./mule stop 
sleep 2


echo 'Stopping 3rd level Active MQ broker'
cd /opt/activemq/bin/linux-x86-64
#sudo 
sh activemq stop
sleep 2


echo "3rd Level stopped"


echo 'Stopping VMS Server'
cd /opt/vms-discovery-server
echo "WARN: Stopping VMS server is NOT IMPLEMENTED"

