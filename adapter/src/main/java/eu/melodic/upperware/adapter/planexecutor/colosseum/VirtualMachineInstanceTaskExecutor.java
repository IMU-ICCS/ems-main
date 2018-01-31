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
import eu.melodic.upperware.adapter.plangenerator.model.VirtualMachineInstance;
import eu.melodic.upperware.adapter.plangenerator.tasks.VirtualMachineInstanceTask;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.Future;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

@Slf4j
public class VirtualMachineInstanceTaskExecutor extends ColosseumTaskExecutor<VirtualMachineInstance> {

  VirtualMachineInstanceTaskExecutor(VirtualMachineInstanceTask task, Collection<Future> predecessors,
                                     ColosseumApi api, ColosseumContext context) {
    super(task, predecessors, api, context);
  }

  @Override
  public void create(VirtualMachineInstance vmInst) {
    String name = vmInst.getName();
    checkNotNull(name);
    String vmName = vmInst.getVmName();
    checkNotNull(vmName);
    String cloudName = vmInst.getCloudName();
    checkNotNull(cloudName);

    String location = vmInst.getLocation();
    checkNotNull(location);
    String hardware = vmInst.getHardware();
    checkNotNull(hardware);
    String image = vmInst.getImage();
    checkNotNull(image);

    log.info("Executing Create Virtual Machine Instance {} task for VM {}", name, vmName);

    if (context.getVirtualMachineInstance(name).isPresent()) {
      log.warn("Virtual Machine {} already exists in Colosseum - skipping execution of the task", name);
      return;
    }

    Cloud cloudEntity = context.getCloud(cloudName).orElseThrow(() -> new IllegalStateException(
      format("Cloud %s was not configured in Colosseum - VM instance cannot be created", cloudName)));

    Long cloudId = cloudEntity.getId();
    checkNotNull(cloudId);

    Location locationEntity = api.getLocation(cloudId, location);
    if (locationEntity == null) {
      throw new IllegalArgumentException(format("Location %s in cloud %s (id=%s) does not exist in Colosseum " +
        "- VM Instance cannot be created", location, cloudName, cloudId));
    }

    Hardware hardwareEntity = api.getHardware(cloudId, hardware);
    if (hardwareEntity == null) {
      throw new IllegalArgumentException(format("Hardware %s in cloud %s (id=%s) does not exist in Colosseum " +
        "- VM Instance cannot be created", hardware, cloudName, cloudId));
    }

    Image imageEntity = api.getImage(cloudId, image);
    if (imageEntity == null) {
      throw new IllegalArgumentException(format("Image %s in cloud %s (id=%s) does not exist in Colosseum " +
        "- VM Instance cannot be created", image, cloudName, cloudId));
    }

    Long locationId = locationEntity.getId();
    checkNotNull(locationId);
    Long hardwareId = hardwareEntity.getId();
    checkNotNull(hardwareId);
    Long imageId = imageEntity.getId();
    checkNotNull(imageId);

    VirtualMachine vmEntity = new VirtualMachine(null, null, null, null, cloudId,
      null, null, locationId, name, imageId, hardwareId, null);
    vmEntity = api.createVirtualMachineInstance(vmEntity);
    context.addVirtualMachineInstance(vmEntity);

    log.info("Virtual Machine Instance {} was successfully created at {}", name, vmEntity.getSelfLink());
  }

  @Override
  public void update(VirtualMachineInstance vmInst) {
    throw new UnsupportedOperationException("Cannot update instance of any component - this method should not be run at all");
  }

  @Override
  public void delete(VirtualMachineInstance vmInst) {
    String name = vmInst.getName();
    checkNotNull(name);
    String vmName = vmInst.getVmName();
    checkNotNull(vmName);

    log.info("Executing Delete Virtual Machine Instance {} task for VM {}", name, vmName);

    Optional<VirtualMachine> vmEntityOptional = context.getVirtualMachineInstance(name);

    if (vmEntityOptional.isPresent()) {
      VirtualMachine vmEntity = vmEntityOptional.get();
      api.deleteVirtualMachineInstance(vmEntity);
      context.deleteVirtualMachineInstance(vmEntity);

      log.info("Virtual Machine Instance {} was successfully deleted from {}", name, vmEntity.getSelfLink());
    } else {
      log.warn("Virtual Machine Instance {} does not exist in Colosseum - cannot be deleted - skipping execution of the task", name);
    }
  }
}
