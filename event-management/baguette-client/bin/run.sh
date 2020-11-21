#!/bin/bash
#
# Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
#
# This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
# Esper library is used, in which case it is subject to the terms of General Public License v2.0.
# If a copy of the MPL was not distributed with this file, you can obtain one at
# https://www.mozilla.org/en-US/MPL/2.0/
#

# Change directory to Baguette client home
PREVWORKDIR=`pwd`
BASEDIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && cd .. && pwd )
cd ${BASEDIR}
MELODIC_CONFIG_DIR=${BASEDIR}/conf
PAASAGE_CONFIG_DIR=${BASEDIR}/conf
LOG_FILE=${BASEDIR}/logs/output.txt
TEE_FILE=${BASEDIR}/logs/tee.txt
JASYPT_PASSWORD=melodic
export MELODIC_CONFIG_DIR PAASAGE_CONFIG_DIR LOG_FILE JASYPT_PASSWORD

# Update path
PATH=$PATH:/opt/cloudiator/jre8/bin/

# Check if baguette client is already running
#PID=`jps | grep BaguetteClient | cut -d " " -f 1`
PID=`ps -ef |grep java |grep BaguetteClient | cut -c 10-14`
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

# Run Baguette client
JAVA_OPTS=-Djavax.net.ssl.trustStore=${MELODIC_CONFIG_DIR}/client-broker-truststore.p12
JAVA_OPTS="${JAVA_OPTS} -Djavax.net.ssl.trustStorePassword=melodic -Djavax.net.ssl.trustStoreType=pkcs12"
JAVA_OPTS="${JAVA_OPTS} -Djasypt.encryptor.password=$JASYPT_PASSWORD"
#JAVA_OPTS="-Djavax.net.debug=all ${JAVA_OPTS}"

echo "Starting baguette client..."
echo "MELODIC_CONFIG_DIR=${MELODIC_CONFIG_DIR}"
echo "LOG_FILE=${LOG_FILE}"

echo "Starting baguette client..." &>> ${LOG_FILE}
echo "MELODIC_CONFIG_DIR=${MELODIC_CONFIG_DIR}" &>> ${LOG_FILE}
echo "LOG_FILE=${LOG_FILE}" &>> ${LOG_FILE}

if [ "$1" == "--i" ]; then
  echo "Baguette client running in Interactive mode"
  java ${JAVA_OPTS} -classpath "conf:jars/*:target/classes:target/dependency/*" eu.melodic.event.baguette.client.BaguetteClient $* 2>&1 | tee ${TEE_FILE}
else
  java ${JAVA_OPTS} -classpath "conf:jars/*:target/classes:target/dependency/*" eu.melodic.event.baguette.client.BaguetteClient $* &>> ${LOG_FILE} &
  PID=`jps | grep BaguetteClient | cut -d " " -f 1`
  PID=`ps -ef |grep java |grep BaguetteClient | cut -c 10-14`
  echo "Baguette client PID: $PID"
fi

cd $PREVWORKDIR