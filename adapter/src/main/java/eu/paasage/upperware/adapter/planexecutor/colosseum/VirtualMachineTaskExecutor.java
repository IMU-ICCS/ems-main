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
import eu.paasage.upperware.adapter.plangenerator.model.VirtualMachine;
import eu.paasage.upperware.adapter.plangenerator.tasks.VirtualMachineTask;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.concurrent.Future;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

@Slf4j
public class VirtualMachineTaskExecutor extends ColosseumTaskExecutor<VirtualMachine> {

  VirtualMachineTaskExecutor(VirtualMachineTask task, Set<Future> predecessors, ColosseumApi api,
                             ColosseumConfigApi configApi, ColosseumContext context) {
    super(task, predecessors, api, configApi, context);
  }

  @Override
  public void create(VirtualMachine vm) {
    String name = vm.getName();
    checkNotNull(name);
    String cloudName = vm.getCloudName();
    checkNotNull(cloudName);

    String location = vm.getLocation();
    checkNotNull(location);
    Long locationTimeout = vm.getLocationTimeout();
    checkNotNull(locationTimeout);

    String hardware = vm.getHardware();
    checkNotNull(hardware);
    Long hardwareTimeout = vm.getHardwareTimeout();
    checkNotNull(hardwareTimeout);

    String image = vm.getImage();
    checkNotNull(image);
    Long imageTimeout = vm.getImageTimeout();
    checkNotNull(imageTimeout);

    log.info("Executing Create Virtual Machine task for VM {}", name);

    Cloud cloudEntity = context.getCloud(cloudName).orElseThrow(() -> new IllegalStateException(
      format("Cloud %s was not configured in Colosseum - VM cannot be created", cloudName)));

    Long cloudId = cloudEntity.getId();
    checkNotNull(cloudId);

    Location locationEntity = configApi.getLocationWithWait(cloudId, location, locationTimeout)
      .orElseThrow(() -> new IllegalArgumentException(format("Location %s in cloud %s (id=%s) does not exist in Colosseum " +
        "- VM cannot be created", location, cloudName, cloudId)));

    Hardware hardwareEntity = configApi.getHardwareWithWait(cloudId, hardware, hardwareTimeout)
      .orElseThrow(() -> new IllegalArgumentException(format("Hardware %s in cloud %s (id=%s) does not exist in Colosseum " +
        "- VM cannot be created", hardware, cloudName, cloudId)));

    Image imageEntity = configApi.getImageWithWait(cloudId, image, imageTimeout)
      .orElseThrow(() -> new IllegalArgumentException(format("Image %s in cloud %s (id=%s) does not exist in Colosseum " +
        "- VM cannot be created", image, cloudName, cloudId)));

    Long locationId = locationEntity.getId();
    checkNotNull(locationId);
    Long hardwareId = hardwareEntity.getId();
    checkNotNull(hardwareId);
    Long imageId = imageEntity.getId();
    checkNotNull(imageId);

    VirtualMachineTemplate vmEntity = new VirtualMachineTemplate(cloudId, imageId, locationId, hardwareId, null);
    vmEntity = api.createVirtualMachine(vmEntity);
    context.addVirtualMachine(vmEntity);

    log.info("Virtual Machine {} was successfully created at {}", name, vmEntity.getSelfLink());
  }

  @Override
  public void update(VirtualMachine vm) {
    throw new UnsupportedOperationException("Cannot update Virtual Machine - this method should not be run at all");
  }

  @Override
  public void delete(VirtualMachine vm) {
    // TODO
  }
}
