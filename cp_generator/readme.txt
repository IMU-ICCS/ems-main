/**
 * CP Generator
 *  Copyright (C) 2014 INRIA, Universite Lille 1
 *
 * Contacts: daniel.romero@inria.fr & clement.quinton@inria.fr & laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 03/2014
 */

/** REQUIREMENTS **/

- CDO Server running
- Definition of the PAASAGE_CONFIG_DIR property


/** COMPILATION AND PACKAGING **/
mvn clean install


/** DEPLOYMENT **/

Copy the ./target/cp-generator-service.war in tomcat "webapps" directory. 


/** USE VIA TOMCAT **/

-Open the following link in a web browser: http://localhost:8080/cp-generator-service/  
-Select a zip file containing the CPIM model, the saloon ontology and descriptor.paasage files (e.g., ./src/main/resources/examples/scenario3/scenario3.zip) 

/** USE AS STANDALONE APPLICATION VIA MAVEN **/


mvn exec:java -Dexec.args="<camel_xmi_file>"

/** USE AS STANDALONE APPLICATION - JAR FILE **/

mvn clean compile assembly:single


java -jar ./target/cp-generator-service-jar-with-dependencies.jar <camel_xmi_file>