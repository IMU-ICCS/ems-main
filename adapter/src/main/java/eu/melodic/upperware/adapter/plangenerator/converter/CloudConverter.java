/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.plangenerator.converter;

import camel.deployment.DeploymentInstanceModel;
import camel.deployment.VMInstance;
import com.google.common.collect.Sets;
import eu.melodic.upperware.adapter.plangenerator.model.Cloud;
import eu.melodic.upperware.adapter.properties.AdapterProperties;
import eu.melodic.upperware.adapter.service.ProviderInfoSupplier;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.common.util.EList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Deprecated
@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class CloudConverter implements ModelConverter<DeploymentInstanceModel, Collection<Cloud>> {

  private AdapterProperties properties;
  private ProviderInfoSupplier providerInfoSupplier;

  @Override
  public Collection<Cloud> toComparableModel(DeploymentInstanceModel model) {
    log.info("Building cloud models (based on VM instances)");
    EList<VMInstance> vmInsts = model.getVmInstances();
    if (CollectionUtils.isEmpty(vmInsts)) {
      log.info("There are no VM instances defined - no clouds will be created");
      return Sets.newHashSet();
    }
    log.debug("Iterating over VM instances to retrieve Root Features describing clouds associated with those instances");
    Set<Cloud> clouds = vmInsts.stream().map(this::toCloud).collect(toSet());
    log.info("Built unique clouds: {}", clouds);
    return clouds;
  }

  private Cloud toCloud(VMInstance vmInst) {
    log.info("Processing of {}", vmInst.getName());

    String name = providerInfoSupplier.getName(vmInst);
    String apiName = providerInfoSupplier.getApiName(vmInst);
    String endpoint = providerInfoSupplier.getEndpoint(vmInst);

    AdapterProperties.Clouds clouds = properties.getClouds();

    if (endpoint == null) {
      log.debug("Endpoint is not defined in the Camel Model - will be read from properties");
      endpoint = clouds.getEndpoint(name);
    } else {
      log.debug("Endpoint is defined in the Camel Model - endpoint property will be ignored");
    }

    Cloud cloud = Cloud.builder()
      .name(name)
      .apiName(apiName)
      .endpoint(endpoint)
      .build();

    log.info("Built cloud: {}", cloud);

    return cloud;
  }
}
