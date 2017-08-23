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

import static eu.melodic.upperware.adapter.plangenerator.GraphValidatorUtils.addToSet;
import static eu.melodic.upperware.adapter.plangenerator.GraphValidatorUtils.countAndCheckConnections;
import static eu.melodic.upperware.adapter.plangenerator.GraphValidatorUtils.setPredicateMap;
import static eu.melodic.upperware.adapter.plangenerator.TaskType.*;
import static eu.melodic.upperware.adapter.plangenerator.tasks.Type.*;


public class ReconfigGraphValidator {

  static boolean checkReconfigVertex(Task v, SimpleDirectedGraph<Task, DefaultEdge> graph,
                                     Map<TaskType, Set<Task>> tasks,
                                     Map<TaskType, Set<TaskType>> dependencies,
                                     Map<TaskType, Set<TaskType>> deletingDependencies) {
    Map<TaskType, Predicate<Task>> predicateMap = new HashMap<>();
    setPredicateMap(predicateMap, v, DELETE.equals(v.getType()));

    if (v instanceof CloudApiTask) {
      if (!DELETE.equals(v.getType())) {
        countAndCheckConnections(v, CLOUD_API, graph, tasks, dependencies, predicateMap);
      } else {
        countAndCheckConnections(v, CLOUD_API, graph, tasks, deletingDependencies, predicateMap);
      }
    }

    if (v instanceof CloudTask) {
      if (!DELETE.equals(v.getType())) {
        countAndCheckConnections(v, CLOUD, graph, tasks, dependencies, predicateMap);
      } else {
        countAndCheckConnections(v, CLOUD, graph, tasks, deletingDependencies, predicateMap);
      }
    }

    if (v instanceof ApplicationTask) {
      if (!DELETE.equals(v.getType())) {
        countAndCheckConnections(v, APPLICATION, graph, tasks, dependencies, predicateMap);
      } else {
        countAndCheckConnections(v, APPLICATION, graph, tasks, deletingDependencies, predicateMap);
      }

    }
    if (v instanceof LifecycleComponentTask) {
      if (!DELETE.equals(v.getType())) {
        countAndCheckConnections(v, LIFECYCLE, graph, tasks, dependencies, predicateMap);
      } else {
        countAndCheckConnections(v, LIFECYCLE, graph, tasks, deletingDependencies, predicateMap);
      }
    }
    if (v instanceof VirtualMachineTask) {
      if (!DELETE.equals(v.getType())) {
        countAndCheckConnections(v, VIRTUALMACHINE, graph, tasks, dependencies, predicateMap);
      } else {
        countAndCheckConnections(v, VIRTUALMACHINE, graph, tasks, deletingDependencies, predicateMap);
      }
    }
    if (v instanceof VirtualMachineInstanceTask) {
      if (!DELETE.equals(v.getType())) {
        countAndCheckConnections(v, VIRTUALMACHINE_INSTANCE, graph, tasks, dependencies, predicateMap);
      } else {
        countAndCheckConnections(v, VIRTUALMACHINE_INSTANCE, graph, tasks, deletingDependencies, predicateMap);
      }
    }
    if (v instanceof ApplicationComponentTask) {
      if (!DELETE.equals(v.getType())) {
        countAndCheckConnections(v, APPLICATION_COMPONENT, graph, tasks, dependencies, predicateMap);
      } else {
        countAndCheckConnections(v, APPLICATION_COMPONENT, graph, tasks, deletingDependencies, predicateMap);
      }
    }

    if (v instanceof ApplicationComponentInstanceTask) {
      if (!DELETE.equals(v.getType())) {
        countAndCheckConnections(v, APPLICATION_COMPONENT_INSTANCE, graph, tasks, dependencies, predicateMap);
      } else {
        countAndCheckConnections(v, APPLICATION_COMPONENT_INSTANCE, graph, tasks, deletingDependencies, predicateMap);
      }
    }
    if (v instanceof PortProvidedTask) {
      if (!DELETE.equals(v.getType())) {
        countAndCheckConnections(v, PORT_PROVIDED, graph, tasks, dependencies, predicateMap);
      } else {
        countAndCheckConnections(v, PORT_PROVIDED, graph, tasks, deletingDependencies, predicateMap);
      }
    }
    if (v instanceof PortRequiredTask) {
      if (!DELETE.equals(v.getType())) {
        countAndCheckConnections(v, PORT_REQUIRED, graph, tasks, dependencies, predicateMap);
      } else {
        countAndCheckConnections(v, PORT_REQUIRED, graph, tasks, deletingDependencies, predicateMap);
      }
    }
    return true;
  }

  public static Map<TaskType, Set<TaskType>> createDeleteDependencies() {
    Map<TaskType, Set<TaskType>> dependencies = new HashMap<>();
    Set<TaskType> emptySet = new HashSet<>();

    dependencies.put(CLOUD_API, addToSet(CLOUD));
    dependencies.put(CLOUD, addToSet(CLOUD_PROPERTY, CLOUD_CREDENTIAL, VIRTUALMACHINE));
    dependencies.put(CLOUD_PROPERTY, emptySet);
    dependencies.put(CLOUD_CREDENTIAL, emptySet);
    dependencies.put(APPLICATION, addToSet(APPLICATION_INSTANCE, APPLICATION_COMPONENT));
    dependencies.put(APPLICATION_INSTANCE, addToSet(APPLICATION_COMPONENT_INSTANCE));
    dependencies.put(LIFECYCLE, addToSet(APPLICATION_COMPONENT));
    dependencies.put(VIRTUALMACHINE, addToSet(VIRTUALMACHINE_INSTANCE, APPLICATION_COMPONENT));
    dependencies.put(VIRTUALMACHINE_INSTANCE, addToSet(APPLICATION_COMPONENT_INSTANCE, VM_INSTANCE_MONITOR));

    dependencies.put(APPLICATION_COMPONENT, addToSet(PORT_PROVIDED, PORT_REQUIRED, APPLICATION_COMPONENT_INSTANCE));
    dependencies.put(APPLICATION_COMPONENT_INSTANCE, addToSet(APP_COMP_INSTANCE_MONITOR));

    dependencies.put(COMMUNICATION, emptySet);

    dependencies.put(PORT_PROVIDED, addToSet(COMMUNICATION));
    dependencies.put(PORT_REQUIRED, addToSet(COMMUNICATION));

    dependencies.put(VM_INSTANCE_MONITOR, emptySet);

    dependencies.put(APP_COMP_INSTANCE_MONITOR, emptySet);

    return dependencies;
  }


}
