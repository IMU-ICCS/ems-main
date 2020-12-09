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
import eu.melodic.upperware.adapter.planexecutor.PlanExecutor;
import eu.melodic.upperware.adapter.planexecutor.RunnableTaskExecutor;
import eu.melodic.upperware.adapter.plangenerator.Plan;
import eu.melodic.upperware.adapter.plangenerator.tasks.Task;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.DirectedNeighborIndex;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.TopologicalOrderIterator;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ColosseumExecutor implements PlanExecutor, InitializingBean {

  private ColosseumExecutorFactory factory;
  private ThreadPoolTaskExecutor executor;

  @Override
  public void executePlan(Plan plan, String applicationId) {
    DirectedGraph<Task, DefaultEdge> graph = plan.getTaskGraph();

    DirectedNeighborIndex<Task, DefaultEdge> neighbors = new DirectedNeighborIndex(graph);
    TopologicalOrderIterator<Task, DefaultEdge> it = new TopologicalOrderIterator(graph);
    Map<Task, Future> taskToFutureMap = Maps.newHashMap();

    while (it.hasNext()) {
      Task task = it.next();
      Set<Task> dependentTasks = neighbors.predecessorsOf(task);
      Set<Future> dependentFeatures = getDependentFeatures(taskToFutureMap, dependentTasks);
      Future future = submitTask(task, dependentFeatures, applicationId);
      log.info("ProActive Dev [executePlan]: Task task= {}", task);
      log.info("ProActive Dev [executePlan]: Set<Task> dependentTasks= {}", dependentTasks);
      log.info("ProActive Dev [executePlan]: Set<Future> dependentFeatures= {}", dependentFeatures);
      taskToFutureMap.put(task, future);
    }

    for (Future future : taskToFutureMap.values()) {
      try {
        future.get();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }

  private Set<Future> getDependentFeatures(Map<Task, Future> taskToFeatureMap, Set<Task> predecessors) {
    return predecessors.stream().map(taskToFeatureMap::get).collect(Collectors.toSet());
  }

  private Future submitTask(Task task, Set<Future> predecessors, String applicationId) {
    return executor.submit(createTaskExecutor(task, predecessors, applicationId));
  }

  private RunnableTaskExecutor createTaskExecutor(Task task, Set<Future> predecessors, String applicationId) {
    return factory.createTaskExecutor(task, predecessors, applicationId);
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    log.info("External ThreadPoolTaskExecutor prefix: {}", executor.getThreadNamePrefix());
  }
}
