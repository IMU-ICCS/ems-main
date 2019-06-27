package eu.melodic.upperware.adapter.planexecutor.colosseum;

import eu.melodic.upperware.adapter.communication.colosseum.ColosseumApi;
import eu.melodic.upperware.adapter.exception.AdapterException;
import eu.melodic.upperware.adapter.executioncontext.colosseum.ColosseumContext;
import eu.melodic.upperware.adapter.plangenerator.model.*;
import eu.melodic.upperware.adapter.plangenerator.tasks.CheckFinishTask;
import eu.melodic.upperware.adapter.plangenerator.tasks.MonitorTask;
import io.github.cloudiator.rest.ApiException;
import io.github.cloudiator.rest.model.Queue;
import io.github.cloudiator.rest.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Slf4j
public class MonitorTaskExecutor extends WatchdogColosseumTaskExecutor<AdapterMonitor> {

    MonitorTaskExecutor(MonitorTask task, Collection<Future> predecessors,
                               ColosseumApi api, ColosseumContext context,
                               Function<CheckFinishTask, Future<Queue>> checkFinishTaskToFuture) {

        super(task, predecessors, api, context, checkFinishTaskToFuture);
    }

    @Override
    public void create(AdapterMonitor taskBody) {

        String nodeId = context.getNode(taskBody.getNodeName())
                .orElseThrow(() -> new AdapterException(format("Could not find Node with id %s", taskBody.getNodeName())))
                .getId();

        Monitor monitor = convertToMonitor(taskBody, nodeId);
        MonitoringTarget monitoringTarget = createMonitoringTarget(nodeId);

        Optional<Monitor> monitorOpt = context.getMonitor(taskBody.getMetricName(), monitoringTarget);
        if (monitorOpt.isPresent()) {
            log.info("There is already Monitor defined with metric: {}", taskBody.getMetricName());
            return;
        }

        try {
            Monitor addedMonitor = api.addMonitor(monitor);
            context.addMonitor(addedMonitor);
        } catch (ApiException e) {
            log.error("Could not add Monitor. Error code: {}, Response body: {}, ResponseHeaders: {}", e.getCode(), e.getResponseBody(), e.getResponseHeaders());
            throw new AdapterException("Problem during adding Monitor", e);
        }
    }

    private Monitor convertToMonitor(AdapterMonitor taskBody, String nodeId) {
        return new Monitor()
                .metric(taskBody.getMetricName())
                .targets(convertToTargets(nodeId))
                .sensor(convertToSensor(taskBody.getSensor()))
                .sinks(convertToSinks(taskBody.getSinks()))
                .tags(convertToTags(taskBody.getTags()));
    }

    private Map<String, String> convertToTags(List<Pair<String, String>> tags) {
        return tags.stream()
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
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
                    .port(((AdapterPushSensor) sensor).getPort())
                    .type(PushSensor.class.getSimpleName());
        } else if (sensor instanceof AdapterPullSensor) {
            return new PullSensor()
                    .className(((AdapterPullSensor) sensor).getClassName())
                    ._configuration(convertToConfiguration(((AdapterPullSensor) sensor).getConfiguration()))
                    .interval(convertToInterval(((AdapterPullSensor) sensor).getInterval()))
                    .type(PullSensor.class.getSimpleName());
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

    private List<MonitoringTarget> convertToTargets(String nodeId) {
        return Collections.singletonList(
                new MonitoringTarget()
                        .type(MonitoringTarget.TypeEnum.NODE)
                        .identifier(nodeId));
    }

    @Override
    public void delete(AdapterMonitor taskBody) {

        String nodeId = context.getNode(taskBody.getNodeName())
                .orElseThrow(() -> new AdapterException(format("Could not find Node with id %s", taskBody.getNodeName())))
                .getId();

        MonitoringTarget monitoringTarget = createMonitoringTarget(nodeId);

        if (!context.getMonitor(taskBody.getMetricName(), monitoringTarget).isPresent()) {
            log.warn("Monitor with metricName {} does not exist", taskBody.getMetricName());
            return;
        }

        log.info("Going to remove monitor {} with MonitoringTarget({}, {})", taskBody.getMetricName(), monitoringTarget.getType(), monitoringTarget.getIdentifier());

        try {
            api.deleteMonitor(taskBody.getMetricName(), monitoringTarget);
            context.deleteMonitor(taskBody.getMetricName(), monitoringTarget);
        } catch (ApiException e) {
            log.error("Could not delete Monitor with metricName {}. Error code: {}, Response body: {}, ResponseHeaders: {}",
                    taskBody.getMetricName(), e.getCode(), e.getResponseBody(), e.getResponseHeaders());
        }
    }

    private MonitoringTarget createMonitoringTarget(String nodeId) {
        MonitoringTarget monitoringTarget = new MonitoringTarget();
        monitoringTarget.setType(MonitoringTarget.TypeEnum.NODE);
        monitoringTarget.setIdentifier(nodeId);
        return monitoringTarget;
    }
}
