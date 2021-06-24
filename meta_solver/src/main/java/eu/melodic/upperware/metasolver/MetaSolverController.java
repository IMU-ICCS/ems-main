/*
 * Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.upperware.metasolver;

import eu.melodic.models.commons.NotificationResult;
import eu.melodic.models.commons.NotificationResultImpl;
import eu.melodic.models.interfaces.metaSolver.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
//@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class MetaSolverController {

    @Autowired
    private Coordinator coordinator;

	private String jwtToken = null;
	
	public String getAuthenticationToken() {
		return jwtToken;
	}

	public void setAuthenticationToken(String s) { if (StringUtils.isNotEmpty(s)) jwtToken = s.trim(); }

    @RequestMapping(value = "/constraintProblemEnhancement", method = POST)
    public ConstraintProblemEnhancementResponse selectSolver(@RequestBody ConstraintProblemEnhancementRequestImpl request,
                                                             @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String jwtToken)
            throws ConcurrentAccessException
	{
        setAuthenticationToken(jwtToken);
		
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
        coordinator.stopUpdatingCpModel();
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
        setAuthenticationToken(jwtToken);
		
        // Get information from request
        String applicationId = request.getApplicationId();
        String cdoModelsPath = request.getCdoModelsPath();
        String requestUuid = request.getWatermark().getUuid();
        log.info("Received request: " + applicationId + " " + cdoModelsPath + " " + requestUuid);

        // Evaluate new solution
        log.info("Evaluate current solution: ");
        SolutionEvaluationResponse.EvaluationResultType solutionEvaluation = coordinator.evaluateSolution(applicationId, cdoModelsPath);
        coordinator.startUpdatingCpModel(applicationId, cdoModelsPath);
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
        setAuthenticationToken(jwtToken);
		
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
        response.setDeployedSolutionId(pos.getLeft());
        response.setWatermark(coordinator.prepareWatermark(requestUuid));

        return response;
    }

    @RequestMapping(value = "/updateConfiguration", method = POST)
    public String updateConfiguration(@RequestBody String configStr,
                                      @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String jwtToken)
            throws ConcurrentAccessException
	{
        setAuthenticationToken(jwtToken);
		
        log.info("updateConfiguration: json={}", configStr);

        // Unserialize configuration from JSON
        com.google.gson.Gson gson = new com.google.gson.Gson();
        Map<String,Object> configuration = gson.fromJson(configStr, Map.class);
        log.info("updateConfiguration: new configuration={}", configuration);

        // Update MetaSolver MVV-map
        Map<String,String> mvvMap = (Map<String,String>) configuration.get("mvv");
        coordinator.stopUpdatingCpModel();
        coordinator.setMvvMap(mvvMap);

        // Update MetaSolver subscriptions
        Set<Map> subscriptions = new HashSet<>( (Collection<Map>)configuration.get("subscriptions") );
        coordinator.updateSubscriptions(subscriptions);

        return "OK";
    }

    @RequestMapping(value = "/simulateReconfiguration", method = POST)
    public SimulatedMetricValuesResponseImpl simulateReconfiguration(@RequestBody SimulatedMetricValuesRequestImpl request,
                                                                          @RequestHeader(name = HttpHeaders.AUTHORIZATION) String jwtToken)
            throws ConcurrentAccessException
    {
        setAuthenticationToken(jwtToken);
        String applicationId = request.getApplicationId();
        log.info("Received request: {}", applicationId);

        // set metrics and request reconfiguration
        log.info("Setting Simulated metrics and reconfiguration request");
        coordinator.simulateReconfiguration(request.getMetricValues(), applicationId);
        log.info("SimulateReconfiguration: Setting Simulated metrics and reconfiguration request finished");

        SimulatedMetricValuesResponseImpl response = new SimulatedMetricValuesResponseImpl();
        response.setApplicationId(applicationId);

        return response;
    }

    @GetMapping("/getMetricNames/{applicationId}")
    public MetricsNamesResponse getMetricNames(@PathVariable("applicationId") String applicationId,
                                               @RequestHeader(name = HttpHeaders.AUTHORIZATION) String jwtToken) {

        setAuthenticationToken(jwtToken);
        log.info("Received request for metric names: ");
        MetricsNamesResponse metricsNamesResponse = new MetricsNamesResponseImpl();
        metricsNamesResponse.setMetricsNames(coordinator.getMetricNames(applicationId));

        return metricsNamesResponse;
    }

    @RequestMapping(value = "/health", method = GET)
    public void health() {
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public String handleException(BadRequestException exception) {
        log.error(format("Returning error response: invalid request (%s) ", exception.getMessage()));
        return exception.getMessage();
    }
}
