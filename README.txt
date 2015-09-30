README on how to run the Rule Processor

Create a jar file (it is available on the target/ directory):
$ mvn clean install

To create a executable jar with all the required dependencies: 
$ mvn clean compile assembly:single


Create a directory to store the log and properties files (as root):
$ sudo export PAASAGE_CONFIG_DIR=/opt/paasage/
$ sudo mkdir -p $PAASAGE_CONFIG_DIR
$ sudo chown -hR user:group $PAASAGE_CONFIG_DIR  --- NOTE: user is the username and group is the group that the user belongs to

Copy relevant properties files into the running PaaSage directory:
$ export PAASAGE_CONFIG_DIR=/opt/paasage/
$ cp ../cdo-server/eu.paasage.mddb.cdo.server.properties $PAASAGE_CONFIG_DIR
$ cp src/main/resources/wp3_profiler.properties $PAASAGE_CONFIG_DIR

Compile the CDO server, CDO client and CP generator components from
    http://git.cetic.be/paasage/cdo-server
    http://git.cetic.be/paasage/cdo_client
    http://git.cetic.be/paasage/wp3_profiler/paasage-wp3-profiler/init
    http://git.cetic.be/paasage/wp3_profiler/paasage-wp3-profiler/wp3-cp-generator
    http://git.cetic.be/paasage/wp3_profiler/paasage-wp3-profiler/wp3-cp-model-cloner
with
    mvn clean install

Then copy all the relevant jar files into this directory:
$ cp target/rule-processor-2.0.0-SNAPSHOT-jar-with-dependencies.jar  .

Run the CDO server first on a new terminal:
$ java -jar server-2.0.0-SNAPSHOT-jar-with-dependencies.jar | tee log-CDO-server.txt

Run the init and CP generator components on a new terminal:
$ cp $DIR/cdo_client/examples/ScalarmAmazonUnsupportedRuleProcessor.xmi .
  NOTE: $DIR refers to the directory path of the git repositories

$ java -jar init-service-jar-with-dependencies.jar ScalarmAmazonUnsupportedRuleProcessor.xmi app1234 | tee log-init-service.txt

$ java -jar cp-generator-service-jar-with-dependencies.jar app1234 cp-output.txt | tee log-cp-generator.txt

Find out the generated number for "app1234" in the cp-output.txt
$ cat cp-output.txt

Finally, run the Rule Processor:
$ java -jar rule-processor-2.0.0-SNAPSHOT-jar-with-dependencies.jar scalarm0 upperware-models/1435154797357 | tee log-rule-processor.txt

