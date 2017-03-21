# To LA Based Reasoner Tool

LA Based Reasoner Tool provides two functionalities: (i) Translation of Upperware models in the format supported by LA Based Reasoner, (ii)  Storage of a given solution in CDO Server by using the related Upperware model.    


## Requirements

* [Java](https://www.java.com/en/) 7 or higher
* Maven dependencies (see [pom.xml](pom.xml))

## Building

To build standalone JAR `target/la-based-reasoner-converter-jar-with-dependencies.jar`:

```shell
mvn clean compile assembly:single
```

## Configuration

There are no options specific to the component.

CDO server host and port should be set in `$PAASAGE_CONFIG_DIR/eu.paasage.cdo.client.properties` similarly to other PaaSage components.

## Usage

Component provides CLI interface:

```shell
$ java -jar la-based-reasoner-converter-jar-with-dependencies.jar serviceType file resourceName
```

**Input:** o LA Based Reasoner Tool reads Upperware models directly from CDO Server. `serviceType` is an integer that refers to translation (0) of Upperware model in the format of LA Based Reasoner or the storage of a solution (1). `file` is a text file containing the solution to be stored or the output directory for the translation.`resourceName`is the identifier of the Upperware model in CDO, which be used to assign values in the solution or generate the description in the format required by LA Based Reasoner.    
 
**Output**: If `serviceType`== 0 => Two files are generated: (i) `file/Variables.model` containing the problem variables and (ii) `file/Constraints.model` containing constraints on these variables. If `serviceType`== 1 =>`resourceName` Upperware model is updated with a new solution defined by `file`.  

 ## Limitations

* The tool is only compatible the LA Based Reasoner solver. 

