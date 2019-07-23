package de.cas.dcsresearch.melodic.activemqtorest.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.cas.dcsresearch.melodic.activemqtorest.activemq.ActiveMqStatisticHolder;
import de.cas.dcsresearch.melodic.activemqtorest.objects.ActiveMqStatistics;
import de.cas.dcsresearch.melodic.activemqtorest.objects.PingResult;

@RestController
public class HealthCheckController {

	@Autowired
	private ActiveMqStatisticHolder activeMqStatisticHolder;

	@RequestMapping(value = "/ping", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
	public PingResult getPingPong() {
		return PingResult.create();
	}

	@RequestMapping(value = "/statistics", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
	public ActiveMqStatistics getActiveMqStatistics() {
		return activeMqStatisticHolder.getActiveMqStatistics();
	}

}