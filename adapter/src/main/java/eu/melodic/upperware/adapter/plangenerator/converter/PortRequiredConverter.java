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
import eu.melodic.upperware.adapter.plangenerator.model.PortRequired;
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
public class PortRequiredConverter implements ModelConverter<DeploymentModel, Collection<PortRequired>> {

    @Override
    public Collection<PortRequired> toComparableModel(DeploymentModel model) {
        log.info("Building port required models (based on communications)");
        EList<Communication> comms = model.getCommunications();
        if (CollectionUtils.isEmpty(comms)) {
            log.info("There are no communications defined - no ports required will be created");
            return Sets.newHashSet();
        }
        return comms.stream().map(this::toPortRequired).collect(toSet());
    }

    private PortRequired toPortRequired(Communication comm) {
        log.info("Processing of {}", comm.getName());

        Application app = ConverterUtils.extractApplication((CamelModel) comm.eContainer().eContainer());
        RequiredCommunication reqComm = comm.getRequiredCommunication();
        InternalComponent ic = (InternalComponent) reqComm.eContainer();

        VM vm = ConverterUtils.findAssociatedVm(ic);
        VMInstance vmInst = ConverterUtils.findAssociatedVmInstance(vm);
        Feature rootFeature = (Feature) vmInst.getVmType().eContainer().eContainer();

        String startConfigurationCommand = null;
        if (comm.getRequiredPortConfiguration() != null){
            startConfigurationCommand = comm.getRequiredPortConfiguration().getStartCommand();
        }

        PortRequired portRequired = PortRequired.builder()
                .name(reqComm.getName())
                .acName(ic.getName())
                .mandatory(reqComm.isIsMandatory())
                .cloudName(ConverterUtils.extractCloudName(rootFeature))
                .appName(app.getName())
                .lcName(ConverterUtils.extractConfiguration(ic).getName())
                .vmName(vm.getName())
                .location(ConverterUtils.extractLocation(rootFeature))
                .hardware(ConverterUtils.convertToString(vmInst.getVmTypeValue()))
                .image(ConverterUtils.extractImage(rootFeature))
                .startCmd(startConfigurationCommand)
                .build();

        log.info("Built port: {}", portRequired);

        return portRequired;
    }
}
