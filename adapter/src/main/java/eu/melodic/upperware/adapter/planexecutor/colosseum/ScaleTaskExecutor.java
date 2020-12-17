package eu.melodic.upperware.adapter.planexecutor.colosseum;

import eu.melodic.upperware.adapter.communication.colosseum.ColosseumApi;
import eu.melodic.upperware.adapter.exception.AdapterException;
import eu.melodic.upperware.adapter.executioncontext.colosseum.ColosseumContext;
import eu.melodic.upperware.adapter.plangenerator.model.AdapterScale;
import eu.melodic.upperware.adapter.plangenerator.tasks.CheckFinishTask;
import eu.melodic.upperware.adapter.plangenerator.tasks.ScaleTask;
import io.github.cloudiator.rest.ApiException;
import io.github.cloudiator.rest.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Slf4j
public class ScaleTaskExecutor extends WatchdogColosseumTaskExecutor<AdapterScale> {

    private final String applicationId;

    ScaleTaskExecutor(ScaleTask task, Collection<Future> predecessors, ColosseumApi api, ColosseumContext context,
                      Function<CheckFinishTask, Future<Queue>> checkFinishTaskToFuture, String applicationId) {
        super(task, predecessors, api, context, checkFinishTaskToFuture);
        this.applicationId = applicationId;
    }

    @Override
    public void create(AdapterScale taskBody) {

        //Scale scale = createScale(taskBody, Scale.ScaleDirectionEnum.OUT);
        try {
            log.info("ProActive Dev [ScaleTaskExecutor]: applicationId: {}\nAdapterScale taskBody= {}", applicationId, taskBody);
            /*Queue queue = api.triggerScale(scale);
            // TODO: LSZ
            // here we can trigger scale out (adding node) to task/component (e.g. Component_App). We have new node name, new node will be
            // the same as the original one (based on the same node candidate). Probably monitor created for original node will have to be created for
            // new node as well. Scale out is one node at a time.

            Queue watch = watch(queue.getId());
            log.info("Response from queue {} successfully reached. Node has been added to existing process", queue.getId());

            String scheduleId = getId(watch.getLocation());
            Optional<Schedule> scheduleOpt = api.getSchedule(scheduleId);

            if (scheduleOpt.isPresent()){
                Schedule newSchedule = scheduleOpt.get();

                log.info("Schedule details: {}", newSchedule);
                context.deleteSchedule(scheduleId);
                context.addSchedule(newSchedule);
            } else {
                log.error("Could not scaleTask OUT {}", taskBody.getTaskName());
            }*/
        } /*catch (ApiException e) {
            throw new RuntimeException(e);
        }*/
        catch (RuntimeException e) {
            throw new AdapterException(String.format("Could not scaleTask OUT %s", taskBody.getTaskName()), e);
        }
    }

    @Override
    public void delete(AdapterScale taskBody) {

        //Scale scale = createScale(taskBody, Scale.ScaleDirectionEnum.IN);
        try {
            log.info("ProActive Dev [ScaleTaskExecutor]: delete - applicationId: {}\nAdapterScale taskBody= {}", applicationId, taskBody);
            /*Queue queue = api.triggerScale(scale);
            // TODO: LSZ
            // here we can trigger scale in (deleting nodes) from task/component (e.g. Component_App). We have a list of nodes names to delete.

            Queue watch = watch(queue.getId());
            log.info("Response from queue {} successfully reached. Process has been removed from existing Process.", queue.getId());

            String scheduleId = getId(watch.getLocation());
            Optional<Schedule> scheduleOpt = api.getSchedule(scheduleId);

            if (scheduleOpt.isPresent()){
                Schedule newSchedule = scheduleOpt.get();

                log.info("Schedule details: {}", newSchedule);
                context.deleteSchedule(scheduleId);
                context.addSchedule(newSchedule);
            } else {
                log.error("Could not scaleTask IN {}", taskBody.getTaskName());
            }*/
        } /*catch (ApiException e) {
            throw new RuntimeException(e);
        }*/
        catch (RuntimeException e) {
            throw new AdapterException(String.format("Could not scaleTask IN %s", taskBody.getTaskName()), e);
        }
    }

    private Scale createScale(AdapterScale taskBody, Scale.ScaleDirectionEnum direction){
        Pair<List<Node>, Schedule> requiredData = getRequiredData(taskBody);

        return new Scale()
                .schedule(requiredData.getRight().getId())
                .task(taskBody.getTaskName())
                .scaleDirection(direction)
                .nodes(requiredData.getLeft().stream().map(Node::getId).collect(Collectors.toList()));

    }

    private Pair<List<Node>, Schedule> getRequiredData(AdapterScale taskBody) {

        final List<Node> nodes = taskBody.getNodeNames()
                .stream()
                .map(this::getNode)
                .collect(Collectors.toList());

        Job job = context.getJob(taskBody.getJobName())
                .orElseThrow(() -> new AdapterException((format("Could not find Job with name %s", taskBody.getJobName()))));

        Schedule schedule = context.getScheduleByJobId(job.getId())
                .orElseThrow(() -> new AdapterException(format("Could not find Schedule with job id %s", job.getId())));

        return Pair.of(nodes, schedule);
    }

    private Node getNode(String nodeName) {
        return context.getNode(nodeName)
                .orElseThrow(() -> new AdapterException(format("Could not find Node with id %s", nodeName)));
    }

}