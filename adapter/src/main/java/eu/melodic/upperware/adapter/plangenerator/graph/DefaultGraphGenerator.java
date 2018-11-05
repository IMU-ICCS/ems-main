/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.plangenerator.graph;

import com.google.common.collect.Lists;
import eu.melodic.upperware.adapter.graphlogger.ToLogGraphLogger;
import eu.melodic.upperware.adapter.plangenerator.graph.model.MelodicGraph;
import eu.melodic.upperware.adapter.plangenerator.model.*;
import eu.melodic.upperware.adapter.plangenerator.tasks.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.jgrapht.alg.CycleDetector;
import org.jgrapht.alg.DirectedNeighborIndex;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;

import static eu.melodic.upperware.adapter.plangenerator.graph.model.Type.CONFIG;
import static eu.melodic.upperware.adapter.plangenerator.graph.model.Type.RECONFIG;
import static eu.melodic.upperware.adapter.plangenerator.tasks.Type.*;
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

//    Collection<CloudApiTask> cloudApiTasks = genCloudApiConfigTasks(graph, model.getCloudApis());
//    Collection<CloudTask> cloudTasks = genCloudConfigTasks(graph, cloudApiTasks, model.getClouds());
//    Collection<CloudPropertyTask> cloudPropertyTasks = genCloudPropertyConfigTasks(graph, cloudTasks,model.getCloudProperties());
//    Collection<CloudCredentialTask> cloudCredentialTasks = genCloudCredentialConfigTasks(graph, cloudTasks, model.getCloudCredentials());
//
//    ApplicationTask appTask = genAppConfigTask(graph, model.getApplication());
//    ApplicationInstanceTask appInstTask = genAppInstConfigTask(graph, appTask, model.getApplicationInstance());
//
//    Collection<LifecycleComponentTask> lcTasks = genLcConfigTasks(graph, model.getLifecycleComponents());
//
//    Collection<VirtualMachineTask> vmTasks = genVmConfigTasks(graph, cloudTasks, model.getVirtualMachines());
//
//    Collection<ApplicationComponentTask> acTasks = genAcConfigTasks(graph, appTask, lcTasks, vmTasks, model.getApplicationComponents());
//
//    Collection<PortProvidedTask> portProvTasks = genPortProvConfigTasks(graph, acTasks, model.getPortsProvided());
//    Collection<PortRequiredTask> portReqTasks = genPortReqConfigTasks(graph, acTasks, model.getPortsRequired());
//    Collection<CommunicationTask> commTasks = genCommConfigTasks(graph, portProvTasks, portReqTasks, model.getCommunications());
//
//    Collection<VirtualMachineInstanceTask> vmInstTasks = genVmInstConfigTasks(graph, vmTasks, commTasks, model.getVirtualMachineInstances());
//
//    Collection<ApplicationComponentInstanceTask> acInstTasks = genAcInstConfigTasks(graph, appInstTask, acTasks, vmInstTasks, commTasks, model.getApplicationComponentInstances());
//
//    genVmInstMonitorConfigTasks(graph, vmInstTasks, model.getVirtualMachineInstanceMonitors());
//    genAcInstMonitorConfigTasks(graph, acInstTasks, model.getApplicationComponentInstanceMonitors());
//
//    setSequentiallyVirtualMachineTasks(graph, vmTasks);

    log.info("Built graph: {}", graph);

    return graph;
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





    Collection<CloudTask> cloudTasks = Collections.emptyList();
    ApplicationTask appTask = genReconfigAppTask(graph, oldModel.getApplication(), newModel.getApplication());

    Collection<VirtualMachineTask> vmTasks = genVmReconfigTasks(
            graph, cloudTasks, oldModel.getVirtualMachines(), newModel.getVirtualMachines());

    //TAK
    Collection<VirtualMachineInstanceTask> vmInstTasks = genVmInstReconfigTasks(
            graph, vmTasks, oldModel.getVirtualMachineInstances(), newModel.getVirtualMachineInstances());


    Collection<ApplicationComponentTask> acTasks = Collections.emptyList();

    Collection<ApplicationComponentInstanceTask> acInstTasks = genAcInstReconfigTasks(
            graph, acTasks, vmInstTasks, oldModel.getApplicationComponentInstances(), newModel.getApplicationComponentInstances());

    Collection<VirtualMachineInstanceMonitorTask> vmInstMonitorTasks = genVmInstMonitorReconfigTasks(
            graph, vmInstTasks, oldModel.getVirtualMachineInstanceMonitors(), newModel.getVirtualMachineInstanceMonitors());

    Collection<ApplicationComponentInstanceMonitorTask> acInstMonitorTasks = genAcInstMonitorReconfigTasks(
            graph, acInstTasks, oldModel.getApplicationComponentInstanceMonitors(),
            newModel.getApplicationComponentInstanceMonitors());

    toLogGraphLogger.logGraph(graph);

    setSequentiallyVirtualMachineTasks(graph, vmTasks);
    setMonitors(graph, vmInstMonitorTasks, acInstMonitorTasks);

    log.info("Built graph: {}", graph);

    return graph;
  }

  private Collection<CloudApiTask> genCloudApiConfigTasks(MelodicGraph<Task, DefaultEdge> graph,
          Collection<CloudApi> cloudApis) {
    return genCloudApiTasks(graph, CREATE, cloudApis);
  }

  private Collection<CloudTask> genCloudConfigTasks(MelodicGraph<Task, DefaultEdge> graph,
          Collection<CloudApiTask> cloudApiTasks, Collection<Cloud> clouds) {
    return genCloudTasks(graph, CREATE, cloudApiTasks, clouds);
  }

  private Collection<CloudPropertyTask> genCloudPropertyConfigTasks(MelodicGraph<Task, DefaultEdge> graph,
          Collection<CloudTask> cloudTasks, Collection<CloudProperty> cloudProperties) {
    return genCloudPropertyTasks(graph, CREATE, cloudTasks, cloudProperties);
  }

  private Collection<CloudCredentialTask> genCloudCredentialConfigTasks(MelodicGraph<Task, DefaultEdge> graph,
          Collection<CloudTask> cloudTasks, Collection<CloudCredential> cloudCredentials) {
    return genCloudCredentialTasks(graph, CREATE, cloudTasks, cloudCredentials);
  }

  private ApplicationTask genAppConfigTask(MelodicGraph<Task, DefaultEdge> graph, Application app) {
    return genAppTask(graph, CREATE, app);
  }

  private ApplicationTask genReconfigAppTask(MelodicGraph<Task, DefaultEdge> graph, Application oldApp, Application newApp) {
    if (!newApp.equals(oldApp)) {
      newApp.setOldName(oldApp.getName());
      return genAppTask(graph, UPDATE, newApp);
    }
    return null;
  }

  private ApplicationInstanceTask genAppInstConfigTask(MelodicGraph<Task, DefaultEdge> graph, ApplicationTask appTask,
          ApplicationInstance appInst) {
    return genAppInstTask(graph, CREATE, appTask, appInst);
  }

  private Collection<LifecycleComponentTask> genLcConfigTasks(MelodicGraph<Task, DefaultEdge> graph,
          Collection<LifecycleComponent> lcs) {
    return genLcTasks(graph, CREATE, lcs);
  }

  private Collection<LifecycleComponentTask> genLcReconfigTasks(MelodicGraph<Task, DefaultEdge> graph,
          Collection<LifecycleComponent> oldLcs, Collection<LifecycleComponent> newLcs) {
    Collection<LifecycleComponentTask> lcTasks = Lists.newArrayList();

    lcTasks.addAll(genLcTasks(graph, CREATE,
      newLcs.stream().filter(newLc -> !oldLcs.contains(newLc)).collect(toList())));
    lcTasks.addAll(genLcTasks(graph, DELETE,
      oldLcs.stream().filter(oldLc -> !newLcs.contains(oldLc)).collect(toList())));

    return lcTasks;
  }

  private Collection<VirtualMachineTask> genVmConfigTasks(MelodicGraph<Task, DefaultEdge> graph,
          Collection<CloudTask> cloudTasks, Collection<VirtualMachine> vms) {
    return genVmTasks(graph, CREATE, cloudTasks, vms);
  }

  private Collection<VirtualMachineTask> genVmReconfigTasks(MelodicGraph<Task, DefaultEdge> graph,
          Collection<CloudTask> cloudTasks, Collection<VirtualMachine> oldVms, Collection<VirtualMachine> newVms) {
    Collection<VirtualMachineTask> vmTasks = Lists.newArrayList();

    vmTasks.addAll(genVmTasks(graph, CREATE, cloudTasks, newVms.stream().filter(newVm -> !oldVms.contains(newVm)).collect(toList())));
    vmTasks.addAll(genVmTasks(graph, DELETE, cloudTasks, oldVms.stream().filter(oldVm -> !newVms.contains(oldVm)).collect(toList())));

    return vmTasks;
  }

  private Collection<VirtualMachineInstanceTask> genVmInstConfigTasks(MelodicGraph<Task, DefaultEdge> graph,
          Collection<VirtualMachineTask> vmTasks, Collection<CommunicationTask> commTasks, Collection<VirtualMachineInstance> vmInsts) {
    return genVmInstTasks(graph, CREATE, vmTasks, commTasks, vmInsts);
  }

  private Collection<VirtualMachineInstanceTask> genVmInstReconfigTasks(MelodicGraph<Task, DefaultEdge> graph,
          Collection<VirtualMachineTask> vmTasks, Collection<VirtualMachineInstance> oldVmInsts,
          Collection<VirtualMachineInstance> newVmInsts) {
    Collection<VirtualMachineInstanceTask> vmInstTasks = Lists.newArrayList();

    vmInstTasks.addAll(genVmInstTasks(graph, CREATE, vmTasks, Collections.emptyList(),
      newVmInsts.stream().filter(newVmInst -> !oldVmInsts.contains(newVmInst)).collect(toList())));
    vmInstTasks.addAll(genVmInstTasks(graph, DELETE, vmTasks, Collections.emptyList(),
      oldVmInsts.stream().filter(oldVmInst -> !newVmInsts.contains(oldVmInst)).collect(toList())));

    return vmInstTasks;
  }

  private Collection<ApplicationComponentTask> genAcConfigTasks(MelodicGraph<Task, DefaultEdge> graph, ApplicationTask appTask,
          Collection<LifecycleComponentTask> lcTasks, Collection<VirtualMachineTask> vmTasks,
          Collection<ApplicationComponent> acs) {
    return genAcTasks(graph, CREATE, appTask, lcTasks, vmTasks, acs);
  }

  private Collection<ApplicationComponentInstanceTask> genAcInstConfigTasks(MelodicGraph<Task, DefaultEdge> graph,
      ApplicationInstanceTask appInstTask, Collection<ApplicationComponentTask> acTasks,
      Collection<VirtualMachineInstanceTask> vmInstTasks, Collection<CommunicationTask> commTasks, Collection<ApplicationComponentInstance> acInsts) {
    return genAcInstTasks(graph, CREATE, appInstTask, acTasks, vmInstTasks, commTasks, acInsts);
  }

  private Collection<ApplicationComponentInstanceTask> genAcInstReconfigTasks(MelodicGraph<Task, DefaultEdge> graph,
          Collection<ApplicationComponentTask> acTasks, Collection<VirtualMachineInstanceTask> vmInstTasks,
          Collection<ApplicationComponentInstance> oldAcInsts, Collection<ApplicationComponentInstance> newAcInsts) {
    Collection<ApplicationComponentInstanceTask> acInstTasks = Lists.newArrayList();

    acInstTasks.addAll(genAcInstTasks(graph, CREATE, null, acTasks, vmInstTasks, Lists.newArrayList(),
      newAcInsts.stream().filter(newAcInst -> !oldAcInsts.contains(newAcInst)).collect(toList())));
    acInstTasks.addAll(genAcInstTasks(graph, DELETE, null, acTasks, vmInstTasks, Lists.newArrayList(),
      oldAcInsts.stream().filter(oldAcInst -> !newAcInsts.contains(oldAcInst)).collect(toList())));

    return acInstTasks;
  }

  private Collection<PortProvidedTask> genPortProvConfigTasks(MelodicGraph<Task, DefaultEdge> graph,
          Collection<ApplicationComponentTask> acTasks, Collection<PortProvided> portsProv) {
    return genPortProvTasks(graph, CREATE, acTasks, portsProv);
  }

  private Collection<PortRequiredTask> genPortReqConfigTasks(MelodicGraph<Task, DefaultEdge> graph,
          Collection<ApplicationComponentTask> acTasks, Collection<PortRequired> portsReq) {
    return genPortReqTasks(graph, CREATE, acTasks, portsReq);
  }

  private Collection<CommunicationTask> genCommConfigTasks(MelodicGraph<Task, DefaultEdge> graph,
          Collection<PortProvidedTask> portProvTasks, Collection<PortRequiredTask> portReqTasks,
          Collection<Communication> comms) {
    return genCommTasks(graph, CREATE, portProvTasks, portReqTasks, comms);
  }

  private Collection<VirtualMachineInstanceMonitorTask> genVmInstMonitorConfigTasks(MelodicGraph<Task, DefaultEdge> graph,
          Collection<VirtualMachineInstanceTask> vmInstTasks, Collection<VirtualMachineInstanceMonitor> vmInstMonitors) {
    return genVmInstMonitorTasks(graph, CREATE, vmInstTasks, vmInstMonitors);
  }

  private Collection<VirtualMachineInstanceMonitorTask> genVmInstMonitorReconfigTasks(MelodicGraph<Task, DefaultEdge> graph,
          Collection<VirtualMachineInstanceTask> vmInstTasks, Collection<VirtualMachineInstanceMonitor> oldVmInstMonitors,
          Collection<VirtualMachineInstanceMonitor> newVmInstMonitors){
    Collection<VirtualMachineInstanceMonitorTask> vmInstMonitorTasks = Lists.newArrayList();

    if (graph.vertexSet().stream().anyMatch(v -> !DELETE.equals(v.getType()))){

      Predicate<VirtualMachineInstanceMonitor> monitorIsConnectedWithCreateNewInstanceTask =
        vmInstMonitor -> vmInstTasks.stream()
          .anyMatch(acInstTask -> acInstTask.getData().getName().equals(vmInstMonitor.getVmInstName()));

      vmInstMonitorTasks.addAll(genVmInstMonitorTasks(graph, CREATE, Lists.newArrayList(), oldVmInstMonitors));
      vmInstMonitorTasks.addAll(genVmInstMonitorTasks(graph, CREATE, Lists.newArrayList(),
        newVmInstMonitors.stream().filter(monitorIsConnectedWithCreateNewInstanceTask).collect(toList()))
      );
    }
    if (graph.vertexSet().stream().anyMatch(v -> DELETE.equals(v.getType()))){

      vmInstMonitorTasks.addAll(genVmInstMonitorTasks(graph, DELETE, Lists.newArrayList(), newVmInstMonitors));
    }

    return vmInstMonitorTasks;
  }

  private Collection<ApplicationComponentInstanceMonitorTask> genAcInstMonitorConfigTasks(MelodicGraph<Task, DefaultEdge> graph,
          Collection<ApplicationComponentInstanceTask> acInstTasks, Collection<ApplicationComponentInstanceMonitor> acInstMonitors) {
    return genAcInstMonitorTasks(graph, CREATE, acInstTasks, acInstMonitors);
  }

  private Collection<ApplicationComponentInstanceMonitorTask> genAcInstMonitorReconfigTasks(MelodicGraph<Task, DefaultEdge> graph,
          Collection<ApplicationComponentInstanceTask> acInstTasks, Collection<ApplicationComponentInstanceMonitor> oldAcInstMonitors,
          Collection<ApplicationComponentInstanceMonitor> newAcInstMonitors) {
          Collection<ApplicationComponentInstanceMonitorTask> acInstMonitorTasks = Lists.newArrayList();

    if (graph.vertexSet().stream().anyMatch(v -> !DELETE.equals(v.getType()))) {

      Predicate<ApplicationComponentInstanceMonitor> monitorIsConnectedWithCreateNewInstanceTask =
        acInstMonitor -> acInstTasks.stream()
          .anyMatch(acInstTask -> acInstTask.getData().getName().equals(acInstMonitor.getAcInstName()));

      acInstMonitorTasks.addAll(genAcInstMonitorTasks(graph, CREATE, Lists.newArrayList(), oldAcInstMonitors));
      acInstMonitorTasks.addAll(genAcInstMonitorTasks(graph, CREATE, Lists.newArrayList(),
        newAcInstMonitors.stream().filter(monitorIsConnectedWithCreateNewInstanceTask).collect(toList())));
    }

    if (graph.vertexSet().stream().anyMatch(v -> DELETE.equals(v.getType()))) {
      acInstMonitorTasks.addAll(genAcInstMonitorTasks(graph, DELETE, Lists.newArrayList(), newAcInstMonitors));
    }

    return acInstMonitorTasks;
  }

  private void setMonitors(MelodicGraph<Task, DefaultEdge> graph,
          Collection<VirtualMachineInstanceMonitorTask> vmInstMonitorTasks,
          Collection<ApplicationComponentInstanceMonitorTask> acInstMonitorTasks) {

    toLogGraphLogger.logCount(graph);

    DirectedNeighborIndex<Task, DefaultEdge> neighbors = new DirectedNeighborIndex(graph);
    TopologicalOrderIterator<Task, DefaultEdge> it = new TopologicalOrderIterator(graph);

    while (it.hasNext()) {
      Task task = it.next();
      if (task instanceof VirtualMachineInstanceMonitorTask
        || task instanceof ApplicationComponentInstanceMonitorTask) {
        continue;
      }

      setMonitorsAfterTask(graph, vmInstMonitorTasks, acInstMonitorTasks, task, neighbors.successorsOf(task));
      setDeleteTaskAfterMonitors(graph, vmInstMonitorTasks, acInstMonitorTasks, task, neighbors.predecessorsOf(task));

      log.info("Current task {}", task.toString());
      toLogGraphLogger.logCount(graph);
      toLogGraphLogger.logCycles(graph);

      if (new CycleDetector<>(graph).detectCycles()){
        throw new IllegalArgumentException("Graph contains cycles!");
      }
    }
  }

  private void setMonitorsAfterTask(MelodicGraph<Task, DefaultEdge> graph, Collection<VirtualMachineInstanceMonitorTask> vmInstMonitorTasks,
          Collection<ApplicationComponentInstanceMonitorTask> acInstMonitorTasks, Task task, Set<Task> successors){

    if (CollectionUtils.isEmpty(successors)) {
      Type monitorType = DELETE.equals(task.getType()) ? DELETE : CREATE;

      setMonitorsAfterTask(vmInstMonitorTasks, task, monitorType, graph);
      setMonitorsAfterTask(acInstMonitorTasks, task, monitorType, graph);
    }

  }

  private <T extends ConfigurationTask> void setMonitorsAfterTask(Collection<T> tasks, Task task, Type monitorType, MelodicGraph<Task, DefaultEdge> graph){
    tasks.stream()
            .filter(t -> monitorType.equals(t.getType()))
            .forEach(t -> setDependencies(graph, CREATE, task, t));
  }

  private void setDeleteTaskAfterMonitors(MelodicGraph<Task, DefaultEdge> graph, Collection<VirtualMachineInstanceMonitorTask> vmInstMonitorTasks,
          Collection<ApplicationComponentInstanceMonitorTask> acInstMonitorTasks, Task task, Set<Task> predecessors) {

    if (DELETE.equals(task.getType()) && CollectionUtils.isEmpty(predecessors)) {
      setDeleteTaskAfterMonitors(vmInstMonitorTasks, task, graph);
      setDeleteTaskAfterMonitors(acInstMonitorTasks, task, graph);
    }
  }

  private <T extends ConfigurationTask> void setDeleteTaskAfterMonitors(Collection<T> tasks, Task task, MelodicGraph<Task, DefaultEdge> graph){
    tasks.stream()
            .filter(t -> CREATE.equals(t.getType()))
            .forEach(t -> setDependencies(graph, CREATE, t, task));
  }

  private void setSequentiallyVirtualMachineTasks(MelodicGraph<Task, DefaultEdge> graph,
    Collection<VirtualMachineTask> vmTasks){

    Iterator<VirtualMachineTask> it = vmTasks.iterator();

    if (it.hasNext()){
      VirtualMachineTask prevTask = it.next();

      while (it.hasNext()) {
        VirtualMachineTask task = it.next();
        if (task.getType().equals(prevTask.getType())){
          setDependencies(graph, task.getType(), prevTask, task);
        }
        prevTask = task;

      }
    }
  }

}