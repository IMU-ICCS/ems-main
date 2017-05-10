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
import eu.paasage.upperware.adapter.plangenerator.model.ApplicationComponentInstance;
import eu.paasage.upperware.adapter.plangenerator.tasks.ApplicationComponentInstanceTask;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.concurrent.Future;

@Slf4j
public class ApplicationComponentInstanceTaskExecutor extends ColosseumTaskExecutor<ApplicationComponentInstance> {

  ApplicationComponentInstanceTaskExecutor(ApplicationComponentInstanceTask task, Collection<Future> predecessors,
                                           ColosseumApi api, ColosseumConfigApi configApi, ColosseumContext context) {
    super(task, predecessors, api, configApi, context);
  }

  @Override
  public void create(@NonNull ApplicationComponentInstance compInst) {
    // TODO
  }

  @Override
  public void update(ApplicationComponentInstance compInst) {
    // TODO
  }

  @Override
  public void delete(ApplicationComponentInstance compInst) {
    // TODO
  }
}
