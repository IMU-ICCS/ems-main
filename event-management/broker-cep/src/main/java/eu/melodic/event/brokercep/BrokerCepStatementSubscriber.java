/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.brokercep;

import eu.melodic.event.brokercep.cep.StatementSubscriber;
import eu.melodic.event.util.GroupingConfiguration;
import eu.melodic.event.util.PasswordUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class BrokerCepStatementSubscriber implements StatementSubscriber {
    private static AtomicLong counterLocalPublishSuccess = new AtomicLong(0);
    private static AtomicLong counterLocalPublishFailure = new AtomicLong(0);
    private static AtomicLong counterForwardSuccess = new AtomicLong(0);
    private static AtomicLong counterForwardFailure = new AtomicLong(0);

    private final String name;
    private final String topic;
    private final String statement;
    private final BrokerCepService brokerCep;
    private final PasswordUtil passwordUtil;
    @Setter
    private Set<GroupingConfiguration.BrokerConnectionConfig> forwardToGroupings;

    public void update(Map<String, Object> eventMap) {
        publishToLocalBroker(eventMap);
        forwardToGroupings(eventMap);
    }

    protected void publishToLocalBroker(Map<String, Object> eventMap) {
        log.info("- New event received: subscriber={}, topic={}, payload={}", name, topic, eventMap);
        String localBrokerUrl = brokerCep.getBrokerCepProperties().getBrokerUrlForConsumer();
        String username = brokerCep.getBrokerUsername();
        String password = passwordUtil.getPasswordEncoder().encode(brokerCep.getBrokerPassword());
        try {
            // Publish new event to Local Broker topic
            log.trace("- Publishing event to local broker: subscriber={}, local-broker={}, username={}, password={}, topic={}, payload={}",
                    name, localBrokerUrl, username, password, topic, eventMap);
            brokerCep.publishEvent(localBrokerUrl, username, password, topic, eventMap);
            log.debug("- Event published to local broker: subscriber={}, local-broker={}, username={}, password={}, topic={}, payload={}",
                    name, localBrokerUrl, username, password, topic, eventMap);
            countLocalPublish(true);

        } catch (Exception ex) {
            log.error("- New event: ERROR while publishing to local broker: subscriber={}, local-broker={}, username={}, password={}, topic={}, exception=",
                    name, localBrokerUrl, username, password, topic, ex);
            countLocalPublish(false);
        }
    }

    protected void forwardToGroupings(Map<String, Object> eventMap) {
        // Send new event to the next grouping(s)
        log.trace("- Forwarding event to groupings: subscriber={}, forward-to-groupings={}, payload={}",
                name, forwardToGroupings, eventMap);
        if (forwardToGroupings==null)
            return;
        for (GroupingConfiguration.BrokerConnectionConfig fwdToGrouping : forwardToGroupings) {
            try {
                String brokerUrl = fwdToGrouping.getUrl();
                String username = fwdToGrouping.getUsername();
                String password = fwdToGrouping.getPassword();
                log.debug("- Forwarding event to grouping: subscriber={}, forward-to-grouping={}, url={}, username={}, topic={}, payload={}",
                        name, fwdToGrouping, brokerUrl, username, topic, eventMap);
                brokerCep.publishEvent(brokerUrl, username, password, topic, eventMap);
                log.debug("- Event forwarded to grouping: subscriber={}, forwarded-to-grouping={}, url={}, username={}, topic={}, payload={}",
                        name, fwdToGrouping, brokerUrl, username, topic, eventMap);
                countForward(true);
            } catch (Exception ex) {
                log.error("- Error while sending event: subscriber={}, forward-to-groupings={}, payload={}, exception: ",
                        name, forwardToGroupings, eventMap, ex);
                countForward(false);
            }
        }
    }

    private void countLocalPublish(boolean success) {
        if (success) counterLocalPublishSuccess.incrementAndGet();
        else counterLocalPublishFailure.incrementAndGet();
    }

    private void countForward(boolean success) {
        if (success) counterForwardSuccess.incrementAndGet();
        else counterForwardFailure.incrementAndGet();
    }

    public long getLocalPublishSuccessCounter() { return counterLocalPublishSuccess.get(); }
    public long getLocalPublishFailureCounter() { return counterLocalPublishFailure.get(); }
    public long getForwardSuccessCounter() { return counterForwardSuccess.get(); }
    public long getForwardFailureCounter() { return counterForwardFailure.get(); }
}
