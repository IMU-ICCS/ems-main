#!/bin/bash
#
# Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
#
# This Source Code Form is subject to the terms of the
# Mozilla Public License, v. 2.0. If a copy of the MPL
# was not distributed with this file, You can obtain one at
# http://mozilla.org/MPL/2.0/.
#

# Get Baguette client home directory
BASEDIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && cd .. && pwd )

# Update path
PATH=$PATH:/opt/cloudiator/jre8/bin/

# Kill Baguette client
PID=`jps | grep BaguetteClient | cut -d " " -f 1`
if [ "$PID" != "" ]
then
	echo "Killing baguette client (pid: $PID)"
	kill -9 $PID
else
	echo "Baguette client is not running"
fi
