package eu.melodic.upperware.adapter.plangenerator.converter.job;

import camel.core.Attribute;
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
import eu.melodic.upperware.adapter.plangenerator.model.AdapterTaskInterface;
import eu.passage.upperware.commons.model.tools.metadata.CamelMetadataForTaskInterfaces;
import eu.passage.upperware.commons.model.tools.metadata.CamelMetadataToolForTaskInterfaces;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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
                .build();
    }

    public AdapterFaasInterface addInformationFromSoftwareComponent(AdapterTaskInterface result, SoftwareComponent softwareComponent) {
        PaaSRequirement paasRequirement = softwareComponent.getRequirementSet().getPaasRequirement();
        AdapterFaasInterface faasInterface = (AdapterFaasInterface) result;
        faasInterface.setRuntime(findRuntime(paasRequirement));
        faasInterface.setMemory(findMemory(paasRequirement));
        faasInterface.setTimeout(findTimeout(paasRequirement));
        faasInterface.setFunctionName(softwareComponent.getName());
        return faasInterface;
    }

    private String findHandler(ServerlessConfiguration configuration) {
        Attribute attribute = CamelMetadataToolForTaskInterfaces.findAttributeByAnnotation(configuration.getEnvironmentConfigParams(), CamelMetadataForTaskInterfaces.FAAS_HANDLER.camelName)
                .orElseThrow(() -> new AdapterException("Could not find required attribute: handler in camel"));
        return ((StringValue) attribute.getValue()).getValue();
    }

    private Map<String, String> createFunctionEnvironment(ServerlessConfiguration configuration) {
        Feature feature = CamelMetadataToolForTaskInterfaces.findFeatureByAnnotation(configuration.getSubFeatures(), CamelMetadataForTaskInterfaces.FAAS_ENVIRONMENT.camelName)
                .orElseGet(() -> {
                    log.warn("Feature with annotation: {} not found in camel model configuration", CamelMetadataForTaskInterfaces.FAAS_ENVIRONMENT.camelName);
                    return null;
                });
        return feature == null ? Collections.emptyMap() : CamelMetadataToolForTaskInterfaces.createStringAttributesMapForFeature(feature);
    }

    // currently list with one element
    private List<AdapterFaasTrigger> createTriggers(ServerlessConfiguration configuration) {
        AdapterFaasTrigger trigger = createTrigger(configuration.getEventConfiguration());
        return Collections.singletonList(trigger);
    }

    private AdapterFaasTrigger createTrigger(EventConfiguration eventConfiguration) {
        return AdapterFaasTrigger.builder()
                .type("HttpTrigger")
                .httpMethod(eventConfiguration.getHttpMethodType().getLiteral())
                .httpPath(eventConfiguration.getHttpMethodName())
                .build();
    }

    private int findMemory(PaaSRequirement paasRequirement) {
        return findLimitAttribute(paasRequirement, CamelMetadataForTaskInterfaces.FAAS_MEMORY.camelName, 1024);
    }

    private int findTimeout(PaaSRequirement paasRequirement) {
        return findLimitAttribute(paasRequirement, CamelMetadataForTaskInterfaces.FAAS_TIMEOUT.camelName, 6);
    }

    private int findLimitAttribute(PaaSRequirement paaSRequirement, String attributeName, int defaultValue) {
        Feature feature = CamelMetadataToolForTaskInterfaces.findFeatureByAnnotation(paaSRequirement.getSubFeatures(), CamelMetadataForTaskInterfaces.FAAS_LIMITS.camelName)
                .orElseGet(() -> {
                    log.warn("Feature with annotation: {} not found in camel model requirements", CamelMetadataForTaskInterfaces.FAAS_LIMITS.camelName);
                    return null;
                });
        if (feature == null) {
            return defaultValue;
        }
        Attribute attribute = CamelMetadataToolForTaskInterfaces.findAttributeByAnnotation(feature.getAttributes(), attributeName)
                .orElseGet(() -> {
                    log.warn("Attribute with annotation: {} not found in feature", attributeName);
                    return null;
                });
        return attribute == null ? defaultValue : ((IntValue) attribute.getValue()).getValue();
    }

    private String findRuntime(PaaSRequirement paasRequirement) {
        Feature feature = CamelMetadataToolForTaskInterfaces.findFeatureByAnnotation(paasRequirement.getSubFeatures(), CamelMetadataForTaskInterfaces.FAAS_ENVIRONMENT.camelName)
                .orElseThrow(() -> new AdapterException("Could not find feature with required attribute: runtime in camel"));
        Attribute attribute = CamelMetadataToolForTaskInterfaces.findAttributeByAnnotation(feature.getAttributes(), CamelMetadataForTaskInterfaces.FAAS_RUNTIME.camelName)
                .orElseThrow(() -> new AdapterException("Could not find required attribute: runtime in feature"));
        return ((StringValue) attribute.getValue()).getValue();
    }
}
