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
import eu.melodic.upperware.adapter.plangenerator.model.CloudApi;
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
public class CloudApiConverter implements ModelConverter<DeploymentInstanceModel, Collection<CloudApi>> {

  private ProviderInfoSupplier providerInfoSupplier;

  @Override
  public Collection<CloudApi> toComparableModel(DeploymentInstanceModel model) {
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

    CloudApi cloudApi = CloudApi.builder()
      .name(providerInfoSupplier.getApiName(vmInst))
      .driver(providerInfoSupplier.getDriver(vmInst))
      .build();

    log.info("Built cloud api: {}", cloudApi);

    return cloudApi;
  }
}
