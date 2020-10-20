/*
 * Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.client.install.helper;

import eu.melodic.event.baguette.client.install.instruction.InstallationInstructions;
import eu.melodic.event.baguette.server.BaguetteServer;
import eu.melodic.event.util.NetUtil;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public interface InstallationInstructionsGenerator {
    InstallationInstructions prepareInstallationInstructionsForOs(Map<String,Object> nodeMap, String baseUrl, String clientId, BaguetteServer baguette, String ipSetting) throws IOException;
    InstallationInstructions prepareInstallationInstructionsForWin(String baseUrl, String clientId, BaguetteServer baguette, String ipSetting);
    InstallationInstructions prepareInstallationInstructionsForLinux(String baseUrl, String clientId, BaguetteServer baguette, String ipSetting) throws IOException;

    default String _prepareUrl(String urlTemplate, String baseUrl) {
        return urlTemplate
                .replace("%{BASE_URL}%", baseUrl)
                .replace("%{PUBLIC_IP}%", Optional.ofNullable(NetUtil.getPublicIpAddress()).orElse(""))
                .replace("%{DEFAULT_IP}%", Optional.ofNullable(NetUtil.getDefaultIpAddress()).orElse(""));
    }
}
