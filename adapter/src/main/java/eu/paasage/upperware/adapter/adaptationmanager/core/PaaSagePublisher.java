package eu.paasage.upperware.adapter.adaptationmanager.core;/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 * 
 * Copyright (c) 2016 CETIC ASBL
 * 
 * */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeromq.ZMQ;

/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright (c) 2016 CETIC ASBL
 *
 * */

/**
 * Created by ec on 3/03/16.
 */
public class PaaSagePublisher extends Thread {

    static Logger LOGGER = LoggerFactory.getLogger(PaaSagePublisher.class.getSimpleName());


    private String destinationUrl; // Where to publish
    private String topic;          // Topic to use
    private String[] messages;        // What to publish

    private PaaSagePublisher(String destinationUrl, String topic, String[] messages){
        this.destinationUrl=destinationUrl;
        this.topic=topic;
        this.messages = messages;
    }

    @Override
    public void run() {
        LOGGER.info("ZmqPublisher Thread");

        try {

            LOGGER.info("Publishing data on " + destinationUrl);

            ZMQ.Context context = ZMQ.context(1);

            ZMQ.Socket publisher = context.socket(ZMQ.PUB);
            publisher.bind(destinationUrl);

            LOGGER.info("Bound");
            Thread.sleep(2000);

            publisher.sendMore(topic);
            LOGGER.info("Topic : " + topic);


            for ( int i=0; i < messages.length; i++) {
                String message = messages[i];
                if ( i == messages.length - 1) {
                    // Last element
                    publisher.send(message);
                }
                else {
                    publisher.sendMore(message);
                }


                LOGGER.info("Message sent: " + message);
            }

            LOGGER.info("About to close: ");
            publisher.close();

            LOGGER.info("About to term: ");
            context.term();
        } catch (Exception ex) {
            String s = ex.toString();
            LOGGER.error(String.format("Cannot Publish to %s: Exception: %s",destinationUrl,s));
        }

    }


    public static void publishBackend(String topic, String[] messages) {

        LOGGER.info("Publish to PaaSage Backend: topic={}, message={}", topic, String.join("|",messages));

        String server = "tcp://*:5559";

        PaaSagePublisher publisherThread = new PaaSagePublisher(server,topic,messages);
        publisherThread.start();
    }


}

