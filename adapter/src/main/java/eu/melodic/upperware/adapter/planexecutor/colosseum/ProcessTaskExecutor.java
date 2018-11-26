package eu.melodic.upperware.adapter.planexecutor.colosseum;

import eu.melodic.upperware.adapter.communication.colosseum.ColosseumApi;
import eu.melodic.upperware.adapter.exception.AdapterException;
import eu.melodic.upperware.adapter.executioncontext.colosseum.*;
import eu.melodic.upperware.adapter.planexecutor.PlanExecutor;
import eu.melodic.upperware.adapter.plangenerator.model.AdapterProcess;
import eu.melodic.upperware.adapter.plangenerator.tasks.ProcessTask;
import io.github.cloudiator.rest.ApiException;
import io.github.cloudiator.rest.model.*;
import io.github.cloudiator.rest.model.Process;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Collection;
import java.util.concurrent.Future;

import static java.lang.String.format;

@Slf4j
public class ProcessTaskExecutor extends WatchdogColosseumTaskExecutor<AdapterProcess> {

    ProcessTaskExecutor(ProcessTask task, Collection<Future> predecessors, ColosseumApi api,
                        ColosseumContext context, ThreadPoolTaskExecutor executor, ColosseumExecutorFactory colosseumExecutorFactory, ShelveContext shelveContext) {
        super(task, predecessors, api, context, executor, colosseumExecutorFactory, shelveContext);
    }

    @Override
    public void create(AdapterProcess taskBody) {

        NodeGroup nodeGroup = context.getNodeGroupByNodeName(taskBody.getNodeName())
                .orElseThrow(() -> new AdapterException(format("Could not find NodeGroup with id %s", taskBody.getNodeName())));

        ShelveJob shelveJob = shelveContext.getShelveJobByName(taskBody.getJobName())
                .orElseThrow(() -> new AdapterException(format("Could not find ShelveJob with name %s", taskBody.getJobName())));

        Job job = context.getJob(shelveJob.getId())
                .orElseThrow(() -> new AdapterException(format("Could not find Job with id %s", shelveJob.getId())));


        ShelveSchedule shelveSchedule = shelveContext.getShelveScheduleByJobId(job.getId())
                .orElseThrow(() -> new AdapterException(format("Could not find ShelveSchedule with job %s", job.getId())));

        Schedule schedule = context.getSchedule(shelveSchedule.getId())
                .orElseThrow(() -> new AdapterException(format("Could not find Schedule with id %s", shelveSchedule.getId())));

        Task task = job.getTasks()
                .stream()
                .filter(t -> t.getName().equals(taskBody.getTaskName()))
                .findFirst().orElseThrow(() -> new AdapterException(format("Could not find Task with name %s", taskBody.getTaskName())));


        ProcessNew processNew = new ProcessNew()
                .node(nodeGroup.getNodes().get(0).getNodeId())
                .schedule(schedule.getId())
                .task(task.getName());

        try {
            log.info("Creating Process with Node: {}, Schedule {}, Task: {}", processNew.getNode(), processNew.getSchedule(), processNew.getTask());
            Queue queue = api.addProcess(processNew);
            log.info("Waiting for response from queue: {}", queue.getId());

            Queue watch = watch(queue.getId());
            log.info("Response from queue {} successfully reached. New process is created", queue.getId());

            Process process = api.getProcess(schedule.getId(), getId(watch.getLocation()));

            log.info("New process is created: {}", process.getId());
            log.debug("Process details: {}", process);
            context.addProcess(process);

        } catch (ApiException e) {
            log.error("Could not add Process. Error code: {}, Response body: {}, ResponseHeaders: {}", e.getCode(), e.getResponseBody(), e.getResponseHeaders());
            throw new AdapterException("Problem during adding Process", e);
        }
    }

    @Override
    public void delete(AdapterProcess taskBody) {

    }
}
