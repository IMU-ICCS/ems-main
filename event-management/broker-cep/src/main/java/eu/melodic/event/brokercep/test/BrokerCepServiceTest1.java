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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static eu.melodic.event.brokercep.test.BrokerCepFakeEventsProducer.PayloadGenerator;
import static eu.melodic.event.brokercep.test.BrokerCepFakeEventsProducer.PayloadMap;

/**
 * This class implements a test scenario where events are generated and sent to queues/topics without setting up any EPL statements
 */
@Service
@ConditionalOnProperty(name = "run-broker-cep-test", havingValue = "BrokerCepServiceTest1", matchIfMissing = false)
@Slf4j
public class BrokerCepServiceTest1 implements CommandLineRunner {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void run(String... args) throws Exception {
        log.info("Starting everything...");

        // Setting up incoming event queues/topics...
		/*BrokerCepConsumer bridge = applicationContext.getBean(BrokerCepConsumer.class);
		bridge.addQueue("temperatureQueue");
		bridge.addQueue("radiationQueue");
		bridge.addQueue("windQueue");*/
        // If uncommented exceptions will be thrown, since 'bridge.addQueue'
        // sets the queue message listener, which forwards messages to Esper

        log.info("Waiting for 5 seconds...");
        TimeUnit.SECONDS.sleep(5);

        // Start fake event producers
        BrokerCepFakeEventsProducer generator1 = applicationContext.getBean(BrokerCepFakeEventsProducer.class);
        PayloadGenerator pg1 = new PayloadGenerator() {
            public Object payloadAsObject(int it) {
                return PayloadMap.create()
                        .put("temperature", new Random().nextInt(500))
                        .put("timeOfReading", new Date());
            }
        };
        generator1.sendEvents("temperatureQueue", pg1, 100, 5000);

        BrokerCepFakeEventsProducer generator2 = applicationContext.getBean(BrokerCepFakeEventsProducer.class);
        PayloadGenerator pg2 = new PayloadGenerator() {
            public Object payloadAsObject(int it) {
                return PayloadMap.create()
                        .put("radiation", new Random().nextInt(500) + 400)
                        .put("timeOfReading", new Date());
            }
        };
        generator2.sendEvents("radiationQueue", pg2, 500, 1000);

        BrokerCepFakeEventsProducer generator3 = applicationContext.getBean(BrokerCepFakeEventsProducer.class);
        PayloadGenerator pg3 = new PayloadGenerator() {
            public Object payloadAsObject(int it) {
                return PayloadMap.create()
                        .put("speed", new Random().nextInt(8) + 2)
                        .put("direction", new Random().nextInt(60) + 230)
                        .put("timeOfReading", new Date());
            }
        };
        generator3.sendEvents("windQueue", pg3, 50, 10000);

        log.info("Started everything!!!!");
    }
}