package eu.melodic.upperware.adapter.planexecutor.colosseum;

import eu.melodic.upperware.adapter.communication.colosseum.ColosseumApi;
import eu.melodic.upperware.adapter.exception.AdapterException;
import eu.melodic.upperware.adapter.executioncontext.colosseum.ColosseumContext;
import eu.melodic.upperware.adapter.plangenerator.model.AdapterProcess;
import eu.melodic.upperware.adapter.plangenerator.tasks.ProcessTask;
import io.github.cloudiator.rest.ApiException;
import io.github.cloudiator.rest.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.Future;

import static java.lang.String.format;

@Slf4j
public class ProcessTaskExecutor extends WatchdogColosseumTaskExecutor<AdapterProcess> {

    ProcessTaskExecutor(ProcessTask task, Collection<Future> predecessors, ColosseumApi api,
                        ColosseumContext context, ThreadPoolTaskExecutor executor, ColosseumExecutorFactory colosseumExecutorFactory) {
        super(task, predecessors, api, context, executor, colosseumExecutorFactory);
    }

    @Override
    public void create(AdapterProcess taskBody) {

        NodeGroup nodeGroup = context.getNodeGroupByNodeName(taskBody.getNodeName())
                .orElseThrow(() -> new AdapterException(format("Could not find NodeGroup with id %s", taskBody.getNodeName())));

        Job job = context.getJob(taskBody.getJobName())
                .orElseThrow(() -> new AdapterException((format("Could not find Job with name %s", taskBody.getJobName()))));

        Schedule schedule = context.getSchedule(taskBody.getScheduleName())
                .orElseThrow(() -> new AdapterException(format("Could not find Schedule with job id %s", job.getId())));

        Task task = job.getTasks()
                .stream()
                .filter(t -> t.getName().equals(taskBody.getTaskName()))
                .findFirst().orElseThrow(() -> new AdapterException(format("Could not find Task with name %s", taskBody.getTaskName())));


        CloudiatorProcessNew cloudiatorProcessNew = new CloudiatorProcessNew()
                .nodeGroup(nodeGroup.getId())
                .schedule(schedule.getId())
                .task(task.getName());

        try {
            log.info("Creating Process with NodeGroup: {}, Schedule {}, Task: {}", cloudiatorProcessNew.getNodeGroup(), cloudiatorProcessNew.getSchedule(), cloudiatorProcessNew.getTask());
            Queue queue = api.addProcess(cloudiatorProcessNew);
            log.info("Waiting for response from queue: {}", queue.getId());

            Queue watch = watch(queue.getId());
            log.info("Response from queue {} successfully reached. New process is created", queue.getId());

            String processGroupId = getId(watch.getLocation());
            Optional<ProcessGroup> processGroupOpt = api.getProcessGroup(processGroupId);
            if (processGroupOpt.isPresent()){
                ProcessGroup processGroup = processGroupOpt.get();
                log.info("ProcessGroup details: {}", processGroup);
                context.addProcessGroup(processGroup);
            } else {
                log.error("Could not get ProcessGroup with id {}", processGroupId);
            }

        } catch (ApiException e) {
            log.error("Could not add Process. Error code: {}, Response body: {}, ResponseHeaders: {}", e.getCode(), e.getResponseBody(), e.getResponseHeaders());
            throw new AdapterException("Problem during adding Process", e);
        }
    }

    @Override
    public void delete(AdapterProcess taskBody) {

    }
}
