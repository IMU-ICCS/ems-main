/* Copyright (C) 2015 KYRIAKOS KRITIKOS <kritikos@ics.forth.gr> */

/* This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ 
 */

package eu.paasage.executionware.metric_collector;

public class TimeThread extends Thread{
	
	private int deadline;
	
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(TimeThread.class);
	
	public TimeThread(int deadline){
		this.deadline = deadline;
	}

	public void run() {
		try{
			Thread.sleep(deadline);
			logger.info("Will now commit transaction");
			SynchronisedMetricStorage.commitTransaction(true);
		}
		catch(Exception e){
			//e.printStackTrace();
		}
	}
}
