/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.discovery.server.coordinator;

import eu.melodic.upperware.discovery.server.*;

import java.util.Vector;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerCoordinatorClientsOnly implements ServerCoordinator {
	private Runnable callback;
	private boolean started;
	private int numClients;
	private int phase;
	private Vector<ClientShellCommand> clients;
	private int readyClients;
	private String brokerCfgIpAddressCmd = "SET-PARAM bin/broker.cfg.tpl BROKER_IP_ADDR localhost bin/broker.cfg";
	private String brokerCfgPortCmd = "SET-PARAM bin/broker.cfg BROKER_PORT 61616 bin/broker.cfg";
	
	public ServerCoordinatorClientsOnly(Runnable callback) {
		this.callback = callback;
		this.clients = new Vector<>();
	}
	
	public void start() {
		log.info("ServerCoordinatorClientsOnly: START");
		started = true;
	}
	
	public void stop() {
		started = false;
	}
	
	public boolean isStarted() { return started; }
	
	public int getPhase() { return phase; }
	
	public synchronized void register(ClientShellCommand c) {
		log.info("BEGIN register");
		if (!started) return;
		log.info("COORDINATOR HAS BEEN STARTED");
		//if (phase!=0) return;
		clients.add(c);
		numClients++;
		
		// configure client
		log.info("BEFORE CONFIG");
		c.sendToClient( brokerCfgIpAddressCmd );
		c.sendToClient( brokerCfgPortCmd );
		log.info("AFTER CONFIG");
		
		// assign client role
		c.sendToClient("ROLE CLIENT");
		log.info("ServerCoordinatorClientsOnly: register: {} clients registered", numClients);
	}
	
	public synchronized void unregister(ClientShellCommand c) {
		if (!started) return;
		//if (phase!=0) return;
		clients.remove(c);
		numClients--;
		log.info("ServerCoordinatorClientsOnly: unregister: {} clients registered", numClients);
	}
	
	public synchronized void brokerReady(ClientShellCommand c) {
		// Not used
	}
	
	public synchronized void clientReady(ClientShellCommand c) {
		if (!started) return;
		readyClients++;
		log.info("ServerCoordinatorClientsOnly: {} of {} clients are ready", readyClients, numClients);
	}
	
	protected void signalTopologyReady() {
		// Not used
	}
}
