/* Copyright (C) 2015 KYRIAKOS KRITIKOS <kritikos@ics.forth.gr> */

/* This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ 
 */



import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

/**
* The Daemon of the CPSolver which listens to requests for new solvings of 
* a cp model and publishes the availability of the solution as a reference
* to the cp model which has been modified
*/

public class CPSolverDaemon implements Runnable{
	private Context recContext, servContext;
	private Socket recSocket, servSocket;
	private static final int ADAPTER_PORT = 5550;
	private static final int SOLVER_PORT = 5554;
	private boolean run = true;
	
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(CPSolverDaemon.class);
	
	/* Creates receiving and sending sockets on different ports */
	public CPSolverDaemon(){
		recContext = ZMQ.context(1);
        recSocket = recContext.socket(ZMQ.SUB);

        recSocket.connect("tcp://localhost:" + SOLVER_PORT);
        recSocket.subscribe("startSolving".getBytes(ZMQ.CHARSET));
        
        servContext = ZMQ.context(1);
        servSocket = servContext.socket(ZMQ.PUB);
        servSocket.bind("tcp://*:" + ADAPTER_PORT);
        logger.info("Init call finished for CPSolverDaemon");
	}
	
	/* Terminating the run of this thread */
	public synchronized void terminate(){
		run = false;
	}

	/* Main method of this thread which reads a new problem, solves it and then publishes 
	 * the solution (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		while (run) {
			String topic="not set";
			String camelModelRef="not set";
			String cpModelRef="not set";
			long timestamp = -1;
			CPSolver cp;
			logger.info("Waiting to receive message");
			try
			{
				// Read envelope with topic
				topic = recSocket.recvStr();
				// Read message contents -- camelModel
				if (recSocket.hasReceiveMore())
				{
					camelModelRef = recSocket.recvStr();
				} else throw new Exception("CamelModel is missing");
				// Read message contents -- cpModel
				if (recSocket.hasReceiveMore())
				{
					cpModelRef = recSocket.recvStr();
				} else throw new Exception("cpModel is missing");
				// Read message contents -- timestamp (optional?)
				if (recSocket.hasReceiveMore())
				{
					String timestampStr = recSocket.recvStr();
					if (timestampStr != null){
						timestamp = new Long(timestampStr);
					}            	
					cp = new CPSolver(cpModelRef,null, timestamp);
				} else
				{
					cp = new CPSolver(cpModelRef,null);            	
				}
			}	catch (Exception e) {
	            logger.error("Got an exception: "+e.getMessage());
	            continue;
			}

            logger.info("Got new problem to solve: " + topic + " : " + camelModelRef + " / " + cpModelRef + " / "+ timestamp);
            boolean hasSolution = false;
            try{
            	hasSolution = cp.solve();
            	if (hasSolution) logger.info("Solution has been produced");
            	else logger.info("Problem is infeasible");
            }
            catch(Exception e){
            	logger.error("Something went wrong while trying to solve the problem",e);
            	//e.printStackTrace();
            }
            
            if (hasSolution){
            	logger.info("Got new solution and publishing it");
	            servSocket.sendMore("CPSolutionAvailable");
				servSocket.sendMore(camelModelRef);
				servSocket.send(cpModelRef);
            }
            else{
            	logger.info("Warning subscriber for problem infeasibility");
	            servSocket.sendMore("CPSolutionAvailable");
				servSocket.sendMore(camelModelRef);
				servSocket.send("");
            }
        }
        servSocket.close();
        recSocket.close();
        servContext.term ();
        recContext.term ();
	}
}