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
import eu.melodic.upperware.adapter.plangenerator.model.Application;
import eu.melodic.upperware.adapter.plangenerator.tasks.ApplicationTask;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.Future;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

@Slf4j
public class ApplicationTaskExecutor extends ColosseumTaskExecutor<Application> {

  ApplicationTaskExecutor(ApplicationTask task, Collection<Future> predecessors, ColosseumApi api, ColosseumContext context) {
    super(task, predecessors, api, context);
  }

  @Override
  public void create(Application app) {
    String name = app.getName();
    checkNotNull(name);

    log.info("Executing Create Application task for application {}", name);

    if (context.getApplication(name).isPresent()) {
      log.warn("Application {} already exists in Colosseum - skipping execution of the task", name);
      return;
    }

    de.uniulm.omi.cloudiator.colosseum.client.entities.Application appEntity =
      new de.uniulm.omi.cloudiator.colosseum.client.entities.Application(name);
    appEntity = api.createApplication(appEntity);
    context.addApplication(appEntity);

    log.info("Application {} was successfully created at {}", name, appEntity.getSelfLink());
  }

  @Override
  public void update(Application app) {
    String name = app.getName();
    checkNotNull(name);
    String oldName = app.getOldName();
    checkNotNull(oldName);

    log.info("Executing Update Application task for application {} -> {}", oldName, name);

    de.uniulm.omi.cloudiator.colosseum.client.entities.Application appEntity = context.getApplication(oldName)
      .orElseThrow(() -> new IllegalStateException(format("Application %s does not exist in Colosseum - cannot be updated", oldName)));
    appEntity.setName(name);
    api.updateApplication(appEntity);

    log.info("Application {} was successfully updated at {}", name, appEntity.getSelfLink());
  }

  @Override
  public void delete(Application app) {
    String name = app.getName();
    checkNotNull(name);

    log.info("Executing Delete Application task for application {}", name);

    Optional<de.uniulm.omi.cloudiator.colosseum.client.entities.Application> appEntityOptional =
      context.getApplication(name);

    if (appEntityOptional.isPresent()) {
      de.uniulm.omi.cloudiator.colosseum.client.entities.Application appEntity = appEntityOptional.get();
      api.deleteApplication(appEntity);
      context.deleteApplication(appEntity);

      log.info("Application {} was successfully deleted from {}", name, appEntity.getSelfLink());
    } else {
      log.warn("Application {} does not exist in Colosseum - cannot be deleted - skipping execution of the task", name);
    }
  }
}
