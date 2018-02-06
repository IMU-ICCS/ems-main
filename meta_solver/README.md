MetaSolver
==========
This is metasolver that is used in the PaaSage Upperware module for calling and coordinating the reasoning over deployment configurations.

FUNCTIONALITY:
- invokes the right solver
- 
- 
- 

REQUIREMENTS:
 - java 7
 - linux
 - maven

BUILDING:
 - mvn clean install

CONFIGURATION:
Where is config file: eu.melodic.upperware.metaSolver.properties that is provided during application startup as an program argument:
--spring.config.location=classpath:/config/eu.melodic.upperware.metaSolver.properties

Configuration
-------------
Please make sure $MELODIC_CONFIG_DIR environment variable is set and following property files are available in this Melodic config directory:
- eu.melodic.upperware.metasolver.properties - used by the Metasolver
- eu.melodic.mddb.cdo.client.properties - used by the CDO Client repository


USAGE:
Metasolver is a Spring Boot application that requires following environment variable to be set:
MELODIC_CONFIG_DIR=<path to directory where eu.paasage.mddb.cdo.client.properties file is included>

It exposes REST interface(s) (defined in MetaSolverController class according to description in the integration/interfaces project)


LIMITATIONS:
- 
