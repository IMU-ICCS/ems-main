package eu.melodic.upperware.adapter.plangenerator.converter.job;

import camel.core.Feature;
import camel.deployment.EventConfiguration;
import camel.deployment.ServerlessConfiguration;
import camel.deployment.SoftwareComponent;
import camel.requirement.PaaSRequirement;
import camel.type.IntValue;
import camel.type.StringValue;
import eu.melodic.upperware.adapter.exception.AdapterException;
import eu.melodic.upperware.adapter.plangenerator.model.AdapterFaasInterface;
import eu.melodic.upperware.adapter.plangenerator.model.AdapterFaasTrigger;
import eu.passage.upperware.commons.model.tools.metadata.CamelMetadataForTaskInterfaces;
import eu.passage.upperware.commons.model.tools.metadata.CamelMetadataToolForTaskInterfaces;
import io.github.cloudiator.rest.model.HttpTrigger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class FaasInterfaceConverter implements InterfaceConverter<ServerlessConfiguration, AdapterFaasInterface> {

    @Override
    public AdapterFaasInterface convert(ServerlessConfiguration configuration) {
        return AdapterFaasInterface
                .builder()
                .sourceCodeUrl(configuration.getBinaryCodeURL())
                .handler(findHandler(configuration))
                .triggers(createTriggers(configuration))
                .functionEnvironment(createFunctionEnvironment(configuration))
                .functionName(configuration.getName())
                .timeout(findTimeout(configuration))
                .build();
    }

    private String findHandler(ServerlessConfiguration configuration) {
        return CamelMetadataToolForTaskInterfaces.findAttributeByAnnotation(configuration.getEnvironmentConfigParams(), CamelMetadataForTaskInterfaces.FAAS_HANDLER.camelName)
                .map(attribute -> ((StringValue) attribute.getValue()).getValue())
                .orElseThrow(() -> new AdapterException("Could not find required attribute: handler in camel"));
    }

    private Map<String, String> createFunctionEnvironment(ServerlessConfiguration configuration) {
        return CamelMetadataToolForTaskInterfaces.findFeatureByAnnotation(configuration.getSubFeatures(), CamelMetadataForTaskInterfaces.FAAS_ENVIRONMENT.camelName)
                .map(CamelMetadataToolForTaskInterfaces::createStringAttributesMapForFeature)
                .orElse(Collections.emptyMap());
    }

    // currently list with one element
    private List<AdapterFaasTrigger> createTriggers(ServerlessConfiguration configuration) {
        return Collections.singletonList(createTrigger(configuration.getEventConfiguration()));
    }

    private AdapterFaasTrigger createTrigger(EventConfiguration eventConfiguration) {
        return AdapterFaasTrigger.builder()
                .type(HttpTrigger.class.getSimpleName())
                .httpMethod(eventConfiguration.getHttpMethodType().getLiteral())
                .httpPath(eventConfiguration.getHttpMethodName())
                .build();
    }

    private int findTimeout(ServerlessConfiguration configuration) {
        return findLimitAttribute(getPaaSRequirement(configuration), CamelMetadataForTaskInterfaces.FAAS_TIMEOUT.camelName, 6);
    }

    private int findLimitAttribute(PaaSRequirement paaSRequirement, String attributeName, int defaultValue) {
        Optional<Feature> optionalFeature = CamelMetadataToolForTaskInterfaces.findFeatureByAnnotation(paaSRequirement.getSubFeatures(), CamelMetadataForTaskInterfaces.FAAS_LIMITS.camelName);

        return optionalFeature.map(feature -> CamelMetadataToolForTaskInterfaces.findAttributeByAnnotation(feature.getAttributes(), attributeName)
            .filter(IntValue.class::isInstance)
            .map(attribute -> ((IntValue) attribute.getValue()).getValue())
            .orElse(defaultValue)
        ).orElse(defaultValue);
    }

    private PaaSRequirement getPaaSRequirement(ServerlessConfiguration configuration) {
        SoftwareComponent softwareComponent = (SoftwareComponent) configuration.eContainer();
        return softwareComponent.getRequirementSet().getPaasRequirement();
    }
}
