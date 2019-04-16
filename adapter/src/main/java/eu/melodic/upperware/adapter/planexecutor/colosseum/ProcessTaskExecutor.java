package eu.melodic.upperware.adapter.planexecutor.colosseum;

import eu.melodic.upperware.adapter.communication.colosseum.ColosseumApi;
import eu.melodic.upperware.adapter.exception.AdapterException;
import eu.melodic.upperware.adapter.executioncontext.colosseum.ColosseumContext;
import eu.melodic.upperware.adapter.plangenerator.model.AdapterProcess;
import eu.melodic.upperware.adapter.plangenerator.tasks.CheckFinishTask;
import eu.melodic.upperware.adapter.plangenerator.tasks.ProcessTask;
import io.github.cloudiator.rest.ApiException;
import io.github.cloudiator.rest.model.*;
import io.github.cloudiator.rest.model.Queue;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.Future;
import java.util.function.Function;

import static java.lang.String.format;

@Slf4j
public class ProcessTaskExecutor extends WatchdogColosseumTaskExecutor<AdapterProcess> {

    ProcessTaskExecutor(ProcessTask task, Collection<Future> predecessors, ColosseumApi api,
                        ColosseumContext context, Function<CheckFinishTask, Future<Queue>> checkFinishTaskToFuture) {
        super(task, predecessors, api, context, checkFinishTaskToFuture);
    }

    @Override
    public void create(AdapterProcess taskBody) {

        Node node = context.getNode(taskBody.getNodeName())
                .orElseThrow(() -> new AdapterException(format("Could not find Node with id %s", taskBody.getNodeName())));

        Job job = context.getJob(taskBody.getJobName())
                .orElseThrow(() -> new AdapterException((format("Could not find Job with name %s", taskBody.getJobName()))));

        Schedule schedule = context.getScheduleByJobId(job.getId())
                .orElseThrow(() -> new AdapterException(format("Could not find Schedule with job id %s", job.getId())));

        Task task = job.getTasks()
                .stream()
                .filter(t -> t.getName().equals(taskBody.getTaskName()))
                .findFirst().orElseThrow(() -> new AdapterException(format("Could not find Task with name %s", taskBody.getTaskName())));


        try {
            log.info("Creating Process with Node: {}, Schedule {}, Task: {}", node.getId(), schedule.getId(), task.getName());

            CloudiatorProcessNew cpNew;

            TaskInterface taskInterface = task.getInterfaces().get(0);
            if (taskInterface instanceof SparkInterface) {
                cpNew = new ClusterProcessNew()
                        .nodes(Collections.singletonList(node.getId()))
                        .schedule(schedule.getId())
                        .task(task.getName())
                        .taskInterface("io.github.cloudiator.deployment.domain.SparkInterface")
                        .processType("ClusterProcessNew");
            } else if (taskInterface instanceof FaasInterface) {
                cpNew = getCloudiatorProcessNew(node, schedule, task, "io.github.cloudiator.deployment.domain.FaasInterface");
            } else if (taskInterface instanceof LanceInterface) {
                cpNew = getCloudiatorProcessNew(node, schedule, task, "io.github.cloudiator.deployment.domain.LanceInterface");
            } else if (taskInterface instanceof DockerInterface) {
                cpNew = getCloudiatorProcessNew(node, schedule, task, "io.github.cloudiator.deployment.domain.DockerInterface");
            } else {
                throw new AdapterException("Unsupported type: " + taskInterface);
            }

            Queue queue = api.addProcess(cpNew);

            Queue watch = watch(queue.getId(), queueId -> {
                String processId = getId(queueId);
                try {
                    return !CloudiatorProcess.StateEnum.PENDING.equals(api.getCloudiatorProcess(processId)
                            .orElseThrow(() -> new AdapterException("Could not find CloudiatorProcess for " + processId))
                            .getState());
                } catch (ApiException e) {
                    throw new AdapterException("Could not get CloudiatorProcess for " + processId, e);
                }
            });

            String cloudiatorProcessId = getId(watch.getLocation());

            CloudiatorProcess cp = api.getCloudiatorProcess(cloudiatorProcessId)
                    .orElseThrow(() -> new AdapterException((format("Could not get CloudiatorProcess with id %s", cloudiatorProcessId))));

            boolean isRunning = CloudiatorProcess.StateEnum.RUNNING.equals(cp.getState());
            if (isRunning) {
                log.info("Response from queue {} successfully reached. New CloudiatorProcess has been created {}", queue.getId(), cloudiatorProcessId);
                context.addCloudiatorProcess(cp);
            } else {
                log.info("Response from queue {} successfully reached. But CloudiatorProcess {} is in state {}", queue.getId(), cloudiatorProcessId, cp.getState());
                throw new AdapterException(format("Cloudiator Process %s is in %s state", cloudiatorProcessId, cp.getState()));
            }

        } catch (ApiException e) {
            log.error("Could not add Process. Error code: {}, Response body: {}, ResponseHeaders: {}", e.getCode(), e.getResponseBody(), e.getResponseHeaders());
            throw new AdapterException("Problem during adding Process", e);
        }
    }

    private CloudiatorProcessNew getCloudiatorProcessNew(Node node, Schedule schedule, Task task, String s) {
        CloudiatorProcessNew cpNew;
        cpNew = new SingleProcessNew()
                .node(node.getId())
                .schedule(schedule.getId())
                .task(task.getName())
                .taskInterface(s)
                .processType("SingleProcessNew");
        return cpNew;
    }

    @Override
    public void delete(AdapterProcess taskBody) {

        Node node = context.getNode(taskBody.getNodeName())
                .orElseThrow(() -> new AdapterException(format("Could not find Node with id %s", taskBody.getNodeName())));

        Job job = context.getJob(taskBody.getJobName())
                .orElseThrow(() -> new AdapterException((format("Could not find Job with name %s", taskBody.getJobName()))));

        Schedule schedule = context.getScheduleByJobId(job.getId())
                .orElseThrow(() -> new AdapterException(format("Could not find Schedule with job id %s", job.getId())));

        Task task = job.getTasks()
                .stream()
                .filter(t -> t.getName().equals(taskBody.getTaskName()))
                .findFirst().orElseThrow(() -> new AdapterException(format("Could not find Task with name %s", taskBody.getTaskName())));

        try {
            Optional<CloudiatorProcess> optionalCloudiatorProcess = context.getCloudiatorProcess(node.getId(), schedule.getId(), task.getName());

            if (optionalCloudiatorProcess.isPresent()) {
                CloudiatorProcess cloudiatorProcess = optionalCloudiatorProcess.get();

                Queue deleteProcessQueue = api.deleteCloudiatorProcess(cloudiatorProcess.getId());
                watch(deleteProcessQueue.getId());
                log.info("Response from queue {} successfully reached. CloudiatorProcess {} is deleted", deleteProcessQueue.getId(), cloudiatorProcess.getId());
                context.deleteCloudiatorProcess(cloudiatorProcess.getId());
            } else {
                log.warn("Could not find process group with node {}, schedule {} and task {}. Nothing will be deleted.", node.getId(), schedule.getId(), task.getName());
            }
        } catch (ApiException e) {
            log.error("Could not delete ProcessGroup. Error code: {}, Response body: {}, ResponseHeaders: {}", e.getCode(), e.getResponseBody(), e.getResponseHeaders());
            throw new AdapterException("Problem during removing ProcessGroup", e);
        }
    }
}
