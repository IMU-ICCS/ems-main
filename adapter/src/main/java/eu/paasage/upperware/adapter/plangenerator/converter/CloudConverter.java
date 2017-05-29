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
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.VMInstance;
import eu.paasage.camel.provider.Attribute;
import eu.paasage.camel.provider.Feature;
import eu.paasage.upperware.adapter.plangenerator.model.Cloud;
import eu.paasage.upperware.adapter.properties.AdapterProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.common.util.EList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

import static eu.paasage.upperware.adapter.plangenerator.converter.ConverterUtils.convertToString;
import static java.util.stream.Collectors.toSet;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class CloudConverter implements ModelConverter<DeploymentModel, Collection<Cloud>> {

  private static final String ATTRIB_NAME = "Name";
  private static final String ATTRIB_ENDPOINT = "Endpoint";
  private static final String ATTRIB_DRIVER = "Driver";

  private AdapterProperties properties;

  @Override
  public Collection<Cloud> toComparableModel(DeploymentModel model) {
    log.info("Building cloud models (based on VM instances)");
    EList<VMInstance> vmInsts = model.getVmInstances();
    if (CollectionUtils.isEmpty(vmInsts)) {
      log.info("There are no VM instances defined - no clouds will be created");
      return Sets.newHashSet();
    }
    log.debug("Iterating over VM instances to retrieve Root Features describing clouds associated with those instances");
    log.debug("Logins, passwords and filters will be read from properties");
    Set<Cloud> clouds = vmInsts.stream().map(this::toCloud).collect(toSet());
    log.info("Built unique clouds: {}", clouds);
    return clouds;
  }

  private Cloud toCloud(VMInstance vmInst) {
    log.info("Processing of {}", vmInst.getName());

    String name = null;
    String endpoint = null;
    String provider = null;
    String driver = null;

    Feature rootFeature = (Feature) vmInst.getVmType().eContainer().eContainer();

    for (Attribute attr : rootFeature.getAttributes()) {
      switch (attr.getName()) {
        case ATTRIB_NAME:
          name = convertToString(attr.getValue());
          break;
        case ATTRIB_ENDPOINT:
          endpoint = convertToString(attr.getValue());
          break;
        case ATTRIB_DRIVER:
          driver = convertToString(attr.getValue());
          provider = driver;
          break;
      }
    }

    AdapterProperties.Clouds clouds = properties.getClouds();
    AdapterProperties.Clouds.Filters filters = clouds.getFilters();

    if (endpoint == null) {
      log.debug("Endpoint is not defined in the Camel Model - will be read from properties");
      endpoint = clouds.getEndpoint(name);
    } else {
      log.debug("Endpoint is defined in the Camel Model - endpoint property will be ignored");
    }

    Cloud cloud = Cloud.builder()
      .name(name)
      .login(clouds.getLogin(name))
      .password(clouds.getPassword(name))
      .tenant(1L)
      .endpoint(endpoint)
      .provider(provider)
      .driver(driver)
      .filters(filters != null ? filters.getPairs(name) : null)
      .build();

    log.info("Built cloud: {}", cloud);

    return cloud;
  }
}
