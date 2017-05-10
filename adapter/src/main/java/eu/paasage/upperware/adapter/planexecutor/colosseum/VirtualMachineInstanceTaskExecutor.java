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
import eu.paasage.upperware.adapter.plangenerator.model.VirtualMachineInstance;
import eu.paasage.upperware.adapter.plangenerator.tasks.VirtualMachineInstanceTask;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.concurrent.Future;

@Slf4j
public class VirtualMachineInstanceTaskExecutor extends ColosseumTaskExecutor<VirtualMachineInstance> {

  VirtualMachineInstanceTaskExecutor(VirtualMachineInstanceTask task, Set<Future> predecessors, ColosseumApi api,
                                     ColosseumConfigApi configApi, ColosseumContext context) {
    super(task, predecessors, api, configApi, context);
  }

  @Override
  public void create(VirtualMachineInstance vmInst) {
    // TODO
  }

  @Override
  public void update(VirtualMachineInstance vmInst) {
    // TODO
  }

  @Override
  public void delete(VirtualMachineInstance vmInst) {
    // TODO
  }
}
