/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.brokercep.event;

import eu.melodic.event.brokercep.BrokerCepService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class EventGenerator implements Runnable {
	private final BrokerCepService brokerCepService;
	private final String topicName;
	private final long interval;
	private final double lowerValue;
	private final double upperValue;
	
	private transient boolean keepRunning;
	
	public void start() {
		if (keepRunning) return;
		Thread runner = new Thread(this);
		runner.setDaemon(true);
		keepRunning = true;
		runner.start();
	}
	
	public void stop() {
		keepRunning = false;
	}
	
	public void run() {
		log.info("EventGenerator.run(): Start sending events: event-generator: {}", this);
		
		String brokerUrl = brokerCepService.getBrokerCepProperties().getBrokerUrl();
		double valueRangeWidth = upperValue - lowerValue;
		
		while (keepRunning) {
			try {
				double newValue = Math.random()*valueRangeWidth + lowerValue;
				EventMap event = new EventMap(newValue, 1, System.currentTimeMillis());
				log.info("EventGenerator.run(): Sending event: {}", event);
				brokerCepService.publishEvent( brokerUrl, topicName, event );
				log.info("EventGenerator.run(): Event sent: {}", event);
			} catch (Exception ex) {
				log.warn("EventGenerator.run(): WHILE-EXCEPTION: {}", ex);
			}
			// sleep for 'interval' ms
			try {
				Thread.sleep(interval);
			} catch (InterruptedException ex) {
				log.warn("EventGenerator.run(): Sleep interrupted");
			}
		}
		
		log.info("EventGenerator.run(): Stop sending events: event-generator: {}", this);
	}
}