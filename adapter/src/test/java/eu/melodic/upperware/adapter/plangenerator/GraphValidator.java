/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.plangenerator;

import eu.melodic.upperware.adapter.plangenerator.model.*;
import eu.melodic.upperware.adapter.plangenerator.tasks.*;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GraphValidator {

  public enum TASK_TYPE {
    CLOUD_API, CLOUD, CLOUD_PROPERTY, CLOUD_CREDENTIAL,
    APPLICATION, APPLICATION_INSTANCE,
    VIRTUALMACHINE, VIRTUALMACHINE_INSTANCE,
    LIFECYCLE,
    APPLICATION_COMPONENT, APPLICATION_COMPONENT_INSTANCE,
    PORT_PROVIDED, PORT_REQUIRED, COMMUNICATION,
    VM_INSTANCE_MONITOR, APP_COMP_INSTANCE_MONITOR
  }

  private static boolean checkVertex(Task v, SimpleDirectedGraph<Task, DefaultEdge> graph,
                                     Map<TASK_TYPE, Set<Task>> tasks) {
    if (v instanceof CloudApiTask) {
      return containsOnlyOutEdges(v, graph, tasks.get(TASK_TYPE.CLOUD_API));
    }

    if (v instanceof CloudTask) {
      return containsConnections(v, graph, tasks.get(TASK_TYPE.CLOUD), tasks.get(TASK_TYPE.CLOUD_API),
              task -> ((CloudApiTask) task).getData().getName()
                      .equals(((CloudTask) v).getData().getApiName()));
//      return containsConnectionsWithCloudApiTasks(v, graph,
//              tasks.get(TASK_TYPE.CLOUD),
//              ((CloudTask)v).getData().getApiName(),
//              tasks.get(TASK_TYPE.CLOUD_API));
    }

    if (v instanceof CloudPropertyTask) {
      return containsConnectionsWithCloudTasks(v, graph,
              tasks.get(TASK_TYPE.CLOUD_PROPERTY),
              ((CloudPropertyTask) v).getData().getCloudName(),
              tasks.get(TASK_TYPE.CLOUD));
    }

    if (v instanceof CloudCredentialTask) {
      return containsConnectionsWithCloudTasks(v, graph,
              tasks.get(TASK_TYPE.CLOUD_CREDENTIAL),
              ((CloudCredentialTask) v).getData().getCloudName(),
              tasks.get(TASK_TYPE.CLOUD));
    }

    if (v instanceof ApplicationTask) {
      return containsOnlyOutEdges(v, graph, tasks.get(TASK_TYPE.APPLICATION));
    }

    if (v instanceof ApplicationInstanceTask) {
      return containsConnectionWithApplication(v, graph,
              tasks.get(TASK_TYPE.APPLICATION_INSTANCE), tasks.get(TASK_TYPE.APPLICATION));
    }
    if (v instanceof LifecycleComponentTask) {
      return containsOnlyOutEdges(v, graph, tasks.get(TASK_TYPE.LIFECYCLE));
    }

    if (v instanceof VirtualMachineTask) {
      return containsConnectionsWithCloudTasks(v, graph,
              tasks.get(TASK_TYPE.VIRTUALMACHINE),
              ((VirtualMachineTask) v).getData().getCloudName(),
              tasks.get(TASK_TYPE.CLOUD));
    }

    if (v instanceof VirtualMachineInstanceTask) {
      return containsConnectionsWithVmTasks(v,
              ((VirtualMachineInstanceTask) v).getData().getVmName(), graph,
              tasks.get(TASK_TYPE.VIRTUALMACHINE_INSTANCE),
              tasks.get(TASK_TYPE.VIRTUALMACHINE));
    }

    if (v instanceof ApplicationComponentTask) {
      return containsConnectionsApplicationComponent((ApplicationComponentTask) v,
              graph, tasks.get(TASK_TYPE.APPLICATION_COMPONENT),
              tasks.get(TASK_TYPE.APPLICATION), tasks.get(TASK_TYPE.LIFECYCLE),
              tasks.get(TASK_TYPE.VIRTUALMACHINE));
    }

    if (v instanceof ApplicationComponentInstanceTask) {
      return containsConnectionsApplicationComponentInstance(
              (ApplicationComponentInstanceTask) v, graph,
              tasks.get(TASK_TYPE.APPLICATION_COMPONENT_INSTANCE),
              tasks.get(TASK_TYPE.APPLICATION_INSTANCE),
              tasks.get(TASK_TYPE.VIRTUALMACHINE_INSTANCE),
              tasks.get(TASK_TYPE.APPLICATION_COMPONENT));
    }

    if (v instanceof CommunicationTask) {
      return containsConnectionsCommunication((CommunicationTask) v, graph,
              tasks.get(TASK_TYPE.COMMUNICATION),
              tasks.get(TASK_TYPE.PORT_PROVIDED),
              tasks.get(TASK_TYPE.PORT_REQUIRED));
    }
    if (v instanceof PortProvidedTask) {
      return containsConnectionsWithApplicationComponent(v,
              ((PortProvidedTask) v).getData().getAcName(), graph,
              tasks.get(TASK_TYPE.PORT_PROVIDED),
              tasks.get(TASK_TYPE.APPLICATION_COMPONENT));
    }

    if (v instanceof PortRequiredTask) {
      return containsConnectionsWithApplicationComponent(v,
              ((PortRequiredTask) v).getData().getAcName(), graph,
              tasks.get(TASK_TYPE.PORT_REQUIRED),
              tasks.get(TASK_TYPE.APPLICATION_COMPONENT));
    }

    if (v instanceof VirtualMachineInstanceMonitorTask) {
      return containsConnectionsWithVmInstanceTasks(v,
              ((VirtualMachineInstanceMonitorTask) v).getData().getVmInstName(),
              graph,
              tasks.get(TASK_TYPE.VM_INSTANCE_MONITOR),
              tasks.get(TASK_TYPE.VIRTUALMACHINE_INSTANCE));
    }

    if (v instanceof ApplicationComponentInstanceMonitorTask) {
      return containsConnectionsWithAppComponentTasks(v,
              ((ApplicationComponentInstanceMonitorTask) v).getData().getAcInstName(),
              graph,
              tasks.get(TASK_TYPE.APP_COMP_INSTANCE_MONITOR),
              tasks.get(TASK_TYPE.APPLICATION_COMPONENT_INSTANCE));
    }


    return true;
  }

  private static boolean containsOnlyOutEdges(Task v,
                                              SimpleDirectedGraph<Task, DefaultEdge> graph,
                                              Set<Task> tasks) {
    return tasks.contains(v) && (getInEdges(v, graph) == 0);
  }


  private static boolean containsConnections(Task v,
                                             SimpleDirectedGraph<Task, DefaultEdge> graph,
                                             Set<Task> tasks, Set<Task> srcTask,
                                             Predicate predicate) {
    if (!tasks.contains(v)) {
      return false;
    }

    Holder holder = new Holder();

    Set<Task> filteredTask = filterTasks(srcTask, predicate);

    boolean b = filteredTask.stream()
            .peek(task -> holder.increment())
            .allMatch(task -> (graph.containsEdge(task, v)));

    return b && (holder.get() == getInEdges(v, graph));


  }

  private static boolean containsConnectionsWithCloudApiTasks(Task v,
                                                              SimpleDirectedGraph<Task, DefaultEdge> graph,
                                                              Set<Task> tasks, String cloudApiName,
                                                              Set<Task> cloudApiTasks) {


    int connectedVertex = 0;

    for (Task t : cloudApiTasks) {
      CloudApiTask c = (CloudApiTask) t;
      if (c.getData().getName().equals(cloudApiName)) {
        connectedVertex++;
        if (!(graph.containsEdge(t, v))) {
          return false;
        }
      }
    }
    return (connectedVertex == getInEdges(v, graph));

  }

  private static boolean containsConnectionsWithCloudTasks(
          Task v,
          SimpleDirectedGraph<Task, DefaultEdge> graph,
          Set<Task> tasks,
          String cloudName,
          Set<Task> cloudTasks) {

    if (!tasks.contains(v)) {
      return false;
    }
    Holder holder = new Holder();

    Set<Task> filteredTask = filterTasks(
            cloudTasks,
            cloudTask -> ((CloudTask) cloudTask).getData().getName().equals(cloudName));

    boolean b = filteredTask.stream()
            .peek(task -> holder.increment())
            .allMatch(task -> (graph.containsEdge(task, v)));

    return b && (holder.get() == getInEdges(v, graph));


//    for (Task t : cloudTasks) {
//      CloudTask c = (CloudTask) t;
//      if (c.getData().getName().equals(cloudName)){
//        connectedVertex++;
//        if (!(graph.containsEdge(t, v))){
//          return false;
//        }
//      }
//    }
//    return (connectedVertex == getInEdges(v,graph));
  }

  private static boolean containsConnectionsWithVmTasks(Task v,
                                                        String srcNameInVertex,
                                                        SimpleDirectedGraph<Task, DefaultEdge> graph,
                                                        Set<Task> tasks,
                                                        Set<Task> vmTasks) {
    if (!tasks.contains(v)) {
      return false;
    }
    int connectedVertex = 0;

    for (Task t : vmTasks) {
      VirtualMachineTask c = (VirtualMachineTask) t;
      if (c.getData().getName().equals(srcNameInVertex)) {
        connectedVertex++;
        if (!(graph.containsEdge(t, v))) {
          return false;
        }
      }
    }
    return (connectedVertex == getInEdges(v, graph));

  }

  private static boolean containsConnectionWithApplication(Task v,
                                                           SimpleDirectedGraph<Task, DefaultEdge> graph,
                                                           Set<Task> tasks,
                                                           Set<Task> appTask) {
    if (!tasks.contains(v)) {
      return false;
    }
    int connectedVertex = 1;

    for (Task t : appTask) {
      if (t != null) {
        if (!(graph.containsEdge(t, v))) {
          return false;
        }
      }
    }
    return (connectedVertex == getInEdges(v, graph));

  }

  private static boolean containsConnectionsWithApplicationComponent(
          Task v, String acName,
          SimpleDirectedGraph<Task, DefaultEdge> graph,
          Set<Task> tasks, Set<Task> appCompTasks) {

    if (!tasks.contains(v)) {
      return false;
    }
    int connectedVertex = 0;

    for (Task t : appCompTasks) {
      ApplicationComponentTask c = (ApplicationComponentTask) t;
      if (c.getData().getName().equals(acName)) {
        connectedVertex++;
        if (!(graph.containsEdge(t, v))) {
          return false;
        }
      }
    }
    return (connectedVertex == getInEdges(v, graph));
  }

  private static boolean containsConnectionsWithVmInstanceTasks(
          Task v, String vmInstName,
          SimpleDirectedGraph<Task, DefaultEdge> graph,
          Set<Task> tasks, Set<Task> vmInstanceTasks) {

    if (!tasks.contains(v)) {
      return false;
    }
    int connectedVertex = 0;

    for (Task t : vmInstanceTasks) {
      VirtualMachineInstanceTask c = (VirtualMachineInstanceTask) t;
      if (c.getData().getName().equals(vmInstName)) {
        connectedVertex++;
        if (!(graph.containsEdge(t, v))) {
          return false;
        }
      }
    }
    return (connectedVertex == getInEdges(v, graph));

  }


  private static boolean containsConnectionsWithAppComponentTasks(
          Task v, String acInstName,
          SimpleDirectedGraph<Task, DefaultEdge> graph,
          Set<Task> tasks, Set<Task> appComponentTasks) {
    if (!tasks.contains(v)) {
      return false;
    }
    int connectedVertex = 0;

    for (Task t : appComponentTasks) {
      ApplicationComponentInstanceTask c = (ApplicationComponentInstanceTask) t;
      if (c.getData().getName().equals(acInstName)) {
        connectedVertex++;
        if (!(graph.containsEdge(t, v))) {
          return false;
        }
      }
    }
    return (connectedVertex == getInEdges(v, graph));

  }


  private static boolean containsConnectionsCommunication(
          CommunicationTask v,
          SimpleDirectedGraph<Task, DefaultEdge> graph,
          Set<Task> tasks,
          Set<Task> portProvTasks, Set<Task> portReqTasks) {

    if (!tasks.contains(v)) {
      return false;
    }
    int connectedVertex = 0;

    for (Task t : portProvTasks) {
      PortProvidedTask c = (PortProvidedTask) t;
      if (c.getData().getName().equals(v.getData().getPortProvName())) {
        connectedVertex++;
        if (!(graph.containsEdge(t, v))) {
          return false;
        }
      }
    }
    for (Task t : portReqTasks) {
      PortRequiredTask c = (PortRequiredTask) t;
      if (c.getData().getName().equals(v.getData().getPortReqName())) {
        connectedVertex++;
        if (!(graph.containsEdge(t, v))) {
          return false;
        }
      }
    }
    return (connectedVertex == getInEdges(v, graph));
  }

  private static boolean containsConnectionsApplicationComponent(
          ApplicationComponentTask v,
          SimpleDirectedGraph<Task, DefaultEdge> graph,
          Set<Task> tasks, Set<Task> appTasks,
          Set<Task> lcTasks, Set<Task> vmTasks) {

    if (!tasks.contains(v)) {
      return false;
    }
    int connectedVertex = 0;

    for (Task t : appTasks) {
      if (t != null) {
        connectedVertex++;
        if (!(graph.containsEdge(t, v))) {
          return false;
        }
      }
    }
    for (Task t : lcTasks) {
      LifecycleComponentTask c = (LifecycleComponentTask) t;
      if (c.getData().getName().equals(v.getData().getLcName())) {
        connectedVertex++;
        if (!(graph.containsEdge(t, v))) {
          return false;
        }
      }
    }
    for (Task t : vmTasks) {
      VirtualMachineTask c = (VirtualMachineTask) t;
      if (c.getData().getName().equals(v.getData().getVmName())) {
        connectedVertex++;
        if (!(graph.containsEdge(t, v))) {
          return false;
        }
      }
    }
    return (connectedVertex == getInEdges(v, graph));

  }


  private static boolean containsConnectionsApplicationComponentInstance(
          ApplicationComponentInstanceTask v,
          SimpleDirectedGraph<Task, DefaultEdge> graph,
          Set<Task> tasks, Set<Task> appInstanceTasks,
          Set<Task> vmInstanceTasks, Set<Task> appComponentTasks) {

    if (!tasks.contains(v)) {
      return false;
    }
    int connectedVertex = 0;

    for (Task t : appInstanceTasks) {
      if (t != null) {
        connectedVertex++;
        if (!(graph.containsEdge(t, v))) {
          return false;
        }
      }
    }
    for (Task t : vmInstanceTasks) {
      VirtualMachineInstanceTask c = (VirtualMachineInstanceTask) t;
      if (c.getData().getName().equals(v.getData().getVmInstName())) {
        connectedVertex++;
        if (!(graph.containsEdge(t, v))) {
          return false;
        }
      }
    }
    for (Task t : appComponentTasks) {
      ApplicationComponentTask c = (ApplicationComponentTask) t;
      if (c.getData().getName().equals(v.getData().getAcName())) {
        connectedVertex++;
        if (!(graph.containsEdge(t, v))) {
          return false;
        }
      }
    }
    return (connectedVertex == getInEdges(v, graph));
  }

  private static int getInEdges(Task v, SimpleDirectedGraph<Task, DefaultEdge> graph) {
    int inEdgesCounter = 0;

    for (DefaultEdge e : graph.edgesOf(v)) {
      if (graph.getEdgeTarget(e).equals(v)) {
        inEdgesCounter++;
      }
    }
    return inEdgesCounter;
  }


  private static Set<Task> filterTasks(Set<Task> tasks, Predicate<Task> predicate) {
    return tasks.stream().filter(predicate).collect(Collectors.toSet());

  }


  static class Holder {

    int connectedVertex = 0;

    void increment() {
      connectedVertex++;
    }

    int get() {
      return connectedVertex;
    }
  }
}
