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
BASEDIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && cd .. && pwd )
PWD=$( pwd )
cd $BASEDIR

# Update path
PATH=$PATH:/opt/cloudiator/jre8/bin/

# Check if baguette client is already running
PID=`jps | grep BaguetteClient | cut -d " " -f 1`
if [ "$PID" != "" ]
then
    echo "Baguette client is already running (pid: $PID)"
    exit 0
fi

# Setting necessary environment variables
MELODIC_CONFIG_DIR=$BASEDIR/conf
PAASAGE_CONFIG_DIR=$BASEDIR/conf
echo "MELODIC_CONFIG_DIR=$MELODIC_CONFIG_DIR"

export MELODIC_CONFIG_DIR PAASAGE_CONFIG_DIR BASEDIR

# Run Baguette client
echo "Starting baguette client..."
java -classpath "conf:jars/*" eu.melodic.event.baguette.client.BaguetteClient $*
PID=`jps | grep BaguetteClient | cut -d " " -f 1`
echo "Baguette client PID: $PID"
cd $PWD
