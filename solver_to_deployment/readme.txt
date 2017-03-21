/**
 * Solver To Deployment
 *  
 * Date: 10/2014
 */


/** COMPILATION AND PACKAGING **/

mvn clean install


/** USE AS STANDALONE APPLICATION VIA MAVEN **/

mvn exec:java -Dexec.args="[-o dstDMid (or -1 for last one)] [-t SolutionTimeStamp] [-d level] ConfigurationCDOId CamelCDOId CloudProviderCDODirID"

-d level: dump DM level
  0 : no dump
  1 : # of DM
  2 : # of DM + # of instances for each DM entry
  3 : like 2 + names of intances

/** USE AS STANDALONE APPLICATION - JAR FILE **/

mvn clean compile assembly:single

java -jar ./target/solver-to-deployment-0.0.2-SNAPSHOT-jar-with-dependencies.jar ConfigurationCDOId CamelCDOId

/** Helper Applications **/

==> SolutionManager

List and create new Solution in an existing ConstraintProblem (with id <CPID> in the CDO)

java -cp solver-to-deployment.jar eu.paasage.upperware.solvertodeployment.utils.SolutionManager <CPID> <command with parameter>

Usage: SolutionManager CPid [add timeStamp / new ]* [del timeStamp / all / last]* [list level (0-2)]* [ls == list 2]*



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