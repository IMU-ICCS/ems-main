/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.plangenerator.converter;

import camel.core.CamelModel;
import camel.deployment.DeploymentInstanceModel;
import camel.organisation.OrganisationModel;
import eu.melodic.upperware.adapter.plangenerator.model.Application;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.common.util.EList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ApplicationConverter implements ModelConverter<DeploymentInstanceModel, Application> {

  @Override
  public Application toComparableModel(DeploymentInstanceModel model) {
    log.info("Building application model");
    Application app = toApplication(ConverterUtils.extractApplication((CamelModel) model.eContainer()));
    log.info("Built application: {}", app);
    return app;
  }

  private Application toApplication(camel.core.Application app) {
    EList<OrganisationModel> organisationModels = ((CamelModel) app.eContainer()).getOrganisationModels();
    if (CollectionUtils.isEmpty(organisationModels)) {
      throw new IllegalArgumentException("Empty OrganisationModel!");
    }

    return Application.builder()
      .name(app.getName())
      .version(app.getVersion())
      .description(app.getDescription())
      .owner(organisationModels.get(0).getName())
      .build();
  }

}
