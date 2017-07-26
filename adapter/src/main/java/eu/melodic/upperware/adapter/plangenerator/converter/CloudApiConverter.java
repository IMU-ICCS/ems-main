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
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.VMInstance;
import eu.paasage.camel.provider.Attribute;
import eu.paasage.camel.provider.Feature;
import eu.melodic.upperware.adapter.plangenerator.model.CloudApi;
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
public class CloudApiConverter implements ModelConverter<DeploymentModel, Collection<CloudApi>> {

  @Override
  public Collection<CloudApi> toComparableModel(DeploymentModel model) {
    log.info("Building cloud api models (based on VM instances)");
    EList<VMInstance> vmInsts = model.getVmInstances();
    if (CollectionUtils.isEmpty(vmInsts)) {
      log.info("There are no VM instances defined - no cloud apis will be created");
      return Sets.newHashSet();
    }
    log.debug("Iterating over VM instances to retrieve Root Features describing cloud apis associated with those instances");
    Set<CloudApi> cloudApis = vmInsts.stream().map(this::toCloudApi).collect(toSet());
    log.info("Built unique cloud apis: {}", cloudApis);
    return cloudApis;
  }

  private CloudApi toCloudApi(VMInstance vmInst) {
    log.info("Processing of {}", vmInst.getName());

    String name = null;
    String driver = null;

    Feature rootFeature = (Feature) vmInst.getVmType().eContainer().eContainer();

    for (Attribute attr : rootFeature.getAttributes()) {
      switch (attr.getName()) {
        case ConverterUtils.ATTRIB_NAME:
          name = ConverterUtils.convertToString(attr.getValue()) + ConverterUtils.CLOUD_API_NAME_SUFFIX;
          break;
        case ConverterUtils.ATTRIB_DRIVER:
          driver = ConverterUtils.convertToString(attr.getValue());
          break;
      }
    }

    CloudApi cloudApi = CloudApi.builder()
      .name(name)
      .driver(driver)
      .build();

    log.info("Built cloud api: {}", cloudApi);

    return cloudApi;
  }
}
