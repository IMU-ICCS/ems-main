# CP Model Cloner Tool

CP Model Cloner creates a copy of a given Upperware model and stores it in CDO server.    


## Requirements

* [Java](https://www.java.com/en/) 7 or higher
* Maven dependencies (see [pom.xml](pom.xml))

## Building

To build standalone JAR `target/cp-cloner-service-jar-with-dependencies.jar`:

```shell
$ mvn clean install
```
## Configuration

There are no options specific to the component.

CDO server host and port should be set in `$PAASAGE_CONFIG_DIR/eu.paasage.cdo.client.properties` similarly to other PaaSage components.

## Usage

Component provides CLI interface:

```shell
$ java -jar cp-cloner-service-jar-with-dependencies.jar upperwareModelId upperwareModelCopyId
```

**Input:** CP Model Cloner tool reads Upperware models directly from CDO resource. `upperwareModelId` in CDO Server is an Upperware model. `upperwareModelCopyId` is the identifier of the copy of the model that will be created and stored in CDO.  

**Output**: A copy of Upperware model is created and stored in CDO Server. If there already exists a model with `upperwareModelCopyId` identifier, it will be erased and replaced with the copy. The original Upperware model is not modified.  

## Limitations

* The tool only can create copies of Upperware models.

