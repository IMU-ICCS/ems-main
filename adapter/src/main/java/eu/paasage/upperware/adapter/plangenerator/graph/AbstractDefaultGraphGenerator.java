/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.plangenerator.graph;

import eu.paasage.upperware.adapter.plangenerator.model.*;
import eu.paasage.upperware.adapter.plangenerator.tasks.*;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.util.Collection;

import static eu.paasage.upperware.adapter.plangenerator.tasks.Type.CREATE;
import static eu.paasage.upperware.adapter.plangenerator.tasks.Type.DELETE;
import static java.util.stream.Collectors.toList;

@Slf4j
abstract public class AbstractDefaultGraphGenerator<T> implements GraphGenerator<T> {

  protected Collection<CloudApiTask> genCloudApiTasks(Graph<Task, DefaultEdge> graph, Type type,
          Collection<CloudApi> cloudApis) {
    Collection<CloudApiTask> cloudApiTasks = cloudApis.stream()
      .map(cloud -> new CloudApiTask(type, cloud)).collect(toList());

    cloudApiTasks.forEach(cloudApiTask -> {
      log.debug("Adding vertex {}", cloudApiTask);
      graph.addVertex(cloudApiTask);
    });

    return cloudApiTasks;
  }

  protected Collection<CloudTask> genCloudTasks(Graph<Task, DefaultEdge> graph, Type type,
          Collection<CloudApiTask> cloudApiTasks, Collection<Cloud> clouds) {
    Collection<CloudTask> cloudTasks = clouds.stream()
      .map(cloud -> new CloudTask(type, cloud)).collect(toList());

    cloudTasks.forEach(cloudTask -> {
      log.debug("Adding vertex {}", cloudTask);
      graph.addVertex(cloudTask);

      String apiName = cloudTask.getData().getApiName();
      cloudApiTasks.forEach(cloudApiTask -> {
        if (cloudApiTask.getData().getName().equals(apiName)) {
          log.debug("Setting {} as a dependency to {}", cloudApiTask, cloudTask);
          graph.addEdge(cloudApiTask, cloudTask);
        }
      });
    });

    return cloudTasks;
  }

  protected Collection<CloudPropertyTask> genCloudPropertyTasks(Graph<Task, DefaultEdge> graph, Type type,
          Collection<CloudTask> cloudTasks, Collection<CloudProperty> cloudProperties) {
    Collection<CloudPropertyTask> cloudPropertyTasks = cloudProperties.stream()
      .map(cloud -> new CloudPropertyTask(type, cloud)).collect(toList());

    cloudPropertyTasks.forEach(cloudPropertyTask -> {
      log.debug("Adding vertex {}", cloudPropertyTask);
      graph.addVertex(cloudPropertyTask);

      String cloudName = cloudPropertyTask.getData().getCloudName();
      cloudTasks.forEach(cloudTask -> {
        if (cloudTask.getData().getName().equals(cloudName)) {
          log.debug("Setting {} as a dependency to {}", cloudTask, cloudPropertyTask);
          graph.addEdge(cloudTask, cloudPropertyTask);
        }
      });
    });

    return cloudPropertyTasks;
  }

  protected Collection<CloudCredentialTask> genCloudCredentialTasks(Graph<Task, DefaultEdge> graph, Type type,
          Collection<CloudTask> cloudTasks, Collection<CloudCredential> cloudCredentials) {
    Collection<CloudCredentialTask> cloudCredentialTasks = cloudCredentials.stream()
      .map(cloud -> new CloudCredentialTask(type, cloud)).collect(toList());

    cloudCredentialTasks.forEach(cloudCredentialTask -> {
      log.debug("Adding vertex {}", cloudCredentialTask);
      graph.addVertex(cloudCredentialTask);

      String cloudName = cloudCredentialTask.getData().getCloudName();
      cloudTasks.forEach(cloudTask -> {
        if (cloudTask.getData().getName().equals(cloudName)) {
          log.debug("Setting {} as a dependency to {}", cloudTask, cloudCredentialTask);
          graph.addEdge(cloudTask, cloudCredentialTask);
        }
      });
    });

    return cloudCredentialTasks;
  }

  protected ApplicationTask genAppTask(Graph<Task, DefaultEdge> graph, Type type, Application app) {
    ApplicationTask appTask = new ApplicationTask(type, app);

    log.debug("Adding vertex {}", appTask);
    graph.addVertex(appTask);

    return appTask;
  }

  protected ApplicationInstanceTask genAppInstTask(Graph<Task, DefaultEdge> graph, Type type,
          ApplicationTask appTask, ApplicationInstance appInst) {
    ApplicationInstanceTask appInstTask = new ApplicationInstanceTask(type, appInst);

    log.debug("Adding vertex {}", appInstTask);
    graph.addVertex(appInstTask);

    if (appTask != null) {
      log.debug("Setting {} as a dependency to {}", appTask, appInstTask);
      graph.addEdge(appTask, appInstTask);
    }

    return appInstTask;
  }

  protected Collection<LifecycleComponentTask> genLcTasks(Graph<Task, DefaultEdge> graph, Type type,
          Collection<LifecycleComponent> lcs) {
    Collection<LifecycleComponentTask> lcTasks = lcs.stream()
      .map(lc -> new LifecycleComponentTask(type, lc)).collect(toList());

    lcTasks.forEach(lcTask -> {
      log.debug("Adding vertex {}", lcTask);
      graph.addVertex(lcTask);
    });

    return lcTasks;
  }

  protected Collection<VirtualMachineTask> genVmTasks(Graph<Task, DefaultEdge> graph, Type type,
          Collection<CloudTask> cloudTasks, Collection<VirtualMachine> vms) {
    Collection<VirtualMachineTask> vmTasks = vms.stream()
      .map(vm -> new VirtualMachineTask(type, vm)).collect(toList());

    vmTasks.forEach(vmTask -> {
      log.debug("Adding vertex {}", vmTask);
      graph.addVertex(vmTask);

      String cloudName = vmTask.getData().getCloudName();
      cloudTasks.forEach(cloudTask -> {
        if (cloudTask.getData().getName().equals(cloudName)) {
          log.debug("Setting {} as a dependency to {}", cloudTask, vmTask);
          graph.addEdge(cloudTask, vmTask);
        }
      });
    });

    return vmTasks;
  }

  protected Collection<VirtualMachineInstanceTask> genVmInstTasks(Graph<Task, DefaultEdge> graph, Type type,
          Collection<VirtualMachineTask> vmTasks, Collection<VirtualMachineInstance> vmInsts) {
    Collection<VirtualMachineInstanceTask> vmInstTasks = vmInsts.stream()
      .map(vmInst -> new VirtualMachineInstanceTask(type, vmInst)).collect(toList());

    vmInstTasks.forEach(vmInstTask -> {
      log.debug("Adding vertex {}", vmInstTask);
      graph.addVertex(vmInstTask);

      String vmName = vmInstTask.getData().getVmName();
      vmTasks.forEach(vmTask -> {
        if (vmTask.getData().getName().equals(vmName)) {
          log.debug("Setting {} as a dependency to {}", vmTask, vmInstTask);
          graph.addEdge(vmTask, vmInstTask);
        }
      });
    });

    return vmInstTasks;
  }

  protected Collection<ApplicationComponentTask> genAcTasks(Graph<Task, DefaultEdge> graph, Type type,
          ApplicationTask appTask, Collection<LifecycleComponentTask> lcTasks, Collection<VirtualMachineTask> vmTasks,
          Collection<ApplicationComponent> acs) {
    Collection<ApplicationComponentTask> acTasks = acs.stream()
      .map(ac -> new ApplicationComponentTask(type, ac)).collect(toList());

    acTasks.forEach(acTask -> {
      log.debug("Adding vertex {}", acTask);
      graph.addVertex(acTask);

      if (appTask != null) {
        log.debug("Setting {} as a dependency to {}", appTask, acTask);
        graph.addEdge(appTask, acTask);
      }

      ApplicationComponent ac = acTask.getData();

      String lcName = ac.getLcName();
      lcTasks.forEach(lcTask -> {
        if (lcTask.getData().getName().equals(lcName)) {
          log.debug("Setting {} as a dependency to {}", lcTask, acTask);
          graph.addEdge(lcTask, acTask);
        }
      });

      String vmName = ac.getVmName();
      vmTasks.forEach(vmTask -> {
        if (vmTask.getData().getName().equals(vmName)) {
          log.debug("Setting {} as a dependency to {}", vmTask, acTask);
          graph.addEdge(vmTask, acTask);
        }
      });
    });

    return acTasks;
  }

  protected Collection<ApplicationComponentInstanceTask> genAcInstTasks(Graph<Task, DefaultEdge> graph, Type type,
          ApplicationInstanceTask appInstTask, Collection<ApplicationComponentTask> acTasks,
          Collection<VirtualMachineInstanceTask> vmInstTasks, Collection<ApplicationComponentInstance> acInsts) {
    Collection<ApplicationComponentInstanceTask> acInstTasks = acInsts.stream()
      .map(acInst -> new ApplicationComponentInstanceTask(type, acInst)).collect(toList());

    acInstTasks.forEach(acInstTask -> {
      log.debug("Adding vertex {}", acInstTask);
      graph.addVertex(acInstTask);

      if (appInstTask != null) {
        log.debug("Setting {} as a dependency to {}", appInstTask, acInstTask);
        graph.addEdge(appInstTask, acInstTask);
      }

      ApplicationComponentInstance acInst = acInstTask.getData();

      String acName = acInst.getAcName();
      acTasks.forEach(acTask -> {
        if (acTask.getData().getName().equals(acName)) {
          log.debug("Setting {} as a dependency to {}", acTask, acInstTask);
          graph.addEdge(acTask, acInstTask);
        }
      });

      String vmInstName = acInst.getVmInstName();
      vmInstTasks.forEach(vmInstTask -> {
        if (vmInstTask.getData().getName().equals(vmInstName)) {
          log.debug("Setting {} as a dependency to {}", vmInstTask, acInstTask);
          graph.addEdge(vmInstTask, acInstTask);
        }
      });
    });

    return acInstTasks;
  }

  protected Collection<PortProvidedTask> genPortProvTasks(Graph<Task, DefaultEdge> graph, Type type,
          Collection<ApplicationComponentTask> acTasks, Collection<PortProvided> portsProv) {
    Collection<PortProvidedTask> portProvTasks = portsProv.stream()
      .map(portProv -> new PortProvidedTask(type, portProv)).collect(toList());

    portProvTasks.forEach(portProvTask -> {
      log.debug("Adding vertex {}", portProvTask);
      graph.addVertex(portProvTask);

      String acName = portProvTask.getData().getAcName();
      acTasks.forEach(acTask -> {
        if (acTask.getData().getName().equals(acName)) {
          log.debug("Setting {} as a dependency to {}", acTask, portProvTask);
          graph.addEdge(acTask, portProvTask);
        }
      });
    });

    return portProvTasks;
  }

  protected Collection<PortRequiredTask> genPortReqTasks(Graph<Task, DefaultEdge> graph, Type type,
          Collection<ApplicationComponentTask> acTasks, Collection<PortRequired> portsReq) {
    Collection<PortRequiredTask> portReqTasks = portsReq.stream()
      .map(portReq -> new PortRequiredTask(type, portReq)).collect(toList());

    portReqTasks.forEach(portReqTask -> {
      log.debug("Adding vertex {}", portReqTask);
      graph.addVertex(portReqTask);

      String acName = portReqTask.getData().getAcName();
      acTasks.forEach(acTask -> {
        if (acTask.getData().getName().equals(acName)) {
          log.debug("Setting {} as a dependency to {}", acTask, portReqTask);
          graph.addEdge(acTask, portReqTask);
        }
      });
    });

    return portReqTasks;
  }

  protected Collection<CommunicationTask> genCommTasks(Graph<Task, DefaultEdge> graph, Type type,
          Collection<PortProvidedTask> portProvTasks, Collection<PortRequiredTask> portReqTasks,
          Collection<Communication> comms) {
    Collection<CommunicationTask> commTasks = comms.stream()
      .map(comm -> new CommunicationTask(type, comm)).collect(toList());

    commTasks.forEach(commTask -> {
      log.debug("Adding vertex {}", commTask);
      graph.addVertex(commTask);

      Communication comm = commTask.getData();

      String portProvName = comm.getPortProvName();
      portProvTasks.forEach(portProTask -> {
        if (portProTask.getData().getName().equals(portProvName)) {
          log.debug("Setting {} as a dependency to {}", portProTask, commTask);
          graph.addEdge(portProTask, commTask);
        }
      });

      String portReqName = comm.getPortReqName();
      portReqTasks.forEach(portReqTask -> {
        if (portReqTask.getData().getName().equals(portReqName)) {
          log.debug("Setting {} as a dependency to {}", portReqTask, commTask);
          graph.addEdge(portReqTask, commTask);
        }
      });
    });

    return commTasks;
  }

  protected Collection<VirtualMachineInstanceMonitorTask> genVmInstMonitorTasks(Graph<Task, DefaultEdge> graph, Type type,
          Collection<VirtualMachineInstanceTask> vmInstTasks, Collection<VirtualMachineInstanceMonitor> vmInstMonitors) {
    Collection<VirtualMachineInstanceMonitorTask> vmInstMonitorTasks = vmInstMonitors.stream()
      .map(vmInstMonitor -> new VirtualMachineInstanceMonitorTask(type, vmInstMonitor)).collect(toList());

    vmInstMonitorTasks.forEach(vmInstMonitorTask -> {
      log.debug("Adding vertex {}", vmInstMonitorTask);
      graph.addVertex(vmInstMonitorTask);

      VirtualMachineInstanceMonitor vmInstMonitor = vmInstMonitorTask.getData();

      String vmInstName = vmInstMonitor.getVmInstName();
      vmInstTasks.forEach(vmInstTask -> {
        if (vmInstTask.getData().getName().equals(vmInstName)) {
          if (DELETE.equals(type)) {
            log.debug("Setting {} as a dependency to {}", vmInstMonitorTask, vmInstTask);
            graph.addEdge(vmInstMonitorTask, vmInstTask);
          } else {
            log.debug("Setting {} as a dependency to {}", vmInstTask, vmInstMonitorTask);
            graph.addEdge(vmInstTask, vmInstMonitorTask);
          }
        }
      });
    });

    return vmInstMonitorTasks;
  }

  protected Collection<ApplicationComponentInstanceMonitorTask> genAcInstMonitorTasks(Graph<Task, DefaultEdge> graph,
          Type type, Collection<ApplicationComponentInstanceTask> acInstTasks,
          Collection<ApplicationComponentInstanceMonitor> acInstMonitors) {
    Collection<ApplicationComponentInstanceMonitorTask> acInstMonitorTasks = acInstMonitors.stream()
      .map(acInstMonitor -> new ApplicationComponentInstanceMonitorTask(type, acInstMonitor)).collect(toList());

    acInstMonitorTasks.forEach(acInstMonitorTask -> {
      log.debug("Adding vertex {}", acInstMonitorTask);
      graph.addVertex(acInstMonitorTask);

      ApplicationComponentInstanceMonitor acInstMonitor = acInstMonitorTask.getData();

      String acInstName = acInstMonitor.getAcInstName();
      acInstTasks.forEach(acInstTask -> {
        if (acInstTask.getData().getName().equals(acInstName)) {
          if (DELETE.equals(type)) {
            log.debug("Setting {} as a dependency to {}", acInstMonitorTask, acInstTask);
            graph.addEdge(acInstMonitorTask, acInstTask);
          } else {
            log.debug("Setting {} as a dependency to {}", acInstTask, acInstMonitorTask);
            graph.addEdge(acInstTask, acInstMonitorTask);
          }
        }
      });
    });

    return acInstMonitorTasks;
  }
}
