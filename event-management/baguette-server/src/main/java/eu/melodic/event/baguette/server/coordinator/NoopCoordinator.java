/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.baguette.server.coordinator;

import eu.melodic.event.baguette.server.*;
import eu.melodic.event.baguette.server.properties.BaguetteServerProperties;
import eu.melodic.event.baguette.server.segment.*;
import eu.melodic.event.baguette.server.util.CloudiatorUtil;
import eu.melodic.event.baguette.server.util.NetUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NoopCoordinator implements ServerCoordinator {
	private BaguetteServer server;
	private BaguetteServerProperties config;
	private Runnable callback;
	private boolean started;
	
	@Override
	public void initialize(BaguetteServer server, Runnable callback) {
		if (started) return;
		log.info("NoopCoordinator: initialize");
		this.server = server;
		this.config = server.getConfiguration();
		this.callback = callback;
	}
	
	@Override
	public void start() {
		if (started) return;
		log.info("NoopCoordinator: start");
		started = true;
		
		if (callback!=null) {
			log.info("NoopCoordinator: start: Invoking callback");
			callback.run();
		}
	}
	
	@Override
	public void stop() {
		if (!started) return;
		log.info("NoopCoordinator: stop");
		started = false;
	}
	
	public boolean isStarted() { return started; }
	
	@Override
	public int getPhase() { return -1; }
	
	@Override
	public synchronized void register(ClientShellCommand c) {
		if (!started) return;
		log.info("NoopCoordinator: register: {}", c);
		
//XXX:DEL: TEST....
		test(c);
	}
	
	public synchronized void test(ClientShellCommand c) {
//XXX:DEL: TEST....
		// prepare configuration
		java.util.Properties cfg = new java.util.Properties();
		cfg.setProperty("GLOBAL", server.getUpperwareBrokerUrl());	// <-- XXX:SOS: check SCHEME (ssl, tcp) in Upperware Broker-CEP configuration
		cfg.setProperty("PER_CLOUD", "ssl://localhost:61614");		// <-- XXX:SOS: check SCHEME (ssl, tcp)
		
		// prepare Broker-CEP configuration
		log.info("NoopCoordinator.test(): --------------------------------------------------");
		log.info("NoopCoordinator.test(): Sending grouping configurations...");
		sendGroupingConfigurations(cfg, c, server);
		log.info("NoopCoordinator.test(): Sending grouping configurations... done");
		
		// Set active grouping and send an event
		String grouping = "PER_INSTANCE";
		try { Thread.sleep(500); } catch (Exception ex) {}
		log.info("NoopCoordinator.test(): --------------------------------------------------");
		log.info("NoopCoordinator.test(): Setting active grouping: {}", grouping );
		c.setActiveGrouping(grouping);
		
		try { Thread.sleep(5000); } catch (Exception ex) {}
		log.info("NoopCoordinator.test(): --------------------------------------------------");
		//c.sendCommand("SEND-EVENT tcp://localhost:61616 CPURawMetricContext "+mv);
		//c.sendCommand("SEND-LOCAL-EVENT MySENSOR "+mv);
		//c.sendCommand("GENERATE-EVENTS-START MySENSOR 2000 100 500");
	}
	
	
	
	@Override
	public synchronized void unregister(ClientShellCommand c) {
		if (!started) return;
		log.info("NoopCoordinator: unregister: {}", c);
	}
	
	@Override
	public synchronized void brokerReady(ClientShellCommand c) {
		if (!started) return;
		log.info("NoopCoordinator: brokerReady: {}", c);
	}
	
	@Override
	public synchronized void clientReady(ClientShellCommand c) {
		if (!started) return;
		log.info("NoopCoordinator: clientReady: {}", c);
	}
}
