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

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
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
		
		//ServerCoordinator coordinator = new ServerCoordinatorWaitAll(numOfVms, 
		//ServerCoordinator coordinator = new ServerCoordinatorTimeWin(registrationWindow,
		ServerCoordinator coordinator = new ServerCoordinatorClientsOnly(
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
		
		// Wait here until ENTER is hit in server console
		System.out.println( "\nPress enter to exit" );
		try { System.in.read(); } catch (Exception ex) {}
		
		server.stop();
	}
	
	protected static Properties loadConfig(String configFile) throws IOException {
		Properties config = new Properties();
		try (InputStream in = VmsDiscoveryServer.class.getResourceAsStream(configFile)) {
			config.load(in);
		}
		return config;
	}
}
