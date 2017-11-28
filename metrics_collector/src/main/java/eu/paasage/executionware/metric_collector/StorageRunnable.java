/* Copyright (C) 2015 KYRIAKOS KRITIKOS <kritikos@ics.forth.gr> */

/* This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ 
 */

package eu.paasage.executionware.metric_collector;

import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.view.CDOView;

import eu.paasage.camel.CamelModel;
import eu.paasage.mddb.cdo.client.CDOClient;

public class StorageRunnable implements Runnable{
	private CDOID id;
	private double v;
	private int times = 100;
	
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(StorageRunnable.class);
	
	public StorageRunnable(CDOID id, double v){
		this.id = id;
		this.v = v;
	}
	
	public StorageRunnable(CDOID id, double v, int times){
		this.id = id;
		this.v = v;
		this.times = times;
	}
	
	public void run() {
		CDOClient client = new CDOClient();
		CDOView view = client.openView();
		CDOResource res = view.getResource("test");
		CamelModel cm = (CamelModel)res.getContents().get(0);
		CDOID ecID = cm.getExecutionModels().get(0).getExecutionContexts().get(0).cdoID();
		CDOID appID = cm.getApplications().get(0).cdoID();
		view.close();
		client.closeSession();
		logger.info("Starting my work: " + v);
		for (int i = 0; i < times; i++){
			//logger.info("Running the " + i + " measurement for thread: " + v);
			/* Change to SynchronisedMetricStorage.storeMeasurement to run the other test for measurement
			 * storage according to the other implementation
			 */
			MetricStorageWithBlockingQueue.storeMeasurement(v, id, ecID, SynchronisedMetricStorage.MeasurementType.APPLICATION_MEASUREMENT, appID, null);
			//SynchronisedMetricStorage.storeMeasurement(v, id, ecID, SynchronisedMetricStorage.MeasurementType.APPLICATION_MEASUREMENT, appID, null);
		}
		logger.info("Ended my work: " + v);
	}
}
