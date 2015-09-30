/**
 * Solver To Deployment
 *  
 * Date: 10/2014
 */


/** COMPILATION AND PACKAGING **/

mvn clean install


/** USE AS STANDALONE APPLICATION VIA MAVEN **/

mvn exec:java -Dexec.args="ConfigurationCDOId DeploymentCDOId"

/** USE AS STANDALONE APPLICATION - JAR FILE **/

mvn clean compile assembly:single

java -jar ./target/solver-to-deployment-0.0.2-SNAPSHOT-jar-with-dependencies.jar ConfigurationCDOId DeploymentCDOI

