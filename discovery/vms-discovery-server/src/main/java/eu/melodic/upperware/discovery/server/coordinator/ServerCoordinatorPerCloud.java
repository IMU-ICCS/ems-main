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
import eu.melodic.upperware.discovery.server.util.CloudiatorUtil;
import eu.melodic.upperware.discovery.server.util.NetUtil;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Properties;
import java.util.Vector;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerCoordinatorPerCloud implements ServerCoordinator {
	private Properties config;
	private Runnable callback;
	private boolean started;
	private int numClients;
	private int phase;
	private Vector<ClientShellCommand> clients;
	private int readyClients;
	//private String brokerCfgIpAddressCmd;
	//private String brokerCfgPortCmd;
	
	private HashMap<String,Vector<ClientShellCommand>> vmGroupsMap;
	
	public ServerCoordinatorPerCloud(Properties config, Runnable callback) {
		this.config = config;
		this.callback = callback;
		this.clients = new Vector<>();
		this.vmGroupsMap = new HashMap<String,Vector<ClientShellCommand>>();
	}
	
	public void start() {
		log.info("ServerCoordinatorPerCloud: START");
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
		
		// Get new client IP address (from session)
		String clientIpAddr = ((InetSocketAddress)c.getSession().getIoSession().getRemoteAddress()).getAddress().getHostAddress();
		int clientPort = ((InetSocketAddress)c.getSession().getIoSession().getRemoteAddress()).getPort();
		log.info("{}--> Client connection : {}:{}", c.getId(), clientIpAddr, clientPort);
		
		//XXX: Useful for debugging
		String clientAddressOverride = config.getProperty("client.address-override","").trim();
		if (!clientAddressOverride.isEmpty()) clientIpAddr = clientAddressOverride;
		
		// Query cloudiator for new client's cloud and location (using IP address)
		String vmGroup;
		try {
			CloudiatorUtil.VmCloudInfo vmInfo = CloudiatorUtil.getInstance().findVmInfoUsingIpAddress(clientIpAddr, true);
			if (vmInfo.location!=null) vmGroup = vmInfo.cloud.getId()+"/"+vmInfo.providerName+" @ "+vmInfo.location.getId()+"/"+vmInfo.location.getName();
			else vmGroup = vmInfo.cloud.getId()+"/"+vmInfo.providerName;
			log.info("{}--> Client VM group : {}", c.getId(), vmGroup);
		} catch (Exception ex) {
			log.error("{}--> Could not retrieve VM info from Cloudiator: {}", c.getId(), ex);
			throw ex;
		}
		
		// Add client in VM group
		Vector<ClientShellCommand> group;
		boolean isFirst;
		int order;
		synchronized (vmGroupsMap) {
			group = vmGroupsMap.get(vmGroup);
			if (group==null) {
				group = new Vector<ClientShellCommand>();
				vmGroupsMap.put(vmGroup, group);
			}
			isFirst = group.size()==0;
			group.add(c);
			order = group.size();
		}
		log.info("{}--> Client is the {} in VM group {}", c.getId(), order, vmGroup);
		if (isFirst) log.info("{}--> Client will become the broker of VM group {}", c.getId(), vmGroup);
		
		//if (phase!=0) return;
		clients.add(c);
		numClients++;
		log.info("ServerCoordinatorPerCloud: register: {} VMs registered", numClients);
		
		if (isFirst) {
			// configure broker
			log.info("BEFORE BROKER CONFIG");
			String thirdLevelAddr = config.getProperty("third-level.address", "").trim();
			if (thirdLevelAddr.isEmpty() || thirdLevelAddr.equalsIgnoreCase("autodetect")) {
				thirdLevelAddr = NetUtil.getIpAddress();
			}
			String thirdLevelPort = config.getProperty("third-level.port", "61616").trim();
			String tplFile = config.getProperty("broker.config.template", "").trim();
			String outFile = config.getProperty("broker.config.file", "").trim();
			if (!thirdLevelAddr.isEmpty() && !thirdLevelPort.isEmpty() && !tplFile.isEmpty() && !outFile.isEmpty()) {
				c.sendToClient( String.format("SET-PARAM %s THIRD_LEVEL_IP_ADDR %s %s", tplFile, thirdLevelAddr, outFile) );
				c.sendToClient( String.format("SET-PARAM %s THIRD_LEVEL_PORT %s %s", outFile, thirdLevelPort, outFile) );
			}
			log.info("AFTER BROKER CONFIG");
			
			// assign broker role
			c.sendToClient("ROLE BROKER");
		} else {
			// configure client
			log.info("BEFORE CLIENT CONFIG");
			String vmGroupBrokerIpAddress = ((InetSocketAddress)group.get(0).getSession().getIoSession().getRemoteAddress()).getAddress().getHostAddress();
			
			String brokerAddr = config.getProperty("broker.address-override", "").trim();
			if (brokerAddr.isEmpty()) brokerAddr = vmGroupBrokerIpAddress;
			String brokerPort = config.getProperty("broker.port", "61616").trim();
			String tplFile = config.getProperty("client.config.template", "").trim();
			String outFile = config.getProperty("client.config.file", "").trim();
			if (!brokerAddr.isEmpty() && !brokerPort.isEmpty() && !tplFile.isEmpty() && !outFile.isEmpty()) {
				c.sendToClient( String.format("SET-PARAM %s BROKER_IP_ADDR %s %s", tplFile, brokerAddr, outFile) );
				c.sendToClient( String.format("SET-PARAM %s BROKER_PORT %s %s", outFile, brokerPort, outFile) );
			}
			log.info("AFTER CLIENT CONFIG");
			
			// assign client role
			c.sendToClient("ROLE CLIENT");
		}
	}
	
	public synchronized void unregister(ClientShellCommand c) {
		if (!started) return;
		//if (phase!=0) return;
		clients.remove(c);
		numClients--;
		log.info("ServerCoordinatorPerCloud: unregister: {} clients registered", numClients);
	}
	
	public synchronized void brokerReady(ClientShellCommand c) {
		// Not used
	}
	
	public synchronized void clientReady(ClientShellCommand c) {
		if (!started) return;
		readyClients++;
		log.info("ServerCoordinatorPerCloud: {} of {} clients are ready", readyClients, numClients);
	}
	
	protected void signalTopologyReady() {
		// Not used
	}
}
