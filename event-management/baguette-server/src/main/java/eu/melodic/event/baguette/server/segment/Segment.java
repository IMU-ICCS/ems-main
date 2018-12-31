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
    String getId();

    String getState();

    void start();

    void stop();

    void register(ClientShellCommand c);

    void unregister(ClientShellCommand c);

    void brokerReady(ClientShellCommand broker);

    void clientReady(ClientShellCommand client);

    Function getReadyCallback();

    void setReadyCallback(Function f);

    Function getChangeCallback();

    void setChangeCallback(Function f);
}