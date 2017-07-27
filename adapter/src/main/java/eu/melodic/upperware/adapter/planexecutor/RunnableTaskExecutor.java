/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.planexecutor;

import eu.melodic.upperware.adapter.plangenerator.tasks.Task;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
@AllArgsConstructor
public abstract class RunnableTaskExecutor<T> implements TaskExecutor<T>, Runnable {

  protected Task<T> task;

  private Collection<Future> predecessors;

  @Override
  public void run() {
    try {
      T data = task.getData();
      log.info("Starting task executor thread for {}(type={}, data={})",
        task.getClass().getSimpleName(), task.getType(), data);
      waitForPredecessors();
      switch (task.getType()) {
        case CREATE:
          create(data);
          break;
        case UPDATE:
          update(data);
          break;
        case DELETE:
          delete(data);
          break;
      }
      log.info("Task executor thread for {}(type={}) was successfully finished",
        task.getClass().getSimpleName(), task.getType());
    } catch (ExecutionException e) {
      log.error("An exception occurred while executing dependent task - execution of successor thread will be interrupted as well", e);
      throw new RuntimeException(e);
    } catch (InterruptedException e) {
      log.error("Dependent task execution was interrupted - execution of successor thread will be interrupted as well", e);
      throw new RuntimeException(e);
    }
  }

  private void waitForPredecessors() throws ExecutionException, InterruptedException {
    if (CollectionUtils.isEmpty(predecessors)) {
      return;
    }
    for (Future future : predecessors) {
      if (future.isDone() || future.isCancelled()) {
        continue;
      }
      log.info("{}(type={}) is waiting for finish dependent task executions",
        task.getClass().getSimpleName(), task.getType());
      future.get();
    }
    log.info("Dependent task executions of {}(type={}) were finished",
      task.getClass().getSimpleName(), task.getType());
  }
}
