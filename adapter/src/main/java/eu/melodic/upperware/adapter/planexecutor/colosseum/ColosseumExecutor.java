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
import eu.melodic.upperware.adapter.communication.proactive.ProactiveClientServiceForAdapter;
import eu.melodic.upperware.adapter.planexecutor.PlanExecutor;
import eu.melodic.upperware.adapter.planexecutor.RunnableTaskExecutor;
import eu.melodic.upperware.adapter.plangenerator.Plan;
import eu.melodic.upperware.adapter.plangenerator.PlanType;
import eu.melodic.upperware.adapter.plangenerator.tasks.Task;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.activeeon.morphemic.model.SubmittedJobType;
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
  private final ProactiveClientServiceForAdapter proactiveClientServiceForAdapter;

  @Override
  public void executePlan(Plan plan, String applicationId, String authorizationBearer) {
    DirectedGraph<Task, DefaultEdge> graph = plan.getTaskGraph();

    DirectedNeighborIndex<Task, DefaultEdge> neighbors = new DirectedNeighborIndex(graph);
    TopologicalOrderIterator<Task, DefaultEdge> it = new TopologicalOrderIterator(graph);
    Map<Task, Future> taskToFutureMap = Maps.newHashMap();

    while (it.hasNext()) {
      Task task = it.next();
      Set<Task> dependentTasks = neighbors.predecessorsOf(task);
      Set<Future> dependentFeatures = getDependentFeatures(taskToFutureMap, dependentTasks);
      Future future = submitTask(task, dependentFeatures, applicationId, authorizationBearer);
      log.debug("Execute Plan [application id: {}]: task= {}", applicationId, task);
      log.debug("Execute Plan [application id: {}]: dependent tasks= {}", applicationId, dependentTasks);
      log.debug("Execute Plan [application id: {}]: dependent features= {}", applicationId, dependentFeatures);
      taskToFutureMap.put(task, future);
    }

    for (Future future : taskToFutureMap.values()) {
      try {
        future.get();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    if(plan.getType().equals(PlanType.CONFIGURATION)) {
      log.info("Execute Plan [application id: {}]: all tasks (futures) have been completed, now submitting the job", applicationId);
      long jobId = proactiveClientServiceForAdapter.submitJob(applicationId);
      log.info("Execute Plan [application id: {}]: ProActive jobId={}", applicationId, jobId);
      proactiveClientServiceForAdapter.waitForJobFinish(applicationId, SubmittedJobType.FIRST_DEPLOYMENT);
    } else {
      log.info("Execute Plan [application id: {}]: all tasks (futures) have been completed, the plan type is: {} " +
              "meaning there is no job to submit", applicationId, plan.getType().name());
    }
  }

  private Set<Future> getDependentFeatures(Map<Task, Future> taskToFeatureMap, Set<Task> predecessors) {
    return predecessors.stream().map(taskToFeatureMap::get).collect(Collectors.toSet());
  }

  private Future submitTask(Task task, Set<Future> predecessors, String applicationId, String authorizationBearer) {
    return executor.submit(createTaskExecutor(task, predecessors, applicationId, authorizationBearer));
  }

  private RunnableTaskExecutor createTaskExecutor(Task task, Set<Future> predecessors, String applicationId, String authorizationBearer) {
    return factory.createTaskExecutor(task, predecessors, applicationId, authorizationBearer);
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    log.info("External ThreadPoolTaskExecutor prefix: {}", executor.getThreadNamePrefix());
  }
}
