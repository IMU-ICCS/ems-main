package eu.melodic.upperware.adapter.planexecutor.colosseum;

import eu.melodic.upperware.adapter.communication.proactive.ProactiveClientServiceForAdapter;
import eu.melodic.upperware.adapter.exception.AdapterException;
import eu.melodic.upperware.adapter.planexecutor.RunnableTaskExecutor;
import eu.melodic.upperware.adapter.plangenerator.model.AdapterRequirement;
import eu.melodic.upperware.adapter.plangenerator.tasks.NodeTask;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.ow2.proactive.sal.model.IaasDefinition;

import java.util.*;
import java.util.concurrent.Future;

@Slf4j
public class NodeTaskExecutor extends RunnableTaskExecutor<AdapterRequirement> {

    private final String applicationId;
    private final ProactiveClientServiceForAdapter proactiveClientServiceForAdapter;

    NodeTaskExecutor(NodeTask task, Collection<Future> predecessors, String applicationId, ProactiveClientServiceForAdapter proactiveClientServiceForAdapter) {
        super(task, predecessors);
        this.applicationId = applicationId;
        this.proactiveClientServiceForAdapter = proactiveClientServiceForAdapter;
    }

    @Override
    public void create(AdapterRequirement taskBody) {
        try {
            // here we create a node for task/component (e.g. Component_App) based on a node candidate and assign it a name
            log.info("NodeTaskExecutor->create: [application id: {}] AdapterRequirement= {}", applicationId, taskBody);

            switch (taskBody.getNodeCandidate().getNodeCandidateType()) {
                case IAAS:
                    log.info("NodeTaskExecutor->create: [application id: {}] adding IAAS node", applicationId);
                    addIAASNode(taskBody);
                    break;
                case BYON:
                    log.info("NodeTaskExecutor->create: [application id: {}] adding BYON node", applicationId);
                    addBYONNode(taskBody);
                    break;
                case EDGE:
                    log.info("NodeTaskExecutor->create: [application id: {}] adding EDGE node", applicationId);
                    addEDGENode(taskBody);
                    break;
            }
        }
        catch (RuntimeException e) {
            log.error("NodeTaskExecutor->create: [application id: {}] Could not add Node. Error: {}", applicationId, e.getMessage());
            throw new AdapterException(String.format("Problem during adding Node [application id: %s]: %s", applicationId, e.getMessage()), e);
        }
    }

    private void addIAASNode(AdapterRequirement taskBody) {
        List<IaasDefinition> iaasDefinitionList = new LinkedList<>();
        IaasDefinition iaasDefinition = new IaasDefinition(
                taskBody.getNodeName(),
                taskBody.getTaskName(),
                checkEmptiness(taskBody.getNodeCandidate().getId(), "nodeCandidateId"),
                checkEmptiness(taskBody.getNodeCandidate().getCloud().getId(), "cloudID")
        );
        iaasDefinitionList.add(iaasDefinition);
        log.info("NodeTaskExecutor->addIAASNode: [application id: {}] ProActive node(s) (JSONArray): \n{}", applicationId, Arrays.toString(iaasDefinitionList.toArray()));

        int status = proactiveClientServiceForAdapter.addNodes(iaasDefinitionList, applicationId);

        log.info("NodeTaskExecutor->addIAASNode: [application id: {}] addNodes status= {}", applicationId, status);
    }

    private void addBYONNode(AdapterRequirement taskBody) {
        String byonId = proactiveClientServiceForAdapter.getByonNodeList(applicationId).stream()
                .filter(byonNode -> byonNode.getNodeCandidate().getId().equals(taskBody.getNodeCandidate().getId()))
                .findFirst()
                .orElseThrow(() -> new AdapterException(String.format("Could not find BYON with associated NodeCandidate id=%s", taskBody.getNodeCandidate().getId())))
                .getId();

        final Map<String, String> byonIdPerComponent = Collections.singletonMap(byonId,
                String.join("/", taskBody.getNodeName(), taskBody.getTaskName()));
        log.info("NodeTaskExecutor->addBYONNode: [application id: {}] ProActive byonIdPerComponent= {}", applicationId, byonIdPerComponent);

        int status = proactiveClientServiceForAdapter.addByonNodes(byonIdPerComponent, applicationId);

        log.info("NodeTaskExecutor->addBYONNode: [application id: {}] addByonNodes status= {}", applicationId, status);
    }

    private void addEDGENode(AdapterRequirement taskBody) {
        String edgeId = proactiveClientServiceForAdapter.getEdgeNodeList(applicationId).stream()
                .filter(edgeNode -> edgeNode.getNodeCandidate().getId().equals(taskBody.getNodeCandidate().getId()))
                .findFirst()
                .orElseThrow(() -> new AdapterException(String.format("Could not find EDGE with associated NodeCandidate id=%s", taskBody.getNodeCandidate().getId())))
                .getId();

        final Map<String, String> edgeIdPerComponent = Collections.singletonMap(edgeId,
                String.join("/", taskBody.getNodeName(), taskBody.getTaskName()));
        log.info("NodeTaskExecutor->addEDGENode: [application id: {}] ProActive edgeIdPerComponent= {}", applicationId, edgeIdPerComponent);

        int status = proactiveClientServiceForAdapter.addEdgeNodes(edgeIdPerComponent, applicationId);

        log.info("NodeTaskExecutor->addEDGENode: [application id: {}] addEDGENodes status= {}", applicationId, status);
    }
    @Override
    public void delete(AdapterRequirement taskBody) {
        try {
            log.info("NodeTaskExecutor->delete: [application id: {}] AdapterRequirement= {}", applicationId, taskBody);
            List<String> nodeNames = Collections.singletonList(taskBody.getNodeName());
            log.info("NodeTaskExecutor->delete: [application id: {}] nodeNames= {}", applicationId, nodeNames);

            int status = proactiveClientServiceForAdapter.removeNodes(nodeNames);

            log.info("NodeTaskExecutor->delete: [application id: {}] removeNodes status= {}", applicationId, status);
        }
        catch (RuntimeException e) {
            log.error("NodeTaskExecutor->delete: [application id: {}] Could not remove Node. Error: {}", applicationId, e.getMessage());
            throw new AdapterException(String.format("Problem during removing Node [application id: %s]: %s", applicationId, e.getMessage()), e);
        }
    }

    private String checkEmptiness(String text, String type) throws AdapterException {
        if (StringUtils.isNotEmpty(text))
            return text;
        else {
            throw new AdapterException(String.format("Value of type: %s cannot be empty [application id: %s]", type, applicationId));
        }
    }
}
