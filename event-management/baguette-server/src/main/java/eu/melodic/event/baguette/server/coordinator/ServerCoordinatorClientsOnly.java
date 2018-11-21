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
import eu.melodic.event.baguette.server.util.NetUtil;

//import java.util.Properties;
import java.util.Vector;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerCoordinatorClientsOnly implements ServerCoordinator {
	private BaguetteServer server;
	private BaguetteServerProperties config;
	private Runnable callback;
	private boolean started;
	private int numClients;
	private int phase;
	private Vector<ClientShellCommand> clients;
	private int readyClients;
	private String brokerCfgIpAddressCmd;
	private String brokerCfgPortCmd;
	
	public void initialize(BaguetteServer server, Runnable callback) {
		this.server = server;
		this.config = server.getConfiguration();
		this.callback = callback;
		this.clients = new Vector<>();
		
		String addr = config.getBrokerAddress();
		String port = Integer.toString( config.getBrokerPort() );
		String tplFile = config.getBrokerConfigTemplate();
		String outFile = config.getBrokerConfigFile();
		if (addr.equalsIgnoreCase("autodetect")) addr = NetUtil.getIpAddress();
		if (port.isEmpty()) port = "61616";
		if (!addr.isEmpty() && !port.isEmpty() && !tplFile.isEmpty() && !outFile.isEmpty()) {
			brokerCfgIpAddressCmd = String.format("SET-PARAM %s BROKER_IP_ADDR %s %s", tplFile, addr, outFile);
			brokerCfgPortCmd = String.format("SET-PARAM %s BROKER_PORT %s %s", outFile, port, outFile);
		}
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
