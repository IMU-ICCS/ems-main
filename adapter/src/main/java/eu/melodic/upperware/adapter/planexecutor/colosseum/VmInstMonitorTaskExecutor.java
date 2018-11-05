/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.planexecutor.colosseum;

import de.uniulm.omi.cloudiator.colosseum.client.entities.*;
import eu.melodic.upperware.adapter.communication.colosseum.ColosseumApi;
import eu.melodic.upperware.adapter.executioncontext.colosseum.ColosseumContext;
import eu.melodic.upperware.adapter.plangenerator.model.VirtualMachineInstanceMonitor;
import eu.melodic.upperware.adapter.plangenerator.tasks.VirtualMachineInstanceMonitorTask;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.concurrent.Future;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

@Deprecated
@Slf4j
public class VmInstMonitorTaskExecutor extends ColosseumTaskExecutor<VirtualMachineInstanceMonitor> {

  VmInstMonitorTaskExecutor(VirtualMachineInstanceMonitorTask task, Collection<Future> predecessors,
          ColosseumApi api, ColosseumContext context) {
    super(task, predecessors, api, context);
  }

  @Override
  public void create(VirtualMachineInstanceMonitor vmInstMonitor) {
    monitor(vmInstMonitor);
  }

  @Override
  public void delete(VirtualMachineInstanceMonitor vmInstMonitor) {
    monitor(vmInstMonitor);
  }

  private void monitor(VirtualMachineInstanceMonitor vmInstMonitor) {
    String vmInstName = vmInstMonitor.getVmInstName();
    checkNotNull(vmInstName);
    Long vmInstTimeout = vmInstMonitor.getVmInstTimeout();
    checkNotNull(vmInstTimeout);

    log.info("Executing VM Instance Monitoring task {}", vmInstName);

    VirtualMachine vmInstEntity = context.getVirtualMachineInstance(vmInstName)
      .orElseThrow(() -> new IllegalStateException(format("Virtual Machine Instance %s does not exist in Colosseum " +
        "- cannot monitor its startup", vmInstName)));

    if (!api.isVirtualMachineInstanceRunning(vmInstEntity, vmInstTimeout)) {
      throw new RuntimeException(format("Virtual Machine Instance %s does not seem to be working", vmInstName));
    }

    log.info("Virtual Machine Instance {} is up", vmInstName);
  }
}
