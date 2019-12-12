package eu.melodic.upperware.activemqtorest.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.melodic.upperware.activemqtorest.activemq.MqAdapterStatusHolder;
import eu.melodic.upperware.activemqtorest.entry.MqAdapterStatus;
import eu.melodic.upperware.activemqtorest.entry.PingResult;

@RestController
public class HealthCheckController {

	@Autowired
	private MqAdapterStatusHolder mqAdapterStatusHolder;

	@RequestMapping(value = "/ping", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
	public PingResult getPingPong() {
		return PingResult.create();
	}

	@RequestMapping(value = "/status", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
	public MqAdapterStatus getMqAdapterStatus() {
		return mqAdapterStatusHolder.getMqAdapterStatus();
	}

}