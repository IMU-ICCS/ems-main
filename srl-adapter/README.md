# SRL Adapter (srl-adapter)
***
## Description

The SRL Adapter is used two folded:
 - Instantiate the MetricInstances of MetricContexts inside a CAMEL model stored on a CDO server
 - Install probes and aggregation in Visor, respectively Axe.

***
## Building
The adapter is build using maven:
```
mvn clean install
```
Afterwards a bundled jar with dependencies can be found in the target folder.
***
## Usage
srl-adapter requires JRE 8.
```
java -jar target/srl-adapter-jar-with-dependencies.jar
```
```
usage: java -jar [args] srl-adapter-jar-with-dependencies.jar
 -cdoUser                   Username to access the CDO
 -cdoPass                   Password to access the CDO
 -modelName                 Name of model in the CDO
 -resourceName              Name of resource in the CDO
 -executionContextName      Name of ExecutionContext in the CDO
 -colUser                   Username to access the colosseum
 -colTen                    Tenant to access the colosseum
 -colPass                   Password to access the colosseum
 -writeExample              Write example to CDO? [true|false]
 -createMetricInstances     Create MetricInstances? [true|false]
```
