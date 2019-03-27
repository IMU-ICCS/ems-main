/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.plangenerator.graph;

import eu.melodic.upperware.adapter.graphlogger.ToLogGraphLogger;
import eu.melodic.upperware.adapter.plangenerator.graph.model.MelodicGraph;
import eu.melodic.upperware.adapter.plangenerator.model.*;
import eu.melodic.upperware.adapter.plangenerator.tasks.*;
import eu.melodic.upperware.adapter.properties.AdapterProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Supplier;

import static eu.melodic.upperware.adapter.plangenerator.graph.model.Type.CONFIG;
import static eu.melodic.upperware.adapter.plangenerator.graph.model.Type.RECONFIG;
import static eu.melodic.upperware.adapter.plangenerator.tasks.Type.CREATE;
import static eu.melodic.upperware.adapter.plangenerator.tasks.Type.DELETE;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class DefaultGraphGenerator extends AbstractDefaultGraphGenerator<ComparableModel> {

    private ToLogGraphLogger toLogGraphLogger;
    private AdapterProperties adapterProperties;

    // compare two the same instances
    private static final BiPredicate<AdapterProcess, AdapterProcess> PROCESS_BI_PREDICATE = (newReq, oldReq) ->
            newReq.getNodeName().equals(oldReq.getNodeName()) &&
                    newReq.getScheduleName().equals((oldReq.getScheduleName())) &&
                    newReq.getJobName().equals(oldReq.getJobName()) &&
                    newReq.getTaskName().equals(oldReq.getTaskName());

    private static final BiPredicate<AdapterMonitor, AdapterMonitor> MONITOR_BI_PREDICATE = (newReq, oldReq) ->
            newReq.getMetricName().equals(oldReq.getMetricName()) &&
                    newReq.getNodeName().equals(oldReq.getNodeName());

    private static final BiPredicate<AdapterRequirement, AdapterRequirement> NODE_BI_PREDICATE = (newReq, oldReq) ->
            newReq.getNodeName().equals(oldReq.getNodeName());


    //link different tasks
    private static final BiPredicate<NodeTask, ProcessTask> NODE_TO_PROCESS_BI_PREDICATE = (nodeTask, processTask) ->
            nodeTask.getData().getNodeName().equals(processTask.getData().getNodeName()) &&
                    nodeTask.getType().equals(processTask.getType());

    private static final BiPredicate<ProcessTask, NodeTask> PROCESS_TO_NODE_BI_PREDICATE = (processTask, nodeTask) ->
            nodeTask.getData().getNodeName().equals(processTask.getData().getNodeName()) &&
                    nodeTask.getType().equals(processTask.getType());

    private static final BiPredicate<ProcessTask, MonitorTask> PROCESS_TO_MONITOR_BI_PREDICATE = (processTask, monitorTask) ->
            processTask.getData().getTaskName().equals(monitorTask.getData().getTaskName()) &&
                    processTask.getData().getNodeName().equals(monitorTask.getData().getNodeName()) &&
                    processTask.getType().equals(monitorTask.getType());

    private static final BiPredicate<MonitorTask, ProcessTask> MONITOR_TO_PROCESS_BI_PREDICATE = (monitorTask, processTask) ->
            processTask.getData().getTaskName().equals(monitorTask.getData().getTaskName()) &&
                    processTask.getData().getNodeName().equals(monitorTask.getData().getNodeName()) &&
                    processTask.getType().equals(monitorTask.getType());


    @Override
    public SimpleDirectedGraph<Task, DefaultEdge> generateGraph(ComparableModel newComparableModel) {
        return generateGraph(newComparableModel, ComparableModel.builder().build(), () -> new MelodicGraph<>(DefaultEdge.class, CONFIG));
    }

    @Override
    public SimpleDirectedGraph<Task, DefaultEdge> generateGraph(ComparableModel newComparableModel, ComparableModel oldComparableModel) {
        return generateGraph(newComparableModel, oldComparableModel, () -> new MelodicGraph<>(DefaultEdge.class, RECONFIG));
    }

    private MelodicGraph<Task, DefaultEdge> generateGraph(ComparableModel newComparableModel, ComparableModel oldComparableModel, Supplier<MelodicGraph<Task, DefaultEdge>> graphSupplier) {
        MelodicGraph<Task, DefaultEdge> graph = graphSupplier.get();

        //Job
        AdapterJob newAdapterJob = newComparableModel.getAdapterJob();
        AdapterJob oldAdapterJob = oldComparableModel.getAdapterJob();
        JobTask jobTask = null;
        if (!newAdapterJob.equals(oldAdapterJob)) {
            jobTask = createTask(graph, newAdapterJob, JobTask.JOB_TASK_CREATE);
        }
        Optional<JobTask> jobTaskOpt = Optional.ofNullable(jobTask);

        //Node
        List<AdapterRequirement> nodesToCreate = getDataToCreate(newComparableModel.getAdapterRequirements(), oldComparableModel.getAdapterRequirements(), NODE_BI_PREDICATE);
        List<NodeTask> nodeTasksToCreate = createTasks(graph, nodesToCreate, NodeTask.NODE_TASK_CREATE);

        //Job -> Schedule
        AdapterSchedule newAdapterSchedule = newComparableModel.getAdapterSchedule();
        AdapterSchedule oldAdapterSchedule = oldComparableModel.getAdapterSchedule();

        ScheduleTask scheduleTask = null;
        if (!newAdapterSchedule.equals(oldAdapterSchedule)) {
            scheduleTask = createTask(graph, newAdapterSchedule, ScheduleTask.SCHEDULE_TASK_CREATE);
            addEdge(graph, jobTask, scheduleTask, jobTaskOpt::isPresent);
        }
        Optional<ScheduleTask> scheduleTaskOpt = Optional.ofNullable(scheduleTask);

        //Node, Schedule -> Process
        List<AdapterProcess> processesToCreate = getDataToCreate(newComparableModel.getAdapterProcesses(), oldComparableModel.getAdapterProcesses(), PROCESS_BI_PREDICATE);
        List<ProcessTask> processTasksToCreate = createTasks(graph, processesToCreate, ProcessTask.PROCESS_TASK_CREATE);
        addEdge(graph, scheduleTask, processTasksToCreate, scheduleTaskOpt::isPresent);
        addEdge(graph, nodeTasksToCreate, processTasksToCreate, NODE_TO_PROCESS_BI_PREDICATE);

        //Process -> Monitors
        if (adapterProperties.getEms().isEnabled()) {
            List<AdapterMonitor> monitorsToCreate = getDataToCreate(newComparableModel.getAdapterMonitors(), oldComparableModel.getAdapterMonitors(), MONITOR_BI_PREDICATE);
            List<MonitorTask> monitorTasksToCreate = createTasks(graph, monitorsToCreate, MonitorTask.MONITOR_TASK_CREATE);
            addReverseEdge(graph, processTasksToCreate, monitorTasksToCreate, PROCESS_TO_MONITOR_BI_PREDICATE);
        }

        //Monitors, Process -> Wait
        //connect monitors to last tasks
        List<Task> tasksToCreateWithoutOutgoingEdges = getTasksWithoutOutgoingEdges(graph, CREATE);
        addWaitEdge(graph, () -> createTask(graph, new AdapterWaitData(), WaitTask.WAIT_TASK_CREATE), tasksToCreateWithoutOutgoingEdges, () -> CollectionUtils.isNotEmpty(tasksToCreateWithoutOutgoingEdges));

        //Wait -> Monitors (D)
        List<Task> lastCreateTasks = getTasksWithoutOutgoingEdges(graph, CREATE);
        Task lastCreateTask = CollectionUtils.isNotEmpty(lastCreateTasks) ? lastCreateTasks.get(0) : null;

        List<MonitorTask>  monitorTasksToDelete = Collections.emptyList();
        if (adapterProperties.getEms().isEnabled()) {
            List<AdapterMonitor> monitorsToDelete = getDataToDelete(newComparableModel.getAdapterMonitors(), oldComparableModel.getAdapterMonitors(), MONITOR_BI_PREDICATE);
            monitorTasksToDelete = createTasks(graph, monitorsToDelete, MonitorTask.MONITOR_TASK_DELETE);
            addEdge(graph, lastCreateTask, monitorTasksToDelete, () -> lastCreateTask != null);
        }

        //Monitors (D) -> Process (D)
        List<AdapterProcess> processesToDelete = getDataToDelete(newComparableModel.getAdapterProcesses(), oldComparableModel.getAdapterProcesses(), PROCESS_BI_PREDICATE);
        List<ProcessTask> processTasksToDelete = createTasks(graph, processesToDelete, ProcessTask.PROCESS_TASK_DELETE);

        if (CollectionUtils.isNotEmpty(monitorTasksToDelete)){
            addEdge(graph, monitorTasksToDelete, processTasksToDelete, MONITOR_TO_PROCESS_BI_PREDICATE);
        } else {
            addEdge(graph, lastCreateTask, processTasksToDelete, () -> lastCreateTask != null);
        }

        //Process (D) -> Nodes(D)
        List<AdapterRequirement> nodesToDelete = getDataToDelete(newComparableModel.getAdapterRequirements(), oldComparableModel.getAdapterRequirements(), NODE_BI_PREDICATE);
        List<NodeTask> nodeTasksToDelete = createTasks(graph, nodesToDelete, NodeTask.NODE_TASK_DELETE);
        addEdge(graph, processTasksToDelete, nodeTasksToDelete, PROCESS_TO_NODE_BI_PREDICATE);

        //Process (D) -> Schedule (D)
        //NOT implemented yet

        // Schedule (D) -> Job (D)
        //NOT implemented yet

        // Job (D) -> Wait (D)
        //NOT implemented yet
        List<Task> tasksToDeleteWithoutOutgoingEdges = getTasksWithoutOutgoingEdges(graph, DELETE);

        addWaitEdge(graph, () -> createTask(graph, new AdapterWaitData(), WaitTask.WAIT_TASK_DELETE), tasksToDeleteWithoutOutgoingEdges, () -> CollectionUtils.isNotEmpty(tasksToDeleteWithoutOutgoingEdges));

        toLogGraphLogger.logGraph(graph);
        log.info("Built {} graph: {}", graph.getType(), graph);
        return graph;
    }

    private List<Task> getTasksWithoutOutgoingEdges(MelodicGraph<Task, DefaultEdge> graph, Type create) {
        return graph.vertexSet()
                .stream()
                .filter(task -> graph.outgoingEdgesOf(task).isEmpty())
                .filter(task -> create.equals(task.getType()))
                .collect(toList());
    }

    private <T extends Data> List<T> getDataToCreate(Collection<T> p1, Collection<T> p2, BiPredicate<T, T> biPredicate) {
        return getData(p1, p2, biPredicate);
    }

    private <T extends Data> List<T> getDataToDelete(Collection<T> p1, Collection<T> p2, BiPredicate<T, T> biPredicate) {
        //we need to change arguments
        return getData(p2, p1, biPredicate);
    }

    private <T extends Data> List<T> getData(Collection<T> p1, Collection<T> p2, BiPredicate<T, T> biPredicate) {
        return p1.stream()
                .filter(oldReq -> p2.stream().noneMatch(newReq -> biPredicate.test(newReq, oldReq)))
                .collect(toList());
    }

    private <T extends Data, U extends Task> U createTask(MelodicGraph<Task, DefaultEdge> graph, T data, Function<T, U> function) {
        U task = function.apply(data);
        addVertex(graph, task);
        return task;
    }

    private <T extends Data, U extends Task> List<U> createTasks(MelodicGraph<Task, DefaultEdge> graph, List<T> data, Function<T, U> function) {

        return CollectionUtils.emptyIfNull(data)
                .stream()
                .map(t -> createTask(graph, t, function))
                .collect(toList());
    }

}