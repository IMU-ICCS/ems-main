/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.executioncontext.colosseum;

import com.google.common.collect.Lists;
import eu.melodic.upperware.adapter.exception.AmbiguousResultException;
import eu.melodic.upperware.adapter.executioncontext.ContextOperations;
import eu.melodic.upperware.adapter.properties.AdapterProperties;
import io.github.cloudiator.rest.ApiException;
import io.github.cloudiator.rest.api.JobApi;
import io.github.cloudiator.rest.api.MonitoringApi;
import io.github.cloudiator.rest.api.NodeApi;
import io.github.cloudiator.rest.api.ProcessApi;
import io.github.cloudiator.rest.model.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class ColosseumContext implements ContextOperations {

    private final JobApi jobApi;
    private final NodeApi nodeApi;
    private final ProcessApi processApi;
    private final MonitoringApi monitoringApi;

    private final AdapterProperties adapterProperties;

    private final List<Node> nodes = synchronizedList();
    private final List<Schedule> schedules = synchronizedList();
    private final List<ProcessGroup> processGroups = synchronizedList();
    private final List<Job> jobs = synchronizedList();
    private final List<Monitor> monitors = synchronizedList();

    private boolean loaded;

    private BiPredicate<MonitoringTarget, MonitoringTarget> monitoringTargetBiPredicate = (mt1, mt2) -> mt1.getType().equals(mt2.getType()) && mt1.getIdentifier().equals(mt2.getIdentifier());

    public synchronized void addNode(@NonNull Node node) {
        nodes.add(node);
    }

    public synchronized Optional<Node> getNode(String nodeName) {
        Objects.requireNonNull(nodeName);
        return getElement(nodes, node -> nodeName.equals(node.getName()),
                () -> new AmbiguousResultException(format("Ambiguous search result - there are more than one node with the same name=%s", nodeName)));
    }

    public synchronized void deleteNode(String nodeId) {
        nodes.removeIf(node -> nodeId.equals(node.getId()));
    }

    public synchronized void addSchedule(@NonNull Schedule schedule) {
        schedules.add(schedule);
    }

    public synchronized Optional<Schedule> getSchedule(String name) {
        return getElement(schedules, schedule -> name.equals(schedule.getId()), createAmbiguousResultException(Schedule.class, name));
    }

    public synchronized Optional<Schedule> getScheduleByJobId(String jobId) {
        return getElement(schedules, schedule -> jobId.equals(schedule.getJob()), () -> new AmbiguousResultException(format("Ambiguous search result - there are more than one schedules with the same jobId=%s", jobId)));
    }

    public synchronized void addProcessGroup(@NonNull ProcessGroup processGroup) {
        processGroups.add(processGroup);
    }

    public synchronized Optional<ProcessGroup> getProcessGroup(String processGroupId) {
        return getElement(processGroups, processGroup -> processGroupId.equals(processGroup.getId()), createAmbiguousResultException(ProcessGroup.class, processGroupId));
    }

    public synchronized Optional<ProcessGroup> getProcessGroup(String nodeId, String scheduleId, String taskName) throws ApiException {
        Objects.requireNonNull(nodeId);
        Objects.requireNonNull(scheduleId);
        Objects.requireNonNull(taskName);

        return processApi.findProcessGroups()
                .stream()
                .filter(processGroup ->
                        processGroup.getProcesses()
                                .stream()
                                .anyMatch(cloudiatorProcess -> scheduleId.equals(cloudiatorProcess.getSchedule()) &&
                                        taskName.equals(cloudiatorProcess.getTask()) &&
                                        checkProcess(cloudiatorProcess, nodeId)))
                .findFirst();
    }

    private synchronized boolean checkProcess(CloudiatorProcess cloudiatorProcess, String nodeId) {
        if (cloudiatorProcess instanceof SingleProcess) {
            String nodeName = ((SingleProcess) cloudiatorProcess).getNode();
            return nodeId.equals(nodeName);
        } else if (cloudiatorProcess instanceof ClusterProcess) {
            return ((ClusterProcess) cloudiatorProcess)
                    .getNodes()
                    .stream()
                    .anyMatch(s -> s.equals(nodeId));
        }
        log.warn("Cloudiator process is neither SingleProcess nor ClusterProcess but: {}", cloudiatorProcess.getClass().getSimpleName());
        return false;
    }

    public synchronized void deleteProcessGroup(String processGroupId) {
        processGroups.removeIf(processGroup -> processGroupId.equals(processGroup.getId()));
    }

    public synchronized void addJob(@NonNull Job job) {
        jobs.add(job);
    }

    public synchronized Optional<Job> getJob(String name) {
        return getElement(jobs, job -> name.equals(job.getName()), createAmbiguousResultException(Job.class, name));
    }

    private Supplier<AmbiguousResultException> createAmbiguousResultException(Class clazz, String id) {
        return () -> new AmbiguousResultException(format("Ambiguous search result - there are more than one %s with the same id=%s", clazz.getSimpleName(), id));
    }

    public synchronized Optional<Monitor> getMonitor(String metricName, MonitoringTarget monitoringTarget){
        Objects.requireNonNull(metricName);
        Objects.requireNonNull(monitoringTarget);
        Objects.requireNonNull(monitoringTarget.getType());
        Objects.requireNonNull(monitoringTarget.getIdentifier());
        return getElement(monitors, monitor -> metricName.equals(monitor.getMetric()) &&
                                        monitor.getTargets().stream().anyMatch(mt -> monitoringTargetBiPredicate.test(mt, monitoringTarget)),
                () -> new AmbiguousResultException(format("Ambiguous search result - there are more than one job with the same name=%s", metricName)));
    }

    private Optional<Monitor> getMonitor(String metricName){
        Objects.requireNonNull(metricName);
        return getElement(monitors, monitor -> metricName.equals(monitor.getMetric()),
                () -> new AmbiguousResultException(format("Ambiguous search result - there are more than one job with the same name=%s", metricName)));
    }

    public synchronized Optional<ProcessGroup> getProcessGroupByNodeId(String nodeId) {
        return getElement(processGroups, processGroup -> processGroup.getProcesses()
                .stream()
                .filter(SingleProcess.class::isInstance)
                .map(SingleProcess.class::cast)
                .anyMatch(cloudiatorProcess -> cloudiatorProcess.getNode().equals(nodeId)),
                    () -> new AmbiguousResultException(format("Ambiguous search result - there are more than one SingleProcess containing process with the same node=%s", nodeId)));
    }


    public synchronized void addMonitor(@NonNull Monitor monitor) {
        Optional<Monitor> monitorOpt = getMonitor(monitor.getMetric());
        if (!monitorOpt.isPresent()){
            monitors.add(monitor);
        } else {
            Monitor m = monitorOpt.get();

            monitor.getTargets()
                    .stream()
                    .filter(monitoringTarget -> m.getTargets().stream().noneMatch( mt1 -> monitoringTargetBiPredicate.test(mt1, monitoringTarget)))
                    .forEach(monitoringTarget ->  m.getTargets().add(monitoringTarget));
        }
    }

    public synchronized void deleteMonitor(String metricName, MonitoringTarget monitoringTarget) {
        getMonitor(metricName, monitoringTarget)
                .ifPresent(monitor -> {
                    monitor.getTargets().removeIf(mt1 ->  monitoringTargetBiPredicate.test(mt1, monitoringTarget));

                    if (CollectionUtils.isEmpty(monitor.getTargets())){
                        monitors.remove(monitor);
                    }
                });
    }

    private <T> Optional<T> getElement(List<T> collection, Predicate<T> predicate, Supplier<AmbiguousResultException> exceptionSupplier) {
        synchronized (collection) {
            return collection.stream()
                    .filter(predicate)
                    .collect(toSingleton(exceptionSupplier));
        }
    }

    private <T> Collector<T, ?, Optional<T>> toSingleton(Supplier<AmbiguousResultException> exceptionSupplier) {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.size() > 1) {
                        throw exceptionSupplier.get();
                    }
                    if (list.size() == 0) {
                        return Optional.empty();
                    }
                    return Optional.ofNullable(list.get(0));
                }
        );
    }

    @Override
    @Synchronized
    public void refreshContext() throws ApiException {
        log.info("Refreshing Colosseum context");

        nodes.clear();
        nodes.addAll(nodeApi.findNodes());

        schedules.clear();
        schedules.addAll(processApi.getSchedules());

        processGroups.clear();
        processGroups.addAll(processApi.findProcessGroups());

        jobs.clear();
        jobs.addAll(jobApi.findJobs());

        if (adapterProperties.getEms().isEnabled()) {
            monitors.clear();
            monitors.addAll(monitoringApi.findMonitors());
        }

        loaded = true;
    }

    @Override
    public boolean isLoaded() {
        return loaded;
    }

    private <E> List<E> synchronizedList() {
        return Collections.synchronizedList(Lists.newLinkedList());
    }

}
