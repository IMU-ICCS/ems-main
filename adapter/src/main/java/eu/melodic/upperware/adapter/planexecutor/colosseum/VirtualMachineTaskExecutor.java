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
import eu.melodic.upperware.adapter.plangenerator.model.VirtualMachine;
import eu.melodic.upperware.adapter.plangenerator.tasks.VirtualMachineTask;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.Future;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

@Deprecated
@Slf4j
public class VirtualMachineTaskExecutor extends ColosseumTaskExecutor<VirtualMachine> {

  VirtualMachineTaskExecutor(VirtualMachineTask task, Collection<Future> predecessors, ColosseumApi api, ColosseumContext context) {
    super(task, predecessors, api, context);
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

    Location locationEntity = api.getLocation(cloudId, location, locationTimeout);
    if (locationEntity == null) {
      throw new IllegalArgumentException(format("Location %s in cloud %s (id=%s) does not exist in Colosseum " +
        "- VM cannot be created", location, cloudName, cloudId));
    }

    Hardware hardwareEntity = api.getHardware(cloudId, hardware, hardwareTimeout);
    if (hardwareEntity == null) {
      throw new IllegalArgumentException(format("Hardware %s in cloud %s (id=%s) does not exist in Colosseum " +
        "- VM cannot be created", hardware, cloudName, cloudId));
    }

    Image imageEntity = api.getImage(cloudId, image, imageTimeout);
    if (imageEntity == null) {
      throw new IllegalArgumentException(format("Image %s in cloud %s (id=%s) does not exist in Colosseum " +
        "- VM cannot be created", image, cloudName, cloudId));
    }

    Long locationId = locationEntity.getId();
    checkNotNull(locationId);
    Long hardwareId = hardwareEntity.getId();
    checkNotNull(hardwareId);
    Long imageId = imageEntity.getId();
    checkNotNull(imageId);

    if (context.getVirtualMachine(cloudId, locationId, hardwareId, imageId).isPresent()) {
      log.warn("Virtual Machine with params (cloudId={}, locationId={}, hardwareId={}, imageId={}) already exists " +
        "in Colosseum - skipping execution of the task", cloudId, locationId, hardwareId, imageId);
      return;
    }

    VirtualMachineTemplate vmEntity = new VirtualMachineTemplate(cloudId, imageId, locationId, hardwareId, null);
    vmEntity = api.createVirtualMachine(vmEntity);
    context.addVirtualMachine(vmEntity);

    log.info("Virtual Machine {} was successfully created at {}", name, vmEntity.getSelfLink());
  }

  @Override
  public void delete(VirtualMachine vm) {
    String name = vm.getName();
    checkNotNull(name);
    String cloudName = vm.getCloudName();
    checkNotNull(cloudName);

    String location = vm.getLocation();
    checkNotNull(location);

    String hardware = vm.getHardware();
    checkNotNull(hardware);

    String image = vm.getImage();
    checkNotNull(image);

    log.info("Executing Delete Virtual Machine task for VM {}", name);

    Cloud cloudEntity = context.getCloud(cloudName).orElseThrow(() -> new IllegalStateException(
      format("Cloud %s was not configured in Colosseum - VM cannot be deleted", cloudName)));

    Long cloudId = cloudEntity.getId();
    checkNotNull(cloudId);

    Location locationEntity = api.getLocation(cloudId, location);
    if (locationEntity == null) {
      throw new IllegalArgumentException(format("Location %s in cloud %s (id=%s) does not exist in Colosseum " +
        "- VM cannot be deleted", location, cloudName, cloudId));
    }

    Hardware hardwareEntity = api.getHardware(cloudId, hardware);
    if (hardwareEntity == null) {
      throw new IllegalArgumentException(format("Hardware %s in cloud %s (id=%s) does not exist in Colosseum " +
        "- VM cannot be deleted", hardware, cloudName, cloudId));
    }

    Image imageEntity = api.getImage(cloudId, image);
    if (imageEntity == null) {
      throw new IllegalArgumentException(format("Image %s in cloud %s (id=%s) does not exist in Colosseum " +
        "- VM cannot be deleted", image, cloudName, cloudId));
    }

    Long locationId = locationEntity.getId();
    checkNotNull(locationId);
    Long hardwareId = hardwareEntity.getId();
    checkNotNull(hardwareId);
    Long imageId = imageEntity.getId();
    checkNotNull(imageId);

    Optional<VirtualMachineTemplate> vmEntityOptional = context.getVirtualMachine(cloudId, locationId,
            hardwareId,
      imageId);

    if (vmEntityOptional.isPresent()) {
      VirtualMachineTemplate vmEntity = vmEntityOptional.get();
      api.deleteVirtualMachine(vmEntity);
      context.deleteVirtualMachine(vmEntity);

      log.info("Virtual Machine {} was successfully deleted from {}", name, vmEntity.getSelfLink());
    } else {
      log.warn("Virtual Machine {} does not exist in Colosseum - cannot be deleted - skipping execution of the task", name);
    }
  }
}
