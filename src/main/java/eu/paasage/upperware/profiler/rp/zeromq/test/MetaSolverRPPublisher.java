/* 
 * Copyright (C) 2014-2015 University of Stuttgart
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.profiler.rp.zeromq.test;

import org.zeromq.ZMQ;

/**
 * @author hopped
 *
 */
public class MetaSolverRPPublisher {

	public static void main(String[] args) throws InterruptedException {
		ZMQ.Context ctx = ZMQ.context(1);
		ZMQ.Socket publisher = ctx.socket(ZMQ.PUB);
		publisher.bind("tcp://*:5544");

		Thread.sleep(5000);
		
		while (!Thread.currentThread().isInterrupted()) {
			publisher.sendMore("startSolving");
			publisher.sendMore("mdhf2");
			publisher.send("upperware-models/1444391197664");
			return;
		}

		publisher.close();
		ctx.term();
	}

}
