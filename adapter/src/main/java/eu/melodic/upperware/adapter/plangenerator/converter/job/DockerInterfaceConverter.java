package eu.melodic.upperware.adapter.plangenerator.converter.job;

import camel.core.Attribute;
import camel.deployment.Configuration;
import camel.deployment.ScriptConfiguration;
import camel.type.StringValue;
import eu.melodic.upperware.adapter.plangenerator.model.AdapterDockerInterface;
import eu.passage.upperware.commons.model.tools.metadata.CamelMetadataForTaskInterfaces;
import eu.passage.upperware.commons.model.tools.metadata.CamelMetadataToolForTaskInterfaces;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
public class DockerInterfaceConverter implements InterfaceConverter<ScriptConfiguration, AdapterDockerInterface> {

    @Override
    public boolean isInstance(Configuration configuration) {
        if (configuration instanceof ScriptConfiguration) {
            ScriptConfiguration scriptConfiguration = (ScriptConfiguration) configuration;
            return DOCKER_TAG.equals(scriptConfiguration.getDevopsTool());
        }
        return false;
    }

    @Override
    public AdapterDockerInterface convert(ScriptConfiguration configuration) {
        return AdapterDockerInterface
                .builder()
                .dockerImage(findDockerImageId(configuration))
                .environment(findEnvironment(configuration))
                .build();
    }

    private Map<String, String> findEnvironment(ScriptConfiguration configuration) {
        return CamelMetadataToolForTaskInterfaces.findFeatureByAnnotation(configuration.getSubFeatures(), CamelMetadataForTaskInterfaces.DOCKER_ENVIRONMENT.camelName)
                .map(CamelMetadataToolForTaskInterfaces::createStringAttributesMapForFeature)
                .orElse(Collections.emptyMap());
    }

    private String findDockerImageId(ScriptConfiguration configuration) {
        Attribute attribute = findAttribute(configuration, CamelMetadataForTaskInterfaces.DOCKER_IMAGE.camelName);
        return attribute == null ? null : ((StringValue) attribute.getValue()).getValue();
    }

    private Attribute findAttribute(ScriptConfiguration configuration, String camelAnnotation) {
        return CamelMetadataToolForTaskInterfaces.findAttributeByAnnotation(configuration.getAttributes(), camelAnnotation)
                .orElseGet(() -> {
                    log.warn("Attribute with annotation: {} not found in camel model configuration", camelAnnotation);
                    return null;
                });
    }
}
