@echo off
rem mvn dependency:copy-dependencies
java -classpath control-service/target/classes;control-service/target/dependency/* eu.melodic.event.control.util.CpModelHelper %*
rem Usage: cp2cdo <file> <cdo-resource>
