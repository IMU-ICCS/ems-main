/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.plangenerator.converter;

import camel.deployment.DeploymentInstanceModel;
import camel.deployment.VMInstance;
import com.google.common.collect.Sets;
import eu.melodic.upperware.adapter.plangenerator.model.VirtualMachineInstance;
import eu.melodic.upperware.adapter.service.ProviderInfoSupplier;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.common.util.EList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static java.util.stream.Collectors.toSet;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class VirtualMachineInstanceConverter implements ModelConverter<DeploymentInstanceModel, Collection<VirtualMachineInstance>> {

  private ProviderInfoSupplier providerInfoSupplier;

  @Override
  public Collection<VirtualMachineInstance> toComparableModel(DeploymentInstanceModel model) {
    log.info("Building virtual machine instance models");
    EList<VMInstance> vmInsts = model.getVmInstances();
    if (CollectionUtils.isEmpty(vmInsts)) {
      log.info("There are no VM instances defined - no virtual machine instances will be created");
      return Sets.newHashSet();
    }
    return vmInsts.stream().map(this::toVirtualMachineInstance).collect(toSet());
  }

  private VirtualMachineInstance toVirtualMachineInstance(VMInstance vmInst) {
    log.info("Processing of {}", vmInst.getName());

    VirtualMachineInstance vmInstance = VirtualMachineInstance.builder()
      .name(vmInst.getName())
      .vmName(vmInst.getType().getName())
      .cloudName(providerInfoSupplier.getCloudName(vmInst))
      .location(providerInfoSupplier.getLocation(vmInst))
      .hardware(providerInfoSupplier.getMachineType(vmInst))
      .image(providerInfoSupplier.getImage(vmInst))
      .build();

    log.info("Built virtual machine instance: {}", vmInstance);

    return vmInstance;
  }
}
