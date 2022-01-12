/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.server.coordinator.cluster;

import eu.melodic.event.baguette.server.ClientShellCommand;
import eu.melodic.event.baguette.server.NodeRegistryEntry;
import lombok.NonNull;

import java.io.File;
import java.util.List;
import java.util.Set;

public interface IClusterZone {
    String getId();
    void addNode(@NonNull ClientShellCommand csc);
    void removeNode(@NonNull ClientShellCommand csc);
    Set<String> getNodeAddresses();
    List<ClientShellCommand> getNodes();
    ClientShellCommand getNodeByAddress(String address);

    void addNodeWithoutClient(@NonNull NodeRegistryEntry entry);
    void removeNodeWithoutClient(@NonNull NodeRegistryEntry entry);
    Set<String> getNodeWithoutClientAddresses();
    List<NodeRegistryEntry> getNodesWithoutClient();
    NodeRegistryEntry getNodeWithoutClientByAddress(String address);

    File getClusterKeystoreFile();
    String getClusterKeystoreType();
    String getClusterKeystorePassword();
    String getClusterKeystoreBase64();
}
