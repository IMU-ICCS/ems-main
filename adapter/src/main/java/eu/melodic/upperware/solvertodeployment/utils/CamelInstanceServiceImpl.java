package eu.melodic.upperware.solvertodeployment.utils;

import camel.deployment.*;
import com.google.gson.Gson;
import eu.melodic.upperware.solvertodeployment.exception.S2DException;
import io.github.cloudiator.rest.model.NodeCandidate;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.String.format;

@Service
@Slf4j
public class CamelInstanceServiceImpl implements CamelInstanceService {

    private ProviderEnricherService providerEnricherService;

    private AtomicInteger globalCount = new AtomicInteger(0);
    private AtomicInteger localCount = new AtomicInteger(-1);

    @Override
    public void resetGlobalCount() {
        globalCount = new AtomicInteger(0);
    }

    @Override
    public void setGlobalDMIdx(int idx) {
        localCount = new AtomicInteger(idx);
    }

    private int getGlobalDMIdx() {
        return localCount.get();
    }

    private int getGlobalCount() {
        return globalCount.getAndIncrement();
    }

    private String getGlobalSuffix() {
        return getGlobalDMIdx() + "_" + getGlobalCount();
    }

    @Override
    public SoftwareComponentInstance createSCInstance(SoftwareComponent softwareComponent) {
        // Create Instance + name + type
        SoftwareComponentInstance softwareComponentInstance = DeploymentFactory.eINSTANCE.createSoftwareComponentInstance();
        softwareComponentInstance.setName(softwareComponent.getName() + "Instance_" + getGlobalSuffix());
        softwareComponentInstance.setType(softwareComponent);

        //Create ProvidedCommunicationInstance
        softwareComponent.getProvidedCommunications()
                .stream()
                .map(this::createProvidedCommunicationInstance)
                .forEach(providedCommunicationInstance -> softwareComponentInstance.getProvidedCommunicationInstances().add(providedCommunicationInstance));

        //Create RequiredCommunicationInstance
        softwareComponent.getRequiredCommunications()
                .stream()
                .map(this::createRequiredCommunicationInstance)
                .forEach(requiredCommunicationInstance -> softwareComponentInstance.getRequiredCommunicationInstances().add(requiredCommunicationInstance));

        //Create RequiredHostInstance
        softwareComponentInstance.setRequiredHostInstance(getRequiredHostInstance(softwareComponent));

        return softwareComponentInstance;
    }

    private RequiredHostInstance getRequiredHostInstance(SoftwareComponent softwareComponent) {
        RequiredHostInstance requiredHostInstance = DeploymentFactory.eINSTANCE.createRequiredHostInstance();
        requiredHostInstance.setType(softwareComponent.getRequiredHost());
        requiredHostInstance.setName(softwareComponent.getName() + "RequiredHostInstance_" + getGlobalCount());
        return requiredHostInstance;
    }

    private RequiredCommunicationInstance createRequiredCommunicationInstance(RequiredCommunication requiredCommunication) {
        RequiredCommunicationInstance requiredCommunicationInstance = DeploymentFactory.eINSTANCE.createRequiredCommunicationInstance();
        requiredCommunicationInstance.setType(requiredCommunication);
        requiredCommunicationInstance.setName(requiredCommunication.getName() + "ReqCommunicationInstance_" + getGlobalCount());
        return requiredCommunicationInstance;
    }

    private ProvidedCommunicationInstance createProvidedCommunicationInstance(ProvidedCommunication providedCommunication) {
        ProvidedCommunicationInstance providedCommunicationInstance = DeploymentFactory.eINSTANCE.createProvidedCommunicationInstance();
        providedCommunicationInstance.setType(providedCommunication);
        providedCommunicationInstance.setName(providedCommunication.getName() + "ProvidedCommunicationInstance_" + getGlobalCount());
        return providedCommunicationInstance;
    }

    @Override
    public List<SoftwareComponentInstance> createSoftwareComponentInstances(String componentName, DeploymentTypeModel deploymentTypeModel, int cardinality, NodeCandidate nodeCandidate) throws S2DException {
        SoftwareComponent softwareComponent = findSoftwareComponent(deploymentTypeModel, componentName);
        return IntStream.range(0, cardinality)
                .mapToObj(value -> createSCInstance(softwareComponent))
                .peek(softwareComponentInstance -> providerEnricherService.enrich(softwareComponentInstance, "nodeCandidate", new Gson().toJson(nodeCandidate)))
                .collect(Collectors.toList());
    }

    private SoftwareComponent findSoftwareComponent(DeploymentTypeModel deploymentTypeModel, String componentName) throws S2DException {
        return deploymentTypeModel.getSoftwareComponents().stream()
                .filter(internalComponent -> internalComponent.getName().equalsIgnoreCase(componentName))
                .findFirst()
                .orElseThrow(() -> new S2DException(format("Unable to find %s component in camel model", componentName)));
    }

    @Override
    public List<CommunicationInstance> createCommunicationInstanceFromDemand(Communication com, DeploymentInstanceModel deploymentInstanceModel,
                                                                             List<SoftwareComponentInstance> softwareComponentInstances) throws S2DException {
        // Gathering information
        FullCommunication fullCommunication = FullCommunication.fromCommunication(com);

        List<CommunicationInstance> communicationInstances = new ArrayList<>();

        List<SoftwareComponentInstance> reqInstances = null;
        List<SoftwareComponentInstance> provInstances = null;
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
        List<CommunicationPortInstance> providedCommunicationPortInstances = new ArrayList<>();
        List<CommunicationPortInstance> requiredCommunicationPortInstances = new ArrayList<>();

        for (SoftwareComponentInstance iCI : provInstances) {
            CommunicationPortInstance providedCommunicationPortInstance = findCommunicationPortInstanceFor(fullCommunication.communication.getProvidedCommunication(), iCI.getProvidedCommunicationInstances());
            if (providedCommunicationPortInstance!=null) {
                providedCommunicationPortInstances.add(providedCommunicationPortInstance);
            } else {
                log.error("Unable to find providedCommunicationPortInstance for {} for communication {}", iCI.getName(), com.getName());
            }
        }

        for (SoftwareComponentInstance iCI : reqInstances) {
            CommunicationPortInstance requiredCommunicationPortInstance = findCommunicationPortInstanceFor(fullCommunication.communication.getRequiredCommunication(), iCI.getRequiredCommunicationInstances());
            if (requiredCommunicationPortInstance!=null) {
                requiredCommunicationPortInstances.add(requiredCommunicationPortInstance);
            } else {
                log.error("Unable to find requiredCommunicationPortInstance for {} for communication {}", iCI.getName(), com.getName());
            }
        }

        // Creating Communication Instances
        int cnt=0;
        for(CommunicationPortInstance providedPI : providedCommunicationPortInstances) {
            for(CommunicationPortInstance requiredPI : requiredCommunicationPortInstances) {
                CommunicationInstance communicationInstance = DeploymentFactory.eINSTANCE.createCommunicationInstance();
                communicationInstance.setName(fullCommunication.communication.getName() + "Instance_" + cnt);
                communicationInstance.setProvidedCommunicationInstance((ProvidedCommunicationInstance) providedPI);
                communicationInstance.setRequiredCommunicationInstance((RequiredCommunicationInstance) requiredPI);
                communicationInstance.setType(fullCommunication.communication);

                log.debug("Creating CommunicationInstance {}", communicationInstance.getName());

                communicationInstances.add(communicationInstance);
                cnt++;
            }
        }

        return communicationInstances;
    }

    private static List<SoftwareComponentInstance> findComponentInstanceFromDeploymentInstanceModels(Component component, DeploymentInstanceModel deploymentInstanceModel) {
        List<SoftwareComponentInstance> softwareCIs = new ArrayList<>();

        log.debug("Looking for ComponentInstance (SoftwareCI from DM) for type: {}", component.getName());
        StringBuilder logTxt = new StringBuilder();
        for (SoftwareComponentInstance softwareComponentInstance : deploymentInstanceModel.getSoftwareComponentInstances()) {
            log.debug("finComponentInstance: testing {} of type {}", softwareComponentInstance.getName(), softwareComponentInstance.getType().getName());
            logTxt.append("Compare ").append(softwareComponentInstance.getType()).append(" AND ").append(component);
            if (softwareComponentInstance.getType().getName().equals(component.getName())) {
                log.error("Ok Component Instance Find {}", logTxt);
                softwareCIs.add(softwareComponentInstance);
            }
        }
        if (softwareCIs.isEmpty()) {
            log.info("**WARNING. Component Instance not found for component : {}", component.getName());
        }
        return softwareCIs;
    }

    private static List<SoftwareComponentInstance> findComponentInstanceFromComponents(Component component, List<SoftwareComponentInstance> softwareComponentInstances) {
        List<SoftwareComponentInstance> internalCIs = new ArrayList<>();

        StringBuilder logTxt = new StringBuilder();
        log.debug("Looking for ComponentInstance (InternalCI list) for type: {}", component.getName());
        for (SoftwareComponentInstance internalCI : softwareComponentInstances) {
            log.debug("finComponentInstance: testing {} of type {}", internalCI.getName(), internalCI.getType().getName());
//            logTxt.append("Compare ").append(internalCI.getType()).append(" AND ").append(component);
            if(internalCI.getType().getName().equals(component.getName())) {
                log.debug("Ok Component Instance Find {}", logTxt);
                internalCIs.add(internalCI);
            }
        }
        if (internalCIs.isEmpty())
            log.warn("WARNING. Component Instance not found for component : {}", component.getName());
        return internalCIs;
    }

    private static CommunicationPortInstance findCommunicationPortInstanceFor(CommunicationPort communication,
                                                                              List<? extends CommunicationPortInstance> requiredCommunicationInstances) {
        if(communication == null) {
            log.error("Try to find Communication port instance with communication port equal to null!!");
            return null;
        }

        return requiredCommunicationInstances.stream()
                .filter(requiredCommunicationInstance -> (requiredCommunicationInstance).getType().getName().equals(communication.getName()))
                .findFirst()
                .orElseGet(() -> {
                    log.error("Unable to find CommunicationPortInstance for {}!!", communication.getName());
                    return null;
                });
    }


    @Getter
    @Builder
//    @NoArgsConstructor(access = AccessLevel.PRIVATE)
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
}
