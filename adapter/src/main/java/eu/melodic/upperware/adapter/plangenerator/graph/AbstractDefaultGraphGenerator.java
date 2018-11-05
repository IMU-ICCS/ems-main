/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.plangenerator.graph;

import eu.melodic.upperware.adapter.plangenerator.graph.model.MelodicGraph;
import eu.melodic.upperware.adapter.plangenerator.model.*;
import eu.melodic.upperware.adapter.plangenerator.tasks.*;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.graph.DefaultEdge;

import java.util.Collection;

import static eu.melodic.upperware.adapter.plangenerator.graph.model.Type.CONFIG;
import static eu.melodic.upperware.adapter.plangenerator.tasks.Type.DELETE;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Slf4j
public abstract class AbstractDefaultGraphGenerator<T> implements GraphGenerator<T> {

  protected Collection<CloudApiTask> genCloudApiTasks(MelodicGraph<Task, DefaultEdge> graph, Type type,
                                                      Collection<CloudApi> cloudApis) {
    Collection<CloudApiTask> cloudApiTasks = cloudApis.stream()
      .map(cloud -> new CloudApiTask(type, cloud)).collect(toList());

    cloudApiTasks.forEach(cloudApiTask -> addVertex(graph, cloudApiTask));

    return cloudApiTasks;
  }

  protected Collection<CloudTask> genCloudTasks(MelodicGraph<Task, DefaultEdge> graph, Type type,
          Collection<CloudApiTask> cloudApiTasks, Collection<Cloud> clouds) {
    Collection<CloudTask> cloudTasks = clouds.stream()
      .map(cloud -> new CloudTask(type, cloud)).collect(toList());

    cloudTasks.forEach(cloudTask -> {
      addVertex(graph, cloudTask);

      String apiName = cloudTask.getData().getApiName();
      findAndSetDependencies(graph, cloudTask, apiName, cloudApiTasks, type);
    });

    return cloudTasks;
  }

  protected Collection<CloudPropertyTask> genCloudPropertyTasks(MelodicGraph<Task, DefaultEdge> graph, Type type,
          Collection<CloudTask> cloudTasks, Collection<CloudProperty> cloudProperties) {
    Collection<CloudPropertyTask> cloudPropertyTasks = cloudProperties.stream()
      .map(cloud -> new CloudPropertyTask(type, cloud)).collect(toList());

    cloudPropertyTasks.forEach(cloudPropertyTask -> {
      addVertex(graph, cloudPropertyTask);

      String cloudName = cloudPropertyTask.getData().getCloudName();
      findAndSetDependencies(graph, cloudPropertyTask, cloudName, cloudTasks, type);
    });

    return cloudPropertyTasks;
  }

  protected Collection<CloudCredentialTask> genCloudCredentialTasks(MelodicGraph<Task, DefaultEdge> graph, Type type,
          Collection<CloudTask> cloudTasks, Collection<CloudCredential> cloudCredentials) {
    Collection<CloudCredentialTask> cloudCredentialTasks = cloudCredentials.stream()
      .map(cloud -> new CloudCredentialTask(type, cloud)).collect(toList());

    cloudCredentialTasks.forEach(cloudCredentialTask -> {
      addVertex(graph, cloudCredentialTask);

      String cloudName = cloudCredentialTask.getData().getCloudName();
      findAndSetDependencies(graph, cloudCredentialTask, cloudName, cloudTasks, type);
    });

    return cloudCredentialTasks;
  }

  protected ApplicationTask genAppTask(MelodicGraph<Task, DefaultEdge> graph, Type type, Application app) {
    ApplicationTask appTask = new ApplicationTask(type, app);

    addVertex(graph, appTask);

    return appTask;
  }

  protected ApplicationInstanceTask genAppInstTask(MelodicGraph<Task, DefaultEdge> graph, Type type,
          ApplicationTask appTask, ApplicationInstance appInst) {
    ApplicationInstanceTask appInstTask = new ApplicationInstanceTask(type, appInst);

    addVertex(graph, appInstTask);

    if (appTask != null) {
      setDependencies(graph, type, appTask, appInstTask);
    }

    return appInstTask;
  }

  protected Collection<LifecycleComponentTask> genLcTasks(MelodicGraph<Task, DefaultEdge> graph, Type type,
          Collection<LifecycleComponent> lcs) {
    Collection<LifecycleComponentTask> lcTasks = lcs.stream()
      .map(lc -> new LifecycleComponentTask(type, lc)).collect(toList());

    lcTasks.forEach(lcTask -> {
      addVertex(graph, lcTask);
    });

    return lcTasks;
  }

  protected Collection<VirtualMachineTask> genVmTasks(MelodicGraph<Task, DefaultEdge> graph, Type type,
          Collection<CloudTask> cloudTasks, Collection<VirtualMachine> vms) {
    Collection<VirtualMachineTask> vmTasks = vms.stream()
      .map(vm -> new VirtualMachineTask(type, vm)).collect(toList());

    vmTasks.forEach(vmTask -> {
      addVertex(graph, vmTask);

      String cloudName = vmTask.getData().getCloudName();
      findAndSetDependencies(graph, vmTask, cloudName, cloudTasks, type);
    });

    return vmTasks;
  }

  protected Collection<VirtualMachineInstanceTask> genVmInstTasks(MelodicGraph<Task, DefaultEdge> graph, Type type,
          Collection<VirtualMachineTask> vmTasks, Collection<CommunicationTask> commTasks, Collection<VirtualMachineInstance> vmInsts) {
    Collection<VirtualMachineInstanceTask> vmInstTasks = vmInsts.stream()
            .map(vmInst -> new VirtualMachineInstanceTask(type, vmInst)).collect(toList());

    vmInstTasks.forEach(vmInstTask -> {
      addVertex(graph, vmInstTask);

      String vmName = vmInstTask.getData().getVmName();
      findAndSetDependencies(graph, vmInstTask, vmName, vmTasks, type);

      commTasks.forEach(commTask -> setDependencies(graph, type, commTask, vmInstTask));
    });

    return vmInstTasks;
  }

  protected Collection<ApplicationComponentTask> genAcTasks(MelodicGraph<Task, DefaultEdge> graph, Type type,
          ApplicationTask appTask, Collection<LifecycleComponentTask> lcTasks, Collection<VirtualMachineTask> vmTasks,
          Collection<ApplicationComponent> acs) {
    Collection<ApplicationComponentTask> acTasks = acs.stream()
      .map(ac -> new ApplicationComponentTask(type, ac)).collect(toList());

    acTasks.forEach(acTask -> {
      addVertex(graph, acTask);

      if (appTask != null) {
        setDependencies(graph, type, appTask, acTask);
      }
      ApplicationComponent ac = acTask.getData();
      String lcName = ac.getLcName();
      String vmName = ac.getVmName();

      findAndSetDependencies(graph, acTask, lcName, lcTasks, type);
      findAndSetDependencies(graph, acTask, vmName, vmTasks, type);
    });

    return acTasks;
  }

  protected Collection<ApplicationComponentInstanceTask> genAcInstTasks(MelodicGraph<Task, DefaultEdge> graph, Type type,
          ApplicationInstanceTask appInstTask, Collection<ApplicationComponentTask> acTasks,
          Collection<VirtualMachineInstanceTask> vmInstTasks, Collection<CommunicationTask> commTasks,
          Collection<ApplicationComponentInstance> acInsts) {
    Collection<ApplicationComponentInstanceTask> acInstTasks = acInsts.stream()
      .map(acInst -> new ApplicationComponentInstanceTask(type, acInst)).collect(toList());

    acInstTasks.forEach(acInstTask -> {
      addVertex(graph, acInstTask);

      if (appInstTask != null) {
        setDependencies(graph, type, appInstTask, acInstTask);
      }

      ApplicationComponentInstance acInst = acInstTask.getData();

      String acName = acInst.getAcName();
      String vmInstName = acInst.getVmInstName();

      findAndSetDependencies(graph, acInstTask, acName, acTasks, type);
      findAndSetDependencies(graph, acInstTask, vmInstName, vmInstTasks, type);
      commTasks.forEach(commTask -> setDependencies(graph, type, commTask, acInstTask));
    });

    return acInstTasks;
  }

  protected Collection<PortProvidedTask> genPortProvTasks(MelodicGraph<Task, DefaultEdge> graph, Type type,
          Collection<ApplicationComponentTask> acTasks, Collection<PortProvided> portsProv) {
    Collection<PortProvidedTask> portProvTasks = portsProv.stream().map(portProv -> new PortProvidedTask(type, portProv)).collect(toList());

    portProvTasks.forEach(portProvTask -> {
      addVertex(graph, portProvTask);

      String acName = portProvTask.getData().getAcName();
      findAndSetDependencies(graph, portProvTask, acName, acTasks, type);
    });

    return portProvTasks;
  }

  protected Collection<PortRequiredTask> genPortReqTasks(MelodicGraph<Task, DefaultEdge> graph, Type type,
          Collection<ApplicationComponentTask> acTasks, Collection<PortRequired> portsReq) {
    Collection<PortRequiredTask> portReqTasks = portsReq.stream()
      .map(portReq -> new PortRequiredTask(type, portReq)).collect(toList());

    portReqTasks.forEach(portReqTask -> {
      addVertex(graph, portReqTask);

      String acName = portReqTask.getData().getAcName();
      findAndSetDependencies(graph, portReqTask, acName, acTasks, type);
    });

    return portReqTasks;
  }

  protected Collection<CommunicationTask> genCommTasks(MelodicGraph<Task, DefaultEdge> graph, Type type,
          Collection<PortProvidedTask> portProvTasks, Collection<PortRequiredTask> portReqTasks,
          Collection<Communication> comms) {
    Collection<CommunicationTask> commTasks = comms.stream()
      .map(comm -> new CommunicationTask(type, comm)).collect(toList());

    commTasks.forEach(commTask -> {
      addVertex(graph, commTask);

      Communication comm = commTask.getData();
      String portProvName = comm.getPortProvName();
      String portReqName = comm.getPortReqName();

      findAndSetDependencies(graph, commTask, portProvName, portProvTasks, type);
      findAndSetDependencies(graph, commTask, portReqName, portReqTasks, type);
    });

    return commTasks;
  }

  protected Collection<VirtualMachineInstanceMonitorTask> genVmInstMonitorTasks(MelodicGraph<Task, DefaultEdge> graph, Type type,
          Collection<VirtualMachineInstanceTask> vmInstTasks, Collection<VirtualMachineInstanceMonitor> vmInstMonitors) {
    Collection<VirtualMachineInstanceMonitorTask> vmInstMonitorTasks = vmInstMonitors.stream()
      .map(vmInstMonitor -> new VirtualMachineInstanceMonitorTask(type, vmInstMonitor)).collect(toList());

    vmInstMonitorTasks.forEach(vmInstMonitorTask -> {
      addVertex(graph, vmInstMonitorTask);

      VirtualMachineInstanceMonitor vmInstMonitor = vmInstMonitorTask.getData();
      String vmInstName = vmInstMonitor.getVmInstName();

      findAndSetDependencies(graph, vmInstMonitorTask, vmInstName, vmInstTasks, type);
    });

    return vmInstMonitorTasks;
  }

  protected Collection<ApplicationComponentInstanceMonitorTask> genAcInstMonitorTasks(MelodicGraph<Task, DefaultEdge> graph,
          Type type, Collection<ApplicationComponentInstanceTask> acInstTasks,
          Collection<ApplicationComponentInstanceMonitor> acInstMonitors) {
    Collection<ApplicationComponentInstanceMonitorTask> acInstMonitorTasks = acInstMonitors.stream()
      .map(acInstMonitor -> new ApplicationComponentInstanceMonitorTask(type, acInstMonitor)).collect(toList());

    acInstMonitorTasks.forEach(acInstMonitorTask -> {
      addVertex(graph, acInstMonitorTask);

      ApplicationComponentInstanceMonitor acInstMonitor = acInstMonitorTask.getData();
      String acInstName = acInstMonitor.getAcInstName();

      findAndSetDependencies(graph, acInstMonitorTask, acInstName, acInstTasks, type);
    });

    return acInstMonitorTasks;
  }


  protected void setDependencies(MelodicGraph<Task, DefaultEdge> graph, Type type, Task source, Task target) {
    if (DELETE.equals(type)) {
      log.debug("Setting {} as a dependency to {}", target, source);
      graph.addEdge(target, source);
    } else {
      log.debug("Setting {} as a dependency to {}", source, target);
      graph.addEdge(source, target);
    }
  }

  protected void addVertex(MelodicGraph<Task, DefaultEdge> graph, Task task) {
    log.debug("Adding vertex {}", task);
    graph.addVertex(task);
  }

  protected void findAndSetDependencies(MelodicGraph<Task, DefaultEdge> graph, Task task, String depName,
                                      Collection<? extends Task> depTasks, Type type) {
    boolean wasSet = false;
    for (Task depTask : depTasks) {
      if (depTask.getData().getName().equals(depName) && depTask.getType().equals(task.getType())) {
        setDependencies(graph, type, depTask, task);
        wasSet = true;
      }
    }
    if (CONFIG.equals(graph.getType()) && !wasSet) {
      throw new IllegalStateException(
        format("Missing obligatory node of graph - dependency between %s and %s was not set",
          depName, task.getData().getName()));
    }
  }

  protected void findAndSetNodeDependencies(MelodicGraph<Task, DefaultEdge> graph, ProcessTask processTask, String depName,
                                        Collection<NodeTask> nodeTasks, Type type) {
    boolean wasSet = false;
    for (NodeTask nodeTask : nodeTasks) {
      if (nodeTask.getData().getNodeName().equals(depName) && nodeTask.getType().equals(processTask.getType())) {
        setDependencies(graph, type, nodeTask, processTask);
        wasSet = true;
      }
    }
    if (CONFIG.equals(graph.getType()) && !wasSet) {
      throw new IllegalStateException(
              format("Missing obligatory node of graph - dependency between %s and %s was not set",
                      depName, processTask.getData().getName()));
    }
  }

}
