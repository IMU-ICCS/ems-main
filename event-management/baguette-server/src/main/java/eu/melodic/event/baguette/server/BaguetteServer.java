/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.baguette.server;

import eu.melodic.event.baguette.server.coordinator.*;
import eu.melodic.event.baguette.server.properties.BaguetteServerCredentials;
import eu.melodic.event.baguette.server.properties.BaguetteServerProperties;
import eu.melodic.event.brokercep.cep.FunctionDefinition;

import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Baguette Server
 *
 */
@Slf4j
@Service
public class BaguetteServer
{
	@Autowired
	private BaguetteServerProperties config;
	@Autowired
	private BaguetteServerCredentials credentials;
	
	private Sshd server;
	
	private Map<String,Set<String>> groupingTopicsMap;
	private Map<String,Map<String,Set<String>>> groupingRulesMap;
	private Map<String,Map<String,Set<String>>> topicConnections;
	private Map<String,Double> constants;
	private Set<FunctionDefinition> functionDefinitions;
	private String upperwareGrouping;
	
	// Configuration getter methods
	public Set<String> getGroupingNames() {
		Set<String> groupings = new HashSet<String>();
		groupings.addAll( groupingTopicsMap.keySet() );
		groupings.addAll( groupingRulesMap.keySet() );
		groupings.addAll( topicConnections.keySet() );
		// remove upperware grouping (e.g. GLOBAL)
		groupings.remove(upperwareGrouping);
		return groupings;
	}
	
	public BaguetteServerProperties getConfiguration() {
		return config;
	}
	public Set<String> getTopicsForGrouping(String grouping) {
		return groupingTopicsMap.get(grouping);
	}
	public Map<String,Set<String>> getRulesForGrouping(String grouping) {
		return groupingRulesMap.get(grouping);
	}
	public Map<String,Set<String>> getTopicConnectionsForGrouping(String grouping) {
		return topicConnections.get(grouping);
	}
	public Map<String,Double> getConstants() {
		return constants;
	}
	public Set<FunctionDefinition> getFunctionDefinitions() {
		return functionDefinitions;
	}
	
	public BaguetteServerCredentials getCredentials() {
		return credentials;
	}
	
	// Server control methods
	public synchronized void startServer(ServerCoordinator coordinator) throws IOException {
		if (server==null) {
			log.info("BaguetteServer.startServer(): Starting SSH server instance...");
			Sshd server = new Sshd();
			server.start(config, coordinator, credentials);
			this.server = server;
			log.info("BaguetteServer.startServer(): Starting SSH server instance... done");
		} else {
			log.warn("BaguetteServer.startServer(): An SSH server instance is already running");
		}
	}
	
	public synchronized void stopServer() throws IOException {
		if (server!=null) {
			log.info("BaguetteServer.setServerConfiguration(): stopping running instance of SSH server...");
			server.stop();
			this.server = null;
			log.info("BaguetteServer.setServerConfiguration(): stopping running instance of SSH server... done");
		} else {
			log.warn("BaguetteServer.stop(): No SSH server instance is running");
		}
	}
	
	public synchronized void restartServer(ServerCoordinator coordinator) throws IOException {
		stopServer();
		startServer(coordinator);
	}
	
	public synchronized boolean isServerRunning() {
		return server!=null;
	}
	
	// Topology configuration methods
	public synchronized void setTopologyConfiguration(
				Map<String,Set<String>> G2T, 
				Map<String,Map<String,Set<String>>> G2R, 
				Map<String,Map<String,Set<String>>> topicConnections, 
				Map<String,Double> constants,
				Set<FunctionDefinition> functionDefinitions,
				String upperwareGrouping ) 
	throws IOException 
	{
		log.info("BaguetteServer.setTopologyConfiguration(): BEGIN");
		log.info("BaguetteServer.setTopologyConfiguration(): ARGS: Grouping-to-Topics (G2T): {}", G2T);
		log.info("BaguetteServer.setTopologyConfiguration(): ARGS: Grouping-to-Rules (G2R): {}", G2R);
		log.info("BaguetteServer.setTopologyConfiguration(): ARGS: Topic-Connections: {}", topicConnections);
		log.info("BaguetteServer.setTopologyConfiguration(): ARGS: Constants: {}", constants);
		log.info("BaguetteServer.setTopologyConfiguration(): ARGS: Function-Definitions: {}", functionDefinitions);
		log.info("BaguetteServer.setTopologyConfiguration(): ARGS: Upperware-grouping: {}", upperwareGrouping);
		
		// Stop any running instance of SSH server
		stopServer();
		
		// Set new configuration
		this.groupingTopicsMap = G2T;
		this.groupingRulesMap = G2R;
		this.topicConnections = topicConnections;
		this.constants = constants;
		this.functionDefinitions = functionDefinitions;
		this.upperwareGrouping = upperwareGrouping;
		
		// load credentials
//XXX: TODO: read using Spring-boot properties
		log.info("BaguetteServer.setTopologyConfiguration(): Loading Baguette server configuration...");
		Properties cred = loadConfig("/credentials.properties");
		this.credentials.setCredentials((Map)cred);
		
		log.info("BaguetteServer.setTopologyConfiguration(): Baguette server configuration: {}", config);
		log.info("BaguetteServer.setTopologyConfiguration(): Server credentials count: {}", credentials.getCredentials().size());
		
		// Initialize server coordinator
		log.info("BaguetteServer.setTopologyConfiguration(): Initializing Baguette protocol coordinator...");
		ServerCoordinator coordinator = createServerCoordinator( config.getCoordinatorClass() );
		log.info("BaguetteServer.setTopologyConfiguration(): Coordinator: {}", coordinator.getClass().getName());
		coordinator.initialize(
			this,
//XXX: TODO: implement a useful EP Network callback, capable to notify EMS when EPN is ready
			new Runnable() {
				public void run() {
					log.info("****************************************");
					log.info("****  MONITORING TOPOLOGY IS READY  ****");
					log.info("****************************************");
				}
			}
		);
		
		// Start an new instance of SSH server
		startServer(coordinator);
		
		log.info("BaguetteServer.setTopologyConfiguration(): END");
	}
	
	protected static ServerCoordinator createServerCoordinator(String classStr) {
		try {
			return (ServerCoordinator) Class.forName(classStr).newInstance();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
//XXX: TODO: replace this method with Spring-boot properties
	protected static Properties loadConfig(String configFile) throws IOException {
		Properties config = new Properties();
		try {
			try (InputStream in = new FileInputStream( new File(configFile) )) {
				config.load(in);
			}
		} catch (FileNotFoundException ex) {
			try (InputStream in = BaguetteServer.class.getResourceAsStream(configFile)) {
				if (in==null) throw ex;
				config.load(in);
			}
		}
		return config;
	}
	
	public void sendToActiveClients(String command) {
		server.sendToActiveClients(command);
	}
	
	public void sendToClient(String clientId, String command) {
		server.sendToClient(clientId, command);
	}
	
//XXX: TODO: change 'main()' to make use of 'setTopologyConfiguration()'
/*	public static void main( String[] args ) throws IOException
	{
		// load configuration
		Properties config = loadConfig("/server.properties");
		Properties credentials = loadConfig("/credentials.properties");
		
		// prepapre client installation files
//		prepareClientInstallationFile(config, "/baguette-server.credentials.tpl", "./conf/baguette-server.credentials");
//		prepareClientInstallationFile(config, "/install.sh.tpl", "./conf/install.sh");
//		prepareClientInstallationFile(config, "/install-local.sh.tpl", "./conf/install-local.sh");
		
		// check if a plan has been specified
		int nextArg = 0;
		Properties planConfig = null;
		if (args.length>0 && !args[0].trim().equalsIgnoreCase("-i")) {
			planConfig = loadConfig(args[0]);
			if (planConfig!=null) config.putAll(planConfig);
			nextArg++;
		}
		log.info("Boot-time config: {}", config);
		
		//ServerCoordinator coordinator = new ServerCoordinatorWaitAll(config, 
		//ServerCoordinator coordinator = new ServerCoordinatorTimeWin(config,
		//ServerCoordinator coordinator = new ServerCoordinatorClientsOnly(config, 
		//ServerCoordinator coordinator = new ServerCoordinatorPerCloud(config, 
		//ServerCoordinator coordinator = new ThreeLevelSegmentCoordinator(config,
		//ServerCoordinator coordinator = new TwoLevelCoordinator(config,
		ServerCoordinator coordinator = createServerCoordinator(config.getProperty("coordinator.class", "eu.melodic.event.baguette.server.coordinator.NoopCoordinator"));
		coordinator.initialize(
			config,
			new Runnable() {
				public void run() {
					log.info("****************************************");
					log.info("****  MONITORING TOPOLOGY IS READY  ****");
					log.info("****************************************");
				}
			}
		);
		
		Sshd server = new Sshd();
		server.start(config, coordinator, credentials);
		
		// check if interactive flag is present
		if (args.length>nextArg && args[nextArg].trim().equalsIgnoreCase("-i")) {
			// Wait here until ENTER is hit in server console
			System.out.println( "\nPress enter to exit" );
			try { System.in.read(); } catch (Exception ex) {}
		} else {
			try { Object noexit = new Object(); synchronized (noexit) { noexit.wait(); } log.info("Server notified to exit"); } catch (InterruptedException ex) { log.info("Server interrupted and exits"); }
		}
		
		server.stop();
	}*/
	
//XXX: TODO: this method should be replaced with a template engine, and not require the creation of client config. files
/*	protected static void prepareClientInstallationFile(Properties config, String tplFile, String outFile) throws FileNotFoundException, IOException {
		log.debug("BaguetteServer.prepareClientInstallationFile(): BEGIN: template={}, output={}, config={}", tplFile, outFile, config);
		//try (Scanner scanner = new Scanner(new File(tplFile))) {
		try (Scanner scanner = new Scanner(BaguetteServer.class.getResourceAsStream(tplFile))) {
			// Load template file
			String contents = scanner.useDelimiter("\\A").next();
			
			// Replace placeholders with real values
			contents = contents.replace("BAGUETTE_CLIENT_DOWNLOAD_URL", config.getProperty("BAGUETTE_CLIENT_DOWNLOAD_URL", "http://localhost:8080/melodic"));
			contents = contents.replace("BAGUETTE_SERVER_HOST", config.getProperty("BAGUETTE_SERVER_HOST", "localhost"));
			contents = contents.replace("BAGUETTE_SERVER_PORT", config.getProperty("BAGUETTE_SERVER_PORT", "22"));
			contents = contents.replace("BAGUETTE_SERVER_PUBKEY", config.getProperty("BAGUETTE_SERVER_PUBKEY", ""));
			contents = contents.replace("BAGUETTE_SERVER_FINGERPRINT", config.getProperty("BAGUETTE_SERVER_FINGERPRINT", ""));
			contents = contents.replace("BAGUETTE_SERVER_USERNAME", config.getProperty("BAGUETTE_SERVER_USERNAME", ""));
			contents = contents.replace("BAGUETTE_SERVER_PASSWORD", config.getProperty("BAGUETTE_SERVER_PASSWORD", ""));
			contents = contents.replace("BAGUETTE_SERVER_CREDENTIALS_FILE", config.getProperty("BAGUETTE_SERVER_CREDENTIALS_FILE", ""));
			
			// Save to out file
			try (FileWriter out = new FileWriter(outFile)) {
				log.trace("BaguetteServer.prepareClientInstallationFile(): Writing to file {}: contents={}", outFile, contents);
				out.write( contents );
			}
		}
		log.debug("BaguetteServer.prepareClientInstallationFile(): END: template={}, output={}", tplFile, outFile);
	}*/
}
