package eu.paasage.upperware.metasolver;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

import com.eclipsesource.json.JsonObject;

import eu.paasage.upperware.metasolver.exception.MetricMapperException;
import eu.paasage.upperware.metasolver.metrics.Mapper;

public class solutionPublisherMQ {

	
	public String MILPpubMQ (String model) throws MetricMapperException{
		
			Context context = ZMQ.context(1);
		Socket publisher = context.socket(ZMQ.PUB);
       	System.out.println("binding .... ");
		publisher.bind("tcp://*:5554");
		while (!Thread.currentThread ().isInterrupted ()) {
			// Write two messages, each with an envelope and content
			System.out.println("publishing .... ");
			//topic
			publisher.sendMore ("startDeployment");
			//contents
			Mapper map = new Mapper(model);
			/* 26Nov15 syc17 aligned code with updated Mapper
			long mapResult = map.mapMetricVariables(model); */
			JsonObject jObj = map.mapMetricVariables();
			publisher.send(model);
		 //   publisher.send(mapResult);
			System.out.println("done publishing .... ");
		}
		publisher.close ();
		context.term ();
		return "done";
	}

		
		
	public String S2D (String CAMELmodel, String CPmodel, long timestamp) throws MetricMapperException{
		
		Context context = ZMQ.context(1);
	Socket publisher = context.socket(ZMQ.PUB);
   	System.out.println("binding .... ");
	publisher.bind("tcp://*:5546");
	while (!Thread.currentThread ().isInterrupted ()) {
		// Write two messages, each with an envelope and content
		System.out.println("publishing .... ");
		//topic
		publisher.sendMore ("newDeploymentCAMELModel");
		//contents
		publisher.send(CAMELmodel); //CP ID 
		publisher.send(CPmodel);
		publisher.send(Long.toString(timestamp));
		System.out.println("done publishing to S2D.... ");
	}
	publisher.close ();
	context.term ();
	return "done S2D";
}

		
		
		
		
	

		
	
	
}
