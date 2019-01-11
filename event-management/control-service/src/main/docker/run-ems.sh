#!/usr/bin/env bash

BASEDIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
MELODIC_CONFIG_DIR=$BASEDIR/config
PAASAGE_CONFIG_DIR=$BASEDIR/config

export BASEDIR MELODIC_CONFIG_DIR PAASAGE_CONFIG_DIR

java -Duser.timezone=Europe/Warsaw -Djava.security.egd=file:/dev/./urandom -Dloader.path=$BASEDIR/esper-7.1.0.jar -cp $BASEDIR/control-service.jar org.springframework.boot.loader.PropertiesLauncher --logging.config=$MELODIC_CONFIG_DIR/logback-spring.xml
