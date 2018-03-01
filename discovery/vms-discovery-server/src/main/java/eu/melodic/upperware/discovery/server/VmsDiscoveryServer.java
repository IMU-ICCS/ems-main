/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.discovery.server;

import eu.melodic.upperware.discovery.server.coordinator.*;

import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

/**
 * Event Management Server
 *
 */
@Slf4j
public class VmsDiscoveryServer
{
	public static void main( String[] args ) throws IOException
	{
		Properties config = loadConfig("/server.properties");
		int numOfVms = Integer.parseInt(config.getProperty("num-of-vms","1"));;
		long registrationWindow = Long.parseLong(config.getProperty("registration-window","60000"));;
		
		Properties credentials = loadConfig("/credentials.properties");
		
		prepareVmsClientInstallationFile(config, "/vms-server.credentials.tpl", "./conf/vms-server.credentials");
		prepareVmsClientInstallationFile(config, "/install.sh.tpl", "./conf/install.sh");
		prepareVmsClientInstallationFile(config, "/install-local.sh.tpl", "./conf/install-local.sh");
		
		//ServerCoordinator coordinator = new ServerCoordinatorWaitAll(numOfVms, 
		//ServerCoordinator coordinator = new ServerCoordinatorTimeWin(registrationWindow,
		ServerCoordinator coordinator = new ServerCoordinatorClientsOnly(config, 
			new Runnable() {
				public void run() {
					log.info("*****************************");
					log.info("****  TOPOLOGY IS READY  ****");
					log.info("*****************************");
				}
			}
		);
		
		Sshd server = new Sshd();
		server.start(config, coordinator, credentials);
		
		// check if interactive flag is present
		if (args.length>0 && args[0].trim().equalsIgnoreCase("-i")) {
			// Wait here until ENTER is hit in server console
			System.out.println( "\nPress enter to exit" );
			try { System.in.read(); } catch (Exception ex) {}
		} else {
			try { Object noexit = new Object(); synchronized (noexit) { noexit.wait(); } log.info("Server notified to exit"); } catch (InterruptedException ex) { log.info("Server interrupted and exits"); }
		}
		
		server.stop();
	}
	
	protected static Properties loadConfig(String configFile) throws IOException {
		Properties config = new Properties();
		try (InputStream in = VmsDiscoveryServer.class.getResourceAsStream(configFile)) {
			config.load(in);
		}
		return config;
	}
	
	protected static void prepareVmsClientInstallationFile(Properties config, String tplFile, String outFile) throws FileNotFoundException, IOException {
		//try (Scanner scanner = new Scanner(new File(tplFile))) {
		try (Scanner scanner = new Scanner(VmsDiscoveryServer.class.getResourceAsStream(tplFile))) {
			// Load template file
			String contents = scanner.useDelimiter("\\A").next();
			
			// Replace placeholders with real values
			contents = contents.replace("VMS_CLIENT_DOWNLOAD_URL", config.getProperty("VMS_CLIENT_DOWNLOAD_URL", "http://localhost:8080/melodic"));
			contents = contents.replace("VMS_SERVER_HOST", config.getProperty("VMS_SERVER_HOST", "localhost"));
			contents = contents.replace("VMS_SERVER_PORT", config.getProperty("VMS_SERVER_PORT", "22"));
			contents = contents.replace("VMS_SERVER_PUBKEY", config.getProperty("VMS_SERVER_PUBKEY", ""));
			contents = contents.replace("VMS_SERVER_FINGERPRINT", config.getProperty("VMS_SERVER_FINGERPRINT", ""));
			contents = contents.replace("VMS_SERVER_USERNAME", config.getProperty("VMS_SERVER_USERNAME", ""));
			contents = contents.replace("VMS_SERVER_PASSWORD", config.getProperty("VMS_SERVER_PASSWORD", ""));
			contents = contents.replace("VMS_SERVER_CREDENTIALS_FILE", config.getProperty("VMS_SERVER_CREDENTIALS_FILE", ""));
			
			// Save to out file
			try (FileWriter out = new FileWriter(outFile)) {
				out.write( contents );
			}
		}
	}
}
