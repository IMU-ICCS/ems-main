/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.control;

import eu.melodic.event.control.properties.ControlServiceProperties;

import eu.melodic.models.interfaces.ems.CamelModelRequest;
import eu.melodic.models.interfaces.ems.CamelModelRequestImpl;
import eu.melodic.models.interfaces.ems.MonitorsDataRequest;
import eu.melodic.models.interfaces.ems.MonitorsDataRequestImpl;
import eu.melodic.models.interfaces.ems.MonitorsDataResponse;
import eu.melodic.models.interfaces.ems.MonitorsDataResponseImpl;
import eu.melodic.models.interfaces.ems.Monitor;

import java.util.List;
import java.util.Optional;
import javax.ws.rs.BadRequestException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.cdo.util.ConcurrentAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

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
	@Autowired
	private ControlServiceProperties properties;
	
	// ------------------------------------------------------------------------------------------------------------
	// ESB interfacing methods
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
		String camelModelId = applicationId;
		coordinator.processNewModel( camelModelId, notificationUri );
		log.debug("ControlServiceController.newCamelModel(): Model translation dispatched to a worker thread");
		
		return "OK";
	}
	
	@RequestMapping(value = "/camelModelJson", method = POST,
		consumes=MediaType.APPLICATION_JSON_UTF8_VALUE, 
		produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String newCamelModel(@RequestBody String requestStr) throws ConcurrentAccessException {
		log.info("ControlServiceController.newCamelModel(): Received request: {}", requestStr);
		
		// Use Gson to get model id's from request body (in JSON format)
		com.google.gson.JsonObject jobj = new com.google.gson.Gson().fromJson(requestStr, com.google.gson.JsonObject.class);
		String camelModelId = Optional.ofNullable(jobj.get("camel-model-id")).map(je -> stripQuotes( je.toString() )).orElse(null);
		String cpModelId = Optional.ofNullable(jobj.get("cp-model-id")).map(je -> stripQuotes( je.toString() )).orElse(null);
		log.info("ControlServiceController.newCamelModel(): CAMEL model id from request: {}", camelModelId);
		log.info("ControlServiceController.newCamelModel(): CP model id from request: {}", cpModelId);
		
		// Start translation and component reconfiguration in a worker thread
		coordinator.processNewModel( camelModelId, cpModelId, null );
		log.debug("ControlServiceController.newCamelModel(): Model translation dispatched to a worker thread");
		
		return "OK";
	}
	
//XXX:NEW: Keep it??
	@RequestMapping(value = "/cpModelJson", method = POST,
		consumes=MediaType.APPLICATION_JSON_UTF8_VALUE, 
		produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String newCpModel(@RequestBody String requestStr) throws ConcurrentAccessException {
		log.info("ControlServiceController.newCpModel(): Received request: {}", requestStr);
		
		// Use Gson to get model id's from request body (in JSON format)
		com.google.gson.JsonObject jobj = new com.google.gson.Gson().fromJson(requestStr, com.google.gson.JsonObject.class);
		String cpModelId = Optional.ofNullable(jobj.get("cp-model-id")).map(je -> stripQuotes( je.toString() )).orElse(null);
		log.info("ControlServiceController.newCpModel(): CP model id from request: {}", cpModelId);
		
		// Start CP model processing in a worker thread
		coordinator.processCpModelId( cpModelId, null );
		log.debug("ControlServiceController.newCpModel(): CP Model processing dispatched to a worker thread");
		
		return "OK";
	}
	
	@RequestMapping(value = "/monitors", method = POST)
	public MonitorsDataResponse getSensors(@RequestBody MonitorsDataRequestImpl request) throws ConcurrentAccessException {
		log.info("ControlServiceController.getSensors(): Received request: {}", request);
		
		// Get information from request
		String applicationId = request.getApplicationId();
		String requestUuid = request.getWatermark().getUuid();
		log.info("ControlServiceController.getSensors(): Request info: app-id={}, request-id={}", applicationId, requestUuid);
		
		// Retrieve sensor information
		List<Monitor> sensors = coordinator.getSensorsOfCamelModel(applicationId);
		
		// Prepare response
		MonitorsDataResponse response = new MonitorsDataResponseImpl();
		response.setMonitors(sensors);
		log.info("ControlServiceController.getSensors(): Response: {}", response);
		
		return response;
	}
	
	// ------------------------------------------------------------------------------------------------------------
	// Baguette control methods
	// ------------------------------------------------------------------------------------------------------------
	
	@RequestMapping(value = "/stopBaguette", method = {GET,POST},
		produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String stopBaguette() {
		log.info("ControlServiceController.stopBaguette(): BEGIN");
		
		// Dispatch Baguette stop operation in a worker thread
		coordinator.stopBaguette();
		log.debug("ControlServiceController.stopBaguette(): Baguette stop operation dispatched to a worker thread");
		
		return "OK";
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
	
	@RequestMapping(value = "/ems/status", method = {GET,POST},
		produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String emsStatus() {
		log.info("ControlServiceController.emsStatus(): BEGIN");
		
		log.debug("ControlServiceController.emsStatus(): END");
		return "{}";
	}
	
	@RequestMapping(value = "/ems/topology", method = {GET,POST},
		produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
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
		return (s!=null && s.startsWith("\"") && s.endsWith("\"")) ? s.substring(1,s.length()-1) : s;
	}
}
