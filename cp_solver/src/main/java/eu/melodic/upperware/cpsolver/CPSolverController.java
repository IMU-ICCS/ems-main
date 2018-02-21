/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.cpsolver;

import eu.melodic.models.interfaces.cpSolver.ConstraintProblemSolutionFromFileRequestImpl;
import eu.melodic.models.interfaces.cpSolver.ConstraintProblemSolutionRequestImpl;
import eu.melodic.upperware.cpsolver.lib.CPSolverExecutor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@RestController
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class CPSolverController {

  private CPSolverExecutor executor;

  @RequestMapping(value = "/constraintProblemSolution", method = POST)
  public void applySolution(@RequestBody ConstraintProblemSolutionRequestImpl request) {
    String applicationId = request.getApplicationId();
    String cdoResourcePath = request.getCdoModelsPath();
    String notificationUri = request.getNotificationURI();
    String requestUuid = request.getWatermark().getUuid();
    Boolean useExternalOptimizer = request.getUseExternalOptimizer();
    log.info("Received request: " +applicationId +" " + cdoResourcePath + " " +notificationUri + " " +requestUuid + " " + useExternalOptimizer );

    executor.generateCPSolution(applicationId, cdoResourcePath, notificationUri, requestUuid, useExternalOptimizer);
    log.info("Sleeping...");
  }

  @RequestMapping(value = "/constraintProblemSolutionFromFile", method = POST)
  public void constraintProblemSolutionFromFile(@RequestBody ConstraintProblemSolutionFromFileRequestImpl request) throws Exception {
    String applicationId = request.getApplicationId();
    String filePath = request.getFileModelsPath();
    String requestUuid = request.getWatermark().getUuid();
    String nodeCandidatesFilePath = "/Users/mrozanska/logs/FCR_withMetrics1519221672517.txt";
    Boolean useExternalOptimizer = request.getUseExternalOptimizer();
    log.info("Received constraintProblemSolutionFromFile request: " +applicationId +" " + filePath + " " +requestUuid + " " +useExternalOptimizer);
    executor.generateCPSolutionFromFile(applicationId, filePath, nodeCandidatesFilePath, requestUuid, useExternalOptimizer);
  }


}
