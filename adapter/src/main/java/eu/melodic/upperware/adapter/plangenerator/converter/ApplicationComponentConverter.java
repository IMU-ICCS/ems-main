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
import eu.melodic.upperware.adapter.plangenerator.model.ApplicationComponent;
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
public class ApplicationComponentConverter implements ModelConverter<DeploymentInstanceModel, Collection<ApplicationComponent>> {

  private ProviderInfoSupplier providerInfoSupplier;

  @Override
  public Collection<ApplicationComponent> toComparableModel(DeploymentInstanceModel model) {
    log.info("Building application component models (based on hostings)");
    DeploymentTypeModel initialModel = ConverterUtils.findDeploymentTypeModel(model);
    EList<Hosting> hostings = initialModel.getHostings();
    if (CollectionUtils.isEmpty(hostings)) {
      log.info("There are no hostings defined - no application components will be created");
      return Sets.newHashSet();
    }
    return hostings.stream().map(this::toApplicationComponent).collect(toSet());
  }

  private ApplicationComponent toApplicationComponent(Hosting hosting) {
    log.info("Processing of {}", hosting.getName());

    Application app = ConverterUtils.extractApplication((CamelModel) hosting.eContainer().eContainer());
    VM vm = ConverterUtils.findVM(hosting);
    SoftwareComponent sc = ConverterUtils.findSoftwareComponent(hosting);

    VMInstance vmInstance = ConverterUtils.findAssociatedVmInstance(vm);

    ApplicationComponent ac = ApplicationComponent.builder()
      .name(sc.getName())
      .appName(app.getName())
      .lcName(ConverterUtils.extractConfiguration(sc).getName())
      .vmName(vm.getName())
      .cloudName(providerInfoSupplier.getName(vmInstance))
      .location(providerInfoSupplier.getLocation(vmInstance))
      .hardware(providerInfoSupplier.getMachineType(vmInstance))
      .image(providerInfoSupplier.getImage(vmInstance))
      .build();

    log.info("Built component: {}", ac);

    return ac;
  }
}
