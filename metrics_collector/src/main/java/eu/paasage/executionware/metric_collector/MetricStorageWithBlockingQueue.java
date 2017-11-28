/* Copyright (C) 2015 KYRIAKOS KRITIKOS <kritikos@ics.forth.gr> */

/* This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ 
 */

package eu.paasage.executionware.metric_collector;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.id.CDOIDUtil;
import org.eclipse.emf.cdo.transaction.CDOTransaction;

import eu.paasage.camel.Application;
import eu.paasage.camel.deployment.InternalComponentInstance;
import eu.paasage.camel.deployment.VMInstance;
import eu.paasage.camel.execution.ApplicationMeasurement;
import eu.paasage.camel.execution.CommunicationMeasurement;
import eu.paasage.camel.execution.ExecutionContext;
import eu.paasage.camel.execution.ExecutionFactory;
import eu.paasage.camel.execution.ExecutionModel;
import eu.paasage.camel.execution.InternalComponentMeasurement;
import eu.paasage.camel.execution.Measurement;
import eu.paasage.camel.execution.VMMeasurement;
import eu.paasage.camel.metric.MetricInstance;
import eu.paasage.executionware.metric_collector.SynchronisedMetricStorage.MeasurementType;
import eu.paasage.mddb.cdo.client.CDOClient;

public class MetricStorageWithBlockingQueue extends MetricStorage{
	
	private static CDOClient cl = null;
	private static int transNum = 0;
	private static ArrayBlockingQueue<StorageRequest> queue = new ArrayBlockingQueue<StorageRequest>(10000);
		
	private static final Integer maxTrans = 5;
	private static final int maxItems = 100;
	
	//private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(MetricStorageWithBlockingQueue.class);
	
	private static class StorageRequest{
		public double value;
		public CDOID ID;
		public CDOID ecID;
		public MeasurementType measurementType;
		public CDOID object;
		public CDOID object2;
		
		public StorageRequest(double value, CDOID ID, CDOID ecID, MeasurementType measurementType, CDOID object, CDOID object2){
			this.value = value;
			this.ID = ID;
			this.ecID = ecID;
			this.measurementType = measurementType;
			this.object = object;
			this.object2 = object2;
		}
	}
	
	private static class TransactionThread extends Thread{
		public void run(){
			int prevSize = 0;
			while(true){
				int queueSize = queue.size();
				logger.info("Queue size is: " + queueSize);
				if (queueSize >= maxItems || (queueSize == prevSize && queueSize != 0)){
					runTransaction();
				}
				prevSize = queueSize;
			}
		}
	}
	
	static {
		checkTransaction();
		logger.info("About to create the transaction thread");
		TransactionThread th = new MetricStorageWithBlockingQueue.TransactionThread();
		th.start();
		logger.info("Transaction thread created");
	}
	
	private static void runTransaction(){
		List<StorageRequest> reqs = new ArrayList<StorageRequest>();
		//int num = queue.drainTo(reqs, maxItems);
		int num = queue.drainTo(reqs);
		logger.info("About to run a transaction for " + num + " items");
		for (StorageRequest req: reqs){
			enforceMeasurementRequest(req);
		}
		commitTransaction();
	}
	
	
	private static void checkTransaction(){
		if (cl == null){
			cl = new CDOClient();
			trans = cl.openTransaction();
			transNum = 0;
		}
		else if (trans == null){
			trans = cl.openTransaction();
		}
		/*if (tr == null){
			tr = new TimeThread(deadline);
			tr.start();
		}*/
	}
	
	public static void storeMeasurement(double value, CDOID ID, CDOID ecID, MeasurementType measurementType, CDOID object, CDOID object2){
		StorageRequest sr = new MetricStorageWithBlockingQueue.StorageRequest(value,ID,ecID,measurementType,object,object2);
		try{
			queue.put(sr);
		}
		catch(Exception e){
			logger.error("Something went wrong while attempting to insert the storage request in the queue",e);
			//e.printStackTrace();
		}
	}
	
	private static void enforceMeasurementRequest(StorageRequest sr){
		checkTransaction();
		MetricStorage.storeMeasurement(sr.value, sr.ID, sr.ecID, sr.measurementType, sr.object, sr.object2);
	}
	
	public static void commitTransaction(){
		try{
			trans.commit();
		}
		catch(Exception e){
			logger.error("Something went wrong while attempting to commit the transaction",e);
			e.printStackTrace();
		}
		trans.close();
		transNum ++;
		if (transNum == maxTrans){
			cl.closeSession();
			cl = new CDOClient();
			transNum = 0;
		}
		trans = cl.openTransaction();
		//tr = new TimeThread(deadline);
		//tr.start();
	}
}
