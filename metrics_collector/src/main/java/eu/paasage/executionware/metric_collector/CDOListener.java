/* Copyright (C) 2015 KYRIAKOS KRITIKOS <kritikos@ics.forth.gr> */

/* This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ 
 */

package eu.paasage.executionware.metric_collector;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;

import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.revision.CDOIDAndVersion;
import org.eclipse.emf.cdo.session.CDOSessionInvalidationEvent;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.net4j.util.event.IEvent;
import org.eclipse.net4j.util.event.IListener;

import eu.paasage.camel.execution.Measurement;
import eu.paasage.camel.metric.MetricInstance;
import eu.paasage.executionware.metric_collector.pubsub.PublicationServer;
import eu.paasage.mddb.cdo.client.CDOClient;

public class CDOListener implements Runnable, IListener{

	private String metricInstanceID;
	private double threshold;
	private CDOClient cl;
	private CDOView view;
	private PrintWriter pw;
	private PublicationServer server;
	private boolean publish = false;
	private HashSet<CDOID> ids;
	
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(CDOListener.class);
	
	public CDOListener(String metricInstanceID, double threshold){
		this.metricInstanceID = metricInstanceID;
		this.threshold = threshold;
		cl = new CDOClient();
		view = cl.openView();
		cl.addListener(this);
		try{
			pw = new PrintWriter(metricInstanceID);
		}
		catch(Exception e){
			logger.error("Something went wrong wile attempting to open file named as: " + metricInstanceID, e);
			//e.printStackTrace();
		}
	}
	
	public CDOListener(PublicationServer server, HashSet<CDOID> ids){
		this.server = server;
		this.ids = ids;
		if (server != null) publish = true;
		cl = new CDOClient();
		view = cl.openView();
		cl.addListener(this);
		logger.info("CDOListener was called with ids: " + ids);
	}
	
	private void processMeasurement(Measurement am){
		if (!publish){
			if (am.getMetricInstance().getName().equals(metricInstanceID)){
	    		double value = am.getValue();
	    		logger.info("MetricInstance: " + metricInstanceID + " with value: " + value);
	    		pw.println("MetricInstance: " + metricInstanceID + " with value: " + value);
	    		if (value < threshold)
	    			pw.println("Violation of SLO with threshold: " + threshold);
	    		pw.flush();
	    	}
		}
		else{
			MetricInstance mi = am.getMetricInstance();
			if (ids.contains(mi.cdoID())) server.submitValue(mi.getName(), am.getValue());
		}
	}
	
	public void notifyEvent(IEvent event){
        //logger.info("EVENT: " + event);
        if (event instanceof CDOSessionInvalidationEvent){
            CDOSessionInvalidationEvent e = (CDOSessionInvalidationEvent)event;
            List<CDOIDAndVersion> newObjs = e.getNewObjects();
            for (CDOIDAndVersion id: newObjs){
                logger.info("Got new object with id: " + id.getID());
                Object o = view.getObject(id.getID());
                if (o instanceof Measurement){
                	Measurement am = (Measurement)o;
                	processMeasurement(am);
                }
            }
        }
	}
	
	public void run() {
		while(true){
			try{
				Thread.sleep(20000);
			}
			catch(Exception e){
				//e.printStackTrace();
				if (e instanceof InterruptedException){
					if (pw != null){
						pw.flush();
						pw.close();
					}
					view.close();
					cl.closeSession();
					return;
				}
				logger.error("Something went wrong while CDOListener was sleeping",e);
			}
		}
	}
}
