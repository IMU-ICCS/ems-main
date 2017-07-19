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
import eu.paasage.upperware.adapter.plangenerator.model.ApplicationComponentInstance;
import eu.paasage.upperware.adapter.plangenerator.tasks.ApplicationComponentInstanceTask;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.concurrent.Future;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

@Slf4j
public class ApplicationComponentInstanceTaskExecutor extends ColosseumTaskExecutor<ApplicationComponentInstance> {

  ApplicationComponentInstanceTaskExecutor(ApplicationComponentInstanceTask task, Collection<Future> predecessors,
                                           ColosseumApi api, ColosseumConfigApi configApi, ColosseumContext context) {
    super(task, predecessors, api, configApi, context);
  }

  @Override
  public void create(ApplicationComponentInstance acInst) {
    String name = acInst.getName();
    checkNotNull(name);
    String acName = acInst.getAcName();
    checkNotNull(acName);
    String vmInstName = acInst.getVmInstName();
    checkNotNull(vmInstName);

    String cloudName = acInst.getCloudName();
    checkNotNull(cloudName);
    String appName = acInst.getAppName();
    checkNotNull(appName);
    String lcName = acInst.getLcName();
    checkNotNull(lcName);
    String vmName = acInst.getVmName();
    checkNotNull(vmName);

    String location = acInst.getLocation();
    checkNotNull(location);
    String hardware = acInst.getHardware();
    checkNotNull(hardware);
    String image = acInst.getImage();
    checkNotNull(image);

    log.info("Executing Create Application Component Instance {} task for application component {}", name, acName);

    Cloud cloudEntity = context.getCloud(cloudName).orElseThrow(() -> new IllegalStateException(
      format("Cloud %s was not configured in Colosseum - application component instance cannot be created", cloudName)));

    Long cloudId = cloudEntity.getId();
    checkNotNull(cloudId);

    Location locationEntity = configApi.getLocation(cloudId, location)
      .orElseThrow(() -> new IllegalArgumentException(format("Location %s in cloud %s (id=%s) does not exist in Colosseum " +
        "- application component instance cannot be created", location, cloudName, cloudId)));

    Hardware hardwareEntity = configApi.getHardware(cloudId, hardware)
      .orElseThrow(() -> new IllegalArgumentException(format("Hardware %s in cloud %s (id=%s) does not exist in Colosseum " +
        "- application component instance cannot be created", hardware, cloudName, cloudId)));

    Image imageEntity = configApi.getImage(cloudId, image)
      .orElseThrow(() -> new IllegalArgumentException(format("Image %s in cloud %s (id=%s) does not exist in Colosseum " +
        "- application component instance cannot be created", image, cloudName, cloudId)));

    Long locationId = locationEntity.getId();
    checkNotNull(locationId);
    Long hardwareId = hardwareEntity.getId();
    checkNotNull(hardwareId);
    Long imageId = imageEntity.getId();
    checkNotNull(imageId);

    LifecycleComponent lcEntity = context.getLifecycleComponent(lcName)
      .orElseThrow(() -> new IllegalStateException(format("Lifecycle component %s does not exist in Colosseum " +
        "- application component instance cannot be created", lcName)));

    VirtualMachineTemplate vmEntity = context.getVirtualMachine(cloudId, locationId, hardwareId, imageId)
      .orElseThrow(() -> new IllegalStateException(format("Virtual Machine %s (cloudId=%s, locationId=%s, hardwareId=%s, imageId=%s) " +
        "does not exist in Colosseum - application component instance cannot be created", vmName, cloudId, locationId, hardwareId, imageId)));

    Long lcId = lcEntity.getId();
    checkNotNull(lcId);
    Long vmId = vmEntity.getId();
    checkNotNull(vmId);

    ApplicationComponent acEntity = context.getApplicationComponent(appName, lcId, vmId)
      .orElseThrow(() -> new IllegalStateException(format("Application component %s (appName=%s, lcId=%s, vmId=%s) " +
        "does not exist in Colosseum - application component instance cannot be created", acName, appName, lcId, vmId)));

    ApplicationInstance appInstEntity = context.getApplicationInstance(appName)
      .orElseThrow(() -> new IllegalStateException(format("Application instance %s does not exist on Colosseum " +
        "- application component instance cannot be created", appName)));

    VirtualMachine vmInstEntity = context.getVirtualMachineInstance(vmInstName)
      .orElseThrow(() -> new IllegalStateException(format("Virtual Machine Instance %s does not exist in Colosseum " +
        "- application component instance cannot be created", vmInstName)));

    Long acId = acEntity.getId();
    checkNotNull(acId);
    Long appInstId = appInstEntity.getId();
    checkNotNull(appInstId);
    Long vmInstId = vmInstEntity.getId();
    checkNotNull(vmInstId);

    Instance acInstEntity = new Instance(null, null, null, null, acId, appInstId, vmInstId);
    acInstEntity = api.createApplicationComponentInstance(acInstEntity);
    context.addApplicationComponentInstance(acInstEntity);

    log.info("Application Component Instance {} was successfully created at {}", name, acInstEntity.getSelfLink());
  }

  @Override
  public void update(ApplicationComponentInstance acInst) {
    // TODO
  }

  @Override
  public void delete(ApplicationComponentInstance acInst) {
    // TODO
  }
}
