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
import eu.paasage.camel.deployment.*;
import eu.paasage.upperware.adapter.plangenerator.model.VirtualMachineInstanceMonitor;
import eu.paasage.upperware.adapter.properties.AdapterProperties;
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
public class VirtualMachineInstanceMonitorConverter implements ModelConverter<DeploymentModel, Collection<VirtualMachineInstanceMonitor>> {

  private AdapterProperties properties;

  @Override
  public Collection<VirtualMachineInstanceMonitor> toComparableModel(DeploymentModel model) {
    log.info("Building virtual machine instance monitors model");
    EList<VMInstance> vmInsts = model.getVmInstances();
    if (CollectionUtils.isEmpty(vmInsts)) {
      log.info("There are no VM instances defined - no monitors for virtual machine instances will be created");
      return Sets.newHashSet();
    }
    return vmInsts.stream().map(this::toMonitor).collect(toSet());
  }

  private VirtualMachineInstanceMonitor toMonitor(VMInstance vmInst) {
    String vmInstName = vmInst.getName();

    log.info("Processing of {}", vmInstName);

    AdapterProperties.Colosseum.Timeouts timeouts = properties.getColosseum().getTimeouts();

    VirtualMachineInstanceMonitor monitor = VirtualMachineInstanceMonitor.builder()
      .vmInstName(vmInstName)
      .vmInstTimeout(timeouts.getVmInst())
      .build();

    log.info("Built virtual machine instance monitor: {}", vmInstName);

    return monitor;
  }
}
