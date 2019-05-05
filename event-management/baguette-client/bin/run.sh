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
PREVWORKDIR=`pwd`
BASEDIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && cd .. && pwd )
cd ${BASEDIR}
MELODIC_CONFIG_DIR=${BASEDIR}/conf
PAASAGE_CONFIG_DIR=${BASEDIR}/conf
export MELODIC_CONFIG_DIR PAASAGE_CONFIG_DIR

# Update path
PATH=$PATH:/opt/cloudiator/jre8/bin/

# Check if baguette client is already running
PID=`jps | grep BaguetteClient | cut -d " " -f 1`
if [ "$PID" != "" ]
then
    echo "Baguette client is already running (pid: $PID)"
    exit 0
fi

# Copy dependencies if missing
if [ -f pom.xml ]; then
	if [ ! -d ${BASEDIR}/target/dependency ]; then
		mvn dependency:copy-dependencies
	fi
fi

# Initialize keystores and certificate
./bin/initialize-keystores.sh

# Run Baguette client
JAVA_OPTS=-Djavax.net.ssl.trustStore=${MELODIC_CONFIG_DIR}/client-broker-truststore.p12
JAVA_OPTS="${JAVA_OPTS} -Djavax.net.ssl.trustStorePassword=melodic -Djavax.net.ssl.trustStoreType=pkcs12"
#JAVA_OPTS="-Djavax.net.debug=all ${JAVA_OPTS}"

echo "MELODIC_CONFIG_DIR=${MELODIC_CONFIG_DIR}"
echo "Starting baguette client..."
java ${JAVA_OPTS} -classpath "conf:jars/*:target/classes:target/dependency/*" eu.melodic.event.baguette.client.BaguetteClient $* &
PID=`jps | grep BaguetteClient | cut -d " " -f 1`
echo "Baguette client PID: $PID"

cd $PREVWORKDIR