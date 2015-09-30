# Init Tool

Init Tool stores a given CAMEL model on CDO Server.     


## Requirements

* [Java](https://www.java.com/en/) 7 or higher
* Maven dependencies (see [pom.xml](pom.xml))

## Building

To build standalone JAR `init-service-jar-with-dependencies.jar`:

```shell
$ mvn clean install
```

## Configuration

There are no options specific to the component.

CDO server host and port should be set in `$PAASAGE_CONFIG_DIR/eu.paasage.cdo.client.properties` similarly to other PaaSage components.

## Usage

Component provides CLI interface:

```shell
$ java -jar target/init-service-jar-with-dependencies.jar camelModelFile camelModelId
```

**Input:** Init tool stores a xmi file in CDO Server. `camelModelFile` is the xmi file that contains the CAMEL model.`camelModelId` is the identifier that will be used to store the CAMEL model in CDO server. 

**Output**: The CAMEL model has been stored in CDO Server with the specified id.  

## Limitations

* The tool only supports the storage of CAMEL models. 

