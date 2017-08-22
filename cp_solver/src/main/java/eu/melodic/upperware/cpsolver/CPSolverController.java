/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.cpsolver;

import eu.melodic.models.interfaces.cpSolver.ConstraintProblemSolutionRequestImpl;
import eu.melodic.upperware.cpsolver.lib.CPSolver;
import eu.melodic.upperware.cpsolver.lib.CPSolverExecutor;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class CPSolverController {

  private CPSolverExecutor executor;

  final static Logger logger = Logger.getLogger(CPSolverController.class);

  @RequestMapping(value = "/constraintProblemSolution", method = POST)
  public void applySolution(@RequestBody ConstraintProblemSolutionRequestImpl request) {
    String applicationId = request.getApplicationId();
    String cdoResourcePath = request.getCdoModelsPath();
    String notificationUri = request.getNotificationURI();
    String requestUuid = request.getWatermark().getUuid();
    logger.info("Received request: " +applicationId +" " + cdoResourcePath + " " +notificationUri + " " +requestUuid);

    executor.generateCPSolution(applicationId, cdoResourcePath, notificationUri, requestUuid);
    logger.info("Sleeping...");
  }


}
