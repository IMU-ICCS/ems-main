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
import com.google.common.collect.Sets;
import eu.melodic.upperware.adapter.plangenerator.model.*;
import eu.melodic.upperware.adapter.plangenerator.tasks.*;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class DefaultGraphGenerator extends AbstractDefaultGraphGenerator<ComparableModel> {

  @Override
  public SimpleDirectedGraph<Task, DefaultEdge> generateConfigGraph(ComparableModel model) {
    log.info("Building configuration graph from prepared model");

    SimpleDirectedGraph<Task, DefaultEdge> graph = new SimpleDirectedGraph<>(DefaultEdge.class);

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
  public SimpleDirectedWeightedGraph<Task, DefaultEdge> generateReconfigGraph(ComparableModel oldModel, ComparableModel newModel) {
    log.info("Building reconfiguration graph from prepared models");

    SimpleDirectedWeightedGraph<Task, DefaultEdge> graph = new SimpleDirectedWeightedGraph<>(DefaultEdge.class);

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

    genVmInstMonitorConfigTasks(graph, vmInstTasks, newModel.getVirtualMachineInstanceMonitors());
    genAcInstMonitorConfigTasks(graph, acInstTasks, newModel.getApplicationComponentInstanceMonitors());

    log.info("Built graph: {}", graph);

    return graph;
  }

  private Collection<CloudApiTask> genCloudApiConfigTasks(Graph<Task, DefaultEdge> graph,
          Collection<CloudApi> cloudApis) {
    return genCloudApiTasks(graph, Type.CREATE, cloudApis);
  }

  private Collection<CloudApiTask> genCloudApiReconfigTasks(Graph<Task, DefaultEdge> graph,
          Collection<CloudApi> oldCloudApis, Collection<CloudApi> newCloudApis) {
    Collection<CloudApiTask> cloudApiTasks = Lists.newArrayList();

    newCloudApis.stream()
      .filter(newCloudApi -> !oldCloudApis.contains(newCloudApi))
      .forEach(newCloudApi -> {
        boolean isNew = true;
        String newCloudApiName = newCloudApi.getName();

        for (CloudApi oldCloudApi : oldCloudApis) {
          if (newCloudApiName.equals(oldCloudApi.getName())) {
            cloudApiTasks.addAll(genCloudApiTasks(graph, Type.UPDATE, Lists.newArrayList(newCloudApi)));
            isNew = false;
            break;
          }
        }
        if (isNew) {
          cloudApiTasks.addAll(genCloudApiTasks(graph, Type.CREATE, Lists.newArrayList(newCloudApi)));
        }
      });

    return cloudApiTasks;
  }

  private Collection<CloudTask> genCloudConfigTasks(Graph<Task, DefaultEdge> graph,
          Collection<CloudApiTask> cloudApiTasks, Collection<Cloud> clouds) {
    return genCloudTasks(graph, Type.CREATE, cloudApiTasks, clouds);
  }

  private Collection<CloudTask> genCloudReconfigTasks(Graph<Task, DefaultEdge> graph,
          Collection<CloudApiTask> cloudApiTasks, Collection<Cloud> oldClouds, Collection<Cloud> newClouds) {
    Collection<CloudTask> cloudTasks = Lists.newArrayList();

    newClouds.stream()
      .filter(newCloud -> !oldClouds.contains(newCloud))
      .forEach(newCloud -> {
        boolean isNew = true;
        String newCloudName = newCloud.getName();

        for (Cloud oldCloud : oldClouds) {
          if (newCloudName.equals(oldCloud.getName())) {
            cloudTasks.addAll(genCloudTasks(graph, Type.UPDATE, cloudApiTasks, Lists.newArrayList(newCloud)));
            isNew = false;
            break;
          }
        }
        if (isNew) {
          cloudTasks.addAll(genCloudTasks(graph, Type.CREATE, cloudApiTasks, Lists.newArrayList(newCloud)));
        }
      });

    return cloudTasks;
  }

  private Collection<CloudPropertyTask> genCloudPropertyConfigTasks(Graph<Task, DefaultEdge> graph,
          Collection<CloudTask> cloudTasks, Collection<CloudProperty> cloudProperties) {
    return genCloudPropertyTasks(graph, Type.CREATE, cloudTasks, cloudProperties);
  }

  private Collection<CloudPropertyTask> genCloudPropertyReconfigTasks(Graph<Task, DefaultEdge> graph,
          Collection<CloudTask> cloudTasks, Collection<CloudProperty> oldCloudProperties,
          Collection<CloudProperty> newCloudProperties) {
    Collection<CloudPropertyTask> cloudPropertyTasks = Lists.newArrayList();
    Set<CloudProperty> oldCloudPropertiesToDelete = Sets.newHashSet();

    newCloudProperties.stream()
      .filter(newCloudProperty -> !oldCloudProperties.contains(newCloudProperty))
      .forEach(newCloudProperty -> {
        String newCloudPropertyName = newCloudProperty.getName();

        for (CloudProperty oldCloudProperty : oldCloudProperties) {
          if (newCloudPropertyName.equals(oldCloudProperty.getName())) {
            oldCloudPropertiesToDelete.add(oldCloudProperty);
            cloudPropertyTasks.addAll(genCloudPropertyTasks(graph, Type.DELETE, cloudTasks,
              Lists.newArrayList(oldCloudProperty)));
            break;
          }
        }
        cloudPropertyTasks.addAll(genCloudPropertyTasks(graph, Type.CREATE, cloudTasks,
          Lists.newArrayList(newCloudProperty)));
      });

    oldCloudProperties.forEach(oldCloudProperty -> {
      if (!oldCloudPropertiesToDelete.contains(oldCloudProperty)) {
        cloudPropertyTasks.addAll(genCloudPropertyTasks(graph, Type.DELETE, cloudTasks,
          Lists.newArrayList(oldCloudProperty)));
      }
    });

    return cloudPropertyTasks;
  }

  private Collection<CloudCredentialTask> genCloudCredentialConfigTasks(Graph<Task, DefaultEdge> graph,
          Collection<CloudTask> cloudTasks, Collection<CloudCredential> cloudCredentials) {
    return genCloudCredentialTasks(graph, Type.CREATE, cloudTasks, cloudCredentials);
  }

  private Collection<CloudCredentialTask> genCloudCredentialReconfigTasks(Graph<Task, DefaultEdge> graph,
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
            cloudCredentialTasks.addAll(genCloudCredentialTasks(graph, Type.UPDATE, cloudTasks,
              Lists.newArrayList(newCloudCredential)));
            isNew = false;
            break;
          }
        }
        if (isNew) {
          cloudCredentialTasks.addAll(genCloudCredentialTasks(graph, Type.CREATE, cloudTasks,
            Lists.newArrayList(newCloudCredential)));
        }
      });

    return cloudCredentialTasks;
  }

  private ApplicationTask genAppConfigTask(Graph<Task, DefaultEdge> graph, Application app) {
    return genAppTask(graph, Type.CREATE, app);
  }

  private ApplicationTask genReconfigAppTask(Graph<Task, DefaultEdge> graph, Application oldApp, Application newApp) {
    if (!newApp.equals(oldApp)) {
      newApp.setOldName(oldApp.getName());
      return genAppTask(graph, Type.UPDATE, newApp);
    }
    return null;
  }

  private ApplicationInstanceTask genAppInstConfigTask(Graph<Task, DefaultEdge> graph, ApplicationTask appTask,
          ApplicationInstance appInst) {
    return genAppInstTask(graph, Type.CREATE, appTask, appInst);
  }

  private Collection<LifecycleComponentTask> genLcConfigTasks(Graph<Task, DefaultEdge> graph,
          Collection<LifecycleComponent> lcs) {
    return genLcTasks(graph, Type.CREATE, lcs);
  }

  private Collection<LifecycleComponentTask> genLcReconfigTasks(Graph<Task, DefaultEdge> graph,
          Collection<LifecycleComponent> oldLcs, Collection<LifecycleComponent> newLcs) {
    Collection<LifecycleComponentTask> lcTasks = Lists.newArrayList();

    lcTasks.addAll(genLcTasks(graph, Type.CREATE,
      newLcs.stream().filter(newLc -> !oldLcs.contains(newLc)).collect(toList())));
    lcTasks.addAll(genLcTasks(graph, Type.DELETE,
      oldLcs.stream().filter(oldLc -> !newLcs.contains(oldLc)).collect(toList())));

    return lcTasks;
  }

  private Collection<VirtualMachineTask> genVmConfigTasks(Graph<Task, DefaultEdge> graph,
          Collection<CloudTask> cloudTasks, Collection<VirtualMachine> vms) {
    return genVmTasks(graph, Type.CREATE, cloudTasks, vms);
  }

  private Collection<VirtualMachineTask> genVmReconfigTasks(Graph<Task, DefaultEdge> graph,
          Collection<CloudTask> cloudTasks, Collection<VirtualMachine> oldVms, Collection<VirtualMachine> newVms) {
    Collection<VirtualMachineTask> vmTasks = Lists.newArrayList();

    vmTasks.addAll(genVmTasks(graph, Type.CREATE, cloudTasks,
      newVms.stream().filter(newVm -> !oldVms.contains(newVm)).collect(toList())));
    vmTasks.addAll(genVmTasks(graph, Type.DELETE, cloudTasks,
      oldVms.stream().filter(oldVm -> !newVms.contains(oldVm)).collect(toList())));

    return vmTasks;
  }

  private Collection<VirtualMachineInstanceTask> genVmInstConfigTasks(Graph<Task, DefaultEdge> graph,
          Collection<VirtualMachineTask> vmTasks, Collection<VirtualMachineInstance> vmInsts) {
    return genVmInstTasks(graph, Type.CREATE, vmTasks, vmInsts);
  }

  private Collection<VirtualMachineInstanceTask> genVmInstReconfigTasks(Graph<Task, DefaultEdge> graph,
          Collection<VirtualMachineTask> vmTasks, Collection<VirtualMachineInstance> oldVmInsts,
          Collection<VirtualMachineInstance> newVmInsts) {
    Collection<VirtualMachineInstanceTask> vmInstTasks = Lists.newArrayList();

    vmInstTasks.addAll(genVmInstTasks(graph, Type.CREATE, vmTasks,
      newVmInsts.stream().filter(newVmInst -> !oldVmInsts.contains(newVmInst)).collect(toList())));
    vmInstTasks.addAll(genVmInstTasks(graph, Type.DELETE, vmTasks,
      oldVmInsts.stream().filter(oldVmInst -> !newVmInsts.contains(oldVmInst)).collect(toList())));

    return vmInstTasks;
  }

  private Collection<ApplicationComponentTask> genAcConfigTasks(Graph<Task, DefaultEdge> graph, ApplicationTask appTask,
          Collection<LifecycleComponentTask> lcTasks, Collection<VirtualMachineTask> vmTasks,
          Collection<ApplicationComponent> acs) {
    return genAcTasks(graph, Type.CREATE, appTask, lcTasks, vmTasks, acs);
  }

  private Collection<ApplicationComponentTask> genAcReconfigTasks(Graph<Task, DefaultEdge> graph, ApplicationTask appTask,
          Collection<LifecycleComponentTask> lcTasks, Collection<VirtualMachineTask> vmTasks,
          Collection<ApplicationComponent> oldAcs, Collection<ApplicationComponent> newAcs) {
    Collection<ApplicationComponentTask> acTasks = Lists.newArrayList();

    acTasks.addAll(genAcTasks(graph, Type.CREATE, appTask, lcTasks, vmTasks,
      newAcs.stream().filter(newAc -> !oldAcs.contains(newAc)).collect(toList())));
    acTasks.addAll(genAcTasks(graph, Type.DELETE, appTask, lcTasks, vmTasks,
      oldAcs.stream().filter(oldAc -> !newAcs.contains(oldAc)).collect(toList())));

    return acTasks;
  }

  private Collection<ApplicationComponentInstanceTask> genAcInstConfigTasks(Graph<Task, DefaultEdge> graph,
          ApplicationInstanceTask appInstTask, Collection<ApplicationComponentTask> acTasks,
          Collection<VirtualMachineInstanceTask> vmInstTasks, Collection<ApplicationComponentInstance> acInsts) {
    return genAcInstTasks(graph, Type.CREATE, appInstTask, acTasks, vmInstTasks, acInsts);
  }

  private Collection<ApplicationComponentInstanceTask> genAcInstReconfigTasks(Graph<Task, DefaultEdge> graph,
          Collection<ApplicationComponentTask> acTasks, Collection<VirtualMachineInstanceTask> vmInstTasks,
          Collection<ApplicationComponentInstance> oldAcInsts, Collection<ApplicationComponentInstance> newAcInsts) {
    Collection<ApplicationComponentInstanceTask> acInstTasks = Lists.newArrayList();

    acInstTasks.addAll(genAcInstTasks(graph, Type.CREATE, null, acTasks, vmInstTasks,
      newAcInsts.stream().filter(newAcInst -> !oldAcInsts.contains(newAcInst)).collect(toList())));
    acInstTasks.addAll(genAcInstTasks(graph, Type.DELETE, null, acTasks, vmInstTasks,
      oldAcInsts.stream().filter(oldAcInst -> !newAcInsts.contains(oldAcInst)).collect(toList())));

    return acInstTasks;
  }

  private Collection<PortProvidedTask> genPortProvConfigTasks(Graph<Task, DefaultEdge> graph,
          Collection<ApplicationComponentTask> acTasks, Collection<PortProvided> portsProv) {
    return genPortProvTasks(graph, Type.CREATE, acTasks, portsProv);
  }

  private Collection<PortProvidedTask> genPortProvReconfigTasks(Graph<Task, DefaultEdge> graph,
          Collection<ApplicationComponentTask> acTasks, Collection<PortProvided> oldPortsProv,
          Collection<PortProvided> newPortsProv) {
    Collection<PortProvidedTask> portProvTasks = Lists.newArrayList();

    portProvTasks.addAll(genPortProvTasks(graph, Type.CREATE, acTasks,
      newPortsProv.stream().filter(newPortProv -> !oldPortsProv.contains(newPortProv)).collect(toList())));
    portProvTasks.addAll(genPortProvTasks(graph, Type.DELETE, acTasks,
      oldPortsProv.stream().filter(oldPortProv -> !newPortsProv.contains(oldPortProv)).collect(toList())));

    return portProvTasks;
  }

  private Collection<PortRequiredTask> genPortReqConfigTasks(Graph<Task, DefaultEdge> graph,
          Collection<ApplicationComponentTask> acTasks, Collection<PortRequired> portsReq) {
    return genPortReqTasks(graph, Type.CREATE, acTasks, portsReq);
  }

  private Collection<PortRequiredTask> genPortReqReconfigTasks(Graph<Task, DefaultEdge> graph,
          Collection<ApplicationComponentTask> acTasks, Collection<PortRequired> oldPortsReq,
          Collection<PortRequired> newPortsReq) {
    Collection<PortRequiredTask> portReqTasks = Lists.newArrayList();

    portReqTasks.addAll(genPortReqTasks(graph, Type.CREATE, acTasks,
      newPortsReq.stream().filter(newPortReq -> !oldPortsReq.contains(newPortReq)).collect(toList())));
    portReqTasks.addAll(genPortReqTasks(graph, Type.DELETE, acTasks,
      oldPortsReq.stream().filter(oldPortReq -> !newPortsReq.contains(oldPortReq)).collect(toList())));

    return portReqTasks;
  }

  private Collection<CommunicationTask> genCommConfigTasks(SimpleDirectedGraph<Task, DefaultEdge> graph,
          Collection<PortProvidedTask> portProvTasks, Collection<PortRequiredTask> portReqTasks,
          Collection<Communication> comms) {
    return genCommTasks(graph, Type.CREATE, portProvTasks, portReqTasks, comms);
  }

  private Collection<CommunicationTask> genCommReconfigTasks(SimpleDirectedWeightedGraph<Task, DefaultEdge> graph,
          Collection<PortProvidedTask> portProvTasks, Collection<PortRequiredTask> portReqTasks,
          Collection<Communication> oldComms, Collection<Communication> newComms) {
    Collection<CommunicationTask> commTasks = Lists.newArrayList();

    commTasks.addAll(genCommTasks(graph, Type.CREATE, portProvTasks, portReqTasks,
      newComms.stream().filter(newComm -> !oldComms.contains(newComm)).collect(toList())));
    commTasks.addAll(genCommTasks(graph, Type.DELETE, portProvTasks, portReqTasks,
      oldComms.stream().filter(oldComm -> !newComms.contains(oldComm)).collect(toList())));

    return commTasks;
  }

  private Collection<VirtualMachineInstanceMonitorTask> genVmInstMonitorConfigTasks(Graph<Task, DefaultEdge> graph,
          Collection<VirtualMachineInstanceTask> vmInstTasks, Collection<VirtualMachineInstanceMonitor> vmInstMonitors) {
    return genVmInstMonitorTasks(graph, Type.CREATE, vmInstTasks, vmInstMonitors);
  }

  private Collection<ApplicationComponentInstanceMonitorTask> genAcInstMonitorConfigTasks(Graph<Task, DefaultEdge> graph,
          Collection<ApplicationComponentInstanceTask> acInstTasks, Collection<ApplicationComponentInstanceMonitor> acInstMonitors) {
    return genAcInstMonitorTasks(graph, Type.CREATE, acInstTasks, acInstMonitors);
  }
}