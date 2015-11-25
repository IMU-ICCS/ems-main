/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package eu.paasage.upperware.solvertodeployment.zeromq;

import org.apache.log4j.Logger;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

import eu.paasage.upperware.solvertodeployment.lib.S2DException;
import eu.paasage.upperware.solvertodeployment.lib.SolverToDeployment;

/**
 * The S2D listens on the topic "computeDeployment", and expects the
 * following multi-part message:
 * 
 * topic: computeDeployment
 * - Camel Model, e.g. bewan
 * - CDO Identifier, e.g. upperware-models/1444391197664
 * 
 * The S2D send publishes on the topic "newCamelDeploymentAvailable" the
 * following multi-part message:
 * 
 * topic: newCamelDeploymentAvailable
 * - Camel Model, e.g. bewan
 * 
 * If an error occurs, then the S2D will publish an error message on
 * the topic "newCamelDeploymentAvailable". Please check if the key "ERROR" exists in
 * the incoming multi-part message:
 * 
 * topic: newCamelDeploymentAvailable
 * - "ERROR"
 * - suitable error message
 * 
 * @author hopped (Author of RP version)
 * @author chris (Adaptation to S2D)
 */

public class S2D_ZMQ_Service {

	/** TCP connection used for subscribing (ZeroMQ) **/
	public static final String SUB_TCP_CONNECT = "tcp://127.0.0.1:5544";

	/** ZeroMQ group we are subscribing to **/
	public static final String SUB_GROUP = "computeDeployment";

	/** TCP connection used for publishing (ZeroMQ) **/
	public static final String PUB_TCP_CONNECT = "tcp://127.0.0.1:5546";

	/** ZeroMQ group we are publishing onto **/
	public static final String PUB_GROUP = "newCamelDeploymentAvailable";

	/** Key identifying an error **/
	public static final String ERROR = "S2D_ERROR";

	/** Logging via Log4j **/
	private static Logger logger = Logger.getLogger(SolverToDeployment.class);

	/** Singleton instance **/
    private static S2D_ZMQ_Service instance;

    private S2D_ZMQ_Service() {
    	// nothing to initialize
    }

    /**
     * Returns a singleton-instance of the RuleProcessorService.
     * 
     * @return the RuleProcessorService instance
     */
    public static S2D_ZMQ_Service getInstance() {
        if (instance == null) {
            instance = new S2D_ZMQ_Service();
        }

        return instance;
    }
    
	/**
	 * 
	 * @param subscriber
	 *            ZeroMQ subscriber socket
	 * @param publisher
	 *            ZeroMQ publisher socket
	 */
	private static void processRequest(Socket subscriber, Socket publisher) {
		logger.info("Waiting for incoming request ...");

		if (subscriber == null) {
			String error = "ZeroMQ subscriber socket not initialized";
			publishError(publisher, error);
			logger.error(error);
			return;
		}

		if (publisher == null) {
			String error = "ZeroMQ publisher socket not initialized";
			publishError(publisher, error);
			logger.error(error);
			return;
		}

		String requestType = subscriber.recvStr();
		if (!requestType.equals(SUB_GROUP)) {
			String error = "ZeroMQ init error: Subscriber socket is missing";
			publishError(publisher, error);
			logger.error(error);
			return;
		}

		String camelModel = null;
		if (subscriber.hasReceiveMore()) {
			camelModel = subscriber.recvStr();
		} else {
			String error = "ZeroMQ init error: Subscriber socket is missing";
			publishError(publisher, error);
			logger.error(error);
			return;
		}

		String cdoIdentifier = null;
		if (subscriber.hasReceiveMore()) {
			cdoIdentifier = subscriber.recvStr();
		} else {
			String error = "ZeroMQ init error: Subscriber socket is missing";
			publishError(publisher, error);
			logger.error(error);
			return;
		}
		
		String cpDirId = null;
		if (subscriber.hasReceiveMore()) {
			cpDirId = subscriber.recvStr();
		} else {
			String error = "ZeroMQ init error: Subscriber socket is missing";
			publishError(publisher, error);
			logger.error(error);
			return;
		}
		
		String msg;
		if (subscriber.hasReceiveMore()) {
			msg = subscriber.recvStr();
		} else {
			String error = "ZeroMQ init error: Subscriber socket is missing";
			publishError(publisher, error);
			logger.error(error);
			return;
		}
		long solutionTS = Long.valueOf(msg);

		if (logger.isDebugEnabled()) {
			logger.debug("Received new incoming request");
			logger.debug("  > request type: " + requestType);
			logger.debug("  > camel model: " + camelModel);
			logger.debug("  > cdo identifier: " + cdoIdentifier);
		}

		// Invoking actual S2D
		boolean res;
		try {
			res = SolverToDeployment.doWorkTS(cdoIdentifier, camelModel, cpDirId, solutionTS, true, 0, false, 0);
		} catch (S2DException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res=false;
		}		
		if (res) {
			logger.info("S2D passed. Publish onto topic " + PUB_GROUP);
			publisher.sendMore(PUB_GROUP);
			publisher.send(camelModel);
		} else {
			String error = "An error occurred while validating the model with the Rule Processor";
			publishError(publisher, error);
		}
		
		logger.info("  > request processed.");
	}

	private static void publishError(Socket publisher, String errorMessage) {
		logger.error("Failure in RP: " + errorMessage);
		publisher.sendMore(PUB_GROUP);
		publisher.sendMore(ERROR);
		publisher.send(errorMessage);
	}

	/**
	 *
	 */
    public void run() {
		// [1] Create socket and subscribe to SUB_GROUP to receive new requests
		ZMQ.Context context = ZMQ.context(1);
		ZMQ.Socket subscriber = context.socket(ZMQ.SUB);
		subscriber.connect(SUB_TCP_CONNECT);
		subscriber.subscribe(SUB_GROUP.getBytes());

		// [2] Bind publisher to the PUB_TCP_CONNECT
		ZMQ.Socket publisher = context.socket(ZMQ.PUB);
		publisher.bind(PUB_TCP_CONNECT);

		// [3] Process incoming requests
		while (!Thread.currentThread().isInterrupted()) {
			processRequest(subscriber, publisher);
		}

		subscriber.close();
		context.term();
	}

	public static void main(String[] args) throws Exception {
		S2D_ZMQ_Service.getInstance().run();
	}
}
