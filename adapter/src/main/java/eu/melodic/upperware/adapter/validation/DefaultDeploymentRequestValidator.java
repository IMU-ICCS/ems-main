/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.validation;

import eu.melodic.models.commons.Watermark;
import eu.melodic.models.interfaces.adapter.ApplicationDeploymentRequestImpl;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Service
public class DefaultDeploymentRequestValidator implements DeploymentRequestValidator {

  @Override
  public boolean validate(ApplicationDeploymentRequestImpl request) {

    if (isEmpty(request.getApplicationId())) {
      throw new BadRequestException("applicationId cannot be empty");
    }
    if (isEmpty(request.getNotificationURI())) {
      throw new BadRequestException("notificationUri cannot be empty");
    }

    Watermark watermark = request.getWatermark();

    if (watermark == null) {
      throw new BadRequestException("watermark cannot be empty");
    }
    if (isEmpty(watermark.getUuid())) {
      throw new BadRequestException("watermark uuid cannot be empty");
    }

    return true;
  }
}
