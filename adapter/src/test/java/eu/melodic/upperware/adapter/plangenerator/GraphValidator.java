/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.plangenerator;

import eu.melodic.upperware.adapter.plangenerator.model.LifecycleComponent;
import eu.melodic.upperware.adapter.plangenerator.tasks.*;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GraphValidator {

  public enum TASK_TYPE {
    CLOUD_API, CLOUD, CLOUD_PROPERTY, CLOUD_CREDENTIAL, APPLICATION, APPLICATION_INSTANCE,
    VIRTUALMACHINE, VIRTUALMACHINE_INSTANCE, LIFECYCLE, APPLICATION_COMPONENT, APPLICATION_COMPONENT_INSTANCE,
    PORT_PROVIDED, PORT_REQUIRED, COMMUNICATION, VM_INSTANCE_MONITOR, APP_COMP_INSTANCE_MONITOR
  }

  private static boolean checkVertex(Task v, SimpleDirectedGraph<Task, DefaultEdge> graph,
                                     Map<TASK_TYPE, Set<Task>> tasks) {
    if (v instanceof CloudApiTask) {
      return containsOnlyOutEdges(v, graph, tasks.get(TASK_TYPE.CLOUD_API));
    }

    if (v instanceof CloudTask) {
      return checkCloudVertex((CloudTask) v, graph,
              tasks.get(TASK_TYPE.CLOUD), tasks.get(TASK_TYPE.CLOUD_API));
    }
    if (v instanceof CloudPropertyTask) {
      return checkCloudPropertiesVertex((CloudPropertyTask) v, graph,
              tasks.get(TASK_TYPE.CLOUD_PROPERTY), tasks.get(TASK_TYPE.CLOUD));
    }
    if (v instanceof CloudCredentialTask) {
      return checkCloudCredentialVertex((CloudCredentialTask) v, graph,
              tasks.get(TASK_TYPE.CLOUD_CREDENTIAL), tasks.get(TASK_TYPE.CLOUD));
    }
    if (v instanceof ApplicationTask) {
      return containsOnlyOutEdges(v, graph, tasks.get(TASK_TYPE.APPLICATION));
    }
    if (v instanceof ApplicationInstanceTask) {
      return checkApplicationInstanceVertex((ApplicationInstanceTask) v, graph,
              tasks.get(TASK_TYPE.APPLICATION_INSTANCE), tasks.get(TASK_TYPE.APPLICATION));
    }
    if (v instanceof LifecycleComponentTask) {
      return containsOnlyOutEdges(v, graph, tasks.get(TASK_TYPE.LIFECYCLE));
    }
    if (v instanceof VirtualMachineTask) {
      return checkVirtualMachineVertex((VirtualMachineTask) v, graph,
              tasks.get(TASK_TYPE.VIRTUALMACHINE), tasks.get(TASK_TYPE.CLOUD));
    }
    if (v instanceof VirtualMachineInstanceTask) {
      return checkVirtualMachineInstanceVertex((VirtualMachineInstanceTask) v, graph,
              tasks.get(TASK_TYPE.VIRTUALMACHINE_INSTANCE), tasks.get(TASK_TYPE.VIRTUALMACHINE));
    }


    return true;
  }

  private static boolean containsOnlyOutEdges(Task v,
                                              SimpleDirectedGraph<Task, DefaultEdge> graph,
                                              Set<Task> tasks) {
    return tasks.contains(v) && (getInEdges(v, graph).size() == 0);
  }


  private static boolean checkCloudVertex(CloudTask v, SimpleDirectedGraph<Task, DefaultEdge> graph,
                                          Set<Task> tasks, Set<Task> cloudApiTasks) {

    if (!tasks.contains(v)) {
      return false;
    }

    Set<DefaultEdge> inEdges = getInEdges(v, graph);
    int connectedVertex = cloudApiTasks.size();
    if (connectedVertex != inEdges.size()) {
      return false;
    }
    for (Task t : cloudApiTasks) {
      CloudApiTask c = (CloudApiTask) t;
      if (!(c.getData().getName().equals(v.getData().getApiName()))
              || ((!(graph.containsEdge(t, v))))) {
        return false;
      }
    }
    return true;

  }

  private static boolean checkCloudPropertiesVertex(CloudPropertyTask v,
                                                    SimpleDirectedGraph<Task, DefaultEdge> graph,
                                                    Set<Task> tasks, Set<Task> cloudTasks) {
    if (!tasks.contains(v)) {
      return false;
    }
    if (cloudTasks.size() != getInEdges(v, graph).size()) {
      return false;
    }

    for (Task t : cloudTasks) {
      if (!(graph.containsEdge(t, v))) {
        return false;
      }
    }
    return true;
  }


  private static boolean checkCloudCredentialVertex(CloudCredentialTask v,
                                                    SimpleDirectedGraph<Task, DefaultEdge> graph,
                                                    Set<Task> tasks, Set<Task> cloudTasks) {
    if (!tasks.contains(v)) {
      return false;
    }
    if (cloudTasks.size() != getInEdges(v, graph).size()) {
      return false;
    }

    for (Task t : cloudTasks) {
      if (!(graph.containsEdge(t, v))) {
        return false;
      }
    }

    return true;
  }

  private static boolean checkApplicationInstanceVertex(ApplicationInstanceTask v,
                                                        SimpleDirectedGraph<Task, DefaultEdge> graph,
                                                        Set<Task> tasks, Set<Task> appTasks) {
    if (!tasks.contains(v)) {
      return false;
    }
    if (appTasks.size() != getInEdges(v, graph).size()) {
      return false;
    }

    for (Task t : appTasks) {
      if (!(graph.containsEdge(t, v))) {
        return false;
      }
    }

    return true;
  }

  private static boolean checkVirtualMachineVertex(VirtualMachineTask v,
                                                   SimpleDirectedGraph<Task, DefaultEdge> graph,
                                                   Set<Task> tasks, Set<Task> cloudsTasks) {

    if (!tasks.contains(v)) {
      return false;
    }
    if (cloudsTasks.size() != getInEdges(v, graph).size()) {
      return false;
    }

    for (Task t : cloudsTasks) {
      if (!(graph.containsEdge(t, v))) {
        return false;
      }
    }

    return true;

  }

  private static boolean checkVirtualMachineInstanceVertex(VirtualMachineInstanceTask v,
                                                           SimpleDirectedGraph<Task, DefaultEdge> graph,
                                                           Set<Task> tasks, Set<Task> vmTasks) {
    return true;

  }

  private static Set<DefaultEdge> getInEdges(Task v, SimpleDirectedGraph<Task, DefaultEdge> graph) {
    Set<DefaultEdge> inEdges = new HashSet<>();

    for (DefaultEdge e : graph.edgesOf(v)) {
      if (graph.getEdgeTarget(e).equals(v)) {
        inEdges.add(e);
      }
    }
    return inEdges;
  }

}
