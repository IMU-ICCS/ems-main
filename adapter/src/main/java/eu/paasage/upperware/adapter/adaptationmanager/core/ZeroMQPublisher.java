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

public class ZeroMQPublisher{
	
	private String publisherName;
	private int port;
	
	ZMQ.Context context;
	ZMQ.Socket publisher;
	
	private String topic;
	
	private String request;
	private String reply;
	private final static Logger LOGGER = Logger
		.getLogger(ZeroMQPublisher.class.getName());

	public ZeroMQPublisher(String publisherName, String topic, int port){
		this.publisherName = publisherName;
		this.topic = topic;
		this.port = port;
		LOGGER.log(Level.INFO, "0MQ Publisher " + this.publisherName + ":" + this.port + " starting");
		bindPublisher();
	}
	
	private void bindPublisher(){
		context = ZMQ.context(1);
		publisher = context.socket(ZMQ.PUB);
		publisher.bind("tcp://*:" + port);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		LOGGER.log(Level.INFO, "0MQ Publisher " + this.publisherName + ":" + this.port + " started");
	}
	
	/*public boolean publishMsg(String str){
		boolean status = false;
		if(!Thread.currentThread().isInterrupted()){
			if(publisher.send(str)){
				status = true;
				LOGGER.log(Level.INFO, "0MQ Publisher " + this.publisherName + ":" + this.port + " published msg: " + str);
			}
		}
		return status;
	}*/
	
	public boolean publishMsg(String[] messages){
		boolean status = true;
		
		if(!Thread.currentThread().isInterrupted()){
			
			if(publisher.sendMore(this.topic))
				status = status && true;
            else
            	status = status && false;
			
			
            for ( int i=0; i < messages.length; i++){
                String message = messages[i];
                if ( i == messages.length - 1){//last part of the message
                    if(publisher.send(message))
                    	status = status && true;
                    else
                    	status = status && false;
                }
                else {
                    if(publisher.sendMore(message))
                    	status = status && true;
                    else
                    	status = status && false;
                }
            }
            
            LOGGER.log(Level.INFO, "0MQ Publisher " + this.publisherName + ":" + this.port + " => topic " + this.topic + " published msg: " + String.join(" | ", messages));
		}
		
		return status;
	}
	
	public void finalize() throws Throwable{
		try{
			publisher.close();
			context.term();
		}catch (Exception e) {
			e.printStackTrace();
        } finally {
        	LOGGER.log(Level.INFO, "0MQ Publisher " + this.publisherName + ":" + this.port + " closed");
        	super.finalize();
    	}

	}
}
