/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.brokercep.test;

import eu.melodic.event.brokercep.BrokerCepService;
import eu.melodic.event.brokercep.cep.*;
import eu.melodic.event.brokercep.test.BrokerCepFakeEventsProducer;
import static eu.melodic.event.brokercep.test.BrokerCepFakeEventsProducer.PayloadGenerator;
import static eu.melodic.event.brokercep.test.BrokerCepFakeEventsProducer.PayloadMap;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name="run-broker-cep-test", havingValue="BrokerCepServiceTest3", matchIfMissing=false)
@Slf4j
public class BrokerCepServiceTest3 implements CommandLineRunner {
	
	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private BrokerCepService brokerCep;
	
	private CepService cepService;
	
	@Override
	public void run(String...args) throws Exception {
		log.info("Starting Broker-CEP test...");
		
		// Register new event types (aka new queues or topics)
		brokerCep.addEventType("TemperatureEvent", TemperatureEvent.class );
		brokerCep.addEventType("RadiationEvent", RadiationEvent.class );
		brokerCep.addEventType("WindEvent", WindEvent.class );
		
		// Register EPL statements in CEP engine
		cepService = brokerCep.getCepService();
		registerEplStatements();
		
		// Start fake event producers
		try {
			log.info("Waiting for 5 seconds...");
			java.util.concurrent.TimeUnit.SECONDS.sleep(5);
			
			BrokerCepFakeEventsProducer generator1 = applicationContext.getBean(BrokerCepFakeEventsProducer.class);
			generator1.sendEvents("TemperatureEvent", new PayloadGenerator() { 	public Object payloadAsObject(int it) { return new TemperatureEvent(); } }, 100, 5000);
			
			BrokerCepFakeEventsProducer generator2 = applicationContext.getBean(BrokerCepFakeEventsProducer.class);
			generator2.sendEvents("RadiationEvent", new PayloadGenerator() { public Object payloadAsObject(int it) { return new RadiationEvent(); } }, 500, 1000);
			
			BrokerCepFakeEventsProducer generator3 = applicationContext.getBean(BrokerCepFakeEventsProducer.class);
			generator3.sendEvents("WindEvent", new PayloadGenerator() { public Object payloadAsObject(int it) { return new WindEvent(); } }, 50, 10000);
		} catch (InterruptedException ex) {}
		
		log.info("Broker-CEP test started...");
	}
	
	protected void registerEplStatements() {
		log.info("Adding EPL statements & subscribers...");
		
		cepService.addStatementSubscriber(new StatementSubscriber() {
			public String getName() { return "MonitorEventSubscriber"; }
			public String getStatement() {
				return "select avg(temperature) as avg_val from TemperatureEvent.win:time_batch(5 sec)";
			}
			public void update(java.util.Map<String, Double> eventMap) {
				// average temperature over 10 secs
				Double avg = (Double) eventMap.get("avg_val");
				log.info("- [MONITOR] Average Temp = {}", avg);
			}
		});
		cepService.addStatementSubscriber(new StatementSubscriber() {
			private static final int WARNING_EVENT_THRESHOLD = 400;
			
			public String getName() { return "WarningEventSubscriber"; }
			public String getStatement() {
				return 
					  "select * from TemperatureEvent "
					+ "match_recognize ( "
					+ "       measures A as temp1, B as temp2 "
					+ "       pattern (A B) " 
					+ "       define " 
					+ "               A as A.temperature > " + WARNING_EVENT_THRESHOLD + ", "
					+ "               B as B.temperature > " + WARNING_EVENT_THRESHOLD + ")";
			}
			public void update(java.util.Map<String, TemperatureEvent> eventMap) {
				// 1st Temperature in the Warning Sequence
				TemperatureEvent temp1 = (TemperatureEvent) eventMap.get("temp1");
				// 2nd Temperature in the Warning Sequence
				TemperatureEvent temp2 = (TemperatureEvent) eventMap.get("temp2");
				log.info("- [WARNING] : TEMPERATURE SPIKE DETECTED = {},{}", temp1, temp2);
			}
		});
		cepService.addStatementSubscriber(new StatementSubscriber() {
			private static final int CRITICAL_EVENT_THRESHOLD = 100;
			private static final double CRITICAL_EVENT_MULTIPLIER = 1.5;
			
			public String getName() { return "CriticalEventSubscriber"; }
			public String getStatement() {
				return 
					  "select * from TemperatureEvent "
					+ "match_recognize ( "
					+ "       measures A as temp1, B as temp2, C as temp3, D as temp4 "
					+ "       pattern (A B C D) " 
					+ "       define "
					+ "               A as A.temperature > " + CRITICAL_EVENT_THRESHOLD + ", "
					+ "               B as (A.temperature < B.temperature), "
					+ "               C as (B.temperature < C.temperature), "
					+ "               D as (C.temperature < D.temperature) and D.temperature > (A.temperature * " + CRITICAL_EVENT_MULTIPLIER + ")" + ")";
			}
			public void update(java.util.Map<String, TemperatureEvent> eventMap) {
				// 1st Temperature in the Critical Sequence
				TemperatureEvent temp1 = (TemperatureEvent) eventMap.get("temp1");
				// 2nd Temperature in the Critical Sequence
				TemperatureEvent temp2 = (TemperatureEvent) eventMap.get("temp2");
				// 3rd Temperature in the Critical Sequence
				TemperatureEvent temp3 = (TemperatureEvent) eventMap.get("temp3");
				// 4th Temperature in the Critical Sequence
				TemperatureEvent temp4 = (TemperatureEvent) eventMap.get("temp4");
				log.warn("* [ALERT] : CRITICAL EVENT DETECTED! ");
				log.warn("* {} > {} > {} > {}", temp1, temp2, temp3, temp4);
			}
		});
		cepService.addStatementSubscriber(new StatementSubscriber() {
			public String getName() { return "LatestEventValuesSubscriber"; }
			public String getStatement() {
				return "select temperature, radiation, speed, direction from TemperatureEvent.std:lastevent(), RadiationEvent.std:lastevent(), WindEvent.std:lastevent()";
			}
			public void update(java.util.Map<String, Integer> eventMap) {
				// latest values
				Integer temp = (Integer) eventMap.get("temperature");
				Integer rad  = (Integer) eventMap.get("radiation");
				Integer speed= (Integer) eventMap.get("speed");
				Integer dir  = (Integer) eventMap.get("direction");
				log.info("- [!! TEST !!] Temp={}, Rad={}, Speed={}, Dir={}", temp, rad, speed, dir);
			}
		});
		
	}
}