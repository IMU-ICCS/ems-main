/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.planexecutor.colosseum;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import eu.melodic.upperware.adapter.planexecutor.PlanExecutor;
import eu.melodic.upperware.adapter.planexecutor.RunnableTaskExecutor;
import eu.melodic.upperware.adapter.plangenerator.Plan;
import eu.melodic.upperware.adapter.plangenerator.tasks.Task;
import lombok.AllArgsConstructor;
import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.DirectedNeighborIndex;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.TopologicalOrderIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ColosseumExecutor implements PlanExecutor {

  private ColosseumExecutorFactory factory;

  private ThreadPoolTaskExecutor executor;

  @Override
  public void executePlan(Plan plan) {
    DirectedGraph<Task, DefaultEdge> graph = plan.getTaskGraph();

    DirectedNeighborIndex<Task, DefaultEdge> neighbors = new DirectedNeighborIndex(graph);
    TopologicalOrderIterator<Task, DefaultEdge> it = new TopologicalOrderIterator(graph);
    Map<Task, Future> taskToFeatureMap = Maps.newHashMap();

    while (it.hasNext()) {
      Task task = it.next();
      Set<Task> dependentTasks = neighbors.predecessorsOf(task);
      Set<Future> dependentFeatures = getDependentFeatures(taskToFeatureMap, dependentTasks);
      Future future = submitTask(task, dependentFeatures);
      taskToFeatureMap.put(task, future);
    }
  }

  private Set<Future> getDependentFeatures(Map<Task, Future> taskToFeatureMap, Set<Task> predecessors) {
    Set<Future> futures = Sets.newHashSet();
    for (Task predecessor : predecessors) {
      futures.add(taskToFeatureMap.get(predecessor));
    }
    return futures;
  }

  private Future submitTask(Task task, Set<Future> predecessors) {
    return executor.submit(createTaskExecutor(task, predecessors));
  }

  private RunnableTaskExecutor createTaskExecutor(Task task, Set<Future> predecessors) {
    return factory.createTaskExecutor(task, predecessors);
  }
}
