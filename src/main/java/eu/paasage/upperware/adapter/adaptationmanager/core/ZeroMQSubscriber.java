/*
 * Copyright (c) 2016 INRIA, INSA Rennes
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.adaptationmanager.core;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.zeromq.ZMQ;

public class ZeroMQSubscriber extends Thread {
	
	private String subscriberName;
	private int port;
	
	ZMQ.Context context;
	ZMQ.Socket subscriber;
	
	private final static Logger LOGGER = Logger
		.getLogger(ZeroMQSubscriber.class.getName());
		
	public ZeroMQSubscriber(String subscriberName, String ipAddress, String topic, int port){
		super(subscriberName);
		this.subscriberName = subscriberName;
		this.port = port;
		LOGGER.log(Level.INFO, "0MQ Subscriber " + this.subscriberName + ":" + this.port + " subscribing");
		setDaemon(true);
		context = ZMQ.context(1);
		subscriber = context.socket(ZMQ.SUB);
		subscriber.connect("tcp://" + ipAddress + ":" + port);
		LOGGER.log(Level.INFO, "0MQ Subscriber " + this.subscriberName + ":" + this.port + " subscribed");
		subscriber.subscribe(topic.getBytes());
	}
	
	public void finalize() throws Throwable{
		try{
			subscriber.close();
			context.term();
		}catch (Exception e) {
			e.printStackTrace();
        } finally {
        	LOGGER.log(Level.INFO, "0MQ Subscriber " + this.subscriberName + ":" + this.port + " closed");
        	super.finalize();
    	}

	}
    
    public void run(){
    	LOGGER.log(Level.INFO, "0MQ Subscriber " + this.subscriberName + ":" + this.port + " Listen mode");
		while(true){
			try {					
				if (!Thread.currentThread ().isInterrupted ()) {
					// Read message contents
					String contents = subscriber.recvStr();
					LOGGER.log(Level.INFO, "0MQ Subscriber " + this.subscriberName + ":" + this.port + " received msg: " +contents);
				}
			} catch (Exception e){
				LOGGER.log(Level.SEVERE, "0MQ Subscriber " + this.subscriberName + " failed running");
			}
		}
	}
}
