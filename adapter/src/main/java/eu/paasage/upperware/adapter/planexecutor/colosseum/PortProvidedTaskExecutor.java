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
import eu.paasage.upperware.adapter.executioncontext.colosseum.ColosseumContext;
import eu.paasage.upperware.adapter.plangenerator.model.PortProvided;
import eu.paasage.upperware.adapter.plangenerator.tasks.PortProvidedTask;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.concurrent.Future;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

@Slf4j
public class PortProvidedTaskExecutor extends ColosseumTaskExecutor<PortProvided> {

  PortProvidedTaskExecutor(PortProvidedTask task, Collection<Future> predecessors, ColosseumApi api, ColosseumContext context) {
    super(task, predecessors, api, context);
  }

  @Override
  public void create(PortProvided portProv) {
    String name = portProv.getName();
    checkNotNull(name);
    String acName = portProv.getAcName();
    checkNotNull(acName);

    Integer port = portProv.getPort();
    checkNotNull(port);

    String cloudName = portProv.getCloudName();
    checkNotNull(cloudName);
    String appName = portProv.getAppName();
    checkNotNull(appName);
    String lcName = portProv.getLcName();
    checkNotNull(lcName);
    String vmName = portProv.getVmName();
    checkNotNull(vmName);

    String location = portProv.getLocation();
    checkNotNull(location);
    String hardware = portProv.getHardware();
    checkNotNull(hardware);
    String image = portProv.getImage();
    checkNotNull(image);

    log.info("Executing Create Port Provided task for port {}", name);

    Cloud cloudEntity = context.getCloud(cloudName).orElseThrow(() -> new IllegalStateException(
      format("Cloud %s was not configured in Colosseum - port provided cannot be created", cloudName)));

    Long cloudId = cloudEntity.getId();
    checkNotNull(cloudId);

    Location locationEntity = api.getLocation(cloudId, location);
    if (locationEntity == null) {
      throw new IllegalArgumentException(format("Location %s in cloud %s (id=%s) does not exist in Colosseum " +
        "- port provided cannot be created", location, cloudName, cloudId));
    }

    Hardware hardwareEntity = api.getHardware(cloudId, hardware);
    if (hardwareEntity == null) {
      throw new IllegalArgumentException(format("Hardware %s in cloud %s (id=%s) does not exist in Colosseum " +
        "- port provided cannot be created", hardware, cloudName, cloudId));
    }

    Image imageEntity = api.getImage(cloudId, image);
    if (imageEntity == null) {
      throw new IllegalArgumentException(format("Image %s in cloud %s (id=%s) does not exist in Colosseum " +
        "- port provided cannot be created", image, cloudName, cloudId));
    }

    Long locationId = locationEntity.getId();
    checkNotNull(locationId);
    Long hardwareId = hardwareEntity.getId();
    checkNotNull(hardwareId);
    Long imageId = imageEntity.getId();
    checkNotNull(imageId);

    LifecycleComponent lcComponent = context.getLifecycleComponent(lcName)
      .orElseThrow(() -> new IllegalStateException(format("Lifecycle component %s does not exist in Colosseum " +
        "- port provided cannot be created", lcName)));

    VirtualMachineTemplate vmEntity = context.getVirtualMachine(cloudId, locationId, hardwareId, imageId)
      .orElseThrow(() -> new IllegalStateException(format("Virtual Machine %s (cloudId=%s, locationId=%s, hardwareId=%s, imageId=%s) " +
        "does not exist in Colosseum - port provided cannot be created", vmName, cloudId, locationId, hardwareId, imageId)));

    Long lcId = lcComponent.getId();
    checkNotNull(lcId);
    Long vmId = vmEntity.getId();
    checkNotNull(vmId);

    ApplicationComponent acEntity = context.getApplicationComponent(appName, lcId, vmId)
      .orElseThrow(() -> new IllegalStateException(format("Application component %s (appName=%s, lcId=%s, vmId=%s) " +
        "does not exist in Colosseum - port provided cannot be created", acName, appName, lcId, vmId)));

    Long acId = acEntity.getId();
    checkNotNull(acId);

    de.uniulm.omi.cloudiator.colosseum.client.entities.PortProvided portProvEntity =
      new de.uniulm.omi.cloudiator.colosseum.client.entities.PortProvided(name, acId, port);
    portProvEntity = api.createPortProvided(portProvEntity);
    context.addPortProvided(portProvEntity);

    log.info("Port Provided {} was successfully created at {}", name, portProvEntity.getSelfLink());
  }

  @Override
  public void update(PortProvided portProv) {
    throw new UnsupportedOperationException("Cannot update port provided - this method should not be run at all");
  }

  @Override
  public void delete(PortProvided portProv) {
    String name = portProv.getName();
    checkNotNull(name);

    log.info("Executing Delete Port Provided task for port {}", name);

    de.uniulm.omi.cloudiator.colosseum.client.entities.PortProvided portProvEntity = context.getPortProvided(name)
      .orElseThrow(() -> new IllegalStateException(format("Port provided %s does not exist in Colosseum " +
        "- cannot be deleted", name)));
    api.deletePortProvided(portProvEntity);
    context.deletePortProvided(portProvEntity);

    log.info("Port Provided {} was successfully deleted from {}", name, portProvEntity.getSelfLink());
  }
}
