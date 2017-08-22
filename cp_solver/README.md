This is another solver that can be used in the PaaSage Upperware module for performing reasoning over deployment configurations.

FUNCTIONALITY:
This solver encompasses the Choco CP solver (http://choco-solver.org/) which has the following capabilities:
- handling of boolean, integer, real and set variables
- mapping of boolean, integer and set variables to a variety of strategies
- consideration of non-linear constraints
- optimization problems can be solved apart from regular CP problems involving either integer or real variables

REQUIREMENTS:
 - java 7
 - linux
 - maven

BUILDING:
 - mvn clean install

CONFIGURATION:
Where is config file: eu.melodic.upperware.cpSolver.properties that is provided during application startup as an program argument:
--spring.config.location=classpath:/config/eu.melodic.upperware.cpSolver.properties
 

USAGE:
This solver is a Spring Boot application that require following environment variables to be set:
PAASAGE_CONFIG_DIR=<path to directory where eu.paasage.mddb.cdo.client.properties file is included>
LD_LIBRARY_PATH=<path to so external libraries (i.e. ibex) >

It exposes REST interface(s) (defined in CPSolverController class according to description in the integration/interfaces project)


LIMITATIONS:
- objective function should be given by the CP generator; in case that many optimization/soft requirements are provided by the end-user, then the solver can generate a particular objective function through the weighted sum of the priority given to each soft requirement multiplied by the utility of value of the metric involved in the soft requirement. 
