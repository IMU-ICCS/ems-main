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
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.springframework.stereotype.Service;

import java.util.List;

import static eu.paasage.upperware.adapter.plangenerator.tasks.Type.CREATE;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class DefaultGraphGenerator implements GraphGenerator<ComparableModel> {

  @Override
  public SimpleDirectedGraph<Task, DefaultEdge> generateConfigGraph(ComparableModel model) {
    log.info("Building graph from prepared model");

    SimpleDirectedGraph<Task, DefaultEdge> graph = new SimpleDirectedGraph<>(DefaultEdge.class);

    List<CloudTask> cloudTasks = model.getClouds().stream()
      .map(cloud -> new CloudTask(CREATE, cloud)).collect(toList());
    cloudTasks.forEach(cloudTask -> {
      log.debug("Adding vertex {}", cloudTask);
      graph.addVertex(cloudTask);
    });

    Application app = model.getApplication();
    ApplicationTask appTask = new ApplicationTask(CREATE, app);
    log.debug("Adding vertex {}", appTask);
    graph.addVertex(appTask);

    ApplicationInstance appInst = model.getApplicationInstance();
    ApplicationInstanceTask appInstTask = new ApplicationInstanceTask(CREATE, appInst);
    log.debug("Adding vertex {}", appInstTask);
    graph.addVertex(appInstTask);
    log.debug("Setting {} as a dependency to {}", appTask, appInstTask);
    graph.addEdge(appTask, appInstTask);

    List<LifecycleComponentTask> lcTasks = model.getLifecycleComponents().stream()
      .map(lc -> new LifecycleComponentTask(CREATE, lc)).collect(toList());
    lcTasks.forEach(lcTask -> {
      log.debug("Adding vertex {}", lcTask);
      graph.addVertex(lcTask);
    });

    List<VirtualMachineTask> vmTasks = model.getVirtualMachines().stream()
      .map(vm -> new VirtualMachineTask(CREATE, vm)).collect(toList());
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

    List<VirtualMachineInstanceTask> vmInstTasks = model.getVirtualMachineInstances().stream()
      .map(vmInst -> new VirtualMachineInstanceTask(CREATE, vmInst)).collect(toList());
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

    List<ApplicationComponentTask> acTasks = model.getApplicationComponents().stream()
      .map(ac -> new ApplicationComponentTask(CREATE, ac)).collect(toList());
    acTasks.forEach(acTask -> {
      log.debug("Adding vertex {}", acTask);
      graph.addVertex(acTask);
      log.debug("Setting {} as a dependency to {}", appTask, acTask);
      graph.addEdge(appTask, acTask);
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

    List<ApplicationComponentInstanceTask> acInstTasks = model.getApplicationComponentInstances().stream()
      .map(acInst -> new ApplicationComponentInstanceTask(CREATE, acInst)).collect(toList());
    acInstTasks.forEach(acInstTask -> {
      log.debug("Adding vertex {}", acInstTask);
      graph.addVertex(acInstTask);
      log.debug("Setting {} as a dependency to {}", appInstTask, acInstTask);
      graph.addEdge(appInstTask, acInstTask);
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

    List<PortProvidedTask> portProvidedTasks = model.getPortsProvided().stream()
      .map(portProvided -> new PortProvidedTask(CREATE, portProvided)).collect(toList());
    portProvidedTasks.forEach(portProvidedTask -> {
      log.debug("Adding vertex {}", portProvidedTask);
      graph.addVertex(portProvidedTask);
      PortProvided pp = portProvidedTask.getData();
      String acName = pp.getAcName();
      acTasks.forEach(acTask -> {
        if (acTask.getData().getName().equals(acName)) {
          log.debug("Setting {} as a dependency to {}", acTask, portProvidedTask);
          graph.addEdge(acTask, portProvidedTask);
        }
      });
    });

    List<PortRequiredTask> portRequiredTasks = model.getPortsRequired().stream()
      .map(portRequired -> new PortRequiredTask(CREATE, portRequired)).collect(toList());
    portRequiredTasks.forEach(portRequiredTask -> {
      log.debug("Adding vertex {}", portRequiredTask);
      graph.addVertex(portRequiredTask);
      PortRequired pr = portRequiredTask.getData();
      String acName = pr.getAcName();
      acTasks.forEach(acTask -> {
        if (acTask.getData().getName().equals(acName)) {
          log.debug("Setting {} as a dependency to {}", acTask, portRequiredTask);
          graph.addEdge(acTask, portRequiredTask);
        }
      });
    });

    List<CommunicationTask> commTasks = model.getCommunications().stream()
      .map(comm -> new CommunicationTask(CREATE, comm)).collect(toList());
    commTasks.forEach(commTask -> {
      log.debug("Adding vertex {}", commTask);
      graph.addVertex(commTask);
      Communication comm = commTask.getData();
      portProvidedTasks.forEach(portProvidedTask -> {
        if (portProvidedTask.getData().getName().equals(comm.getPortProvName())) {
          log.debug("Setting {} as a dependency to {}", portProvidedTask, commTask);
          graph.addEdge(portProvidedTask, commTask);
        }
      });
      portRequiredTasks.forEach(portRequiredTask -> {
        if (portRequiredTask.getData().getName().equals(comm.getPortReqName())) {
          log.debug("Setting {} as a dependency to {}", portRequiredTask, commTask);
          graph.addEdge(portRequiredTask, commTask);
        }
      });
    });

    log.info("Built graph: {}", graph);

    return graph;
  }

  @Override
  public SimpleDirectedWeightedGraph<Task, DefaultEdge> generateReconfigGraph(ComparableModel oldModel, ComparableModel newModel) {
    // TODO
    return null;
  }
}
