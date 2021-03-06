/*
 * Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.control;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import eu.melodic.event.baguette.client.install.*;
import eu.melodic.event.baguette.client.install.helper.CloudiatorInstallationHelper;
import eu.melodic.event.baguette.client.install.helper.InstallationHelperFactory;
import eu.melodic.event.baguette.client.install.instruction.InstallationInstructions;
import eu.melodic.event.baguette.server.BaguetteServer;
import eu.melodic.event.control.properties.ControlServiceProperties;
import eu.melodic.event.util.NetUtil;
import eu.melodic.models.commons.Watermark;
import eu.melodic.models.interfaces.ems.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.cdo.util.ConcurrentAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BadRequestException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@RestController
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ControlServiceController {

    @Autowired
    private ControlServiceProperties properties;
    @Autowired
    private ControlServiceCoordinator coordinator;

    // ------------------------------------------------------------------------------------------------------------
    // ESB and Upperware interfacing methods
    // ------------------------------------------------------------------------------------------------------------

    @RequestMapping(value = "/camelModel", method = POST)
    public String newCamelModel(@RequestBody CamelModelRequestImpl request,
                                @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String jwtToken)
            throws ConcurrentAccessException
    {
        log.info("ControlServiceController.newCamelModel(): Received request: {}", request);
        log.trace("ControlServiceController.newCamelModel()/camelModel: JWT token: {}", jwtToken);

        // Get information from request
        String applicationId = request.getApplicationId();
        String notificationUri = request.getNotificationURI();
        String requestUuid = request.getWatermark().getUuid();
        log.info("ControlServiceController.newCamelModel(): Request info: app-id={}, notification-uri={}, request-id={}",
                applicationId, notificationUri, requestUuid);

        // Start translation and reconfiguration in a worker thread
        coordinator.processNewModel(applicationId, null, notificationUri, requestUuid, jwtToken);
        log.debug("ControlServiceController.newCamelModel(): Model translation dispatched to a worker thread");

        return "OK";
    }

    @RequestMapping(value = "/camelModelJson", method = POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String newCamelModel(@RequestBody String requestStr,
                                @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String jwtToken)
            throws ConcurrentAccessException
    {
        log.info("ControlServiceController.newCamelModel(): Received request: {}", requestStr);
        log.trace("ControlServiceController.newCamelModel()/camelModelJson: JWT token: {}", jwtToken);

        // Use Gson to get model id's from request body (in JSON format)
        com.google.gson.JsonObject jobj = new com.google.gson.Gson().fromJson(requestStr, com.google.gson.JsonObject.class);
        String camelModelId = Optional.ofNullable(jobj.get("camel-model-id")).map(je -> stripQuotes(je.toString())).orElse(null);
        String cpModelId = Optional.ofNullable(jobj.get("cp-model-id")).map(je -> stripQuotes(je.toString())).orElse(null);
        log.info("ControlServiceController.newCamelModel(): CAMEL model id from request: {}", camelModelId);
        log.info("ControlServiceController.newCamelModel(): CP model id from request: {}", cpModelId);

        // Start translation and component reconfiguration in a worker thread
        coordinator.processNewModel(camelModelId, cpModelId, null, null, jwtToken);
        log.debug("ControlServiceController.newCamelModel(): Model translation dispatched to a worker thread");

        return "OK";
    }

    // ------------------------------------------------------------------------------------------------------------

    @RequestMapping(value = "/cpModelJson", method = POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String newCpModel(@RequestBody String requestStr,
                             @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String jwtToken)
            throws ConcurrentAccessException
    {
        log.info("ControlServiceController.newCpModel(): Received request: {}", requestStr);
        log.trace("ControlServiceController.newCpModel(): JWT token: {}", jwtToken);

        // Use Gson to get model id's from request body (in JSON format)
        com.google.gson.JsonObject jobj = new com.google.gson.Gson().fromJson(requestStr, com.google.gson.JsonObject.class);
        String cpModelId = Optional.ofNullable(jobj.get("cp-model-id")).map(je -> stripQuotes(je.toString())).orElse(null);
        log.info("ControlServiceController.newCpModel(): CP model id from request: {}", cpModelId);

        // Start CP model processing in a worker thread
        coordinator.processCpModel(cpModelId, null, null, jwtToken);
        log.debug("ControlServiceController.newCpModel(): CP Model processing dispatched to a worker thread");

        return "OK";
    }

    // ------------------------------------------------------------------------------------------------------------

    @RequestMapping(value = "/monitors", method = POST)
    public HttpEntity<MonitorsDataResponse> getSensors(@RequestBody MonitorsDataRequestImpl request,
                                                       @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String jwtToken)
            throws ConcurrentAccessException
    {
        log.info("ControlServiceController.getSensors(): Received request: {}", request);
        log.trace("ControlServiceController.getSensors(): JWT token: {}", jwtToken);

        // Get information from request
        String applicationId = request.getApplicationId();
        Watermark watermark = request.getWatermark();
        String requestUuid = watermark.getUuid();
        log.info("ControlServiceController.getSensors(): Request info: app-id={}, watermark={}, request-id={}", applicationId, watermark, requestUuid);

        // Retrieve sensor information
        List<Monitor> sensors = coordinator.getSensorsOfCamelModel(applicationId);

        // Update watermark
        watermark.setUser("EMS");
        watermark.setSystem("EMS");
        watermark.setDate(new java.util.Date());

        // Print debug info about sensors
        if (log.isDebugEnabled()) {
            log.debug("ControlServiceController.getSensors(): Printing monitors for Request: {}", requestUuid);
            sensors.forEach(m -> {
                log.debug("ControlServiceController.getSensors():     Monitor: metric/topic={}, component={}, additional-properties={}",
                        m.getMetric(), m.getComponent(), m.getAdditionalProperties());
                Sensor s = m.getSensor();
                if (s.isPushSensor())
                    log.debug("ControlServiceController.getSensors():       PushSensor: port={}", m.getSensor().getPushSensor().getPort());
                else
                    log.debug("ControlServiceController.getSensors():       PullSensor: class-name={}, interval={}, configuration={}",
                            m.getSensor().getPullSensor().getClassName(), m.getSensor().getPullSensor().getInterval(), m.getSensor().getPullSensor().getConfiguration());
            });
        }

        // Prepare response
        MonitorsDataResponse response = new MonitorsDataResponseImpl();
        response.setMonitors(sensors);
        response.setWatermark(watermark);
        HttpEntity<MonitorsDataResponse> entity = coordinator.createHttpEntity(MonitorsDataResponse.class, response, jwtToken);
        log.info("ControlServiceController.getSensors(): Response: {}", response);

        //return response;
        return entity;
    }

    // ------------------------------------------------------------------------------------------------------------
    // Translation results methods
    // ------------------------------------------------------------------------------------------------------------

    @RequestMapping(value = "/translator/currentCamelModel", method = {GET,POST})
    public String getCurrentCamelModel(@RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String jwtToken)
    {
        log.info("ControlServiceController.getCurrentCamelModel(): Received request");
        log.trace("ControlServiceController.getCurrentCamelModel(): JWT token: {}", jwtToken);

        String currentCamelModelId = coordinator.getCurrentCamelModelId();
        log.info("ControlServiceController.getCurrentCamelModel(): Current CAMEL model: {}", currentCamelModelId);

        return currentCamelModelId;
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

    @RequestMapping(value = { "/translator/constraintThresholds/{appId}", "/translator/constraintThresholds" }, method = {GET,POST})
    public Collection getConstraintThresholds(@PathVariable("appId") Optional<String> optAppId,
                                              @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String jwtToken)
    {
        String applicationId = optAppId.orElse(null);
        log.info("ControlServiceController.getConstraintThresholds(): Received request: app-id={}", applicationId);
        log.trace("ControlServiceController.getConstraintThresholds(): JWT token: {}", jwtToken);

        if (StringUtils.isBlank(applicationId)) {
            applicationId = coordinator.getCurrentCamelModelId();
            log.info("ControlServiceController.getConstraintThresholds(): Using current application: curr-app-id={}", applicationId);
            if (applicationId==null) applicationId = "";
        }

        // Retrieve sensor information
        String appPath = (applicationId.startsWith("/")) ? applicationId : "/"+applicationId;
        Set constraints = coordinator.getMetricConstraints(appPath);
        log.info("ControlServiceController.getConstraintThresholds(): Constraints for application: {}: {}", applicationId, constraints);

        return constraints;
    }

    // ------------------------------------------------------------------------------------------------------------
    // Broker-CEP query & control methods
    // ------------------------------------------------------------------------------------------------------------

    @RequestMapping(value = "/broker/credentials", method = {GET,POST},
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public HttpEntity<Map> getBrokerCredentials(@RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String jwtToken)
    {
        log.info("ControlServiceController.getBrokerCredentials(): BEGIN");
        log.trace("ControlServiceController.getBrokerCredentials(): JWT token: {}", jwtToken);

        // Retrieve sensor information
        String brokerClientsUrl = coordinator.getBrokerCep().getBrokerCepProperties().getBrokerUrlForClients();
        String brokerUsername = coordinator.getBrokerCep().getBrokerUsername();
        String brokerPassword = coordinator.getBrokerCep().getBrokerPassword();
        String brokerCertificatePem = coordinator.getBrokerCep().getBrokerCertificate();

        // Prepare response
        Map<String,String> response = new HashMap<>();
        response.put("url", brokerClientsUrl);
        response.put("username", brokerUsername);
        response.put("password", brokerPassword);
        response.put("certificate", brokerCertificatePem);
        HttpEntity<Map> entity = coordinator.createHttpEntity(Map.class, response, jwtToken);
        log.info("ControlServiceController.getBrokerCredentials(): Response: {}", response);

        //return response;
        return entity;
    }

    // ------------------------------------------------------------------------------------------------------------
    // Baguette control methods
    // ------------------------------------------------------------------------------------------------------------

    @RequestMapping(value = "/baguette/stopServer", method = {GET, POST},
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String baguetteStopServer() {
        log.info("ControlServiceController.baguetteStopServer(): Request received");

        // Dispatch Baguette stop operation in a worker thread
        coordinator.stopBaguette();
        log.info("ControlServiceController.baguetteStopServer(): Baguette stop operation dispatched to a worker thread");

        return "OK";
    }

    @RequestMapping(value = "/baguette/registerNode", method = POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String baguetteRegisterNode(@RequestBody String jsonNode, HttpServletRequest request) throws Exception {
        log.info("ControlServiceController.baguetteRegisterNode(): Invoked");
        log.debug("ControlServiceController.baguetteRegisterNode(): Node json:\n{}", jsonNode);

        // Extract node information from json
        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        Map<String,Object> nodeMap = new Gson().fromJson(jsonNode, type);
        String nodeId = (String) nodeMap.get("id");
        log.debug("ControlServiceController.baguetteRegisterNode(): Node information: map={}", nodeMap);

        // Register node to Baguette server
        BaguetteServer baguette = coordinator.getBaguetteServer();
        String clientId = baguette.registerClient(nodeMap);

        // Get web server base URL
        String staticResourceContext = coordinator.getControlServiceProperties().getStaticResourceContext();
        staticResourceContext =  StringUtils.substringBeforeLast(staticResourceContext,"/**");
        staticResourceContext =  StringUtils.substringBeforeLast(staticResourceContext,"/*");
        if (!staticResourceContext.startsWith("/")) staticResourceContext = "/"+staticResourceContext;
        String ipSetting = coordinator.getControlServiceProperties().getIpSetting().toString();
        String baseUrl =
                (ControlServiceProperties.IpSetting.DEFAULT_IP == coordinator.getControlServiceProperties().getIpSetting())
                        ? request.getScheme()+"://"+ NetUtil.getDefaultIpAddress() +":"+request.getServerPort()+staticResourceContext
                        : request.getScheme()+"://"+ NetUtil.getPublicIpAddress() +":"+request.getServerPort()+staticResourceContext;
        log.debug("ControlServiceController.baguetteRegisterNode(): baseUrl={}", baseUrl);

        // Create context map
        Map<String,String> contextMap = new HashMap<>();
        contextMap.put("BASE_URL", baseUrl);
        contextMap.put("CLIENT_ID", clientId);
        contextMap.put("IP_SETTING", ipSetting);

        // Continue processing according to ExecutionWare type
        String response;
        if (properties.getExecutionware()==ControlServiceProperties.ExecutionWare.CLOUDIATOR) {
            response = getClientInstallationInstructionsFromCloudiator(nodeMap, contextMap, baguette);
        } else {
            response = createClientInstallationTask(nodeMap, contextMap, baguette);
        }

        log.info("ControlServiceController.baguetteRegisterNode(): node-id: {}", nodeId);
        log.debug("ControlServiceController.baguetteRegisterNode(): node: {}, json: {}", nodeId, response);
        return response;
    }

    // Retained for backward compatibility with Cloudiator
    public String getClientInstallationInstructionsFromCloudiator(Map<String,Object> nodeMap, Map<String,String> contextMap, BaguetteServer baguette) throws IOException {
        // Prepare Baguette Client installation instructions for node
        String nodeId = (String) nodeMap.get("id");
        String nodeOs = (String) nodeMap.get("operatingSystem");
        InstallationInstructions installationInstructions =
                CloudiatorInstallationHelper.getInstance().prepareInstallationInstructionsForOs(nodeMap, contextMap, baguette);
        if (installationInstructions==null) {
            log.warn("ControlServiceController.baguetteRegisterNode(): ERROR: Unknown node OS: {}", nodeOs);
            return null;
        }
        log.debug("ControlServiceController.baguetteRegisterNode(): installationInstructions: {}", installationInstructions);

        // Convert 'installationInstructions' into json string
        Gson gson = new Gson();
        String json = gson.toJson(installationInstructions, InstallationInstructions.class);

        log.trace("ControlServiceController.baguetteRegisterNode(): installationInstructions: node: {}, json:\n{}", nodeId, json);
        return json;
    }

    public String createClientInstallationTask(Map<String,Object> nodeMap, Map<String,String> contextMap, BaguetteServer baguette) throws Exception {
        //log.info("ControlServiceController.baguetteRegisterNodeForProactive(): INPUT: node-map: {}", nodeMap);

        ClientInstallationTask installationTask = InstallationHelperFactory.getInstance()
                .createInstallationHelper(nodeMap)
                .createClientInstallationTask(nodeMap, contextMap, baguette);
        ClientInstaller.instance().addTask(installationTask);
        log.debug("ControlServiceController.baguetteRegisterNodeForProactive(): New installation-task: {}", installationTask);

        return "OK";
    }

    @RequestMapping(value = "/baguette/getNodeInfoByAddress/{ipAddress:.+}", method = {GET, POST})
    public Map<String,Object> baguetteGetNodeInfoByAddress(@PathVariable String ipAddress) throws Exception {
        log.info("ControlServiceController.baguetteGetNodeInfoByAddress(): ip-address={}", ipAddress);

        BaguetteServer baguette = coordinator.getBaguetteServer();
        Map<String,Object> nodeInfo = baguette.getNodeRegistry().getNodeByAddress(ipAddress);

        log.info("ControlServiceController.baguetteGetNodeInfoByAddress(): Info for node at: ip-address={}, Node Info:\n{}",
                ipAddress, nodeInfo);
        return nodeInfo;
    }

    @RequestMapping(value = "/baguette/getNodeNameByAddress/{ipAddress:.+}", method = {GET, POST},
            produces = MediaType.TEXT_PLAIN_VALUE)
    public String baguetteGetNodeNameByAddress(@PathVariable String ipAddress) throws Exception {
        log.info("ControlServiceController.baguetteGetNodeNameByAddress(): ip-address={}", ipAddress);

        BaguetteServer baguette = coordinator.getBaguetteServer();
        Map<String,Object> nodeInfo = baguette.getNodeRegistry().getNodeByAddress(ipAddress);
        String nodeName = (String)nodeInfo.get("name");

        log.info("ControlServiceController.baguetteGetNodeNameByAddress(): Name of node at: ip-address={}, Node name: {}",
                ipAddress, nodeName);
        return nodeName;
    }

    // ------------------------------------------------------------------------------------------------------------
    // Event Generation and Debugging methods
    // ------------------------------------------------------------------------------------------------------------

    @RequestMapping(value = "/event/generate-start/{clientId}/{topicName}/{interval}/{lowerValue}-{upperValue}", method = GET)
    public String startEventGeneration(@PathVariable String clientId, @PathVariable String topicName, @PathVariable long interval, @PathVariable double lowerValue, @PathVariable double upperValue) {
        log.info("ControlServiceController.startEventGeneration(): PARAMS: client={}, topic={}, interval={}, value-range=[{},{}]", clientId, topicName, interval, lowerValue, upperValue);
        return coordinator.eventGenerationStart(clientId, topicName, interval, lowerValue, upperValue);
    }

    @RequestMapping(value = "/event/generate-stop/{clientId}/{topicName}", method = GET)
    public String stopEventGeneration(@PathVariable String clientId, @PathVariable String topicName) {
        log.info("ControlServiceController.stopEventGeneration(): PARAMS: client={}, topic={}", clientId, topicName);
        return coordinator.eventGenerationStop(clientId, topicName);
    }

    @RequestMapping(value = "/event/send/{clientId}/{topicName}/{value}", method = GET)
    public String sendEvent(@PathVariable String clientId, @PathVariable String topicName, @PathVariable double value) {
        log.info("ControlServiceController.sendEvent(): PARAMS: client={}, topic={}, value={}", clientId, topicName, value);
        return coordinator.eventLocalSend(clientId, topicName, value);
    }

    // ------------------------------------------------------------------------------------------------------------

    @RequestMapping(value = "/client/list", method = GET)
    public List<String> listClients() {
        List<String> clients = coordinator.clientList();
        log.info("ControlServiceController.listClients(): {}", clients);
        return clients;
    }

    @RequestMapping(value = "/client/command/{clientId}/{command}", method = GET)
    public String sendClientCommand(@PathVariable String clientId, @PathVariable String command) {
        log.info("ControlServiceController.sendClientCommand(): PARAMS: client={}, command={}", clientId, command);
        return coordinator.clientCommandSend(clientId, command);
    }

    // ------------------------------------------------------------------------------------------------------------
    // EMS status and information query methods
    // ------------------------------------------------------------------------------------------------------------

    @RequestMapping(value = { "/ems/shutdown", "/ems/shutdown/{exitApp}" }, method = {GET, POST})
    public String emsShutdown(@PathVariable Optional<Boolean> exitApp) {
        boolean _exitApp = exitApp.orElse(false);
        log.info("ControlServiceController.emsShutdown(): exitApp={}", _exitApp);
        coordinator.emsShutdown();
        if (_exitApp) coordinator.emsExit();
        return "OK";
    }

    @RequestMapping(value = "/ems/status", method = {GET, POST},
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String emsStatus() {
        log.info("ControlServiceController.emsStatus(): BEGIN");

        log.debug("ControlServiceController.emsStatus(): END");
        return "{}";
    }

    @RequestMapping(value = "/ems/topology", method = {GET, POST},
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String emsTopology() {
        log.info("ControlServiceController.emsTopology(): BEGIN");

        log.debug("ControlServiceController.emsTopology(): END");
        return "{}";
    }

    // ------------------------------------------------------------------------------------------------------------

    @RequestMapping(value = "/health", method = GET)
    public void health() {
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public String handleException(BadRequestException exception) {
        log.error(format("Returning error response: invalid request (%s) ", exception.getMessage()));
        return exception.getMessage();
    }


    // ---------------------------------------------------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------------------------------------------------

    protected String stripQuotes(String s) {
        return (s != null && s.startsWith("\"") && s.endsWith("\"")) ? s.substring(1, s.length() - 1) : s;
    }
}
