package eu.paasage.upperware.profiler.cp.generator.zeroMQ.lib;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.zeromq.ZMQ;

import eu.paasage.upperware.profiler.cp.generator.db.lib.CDODatabaseProxy;
import eu.paasage.upperware.profiler.cp.generator.model.lib.GenerationOrchestrator;
import eu.paasage.upperware.profiler.cp.generator.model.lib.ModelFileInfo;

public class ZeroMQServer 
{
	
	private static String DEFAULT_SUBSCRIBER_PORT="5555"; 
	
	private static String DEFAULT_PUBLISHER_PORT="5544"; 
	
	private static String DEFAULT_PROTOCOL="tcp://*:"; 
	
	private static String DEFAULT_PROTOCOL_SUBS="tcp://localhost:"; 
	
	
	/**
	 * Executes the Server. Arguments can be empty, specify the subscribe port or specify the subscribe port and the publisher port
	 * @param args Arguments for running the Server. args[0]= subcriber port, args[1]= publisher port
	 */
	public static void main(String[] args) 
	{
		
		String urlSubs= DEFAULT_PROTOCOL_SUBS+DEFAULT_SUBSCRIBER_PORT; 
		
		String urlPubs= DEFAULT_PROTOCOL+DEFAULT_PUBLISHER_PORT; 
		
		if(args.length>0)
		{
			urlSubs= DEFAULT_PROTOCOL+args[0]; 
			
			if(args.length>1)
			{
				//PaaSagePropertyManager.getInstance().addCPGeneratorProperty(Constants.FILE_NAME_SENDER_PROPERTY_NAME, args[1]);
				urlPubs= DEFAULT_PROTOCOL+args[1]; 
			}
		}
		
		ZMQ.Context context = ZMQ.context(1);

        //  Socket to receive info from publishers
        ZMQ.Socket subscriber = context.socket(ZMQ.SUB);
        subscriber.connect(urlSubs);
        subscriber.subscribe("ID".getBytes());
        // Socket to publish info
        ZMQ.Socket publisher = context.socket(ZMQ.PUB);
        publisher.bind(urlPubs);

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
            
			publisher.sendMore("startSolving"); 
            publisher.send(CDODatabaseProxy.CDO_SERVER_PATH+paasageConfigID);
            System.out.println("CP Model Id sent "+CDODatabaseProxy.CDO_SERVER_PATH+paasageConfigID);
            
            publisher.sendMore("camelModelId"); 
            publisher.send(modelId); 
        }
        subscriber.close();
        publisher.close();
        context.term();
	}

}
