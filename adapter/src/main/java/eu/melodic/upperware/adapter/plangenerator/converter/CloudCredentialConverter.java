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
import eu.melodic.upperware.adapter.plangenerator.model.CloudCredential;
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

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class CloudCredentialConverter implements ModelConverter<DeploymentInstanceModel, Collection<CloudCredential>> {

  private AdapterProperties properties;
  private ProviderInfoSupplier providerInfoSupplier;

  @Override
  public Collection<CloudCredential> toComparableModel(DeploymentInstanceModel model) {
    log.info("Building cloud credential models (based on VM instances)");
    EList<VMInstance> vmInsts = model.getVmInstances();
    if (CollectionUtils.isEmpty(vmInsts)) {
      log.info("There are no VM instances defined - no cloud credentials will be created");
      return Sets.newHashSet();
    }
    log.debug("Iterating over VM instances to retrieve Root Features describing cloud credentials associated with those instances");
    log.debug("Logins and passwords will be read from properties");
    Set<CloudCredential> clouds = vmInsts.stream().map(this::toCloudCredential).collect(toSet());
    log.info("Built unique cloud credentials: {}", clouds);
    return clouds;
  }

  private CloudCredential toCloudCredential(VMInstance vmInst) {
    log.info("Processing of {}", vmInst.getName());

    String cloudName = providerInfoSupplier.getCloudName(vmInst);

    AdapterProperties.Clouds clouds = properties.getClouds();

    CloudCredential cloudCredential = CloudCredential.builder()
      .name(providerInfoSupplier.getCredentialsName(vmInst))
      .cloudName(cloudName)
      .login(clouds.getLogin(cloudName))
      .password(clouds.getPassword(cloudName))
      .tenant(1L)
      .build();

    log.info("Built cloud credential: {}", cloudCredential);

    return cloudCredential;
  }
}
