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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

import static eu.melodic.upperware.adapter.plangenerator.graph.model.Type.CONFIG;
import static eu.melodic.upperware.adapter.plangenerator.graph.model.Type.RECONFIG;
import static eu.melodic.upperware.adapter.plangenerator.tasks.Type.CREATE;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class DefaultGraphGenerator extends AbstractDefaultGraphGenerator<ComparableModel> {

  private ToLogGraphLogger toLogGraphLogger;

  @Override
  public SimpleDirectedGraph<Task, DefaultEdge> generateConfigGraph(ComparableModel model) {
    log.info("Building configuration graph from prepared model");

    MelodicGraph<Task, DefaultEdge> graph = new MelodicGraph<>(DefaultEdge.class, CONFIG);

    JobTask jobTask = genJobCreateTask(graph, model.getAdapterJob());

    ScheduleTask scheduleTask = genScheduleCreateTask(graph, jobTask, model.getAdapterSchedule());

    Collection<NodeTask> nodeTasks = genNodeCreateTasks(graph, model.getAdapterRequirements());

    Collection<ProcessTask> processTasks = genProcessTasks(graph, scheduleTask, jobTask, nodeTasks, model.getAdapterProcesses());

    Collection<MonitorTask> monitorTasks = getMonitorsTasks(graph, processTasks, model.getAdapterMonitors());

    log.info("Built graph: {}", graph);

    return graph;
  }

  private Collection<MonitorTask> getMonitorsTasks(MelodicGraph<Task, DefaultEdge> graph, Collection<ProcessTask> processTasks, Collection<AdapterMonitor> adapterMonitors) {

    List<MonitorTask> monitorTasks = adapterMonitors.stream()
            .map(monitor -> new MonitorTask(CREATE, monitor))
            .collect(toList());

    monitorTasks.forEach(monitorTask -> {
      addVertex(graph, monitorTask);

//      findAndSetDependencies(graph, monitorTask, monitorTask.getData().getName(), processTasks, CREATE,
//              task -> ((ProcessTask) task).getData().getTaskName().equals(monitorTask.getData().getTaskName())
//      );

      findAndSetProcessDependencies(graph, monitorTask, monitorTask.getData().getTaskName(), processTasks, CREATE);
    });

    return monitorTasks;
  }

  private Collection<ProcessTask> genProcessTasks(MelodicGraph<Task, DefaultEdge> graph, ScheduleTask scheduleTask, JobTask jobTask, Collection<NodeTask> nodeTasks, Collection<AdapterProcess> adapterProcesses) {

    List<ProcessTask> processTasks = adapterProcesses.stream()
            .map(adapterRequirement -> new ProcessTask(CREATE, adapterRequirement))
            .collect(toList());

    processTasks.forEach(processTask -> {
      addVertex(graph, processTask);

      findAndSetNodeDependencies(graph, processTask, processTask.getData().getNodeName(), nodeTasks, CREATE);

      setDependencies(graph, CREATE, scheduleTask, processTask);
      setDependencies(graph, CREATE, jobTask, processTask);
    });

    return processTasks;
  }

  private Collection<NodeTask> genNodeCreateTasks(MelodicGraph<Task, DefaultEdge> graph, Collection<AdapterRequirement> adapterNodeRequirements) {

    List<NodeTask> nodeTasks = adapterNodeRequirements.stream()
            .map(adapterRequirement -> new NodeTask(CREATE, adapterRequirement))
            .collect(toList());

    nodeTasks.forEach(nodeTask -> {
      addVertex(graph, nodeTask);
    });

    return nodeTasks;
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

  @Override
  public SimpleDirectedGraph<Task, DefaultEdge> generateReconfigGraph(ComparableModel oldModel, ComparableModel newModel) {
    log.info("Building reconfiguration graph from prepared models");

    MelodicGraph<Task, DefaultEdge> graph = new MelodicGraph<>(DefaultEdge.class, RECONFIG);

    toLogGraphLogger.logGraph(graph);

    log.info("Built graph: {}", graph);

    return graph;
  }

}