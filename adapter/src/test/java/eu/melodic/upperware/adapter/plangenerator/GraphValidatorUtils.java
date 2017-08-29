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

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static eu.melodic.upperware.adapter.plangenerator.TaskType.*;

public class GraphValidatorUtils {

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


  static class Holder {

    int connectedVertex = 0;

    void increment() {
      connectedVertex++;
    }

    int get() {
      return connectedVertex;
    }
  }

  static int countAndCheckConnections(Task v, TaskType t,
                                      SimpleDirectedGraph<Task, DefaultEdge> graph,
                                      Map<TaskType, Set<Task>> tasksMap,
                                      Map<TaskType, Set<TaskType>> dependencies,
                                      Map<TaskType, Predicate<Task>> preds) {
    Set<Task> tasks = tasksMap.get(t);

    assert (tasks.contains(v));

    GraphValidatorUtils.Holder holder = new GraphValidatorUtils.Holder();

    for (TaskType connectedType : dependencies.get(t)) {
      Set<Task> filteredTask = filterTasks(tasksMap.get(connectedType), preds.get(connectedType));
      boolean b = filteredTask.stream()
              .peek(task -> holder.increment())
              .allMatch(task -> (graph.containsEdge(task, v)));
      assert (b);
    }
    return holder.get();
  }


  private static Set<Task> filterTasks(Set<Task> tasks, Predicate<Task> predicate) {
    return tasks.stream().filter(predicate).collect(Collectors.toSet());
  }

  static void addDeleteTasks(POJOCreatorExample c,
                        Map<TaskType, Set<Task>> newTasks, Map<TaskType, Set<Task>> oldTasks) {
    c.addTasksToDelete(TaskType.CLOUD_PROPERTY, newTasks, oldTasks);
    c.addTasksToDelete(LIFECYCLE, newTasks, oldTasks);
    c.addTasksToDelete(VIRTUALMACHINE, newTasks, oldTasks);
    c.addTasksToDelete(VIRTUALMACHINE_INSTANCE, newTasks, oldTasks);
    c.addTasksToDelete(APPLICATION_COMPONENT, newTasks, oldTasks);
    c.addTasksToDelete(APPLICATION_COMPONENT_INSTANCE, newTasks, oldTasks);
    c.addTasksToDelete(PORT_PROVIDED, newTasks, oldTasks);
    c.addTasksToDelete(PORT_REQUIRED, newTasks, oldTasks);
    c.addTasksToDelete(COMMUNICATION, newTasks, oldTasks);
  }

  static Set<TaskType> addToSet(TaskType t) {
    Set<TaskType> set = new HashSet<>();
    set.add(t);
    return set;
  }

  static Set<TaskType> addToSet(TaskType t1, TaskType t2) {
    Set<TaskType> set = new HashSet<>();
    set.add(t1);
    set.add(t2);
    return set;
  }

  static Set<TaskType> addToSet(TaskType t1, TaskType t2, TaskType t3) {
    Set<TaskType> set = new HashSet<>();
    set.add(t1);
    set.add(t2);
    set.add(t3);
    return set;
  }

  static int getInEdges(Task v, SimpleDirectedGraph<Task, DefaultEdge> graph) {
    int inEdgesCounter = 0;

    for (DefaultEdge e : graph.edgesOf(v)) {
      if (graph.getEdgeTarget(e).equals(v)) {
        inEdgesCounter++;
      }
    }
    return inEdgesCounter;
  }

  static void setPredicateMap(Map<TaskType, Predicate<Task>> predicateMap, Task v, boolean delete) {
    if (delete) {
      if (v instanceof CloudApiTask) {
        predicateMap.put(CLOUD_API, task -> ((CloudTask) task).getData().getApiName()
                .equals(((CloudApiTask) v).getData().getName()));
      }

      if (v instanceof CloudTask) {
        predicateMap.put(CLOUD_PROPERTY, task -> ((CloudPropertyTask) task).getData().getCloudName()
                .equals(((CloudTask) v).getData().getName()));
        predicateMap.put(CLOUD_CREDENTIAL, task -> ((CloudCredentialTask) task).getData().getCloudName()
                .equals(((CloudTask) v).getData().getName()));
        predicateMap.put(VIRTUALMACHINE, task -> ((VirtualMachineTask) task).getData().getCloudName()
                .equals(((CloudTask) v).getData().getName()));
      }
      if (v instanceof ApplicationTask) {
        predicateMap.put(APPLICATION_INSTANCE, task -> (
                (ApplicationInstanceTask) task).getData().getAppName()
                .equals(((ApplicationTask) v).getData().getName()));
        predicateMap.put(APPLICATION_COMPONENT, task -> (
                (ApplicationComponentTask) task).getData().getAppName()
                .equals(((ApplicationTask) v).getData().getName()));
      }

      if (v instanceof VirtualMachineTask) {
        predicateMap.put(VIRTUALMACHINE_INSTANCE, task -> ((VirtualMachineInstanceTask) task).getData().getVmName()
                .equals(((VirtualMachineTask) v).getData().getName()));
        predicateMap.put(APPLICATION_COMPONENT, task -> ((ApplicationComponentTask) task).getData().getVmName()
                .equals(((VirtualMachineTask) v).getData().getName()));
      }

      if (v instanceof VirtualMachineInstanceTask) {
        predicateMap.put(APPLICATION_COMPONENT_INSTANCE, task -> (
                (ApplicationComponentInstanceTask) task).getData().getVmInstName()
                .equals(((VirtualMachineInstanceTask) v).getData().getName()));
        predicateMap.put(VM_INSTANCE_MONITOR, task -> (
                (VirtualMachineInstanceMonitorTask) task).getData().getVmInstName()
                .equals(((VirtualMachineInstanceTask) v).getData().getName()));
      }

      if (v instanceof LifecycleComponentTask) {
        predicateMap.put(APPLICATION_COMPONENT, task -> (
                (ApplicationComponentTask) task).getData().getLcName()
                .equals(((LifecycleComponentTask) v).getData().getName()));
      }

      if (v instanceof ApplicationComponentTask) {
        predicateMap.put(PORT_PROVIDED, task -> (
                (PortProvidedTask) task).getData().getAcName()
                .equals(((ApplicationComponentTask) v).getData().getName()));
        predicateMap.put(PORT_REQUIRED, task -> (
                (PortRequiredTask) task).getData().getAcName()
                .equals(((ApplicationComponentTask) v).getData().getName()));
        predicateMap.put(APPLICATION_COMPONENT_INSTANCE, task -> (
                (ApplicationComponentInstanceTask) task).getData().getAcName()
                .equals(((ApplicationComponentTask) v).getData().getName()));
      }

      if (v instanceof ApplicationComponentInstanceTask) {
        predicateMap.put(APP_COMP_INSTANCE_MONITOR, task -> (
                (ApplicationComponentInstanceMonitorTask) task).getData().getAcInstName()
                .equals(((ApplicationComponentInstanceTask) v).getData().getName()));
      }

      if (v instanceof PortProvidedTask) {
        predicateMap.put(COMMUNICATION, task -> (
                (CommunicationTask) task).getData().getPortProvName()
                .equals(((PortProvidedTask) v).getData().getName()));

      }
      if (v instanceof PortRequiredTask) {
        predicateMap.put(COMMUNICATION, task -> (
                (CommunicationTask) task).getData().getPortReqName()
                .equals(((PortRequiredTask) v).getData().getName()));
      }
    } else {
      if (v instanceof CloudTask) {
        predicateMap.put(CLOUD_API, task -> ((CloudApiTask) task).getData().getName()
                .equals(((CloudTask) v).getData().getApiName()));
      }
      if (v instanceof CloudPropertyTask) {
        predicateMap.put(CLOUD, task -> (
                (CloudTask) task).getData().getName()
                .equals(((CloudPropertyTask) v).getData().getCloudName()));
      }
      if (v instanceof CloudCredentialTask) {
        predicateMap.put(CLOUD, task -> (
                (CloudTask) task).getData().getName()
                .equals(((CloudCredentialTask) v).getData().getCloudName()));
      }
      if (v instanceof ApplicationInstanceTask) {
        predicateMap.put(APPLICATION, task -> (task != null));
      }
      if (v instanceof VirtualMachineTask) {
        predicateMap.put(CLOUD, task -> (
                (CloudTask) task).getData().getName()
                .equals(((VirtualMachineTask) v).getData().getCloudName()));
      }
      if (v instanceof VirtualMachineInstanceTask) {
        predicateMap.put(VIRTUALMACHINE, task -> (
                (VirtualMachineTask) task).getData().getName()
                .equals(((VirtualMachineInstanceTask) v).getData().getVmName()));
      }
      if (v instanceof ApplicationComponentTask) {
        predicateMap.put(APPLICATION, task -> ((ApplicationTask) task).getData().getName()
                .equals(((ApplicationComponentTask) v).getData().getAppName()));
        predicateMap.put(LIFECYCLE, task -> ((LifecycleComponentTask) task).getData().getName()
                .equals(((ApplicationComponentTask) v).getData().getLcName()));
        predicateMap.put(VIRTUALMACHINE, task -> ((VirtualMachineTask) task).getData().getName()
                .equals(((ApplicationComponentTask) v).getData().getVmName()));
      }
      if (v instanceof ApplicationComponentInstanceTask) {
        predicateMap.put(APPLICATION_INSTANCE, task -> (task != null));
        predicateMap.put(APPLICATION_COMPONENT, task -> ((ApplicationComponentTask) task).getData().getName()
                .equals(((ApplicationComponentInstanceTask) v).getData().getAcName()));
        predicateMap.put(VIRTUALMACHINE_INSTANCE, task -> ((VirtualMachineInstanceTask) task).getData().getName()
                .equals(((ApplicationComponentInstanceTask) v).getData().getVmInstName()));
      }
      if (v instanceof CommunicationTask) {
        predicateMap.put(PORT_PROVIDED, task -> ((PortProvidedTask) task).getData().getName()
                .equals(((CommunicationTask) v).getData().getPortProvName()));
        predicateMap.put(PORT_REQUIRED, task -> ((PortRequiredTask) task).getData().getName()
                .equals(((CommunicationTask) v).getData().getPortReqName()));
      }
      if (v instanceof PortProvidedTask) {
        predicateMap.put(APPLICATION_COMPONENT, task -> (
                (ApplicationComponentTask) task).getData().getName()
                .equals(((PortProvidedTask) v).getData().getAcName()));
      }
      if (v instanceof PortRequiredTask) {
        predicateMap.put(APPLICATION_COMPONENT, task -> (
                (ApplicationComponentTask) task).getData().getName()
                .equals(((PortRequiredTask) v).getData().getAcName()));
      }
      if (v instanceof VirtualMachineInstanceMonitorTask) {
        predicateMap.put(VIRTUALMACHINE_INSTANCE, task -> (
                (VirtualMachineInstanceTask) task).getData().getName()
                .equals(((VirtualMachineInstanceMonitorTask) v).getData().getVmInstName()));
      }
      if (v instanceof ApplicationComponentInstanceMonitorTask) {
        predicateMap.put(APPLICATION_COMPONENT_INSTANCE, task -> (
                (ApplicationComponentInstanceTask) task).getData().getName()
                .equals(((ApplicationComponentInstanceMonitorTask) v).getData().getAcInstName()));
      }
    }
  }

}
