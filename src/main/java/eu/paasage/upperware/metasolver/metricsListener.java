
/*
 * Copyright (c) 2014-5 UK Science and Technology Facilities Council
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.metasolver;

//package org.ow2.paasage.upperware.metasolver;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;
import org.jeromq.*;
//The metasolver is responsible for calling the different solvers in PaaSage.



public class metricsListener implements Runnable{

	private Thread t;
	private String threadName = "metricsListener";
	private Context context1;
	private Context context2;
	private Context context3;
	private Socket socket1;

	private Socket socket2;

	private Socket socket3;

	private String metricId;
	private boolean run = true;

	static ZMQ zmq = new ZMQ();
	static ZMQ ZMQ = new ZMQ();
	public metricsListener(String metricId){


		this.metricId = metricId;
		context1 = ZMQ.context(1);
		socket1 = context1.socket(ZMQ.SUB);
		socket1.connect("tcp://localhost:5545");
		socket1.subscribe("B_2".getBytes());

	


	}

	public synchronized void terminate(){
		run = false;
	}

	public void run() {
		while (run) {

			System.out.println("ML: lets go for a ML subscription .....");
			System.out.println("ML: setting  ML context ....");	
			Context cntx1 = zmq.context(1);
		
			System.out.println("ML: context ML set .....");
			Socket	subscriber1 = cntx1.socket (zmq.SUB);
		
			System.out.println("ML: socket  ML set .....");
			subscriber1.connect ("tcp://localhost:5545");
		

			System.out.println("ML: connection ML set .....");
			subscriber1.subscribe("B_2".getBytes());//listening to meessages on ...
			System.out.println("ML: subscription ML done .....");
			while (!Thread.currentThread ().isInterrupted () && subscriber1.recvStr() != null) {
				
					System.out.println("got1");
				// Read envelope with address

				String address1 = subscriber1.recvStr ();
				String contents1 = subscriber1.recvStr ();
				System.out.println(" message contents " + address1 + " : " + contents1); 
			}
				subscriber1.close ();
			cntx1.term ();
		}
	}
	 public void start ()
	   {
	      System.out.println("Starting " +  threadName );
	      if (t == null)
	      {
	         t = new Thread (this, threadName);
	         t.start ();
	      }
	   }

}