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
                                     Map<TASK_TYPE, Set<Task>> tasks,
                                     Map<TASK_TYPE, Set<TASK_TYPE>> dependencies) {
    Map<TASK_TYPE, Predicate<Task>> predicateMap = new HashMap<>();

    if (v instanceof CloudApiTask) {
      return containsOnlyOutEdges(v, graph, tasks.get(TASK_TYPE.CLOUD_API));
    }

    if (v instanceof CloudTask) {
      predicateMap.put(TASK_TYPE.CLOUD_API, task -> ((CloudApiTask) task).getData().getName()
              .equals(((CloudTask) v).getData().getApiName()));

      return checkConnections(v, TASK_TYPE.CLOUD, graph, tasks, dependencies, predicateMap);
    }

    if (v instanceof CloudPropertyTask) {
      predicateMap.put(TASK_TYPE.CLOUD, task -> (
              (CloudTask) task).getData().getName()
              .equals(((CloudPropertyTask) v).getData().getCloudName()));

      return checkConnections(v, TASK_TYPE.CLOUD_PROPERTY, graph, tasks, dependencies, predicateMap);
    }

    if (v instanceof CloudCredentialTask) {
      predicateMap.put(TASK_TYPE.CLOUD, task -> (
              (CloudTask) task).getData().getName()
              .equals(((CloudCredentialTask) v).getData().getCloudName()));

      return checkConnections(v, TASK_TYPE.CLOUD_CREDENTIAL, graph, tasks, dependencies, predicateMap);
    }

    if (v instanceof ApplicationTask) {
      return containsOnlyOutEdges(v, graph, tasks.get(TASK_TYPE.APPLICATION));
    }

    if (v instanceof ApplicationInstanceTask) {
      predicateMap.put(TASK_TYPE.APPLICATION, task -> (task!=null));

      return checkConnections(v, TASK_TYPE.APPLICATION_INSTANCE, graph, tasks, dependencies, predicateMap);
    }

    if (v instanceof LifecycleComponentTask) {
      return containsOnlyOutEdges(v, graph, tasks.get(TASK_TYPE.LIFECYCLE));
    }

    if (v instanceof VirtualMachineTask) {
      predicateMap.put(TASK_TYPE.CLOUD, task -> (
              (CloudTask) task).getData().getName()
              .equals(((VirtualMachineTask) v).getData().getCloudName()));

      return checkConnections(v, TASK_TYPE.VIRTUALMACHINE, graph, tasks, dependencies, predicateMap);
    }

    if (v instanceof VirtualMachineInstanceTask) {
      predicateMap.put(TASK_TYPE.VIRTUALMACHINE, task -> (
              (VirtualMachineTask) task).getData().getName()
              .equals(((VirtualMachineInstanceTask) v).getData().getVmName()));

      return checkConnections(v, TASK_TYPE.VIRTUALMACHINE_INSTANCE, graph, tasks, dependencies, predicateMap);
    }

    if (v instanceof ApplicationComponentTask) {
      predicateMap.put(TASK_TYPE.APPLICATION, task -> ((ApplicationTask) task).getData().getName()
              .equals(((ApplicationComponentTask) v).getData().getAppName()));
      predicateMap.put(TASK_TYPE.LIFECYCLE, task -> ((LifecycleComponentTask) task).getData().getName()
              .equals(((ApplicationComponentTask) v).getData().getLcName()));
      predicateMap.put(TASK_TYPE.VIRTUALMACHINE, task -> ((VirtualMachineTask) task).getData().getName()
              .equals(((ApplicationComponentTask) v).getData().getVmName()));

      return checkConnections(v, TASK_TYPE.APPLICATION_COMPONENT, graph, tasks, dependencies, predicateMap);
    }

    if (v instanceof ApplicationComponentInstanceTask) {
      predicateMap.put(TASK_TYPE.APPLICATION_INSTANCE, task -> (task==null));
      predicateMap.put(TASK_TYPE.APPLICATION_COMPONENT, task -> ((ApplicationComponentTask) task).getData().getName()
              .equals(((ApplicationComponentInstanceTask) v).getData().getAcName()));
      predicateMap.put(TASK_TYPE.VIRTUALMACHINE_INSTANCE, task -> ((VirtualMachineInstanceTask) task).getData().getName()
              .equals(((ApplicationComponentInstanceTask) v).getData().getVmInstName()));

      return checkConnections(v, TASK_TYPE.APPLICATION_COMPONENT_INSTANCE, graph, tasks, dependencies, predicateMap);
    }

    if (v instanceof CommunicationTask) {
      predicateMap.put(TASK_TYPE.PORT_PROVIDED, task -> ((PortProvidedTask) task).getData().getName()
              .equals(((CommunicationTask) v).getData().getPortProvName()));
      predicateMap.put(TASK_TYPE.PORT_REQUIRED, task -> ((PortRequiredTask) task).getData().getName()
              .equals(((CommunicationTask) v).getData().getPortReqName()));

      return checkConnections(v, TASK_TYPE.COMMUNICATION, graph, tasks, dependencies, predicateMap);
    }

    if (v instanceof PortProvidedTask) {
      predicateMap.put(TASK_TYPE.APPLICATION_COMPONENT, task -> (
              (ApplicationComponentTask) task).getData().getName()
              .equals(((PortProvidedTask) v).getData().getAcName()));

      return checkConnections(v, TASK_TYPE.PORT_PROVIDED, graph, tasks, dependencies, predicateMap);
    }

    if (v instanceof PortRequiredTask) {
      predicateMap.put(TASK_TYPE.APPLICATION_COMPONENT, task -> (
              (ApplicationComponentTask) task).getData().getName()
              .equals(((PortRequiredTask) v).getData().getAcName()));

      return checkConnections(v, TASK_TYPE.PORT_REQUIRED, graph, tasks, dependencies, predicateMap);
    }

    if (v instanceof VirtualMachineInstanceMonitorTask) {
      predicateMap.put(TASK_TYPE.VIRTUALMACHINE_INSTANCE, task -> (
              (VirtualMachineInstanceTask) task).getData().getName()
              .equals(((VirtualMachineInstanceMonitorTask) v).getData().getVmInstName()));

      return checkConnections(v, TASK_TYPE.VM_INSTANCE_MONITOR, graph, tasks, dependencies, predicateMap);
    }

    if (v instanceof ApplicationComponentInstanceMonitorTask) {
      predicateMap.put(TASK_TYPE.APPLICATION_COMPONENT_INSTANCE, task -> (
              (ApplicationComponentInstanceTask) task).getData().getName()
              .equals(((ApplicationComponentInstanceMonitorTask) v).getData().getAcInstName()));

      return checkConnections(v, TASK_TYPE.APP_COMP_INSTANCE_MONITOR, graph, tasks, dependencies, predicateMap);
    }


    return true;
  }

  private static boolean containsOnlyOutEdges(Task v,
                                              SimpleDirectedGraph<Task, DefaultEdge> graph,
                                              Set<Task> tasks) {
    return tasks.contains(v) && (getInEdges(v, graph) == 0);
  }



  private static boolean checkConnections(Task v, TASK_TYPE t,
                                          SimpleDirectedGraph<Task, DefaultEdge> graph,
                                          Map<TASK_TYPE, Set<Task>> tasksMap,
                                          Map<TASK_TYPE, Set<TASK_TYPE>> dependencies,
                                          Map <TASK_TYPE, Predicate<Task>> preds){

    Set<Task> tasks = tasksMap.get(t);
    if (!tasks.contains(v)) {
      return false;
    }
    Holder holder = new Holder();


    for (TASK_TYPE connectedType : dependencies.get(t)){
      Set<Task> filteredTask = filterTasks(tasksMap.get(connectedType), preds.get(connectedType));
      boolean b = filteredTask.stream()
              .peek(task -> holder.increment())
              .allMatch(task -> (graph.containsEdge(task, v)));
      assert(b);
//      if (!b){
//        return false;
//      }
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




  private static Set<GraphValidator.TASK_TYPE> addToSet(GraphValidator.TASK_TYPE t){
    Set<GraphValidator.TASK_TYPE> set = new HashSet<>();
    set.add(t);
    return set;
  }


  private static Set<GraphValidator.TASK_TYPE> addToSet(GraphValidator.TASK_TYPE t1, GraphValidator.TASK_TYPE t2){
    Set<GraphValidator.TASK_TYPE> set = new HashSet<>();
    set.add(t1);
    set.add(t2);
    return set;
  }

  private static Set<GraphValidator.TASK_TYPE> addToSet(GraphValidator.TASK_TYPE t1, GraphValidator.TASK_TYPE t2,
                                                        GraphValidator.TASK_TYPE t3){
    Set<GraphValidator.TASK_TYPE> set = new HashSet<>();
    set.add(t1);
    set.add(t2);
    set.add(t3);
    return set;
  }


  public static Map<TASK_TYPE, Set<TASK_TYPE>> createDependencies() {
    Map<TASK_TYPE, Set<TASK_TYPE>> dependencies = new HashMap<>();
    Set<TASK_TYPE> emptySet = new HashSet<>();

    dependencies.put(TASK_TYPE.CLOUD_API, emptySet);
    dependencies.put(TASK_TYPE.CLOUD, addToSet(TASK_TYPE.CLOUD_API));
    dependencies.put(TASK_TYPE.CLOUD_PROPERTY, addToSet(TASK_TYPE.CLOUD));
    dependencies.put(TASK_TYPE.CLOUD_CREDENTIAL, addToSet(TASK_TYPE.CLOUD));
    dependencies.put(TASK_TYPE.APPLICATION, emptySet);
    dependencies.put(TASK_TYPE.APPLICATION_INSTANCE, addToSet(TASK_TYPE.APPLICATION));
    dependencies.put(TASK_TYPE.LIFECYCLE, emptySet);
    dependencies.put(TASK_TYPE.VIRTUALMACHINE, addToSet(TASK_TYPE.CLOUD));
    dependencies.put(TASK_TYPE.VIRTUALMACHINE_INSTANCE, addToSet(TASK_TYPE.VIRTUALMACHINE));
    dependencies.put(TASK_TYPE.APPLICATION_COMPONENT, addToSet(
            TASK_TYPE.APPLICATION, TASK_TYPE.LIFECYCLE, GraphValidator.TASK_TYPE.VIRTUALMACHINE));

    dependencies.put(TASK_TYPE.APPLICATION_COMPONENT_INSTANCE, addToSet(
            TASK_TYPE.APPLICATION_INSTANCE, GraphValidator.TASK_TYPE.VIRTUALMACHINE_INSTANCE,
            GraphValidator.TASK_TYPE.APPLICATION_COMPONENT));

    dependencies.put(GraphValidator.TASK_TYPE.COMMUNICATION, addToSet(
            GraphValidator.TASK_TYPE.PORT_PROVIDED, GraphValidator.TASK_TYPE.PORT_REQUIRED));

    dependencies.put(GraphValidator.TASK_TYPE.PORT_PROVIDED, addToSet(GraphValidator.TASK_TYPE.APPLICATION_COMPONENT));
    dependencies.put(GraphValidator.TASK_TYPE.PORT_REQUIRED, addToSet(GraphValidator.TASK_TYPE.APPLICATION_COMPONENT));

    dependencies.put(GraphValidator.TASK_TYPE.VM_INSTANCE_MONITOR, addToSet(
            GraphValidator.TASK_TYPE.VIRTUALMACHINE_INSTANCE));

    dependencies.put(GraphValidator.TASK_TYPE.APP_COMP_INSTANCE_MONITOR, addToSet(
            GraphValidator.TASK_TYPE.APPLICATION_COMPONENT_INSTANCE));

    return dependencies;
  }


}
