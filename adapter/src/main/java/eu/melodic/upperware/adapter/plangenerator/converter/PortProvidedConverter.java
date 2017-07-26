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
import eu.paasage.camel.Application;
import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.*;
import eu.paasage.camel.provider.Feature;
import eu.melodic.upperware.adapter.plangenerator.model.PortProvided;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.common.util.EList;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Slf4j
@Service
public class PortProvidedConverter implements ModelConverter<DeploymentModel, Collection<PortProvided>> {

  @Override
  public Collection<PortProvided> toComparableModel(DeploymentModel model) {
    log.info("Building port provided models (based on provided communications)");
    Set<ProvidedCommunication> pcs = getProvidedCommunications(model);
    if (CollectionUtils.isEmpty(pcs)) {
      log.info("There are no provided communications defined - no ports provided will be created");
    }
    return pcs.stream().map(this::toPortProvided).collect(toSet());
  }

  private PortProvided toPortProvided(ProvidedCommunication pc) {
    log.info("Processing of {}", pc.getName());

    Application app = ConverterUtils.extractApplication(((CamelModel) pc.eContainer().eContainer().eContainer()));
    InternalComponent ic = (InternalComponent) pc.eContainer();

    VM vm = ConverterUtils.findAssociatedVm(ic);
    VMInstance vmInstance = ConverterUtils.findAssociatedVmInstance(vm);
    Feature rootFeature = (Feature) vmInstance.getVmType().eContainer().eContainer();

    PortProvided portProvided = PortProvided.builder()
      .name(pc.getName())
      .acName(ic.getName())
      .port(pc.getPortNumber())
      .cloudName(ConverterUtils.extractCloudName(rootFeature))
      .appName(app.getName())
      .lcName(ConverterUtils.extractConfiguration(ic).getName())
      .vmName(vm.getName())
      .location(ConverterUtils.extractLocation(rootFeature))
      .hardware(ConverterUtils.convertToString(vmInstance.getVmTypeValue()))
      .image(ConverterUtils.extractImage(rootFeature))
      .build();

    log.info("Built port: {}", portProvided);

    return portProvided;
  }

  private Set<ProvidedCommunication> getProvidedCommunications(DeploymentModel model) {
    Set<ProvidedCommunication> pcs = Sets.newHashSet();
    List<InternalComponent> ics = model.getInternalComponents();

    if (CollectionUtils.isNotEmpty(ics)) {
      ics.forEach(ic -> {
        EList<ProvidedCommunication> $pcs = ic.getProvidedCommunications();
        if (CollectionUtils.isNotEmpty($pcs)) {
          pcs.addAll($pcs);
        }
      });
    }

    return pcs;
  }
}
