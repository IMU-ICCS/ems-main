#!/bin/bash
#
# Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
#
# This Source Code Form is subject to the terms of the
# Mozilla Public License, v. 2.0. If a copy of the MPL
# was not distributed with this file, You can obtain one at
# http://mozilla.org/MPL/2.0/.
#

# Change directory to Baguette client home
BASEDIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
PWD=$( pwd )
cd $BASEDIR

# Append Baguette server credentials to client.properties file
if [[ -f 'target/classes/client.properties' ]]; then
	BAGUETTE_CRED=`cat target/classes/client.properties | grep username | wc -l`
	#echo $BAGUETTE_CRED
	if [ $BAGUETTE_CRED == '0' ]; then
	  echo 'Missing Baguette Server credentials...'
	  #sudo 
	  /bin/bash -c "cat target/classes/baguette-server.credentials >> target/classes/client.properties"
	  echo 'Credentials appended to target/classes/client.properties'
	fi
fi
if [[ -f 'conf/client.properties' ]]; then
	BAGUETTE_CRED=`cat conf/client.properties | grep username | wc -l`
	#echo $BAGUETTE_CRED
	if [ $BAGUETTE_CRED == '0' ]; then
	  echo 'Missing Baguette Server credentials...'
	  #sudo 
	  /bin/bash -c "cat conf/baguette-server.credentials >> conf/client.properties"
	  echo 'Credentials appended to conf/client.properties'
	fi
fi

# Run Baguette client
java -classpath "target/classes:target/dependency/*:conf:jars/*" eu.melodic.event.baguette.client.BaguetteClient $*
cd $PWD
