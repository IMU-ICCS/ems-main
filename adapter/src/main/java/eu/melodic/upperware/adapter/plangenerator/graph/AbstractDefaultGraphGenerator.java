/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.plangenerator.graph;

import eu.melodic.upperware.adapter.plangenerator.graph.model.MelodicGraph;
import eu.melodic.upperware.adapter.plangenerator.tasks.*;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.graph.DefaultEdge;

import java.util.Collection;
import java.util.function.Predicate;

import static eu.melodic.upperware.adapter.plangenerator.graph.model.Type.CONFIG;
import static eu.melodic.upperware.adapter.plangenerator.tasks.Type.DELETE;
import static java.lang.String.format;

@Slf4j
public abstract class AbstractDefaultGraphGenerator<T> implements GraphGenerator<T> {

  void setDependencies(MelodicGraph<Task, DefaultEdge> graph, Type type, Task source, Task target) {
    if (DELETE.equals(type)) {
      log.debug("Setting {} as a dependency to {}", target, source);
      graph.addEdge(target, source);
    } else {
      log.debug("Setting {} as a dependency to {}", source, target);
      graph.addEdge(source, target);
    }
  }

  void addVertex(MelodicGraph<Task, DefaultEdge> graph, Task task) {
    log.debug("Adding vertex {}", task);
    graph.addVertex(task);
  }

  protected void findAndSetDependencies(MelodicGraph<Task, DefaultEdge> graph, Task task, String depName,
                                        Collection<? extends Task> depTasks, Type type, Predicate<Task> predicate) {
    boolean wasSet = false;
    for (Task depTask : depTasks) {
      if (predicate.test(depTask) && depTask.getType().equals(task.getType())) {
        setDependencies(graph, type, depTask, task);
        wasSet = true;
      }
    }
    if (CONFIG.equals(graph.getType()) && !wasSet) {
      throw new IllegalStateException(
        format("Missing obligatory node of graph - dependency between %s and %s was not set",
          depName, task.getData().getName()));
    }
  }

  void findAndSetNodeDependencies(MelodicGraph<Task, DefaultEdge> graph, ProcessTask processTask, String depName,
                                        Collection<NodeTask> nodeTasks, Type type) {
    boolean wasSet = false;
    for (NodeTask nodeTask : nodeTasks) {
      if (nodeTask.getData().getNodeName().equals(depName) && nodeTask.getType().equals(processTask.getType())) {
        setDependencies(graph, type, nodeTask, processTask);
        wasSet = true;
      }
    }
    if (CONFIG.equals(graph.getType()) && !wasSet) {
      throw new IllegalStateException(
              format("Missing obligatory node of graph - dependency between %s and %s was not set",
                      depName, processTask.getData().getName()));
    }
  }

  void findAndSetProcessDependencies(MelodicGraph<Task, DefaultEdge> graph, MonitorTask monitorTask, String taskName,
                                          Collection<ProcessTask> processTasks, Type type) {
    boolean wasSet = false;
    for (ProcessTask processTask : processTasks) {
      if (processTask.getData().getTaskName().equals(taskName) && processTask.getType().equals(monitorTask.getType())) {
        setDependencies(graph, type, processTask, monitorTask);
        wasSet = true;
      }
    }
    if (CONFIG.equals(graph.getType()) && !wasSet) {
      throw new IllegalStateException(
              format("Missing obligatory node of graph - dependency between %s and %s was not set",
                      taskName, monitorTask.getData().getName()));
    }
  }

}
