package eu.paasage.upperware.solvertodeployment.zeromq;

import org.apache.log4j.Logger;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

import eu.paasage.upperware.solvertodeployment.lib.S2DException;
import eu.paasage.upperware.solvertodeployment.lib.SolverToDeployment;

public class S2D_ZeroMQServer 
{

	private static Logger logger = Logger.getLogger(SolverToDeployment.class);

	//PROPERTY NAMES
	
	private final static String SUBSCRIBER_PORT_PROPERTY = "zeromqSubscriberPort";
	
	private final static String SUSBSCRIBER_HOST_PROPERTY= "zeromqSubscriberHostName";
	
	private final static String SUBSCRIBER_TOPIC_PROPERTY= "zeromqSubscriberTopicName";

	private final static String PUBLISHER_PORT_PROPERTY  = "zeromqPublisherPort";
	
	private final static String PUBLISHER_TOPIC_PROPERTY ="zeromqPublisherTopicName";
		
	//DEFAULT VALUES
	
	private final static String DEFAULT_SUBSCRIBER_PORT="5544"; 
	
	private final static String DEFAULT_PUBLISHER_PORT="5546"; 
	
	private final static String DEFAULT_PROTOCOL="tcp://*:"; 
	
	private final static String DEFAULT_PROTOCOL_SUBS="tcp://"; 
	
	private final static String DEFAULT_HOST_SUBS="localhost";
		
	private final static String DEFAULT_SUBSCRIBE_TOPIC="SolutionAvailable";
	
	private final static String DEFAULT_PUBLISH_TOPIC="newCamelDeploymentAvailable";
	
	
	//Property Manager
	private PaaSagePropertyManager propertyManager;

	/** Singleton instance **/
    private static S2D_ZeroMQServer instance;

    private S2D_ZeroMQServer() {
    	propertyManager= PaaSagePropertyManager.getInstance();
    }

    /**
     * Returns a singleton-instance of the S2DService.
     * 
     * @return the S2DService instance
     */
    public static S2D_ZeroMQServer getInstance() {
        if (instance == null) {
            instance = new S2D_ZeroMQServer();
        }

        return instance;
    }
    
	/**
	 * Executes the Server. The arguments can be defined via the property file wp3_cp_generator.properties
	 */
	public void run() 
	{
		
		String host= DEFAULT_HOST_SUBS;
		
		String portSubs= DEFAULT_SUBSCRIBER_PORT;
		
		String subsTopic= DEFAULT_SUBSCRIBE_TOPIC;
		
		if(propertyManager.getS2DProperty(SUSBSCRIBER_HOST_PROPERTY)!=null && !propertyManager.getS2DProperty(SUSBSCRIBER_HOST_PROPERTY).equals(""))
		{
			host= propertyManager.getS2DProperty(SUSBSCRIBER_HOST_PROPERTY);
		}
		
		if(propertyManager.getS2DProperty(SUBSCRIBER_PORT_PROPERTY)!=null && !propertyManager.getS2DProperty(SUBSCRIBER_PORT_PROPERTY).equals(""))
		{
			portSubs= propertyManager.getS2DProperty(SUBSCRIBER_PORT_PROPERTY);
		}
		
		if(propertyManager.getS2DProperty(SUBSCRIBER_TOPIC_PROPERTY)!=null && !propertyManager.getS2DProperty(SUBSCRIBER_TOPIC_PROPERTY).equals(""))
		{
			subsTopic= propertyManager.getS2DProperty(SUBSCRIBER_TOPIC_PROPERTY);
		}
		
		String urlSubs= DEFAULT_PROTOCOL_SUBS+host+":"+portSubs;
		
		String portPubs= DEFAULT_PUBLISHER_PORT;
		
		String publishTopic= DEFAULT_PUBLISH_TOPIC;
		
		
		if(propertyManager.getS2DProperty(PUBLISHER_PORT_PROPERTY)!=null && !propertyManager.getS2DProperty(PUBLISHER_PORT_PROPERTY).equals(""))
		{
			portPubs= propertyManager.getS2DProperty(PUBLISHER_PORT_PROPERTY);
		}
		
		if(propertyManager.getS2DProperty(PUBLISHER_TOPIC_PROPERTY)!=null && !propertyManager.getS2DProperty(PUBLISHER_TOPIC_PROPERTY).equals(""))
		{
			publishTopic = propertyManager.getS2DProperty(PUBLISHER_TOPIC_PROPERTY);
		}
		
		
		String urlPubs= DEFAULT_PROTOCOL+portPubs; 
		
		ZMQ.Context context = ZMQ.context(1);

        //  Socket to receive info from publishers
        ZMQ.Socket subscriber = context.socket(ZMQ.SUB);
        subscriber.connect(urlSubs);
        subscriber.subscribe(subsTopic.getBytes());
        System.out.println("Subscribed to "+urlSubs+" and topic "+subsTopic);
        // Socket to publish info
        ZMQ.Socket publisher = context.socket(ZMQ.PUB);
        publisher.bind(urlPubs);

        System.out.println("Publishing to "+urlPubs+" and camel model id & cp model id to topic "+publishTopic);
        
        String modelId="";
        
        while (!Thread.currentThread().isInterrupted()) {
        	
        	try{
    			processRequest(subscriber, publisher, subsTopic, publishTopic);
        	}
        	catch(Exception e)
        	{
        		System.out.println("Problems dealing with the camel model"+modelId+". Error Message "+e.getMessage());
        		continue;
        	}
        }
        subscriber.close();
        publisher.close();
        context.term();
	}

	/**
	 * 
	 * @param subscriber
	 *            ZeroMQ subscriber socket
	 * @param publisher
	 *            ZeroMQ publisher socket
	 */
	private static void processRequest(Socket subscriber, Socket publisher, String subTopic, String pubTopic) {
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
		if (!requestType.equals(subTopic)) {
			String error = "ZeroMQ: wrong topic "+requestType;
			publishError(publisher, error);
			logger.error(error);
			return;
		}

		String camelModel = null;
		if (subscriber.hasReceiveMore()) {
			camelModel = subscriber.recvStr();
		} else {
			String error = "ZeroMQ: error when trying to read camel model ID";
			publishError(publisher, error);
			logger.error(error);
			return;
		}

		String cdoIdentifier = null;
		if (subscriber.hasReceiveMore()) {
			cdoIdentifier = subscriber.recvStr();
		} else {
			String error = "ZeroMQ: error when trying to read cp ID";
			publishError(publisher, error);
			logger.error(error);
			return;
		}
		
		String cpDirId = null;
		if (subscriber.hasReceiveMore()) {
			cpDirId = subscriber.recvStr();
		} else {
			String error = "ZeroMQ: error when trying to read cp dir id (from CP)";
			publishError(publisher, error);
			logger.error(error);
			return;
		}
		
		String msg;
		long solutionTS;
		boolean solutionAvailable;
		if (subscriber.hasReceiveMore()) {
			msg = subscriber.recvStr();
			solutionTS = Long.valueOf(msg);
			solutionAvailable = true;
			logger.info("S2D founds a timestamp in zeromq: " + solutionTS);
		} else {
			solutionTS = 0;
			solutionAvailable = false;
			logger.info("S2D do not find a timestamp in zeromq; using highest ts");
//			String error = "ZeroMQ: error when trying to read time stamp";
//			publishError(publisher, error);
//			logger.error(error);
//			return;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Received new incoming request");
			logger.debug("  > request type: " + requestType);
			logger.debug("  > camel model: " + camelModel);
			logger.debug("  > cdo identifier: " + cdoIdentifier);
		}

		// Invoking actual S2D
		boolean res;
		try {
			res = SolverToDeployment.doWorkTS(cdoIdentifier, camelModel, cpDirId, solutionTS, solutionAvailable, 0, false, 0);
		} catch (S2DException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res=false;
		}		
		if (res) {
			logger.info("S2D passed. Publish onto topic " + pubTopic);
			publisher.sendMore(pubTopic);
			publisher.send(camelModel);
		} else {
			String error = "An error occurred while validating the model with the Rule Processor";
			publishError(publisher, error);
		}
		
		logger.info("  > request processed.");
	}

	private static void publishError(Socket publisher, String errorMessage) {
		logger.error("Failure in S2D: " + errorMessage);
		publisher.sendMore("ERROR");
		publisher.sendMore("S2D");
		publisher.send(errorMessage);
	}

	public static void main(String[] args) throws Exception {
		S2D_ZeroMQServer.getInstance().run();
	}
}
