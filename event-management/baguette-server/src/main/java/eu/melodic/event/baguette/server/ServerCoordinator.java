/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.baguette.server;

import eu.melodic.event.brokercep.cep.FunctionDefinition;
import java.util.Map;
import java.util.Set;
import java.util.Properties;

public interface ServerCoordinator {
	//public abstract void initialize(Properties config, Runnable callback);
	public abstract void initialize(BaguetteServer server, Runnable callback);
	public abstract int getPhase();
	public abstract void register(ClientShellCommand c);
	public abstract void unregister(ClientShellCommand c);
	public abstract void brokerReady(ClientShellCommand c);
	public abstract void clientReady(ClientShellCommand c);
	public abstract void start();
	public abstract void stop();
	
	default public void sendGroupingConfigurations(Properties cfg, ClientShellCommand c, BaguetteServer server) {
		for (String grouping : server.getGroupingNames()) {
			GroupingConfiguration gc = new GroupingConfiguration(grouping, cfg, server);
			c.sendGroupingConfiguration(grouping, gc);
		}
	}
}
