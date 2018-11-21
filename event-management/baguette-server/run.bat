@echo off

if not exist target\dependency cmd /C "mvn dependency:copy-dependencies"

java -classpath "target\classes;target\dependency\*;conf;jars\*" eu.melodic.event.baguette.server.BaguetteServer %*