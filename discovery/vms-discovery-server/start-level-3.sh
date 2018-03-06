#!/bin/bash

echo 'Starting 3rd level Active MQ broker'
cd /opt/activemq/bin/linux-x86-64
#sudo 
sh activemq start 
sleep 2


echo 'Starting Mule engine and 3rd level CEP service'
cd /opt/mule-standalone-3.9.0/bin
JAVA_OPTS="-Xms256m -Xmx1024m -XX:PermSize=512m -XX:MaxPermSize=512m"
#sudo 
./mule start "-M-Dhost.rule=select\ avg\(metricValue\)\ as\ metricValue,\ window\(vmName\)\ as\ vmName,\ cloudName\ as\ cloudName,\ window\(componentName\)\ as\ componentName,\ 2\ as\ level,\ current_timestamp\ as\ timestamp\ from\ Visor_event_second.win:time\(60\ sec\)\ where\(\ level=1\)\ output\ last\ every\ 60\ sec"  "-M-Dhost.rulealert=select\ metricValue\ as\ metricValue,\ vmName\ as\ vmName,\ cloudName\ as\ cloudName,\ componentName\ as\ componentName,\ 2\ as\ level,\ current_timestamp\ as\ timestamp\ from\ Visor_event_second.win:time\(60\ sec\)\ having\ metricValue\>\10\ output\ last\ every\ 60\ sec"
unset JAVA_OPTS
sleep 2


echo "3rd Level started"


echo 'Starting VMS Server'
cd /opt/vms-discovery-server
#sudo 
./run.sh
