package eu.paasage.executionware.metric_collector;
/* Copyright (C) 2015 KYRIAKOS KRITIKOS <kritikos@ics.forth.gr> */

/* This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ 
 */



import org.eclipse.emf.cdo.common.id.CDOID;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

/**
* Fake Adapter which is used for testing purposes to send requests for solving CP problems
*/

public class FakeAdapterPublisher implements Runnable{
	private Context context;
	private Socket socket;
	private boolean run = true;
	private CDOID ecID = null;
	
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(FakeAdapterPublisher.class);
	
	//Creates receiving and sending sockets on different ports
	public FakeAdapterPublisher(CDOID ecID){      
        context = ZMQ.context(1);
        socket = context.socket(ZMQ.PUB);
        socket.bind("tcp://*:5550");
        logger.info("Init call finished for FakeAdapterPublisher");
        this.ecID = ecID;
	}
	
	public synchronized void terminate(){
		run = false;
	}

	public void run() {
		int times = 0;
		while (run) {
            socket.sendMore("startCollection");
			socket.send(ecID.toURIFragment());
			logger.info("Have sent the ecID for starting the collection of metrics");
			try{
				Thread.sleep(10000);
				times++;
				if (times == 2) break;
			}
			catch(Exception e){
				logger.error("Thread interrupted",e);
				//e.printStackTrace();
				break;
			}
        }
        socket.close();
        context.term();
	}
}