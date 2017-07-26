/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.plangenerator.converter;

import com.google.common.collect.Sets;
import eu.melodic.upperware.adapter.plangenerator.model.ApplicationComponent;
import eu.paasage.camel.Application;
import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.*;
import eu.paasage.camel.provider.Feature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.common.util.EList;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static java.util.stream.Collectors.toSet;

@Slf4j
@Service
public class ApplicationComponentConverter implements ModelConverter<DeploymentModel, Collection<ApplicationComponent>> {

  @Override
  public Collection<ApplicationComponent> toComparableModel(DeploymentModel model) {
    log.info("Building application component models (based on hostings)");
    EList<Hosting> hostings = model.getHostings();
    if (CollectionUtils.isEmpty(hostings)) {
      log.info("There are no hostings defined - no application components will be created");
      return Sets.newHashSet();
    }
    return hostings.stream().map(this::toApplicationComponent).collect(toSet());
  }

  private ApplicationComponent toApplicationComponent(Hosting hosting) {
    log.info("Processing of {}", hosting.getName());

    Application app = ConverterUtils.extractApplication((CamelModel) hosting.eContainer().eContainer());
    InternalComponent ic = (InternalComponent) hosting.getRequiredHost().eContainer();
    VM vm = (VM) hosting.getProvidedHost().eContainer();
    
    VMInstance vmInstance = ConverterUtils.findAssociatedVmInstance(vm);
    Feature rootFeature = (Feature) vmInstance.getVmType().eContainer().eContainer();

    ApplicationComponent ac = ApplicationComponent.builder()
      .name(ic.getName())
      .appName(app.getName())
      .lcName(ConverterUtils.extractConfiguration(ic).getName())
      .vmName(vm.getName())
      .cloudName(ConverterUtils.extractCloudName(rootFeature))
      .location(ConverterUtils.extractLocation(rootFeature))
      .hardware(ConverterUtils.convertToString(vmInstance.getVmTypeValue()))
      .image(ConverterUtils.extractImage(rootFeature))
      .build();

    log.info("Built component: {}", ac);

    return ac;
  }
}
