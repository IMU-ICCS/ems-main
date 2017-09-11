/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.solvertodeployment;

import eu.melodic.models.interfaces.solverToDeployment.ApplySolutionRequestImpl;
import eu.paasage.upperware.solvertodeployment.lib.SolverToDeployment;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@AllArgsConstructor(onConstructor = @__({@Autowired}))
@Slf4j
public class SolverToDeploymentController {

  private  SolverToDeployment solverToDeployment;

  @RequestMapping(value = "/applySolution", method = POST)
  public void applySolution(@RequestBody ApplySolutionRequestImpl request) {
    String applicationId = request.getApplicationId();
    String cdoResourcePath = request.getCdoModelsPath();
    String notificationUri = request.getNotificationURI();
    String requestUuid = request.getWatermark().getUuid();
    log.info("Received request: " +applicationId +" " + cdoResourcePath + " " +notificationUri + " " +requestUuid);

    try {
      solverToDeployment.doWorkTS(applicationId,cdoResourcePath , notificationUri, requestUuid);
    } catch (Exception e) {
      log.error("doWorkTS returned exception.", e);
    }
  }


}
