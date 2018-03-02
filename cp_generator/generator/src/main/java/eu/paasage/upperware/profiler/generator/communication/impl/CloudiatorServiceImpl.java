package eu.paasage.upperware.profiler.generator.communication.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.paasage.camel.deployment.VM;
import eu.paasage.camel.deployment.VMRequirementSet;
import eu.paasage.camel.requirement.OSOrImageRequirement;
import eu.paasage.camel.requirement.QuantitativeHardwareRequirement;
import eu.paasage.upperware.profiler.generator.communication.CloudiatorService;
import eu.paasage.upperware.profiler.generator.properties.GeneratorProperties;
import eu.paasage.upperware.profiler.generator.service.camel.impl.NewCamelModelTools;
import io.github.cloudiator.rest.ApiException;
import io.github.cloudiator.rest.api.MatchmakingApi;
import io.github.cloudiator.rest.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class CloudiatorServiceImpl implements CloudiatorService {

    private static final String HARDWARE_CLASS = "hardware";
    private static final String IMAGE_CLASS = "image";

    private MatchmakingApi matchmakingApi;

    public CloudiatorServiceImpl(GeneratorProperties generatorProperties) {
        this.matchmakingApi = new MatchmakingApi();
        this.matchmakingApi.getApiClient().setBasePath(generatorProperties.getCloudiatorV2().getUrl());
        this.matchmakingApi.getApiClient().setApiKey(generatorProperties.getCloudiatorV2().getApiKey());
    }

    @Override
    public List<NodeCandidate> findNodeCandidates(NodeRequirements nodeRequirements) throws ApiException {
        return matchmakingApi.findNodeCandidates(nodeRequirements);
    }

    @Override
    public NodeRequirements createNodeRequirements(VM vm){
        List<Requirement> requirements = new ArrayList<>();

        QuantitativeHardwareRequirement quantitativeHardwareRequirement = NewCamelModelTools.getHardwareRequirements(vm);
        requirements.addAll(createQuantitativeHardwareRequirements(quantitativeHardwareRequirement));
//        requirements.addAll(createLocationRequirement(vmRequirementSet, globalVMRequirements));
        requirements.addAll(createOsOrImageRequirement(NewCamelModelTools.getOsOrImageRequirements(vm)));
//        requirements.addAll(createProviderRequirement(vmRequirementSet, globalVMRequirements));
        return new NodeRequirements().requirements(requirements);
    }

    private Collection<? extends Requirement> createProviderRequirement(VMRequirementSet vmRequirementSet, VMRequirementSet globalVMRequirements) {
        log.warn("Provider Requirements are not supported in cloudiator");
        return Collections.emptyList();
    }

    private Collection<? extends Requirement> createOsOrImageRequirement(OSOrImageRequirement osOrImageRequirement) {
        List<Requirement> requirements = new ArrayList<>();
        if (osOrImageRequirement != null && StringUtils.isNotBlank(osOrImageRequirement.getName())) {
            requirements.add(createRequirement(IMAGE_CLASS, "operatingSystem.family", RequirementOperator.EQ, prepareOSFamilyValue(osOrImageRequirement.getName())));
        } else {
            log.warn("OSOrImageRequirement is missing - aborting generating os requirement");
        }
        return requirements;
    }

    private String prepareOSFamilyValue(String osName) {
        return "OSFamily::" + StringUtils.upperCase(osName);
    }

    private Collection<? extends Requirement> createLocationRequirement(VMRequirementSet vmRequirementSet, VMRequirementSet globalVMRequirements) {
        log.warn("Location Requirements are not supported in cloudiator");
        return Collections.emptyList();
    }

    private Collection<? extends Requirement> createQuantitativeHardwareRequirements(QuantitativeHardwareRequirement quantitativeHardwareRequirement) {
        List<Requirement> requirements = new ArrayList<>();
        if (quantitativeHardwareRequirement != null) {
            requirements.addAll(createRamRequirements(quantitativeHardwareRequirement));
            requirements.addAll(createStorageRequirements(quantitativeHardwareRequirement));
            requirements.addAll(createCoresRequirements(quantitativeHardwareRequirement));
            requirements.addAll(createCpuRequirements(quantitativeHardwareRequirement));
        } else {
            log.warn("QuantitativeHardwareRequirement is missing - aborting generating hardware requirement");
        }
        return requirements;
    }

    private Collection<? extends Requirement> createRamRequirements(QuantitativeHardwareRequirement quantitativeHardwareRequirement) {
        List<Requirement> result = new ArrayList<>();
        if (quantitativeHardwareRequirement != null) {

            int minRAM = quantitativeHardwareRequirement.getMinRAM();
            if (minRAM > 0){
                result.add(createRequirement(HARDWARE_CLASS, "ram", RequirementOperator.GEQ, String.valueOf(minRAM)));
            }

            int maxRAM = quantitativeHardwareRequirement.getMaxRAM();
            if (maxRAM > 0) {
                result.add(createRequirement(HARDWARE_CLASS, "ram", RequirementOperator.LEQ, String.valueOf(maxRAM)));
            }
        }
        return result;
    }

    private Collection<? extends Requirement> createCoresRequirements(QuantitativeHardwareRequirement quantitativeHardwareRequirement) {
        List<Requirement> result = new ArrayList<>();
        if (quantitativeHardwareRequirement != null) {

            int minCores = quantitativeHardwareRequirement.getMinCores();
            if (minCores > 0) {
                result.add(createRequirement(HARDWARE_CLASS, "cores", RequirementOperator.GEQ, String.valueOf(minCores)));
            }

            int maxCores = quantitativeHardwareRequirement.getMaxCores();
            if (maxCores > 0) {
                result.add(createRequirement(HARDWARE_CLASS, "cores", RequirementOperator.LEQ, String.valueOf(maxCores)));
            }
        }
        return result;
    }

    private Collection<? extends Requirement> createStorageRequirements(QuantitativeHardwareRequirement quantitativeHardwareRequirement) {
        List<Requirement> result = new ArrayList<>();
        if (quantitativeHardwareRequirement != null) {

            int minStorage = quantitativeHardwareRequirement.getMinStorage();
            if (minStorage > 0) {
                result.add(createRequirement(HARDWARE_CLASS, "disk", RequirementOperator.GEQ, String.valueOf(minStorage)));
            }

            int maxStorage = quantitativeHardwareRequirement.getMaxStorage();
            if (maxStorage > 0) {
                result.add(createRequirement(HARDWARE_CLASS, "disk", RequirementOperator.LEQ, String.valueOf(maxStorage)));
            }
        }
        return result;
    }

    private Collection<? extends Requirement> createCpuRequirements(QuantitativeHardwareRequirement quantitativeHardwareRequirement) {
        log.warn("Cpu Requirements are not supported in cloudiator");
        return Collections.emptyList();
    }


    private Requirement createRequirement(String requirementClass, String requirementAttribute, RequirementOperator requirementOperator, String value){
        return new AttributeRequirement()
                .requirementClass(requirementClass)
                .requirementAttribute(requirementAttribute)
                .requirementOperator(requirementOperator)
                .value(value)
                .type("AttributeRequirement");
    }


    private List<NodeCandidate> getSampleNodeCandidates() {
        File file = new File(getClass().getClassLoader().getResource("test/nodeCandidates.json").getFile());
        try {
            return new ObjectMapper().readValue(file, new TypeReference<List<NodeCandidate>>(){});
        } catch (IOException e) {
            System.out.println(e);
        }
        return Collections.emptyList();
    }



}
