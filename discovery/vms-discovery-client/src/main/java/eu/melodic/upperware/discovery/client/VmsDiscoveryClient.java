/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.discovery.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;

/**
 * Custom SSH client
 *
 */
@Slf4j
public class VmsDiscoveryClient
{
	public static void main( String[] args ) throws IOException
	{
		log.trace("** VmsDiscoveryClient **");
		Properties config = loadConfig("/client.properties");
		Sshc client = new Sshc(config);
		log.trace("VmsDiscoveryClient: Calling start()");
		client.start(true);
		log.trace("VmsDiscoveryClient: Calling run()");
		client.run();
		log.trace("VmsDiscoveryClient: Calling stop()");
		client.stop();
		log.trace("VmsDiscoveryClient: Exiting");
	}
	
	protected static Properties loadConfig(String configFile) throws IOException {
		Properties config = new Properties();
		try (InputStream in = VmsDiscoveryClient.class.getResourceAsStream(configFile)) {
			config.load(in);
		}
		return config;
	}
}
