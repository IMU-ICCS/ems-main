@echo off

if not exist target\dependency cmd /C "mvn dependency:copy-dependencies"

java -classpath "target\classes;target\dependency\*" eu.melodic.event.brokercep.broker.BrokerClient %*