#!/usr/bin/env bash

#BASEDIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
#MELODIC_CONFIG_DIR=/config
#PAASAGE_CONFIG_DIR=/config

#export BASEDIR MELODIC_CONFIG_DIR PAASAGE_CONFIG_DIR

$MELODIC_CONFIG_DIR/wait-for-cdo.sh && java -Duser.timezone=Europe/Warsaw -Djava.security.egd=file:/dev/./urandom -Dloader.path=esper-7.1.0.jar -cp control-service.jar org.springframework.boot.loader.PropertiesLauncher --logging.config=$MELODIC_CONFIG_DIR/logback-conf/logback-ems.xml