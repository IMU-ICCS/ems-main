/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.planexecutor.colosseum;

import eu.paasage.upperware.adapter.communication.colosseum.ColosseumApi;
import eu.paasage.upperware.adapter.communication.colosseum.ColosseumConfigApi;
import eu.paasage.upperware.adapter.executioncontext.colosseum.ColosseumContext;
import eu.paasage.upperware.adapter.planexecutor.RunnableTaskExecutor;
import eu.paasage.upperware.adapter.plangenerator.tasks.Task;

import java.util.Collection;
import java.util.concurrent.Future;

public abstract class ColosseumTaskExecutor<T> extends RunnableTaskExecutor<T> {

  protected ColosseumApi api;

  protected ColosseumConfigApi configApi;

  protected ColosseumContext context;

  ColosseumTaskExecutor(Task<T> task, Collection<Future> predecessors, ColosseumApi api,
                        ColosseumConfigApi configApi, ColosseumContext context) {
    super(task, predecessors);
    this.api = api;
    this.configApi = configApi;
    this.context = context;
  }
}
