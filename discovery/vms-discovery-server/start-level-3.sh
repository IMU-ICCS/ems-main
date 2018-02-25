#!/bin/bash

echo 'Starting 3rd level Active MQ broker'
cd /opt/activemq/bin/linux-x86-64
sudo sh activemq start 
sleep 2


echo 'Starting Mule engine and 3rd level CEP service'
cd /opt/mule-standalone-3.9.0/bin
sudo ./mule  -M-Dhost.broker="tcp://localhost:61616"  "-M-Dhost.rule=select\ avg\(metricValue\)\ as\ metricValue,\ window\(vmName\)\ as\ vmName,\ cloudName\ as\ cloudName,\ window\(componentName\)\ as\ componentName,\ 3\ as\ level,\ current_timestamp\ as\ timestamp\ \ from\ Visor_event_third.win:time\(60\ sec\)\ where\ \(metricValue\ is\ not\ null\ AND\ vmName\ is\ not\ null\ AND\ componentName\ is\ not\ null\)\ output\ last\ every\ 60\ sec" &
sleep 2


echo "3rd Level started"


echo 'Starting VMS Server'
cd /opt/vms-discovery-server
sudo ./run.sh


