/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.metasolver;

import eu.melodic.models.commons.NotificationResult;
import eu.melodic.models.commons.NotificationResultImpl;

import eu.melodic.models.interfaces.metaSolver.ConstraintProblemEnhancementRequest;
import eu.melodic.models.interfaces.metaSolver.ConstraintProblemEnhancementRequestImpl;
import eu.melodic.models.interfaces.metaSolver.ConstraintProblemEnhancementResponse;
import eu.melodic.models.interfaces.metaSolver.ConstraintProblemEnhancementResponseImpl;

import eu.melodic.models.interfaces.metaSolver.SolutionEvaluationRequest;
import eu.melodic.models.interfaces.metaSolver.SolutionEvaluationRequestImpl;
import eu.melodic.models.interfaces.metaSolver.SolutionEvaluationResponse;
import eu.melodic.models.interfaces.metaSolver.SolutionEvaluationResponseImpl;

import eu.melodic.models.interfaces.metaSolver.UpdateSolutionRequest;
import eu.melodic.models.interfaces.metaSolver.UpdateSolutionRequestImpl;
import eu.melodic.models.interfaces.metaSolver.UpdateSolutionResponse;
import eu.melodic.models.interfaces.metaSolver.UpdateSolutionResponseImpl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.cdo.util.ConcurrentAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import javax.ws.rs.BadRequestException;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@RestController
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class MetaSolverController {

	private Coordinator coordinator;

	@RequestMapping(value = "/constraintProblemEnhancement", method = POST)
	public ConstraintProblemEnhancementResponse selectSolver(@RequestBody ConstraintProblemEnhancementRequestImpl request) throws ConcurrentAccessException {
		// Get information from request
		String applicationId = request.getApplicationId();
		String cdoModelsPath = request.getCdoModelsPath();
		String requestUuid = request.getWatermark().getUuid();
		log.info("Received request: " +applicationId +" " + cdoModelsPath + " " + requestUuid );
		
		// Select suitable solver
		log.info("Selecting suitable solver: ");
		ConstraintProblemEnhancementResponse.DesignatedSolverType selectedSolver = coordinator.selectSolver(applicationId, cdoModelsPath);
		log.info("Selecting suitable solver: {}", selectedSolver);
		
		// Set metric values in CP model
		coordinator.setMetricValuesInCpModel(applicationId, cdoModelsPath);
		
		// Prepare and return response
		NotificationResult notificationResult = new NotificationResultImpl();
		notificationResult.setStatus( NotificationResult.StatusType.SUCCESS );
		
		ConstraintProblemEnhancementResponseImpl response = new ConstraintProblemEnhancementResponseImpl();
		response.setApplicationId( applicationId );
		response.setResult( notificationResult );
		response.setDesignatedSolver( selectedSolver );
		response.setWatermark( coordinator.prepareWatermark(requestUuid) );
		
		return response;
	}

	@RequestMapping(value = "/solutionEvaluation", method = POST)
	public SolutionEvaluationResponse solutionEvaluation(@RequestBody SolutionEvaluationRequestImpl request) throws ConcurrentAccessException {
		// Get information from request
		String applicationId = request.getApplicationId();
		String cdoModelsPath = request.getCdoModelsPath();
		String requestUuid = request.getWatermark().getUuid();
		log.info("Received request: " +applicationId +" " + cdoModelsPath + " " + requestUuid );
		
		// Evaluate new solution
		log.info("Evaluate current sulution: ");
		SolutionEvaluationResponse.EvaluationResultType solutionEvaluation = coordinator.evaluateSolution(applicationId, cdoModelsPath);
		log.info("Evaluate current sulution: {}", solutionEvaluation);
		
		// Prepare and return response
		SolutionEvaluationResponseImpl response = new SolutionEvaluationResponseImpl();
		response.setApplicationId( applicationId );
		response.setEvaluationResult( solutionEvaluation );
		response.setWatermark( coordinator.prepareWatermark(requestUuid) );
		
		return response;
	}

	@RequestMapping(value = "/updateSolution", method = POST)
	public UpdateSolutionResponse updateSolution(@RequestBody UpdateSolutionRequest request) throws ConcurrentAccessException {
		// Get information from request
		String applicationId = request.getApplicationId();
		String cdoModelsPath = request.getCdoModelsPath();
		NotificationResult notifRes = request.getDeploymentResult();
		boolean success = notifRes.getStatus().equals( NotificationResult.StatusType.SUCCESS );
		String requestUuid = request.getWatermark().getUuid();
		log.info("Received request: " +applicationId +" " + cdoModelsPath + " " + requestUuid );
		
		// Update deployed and candidate solution Ids (positions) in CP model
		log.info("Update solution: ");
		int[] pos = coordinator.updateSolutionIdsInCpModel(applicationId, cdoModelsPath, success);
		log.info("Update solution: deployed={}, candidate={}", pos[0], pos[1]);
		
		// Prepare and return response
		UpdateSolutionResponseImpl response = new UpdateSolutionResponseImpl();
		response.setApplicationId( applicationId );
		response.setUpdateResult( notifRes );
		response.setWatermark( coordinator.prepareWatermark(requestUuid) );
		
		return response;
	}

	@RequestMapping(value = "/health", method = GET)
	public void health() {
	}

	@RequestMapping(value = "/esbTest", method = POST)
	public eu.melodic.models.services.metaSolver.DeploymentProcessResponse esbTest(@RequestBody eu.melodic.models.services.metaSolver.DeploymentProcessRequest request) {
		log.warn(">>>> Call to /esbTest: app-id={}, use-existing-cp={}, cdo-path={}, uuid={}", 
		request.getApplicationId(), request.getUseExistingCP(), request.getCdoResourcePath(), request.getWatermark().getUuid());
		
		eu.melodic.models.services.metaSolver.DeploymentProcessResponseImpl response = new eu.melodic.models.services.metaSolver.DeploymentProcessResponseImpl();
		eu.melodic.models.commons.NotificationResult notif = new eu.melodic.models.commons.NotificationResultImpl();
		java.util.Map<String,Object> notifMap = new java.util.HashMap<>();
		notifMap.put("xxxxxx-test-additional-property-name", "yyyyyyyyy-value");
		notif.setStatus(eu.melodic.models.commons.NotificationResult.StatusType.SUCCESS);
		notif.setAdditionalProperties(notifMap);
		response.setResult(notif);
		response.setProcessId("zzzzzzzzzzz-the-process-ID");
		return response;
	}

	@ExceptionHandler
	@ResponseStatus(BAD_REQUEST)
	public String handleException(BadRequestException exception) {
		log.error(format("Returning error response: invalid request (%s) ", exception.getMessage()));
		return exception.getMessage();
	}

}
