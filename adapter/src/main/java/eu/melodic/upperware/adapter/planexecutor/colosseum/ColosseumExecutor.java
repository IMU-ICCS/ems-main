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
import eu.melodic.upperware.adapter.exception.AdapterException;
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
import org.ow2.proactive.scheduler.common.job.JobStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ColosseumExecutor implements PlanExecutor, InitializingBean {

  private ColosseumExecutorFactory factory;
  private ThreadPoolTaskExecutor executor;
  private final ProactiveClientServiceForAdapter proactiveClientServiceForAdapter;
  private final long TIMEOUT_SECONDS = 5;

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
      log.debug("Execute Plan: task= {}", task);
      log.debug("Execute Plan: dependent tasks= {}", dependentTasks);
      log.debug("Execute Plan: dependent features= {}", dependentFeatures);
      taskToFutureMap.put(task, future);
    }

    for (Future future : taskToFutureMap.values()) {
      try {
        future.get();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    log.info("Execute Plan: all tasks (futures) have been completed, now submitting the job");
    long jobId = proactiveClientServiceForAdapter.submitJob(applicationId);
    log.info("Execute Plan: ProActive jobId={}", jobId);

    waitForJobFinish(applicationId);
  }

  private void waitForJobFinish(String applicationId) {
    int loops = 0;
    Optional<JobStatus> jobStatus = proactiveClientServiceForAdapter.getJobStatus(applicationId);

    while(jobStatus.isPresent() && jobStatus.get().isJobAlive()) {
      try {
        if (loops % 6 == 0) {
          log.info("Execute Plan: job is alive and jobStatus: {} - waiting (waited for {} seconds so far)", jobStatus.get(), loops * TIMEOUT_SECONDS);
        }
        TimeUnit.SECONDS.sleep(TIMEOUT_SECONDS);
      } catch (InterruptedException e) {
        log.error("Execute Plan: [application id: {}, loops: {}, TIMEOUT_SECONDS: {}] job got interrupted while sleeping: {}", applicationId, loops, TIMEOUT_SECONDS, e.getMessage());
      }
      loops++;
      jobStatus = proactiveClientServiceForAdapter.getJobStatus(applicationId);
    }

    log.info("Execute Plan: final jobStatus: {} - waited for a total of {} seconds", jobStatus, loops * TIMEOUT_SECONDS);

    if(jobStatus.isPresent()) {
      if(isJobCompletedSuccessfully(jobStatus)) {
        log.info("Execute Plan: [application id: {}] job status indicates that it finished successfully: {}", applicationId, jobStatus.get());
      } else {
        log.error("Execute Plan: [application id: {}] job status indicates that it didn't finish successfully: {}", applicationId, jobStatus.get());
        throw new AdapterException(String.format("Job status indicates that it didn't finish successfully: %s [application id: %s]", jobStatus.get(), applicationId));
      }
    } else {
      log.error("Execute Plan: [application id: {}] job status is not present - could not get job status from ProActive", applicationId);
      throw new AdapterException(String.format("Job status is not present - could not get job status from ProActive [application id: %s]", applicationId));
    }
  }

  private boolean isJobCompletedSuccessfully(Optional<JobStatus> jobStatus) {
    switch (jobStatus.get()) {
      case FINISHED: {
        return true;
      }
      case CANCELED:
      case FAILED:
      case KILLED: {
        return false;
      }
      default: {
        log.error("Execute Plan: unknown final status from ProActive - returning false");
        return false;
      }
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
