/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.planexecutor.colosseum;

import eu.melodic.upperware.adapter.communication.colosseum.ColosseumApi;
import eu.melodic.upperware.adapter.executioncontext.colosseum.ColosseumContext;
import eu.melodic.upperware.adapter.planexecutor.RunnableTaskExecutor;
import eu.melodic.upperware.adapter.plangenerator.tasks.*;
import eu.melodic.upperware.adapter.properties.AdapterProperties;
import io.github.cloudiator.rest.model.Queue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.function.Function;

import static eu.melodic.upperware.adapter.ApplicationContext.INNER_THREAD_POOL_TASK_EXECUTOR_NAME;
import static java.lang.String.format;

@Slf4j
@Service
public class ColosseumExecutorFactory implements InitializingBean {

  public ColosseumExecutorFactory(ColosseumApi api, @Qualifier(INNER_THREAD_POOL_TASK_EXECUTOR_NAME) ThreadPoolTaskExecutor executor,
                                  AdapterProperties adapterProperties, ColosseumContext context) {
    this.api = api;
    this.executor = executor;
    this.adapterProperties = adapterProperties;
    this.context = context;
  }

  private ColosseumApi api;
  private ThreadPoolTaskExecutor executor;
  private AdapterProperties adapterProperties;
  private ColosseumContext context;

  private final Function<CheckFinishTask, Callable<Queue>> checkFinishTaskToCallableFunction =
          task -> new CheckFinishTaskExecutor(task, api, adapterProperties.getCloudiatorV2().getDelayBetweenQueueCheck());

  private final Function<Callable<Queue>, Future<Queue>> callableToFutureFunction =
          callable -> executor.submit(callable);

  private final Function<CheckFinishTask, Future<Queue>> checkFinishTaskToFutureFunction =
          checkFinishTaskToCallableFunction
                  .andThen(callableToFutureFunction);

  RunnableTaskExecutor createTaskExecutor(Task task, Set<Future> predecessors) {
    if (task instanceof JobTask) {
     return new JobTaskExecutor((JobTask) task, predecessors, api, context, checkFinishTaskToFutureFunction);
    }
    if (task instanceof ScheduleTask) {
      return new ScheduleTaskExecutor((ScheduleTask) task, predecessors, api, context, checkFinishTaskToFutureFunction);
    }
    if (task instanceof NodeTask) {
      return new NodeTaskExecutor((NodeTask) task, predecessors, api, context, checkFinishTaskToFutureFunction);
    }
    if (task instanceof ProcessTask) {
      return new ProcessTaskExecutor((ProcessTask) task, predecessors, api, context, checkFinishTaskToFutureFunction);
    }
    if (task instanceof WaitTask) {
      return new WaitTaskExecutor((WaitTask) task, predecessors, api, context, checkFinishTaskToFutureFunction);
    }
    if (task instanceof MonitorTask) {
      return new MonitorTaskExecutor((MonitorTask)task, predecessors, api, context, checkFinishTaskToFutureFunction);
    }

    throw new IllegalArgumentException(format("Task %s is not supported as RunnableTask", task.getClass().getName()));
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    log.info("Internal ThreadPoolTaskExecutor prefix: {}", executor.getThreadNamePrefix());
  }
}
