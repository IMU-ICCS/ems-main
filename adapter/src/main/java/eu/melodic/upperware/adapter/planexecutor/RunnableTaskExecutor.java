/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.planexecutor;

import eu.melodic.upperware.adapter.plangenerator.model.Data;
import eu.melodic.upperware.adapter.plangenerator.tasks.Task;
import eu.melodic.upperware.adapter.plangenerator.tasks.Type;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
@AllArgsConstructor
public abstract class RunnableTaskExecutor<T extends Data> implements TaskExecutor<T>, Runnable {

  protected Task<T> task;

  private Collection<Future> predecessors;

  @Override
  public void run() {
    Type type = task.getType();
    T data = task.getData();
    try {
      log.info("Starting task executor thread for {}(type={}, data={})",
        task.getClass().getSimpleName(), type, data);
      waitForPredecessors(type, data);
      switch (type) {
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
      log.info("Task executor thread for {}(type={}, data={}) was successfully finished",
        task.getClass().getSimpleName(), type, data);
    } catch (ExecutionException e) {
      log.error("An exception occurred while executing dependent task of {}(type={}, data={}) - execution of successor thread " +
        "will be interrupted as well", task.getClass().getSimpleName(), type, data);
      throw new RuntimeException(e);
    } catch (InterruptedException e) {
      log.error("Dependent task execution of {}(type={}, data={}) was interrupted - execution of successor thread " +
        "will be interrupted as well", task.getClass().getSimpleName(), type, data);
      throw new RuntimeException(e);
    }
  }

  private void waitForPredecessors(Type type, T data) throws ExecutionException, InterruptedException {
    if (CollectionUtils.isEmpty(predecessors)) {
      return;
    }
    for (Future future : predecessors) {
      if (future.isDone() || future.isCancelled()) {
        future.get();
        continue;
      }
      log.info("{}(type={}, data={}) is waiting for finish dependent task executions",
        task.getClass().getSimpleName(), type, data);
      future.get();
    }
    log.info("Dependent task executions of {}(type={}, data={}) were finished",
      task.getClass().getSimpleName(), type, data);
  }
}
