#!/bin/bash

if [ ! -d "target/dependency" ]; then
  mvn dependency:copy-dependencies
fi

java -classpath "target/classes:target/dependency/*" eu.melodic.upperware.discovery.client.VmsDiscoveryClient $*
