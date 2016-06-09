/* 
 * Copyright (C) 2014-2015 University of Stuttgart
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.profiler.rp.zeromq.test;

import java.nio.charset.StandardCharsets;

import org.zeromq.ZMQ;

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
            String requestType = subscriber.recvStr(StandardCharsets.UTF_8);
            System.out.println("Received message on topic '" + requestType + "'");
            if (subscriber.hasReceiveMore()) {
            	String message = subscriber.recvStr(StandardCharsets.UTF_8);
            	System.out.println("  > Camel Model: " + message);
            }
            if (subscriber.hasReceiveMore()) {
            	String cdoIdentifier = subscriber.recvStr(StandardCharsets.UTF_8);
            	System.out.println("  > CDO Identifier (new): " + cdoIdentifier);
            }
            if (subscriber.hasReceiveMore()) {
            	String cdoIdentifier = subscriber.recvStr(StandardCharsets.UTF_8);
            	System.out.println("  > CDO Identifier (old): " + cdoIdentifier);
            }
        }
        subscriber.close ();
        context.term ();
    }
	
}
