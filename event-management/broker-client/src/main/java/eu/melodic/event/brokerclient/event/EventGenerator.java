/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.brokerclient.event;

import eu.melodic.event.brokerclient.BrokerClient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class EventGenerator implements Runnable {
    private BrokerClient client;
    private String brokerUrl;
    private String destinationName;
    private long interval;
    private long howmany = -1;
    private double lowerValue;
    private double upperValue;
    private int level;

    private transient boolean keepRunning;

    public void start() {
        if (keepRunning) return;
        Thread runner = new Thread(this);
        runner.setDaemon(true);
        runner.start();
    }

    public void stop() {
        keepRunning = false;
    }

    public void run() {
        log.info("EventGenerator.run(): Start sending events: event-generator: {}", this);

        keepRunning = true;
        double valueRangeWidth = upperValue - lowerValue;
        long countSent = 0;
        while (keepRunning) {
            try {
                double newValue = Math.random() * valueRangeWidth + lowerValue;
                EventMap event = new EventMap(newValue, level, System.currentTimeMillis());
                log.info("EventGenerator.run(): Sending event #{}: {}", countSent + 1, event);
                client.publishEvent(brokerUrl, destinationName, event);
                countSent++;
                if (countSent == howmany) keepRunning = false;
                log.info("EventGenerator.run(): Event sent #{}: {}", countSent, event);
            } catch (Exception ex) {
                log.warn("EventGenerator.run(): WHILE-EXCEPTION: {}", ex);
            }
            // sleep for 'interval' ms
            try {
                if (keepRunning) {
                    Thread.sleep(interval);
                }
            } catch (InterruptedException ex) {
                log.warn("EventGenerator.run(): Sleep interrupted");
            }
        }

        log.info("EventGenerator.run(): Stop sending events: event-generator: {}", this);
    }
}