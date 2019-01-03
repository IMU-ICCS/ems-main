package eu.melodic.upperware.adapter.planexecutor.colosseum;

import eu.melodic.upperware.adapter.communication.colosseum.ColosseumApi;
import eu.melodic.upperware.adapter.exception.AdapterException;
import eu.melodic.upperware.adapter.executioncontext.colosseum.ColosseumContext;
import eu.melodic.upperware.adapter.plangenerator.model.AdapterSchedule;
import eu.melodic.upperware.adapter.plangenerator.tasks.ScheduleTask;
import io.github.cloudiator.rest.ApiException;
import io.github.cloudiator.rest.model.Job;
import io.github.cloudiator.rest.model.Queue;
import io.github.cloudiator.rest.model.Schedule;
import io.github.cloudiator.rest.model.ScheduleNew;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.Future;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

@Slf4j
public class ScheduleTaskExecutor extends WatchdogColosseumTaskExecutor<AdapterSchedule> {

    ScheduleTaskExecutor(ScheduleTask task, Collection<Future> predecessors, ColosseumApi api,
                         ColosseumContext context, ThreadPoolTaskExecutor executor, ColosseumExecutorFactory colosseumExecutorFactory) {
        super(task, predecessors, api, context, executor, colosseumExecutorFactory);
    }

    @Override
    public void create(AdapterSchedule taskBody) {
        String jobName = checkNotNull(taskBody.getJobName());

        Job job = context.getJobByName(taskBody.getJobName())
                .orElseThrow(() -> new IllegalStateException(
                        format("Job with name %s was not configured in Colosseum - schedule cannot be created", taskBody.getJobName())));

        ScheduleNew scheduleNew = new ScheduleNew()
                .job(job.getId())
                .instantiation(ScheduleNew.InstantiationEnum.fromValue(taskBody.getInstantiation().name()));

        if (jobName.equals(scheduleNew.getJob())) {
            log.info("Schedule with job {} and instantiation {} already created", jobName, taskBody.getInstantiation());
            return;
        }

        try {
            log.info("Creating Schedule with Job: [name: {}, id: {}], Instantiation: {}", job.getName(), job.getId(), scheduleNew.getInstantiation());
            Queue queue = api.addSchedule(scheduleNew);
            log.info("Waiting for response from queue: {}", queue.getId());

            Queue watch = watch(queue.getId());
            log.info("Response from queue {} successfully reached. New schedule is created", queue.getId());

            String scheduleId = getId(watch.getLocation());
            Optional<Schedule> scheduleOpt = api.getSchedule(scheduleId);

            if (scheduleOpt.isPresent()){
                Schedule schedule = scheduleOpt.get();

                log.info("Schedule details: {}", schedule);
                context.addSchedule(schedule);
            } else {
                log.error("Could not get Schedule with id {}", scheduleId);
            }
        } catch (ApiException e) {
            log.error("Could not add Schedule. Error code: {}, Response body: {}, ResponseHeaders: {}", e.getCode(), e.getResponseBody(), e.getResponseHeaders());
            throw new AdapterException("Problem during adding Schedule", e);
        }
    }

    @Override
    public void delete(AdapterSchedule taskBody) {

    }
}
