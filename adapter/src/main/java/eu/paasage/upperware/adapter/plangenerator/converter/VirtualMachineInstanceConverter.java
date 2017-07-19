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
import eu.paasage.camel.deployment.VMInstance;
import eu.paasage.camel.provider.Feature;
import eu.paasage.upperware.adapter.plangenerator.model.VirtualMachineInstance;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.common.util.EList;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static eu.paasage.upperware.adapter.plangenerator.converter.ConverterUtils.*;
import static java.util.stream.Collectors.toSet;

@Slf4j
@Service
public class VirtualMachineInstanceConverter implements ModelConverter<DeploymentModel, Collection<VirtualMachineInstance>> {

  @Override
  public Collection<VirtualMachineInstance> toComparableModel(DeploymentModel model) {
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

    Feature rootFeature = (Feature) vmInst.getVmType().eContainer().eContainer();

    VirtualMachineInstance vmInstance = VirtualMachineInstance.builder()
      .name(vmInst.getName())
      .vmName(vmInst.getType().getName())
      .cloudName(extractCloudName(rootFeature))
      .location(extractLocation(rootFeature))
      .hardware(convertToString(vmInst.getVmTypeValue()))
      .image(extractImage(rootFeature))
      .build();

    log.info("Built virtual machine instance: {}", vmInstance);

    return vmInstance;
  }
}
