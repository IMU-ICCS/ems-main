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
 if [ "$USECASE" == "FCR" ]
  then
    echo 'Copying Mule App for FCR...'
    cp /opt/2ndLevelEsper2outs_app.zip /opt/mule-standalone-3.9.0/apps
    echo 'Copying Mule App for FCR completed. Starting Mule...'
    ./mule start "-M-Dhost.rule=select\ avg\(metricValue\)\ as\ metricValue,\ window\(vmName\)\ as\ vmName,\ cloudName\ as\ cloudName,\ window\(componentName\)\ as\ componentName,\ 2\ as\ level,\ current_timestamp\ as\ timestamp\ from\ Visor_event_second.win:time\(60\ sec\)\ where\(\ level=1\)\ output\ last\ every\ 60\ sec"  "-M-Dhost.rulealert=select\ metricValue\ as\ metricValue,\ vmName\ as\ vmName,\ cloudName\ as\ cloudName,\ componentName\ as\ componentName,\ 2\ as\ level,\ current_timestamp\ as\ timestamp\ from\ Visor_event_second.win:time\(60\ sec\)\ having\ metricValue\>\100\ output\ last\ every\ 60\ sec"
    echo 'Mule started.'
  elif [ "$USECASE" == "GENOM" ]
  then
    echo 'Copying Mule App for Genom App...'
    cp /opt/CEtrafficmodifiedApp.zip /opt/mule-standalone-3.9.0/apps
    echo 'Copying Mule App for Genom completed. Starting mule...'
    ./mule start "-M-Dhost.rulefirst=select\ ComputeMetricValue\(metricValue,\ 0.95\)\ as\ metricValue,\  vmName\ as\ vmName,\ cloudName\ as\ cloudName,\ componentName\ as\ componentName,\ 2\ as\ level,\ current_timestamp\ as\ timestamp\  from\ Visor_event_second.win:length\(\6\)\ output\ last\ every\ 6\ events" "-M-Dhost.rulesecond=insert\  into\ CombinedEvent\(\metricValue\)\ select\ \(\(\Math.ceil\(\A.metricValue\ /\ D.metricValue\)\ *\ B.metricValue\)\ -\ C.metricValue\)\  as\ metricValue\  from\ Visor_event.win:length_batch\(1\)\ A,\ Visor_event_second.win:length_batch\(1\)\ B,\ Visor_event_third.win:length_batch\(1\)\ C,\ Visor_event_fourth.win:length_batch\(1\)\ D\  output\ last\ every\ 10\ sec" "-M-Dhost.rulethird=insert\ into\ MinimumCoresEvent\(\metricValue\)\ select\ \(\Math.ceil\(\A.metricValue\ /\ \Math.floor\(\C.metricValue\ /\ B.metricValue\)\)\)\  as\ metricValue\  from\ Visor_event.win:length_batch\(1\)\ A,\ Visor_event_second.win:length_batch\(1\)\ B,\ Visor_event_third.win:length_batch\(1\)\ C\  output\ last\ every\ 10\ sec"  "-M-Dhost.rulefourth=select\ *\ from\ Visor_event_fifth.win:time\(20\ sec\)\ having\ metricValue\>\0\  output\ last\ every\ 20\ sec"
    echo 'Mule started.'
  elif [ "$USECASE" == "CETRAFFIC" ]
  then
    echo 'Copying Mule App for CETRAFFIC App...'
    cp /opt/CEtrafficmodifiedApp.zip /opt/mule-standalone-3.9.0/apps
    echo 'Copying Mule App for CETRAFFIC completed. Starting mule...'
    ./mule start "-M-Dhost.rulefirst=select\ ComputeMetricValue\(metricValue,\ 0.95\)\ as\ metricValue,\  vmName\ as\ vmName,\ cloudName\ as\ cloudName,\ componentName\ as\ componentName,\ 2\ as\ level,\ current_timestamp\ as\ timestamp\  from\ Visor_event.win:length\(\20\)\ output\ last\ every\ 10\ events"  "-M-Dhost.rulesecond=insert\  into\ CombinedEvent\(\metricValue\)\ select\ \(\(\Math.ceil\(\B.metricValue\ /\ D.metricValue\)\ *\ A.metricValue\)\ -\ C.metricValue\)\  as\ metricValue\  from\ Visor_event_second.win:length_batch\(1\)\ A,\ Visor_event_third.win:length_batch\(1\)\ B,\ Visor_event_fourth.win:length_batch\(1\)\ C,\ Visor_event_fifth.win:length_batch\(1\)\ D\  output\ last\ every\ 10\ sec" "-M-Dhost.rulethird=insert\ into\ MinimumCoresEvent\(\metricValue\)\ select\ \(\Math.ceil\(\B.metricValue\ /\ \Math.floor\(\C.metricValue\ /\ A.metricValue\)\)\)\  as\ metricValue\  from\ Visor_event_second.win:length_batch\(1\)\ A,\ Visor_event_third.win:length_batch\(1\)\ B,\ Visor_event_fourth.win:length_batch\(1\)\ C\  output\ last\ every\ 10\ sec"  "-M-Dhost.rulefourth=select\ *\ from\ Visor_event_sixth.win:time\(300\ sec\)\ having\ metricValue\>\0\  output\ last\ every\ 300\ sec"
    echo 'Mule started.'
  elif [ "$USECASE" == "CAS" ]
  then
    echo 'Copying Mule App for CAS App...'
    cp /opt/CAS_app.zip /opt/mule-standalone-3.9.0/apps
    echo 'Copying Mule App for CAS completed. Starting mule...'
    ./mule start "-M-Dhost.rulescaleout=select\ avg\(metricValue\)\ as\ metricValue,\ vmName\ as\ vmName,\ cloudName\ as\ cloudName,\ componentName\ as\ componentName,\ 2\ as\ level,\ current_timestamp\ as\ timestamp\ from\ Visor_event.win:time\(60\ sec\)\ having\ avg\(\metricValue\)\>80\ output\ last\ every\ 120\ sec" "-M-Dhost.rulescalein=select\ avg\(metricValue\)\ as\ metricValue,\ vmName\ as\ vmName,\ cloudName\ as\ cloudName,\ componentName\ as\ componentName,\ 2\ as\ level,\ current_timestamp\ as\ timestamp\ from\ Visor_event.win:time\(60\ sec\)\ having\ avg\(\metricValue\)\<\=60\ output\ last\ every\ 120\ sec" "-M-Dhost.ruleram=select\ avg\(metricValue\)\ as\ metricValue,\ vmName\ as\ vmName,\ cloudName\ as\ cloudName,\ componentName\ as\ componentName,\ 2\ as\ level,\ current_timestamp\ as\ timestamp\ from\ Visor_event.win:time\(30\ sec\)\ output\ last\ every\ 30\ sec"
    echo 'Mule started.'
  else
    echo "Unsupported use-case: $USECASE"
  fi

unset JAVA_OPTS
sleep 2


echo "3rd Level started"


echo 'Starting VMS Server'
cd /opt/vms-discovery-server
#sudo 
./run.sh
