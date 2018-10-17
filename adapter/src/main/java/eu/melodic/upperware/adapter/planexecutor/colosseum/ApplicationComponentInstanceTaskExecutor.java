/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.planexecutor.colosseum;

import de.uniulm.omi.cloudiator.colosseum.client.entities.*;
import eu.melodic.upperware.adapter.communication.colosseum.ColosseumApi;
import eu.melodic.upperware.adapter.executioncontext.colosseum.ColosseumContext;
import eu.melodic.upperware.adapter.plangenerator.model.ApplicationComponentInstance;
import eu.melodic.upperware.adapter.plangenerator.tasks.ApplicationComponentInstanceTask;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.Future;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

@Slf4j
public class ApplicationComponentInstanceTaskExecutor extends ColosseumTaskExecutor<ApplicationComponentInstance> {

  ApplicationComponentInstanceTaskExecutor(ApplicationComponentInstanceTask task, Collection<Future> predecessors,
          ColosseumApi api, ColosseumContext context) {
    super(task, predecessors, api, context);
  }

  @Override
  public void create(ApplicationComponentInstance acInst) {
    String name = checkNotNull(acInst.getName());
    String acName = checkNotNull(acInst.getAcName());
    String vmInstName = checkNotNull(acInst.getVmInstName());

    String cloudName = checkNotNull(acInst.getCloudName());
    String appName = checkNotNull(acInst.getAppName());
    String lcName = checkNotNull(acInst.getLcName());
    String vmName = checkNotNull(acInst.getVmName());

    String location = checkNotNull(acInst.getLocation());
    String hardware = checkNotNull(acInst.getHardware());
    String image = checkNotNull(acInst.getImage());

    log.info("Executing Create Application Component Instance {} task for application component {}", name, acName);

    Cloud cloudEntity = context.getCloud(cloudName).orElseThrow(() -> new IllegalStateException(
      format("Cloud %s was not configured in Colosseum - application component instance cannot be created", cloudName)));

    Long cloudId = cloudEntity.getId();
    checkNotNull(cloudId);

    Location locationEntity = api.getLocation(cloudId, location);
    if (locationEntity == null) {
      throw new IllegalArgumentException(format("Location %s in cloud %s (id=%s) does not exist in Colosseum " +
        "- application component instance cannot be created", location, cloudName, cloudId));
    }

    Hardware hardwareEntity = api.getHardware(cloudId, hardware);
    if (hardwareEntity == null) {
      throw new IllegalArgumentException(format("Hardware %s in cloud %s (id=%s) does not exist in Colosseum " +
        "- application component instance cannot be created", hardware, cloudName, cloudId));
    }

    Image imageEntity = api.getImage(cloudId, image);
    if (imageEntity == null) {
      throw new IllegalArgumentException(format("Image %s in cloud %s (id=%s) does not exist in Colosseum " +
        "- application component instance cannot be created", image, cloudName, cloudId));
    }

    Long locationId = checkNotNull(locationEntity.getId());
    Long hardwareId = checkNotNull(hardwareEntity.getId());
    Long imageId = checkNotNull(imageEntity.getId());

    LifecycleComponent lcEntity = context.getLifecycleComponent(lcName)
      .orElseThrow(() -> new IllegalStateException(format("Lifecycle component %s does not exist in Colosseum " +
        "- application component instance cannot be created", lcName)));

    VirtualMachineTemplate vmEntity = context.getVirtualMachine(cloudId, locationId, hardwareId, imageId)
      .orElseThrow(() -> new IllegalStateException(format("Virtual Machine %s (cloudId=%s, locationId=%s, hardwareId=%s, imageId=%s) " +
        "does not exist in Colosseum - application component instance cannot be created", vmName, cloudId, locationId, hardwareId, imageId)));

    Long lcId = checkNotNull(lcEntity.getId());
    Long vmId = checkNotNull(vmEntity.getId());

    ApplicationComponent acEntity = context.getApplicationComponent(appName, lcId, vmId)
      .orElseThrow(() -> new IllegalStateException(format("Application component %s (appName=%s, lcId=%s, vmId=%s) " +
        "does not exist in Colosseum - application component instance cannot be created", acName, appName, lcId, vmId)));

    ApplicationInstance appInstEntity = context.getApplicationInstance(appName)
      .orElseThrow(() -> new IllegalStateException(format("Application instance %s does not exist on Colosseum " +
        "- application component instance cannot be created", appName)));

    VirtualMachine vmInstEntity = context.getVirtualMachineInstance(vmInstName)
      .orElseThrow(() -> new IllegalStateException(format("Virtual Machine Instance %s does not exist in Colosseum " +
        "- application component instance cannot be created", vmInstName)));

    Long acId = checkNotNull(acEntity.getId());
    Long appInstId = checkNotNull(appInstEntity.getId());
    Long vmInstId = checkNotNull(vmInstEntity.getId());

    if (context.getApplicationComponentInstance(appInstId, vmInstId).isPresent()) {
      log.warn("Application Component Instance with params (appInstId={}, vmInstId={}) already exists " +
        "in Colosseum - skipping execution of the task", appInstId, vmInstId);
      return;
    }

    Instance acInstEntity = new Instance(null, null, null, null, acId, appInstId, vmInstId);
    acInstEntity = api.createApplicationComponentInstance(acInstEntity);
    context.addApplicationComponentInstance(acInstEntity);

    log.info("Application Component Instance {} was successfully created at {}", name, acInstEntity.getSelfLink());
  }

  @Override
  public void update(ApplicationComponentInstance acInst) {
    throw new UnsupportedOperationException("Cannot update instance of any component - this method should not be run at all");
  }

  @Override
  public void delete(ApplicationComponentInstance acInst) {
    String name = checkNotNull(acInst.getName());
    String acName = checkNotNull(acInst.getAcName());
    String vmInstName = checkNotNull(acInst.getVmInstName());

    String cloudName = checkNotNull(acInst.getCloudName());
    String appName = checkNotNull(acInst.getAppName());
    String lcName = checkNotNull(acInst.getLcName());
    String vmName = checkNotNull(acInst.getVmName());

    String location = checkNotNull(acInst.getLocation());
    String hardware = checkNotNull(acInst.getHardware());
    String image = checkNotNull(acInst.getImage());

    log.info("Executing Delete Application Component Instance {} task for application component {}", name, acName);

    Cloud cloudEntity = context.getCloud(cloudName).orElseThrow(() -> new IllegalStateException(
      format("Cloud %s was not configured in Colosseum - application component instance cannot be deleted", cloudName)));

    Long cloudId = checkNotNull(cloudEntity.getId());

    Location locationEntity = api.getLocation(cloudId, location);
    if (locationEntity == null) {
      throw new IllegalArgumentException(format("Location %s in cloud %s (id=%s) does not exist in Colosseum " +
        "- application component instance cannot be deleted", location, cloudName, cloudId));
    }

    Hardware hardwareEntity = api.getHardware(cloudId, hardware);
    if (hardwareEntity == null) {
      throw new IllegalArgumentException(format("Hardware %s in cloud %s (id=%s) does not exist in Colosseum " +
        "- application component instance cannot be deleted", hardware, cloudName, cloudId));
    }

    Image imageEntity = api.getImage(cloudId, image);
    if (imageEntity == null) {
      throw new IllegalArgumentException(format("Image %s in cloud %s (id=%s) does not exist in Colosseum " +
        "- application component instance cannot be deleted", image, cloudName, cloudId));
    }

    Long locationId = checkNotNull(locationEntity.getId());
    Long hardwareId = checkNotNull(hardwareEntity.getId());
    Long imageId = checkNotNull(imageEntity.getId());

    LifecycleComponent lcEntity = context.getLifecycleComponent(lcName)
      .orElseThrow(() -> new IllegalStateException(format("Lifecycle component %s does not exist in Colosseum " +
        "- application component instance cannot be deleted", lcName)));

    VirtualMachineTemplate vmEntity = context.getVirtualMachine(cloudId, locationId, hardwareId, imageId)
      .orElseThrow(() -> new IllegalStateException(format("Virtual Machine %s (cloudId=%s, locationId=%s, hardwareId=%s, imageId=%s) " +
        "does not exist in Colosseum - application component instance cannot be deleted", vmName, cloudId, locationId, hardwareId, imageId)));

    Long lcId = checkNotNull(lcEntity.getId());
    Long vmId = checkNotNull(vmEntity.getId());

    ApplicationComponent acEntity = context.getApplicationComponent(appName, lcId, vmId)
      .orElseThrow(() -> new IllegalStateException(format("Application component %s (appName=%s, lcId=%s, vmId=%s) " +
        "does not exist in Colosseum - application component instance cannot be deleted", acName, appName, lcId, vmId)));

    ApplicationInstance appInstEntity = context.getApplicationInstance(appName)
      .orElseThrow(() -> new IllegalStateException(format("Application instance %s does not exist on Colosseum " +
        "- application component instance cannot be deleted", appName)));

    VirtualMachine vmInstEntity = context.getVirtualMachineInstance(vmInstName)
      .orElseThrow(() -> new IllegalStateException(format("Virtual Machine Instance %s does not exist in Colosseum " +
        "- application component instance cannot be deleted", vmInstName)));

    Long acId = checkNotNull(acEntity.getId());
    Long appInstId = checkNotNull(appInstEntity.getId());
    Long vmInstId = checkNotNull(vmInstEntity.getId());

    Optional<Instance> acInstEntityOptional = context.getApplicationComponentInstance(appInstId, vmInstId);

    if (acInstEntityOptional.isPresent()) {
      Instance acInstEntity = acInstEntityOptional.get();
      api.deleteApplicationComponentInstance(acInstEntity);
      context.deleteApplicationComponentInstance(acInstEntity);

      log.info("Application Component Instance {} was successfully deleted from {}", name, acInstEntity.getSelfLink());
    } else {
      log.warn("Application Component Instance {} (acId={}, appInstId={}, vmInstId={}) does not exist in Colosseum - " +
              "cannot be deleted - skipping execution of the task", name, acId, appInstId, vmInstId);
    }
  }
}
