# Upperware Models Library

Upperware models are used by different components in order to find a valid deployment solution for a PaaSage application. These models capture key information available in CAMEL models as well as description of the provider selection problem as a constraint problem.  

## Requirements

* [Java](https://www.java.com/en/) 7 or higher
* Maven dependencies (see [pom.xml](pom.xml))

## Building

To build standalone JAR `target/wp3-cp-models-2.0.0-SNAPSHOT.jar`:

```shell
$ mvn clean install
```

## Configuration

There are no options specific to the library.

## Usage

The code enabling Upperware models manipulation is mainly used as a library.