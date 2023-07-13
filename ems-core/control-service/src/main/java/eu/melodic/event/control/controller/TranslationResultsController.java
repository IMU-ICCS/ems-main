/*
 * Copyright (C) 2017-2023 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.control.controller;

import eu.melodic.event.models.commons.Watermark;
import eu.melodic.event.models.interfaces.*;
import eu.melodic.event.translate.model.MetricContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TranslationResultsController {
    private final ControlServiceCoordinator coordinator;
    private final TranslationResultsCoordinator translationResultsCoordinator;

    // ------------------------------------------------------------------------------------------------------------
    // Monitor methods
    // ------------------------------------------------------------------------------------------------------------

    @RequestMapping(value = "/monitors", method = POST)
    public HttpEntity<MonitorsDataResponse> getMonitors(@RequestBody MonitorsDataRequestImpl request,
                                                        @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String jwtToken)
    {
        log.info("ControlServiceController.getMonitors(): Received request: {}", request);
        log.trace("ControlServiceController.getMonitors(): JWT token: {}", jwtToken);

        // Get information from request
        String applicationId = request.getApplicationId();
        Watermark watermark = request.getWatermark();
        String requestUuid = watermark.getUuid();
        log.info("ControlServiceController.getMonitors(): Request info: app-id={}, watermark={}, request-id={}", applicationId, watermark, requestUuid);

        // Update watermark
        watermark.setUser("EMS");
        watermark.setSystem("EMS");
        watermark.setDate(new java.util.Date());

        // Retrieve sensor information
        List<eu.melodic.event.translate.model.Monitor> monitors = translationResultsCoordinator.getMonitorsOfAppModel(applicationId);
        List<Monitor> responseMonitors = translationResultsCoordinator.convertMonitorsForMessage(monitors);

        // Prepare response
        MonitorsDataResponse response = new MonitorsDataResponseImpl();
        response.setMonitors(responseMonitors);
        response.setWatermark(watermark);
        HttpEntity<MonitorsDataResponse> entity = coordinator.createHttpEntity(MonitorsDataResponse.class, response, jwtToken);
        log.info("ControlServiceController.getMonitors(): Response: {}", response);

        //return response;
        return entity;
    }

    // ------------------------------------------------------------------------------------------------------------
    // Translation results methods
    // ------------------------------------------------------------------------------------------------------------

    @RequestMapping(value = "/translator/currentCamelModel", method = {GET,POST})
    public String getCurrentAppModel(@RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String jwtToken)
    {
        log.info("TranslationResultsController.getCurrentAppModel(): Received request");
        log.trace("TranslationResultsController.getCurrentAppModel(): JWT token: {}", jwtToken);

        String currentAppModelId = coordinator.getCurrentAppModelId();
        log.info("TranslationResultsController.getCurrentAppModel(): Current App model: {}", currentAppModelId);

        return currentAppModelId;
    }

    @RequestMapping(value = "/translator/currentCpModel", method = {GET,POST})
    public String getCurrentCpModel(@RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String jwtToken)
    {
        log.info("TranslationResultsController.getCurrentCpModel(): Received request");
        log.trace("TranslationResultsController.getCurrentCpModel(): JWT token: {}", jwtToken);

        String currentCpModelId = coordinator.getCurrentCpModelId();
        log.info("TranslationResultsController.getCurrentCpModel(): Current CP model: {}", currentCpModelId);

        return currentCpModelId;
    }

    @RequestMapping(value = { "/translator/constraintThresholds/{appId}", "/translator/constraintThresholds" }, method = {GET,POST},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection getConstraintThresholds(@PathVariable("appId") Optional<String> optAppId,
                                              @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String jwtToken)
    {
        String applicationId = optAppId.orElse(null);
        log.info("TranslationResultsController.getConstraintThresholds(): Received request: app-id={}", applicationId);
        log.trace("TranslationResultsController.getConstraintThresholds(): JWT token: {}", jwtToken);

        if (StringUtils.isBlank(applicationId)) {
            applicationId = coordinator.getCurrentAppModelId();
            log.info("TranslationResultsController.getConstraintThresholds(): Using current application: curr-app-id={}", applicationId);
            if (applicationId==null) applicationId = "";
        }

        // Retrieve sensor information
        String appPath = coordinator._normalizeModelId(applicationId);
        Set constraints = translationResultsCoordinator.getMetricConstraints(appPath);
        log.info("TranslationResultsController.getConstraintThresholds(): Constraints for application: {}: {}", applicationId, constraints);

        return constraints;
    }

    @GetMapping(value = {"/translator/getTopLevelNodesMetricContexts/{appId}", "/translator/getTopLevelNodesMetricContexts"},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<?> getTopLevelNodesMetricContexts(@PathVariable("appId") Optional<String> optAppId,
                                                        @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String jwtToken)
    {
        String applicationId = optAppId.orElse(null);
        log.info("TranslationResultsController.getTopLevelNodesMetricContexts(): Received request: app-id={}", applicationId);
        log.trace("TranslationResultsController.getTopLevelNodesMetricContexts(): JWT token: {}", jwtToken);

        if (StringUtils.isBlank(applicationId)) {
            applicationId = coordinator.getCurrentAppModelId();
            log.info("TranslationResultsController.getTopLevelNodesMetricContexts(): Using current application: curr-app-id={}", applicationId);
            if (applicationId==null) return Collections.emptyList();
        }

        // Retrieve context metrics of the top-level DAG nodes
        String appModelId = coordinator._normalizeModelId(applicationId);
        log.debug("TranslationResultsController.getTopLevelNodesMetricContexts(): appModelId: {}", appModelId);
        Set<MetricContext> results = translationResultsCoordinator.getMetricContextsForPrediction(appModelId);
        log.info("TranslationResultsController.getTopLevelNodesMetricContexts(): Result: {}", results);

        return results;
    }
}
