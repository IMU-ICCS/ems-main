/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.plangenerator.converter;

import eu.melodic.upperware.adapter.plangenerator.model.Application;
import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.organisation.Entity;
import eu.paasage.camel.organisation.OrganisationModel;
import eu.paasage.camel.organisation.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ApplicationConverter implements ModelConverter<DeploymentModel, Application> {

  @Override
  public Application toComparableModel(DeploymentModel model) {
    log.info("Building application model");
    Application app = toApplication(ConverterUtils.extractApplication((CamelModel) model.eContainer()));
    log.info("Built application: {}", app);
    return app;
  }

  private Application toApplication(eu.paasage.camel.Application app) {
    return Application.builder()
      .name(app.getName())
      .version(app.getVersion())
      .description(app.getDescription())
      .owner(toOwner(app.getOwner()))
      .build();
  }

  private String toOwner(Entity owner) {
    if (owner instanceof OrganisationModel) {
      log.debug("Application owner is being got directly from name of OrganisationModel");
      return ((OrganisationModel) owner).getName();
    }
    if (owner instanceof User) {
      log.debug("Application owner is being got from name of OrganisationModel via User");
      return ((OrganisationModel) owner.eContainer()).getName();
    }
    log.debug("Application owner is not an instance of OrganisationModel nor User - the owner will not be set");
    return null;
  }
}
