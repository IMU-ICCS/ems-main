/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.planexecutor.colosseum;

import eu.melodic.upperware.adapter.communication.proactive.ProactiveClientServiceForAdapter;
import eu.melodic.upperware.adapter.planexecutor.RunnableTaskExecutor;
import eu.melodic.upperware.adapter.plangenerator.tasks.*;
import eu.melodic.upperware.adapter.properties.AdapterProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.Future;

import static eu.melodic.upperware.adapter.ApplicationContext.INNER_THREAD_POOL_TASK_EXECUTOR_NAME;
import static java.lang.String.format;

@Slf4j
@Service
public class ColosseumExecutorFactory implements InitializingBean {

  public ColosseumExecutorFactory(@Qualifier(INNER_THREAD_POOL_TASK_EXECUTOR_NAME) ThreadPoolTaskExecutor executor,
                                  AdapterProperties adapterProperties, ProactiveClientServiceForAdapter proactiveClientServiceForAdapter) {
    this.executor = executor;
    this.adapterProperties = adapterProperties;
    this.proactiveClientServiceForAdapter = proactiveClientServiceForAdapter;
  }

  private ThreadPoolTaskExecutor executor;
  private AdapterProperties adapterProperties;
  private ProactiveClientServiceForAdapter proactiveClientServiceForAdapter;

  RunnableTaskExecutor createTaskExecutor(Task task, Set<Future> predecessors, String applicationId, String authorizationBearer) {
    if (task instanceof JobTask) {
     return new JobTaskExecutor((JobTask) task, predecessors, applicationId, proactiveClientServiceForAdapter);
    }
    if (task instanceof NodeTask) {
      return new NodeTaskExecutor((NodeTask) task, predecessors, applicationId, proactiveClientServiceForAdapter);
    }
    if (task instanceof MonitorTask) {
      return new MonitorTaskExecutor((MonitorTask)task, predecessors, applicationId, authorizationBearer, proactiveClientServiceForAdapter);
    }
    if (task instanceof ScaleTask) {
      return new ScaleTaskExecutor((ScaleTask) task, predecessors, applicationId, proactiveClientServiceForAdapter);
    }

    throw new IllegalArgumentException(format("Task %s is not supported as RunnableTask", task.getClass().getName()));
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    log.info("Internal ThreadPoolTaskExecutor prefix: {}", executor.getThreadNamePrefix());
  }
}
