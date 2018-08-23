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
import eu.melodic.upperware.adapter.plangenerator.model.ApplicationComponentInstance;
import eu.melodic.upperware.adapter.service.ProviderInfoSupplier;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.common.util.EList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static eu.melodic.upperware.adapter.plangenerator.converter.ConverterUtils.extractApplication;
import static eu.melodic.upperware.adapter.plangenerator.converter.ConverterUtils.extractConfiguration;
import static java.util.stream.Collectors.toSet;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ApplicationComponentInstanceConverter implements ModelConverter<DeploymentInstanceModel, Collection<ApplicationComponentInstance>> {

  private ProviderInfoSupplier providerInfoSupplier;


  @Override
  public Collection<ApplicationComponentInstance> toComparableModel(DeploymentInstanceModel model) {
    log.info("Building application component instance models (based on hosting instances)");
    EList<HostingInstance> hostingInsts = model.getHostingInstances();
    if (CollectionUtils.isEmpty(hostingInsts)) {
      log.info("There are no hosting instances defined - no application component instances will be created");
      return Sets.newHashSet();
    }
    return hostingInsts.stream().map(this::toApplicationComponentInstance).collect(toSet());
  }

  private ApplicationComponentInstance toApplicationComponentInstance(HostingInstance hostingInst) {
    log.info("Processing of {}", hostingInst.getName());

    Application app = extractApplication((CamelModel) hostingInst.eContainer().eContainer());

    SoftwareComponentInstance scInst = ConverterUtils.findSoftwareComponentInstance(hostingInst);
    VMInstance vmInst = ConverterUtils.findVMInstance(hostingInst);

    ApplicationComponentInstance acInst = ApplicationComponentInstance.builder()
      .name(scInst.getName())
      .acName(scInst.getType().getName())
      .vmInstName(vmInst.getName())
      .cloudName(providerInfoSupplier.getCloudName(vmInst))
      .appName(app.getName())
      .lcName(extractConfiguration((SoftwareComponent) scInst.getType()).getName())
      .vmName(vmInst.getType().getName())
      .location(providerInfoSupplier.getLocation(vmInst))
      .hardware(providerInfoSupplier.getMachineType(vmInst))
      .image(providerInfoSupplier.getImage(vmInst))
      .build();

    log.info("Built component instance: {}", acInst);

    return acInst;
  }
}
