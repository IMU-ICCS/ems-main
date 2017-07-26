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
import eu.paasage.camel.Application;
import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.*;
import eu.paasage.camel.provider.Feature;
import eu.paasage.upperware.adapter.plangenerator.model.ApplicationComponentInstanceMonitor;
import eu.paasage.upperware.adapter.properties.AdapterProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.common.util.EList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static eu.paasage.upperware.adapter.plangenerator.converter.ConverterUtils.*;
import static eu.paasage.upperware.adapter.plangenerator.converter.ConverterUtils.extractImage;
import static java.util.stream.Collectors.toSet;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ApplicationComponentInstanceMonitorConverter implements ModelConverter<DeploymentModel, Collection<ApplicationComponentInstanceMonitor>> {

  private AdapterProperties properties;

  @Override
  public Collection<ApplicationComponentInstanceMonitor> toComparableModel(DeploymentModel model) {
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

    Application app = extractApplication((CamelModel) hostingInst.eContainer().eContainer());
    InternalComponentInstance icInst = (InternalComponentInstance) hostingInst.getRequiredHostInstance().eContainer();
    VMInstance vmInst = (VMInstance) hostingInst.getProvidedHostInstance().eContainer();

    Feature rootFeature = (Feature) vmInst.getVmType().eContainer().eContainer();

    AdapterProperties.Colosseum.Timeouts timeouts = properties.getColosseum().getTimeouts();

    ApplicationComponentInstanceMonitor monitor = ApplicationComponentInstanceMonitor.builder()
      .acInstName(icInst.getName())
      .acInstTimeout(timeouts.getAcInst())
      .acName(icInst.getType().getName())
      .vmInstName(vmInst.getName())
      .cloudName(extractCloudName(rootFeature))
      .appName(app.getName())
      .lcName(extractConfiguration((InternalComponent) icInst.getType()).getName())
      .vmName(vmInst.getType().getName())
      .location(extractLocation(rootFeature))
      .hardware(convertToString(vmInst.getVmTypeValue()))
      .image(extractImage(rootFeature))
      .build();

    log.info("Built application component instance monitor: {}", monitor);

    return monitor;
  }
}
