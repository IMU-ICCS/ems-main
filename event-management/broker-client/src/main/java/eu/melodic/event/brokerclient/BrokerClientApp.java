/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.brokerclient;

import eu.melodic.event.brokerclient.event.EventGenerator;
import eu.melodic.event.brokerclient.event.EventMap;
import eu.melodic.event.brokerclient.properties.BrokerClientProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;

@Slf4j
public class BrokerClientApp {

    @Autowired
    private BrokerClientProperties properties;

    public static void main(String args[]) throws java.io.IOException, JMSException {
        String command = args[0];
        // send an event
        if ("publish".equalsIgnoreCase(command)) {
            String url = args[1];
            String topic = args[2];
            String value = args[3];
            String level = args[4];
            EventMap event = new EventMap(Double.parseDouble(value), Integer.parseInt(level), System.currentTimeMillis());
            log.info("BrokerClientApp: Publishing event: {}", event);
            BrokerClient client = BrokerClient.newClient();
            client.publishEvent(url, topic, event);
        } else
            // subscribe to topic
            if ("subscribe".equalsIgnoreCase(command)) {
                String url = args[1];
                String topic = args[2];
                log.info("BrokerClientApp: Subscribing to topic: {}", topic);
                BrokerClient client = BrokerClient.newClient();
                client.subscribeToTopic(url, topic, new BrokerClient.EventReceiver() {
                    public void eventReceived(Object o) {
                        log.info("BrokerClientApp: eventReceieved(Object): {}", o);
                    }

                    public void eventReceived(String t) {
                        log.info("BrokerClientApp: eventReceieved(String): {}", t);
                    }
                });
            } else
                // start event generator
                if ("generator".equalsIgnoreCase(command)) {
                    String url = args[1];
                    String topic = args[2];
                    long interval = Long.parseLong(args[3]);
                    long howmany = Long.parseLong(args[4]);
                    double lowerValue = Double.parseDouble(args[5]);
                    double upperValue = Double.parseDouble(args[6]);
                    int level = Integer.parseInt(args[7]);

                    BrokerClient client = BrokerClient.newClient();
                    EventGenerator generator = new EventGenerator();
                    generator.setClient(client);
                    generator.setBrokerUrl(url);
                    generator.setDestinationName(topic);
                    generator.setInterval(interval);
                    generator.setHowmany(howmany);
                    generator.setLowerValue(lowerValue);
                    generator.setUpperValue(upperValue);
                    generator.setLevel(level);
                    generator.run();
                } else
                // error
                {
                    log.error("BrokerClientApp: Unknown command: {}", command);
                    usage();
                }
    }

    protected static void usage() {
        log.info("BrokerClientApp: Usage: ");
        log.info("BrokerClientApp: client publish <URL> <TOPIC> <VALUE> <LEVEL> ");
        log.info("BrokerClientApp: client subscribe <URL> <TOPIC> ");
        log.info("BrokerClientApp: client generator <URL> <TOPIC> <INTERVAL> <HOWMANY> <LOWER-VALUE> <UPPER-VALUE> <LEVEL> ");
    }
}