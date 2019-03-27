package eu.melodic.upperware.adapter.plangenerator.converter.job;

import camel.core.Attribute;
import camel.core.Feature;
import camel.deployment.ClusterConfiguration;
import camel.deployment.Configuration;
import camel.type.StringValue;
import eu.melodic.upperware.adapter.plangenerator.model.AdapterSparkInterface;
import eu.passage.upperware.commons.model.tools.metadata.CamelMetadataForTaskInterfaces;
import eu.passage.upperware.commons.model.tools.metadata.CamelMetadataToolForTaskInterfaces;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class SparkInterfaceConverter implements InterfaceConverter<ClusterConfiguration, AdapterSparkInterface> {

    @Override
    public boolean isInstance(Configuration configuration) {
        return configuration instanceof ClusterConfiguration;
    }

    @Override
    public AdapterSparkInterface convert(ClusterConfiguration configuration) {
        return AdapterSparkInterface
                .builder()
                .file(configuration.getDownloadURL())
                .className(findClassName(configuration))
                .arguments(findAppArguments(configuration))
                .sparkArguments(findSparkArguments(configuration))
                .sparkConfiguration(findSparkConfiguration(configuration))
                .build();
    }

    private String findClassName(ClusterConfiguration configuration) {
        Attribute attribute = findAttribute(configuration, CamelMetadataForTaskInterfaces.SPARK_CLASS_NAME.camelName);
        return attribute == null ? null : ((StringValue) attribute.getValue()).getValue();
    }

    private List<String> findAppArguments(ClusterConfiguration configuration) {
        Attribute attribute = findAttribute(configuration, CamelMetadataForTaskInterfaces.APP_ARGUMENTS.camelName);
        return attribute == null ? Collections.emptyList() : parseApplicationArguments(((StringValue) attribute.getValue()).getValue());
    }

    private Map<String, String> findSparkArguments(ClusterConfiguration configuration) {
        return createMapConfigurationForAnnotation(configuration, CamelMetadataForTaskInterfaces.SPARK_ARGUMENTS.camelName);
    }

    private Map<String, String> findSparkConfiguration(ClusterConfiguration configuration) {
        return createMapConfigurationForAnnotation(configuration, CamelMetadataForTaskInterfaces.SPARK_CONFIGURATION.camelName);
    }

    private Attribute findAttribute(ClusterConfiguration configuration, String camelAnnotation) {
        return CamelMetadataToolForTaskInterfaces.findAttributeByAnnotation(configuration.getConfigParameters(), camelAnnotation)
                .orElseGet(() -> {
                    log.warn("Attribute with annotation: {} not found in camel model configuration", camelAnnotation);
                    return null;
                });
    }

    private Map<String, String> createMapConfigurationForAnnotation(ClusterConfiguration configuration, String camelAnnotation) {
        Feature feature = CamelMetadataToolForTaskInterfaces.findFeatureByAnnotation(configuration.getSubFeatures(), camelAnnotation)
                .orElseGet(() -> {
                    log.warn("Feature with annotation: {} not found in camel model configuration", camelAnnotation);
                    return null;
                });
        return feature == null ? Collections.emptyMap() : CamelMetadataToolForTaskInterfaces.createStringAttributesMapForFeature(feature);
    }

    private static List<String> parseApplicationArguments(String arguments) {
        log.debug("Parsing application arguments: {}", arguments);
        String argumentSign = "\\s+";
        return Stream.of(arguments.split(argumentSign))
                .map(String::trim)
                .filter(StringUtils::isNotBlank)
                .peek(s -> log.debug("arg: {}", s))
                .collect(Collectors.toList());
    }
}
