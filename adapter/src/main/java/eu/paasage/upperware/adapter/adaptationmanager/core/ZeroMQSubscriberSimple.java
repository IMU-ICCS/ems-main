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

public class ZeroMQSubscriberSimple{
	
	private String subscriberName;
	private String ipAddress;
	private String topic;
	private int port;
	
	private String lastMessage;
	
	ZMQ.Context context;
	ZMQ.Socket subscriber;
	
	private final static Logger LOGGER = Logger
		.getLogger(ZeroMQSubscriberSimple.class.getName());
		
	public ZeroMQSubscriberSimple(String subscriberName, String ipAddress, String topic, int port){
		this.subscriberName = subscriberName;
		this.ipAddress = ipAddress;
		this.topic = topic;
		this.port = port;
		//LOGGER.log(Level.INFO, "0MQ Subscriber " + this.subscriberName + ":" + this.port + " subscribing");
		context = ZMQ.context(1);
		subscriber = context.socket(ZMQ.SUB);
		subscriber.connect("tcp://" + this.ipAddress + ":" + this.port);
		LOGGER.log(Level.INFO, "0MQ Subscriber " + this.subscriberName + ":" + this.port + " subscribed");
		subscriber.subscribe(topic.getBytes());
	}
	
	public boolean hasMoreMessage(){
		return subscriber.hasReceiveMore();
	}
	
	public String readMessage(boolean saveMessage){
		String message = subscriber.recvStr(ZMQ.DONTWAIT);
		if(message != null && saveMessage){
			this.lastMessage = message;
			return message;
		}else if(message != null && !saveMessage){
			return message;
		}
		else
			return "";
	}
	
	public String getLastMessage(){return this.lastMessage;}
}
