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
import eu.paasage.upperware.adapter.plangenerator.model.Communication;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.common.util.EList;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static java.util.stream.Collectors.toSet;

@Slf4j
@Service
public class CommunicationConverter implements ModelConverter<DeploymentModel, Collection<Communication>> {

  @Override
  public Collection<Communication> toComparableModel(DeploymentModel model) {
    log.info("Building communication models");
    EList<eu.paasage.camel.deployment.Communication> comms = model.getCommunications();
    if (CollectionUtils.isEmpty(comms)) {
      log.info("There are no communications defined - no communications will be created");
      return Sets.newHashSet();
    }
    return comms.stream().map(this::toCommunication).collect(toSet());
  }

  private Communication toCommunication(eu.paasage.camel.deployment.Communication comm) {
    log.info("Processing of {}", comm.getName());

    Communication communication = Communication.builder()
      .name(comm.getName())
      .portProvName(comm.getProvidedCommunication().getName())
      .portReqName(comm.getRequiredCommunication().getName())
      .build();

    log.info("Built communication: {}", communication);

    return communication;
  }
}
