/*
 * Copyright (C) 2015 University of Ulm.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package org.ow2.paasage.camel.srl.adapter.communication;

import org.ow2.paasage.camel.srl.adapter.Execution;
import org.ow2.paasage.camel.srl.adapter.config.CommandLinePropertiesAccessor;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

/**
 * Created by Frank on 16.11.2015.
 */
public class ZeroMqSubscriber implements Runnable {

    private final CommandLinePropertiesAccessor conf;
    private final String uri;
    private final String queueName;
    private final Context context;
    private final Socket socket;
    private boolean isRunning = true;
    private final int ioThreadNum = 1;
    public static final String SEPERATOR = ":";


    public ZeroMqSubscriber(CommandLinePropertiesAccessor conf) {
        this(conf, null);
    }

    public ZeroMqSubscriber(CommandLinePropertiesAccessor conf, String alternativeUri) {
        this.conf = conf;
        if(alternativeUri == null){
            this.uri = conf.getZeroMqUri();
        } else {
            this.uri = alternativeUri;
        }
        this.queueName = conf.getZeroMqQueue();
        context = ZMQ.context(ioThreadNum);
        socket = context.socket(ZMQ.SUB);

        socket.connect(uri);
        socket.subscribe(queueName.getBytes(ZMQ.CHARSET));
    }

    public synchronized void stop(){
        this.isRunning = false;
    }

    public void run() {
        while (isRunning) {
            // Read envelope with address
            String address = socket.recvStr ();
            // Read message contents
            String contents = socket.recvStr ();

            CdoConfigTuple converted = convertLine(contents);

            // TODO this is blocking - check if this is a problem for frequent requests
            Execution ex = new Execution(conf);
            ex.run(converted.getResourceName(), converted.getModelName(), converted.getExecutionContext());
        }
        socket.close ();
        context.term ();
    }

    public static CdoConfigTuple convertLine(String contents){

        // Content format: resource:model:executioncontext

        int indexOfSeperator1 = contents.indexOf(SEPERATOR);
        int indexOfSeperator2 = indexOfSeperator1 + contents.substring(indexOfSeperator1 + 1).indexOf(SEPERATOR) + 1;

        String resourceName = contents.substring(0, indexOfSeperator1);

        String modelName = contents.substring(indexOfSeperator1 + 1, indexOfSeperator2);

        String executionContext = contents.substring(indexOfSeperator2 + 1, contents.length());
        if("".equals(executionContext)){
            executionContext = null;
        }

        return new CdoConfigTuple(resourceName, modelName, executionContext);
    }
}
