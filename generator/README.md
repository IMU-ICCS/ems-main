# CP Generator Model-to-Solver

CP Generator is responsible for generating a Upperware or CP model, which represents the description of a constraint problem. Furthermore, the component filters cloud providers, detects a solution given by end users via the Deployment/CAMEL model and define in provider models of selected candidates the configuration for virtual machines.     


## Requirements

* [Java](https://www.java.com/en/) 7 or higher
* Maven dependencies (see [pom.xml](pom.xml))
* [ZeroMQ Java Binding](http://zeromq.org/bindings:java)

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

The component provides a CLI interface:

```shell
$ java -jar cp-generator-service-jar-with-dependencies.jar resourceName outputFile
```

**Input:** CP Generator reads CAMEL models directly from CDO resource. `resourceName` in CDO is a CAMEL model. `outputFile` is a file name where the output of the component will be stored.  

**Output**: `outputFile` is updated with the identifier of the generated Upperware model. CAMEL model is not modified. 

## Usage with ZeroMQ

The component also provides a CLI interface for executions with ZeroMQ: 

```shell
java -Djava.library.path=pathToZmqLibraries -cp cp-generator-service-jar-with-dependencies.jar eu.paasage.upperware.profiler.cp.generator.zeroMQ.lib.ZeroMQServer
```

**Input:** CP Generator reads CAMEL models directly from CDO resource. To do that via ZeroMQ, the component subscribes to the "ID" topic via  by using `zeromqSubscriberPort` and retrieves the resource name. If subscriber port is not specified, the component uses 5555 as default port.

**Output:** CP Generator publishes the identifier of the generated Upperware model via ZeroMQ by using `zeromqPublisherPort` and the "startSolving" as topic. The component also publishes the identifier of the input Camel model by using the topic "camelModelId". If publisher port is not specified, the component uses 5544 as default port.CAMEL model is not modified.

 The `java.library.path` has to define the directory where the libzmq and libjzmq shared libraries were installed (normally /usr/local/lib on UNIX-like systems) 

You can define topics and ports by using the wp3_cp_generator.properties file available in the `PAASAGE_CONFIG_DIR directory`. Below, you have the list of properties that you can specify via this file.  
  
- `zeromqSubscriberPort`: The subscriber port. The default value is 5555.  
- `zeromqSubscriberHostName`: The host name for subscription. The default value is "localhost" 
- `zeromqSubscriberTopicName`: The subscription topic. The default value is "ID"
- `zeromqPublisherPort`: The publisher port. The default value is 5544. 
- `zeromqPublisherrCPModelID`: The topic used for publishing the generated model id. The default value is "startSolving".  
- `zeromqPublisherrCamelModelID`: The topic used for publshing the Camel Model id. The default value is "camelModelId".   
    

## Limitations

* The component does not guarantee that the generated Upperware model contains a solvable problem. 

