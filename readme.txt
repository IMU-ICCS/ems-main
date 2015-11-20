/**
 * Solver To Deployment
 *  
 * Date: 10/2014
 */


/** COMPILATION AND PACKAGING **/

mvn clean install


/** USE AS STANDALONE APPLICATION VIA MAVEN **/

mvn exec:java -Dexec.args="[-o dstDMid (or -1 for last one)] [-t SolutionTimeStamp] ConfigurationCDOId CamelCDOId CloudProviderCDODirID"

/** USE AS STANDALONE APPLICATION - JAR FILE **/

mvn clean compile assembly:single

java -jar ./target/solver-to-deployment-0.0.2-SNAPSHOT-jar-with-dependencies.jar ConfigurationCDOId CamelCDOId


/** S2D OVERVIEW **/

From a CP model stored in CDO under the id <ConfigurationCDOId> (String), S2D creates a new DeploymentModel entry into
the CAMEL document stored in CDO under the id <CamelCDOId>. To that, it uses the solution whose time stamp is SolutionTimeStamp.
If a solutionTimeStamp is not provided, S2D uses the entry with the highest time stamp.
S2D load CloudProvider configurations from CDO under repository CloudProviderCDODirID (as returns by the CP Generator that has created them).

The new DeploymentModel is a copy of the 1st DeploymentModel in the CAMEL document extended with
- component instances
- vm instances
- hosting instances
- communication instances
Those instances are computed from the Solution. 

If -o is given, a deployment model whose number if DMid is overwritten. If DMis is -1, the last entry is overwritten.

More information in the CP generator component, in particular how information is encoded in the name of the variables of the Solution.

/** ZeroMQ OVERVIEW **/

Cf src/main/java/eu/paasage/upperware/solvertodeployment/zeromq/S2D_ZMQ_Service.java 