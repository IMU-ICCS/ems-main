#!/usr/bin/env bash
#
# Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
#
# This Source Code Form is subject to the terms of the
# Mozilla Public License, v. 2.0. If a copy of the MPL
# was not distributed with this file, You can obtain one at
# http://mozilla.org/MPL/2.0/.
#

PREVWORKDIR=`pwd`
BASEDIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && cd .. && pwd )
cd ${BASEDIR}
if [[ -z $MELODIC_CONFIG_DIR ]]; then MELODIC_CONFIG_DIR=${BASEDIR}/config-files; export MELODIC_CONFIG_DIR; fi
if [[ -z $PAASAGE_CONFIG_DIR ]]; then PAASAGE_CONFIG_DIR=${BASEDIR}/config-files; export PAASAGE_CONFIG_DIR; fi

# Copy dependencies if missing
if [[ -f ${BASEDIR}/control-service/pom.xml ]]; then
    if [[ ! -d ${BASEDIR}/control-service/target/dependency ]]; then
        cd ${BASEDIR}/control-service
        mvn dependency:copy-dependencies
        cd ${BASEDIR}
    fi
fi

java -classpath "control-service/target/classes;control-service/target/dependency/*" eu.melodic.event.control.util.CpModelHelper $*
# Usage: cp2cdo <file> <cdo-resource>

cd ${PREVWORKDIR}
