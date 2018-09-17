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
import eu.melodic.upperware.adapter.plangenerator.model.PortProvided;
import eu.melodic.upperware.adapter.service.ProviderInfoSupplier;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class PortProvidedConverter implements ModelConverter<DeploymentInstanceModel, Collection<PortProvided>> {

  private ProviderInfoSupplier providerInfoSupplier;

  @Override
  public Collection<PortProvided> toComparableModel(DeploymentInstanceModel model) {
    log.info("Building port provided models (based on provided communications)");
    DeploymentTypeModel initialModel = ConverterUtils.findDeploymentTypeModel(model);
    Set<ProvidedCommunication> pcs = getProvidedCommunications(initialModel);
    if (CollectionUtils.isEmpty(pcs)) {
      log.info("There are no provided communications defined - no ports provided will be created");
    }
    return pcs.stream().map(this::toPortProvided).collect(toSet());
  }

  private PortProvided toPortProvided(ProvidedCommunication pc) {
    log.info("Processing of {}", pc.getName());

    Application app = ConverterUtils.extractApplication(((CamelModel) pc.eContainer().eContainer().eContainer()));
    SoftwareComponent sc = (SoftwareComponent) pc.eContainer();

    VM vm = ConverterUtils.findAssociatedVm(sc);
    VMInstance vmInstance = ConverterUtils.findAssociatedVmInstance(vm);

    PortProvided portProvided = PortProvided.builder()
            .name(pc.getName())
            .acName(sc.getName())
            .port(pc.getPortNumber())
            .cloudName(providerInfoSupplier.getName(vmInstance))
            .appName(app.getName())
            .lcName(ConverterUtils.extractConfiguration(sc).getName())
            .vmName(vm.getName())
            .location(providerInfoSupplier.getLocation(vmInstance))
            .hardware(providerInfoSupplier.getMachineType(vmInstance))
            .image(providerInfoSupplier.getImage(vmInstance))
            .build();

    log.info("Built port: {}", portProvided);

    return portProvided;
  }

  private Set<ProvidedCommunication> getProvidedCommunications(DeploymentTypeModel model) {
    List<SoftwareComponent> ics = model.getSoftwareComponents();
    return ics.stream().map(Component::getProvidedCommunications).flatMap(Collection::stream).collect(toSet());
  }
}
