/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.plangenerator

import eu.melodic.upperware.adapter.plangenerator.tasks.*
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleDirectedGraph

import java.util.function.Predicate

import static GraphValidatorUtils.getInEdges
import static eu.melodic.upperware.adapter.plangenerator.GraphValidatorUtils.getTaskType
import static eu.melodic.upperware.adapter.plangenerator.GraphValidatorUtils.setPredicateMap
import static eu.melodic.upperware.adapter.plangenerator.GraphValidatorValidatingUtils.countAndCheckConnections

class GraphValidator {

  static void checkGraph(SimpleDirectedGraph<Task, DefaultEdge> graph,
                         Map<TaskType, Set<Task>> tasks,
                         Map<TaskType, Set<TaskType>> dependencies) {

    int tasksSize = 0
    for (Set<Task> s in tasks.values()) {
      tasksSize += s.size()
    }
    assert (graph.vertexSet().size() == tasksSize)

    for (Task v in graph.vertexSet()) {
      checkVertex(v, graph, tasks, dependencies)
    }
  }

  static void checkVertex(Task v, SimpleDirectedGraph<Task, DefaultEdge> graph,
                          Map<TaskType, Set<Task>> tasks,
                          Map<TaskType, Set<TaskType>> dependencies) {
    Map<TaskType, Predicate<Task>> predicateMap = new HashMap<>()

    setPredicateMap(predicateMap, v, false)
    TaskType taskType = getTaskType(v)

    /* these tasks have not needed dependencies*/
    if (v instanceof CloudApiTask
      || v instanceof ApplicationTask
      || v instanceof LifecycleComponentTask) {

      assert (tasks.get(taskType).contains(v))
      containsOnlyOutEdges(v, graph)
    }
    /* others have needed dependencies */
    else {
      checkConnections(v, taskType, graph, tasks, dependencies, predicateMap)
    }
  }

  private static void containsOnlyOutEdges(Task v, SimpleDirectedGraph<Task, DefaultEdge> graph) {
    assert (getInEdges(v, graph) == 0)
  }

  private static void checkConnections(Task v, TaskType t, SimpleDirectedGraph<Task, DefaultEdge> graph,
                                       Map<TaskType, Set<Task>> tasksMap, Map<TaskType, Set<TaskType>> dependencies,
                                       Map<TaskType, Predicate<Task>> preds) {

    assert (countAndCheckConnections(v, t, graph, tasksMap, dependencies, preds) == getInEdges(v, graph))
  }

}
