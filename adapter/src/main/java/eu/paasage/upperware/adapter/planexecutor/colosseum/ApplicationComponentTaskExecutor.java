/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.planexecutor.colosseum;

import de.uniulm.omi.cloudiator.colosseum.client.entities.*;
import eu.paasage.upperware.adapter.communication.colosseum.ColosseumApi;
import eu.paasage.upperware.adapter.communication.colosseum.ColosseumConfigApi;
import eu.paasage.upperware.adapter.executioncontext.colosseum.ColosseumContext;
import eu.paasage.upperware.adapter.plangenerator.model.ApplicationComponent;
import eu.paasage.upperware.adapter.plangenerator.tasks.ApplicationComponentTask;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.concurrent.Future;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

@Slf4j
public class ApplicationComponentTaskExecutor extends ColosseumTaskExecutor<ApplicationComponent> {

  ApplicationComponentTaskExecutor(ApplicationComponentTask task, Collection<Future> predecessors,
                                   ColosseumApi api, ColosseumConfigApi configApi, ColosseumContext context) {
    super(task, predecessors, api, configApi, context);
  }

  @Override
  public void create(ApplicationComponent ac) {
    String name = ac.getName();
    checkNotNull(name);
    String appName = ac.getAppName();
    checkNotNull(appName);
    String lcName = ac.getLcName();
    checkNotNull(lcName);
    String vmName = ac.getVmName();
    checkNotNull(vmName);

    String cloudName = ac.getCloudName();
    checkNotNull(cloudName);

    String location = ac.getLocation();
    checkNotNull(location);
    String hardware = ac.getHardware();
    checkNotNull(hardware);
    String image = ac.getImage();
    checkNotNull(image);

    log.debug("Executing Create Application Component task for component {}", name);

    Cloud cloudEntity = context.getCloud(cloudName).orElseThrow(() -> new IllegalStateException(
      format("Cloud %s was not configured in Colosseum - application component cannot be created", cloudName)));

    Long cloudId = cloudEntity.getId();
    checkNotNull(cloudId);

    Location locationEntity = configApi.getLocation(cloudId, location)
      .orElseThrow(() -> new IllegalArgumentException(format(
        "Location %s in cloud %s (id=%s) does not exist in Colosseum - application component cannot be created", location, cloudName, cloudId)));

    Hardware hardwareEntity = configApi.getHardware(cloudId, hardware)
      .orElseThrow(() -> new IllegalArgumentException(format(
        "Hardware %s in cloud %s (id=%s) does not exist in Colosseum - application component cannot be created", hardware, cloudName, cloudId)));

    Image imageEntity = configApi.getImage(cloudId, image)
      .orElseThrow(() -> new IllegalArgumentException(format(
        "Image %s in cloud %s (id=%s) does not exist in Colosseum - application component cannot be created", image, cloudName, cloudId)));

    Long locationId = locationEntity.getId();
    checkNotNull(locationId);
    Long hardwareId = hardwareEntity.getId();
    checkNotNull(hardwareId);
    Long imageId = imageEntity.getId();
    checkNotNull(imageId);

    Application appEntity = context.getApplication(appName)
      .orElseThrow(() -> new IllegalStateException(format(
        "Application %s does not exist in Colosseum - application component cannot be created", appName)));

    LifecycleComponent lcComponent = context.getLifecycleComponent(lcName)
      .orElseThrow(() -> new IllegalStateException(format(
        "Lifecycle component %s does not exist in Colosseum - application component cannot be created", lcName)));


    VirtualMachineTemplate vmEntity = context.getVirtualMachine(cloudId, locationId, hardwareId, imageId)
      .orElseThrow(() -> new IllegalStateException(format(
        "Virtual Machine %s (cloudId=%s, locationId=%s, hardwareId=%s, imageId=%s) does not exist in Colosseum " +
          "- application component cannot be created", vmName, cloudId, locationId, hardwareId, imageId)));

    Long appId = appEntity.getId();
    checkNotNull(appId);
    Long lcId = lcComponent.getId();
    checkNotNull(lcId);
    Long vmId = vmEntity.getId();
    checkNotNull(vmId);

    de.uniulm.omi.cloudiator.colosseum.client.entities.ApplicationComponent acEntity =
      new de.uniulm.omi.cloudiator.colosseum.client.entities.ApplicationComponent(appId, lcId, vmId);

    acEntity = api.createApplicationComponent(acEntity);
    context.addApplicationComponent(acEntity);

    log.debug("Application Component {} was successfully created at {}", name, acEntity.getSelfLink());
  }

  @Override
  public void update(ApplicationComponent ac) {
    // TODO
  }

  @Override
  public void delete(ApplicationComponent ac) {
    // TODO
  }
}
