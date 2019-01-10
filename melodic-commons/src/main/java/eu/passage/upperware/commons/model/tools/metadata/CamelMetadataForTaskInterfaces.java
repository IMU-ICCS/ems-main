package eu.passage.upperware.commons.model.tools.metadata;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum CamelMetadataForTaskInterfaces {

    // SparkInterface
    SPARK_CLASS_NAME("ClassName"),
    APP_ARGUMENTS("ApplicationArguments"),
    SPARK_ARGUMENTS("SPARKArguments"),
    SPARK_CONFIGURATION("SPARKConfiguration"),

    // DockerInterface
    DOCKER_ENVIRONMENT("DockerArguments"),

    // FaasInterface
    FAAS_HANDLER("Handler"),
    FAAS_ENVIRONMENT("Environment"),
    FAAS_RUNTIME("Runtime"),
    FAAS_LIMITS("Limits"),
    FAAS_TIMEOUT("maxDuration"),
    FAAS_MEMORY("RAM");

    public String camelName;
}