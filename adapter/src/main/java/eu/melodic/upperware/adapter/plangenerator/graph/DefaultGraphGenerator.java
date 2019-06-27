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
import lombok.Getter;
import lombok.Singular;
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
import java.util.stream.Stream;

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
//    private static final BiPredicate<NodeTask, ProcessTask> NODE_TO_PROCESS_BI_PREDICATE = (nodeTask, processTask) ->
//            nodeTask.getData().getNodeName().equals(processTask.getData().getNodeName()) &&
//                    nodeTask.getType().equals(processTask.getType());

    private static final BiPredicate<NodeTask, ScaleTask> NODE_TO_SCALE_BI_PREDICATE = (nodeTask, scaleTask) ->
            nodeTask.getData().getNodeName().equals(scaleTask.getData().getNodeName()) &&
                    nodeTask.getType().equals(scaleTask.getType());

    private static final BiPredicate<ProcessTask, NodeTask> PROCESS_TO_NODE_BI_PREDICATE = (processTask, nodeTask) ->
            nodeTask.getData().getNodeName().equals(processTask.getData().getNodeName()) &&
                    nodeTask.getType().equals(processTask.getType());

    private static final BiPredicate<ScaleTask, NodeTask> SCALE_TO_NODE_BI_PREDICATE = (scaleTask, nodeTask) ->
            nodeTask.getData().getNodeName().equals(scaleTask.getData().getNodeName()) &&
                    nodeTask.getType().equals(scaleTask.getType());

    private static final BiPredicate<MonitorTask, NodeTask> MONITOR_TO_NODE_BI_PREDICATE = (monitorTask, nodeTask) ->
            nodeTask.getData().getNodeName().equals(monitorTask.getData().getNodeName()) &&
                    nodeTask.getType().equals(monitorTask.getType());

//    private static final BiPredicate<ProcessTask, MonitorTask> PROCESS_TO_MONITOR_BI_PREDICATE = (processTask, monitorTask) ->
//            processTask.getData().getTaskName().equals(monitorTask.getData().getTaskName()) &&
//                    processTask.getData().getNodeName().equals(monitorTask.getData().getNodeName()) &&
//                    processTask.getType().equals(monitorTask.getType());

    private static final BiPredicate<NodeTask, MonitorTask> NODES_TO_MONITOR_BI_PREDICATE = (nodeTask, monitorTask) -> {
        return nodeTask.getData().getTaskName().equals(monitorTask.getData().getTaskName()) &&
                nodeTask.getData().getNodeName().equals(monitorTask.getData().getNodeName()) &&
                nodeTask.getType().equals(monitorTask.getType());
    };


    private static final BiPredicate<ScaleTask, MonitorTask> SCALE_TO_MONITOR_BI_PREDICATE = (scaleTask, monitorTask) ->
            scaleTask.getData().getTaskName().equals(monitorTask.getData().getTaskName()) &&
                    scaleTask.getData().getNodeName().equals(monitorTask.getData().getNodeName()) &&
                    scaleTask.getType().equals(monitorTask.getType());

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

        //Process -> Monitors
        if (adapterProperties.getEms().isEnabled()) {
            log.info("EMS enabled = {}, newComparableModel.getAdapterMonitors(): {}, oldComparableModel.getAdapterMonitors(): {}", adapterProperties.getEms().isEnabled(),
                    newComparableModel.getAdapterMonitors().size(), oldComparableModel.getAdapterMonitors().size());

            List<AdapterMonitor> monitorsToCreate = getDataToCreate(newComparableModel.getAdapterMonitors(), oldComparableModel.getAdapterMonitors(), MONITOR_BI_PREDICATE);
            List<MonitorTask> monitorTasksToCreate = createTasks(graph, monitorsToCreate, MonitorTask.MONITOR_TASK_CREATE);

            log.info("monitorsToCreate: {}, monitorTasksToCreate: {}", monitorsToCreate.size(), monitorTasksToCreate.size());
            addReverseEdge(graph, nodeTasksToCreate, monitorTasksToCreate, NODES_TO_MONITOR_BI_PREDICATE);
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


        // BEGIN
        EntireElement entireElement = new EntireElement(newComparableModel.getAdapterProcesses(), oldComparableModel.getAdapterProcesses());
        for (String taskName : entireElement.getElements().keySet()) {
            DividedElement dividedElement = entireElement.getElements().get(taskName);
            log.info("CREATE Working with {} -> toCreate: {}, toRemain: {}, toDelete: {}", taskName, dividedElement.toCreate.size(), dividedElement.toRemain.size(), dividedElement.toDelete.size());

            List<AdapterProcess> toCreate = dividedElement.getToCreate();
            if (CollectionUtils.isNotEmpty(toCreate)) {
                boolean alreadyCreated = !dividedElement.getToRemain().isEmpty() || !dividedElement.getToDelete().isEmpty();
                if (alreadyCreated) {
                    log.info("First instance has already be created");
                    //create TRIGGER_SCALE
                    List<ScaleTask> scalesTasksToCreate = createTasks(graph, toCreate, ScaleTask.SCALE_TASK_CREATE);
                    addEdge(graph, scheduleTask, scalesTasksToCreate, scheduleTaskOpt::isPresent);
                    addEdge(graph, nodeTasksToCreate, scalesTasksToCreate, NODE_TO_SCALE_BI_PREDICATE);
                } else {
                    //create PROCESS
                    log.info("Creating first process...");
                    AdapterProcess toCreateFirst = toCreate.get(0);
                    List<AdapterProcess> toCreateWithoutFirst = toCreate.stream()
                            .skip(1)
                            .collect(toList());

                        ProcessTask processTask = createTask(graph, toCreateFirst, ProcessTask.PROCESS_TASK_CREATE);
                        addEdge(graph, scheduleTask, processTask, scheduleTaskOpt::isPresent);

                        nodeTasksToCreate
                                .stream()
                                .filter(nodeTask -> nodeTask.getData().getNodeName().equalsIgnoreCase(toCreateFirst.getNodeName()))
                                .findFirst()
                                .ifPresent(nodeTask -> addEdge(graph, nodeTask, processTask, scheduleTaskOpt::isPresent));

                        //crete TRIGGER_SCALE
                        if (CollectionUtils.isNotEmpty(toCreateWithoutFirst)) {
                            log.info("...and {} scaleTasks", toCreateWithoutFirst.size());
                            List<ScaleTask> scalesTasksToCreate = createTasks(graph, toCreateWithoutFirst, ScaleTask.SCALE_TASK_CREATE);
                            addEdge(graph, processTask, scalesTasksToCreate, scheduleTaskOpt::isPresent);

                            List<NodeTask> remainedNodes = nodeTasksToCreate
                                    .stream()
                                    .filter(nodeTask -> !nodeTask.getData().getNodeName().equalsIgnoreCase(toCreateFirst.getNodeName()))
                                    .collect(toList());

                            addEdge(graph, remainedNodes, scalesTasksToCreate, NODE_TO_SCALE_BI_PREDICATE);
                        } else {
                            log.info("...and no scaleTasks");
                        }
                }
            }
        }








                // END




////WORKING START
//        List<AdapterProcess> processesToCreate = getDataToCreate(newComparableModel.getAdapterProcesses(), oldComparableModel.getAdapterProcesses(), PROCESS_BI_PREDICATE);
//
//        Map<String, List<AdapterProcess>> collect = processesToCreate.stream()
//                .collect(groupingBy(AdapterProcess::getTaskName));
//
//        for (String taskName : collect.keySet()) {
//            log.info("Working with {}", taskName);
//            List<AdapterProcess> adapterProcesses = collect.get(taskName);
//            if (isAlreadyCreated(oldComparableModel.getAdapterProcesses())) {
//                log.info("First instance has already be created");
//                //create TRIGGER_SCALE
//                List<ScaleTask> scalesTasksToCreate = createTasks(graph, adapterProcesses, ScaleTask.SCALE_TASK_CREATE);
//                addEdge(graph, scheduleTask, scalesTasksToCreate, scheduleTaskOpt::isPresent);
//                addEdge(graph, nodeTasksToCreate, scalesTasksToCreate, NODE_TO_SCALE_BI_PREDICATE);
//            } else {
//                //create PROCESS
//                log.info("Creating first process...");
//                Optional<AdapterProcess> adapterProcess = getFirst(adapterProcesses);
//
//                if (adapterProcess.isPresent()) {
//                    AdapterProcess ap = adapterProcess.get();
//                    ProcessTask processTask = createTask(graph, ap, ProcessTask.PROCESS_TASK_CREATE);
//                    addEdge(graph, scheduleTask, processTask, scheduleTaskOpt::isPresent);
//
//                    nodeTasksToCreate
//                            .stream()
//                            .filter(nodeTask -> nodeTask.getData().getNodeName().equalsIgnoreCase(ap.getNodeName()))
//                            .findFirst()
//                            .ifPresent(nodeTask -> addEdge(graph, nodeTask, processTask, scheduleTaskOpt::isPresent));
//
//                    //crete TRIGGER_SCALE
//                    List<AdapterProcess> scalesToCreate = getRest(adapterProcesses);
//                    if (CollectionUtils.isNotEmpty(scalesToCreate)) {
//                        log.info("...and {} scaleTasks", scalesToCreate.size());
//                        List<ScaleTask> scalesTasksToCreate = createTasks(graph, scalesToCreate, ScaleTask.SCALE_TASK_CREATE);
//                        addEdge(graph, processTask, scalesTasksToCreate, scheduleTaskOpt::isPresent);
//
//                        List<NodeTask> remainedNodes = nodeTasksToCreate
//                                .stream()
//                                .filter(nodeTask -> !nodeTask.getData().getNodeName().equalsIgnoreCase(ap.getNodeName()))
//                                .collect(toList());
//
//                        addEdge(graph, remainedNodes, scalesTasksToCreate, NODE_TO_SCALE_BI_PREDICATE);
//                    } else {
//                        log.info("...and no scaleTasks");
//                    }
//                }
//            }
//        }
////WORKING END

//
//        if (graph.getType().equals(CONFIG)) {
//            //Node, Schedule -> Process
//            List<AdapterProcess> processesToCreate = getDataToCreate(newComparableModel.getAdapterProcesses(), oldComparableModel.getAdapterProcesses(), PROCESS_BI_PREDICATE);
//            List<ProcessTask> processTasksToCreate = createTasks(graph, processesToCreate, ProcessTask.PROCESS_TASK_CREATE);
//            addEdge(graph, scheduleTask, processTasksToCreate, scheduleTaskOpt::isPresent);
//            addEdge(graph, nodeTasksToCreate, processTasksToCreate, NODE_TO_PROCESS_BI_PREDICATE);
//
//            //Process -> Monitors
//            if (adapterProperties.getEms().isEnabled()) {
//                List<AdapterMonitor> monitorsToCreate = getDataToCreate(newComparableModel.getAdapterMonitors(), oldComparableModel.getAdapterMonitors(), MONITOR_BI_PREDICATE);
//                List<MonitorTask> monitorTasksToCreate = createTasks(graph, monitorsToCreate, MonitorTask.MONITOR_TASK_CREATE);
//                addReverseEdge(graph, processTasksToCreate, monitorTasksToCreate, PROCESS_TO_MONITOR_BI_PREDICATE);
//            }
//        } else {
//            //Node, Schedule -> Scale
//            List<AdapterProcess> scalesToCreate = getDataToCreate(newComparableModel.getAdapterProcesses(), oldComparableModel.getAdapterProcesses(), PROCESS_BI_PREDICATE);
//            List<ScaleTask> scalesTasksToCreate = createTasks(graph, scalesToCreate, ScaleTask.SCALE_TASK_CREATE);
//            addEdge(graph, scheduleTask, scalesTasksToCreate, scheduleTaskOpt::isPresent);
//            addEdge(graph, nodeTasksToCreate, scalesTasksToCreate, NODE_TO_SCALE_BI_PREDICATE);
//
//            //Scale -> Monitors
//            if (adapterProperties.getEms().isEnabled()) {
//                List<AdapterMonitor> monitorsToCreate = getDataToCreate(newComparableModel.getAdapterMonitors(), oldComparableModel.getAdapterMonitors(), MONITOR_BI_PREDICATE);
//                List<MonitorTask> monitorTasksToCreate = createTasks(graph, monitorsToCreate, MonitorTask.MONITOR_TASK_CREATE);
//                addReverseEdge(graph, scalesTasksToCreate, monitorTasksToCreate, SCALE_TO_MONITOR_BI_PREDICATE);
//            }
//        }
//
//        //Node, Schedule -> Process
//        List<AdapterProcess> processesToCreate = getDataToCreate(newComparableModel.getAdapterProcesses(), oldComparableModel.getAdapterProcesses(), PROCESS_BI_PREDICATE);
//        List<ProcessTask> processTasksToCreate = createTasks(graph, processesToCreate, ProcessTask.PROCESS_TASK_CREATE);
//        addEdge(graph, scheduleTask, processTasksToCreate, scheduleTaskOpt::isPresent);
//        addEdge(graph, nodeTasksToCreate, processTasksToCreate, NODE_TO_PROCESS_BI_PREDICATE);
//
//        //Process -> Monitors
//        if (adapterProperties.getEms().isEnabled()) {
//            List<AdapterMonitor> monitorsToCreate = getDataToCreate(newComparableModel.getAdapterMonitors(), oldComparableModel.getAdapterMonitors(), MONITOR_BI_PREDICATE);
//            List<MonitorTask> monitorTasksToCreate = createTasks(graph, monitorsToCreate, MonitorTask.MONITOR_TASK_CREATE);
//            addReverseEdge(graph, processTasksToCreate, monitorTasksToCreate, PROCESS_TO_MONITOR_BI_PREDICATE);
//        }

        //Monitors, Process -> Wait
        //connect monitors to last tasks
        List<Task> tasksToCreateWithoutOutgoingEdges = getTasksWithoutOutgoingEdges(graph, CREATE);
        addWaitEdge(graph, () -> createTask(graph, new AdapterWaitData(), WaitTask.WAIT_TASK_CREATE), tasksToCreateWithoutOutgoingEdges, () -> CollectionUtils.isNotEmpty(tasksToCreateWithoutOutgoingEdges));

        //Wait -> Monitors (D)
        List<WaitTask> lastCreateTasks = getTasksWithoutOutgoingEdges(graph, CREATE, WaitTask.class);
        WaitTask lastCreateTask = CollectionUtils.isNotEmpty(lastCreateTasks) ? lastCreateTasks.get(0) : null;

        List<MonitorTask>  monitorTasksToDelete = Collections.emptyList();
        if (adapterProperties.getEms().isEnabled()) {
            List<AdapterMonitor> monitorsToDelete = getDataToDelete(newComparableModel.getAdapterMonitors(), oldComparableModel.getAdapterMonitors(), MONITOR_BI_PREDICATE);
            monitorTasksToDelete = createTasks(graph, monitorsToDelete, MonitorTask.MONITOR_TASK_DELETE);
            addEdge(graph, lastCreateTask, monitorTasksToDelete, () -> lastCreateTask != null);
        }

        for (String taskName : entireElement.getElements().keySet()) {
            DividedElement dividedElement = entireElement.getElements().get(taskName);
            log.info("DELETE Working with {} -> toCreate: {}, toRemain: {}, toDelete: {}", taskName, dividedElement.toCreate.size(), dividedElement.toRemain.size(), dividedElement.toDelete.size());

            List<AdapterProcess> toDelete = dividedElement.getToDelete();
            if (CollectionUtils.isNotEmpty(toDelete)) {
                boolean hasExistingProcesses = hasExistingProcesses(dividedElement);
                if (hasExistingProcesses) {
                    //ScaleIn
                    log.info("DELETE Working with {} -> hasExistingProcesses: {}, {} ScaleTasks will be created", taskName, hasExistingProcesses, toDelete.size());
                    toDelete.forEach(adapterProcess -> createTask(graph, adapterProcess, ScaleTask.SCALE_TASK_CREATE));
                } else {
                    AdapterProcess toDeleteFirst = toDelete.get(0);
                    List<AdapterProcess> toDeleteWithoutFirst = toDelete.stream()
                            .skip(1)
                            .collect(toList());
                    log.info("DELETE Working with {} -> hasExistingProcesses: {}, {} ScaleTasks, and 1 ProcessTask will be created", taskName, hasExistingProcesses, toDeleteWithoutFirst.size());

                    ProcessTask processTaskToDelete = createTask(graph, toDeleteFirst, ProcessTask.PROCESS_TASK_DELETE);
                    List<ScaleTask> scalesTasksToDelete = createTasks(graph, toDeleteWithoutFirst, ScaleTask.SCALE_TASK_DELETE);

                    addEdge(graph, scalesTasksToDelete, processTaskToDelete);
                }
            }
        }


        //Process (D) -> Nodes(D)
        List<AdapterRequirement> nodesToDelete = getDataToDelete(newComparableModel.getAdapterRequirements(), oldComparableModel.getAdapterRequirements(), NODE_BI_PREDICATE);
        List<NodeTask> nodeTasksToDelete = createTasks(graph, nodesToDelete, NodeTask.NODE_TASK_DELETE);
        addEdge(graph, monitorTasksToDelete, nodeTasksToDelete, MONITOR_TO_NODE_BI_PREDICATE);

        List<ProcessTask> processTasksToDelete = getTasksWithoutOutgoingEdges(graph, DELETE, ProcessTask.class);
        addEdge(graph, processTasksToDelete, nodeTasksToDelete, PROCESS_TO_NODE_BI_PREDICATE);
        addEdge(graph, lastCreateTask, processTasksToDelete, () -> lastCreateTask != null);

        List<ScaleTask> scaleTasksToDelete = getTasksWithoutOutgoingEdges(graph, DELETE, ScaleTask.class);
        addEdge(graph, scaleTasksToDelete, nodeTasksToDelete, SCALE_TO_NODE_BI_PREDICATE);
        addEdge(graph, lastCreateTask, scaleTasksToDelete, () -> lastCreateTask != null);

//        //Monitors (D) -> Process (D)
//        List<AdapterProcess> processesToDelete = getDataToDelete(newComparableModel.getAdapterProcesses(), oldComparableModel.getAdapterProcesses(), PROCESS_BI_PREDICATE);
//        List<ProcessTask> processTasksToDelete = createTasks(graph, processesToDelete, ProcessTask.PROCESS_TASK_DELETE);
//
//        if (CollectionUtils.isNotEmpty(monitorTasksToDelete)){
//            addEdge(graph, monitorTasksToDelete, processTasksToDelete, MONITOR_TO_PROCESS_BI_PREDICATE);
//        } else {
//            addEdge(graph, lastCreateTask, processTasksToDelete, () -> lastCreateTask != null);
//        }
//
//        //Process (D) -> Nodes(D)
//        List<AdapterRequirement> nodesToDelete = getDataToDelete(newComparableModel.getAdapterRequirements(), oldComparableModel.getAdapterRequirements(), NODE_BI_PREDICATE);
//        List<NodeTask> nodeTasksToDelete = createTasks(graph, nodesToDelete, NodeTask.NODE_TASK_DELETE);
//        addEdge(graph, processTasksToDelete, nodeTasksToDelete, PROCESS_TO_NODE_BI_PREDICATE);

        //Process (D) -> Nodes(D)
//        List<AdapterRequirement> nodesToDelete = getDataToDelete(newComparableModel.getAdapterRequirements(), oldComparableModel.getAdapterRequirements(), NODE_BI_PREDICATE);
//        List<NodeTask> nodeTasksToDelete = createTasks(graph, nodesToDelete, NodeTask.NODE_TASK_DELETE);
//        addEdge(graph, monitorTasksToDelete, nodeTasksToDelete, MONITOR_TO_NODE_BI_PREDICATE);

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

    private boolean hasExistingProcesses(DividedElement dividedElement) {
        return !dividedElement.getToRemain().isEmpty() || !dividedElement.getToCreate().isEmpty();
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

    private <T extends Data> List<T> getDataToCreate(Collection<T> p1, Collection<T> p2, BiPredicate<T, T> biPredicate) {
        return getData(p1, p2, biPredicate);
    }

    private <T extends Data> List<T> getDataToDelete(Collection<T> p1, Collection<T> p2, BiPredicate<T, T> biPredicate) {
        //we need to change arguments
        return getData(p2, p1, biPredicate);
    }

    private <T extends Data> List<T> getDataToRemain(Collection<T> p1, Collection<T> p2, BiPredicate<T, T> biPredicate) {
        return p1.stream()
                .filter(oldReq -> p2.stream().anyMatch(newReq -> biPredicate.test(newReq, oldReq)))
                .collect(toList());
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

    @Getter
    private class EntireElement {
        @Singular
        private Map<String, DividedElement> elements;

        public EntireElement(Collection<AdapterProcess> newElements, Collection<AdapterProcess> oldElements) {
            Map<String, DividedElement> result = new HashMap<>();

            List<String> taskNames = Stream.concat(newElements.stream(), oldElements.stream()).map(AdapterProcess::getTaskName).collect(toList());
            for (String taskName : taskNames) {
                List<AdapterProcess> newElementsByTask = getFiltered(newElements, taskName);
                List<AdapterProcess> oldElementsByTask = getFiltered(oldElements, taskName);

                DividedElement dividedElement = new DividedElement(newElementsByTask, oldElementsByTask, PROCESS_BI_PREDICATE);
                result.put(taskName, dividedElement);
            }
            this.elements = result;
        }

        private List<AdapterProcess> getFiltered(Collection<AdapterProcess> elements, String taskName){
            return elements.stream().filter(adapterProcess -> adapterProcess.getTaskName().equalsIgnoreCase(taskName)).collect(toList());
        }
    }

    @Getter
    private class DividedElement {
        @Singular
        private List<AdapterProcess> toCreate;
        @Singular
        private List<AdapterProcess> toRemain;
        @Singular
        private List<AdapterProcess> toDelete;

        public DividedElement(Collection<AdapterProcess> newElements, Collection<AdapterProcess> oldElements, BiPredicate<AdapterProcess, AdapterProcess> predicate) {
            this.toCreate = getDataToCreate(newElements, oldElements, predicate);
            this.toRemain = getDataToRemain(newElements, oldElements, predicate);
            this.toDelete = getDataToDelete(newElements, oldElements, predicate);
        }
    }

}