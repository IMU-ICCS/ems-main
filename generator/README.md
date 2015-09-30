# CP Generator Model-to-Solver

CP Generator is responsible for generating a Upperware or CP model, which represents the description of a constraint problem. Furthermore, the component filters cloud providers, detects a solution given by end users via the Deployment/CAMEL model and define in provider models of selected candidates the configuration for virtual machines.     


## Requirements

* [Java](https://www.java.com/en/) 7 or higher
* Maven dependencies (see [pom.xml](pom.xml))

## Building

To build standalone JAR `target/cp-generator-service-jar-with-dependencies.jar` without executing tests:

```shell
$ mvn clean install -Dmaven.test.skip=true
```

To generate the JAR with execution of tests, the CDO Server has to be running (cf. Configuration):

```shell
$ mvn clean install
``` 


## Configuration

There are no options specific to the component.

CDO server host and port should be set in `$PAASAGE_CONFIG_DIR/eu.paasage.cdo.client.properties` similarly to other PaaSage components.

## Usage

Component provides CLI interface:

```shell
$ java -jar cp-generator-service-jar-with-dependencies.jar resourceName outputFile
```

**Input:** CP Generator reads CAMEL models directly from CDO resource. `resourceName` in CDO is a CAMEL model. `outputFile` is a file name where the output of the component will be stored.  

**Output**: `outputFile` is updated with the identifier of the generated Upperware model. CAMEL model is not modified. 

### Example
 
The `src/main/resources/examples/scalarm` directory contains 4 xmi files with CAMEL models that can be used for testing purposes:

- `ScalarmFlexiant.xmi`
- `ScalarmFlexiantFull.xmi`
- `ScalarmGWDG.xmi`
- `ScalarmGWDGFull.xmi`

They can be stored in CDO Server by using the Init tool.  

## Limitations

* The component does not guarantee that the generated Upperware model contains a solvable problem. 

