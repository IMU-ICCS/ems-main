package eu.melodic.upperware.adapter.planexecutor.colosseum;

import eu.melodic.upperware.adapter.communication.colosseum.ColosseumApi;
import eu.melodic.upperware.adapter.exception.AdapterException;
import eu.melodic.upperware.adapter.executioncontext.colosseum.ColosseumContext;
import eu.melodic.upperware.adapter.planexecutor.TaskWatchDog;
import eu.melodic.upperware.adapter.plangenerator.model.AdapterRequirement;
import eu.melodic.upperware.adapter.plangenerator.tasks.NodeTask;
import io.github.cloudiator.rest.ApiException;
import io.github.cloudiator.rest.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Slf4j
public class NodeTaskExecutor extends WatchdogColosseumTaskExecutor<AdapterRequirement> implements TaskWatchDog {

    NodeTaskExecutor(NodeTask task, Collection<Future> predecessors, ColosseumApi api,
                     ColosseumContext context, ThreadPoolTaskExecutor executor, ColosseumExecutorFactory colosseumExecutorFactory) {
        super(task, predecessors, api, context, executor, colosseumExecutorFactory);
    }

    @Override
    public void create(AdapterRequirement taskBody) {
        //TODO - in the future check if node exists

        try {
            log.info("Creating Node with NodeCandidateId: {}, groupName: {}", taskBody.getNodeCandidate().getId(), taskBody.getNodeName());

            Queue queue = api.addNode(new NodeRequest()
                    .groupName(taskBody.getNodeName())
                    .nodeCandidate(taskBody.getNodeCandidate())
                    .requirements(new NodeRequirements().requirements(Collections.emptyList())));

            Queue watch = watch(queue.getId());
            log.info("Response from queue {} successfully reached. New node is created", queue.getId());

            String nodeGroupId = getId(watch.getLocation());
            NodeGroup nodeGroup = api.getNodeGroup(nodeGroupId)
                    .orElseThrow(() -> new AdapterException(format("Could not get NodeGroup with id %s", nodeGroupId)));

            boolean isNodeRunning = nodeGroup
                    .getNodes()
                    .stream()
                    .allMatch(node -> Node.StateEnum.RUNNING.equals(node.getState()));

            if (isNodeRunning) {
                log.info("New nodeGroup has been created: name: {}, nodeId: {}", nodeGroup.getId(),
                        nodeGroup
                                .getNodes()
                                .stream()
                                .map(node -> "{nodeId: " + node.getId() + ", name: " + node.getName() + "}")
                                .collect(Collectors.joining(", ", "[", "]")));

                context.addNodeGroup(nodeGroup);
            } else {
                String errorMessage = nodeGroup
                        .getNodes()
                        .stream()
                        .filter(node -> !Node.StateEnum.RUNNING.equals(node.getState()))
                        .map(node -> format("Node %s (id: %s) is in %s state", node.getName(), node.getId(), node.getState()))
                        .collect(Collectors.joining(", ", "NodeGroup " + nodeGroupId + " has been created but ", "."));

                throw new AdapterException(errorMessage);
            }

        } catch (ApiException e) {
            log.error("Could not add NodeGroup. Error code: {}, Response body: {}, ResponseHeaders: {}", e.getCode(), e.getResponseBody(), e.getResponseHeaders());
            throw new AdapterException("Problem during adding Node", e);
        }
    }

    @Override
    public void delete(AdapterRequirement taskBody) {
        Optional<NodeGroup> nodeGroupOptional = context.getNodeGroupByNodeName(taskBody.getNodeName());

        try {
            if (nodeGroupOptional.isPresent()) {
                String nodeId = nodeGroupOptional.get().getNodes().get(0).getId();
                Queue queue = api.deleteNode(nodeId);

                watch(queue.getId());
                log.info("Response from queue {} successfully reached. Node {} is deleted", queue.getId(), nodeId);
                context.deleteNodeGroup(nodeGroupOptional.get().getId());
            } else {
                log.warn("Could not find node group with nodeName {} Nothing will be deleted.", taskBody.getNodeName());
            }
        } catch (ApiException e) {
            log.error("Could not remove NodeGroup. Error code: {}, Response body: {}, ResponseHeaders: {}", e.getCode(), e.getResponseBody(), e.getResponseHeaders());
            throw new AdapterException("Problem during removing Node", e);
        }

    }
}
