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
                .runtime(findRuntime(configuration))
                .memory(findMemory(configuration))
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
                .type("HttpTrigger")
                .httpMethod(eventConfiguration.getHttpMethodType().getLiteral())
                .httpPath(eventConfiguration.getHttpMethodName())
                .build();
    }

    private int findMemory(ServerlessConfiguration configuration) {
        return findLimitAttribute(getPaaSRequirement(configuration), CamelMetadataForTaskInterfaces.FAAS_MEMORY.camelName, 1024);
    }

    private int findTimeout(ServerlessConfiguration configuration) {
        return findLimitAttribute(getPaaSRequirement(configuration), CamelMetadataForTaskInterfaces.FAAS_TIMEOUT.camelName, 6);
    }

    private int findLimitAttribute(PaaSRequirement paaSRequirement, String attributeName, int defaultValue) {
        Optional<Feature> optionalFeature = CamelMetadataToolForTaskInterfaces.findFeatureByAnnotation(paaSRequirement.getSubFeatures(), CamelMetadataForTaskInterfaces.FAAS_LIMITS.camelName);

        if (optionalFeature.isPresent()) {
            return CamelMetadataToolForTaskInterfaces.findAttributeByAnnotation(optionalFeature.get().getAttributes(), attributeName)
                    .filter(IntValue.class::isInstance)
                    .map(attribute -> ((IntValue) attribute.getValue()).getValue())
                    .orElse(defaultValue);
        } else {
            return defaultValue;
        }
    }

    private String findRuntime(ServerlessConfiguration configuration) {
        Feature feature = CamelMetadataToolForTaskInterfaces.findFeatureByAnnotation(getPaaSRequirement(configuration).getSubFeatures(), CamelMetadataForTaskInterfaces.FAAS_ENVIRONMENT.camelName)
                .orElseThrow(() -> new AdapterException("Could not find feature with required attribute: runtime in camel"));
        return CamelMetadataToolForTaskInterfaces.findAttributeByAnnotation(feature.getAttributes(), CamelMetadataForTaskInterfaces.FAAS_RUNTIME.camelName)
                .map(attribute1 -> ((StringValue) attribute1.getValue()).getValue())
                .orElseThrow(() -> new AdapterException("Could not find required attribute: runtime in feature"));
    }

    private PaaSRequirement getPaaSRequirement(ServerlessConfiguration configuration) {
        SoftwareComponent softwareComponent = (SoftwareComponent) configuration.eContainer();
        return softwareComponent.getRequirementSet().getPaasRequirement();
    }
}
