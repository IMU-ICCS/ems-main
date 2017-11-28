/* Copyright (C) 2015 KYRIAKOS KRITIKOS <kritikos@ics.forth.gr> */

/* This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ 
 */

package eu.paasage.executionware.metric_collector;

import java.util.Date;

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
import eu.paasage.mddb.cdo.client.CDOClient;

public class SynchronisedMetricStorage extends MetricStorage{
	
	private static CDOClient cl = null;
	private static int transNum = 0;
	private static int itemNum = 0;
	private static int deadline = 600;
	private static TimeThread tr = null;
		
	private static final Integer maxTrans = 5;
	private static final int maxItems = 100;
	
	//private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(SynchronisedMetricStorage.class);
	
	public enum MeasurementType {
		VM_MEASUREMENT,
		APPLICATION_MEASUREMENT,
		COMMUNICATION_MEASUREMENT,
		COMPONENT_MEASUREMENT
	}
	
	
	private static synchronized void checkTransaction(){
		if (cl == null){
			cl = new CDOClient();
			trans = cl.openTransaction();
			transNum = 0;
			itemNum = 0;
		}
		else if (trans == null){
			trans = cl.openTransaction();
		}
		if (tr == null){
			tr = new TimeThread(deadline);
			tr.start();
		}
	}
	
	public static void storeMeasurement(double value, CDOID ID, CDOID ecID, MeasurementType measurementType, CDOID object, CDOID object2){
		checkTransaction();
		synchronized(maxTrans){
			MetricStorage.storeMeasurement(value, ID, ecID, measurementType, object, object2);
			
			if ((++itemNum) == maxItems){
				commitTransaction(false);
			}
		
		}
	}
	
	public static void commitTransaction(boolean timerCall){
		synchronized(maxTrans){
			if (!timerCall) tr.interrupt();
			if (itemNum > 0){
				logger.info("Trying to commit with param: " + timerCall + " " + itemNum + " " + transNum);
				try{
					trans.commit();
				}
				catch(Exception e){
					logger.error("Something went wrong while attempting to commit the transaction",e);
					//e.printStackTrace();
				}
				trans.close();
				transNum ++;
				if (transNum == maxTrans){
					cl.closeSession();
					cl = new CDOClient();
					transNum = 0;
				}
				trans = cl.openTransaction();
				itemNum = 0;
			}
			tr = new TimeThread(deadline);
			tr.start();
		}
	}
}
