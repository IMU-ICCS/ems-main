#!/bin/bash

echo 'start local AMQ broker'
cd /opt/activemq/bin/linux-x86-64
#sudo 
sh activemq start 
sleep 2


cd /opt/mule-standalone-3.9.0/bin

echo 'deploy and start the mule app service with CEP dynamic rule command giving parameter for broker URL and CEP rule'
#sudo ./mule start -M-Dhost.broker="tcp://BROKER_IP_ADDR:BROKER_PORT"  "-M-Dhost.rule=select\ avg\(metricValue\)\ as\ metricValue,\ window\(vmName\)\ as\ vmName,\ cloudName\ as\ cloudName,\ window\(componentName\)\ as\ componentName,\ 1\ as\ level,\ current_timestamp\ as\ timestamp\ \ from\ Visor_event_third.win:time\(60\ sec\)\ where\ \(metricValue\ is\ not\ null\ AND\ vmName\ is\ not\ null\ AND\ componentName\ is\ not\ null\)\ output\ last\ every\ 60\ sec"
#sudo 
./mule start -M-Dhost.broker="tcp://BROKER_IP_ADDR:BROKER_PORT"  "-M-Dhost.rule=select\ avg\(metricValue\)\ as\ metricValue,\ vmName\ as\ vmName,\ cloudName\ as\ cloudName,\ componentName\ as\ componentName,\ 1\ as\ level,\ current_timestamp\ as\ timestamp\ from\ Visor_event_second.win:time\(60\ sec\)\ where\ metricValue\ is\ not\ null\ output\ last\ every\ 60\ sec"
sleep 2


echo "Ready"

