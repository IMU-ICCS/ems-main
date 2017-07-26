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
import eu.melodic.upperware.adapter.plangenerator.model.CloudProperty;
import eu.melodic.upperware.adapter.properties.AdapterProperties;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.VMInstance;
import eu.paasage.camel.provider.Attribute;
import eu.paasage.camel.provider.Feature;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.common.util.EList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class CloudPropertyConverter implements ModelConverter<DeploymentModel, Collection<CloudProperty>> {

  private AdapterProperties properties;

  @Override
  public Collection<CloudProperty> toComparableModel(DeploymentModel model) {
    log.info("Building cloud property models (based on VM instances)");
    EList<VMInstance> vmInsts = model.getVmInstances();
    if (CollectionUtils.isEmpty(vmInsts)) {
      log.info("There are no VM instances defined - no cloud properties will be created");
      return Sets.newHashSet();
    }
    log.debug("Iterating over VM instances to retrieve Root Features describing cloud properties associated with those instances");
    log.debug("Filters will be read from properties");
    Set<CloudProperty> cloudProperties = vmInsts.stream().map(this::toCloudProperty).collect(toSet());
    log.info("Built unique cloud properties: {}", cloudProperties);
    return cloudProperties;
  }

  private CloudProperty toCloudProperty(VMInstance vmInst) {
    log.info("Processing of {}", vmInst.getName());

    String name = null;
    String cloudName = null;

    Feature rootFeature = (Feature) vmInst.getVmType().eContainer().eContainer();

    for (Attribute attr : rootFeature.getAttributes()) {
      switch (attr.getName()) {
        case ConverterUtils.ATTRIB_NAME:
          cloudName = ConverterUtils.convertToString(attr.getValue());
          name = cloudName + ConverterUtils.CLOUD_PROPERTY_NAME_SUFFIX;
          break;
      }
    }

    AdapterProperties.Clouds clouds = properties.getClouds();
    AdapterProperties.Clouds.Filters filters = clouds.getFilters();

    CloudProperty cloudProperty = CloudProperty.builder()
      .name(name)
      .cloudName(cloudName)
      .filters(filters != null ? filters.getPairs(cloudName) : null)
      .build();

    log.info("Built cloud property: {}", cloudProperty);

    return cloudProperty;
  }
}
