package eu.melodic.upperware.adapter.plangenerator.converter.job;

import camel.deployment.ScriptConfiguration;
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
    public AdapterDockerInterface convert(ScriptConfiguration configuration) {
        return AdapterDockerInterface
                .builder()
                .dockerImage(configuration.getImageId())
                .environment(findEnvironment(configuration))
                .build();
    }

    private Map<String, String> findEnvironment(ScriptConfiguration configuration) {
        return CamelMetadataToolForTaskInterfaces.findFeatureByAnnotation(configuration.getSubFeatures(), CamelMetadataForTaskInterfaces.DOCKER_ENVIRONMENT.camelName)
                .map(CamelMetadataToolForTaskInterfaces::createStringAttributesMapForFeature)
                .orElse(Collections.emptyMap());
    }

}
