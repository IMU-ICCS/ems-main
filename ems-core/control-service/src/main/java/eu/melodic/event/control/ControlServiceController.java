/*
 * Copyright (C) 2017-2023 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.control;

import com.google.gson.reflect.TypeToken;
import eu.melodic.event.control.properties.ControlServiceProperties;
import eu.melodic.event.models.commons.Watermark;
import eu.melodic.event.models.interfaces.*;
import eu.melodic.event.translate.model.MetricContext;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ControlServiceController {

    private final ControlServiceProperties properties;
    private final ControlServiceCoordinator coordinator;

    @Getter
    private List<String> controllerEndpoints;
    @Getter
    private List<String> controllerEndpointsShort;

    // ------------------------------------------------------------------------------------------------------------
    // ESB and Upperware interfacing methods
    // ------------------------------------------------------------------------------------------------------------

    @RequestMapping(value = "/camelModel", method = POST)
    public String newAppModel(@RequestBody CamelModelRequestImpl request,
                              @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String jwtToken)
    {
        log.info("ControlServiceController.newAppModel(): Received request: {}", request);
        log.trace("ControlServiceController.newAppModel(): JWT token: {}", jwtToken);

        // Get information from request
        String applicationId = request.getApplicationId();
        String notificationUri = request.getNotificationURI();
        String requestUuid = request.getWatermark().getUuid();
        log.info("ControlServiceController.newAppModel(): Request info: app-id={}, notification-uri={}, request-id={}",
                applicationId, notificationUri, requestUuid);

        // Start translation and reconfiguration in a worker thread
        coordinator.processAppModel(applicationId, null, ControlServiceRequestInfo.create(notificationUri, requestUuid, jwtToken));
        log.debug("ControlServiceController.newAppModel()/camelModel: Model translation dispatched to a worker thread");

        return "OK";
    }

    @RequestMapping(value = "/camelModelJson", method = POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public String newAppModel(@RequestBody String requestStr,
                              @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String jwtToken)
    {
        log.info("ControlServiceController.newAppModel(): Received request: {}", requestStr);
        log.trace("ControlServiceController.newAppModel()/camelModelJson: JWT token: {}", jwtToken);

        // Use Gson to get model id's from request body (in JSON format)
        com.google.gson.JsonObject jobj = new com.google.gson.Gson().fromJson(requestStr, com.google.gson.JsonObject.class);
        String appModelId = Optional.ofNullable(jobj.get("camel-model-id")).map(je -> stripQuotes(je.toString())).orElse(null);
        String cpModelId = Optional.ofNullable(jobj.get("cp-model-id")).map(je -> stripQuotes(je.toString())).orElse(null);
        log.info("ControlServiceController.newAppModel(): App model id from request: {}", appModelId);
        log.info("ControlServiceController.newAppModel(): CP model id from request: {}", cpModelId);

        // Start translation and component reconfiguration in a worker thread
        coordinator.processAppModel(appModelId, cpModelId, ControlServiceRequestInfo.create(null, null, jwtToken));
        log.debug("ControlServiceController.newAppModel(): Model translation dispatched to a worker thread");

        return "OK";
    }

    // ------------------------------------------------------------------------------------------------------------

    @RequestMapping(value = "/cpModelJson", method = POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public String newCpModel(@RequestBody String requestStr,
                             @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String jwtToken)
    {
        log.info("ControlServiceController.newCpModel(): Received request: {}", requestStr);
        log.trace("ControlServiceController.newCpModel(): JWT token: {}", jwtToken);

        // Use Gson to get model id's from request body (in JSON format)
        com.google.gson.JsonObject jobj = new com.google.gson.Gson().fromJson(requestStr, com.google.gson.JsonObject.class);
        String cpModelId = Optional.ofNullable(jobj.get("cp-model-id")).map(je -> stripQuotes(je.toString())).orElse(null);
        log.info("ControlServiceController.newCpModel(): CP model id from request: {}", cpModelId);

        // Start CP model processing in a worker thread
        coordinator.processCpModel(cpModelId, ControlServiceRequestInfo.create(null, null, jwtToken));
        log.debug("ControlServiceController.newCpModel(): CP Model processing dispatched to a worker thread");

        return "OK";
    }

    @RequestMapping(value = "/cpConstants", method = POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public String setConstants(@RequestBody String requestStr,
                             @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String jwtToken)
    {
        log.info("ControlServiceController.setConstants(): Received request: {}", requestStr);
        log.trace("ControlServiceController.setConstants(): JWT token: {}", jwtToken);

        // Use Gson to get constants from request body (in JSON format)
        Type type = new TypeToken<Map<String,Double>>(){}.getType();
        Map<String, Double> constants = new com.google.gson.Gson().fromJson(requestStr, type);
        log.info("ControlServiceController.setConstants(): Constants from request: {}", constants);

        // Start CP model processing in a worker thread
        coordinator.setConstants(constants, ControlServiceRequestInfo.create(null, null, jwtToken));
        log.debug("ControlServiceController.setConstants(): Constants set");

        return "OK";
    }

    /*@RequestMapping(value = "/test/**", method = {GET, POST})
    public String test(HttpServletRequest request, @RequestBody(required = false) String body,
                             @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String jwtToken)
    {
        String path = request.getRequestURI().split("/test/", 2)[1];
        Map<String, String> headers = Collections.list(request.getHeaderNames()).stream()
                .collect(Collectors.toMap(h -> h, request::getHeader));
        log.warn("--------------  TEST endpoint: --------------------------------------------------------");
        log.warn("--------------  TEST endpoint: Verb/URL: {} {}", request.getMethod(), UriUtils.decode(path, StandardCharsets.UTF_8));
        log.warn("--------------  TEST endpoint:  headers: {}", headers);
        log.warn("--------------  TEST endpoint:     body: {}", body);
        log.warn("--------------  TEST endpoint:      JWT: {}", jwtToken);
        return "OK";
    }*/

    // ------------------------------------------------------------------------------------------------------------

    //XXX:TODO: MOVE TO A SERVICE (maybe in Translator?)
    private List<KeyValuePair> convertToKeyValuePairList(Map<String,String> map) {
        return map.entrySet().stream()
                .map(e -> {
                    KeyValuePair pair = new KeyValuePairImpl();
                    pair.setKey(e.getKey());
                    pair.setValue(e.getValue());
                    return pair;
                })
                .toList();
    }

    private Interval convertInterval(eu.melodic.event.translate.model.Interval interval) {
        Interval i = new IntervalImpl();
        i.setUnit(Interval.UnitType.valueOf( interval.getUnit().name() ));
        i.setPeriod(interval.getPeriod());
        return i;
    }

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

        // Retrieve sensor information
        List<eu.melodic.event.translate.model.Monitor> monitors = coordinator.getMonitorssOfAppModel(applicationId);

        // Update watermark
        watermark.setUser("EMS");
        watermark.setSystem("EMS");
        watermark.setDate(new java.util.Date());

        // Print debug info about sensors
        if (log.isDebugEnabled()) {
            log.warn("ControlServiceController.getMonitors(): Printing monitors for Request: {}", requestUuid);
            monitors.forEach(m -> {
                log.warn("ControlServiceController.getMonitors():     Monitor: metric/topic={}, component={}, additional-properties={}",
                        m.getMetric(), m.getComponent(), m.getAdditionalProperties());
                eu.melodic.event.translate.model.Sensor s = m.getSensor();
                log.warn("ControlServiceController.getMonitors():       Sensor: {}", s);
                if (s.isPushSensor())
                    log.warn("ControlServiceController.getMonitors():       PushSensor: port={}", m.getSensor().getPushSensor().getPort());
                else
                    log.warn("ControlServiceController.getMonitors():       PullSensor: class-name={}, interval={}, configuration={}",
                            m.getSensor().getPullSensor().getClassName(), m.getSensor().getPullSensor().getInterval(), m.getSensor().getPullSensor().getConfiguration());
            });
        }

        // Prepare monitors list
        List<Monitor> responseMonitors = monitors.stream().map(m -> {
            // Sensor
            Sensor sensor;
            if (m.getSensor().isPullSensor()) {
                PullSensor pullSensor = new PullSensorImpl();
                sensor = new Sensor(pullSensor);
                pullSensor.setClassName(m.getSensor().getPullSensor().getClassName());
                pullSensor.setConfiguration( convertToKeyValuePairList(m.getSensor().getPullSensor().getConfiguration()) );
                pullSensor.setInterval( convertInterval(m.getSensor().getPullSensor().getInterval()) );
            } else if (m.getSensor().isPushSensor()) {
                PushSensor pushSensor = new PushSensorImpl();
                sensor = new Sensor(pushSensor);
                pushSensor.setPort(m.getSensor().getPushSensor().getPort());
            } else {
                log.error("ControlServiceController.getMonitors():       ERROR: Sensor is neither PullSensor or PushSensor: {}", m.getSensor());
                throw new IllegalArgumentException("ERROR: Sensor is neither PullSensor or PushSensor: "+m.getSensor());
            }

            // Sinks
            List<Sink> sinks = m.getSinks()==null
                    ? null
                    : m.getSinks().stream().map(s -> {
                        Sink sink = new SinkImpl();
                        sink.setType(Sink.TypeType.valueOf(s.getType().toString()));
                        sink.setConfiguration(convertToKeyValuePairList(s.getConfiguration()));
                        return sink;
                    }).toList();

            // Monitor
            Monitor mon = new MonitorImpl();
            mon.setComponent(m.getComponent());
            mon.setMetric(m.getMetric());
            mon.setSensor(sensor);
            mon.setSinks(sinks);
            return mon;
        }).toList();

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
        log.info("ControlServiceController.getCurrentAppModel(): Received request");
        log.trace("ControlServiceController.getCurrentAppModel(): JWT token: {}", jwtToken);

        String currentAppModelId = coordinator.getCurrentAppModelId();
        log.info("ControlServiceController.getCurrentAppModel(): Current App model: {}", currentAppModelId);

        return currentAppModelId;
    }

    @RequestMapping(value = "/translator/currentCpModel", method = {GET,POST})
    public String getCurrentCpModel(@RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String jwtToken)
    {
        log.info("ControlServiceController.getCurrentCpModel(): Received request");
        log.trace("ControlServiceController.getCurrentCpModel(): JWT token: {}", jwtToken);

        String currentCpModelId = coordinator.getCurrentCpModelId();
        log.info("ControlServiceController.getCurrentCpModel(): Current CP model: {}", currentCpModelId);

        return currentCpModelId;
    }

    @RequestMapping(value = { "/translator/constraintThresholds/{appId}", "/translator/constraintThresholds" }, method = {GET,POST},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection getConstraintThresholds(@PathVariable("appId") Optional<String> optAppId,
                                              @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String jwtToken)
    {
        String applicationId = optAppId.orElse(null);
        log.info("ControlServiceController.getConstraintThresholds(): Received request: app-id={}", applicationId);
        log.trace("ControlServiceController.getConstraintThresholds(): JWT token: {}", jwtToken);

        if (StringUtils.isBlank(applicationId)) {
            applicationId = coordinator.getCurrentAppModelId();
            log.info("ControlServiceController.getConstraintThresholds(): Using current application: curr-app-id={}", applicationId);
            if (applicationId==null) applicationId = "";
        }

        // Retrieve sensor information
        String appPath = coordinator._normalizeModelId(applicationId);
        Set constraints = coordinator.getMetricConstraints(appPath);
        log.info("ControlServiceController.getConstraintThresholds(): Constraints for application: {}: {}", applicationId, constraints);

        return constraints;
    }

    @GetMapping(value = {"/translator/getTopLevelNodesMetricContexts/{appId}", "/translator/getTopLevelNodesMetricContexts"},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<?> getTopLevelNodesMetricContexts(@PathVariable("appId") Optional<String> optAppId,
                                                        @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String jwtToken)
    {
        String applicationId = optAppId.orElse(null);
        log.info("ControlServiceController.getTopLevelNodesMetricContexts(): Received request: app-id={}", applicationId);
        log.trace("ControlServiceController.getTopLevelNodesMetricContexts(): JWT token: {}", jwtToken);

        if (StringUtils.isBlank(applicationId)) {
            applicationId = coordinator.getCurrentAppModelId();
            log.info("ControlServiceController.getTopLevelNodesMetricContexts(): Using current application: curr-app-id={}", applicationId);
            if (applicationId==null) return Collections.emptyList();
        }

        // Retrieve context metrics of the top-level DAG nodes
        String appModelId = coordinator._normalizeModelId(applicationId);
        log.debug("ControlServiceController.getTopLevelNodesMetricContexts(): appModelId: {}", appModelId);
        Set<MetricContext> results = coordinator.getMetricContextsForPrediction(appModelId);
        log.info("ControlServiceController.getTopLevelNodesMetricContexts(): Result: {}", results);

        return results;
    }

    // ---------------------------------------------------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------------------------------------------------

    protected String stripQuotes(String s) {
        return (s != null && s.startsWith("\"") && s.endsWith("\"")) ? s.substring(1, s.length() - 1) : s;
    }

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        RequestMappingHandlerMapping requestMappingHandlerMapping = applicationContext
                .getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping
                .getHandlerMethods();
        //map.forEach((key, value) -> log.info("..... {} {}", key, value));

        controllerEndpoints = map.keySet().stream()
                .filter(Objects::nonNull)
                .map(RequestMappingInfo::getPatternValues)
                .flatMap(Set::stream)
                .collect(Collectors.toList());
        log.debug("ControlServiceController.handleContextRefresh: controller-endpoints: {}", controllerEndpoints);

        controllerEndpointsShort = controllerEndpoints.stream()
                .map(s -> s.startsWith("/") ? s.substring(1) : s)
                .map(s -> s.indexOf("/") > 0 ? s.split("/", 2)[0] + "/**" : s)
                .map(e -> "/" + e.replaceAll("\\{.*", "**"))
                .distinct()
                .collect(Collectors.toList());
        log.debug("ControlServiceController.handleContextRefresh: controller-endpoints-short: {}", controllerEndpointsShort);
    }
}
