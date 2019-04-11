package eu.melodic.upperware.adapter.planexecutor.colosseum;

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

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.function.Function;

import static java.lang.String.format;

@Slf4j
public class NodeTaskExecutor extends WatchdogColosseumTaskExecutor<AdapterRequirement> implements TaskWatchDog {

    NodeTaskExecutor(NodeTask task, Collection<Future> predecessors, ColosseumApi api,
                     ColosseumContext context, Function<CheckFinishTask, Future<Queue>> checkFinishTaskToFuture) {
        super(task, predecessors, api, context, checkFinishTaskToFuture);
    }

    @Override
    public void create(AdapterRequirement taskBody) {
        try {
            log.info("Creating Node with NodeCandidateId: {}, groupName: {}", taskBody.getNodeCandidate().getId(), taskBody.getNodeName());

            Queue queue = api.addNode(new NodeRequest()
                    .groupName(taskBody.getNodeName())
                    .nodeCandidate(taskBody.getNodeCandidate()));

            Queue watch = watch(queue.getId(), id -> {
                String nodeId = getId(id);
                try {
                    return Node.StateEnum.RUNNING.equals(api.getNode(nodeId)
                            .orElseThrow(() -> new AdapterException("Could not find Node for " + nodeId))
                            .getState());
                } catch (ApiException e) {
                    throw new AdapterException("Could not getNode for " + nodeId, e);
                }
            });
            String nodeId = getId(watch.getLocation());
            Node node = api.getNode(nodeId)
                    .orElseThrow(() -> new AdapterException(format("Could not get Node with id %s", nodeId)));
            context.addNode(node);
            log.info("Response from queue {} successfully reached. New node is created", queue.getId());

        } catch (ApiException e) {
            log.error("Could not add Node. Error code: {}, Response body: {}, ResponseHeaders: {}", e.getCode(), e.getResponseBody(), e.getResponseHeaders());
            throw new AdapterException("Problem during adding Node", e);
        }
    }

    @Override
    public void delete(AdapterRequirement taskBody) {
        Optional<Node> nodeOptional = context.getNode(taskBody.getNodeName());

        try {
            if (nodeOptional.isPresent()) {
                String nodeId = nodeOptional.get().getId();
                Queue queue = api.deleteNode(nodeId);

                watch(queue.getId());
                log.info("Response from queue {} successfully reached. Node {} is deleted", queue.getId(), nodeId);
                context.deleteNode(nodeId);
            } else {
                log.warn("Could not find node group with nodeName {} Nothing will be deleted.", taskBody.getNodeName());
            }
        } catch (ApiException e) {
            log.error("Could not remove Node. Error code: {}, Response body: {}, ResponseHeaders: {}", e.getCode(), e.getResponseBody(), e.getResponseHeaders());
            throw new AdapterException("Problem during removing Node", e);
        }

    }
}
