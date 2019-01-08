#!/bin/bash

# Change directory to Baguette client home
BASEDIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
PWD=$( pwd )
cd $BASEDIR

# Run Baguette Server
java -classpath "target/classes:target/dependency/*:conf:jars/*" eu.melodic.event.baguette.server.BaguetteServer $*  >> /logs/baguette-server.log 2>&1
cd $PWD
