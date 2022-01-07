/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.client.install.helper;

import eu.melodic.event.baguette.client.install.ClientInstallationTask;
import eu.melodic.event.baguette.client.install.instruction.InstructionsSet;
import eu.melodic.event.baguette.server.NodeRegistryEntry;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface InstallationHelper {
    Optional<List<String>> getInstallationInstructionsForOs(NodeRegistryEntry entry) throws IOException;

    List<InstructionsSet> prepareInstallationInstructionsForOs(NodeRegistryEntry entry) throws IOException;
    List<InstructionsSet> prepareInstallationInstructionsForWin(NodeRegistryEntry entry);
    List<InstructionsSet> prepareInstallationInstructionsForLinux(NodeRegistryEntry entry) throws IOException;

    ClientInstallationTask createClientInstallationTask(NodeRegistryEntry entry) throws Exception;
}
