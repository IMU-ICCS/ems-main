/* 
 * Copyright (C) 2014-2015 University of Stuttgart
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.profiler.rp.zeromq.test;

import org.zeromq.ZMQ;

import eu.paasage.upperware.profiler.rp.zeromq.RuleProcessorService;

/**
 * @author hopped
 *
 */
public class MetaSolverRPSubscriber {

	public static void main (String[] args) {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket subscriber = context.socket(ZMQ.SUB);

        subscriber.connect("tcp://127.0.0.1:5545");
        subscriber.subscribe("RPSolutionAvailable".getBytes());

        while (!Thread.currentThread ().isInterrupted ()) {
            String requestType = subscriber.recvStr();
            System.out.println("Received message on topic '" + requestType + "'");
            if (subscriber.hasReceiveMore()) {
            	String message = subscriber.recvStr();
            	if (message.equals(RuleProcessorService.ERROR)) {
            		System.out.println("  > An error has occurred: " + message);
            	} else {
            		System.out.println("  > Camel Model: " + message);
            	}
            } else {
            	continue;
            }
            if (subscriber.hasReceiveMore()) {
            	String cdoIdentifier = subscriber.recvStr();
            	System.out.println("  > CDO Identifier: " + cdoIdentifier);
            }
        }
        subscriber.close ();
        context.term ();
    }
	
}
