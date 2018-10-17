#!/bin/bash

echo 'Starting 3rd level Active MQ broker'
cd /opt/activemq/bin/linux-x86-64
sh activemq start
sleep 2

echo 'Starting Mule engine and 3rd level CEP service'
cd /opt/mule/bin/mule
/opt/mule/bin/mule -M-Duser.timezone=Europe/Warsaw -M-Dhost.rulefirst="select\ ComputeMetricValue\(metricValue,\ 0.95\)\ as\ metricValue,\  vmName\ as\ vmName,\ cloudName\ as\ cloudName,\ componentName\ as\ componentName,\ 2\ as\ level,\ current_timestamp\ as\ timestamp\  from\ Visor_event.win:length\(\20\)\ output\ last\ every\ 20\ events"  -M-Dhost.rulesecond="insert\  into\ CombinedEvent\(\metricValue\)\ select\ \(\(\Math.ceil\(\B.metricValue\ /\ D.metricValue\)\ *\ A.metricValue\)\ -\ C.metricValue\)\  as\ metricValue\  from\ Visor_event_second.win:length_batch\(1\)\ A,\ Visor_event_third.win:length_batch\(1\)\ B,\ Visor_event_fourth.win:length_batch\(1\)\ C,\ Visor_event_fifth.win:length_batch\(1\)\ D\  output\ last\ every\ 10\ sec"  -M-Dhost.rulethird="insert\ into\ MinimumCoresEvent\(\metricValue\)\ select\ \(\Math.ceil\(\B.metricValue\ /\ \Math.floor\(\C.metricValue\ /\ A.metricValue\)\)\)\  as\ metricValue\  from\ Visor_event_second.win:length_batch\(1\)\ A,\ Visor_event_third.win:length_batch\(1\)\ B,\ Visor_event_fourth.win:length_batch\(1\)\ C\  output\ last\ every\ 10\ sec"  -M-Dhost.rulefourth="select\ *\ from\ Visor_event_sixth.win:time\(300\ sec\)\ having\ metricValue\>\0\  output\ last\ every\ 300\ sec"




