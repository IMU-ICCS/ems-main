package eu.melodic.upperware.adapter.service;

import camel.core.CamelModel;
import camel.core.Feature;
import camel.deployment.*;
import com.google.gson.Gson;
import eu.melodic.upperware.adapter.service.Instance_no_provider.InstanceNoProvider;
import eu.passage.upperware.commons.model.tools.CdoTool;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class CamelInstanceServiceImpl implements CamelInstanceService {
    private final static String SEPARATOR_NAME_SIGN = "-";

    private InstanceNoProvider instanceNoProvider;
    private CamelEnricherService camelEnricherService;
    private Gson gson;

    @Override
    public DeploymentInstanceModel createDeploymentInstanceModel(DeploymentTypeModel deploymentTypeModel, List<SoftwareInstanceDetail> softwareInstanceDetails) {

        CamelModel camelModel = (CamelModel) deploymentTypeModel.eContainer();
        int dmId = camelModel.getDeploymentModels().size();

        DeploymentInstanceModel deploymentInstanceModel = DeploymentFactory.eINSTANCE.createDeploymentInstanceModel();
        deploymentInstanceModel.setName(deploymentTypeModel.getName() + "_" + dmId);
        deploymentInstanceModel.setType(deploymentTypeModel);


        softwareInstanceDetails.stream()
                .map(this::createSoftwareComponentInstances)
                .forEach(softwareComponentInstances -> deploymentInstanceModel.getSoftwareComponentInstances().addAll(softwareComponentInstances));

        deploymentTypeModel.getCommunications()
                .stream()
                .map(communication -> createCommunicationInstanceFromDemand(communication, deploymentInstanceModel, deploymentInstanceModel.getSoftwareComponentInstances()))
                .forEach(communicationInstances -> deploymentInstanceModel.getCommunicationInstances().addAll(communicationInstances));

        //changeNames(deploymentInstanceModel.getSoftwareComponentInstances(), camelModel);
        instanceNoProvider.restart(camelModel);
        return deploymentInstanceModel;
    }

    private List<SoftwareComponentInstance> createSoftwareComponentInstances(SoftwareInstanceDetail softwareInstanceDetail){
        return IntStream.range(0, softwareInstanceDetail.getCardinality())
                .mapToObj(value -> createSoftwareComponentInstance(softwareInstanceDetail.getSoftwareComponent()))
                .peek(softwareComponentInstance -> camelEnricherService.enrich(softwareComponentInstance, "nodeCandidate", gson.toJson(softwareInstanceDetail.getNodeCandidate())))
                .collect(Collectors.toList());
    }

    private SoftwareComponentInstance createSoftwareComponentInstance(SoftwareComponent softwareComponent) {
        // Create Instance + name + type
        int softwareInstance = instanceNoProvider.getNewInstanceNoForComponent(softwareComponent.getName());
        String softwareComponentName = CamelInstanceNamingService.createSoftwareInstanceName(softwareComponent.getName(), softwareInstance);
        SoftwareComponentInstance softwareComponentInstance = DeploymentFactory.eINSTANCE.createSoftwareComponentInstance();
        softwareComponentInstance.setName(softwareComponentName);
        softwareComponentInstance.setType(softwareComponent);

        //Create ProvidedCommunicationInstance
        IntStream.range(0, softwareComponent.getProvidedCommunications().size())
                .mapToObj(i -> createProvidedCommunicationInstance(softwareComponent.getProvidedCommunications().get(i), softwareComponentName, i))
                .forEach(providedCommunicationInstance -> softwareComponentInstance.getProvidedCommunicationInstances().add(providedCommunicationInstance));

        //Create RequiredCommunicationInstance
        IntStream.range(0, softwareComponent.getRequiredCommunications().size())
                .mapToObj(i -> createRequiredCommunicationInstance(softwareComponent.getRequiredCommunications().get(i), softwareComponentName, i))
                .forEach(requiredCommunicationInstance -> softwareComponentInstance.getRequiredCommunicationInstances().add(requiredCommunicationInstance));

        //Create RequiredHostInstance
        softwareComponentInstance.setRequiredHostInstance(getRequiredHostInstance(softwareComponent.getRequiredHost(), softwareComponentName, 0));

        return softwareComponentInstance;
    }

    private RequiredHostInstance getRequiredHostInstance(RequiredHost requiredHost, String prefix, int requiredHostInstanceNo) {
        RequiredHostInstance requiredHostInstance = DeploymentFactory.eINSTANCE.createRequiredHostInstance();
        requiredHostInstance.setType(requiredHost);
        requiredHostInstance.setName(CamelInstanceNamingService.createRequiredHostName(prefix,
                requiredHostInstance.getName(), requiredHostInstanceNo));
        return requiredHostInstance;
    }

    private RequiredCommunicationInstance createRequiredCommunicationInstance(RequiredCommunication requiredCommunication,
                                                                              String prefix, int requiredCommunicationInstanceNo) {
        RequiredCommunicationInstance requiredCommunicationInstance = DeploymentFactory.eINSTANCE.createRequiredCommunicationInstance();
        requiredCommunicationInstance.setType(requiredCommunication);
        requiredCommunicationInstance.setName(CamelInstanceNamingService.createRequiredCommunicationName(prefix,
                prefix, requiredCommunicationInstanceNo));
        return requiredCommunicationInstance;
    }

    private ProvidedCommunicationInstance createProvidedCommunicationInstance(ProvidedCommunication providedCommunication,
                                                                              String prefix, int providedCommunicationInstanceNo) {
        ProvidedCommunicationInstance providedCommunicationInstance = DeploymentFactory.eINSTANCE.createProvidedCommunicationInstance();
        providedCommunicationInstance.setType(providedCommunication);
        providedCommunicationInstance.setName(CamelInstanceNamingService.createProvidedCommunicationName(prefix,
                prefix, providedCommunicationInstanceNo));
        return providedCommunicationInstance;
    }

    private List<CommunicationInstance> createCommunicationInstanceFromDemand(Communication com, DeploymentInstanceModel deploymentInstanceModel,
                                                                              List<SoftwareComponentInstance> softwareComponentInstances) {
        // Gathering information
        FullCommunication fullCommunication = FullCommunication.fromCommunication(com);

        List<SoftwareComponentInstance> reqInstances;
        List<SoftwareComponentInstance> provInstances;
        if (softwareComponentInstances == null) {
            reqInstances = findComponentInstanceFromDeploymentInstanceModels(fullCommunication.reqComponent, deploymentInstanceModel);
            provInstances = findComponentInstanceFromDeploymentInstanceModels(fullCommunication.provComponent, deploymentInstanceModel);
        } else {
            reqInstances = findComponentInstanceFromComponents(fullCommunication.reqComponent, softwareComponentInstances);
            provInstances = findComponentInstanceFromComponents(fullCommunication.provComponent, softwareComponentInstances);
        }

        if (CollectionUtils.isEmpty(reqInstances) || CollectionUtils.isEmpty(provInstances)) {
            log.info("WARNING: ignoring communication {}", com.getName());
            return Collections.emptyList();
        }

        log.debug("Looking for ComPI...");
        List<CommunicationPortInstance> providedCommunicationPortInstances = provInstances.stream()
                .map(softwareComponentInstance -> findCommunicationPortInstanceFor(fullCommunication.communication.getProvidedCommunication(), softwareComponentInstance.getProvidedCommunicationInstances()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<CommunicationPortInstance> requiredCommunicationPortInstances = provInstances.stream()
                .map(softwareComponentInstance -> findCommunicationPortInstanceFor(fullCommunication.communication.getRequiredCommunication(), softwareComponentInstance.getRequiredCommunicationInstances()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        // Creating Communication Instances
        List<CommunicationInstance> communicationInstances = new ArrayList<>();

        int cnt=0;
        for(CommunicationPortInstance providedPI : providedCommunicationPortInstances) {
            for(CommunicationPortInstance requiredPI : requiredCommunicationPortInstances) {
                CommunicationInstance communicationInstance = getCommunicationInstance(fullCommunication, fullCommunication.communication.getName() + "Instance_" + cnt, (ProvidedCommunicationInstance) providedPI, (RequiredCommunicationInstance) requiredPI);
                communicationInstances.add(communicationInstance);
                cnt++;
            }
        }

        return communicationInstances;
    }

    private CommunicationInstance getCommunicationInstance(FullCommunication fullCommunication, String name, ProvidedCommunicationInstance providedPI, RequiredCommunicationInstance requiredPI) {
        CommunicationInstance communicationInstance = DeploymentFactory.eINSTANCE.createCommunicationInstance();
        communicationInstance.setName(name);
        communicationInstance.setProvidedCommunicationInstance(providedPI);
        communicationInstance.setRequiredCommunicationInstance(requiredPI);
        communicationInstance.setType(fullCommunication.communication);

        log.debug("Creating CommunicationInstance {}", communicationInstance.getName());
        return communicationInstance;
    }

    private List<SoftwareComponentInstance> findComponentInstanceFromDeploymentInstanceModels(Component component, DeploymentInstanceModel deploymentInstanceModel) {
        return findComponentInstanceFromComponents(component, deploymentInstanceModel.getSoftwareComponentInstances());
    }

    private List<SoftwareComponentInstance> findComponentInstanceFromComponents(Component component, List<SoftwareComponentInstance> softwareComponentInstances) {
        StringBuilder logTxt = new StringBuilder();
        List<SoftwareComponentInstance> softwareCIs = softwareComponentInstances
                .stream()
                .peek(softwareComponentInstance -> {
                    log.debug("finComponentInstance: testing {} of type {}", softwareComponentInstance.getName(), softwareComponentInstance.getType().getName());
                    logTxt.append("Compare ").append(softwareComponentInstance.getType()).append(" AND ").append(component);
                })
                .filter(softwareComponentInstance -> softwareComponentInstance.getType().getName().equals(component.getName()))
                .peek(softwareComponentInstance -> log.info("Ok Component Instance Find {}", logTxt))
                .collect(Collectors.toList());

        if (softwareCIs.isEmpty()) {
            log.info("**WARNING. Component Instance not found for component : {}", component.getName());
        }
        return softwareCIs;
    }

    private CommunicationPortInstance findCommunicationPortInstanceFor(CommunicationPort communication,
                                                                       List<? extends CommunicationPortInstance> requiredCommunicationInstances) {
        if(communication == null) {
            log.error("Try to find Communication port instance with communication port equal to null!!");
            return null;
        }

        return requiredCommunicationInstances.stream()
                .filter(requiredCommunicationInstance -> (requiredCommunicationInstance).getType().getName().equals(communication.getName()))
                .findFirst()
                .orElse(null);
    }

    private void changeNames(List<SoftwareComponentInstance> componentsToRegister, CamelModel camelModel) {
        CdoTool.getLastElementAsOptional(camelModel.getExecutionModels())
                .flatMap(CdoTool::getCurrentlyInstalledModel)
                .ifPresent(deploymentInstanceModel -> {
                    //1. Component
                    changeNames(componentsToRegister, deploymentInstanceModel.getSoftwareComponentInstances(), VMKey::new);
                });
    }

    private <T extends Feature> void changeNames(List<T> newInstances, List<T> oldInstances, Function<T, VMKey> function) {
        Map<VMKey, List<T>> newVmTemporaryMap = createInstanceMap(newInstances, function);
        Map<VMKey, List<T>> deployedInstances = createInstanceMap(oldInstances, function);
        for (VMKey vmKey : deployedInstances.keySet()) {
            List<T> oldVmInstances = deployedInstances.get(vmKey);
            List<T> newVmInstances = newVmTemporaryMap.getOrDefault(vmKey, Collections.emptyList());

            for (int i = 0; i < oldVmInstances.size(); i++) {
                if (newVmInstances.size() > i) {
                    T newVmInstance = newVmInstances.get(i);
                    newVmInstance.setName(oldVmInstances.get(i).getName());
                }
            }
        }
    }

    private <T extends Feature> Map<VMKey, List<T>> createInstanceMap(List<T> vmInstancesToRegister, Function<T, VMKey> function) {
        Map<VMKey, List<T>> result = new HashMap<>();

        for (T instance : vmInstancesToRegister) {
            VMKey vmKey = function.apply(instance);

            if (!result.containsKey(vmKey)) {
                result.put(vmKey, new ArrayList<>());
            }
            result.get(vmKey).add(instance);
        }
        return result;
    }

    @Getter
    @EqualsAndHashCode
    private class VMKey {

        private String name;
        private String nodeCandidateMd5;

        VMKey(SoftwareComponentInstance softwareComponentInstance) {
            this.name = removeSuffixFromInstance(softwareComponentInstance.getName());
            String nodeCandidate = camelEnricherService.fetch("nodeCandidate", softwareComponentInstance);
            this.nodeCandidateMd5 = DigestUtils.md5Hex(nodeCandidate).toUpperCase();
        }

        private String removeSuffixFromInstance(String vmName) {
            //we need to remove everything after last two separator sings
            return removeSuffix(removeSuffix(vmName));
        }

        private String removeSuffix(String name) {
            return StringUtils.substringBeforeLast(name, SEPARATOR_NAME_SIGN);
        }
    }

    @Getter
    @Builder
    private static class FullCommunication {
        private Component provComponent;
        private Component reqComponent;
        private Communication communication;

        private static FullCommunication fromCommunication(Communication com) {
            SoftwareComponent internalComponentProv = findProvidedComponentFromCommunication(com);
            SoftwareComponent internalComponentReq = findRequiredComponentFromCommunication(com);
            log.debug("--> {} -- {}", internalComponentProv.getName(), internalComponentReq.getName());

            return FullCommunication.builder()
                    .communication(com)
                    .reqComponent(internalComponentReq)
                    .provComponent(internalComponentProv)
                    .build();
        }

        private static SoftwareComponent findProvidedComponentFromCommunication(Communication com) {
            return (SoftwareComponent) (com.getProvidedCommunication().eContainer());
        }

        private static SoftwareComponent findRequiredComponentFromCommunication(Communication com) {
            return (SoftwareComponent) (com.getRequiredCommunication().eContainer());
        }
    }

    private static class Counters {

        private AtomicInteger globalCounter;
        private AtomicInteger localCounter;

        Counters(int globalCount) {
            globalCounter = new AtomicInteger(globalCount);
            localCounter = new AtomicInteger(0);
        }

        private int getLocalCount() {
            return localCounter.getAndIncrement();
        }

        private int getGlobalCount() {
            return globalCounter.get();
        }

    }

}
