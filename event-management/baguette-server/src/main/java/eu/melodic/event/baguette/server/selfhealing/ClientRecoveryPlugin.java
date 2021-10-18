/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.server.selfhealing;

import eu.melodic.event.util.EventBus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientRecoveryPlugin implements InitializingBean, EventBus.EventConsumer<String,Object,Object> {
    private final EventBus<String,Object,Object> eventBus;

    @Override
    public void afterPropertiesSet() throws Exception {
        eventBus.subscribe("BAGUETTE_SERVER_CLIENT_EXITED", this);
        log.warn(">>>>>>>>>>>>>>>>>>>  ClientRecoveryPlugin: Subscribed for BAGUETTE_SERVER_CLIENT_EXITED events");
    }

    @Override
    public void onMessage(String topic, Object message, Object sender) {
        log.warn(">>>>>>>>>>>>>>>>>>>  ClientRecoveryPlugin: CLIENT EXITED: message={}", message);
    }
}
