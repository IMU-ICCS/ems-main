/*
 * Copyright (c) 2014-5 UK Science and Technology Facilities Council
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.metasolver;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;
import org.jeromq.*;
public class messageListener {

	Context cntx = null;
	ZMQ zmq = new ZMQ();

	public String subs(){

		Context cntx = zmq.context(1);

		Socket	subscriber = cntx.socket (zmq.SUB);
		subscriber.connect ("tcp://metricscollectior?:5563");
		subscriber.subscribe(null);//need topic here 
		while (!Thread.currentThread ().isInterrupted ()) {
            // Read envelope with address
            String address = subscriber.recvStr ();
            // Read message contents
            String contents = subscriber.recvStr ();
            System.out.println(address + " : " + contents);
            //take contents and get new KPI
        }
        subscriber.close ();
        cntx.term ();
    

		return null;
	}

}
