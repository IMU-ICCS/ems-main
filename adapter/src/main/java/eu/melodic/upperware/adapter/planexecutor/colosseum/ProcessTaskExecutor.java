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
import java.util.stream.Collectors;

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

        Schedule schedule = context.getScheduleByJobId(job.getId())
                .orElseThrow(() -> new AdapterException(format("Could not find Schedule with job id %s", job.getId())));

        Task task = job.getTasks()
                .stream()
                .filter(t -> t.getName().equals(taskBody.getTaskName()))
                .findFirst().orElseThrow(() -> new AdapterException(format("Could not find Task with name %s", taskBody.getTaskName())));


        try {
            log.info("Creating Process with NodeGroup: {}, Schedule {}, Task: {}", nodeGroup.getId(), schedule.getId(), task.getName());

            Queue queue = api.addProcess(new CloudiatorProcessNew()
                    .nodeGroup(nodeGroup.getId())
                    .schedule(schedule.getId())
                    .task(task.getName()));

            Queue watch = watch(queue.getId());
            log.info("Response from queue {} successfully reached. New process is created", queue.getId());

            String processGroupId = getId(watch.getLocation());
            ProcessGroup processGroup = api.getProcessGroup(processGroupId)
                    .orElseThrow(() -> new AdapterException((format("Could not get ProcessGroup with id %s", processGroupId))));

            boolean isProcessRunning = processGroup
                    .getProcesses()
                    .stream()
                    .allMatch(cloudiatorProcess -> CloudiatorProcess.StateEnum.RUNNING.equals(cloudiatorProcess.getState()));

            if (isProcessRunning) {
                log.info("Process group has been created ProcessGroup details: {}", processGroup);
                context.addProcessGroup(processGroup);
            } else {
                String errorMessage = processGroup
                        .getProcesses()
                        .stream()
                        .filter(cloudiatorProcess -> !CloudiatorProcess.StateEnum.RUNNING.equals(cloudiatorProcess.getState()))
                        .map(cloudiatorProcess -> format("Process %s is in %s state", cloudiatorProcess.getId(), cloudiatorProcess.getState()))
                        .collect(Collectors.joining(", ", "ProcessGroup " + processGroupId + " has been created but ", "."));

                throw new AdapterException(errorMessage);
            }

        } catch (ApiException e) {
            log.error("Could not add Process. Error code: {}, Response body: {}, ResponseHeaders: {}", e.getCode(), e.getResponseBody(), e.getResponseHeaders());
            throw new AdapterException("Problem during adding Process", e);
        }
    }

    @Override
    public void delete(AdapterProcess taskBody) {
        NodeGroup nodeGroup = context.getNodeGroupByNodeName(taskBody.getNodeName())
                .orElseThrow(() -> new AdapterException(format("Could not find NodeGroup with id %s", taskBody.getNodeName())));

        Job job = context.getJob(taskBody.getJobName())
                .orElseThrow(() -> new AdapterException((format("Could not find Job with name %s", taskBody.getJobName()))));

        Schedule schedule = context.getScheduleByJobId(job.getId())
                .orElseThrow(() -> new AdapterException(format("Could not find Schedule with job id %s", job.getId())));

        Task task = job.getTasks()
                .stream()
                .filter(t -> t.getName().equals(taskBody.getTaskName()))
                .findFirst().orElseThrow(() -> new AdapterException(format("Could not find Task with name %s", taskBody.getTaskName())));

        try {
            Optional<ProcessGroup> optionalProcessGroup = context.getProcessGroup(nodeGroup.getId(), schedule.getId(), task.getName());

            if (optionalProcessGroup.isPresent()) {
                ProcessGroup processGroup = optionalProcessGroup.get();
                String processGroupId = processGroup.getId();
                CloudiatorProcess cloudiatorProcess = processGroup.getProcesses().get(0);

                Queue deleteProcessQueue = api.deleteProcess(cloudiatorProcess.getId());

                watch(deleteProcessQueue.getId());
                log.info("Response from queue {} successfully reached. Process is deleted", deleteProcessQueue.getId(), processGroupId);
                context.deleteProcessGroup(processGroupId);
            } else {
                log.warn("Could not find process group with nodeGroup {}, schedule {} and task {}. Nothing will be deleted.", nodeGroup.getId(), schedule.getId(), task.getName());
            }
        } catch (ApiException e) {
            log.error("Could not delete ProcessGroup. Error code: {}, Response body: {}, ResponseHeaders: {}", e.getCode(), e.getResponseBody(), e.getResponseHeaders());
            throw new AdapterException("Problem during removing ProcessGroup", e);
        }
    }
}
