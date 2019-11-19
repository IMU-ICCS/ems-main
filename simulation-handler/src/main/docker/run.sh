#!/bin/sh
#
# Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
#
# This Source Code Form is subject to the terms of the Mozilla Public License, v2.0.
# If a copy of the MPL was not distributed with this file, You can obtain one at
# https://www.mozilla.org/en-US/MPL/2.0/
#

keytool -noprompt -storepass changeit -import -alias mule -keystore /usr/lib/jvm/java-1.8-openjdk/jre/lib/security/cacerts -file /config/mule-server.crt
#/config/wait-for-cdo.sh && java -Duser.timezone=Europe/Warsaw -Djava.security.egd=file:/dev/./urandom -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar meta-solver.jar
/config/wait-for-cdo.sh && java  -Djavax.net.ssl.trustStore=/config/common/melodic-truststore.p12 -Djavax.net.ssl.trustStorePassword=melodic -Djavax.net.ssl.trustStoreType=pkcs12 -Duser.timezone=Europe/Warsaw -Djava.security.egd=file:/dev/./urandom -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar simulation-handler.jar
