/*
 * Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
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