/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.control;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import eu.melodic.event.control.util.CloudiatorHelper;
import eu.melodic.models.commons.Watermark;
import eu.melodic.models.interfaces.ems.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.cdo.util.ConcurrentAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BadRequestException;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@RestController
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ControlServiceController {

    @Autowired
    private ControlServiceCoordinator coordinator;

    // ------------------------------------------------------------------------------------------------------------
    // ESB and Upperware interfacing methods
    // ------------------------------------------------------------------------------------------------------------

    @RequestMapping(value = "/camelModel", method = POST)
    public String newCamelModel(@RequestBody CamelModelRequestImpl request) throws ConcurrentAccessException {
        log.info("ControlServiceController.newCamelModel(): Received request: {}", request);

        // Get information from request
        String applicationId = request.getApplicationId();
        String notificationUri = request.getNotificationURI();
        String requestUuid = request.getWatermark().getUuid();
        log.info("ControlServiceController.newCamelModel(): Request info: app-id={}, notification-uri={}, request-id={}", applicationId, notificationUri, requestUuid);

        // Start translation and reconfiguration in a worker thread
        coordinator.processNewModel(applicationId, null, notificationUri, requestUuid);
        log.debug("ControlServiceController.newCamelModel(): Model translation dispatched to a worker thread");

        return "OK";
    }

    @RequestMapping(value = "/camelModelJson", method = POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String newCamelModel(@RequestBody String requestStr) throws ConcurrentAccessException {
        log.info("ControlServiceController.newCamelModel(): Received request: {}", requestStr);

        // Use Gson to get model id's from request body (in JSON format)
        com.google.gson.JsonObject jobj = new com.google.gson.Gson().fromJson(requestStr, com.google.gson.JsonObject.class);
        String camelModelId = Optional.ofNullable(jobj.get("camel-model-id")).map(je -> stripQuotes(je.toString())).orElse(null);
        String cpModelId = Optional.ofNullable(jobj.get("cp-model-id")).map(je -> stripQuotes(je.toString())).orElse(null);
        log.info("ControlServiceController.newCamelModel(): CAMEL model id from request: {}", camelModelId);
        log.info("ControlServiceController.newCamelModel(): CP model id from request: {}", cpModelId);

        // Start translation and component reconfiguration in a worker thread
        coordinator.processNewModel(camelModelId, cpModelId, null, null);
        log.debug("ControlServiceController.newCamelModel(): Model translation dispatched to a worker thread");

        return "OK";
    }

    // ------------------------------------------------------------------------------------------------------------

    @RequestMapping(value = "/cpModelJson", method = POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String newCpModel(@RequestBody String requestStr) throws ConcurrentAccessException {
        log.info("ControlServiceController.newCpModel(): Received request: {}", requestStr);

        // Use Gson to get model id's from request body (in JSON format)
        com.google.gson.JsonObject jobj = new com.google.gson.Gson().fromJson(requestStr, com.google.gson.JsonObject.class);
        String cpModelId = Optional.ofNullable(jobj.get("cp-model-id")).map(je -> stripQuotes(je.toString())).orElse(null);
        log.info("ControlServiceController.newCpModel(): CP model id from request: {}", cpModelId);

        // Start CP model processing in a worker thread
        coordinator.processCpModel(cpModelId, null, null);
        log.debug("ControlServiceController.newCpModel(): CP Model processing dispatched to a worker thread");

        return "OK";
    }

    // ------------------------------------------------------------------------------------------------------------

    @RequestMapping(value = "/monitors", method = POST)
    public MonitorsDataResponse getSensors(@RequestBody MonitorsDataRequestImpl request) throws ConcurrentAccessException {
        log.info("ControlServiceController.getSensors(): Received request: {}", request);

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

        // Prepare response
        MonitorsDataResponse response = new MonitorsDataResponseImpl();
        response.setMonitors(sensors);
        response.setWatermark(watermark);
        log.info("ControlServiceController.getSensors(): Response: {}", response);

        return response;
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

    public static final List<String> LINUX_OS_FAMILIES = java.util.Arrays.asList("CENTOS", "DARWIN", "DEBIAN", "FEDORA ", "FREEBSD ", "GENTOO", "COREOS", "AMZN_LINUX", "MANDRIVA ", "NETBSD", "OEL ", "OPENBSD", "RHEL", "SCIENTIFIC", "CEL", "SLACKWARE", "SOLARIS", "SUSE", "TURBOLINUX", "CLOUD_LINUX", "UBUNTU");

    @RequestMapping(value = "/baguette/registerNode", method = POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String baguetteRegisterNode(@RequestBody String jsonNode, HttpServletRequest request) {
        log.info("ControlServiceController.baguetteRegisterNode(): Invoked");
        log.debug("ControlServiceController.baguetteRegisterNode(): Node json:\n{}", jsonNode);

        // Extract node information from json
        JsonParser parser = new JsonParser();
        JsonObject jo = parser.parse(jsonNode).getAsJsonObject();
        String nodeId = jo.get("id").getAsString();
        String nodeName = jo.get("name").getAsString();
        String nodeType = jo.get("type").getAsString();
        String nodeProvider = jo.getAsJsonObject("nodeProperties").get("providerId").getAsString();
        String nodeOs = jo.getAsJsonObject("nodeProperties").getAsJsonObject("operatingSystem").get("operatingSystemFamily").getAsString();
        String nodeIpAddress = jo.getAsJsonObject("connectTo").get("ip").getAsString();
        log.info("ControlServiceController.baguetteRegisterNode(): Node information: id={}, name={}, type={}, provider={}, os={}, ip-address={}",
                nodeId, nodeName, nodeType, nodeProvider, nodeOs, nodeIpAddress);

        //XXX: TODO: do actual node registration with Baguette server. More information might be returned.
        //++++++++++++++++++++

        // Prepare Baguette Client installation instructions for node
        String baseUrl = request.getRequestURL().toString().replace("/baguette/registerNode", "");
        //int serverPort = request.getServerPort();
        CloudiatorHelper.InstallationInstructions installationInstructions = null;

        if (LINUX_OS_FAMILIES.contains(nodeOs.toUpperCase())) installationInstructions = prepareInstallationInstructionsForLinux(baseUrl);

        if (installationInstructions==null) {
            log.warn("ControlServiceController.baguetteRegisterNode(): ERROR: Unknown node OS: {}", nodeOs);
            return null;
        }
        log.debug("ControlServiceController.baguetteRegisterNode(): installationInstructions: {}", installationInstructions);

        // Convert 'installationInstructions' into json string
        Gson gson = new Gson();
        String json = gson.toJson(installationInstructions, CloudiatorHelper.InstallationInstructions.class);

        log.info("ControlServiceController.baguetteRegisterNode(): installationInstructions: node: {}, json:\n{}", nodeId, json);
        return json;
    }

    private CloudiatorHelper.InstallationInstructions prepareInstallationInstructionsForLinux(String baseUrl) {
        log.debug("ControlServiceController.prepareInstallationInstructionsForLinux(): Invoked");

        String installationDir = "/opt/baguette-client";
        String baseDownloadUrl = baseUrl + "/resources";    //XXX: TODO: use value from control service settings
        String installScriptUrl = baseDownloadUrl + "/install.sh";
        String installScriptPath = installationDir + "/bin/install.sh";
        String apiKey = "1234567890";                       //XXX: TODO: use value from control service settings or generated value
        String clientConfPath = installationDir + "/conf/baguette-client.properties";
        String clientConfAppend = "\n# ++++++++++++  TODO  +++++++++++\n\n";

        CloudiatorHelper.InstallationInstructions installationInstructions = new CloudiatorHelper.InstallationInstructions();
        installationInstructions.setOs("linux");
        // Create Baguette Client installation directory
        installationInstructions.appendInstruction(CloudiatorHelper.INSTRUCTION_TYPE.LOG, "Create Baguette Client installation directory");
        installationInstructions.appendInstruction(CloudiatorHelper.INSTRUCTION_TYPE.CMD, "sudo mkdir -p "+installationDir+"/bin");
        installationInstructions.appendInstruction(CloudiatorHelper.INSTRUCTION_TYPE.CMD, "sudo mkdir -p "+installationDir+"/logs");

        // Download Baguette Client installation script
        installationInstructions.appendInstruction(CloudiatorHelper.INSTRUCTION_TYPE.LOG, "Download Baguette Client installation script");
        installationInstructions.appendInstruction(CloudiatorHelper.INSTRUCTION_TYPE.CMD, "sudo wget "+installScriptUrl+" -O "+installScriptPath);

        // Make Baguette Client installation script executable
        installationInstructions.appendInstruction(CloudiatorHelper.INSTRUCTION_TYPE.LOG, "Make Baguette Client installation script executable");
        installationInstructions.appendInstruction(CloudiatorHelper.INSTRUCTION_TYPE.CMD, "sudo chmod u+rwx,og-rwx "+installScriptPath);

        // Run Baguette Client installation script
        installationInstructions.appendInstruction(CloudiatorHelper.INSTRUCTION_TYPE.LOG, "Run Baguette Client installation script");
        installationInstructions.appendInstruction(CloudiatorHelper.INSTRUCTION_TYPE.CMD, "sudo "+installScriptPath+" "+baseDownloadUrl+" "+apiKey+" \n");

        // Add client identification and server credentials configuration
        installationInstructions.appendInstruction(CloudiatorHelper.INSTRUCTION_TYPE.LOG, "Add client identification and server credentials configuration");
        installationInstructions.appendInstruction(clientConfPath, clientConfAppend, false);

        // Launch Baguette Client
        installationInstructions.appendInstruction(CloudiatorHelper.INSTRUCTION_TYPE.LOG, "Launch Baguette Client");
        installationInstructions.appendInstruction(CloudiatorHelper.INSTRUCTION_TYPE.CMD, "sudo service baguette-client start");

        return installationInstructions;
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
