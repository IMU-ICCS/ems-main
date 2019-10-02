/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.metasolver;

import eu.melodic.models.commons.Watermark;
import eu.melodic.models.commons.WatermarkImpl;
import eu.melodic.models.interfaces.metaSolver.ConstraintProblemEnhancementResponse;
import eu.melodic.models.interfaces.metaSolver.SolutionEvaluationResponse;
import eu.melodic.models.services.metaSolver.DeploymentProcessRequest;
import eu.melodic.models.services.metaSolver.DeploymentProcessRequestImpl;
import eu.melodic.upperware.metasolver.metricvalue.MetricValueMonitorBean;
import eu.melodic.upperware.metasolver.metricvalue.TopicType;
import eu.melodic.upperware.metasolver.properties.MetaSolverProperties;
import eu.melodic.upperware.metasolver.util.CpModelHelper;
import eu.paasage.upperware.security.authapi.SecurityConstants;
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import eu.paasage.upperware.security.authapi.token.JWTService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.cdo.util.ConcurrentAccessException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@Service
public class Coordinator implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    private MetaSolverController controller;
    private MetaSolverProperties metaSolverProperties;
    private JWTService jwtService;
    private MelodicSecurityProperties melodicSecurityProperties;
    private double uvThresholdFactor;
    private RestTemplate restTemplate;

    private String cacheAppId;
    private String cacheCpModelPath;
    private Map<String,String> mvvToCurrentConfigVarsMap;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        this.controller = applicationContext.getBean(MetaSolverController.class);
        this.jwtService = applicationContext.getBean(JWTService.class);
        this.metaSolverProperties = applicationContext.getBean(MetaSolverProperties.class);
        this.melodicSecurityProperties = applicationContext.getBean(MelodicSecurityProperties.class);
        this.uvThresholdFactor = metaSolverProperties.getUtilityThresholdFactor();
        this.restTemplate = new RestTemplate();
        log.debug("MetaSolver.Coordinator: setApplicationContext(): configuration={}", metaSolverProperties);
    }

    /**
     * How can we select the most appropriate solver??
     * For R2.5 it will always be CP solver
     */
    public ConstraintProblemEnhancementResponse.DesignatedSolverType selectSolver(String applicationId, String cpModelPath) throws ConcurrentAccessException {
        log.info("MetaSolver.Coordinator: selectSolver(): appId={}, model={}", applicationId, cpModelPath);
        this.cacheAppId = applicationId;
        this.cacheCpModelPath = cpModelPath;

        log.warn("MetaSolver.Coordinator: selectSolver(): ** NOTE: CP Solver is ALWAYS selected **");
        log.warn("MetaSolver.Coordinator: selectSolver(): ** NOT IMPLEMENTED **");
        return ConstraintProblemEnhancementResponse.DesignatedSolverType.CPSOLVER;
    }

    /**
     * Update CP model with current metric variable values
     */
    public void setMetricValuesInCpModel(String applicationId, String cpModelPath) throws ConcurrentAccessException {
        log.info("MetaSolver.Coordinator: setMetricValuesInCpModel(): appId={}, model={}", applicationId, cpModelPath);

        // get metric values from metric value registry
        MetricValueMonitorBean monitor = (MetricValueMonitorBean) applicationContext.getBean(MetricValueMonitorBean.class);
        Map<String, String> metricValues = monitor.getMetricValuesRegistry().getMetricValuesAsMap();
        log.debug("MetaSolver.Coordinator: setMetricValuesInCpModel(): Metric values map: {}", metricValues);

        // Update CP model with current metric variable values
        CpModelHelper helper = (CpModelHelper) applicationContext.getBean(CpModelHelper.class);
        helper.updateCpModelWithMetricValues(applicationId, cpModelPath, metricValues);

        log.info("MetaSolver.Coordinator: setMetricValuesInCpModel(): CP model updated with current MVV's");
    }

    /**
     * Compare new and (currently) deployed solutions using their utility values.
     * - If no deployed solution exists (first deployment) then 'accept' new solution
     * - if a deployed solution exists then new solution's utility value must be better
     * than deployed solution's utility value, at least 'uvThresholdFactor' times
     */
    public SolutionEvaluationResponse.EvaluationResultType evaluateSolution(String applicationId, String cpModelPath) throws ConcurrentAccessException {
        log.info("MetaSolver.Coordinator: evaluateSolution(): appId={}, model={}", applicationId, cpModelPath);
        this.cacheAppId = applicationId;
        this.cacheCpModelPath = cpModelPath;

        // Update candidate solution
        CpModelHelper helper = (CpModelHelper) applicationContext.getBean(CpModelHelper.class);
        int newCanPos = helper.findAndSetCandidateSolutionIdInCpModel(applicationId, cpModelPath);
        if (newCanPos >= 0) log.debug("MetaSolver.Coordinator: candidate solution updated: id={}", newCanPos);
        else if (newCanPos == -1) log.debug("MetaSolver.Coordinator: no candidate solution found");
        else log.debug("MetaSolver.Coordinator: an error occurred while looking for candidate solution");

        // Get utility values of new and deployed solutions
        double[] solUv = helper.getSolutionUtilities(applicationId, cpModelPath);
        log.debug("MetaSolver.Coordinator: solUv: {}", solUv);

        // check if an error occurred
        if (solUv == null) {
            log.warn("MetaSolver.Coordinator: evaluateSolution(): RETURN ERROR: An error occurred: appId={}, model={}", applicationId, cpModelPath);
            return SolutionEvaluationResponse.EvaluationResultType.ERROR;
        }
        if (solUv[0] == -2) {
            log.warn("MetaSolver.Coordinator: evaluateSolution(): RETURN ERROR: No solutions found in CP model: appId={}, model={}", applicationId, cpModelPath);
            return SolutionEvaluationResponse.EvaluationResultType.ERROR;
        }
        if (solUv[1] == -1) {
            log.warn("MetaSolver.Coordinator: evaluateSolution(): RETURN ERROR: No candidate solution found in CP model: appId={}, model={}", applicationId, cpModelPath);
            return SolutionEvaluationResponse.EvaluationResultType.ERROR;
        }

        // check if a solution is deployed. If no solution is deployed accept new solution
        if (solUv[0] < 0) {
            log.info("MetaSolver.Coordinator: evaluateSolution(): RETURN POSITIVE: No deployed solution found. Accepting new solution: appId={}, model={}", applicationId, cpModelPath);
            return SolutionEvaluationResponse.EvaluationResultType.POSITIVE;
        }

        // a deployed solution exists. We need to compare the utility values of new and deployed solutions
        log.debug("MetaSolver.Coordinator: evaluateSolution(): utility-threshold-factor={} : appId={}, model={}", uvThresholdFactor, applicationId, cpModelPath);
        double depSolUv = solUv[0];
        double newSolUv = solUv[1];
        if (newSolUv > uvThresholdFactor * depSolUv) {
            log.info("MetaSolver.Coordinator: evaluateSolution(): RETURN POSITIVE: New solution is ACCEPTED: appId={}, model={}", applicationId, cpModelPath);
            return SolutionEvaluationResponse.EvaluationResultType.POSITIVE;
        } else {
            log.info("MetaSolver.Coordinator: evaluateSolution(): RETURN NEGATIVE: New solution is NOT ACCEPTED: appId={}, model={}", applicationId, cpModelPath);
            return SolutionEvaluationResponse.EvaluationResultType.NEGATIVE;
        }
    }

    /**
     * Update deployed and candidate solutions in CP model
     * Input:
     * applicationId : application id
     * cpModelPath: the path to CP model resource in CDO server
     * success: indicates whether adapter succeeded to deploy candidate solution or not
     * if SUCCESS
     * deployed Id <-- candidate Id
     * candidate Id <-- -1
     * if ERROR
     * candidate Id <-- -1
     * Returns:
     * the new solution positions (int[])
     * int[0] : Index of deployed solution in 'solutions' EList
     * int[1] : Index of candidate solution in 'solutions' EList
     * An index equal to -1 means absence of deployed/candidate solution
     * An index equal to -2 means empty 'solutions' EList
     * An index >=0 indicates the position of the deployed/candidate solution in 'solutions' EList
     */
    public Pair<Integer,Integer> updateSolutionIdsInCpModel(String applicationId, String cpModelPath, boolean success) throws ConcurrentAccessException {
        log.info("MetaSolver.Coordinator: updateSolutionIdsInCpModel(): appId={}, model={}, deploy-success={}", applicationId, cpModelPath, success);
        this.cacheAppId = applicationId;
        this.cacheCpModelPath = cpModelPath;

        // Update CP model with new solution positions (or Ids)
        CpModelHelper helper = (CpModelHelper) applicationContext.getBean(CpModelHelper.class);
        Pair<Integer,Integer> newPos = helper.updateSolutionIdsInCpModel(applicationId, cpModelPath, success);

        // Copy values of MVVs to Current Config. variables
        if (mvvToCurrentConfigVarsMap!=null) {
            helper.copyVarValuesFromDeployedSolution(cacheAppId, cacheCpModelPath, mvvToCurrentConfigVarsMap);
        }

		// Notify EMS about new solution acceptance
		notifyEMS(cpModelPath);
		
        log.info("MetaSolver.Coordinator: updateSolutionIdsInCpModel(): Solution Ids have been updated in CP model: deployed-solution-id={}, candidate-solution-id={}",
                newPos.getLeft(), newPos.getRight());
        return newPos;
    }

    // --------------------------------------------------------------------------

    public void requestStartProcessForScaling() throws ConcurrentAccessException {
        // Use previously cached 'application id' and 'CP model'
        String appId = this.cacheAppId;
        String cpModelPath = this.cacheCpModelPath;
        log.info("MetaSolver.Coordinator: requestStartProcessForScaling(): Cached appId={}, Cached cp-model={}", appId, cpModelPath);

        // Set metric values in CP model
        log.debug("MetaSolver.Coordinator: requestStartProcessForScaling(): Updating metric values in CP model: {}", cpModelPath);
        setMetricValuesInCpModel(appId, cpModelPath);
        log.debug("MetaSolver.Coordinator: requestStartProcessForScaling(): Metric values updated in CP model: {}", cpModelPath);

        // Send request to start Deployment Process (reusing existing CP model)
        DeploymentProcessRequest notification = prepareDeploymentProcessRequest(appId, cpModelPath);
        log.debug("MetaSolver.Coordinator: requestStartProcessForScaling(): Sending deployment process request: {}", notification);
        sendNotification(notification);
        log.debug("MetaSolver.Coordinator: requestStartProcessForScaling(): Deployment process request sent: {}", notification);
    }

    private DeploymentProcessRequest prepareDeploymentProcessRequest(String appId, String cpModelPath) {
        DeploymentProcessRequest notification = new DeploymentProcessRequestImpl();
        notification.setApplicationId(appId);
        notification.setUseExistingCP(true);        // For scaling we need to re-use the existing CP model
        notification.setCdoResourcePath(cpModelPath);

        notification.setUsername(melodicSecurityProperties.getUser().getUsername());
        notification.setPassword(melodicSecurityProperties.getUser().getPassword());

        String uuid = UUID.randomUUID().toString().toLowerCase();
        notification.setWatermark(prepareWatermark(uuid));
        return notification;
    }

    private void sendNotification(DeploymentProcessRequest notification) {
        String esbUrl = metaSolverProperties.getEsb().getUrl();
        if (esbUrl.endsWith("/")) {
            esbUrl = esbUrl.substring(0, esbUrl.length() - 1);
        }
        log.debug("MetaSolver.Coordinator: sendNotification(DeploymentProcessRequest): Request to ESB: url={}, notification={}", esbUrl, notification.toString());
        ResponseEntity<String> response = sendDeploymentProcessRequestToUrl(esbUrl, notification);
        log.debug("MetaSolver.Coordinator: sendNotification(DeploymentProcessRequest): Response: status={}, body={}",
                response.getStatusCode(), response.getBody());
    }

    private ResponseEntity<String> sendDeploymentProcessRequestToUrl(String url, DeploymentProcessRequest notification) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<DeploymentProcessRequest> entity = new HttpEntity<>(notification, headers);
        return restTemplate.postForEntity(url, entity, String.class);
    }

    public Watermark prepareWatermark(String uuid) {
        Watermark watermark = new WatermarkImpl();
        watermark.setUser("metaSolver");
        watermark.setSystem("metaSolver");
        watermark.setDate(new Date());
        watermark.setUuid(uuid);
        return watermark;
    }

    // --------------------------------------------------------------------------
    public void updateSubscriptions(Set<Map> subscriptions) {
        log.info("MetaSolver.Coordinator: updateSubscriptions(): subscriptions={}", subscriptions);

        // get metric value registry
        MetricValueMonitorBean metricValueMonitorBean = (MetricValueMonitorBean) applicationContext.getBean(MetricValueMonitorBean.class);

        // unsubscribe from previous topics
        log.info("MetaSolver.Coordinator: updateSubscriptions(): Unsubscribing from old topics...");
        metricValueMonitorBean.unsubscribe();
        log.info("MetaSolver.Coordinator: updateSubscriptions(): Unsubscribing from old topics... ok");

        // subscribe to new topics
        log.info("MetaSolver.Coordinator: updateSubscriptions(): Subscribing to current topics...");
        for (Map p : subscriptions) {
            String url = (String) p.get("url");
            String topicName = (String) p.get("topic");
            String clientId = (String) p.get("client-id");
            TopicType type = TopicType.valueOf((String) p.get("type"));
            log.info("MetaSolver.Coordinator: updateSubscriptions(): Subscribing to topic: url={}, topic={}, client-id={}, type={}", url, topicName, clientId, type);
            metricValueMonitorBean.subscribe(url, topicName, clientId, type);
            log.info("MetaSolver.Coordinator: updateSubscriptions(): Subscribed to topic: {}", topicName);
        }
        log.info("MetaSolver.Coordinator: updateSubscriptions(): Subscribing to current topics... ok");
    }

    public void setMvvMap(Map<String,String> mvvMap) {
        log.info("MetaSolver.Coordinator: setMvvMap(): map={}", mvvMap);
        mvvToCurrentConfigVarsMap = mvvMap;
        log.info("MetaSolver.Coordinator: setMvvMap(): 'mvvToCurrentConfigVarsMap' updated");
    }

    private void notifyEMS(String cpModelPath) {
        String emsUrl = metaSolverProperties.getEmsUrl();
        if (StringUtils.isEmpty(emsUrl)) {
            log.debug("MetaSolver.Coordinator: notifyEMS(): EMS-URL has not been set");
            return;
        }

        if (emsUrl.endsWith("/")) {
            emsUrl = emsUrl.substring(0, emsUrl.length() - 1);
        }
        log.debug("MetaSolver.Coordinator: notifyEMS(): Request to EMS: url={}, cp-model-path={}", emsUrl, cpModelPath);

        Map<String, String> notification = new HashMap<>();
        notification.put("cp-model-id", cpModelPath);
		
        final ResponseEntity<String> response =
                postToUrl(emsUrl, HashMap.class, notification, false);

        log.debug("MetaSolver.Coordinator: notifyEMS(): Response from EMS: status={}, response={}, body={}",
                response.getStatusCode(), response, response.getBody());
    }

    public ResponseEntity<String> postToUrl(String url, Class notifType, Object notification, boolean createNewToken) {
        String jwtToken = createNewToken
                ? createToken()
                : controller.getAuthenticationToken();
        log.debug("MetaSolver.postToUrl(): JWT token={}, created={}", jwtToken, createNewToken);

        final ResponseEntity<String> response;
        if (StringUtils.isNotEmpty(jwtToken)) {
            HttpEntity<HashMap> entity = createHttpEntity(notifType, notification, jwtToken);
            response = restTemplate.postForEntity(url, entity, String.class);
        } else {
            response = restTemplate.postForEntity(url, notification, String.class);
        }
        return response;
    }

    public <T> HttpEntity<T> createHttpEntity(Class<T> notifType, Object notification, String jwtToken) {
        HttpHeaders headers = createHttpHeaders(jwtToken);
        return new HttpEntity<T>((T)notification, headers);
    }

    private HttpHeaders createHttpHeaders(String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        if (StringUtils.isNotBlank(jwtToken)) {
            headers.set(HttpHeaders.AUTHORIZATION, jwtToken);
        }
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

    public String createToken() {
        String username = melodicSecurityProperties.getUser().getUsername();
        log.debug("MetaSolver.createToken():  username={}, jwt-service={}", username, jwtService);
        String token = SecurityConstants.TOKEN_PREFIX + jwtService.create(username);
        log.debug("MetaSolver.createToken():  username={}, token={}", username, token);
        return token;
    }
}
