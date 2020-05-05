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
import eu.melodic.upperware.cpsolver.solver.CPSolverCoordinator;
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

    private CPSolverCoordinator cpSolverCoordinator;

    @RequestMapping(value = "/constraintProblemSolution", method = POST)
    public void applySolution(@RequestBody ConstraintProblemSolutionRequestImpl request) {
        log.info("LEDDDDSRERR");
        String applicationId = request.getApplicationId();
        String cdoResourcePath = request.getCdoModelsPath();
        String notificationUri = request.getNotificationURI();
        String requestUuid = request.getWatermark().getUuid();
        log.info("Received request: " + applicationId + " " + cdoResourcePath + " " + notificationUri + " " + requestUuid);

        cpSolverCoordinator.generateCPSolution(applicationId, cdoResourcePath, notificationUri, requestUuid);
        log.info("Sleeping...");
    }


    @RequestMapping(value = "/constraintProblemSolutionFromFile", method = POST)
    public void constraintProblemSolutionFromFile(@RequestBody ConstraintProblemSolutionFromFileRequestImpl request) throws Exception {
        log.info("LESRERR");
        String camelModelFilePath = request.getCamelModelFilePath();
        String cpModelPath = request.getCpProblemFilePath();
        String nodeCandidatesFilePath = request.getNodeCandidatesFilePath();
        log.info("Received constraintProblemSolutionFromFile request: \n" + camelModelFilePath + " \n" + cpModelPath);

        cpSolverCoordinator.generateCPSolutionFromFile(camelModelFilePath, cpModelPath, nodeCandidatesFilePath);
        log.info("Sleeping...");
    }

}
