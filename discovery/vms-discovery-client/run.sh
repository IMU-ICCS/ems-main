#!/bin/bash

# Change directory to VMS client home
BASEDIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
PWD=$( pwd )
cd $BASEDIR

# Append VMS server credentials to client.properties file
if [[ -f 'target/classes/client.properties' ]]; then
	VMSCRED=`cat target/classes/client.properties | grep username | wc -l`
	#echo $VMSCRED
	if [ $VMSCRED == '0' ]; then
	  echo 'Missing VMS Discovery Server credentials...'
	  sudo /bin/bash -c "cat ../vms-server.credentials >> target/classes/client.properties"
	  echo 'Credentials appended to target/classes/client.properties'
	fi
fi
if [[ -f 'conf/client.properties' ]]; then
	VMSCRED=`cat conf/client.properties | grep username | wc -l`
	#echo $VMSCRED
	if [ $VMSCRED == '0' ]; then
	  echo 'Missing VMS Discovery Server credentials...'
	  sudo /bin/bash -c "cat ../vms-server.credentials >> conf/client.properties"
	  echo 'Credentials appended to conf/client.properties'
	fi
fi

# Run VMS client
java -classpath "target/classes:target/dependency/*:conf:jars/*" eu.melodic.upperware.discovery.client.VmsDiscoveryClient $*
cd $PWD
