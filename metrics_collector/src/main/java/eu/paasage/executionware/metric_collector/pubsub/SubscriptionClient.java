/* Copyright (C) 2015 KYRIAKOS KRITIKOS <kritikos@ics.forth.gr> */

/* This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ 
 */

package eu.paasage.executionware.metric_collector.pubsub;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

import eu.paasage.executionware.metric_collector.Test;

/**
* Pubsub envelope subscriber
*/

public class SubscriptionClient implements Runnable{
	
	private Context context;
	private Socket socket;
	private String metricId;
	private boolean run = true;
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(SubscriptionClient.class);
	
	public SubscriptionClient(String metricId){
		this.metricId = metricId;
		context = ZMQ.context(1);
        socket = context.socket(ZMQ.SUB);

        socket.connect("tcp://localhost:5563");
        socket.subscribe(metricId.getBytes(ZMQ.CHARSET));
	}
	
	public synchronized void terminate(){
		run = false;
	}

	public void run() {
		while (run) {
            // Read envelope with address
            String address = socket.recvStr ();
            // Read message contents
            String contents = socket.recvStr ();
            logger.info("SubscriptionClient: " + address + " : " + contents);
        }
        socket.close ();
        context.term ();
	}
}