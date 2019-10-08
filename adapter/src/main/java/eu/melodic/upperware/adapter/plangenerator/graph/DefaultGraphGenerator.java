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
import eu.melodic.upperware.adapter.plangenerator.graph.model.DividedElement;
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

import java.util.*;
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

    private DiffCalculator<AdapterRequirement, String> requirementDiffCalculator;
    private DiffCalculator<AdapterProcess, String> processDiffCalculator;
    private DiffCalculator<AdapterMonitor, String> monitorDiffCalculator;

    private static final BiPredicate<NodeTask, ScaleTask> NODE_TO_SCALE_BI_PREDICATE = (nodeTask, scaleTask) ->
            scaleTask.getData().getNodeNames().contains(nodeTask.getData().getNodeName()) &&
                    scaleTask.getType().equals(nodeTask.getType());

    private static final BiPredicate<ProcessTask, NodeTask> PROCESS_TO_NODE_BI_PREDICATE = (processTask, nodeTask) ->
            nodeTask.getData().getNodeName().equals(processTask.getData().getNodeName()) &&
                    nodeTask.getType().equals(processTask.getType());

    private static final BiPredicate<ProcessTask, MonitorTask> PROCESS_TO_MONITOR_BI_PREDICATE = (processTask, monitorTask) ->
            processTask.getData().getNodeName().equals(monitorTask.getData().getNodeName()) &&
            processTask.getData().getJobName().equals(monitorTask.getData().getJobName()) &&
            processTask.getData().getTaskName().equals(monitorTask.getData().getTaskName());

    private static final BiPredicate<ScaleTask, MonitorTask> SCALE_TO_MONITOR_BI_PREDICATE = (scaleTask, monitorTask) ->
            scaleTask.getData().getNodeNames().contains(monitorTask.getData().getNodeName()) &&
                    scaleTask.getData().getJobName().equals(monitorTask.getData().getJobName()) &&
                    scaleTask.getData().getTaskName().equals(monitorTask.getData().getTaskName());

    private static final BiPredicate<MonitorTask, ScaleTask> MONITOR_TO_SCALE_BI_PREDICATE = (monitorTask, scaleTask) ->
            SCALE_TO_MONITOR_BI_PREDICATE.test(scaleTask, monitorTask);

    private static final BiPredicate<MonitorTask, ProcessTask> MONITOR_TO_PROCESS_BI_PREDICATE = (monitorTask, processTask) ->
            PROCESS_TO_MONITOR_BI_PREDICATE.test(processTask, monitorTask);

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

        Map<String, DividedElement<AdapterRequirement>> adapterRequirementDiff = requirementDiffCalculator.calculateDiff(
                newComparableModel.getAdapterRequirements(),
                oldComparableModel.getAdapterRequirements(),
                AdapterRequirement.NODE_BI_PREDICATE,
                AdapterRequirement::getTaskName);
        requirementDiffCalculator.print("AdapterRequirement", adapterRequirementDiff);

        Map<String, DividedElement<AdapterProcess>> adapterProcessDiff = processDiffCalculator.calculateDiff(
                newComparableModel.getAdapterProcesses(),
                oldComparableModel.getAdapterProcesses(),
                AdapterProcess.PROCESS_BI_PREDICATE,
                AdapterProcess::getTaskName);
        processDiffCalculator.print("AdapterProcess", adapterProcessDiff);

        Map<String, DividedElement<AdapterMonitor>> adapterMonitorDiff = Collections.EMPTY_MAP;
        if (adapterProperties.getEms().isEnabled()) {
            adapterMonitorDiff = monitorDiffCalculator.calculateDiff(
                    newComparableModel.getAdapterMonitors(),
                    oldComparableModel.getAdapterMonitors(),
                    AdapterMonitor.MONITOR_BI_PREDICATE,
                    AdapterMonitor::getTaskName);
            monitorDiffCalculator.print("AdapterMonitor", adapterMonitorDiff);
        }

        //Job
        AdapterJob newAdapterJob = newComparableModel.getAdapterJob();
        AdapterJob oldAdapterJob = oldComparableModel.getAdapterJob();
        JobTask jobTask = null;
        if (!newAdapterJob.equals(oldAdapterJob)) {
            jobTask = createTask(graph, newAdapterJob, JobTask.JOB_TASK_CREATE);
        }
        Optional<JobTask> jobTaskOpt = Optional.ofNullable(jobTask);

        //Node
        List<AdapterRequirement> nodesToCreate = requirementDiffCalculator.getToCreate(adapterRequirementDiff);
        List<NodeTask> nodeTasksToCreate = createTasks(graph, nodesToCreate, NodeTask.NODE_TASK_CREATE);

//        //Process -> Monitors
        List<MonitorTask> monitorTasksToCreate = new ArrayList<>();
        if (adapterProperties.getEms().isEnabled()) {
            log.info("EMS enabled = {}, newComparableModel.getAdapterMonitors(): {}, oldComparableModel.getAdapterMonitors(): {}", adapterProperties.getEms().isEnabled(),
                    newComparableModel.getAdapterMonitors().size(), oldComparableModel.getAdapterMonitors().size());

            List<AdapterMonitor> monitorsToCreate = monitorDiffCalculator.getToCreate(adapterMonitorDiff);
            monitorTasksToCreate = createTasks(graph, monitorsToCreate, MonitorTask.MONITOR_TASK_CREATE);
        }

        //Job -> Schedule
        AdapterSchedule newAdapterSchedule = newComparableModel.getAdapterSchedule();
        AdapterSchedule oldAdapterSchedule = oldComparableModel.getAdapterSchedule();

        ScheduleTask scheduleTask = null;
        if (!newAdapterSchedule.equals(oldAdapterSchedule)) {
            scheduleTask = createTask(graph, newAdapterSchedule, ScheduleTask.SCHEDULE_TASK_CREATE);
            addEdge(graph, jobTask, scheduleTask, jobTaskOpt::isPresent);
        }
        Optional<ScheduleTask> scheduleTaskOpt = Optional.ofNullable(scheduleTask);


        for (String taskName : adapterProcessDiff.keySet()) {
            DividedElement<AdapterProcess> dividedElement = adapterProcessDiff.get(taskName);
            log.info("CREATE Working with {} -> toCreate: {}, toRemain: {}, toDelete: {}", taskName, dividedElement.getToCreate().size(), dividedElement.getToRemain().size(), dividedElement.getToDelete().size());

            List<AdapterProcess> toCreate = dividedElement.getToCreate();
            if (CollectionUtils.isNotEmpty(toCreate)) {
                boolean alreadyCreated = !dividedElement.getToRemain().isEmpty() || !dividedElement.getToDelete().isEmpty();
                if (alreadyCreated) {
                    log.info("First instance has already be created");
                    //create TRIGGER_SCALE
                    final ScaleTask scaleTaskToCreate = createTask(graph, createScaleTask(toCreate), ScaleTask.SCALE_TASK_CREATE);
                    addEdge(graph, scheduleTask, scaleTaskToCreate, scheduleTaskOpt::isPresent);
                    addEdge(graph, nodeTasksToCreate, scaleTaskToCreate, NODE_TO_SCALE_BI_PREDICATE);
                    addEdge(graph, scaleTaskToCreate, monitorTasksToCreate, SCALE_TO_MONITOR_BI_PREDICATE);
                } else {
                    //create PROCESS
                    log.info("Creating first process...");
                    AdapterProcess toCreateFirst = toCreate.get(0);
                    List<AdapterProcess> toCreateWithoutFirst = toCreate.stream()
                            .skip(1)
                            .collect(toList());

                    ProcessTask processTask = createTask(graph, toCreateFirst, ProcessTask.PROCESS_TASK_CREATE);
                    addEdge(graph, scheduleTask, processTask, scheduleTaskOpt::isPresent);
                    addEdge(graph, processTask, monitorTasksToCreate, PROCESS_TO_MONITOR_BI_PREDICATE);

                    nodeTasksToCreate
                            .stream()
                            .filter(nodeTask -> nodeTask.getData().getNodeName().equalsIgnoreCase(toCreateFirst.getNodeName()))
                            .findFirst()
                            .ifPresent(nodeTask -> addEdge(graph, nodeTask, processTask, () -> true));

                    //crete TRIGGER_SCALE
                    if (CollectionUtils.isNotEmpty(toCreateWithoutFirst)) {
                        log.info("...and {} scaleTasks", toCreateWithoutFirst.size());
                        ScaleTask scaleTask = createTask(graph, createScaleTask(toCreateWithoutFirst), ScaleTask.SCALE_TASK_CREATE);

                        addEdge(graph, processTask, scaleTask, () -> true);

                        List<NodeTask> remainedNodes = nodeTasksToCreate
                                .stream()
                                .filter(nodeTask -> !nodeTask.getData().getNodeName().equalsIgnoreCase(toCreateFirst.getNodeName()))
                                .collect(toList());

                        addEdge(graph, remainedNodes, scaleTask, NODE_TO_SCALE_BI_PREDICATE);
                        addEdge(graph, scaleTask, monitorTasksToCreate, SCALE_TO_MONITOR_BI_PREDICATE);
                    } else {
                        log.info("...and no scaleTasks");
                    }
                }
            }
        }

        //Monitors, Process -> Wait
        //connect monitors to last tasks
        List<Task> tasksToCreateWithoutOutgoingEdges = getTasksWithoutOutgoingEdges(graph, CREATE);
        addWaitEdge(graph, () -> createTask(graph, new AdapterWaitData(), WaitTask.WAIT_TASK_CREATE), tasksToCreateWithoutOutgoingEdges, () -> CollectionUtils.isNotEmpty(tasksToCreateWithoutOutgoingEdges));

        //Wait -> Monitors (D)
        List<WaitTask> lastCreateTasks = getTasksWithoutOutgoingEdges(graph, CREATE, WaitTask.class);
        WaitTask lastCreateTask = CollectionUtils.isNotEmpty(lastCreateTasks) ? lastCreateTasks.get(0) : null;

        List<MonitorTask> monitorTasksToDelete = Collections.emptyList();
        if (adapterProperties.getEms().isEnabled()) {
            List<AdapterMonitor> monitorsToDelete = monitorDiffCalculator.getToDelete(adapterMonitorDiff);
            monitorTasksToDelete = createTasks(graph, monitorsToDelete, MonitorTask.MONITOR_TASK_DELETE);
            addEdge(graph, lastCreateTask, monitorTasksToDelete, () -> lastCreateTask != null);
        }

        for (String taskName : adapterProcessDiff.keySet()) {
            DividedElement<AdapterProcess> dividedElement = adapterProcessDiff.get(taskName);

            log.info("DELETE Working with {} -> toCreate: {}, toRemain: {}, toDelete: {}", taskName, dividedElement.getToCreate().size(), dividedElement.getToRemain().size(), dividedElement.getToDelete().size());

            List<AdapterProcess> toDelete = dividedElement.getToDelete();

            if (CollectionUtils.isNotEmpty(toDelete)) {
                boolean hasExistingProcesses = atLeastOneProcessShouldRemain(dividedElement);
                if (hasExistingProcesses) {
                    //ScaleIn
                    log.info("DELETE Working with {} -> hasExistingProcesses: {}, {} ScaleTasks will be deleted", taskName, hasExistingProcesses, toDelete.size());

                    final AdapterScale scaleTask = createScaleTask(toDelete);
                    createTask(graph, scaleTask, ScaleTask.SCALE_TASK_DELETE);
                } else {
                    AdapterProcess toDeleteFirst = toDelete.get(0);
                    List<AdapterProcess> toDeleteWithoutFirst = toDelete.stream()
                            .skip(1)
                            .collect(toList());
                    log.info("DELETE Working with {} -> hasExistingProcesses: {}, {} ScaleTasks, and 1 ProcessTask will be deleted", taskName, hasExistingProcesses, toDeleteWithoutFirst.size());

                    ProcessTask processTaskToDelete = createTask(graph, toDeleteFirst, ProcessTask.PROCESS_TASK_DELETE);
                    if (CollectionUtils.isNotEmpty(toDeleteWithoutFirst)) {
                        final ScaleTask scaleTaskToDelete = createTask(graph, createScaleTask(toDeleteWithoutFirst), ScaleTask.SCALE_TASK_DELETE);
                        addEdge(graph, scaleTaskToDelete, processTaskToDelete);
                    }
                }
            }
        }

        // 1
        List<ProcessTask> processTasksToDelete = getTasksWithoutOutgoingEdges(graph, DELETE, ProcessTask.class);

        //Here
        addEdge(graph, monitorTasksToDelete, processTasksToDelete, DefaultGraphGenerator.MONITOR_TO_PROCESS_BI_PREDICATE);

        //Process (D) -> Nodes(D)
        List<AdapterRequirement> filteredNodesToDelete = requirementDiffCalculator.getToDelete(adapterRequirementDiff).stream()
                .filter(adapterRequirement -> processTasksToDelete.stream()
                        .anyMatch(processTask -> processTask.getData().getNodeName().equals(adapterRequirement.getNodeName())))
                .collect(toList());

        List<NodeTask> nodeTasksToDelete = createTasks(graph, filteredNodesToDelete, NodeTask.NODE_TASK_DELETE);

        addEdge(graph, processTasksToDelete, nodeTasksToDelete, PROCESS_TO_NODE_BI_PREDICATE);
        addEdge(graph, lastCreateTask, processTasksToDelete, () -> lastCreateTask != null);

        List<ScaleTask> scaleTasksToDelete = getTasksWithoutOutgoingEdges(graph, DELETE, ScaleTask.class);

        addEdge(graph, monitorTasksToDelete, scaleTasksToDelete, DefaultGraphGenerator.MONITOR_TO_SCALE_BI_PREDICATE);

        addEdge(graph, lastCreateTask, scaleTasksToDelete, () -> lastCreateTask != null);

        List<Task> tasksToDeleteWithoutOutgoingEdges = getTasksWithoutOutgoingEdges(graph, DELETE);

        addWaitEdge(graph, () -> createTask(graph, new AdapterWaitData(), WaitTask.WAIT_TASK_DELETE), tasksToDeleteWithoutOutgoingEdges, () -> CollectionUtils.isNotEmpty(tasksToDeleteWithoutOutgoingEdges));

        toLogGraphLogger.logGraph(graph);
        log.info("Built {} graph: {}", graph.getType(), graph);
        return graph;
    }

    private boolean atLeastOneProcessShouldRemain(DividedElement dividedElement) {
        return CollectionUtils.isNotEmpty(dividedElement.getToRemain()) || CollectionUtils.isNotEmpty(dividedElement.getToCreate());
    }

    private <T extends Task> List<T> getTasksWithoutOutgoingEdges(MelodicGraph<Task, DefaultEdge> graph, Type type, Class<T> clazz) {
        return getTasksWithoutOutgoingEdges(graph, type)
                .stream()
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .collect(toList());
    }

    private List<Task> getTasksWithoutOutgoingEdges(MelodicGraph<Task, DefaultEdge> graph, Type type) {
        return graph.vertexSet()
                .stream()
                .filter(task -> graph.outgoingEdgesOf(task).isEmpty())
                .filter(task -> type.equals(task.getType()))
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

    private AdapterScale createScaleTask(List<AdapterProcess> processes) {
        final AdapterScale.AdapterScaleBuilder builder = AdapterScale.builder();

        if (CollectionUtils.isNotEmpty(processes)) {
            final AdapterProcess adapterProcess = processes.get(0);

            builder.jobName(adapterProcess.getJobName())
                    .taskName(adapterProcess.getTaskName())
                    .scheduleName(adapterProcess.getScheduleName())
                    .nodeNames(processes.stream().map(AdapterProcess::getNodeName).collect(toList()));
        }

        return builder.build();
    }

}