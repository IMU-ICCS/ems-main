#!/usr/bin/env bash
#
# Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
#
# This Source Code Form is subject to the terms of the
# Mozilla Public License, v. 2.0. If a copy of the MPL
# was not distributed with this file, You can obtain one at
# http://mozilla.org/MPL/2.0/.
#

BASEDIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
MELODIC_CONFIG_DIR=$BASEDIR/config-files
PAASAGE_CONFIG_DIR=$BASEDIR/config-files
export MELODIC_CONFIG_DIR PAASAGE_CONFIG_DIR

# Get IP addresses
echo Resolving Public and Default IP addresses...
PUBLIC_IP=`java -jar util/target/util-2.1.0-SNAPSHOT-jar-with-dependencies.jar -nolog public`
DEFAULT_IP=`java -jar util/target/util-2.1.0-SNAPSHOT-jar-with-dependencies.jar -nolog default`

if [[ "${PUBLIC_IP}" == "" || "${PUBLIC_IP}" == "null" ]]; then
    PUBLIC_IP=127.0.0.1
fi
if [[ "${DEFAULT_IP}" == "" || "${DEFAULT_IP}" == "null" ]]; then
    DEFAULT_IP=127.0.0.1
fi

echo PUBLIC_IP=${PUBLIC_IP}
echo DEFAULT_IP=${DEFAULT_IP}

# Keystore initialization settings
KEY_GEN_ALG=RSA
KEY_SIZE=2048
KEY_ALIAS=ems
START_DATE=-1d
VALIDITY=3650
DN="CN=ems,OU=Information Management Unit (IMU),O=Institute of Communication and Computer Systems (ICCS),L=Athens,ST=Attika,C=GR"
EXT_SAN="SAN=dns:localhost,ip:127.0.0.1,ip:${DEFAULT_IP},ip:${PUBLIC_IP}"
KEYSTORE=${MELODIC_CONFIG_DIR}/broker-keystore.p12
TRUSTSTORE=${MELODIC_CONFIG_DIR}/broker-truststore.p12
CERTIFICATE=${MELODIC_CONFIG_DIR}/broker.crt
KEYSTORE_TYPE=PKCS12
KEYSTORE_PASS=melodic

# Keystores initialization
echo Generating key pair and certificate...
keytool -delete -alias ${KEY_ALIAS} -keystore ${KEYSTORE} -storetype ${KEYSTORE_TYPE} -storepass ${KEYSTORE_PASS} &> /dev/null
keytool -genkey -keyalg ${KEY_GEN_ALG} -keysize ${KEY_SIZE} -alias ${KEY_ALIAS} -startdate ${START_DATE} -validity ${VALIDITY} -dname "${DN}" -ext "${EXT_SAN}" -keystore ${KEYSTORE} -storetype ${KEYSTORE_TYPE} -storepass ${KEYSTORE_PASS}

echo Exporting certificate to file...
rm -rf ${CERTIFICATE} &> /dev/null
keytool -export -alias ${KEY_ALIAS} -file ${CERTIFICATE} -keystore ${KEYSTORE} -storetype ${KEYSTORE_TYPE} -storepass ${KEYSTORE_PASS}

echo Importing certificate to trust store...
keytool -delete -alias ${KEY_ALIAS} -keystore ${TRUSTSTORE} -storetype ${KEYSTORE_TYPE} -storepass ${KEYSTORE_PASS} &> /dev/null
keytool -import -noprompt -file ${CERTIFICATE} -alias ${KEY_ALIAS} -keystore ${TRUSTSTORE} -storetype ${KEYSTORE_TYPE} -storepass ${KEYSTORE_PASS}

echo Key store, trust stores and certificate are ready.
