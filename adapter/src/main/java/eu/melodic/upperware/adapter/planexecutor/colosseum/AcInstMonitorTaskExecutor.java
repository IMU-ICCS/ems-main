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
import eu.melodic.upperware.adapter.plangenerator.model.ApplicationComponentInstanceMonitor;
import eu.melodic.upperware.adapter.plangenerator.tasks.ApplicationComponentInstanceMonitorTask;
import eu.melodic.upperware.adapter.communication.colosseum.ColosseumApi;
import eu.melodic.upperware.adapter.executioncontext.colosseum.ColosseumContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.concurrent.Future;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

@Slf4j
public class AcInstMonitorTaskExecutor extends ColosseumTaskExecutor<ApplicationComponentInstanceMonitor> {

  AcInstMonitorTaskExecutor(ApplicationComponentInstanceMonitorTask task, Collection<Future> predecessors,
          ColosseumApi api, ColosseumContext context) {
    super(task, predecessors, api, context);
  }

  @Override
  public void create(ApplicationComponentInstanceMonitor acInstMonitor) {
    monitor(acInstMonitor);
  }

  @Override
  public void update(ApplicationComponentInstanceMonitor acInstMonitor) {
    throw new UnsupportedOperationException("Cannot update AC instance monitor - this method should not be run at all");
  }

  @Override
  public void delete(ApplicationComponentInstanceMonitor acInstMonitor) {
    monitor(acInstMonitor);
  }

  private void monitor(ApplicationComponentInstanceMonitor acInstMonitor) {
    String acInstName = checkNotNull(acInstMonitor.getAcInstName());
    Long acInstTimeout = checkNotNull(acInstMonitor.getAcInstTimeout());

    String acName = checkNotNull(acInstMonitor.getAcName());
    String vmInstName = checkNotNull(acInstMonitor.getVmInstName());

    String cloudName = checkNotNull(acInstMonitor.getCloudName());
    String appName = checkNotNull(acInstMonitor.getAppName());
    String lcName = checkNotNull(acInstMonitor.getLcName());
    String vmName = checkNotNull(acInstMonitor.getVmName());

    String location = checkNotNull(acInstMonitor.getLocation());
    String hardware = checkNotNull(acInstMonitor.getHardware());
    String image = checkNotNull(acInstMonitor.getImage());

    log.info("Executing AC Instance Monitoring task {}", acInstName);

    Cloud cloudEntity = context.getCloud(cloudName).orElseThrow(() -> new IllegalStateException(
      format("Cloud %s was not configured in Colosseum - application component instance cannot be monitored", cloudName)));

    Long cloudId = cloudEntity.getId();
    checkNotNull(cloudId);

    Location locationEntity = api.getLocation(cloudId, location);
    if (locationEntity == null) {
      throw new IllegalArgumentException(format("Location %s in cloud %s (id=%s) does not exist in Colosseum " +
        "- application component instance cannot be monitored", location, cloudName, cloudId));
    }

    Hardware hardwareEntity = api.getHardware(cloudId, hardware);
    if (hardwareEntity == null) {
      throw new IllegalArgumentException(format("Hardware %s in cloud %s (id=%s) does not exist in Colosseum " +
        "- application component instance cannot be monitored", hardware, cloudName, cloudId));
    }

    Image imageEntity = api.getImage(cloudId, image);
    if (imageEntity == null) {
      throw new IllegalArgumentException(format("Image %s in cloud %s (id=%s) does not exist in Colosseum " +
        "- application component instance cannot be monitored", image, cloudName, cloudId));
    }

    Long locationId = checkNotNull(locationEntity.getId());
    Long hardwareId = checkNotNull(hardwareEntity.getId());
    Long imageId = checkNotNull(imageEntity.getId());

    LifecycleComponent lcEntity = context.getLifecycleComponent(lcName)
      .orElseThrow(() -> new IllegalStateException(format("Lifecycle component %s does not exist in Colosseum " +
        "- application component instance cannot be monitored", lcName)));

    VirtualMachineTemplate vmEntity = context.getVirtualMachine(cloudId, locationId, hardwareId, imageId)
      .orElseThrow(() -> new IllegalStateException(format("Virtual Machine %s (cloudId=%s, locationId=%s, hardwareId=%s, imageId=%s) " +
        "does not exist in Colosseum - application component instance cannot be monitored", vmName, cloudId, locationId, hardwareId, imageId)));

    Long lcId = checkNotNull(lcEntity.getId());
    Long vmId = checkNotNull(vmEntity.getId());

    ApplicationComponent acEntity = context.getApplicationComponent(appName, lcId, vmId)
      .orElseThrow(() -> new IllegalStateException(format("Application component %s (appName=%s, lcId=%s, vmId=%s) " +
        "does not exist in Colosseum - application component instance cannot be monitored", acName, appName, lcId, vmId)));

    ApplicationInstance appInstEntity = context.getApplicationInstance(appName)
      .orElseThrow(() -> new IllegalStateException(format("Application instance %s does not exist on Colosseum " +
        "- application component instance cannot be monitored", appName)));

    VirtualMachine vmInstEntity = context.getVirtualMachineInstance(vmInstName)
      .orElseThrow(() -> new IllegalStateException(format("Virtual Machine Instance %s does not exist in Colosseum " +
        "- application component instance cannot be monitored", vmInstName)));

    Long acId = checkNotNull(acEntity.getId());
    Long appInstId = checkNotNull(appInstEntity.getId());
    Long vmInstId = checkNotNull(vmInstEntity.getId());

    Instance acInstEntity = context.getApplicationComponentInstance(acId, appInstId, vmInstId)
      .orElseThrow(() -> new IllegalStateException(format("Application component instance %s (acId=%s, appInstId=%s, vmInstId=%s) " +
        "does not exist in Colosseum - cannot monitor its startup", acInstName, acId, appInstId, vmInstId)));

    if (!api.isApplicationComponentInstanceRunning(acInstEntity, acInstTimeout)) {
      throw new RuntimeException(format("Application Component Instance %s does not seem to be working", acInstName));
    }

    log.info("Application Component Instance {} is up", acInstName);
  }
}
