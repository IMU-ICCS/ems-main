/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.plangenerator.converter;

import camel.core.Application;
import camel.core.CamelModel;
import camel.deployment.*;
import com.google.common.collect.Sets;
import eu.melodic.upperware.adapter.plangenerator.model.ApplicationComponentInstanceMonitor;
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
public class ApplicationComponentInstanceMonitorConverter implements ModelConverter<DeploymentInstanceModel, Collection<ApplicationComponentInstanceMonitor>> {

  private AdapterProperties properties;
  private ProviderInfoSupplier providerInfoSupplier;

  @Override
  public Collection<ApplicationComponentInstanceMonitor> toComparableModel(DeploymentInstanceModel model) {
    log.info("Building application component instance monitors model (based on hosting instances)");
    EList<HostingInstance> hostingInsts = model.getHostingInstances();
    if (CollectionUtils.isEmpty(hostingInsts)) {
      log.info("There are no hosting instances defined - no monitors for application component instances will be created");
      return Sets.newHashSet();
    }
    return hostingInsts.stream().map(this::toMonitor).collect(toSet());
  }

  private ApplicationComponentInstanceMonitor toMonitor(HostingInstance hostingInst) {
    log.info("Processing of {}", hostingInst.getName());

    Application app = ConverterUtils.extractApplication((CamelModel) hostingInst.eContainer().eContainer());

    SoftwareComponentInstance scInst = ConverterUtils.findSoftwareComponentInstance(hostingInst);
    VMInstance vmInst = ConverterUtils.findVMInstance(hostingInst);

    AdapterProperties.Colosseum.Timeouts timeouts = properties.getColosseum().getTimeouts();

    ApplicationComponentInstanceMonitor monitor = ApplicationComponentInstanceMonitor.builder()
      .acInstName(scInst.getName())
      .acInstTimeout(timeouts.getAcInst())
      .acName(scInst.getType().getName())
      .vmInstName(vmInst.getName())
      .cloudName(providerInfoSupplier.getName(vmInst))
      .appName(app.getName())
      .lcName(ConverterUtils.extractConfiguration((SoftwareComponent) scInst.getType()).getName())
      .vmName(vmInst.getType().getName())
      .location(providerInfoSupplier.getLocation(vmInst))
      .hardware(providerInfoSupplier.getMachineType(vmInst))
      .image(providerInfoSupplier.getImage(vmInst))
      .build();

    log.info("Built application component instance monitor: {}", monitor);

    return monitor;
  }
}
