package eu.melodic.upperware.adapter.planexecutor.colosseum;

import eu.melodic.upperware.adapter.communication.colosseum.ColosseumApi;
import eu.melodic.upperware.adapter.exception.AdapterException;
import eu.melodic.upperware.adapter.executioncontext.colosseum.ColosseumContext;
import eu.melodic.upperware.adapter.executioncontext.colosseum.ShelveContext;
import eu.melodic.upperware.adapter.executioncontext.colosseum.ShelveNode;
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
                     ColosseumContext context, ThreadPoolTaskExecutor executor, ColosseumExecutorFactory colosseumExecutorFactory, ShelveContext shelveContext) {
        super(task, predecessors, api, context, executor, colosseumExecutorFactory, shelveContext);
    }

    @Override
    public void create(AdapterRequirement taskBody) {

        //TODO - in the future check if node exists

        try {
            log.info("Creating Node with NodeCandidateId: {}, groupName: {}", taskBody.getNodeCandidate().getId(), taskBody.getNodeName());

            taskBody.getNodeCandidate().getCloud().credential(new CloudCredential().secret("secret").user("user"));
            Queue queue = api.addNode(new NodeRequest()
                    .groupName(taskBody.getNodeName())
                    .nodeCandidate(taskBody.getNodeCandidate())
                    .requirements(new NodeRequirements().requirements(Collections.emptyList())));

            Queue watch = watch(queue.getId());
            log.info("Response from queue {} successfully reached. New node is created", queue.getId());

            NodeGroup nodeGroup = api.getNodeGroup(getId(watch.getLocation()));

            log.info("New nodeGroup is created: name: {}, nodeId: {}", nodeGroup.getId(),
                    nodeGroup
                            .getNodes()
                            .stream()
                            .map(node -> "{nodeId: " + node.getNodeId() + ", name: " + node.getName() +"}")
                            .collect(Collectors.joining(", ", "[", "]")));

//            Node node = nodeGroup
//                    .getNodes()
//                    .stream()
//                    .filter(_node -> _node.getName().endsWith(taskBody.getNodeName()))
//                    .findFirst()
//                    .orElseThrow(() -> new AdapterException(format("Could not find Node with name ends with: %s ", taskBody.getNodeName())));

//            log.info("New node is created: name: {}, nodeId: {}", node.getName(), node.getNodeId());

//            shelveContext.addShelveNode(new ShelveNode(node.getNodeId(), getId(watch.getLocation()), taskBody.getNodeName()));
//            context.addNode(node);
            context.addNodeGroup(nodeGroup);
        } catch (ApiException e) {
            log.error("Could not add NodeGroup: ", e);
            log.error("Could not add NodeGroup. Error code: {}, Response body: {}, ResponseHeaders: {}", e.getCode(), e.getResponseBody(), e.getResponseHeaders());
            throw new AdapterException("Problem during adding Node", e);
        }
    }

    @Override
    public void delete(AdapterRequirement taskBody) {

    }
}
