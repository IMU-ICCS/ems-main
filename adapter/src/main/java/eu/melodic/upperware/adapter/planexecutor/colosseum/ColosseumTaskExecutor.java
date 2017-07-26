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
import eu.melodic.upperware.adapter.plangenerator.tasks.Task;

import java.util.Collection;
import java.util.concurrent.Future;

public abstract class ColosseumTaskExecutor<T> extends RunnableTaskExecutor<T> {

  protected ColosseumApi api;

  protected ColosseumContext context;

  ColosseumTaskExecutor(Task<T> task, Collection<Future> predecessors, ColosseumApi api, ColosseumContext context) {
    super(task, predecessors);
    this.api = api;
    this.context = context;
  }
}
