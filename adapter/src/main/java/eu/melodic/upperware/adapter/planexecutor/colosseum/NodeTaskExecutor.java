package eu.melodic.upperware.adapter.planexecutor.colosseum;

import com.google.common.base.Objects;
import eu.melodic.upperware.adapter.communication.colosseum.ColosseumApi;
import eu.melodic.upperware.adapter.exception.AdapterException;
import eu.melodic.upperware.adapter.executioncontext.colosseum.ColosseumContext;
import eu.melodic.upperware.adapter.planexecutor.TaskWatchDog;
import eu.melodic.upperware.adapter.plangenerator.model.AdapterRequirement;
import eu.melodic.upperware.adapter.plangenerator.tasks.CheckFinishTask;
import eu.melodic.upperware.adapter.plangenerator.tasks.NodeTask;
import io.github.cloudiator.rest.ApiException;
import io.github.cloudiator.rest.model.Node;
import io.github.cloudiator.rest.model.NodeRequest;
import io.github.cloudiator.rest.model.Queue;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.inject.Singleton;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.function.Function;

import static java.lang.String.format;

@Slf4j
public class NodeTaskExecutor extends WatchdogColosseumTaskExecutor<AdapterRequirement> implements TaskWatchDog {

    private final String applicationId;

    NodeTaskExecutor(NodeTask task, Collection<Future> predecessors, ColosseumApi api,
                     ColosseumContext context, Function<CheckFinishTask, Future<Queue>> checkFinishTaskToFuture, String applicationId) {
        super(task, predecessors, api, context, checkFinishTaskToFuture);
        this.applicationId = applicationId;
    }

    @Override
    public void create(AdapterRequirement taskBody) {
        try {
            log.info("Creating Node with NodeCandidateId: {}, groupName: {}", taskBody.getNodeCandidate().getId(), taskBody.getNodeName());

            /*Queue queue = api.addNode(new NodeRequest()
                    .groupName(taskBody.getNodeName())
                    .nodeCandidate(taskBody.getNodeCandidate()));*/

            // TODO: LSZ
            // here we can create a node for task/component (e.g. Component_App) based on a node candidate and assign it a name
            log.info("ProActive Dev [NodeTaskExecutor]: AdapterRequirement taskBody= {}", taskBody);

            JSONArray nodesJSONArray = new JSONArray();
            JSONObject nodeJSON = new JSONObject();
            nodeJSON.put("taskName", taskBody.getTaskName());
            nodeJSON.put("nodeName", taskBody.getNodeName());
            JSONObject nodeCandidateInformationJSON = new JSONObject();
            nodeCandidateInformationJSON.put("cloudID", checkEmptiness(taskBody.getNodeCandidate().getCloud().getId(), "cloudID"));
            nodeCandidateInformationJSON.put("locationName", checkEmptiness(taskBody.getNodeCandidate().getLocation().getProviderId(), "locationName"));
            nodeCandidateInformationJSON.put("imageProviderId", checkEmptiness(taskBody.getNodeCandidate().getImage().getProviderId(), "imageProviderId"));
            nodeCandidateInformationJSON.put("hardwareProviderId", checkEmptiness(taskBody.getNodeCandidate().getHardware().getProviderId(), "hardwareProviderId"));
            nodeJSON.put("nodeCandidateInformation", nodeCandidateInformationJSON);
            nodesJSONArray.put(nodeJSON);
            log.info("ProActive Dev [NodeTaskExecutor]: just before addNodes - JSONArray nodesJSONArray= {}", nodesJSONArray);

            /*Queue watch = watch(queue.getId(), id -> {
                String nodeId = getId(id);
                try {
                    return !Node.StateEnum.PENDING.equals(api.getNode(nodeId)
                            .orElseThrow(() -> new AdapterException("Could not find Node for " + nodeId))
                            .getState());
                } catch (ApiException e) {
                    throw new AdapterException("Could not getNode for " + nodeId, e);
                }
            });*/
            /*String nodeId = getId(watch.getLocation());
            Node node = api.getNode(nodeId)
                    .orElseThrow(() -> new AdapterException(format("Could not get Node with id %s", nodeId)));

            boolean isRunning = Node.StateEnum.RUNNING.equals(node.getState());
            if (isRunning) {
                log.info("Response from queue {} successfully reached. New node is created", queue.getId());
                context.addNode(node);
            } else {
                log.info("Response from queue {} successfully reached. But Node {} is in state {}", queue.getId(), nodeId, node.getState());
                throw new AdapterException(format("Node %s is in %s state", nodeId, node.getState()));
            }*/
        } /*catch (ApiException e) {
            log.error("Could not add Node. Error code: {}, Response body: {}, ResponseHeaders: {}", e.getCode(), e.getResponseBody(), e.getResponseHeaders());
            throw new AdapterException("Problem during adding Node", e);
        }*/
        catch (RuntimeException e) {
            log.error("Could not add Node. Error: {}", e.getMessage());
            throw new AdapterException("Problem during adding Node", e);
        }
    }

    @Override
    public void delete(AdapterRequirement taskBody) {
        //Optional<Node> nodeOptional = context.getNode(taskBody.getNodeName());

        try {
            log.info("ProActive Dev [NodeTaskExecutor]: delete AdapterRequirement taskBody= {}", taskBody);
            List<String> nodeNames = Collections.singletonList(taskBody.getNodeName());
            log.info("ProActive Dev [NodeTaskExecutor]: delete [part of application id: {}] List<String> nodeNames= {}", applicationId, nodeNames);
            /*if (nodeOptional.isPresent()) {
                String nodeId = nodeOptional.get().getId();
                Queue queue = api.deleteNode(nodeId);

                watch(queue.getId());
                log.info("Response from queue {} successfully reached. Node {} is deleted", queue.getId(), nodeId);
                context.deleteNode(nodeId);
            } else {
                log.warn("Could not find node group with nodeName {} Nothing will be deleted.", taskBody.getNodeName());
            }*/
        } /*catch (ApiException e) {
            log.error("Could not remove Node. Error code: {}, Response body: {}, ResponseHeaders: {}", e.getCode(), e.getResponseBody(), e.getResponseHeaders());
            throw new AdapterException("Problem during removing Node", e);
        }*/
        catch (RuntimeException e) {
            log.error("Could not remove Node. Error: {}", e.getMessage());
            throw new AdapterException("Problem during removing Node", e);
        }
    }

    private String checkEmptiness(String text, String type) throws AdapterException {
        if (StringUtils.isNotEmpty(text))
            return text;
        else {
            throw new AdapterException(String.format("Value of type: %s cannot be empty [part of application id: %s]", type, applicationId));
        }
    }
}
