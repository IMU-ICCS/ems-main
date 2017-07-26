/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.plangenerator.converter;

import eu.paasage.camel.Application;
import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.melodic.upperware.adapter.plangenerator.model.ApplicationInstance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ApplicationInstanceConverter implements ModelConverter<DeploymentModel, ApplicationInstance> {

  @Override
  public ApplicationInstance toComparableModel(DeploymentModel model) {
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
