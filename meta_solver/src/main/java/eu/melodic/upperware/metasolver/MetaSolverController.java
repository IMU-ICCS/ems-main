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
import eu.melodic.models.interfaces.metaSolver.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.cdo.util.ConcurrentAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.BadRequestException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class MetaSolverController {

    private Coordinator coordinator;
	
	private String jwtToken = null;
	
	public String getAuthenticationToken() {
		return jwtToken;
	}

    @RequestMapping(value = "/constraintProblemEnhancement", method = POST)
    public ConstraintProblemEnhancementResponse selectSolver(@RequestBody ConstraintProblemEnhancementRequestImpl request,
                                                             @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String jwtToken)
            throws ConcurrentAccessException
	{
		this.jwtToken = jwtToken;
		
        // Get information from request
        String applicationId = request.getApplicationId();
        String cdoModelsPath = request.getCdoModelsPath();
        String requestUuid = request.getWatermark().getUuid();
        log.info("Received request: " + applicationId + " " + cdoModelsPath + " " + requestUuid);

        // Select suitable solver
        log.info("Selecting suitable solver: ");
        ConstraintProblemEnhancementResponse.DesignatedSolverType selectedSolver = coordinator.selectSolver(applicationId, cdoModelsPath);
        log.info("Selecting suitable solver: {}", selectedSolver);

        // Set metric values in CP model
        coordinator.setMetricValuesInCpModel(applicationId, cdoModelsPath);

        // Prepare and return response
        NotificationResult notificationResult = new NotificationResultImpl();
        notificationResult.setStatus(NotificationResult.StatusType.SUCCESS);

        ConstraintProblemEnhancementResponseImpl response = new ConstraintProblemEnhancementResponseImpl();
        response.setApplicationId(applicationId);
        response.setResult(notificationResult);
        response.setDesignatedSolver(selectedSolver);
        response.setWatermark(coordinator.prepareWatermark(requestUuid));

        return response;
    }

    @RequestMapping(value = "/solutionEvaluation", method = POST)
    public SolutionEvaluationResponse solutionEvaluation(@RequestBody SolutionEvaluationRequestImpl request,
                                                         @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String jwtToken)
            throws ConcurrentAccessException
	{
		this.jwtToken = jwtToken;
		
        // Get information from request
        String applicationId = request.getApplicationId();
        String cdoModelsPath = request.getCdoModelsPath();
        String requestUuid = request.getWatermark().getUuid();
        log.info("Received request: " + applicationId + " " + cdoModelsPath + " " + requestUuid);

        // Evaluate new solution
        log.info("Evaluate current solution: ");
        SolutionEvaluationResponse.EvaluationResultType solutionEvaluation = coordinator.evaluateSolution(applicationId, cdoModelsPath);
        log.info("Evaluate current solution: {}", solutionEvaluation);

        // Prepare and return response
        SolutionEvaluationResponseImpl response = new SolutionEvaluationResponseImpl();
        response.setApplicationId(applicationId);
        response.setEvaluationResult(solutionEvaluation);
        response.setWatermark(coordinator.prepareWatermark(requestUuid));

        return response;
    }

    @RequestMapping(value = "/updateSolution", method = POST)
    public UpdateSolutionResponse updateSolution(@RequestBody UpdateSolutionRequest request,
                                                 @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String jwtToken)
            throws ConcurrentAccessException
	{
		this.jwtToken = jwtToken;
		
        // Get information from request
        String applicationId = request.getApplicationId();
        String cdoModelsPath = request.getCdoModelsPath();
        NotificationResult notifRes = request.getDeploymentResult();
        boolean success = notifRes.getStatus().equals(NotificationResult.StatusType.SUCCESS);
        String requestUuid = request.getWatermark().getUuid();
        log.info("Received request: " + applicationId + " " + cdoModelsPath + " " + requestUuid);

        // Set metric values in CP model
        coordinator.setMetricValuesInCpModel(applicationId, cdoModelsPath);

        // Update deployed and candidate solution Ids (positions) in CP model
        log.info("Update solution: ");
        Pair<Integer,Integer> pos = coordinator.updateSolutionIdsInCpModel(applicationId, cdoModelsPath, success);
        log.info("Update solution: deployed={}, candidate={}", pos.getLeft(), pos.getRight());

        // Prepare and return response
        UpdateSolutionResponseImpl response = new UpdateSolutionResponseImpl();
        response.setApplicationId(applicationId);
        response.setUpdateResult(notifRes);
        response.setWatermark(coordinator.prepareWatermark(requestUuid));

        return response;
    }

    @RequestMapping(value = "/updateConfiguration", method = POST)
    public String updateConfiguration(@RequestBody String configStr,
                                      @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String jwtToken)
            throws ConcurrentAccessException
	{
		this.jwtToken = jwtToken;
		
        log.info("updateConfiguration: json={}", configStr);

        // Unserialize configuration from JSON
        com.google.gson.Gson gson = new com.google.gson.Gson();
        Map<String,Object> configuration = gson.fromJson(configStr, Map.class);
        log.info("updateConfiguration: new configuration={}", configuration);

        // Update MetaSolver MVV-map
        Map<String,String> mvvMap = (Map<String,String>) configuration.get("mvv");
        coordinator.setMvvMap(mvvMap);

        // Update MetaSolver subscriptions
        Set<Map> subscriptions = new HashSet<>( (Collection<Map>)configuration.get("subscriptions") );
        coordinator.updateSubscriptions(subscriptions);

        return "OK";
    }

    @RequestMapping(value = "/health", method = GET)
    public void health() {
    }

    //XXX: DELETE:
    @RequestMapping(value = "/esbTest", method = POST)
    public eu.melodic.models.services.metaSolver.DeploymentProcessResponse esbTest(@RequestBody eu.melodic.models.services.metaSolver.DeploymentProcessRequest request) {
        log.warn(">>>> Call to /esbTest: app-id={}, use-existing-cp={}, cdo-path={}, uuid={}",
                request.getApplicationId(), request.getUseExistingCP(), request.getCdoResourcePath(), request.getWatermark().getUuid());

        eu.melodic.models.services.metaSolver.DeploymentProcessResponseImpl response = new eu.melodic.models.services.metaSolver.DeploymentProcessResponseImpl();
        eu.melodic.models.commons.NotificationResult notif = new eu.melodic.models.commons.NotificationResultImpl();
        java.util.Map<String, Object> notifMap = new java.util.HashMap<>();
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
