/*
 * Copyright (C) 2015 University of Ulm.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package org.ow2.paasage.camel.srl.adapter.communication;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

/**
 * Created by Frank on 16.11.2015.
 */
public class ZeroMqServer {
    private boolean isRunning = true;
    private Socket socket;
    private Context context;
    private int threadNum = 1;
    private static final int DEFAULT_THREAD_NUM = 1;
    private final int port;

    public ZeroMqServer(int port){
        this(DEFAULT_THREAD_NUM, port);
    }

    public ZeroMqServer(int threadNum, int port){
        this.threadNum = threadNum;
        this.port = port;
        context = ZMQ.context(threadNum);
        socket = context.socket(ZMQ.PUB);
        socket.bind("tcp://*:" + port);
    }

    public synchronized void submitValue(String queueName, String resourceName, String modelName, String executionContext){
        if (isRunning){

            // Content format: resource:model:executioncontext
            socket.sendMore(queueName);
            socket.send(resourceName + ZeroMqSubscriber.SEPARATOR
                + modelName + ZeroMqSubscriber.SEPARATOR + executionContext);
        }
    }

    public synchronized void submitValue(String queueName, String message){
        final CdoConfigTuple cdoConfigTuple = ZeroMqSubscriber.convertLine(message);
        this.submitValue(queueName, cdoConfigTuple.getResourceName(), cdoConfigTuple.getModelName(), cdoConfigTuple.getExecutionContext());
    }

    public synchronized void stop(){
        isRunning = false;
        socket.close();
        context.term();
    }
}
