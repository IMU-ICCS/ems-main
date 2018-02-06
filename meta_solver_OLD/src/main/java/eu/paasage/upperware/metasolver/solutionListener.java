
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

import eu.paasage.upperware.metasolver.exception.MetricMapperException;



public class solutionListener implements Runnable{
	private String CAMELmodel = null;
	private String CPmodel = null;
	private long timestamp = 0;
	private Thread t;
	private String threadName = "solutionListener";
	private Context context1;
	private Context context2;
	private Context context3;
	private Socket socket1;

	private Socket socket2;

	private Socket socket3;

	private String metricId;
	private boolean run = true;
	
	public solutionListener(String CAMELmodel, String CPmodel, long timestamp){
        this.CAMELmodel = CAMELmodel;
        this.CPmodel = CPmodel;
        this.timestamp = timestamp;
        Context cntx1 = ZMQ.context(1);
		Context cntx2 = ZMQ.context(1);
		Context cntx3 = ZMQ.context(1);
		System.out.println("context set .....");
		Socket	subscriber1 = cntx1.socket (ZMQ.SUB);
		Socket	subscriber2 = cntx2.socket (ZMQ.SUB);
		Socket	subscriber3 = cntx3.socket (ZMQ.SUB);
		System.out.println("socket set .....");
		subscriber1.connect ("tcp://localhost:5540");
		subscriber2.connect ("tcp://localhost:5554");
		subscriber3.connect ("tcp://localhost:5541");

	}

	public synchronized void terminate(){
		run = false;
	}

	public void run() {
		while (run) {
		
		    solutionPublisherMQ spq = new solutionPublisherMQ();
			System.out.println("lets go for a subscription .....");
			System.out.println("setting context ....");	
			Context cntx1 = ZMQ.context(1);
			Context cntx2 = ZMQ.context(1);
			Context cntx3 = ZMQ.context(1);
			System.out.println("context set .....");
			Socket	subscriber1 = cntx1.socket (ZMQ.SUB);
			Socket	subscriber2 = cntx2.socket (ZMQ.SUB);
			Socket	subscriber3 = cntx3.socket (ZMQ.SUB);
			System.out.println("socket set .....");
			subscriber1.connect ("tcp://localhost:5540");
			subscriber2.connect ("tcp://localhost:5554");
			subscriber3.connect ("tcp://localhost:5541");

			System.out.println("connection set .....");
			subscriber1.subscribe("MILPsolutionAvailable".getBytes());//listening to meessages on ...
			subscriber2.subscribe("CPsolutionAvailable".getBytes());//listening to meessages on ...
			subscriber3.subscribe("SIMULATORsolutionAvailable".getBytes());//listening to meessages on ...
			System.out.println("subscription done .....");
			
			while (!Thread.currentThread ().isInterrupted () && subscriber1.recvStr() != null) {
				System.out.println("got MILP Solution");
				// Read envelope with address
				
				String address1 = subscriber1.recvStr ();
				String contents1 = subscriber1.recvStr ();
				System.out.println("MILP solution " + address1 + " : " + contents1);
                System.out.println("CAMEL MODEL = " + CAMELmodel + " and CPModel = " + CPmodel);
			    try {
					spq.S2D(CAMELmodel, contents1, timestamp);
				} catch (MetricMapperException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			while (!Thread.currentThread ().isInterrupted () && subscriber2.recvStr() != null) {
				System.out.println("got CP Solution");
				// Read envelope with address

				String address2 = subscriber1.recvStr ();
				String contents2 = subscriber1.recvStr ();
				System.out.println(" CP solution " + address2 + " : " + contents2);  	            	
			//		sp.S2D(contents2);
			}

			while (!Thread.currentThread ().isInterrupted () && subscriber1.recvStr() != null) {
				System.out.println("got Simulation Solver Solution");
				// Read envelope with address

				String address3 = subscriber1.recvStr ();
				String contents3 = subscriber1.recvStr ();
				System.out.println("Simulated Solver Solution " + address3 + " : " + contents3);  	            	
				//	sp.S2D(contents3);
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