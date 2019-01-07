#!/bin/bash

# Change directory to Baguette client home
BASEDIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && cd .. && pwd )
PWD=$( pwd )
cd $BASEDIR

MELODIC_CONFIG_DIR=$BASEDIR/conf
PAASAGE_CONFIG_DIR=$BASEDIR/conf
echo "MELODIC_CONFIG_DIR=$MELODIC_CONFIG_DIR"

# Run Baguette client
java -classpath "conf:jars/*" eu.melodic.event.baguette.client.BaguetteClient $*
cd $PWD
