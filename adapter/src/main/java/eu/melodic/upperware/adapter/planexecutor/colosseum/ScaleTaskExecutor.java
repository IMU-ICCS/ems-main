package eu.melodic.upperware.adapter.planexecutor.colosseum;

import eu.melodic.upperware.adapter.communication.colosseum.ColosseumApi;
import eu.melodic.upperware.adapter.exception.AdapterException;
import eu.melodic.upperware.adapter.executioncontext.colosseum.ColosseumContext;
import eu.melodic.upperware.adapter.plangenerator.model.AdapterProcess;
import eu.melodic.upperware.adapter.plangenerator.tasks.CheckFinishTask;
import eu.melodic.upperware.adapter.plangenerator.tasks.ScaleTask;
import io.github.cloudiator.rest.ApiException;
import io.github.cloudiator.rest.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.function.Function;

import static java.lang.String.format;

@Slf4j
public class ScaleTaskExecutor extends WatchdogColosseumTaskExecutor<AdapterProcess> {

    ScaleTaskExecutor(ScaleTask task, Collection<Future> predecessors, ColosseumApi api, ColosseumContext context,
                      Function<CheckFinishTask, Future<Queue>> checkFinishTaskToFuture) {
        super(task, predecessors, api, context, checkFinishTaskToFuture);
    }

    @Override
    public void create(AdapterProcess taskBody) {

        Scale scale = createScale(taskBody, Scale.ScaleDirectionEnum.OUT);
        try {
            Queue queue = api.triggerScale(scale);
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
            }
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(AdapterProcess taskBody) {

        Scale scale = createScale(taskBody, Scale.ScaleDirectionEnum.IN);
        try {
            Queue queue = api.triggerScale(scale);
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
            }
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    private Scale createScale(AdapterProcess taskBody, Scale.ScaleDirectionEnum direction){
        Pair<Node, Schedule> requiredData = getRequiredData(taskBody);

        return new Scale()
                .schedule(requiredData.getRight().getId())
                .task(taskBody.getTaskName())
                .scaleDirection(direction)
                .nodes(Collections.singletonList(requiredData.getLeft().getId()));

    }

    private Pair<Node, Schedule> getRequiredData(AdapterProcess taskBody) {

        Node node = context.getNode(taskBody.getNodeName())
                .orElseThrow(() -> new AdapterException(format("Could not find Node with id %s", taskBody.getNodeName())));

        Job job = context.getJob(taskBody.getJobName())
                .orElseThrow(() -> new AdapterException((format("Could not find Job with name %s", taskBody.getJobName()))));

        Schedule schedule = context.getScheduleByJobId(job.getId())
                .orElseThrow(() -> new AdapterException(format("Could not find Schedule with job id %s", job.getId())));

        return Pair.of(node, schedule);
    }

}