package eu.melodic.upperware.solvertodeployment.utils;

import camel.core.CamelModel;
import camel.core.Feature;
import camel.deployment.*;
import com.google.common.collect.Sets;
import eu.melodic.cache.NodeCandidatePredicates;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.solvertodeployment.db.lib.DataUpdater;
import eu.melodic.upperware.solvertodeployment.exception.S2DException;
import eu.paasage.upperware.metamodel.cp.CpVariableValue;
import eu.paasage.upperware.metamodel.cp.Solution;
import eu.paasage.upperware.metamodel.types.LongValueUpperware;
import eu.passage.upperware.commons.model.tools.CPModelTool;
import eu.passage.upperware.commons.model.tools.CdoTool;
import io.github.cloudiator.rest.model.NodeCandidate;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class DataUtils {

    private DataUpdater dataUpdater;
    private CamelInstanceService camelInstanceService;

    public DataHolder computeDatasToRegister(DeploymentTypeModel deploymentTypeModel, DeploymentInstanceModel deploymentInstanceModel,
                                                    Solution solution, String camelModelId, NodeCandidates nodeCandidates, CDOTransaction transaction) {

        // Analyzing the model for LOCAL group, ie component connected by LOCAL communication
        // component i => i
        Map<String, Integer> localComponentGroups = new HashMap<>();
        // i => { components }
        Map<Integer, Set<String>> localGroups = new HashMap<>();
        // Init

        List<SoftwareComponent> softwareComponents = deploymentTypeModel.getSoftwareComponents();
        for (int i = 0; i < softwareComponents.size(); i++) {
            String componentName = softwareComponents.get(i).getName();
            localComponentGroups.put(componentName, i);
            localGroups.put(i, Sets.newHashSet(componentName));
        }

        localComponentGroups.forEach((name, index) -> log.info("componentGroups: <{}, {}>", name, index));
        localGroups.forEach((index, set) -> log.info("groups: <{} {} >", index, set));


        //checking if each required port is provided by another component
        Set<Integer> providedPorts = softwareComponents.stream()
                .map(Component::getProvidedCommunications)
                .flatMap(Collection::stream)
                .map(CommunicationPort::getPortNumber)
                .collect(Collectors.toSet());

        softwareComponents.forEach(softwareComponent -> softwareComponent.getRequiredCommunications()
                .stream()
                .filter(requiredCommunication -> !providedPorts.contains(requiredCommunication.getPortNumber()))
                .forEach(requiredCommunication -> {
                    log.error("Problem with S2D: Port number {} required by {} is not provided by any component", requiredCommunication.getPortNumber(), softwareComponent.getName());
                }));

        // Preparing VMInstance memory
        int key = 0;
        for (SoftwareComponent sc : softwareComponents) {
            if (localComponentGroups.get(sc.getName()) == key) {
                String msg = String.join(" ", localGroups.get(key));
                log.info("Group {}: {}", key, msg);
            }
            key++;
        }

        // Memory of instances
        Map<String, List<CpVariableValue>> vvByComponentName = CPModelTool.groupVariableValuesByAppName(solution.getVariableValue());

        try {
            DataHolder dataHolder = new DataHolder();
            for (Map.Entry<String, List<CpVariableValue>> entry : vvByComponentName.entrySet()) {
                String componentName = entry.getKey();

                int cardinality = CPModelTool.getIntValue(
                        CPModelTool.getCardinality(entry.getValue()).orElseThrow(() -> new S2DException(String.format("Could not find cardinality for component %s", entry.getKey()))));

                if (cardinality > 0) {
                    NodeCandidate nodeCandidate = getNodeCandidate(nodeCandidates, entry, componentName);
                    dataHolder.getComponentInstancesToRegister().addAll(camelInstanceService.createSoftwareComponentInstances(componentName, deploymentTypeModel, cardinality, nodeCandidate));
                }
            }

            log.debug("2. Dealing with Communication Instances");
            for (Communication communication : deploymentTypeModel.getCommunications()) {
                log.info("2a Dealing with communication: {}", communication.getName());
                dataHolder.getCommunicationInstances().addAll(camelInstanceService.createCommunicationInstanceFromDemand(communication, deploymentInstanceModel, dataHolder.getComponentInstancesToRegister()));
            }
            log.debug("3. Changing names.");
            changeNames(dataHolder, camelModelId, transaction);
            log.debug("4. Done.");
            return dataHolder;

        } catch (Exception e) {
            log.error("Problem with S2D: ", e);
        }
        return null;
    }

    private NodeCandidate getNodeCandidate(NodeCandidates nodeCandidates, Map.Entry<String, List<CpVariableValue>> entry, String componentName) throws S2DException {
        int providerId = CPModelTool.getIntValue(CPModelTool.getProviderId(entry.getValue())
                .orElseThrow(() -> new S2DException(String.format("Could not find provider for component %s", entry.getKey()))));

        Predicate<NodeCandidate>[] nodeCandidatePredicates = getNodeCandidatePredicates(entry.getValue());

        log.info(Arrays.stream(nodeCandidatePredicates)
                .map(Object::toString)
                .collect(Collectors.joining(",", "Filtering node candidates by component " + componentName + ", provider with id: " + providerId + " and " + nodeCandidatePredicates.length + " predicates [", "]")));

        NodeCandidate nodeCandidate = nodeCandidates.getCheapest(componentName, providerId, nodeCandidatePredicates)
                .orElseThrow(() -> new S2DException(String.format("Could not find cheapest nodeCandidate for component %s, provider with index %d and %d predicates", componentName, providerId, nodeCandidatePredicates.length)));

        log.info("Found Node Candidate: {}", nodeCandidate);
        return nodeCandidate;
    }

    private Predicate<NodeCandidate>[] getNodeCandidatePredicates(List<CpVariableValue> variableValues) {
        List<Predicate<NodeCandidate>> result = new ArrayList<>();

        CPModelTool.getCores(variableValues)
                .ifPresent(variableValue -> result.add(NodeCandidatePredicates.getCoresPredicate(CPModelTool.getIntValue(variableValue))));

        CPModelTool.getRam(variableValues)
                .ifPresent(variableValue -> result.add(NodeCandidatePredicates
                        .getRamPredicate(variableValue instanceof LongValueUpperware ? CPModelTool.getLongValue(variableValue) : CPModelTool.getIntValue(variableValue))));

        CPModelTool.getStorage(variableValues)
                .ifPresent(variableValue -> result.add(NodeCandidatePredicates.getStoragePredicate(CPModelTool.getIntValue(variableValue))));

        CPModelTool.getOs(variableValues)
                .ifPresent(variableValue -> result.add(NodeCandidatePredicates.getOsPredicate(CPModelTool.getIntValue(variableValue))));

        return result.toArray(new Predicate[result.size()]);
    }

    private void changeNames(DataHolder result, String camelModelID, CDOTransaction transaction) {
        CamelModel camelModel = CdoTool.getCamelModelById(transaction, camelModelID);

        CdoTool.getLastElementAsOptional(camelModel.getExecutionModels()).ifPresent(executionModel1 ->
            CdoTool.getCurrentlyInstalledModel(executionModel1).ifPresent(deploymentInstanceModel -> {
                //1. Component
                changeNames(result.getComponentInstancesToRegister(), deploymentInstanceModel.getSoftwareComponentInstances(), VMKey::getInstance);
            })
        );
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

    public void registerDataHolderToCDO(String camelModelID, DataHolder dataholder, CDOTransaction transaction) {
        dataUpdater.registerElements(dataholder, camelModelID, transaction);
    }

    @Getter
    @AllArgsConstructor
    @EqualsAndHashCode
    private static class VMKey {

        private String name;

        private static VMKey getInstance(SoftwareComponentInstance softwareComponentInstance) {
            String name = removeSuffixFromInstance(softwareComponentInstance.getName());
            return new VMKey(name);
        }
        private static String removeSuffixFromInstance(String vmName) {
            //we need to remove everything after last two '_'
            return removeSuffix(removeSuffix(vmName));
        }

        private static String removeSuffix(String name) {
            return StringUtils.substringBeforeLast(name, "_");
        }
    }

}