/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.upperware.metasolver;

import eu.melodic.models.commons.Watermark;
import eu.melodic.models.commons.WatermarkImpl;
import eu.melodic.models.interfaces.metaSolver.KeyValuePair;
import eu.melodic.models.interfaces.metaSolver.SolutionEvaluationResponse;
import eu.melodic.models.services.metaSolver.DeploymentProcessRequest;
import eu.melodic.models.services.metaSolver.DeploymentProcessRequestImpl;
import eu.melodic.upperware.metasolver.metricvalue.MetricValueMonitorBean;
import eu.melodic.upperware.metasolver.metricvalue.TopicType;
import eu.melodic.upperware.metasolver.properties.MetaSolverProperties;
import eu.melodic.upperware.metasolver.util.CpModelHelper;
import eu.melodic.upperware.metasolver.util.PredictionHelper;
import eu.paasage.upperware.security.authapi.SecurityConstants;
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import eu.paasage.upperware.security.authapi.token.JWTService;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.cdo.util.ConcurrentAccessException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.*;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Slf4j
@Service
public class Coordinator implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    private MetaSolverController controller;
    @Getter
    private MetaSolverProperties metaSolverProperties;
    private JWTService jwtService;
    private MelodicSecurityProperties melodicSecurityProperties;
    private double uvThresholdFactor;
    private boolean removeRedundantCandidates;
    private RestTemplate restTemplate;

    private String cacheAppId;
    private String cacheCpModelPath;
    private Map<String,String> mvvToCurrentConfigVarsMap;

    private Timer updateTimer;
    private AtomicBoolean updateLocked = new AtomicBoolean(false);
    private String updateAppId;
    private String updatePath;

    private long previousReconfigurationTimestamp = 0;
    private Semaphore reconfigurationRunning = new Semaphore(1);
    private ScheduledFuture<?> reconfigurationRunningTimeoutFuture;
    @Autowired
    private TaskScheduler taskScheduler;

    @Getter
    private PredictionHelper predictionHelper;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        this.controller = applicationContext.getBean(MetaSolverController.class);
        this.jwtService = applicationContext.getBean(JWTService.class);
        this.metaSolverProperties = applicationContext.getBean(MetaSolverProperties.class);
        this.melodicSecurityProperties = applicationContext.getBean(MelodicSecurityProperties.class);
        this.uvThresholdFactor = metaSolverProperties.getUtilityThresholdFactor();
        this.removeRedundantCandidates = metaSolverProperties.isRemoveRedundantCandidates();
        this.restTemplate = new RestTemplate();
        this.predictionHelper = applicationContext.getBean(PredictionHelper.class);
        log.debug("MetaSolver.Coordinator: setApplicationContext(): configuration={}", metaSolverProperties);
    }

    /**
     * How can we select the most appropriate solver??
     * For R3.0 it will be a list of pre-configured solvers
     * @return The selected solver names (List of strings)
     */
    public List<String> selectSolvers(String applicationId, String cpModelPath) throws ConcurrentAccessException {
        log.info("MetaSolver.Coordinator: selectSolvers(): app-id={}, model-path={}", applicationId, cpModelPath);
        this.cacheAppId = applicationId;
        this.cacheCpModelPath = cpModelPath;

        List<String> defaultSolvers = metaSolverProperties.getDefaultSolvers()
                .stream().map(Enum::name)
                .collect(Collectors.toList());
        log.info("MetaSolver.Coordinator: selectSolvers(): Solvers selected: {}", defaultSolvers);
        return defaultSolvers;
    }

    /**
     * Update CP model with current metric variable values
     */
    public boolean setMetricValuesInCpModel(String applicationId, String cpModelPath) throws ConcurrentAccessException, NumberFormatException {
        log.info("MetaSolver.Coordinator: setMetricValuesInCpModel(): appId={}, model={}", applicationId, cpModelPath);

        // get metric values from metric value registry
        MetricValueMonitorBean monitor = (MetricValueMonitorBean) applicationContext.getBean(MetricValueMonitorBean.class);
        Map<String, String> metricValues = monitor.getMetricValuesRegistry().getMetricValuesAsMap();
        log.debug("MetaSolver.Coordinator: setMetricValuesInCpModel(): Metric values map: {}", metricValues);

        return setMetricValuesInCpModel(applicationId, cpModelPath, metricValues);
    }

    public boolean setMetricValuesInCpModel(String applicationId, String cpModelPath, @NonNull Map<String, String> metricValues) throws ConcurrentAccessException, NumberFormatException {
        log.info("MetaSolver.Coordinator: setMetricValuesInCpModel(): appId={}, model={}, metricValues={}", applicationId, cpModelPath, metricValues);

        // Send MetaSolver debug event
        if (metaSolverProperties.getDebugEvents().isEnabled()) {
            try {
                log.debug("setMetricValuesInCpModel: Sending DEBUG event: metricValues={}", metricValues);
                applicationContext.getBean(MetricValueMonitorBean.class).sendDebugEvent(metaSolverProperties.getDebugEvents().getTopicName(), metricValues);
                log.debug("setMetricValuesInCpModel: DEBUG event sent: metricValues={}", metricValues);
            } catch (Exception e) {
                log.error("setMetricValuesInCpModel: EXCEPTION while sending debug event: ", e);
            }
        }

        // Update CP model with current metric variable values
        boolean succeeded = true;
        if (metaSolverProperties.isCpModelUpdateEnabled()) {
            CpModelHelper helper = (CpModelHelper) applicationContext.getBean(CpModelHelper.class);
            succeeded = helper.updateCpModelWithMetricValues(applicationId, cpModelPath, metricValues);
            log.info("MetaSolver.Coordinator: setMetricValuesInCpModel(): CP model update with current MVV's finished");
        } else
            log.warn("MetaSolver.Coordinator: setMetricValuesInCpModel: CP model update is DISABLED");

        return succeeded;
    }

    /**
     * Compare new and (currently) deployed solutions using their utility values.
     * - If no deployed solution exists (first deployment) then 'accept' new solution
     * - if a deployed solution exists then new solution's utility value must be better
     * than deployed solution's utility value, at least 'uvThresholdFactor' times
     */
    public SolutionEvaluationResponse.EvaluationResultType evaluateSolution(String applicationId, String cpModelPath) throws ConcurrentAccessException {
        SolutionEvaluationResponse.EvaluationResultType result = _evaluateSolution(applicationId, cpModelPath);
        if (result==SolutionEvaluationResponse.EvaluationResultType.NEGATIVE) {
            // Enable reconfiguration running
            enableReconfigurationRunning();
        }
        return result;
    }

    protected SolutionEvaluationResponse.EvaluationResultType _evaluateSolution(String applicationId, String cpModelPath) throws ConcurrentAccessException {
        log.info("MetaSolver.Coordinator: evaluateSolution(): appId={}, model={}", applicationId, cpModelPath);
        this.cacheAppId = applicationId;
        this.cacheCpModelPath = cpModelPath;

        // Get current candidate and deployed solution indexes, and all solutions utilities
        CpModelHelper helper = applicationContext.getBean(CpModelHelper.class);
        CpModelHelper.SolutionData solutionData = helper.getSolutionIndexesAndUtilitiesFromCpModel(applicationId, cpModelPath);
        log.debug("MetaSolver.Coordinator: evaluateSolution(): Solutions data in CP model: appId={}, model={}, data={}", applicationId, cpModelPath, solutionData);

        // Check if no new candidate solutions are available (i.e. with index > current (old) candidate index)
        int currentCandidatesIndex = solutionData.getCandidateIndex();
        int currentDeployedIndex = solutionData.getDeployedIndex();
        currentCandidatesIndex = currentCandidatesIndex>=0 ? currentCandidatesIndex : -1;
        currentDeployedIndex = currentDeployedIndex>=0 ? currentDeployedIndex : -1;
        List<Double> utilities = solutionData.getUtilities();
        if (utilities.size() == 0) {
            log.warn("MetaSolver.Coordinator: evaluateSolution(): RETURN ERROR: No solutions found in CP model: appId={}, model={}", applicationId, cpModelPath);
            return SolutionEvaluationResponse.EvaluationResultType.ERROR;
        }
        if (currentCandidatesIndex+1 >= utilities.size()) {
            log.warn("MetaSolver.Coordinator: evaluateSolution(): RETURN ERROR: No new candidate solutions found in CP model: appId={}, model={}", applicationId, cpModelPath);
            return SolutionEvaluationResponse.EvaluationResultType.ERROR;
        }
        log.debug("MetaSolver.Coordinator: evaluateSolution(): Solutions and solution indexes found in CP model: appId={}, model={}, num-of-solutions={}, current-candidates-index={}, deployed-index={}", applicationId, cpModelPath, utilities.size(), currentCandidatesIndex, currentDeployedIndex);

        // Find new candidate solution with the highest utility (search solutions with index > current candidates index)
        int maxUVIndex = currentCandidatesIndex+1;
        double maxUV = utilities.get(maxUVIndex);
        log.trace("MetaSolver.Coordinator: evaluateSolution(): First new candidate solution in CP model: appId={}, model={}, index={}, utility={}", applicationId, cpModelPath, maxUVIndex, maxUV);
        for (int i=solutionData.getCandidateIndex()+2; i<utilities.size(); i++) {
            log.trace("MetaSolver.Coordinator: evaluateSolution(): Checking new candidate solution in CP model: appId={}, model={}, index={}, utility={}", applicationId, cpModelPath, i, utilities.get(i));
            if (utilities.get(i) > maxUV) {
                maxUV = utilities.get(i);
                maxUVIndex = i;
            }
        }
        log.debug("MetaSolver.Coordinator: evaluateSolution(): Selected candidate solution with the highest utility: appId={}, model={}, index={}, utility={}", applicationId, cpModelPath, maxUVIndex, maxUV);
        int selectedIndex = maxUVIndex;
        double selectedUV = maxUV;

        // Check if the selected candidate solution (with the highest utility) is at least X% better than the currently deployed solution
        if (currentDeployedIndex>=0) {
            double deployedUV = utilities.get(currentDeployedIndex);
            if (selectedUV <= uvThresholdFactor * deployedUV) {
                log.info("MetaSolver.Coordinator: evaluateSolution(): Selected candidate solution has NOT better utility than the deployed solution: appId={}, model={}, candidate-index/utility={}/{}, deployed-index/utility={}/{}, uv-threshold-factor={}",
                        applicationId, cpModelPath, selectedIndex, selectedUV, currentDeployedIndex, deployedUV, uvThresholdFactor);
                selectedUV = -1;
                selectedIndex = -1;
            } else {
                log.info("MetaSolver.Coordinator: evaluateSolution(): Selected candidate solution has better utility than the deployed solution: appId={}, model={}, candidate-index/utility={}/{}, deployed-index/utility={}/{}, uv-threshold-factor={}",
                        applicationId, cpModelPath, selectedIndex, selectedUV, currentDeployedIndex, deployedUV, uvThresholdFactor);
            }
        } else {
            log.info("MetaSolver.Coordinator: evaluateSolution(): There is no deployed solution. Selected candidate solution will be accepted: appId={}, model={}, index={}, utility={}",
                    applicationId, cpModelPath, selectedIndex, selectedUV);
        }

        // Move selected candidate solution (if any) to the end of the list
        if (selectedIndex>=0) {
            helper.moveSolutionToPositionInCpModel(applicationId, cpModelPath, selectedIndex, utilities.size() - 1);
            selectedIndex = utilities.size() - 1;
        }
        // Remove not selected candidate solutions
        if (removeRedundantCandidates) {
            int limit = selectedIndex>=0 ? selectedIndex : utilities.size();
            helper.removeSolutionRangeFromCpModel(applicationId, cpModelPath, currentCandidatesIndex + 1, limit);
            if (selectedIndex>=0) {
                solutionData = helper.getSolutionIndexesAndUtilitiesFromCpModel(applicationId, cpModelPath);
                selectedIndex = solutionData.getUtilities().size() - 1;
            }
        }

        // Update candidate solution index to the last solution (i.e. to the one with the highest utility)
        helper.setSolutionIndexesInCpModel(applicationId, cpModelPath, solutionData.getUtilities().size() - 1, currentDeployedIndex);

        // Return result
        if (selectedIndex>=0) {
            log.info("MetaSolver.Coordinator: evaluateSolution(): RETURN POSITIVE: Selected candidate solution is ACCEPTED: appId={}, model={}, new-solution-index={}", applicationId, cpModelPath, selectedIndex);
            return SolutionEvaluationResponse.EvaluationResultType.POSITIVE;
        } else {
            log.info("MetaSolver.Coordinator: evaluateSolution(): RETURN NEGATIVE: Selected candidate solution is NOT ACCEPTED: appId={}, model={}", applicationId, cpModelPath);
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

        // Enable reconfiguration running
        /*XXX: TODO: Probably not the right place to re-enable reconf.
                BETTER ALTERNATIVE: Adapter should notify us re-enabling reconf.*/
        enableReconfigurationRunning();

        // Notify EMS about new solution acceptance
		notifyEMS(cpModelPath);
		
        log.info("MetaSolver.Coordinator: updateSolutionIdsInCpModel(): Solution Ids have been updated in CP model: deployed-solution-id={}, candidate-solution-id={}",
                newPos.getLeft(), newPos.getRight());
        return newPos;
    }

    // --------------------------------------------------------------------------

    public boolean requestReconfigurationStart(boolean isSimulation) throws ConcurrentAccessException {
        return requestReconfigurationStart(isSimulation, null);
    }

    public synchronized boolean requestReconfigurationStart(boolean isSimulation, Map<String, String> metricValues) throws ConcurrentAccessException {
        // Check if a (previous) reconfiguration process is still running
        if (metaSolverProperties.isPreventConcurrentReconfigurations() && ! reconfigurationRunning.tryAcquire()) {
            log.warn("MetaSolver.Coordinator: requestReconfigurationStart(): A previous Reconfiguration instance is still running. Ignoring requests");
            return false;
        }

        // Check if we are in the reconfiguration blocking period
        if (System.currentTimeMillis() < previousReconfigurationTimestamp + metaSolverProperties.getReconfigurationBlockingPeriod()) {
            log.warn("MetaSolver.Coordinator: requestReconfigurationStart(): Cannot request a new reconfiguration during reconfiguration blocking period");
            enableReconfigurationRunning();
            return false;
        }
        previousReconfigurationTimestamp = System.currentTimeMillis();

        // Schedule reconfiguration running timeout
        if (metaSolverProperties.isPreventConcurrentReconfigurations() && getMetaSolverProperties().getPreventConcurrentReconfigurationsTimeout()>0) {
            reconfigurationRunningTimeoutFuture = taskScheduler.schedule(() -> enableReconfigurationRunning(false),
                    Date.from(Instant.now().plusMillis(getMetaSolverProperties().getPreventConcurrentReconfigurationsTimeout())));
        }

        // Use previously cached 'application id' and 'CP model'
        String appId = this.cacheAppId;
        String cpModelPath = this.cacheCpModelPath;
        log.info("MetaSolver.Coordinator: requestReconfigurationStart(): Cached appId={}, Cached cp-model={}", appId, cpModelPath);

        // Set metric values in CP model
        log.debug("MetaSolver.Coordinator: requestReconfigurationStart(): Updating metric values in CP model: {}", cpModelPath);
        boolean result = (metricValues == null)
                ? setMetricValuesInCpModel(appId, cpModelPath)
                : setMetricValuesInCpModel(appId, cpModelPath, metricValues);
        if (!result) {
            log.debug("MetaSolver.Coordinator: requestReconfigurationStart():" +
                    " Metric values update failed in CP model: {}, aborting scaling process", cpModelPath);
            enableReconfigurationRunning();
            return false;
        }
        log.debug("MetaSolver.Coordinator: requestReconfigurationStart(): Metric values updated in CP model: {}", cpModelPath);

        // Send request to start Deployment Process (reusing existing CP model)
        DeploymentProcessRequest notification = prepareDeploymentProcessRequest(appId, cpModelPath, isSimulation);
        log.debug("MetaSolver.Coordinator: requestReconfigurationStart(): Sending deployment process request: {}", notification);
        sendNotification(notification);
        log.debug("MetaSolver.Coordinator: requestReconfigurationStart(): Deployment process request sent: {}", notification);
        return true;
    }

    protected void enableReconfigurationRunning() {
        // Enable reconfiguration running
        if (metaSolverProperties.isPreventConcurrentReconfigurations()) {
            if (reconfigurationRunningTimeoutFuture!=null) {
                reconfigurationRunningTimeoutFuture.cancel(false);
                reconfigurationRunningTimeoutFuture = null;
            }
            if (reconfigurationRunning.availablePermits()==0)
                reconfigurationRunning.release();
        }
    }

    public void enableReconfigurationRunning(boolean alsoClearBlockingPeriod) {
        enableReconfigurationRunning();
        if (alsoClearBlockingPeriod)
            previousReconfigurationTimestamp = 0;
    }

    private DeploymentProcessRequest prepareDeploymentProcessRequest(String appId, String cpModelPath, boolean isSimulation) {
        DeploymentProcessRequest notification = new DeploymentProcessRequestImpl();
        notification.setApplicationId(appId);
        notification.setUseExistingCP(true);        // For scaling we need to re-use the existing CP model
        notification.setCdoResourcePath(cpModelPath);
        notification.setIsSimulation(Boolean.toString(isSimulation));

        notification.setUsername(melodicSecurityProperties.getUser().getUsername());
        notification.setPassword(melodicSecurityProperties.getUser().getPassword());

        String uuid = UUID.randomUUID().toString().toLowerCase();
        notification.setWatermark(prepareWatermark(uuid));
        return notification;
    }

    private void sendNotification(DeploymentProcessRequest notification) {
        if (!metaSolverProperties.getEsb().isEnabled()) {
            log.warn("MetaSolver.Coordinator: sendNotification(DeploymentProcessRequest): ESB notification is DISABLED");
            return;
        }

        String esbUrl = metaSolverProperties.getEsb().getUrl();
        if (esbUrl.endsWith("/")) {
            esbUrl = esbUrl.substring(0, esbUrl.length() - 1);
        }
        log.debug("MetaSolver.Coordinator: sendNotification(DeploymentProcessRequest): Request to ESB: url={}, notification={}", esbUrl, notification.toString());
        ResponseEntity<String> response = sendDeploymentProcessRequestToUrl(esbUrl, notification);
        log.info("MetaSolver.Coordinator: sendNotification(DeploymentProcessRequest): Response: status={}, body={}",
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

        // subscribe to common topics
        log.info("MetaSolver.Coordinator: updateSubscriptions(): Subscribing to Common topics...");
        metricValueMonitorBean.subscribeToCommonTopics();
        log.info("MetaSolver.Coordinator: updateSubscriptions(): Subscribing to Common topics... ok");

        // subscribe to new topics
        log.info("MetaSolver.Coordinator: updateSubscriptions(): Subscribing to current topics...");
        for (Map p : subscriptions) {
            String url = (String) p.get("url");
            String username = (String) p.get("username");
            String password = (String) p.get("password");
            String certificate = (String) p.get("certificate");
            String topicName = (String) p.get("topic");
            String clientId = (String) p.get("client-id");
            TopicType type = TopicType.valueOf((String) p.get("type"));
            log.info("MetaSolver.Coordinator: updateSubscriptions(): Subscribing to topic: url={}, username={}, topic={}, client-id={}, type={}", url, username, topicName, clientId, type);
            metricValueMonitorBean.subscribe(url, username, password, certificate, topicName, clientId, type);
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

    // --------------------------------------------------------------------------
    void simulateReconfiguration(List<KeyValuePair> metricValues, String applicationId) throws ConcurrentAccessException {
        if (!cacheAppId.equals(applicationId)) {
            log.warn("applications Ids don't match, aborting");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Wrong application Id: %s", applicationId));
        } else {
            MetricValueMonitorBean monitor = applicationContext.getBean(MetricValueMonitorBean.class);
            Set<String> metricNames = monitor.getMetricValuesRegistry().getMvvMetricNames();
            for (KeyValuePair nameValuePair : metricValues) {
                if (metricNames.contains(nameValuePair.getKey())) {
                    monitor.setMetricValueInRegistry(nameValuePair.getKey(), nameValuePair.getValue());
                } else {
                    log.warn("Received invalid metric name: {}", nameValuePair.getKey());
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            String.format("Received invalid metric name: %s", nameValuePair.getKey()));
                }
            }
            log.info("Simulated metrics set");

            log.info("Simulating Reconfiguration: Calling coordinator to start Scaling process...");
            if(!requestReconfigurationStart(true)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Received metric value in invalid format");
            }
        }
    }

    List<String> getMetricNames(String applicationId) {
        List<String> metricNames;
        if (!cacheAppId.equals(applicationId)) {
            log.warn("Applications Ids don't match");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Wrong application Id: %s", applicationId));
        } else {
            MetricValueMonitorBean monitor = applicationContext.getBean(MetricValueMonitorBean.class);
            metricNames = new ArrayList<>(monitor.getMetricValuesRegistry().getMvvMetricNames());
            if (metricNames.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No metrics defined or net yet received");
            }
        }
        return metricNames;
    }

    // --------------------------------------------------------------------------
    public void startUpdatingCpModel(@NonNull String applicationId, @NonNull String cdoModelsPath) {
        log.debug("startUpdatingCpModel: INPUT: app-id={}, cdo-path={}", applicationId, cdoModelsPath);
        if (updateTimer!=null) {
            log.error("CP Model Update Timer is already running with: app-id={}, cdo-path={}", updateAppId, updatePath);
            return;
        }

        final Coordinator coordinator = this;
        TimerTask task = new TimerTask() {
            public void run() {
                if (!updateLocked.compareAndSet(false, true)) {
                    log.warn("CP Model Update Timer: Previous iteration is still running: Updating CP Model: app-id={}, cdo-path={}", updateAppId, updatePath);
                    return;
                }
                log.debug("CP Model Update Timer: Updating CP Model: app-id={}, cdo-path={}", updateAppId, updatePath);
                try {
                    if (coordinator.setMetricValuesInCpModel(updateAppId, updatePath)) {
                        log.debug("CP Model Update Timer: CP Model updated: app-id={}, cdo-path={}", updateAppId, updatePath);
                        return;
                    }
                } catch (ConcurrentAccessException ignored) {
                } finally {
                    updateLocked.set(false);
                }
                log.warn("CP Model Update Timer: Failed to update CP Model: app-id={}, cdo-path={}", updateAppId, updatePath);
            }
        };
        updateTimer = new Timer("CpModelUpdateTimer");
        updateAppId = applicationId;
        updatePath = cdoModelsPath;
        long rate = metaSolverProperties.getCpModelUpdateInterval();
        updateTimer.scheduleAtFixedRate(task, rate, rate);
        log.debug("CP Model Update Timer started with: app-id={}, cdo-path={}", updateAppId, updatePath);
    }

    public void stopUpdatingCpModel() {
        log.debug("stopUpdatingCpModel:");
        if (updateTimer==null) return;
        updateTimer.cancel();
        updateTimer = null;
        log.debug("CP Model Update Timer stopped");
    }
}
