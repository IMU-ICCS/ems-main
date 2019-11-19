/*
 * Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.upperware.simulationHandler;


import eu.melodic.models.interfaces.simulationHandler.SimulatedMetricValuesRequest;
import eu.melodic.models.interfaces.simulationHandler.SimulatedMetricValuesRequestImpl;
import eu.melodic.models.interfaces.simulationHandler.SimulatedMetricValuesResponseImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.cdo.util.ConcurrentAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.BadRequestException;
import java.util.*;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class SimulationHandlerController {

    @Autowired
    private Coordinator coordinator;

    private String jwtToken = null;

    public String getAuthenticationToken() {
        return jwtToken;
    }

    public void setAuthenticationToken(String s) { if (StringUtils.isNotEmpty(s)) jwtToken = s.trim(); }

    //received from EMS (not passing through mule)
    @RequestMapping(value = "/updateMetricSendersConfiguration", method = POST)
    public String updateMetricSenderConfiguration(@RequestBody String configStr,
                                                  @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String jwtToken)
            throws ConcurrentAccessException
    {
        setAuthenticationToken(jwtToken);

        log.info("updateConfiguration: json={}", configStr);

        // Unserialize configuration from JSON
        com.google.gson.Gson gson = new com.google.gson.Gson();
        Map<String,Object> configuration = gson.fromJson(configStr, Map.class);
        log.info("updateConfiguration: new configuration={}", configuration);

        // Update MVV-map
        Map<String,String> mvvMap = (Map<String,String>) configuration.get("mvv");
        coordinator.setMvvMap(mvvMap);

        // Update subscriptions for metricSenders (metric names)
        Set<Map> subscriptions = new HashSet<>( (Collection<Map>)configuration.get("subscriptions") );
        coordinator.updateSubscriptions(subscriptions);

        return "OK";
    }

    @RequestMapping(value = "/getMetricsNames", method = {GET, POST})
    public String getMetricsNames(@RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String jwtToken)
    {
        log.info("SimulationHandler.getMetricsNames(): Received request");
        log.trace("SimulationHandler.getMetricsNames(): JWT token: {}", jwtToken);

        //String currentCamelModelId = coordinator.getCurrentCamelModelId();

        //log.info("SimulationHandler.getMetricsNames(): Current CAMEL model: {}", currentCamelModelId);

        return "OK";
    }

    @RequestMapping(value = "/provideSimulatedMetricValues", method = POST)
    public SimulatedMetricValuesResponseImpl provideSimulatedMetricValues(@RequestBody SimulatedMetricValuesRequestImpl request,
                                                         @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String jwtToken)
            throws ConcurrentAccessException
    {
        log.info("received sth");
        setAuthenticationToken(jwtToken);
        String applicationId = request.getApplicationId();
        String requestUuid = request.getWatermark().getUuid();
        log.info("Received request: " + applicationId + " " + requestUuid);

        // Evaluate new solution
        log.info("Sending simulated metrics to MetaSolver: ");
        coordinator.sendMetricsToMetaSolver(request.getMetricValues());
        log.info("Sending Simulated metrics finished: ");

        SimulatedMetricValuesResponseImpl response = new SimulatedMetricValuesResponseImpl();
        response.setApplicationId(applicationId);
        response.setWatermark(coordinator.prepareWatermark(requestUuid));

        return response;
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
