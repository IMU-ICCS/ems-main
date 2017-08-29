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
import eu.melodic.upperware.adapter.plangenerator.graph.model.MelodicGraph;
import eu.melodic.upperware.adapter.plangenerator.model.*;
import eu.melodic.upperware.adapter.plangenerator.tasks.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.jgrapht.alg.DirectedNeighborIndex;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static eu.melodic.upperware.adapter.plangenerator.graph.model.Type.CONFIG;
import static eu.melodic.upperware.adapter.plangenerator.graph.model.Type.RECONFIG;
import static eu.melodic.upperware.adapter.plangenerator.tasks.Type.CREATE;
import static eu.melodic.upperware.adapter.plangenerator.tasks.Type.DELETE;
import static eu.melodic.upperware.adapter.plangenerator.tasks.Type.UPDATE;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class DefaultGraphGenerator extends AbstractDefaultGraphGenerator<ComparableModel> {

  @Override
  public SimpleDirectedGraph<Task, DefaultEdge> generateConfigGraph(ComparableModel model) {
    log.info("Building configuration graph from prepared model");

    MelodicGraph<Task, DefaultEdge> graph = new MelodicGraph<>(DefaultEdge.class, CONFIG);

    Collection<CloudApiTask> cloudApiTasks = genCloudApiConfigTasks(
      graph, model.getCloudApis());
    Collection<CloudTask> cloudTasks = genCloudConfigTasks(
      graph, cloudApiTasks, model.getClouds());
    Collection<CloudPropertyTask> cloudPropertyTasks = genCloudPropertyConfigTasks(
      graph, cloudTasks,model.getCloudProperties());
    Collection<CloudCredentialTask> cloudCredentialTasks = genCloudCredentialConfigTasks(
      graph, cloudTasks, model.getCloudCredentials());

    ApplicationTask appTask = genAppConfigTask(graph, model.getApplication());
    ApplicationInstanceTask appInstTask = genAppInstConfigTask(graph, appTask, model.getApplicationInstance());

    Collection<LifecycleComponentTask> lcTasks = genLcConfigTasks(graph, model.getLifecycleComponents());

    Collection<VirtualMachineTask> vmTasks = genVmConfigTasks(
      graph, cloudTasks, model.getVirtualMachines());
    Collection<VirtualMachineInstanceTask> vmInstTasks = genVmInstConfigTasks(
      graph, vmTasks, model.getVirtualMachineInstances());

    Collection<ApplicationComponentTask> acTasks = genAcConfigTasks(
      graph, appTask, lcTasks, vmTasks, model.getApplicationComponents());
    Collection<ApplicationComponentInstanceTask> acInstTasks = genAcInstConfigTasks(
      graph, appInstTask, acTasks, vmInstTasks, model.getApplicationComponentInstances());

    Collection<PortProvidedTask> portProvTasks = genPortProvConfigTasks(
      graph, acTasks, model.getPortsProvided());
    Collection<PortRequiredTask> portReqTasks = genPortReqConfigTasks(
      graph, acTasks, model.getPortsRequired());
    Collection<CommunicationTask> commTasks = genCommConfigTasks(
      graph, portProvTasks, portReqTasks, model.getCommunications());

    genVmInstMonitorConfigTasks(graph, vmInstTasks, model.getVirtualMachineInstanceMonitors());
    genAcInstMonitorConfigTasks(graph, acInstTasks, model.getApplicationComponentInstanceMonitors());

    log.info("Built graph: {}", graph);

    return graph;
  }

  @Override
  public SimpleDirectedGraph<Task, DefaultEdge> generateReconfigGraph(ComparableModel oldModel, ComparableModel newModel) {
    log.info("Building reconfiguration graph from prepared models");

    MelodicGraph<Task, DefaultEdge> graph = new MelodicGraph<>(DefaultEdge.class, RECONFIG);

    Collection<CloudApiTask> cloudApiTasks = genCloudApiReconfigTasks(
      graph,oldModel.getCloudApis(), newModel.getCloudApis());
    Collection<CloudTask> cloudTasks = genCloudReconfigTasks(
      graph, cloudApiTasks, oldModel.getClouds(), newModel.getClouds());
    Collection<CloudPropertyTask> cloudPropertyTasks = genCloudPropertyReconfigTasks(
      graph, cloudTasks, oldModel.getCloudProperties(), newModel.getCloudProperties());
    Collection<CloudCredentialTask> cloudCredentialTasks = genCloudCredentialReconfigTasks(
      graph, cloudTasks, oldModel.getCloudCredentials(), newModel.getCloudCredentials());

    ApplicationTask appTask = genReconfigAppTask(graph, oldModel.getApplication(), newModel.getApplication());

    Collection<LifecycleComponentTask> lcTasks = genLcReconfigTasks(
      graph, oldModel.getLifecycleComponents(), newModel.getLifecycleComponents());

    Collection<VirtualMachineTask> vmTasks = genVmReconfigTasks(
      graph, cloudTasks, oldModel.getVirtualMachines(), newModel.getVirtualMachines());
    Collection<VirtualMachineInstanceTask> vmInstTasks = genVmInstReconfigTasks(
      graph, vmTasks, oldModel.getVirtualMachineInstances(), newModel.getVirtualMachineInstances());

    Collection<ApplicationComponentTask> acTasks = genAcReconfigTasks(
      graph, appTask, lcTasks, vmTasks, oldModel.getApplicationComponents(), newModel.getApplicationComponents());
    Collection<ApplicationComponentInstanceTask> acInstTasks = genAcInstReconfigTasks(
      graph, acTasks, vmInstTasks, oldModel.getApplicationComponentInstances(), newModel.getApplicationComponentInstances());

    Collection<PortProvidedTask> portProvTasks = genPortProvReconfigTasks(
      graph, acTasks, oldModel.getPortsProvided(), newModel.getPortsProvided());
    Collection<PortRequiredTask> portReqTasks = genPortReqReconfigTasks(
      graph, acTasks, oldModel.getPortsRequired(), newModel.getPortsRequired());
    Collection<CommunicationTask> commTasks = genCommReconfigTasks(
      graph, portProvTasks, portReqTasks, oldModel.getCommunications(), newModel.getCommunications());

    Collection<VirtualMachineInstanceMonitorTask> vmInstMonitorTasks = genVmInstMonitorReconfigTasks(
      graph, vmInstTasks, oldModel.getVirtualMachineInstanceMonitors(), newModel.getVirtualMachineInstanceMonitors());

    Collection<ApplicationComponentInstanceMonitorTask> acInstMonitorTasks = genAcInstMonitorReconfigTasks(
      graph, acInstTasks, oldModel.getApplicationComponentInstanceMonitors(),
      newModel.getApplicationComponentInstanceMonitors());

    setDeleteTasksAfterMonitors(graph, vmInstMonitorTasks, acInstMonitorTasks);

    log.info("Built graph: {}", graph);

    return graph;
  }

  private Collection<CloudApiTask> genCloudApiConfigTasks(MelodicGraph<Task, DefaultEdge> graph,
          Collection<CloudApi> cloudApis) {
    return genCloudApiTasks(graph, CREATE, cloudApis);
  }

  private Collection<CloudApiTask> genCloudApiReconfigTasks(MelodicGraph<Task, DefaultEdge> graph,
          Collection<CloudApi> oldCloudApis, Collection<CloudApi> newCloudApis) {
    Collection<CloudApiTask> cloudApiTasks = Lists.newArrayList();

    newCloudApis.stream()
      .filter(newCloudApi -> !oldCloudApis.contains(newCloudApi))
      .forEach(newCloudApi -> {
        boolean isNew = true;
        String newCloudApiName = newCloudApi.getName();

        for (CloudApi oldCloudApi : oldCloudApis) {
          if (newCloudApiName.equals(oldCloudApi.getName())) {
            cloudApiTasks.addAll(genCloudApiTasks(graph, UPDATE, Lists.newArrayList(newCloudApi)));
            isNew = false;
            break;
          }
        }
        if (isNew) {
          cloudApiTasks.addAll(genCloudApiTasks(graph, CREATE, Lists.newArrayList(newCloudApi)));
        }
      });

    return cloudApiTasks;
  }

  private Collection<CloudTask> genCloudConfigTasks(MelodicGraph<Task, DefaultEdge> graph,
          Collection<CloudApiTask> cloudApiTasks, Collection<Cloud> clouds) {
    return genCloudTasks(graph, CREATE, cloudApiTasks, clouds);
  }

  private Collection<CloudTask> genCloudReconfigTasks(MelodicGraph<Task, DefaultEdge> graph,
          Collection<CloudApiTask> cloudApiTasks, Collection<Cloud> oldClouds, Collection<Cloud> newClouds) {
    Collection<CloudTask> cloudTasks = Lists.newArrayList();

    newClouds.stream()
      .filter(newCloud -> !oldClouds.contains(newCloud))
      .forEach(newCloud -> {
        boolean isNew = true;
        String newCloudName = newCloud.getName();

        for (Cloud oldCloud : oldClouds) {
          if (newCloudName.equals(oldCloud.getName())) {
            cloudTasks.addAll(genCloudTasks(graph, UPDATE, cloudApiTasks, Lists.newArrayList(newCloud)));
            isNew = false;
            break;
          }
        }
        if (isNew) {
          cloudTasks.addAll(genCloudTasks(graph, CREATE, cloudApiTasks, Lists.newArrayList(newCloud)));
        }
      });

    return cloudTasks;
  }

  private Collection<CloudPropertyTask> genCloudPropertyConfigTasks(MelodicGraph<Task, DefaultEdge> graph,
          Collection<CloudTask> cloudTasks, Collection<CloudProperty> cloudProperties) {
    return genCloudPropertyTasks(graph, CREATE, cloudTasks, cloudProperties);
  }

  private Collection<CloudPropertyTask> genCloudPropertyReconfigTasks(MelodicGraph<Task, DefaultEdge> graph,
          Collection<CloudTask> cloudTasks, Collection<CloudProperty> oldCloudProperties,
          Collection<CloudProperty> newCloudProperties) {
    Collection<CloudPropertyTask> cloudPropertyTasks = Lists.newArrayList();

    newCloudProperties.stream()
      .filter(newCloudProperty -> !oldCloudProperties.contains(newCloudProperty))
      .forEach(newCloudProperty ->
        cloudPropertyTasks.addAll(genCloudPropertyTasks(graph, CREATE, cloudTasks,
          Lists.newArrayList(newCloudProperty)))
      );

    oldCloudProperties.stream()
      .filter(oldCloudProperty -> !newCloudProperties.contains(oldCloudProperty))
      .forEach(oldCloudProperty ->
        cloudPropertyTasks.addAll(genCloudPropertyTasks(graph, DELETE, cloudTasks,
          Lists.newArrayList(oldCloudProperty)))
      );

    return cloudPropertyTasks;
  }

  private Collection<CloudCredentialTask> genCloudCredentialConfigTasks(MelodicGraph<Task, DefaultEdge> graph,
          Collection<CloudTask> cloudTasks, Collection<CloudCredential> cloudCredentials) {
    return genCloudCredentialTasks(graph, CREATE, cloudTasks, cloudCredentials);
  }

  private Collection<CloudCredentialTask> genCloudCredentialReconfigTasks(MelodicGraph<Task, DefaultEdge> graph,
          Collection<CloudTask> cloudTasks, Collection<CloudCredential> oldCloudCredentials,
          Collection<CloudCredential> newCloudCredentials) {
    Collection<CloudCredentialTask> cloudCredentialTasks = Lists.newArrayList();

    newCloudCredentials.stream()
      .filter(newCloudCredential -> !oldCloudCredentials.contains(newCloudCredential))
      .forEach(newCloudCredential -> {
        boolean isNew = true;
        String newCloudCredentialName = newCloudCredential.getName();

        for (CloudCredential oldCloudCredential : oldCloudCredentials) {
          if (newCloudCredentialName.equals(oldCloudCredential.getName())) {
            cloudCredentialTasks.addAll(genCloudCredentialTasks(graph, UPDATE, cloudTasks,
              Lists.newArrayList(newCloudCredential)));
            isNew = false;
            break;
          }
        }
        if (isNew) {
          cloudCredentialTasks.addAll(genCloudCredentialTasks(graph, CREATE, cloudTasks,
            Lists.newArrayList(newCloudCredential)));
        }
      });

    return cloudCredentialTasks;
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

    vmTasks.addAll(genVmTasks(graph, CREATE, cloudTasks,
      newVms.stream().filter(newVm -> !oldVms.contains(newVm)).collect(toList())));
    vmTasks.addAll(genVmTasks(graph, DELETE, cloudTasks,
      oldVms.stream().filter(oldVm -> !newVms.contains(oldVm)).collect(toList())));

    return vmTasks;
  }

  private Collection<VirtualMachineInstanceTask> genVmInstConfigTasks(MelodicGraph<Task, DefaultEdge> graph,
          Collection<VirtualMachineTask> vmTasks, Collection<VirtualMachineInstance> vmInsts) {
    return genVmInstTasks(graph, CREATE, vmTasks, vmInsts);
  }

  private Collection<VirtualMachineInstanceTask> genVmInstReconfigTasks(MelodicGraph<Task, DefaultEdge> graph,
          Collection<VirtualMachineTask> vmTasks, Collection<VirtualMachineInstance> oldVmInsts,
          Collection<VirtualMachineInstance> newVmInsts) {
    Collection<VirtualMachineInstanceTask> vmInstTasks = Lists.newArrayList();

    vmInstTasks.addAll(genVmInstTasks(graph, CREATE, vmTasks,
      newVmInsts.stream().filter(newVmInst -> !oldVmInsts.contains(newVmInst)).collect(toList())));
    vmInstTasks.addAll(genVmInstTasks(graph, DELETE, vmTasks,
      oldVmInsts.stream().filter(oldVmInst -> !newVmInsts.contains(oldVmInst)).collect(toList())));

    return vmInstTasks;
  }

  private Collection<ApplicationComponentTask> genAcConfigTasks(MelodicGraph<Task, DefaultEdge> graph, ApplicationTask appTask,
          Collection<LifecycleComponentTask> lcTasks, Collection<VirtualMachineTask> vmTasks,
          Collection<ApplicationComponent> acs) {
    return genAcTasks(graph, CREATE, appTask, lcTasks, vmTasks, acs);
  }

  private Collection<ApplicationComponentTask> genAcReconfigTasks(MelodicGraph<Task, DefaultEdge> graph, ApplicationTask appTask,
          Collection<LifecycleComponentTask> lcTasks, Collection<VirtualMachineTask> vmTasks,
          Collection<ApplicationComponent> oldAcs, Collection<ApplicationComponent> newAcs) {
    Collection<ApplicationComponentTask> acTasks = Lists.newArrayList();

    acTasks.addAll(genAcTasks(graph, CREATE, appTask, lcTasks, vmTasks,
      newAcs.stream().filter(newAc -> !oldAcs.contains(newAc)).collect(toList())));
    acTasks.addAll(genAcTasks(graph, DELETE, appTask, lcTasks, vmTasks,
      oldAcs.stream().filter(oldAc -> !newAcs.contains(oldAc)).collect(toList())));

    return acTasks;
  }

  private Collection<ApplicationComponentInstanceTask> genAcInstConfigTasks(MelodicGraph<Task, DefaultEdge> graph,
          ApplicationInstanceTask appInstTask, Collection<ApplicationComponentTask> acTasks,
          Collection<VirtualMachineInstanceTask> vmInstTasks, Collection<ApplicationComponentInstance> acInsts) {
    return genAcInstTasks(graph, CREATE, appInstTask, acTasks, vmInstTasks, acInsts);
  }

  private Collection<ApplicationComponentInstanceTask> genAcInstReconfigTasks(MelodicGraph<Task, DefaultEdge> graph,
          Collection<ApplicationComponentTask> acTasks, Collection<VirtualMachineInstanceTask> vmInstTasks,
          Collection<ApplicationComponentInstance> oldAcInsts, Collection<ApplicationComponentInstance> newAcInsts) {
    Collection<ApplicationComponentInstanceTask> acInstTasks = Lists.newArrayList();

    acInstTasks.addAll(genAcInstTasks(graph, CREATE, null, acTasks, vmInstTasks,
      newAcInsts.stream().filter(newAcInst -> !oldAcInsts.contains(newAcInst)).collect(toList())));
    acInstTasks.addAll(genAcInstTasks(graph, DELETE, null, acTasks, vmInstTasks,
      oldAcInsts.stream().filter(oldAcInst -> !newAcInsts.contains(oldAcInst)).collect(toList())));

    return acInstTasks;
  }

  private Collection<PortProvidedTask> genPortProvConfigTasks(MelodicGraph<Task, DefaultEdge> graph,
          Collection<ApplicationComponentTask> acTasks, Collection<PortProvided> portsProv) {
    return genPortProvTasks(graph, CREATE, acTasks, portsProv);
  }

  private Collection<PortProvidedTask> genPortProvReconfigTasks(MelodicGraph<Task, DefaultEdge> graph,
          Collection<ApplicationComponentTask> acTasks, Collection<PortProvided> oldPortsProv,
          Collection<PortProvided> newPortsProv) {
    Collection<PortProvidedTask> portProvTasks = Lists.newArrayList();

    portProvTasks.addAll(genPortProvTasks(graph, CREATE, acTasks,
      newPortsProv.stream().filter(newPortProv -> !oldPortsProv.contains(newPortProv)).collect(toList())));
    portProvTasks.addAll(genPortProvTasks(graph, DELETE, acTasks,
      oldPortsProv.stream().filter(oldPortProv -> !newPortsProv.contains(oldPortProv)).collect(toList())));

    return portProvTasks;
  }

  private Collection<PortRequiredTask> genPortReqConfigTasks(MelodicGraph<Task, DefaultEdge> graph,
          Collection<ApplicationComponentTask> acTasks, Collection<PortRequired> portsReq) {
    return genPortReqTasks(graph, CREATE, acTasks, portsReq);
  }

  private Collection<PortRequiredTask> genPortReqReconfigTasks(MelodicGraph<Task, DefaultEdge> graph,
          Collection<ApplicationComponentTask> acTasks, Collection<PortRequired> oldPortsReq,
          Collection<PortRequired> newPortsReq) {
    Collection<PortRequiredTask> portReqTasks = Lists.newArrayList();

    portReqTasks.addAll(genPortReqTasks(graph, CREATE, acTasks,
      newPortsReq.stream().filter(newPortReq -> !oldPortsReq.contains(newPortReq)).collect(toList())));
    portReqTasks.addAll(genPortReqTasks(graph, DELETE, acTasks,
      oldPortsReq.stream().filter(oldPortReq -> !newPortsReq.contains(oldPortReq)).collect(toList())));

    return portReqTasks;
  }

  private Collection<CommunicationTask> genCommConfigTasks(MelodicGraph<Task, DefaultEdge> graph,
          Collection<PortProvidedTask> portProvTasks, Collection<PortRequiredTask> portReqTasks,
          Collection<Communication> comms) {
    return genCommTasks(graph, CREATE, portProvTasks, portReqTasks, comms);
  }

  private Collection<CommunicationTask> genCommReconfigTasks(MelodicGraph<Task, DefaultEdge> graph,
          Collection<PortProvidedTask> portProvTasks, Collection<PortRequiredTask> portReqTasks,
          Collection<Communication> oldComms, Collection<Communication> newComms) {
    Collection<CommunicationTask> commTasks = Lists.newArrayList();

    commTasks.addAll(genCommTasks(graph, CREATE, portProvTasks, portReqTasks,
      newComms.stream().filter(newComm -> !oldComms.contains(newComm)).collect(toList())));
    commTasks.addAll(genCommTasks(graph, DELETE, portProvTasks, portReqTasks,
      oldComms.stream().filter(oldComm -> !newComms.contains(oldComm)).collect(toList())));

    return commTasks;
  }

  private Collection<VirtualMachineInstanceMonitorTask> genVmInstMonitorConfigTasks(MelodicGraph<Task, DefaultEdge> graph,
          Collection<VirtualMachineInstanceTask> vmInstTasks, Collection<VirtualMachineInstanceMonitor> vmInstMonitors) {
    return genVmInstMonitorTasks(graph, CREATE, vmInstTasks, vmInstMonitors);
  }

  private Collection<VirtualMachineInstanceMonitorTask> genVmInstMonitorReconfigTasks(MelodicGraph<Task, DefaultEdge> graph,
          Collection<VirtualMachineInstanceTask> vmInstTasks, Collection<VirtualMachineInstanceMonitor> oldVmInstMonitors,
          Collection<VirtualMachineInstanceMonitor> newVmInstMonitors){
    Collection<VirtualMachineInstanceMonitorTask> vmInstMonitorTasks = Lists.newArrayList();

    vmInstMonitorTasks.addAll(genVmInstMonitorTasks(graph, CREATE, Lists.newArrayList(), oldVmInstMonitors));

    vmInstMonitorTasks.addAll(genVmInstMonitorTasks(graph, CREATE,
      vmInstTasks.stream().filter(vmInstTask -> CREATE.equals(vmInstTask.getType())).collect(toList()),
      newVmInstMonitors.stream().filter(vmInstMonitor -> vmInstTasks.stream()
        .anyMatch(vmInstTask -> vmInstTask.getData().getName().equals(vmInstMonitor.getVmInstName()))).collect(toList()))
    );

    vmInstMonitorTasks.addAll(genVmInstMonitorTasks(graph, DELETE, Lists.newArrayList(), newVmInstMonitors));

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

  //acInstTasks.stream().filter(acInstTask -> CREATE.equals(acInstTask.getType())).collect(toList()),

    //wszystkie monitory ze starego modelu
    acInstMonitorTasks.addAll(genAcInstMonitorTasks(graph, CREATE, Lists.newArrayList(), oldAcInstMonitors));

    //te, ktore zostaly nowo utworzone, powiazanie z monitorami do nich
    acInstMonitorTasks.addAll(genAcInstMonitorTasks(graph, CREATE,
      acInstTasks.stream().filter(acInstTask -> CREATE.equals(acInstTask.getType())).collect(toList()),
      newAcInstMonitors.stream().filter(acInstMonitor -> acInstTasks.stream()
        .anyMatch(acInstTask -> acInstTask.getData().getName().equals(acInstMonitor.getAcInstName()))).collect(toList()))
    );

    //wszystkie monitory z nowego modelu
    acInstMonitorTasks.addAll(genAcInstMonitorTasks(graph, DELETE, Lists.newArrayList(), newAcInstMonitors));

    return acInstMonitorTasks;
  }

//myląca nazwa
  private void setDeleteTasksAfterMonitors(MelodicGraph<Task, DefaultEdge> graph,
          Collection<VirtualMachineInstanceMonitorTask> vmInstMonitorTasks,
          Collection<ApplicationComponentInstanceMonitorTask> acInstMonitorTasks) {
    DirectedNeighborIndex<Task, DefaultEdge> neighbors = new DirectedNeighborIndex(graph);
    TopologicalOrderIterator<Task, DefaultEdge> it = new TopologicalOrderIterator(graph);

    while (it.hasNext()) {
      Task task = it.next();
      if (task instanceof VirtualMachineInstanceMonitorTask
        || task instanceof ApplicationComponentInstanceMonitorTask) {
        continue;
      }

      Set<Task> successors = neighbors.successorsOf(task);

      if (CollectionUtils.isEmpty(successors)) {
        Type monitorType = DELETE.equals(task.getType()) ? DELETE : CREATE;
        vmInstMonitorTasks.stream().filter(vmInstMonitorTask -> monitorType.equals(vmInstMonitorTask.getType()))
                .forEach(vmInstMonitorTask -> setDependencies(graph, CREATE, task, vmInstMonitorTask));
        acInstMonitorTasks.stream().filter(acInstMonitorTask -> monitorType.equals(acInstMonitorTask.getType()))
                .forEach(acInstMonitorTask -> setDependencies(graph, CREATE, task, acInstMonitorTask));

      }

//      if (!DELETE.equals(task.getType())) {
//        continue;
//      }

      Set<Task> predecessors = neighbors.predecessorsOf(task);
      //monitory create do tasków delete
      if (DELETE.equals(task.getType()) && CollectionUtils.isEmpty(predecessors)) {
        vmInstMonitorTasks.stream().filter(vmInstMonitorTask -> CREATE.equals(vmInstMonitorTask.getType()))
          .forEach(vmInstMonitorTask -> setDependencies(graph, CREATE, vmInstMonitorTask, task)
        );
        acInstMonitorTasks.stream().filter(acInstMonitorTask -> CREATE.equals(acInstMonitorTask.getType()))
          .forEach(acInstMonitorTask -> setDependencies(graph, CREATE, acInstMonitorTask, task)
        );
      }
    }
  }
}