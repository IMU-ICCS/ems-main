/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.plangenerator.graph;

import eu.melodic.upperware.adapter.exception.AdapterException;
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

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static eu.melodic.upperware.adapter.plangenerator.graph.model.Type.CONFIG;
import static eu.melodic.upperware.adapter.plangenerator.graph.model.Type.RECONFIG;
import static eu.melodic.upperware.adapter.plangenerator.tasks.Type.CREATE;
import static eu.melodic.upperware.adapter.plangenerator.tasks.Type.DELETE;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class DefaultGraphGenerator extends AbstractDefaultGraphGenerator<ComparableModel> {

    private ToLogGraphLogger toLogGraphLogger;
    private AdapterProperties adapterProperties;

    @Override
    public SimpleDirectedGraph<Task, DefaultEdge> generateConfigGraph(ComparableModel model) {
        log.info("Building configuration graph from prepared model");

        MelodicGraph<Task, DefaultEdge> graph = new MelodicGraph<>(DefaultEdge.class, CONFIG);

        JobTask jobTask = genJobCreateTask(graph, model.getAdapterJob());

        ScheduleTask scheduleTask = genScheduleCreateTask(graph, jobTask, model.getAdapterSchedule());

        Collection<NodeTask> nodeTasks = genNodeCreateTasks(graph, model.getAdapterRequirements());

        Collection<ProcessTask> processTasks = genProcessCreateTasks(graph, scheduleTask, jobTask, nodeTasks, model.getAdapterProcesses());

        if (adapterProperties.getEms().isEnabled()) {
          Collection<MonitorTask> monitorTasks = getMonitorsTasks(graph, processTasks, model.getAdapterMonitors());
        }

        toLogGraphLogger.logGraph(graph);

        log.info("Built config graph: {}", graph);

        return graph;
    }

    @Override
    public SimpleDirectedGraph<Task, DefaultEdge> generateReconfigGraph(ComparableModel oldModel, ComparableModel newModel) {
        log.info("Building reconfiguration graph from prepared models");

        MelodicGraph<Task, DefaultEdge> graph = new MelodicGraph<>(DefaultEdge.class, RECONFIG);

        Collection<MonitorTask> monitorsTasks = Collections.emptyList();
        if (adapterProperties.getEms().isEnabled()) {
//      0) Monitors
            Collection<AdapterMonitor> monitorsToRemove = getMonitorsToRemove(oldModel.getAdapterMonitors(), newModel.getAdapterMonitors());
            Collection<AdapterMonitor> monitorsToCreate = getMonitorsToCreate(newModel.getAdapterMonitors(), oldModel.getAdapterMonitors());

            monitorsTasks = getMonitorsReconfigTasks(graph, monitorsToCreate, monitorsToRemove);
        }

//      1) Process
        Collection<AdapterProcess> processesToRemove = getProcessesToRemove(oldModel.getAdapterProcesses(), newModel.getAdapterProcesses());
        Collection<AdapterProcess> processesToCreate = getProcessesToCreate(newModel.getAdapterProcesses(), oldModel.getAdapterProcesses());

        Collection<ProcessTask> processTasks = genProcessReconfigTasks(graph, processesToCreate, processesToRemove);

//      2) Node
        Collection<AdapterRequirement> nodesToRemove = getAdapterRequirementsToRemove(oldModel.getAdapterRequirements(), newModel.getAdapterRequirements());
        Collection<AdapterRequirement> nodesToCreate = getAdapterRequirementsToCreate(newModel.getAdapterRequirements(), oldModel.getAdapterRequirements());

        Collection<NodeTask> nodeTasks = genNodeReconfigTasks(graph, nodesToCreate, nodesToRemove);

//      3) Wait task
        setDependenciesAndWaitTask(graph, monitorsTasks, processTasks, nodeTasks);

        toLogGraphLogger.logGraph(graph);

        log.info("Built reconfig graph: {}", graph);
        return graph;
    }

    private void setDependenciesAndWaitTask(MelodicGraph<Task, DefaultEdge> graph, Collection<MonitorTask> monitorsTasks,
                                            Collection<ProcessTask> processTasks, Collection<NodeTask> nodeTasks) {

        WaitTask waitCreateTask = new WaitTask(CREATE, new WaitData());
        addVertex(graph, waitCreateTask);

        List<MonitorTask> createMonitors = getFiltered(monitorsTasks, CREATE);
        List<MonitorTask> deleteMonitors = getFiltered(monitorsTasks, DELETE);

        List<ProcessTask> createProcess = getFiltered(processTasks, CREATE);
        List<ProcessTask> deleteProcess = getFiltered(processTasks, DELETE);

        List<NodeTask> createNodes = getFiltered(nodeTasks, CREATE);
        List<NodeTask> deleteNodes = getFiltered(nodeTasks, DELETE);

        setDependencies1(graph, createMonitors, createProcess, waitCreateTask, CREATE);
        setDependencies(graph, createProcess, createNodes, waitCreateTask, CREATE);

        boolean isAnythingToDelete = CollectionUtils.isNotEmpty(deleteMonitors) || CollectionUtils.isNotEmpty(deleteProcess) || CollectionUtils.isNotEmpty(deleteNodes);
        if (isAnythingToDelete) {
            WaitTask waitDeleteTask = new WaitTask(DELETE, new WaitData());
            addVertex(graph, waitDeleteTask);

            setDependencies1(graph, deleteMonitors, deleteProcess, waitDeleteTask, DELETE);
            setDependencies(graph, deleteProcess, deleteNodes, waitDeleteTask, DELETE);
        }
    }

    private Collection<MonitorTask> getMonitorsTasks(MelodicGraph<Task, DefaultEdge> graph, Collection<ProcessTask> processTasks, Collection<AdapterMonitor> adapterMonitors) {

        List<MonitorTask> monitorTasks = adapterMonitors.stream()
                .map(monitor -> new MonitorTask(CREATE, monitor))
                .collect(toList());

        monitorTasks.forEach(monitorTask -> {
            addVertex(graph, monitorTask);

            findAndSetProcessDependencies(graph, monitorTask, monitorTask.getData().getTaskName(), processTasks, CREATE);
        });

        return monitorTasks;
    }

    private void setDependencies1(MelodicGraph<Task, DefaultEdge> graph, List<MonitorTask> monitors, List<ProcessTask> processes, WaitTask waitTask, Type type) {
        monitors.forEach(monitorTask -> {
            ProcessTask nodeTask = processes
                    .stream()
                    .filter(processTask -> processTask.getData().getNodeName().equals(monitorTask.getData().getNodeName()))
                    .findFirst()
                    .orElseThrow(() -> new AdapterException(format("Could not find %s ProcessTask for nodeName %s", type.name(), monitorTask.getData().getNodeName())));

            setDependencies(graph, type, nodeTask, monitorTask);
            setDependencies(graph, type, monitorTask, waitTask);
        });
    }

    private void setDependencies(MelodicGraph<Task, DefaultEdge> graph, List<ProcessTask> processes, List<NodeTask> nodes, WaitTask waitTask, Type type) {
        processes.forEach(processTask -> {
            NodeTask nodeTask = nodes
                    .stream()
                    .filter(nt -> nt.getData().getNodeName().equals(processTask.getData().getNodeName()))
                    .findFirst()
                    .orElseThrow(() -> new AdapterException(format("Could not find %s Node Task for nodeName %s", type.name(), processTask.getData().getNodeName())));

            setDependencies(graph, type, nodeTask, processTask);
            setDependencies(graph, type, processTask, waitTask);
        });
    }

    private <T extends Task> List<T> getFiltered(Collection<T> collection, Type type){
        return collection.stream().filter(t -> type.equals(t.getType())).collect(toList());
    }

    private Collection<AdapterRequirement> getAdapterRequirementsToRemove(Collection<AdapterRequirement> oldAdapterRequirements, Collection<AdapterRequirement> newAdapterRequirements) {
        return getAdapterRequirements(oldAdapterRequirements, newAdapterRequirements);
    }

    private Collection<AdapterRequirement> getAdapterRequirementsToCreate(Collection<AdapterRequirement> newAdapterRequirements, Collection<AdapterRequirement> oldAdapterRequirements) {
        return getAdapterRequirements(newAdapterRequirements, oldAdapterRequirements);
    }

    private Collection<AdapterRequirement> getAdapterRequirements(Collection<AdapterRequirement> p1, Collection<AdapterRequirement> p2) {
        return p1.stream()
                .filter(newReq -> p2.stream().noneMatch(oldReq -> oldReq.getNodeName().equals(newReq.getNodeName())))
                .collect(Collectors.toList());
    }

    private Collection<AdapterProcess> getProcessesToRemove(Collection<AdapterProcess> oldProcesses, Collection<AdapterProcess> newProcesses) {
        return getProcesses(oldProcesses, newProcesses);
    }

    private Collection<AdapterProcess> getProcessesToCreate(Collection<AdapterProcess> newProcesses, Collection<AdapterProcess> oldProcesses) {
        return getProcesses(newProcesses, oldProcesses);
    }

    private Collection<AdapterProcess> getProcesses(Collection<AdapterProcess> p1, Collection<AdapterProcess> p2) {
        return p1.stream()
                .filter(oldReq -> p2.stream().noneMatch(newReq -> newReq.getNodeName().equals(oldReq.getNodeName()) &&
                        newReq.getScheduleName().equals((oldReq.getScheduleName())) &&
                        newReq.getJobName().equals(oldReq.getJobName()) &&
                        newReq.getTaskName().equals(oldReq.getTaskName())))
                .collect(Collectors.toList());
    }

    private Collection<AdapterMonitor> getMonitorsToRemove(Collection<AdapterMonitor> oldMonitors, Collection<AdapterMonitor> newMonitors) {
        return getMonitors(oldMonitors, newMonitors);
    }

    private Collection<AdapterMonitor> getMonitorsToCreate(Collection<AdapterMonitor> newMonitors, Collection<AdapterMonitor> oldMonitors) {
        return getMonitors(newMonitors, oldMonitors);
    }

    private Collection<AdapterMonitor> getMonitors(Collection<AdapterMonitor> m1, Collection<AdapterMonitor> m2) {
        return m1.stream()
                .filter(m1e -> m2.stream().noneMatch(m2element -> m2element.getMetricName().equals(m1e.getMetricName())))
                .collect(toList());
    }

    private Collection<NodeTask> genNodeReconfigTasks(MelodicGraph<Task, DefaultEdge> graph,
                                                      Collection<AdapterRequirement> toCreate, Collection<AdapterRequirement> toRemove) {

        Collection<NodeTask> nodeCreateTasks = genNodeCreateTasks(graph, toCreate);
        Collection<NodeTask> nodeDeleteTasks = genNodeDeleteTasks(graph, toRemove);

        return Stream.of(nodeDeleteTasks, nodeCreateTasks)
                .flatMap(Collection::stream)
                .collect(toList());
    }

    private Collection<ProcessTask> genProcessDeleteTasks(MelodicGraph<Task, DefaultEdge> graph, Collection<AdapterProcess> adapterProcesses) {
        return genProcessTasks(graph, adapterProcesses, DELETE);
    }

    private Collection<ProcessTask> genProcessCreateTasks(MelodicGraph<Task, DefaultEdge> graph, Collection<AdapterProcess> adapterProcesses) {
        return genProcessCreateTasks(graph, null, null, Collections.emptyList(), adapterProcesses);
    }

    private Collection<ProcessTask> genProcessCreateTasks(MelodicGraph<Task, DefaultEdge> graph, ScheduleTask scheduleTask, JobTask jobTask, Collection<NodeTask> nodeTasks, Collection<AdapterProcess> adapterProcesses) {

        Collection<ProcessTask> processTasks = genProcessTasks(graph, adapterProcesses, CREATE);

        processTasks.forEach(processTask -> {

            if (CollectionUtils.isNotEmpty(nodeTasks)) {
                findAndSetNodeDependencies(graph, processTask, processTask.getData().getNodeName(), nodeTasks, CREATE);
            }

            if (scheduleTask != null) {
                setDependencies(graph, CREATE, scheduleTask, processTask);
            }

            if (jobTask != null) {
                setDependencies(graph, CREATE, jobTask, processTask);
            }
        });

        return processTasks;
    }

    private Collection<ProcessTask> genProcessReconfigTasks(MelodicGraph<Task, DefaultEdge> graph, Collection<AdapterProcess> processesToCreate, Collection<AdapterProcess> processesToRemove) {

        Collection<ProcessTask> processCreateTasks = genProcessCreateTasks(graph, processesToCreate);
        Collection<ProcessTask> processDeleteTasks = genProcessDeleteTasks(graph, processesToRemove);

        return Stream.of(processDeleteTasks, processCreateTasks)
                .flatMap(Collection::stream)
                .collect(toList());
    }

    private Collection<ProcessTask> genProcessTasks(MelodicGraph<Task, DefaultEdge> graph, Collection<AdapterProcess> adapterProcesses, Type type){

        return adapterProcesses.stream()
                .map(adapterRequirement -> new ProcessTask(CREATE, adapterRequirement))
                .peek(processTask ->  addVertex(graph, processTask))
                .collect(toList());
    }

    private Collection<NodeTask> genNodeCreateTasks(MelodicGraph<Task, DefaultEdge> graph, Collection<AdapterRequirement> adapterNodeRequirements) {
        return getNodeTasks(graph, adapterNodeRequirements, CREATE);
    }

    private Collection<NodeTask> genNodeDeleteTasks(MelodicGraph<Task, DefaultEdge> graph, Collection<AdapterRequirement> adapterNodeRequirements) {
        return getNodeTasks(graph, adapterNodeRequirements, DELETE);
    }

    private Collection<NodeTask> getNodeTasks(MelodicGraph<Task, DefaultEdge> graph, Collection<AdapterRequirement> adapterNodeRequirements, Type type) {
        List<NodeTask> nodeTasks = adapterNodeRequirements.stream()
                .map(adapterRequirement -> new NodeTask(type, adapterRequirement))
                .collect(toList());

        nodeTasks.forEach(nodeTask -> addVertex(graph, nodeTask));

        return nodeTasks;
    }

    private Collection<MonitorTask> getMonitorsReconfigTasks(MelodicGraph<Task, DefaultEdge> graph, Collection<AdapterMonitor> monitorsToCreate, Collection<AdapterMonitor> monitorsToRemove) {

        Collection<MonitorTask> MonitorTask = genMonitorsCreateTasks(graph, monitorsToCreate);
        Collection<MonitorTask> monitorsDeleteTasks = genMonitorsDeleteTasks(graph, monitorsToRemove);

        return Stream.of(MonitorTask, monitorsDeleteTasks)
                .flatMap(Collection::stream)
                .collect(toList());
    }

    private Collection<MonitorTask> genMonitorsCreateTasks(MelodicGraph<Task, DefaultEdge> graph, Collection<AdapterMonitor> adapterMonitors) {
        return genMonitorTasks(graph, adapterMonitors, CREATE);
    }

    private Collection<MonitorTask> genMonitorsDeleteTasks(MelodicGraph<Task, DefaultEdge> graph, Collection<AdapterMonitor> adapterMonitors) {
        return genMonitorTasks(graph, adapterMonitors, DELETE);
    }

    private Collection<MonitorTask> genMonitorTasks(MelodicGraph<Task, DefaultEdge> graph, Collection<AdapterMonitor> adapterMonitors, Type type) {
        List<MonitorTask> monitorTasks = adapterMonitors.stream()
                .map(adapterMonitor -> new MonitorTask(type, adapterMonitor))
                .collect(toList());

        monitorTasks.forEach(monitorTask -> addVertex(graph, monitorTask));

        return monitorTasks;
    }

    private ScheduleTask genScheduleCreateTask(MelodicGraph<Task, DefaultEdge> graph, JobTask jobTask, AdapterSchedule adapterSchedule) {
        ScheduleTask scheduleTask = new ScheduleTask(CREATE, adapterSchedule);

        addVertex(graph, scheduleTask);

        if (jobTask != null) {
            setDependencies(graph, CREATE, jobTask, scheduleTask);
        }

        return scheduleTask;
    }

    private JobTask genJobCreateTask(MelodicGraph<Task, DefaultEdge> graph, AdapterJob adapterJob) {
        JobTask appTask = new JobTask(CREATE, adapterJob);

        addVertex(graph, appTask);

        return appTask;
    }

}