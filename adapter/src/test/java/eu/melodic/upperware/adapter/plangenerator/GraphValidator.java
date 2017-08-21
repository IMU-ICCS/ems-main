/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.plangenerator;

import eu.melodic.upperware.adapter.plangenerator.tasks.*;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static eu.melodic.upperware.adapter.plangenerator.TaskType.*;

public class GraphValidator {


  static boolean checkVertex(Task v, SimpleDirectedGraph<Task, DefaultEdge> graph,
                             Map<TaskType, Set<Task>> tasks,
                             Map<TaskType, Set<TaskType>> dependencies) {
    Map<TaskType, Predicate<Task>> predicateMap = new HashMap<>();

    if (v instanceof CloudApiTask) {
      return containsOnlyOutEdges(v, graph, tasks.get(CLOUD_API));
    }

    if (v instanceof CloudTask) {
      predicateMap.put(CLOUD_API, task -> ((CloudApiTask) task).getData().getName()
              .equals(((CloudTask) v).getData().getApiName()));

      return checkConnections(v, CLOUD, graph, tasks, dependencies, predicateMap);
    }

    if (v instanceof CloudPropertyTask) {
      predicateMap.put(CLOUD, task -> (
              (CloudTask) task).getData().getName()
              .equals(((CloudPropertyTask) v).getData().getCloudName()));

      return checkConnections(v, CLOUD_PROPERTY, graph, tasks, dependencies, predicateMap);
    }

    if (v instanceof CloudCredentialTask) {
      predicateMap.put(CLOUD, task -> (
              (CloudTask) task).getData().getName()
              .equals(((CloudCredentialTask) v).getData().getCloudName()));

      return checkConnections(v, CLOUD_CREDENTIAL, graph, tasks, dependencies, predicateMap);
    }

    if (v instanceof ApplicationTask) {
      return containsOnlyOutEdges(v, graph, tasks.get(APPLICATION));
    }

    if (v instanceof ApplicationInstanceTask) {
      predicateMap.put(APPLICATION, task -> (task != null));

      return checkConnections(v, APPLICATION_INSTANCE, graph, tasks, dependencies, predicateMap);
    }

    if (v instanceof LifecycleComponentTask) {
      return containsOnlyOutEdges(v, graph, tasks.get(LIFECYCLE));
    }

    if (v instanceof VirtualMachineTask) {
      predicateMap.put(CLOUD, task -> (
              (CloudTask) task).getData().getName()
              .equals(((VirtualMachineTask) v).getData().getCloudName()));

      return checkConnections(v, VIRTUALMACHINE, graph, tasks, dependencies, predicateMap);
    }

    if (v instanceof VirtualMachineInstanceTask) {
      predicateMap.put(VIRTUALMACHINE, task -> (
              (VirtualMachineTask) task).getData().getName()
              .equals(((VirtualMachineInstanceTask) v).getData().getVmName()));

      return checkConnections(v, VIRTUALMACHINE_INSTANCE, graph, tasks, dependencies, predicateMap);
    }

    if (v instanceof ApplicationComponentTask) {
      predicateMap.put(APPLICATION, task -> ((ApplicationTask) task).getData().getName()
              .equals(((ApplicationComponentTask) v).getData().getAppName()));
      predicateMap.put(LIFECYCLE, task -> ((LifecycleComponentTask) task).getData().getName()
              .equals(((ApplicationComponentTask) v).getData().getLcName()));
      predicateMap.put(VIRTUALMACHINE, task -> ((VirtualMachineTask) task).getData().getName()
              .equals(((ApplicationComponentTask) v).getData().getVmName()));

      return checkConnections(v, APPLICATION_COMPONENT, graph, tasks, dependencies, predicateMap);
    }

    if (v instanceof ApplicationComponentInstanceTask) {
      predicateMap.put(APPLICATION_INSTANCE, task -> (task != null));
      predicateMap.put(APPLICATION_COMPONENT, task -> ((ApplicationComponentTask) task).getData().getName()
              .equals(((ApplicationComponentInstanceTask) v).getData().getAcName()));
      predicateMap.put(VIRTUALMACHINE_INSTANCE, task -> ((VirtualMachineInstanceTask) task).getData().getName()
              .equals(((ApplicationComponentInstanceTask) v).getData().getVmInstName()));

      return checkConnections(v, APPLICATION_COMPONENT_INSTANCE, graph, tasks, dependencies, predicateMap);
    }

    if (v instanceof CommunicationTask) {
      predicateMap.put(PORT_PROVIDED, task -> ((PortProvidedTask) task).getData().getName()
              .equals(((CommunicationTask) v).getData().getPortProvName()));
      predicateMap.put(PORT_REQUIRED, task -> ((PortRequiredTask) task).getData().getName()
              .equals(((CommunicationTask) v).getData().getPortReqName()));

      return checkConnections(v, COMMUNICATION, graph, tasks, dependencies, predicateMap);
    }

    if (v instanceof PortProvidedTask) {
      predicateMap.put(APPLICATION_COMPONENT, task -> (
              (ApplicationComponentTask) task).getData().getName()
              .equals(((PortProvidedTask) v).getData().getAcName()));

      return checkConnections(v, PORT_PROVIDED, graph, tasks, dependencies, predicateMap);
    }

    if (v instanceof PortRequiredTask) {
      predicateMap.put(APPLICATION_COMPONENT, task -> (
              (ApplicationComponentTask) task).getData().getName()
              .equals(((PortRequiredTask) v).getData().getAcName()));

      return checkConnections(v, PORT_REQUIRED, graph, tasks, dependencies, predicateMap);
    }

    if (v instanceof VirtualMachineInstanceMonitorTask) {
      predicateMap.put(VIRTUALMACHINE_INSTANCE, task -> (
              (VirtualMachineInstanceTask) task).getData().getName()
              .equals(((VirtualMachineInstanceMonitorTask) v).getData().getVmInstName()));

      return checkConnections(v, VM_INSTANCE_MONITOR, graph, tasks, dependencies, predicateMap);
    }

    if (v instanceof ApplicationComponentInstanceMonitorTask) {
      predicateMap.put(APPLICATION_COMPONENT_INSTANCE, task -> (
              (ApplicationComponentInstanceTask) task).getData().getName()
              .equals(((ApplicationComponentInstanceMonitorTask) v).getData().getAcInstName()));

      return checkConnections(v, APP_COMP_INSTANCE_MONITOR, graph, tasks, dependencies, predicateMap);
    }


    return true;
  }

  private static boolean containsOnlyOutEdges(Task v,
                                              SimpleDirectedGraph<Task, DefaultEdge> graph,
                                              Set<Task> tasks) {
    return tasks.contains(v) && (getInEdges(v, graph) == 0);
  }


  private static boolean checkConnections(Task v, TaskType t,
                                          SimpleDirectedGraph<Task, DefaultEdge> graph,
                                          Map<TaskType, Set<Task>> tasksMap,
                                          Map<TaskType, Set<TaskType>> dependencies,
                                          Map<TaskType, Predicate<Task>> preds) {

    Set<Task> tasks = tasksMap.get(t);

    assert (tasks.contains(v));

    Holder holder = new Holder();


    for (TaskType connectedType : dependencies.get(t)) {
      Set<Task> filteredTask = filterTasks(tasksMap.get(connectedType), preds.get(connectedType));
      boolean b = filteredTask.stream()
              .peek(task -> holder.increment())
              .allMatch(task -> (graph.containsEdge(task, v)));
      assert (b);
    }

    return holder.get() == getInEdges(v, graph);

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

  private static Set<TaskType> addToSet(TaskType t) {
    Set<TaskType> set = new HashSet<>();
    set.add(t);
    return set;
  }


  private static Set<TaskType> addToSet(TaskType t1, TaskType t2) {
    Set<TaskType> set = new HashSet<>();
    set.add(t1);
    set.add(t2);
    return set;
  }

  private static Set<TaskType> addToSet(TaskType t1, TaskType t2, TaskType t3) {
    Set<TaskType> set = new HashSet<>();
    set.add(t1);
    set.add(t2);
    set.add(t3);
    return set;
  }


  public static Map<TaskType, Set<Task>> initMap() {
    Map<TaskType, Set<Task>> tasks = new HashMap<>();
    tasks.put(CLOUD_API, new HashSet<>());
    tasks.put(CLOUD, new HashSet<>());
    tasks.put(CLOUD_PROPERTY, new HashSet<>());
    tasks.put(CLOUD_CREDENTIAL, new HashSet<>());
    tasks.put(APPLICATION, new HashSet<>());
    tasks.put(APPLICATION_INSTANCE, new HashSet<>());
    tasks.put(LIFECYCLE, new HashSet<>());
    tasks.put(VIRTUALMACHINE, new HashSet<>());
    tasks.put(VIRTUALMACHINE_INSTANCE, new HashSet<>());
    tasks.put(APPLICATION_COMPONENT, new HashSet<>());
    tasks.put(APPLICATION_COMPONENT_INSTANCE, new HashSet<>());
    tasks.put(COMMUNICATION, new HashSet<>());
    tasks.put(PORT_PROVIDED, new HashSet<>());
    tasks.put(PORT_REQUIRED, new HashSet<>());
    tasks.put(VM_INSTANCE_MONITOR, new HashSet<>());
    tasks.put(APP_COMP_INSTANCE_MONITOR, new HashSet<>());
    return tasks;
  }


  public static Map<TaskType, Set<TaskType>> createDependencies() {
    Map<TaskType, Set<TaskType>> dependencies = new HashMap<>();
    Set<TaskType> emptySet = new HashSet<>();

    dependencies.put(CLOUD_API, emptySet);
    dependencies.put(CLOUD, addToSet(CLOUD_API));
    dependencies.put(CLOUD_PROPERTY, addToSet(CLOUD));
    dependencies.put(CLOUD_CREDENTIAL, addToSet(CLOUD));
    dependencies.put(APPLICATION, emptySet);
    dependencies.put(APPLICATION_INSTANCE, addToSet(APPLICATION));
    dependencies.put(LIFECYCLE, emptySet);
    dependencies.put(VIRTUALMACHINE, addToSet(CLOUD));
    dependencies.put(VIRTUALMACHINE_INSTANCE, addToSet(VIRTUALMACHINE));
    dependencies.put(APPLICATION_COMPONENT, addToSet(
            APPLICATION, LIFECYCLE, VIRTUALMACHINE));

    dependencies.put(APPLICATION_COMPONENT_INSTANCE, addToSet(
            APPLICATION_INSTANCE, VIRTUALMACHINE_INSTANCE, APPLICATION_COMPONENT));

    dependencies.put(COMMUNICATION, addToSet(PORT_PROVIDED, PORT_REQUIRED));

    dependencies.put(PORT_PROVIDED, addToSet(APPLICATION_COMPONENT));
    dependencies.put(PORT_REQUIRED, addToSet(APPLICATION_COMPONENT));

    dependencies.put(VM_INSTANCE_MONITOR, addToSet(
            VIRTUALMACHINE_INSTANCE));

    dependencies.put(APP_COMP_INSTANCE_MONITOR, addToSet(
            APPLICATION_COMPONENT_INSTANCE));

    return dependencies;
  }


}
