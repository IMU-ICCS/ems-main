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

        recSocket.connect("tcp://localhost:" + ADAPTER_PORT);
        recSocket.subscribe("startSolving".getBytes(ZMQ.CHARSET));
        
        servContext = ZMQ.context(1);
        servSocket = servContext.socket(ZMQ.PUB);
        servSocket.bind("tcp://*:" + SOLVER_PORT);
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
			logger.info("Waiting to receive message");
            // Read envelope with address
            String address = recSocket.recvStr();
            // Read message contents
            String cpModelRef = recSocket.recvStr();
            logger.info("Got new problem to solve: " + address + " : " + cpModelRef);
            CPSolver cp = new CPSolver(cpModelRef,null);
            boolean hasSolution = false;
            try{
            	hasSolution = cp.solve();
            	logger.info("Solution has been produced");
            }
            catch(Exception e){
            	logger.error("Something went wrong while trying to solve the problem",e);
            	//e.printStackTrace();
            }
            
            if (hasSolution){
            	logger.info("Got new solution and publishing it");
	            servSocket.sendMore("CPSolutionAvailable");
				servSocket.send(cpModelRef);
            }
        }
        servSocket.close();
        recSocket.close();
        servContext.term ();
        recContext.term ();
	}
}