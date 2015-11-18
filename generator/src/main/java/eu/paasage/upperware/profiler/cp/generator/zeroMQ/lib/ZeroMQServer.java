package eu.paasage.upperware.profiler.cp.generator.zeroMQ.lib;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.zeromq.ZMQ;

import eu.paasage.upperware.profiler.cp.generator.db.lib.CDODatabaseProxy;
import eu.paasage.upperware.profiler.cp.generator.model.lib.GenerationOrchestrator;
import eu.paasage.upperware.profiler.cp.generator.model.lib.ModelFileInfo;
import eu.paasage.upperware.profiler.cp.generator.model.tools.PaaSagePropertyManager;

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
		
		String host= DEFAULT_HOST_SUBS;
		
		String portSubs= DEFAULT_SUBSCRIBER_PORT;
		
		String subsTopic= DEFAULT_TOPIC_SUBS;
		
		if(propertyManager.getCPGeneratorProperty(HOST_SUSBSCRIBER_PROPERTY)!=null && !propertyManager.getCPGeneratorProperty(HOST_SUSBSCRIBER_PROPERTY).equals(""))
		{
			host= propertyManager.getCPGeneratorProperty(HOST_SUSBSCRIBER_PROPERTY);
		}
		
		if(propertyManager.getCPGeneratorProperty(SUBSCRIBER_PORT_PROPERTY)!=null && !propertyManager.getCPGeneratorProperty(SUBSCRIBER_PORT_PROPERTY).equals(""))
		{
			portSubs= propertyManager.getCPGeneratorProperty(SUBSCRIBER_PORT_PROPERTY);
		}
		
		if(propertyManager.getCPGeneratorProperty(SUBSCRIBER_TOPIC_PROPERTY)!=null && !propertyManager.getCPGeneratorProperty(SUBSCRIBER_TOPIC_PROPERTY).equals(""))
		{
			subsTopic= propertyManager.getCPGeneratorProperty(SUBSCRIBER_TOPIC_PROPERTY);
		}
		
		String urlSubs= DEFAULT_PROTOCOL_SUBS+host+":"+portSubs;
		
		
		String portPubs= DEFAULT_PUBLISHER_PORT;
		
		String cpModelIdTopic= DEFAULT_CP_MODEL_ID_TOPIC;
		
		String camelModelIdTopic= DEFAULT_CAMEL_MODEL_ID_TOPIC; 
		
		if(propertyManager.getCPGeneratorProperty(PUBLISHER_PORT_PROPERTY)!=null && !propertyManager.getCPGeneratorProperty(PUBLISHER_PORT_PROPERTY).equals(""))
		{
			portPubs= propertyManager.getCPGeneratorProperty(PUBLISHER_PORT_PROPERTY);
		}
		
		if(propertyManager.getCPGeneratorProperty(PUBLISHER_TOPIC_CP_MODEL_ID_PROPERTY)!=null && !propertyManager.getCPGeneratorProperty(PUBLISHER_TOPIC_CP_MODEL_ID_PROPERTY).equals(""))
		{
			cpModelIdTopic= propertyManager.getCPGeneratorProperty(PUBLISHER_TOPIC_CP_MODEL_ID_PROPERTY);
		}
		
		if(propertyManager.getCPGeneratorProperty(PUBLISHER_TOPIC_CAMEL_MODEL_ID_PROPERTY)!=null && !propertyManager.getCPGeneratorProperty(PUBLISHER_TOPIC_CAMEL_MODEL_ID_PROPERTY).equals(""))
		{
			camelModelIdTopic= propertyManager.getCPGeneratorProperty(PUBLISHER_TOPIC_CAMEL_MODEL_ID_PROPERTY);
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

        System.out.println("Publishing to "+urlPubs+" and camel model id topic "+camelModelIdTopic+" and cp model id "+cpModelIdTopic);
        
        while (!Thread.currentThread().isInterrupted()) {
            // Wait for next message from the publisher
        	System.out.println("Waiting for Model Id");
            byte[] request = subscriber.recv();
            
            request = subscriber.recv();
            
            String modelId= new String(request,StandardCharsets.UTF_8); 
            
            System.out.println("model id "+modelId);

            List<ModelFileInfo> modelInfos=  null; 
        	
			ModelFileInfo mfi= new ModelFileInfo(modelId, "camel");
			
			modelInfos= new ArrayList<ModelFileInfo>(); 
			
			modelInfos.add(mfi); 
			
			System.out.println("Creating GenerationOrchestrator");
			
			GenerationOrchestrator go= new GenerationOrchestrator(); 
				
			System.out.println("Generating CP Model");
			String paasageConfigID= go.generateCPModel(modelInfos); 
			System.out.println("CP Model Generated");
            
			publisher.sendMore(cpModelIdTopic); 
            publisher.send(CDODatabaseProxy.CDO_SERVER_PATH+paasageConfigID);
            System.out.println("CP Model Id sent "+CDODatabaseProxy.CDO_SERVER_PATH+paasageConfigID);
            
            publisher.sendMore(camelModelIdTopic); 
            publisher.send(modelId); 
        }
        subscriber.close();
        publisher.close();
        context.term();
	}

}
