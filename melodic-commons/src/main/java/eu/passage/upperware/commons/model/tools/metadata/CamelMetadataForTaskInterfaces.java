package eu.passage.upperware.commons.model.tools.metadata;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum CamelMetadataForTaskInterfaces {

    // SparkInterface
    SPARK_CLASS_NAME("ClassName"),
    APP_ARGUMENTS("ApplicationArguments"),
    SPARK_ARGUMENTS("SPARKArguments"),
    SPARK_CONFIGURATION("SPARKConfiguration");

    public String camelName;
}