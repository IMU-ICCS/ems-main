package eu.melodic.upperware.adapter.planexecutor.colosseum;

import eu.melodic.upperware.adapter.communication.colosseum.ColosseumApi;
import eu.melodic.upperware.adapter.exception.AdapterException;
import eu.melodic.upperware.adapter.executioncontext.colosseum.ColosseumContext;
import eu.melodic.upperware.adapter.executioncontext.colosseum.ShelveContext;
import eu.melodic.upperware.adapter.executioncontext.colosseum.ShelveJob;
import eu.melodic.upperware.adapter.executioncontext.colosseum.ShelveSchedule;
import eu.melodic.upperware.adapter.planexecutor.PlanExecutor;
import eu.melodic.upperware.adapter.plangenerator.model.AdapterSchedule;
import eu.melodic.upperware.adapter.plangenerator.tasks.ScheduleTask;
import io.github.cloudiator.rest.ApiException;
import io.github.cloudiator.rest.model.Job;
import io.github.cloudiator.rest.model.Queue;
import io.github.cloudiator.rest.model.Schedule;
import io.github.cloudiator.rest.model.ScheduleNew;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.Future;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

@Slf4j
public class ScheduleTaskExecutor extends WatchdogColosseumTaskExecutor<AdapterSchedule> {

    ScheduleTaskExecutor(ScheduleTask task, Collection<Future> predecessors, ColosseumApi api,
                         ColosseumContext context, ThreadPoolTaskExecutor executor, ColosseumExecutorFactory colosseumExecutorFactory, ShelveContext shelveContext) {
        super(task, predecessors, api, context, executor, colosseumExecutorFactory, shelveContext);
    }

    @Override
    public void create(AdapterSchedule taskBody) {
        String jobName = checkNotNull(taskBody.getJobName());

        ShelveJob shelveJob = shelveContext.getShelveJobByName(jobName)
                .orElseThrow(() -> new IllegalArgumentException(format("Job with name %s could not be found in shelve", jobName)));

        Job job = context.getJob(shelveJob.getId())
                .orElseThrow(() -> new IllegalStateException(
                        format("Job %s was not configured in Colosseum - schedule cannot be created", shelveJob.getId())));

        ScheduleNew scheduleNew = new ScheduleNew()
                .job(job.getId())
                .instantiation(ScheduleNew.InstantiationEnum.fromValue(taskBody.getInstantiation().name()));


        //TODO - is it correct???
        if (jobName.equals(scheduleNew.getJob()) && taskBody.getInstantiation().name().equals(taskBody.getInstantiation().name())) {
            log.info("Schedule with job {} and instantiation {} already created", jobName, taskBody.getInstantiation());
            return;
        }

        try {
            log.info("Creating Schedule with Job: [name: {}, id: {}], Instantiation: {}", job.getName(), job.getId(), scheduleNew.getInstantiation());
            Queue queue = api.addSchedule(scheduleNew);
            log.info("Waiting for response from queue: {}", queue.getId());

            Queue watch = watch(queue.getId());
            log.info("Response from queue {} successfully reached. New schedule is created", queue.getId());

            Schedule schedule = api.getSchedule(getId(watch.getLocation()));

            log.info("New schedule is created: {}", schedule.getId());
            log.debug("Schedule details: {}", schedule);
            context.addSchedule(schedule);

            shelveContext.addShelveSchedule(new ShelveSchedule(schedule.getId(), watch.getId(), job.getId()));







        } catch (ApiException e) {
            log.error("Could not add Schedule. Error code: {}, Response body: {}, ResponseHeaders: {}", e.getCode(), e.getResponseBody(), e.getResponseHeaders());
            throw new AdapterException("Problem during adding Schedule", e);
        }
    }

    @Override
    public void delete(AdapterSchedule taskBody) {

    }
}
