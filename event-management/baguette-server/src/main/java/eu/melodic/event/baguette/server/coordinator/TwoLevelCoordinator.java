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

import java.util.HashMap;
import java.util.Properties;
import java.util.Vector;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TwoLevelCoordinator implements ServerCoordinator {
	private BaguetteServer server;
	private BaguetteServerProperties config;
	private Runnable callback;
	private boolean started;
	private int phase;
	
	private Properties clientConfig;
	private int expectedClients;
	private BaseScenarioSegment DEFAULT_SEGMENT;
	private int prevCount = 0;
	
	public void initialize(BaguetteServer server, Runnable callback) {
		if (started) return;
		this.server = server;
		this.config = server.getConfiguration();
		this.callback = callback;
	}
	
	protected void initSegment() {
		BaseScenarioSegment seg = (BaseScenarioSegment)SegmentFactory.createSegment("DEFAULT-SEGMENT-ID");
		//seg.setTimeoutPeriod(....);
		//seg.setExpectedMembers(....);
		seg.setClientConfig(clientConfig);
		seg.setNoBroker(true);
		//seg.setReadyCallback(this::....);
		//seg.setChangeCallback(this::....);
		seg.start();
		DEFAULT_SEGMENT = seg;
	}
	
	public void start() {
		log.info("TwoLevelCoordinator: START");
		if (started) return;
		started = true;
		
		clientConfig = new Properties();
		clientConfig.setProperty("broker.ip-address", config.getThirdLevelAddress());
		clientConfig.setProperty("broker.port", Integer.toString(config.getThirdLevelPort()));
		expectedClients = config.getNumberOfInstances();
		
		initSegment();
		
		Thread readyMonitor = new Thread(
			new Runnable() {
				public void run() {
					while (countVms()!=expectedClients) {
						try { Thread.sleep(2000); } catch (Exception ex) { return; }
					}
					signalTopologyReady();
				}
			}
		);
		readyMonitor.setDaemon(true);
		readyMonitor.start();
	}
	
	public void stop() {
		if (!started) return;
		started = false;
		DEFAULT_SEGMENT.stop();
		DEFAULT_SEGMENT = null;
	}
	
	public boolean isStarted() { return started; }
	
	public int getPhase() { return phase; }
	
	public synchronized void register(ClientShellCommand c) {
		log.debug("register: BEGIN: {}", c);
		if (!started) return;
		//log.trace("register: Coordinator has been started");
		
		// Add client in segment
		DEFAULT_SEGMENT.register(c);
		log.debug("register: Client registered: {}", c);
	}
	
	public synchronized void unregister(ClientShellCommand c) {
		if (!started) return;
		DEFAULT_SEGMENT.unregister(c);
	}
	
	public synchronized void brokerReady(ClientShellCommand c) {
		// Not used
	}
	
	public synchronized void clientReady(ClientShellCommand c) {
		if (!started) return;
		DEFAULT_SEGMENT.clientReady(c);
	}
	
	protected int countVms() {
		int count = DEFAULT_SEGMENT.countReadyClients();
		log.trace("countVms: Total num of VMs: {}", count);
		if (prevCount!=count) {
			log.info("countVms: Total num of VMs: {}", count);
		}
		prevCount = count;
		return count;
	}
	
	protected void signalTopologyReady() {
		log.debug("signalTopologyReady: Topology is ready");
		if (callback!=null) {
			log.debug("signalTopologyReady: Invoking callback");
			callback.run();
		}
	}
}
