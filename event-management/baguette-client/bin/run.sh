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

MELODIC_CONFIG_DIR=$BASEDIR/conf
PAASAGE_CONFIG_DIR=$BASEDIR/conf
echo "MELODIC_CONFIG_DIR=$MELODIC_CONFIG_DIR"

export MELODIC_CONFIG_DIR PAASAGE_CONFIG_DIR BASEDIR

# Run Baguette client
java -classpath "conf:jars/*" eu.melodic.event.baguette.client.BaguetteClient $*
cd $PWD
