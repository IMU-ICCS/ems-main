/* Copyright (C) 2015 KYRIAKOS KRITIKOS <kritikos@ics.forth.gr> */

/* This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ 
 */

package eu.paasage.executionware.metric_collector.pubsub;

import java.util.Set;

import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.internal.common.id.CDOIDObjectLongWithClassifierImpl;
import org.eclipse.emf.cdo.view.CDOView;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

import eu.paasage.executionware.metric_collector.CDOUtils;
import eu.paasage.executionware.metric_collector.MetricCollection;
import eu.paasage.mddb.cdo.client.CDOClient;

/**
* Pubsub envelope subscriber
*/

public class MeasurementInitiationSubscriptionClient implements Runnable{
	
	private Context context;
	private Socket socket;
	private MetricCollection mc;
	private boolean run = true;
	
	private static final int ADAPTER_PORT = 5550;
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(MeasurementInitiationSubscriptionClient.class);
	
	public MeasurementInitiationSubscriptionClient(MetricCollection mc){
		context = ZMQ.context(1);
        socket = context.socket(ZMQ.SUB);

        socket.connect("tcp://localhost:" + ADAPTER_PORT);
        socket.subscribe("startCollection".getBytes(ZMQ.CHARSET));
        this.mc = mc;
	}
	
	public synchronized void terminate(){
		run = false;
	}

	public void run() {
		while (run) {
            // Read envelope with address
            socket.recvStr ();
            // Read message contents
            String id = socket.recvStr ();
            logger.info("Attempting to start the measurement of metrics for execution context: " + id);
            CDOClient cl = new CDOClient();
            CDOView view = cl.openView();
            CDOID ecId = CDOIDObjectLongWithClassifierImpl.create(id);
            Set<CDOID> topMetrics = CDOUtils.getTopMetrics(view, ecId);
            Set<CDOID> globalMetrics = CDOUtils.getGlobalMetrics(topMetrics, view);
            mc.readMetrics(globalMetrics, ecId);
            view.close();
            cl.closeSession();
            logger.info("Measurement of metrics started ...");
        }
        socket.close ();
        context.term ();
	}
}