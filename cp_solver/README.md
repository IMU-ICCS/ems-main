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
There is no actual configuration file for this solver. However, as it exploits the CDOClient component, then it needs to properly configure it through modifying the respective properties file with name eu.paasage.mddb.cdo.client.properties. This file, as also indicated in the respective section of the README for the CDOClient, should be either placed at the particular directory whose path is denoted by the system variable PAASAGE_CONFIG_DIR or you can enhance the command line through the following: "-Deu.paasage.configdir=<path to directory where eu.paasage.mddb.cdo.client.properties file is included>". 

USAGE: 
This solver can be used in three modes via the command line:
(a) CDO mode: this means that the solver reads a ConstraintProblem from the CDO server via a particular CDO path and modifies it when a solution is found. In this case, you have to run the solver as follows: 'mvn exec:java -Deu.paasage.configdir=<path to directory where eu.paasage.mddb.cdo.client.properties file is included> -Djava.library.path=./so -Dexec.args="cdo <cdo path>"';
(b) file mode: this means that the solver reads a ConstraintProblem from a file in the file system and modifies it. In this case, you have to run the solver as follows:
'mvn exec:java -Deu.paasage.configdir=<path to directory where eu.paasage.mddb.cdo.client.properties file is included> -Djava.library.path=./so -Dexec.args="file <file path>"'
(c) daemon mode: this means that the solver runs as a daemon and listens to requests for
solving constraint problem models that are stored in the CDOServer. Once the problem is solved
and a particular solution is found, this solution is written in the respective model at the
CDOServer and all subscribers are informed about it. In this mode, the solver should be run as follows: 'mvn exec:java -Deu.paasage.configdir=<path to directory where eu.paasage.mddb.cdo.client.properties file is included> -Djava.library.path=./so'. 
To test this last mode, you can just execute the following command:
'mvn exec:java -Deu.paasage.configdir=<path to directory where eu.paasage.mddb.cdo.client.properties file is included> -Djava.library.path=./so -Dexec.args="xxxx"' where "xxxx" can be an arbitrary String. During this testing, the
CPSolver listens to constraint problem solving requests from a fake Adapter, it fetches
and solves the constraint problem model, it propagates the solution and it finally informs
the subscribers. In the end, all threads are stopped as this is just a testing mode and not
a normal one.
To summarize, the CDO & file modes require two arguments to be provided, the daemon mode none and the testing mode one arbitrary argument. 
 
Obviously, the above ways to run the solver at the command line can be simplified if the PAASAGE_CONFIG_DIR variable is set properly.

LIMITATIONS:
- objective function should be given by the CP generator; in case that many optimization/soft requirements are provided by the end-user, then the solver can generate a particular objective function through the weighted sum of the priority given to each soft requirement multiplied by the utility of value of the metric involved in the soft requirement. 
