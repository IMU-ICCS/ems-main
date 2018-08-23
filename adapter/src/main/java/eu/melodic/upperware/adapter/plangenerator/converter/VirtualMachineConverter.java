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
import camel.deployment.DeploymentTypeModel;
import camel.deployment.VM;
import camel.deployment.VMInstance;
import com.google.common.collect.Sets;
import eu.melodic.upperware.adapter.plangenerator.model.VirtualMachine;
import eu.melodic.upperware.adapter.properties.AdapterProperties;
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
public class VirtualMachineConverter implements ModelConverter<DeploymentInstanceModel, Collection<VirtualMachine>> {

  private AdapterProperties properties;
  private ProviderInfoSupplier providerInfoSupplier;

  @Override
  public Collection<VirtualMachine> toComparableModel(DeploymentInstanceModel model) {
    log.info("Building virtual machine models");
    DeploymentTypeModel initialModel = ConverterUtils.findDeploymentTypeModel(model);
    EList<VM> vms = initialModel.getVms();
    if (CollectionUtils.isEmpty(vms)) {
      log.info("There are no VMs defined - no virtual machines will be created");
      return Sets.newHashSet();
    }
    log.debug("Timeouts will be read from properties");
    return vms.stream().map(this::toVirtualMachine).collect(toSet());
  }

  private VirtualMachine toVirtualMachine(VM vm) {
    log.info("Processing of {}", vm.getName());

    VMInstance vmInst = ConverterUtils.findAssociatedVmInstance(vm);

    AdapterProperties.Colosseum.Timeouts timeouts = properties.getColosseum().getTimeouts();

    VirtualMachine virtualMachine = VirtualMachine.builder()
      .name(vm.getName())
      .cloudName(providerInfoSupplier.getCloudName(vmInst))
      .location(providerInfoSupplier.getLocation(vmInst))
      .locationTimeout(timeouts.getLocation())
      .hardware(providerInfoSupplier.getMachineType(vmInst))
      .hardwareTimeout(timeouts.getHardware())
      .image(providerInfoSupplier.getImage(vmInst))
      .imageTimeout(timeouts.getImage())
      .build();

    log.info("Built virtual machine: {}", virtualMachine);

    return virtualMachine;
  }
}
