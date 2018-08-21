package eu.paasage.upperware.solvertodeployment.utils;

import camel.core.CamelModel;
import camel.core.Feature;
import camel.deployment.*;
import com.google.common.collect.Sets;
import eu.melodic.cache.NodeCandidatePredicates;
import eu.melodic.cache.NodeCandidates;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.CpVariableValue;
import eu.paasage.upperware.metamodel.cp.Solution;
import eu.paasage.upperware.solvertodeployment.db.lib.CDODatabaseProxy2New;
import eu.paasage.upperware.solvertodeployment.lib.CommunicationProvidedRequiredDomain;
import eu.paasage.upperware.solvertodeployment.lib.S2DException;
import eu.paasage.upperware.solvertodeployment.properties.SolverToDeploymentProperties;
import eu.passage.upperware.commons.model.tools.CPModelTool;
import io.github.cloudiator.rest.model.NodeCandidate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.common.util.EList;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
public class DataUtilsNew {

    private static final String VM_PREFIX_NAME = "WM";
    private static final String COMPONENT_PREFIX_NAME = "Component";

    public static DataHolderNew computeDatasToRegister(DeploymentTypeModel deploymentTypeModel, DeploymentInstanceModel deploymentInstanceModel,
                                                       ConstraintProblem constraintProblem, Solution solution, CamelModel camelModel, String camelModelId,
                                                       NodeCandidates nodeCandidates, SolverToDeploymentProperties solverToDeploymentProperties,
                                                       CDOTransaction transaction
    ) {
        // Analyzing the model for LOCAL group, ie component connected by LOCAL communication
        // component i => i
        Map<String, Integer> localComponentGroups = new HashMap<>();
        // i => { components }
        Map<Integer, Set<String>> localGroups = new HashMap<>();
        // Init

        EList<SoftwareComponent> softwareComponents = deploymentTypeModel.getSoftwareComponents();
        for (int i = 0; i < softwareComponents.size(); i++) {
            String componentName = softwareComponents.get(i).getName();
            localComponentGroups.put(componentName, i);
            localGroups.put(i, Sets.newHashSet(componentName));
        }

        localComponentGroups.forEach((name, index) -> log.info("componentGroups: <{}, {}>", name, index));
        localGroups.forEach((index, set) -> log.info("groups: <{} {} >", index, set));


        //checking if each required port is provided by another component
        Set<Integer> providedPorts = new HashSet<>();
        softwareComponents.forEach(softwareComponent ->
                softwareComponent.getProvidedCommunications()
                        .forEach(providedCommunication -> providedPorts.add(providedCommunication.getPortNumber()))
        );

        softwareComponents.forEach(softwareComponent -> {
            softwareComponent.getRequiredCommunications()
                    .forEach(requiredCommunication -> {
                        if (!providedPorts.contains(requiredCommunication.getPortNumber())) {
                            try {
                                throw new S2DException(String.format("Port number %d required by %s is not provided by any component", requiredCommunication.getPortNumber(), softwareComponent.getName()));
                            } catch (S2DException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        });


        // Merging sets
//        for (Communication communication : deploymentTypeModel.getCommunications()) {
//            String provName = CloudMLHelperNew.findProvidedComponentFromCommunication(communication).getName();
//            int provId = localComponentGroups.get(provName);
//            String reqName = CloudMLHelperNew.findRequiredComponentFromCommunication(communication).getName();
//            int reqId = localComponentGroups.get(reqName);
//            if (provId == reqId) continue; // already merge
//            if (provId < reqId) {
//                for (Map.Entry<String, Integer> entry : localComponentGroups.entrySet()) {
//                    if (entry.getValue() == reqId)
//                        entry.setValue(provId);
//                }
//                // merge all elements of reqId into provId
//                localGroups.get(provId).addAll(localGroups.get(reqId));
//                // reqId & provId use the same set
//                localGroups.put(reqId, localGroups.get(provId));
//            } else {
//                for (Map.Entry<String, Integer> entry : localComponentGroups.entrySet()) {
//                    if (entry.getValue() == provId)
//                        entry.setValue(reqId);
//                }
//                // merge all elements of reqId into provId
//                localGroups.get(provId).addAll(localGroups.get(reqId));
//                // reqId & provId use the same set
//                localGroups.put(reqId, localGroups.get(provId));
//            }
//
//        }

        // Preparing VMInstance memory
        int key = 0;
        for (SoftwareComponent sc : softwareComponents) {
            if (localComponentGroups.get(sc.getName()) == key) {
                String msg = localGroups.get(key).stream().collect(Collectors.joining(" "));
                log.info("Group {}: {}", key, msg);
            }
            key++;
        }

        // Memory of instances
        Map<Integer, EList<VMInstance>> localGroupVMInstances = new HashMap<>();
        Map<String, List<CpVariableValue>> vvByComponentName = CPModelTool.groupVariableValuesByAppName(solution.getVariableValue());

        try {
            DataHolderNew dataHolder = new DataHolderNew();
            for (Map.Entry<String, List<CpVariableValue>> entry : vvByComponentName.entrySet()) {
                String componentName = entry.getKey();


                int cardinality = CPModelTool.getIntValue(CPModelTool.getCardinality(entry.getValue()).orElseThrow(() -> new S2DException(String.format("Could not find cardinality for component %s", entry.getKey()))));
                if (cardinality > 0) {
                    int providerId = CPModelTool.getIntValue(CPModelTool.getProviderId(entry.getValue())
                            .orElseThrow(() -> new S2DException(String.format("Could not find provider for component %s", entry.getKey()))));

                    Predicate<NodeCandidate>[] nodeCandidatePredicates = getNodeCandidatePredicates(entry.getValue());

                    log.info(Arrays.stream(nodeCandidatePredicates)
                            .map(Object::toString)
                            .collect(Collectors.joining(",", "Filtering node candidates by component " + componentName + ", provider with id: " + providerId + " and " + nodeCandidatePredicates.length + " predicates [", "]")));

                    NodeCandidate nodeCandidate = nodeCandidates.getCheapest(componentName, providerId, nodeCandidatePredicates)
                            .orElseThrow(() -> new S2DException(String.format("Could not find cheapest nodeCandidate for component %s, provider with index %d and %d predicates", componentName, providerId, nodeCandidatePredicates.length)));

                    log.info("Found Node Candidate: {}", nodeCandidate);

                        EList<SoftwareComponentInstance> softwareComponentInstances = SolverToDeploymentHelperNew.createSoftwareComponentInstance(componentName, deploymentTypeModel, cardinality);
                        dataHolder.getComponentInstancesToRegister().addAll(softwareComponentInstances);


                        //create VM Instance
                        int myKey = localComponentGroups.get(componentName);
                    localComponentGroups.forEach((name, index) -> log.info("componentGroups: <{}, {}>", name, index));
                        EList<VMInstance> vmInstanceToRegisters = localGroupVMInstances.get(myKey);
                    log.info("LocalGroupVMInstances:");
                    localGroupVMInstances.forEach((integer, vmInstances) -> vmInstances
                            .forEach(vmInstance -> log.info("key: {}, vmInstance: {}", integer, vmInstance.getName())));
                        log.info("VMs for key {}: {}", myKey, vmInstanceToRegisters);

                    log.info("Creating VmInstances for component: {} with key: {}", componentName, myKey);

                    if (vmInstanceToRegisters != null) {
                        log.info("vmInstanceToRegisters list: ");
                        vmInstanceToRegisters.forEach(vmInstance -> log.info("Instance: {}", vmInstance.getName()));
                    }

                        if (vmInstanceToRegisters == null) {
                            log.info("Creating new VM Instances for component: {} ...", componentName);


                            VM vm = findVMByName(deploymentTypeModel.getVms(), componentName);

                            if (vm == null) {
                                log.info("Vm does not exist");
                                log.info("Component name: {}", componentName);
                                log.info("Number of VMs in list: {}, vm names: ", deploymentTypeModel.getVms().size());
                                deploymentTypeModel.getVms().forEach(vm1 ->
                                        log.info(vm1.getName()));
                            }

                            ProviderEnricherServiceImpl providerEnricherService = new ProviderEnricherServiceImpl(solverToDeploymentProperties);

                            vmInstanceToRegisters = SolverToDeploymentHelperNew.searchAndCreateVMInstance(vm, cardinality);
                            vmInstanceToRegisters.forEach(vmInstance -> {
                                providerEnricherService.enrichVMInstance(vmInstance, nodeCandidate, constraintProblem.getId(), camelModel);
                                log.info("VmInstance: {}", vmInstance.getName());
                            });
                            dataHolder.getVmInstancesToRegister().addAll(vmInstanceToRegisters);
                            // memorize
                            localGroupVMInstances.put(myKey, vmInstanceToRegisters);
                            log.info("**NEW** VMs for key {}", myKey);
                        }
                    // Create Hosting
                    for (int i = 0; i < cardinality; i++) {
                        SoftwareComponentInstance iCI = softwareComponentInstances.get(i);
                        VMInstance vmI = vmInstanceToRegisters.get(i);
                        dataHolder.getHostingInstancesToRegister().addAll(SolverToDeploymentHelperNew.createHostingInstance(vmI, iCI, deploymentTypeModel));
                    }
                }
            }

            log.debug("2. Dealing with Communication Instances");
            for (Communication communication : deploymentTypeModel.getCommunications()) {
                log.info("2a Dealing with communication: {}", communication.getName());
                EList<CommunicationInstance> communicationInstances = CommunicationProvidedRequiredDomain.createCommunicationInstanceFromDemand(communication, deploymentInstanceModel, dataHolder.getComponentInstancesToRegister());
                dataHolder.getCommunicationInstances().addAll(communicationInstances);
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


    private static Predicate<NodeCandidate>[] getNodeCandidatePredicates(List<CpVariableValue> variableValues) {
        List<Predicate<NodeCandidate>> result = new ArrayList<>();

        CPModelTool.getCores(variableValues)
                .ifPresent(variableValue -> result.add(NodeCandidatePredicates.getCoresPredicate(CPModelTool.getIntValue(variableValue))));

        CPModelTool.getRam(variableValues)
                .ifPresent(variableValue -> result.add(NodeCandidatePredicates.getRamPredicate(CPModelTool.getLongValue(variableValue))));

        CPModelTool.getStorage(variableValues)
                .ifPresent(variableValue -> result.add(NodeCandidatePredicates.getStoragePredicate(CPModelTool.getDoubleValue(variableValue))));

        CPModelTool.getOs(variableValues)
                .ifPresent(variableValue -> result.add(NodeCandidatePredicates.getOsPredicate(CPModelTool.getIntValue(variableValue))));

        return result.toArray(new Predicate[result.size()]);
    }

    private static void changeNames(DataHolderNew result, String camelModelID, CDOTransaction transaction) {
        CDODatabaseProxy2New.getLastDeployedInstanceModel(camelModelID, transaction).ifPresent(deployedModel -> {
            //1. Component
            changeNames(result.getComponentInstancesToRegister(), deployedModel.getSoftwareComponentInstances(), DataUtilsNew.VMKey::getInstance);
        });
    }

    private static VM findVMByName(EList<VM> vms, String componentName) {
        return vms.stream()
                .filter(vm -> vm.getName().replaceFirst(VM_PREFIX_NAME, COMPONENT_PREFIX_NAME).equals(componentName))
                .findFirst().orElse(null);
    }

    private static <T extends Feature> void changeNames(List<T> newInstances, List<T> oldInstances, Function<T, DataUtilsNew.VMKey> function) {
        Map<DataUtilsNew.VMKey, List<T>> newVmTemporaryMap = createInstanceMap(newInstances, function);
        Map<DataUtilsNew.VMKey, List<T>> deployedInstances = createInstanceMap(oldInstances, function);
        for (DataUtilsNew.VMKey vmKey : deployedInstances.keySet()) {
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

    private static <T extends Feature> Map<DataUtilsNew.VMKey, List<T>> createInstanceMap(List<T> vmInstancesToRegister, Function<T, DataUtilsNew.VMKey> function) {
        Map<DataUtilsNew.VMKey, List<T>> result = new HashMap<>();

        for (T instance : vmInstancesToRegister) {
            DataUtilsNew.VMKey vmKey = function.apply(instance);

            if (!result.containsKey(vmKey)) {
                result.put(vmKey, new ArrayList<>());
            }
            result.get(vmKey).add(instance);
        }
        return result;
    }

    public static void registerDataHolderToCDO(String camelModelID, DataHolderNew dataholder, CDOTransaction transaction) {
        new CDODatabaseProxy2New.DataUpdater().registerElements(dataholder, camelModelID, transaction);
    }

    @Getter
    @AllArgsConstructor
    static class VMKey {

        private String name;
        private String type;

        private static DataUtilsNew.VMKey getInstance(SoftwareComponentInstance softwareComponentInstance) {
            String vmName = removeSuffixFromInstance(softwareComponentInstance.getName());
            return new DataUtilsNew.VMKey(vmName, "");
        }

        private static String removeSuffixFromInstance(String vmName) {
            return removeSuffix(removeSuffix(vmName));
        }

        private static String removeSuffix(String vmName) {
            if (vmName != null) {
                int i = vmName.lastIndexOf("_");
                return vmName.substring(0, i);
            }
            return vmName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            DataUtilsNew.VMKey vmKey = (DataUtilsNew.VMKey) o;

            if (name != null ? !name.equals(vmKey.name) : vmKey.name != null) return false;
            return type != null ? type.equals(vmKey.type) : vmKey.type == null;
        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + (type != null ? type.hashCode() : 0);
            return result;
        }
    }

}