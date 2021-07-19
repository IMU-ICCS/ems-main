package eu.melodic.upperware.adapter.planexecutor.colosseum;

import eu.melodic.upperware.adapter.communication.proactive.ProactiveClientServiceForAdapter;
import eu.melodic.upperware.adapter.exception.AdapterException;
import eu.melodic.upperware.adapter.planexecutor.RunnableTaskExecutor;
import eu.melodic.upperware.adapter.plangenerator.model.AdapterRequirement;
import eu.melodic.upperware.adapter.plangenerator.tasks.NodeTask;
import lombok.extern.slf4j.Slf4j;
import org.activeeon.morphemic.model.NodeCandidate;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
            }
        }
        catch (RuntimeException e) {
            log.error("NodeTaskExecutor->create: [application id: {}] Could not add Node. Error: {}", applicationId, e.getMessage());
            throw new AdapterException(String.format("Problem during adding Node [application id: %s]: %s", applicationId, e.getMessage()), e);
        }
    }

    private void addIAASNode(AdapterRequirement taskBody) {
        JSONArray nodesJSONArray = new JSONArray();
        JSONObject nodeJSON = new JSONObject();
        nodeJSON.put("taskName", taskBody.getTaskName());
        nodeJSON.put("nodeName", taskBody.getNodeName());
        JSONObject nodeCandidateInformationJSON = new JSONObject();
        nodeCandidateInformationJSON.put("ID", checkEmptiness(taskBody.getNodeCandidate().getId(), "ID"));
        nodeCandidateInformationJSON.put("cloudID", checkEmptiness(taskBody.getNodeCandidate().getCloud().getId(), "cloudID"));
        nodeCandidateInformationJSON.put("locationName", checkEmptiness(taskBody.getNodeCandidate().getLocation().getName(), "locationName"));
        nodeCandidateInformationJSON.put("imageProviderId", checkEmptiness(taskBody.getNodeCandidate().getImage().getProviderId(), "imageProviderId"));
        nodeCandidateInformationJSON.put("hardwareProviderId", checkEmptiness(taskBody.getNodeCandidate().getHardware().getProviderId(), "hardwareProviderId"));
        nodeJSON.put("nodeCandidateInformation", nodeCandidateInformationJSON);
        nodesJSONArray.put(nodeJSON);
        log.info("NodeTaskExecutor->addIAASNode: [application id: {}] ProActive node(s) (JSONArray): \n{}", applicationId, nodesJSONArray);

        int status = proactiveClientServiceForAdapter.addNodes(nodesJSONArray, applicationId);

        log.info("NodeTaskExecutor->addIAASNode: [application id: {}] addNodes status= {}", applicationId, status);
    }

    private void addBYONNode(AdapterRequirement taskBody) {
        final Map<String, String> byonIdPerComponent = Collections.singletonMap(taskBody.getNodeCandidate().getId(),
                taskBody.getTaskName());
        log.info("NodeTaskExecutor->addBYONNode: [application id: {}] ProActive byonIdPerComponent= {}", applicationId, byonIdPerComponent);

        int status = proactiveClientServiceForAdapter.addByonNodes(byonIdPerComponent, applicationId);

        log.info("NodeTaskExecutor->addBYONNode: [application id: {}] addByonNodes status= {}", applicationId, status);
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
