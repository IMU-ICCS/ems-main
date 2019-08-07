/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.control.util;

import eu.melodic.event.brokercep.BrokerCepService;
import eu.melodic.event.brokercep.event.EventMap;
import eu.melodic.event.control.ControlServiceCoordinator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import java.util.*;

@Service
@EnableScheduling
@Slf4j
public class TopicBeacon implements InitializingBean {
    // Topic Beacon settings
    @Value("${beacon.enable:true}")
    private boolean beaconEnable;
    @Value("${beacon.initial-delay:60000}")
    private long beaconInitialDelay;
    @Value("${beacon.delay:60000}")
    private long beaconDelay;
    @Value("${beacon.rate:60000}")
    private long beaconRate;
    @Value("${beacon.topics.heartbeat:}")
    private Set<String> beaconHeartbeatTopics;

    @Autowired
    private ControlServiceCoordinator coordinator;
    @Autowired
    private BrokerCepService brokerCepService;
    @Autowired
    private TaskScheduler scheduler;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!beaconEnable) {
            log.warn("Topic Beacon is disabled");
            return;
        }

        Date startTime = new Date(System.currentTimeMillis()+beaconInitialDelay);
        log.debug("Topic Beacon settings: init-delay={}, delay={}, heartbeat-topics={}",
                beaconInitialDelay, beaconDelay, beaconHeartbeatTopics);
        scheduler.scheduleWithFixedDelay(() -> {
            try {
                transmitInfo();
            } catch (JMSException e) {
                log.error("Topic Beacon: Exception while sending info: ", e);
            }
        }, startTime, beaconDelay);
        log.info("Topic Beacon started: init-delay={}ms", beaconInitialDelay);
    }

    public void transmitInfo() throws JMSException {
        log.debug("Topic Beacon: Start transmitting info: {}", new Date());

        String message = "TOPIC BEACON HEARTBEAT "+new Date();
        log.debug("Topic Beacon: Transmitting Heartbeat info: message={}, topics={}", message, beaconHeartbeatTopics);
        transmitInfo(message, beaconHeartbeatTopics);

        log.debug("Topic Beacon: Completed transmitting info: {}", new Date());
    }

    private void transmitInfo(String message, Set<String> topics) throws JMSException {
        for (String topicName : topics) {
            log.debug("Topic Beacon: Sending message to topic: message={}, topic={}", message, topicName);
            EventMap event = new EventMap(-1);
            event.put("message", message);
            log.trace("Topic Beacon: Sending event to topic: event={}, topic={}", event, topicName);
            brokerCepService.publishEvent(
                    brokerCepService.getBrokerCepProperties().getBrokerUrl(),
                    brokerCepService.getBrokerUsername(),
                    brokerCepService.getBrokerPassword(),
                    topicName,
                    event);
            log.info("Topic Beacon: Event sent to topic: event={}, topic={}", event, topicName);
        }
    }
}
