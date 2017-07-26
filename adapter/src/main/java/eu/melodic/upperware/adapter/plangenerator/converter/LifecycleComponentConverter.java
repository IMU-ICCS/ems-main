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
import eu.melodic.upperware.adapter.plangenerator.model.LifecycleComponent;
import eu.paasage.camel.deployment.Configuration;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.InternalComponent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.common.util.EList;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static java.util.stream.Collectors.*;

@Slf4j
@Service
public class LifecycleComponentConverter implements ModelConverter<DeploymentModel, Collection<LifecycleComponent>> {

  @Override
  public Collection<LifecycleComponent> toComparableModel(DeploymentModel model) {
    log.info("Building lifecycle component models (based on configuration of internal components)");
    EList<InternalComponent> ics = model.getInternalComponents();
    if (CollectionUtils.isEmpty(ics)) {
      log.info("There are no internal components defined - no lifecycle components will be created");
      return Sets.newHashSet();
    }
    return ics.stream().map(this::toLifecycleComponent).collect(toSet());
  }

  private LifecycleComponent toLifecycleComponent(InternalComponent ic) {
    log.info("Processing of {}", ic.getName());

    Configuration config = ConverterUtils.extractConfiguration(ic);

    LifecycleComponent lc = LifecycleComponent.builder()
      .name(config.getName())
      .preInstallCmd(config.getDownloadCommand())
      .installCmd(config.getInstallCommand())
      .postInstallCmd(config.getConfigureCommand())
      .startCmd(config.getStartCommand())
      .startDetectionCmd(config.getUploadCommand())
      .stopCmd(config.getStopCommand())
      .build();

    log.info("Built component: {}", lc);

    return lc;
  }
}
