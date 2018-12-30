/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.brokercep.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Component
@Scope("prototype")
@EnableAsync
@Slf4j
public class BrokerCepFakeEventsProducer {
    @Autowired
    private BrokerService brokerService;    // Added in order to ensure that BrokerService will be instantiated first
    @Autowired
    private ActiveMQConnectionFactory connectionFactory;

    private Connection connection;
    private Session session;

    @Async
    public void sendEvents(String destinationName, PayloadGenerator payloadGenerator, int numOfEvents, long delay) throws InterruptedException {
        try {
            log.info("BrokerCepFakeEventsProducer: Initializing fake events producer for destination: {}", destinationName);
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
            //Queue destination = session.createQueue( destinationName );
            Topic destination = session.createTopic(destinationName);
            MessageProducer producer = session.createProducer(destination);

            log.info("BrokerCepFakeEventsProducer: Sending {} fake events to destination: {}", numOfEvents, destinationName);
            for (int i = 0; i < numOfEvents; i++) {
                Object payload = payloadGenerator.payloadAsObject(i);
                Message msg = session.createObjectMessage((Serializable) payload);
                producer.send(msg);
                log.debug("BrokerCepFakeEventsProducer: Sending event #{} to destination: {}, payload={}", i, destinationName, payload);
                TimeUnit.MILLISECONDS.sleep(delay);
            }
            log.info("BrokerCepFakeEventsProducer: Done sending to destination: {}", destinationName);
        } catch (JMSException ex) {
            log.error("BrokerCepFakeEventsProducer: EXCEPTION: ", ex);
        } catch (InterruptedException ex) {
            log.info("BrokerCepFakeEventsProducer: Interrupted: exiting send events");
        }
    }

    public static interface PayloadGenerator {
        public default String payloadAsString(int iteration) {
            Object o = payloadAsObject(iteration);
            return (o != null ? o.toString() : null);
        }

        public default Object payloadAsObject(int iteration) {
            return null;
        }
    }

    public static class PayloadMap extends HashMap<String, Object> {
        public static PayloadMap create() {
            return new PayloadMap();
        }

        public PayloadMap put(String key, Object value) {
            super.put(key, value);
            return this;
        }
    }
}