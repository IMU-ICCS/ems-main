
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

import eu.paasage.upperware.metasolver.exception.MetricMapperException;



public class RPListener implements Runnable{

	private Thread t;
	private String threadName = "RPListener";


	private Context context1;
	private Socket socket1;


	private String metricId;
	private boolean run = true;

	static ZMQ zmq = new ZMQ();
	public RPListener(String metricId){


		this.metricId = metricId;
		context1 = ZMQ.context(1);
		socket1 = context1.socket(ZMQ.SUB);
		socket1.connect("tcp://localhost:5545");
		socket1.subscribe("RPsolutionAvailable".getBytes());




	}

	public synchronized void terminate(){
		run = false;
	}

	public void run() {
		while (run) {
			String contents1 = null;
			String contents2 = null;
			metasolver mslv = new metasolver();
			System.out.println("lets go for a subscription .....");
			System.out.println("setting context ....");	
			Context cntx1 = zmq.context(1);

			System.out.println("context set .....");
			Socket	subscriber1 = cntx1.socket (zmq.SUB);

			System.out.println("socket set .....");
			subscriber1.connect ("tcp://localhost:5545");


			System.out.println("connection set .....");
			subscriber1.subscribe("RPsolutionAvailable".getBytes());//listening to meessages on ...
			System.out.println("subscription done .....");
			while (!Thread.currentThread ().isInterrupted () && subscriber1.recvStr() != null) {

				System.out.println("got1");
				// Read envelope with address

				String address1 = subscriber1.recvStr ();
				contents1 = subscriber1.recvStr ();
				contents2 = subscriber1.recvStr ();
				System.out.println(" Rule Processor Notification " + address1 + " : " + contents1 + " " + contents2);  	            	}
			//invoke MILP Solver -- hardwired as only solver present
			solutionPublisherMQ spq = new solutionPublisherMQ();
			try {
				spq.MILPpubMQ(contents1);
				spq.MILPpubMQ(contents2);
			} catch (MetricMapperException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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