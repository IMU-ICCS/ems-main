package eu.melodic.upperware.adapter.planexecutor.colosseum;

import eu.melodic.upperware.adapter.communication.colosseum.ColosseumApi;
import eu.melodic.upperware.adapter.exception.AdapterException;
import eu.melodic.upperware.adapter.executioncontext.colosseum.ColosseumContext;
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
                               ColosseumExecutorFactory colosseumExecutorFactory) {

        super(task, predecessors, api, context, executor, colosseumExecutorFactory);
    }

    @Override
    public void create(AdapterMonitor taskBody) {

        NodeGroup nodeGroup = context.getNodeGroupByNodeName(taskBody.getNodeName())
                .orElseThrow(() -> new AdapterException(format("Could not find NodeGroup with id %s", taskBody.getNodeName())));

        ProcessGroup processGroup = getProcessGroup(nodeGroup)
                .orElseThrow(() -> new AdapterException(format("Could not find ProcessGroup (Cluster) with nodeGroupId %s and ProcessGroup (Simple) with nodeId %s", nodeGroup.getId(), getFistNodeId(nodeGroup))));

        Monitor monitor = convertToMonitor(taskBody, getFistProcessId(processGroup));
        Optional<Monitor> monitorOpt = context.getMonitor(taskBody.getMetricName());
        if (monitorOpt.isPresent()) {
            log.info("There is already Monitor defined with metric: {}", taskBody.getMetricName());
            return;
        }

        try {
            Monitor addedMonitor = api.addMonitor(monitor);
            context.addMonitor(addedMonitor);
        } catch (ApiException e) {
            log.error("Could not add Monitor. Error code: {}, Response body: {}, ResponseHeaders: {}", e.getCode(), e.getResponseBody(), e.getResponseHeaders());
            throw new AdapterException("Problem during adding Node", e);
        }
    }

    private Optional<ProcessGroup> getProcessGroup(NodeGroup nodeGroup) {

        Optional<ProcessGroup> clusterProcessGroupOpt = context.getProcessGroupByNodeGroupId(nodeGroup.getId());
        if (clusterProcessGroupOpt.isPresent()) {
            ProcessGroup processGroup = clusterProcessGroupOpt.get();
            log.info("Found ProcessGroup () with ClusterProcess () by nodeGroupId {}", processGroup.getId(), getFistProcessId(processGroup), nodeGroup.getId());
            return clusterProcessGroupOpt;
        }

        String nodeId = getFistNodeId(nodeGroup);
        Optional<ProcessGroup> singleProcessGroupOpt = context.getProcessGroupByNodeId(nodeId);
        if (singleProcessGroupOpt.isPresent()) {
            ProcessGroup processGroup = singleProcessGroupOpt.get();
            log.info("Found ProcessGroup () with SingleProcess () by nodeId {}", processGroup.getId(), getFistProcessId(processGroup), nodeId);
            return singleProcessGroupOpt;
        }

        return Optional.empty();
    }

    private String getFistNodeId(NodeGroup nodeGroup){
        return nodeGroup.getNodes().get(0).getId();
    }

    private String getFistProcessId(ProcessGroup processGroup){
        return processGroup.getProcesses().get(0).getId();
    }

    private Monitor convertToMonitor(AdapterMonitor taskBody, String processGroupId) {
        return new Monitor()
                .metric(taskBody.getMetricName())
                .targets(convertToTargets(processGroupId))
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

    private List<MonitoringTarget> convertToTargets(String processGroupId) {
        return Collections.singletonList(
                new MonitoringTarget().type(MonitoringTarget.TypeEnum.PROCESS).identifier(processGroupId));
    }

    @Override
    public void delete(AdapterMonitor taskBody) {
        if (!context.getMonitor(taskBody.getMetricName()).isPresent()) {
            log.warn("Monitor with metricName {} does not exist", taskBody.getMetricName());
            return;
        }

        log.info("Going to remove monitor {}", taskBody.getMetricName());

        try {
            api.deleteMonitor(taskBody.getMetricName());
            context.deleteMonitor(taskBody.getMetricName());
        } catch (ApiException e) {
            log.error("Could not delete Monitor with metricName. Error code: {}, Response body: {}, ResponseHeaders: {}",
                    taskBody.getMetricName(), e.getCode(), e.getResponseBody(), e.getResponseHeaders());
        }
    }
}
