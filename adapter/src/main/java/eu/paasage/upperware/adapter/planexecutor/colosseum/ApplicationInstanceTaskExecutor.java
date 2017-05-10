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
import eu.paasage.upperware.adapter.plangenerator.model.ApplicationInstance;
import eu.paasage.upperware.adapter.plangenerator.tasks.ApplicationInstanceTask;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.concurrent.Future;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

@Slf4j
public class ApplicationInstanceTaskExecutor extends ColosseumTaskExecutor<ApplicationInstance> {

  ApplicationInstanceTaskExecutor(ApplicationInstanceTask task, Set<Future> predecessors, ColosseumApi api,
                                  ColosseumConfigApi configApi, ColosseumContext context) {
    super(task, predecessors, api, configApi, context);
  }

  @Override
  public void create(ApplicationInstance appInst) {
    String name = appInst.getName();
    checkNotNull(name);
    String appName = appInst.getAppName();
    checkNotNull(appName);

    log.info("Executing Create Application Instance {} task for application {}", name, appName);

    de.uniulm.omi.cloudiator.colosseum.client.entities.Application appEntity = context.getApplication(appName)
      .orElseThrow(() -> new IllegalStateException(format("Application %s does not exist in Colosseum - instance cannot be created", appName)));

    Long appId = appEntity.getId();
    checkNotNull(appId);

    de.uniulm.omi.cloudiator.colosseum.client.entities.ApplicationInstance appInstEntity =
      new de.uniulm.omi.cloudiator.colosseum.client.entities.ApplicationInstance(appId);
    appInstEntity = api.createApplicationInstance(appInstEntity);
    context.addApplicationInstance(appInstEntity);

    log.info("Application Instance {} of application {} was successfully created at {}", name, appName, appInstEntity.getSelfLink());
  }

  @Override
  public void update(ApplicationInstance appInst) {
    throw new UnsupportedOperationException("Cannot update instance of any component - this method should not be run at all");
  }

  @Override
  public void delete(ApplicationInstance appInst) {
    String name = appInst.getName();
    checkNotNull(name);
    String appName = appInst.getAppName();
    checkNotNull(appName);

    log.info("Executing Delete Application Instance {} task of application {}", name, appName);

    de.uniulm.omi.cloudiator.colosseum.client.entities.ApplicationInstance appInstEntity = context.getApplicationInstance(appName)
      .orElseThrow(() -> new IllegalStateException(format("Application Instance of application %s does not exist in Colosseum - cannot be deleted", appName)));
    api.deleteApplicationInstance(appInstEntity);
    context.deleteApplicationInstance(appInstEntity);

    log.info("Application Instance {} of application {} was successfully deleted from {}", name, appName, appInstEntity.getSelfLink());
  }
}
