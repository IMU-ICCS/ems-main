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
import de.uniulm.omi.cloudiator.colosseum.client.entities.CloudCredential;
import de.uniulm.omi.cloudiator.colosseum.client.entities.CloudProperty;
import eu.paasage.upperware.adapter.communication.colosseum.ColosseumApi;
import eu.paasage.upperware.adapter.communication.colosseum.ColosseumConfigApi;
import eu.paasage.upperware.adapter.executioncontext.colosseum.ColosseumContext;
import eu.paasage.upperware.adapter.plangenerator.model.Cloud;
import eu.paasage.upperware.adapter.plangenerator.tasks.CloudTask;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
public class CloudTaskExecutor extends ColosseumTaskExecutor<Cloud> {

  CloudTaskExecutor(CloudTask task, Set<Future> predecessors, ColosseumApi api,
                    ColosseumConfigApi configApi, ColosseumContext context) {
    super(task, predecessors, api, configApi, context);
  }

  @Override
  public void create(Cloud cloud) {
    String name = cloud.getName();
    checkNotNull(name);

    String login = cloud.getLogin();
    checkNotNull(login);
    String password = cloud.getPassword();
    checkNotNull(password);
    Long tenant = cloud.getTenant();
    checkNotNull(tenant);

    String endpoint = cloud.getEndpoint();
    checkNotNull(endpoint);
    String provider = cloud.getProvider();
    checkNotNull(provider);
    String driver = cloud.getDriver();
    checkNotNull(driver);

    log.info("Executing Create Cloud task for cloud {}", name);

    Api apiEntity = new Api(driver, provider);
    apiEntity = configApi.createApi(apiEntity);
    context.addApi(apiEntity);

    Long apiId = apiEntity.getId();
    checkNotNull(apiId);

    de.uniulm.omi.cloudiator.colosseum.client.entities.Cloud cloudEntity =
      new de.uniulm.omi.cloudiator.colosseum.client.entities.Cloud(name, endpoint, apiId);
    cloudEntity = configApi.createCloud(cloudEntity);
    context.addCloud(cloudEntity);

    Long cloudId = cloudEntity.getId();
    checkNotNull(cloudId);

    Map<String, String> filters = cloud.getFilters();
    if (filters != null) {
      filters.forEach((key, value) -> {
        CloudProperty cloudPropertyEntity = new CloudProperty(key, value, cloudId);
        configApi.createCloudProperty(cloudPropertyEntity);
        context.addCloudProperty(cloudPropertyEntity);
      });
    }

    CloudCredential cloudCredentialEntity = new CloudCredential(login, password, cloudId, tenant);
    cloudCredentialEntity = configApi.createCloudCredential(cloudCredentialEntity);
    context.addCloudCredential(cloudCredentialEntity);

    log.info("Cloud {} was successfully configured", name);
  }

  @Override
  public void update(Cloud cloud) {
    throw new UnsupportedOperationException("Cannot update cloud configuration - this method should not be run at all");
  }

  @Override
  public void delete(Cloud cloud) {
    // TODO
  }
}
