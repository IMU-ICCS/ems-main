Adaptation Manager
========

Configure
--------
The environment variable PAASAGE_CONFIG_DIR should point to a directory that contains a configuration properties file named "eu.paasage.upperware.adapter.properties". This file allows configuring the URL of Executionware ("ExecutionwareURL" property) and the means of obtaining the deployment model. The deployment model can be obtained either from an XMI file ("inputFile" property) or from a CDO server ("CDO.host", "CDP.port", "CDO.repositoryName", and "CDO.resourceName" properties). The configuration properties can also be passed as command-line arguments (see below).

Build
-------
To execute the application via maven:
mvn clean install exec:java

To create an executable jar with all the required dependencies: 
mvn clean compile assembly:single

Test
-------
To test functionalities via maven:
mvn test

Execute
-------
To execute the created jar: 
java -jar target/adaptationmanager-0.0.1-SNAPSHOT-jar-with-dependencies.jar 

To execute the jar passing configuration properties as command-line arguments:
java -jar target/adaptationmanager-0.0.1-SNAPSHOT-jar-with-dependencies.jar -c<property>=<value> 
For example:
java -jar target/adaptationmanager-0.0.1-SNAPSHOT-jar-with-dependencies.jar -cCDO.resourceName=sensAppResource2 

To send a "clean" command to the Executionware:
java -jar target/adaptationmanager-0.0.1-SNAPSHOT-jar-with-dependencies.jar -clean

Run Continuously
-------
To run adaptation manager continuously, a “daemon” command should be sent by one of these ways:

1) By using main method's args:
mvn clean install exec:java -Dexec.args=”daemon”
mvn clean install exec:java -Dexec.args=”daemon” -Dexec.args=”cCDO.resourceName=test”

2) By executing the created jar:
java -jar target/adaptationmanager-2015.06-SNAPSHOT-jar-with-dependencies.jar -daemon
java -jar target/adaptationmanager-2015.06-SNAPSHOT-jar-with-dependencies.jar -cCDO.resourceName=test

Contact
--------
Nikos Parlavantzas (Nikos.Parlavantzas@irisa.fr)
Arnab Sinha (arnab.sinha@inria.fr)
