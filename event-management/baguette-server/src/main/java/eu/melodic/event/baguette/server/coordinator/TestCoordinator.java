/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.baguette.server.coordinator;

import eu.melodic.event.baguette.server.ClientShellCommand;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class TestCoordinator extends NoopCoordinator {
    @Override
    public synchronized void register(ClientShellCommand c) {
        if (!_logInvocation("register", c, true)) return;
        _do_register(c);
    }

    protected synchronized void _do_register(ClientShellCommand c) {
        // prepare configuration
        java.util.Properties cfg = new java.util.Properties();
        String upperwareCfg = server.getUpperwareBrokerUrl()+"\n"+server.getBrokerCepService().getBrokerCertificate();
        cfg.setProperty("GLOBAL", upperwareCfg.trim());
        log.trace("TestCoordinator.test(): GLOBAL broker config.: {}", upperwareCfg);

        cfg.putAll(getGroupingBrokerConfig("PER_CLOUD", c));
        log.trace("TestCoordinator.test(): {} broker config.: {}", "PER_CLOUD", getGroupingBrokerConfig("PER_CLOUD", c));

        // prepare Broker-CEP configuration
        log.info("TestCoordinator.test(): --------------------------------------------------");
        log.info("TestCoordinator.test(): Sending grouping configurations...");
        sendGroupingConfigurations(cfg, c, server);
        log.info("TestCoordinator.test(): Sending grouping configurations... done");

        // Set active grouping and send an event
        String grouping = "PER_INSTANCE";
        try {
            Thread.sleep(500);
        } catch (Exception ex) {
        }
        log.info("TestCoordinator.test(): --------------------------------------------------");
        log.info("TestCoordinator.test(): Setting active grouping: {}", grouping);
        c.setActiveGrouping(grouping);

        try {
            Thread.sleep(5000);
        } catch (Exception ex) {
        }
        log.info("TestCoordinator.test(): --------------------------------------------------");
    }
}
