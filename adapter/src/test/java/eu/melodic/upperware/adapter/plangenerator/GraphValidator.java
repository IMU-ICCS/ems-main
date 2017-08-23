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

import static eu.melodic.upperware.adapter.plangenerator.GraphValidatorUtils.*;
import static eu.melodic.upperware.adapter.plangenerator.TaskType.*;


public class GraphValidator {


  static boolean checkVertex(Task v, SimpleDirectedGraph<Task, DefaultEdge> graph,
                             Map<TaskType, Set<Task>> tasks,
                             Map<TaskType, Set<TaskType>> dependencies) {
    Map<TaskType, Predicate<Task>> predicateMap = new HashMap<>();

    setPredicateMap(predicateMap,v,false);
    if (v instanceof CloudApiTask) {
      return containsOnlyOutEdges(v, graph, tasks.get(CLOUD_API));
    }

    if (v instanceof CloudTask) {
      return checkConnections(v, CLOUD, graph, tasks, dependencies, predicateMap);
    }

    if (v instanceof CloudPropertyTask) {
      return checkConnections(v, CLOUD_PROPERTY, graph, tasks, dependencies, predicateMap);
    }

    if (v instanceof CloudCredentialTask) {
      return checkConnections(v, CLOUD_CREDENTIAL, graph, tasks, dependencies, predicateMap);
    }

    if (v instanceof ApplicationTask) {
      return containsOnlyOutEdges(v, graph, tasks.get(APPLICATION));
    }

    if (v instanceof ApplicationInstanceTask) {
      return checkConnections(v, APPLICATION_INSTANCE, graph, tasks, dependencies, predicateMap);
    }

    if (v instanceof LifecycleComponentTask) {
      return containsOnlyOutEdges(v, graph, tasks.get(LIFECYCLE));
    }

    if (v instanceof VirtualMachineTask) {
      return checkConnections(v, VIRTUALMACHINE, graph, tasks, dependencies, predicateMap);
    }

    if (v instanceof VirtualMachineInstanceTask) {
      return checkConnections(v, VIRTUALMACHINE_INSTANCE, graph, tasks, dependencies, predicateMap);
    }

    if (v instanceof ApplicationComponentTask) {
      return checkConnections(v, APPLICATION_COMPONENT, graph, tasks, dependencies, predicateMap);
    }

    if (v instanceof ApplicationComponentInstanceTask) {
      return checkConnections(v, APPLICATION_COMPONENT_INSTANCE, graph, tasks, dependencies, predicateMap);
    }

    if (v instanceof CommunicationTask) {
      return checkConnections(v, COMMUNICATION, graph, tasks, dependencies, predicateMap);
    }

    if (v instanceof PortProvidedTask) {
      return checkConnections(v, PORT_PROVIDED, graph, tasks, dependencies, predicateMap);
    }

    if (v instanceof PortRequiredTask) {
      return checkConnections(v, PORT_REQUIRED, graph, tasks, dependencies, predicateMap);
    }

    if (v instanceof VirtualMachineInstanceMonitorTask) {
      return checkConnections(v, VM_INSTANCE_MONITOR, graph, tasks, dependencies, predicateMap);
    }

    if (v instanceof ApplicationComponentInstanceMonitorTask) {
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

    return countAndCheckConnections(v,t,graph,tasksMap,dependencies,preds) == getInEdges(v, graph);

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
