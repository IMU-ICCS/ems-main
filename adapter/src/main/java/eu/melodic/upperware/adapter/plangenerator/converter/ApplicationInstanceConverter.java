/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.plangenerator.converter;

import camel.core.Application;
import camel.core.CamelModel;
import camel.deployment.DeploymentInstanceModel;
import eu.melodic.upperware.adapter.plangenerator.model.ApplicationInstance;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ApplicationInstanceConverter implements ModelConverter<DeploymentInstanceModel, ApplicationInstance> {

  @Override
  public ApplicationInstance toComparableModel(DeploymentInstanceModel model) {
    log.info("Building application instance model");
    ApplicationInstance appInst = toApplicationInstance(ConverterUtils.extractApplication((CamelModel) model.eContainer()));
    log.info("Built application instance: {}", appInst);
    return appInst;
  }

  private ApplicationInstance toApplicationInstance(Application app) {
    String appName = app.getName();
    return ApplicationInstance.builder()
      .name(appName + ConverterUtils.APP_INST_NAME_SUFFIX)
      .appName(appName)
      .build();
  }
}
