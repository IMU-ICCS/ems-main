/*
 * Copyright (C) 2015 University of Ulm.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package org.ow2.paasage.camel.srl.adapter;

import org.ow2.paasage.camel.srl.adapter.communication.ZeroMqServer;
import org.ow2.paasage.camel.srl.adapter.communication.ZeroMqSubscriber;
import org.ow2.paasage.camel.srl.adapter.config.CommandLinePropertiesAccessor;
import org.ow2.paasage.camel.srl.adapter.config.CommandLinePropertiesAccessorImpl;
import org.ow2.paasage.camel.srl.adapter.config.ModelSourceType;
import org.ow2.paasage.camel.srl.adapter.execution.Execution;
import org.ow2.paasage.camel.srl.adapter.execution.ImportModelSource;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 *
 */
public class App 
{
    private static org.apache.log4j.Logger logger;

    static {
        logger = org.apache.log4j.Logger.getLogger(App.class);
    }

    public static void main( String[] args ) {
        CommandLinePropertiesAccessor conf = new CommandLinePropertiesAccessorImpl(args);
        ZeroMqSubscriber subscriber;
        ExecutorService executor;

        ImportModelSource ims = ModelSourceType.mapToIms(conf);

        switch(conf.getExecutionMode()){
            case STATIC:
                // just run the adapter once with all information in the conf:
                logger.info("Run SRL-Adapter in STATIC mode.");
                Execution exec = new Execution(conf);
                exec.run(ims);

                break;
            case ZMQ_LISTEN:
                // listen to ZeroMQ for modelname and executioncontext
                logger.info("Run SRL-Adapter in ZMQ_LISTEN mode and start listening to ZERO_MQ server for modelname and executioncontext name.");
                subscriber = new ZeroMqSubscriber(conf);
                executor = Executors.newCachedThreadPool();
                executor.execute(subscriber);
                logger.info("Subscribed to ZeroMQ server.");

                break;
            case ZMQ_HOST:
                // start and listen to ZeroMQ for modelname and executioncontext
                logger.info("Run SRL-Adapter in ZMQ_HOST mode and start ZERO_MQ server to wait for messages with modelname and executioncontext name.");
                ZeroMqServer server = new ZeroMqServer(conf.getZeroMqPort());
                logger.info("Started ZeroMQ server.");
                subscriber = new ZeroMqSubscriber(conf, "tcp://localhost:" + conf.getZeroMqPort());
                executor = Executors.newCachedThreadPool();
                executor.execute(subscriber);
                logger.info("Subscribed to ZeroMQ server.");
                // TEST:
                try {
                    logger.info("Sleep for two seconds to the Subscriber is set up, before we send messages:");
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                server.submitValue(conf.getZeroMqQueue(), "CAMEL_7f9bd23c8779f929b68554e00c187aae", "BewanCamelModel", "ExecutionContext1");

                break;
        }
    }
}
