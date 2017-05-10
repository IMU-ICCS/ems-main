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
import eu.paasage.upperware.adapter.plangenerator.model.PortRequired;
import eu.paasage.upperware.adapter.plangenerator.tasks.PortRequiredTask;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.concurrent.Future;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

@Slf4j
public class PortRequiredTaskExecutor extends ColosseumTaskExecutor<PortRequired> {

  PortRequiredTaskExecutor(PortRequiredTask task, Collection<Future> predecessors, ColosseumApi api,
                           ColosseumConfigApi configApi, ColosseumContext context) {
    super(task, predecessors, api, configApi, context);
  }

  @Override
  public void create(PortRequired portReq) {
    String name = portReq.getName();
    checkNotNull(name);
    String acName = portReq.getAcName();
    checkNotNull(acName);

    String startCmd = portReq.getStartCmd();
    Boolean mandatory = portReq.getMandatory();

    String cloudName = portReq.getCloudName();
    checkNotNull(cloudName);
    String appName = portReq.getAppName();
    checkNotNull(appName);
    String lcName = portReq.getLcName();
    checkNotNull(lcName);
    String vmName = portReq.getVmName();
    checkNotNull(vmName);

    String location = portReq.getLocation();
    checkNotNull(location);
    String hardware = portReq.getHardware();
    checkNotNull(hardware);
    String image = portReq.getImage();
    checkNotNull(image);

    log.info("Executing Create Port Required task for port {}", name);

    Cloud cloudEntity = context.getCloud(cloudName).orElseThrow(() -> new IllegalStateException(
      format("Cloud %s was not configured in Colosseum - port required cannot be created", cloudName)));

    Long cloudId = cloudEntity.getId();
    checkNotNull(cloudId);

    Location locationEntity = configApi.getLocation(cloudId, location)
      .orElseThrow(() -> new IllegalArgumentException(format(
        "Location %s in cloud %s (id=%s) does not exist in Colosseum - port required cannot be created", location, cloudName, cloudId)));

    Hardware hardwareEntity = configApi.getHardware(cloudId, hardware)
      .orElseThrow(() -> new IllegalArgumentException(format(
        "Hardware %s in cloud %s (id=%s) does not exist in Colosseum - port required cannot be created", hardware, cloudName, cloudId)));

    Image imageEntity = configApi.getImage(cloudId, image)
      .orElseThrow(() -> new IllegalArgumentException(format(
        "Image %s in cloud %s (id=%s) does not exist in Colosseum - port required cannot be created", image, cloudName, cloudId)));

    Long locationId = locationEntity.getId();
    checkNotNull(locationId);
    Long hardwareId = hardwareEntity.getId();
    checkNotNull(hardwareId);
    Long imageId = imageEntity.getId();
    checkNotNull(imageId);

    LifecycleComponent lcComponent = context.getLifecycleComponent(lcName)
      .orElseThrow(() -> new IllegalStateException(format(
        "Lifecycle component %s does not exist in Colosseum - port required cannot be created", lcName)));

    VirtualMachineTemplate vmEntity = context.getVirtualMachine(cloudId, locationId, hardwareId, imageId)
      .orElseThrow(() -> new IllegalStateException(format(
        "Virtual Machine %s (cloudId=%s, locationId=%s, hardwareId=%s, imageId=%s) does not exist in Colosseum " +
          "- port required cannot be created", vmName, cloudId, locationId, hardwareId, imageId)));

    Long lcId = lcComponent.getId();
    checkNotNull(lcId);
    Long vmId = vmEntity.getId();
    checkNotNull(vmId);

    ApplicationComponent acEntity = context.getApplicationComponent(appName, lcId, vmId)
      .orElseThrow(() -> new IllegalStateException(format(
        "Application component %s (appName=%s, lcId=%s, vmId=%s) does not exist in Colosseum " +
          "- port required cannot be created", acName, appName, lcId, vmId)));

    Long acId = acEntity.getId();
    checkNotNull(acId);

    de.uniulm.omi.cloudiator.colosseum.client.entities.PortRequired portReqEntity =
      new de.uniulm.omi.cloudiator.colosseum.client.entities.PortRequired(name, acId, startCmd, mandatory);
    portReqEntity = api.createPortRequired(portReqEntity);
    context.addPortRequired(portReqEntity);

    log.info("Port Required {} was successfully created at {}", name, portReqEntity.getSelfLink());
  }

  @Override
  public void update(PortRequired pr) {
    // TODO
  }

  @Override
  public void delete(PortRequired pr) {
    // TODO
  }
}
