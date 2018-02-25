#!/bin/bash

# Change directory to VMS client home
BASEDIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
PWD=$( pwd )
cd $BASEDIR

# Download dependencies if needed
if [ ! -d "target/dependency" ]; then
  mvn dependency:copy-dependencies
fi

# Append VMS server credentials to client.properties file
VMSCRED=`cat target/classes/client.properties | grep username | wc -l`
#echo $VMSCRED
if [ $VMSCRED == '0' ]; then
  echo 'Missing VMS Discovery Server credentials...'
  sudo /bin/bash -c "cat ../vms-server.credentials >> target/classes/client.properties"
  echo 'Credentials appended to target/classes/client.properties'
fi

# Run VMS client
java -classpath "target/classes:target/dependency/*" eu.melodic.upperware.discovery.client.VmsDiscoveryClient $*
cd $PWD
