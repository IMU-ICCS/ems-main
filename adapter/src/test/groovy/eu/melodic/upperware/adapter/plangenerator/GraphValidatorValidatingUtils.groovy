/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */


package eu.melodic.upperware.adapter.plangenerator

import eu.melodic.upperware.adapter.plangenerator.tasks.Task
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleDirectedGraph

import java.util.function.Predicate
import java.util.stream.Collectors

class GraphValidatorValidatingUtils {

  /* check if vertex is correct (type and data)*/
  static void checkVertexCorrectness(Task v, TaskType t, Map<Task, Set<Task>> tasksMap){
    Set<Task> tasks = tasksMap.get(t)
    assert (tasks.contains(v))
  }

  static int countAndCheckConnections(Task v, TaskType t,
                                      SimpleDirectedGraph<Task, DefaultEdge> graph,
                                      Map<TaskType, Set<Task>> tasksMap,
                                      Map<TaskType, Set<TaskType>> dependencies,
                                      Map<TaskType, Predicate<Task>> preds) {

    int counter = 0

    /* check if vertex has all needed dependencies and count them */
    for (TaskType connectedType : dependencies.get(t)) {
      /* filter tasks that should be connected with vertex*/
      Set<Task> filteredTask = filterTasks(tasksMap.get(connectedType), preds.get(connectedType))

      for (Task task: filteredTask){
        assert(graph.containsEdge(task,v))
      }

      counter += filteredTask.size()
    }
    return counter
  }

  static Set<Task> filterTasks(Set<Task> tasks, Predicate<Task> predicate) {
    return tasks.stream().filter(predicate).collect(Collectors.toSet())
  }

}

