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
import eu.melodic.upperware.adapter.plangenerator.model.CloudProperty;
import eu.melodic.upperware.adapter.communication.colosseum.ColosseumApi;
import eu.melodic.upperware.adapter.executioncontext.colosseum.ColosseumContext;
import eu.melodic.upperware.adapter.plangenerator.tasks.CloudPropertyTask;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Future;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

@Slf4j
public class CloudPropertyTaskExecutor extends ColosseumTaskExecutor<CloudProperty> {

  CloudPropertyTaskExecutor(CloudPropertyTask task, Collection<Future> predecessors, ColosseumApi api, ColosseumContext context) {
    super(task, predecessors, api, context);
  }

  @Override
  public void create(CloudProperty cloudProperty) {
    String name = cloudProperty.getName();
    checkNotNull(name);
    String cloudName = cloudProperty.getCloudName();
    checkNotNull(cloudName);

    log.info("Executing Create Cloud Property task {}", name);

    Map<String, String> filters = cloudProperty.getFilters();
    if (filters != null) {
      Cloud cloudEntity = context.getCloud(cloudName).orElseThrow(() -> new IllegalStateException(
        format("Cloud %s was not configured in Colosseum - Cloud Property cannot be created", cloudName)));

      Long cloudId = cloudEntity.getId();
      checkNotNull(cloudId);

      filters.forEach((key, value) -> {
        if (!context.getCloudProperty(cloudId, key).isPresent()) {
          de.uniulm.omi.cloudiator.colosseum.client.entities.CloudProperty cloudPropertyEntity
            = new de.uniulm.omi.cloudiator.colosseum.client.entities.CloudProperty(key, value, cloudId);
          api.createCloudProperty(cloudPropertyEntity);
          context.addCloudProperty(cloudPropertyEntity);
        } else {
          log.warn("Cloud Property {} of the cloud {} already exists in Colosseum - skipping adding of the property",
            key, cloudName);
        }
      });
    }

    log.info("Cloud Property {} was successfully created", name);
  }

  @Override
  public void update(CloudProperty cloudProperty) {
    String name = cloudProperty.getName();
    checkNotNull(name);
    String cloudName = cloudProperty.getCloudName();
    checkNotNull(cloudName);

    log.info("Executing Update Cloud Property task {}", name);

    Map<String, String> filters = cloudProperty.getFilters();
    if (filters != null) {
      Cloud cloudEntity = context.getCloud(cloudName).orElseThrow(() -> new IllegalStateException(
        format("Cloud %s was not configured in Colosseum - Cloud Property cannot be updated", cloudName)));

      Long cloudId = cloudEntity.getId();
      checkNotNull(cloudId);

      filters.forEach((key, value) -> {
        de.uniulm.omi.cloudiator.colosseum.client.entities.CloudProperty cloudPropertyEntity
          = context.getCloudProperty(cloudId, key).orElseThrow(() -> new IllegalStateException(
          format("Cloud Property %s of Cloud %s was not configured in Colosseum - cannot be updated", key, cloudName)));

        cloudPropertyEntity.setValue(value);
        api.updateCloudProperty(cloudPropertyEntity);
      });
    }

    log.info("Cloud Property {} was successfully updated", name);
  }

  @Override
  public void delete(CloudProperty cloudProperty) {
    String name = cloudProperty.getName();
    checkNotNull(name);
    String cloudName = cloudProperty.getCloudName();
    checkNotNull(cloudName);

    log.info("Executing Delete Cloud Property task {}", name);

    Map<String, String> filters = cloudProperty.getFilters();
    filters.forEach((key, value) -> {
      Cloud cloudEntity = context.getCloud(cloudName).orElseThrow(() -> new IllegalStateException(
        format("Cloud %s was not configured in Colosseum - Cloud Property cannot be deleted", cloudName)));

      Long cloudId = cloudEntity.getId();
      checkNotNull(cloudId);

      de.uniulm.omi.cloudiator.colosseum.client.entities.CloudProperty cloudPropertyEntity
        = context.getCloudProperty(cloudId, key).orElseThrow(() -> new IllegalStateException(
        format("Cloud Property %s of Cloud %s was not configured in Colosseum - cannot be deleted", key, cloudName)));

      api.deleteCloudProperty(cloudPropertyEntity);
      context.deleteCloudProperty(cloudPropertyEntity);
    });

    log.info("Cloud Property {} was successfully deleted", name);
  }
}
