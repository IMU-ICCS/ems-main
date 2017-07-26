/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.planexecutor.colosseum;

import de.uniulm.omi.cloudiator.colosseum.client.entities.Api;
import eu.paasage.upperware.adapter.communication.colosseum.ColosseumApi;
import eu.paasage.upperware.adapter.executioncontext.colosseum.ColosseumContext;
import eu.paasage.upperware.adapter.plangenerator.model.CloudApi;
import eu.paasage.upperware.adapter.plangenerator.tasks.CloudApiTask;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.concurrent.Future;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

@Slf4j
public class CloudApiTaskExecutor extends ColosseumTaskExecutor<CloudApi> {

  CloudApiTaskExecutor(CloudApiTask task, Collection<Future> predecessors, ColosseumApi api, ColosseumContext context) {
    super(task, predecessors, api, context);
  }

  @Override
  public void create(CloudApi cloudApi) {
    String name = cloudApi.getName();
    checkNotNull(name);

    String driver = cloudApi.getDriver();
    checkNotNull(driver);

    log.info("Executing Create Cloud Api task {}", name);

    Api cloudApiEntity = new Api(name, driver);
    cloudApiEntity = api.createApi(cloudApiEntity);
    context.addCloudApi(cloudApiEntity);

    log.info("Cloud Api {} was successfully created", name);
  }

  @Override
  public void update(CloudApi cloudApi) {
    String name = cloudApi.getName();
    checkNotNull(name);

    String driver = cloudApi.getDriver();
    checkNotNull(driver);

    log.info("Executing Update Cloud Api task {}", name);

    Api cloudApiEntity = context.getCloudApi(name).orElseThrow(() -> new IllegalStateException(
      format("Cloud Api %s does not exist in Colosseum - cannot be updated", name)));
    cloudApiEntity.setInternalProviderName(driver);
    api.updateApi(cloudApiEntity);

    log.info("Cloud Api {} was successfully updated", name);
  }

  @Override
  public void delete(CloudApi cloudApi) {
    throw new UnsupportedOperationException("Cannot delete Cloud Api - this method should not be run at all");
  }
}
