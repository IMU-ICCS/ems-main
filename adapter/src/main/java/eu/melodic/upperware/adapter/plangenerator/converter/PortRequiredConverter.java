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
import eu.melodic.upperware.adapter.plangenerator.model.PortRequired;
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
public class PortRequiredConverter implements ModelConverter<DeploymentInstanceModel, Collection<PortRequired>> {

    private ProviderInfoSupplier providerInfoSupplier;

    @Override
    public Collection<PortRequired> toComparableModel(DeploymentInstanceModel model) {
        log.info("Building port required models (based on communications)");
        DeploymentTypeModel initialModel = ConverterUtils.findDeploymentTypeModel(model);
        EList<Communication> comms = initialModel.getCommunications();
        if (CollectionUtils.isEmpty(comms)) {
            log.info("There are no communications defined - no ports required will be created");
            return Sets.newHashSet();
        }
        return comms.stream().map(this::toPortRequired).collect(toSet());
    }

    private PortRequired toPortRequired(Communication comm) {
        log.info("Processing of {}", comm.getName());

        Application app = ConverterUtils.extractApplication(ConverterUtils.getAncesstor(comm, CamelModel.class));
        RequiredCommunication reqComm = comm.getRequiredCommunication();
        SoftwareComponent sc = ConverterUtils.getAncesstor(reqComm, SoftwareComponent.class);
        VM vm = ConverterUtils.findAssociatedVm(sc);

        VMInstance vmInstance = ConverterUtils.findAssociatedVmInstance(vm);

        PortRequired portRequired = PortRequired.builder()
                .name(reqComm.getName())
                .acName(sc.getName())
                .mandatory(reqComm.isIsMandatory())
                .cloudName(providerInfoSupplier.getName(vmInstance))
                .appName(app.getName())
                .lcName(ConverterUtils.extractConfiguration(sc).getName())
                .vmName(vm.getName())
                .location(providerInfoSupplier.getLocation(vmInstance))
                .hardware(providerInfoSupplier.getMachineType(vmInstance))
                .image(providerInfoSupplier.getImage(vmInstance))
                .startCmd((comm.getRequiredPortConfiguration() != null && comm.getRequiredPortConfiguration() instanceof ScriptConfiguration) ? ((ScriptConfiguration)comm.getRequiredPortConfiguration()).getStartCommand() : null)
                .build();

        log.info("Built port: {}", portRequired);

        return portRequired;
    }
}
