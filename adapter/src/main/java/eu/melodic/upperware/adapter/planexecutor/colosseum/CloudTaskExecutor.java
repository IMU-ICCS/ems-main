/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.planexecutor.colosseum;

import de.uniulm.omi.cloudiator.colosseum.client.entities.Api;
import eu.melodic.upperware.adapter.communication.colosseum.ColosseumApi;
import eu.melodic.upperware.adapter.executioncontext.colosseum.ColosseumContext;
import eu.melodic.upperware.adapter.plangenerator.model.Cloud;
import eu.melodic.upperware.adapter.plangenerator.tasks.CloudTask;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.concurrent.Future;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

@Slf4j
public class CloudTaskExecutor extends ColosseumTaskExecutor<Cloud> {

  CloudTaskExecutor(CloudTask task, Collection<Future> predecessors, ColosseumApi api, ColosseumContext context) {
    super(task, predecessors, api, context);
  }

  @Override
  public void create(Cloud cloud) {
    String name = cloud.getName();
    checkNotNull(name);
    String apiName = cloud.getApiName();
    checkNotNull(apiName);

    String endpoint = cloud.getEndpoint();
    checkNotNull(endpoint);

    log.info("Executing Create Cloud task {}", name);

    if (context.getCloud(name).isPresent()) {
      log.warn("Cloud {} already exists in Colosseum - skipping execution of the task", name);
      return;
    }

    Api cloudApiEntity = context.getCloudApi(apiName).orElseThrow(() -> new IllegalStateException(
      format("Cloud Api %s does not exist in Colosseum - cloud cannot be created", apiName)));

    Long apiId = cloudApiEntity.getId();
    checkNotNull(apiId);

    de.uniulm.omi.cloudiator.colosseum.client.entities.Cloud cloudEntity =
      new de.uniulm.omi.cloudiator.colosseum.client.entities.Cloud(name, endpoint, apiId);
    cloudEntity = api.createCloud(cloudEntity);
    context.addCloud(cloudEntity);

    log.info("Cloud {} was successfully created", name);
  }

  @Override
  public void update(Cloud cloud) {
    String name = cloud.getName();
    checkNotNull(name);
    String apiName = cloud.getApiName();
    checkNotNull(apiName);

    String endpoint = cloud.getEndpoint();
    checkNotNull(endpoint);

    log.info("Executing Update Cloud task {}", name);

    Api cloudApiEntity = context.getCloudApi(apiName).orElseThrow(() -> new IllegalStateException(
      format("Cloud Api %s does not exist in Colosseum - cloud cannot be updated", apiName)));

    Long apiId = cloudApiEntity.getId();
    checkNotNull(apiId);

    de.uniulm.omi.cloudiator.colosseum.client.entities.Cloud cloudEntity
      = context.getCloud(name).orElseThrow(() -> new IllegalStateException(
        format("Cloud %s was not configured in Colosseum - cloud cannot be created", name)));
    cloudEntity.setApi(apiId);
    cloudEntity.setEndpoint(endpoint);
    api.updateCloud(cloudEntity);

    log.info("Cloud {} was successfully updated", name);
  }

  @Override
  public void delete(Cloud cloud) {
    throw new UnsupportedOperationException("Cannot delete Cloud - this method should not be run at all");
  }
}
