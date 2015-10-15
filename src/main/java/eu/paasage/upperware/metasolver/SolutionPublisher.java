package eu.paasage.upperware.metasolver;


import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

/**
 * Pubsub envelope publisher
 */

public class SolutionPublisher {

	public static void main (String[] args) throws Exception {

		System.out.println("lets go for a pub");

		// Prepare our context and publisher
		Context context = ZMQ.context(1);
		Socket publisher = context.socket(ZMQ.PUB);

		System.out.println("binding .... ");
		publisher.bind("tcp://*:5545");
		while (!Thread.currentThread ().isInterrupted ()) {
			// Write two messages, each with an envelope and content
			System.out.println("publishing .... ");
			//topic
			publisher.sendMore ("B_2");
			//contents
			publisher.send("solution goes here");
			System.out.println("done publishing .... ");
		}
		publisher.close ();
		context.term ();
	}
}



