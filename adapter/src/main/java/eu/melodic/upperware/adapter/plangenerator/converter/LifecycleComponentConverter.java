/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.plangenerator.converter;

import camel.deployment.*;
import com.google.common.collect.Sets;
import eu.melodic.upperware.adapter.plangenerator.model.LifecycleComponent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.common.util.EList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static java.util.stream.Collectors.*;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class LifecycleComponentConverter implements ModelConverter<DeploymentInstanceModel, Collection<LifecycleComponent>> {

  @Override
  public Collection<LifecycleComponent> toComparableModel( DeploymentInstanceModel model) {
    log.info("Building lifecycle component models (based on configuration of internal components)");
    DeploymentTypeModel initialModel = ConverterUtils.findDeploymentTypeModel(model);
    EList<SoftwareComponent> ics = initialModel.getSoftwareComponents();
    if (CollectionUtils.isEmpty(ics)) {
      log.info("There are no internal components defined - no lifecycle components will be created");
      return Sets.newHashSet();
    }
    return ics.stream().map(this::toLifecycleComponent).collect(toSet());
  }

  private LifecycleComponent toLifecycleComponent(SoftwareComponent sc) {
    log.info("Processing of {}", sc.getName());

    ScriptConfiguration config = ConverterUtils.extractConfiguration(sc);

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
