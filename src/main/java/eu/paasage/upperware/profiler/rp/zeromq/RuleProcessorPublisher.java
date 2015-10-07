/**
 * 
 */
package eu.paasage.upperware.profiler.rp.zeromq;

import org.zeromq.ZMQ;

/**
 * @author hopped
 *
 */
public class RuleProcessorPublisher {

	public static void main(String[] args) throws InterruptedException {
		ZMQ.Context ctx = ZMQ.context(1);
		ZMQ.Socket publisher = ctx.socket(ZMQ.PUB);
		publisher.bind("tcp://*:5563");

		while (!Thread.currentThread().isInterrupted()) {
			publisher.sendMore("RP");
			publisher.send("startSolving");
		}

		publisher.close();
		ctx.term();
	}

}
