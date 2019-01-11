package eu.melodic.upperware.adapter.planexecutor.colosseum;

import eu.melodic.upperware.adapter.communication.colosseum.ColosseumApi;
import eu.melodic.upperware.adapter.executioncontext.colosseum.ColosseumContext;
import eu.melodic.upperware.adapter.executioncontext.colosseum.ShelveContext;
import eu.melodic.upperware.adapter.plangenerator.model.*;
import eu.melodic.upperware.adapter.plangenerator.tasks.MonitorTask;
import io.github.cloudiator.rest.ApiException;
import io.github.cloudiator.rest.model.*;
import io.github.cloudiator.rest.model.Monitor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.*;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Slf4j
public class MonitorTaskExecutor extends WatchdogColosseumTaskExecutor<AdapterMonitor> {

    public MonitorTaskExecutor(MonitorTask task, Collection<Future> predecessors,
                               ColosseumApi api, ColosseumContext context, ThreadPoolTaskExecutor executor,
                               ColosseumExecutorFactory colosseumExecutorFactory, ShelveContext shelveContext) {

        super(task, predecessors, api, context, executor, colosseumExecutorFactory, shelveContext);
    }

    @Override
    public void create(AdapterMonitor taskBody) {
        Monitor monitor = convertToMonitor(taskBody);
        Optional<Monitor> monitorOpt = context.getMonitor(taskBody.getMetricName());
        if (monitorOpt.isPresent()) {
            log.info("There is already Monitor defined with metric: {}", taskBody.getMetricName());
            return;
        }

        try {
            Monitor addedMonitor = api.addMonitor(monitor);
            context.addMonitor(addedMonitor);
        } catch (ApiException e) {
            log.error("Exception during adding monitor", e);
        }
    }

    private Monitor convertToMonitor(AdapterMonitor taskBody) {
        return new Monitor()
                .metric(taskBody.getMetricName())
                .targets(convertToTargets(taskBody.getTaskName()))
                .sensor(convertToSensor(taskBody.getSensor()))
                .sinks(convertToSinks(taskBody.getSinks()))
                .tags(convertToTags(taskBody.getTags()));
    }

    private List<MonitoringTag> convertToTags(List<Pair<String, String>> tags) {
        return tags.stream()
                .map(pair -> new MonitoringTag().key(pair.getKey()).value(pair.getValue()))
                .collect(Collectors.toList());
    }

    private List<DataSink> convertToSinks(List<AdapterSink> sinks) {
        return sinks.stream()
                .map(adapterSink -> new DataSink()
                        .type(DataSink.TypeEnum.valueOf(adapterSink.getSinkType().name()))
                        ._configuration(convertToConfiguration(adapterSink.getConfiguration()))
                )
                .collect(Collectors.toList());
    }

    private Sensor convertToSensor(AdapterSensor sensor) {
        if (sensor instanceof AdapterPushSensor) {
            return new PushSensor()
                    .port(((AdapterPushSensor) sensor).getPort());
        } else if (sensor instanceof AdapterPullSensor) {
            return new PullSensor()
                    .className(((AdapterPullSensor) sensor).getClassName())
                    ._configuration(convertToConfiguration(((AdapterPullSensor) sensor).getConfiguration()))
                    .interval(convertToInterval(((AdapterPullSensor) sensor).getInterval()));
        }
        throw new RuntimeException(format("Exception during converting Sensor. Only PushSensor and PullSensor are supported. Current value: %s", sensor != null ? sensor.getClass().getSimpleName() : "null"));
    }

    private Interval convertToInterval(AdapterInterval interval) {
        return new Interval()
                .unit(Interval.UnitEnum.valueOf(interval.getUnit().name()))
                .period(interval.getPeriod());
    }

    private Map<String, String> convertToConfiguration(List<Pair<String, String>> configuration) {
        return configuration.stream()
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    private List<MonitoringTarget> convertToTargets(String taskName) {
        return Collections.singletonList(
                new MonitoringTarget().type(MonitoringTarget.TypeEnum.TASK).identifier(taskName));
    }

    @Override
    public void delete(AdapterMonitor taskBody) {

    }


}
