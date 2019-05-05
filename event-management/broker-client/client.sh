#!/bin/bash
#
# Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
#
# This Source Code Form is subject to the terms of the
# Mozilla Public License, v. 2.0. If a copy of the MPL
# was not distributed with this file, You can obtain one at
# http://mozilla.org/MPL/2.0/.
#

if [ ! -d "target/dependency" ]; then
  mvn dependency:copy-dependencies
fi

MELODIC_CONFIG_DIR=.

JAVA_OPTS=-Djavax.net.ssl.trustStore=./broker-truststore.p12\ -Djavax.net.ssl.trustStorePassword=melodic\ -Djavax.net.ssl.trustStoreType=pkcs12 
# -Djavax.net.debug=all
# -Djavax.net.debug=ssl,handshake,record

java $JAVA_OPTS -classpath "target/classes:target/dependency/*" eu.melodic.event.brokerclient.BrokerClientApp $*
