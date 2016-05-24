/* 
 * Copyright (C) 2014-2016 University of Stuttgart
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.profiler.rp.zeromq;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

import eu.paasage.upperware.profiler.rp.Constants;
import eu.paasage.upperware.profiler.rp.RuleProcessor;
import eu.paasage.upperware.profiler.rp.util.PropertiesReader;
import eu.paasage.upperware.profiler.rp.util.RPOutput;

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
 * - new CDO Identifier, e.g. upperware-models/1444391197664v2
 * - original CDO Identifier, e.g. upperware-models/1444391197664
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
     * @return the RuleProcessorService instance
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
	private static void processRequest(
			Socket subscriber,
			final String subscriberTopic,
			Socket publisher,
			final String publisherTopic)
	{
		logger.info("Waiting for incoming request ...");

		if (subscriber == null) {
			String error = "ZeroMQ subscriber socket not initialized";
			publishError(publisher, publisherTopic, error);
			logger.error(error);
			return;
		}

		if (publisher == null) {
			String error = "ZeroMQ publisher socket not initialized";
			publishError(publisher, publisherTopic, error);
			logger.error(error);
			return;
		}

		String requestType = subscriber.recvStr();
		if (!requestType.equals(subscriberTopic)) {
			String error = "ZeroMQ init error: Subscriber socket is missing";
			publishError(publisher, publisherTopic, error);
			logger.error(error);
			return;
		}

		String camelModel = null;
		if (subscriber.hasReceiveMore()) {
			camelModel = subscriber.recvStr();
		} else {
			String error = "ZeroMQ init error: Subscriber socket is missing";
			publishError(publisher, publisherTopic, error);
			logger.error(error);
			return;
		}

		String cdoIdentifier = null;
		if (subscriber.hasReceiveMore()) {
			cdoIdentifier = subscriber.recvStr();
		} else {
			String error = "ZeroMQ init error: Subscriber socket is missing";
			publishError(publisher, publisherTopic, error);
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
		RPOutput output = rProcessor.processRequest(camelModel, cdoIdentifier, null, true);

		int success = output.getErrorCode();
		if (success == 1) {
			logger.info("RP passed. Publish onto topic " + publisherTopic);
			System.out.println("RP passed. Publish onto topic " + publisherTopic);
			publisher.sendMore(publisherTopic);
			publisher.sendMore(camelModel);
			publisher.sendMore(output.getCpModelId()); // new
			publisher.send(cdoIdentifier); // old
		} else {
			String error = "An error occurred while validating the model with the Rule Processor";
			publishError(publisher, publisherTopic, error);
		}
		
		logger.info("  > request processed.");
		System.out.println("  > request processed.");
	}

	private static void publishError(
			Socket publisher,
			final String publisherTopic,
			String errorMessage)
	{
		logger.error("Failure in RP: " + errorMessage);
		publisher.sendMore(publisherTopic);
		publisher.sendMore(ERROR);
		publisher.send(errorMessage);
	}
	
	private final String getSubscriberURL(Properties properties) {
		if (properties == null) {
			return null;
		}

		String subscriberProtocol = properties.getProperty("SUBSCRIBER_PROTOCOL", Constants.DEFAULT_SUBSCRIBER_PROTOCOL);
		String subscriberHost = properties.getProperty("SUBSCRIBER_HOST",Constants.DEFAULT_SUBSCRIBER_HOST);
		String subscriberPort = properties.getProperty("SUBSCRIBER_PORT",Constants.DEFAULT_SUBSCRIBER_PORT);

		StringBuilder subscriberURL = new StringBuilder();
		subscriberURL.append(subscriberProtocol);
		subscriberURL.append(subscriberHost);
		subscriberURL.append(":");
		subscriberURL.append(subscriberPort);
		
		return subscriberURL.toString();
	}

	private final String getPublisherURL(Properties properties) {
		if (properties == null) {
			return null;
		}

		String publisherProtocol = properties.getProperty("PUBLISHER_PROTOCOL", Constants.DEFAULT_PUBLISHER_PROTOCOL);
		String publisherHost = properties.getProperty("PUBLISHER_HOST",Constants.DEFAULT_PUBLISHER_HOST);
		String publisherPort = properties.getProperty("PUBLISHER_PORT",Constants.DEFAULT_PUBLISHER_PORT);

		StringBuilder publisherUrl = new StringBuilder();
		publisherUrl.append(publisherProtocol);
		publisherUrl.append(publisherHost);
		publisherUrl.append(":");
		publisherUrl.append(publisherPort);
		
		return publisherUrl.toString();
	}

	/**
	 * Initiate ZeroMQ service
	 */
    public void run() {
    	// [0] Read configuration parameters
    	Properties paasageProperties = PropertiesReader.loadPropertyFile();
		PropertyConfigurator.configure(paasageProperties);
				
		// [1] Create socket and subscribe to SUB_GROUP to receive new requests
		final String subscriberURL = getSubscriberURL(paasageProperties);
		final String subscriberTopic = paasageProperties.getProperty("SUBSCRIBER_TOPIC", Constants.DEFAULT_SUBSCRIBER_TOPIC);

		ZMQ.Context context = ZMQ.context(1);
		ZMQ.Socket subscriber = context.socket(ZMQ.SUB);
        subscriber.connect(subscriberURL);
        subscriber.subscribe(subscriberTopic.getBytes());
        StringBuilder sb = new StringBuilder();
        sb.append("Subcribed to ");
        sb.append(subscriberURL);
        sb.append(" for topic ");
        sb.append(subscriberTopic);
        System.out.println(sb);

		// [2] Bind publisher to the PUB_TCP_CONNECT
        final String publisherURL = getPublisherURL(paasageProperties);
		final String publisherTopic = paasageProperties.getProperty("PUBLISHER_TOPIC", Constants.DEFAULT_PUBLISHER_TOPIC);        
		
        ZMQ.Socket publisher = context.socket(ZMQ.PUB);
        try {
        	System.out.println("Trying to bind to " + publisherURL + " ...");
			publisher.bind(publisherURL);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		sb = new StringBuilder();
		sb.append("Now publishing to ");
		sb.append(publisherURL);
		sb.append(": CamelModelID, CPModelID (new), CPModelID (old)");
		System.out.println(sb);

		// [3] Process incoming requests
		while (!Thread.currentThread().isInterrupted()) {
			processRequest(subscriber, subscriberTopic, publisher, publisherTopic);
		}

		subscriber.close();
		context.term();
	}

}
