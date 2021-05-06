/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.client.install.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Installation helper factory
 */
@Slf4j
@Service
public class InstallationHelperFactory implements InitializingBean {
    private static InstallationHelperFactory instance;

    public synchronized static InstallationHelperFactory getInstance() { return instance; }

    @Override
    public void afterPropertiesSet() {
        InstallationHelperFactory.instance = this;
    }

    public InstallationHelper createInstallationHelper(Map<String,Object> nodeMap) {
        String nodeType = (String) nodeMap.get("type");
        if ("VM".equalsIgnoreCase(nodeType)) {
            return createVmInstallationHelper(nodeMap);
        }
        throw new IllegalArgumentException("Unsupported or missing Node type: "+nodeType);
    }

    private InstallationHelper createVmInstallationHelper(Map<String, Object> nodeMap) {
        return VmInstallationHelper.getInstance();
    }
}
