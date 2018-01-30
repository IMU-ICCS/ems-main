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
import eu.melodic.upperware.adapter.plangenerator.model.LifecycleComponent;
import eu.melodic.upperware.adapter.plangenerator.tasks.LifecycleComponentTask;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.Future;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
public class LifecycleComponentTaskExecutor extends ColosseumTaskExecutor<LifecycleComponent> {

  LifecycleComponentTaskExecutor(LifecycleComponentTask task, Collection<Future> predecessors,
          ColosseumApi api, ColosseumContext context) {
    super(task, predecessors, api, context);
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

    if (context.getLifecycleComponent(name).isPresent()) {
      log.warn("Lifecycle Component {} already exists in Colosseum - skipping execution of the task", name);
      return;
    }

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
    throw new UnsupportedOperationException("Cannot update Lifecycle Component - this method should not be run at all");
  }

  @Override
  public void delete(LifecycleComponent lc) {
    String name = lc.getName();
    checkNotNull(name);

    log.info("Executing Delete Lifecycle Component task for component {}", name);

    Optional<de.uniulm.omi.cloudiator.colosseum.client.entities.LifecycleComponent> lcEntityOptional = context.getLifecycleComponent(name);

    if (lcEntityOptional.isPresent()) {
      de.uniulm.omi.cloudiator.colosseum.client.entities.LifecycleComponent lcEntity = lcEntityOptional.get();
      api.deleteLifecycleComponent(lcEntity);
      context.deleteLifecycleComponent(lcEntity);

      log.info("Lifecycle Component {} was successfully deleted from {}", name, lcEntity.getSelfLink());
    } else {
      log.warn("Lifecycle Component {} does not exist in Colosseum - cannot be deleted - skipping execution of the task", name);
    }
  }
}
