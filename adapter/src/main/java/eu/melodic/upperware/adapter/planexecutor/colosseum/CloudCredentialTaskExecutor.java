/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.planexecutor.colosseum;

import de.uniulm.omi.cloudiator.colosseum.client.entities.Cloud;
import eu.melodic.upperware.adapter.communication.colosseum.ColosseumApi;
import eu.melodic.upperware.adapter.executioncontext.colosseum.ColosseumContext;
import eu.melodic.upperware.adapter.plangenerator.model.CloudCredential;
import eu.melodic.upperware.adapter.plangenerator.tasks.CloudCredentialTask;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.concurrent.Future;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

@Deprecated
@Slf4j
public class CloudCredentialTaskExecutor extends ColosseumTaskExecutor<CloudCredential> {

  CloudCredentialTaskExecutor(CloudCredentialTask task, Collection<Future> predecessors, ColosseumApi api, ColosseumContext context) {
    super(task, predecessors, api, context);
  }

  @Override
  public void create(CloudCredential cloudCredential) {
    String name = cloudCredential.getName();
    checkNotNull(name);
    String cloudName = cloudCredential.getCloudName();
    checkNotNull(cloudName);

    String login = cloudCredential.getLogin();
    checkNotNull(login);
    String password = cloudCredential.getPassword();
    checkNotNull(password);
    Long tenant = cloudCredential.getTenant();
    checkNotNull(tenant);

    log.info("Executing Create Cloud Credential task {}", name);

    Cloud cloudEntity = context.getCloud(cloudName).orElseThrow(() -> new IllegalStateException(
      format("Cloud %s was not configured in Colosseum - Cloud Credential cannot be created", cloudName)));

    Long cloudId = cloudEntity.getId();
    checkNotNull(cloudId);

    if (context.getCloudCredential(cloudId).isPresent()) {
      log.warn("Cloud Credential of the cloud {} already exists in Colosseum - skipping execution of the task", cloudName);
      return;
    }

    de.uniulm.omi.cloudiator.colosseum.client.entities.CloudCredential cloudCredentialEntity
      = new de.uniulm.omi.cloudiator.colosseum.client.entities.CloudCredential(login, password, cloudId, tenant);
    cloudCredentialEntity = api.createCloudCredential(cloudCredentialEntity);
    context.addCloudCredential(cloudCredentialEntity);

    log.info("Cloud Credential {} was successfully created", name);
  }

  @Override
  public void delete(CloudCredential cloudCredential) {
    throw new UnsupportedOperationException("Cannot delete Cloud Credential - this method should not be run at all");
  }
}
