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
import eu.paasage.upperware.adapter.plangenerator.model.LifecycleComponent;
import eu.paasage.upperware.adapter.plangenerator.tasks.LifecycleComponentTask;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.concurrent.Future;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
public class LifecycleComponentTaskExecutor extends ColosseumTaskExecutor<LifecycleComponent> {

  LifecycleComponentTaskExecutor(LifecycleComponentTask task, Collection<Future> predecessors, ColosseumApi api,
                                 ColosseumConfigApi configApi, ColosseumContext context) {
    super(task, predecessors, api, configApi, context);
  }

  @Override
  public void create(LifecycleComponent lc) {
    String name = lc.getName();
    checkNotNull(name);

    String initCmd = lc.getInitCmd();

    String preInstallCmd = lc.getPreInstallCmd();
    String installCmd = lc.getInstallCmd();
    String postInstallCmd = lc.getPostInstallCmd();

    String preStartCmd = lc.getPreStartCmd();
    String startCmd = lc.getStartCmd();
    checkNotNull(startCmd);
    String startDetectionCmd = lc.getStartDetectionCmd();
    String postStartCmd = lc.getPostStartCmd();

    String preStopCmd = lc.getPreStopCmd();
    String stopCmd = lc.getStopCmd();
    String stopDetectionCmd = lc.getStopDetectionCmd();
    String postStopCmd = lc.getPostStopCmd();

    String shutdownCmd = lc.getShutdownCmd();

    log.info("Executing Create Lifecycle Component task for component {}", name);

    de.uniulm.omi.cloudiator.colosseum.client.entities.LifecycleComponent lcEntity =
      new de.uniulm.omi.cloudiator.colosseum.client.entities.LifecycleComponent(
        name, initCmd, preInstallCmd, installCmd, postInstallCmd, startCmd, startDetectionCmd,
        stopDetectionCmd, preStartCmd, postStartCmd, preStopCmd, stopCmd, postStopCmd, shutdownCmd);

    lcEntity = api.createLifecycleComponent(lcEntity);
    context.addLifecycleComponent(lcEntity);

    log.info("Lifecycle Component {} was successfully created at {}", name, lcEntity.getSelfLink());
  }

  @Override
  public void update(LifecycleComponent lc) {
    // TODO
  }

  @Override
  public void delete(LifecycleComponent lc) {
    // TODO
  }
}
