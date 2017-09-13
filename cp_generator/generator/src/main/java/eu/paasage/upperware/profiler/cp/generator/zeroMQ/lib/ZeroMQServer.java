package eu.paasage.upperware.profiler.cp.generator.zeroMQ.lib;

import java.nio.charset.StandardCharsets;

import eu.paasage.upperware.profiler.cp.generator.OrchestratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.zeromq.ZMQ;

import eu.paasage.upperware.profiler.cp.generator.model.tools.PaaSagePropertyManager;

import static eu.passage.upperware.commons.MelodicConstants.CDO_SERVER_PATH;

@Slf4j
public class ZeroMQServer 
{
	
	//PROPERTY NAMES
	private static String SUBSCRIBER_PORT_PROPERTY= "zeromqSubscriberPort";
	private static String HOST_SUSBSCRIBER_PROPERTY= "zeromqSubscriberHostName";
	private static String SUBSCRIBER_TOPIC_PROPERTY= "zeromqSubscriberTopicName";
	private static String PUBLISHER_PORT_PROPERTY= "zeromqPublisherPort";
	private static String PUBLISHER_TOPIC_CP_MODEL_ID_PROPERTY=	"zeromqPublisherrCPModelID";
	private static String PUBLISHER_TOPIC_CAMEL_MODEL_ID_PROPERTY= "zeromqPublisherrCamelModelID";
	
	//DEFAULT VALUES
	private static String DEFAULT_SUBSCRIBER_PORT="5555";
	private static String DEFAULT_PUBLISHER_PORT="5544";
	private static String DEFAULT_PROTOCOL="tcp://*:";
	private static String DEFAULT_PROTOCOL_SUBS="tcp://";
	private static String DEFAULT_HOST_SUBS="localhost";
	private static String DEFAULT_TOPIC_SUBS="ID";
	private static String DEFAULT_CP_MODEL_ID_TOPIC="startSolving";
	private static String DEFAULT_CAMEL_MODEL_ID_TOPIC="camelModelId";

	//Property Manager
	private static PaaSagePropertyManager propertyManager= PaaSagePropertyManager.getInstance();
	
	
	/**
	 * Executes the Server. The arguments can be defined via the property file wp3_cp_generator.properties
	 */
	public static void main(String[] args) 
	{
		String host= getPropertyValue(HOST_SUSBSCRIBER_PROPERTY, DEFAULT_HOST_SUBS);
		String portSubs= getPropertyValue(SUBSCRIBER_PORT_PROPERTY, DEFAULT_SUBSCRIBER_PORT);
		String subsTopic= getPropertyValue(SUBSCRIBER_TOPIC_PROPERTY, DEFAULT_TOPIC_SUBS);
		String urlSubs= DEFAULT_PROTOCOL_SUBS+host+":"+portSubs;

		String portPubs = getPropertyValue(PUBLISHER_PORT_PROPERTY, DEFAULT_PUBLISHER_PORT);
		String cpModelIdTopic = getPropertyValue(PUBLISHER_TOPIC_CP_MODEL_ID_PROPERTY, DEFAULT_CP_MODEL_ID_TOPIC);
		String camelModelIdTopic = getPropertyValue(PUBLISHER_TOPIC_CAMEL_MODEL_ID_PROPERTY, DEFAULT_CAMEL_MODEL_ID_TOPIC);
		String urlPubs= DEFAULT_PROTOCOL+portPubs;


		ZMQ.Context context = ZMQ.context(1);

		//  Socket to receive info from publishers
		ZMQ.Socket subscriber = createSubscriber(context, urlSubs, subsTopic);

		// Socket to publish info
		ZMQ.Socket publisher = createPublisher(context, urlPubs, camelModelIdTopic, cpModelIdTopic);


		while (!Thread.currentThread().isInterrupted()) {
			String modelId="";

        	try{
	            // Wait for next message from the publisher
	        	log.debug("Waiting for Model Id");
	        	modelId = getModelId(subscriber);
	            log.debug("model id "+modelId);

	            String paasageConfigID = OrchestratorUtils.generateCPModel(modelId);

				if(!"".equals(paasageConfigID)) {
					log.debug("CP Model Generated");

					publisher.sendMore(cpModelIdTopic);
					publisher.sendMore(modelId);
		            publisher.send(CDO_SERVER_PATH+paasageConfigID);
		            log.debug("CP Model Id sent "+CDO_SERVER_PATH+paasageConfigID);

		            //EC
		            publisher.sendMore(camelModelIdTopic);
		            publisher.sendMore(modelId);
		            publisher.send(CDO_SERVER_PATH+paasageConfigID);
				} else {
					String errorMessage = "Le CP Model was not generated";
					log.debug(errorMessage);
					sendError(publisher, camelModelIdTopic, modelId, errorMessage);
				}
        	}
        	catch(Exception e) {
				String errorMessage = "Problems dealing with the camel model" + modelId + ". Le CP Model was not generated. Error Message " + e.getMessage();
				log.debug(errorMessage);
				sendError(publisher, camelModelIdTopic, modelId, errorMessage);
        	}
        }
        subscriber.close();
        publisher.close();
        context.term();
	}

	private static void sendError(ZMQ.Socket publisher, String camelModelIdTopic, String modelId, String message) {
		publisher.sendMore(camelModelIdTopic);
		publisher.sendMore(modelId);
		publisher.send("ERROR: " + message);
	}

	private static String getModelId(ZMQ.Socket subscriber) {
		byte[] request = subscriber.recv();		//TODO - is this necessary
		request = subscriber.recv();
		return new String(request, StandardCharsets.UTF_8);
	}

	private static String getPropertyValue(String propertyName, String defaultValue) {
		String hostSubscriber = propertyManager.getCPGeneratorProperty(propertyName);
		return StringUtils.isNotBlank(hostSubscriber) ? hostSubscriber : defaultValue;
	}

	private static ZMQ.Socket createSubscriber(ZMQ.Context context, String urlSubs, String subsTopic){
		ZMQ.Socket subscriber = context.socket(ZMQ.SUB);
		subscriber.connect(urlSubs);
		subscriber.subscribe(subsTopic.getBytes());
		log.debug("Subscribed to "+urlSubs+" and topic "+subsTopic);
		return subscriber;
	}

	private static ZMQ.Socket createPublisher(ZMQ.Context context, String urlPubs, String camelModelIdTopic, String cpModelIdTopic){
		ZMQ.Socket publisher = context.socket(ZMQ.PUB);
		publisher.bind(urlPubs);
		log.debug("Publishing to "+urlPubs+" and camel model id topic "+camelModelIdTopic+" and cp model id "+cpModelIdTopic);
		return publisher;
	}

}
