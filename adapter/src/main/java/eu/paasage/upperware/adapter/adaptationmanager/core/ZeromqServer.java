/*
 * Copyright (c) 2014 INRIA, INSA Rennes
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.adaptationmanager.core;
import org.eclipse.net4j.util.event.IEvent;
import org.eclipse.net4j.util.event.IListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zeromq.ZMQ;

public class ZeromqServer extends Thread {
	
	private String request;
	private String reply;
	private final static Logger LOGGER = Logger
		.getLogger(ZeromqServer.class.getName());
		
	public ZeromqServer(){
		LOGGER.log(Level.INFO, "0MQ Server : starting");
		setDaemon(true);
	}
	
	public void zmqServer() throws Exception {
		LOGGER.log(Level.INFO, "Adaptation Manager is waiting for your commands ...");
        ZMQ.Context context = ZMQ.context(1);

        //  Socket to talk to clients
        ZMQ.Socket responder = context.socket(ZMQ.REP);
        responder.bind("tcp://*:5555");

        while (!Thread.currentThread().isInterrupted()) {
            // Wait for next request from the client
            byte[] req = responder.recv(0);
            System.out.println("Received: " + new String(req));
			
			String request = new String(req);
			
			//System.out.println(s);
			
			// Do some 'work'
            Thread.sleep(1000);
            
			if(request.equals("event")){
				LOGGER.log(Level.INFO, "0MQ simulating a CDO event ...");
				reply = "Received: New Event Command";
				responder.send(reply.getBytes(), 0);
				AdaptationManager.zMQResponder(request);
			}
			
			else if(request.equals("terminate")){
				LOGGER.log(Level.INFO, "AM is terminating");
				reply = "Received: Termination Command";
				responder.send(reply.getBytes(), 0);
				AdaptationManager.zMQResponder(request);
			}
			
			else if(request.equals("file3")){
				LOGGER.log(Level.INFO, "Scalarm_full3.xmi will be used as the target plan ...");
				reply = "Scalarm_full3.xmi will be used as the target plan";
				responder.send(reply.getBytes(), 0);
			}			
			else{
            // Send reply back to client
            String reply = "Your command is received by the server";
            responder.send(reply.getBytes(), 0);
			}
        }
        responder.close();
        context.term();
    }
    
    public void run(){
		while(true){
			try {
				this.zmqServer();
			} catch (Exception e){
				LOGGER.log(Level.SEVERE, "0MQ Server faild runnig");
			}
		}
	}
	
	public String getRequest(){
		return request;
	}
	
}
