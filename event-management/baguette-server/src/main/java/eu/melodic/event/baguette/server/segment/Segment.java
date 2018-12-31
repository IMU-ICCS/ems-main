/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.baguette.server.segment;

import eu.melodic.event.baguette.server.ClientShellCommand;

import java.util.function.Function;

public interface Segment {
    public abstract String getId();

    public abstract String getState();

    public abstract void start();

    public abstract void stop();

    public abstract void register(ClientShellCommand c);

    public abstract void unregister(ClientShellCommand c);

    public abstract void brokerReady(ClientShellCommand broker);

    public abstract void clientReady(ClientShellCommand client);

    public abstract Function getReadyCallback();

    public abstract void setReadyCallback(Function f);

    public abstract Function getChangeCallback();

    public abstract void setChangeCallback(Function f);
}