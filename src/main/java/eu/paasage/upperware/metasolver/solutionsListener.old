
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



public class solutionsListener implements Runnable{

	private Thread t;
	private String threadName = "solution Listner";
	
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
	public solutionsListener(String metricId){


		this.metricId = metricId;
		context1 = ZMQ.context(1);
		socket1 = context1.socket(ZMQ.SUB);
		socket1.connect("tcp://localhost:5545");
		socket1.subscribe("B_2".getBytes());

		context2 = ZMQ.context(1);
		socket2 = context2.socket(ZMQ.SUB);
		socket2.connect("tcp://localhost:5543");
		socket2.subscribe("B_3".getBytes());

		context3 = ZMQ.context(1);
		socket3 = context3.socket(ZMQ.SUB);
		socket3.connect("tcp://localhost:5565");
		socket3.subscribe("B_4".getBytes());


	}

	public synchronized void terminate(){
		run = false;
	}

	public void run() {
		while (run) {

			System.out.println("SL: lets go for a subscription .....");
			System.out.println("SL: setting context ....");	
			Context cntx1 = zmq.context(1);
			Context cntx2 = zmq.context(1);
			Context cntx3 = zmq.context(1);
			System.out.println("SL: context set .....");
			Socket	subscriber1 = cntx1.socket (zmq.SUB);
			Socket	subscriber2 = cntx2.socket (zmq.SUB);
			Socket	subscriber3 = cntx3.socket (zmq.SUB);
			System.out.println("SL: socket set .....");
			subscriber1.connect ("tcp://localhost:5545");
			subscriber2.connect ("tcp://localhost:5546");
			subscriber3.connect ("tcp://localhost:5547");

			System.out.println("SL: connection set .....");
			subscriber1.subscribe("B_2".getBytes());//listening to meessages on ...
			subscriber2.subscribe("B_3".getBytes());//listening to meessages on ...
			subscriber3.subscribe("B_4".getBytes());//listening to meessages on ...
			System.out.println("SL: subscription done .....");

			while (!Thread.currentThread ().isInterrupted () && subscriber1.recvStr() != null) {
				System.out.println("got1");
				// Read envelope with address

				String address1 = subscriber1.recvStr ();
				String contents1 = subscriber1.recvStr ();
				System.out.println(" bell1 " + address1 + " : " + contents1);  	            	
			}

			while (!Thread.currentThread ().isInterrupted () && subscriber2.recvStr() != null) {
				System.out.println("got2");
				// Read envelope with address

				String address2 = subscriber1.recvStr ();
				String contents2 = subscriber1.recvStr ();
				System.out.println(" bell2 " + address2 + " : " + contents2);  	            	
			}

			while (!Thread.currentThread ().isInterrupted () && subscriber1.recvStr() != null) {
				System.out.println("listening");
				// Read envelope with address

				String address3 = subscriber1.recvStr ();
				String contents3 = subscriber1.recvStr ();
				System.out.println(" bell3 " + address3 + " : " + contents3);  	            	
			}
			subscriber1.close ();
			subscriber2.close ();
			subscriber3.close ();
			cntx1.term ();
			cntx2.term ();
			cntx3.term ();
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