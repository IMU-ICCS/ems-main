/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.brokerclient;

import com.google.gson.Gson;
import eu.melodic.event.brokerclient.event.EventGenerator;
import eu.melodic.event.brokerclient.event.EventMap;
import javax.jms.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class BrokerClientApp {

    private static boolean filterAMQMessages = true;

    public static void main(String args[]) throws java.io.IOException, JMSException {
        if (args.length==0) {
            usage();
            return;
        }

        int aa=0;
        String command = args[aa++];

        filterAMQMessages = args.length>aa && args[aa].startsWith("-Q") ? false : true;
        if (!filterAMQMessages) aa++;

        String username = args.length>aa && args[aa].startsWith("-U") ? args[aa++].substring(2) : null;
        String password = username!=null && args.length>aa && args[aa].startsWith("-P") ? args[aa++].substring(2) : null;
        if (StringUtils.isNotBlank(username) && password == null) {
            password = new String(System.console().readPassword("Enter broker password: "));
        }

        // list destinations
        if ("list".equalsIgnoreCase(command)) {
            String url = args[aa++];
            log.info("BrokerClientApp: Listing destinations:");
            BrokerClient client = BrokerClient.newClient(username, password);
            client.getDestinationNames(url).stream().forEach(d -> log.info("    {}", d));
        } else
        // send an event
        if ("publish".equalsIgnoreCase(command)) {
            String url = args[aa++];
            String topic = args[aa++];
            String value = args[aa++];
            String level = args[aa++];
            EventMap event = new EventMap(Double.parseDouble(value), Integer.parseInt(level), System.currentTimeMillis());
            log.info("BrokerClientApp: Publishing event: {}", event);
            BrokerClient client = BrokerClient.newClient(username, password);
            client.publishEvent(url, topic, event);
        } else
        if ("publish2".equalsIgnoreCase(command)) {
            String url = args[aa++];
            String topic = args[aa++];
            String payload = args[aa++];
            payload = payload
                    .replaceAll("%TIMESTAMP%|%TS%", ""+System.currentTimeMillis());
            EventMap event = new Gson().fromJson(payload, EventMap.class);
            log.info("BrokerClientApp: Publishing event: {}", event);
            BrokerClient client = BrokerClient.newClient(username, password);
            client.publishEvent(url, topic, event);
            log.info("BrokerClientApp: Event payload: {}", payload);
        } else
        // receive events from topic
        if ("receive".equalsIgnoreCase(command)) {
            String url = args[aa++];
            String topic = args[aa++];
            log.info("BrokerClientApp: Subscribing to topic: {}", topic);
            BrokerClient client = BrokerClient.newClient(username, password);
            client.receiveEvents(url, topic, getMessageListener());
        } else
        // subscribe to topic
        if ("subscribe".equalsIgnoreCase(command)) {
            String url = args[aa++];
            String topic = args[aa++];
            log.info("BrokerClientApp: Subscribing to topic: {}", topic);
            BrokerClient client = BrokerClient.newClient(username, password);
            MessageListener listener = null;
            client.subscribe(url, topic, listener = getMessageListener());

            log.info("BrokerClientApp: Hit ENTER to exit");
            try {
                System.in.read();
            } catch (Exception e) {}
            log.info("BrokerClientApp: Closing connection...");

            client.unsubscribe(listener);
            client.closeConnection();
            log.info("BrokerClientApp: Exiting...");

        } else
        // start event generator
        if ("generator".equalsIgnoreCase(command)) {
            String url = args[aa++];
            String topic = args[aa++];
            long interval = Long.parseLong(args[aa++]);
            long howmany = Long.parseLong(args[aa++]);
            double lowerValue = Double.parseDouble(args[aa++]);
            double upperValue = Double.parseDouble(args[aa++]);
            int level = Integer.parseInt(args[aa++]);

            BrokerClient client = BrokerClient.newClient(username, password);
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

    private static MessageListener getMessageListener() {
        return message -> {
            try {
                String destinationName = getDestinationName(message);
                if (filterAMQMessages && StringUtils.startsWithIgnoreCase(destinationName, "ActiveMQ.")) {
                    log.trace("BrokerClientApp:  - {}: ActiveMQ message filtered out: {}", destinationName, message);
                    log.debug("AMQ: {}:\n{}", destinationName, message);
                    return;
                }
                if (message instanceof ObjectMessage) {
                    ObjectMessage objMessage = (ObjectMessage) message;
                    Object obj = objMessage.getObject();
                    log.trace("BrokerClientApp:  - {}: Received object message: {}", destinationName, obj);
                    log.info("OBJ: {}:\n{}", destinationName, obj);
                } else if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    String text = textMessage.getText();
                    log.trace("BrokerClientApp:  - {}: Received text message: {}", destinationName, text);
                    log.info("TXT: {}:\n{}", destinationName, text);
                } else {
                    log.trace("BrokerClientApp:  - {}: Received message: {}", destinationName, message);
                    log.info("MSG: {}:\n{}", destinationName, message);
                }
            } catch (JMSException je) {
                log.warn("BrokerClientApp: onMessage: EXCEPTION: ", je);
            }
        };
    }

    private static String getDestinationName(Message message) throws JMSException {
        Destination d = message.getJMSDestination();
        if (d instanceof Topic) {
            return ((Topic)d).getTopicName();
        } else
        if (d instanceof Queue) {
            return ((Queue)d).getQueueName();
        } else
            throw new IllegalArgumentException("Argument is not a JMS destination: "+d);
    }

    protected static void usage() {
        log.info("BrokerClientApp: Usage: ");
        log.info("BrokerClientApp: client list [-U<USERNAME> [-P<PASSWORD]] <URL> ");
        log.info("BrokerClientApp: client publish [-U<USERNAME> [-P<PASSWORD]] <URL> <TOPIC> <VALUE> <LEVEL> ");
        log.info("BrokerClientApp: client publish2 [-U<USERNAME> [-P<PASSWORD]] <URL> <TOPIC> <PAYLOAD> ");
        log.info("BrokerClientApp: client receive [-U<USERNAME> [-P<PASSWORD]] <URL> <TOPIC> ");
        log.info("BrokerClientApp: client subscribe [-U<USERNAME> [-P<PASSWORD]] <URL> <TOPIC> ");
        log.info("BrokerClientApp: client generator [-U<USERNAME> [-P<PASSWORD]] <URL> <TOPIC> <INTERVAL> <HOWMANY> <LOWER-VALUE> <UPPER-VALUE> <LEVEL> ");
    }
}