/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.control;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import eu.melodic.event.baguette.client.install.ClientInstallationTask;
import eu.melodic.event.baguette.client.install.ClientInstaller;
import eu.melodic.event.baguette.client.install.helper.InstallationHelperFactory;
import eu.melodic.event.baguette.server.BaguetteServer;
import eu.melodic.event.baguette.server.NodeRegistryEntry;
import eu.melodic.event.baguette.server.properties.BaguetteServerProperties;
import eu.melodic.event.control.properties.ControlServiceProperties;
import eu.melodic.event.control.webconf.WebSecurityConfig;
import eu.melodic.event.translate.TranslationContext;
import eu.melodic.event.util.CredentialsMap;
import eu.melodic.models.commons.Watermark;
import eu.melodic.models.interfaces.ems.*;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.cdo.util.ConcurrentAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BadRequestException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Stream;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@RestController
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ControlServiceController {

    private final static String ROLES_ALLOWED_JWT_TOKEN_OR_API_KEY =
            "hasAnyRole('"+WebSecurityConfig.ROLE_JWT_TOKEN+"','"+WebSecurityConfig.ROLE_API_KEY+"')";

    @Autowired
    private ControlServiceProperties properties;
    @Autowired
    private ControlServiceCoordinator coordinator;

    @Autowired
    private RequestMappingHandlerMapping mvcHandlerMapping;

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

    @GetMapping(value = {"/translator/getTopLevelNodesMetricContexts/{appId}", "/translator/getTopLevelNodesMetricContexts"})
    public Collection<?> getTopLevelNodesMetricContexts(@PathVariable("appId") Optional<String> optAppId,
                                                        @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String jwtToken)
    {
        String applicationId = optAppId.orElse(null);
        log.info("ControlServiceController.getTopLevelNodesMetricContexts(): Received request: app-id={}", applicationId);
        log.trace("ControlServiceController.getTopLevelNodesMetricContexts(): JWT token: {}", jwtToken);

        if (StringUtils.isBlank(applicationId)) {
            applicationId = coordinator.getCurrentCamelModelId();
            log.info("ControlServiceController.getTopLevelNodesMetricContexts(): Using current application: curr-app-id={}", applicationId);
            if (applicationId==null) return Collections.emptyList();
        }

        // Retrieve context metrics of the top-level DAG nodes
        String camelModelId = (applicationId.startsWith("/")) ? applicationId : "/"+applicationId;
        log.debug("ControlServiceController.getTopLevelNodesMetricContexts(): camelModelId: {}", camelModelId);
        Set<TranslationContext.MetricContext> results = coordinator.getMetricContextsForPrediction(camelModelId);
        log.info("ControlServiceController.getTopLevelNodesMetricContexts(): Result: {}", results);

        return results;
    }

    // ------------------------------------------------------------------------------------------------------------
    // Broker-CEP query & control methods
    // ------------------------------------------------------------------------------------------------------------

    @PreAuthorize(ROLES_ALLOWED_JWT_TOKEN_OR_API_KEY)
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

    @PreAuthorize(ROLES_ALLOWED_JWT_TOKEN_OR_API_KEY)
    @RequestMapping(value = "/baguette/ref/{ref}", method = {GET,POST},
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public HttpEntity<Map> getNodeCredentials(@PathVariable("ref") Optional<String> optRef,
                                              @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String jwtToken)
    {
        log.info("ControlServiceController.getNodeCredentials(): BEGIN: ref={}", optRef);
        log.trace("ControlServiceController.getNodeCredentials(): JWT token: {}", jwtToken);

        if (StringUtils.isBlank(optRef.orElse(null)))
            throw new IllegalArgumentException("The 'ref' parameter is mandatory");

        // Check if it is EMS server ref
        if (coordinator.getReference().equals(optRef.get())) {
            if (coordinator.getBaguetteServer()==null || !coordinator.getBaguetteServer().isServerRunning()) {
                log.warn("ControlServiceController.getNodeCredentials(): Baguette Server is not started");
                return null;
            }

            BaguetteServerProperties config = coordinator.getBaguetteServer().getConfiguration();
            String address = config.getServerAddress();
            int port = config.getServerPort();
            String username = null;
            String password = null;
            CredentialsMap credentials = config.getCredentials();
            if (credentials.size()>0) {
                username = credentials.keySet().stream().findFirst().orElse(null);
                password = credentials.get(username);
            }
            String key = coordinator.getBaguetteServer().getServerPubkey();

            log.debug("ControlServiceController.getNodeCredentials(): Retrieved EMS server connection info by reference: ref={}", optRef.get());

            // Prepare response
            Map<String,String> response = new HashMap<>();
            response.put("hostname", address);
            response.put("port", ""+port);
            response.put("username", username);
            response.put("password", password);
            response.put("private-key", key);
            HttpEntity<Map> entity = coordinator.createHttpEntity(Map.class, response, jwtToken);
            log.debug("ControlServiceController.getNodeCredentials(): Response: ** Not shown because it contains credentials **");

            return entity;
        }

        // Retrieve node credentials
        NodeRegistryEntry entry = coordinator.getBaguetteServer().getNodeRegistry().getNodeByReference(optRef.get());
        if (entry==null) {
            throw new IllegalArgumentException("Not found Node with reference: "+optRef.get());
        }
        log.debug("ControlServiceController.getNodeCredentials(): Retrieved node by reference: ref={}", optRef.get());

        // Prepare response
        Map<String,String> response = new HashMap<>();
        response.put("hostname", entry.getIpAddress());
        response.put("port", entry.getPreregistration().getOrDefault("ssh.port", "22"));
        response.put("username", entry.getPreregistration().get("ssh.username"));
        response.put("password", entry.getPreregistration().get("ssh.password"));
        response.put("private-key", entry.getPreregistration().get("ssh.key"));
        HttpEntity<Map> entity = coordinator.createHttpEntity(Map.class, response, jwtToken);
        log.debug("ControlServiceController.getNodeCredentials(): Response: ** Not shown because it contains credentials **");

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
        NodeRegistryEntry entry;
        try {
            entry = coordinator.getBaguetteServer().registerClient(nodeMap);
        } catch (Exception e) {
            log.error("ControlServiceController.baguetteRegisterNode(): EXCEPTION while registering node: map={}\n", nodeMap, e);
            return "ERROR "+e.getMessage();
        }

        // Update client registration info with BASE_URL, IP_SETTING and CLIENT_ID
        updateRegistrationInfo(request, entry);

        // Continue processing according to ExecutionWare type
        String response;
        log.info("ControlServiceController.baguetteRegisterNode(): ExecutionWare: {}", properties.getExecutionware());
        if (properties.getExecutionware()==ControlServiceProperties.ExecutionWare.CLOUDIATOR) {
            response = getClientInstallationInstructions(entry);
        } else {
            response = createClientInstallationTask(entry);
        }

        log.info("ControlServiceController.baguetteRegisterNode(): node-id: {}", nodeId);
        log.debug("ControlServiceController.baguetteRegisterNode(): node: {}, json: {}", nodeId, response);
        return response;
    }

    private void updateRegistrationInfo(HttpServletRequest request, NodeRegistryEntry entry) {
        // Get web server base URL
        String staticResourceContext = coordinator.getControlServiceProperties().getStaticResourceContext();
        staticResourceContext =  StringUtils.substringBeforeLast(staticResourceContext,"/**");
        staticResourceContext =  StringUtils.substringBeforeLast(staticResourceContext,"/*");
        if (!staticResourceContext.startsWith("/")) staticResourceContext = "/"+staticResourceContext;
        String baseUrl =
                request.getScheme()+"://"+ coordinator.getServerIpAddress() +":"+request.getServerPort()+staticResourceContext;
        log.debug("ControlServiceController.baguetteRegisterNode(): baseUrl={}", baseUrl);

        // Get IP Setting and Client ID
        String ipSetting = coordinator.getControlServiceProperties().getIpSetting().toString();
        String clientId = entry.getClientId();

        // Add to context
        entry.getPreregistration().put("BASE_URL", baseUrl);
        entry.getPreregistration().put("CLIENT_ID", clientId);
        entry.getPreregistration().put("IP_SETTING", ipSetting);
    }

    // Retained for backward compatibility with Cloudiator
    @SneakyThrows
    public String getClientInstallationInstructions(NodeRegistryEntry entry) throws IOException {
        // Prepare Baguette Client installation instructions for node
        final String CLOUDIATOR_HELPER_CLASS = "eu.melodic.event.extra.cloudiator.CloudiatorInstallationHelper";
        String json = InstallationHelperFactory.getInstance()
                .createInstallationHelperBean(CLOUDIATOR_HELPER_CLASS, entry)
                .getInstallationInstructionsForOs(entry)
                .orElse(Collections.emptyList())
                .stream().findFirst()
                .orElse(null);
        if (json==null) {
            log.warn("ControlServiceController.baguetteRegisterNode(): No instruction sets: node-map={}", entry.getPreregistration());
            return null;
        }
        log.debug("ControlServiceController.baguetteRegisterNode(): instructionsSet: {}", json);

        log.trace("ControlServiceController.baguetteRegisterNode(): instructionsSet: node-map={}, json:\n{}", entry.getPreregistration(), json);
        return json;
    }

    public String createClientInstallationTask(NodeRegistryEntry entry) throws Exception {
        //log.info("ControlServiceController.baguetteRegisterNodeForProactive(): INPUT: node-map: {}", nodeMap);

        ClientInstallationTask installationTask = InstallationHelperFactory.getInstance()
                .createInstallationHelper(entry)
                .createClientInstallationTask(entry);
        ClientInstaller.instance().addTask(installationTask);
        log.debug("ControlServiceController.baguetteRegisterNodeForProactive(): New installation-task: {}", installationTask);

        return "OK";
    }

    @RequestMapping(value = "/baguette/getNodeInfoByAddress/{ipAddress:.+}", method = {GET, POST})
    public NodeRegistryEntry baguetteGetNodeInfoByAddress(@PathVariable String ipAddress) throws Exception {
        log.info("ControlServiceController.baguetteGetNodeInfoByAddress(): ip-address={}", ipAddress);

        BaguetteServer baguette = coordinator.getBaguetteServer();
        NodeRegistryEntry nodeInfo = baguette.getNodeRegistry().getNodeByAddress(ipAddress);

        log.info("ControlServiceController.baguetteGetNodeInfoByAddress(): Info for node at: ip-address={}, Node Info:\n{}",
                ipAddress, nodeInfo);
        return nodeInfo;
    }

    @RequestMapping(value = "/baguette/getNodeNameByAddress/{ipAddress:.+}", method = {GET, POST},
            produces = MediaType.TEXT_PLAIN_VALUE)
    public String baguetteGetNodeNameByAddress(@PathVariable String ipAddress) throws Exception {
        log.info("ControlServiceController.baguetteGetNodeNameByAddress(): ip-address={}", ipAddress);

        BaguetteServer baguette = coordinator.getBaguetteServer();
        NodeRegistryEntry nodeInfo = baguette.getNodeRegistry().getNodeByAddress(ipAddress);
        String nodeName = nodeInfo!=null ? nodeInfo.getPreregistration().get("name") : null;

        log.info("ControlServiceController.baguetteGetNodeNameByAddress(): Name of node at: ip-address={}, Node name: {}",
                ipAddress, nodeName);
        return nodeName;
    }

    // ------------------------------------------------------------------------------------------------------------
    // Event Generation and Debugging methods
    // ------------------------------------------------------------------------------------------------------------

    @RequestMapping(value = "/event/generate-start/{clientId}/{topicName}/{interval}/{lowerValue}/{upperValue}", method = GET)
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

    @RequestMapping(value = "/client/list/map", method = GET)
    public Map<String, Map<String, String>> listClientMaps() {
        Map<String, Map<String, String>> clients = coordinator.clientMap();
        log.info("ControlServiceController.listClientMaps(): {}", clients);
        return clients;
    }

    @RequestMapping(value = "/client/command/{clientId}/{command:.+}", method = GET)
    public String clientCommand(@PathVariable String clientId, @PathVariable String command) {
        log.info("ControlServiceController.clientCommand(): PARAMS: client={}, command={}", clientId, command);
        return coordinator.clientCommandSend(clientId, command);
    }

    @RequestMapping(value = "/cluster/command/{clusterId}/{command:.+}", method = GET)
    public String clusterCommand(@PathVariable String clusterId, @PathVariable String command) {
        log.info("ControlServiceController.clusterCommand(): PARAMS: cluster={}, command={}", clusterId, command);
        return coordinator.clusterCommandSend(clusterId, command);
    }

    // ------------------------------------------------------------------------------------------------------------
    // EMS status and information query methods
    // ------------------------------------------------------------------------------------------------------------

    @RequestMapping(value = "/ems/shutdown", method = {GET, POST})
    public String emsShutdown() {
        log.info("ControlServiceController.emsShutdown(): ");
        coordinator.emsShutdown();
        return "OK";
    }

    @RequestMapping(value = { "/ems/exit", "/ems/exit/{exitCode}" }, method = {GET, POST})
    public String emsExit(@PathVariable Optional<Integer> exitCode) {
        if (properties.isExitAllowed()) {
            int _exitCode = exitCode.orElse(properties.getExitCode());
            log.info("ControlServiceController.emsExit(): exitCode={}", _exitCode);
            coordinator.emsShutdown();
            coordinator.emsExit(_exitCode);
            return "OK";
        } else {
            log.info("ControlServiceController.emsExit(): Exiting EMS is not allowed");
            return "NOT ALLOWED";
        }
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
    public String health() {
        return "OK";
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

    public Stream<String> getControllerEndpoints() {
        return mvcHandlerMapping.getHandlerMethods().keySet().stream()
                .filter(Objects::nonNull)
                .map(k -> k.getPatternsCondition().getPatterns())
                .flatMap(Set::stream);
    }

    public String[] getControllerEndpointsShort() {
        return getControllerEndpoints()
                .map(s -> s.startsWith("/") ? s.substring(1) : s)
                .map(s -> s.indexOf("/") > 0 ? s.split("/", 2)[0] + "/**" : s)
                .map(e -> "/" + e.replaceAll("\\{.*", "**"))
                .distinct()
                .toArray(String[]::new);
    }
}
