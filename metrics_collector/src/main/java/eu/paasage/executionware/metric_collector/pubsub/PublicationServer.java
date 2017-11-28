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

public class PublicationServer{
	private boolean run = true;
	private Socket socket;
	private Context context;
	private int threadNum = 1;
	
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(PublicationServer.class);
	
	public PublicationServer(){
		context = ZMQ.context(threadNum);
        socket = context.socket(ZMQ.PUB);
        socket.bind("tcp://*:5563");
	}
	
	public PublicationServer(int threadNum){
		this.threadNum = threadNum;
		context = ZMQ.context(threadNum);
        socket = context.socket(ZMQ.PUB);
        socket.bind("tcp://*:5563");
	}
	
	public PublicationServer(int threadNum,int portNum){
		this.threadNum = threadNum;
		context = ZMQ.context(threadNum);
        socket = context.socket(ZMQ.PUB);
        socket.bind("tcp://*:" + portNum);
	}
	
	public synchronized void submitValue(String metricId, double value){
		if (run){
			logger.info("PublicationServer submitting value: " + value + " for metricInstance: " + metricId);
			socket.sendMore(metricId);
			socket.send(new String(value + ""));
		}
	}
	
	public synchronized void terminate(){
		run = false;
		socket.close ();
        context.term ();
	}
}