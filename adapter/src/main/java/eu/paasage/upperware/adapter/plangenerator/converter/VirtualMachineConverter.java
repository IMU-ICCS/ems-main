/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.plangenerator.converter;

import com.google.common.collect.Sets;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.VM;
import eu.paasage.camel.deployment.VMInstance;
import eu.paasage.camel.provider.Feature;
import eu.paasage.upperware.adapter.plangenerator.model.VirtualMachine;
import eu.paasage.upperware.adapter.properties.AdapterProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.common.util.EList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static eu.paasage.upperware.adapter.plangenerator.converter.ConverterUtils.*;
import static java.util.stream.Collectors.*;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class VirtualMachineConverter implements ModelConverter<DeploymentModel, Collection<VirtualMachine>> {

  private AdapterProperties properties;

  @Override
  public Collection<VirtualMachine> toComparableModel(DeploymentModel model) {
    log.info("Building virtual machine models");
    EList<VM> vms = model.getVms();
    if (CollectionUtils.isEmpty(vms)) {
      log.info("There are no VMs defined - no virtual machines will be created");
      return Sets.newHashSet();
    }
    log.debug("Timeouts will be read from properties");
    return vms.stream().map(this::toVirtualMachine).collect(toSet());
  }

  private VirtualMachine toVirtualMachine(VM vm) {
    log.info("Processing of {}", vm.getName());

    VMInstance vmInst = findAssociatedVmInstance(vm);
    Feature rootFeature = (Feature) vmInst.getVmType().eContainer().eContainer();
    AdapterProperties.Colosseum.Timeouts timeouts = properties.getColosseum().getTimeouts();

    VirtualMachine virtualMachine = VirtualMachine.builder()
      .name(vm.getName())
      .cloudName(extractCloudName(rootFeature))
      .location(extractLocation(rootFeature))
      .locationTimeout(timeouts.getLocation())
      .hardware(convertToString(vmInst.getVmTypeValue()))
      .hardwareTimeout(timeouts.getHardware())
      .image(extractImage(rootFeature))
      .imageTimeout(timeouts.getImage())
      .build();

    log.info("Built virtual machine: {}", virtualMachine);

    return virtualMachine;
  }
}
