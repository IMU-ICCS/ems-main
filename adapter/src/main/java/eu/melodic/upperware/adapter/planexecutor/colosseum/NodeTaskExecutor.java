package eu.melodic.upperware.adapter.planexecutor.colosseum;

import eu.melodic.upperware.adapter.communication.colosseum.ColosseumApi;
import eu.melodic.upperware.adapter.exception.AdapterException;
import eu.melodic.upperware.adapter.executioncontext.colosseum.ColosseumContext;
import eu.melodic.upperware.adapter.planexecutor.TaskWatchDog;
import eu.melodic.upperware.adapter.plangenerator.model.AdapterRequirement;
import eu.melodic.upperware.adapter.plangenerator.tasks.CheckFinishTask;
import eu.melodic.upperware.adapter.plangenerator.tasks.NodeTask;
import eu.melodic.upperware.adapter.proactive.client.ProactiveClientService;
import io.github.cloudiator.rest.model.Queue;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;
import java.util.function.Function;

@Slf4j
public class NodeTaskExecutor extends WatchdogColosseumTaskExecutor<AdapterRequirement> implements TaskWatchDog {

    private final String applicationId;
    private final ProactiveClientService proactiveClientService;

    NodeTaskExecutor(NodeTask task, Collection<Future> predecessors, ColosseumApi api,
                     ColosseumContext context, Function<CheckFinishTask, Future<Queue>> checkFinishTaskToFuture, String applicationId, ProactiveClientService proactiveClientService) {
        super(task, predecessors, api, context, checkFinishTaskToFuture);
        this.applicationId = applicationId;
        this.proactiveClientService = proactiveClientService;
    }

    @Override
    public void create(AdapterRequirement taskBody) {
        try {
            // here we create a node for task/component (e.g. Component_App) based on a node candidate and assign it a name
            log.info("NodeTaskExecutor->create: [application id: {}] AdapterRequirement= {}", applicationId, taskBody);

            JSONArray nodesJSONArray = new JSONArray();
            JSONObject nodeJSON = new JSONObject();
            nodeJSON.put("taskName", taskBody.getTaskName());
            nodeJSON.put("nodeName", taskBody.getNodeName());
            JSONObject nodeCandidateInformationJSON = new JSONObject();
            nodeCandidateInformationJSON.put("cloudID", checkEmptiness(taskBody.getNodeCandidate().getCloud().getId(), "cloudID"));
            nodeCandidateInformationJSON.put("locationName", checkEmptiness(taskBody.getNodeCandidate().getLocation().getName(), "locationName"));
            nodeCandidateInformationJSON.put("imageProviderId", checkEmptiness(taskBody.getNodeCandidate().getImage().getProviderId(), "imageProviderId"));
            nodeCandidateInformationJSON.put("hardwareProviderId", checkEmptiness(taskBody.getNodeCandidate().getHardware().getProviderId(), "hardwareProviderId"));
            nodeJSON.put("nodeCandidateInformation", nodeCandidateInformationJSON);
            nodesJSONArray.put(nodeJSON);
            log.info("NodeTaskExecutor->create: [application id: {}] ProActive node(s) (JSONArray): \n{}", applicationId, nodesJSONArray);

            int status = proactiveClientService.addNodes(nodesJSONArray, applicationId);

            log.info("NodeTaskExecutor->create: [application id: {}] addNodes status= {}", applicationId, status);
        }
        catch (RuntimeException e) {
            log.error("NodeTaskExecutor->create: [application id: {}] Could not add Node. Error: {}", applicationId, e.getMessage());
            throw new AdapterException(String.format("Problem during adding Node [application id: %s]: %s", applicationId, e.getMessage()), e);
        }
    }

    @Override
    public void delete(AdapterRequirement taskBody) {
        try {
            log.info("NodeTaskExecutor->delete: [application id: {}] AdapterRequirement= {}", applicationId, taskBody);
            List<String> nodeNames = Collections.singletonList(taskBody.getNodeName());
            log.info("NodeTaskExecutor->delete: [application id: {}] nodeNames= {}", applicationId, nodeNames);

            int status = proactiveClientService.removeNodes(nodeNames);

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
