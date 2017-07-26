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
import eu.paasage.upperware.adapter.plangenerator.model.CloudCredential;
import eu.paasage.upperware.adapter.properties.AdapterProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.common.util.EList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

import static eu.paasage.upperware.adapter.plangenerator.converter.ConverterUtils.ATTRIB_NAME;
import static eu.paasage.upperware.adapter.plangenerator.converter.ConverterUtils.CLOUD_CREDENTIAL_NAME_SUFFIX;
import static eu.paasage.upperware.adapter.plangenerator.converter.ConverterUtils.convertToString;
import static java.util.stream.Collectors.toSet;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class CloudCredentialConverter implements ModelConverter<DeploymentModel, Collection<CloudCredential>> {

  private AdapterProperties properties;

  @Override
  public Collection<CloudCredential> toComparableModel(DeploymentModel model) {
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

    String name = null;
    String cloudName = null;

    Feature rootFeature = (Feature) vmInst.getVmType().eContainer().eContainer();

    for (Attribute attr : rootFeature.getAttributes()) {
      switch (attr.getName()) {
        case ATTRIB_NAME:
          cloudName = convertToString(attr.getValue());
          name = cloudName + CLOUD_CREDENTIAL_NAME_SUFFIX;
          break;
      }
    }

    AdapterProperties.Clouds clouds = properties.getClouds();

    CloudCredential cloudCredential = CloudCredential.builder()
      .name(name)
      .cloudName(cloudName)
      .login(clouds.getLogin(cloudName))
      .password(clouds.getPassword(cloudName))
      .tenant(1L)
      .build();

    log.info("Built cloud credential: {}", cloudCredential);

    return cloudCredential;
  }
}
