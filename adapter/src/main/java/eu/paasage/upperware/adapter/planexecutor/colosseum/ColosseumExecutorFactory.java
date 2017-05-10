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
import eu.paasage.upperware.adapter.plangenerator.tasks.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.Future;

import static java.lang.String.format;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ColosseumExecutorFactory {

  private ColosseumApi api;

  private ColosseumConfigApi configApi;

  private ColosseumContext context;

  public RunnableTaskExecutor createTaskExecutor(Task task, Set<Future> predecessors) {
    if (task instanceof CloudTask) {
      return new CloudTaskExecutor((CloudTask) task, predecessors, api, configApi, context);
    }
    if (task instanceof ApplicationTask) {
      return new ApplicationTaskExecutor((ApplicationTask) task, predecessors, api, configApi, context);
    }
    if (task instanceof ApplicationInstanceTask) {
      return new ApplicationInstanceTaskExecutor((ApplicationInstanceTask) task, predecessors, api, configApi, context);
    }
    if (task instanceof VirtualMachineTask) {
      return new VirtualMachineTaskExecutor((VirtualMachineTask) task, predecessors, api, configApi, context);
    }
    if (task instanceof VirtualMachineInstanceTask) {
      return new VirtualMachineInstanceTaskExecutor((VirtualMachineInstanceTask) task, predecessors, api, configApi, context);
    }
    if (task instanceof LifecycleComponentTask) {
      return new LifecycleComponentTaskExecutor((LifecycleComponentTask) task, predecessors, api, configApi, context);
    }
    if (task instanceof ApplicationComponentTask) {
      return new ApplicationComponentTaskExecutor((ApplicationComponentTask) task, predecessors, api, configApi, context);
    }
    if (task instanceof ApplicationComponentInstanceTask) {
      return new ApplicationComponentInstanceTaskExecutor((ApplicationComponentInstanceTask) task, predecessors, api, configApi, context);
    }
    if (task instanceof PortProvidedTask) {
      return new PortProvidedTaskExecutor((PortProvidedTask) task, predecessors, api, configApi, context);
    }
    if (task instanceof PortRequiredTask) {
      return new PortRequiredTaskExecutor((PortRequiredTask) task, predecessors, api, configApi, context);
    }
    if (task instanceof CommunicationTask) {
      return new CommunicationTaskExecutor((CommunicationTask) task, predecessors, api, configApi, context);
    }
    throw new IllegalArgumentException(format("Task %s is not supported", task.getClass()));
  }
}
