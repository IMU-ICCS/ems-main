/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.discovery.server;

public interface ServerCoordinator {
	public abstract int getPhase();
	public abstract void register(ClientShellCommand c);
	public abstract void unregister(ClientShellCommand c);
	public abstract void brokerReady(ClientShellCommand c);
	public abstract void clientReady(ClientShellCommand c);
	public abstract void start();
	public abstract void stop();
}
