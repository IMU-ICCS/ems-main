package eu.melodic.upperware.adapter.service;

import camel.core.CamelModel;
import camel.deployment.*;
import eu.melodic.cache.NodeCandidatePredicates;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.adapter.exception.AdapterException;
import eu.paasage.upperware.metamodel.cp.CpVariableValue;
import eu.paasage.upperware.metamodel.cp.Solution;
import eu.paasage.upperware.metamodel.types.LongValueUpperware;
import eu.passage.upperware.commons.model.tools.CPModelTool;
import eu.passage.upperware.commons.model.tools.CdoTool;
import io.github.cloudiator.rest.model.NodeCandidate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.common.util.EList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class DataUtils {

    private CamelInstanceService camelInstanceService;

    public DeploymentInstanceModel computeDatasToRegister(CamelModel camelModel, Solution solution, NodeCandidates nodeCandidates) {

        EList<DeploymentModel> deploymentModels = camelModel.getDeploymentModels();
        DeploymentTypeModel deploymentTypeModel = (DeploymentTypeModel) CdoTool.getFirstElement(deploymentModels);

        List<SoftwareComponent> softwareComponents = deploymentTypeModel.getSoftwareComponents();

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
                    log.error("Port number {} required by {} is not provided by any component", requiredCommunication.getPortNumber(), softwareComponent.getName());
                }));

        List<SoftwareInstanceDetail> softwareInstanceDetails = new ArrayList<>();
        Map<String, List<CpVariableValue>> vvByComponentName = CPModelTool.groupVariableValuesByAppName(solution.getVariableValue());
        try {
            for (Map.Entry<String, List<CpVariableValue>> entry : vvByComponentName.entrySet()) {
                String componentName = entry.getKey();
                int cardinality = CPModelTool.getIntValue(
                        CPModelTool.getCardinality(entry.getValue())
                                .orElseThrow(() -> new AdapterException(String.format("Could not find cardinality for component %s", entry.getKey()))));

                if (cardinality <= 0) {
                    log.warn("Cardinality is {}. Skipping execution for {}", cardinality, componentName);
                    continue;
                }

                NodeCandidate nodeCandidate = getNodeCandidate(nodeCandidates, entry, componentName);
                if (nodeCandidate == null) {
                    log.warn("Node candidate is null. Skipping execution for {}", componentName);
                    continue;
                }

                SoftwareComponent softwareComponent = getSoftwareComponent(camelModel, componentName);
                if (softwareComponent == null) {
                    log.warn("Software component is null. Skipping execution for {}", componentName);
                    continue;
                }

                softwareInstanceDetails.add(
                        new SoftwareInstanceDetail.SoftwareInstanceDetailBuilder()
                            .cardinality(cardinality)
                            .nodeCandidate(nodeCandidate)
                            .softwareComponent(softwareComponent)
                            .build());
            }

            return camelInstanceService.createDeploymentInstanceModel(deploymentTypeModel, softwareInstanceDetails);

        } catch (Exception e) {
            log.error("Problem with Applying Solution: ", e);
        }
        return null;
    }

    private SoftwareComponent getSoftwareComponent(CamelModel camelModel, String componentName) {
        DeploymentTypeModel deploymentTypeModel = (DeploymentTypeModel) CdoTool.getFirstElement(camelModel.getDeploymentModels());
        return deploymentTypeModel.getSoftwareComponents()
                .stream()
                .filter(softwareComponent -> componentName.equals(softwareComponent.getName()))
                .findFirst()
                .orElseThrow(()-> new AdapterException("Could not find Component for " + componentName));
    }

    private NodeCandidate getNodeCandidate(NodeCandidates nodeCandidates, Map.Entry<String, List<CpVariableValue>> entry, String componentName) {
        int providerId = CPModelTool.getIntValue(CPModelTool.getProviderId(entry.getValue())
                .orElseThrow(() -> new AdapterException(String.format("Could not find provider for component %s", entry.getKey()))));

        Predicate<NodeCandidate>[] nodeCandidatePredicates = getNodeCandidatePredicates(entry.getValue());

        log.info(Arrays.stream(nodeCandidatePredicates)
                .map(Object::toString)
                .collect(Collectors.joining(",", "Filtering node candidates by component " + componentName + ", provider with id: " + providerId + " and " + nodeCandidatePredicates.length + " predicates [", "]")));

        NodeCandidate nodeCandidate = nodeCandidates.getCheapest(componentName, providerId, nodeCandidatePredicates)
                .orElseThrow(() -> new AdapterException(String.format("Could not find cheapest nodeCandidate for component %s, provider with index %d and %d predicates", componentName, providerId, nodeCandidatePredicates.length)));

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

}