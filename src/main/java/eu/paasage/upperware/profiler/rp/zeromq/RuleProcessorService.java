/* 
 * Copyright (C) 2014-2015 University of Stuttgart
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.profiler.rp.zeromq;

import org.apache.log4j.Logger;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

import eu.paasage.upperware.profiler.rp.RuleProcessor;

/**
 * The RuleProcessor listens on the topic "startSolving", and expects the
 * following multi-part message:
 * 
 * topic: startSolving
 * - Camel Model, e.g. bewan
 * - CDO Identifier, e.g. upperware-models/1444391197664
 * 
 * The RuleProcessor send publishes on the topic "RPsolutionAvailable" the
 * following multi-part message:
 * 
 * topic: RPsolutionAvailable
 * - Camel Model, e.g. bewan
 * - CDO Identifier, e.g. upperware-models/1444391197664
 * 
 * If an error occurs, then the RuleProcessor will publish an error message on
 * the topic "RPSolutionAvailable". Please check if the key "ERROR" exists in
 * the incoming multi-part message:
 * 
 * topic: RPSolutionAvailable
 * - "ERROR"
 * - suitable error message
 * 
 * @author hopped
 *
 */
public class RuleProcessorService {

	/** TCP connection used for subscribing (ZeroMQ) **/
	public static final String SUB_TCP_CONNECT = "tcp://127.0.0.1:5544";

	/** ZeroMQ group we are subscribing to **/
	public static final String SUB_GROUP = "startSolving";

	/** TCP connection used for publishing (ZeroMQ) **/
	public static final String PUB_TCP_CONNECT = "tcp://127.0.0.1:5545";

	/** ZeroMQ group we are publishing onto **/
	public static final String PUB_GROUP = "RPsolutionAvailable";

	/** Key identifying an error **/
	public static final String ERROR = "RP_ERROR";

	/** Logging via Log4j **/
	private final static Logger logger = Logger.getLogger(RuleProcessorService.class);

	/** Singleton instance **/
    private static RuleProcessorService instance;

    private RuleProcessorService() {
    	// nothing to initialize
    }

    /**
     * Returns a singleton-instance of the RuleProcessorService.
     * 
     * @return
     */
    public static RuleProcessorService getInstance() {
        if (instance == null) {
            instance = new RuleProcessorService();
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
	@SuppressWarnings("deprecation")
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

		if (logger.isDebugEnabled()) {
			logger.debug("Received new incoming request");
			logger.debug("  > request type: " + requestType);
			logger.debug("  > camel model: " + camelModel);
			logger.debug("  > cdo identifier: " + cdoIdentifier);
		}

		RuleProcessor rProcessor = new RuleProcessor();
		int success = rProcessor.processRequest(camelModel, cdoIdentifier);
		if (success == 1) {
			logger.info("RP passed. Publish onto topic " + PUB_GROUP);
			System.out.println("RP passed. Publish onto topic " + PUB_GROUP);
			publisher.sendMore(PUB_GROUP);
			publisher.sendMore(camelModel);
			publisher.send(cdoIdentifier);
		} else {
			String error = "An error occurred while validating the model with the Rule Processor";
			publishError(publisher, error);
		}
		
		logger.info("  > request processed.");
		System.out.println("  > request processed.");
	}

	private static void publishError(Socket publisher, String errorMessage) {
		logger.error("Failure in RP: " + errorMessage);
		publisher.sendMore(PUB_GROUP);
		publisher.sendMore(ERROR);
		publisher.send(errorMessage);
	}

	/**
	 * @param args
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

}
