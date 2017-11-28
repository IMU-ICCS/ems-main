/* Copyright (C) 2015 KYRIAKOS KRITIKOS <kritikos@ics.forth.gr> */

/* This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ 
 */

package eu.paasage.executionware.metric_collector;

import java.io.File;
import java.io.PrintWriter;
import java.util.Random;

import org.eclipse.emf.cdo.common.id.CDOID;

import eu.paasage.executionware.metric_collector.influxdb.InfluxDBClient;
import eu.paasage.executionware.metric_collector.kairosdb.KairosDbClient;


public class RandomMeasurementRunnable implements Runnable{

	private String fileName;
	private int sleepPeriod;
	private String metricName;
	private Random r = new Random();
	private CDOID id, ecID, appID;
	private boolean inKairos = false;
	
	public RandomMeasurementRunnable(String fileName, int sleepPeriod, String metricName, CDOID id, CDOID ecID, CDOID appID, boolean inKairos){
		this.fileName = fileName;
		this.sleepPeriod = sleepPeriod;
		this.metricName = metricName;
		this.id = id;
		this.ecID = ecID;
		this.appID = appID;
		this.inKairos = inKairos;
	}
	
	public void run() {
		File f = null;
		PrintWriter pw = null;
		KairosDbClient kairosClient = null;
		InfluxDBClient influxClient = null;
		if (inKairos) kairosClient = new KairosDbClient("http://localhost:8080");
		else influxClient = new InfluxDBClient("localhost","test");
		Tag t1 = new Tag("user", "kyriakos");
		try{
			f = new File(fileName);
			pw = new PrintWriter(f);
			while (true){
				int value = r.nextInt(100);
				pw.println(value);
				pw.flush();
				if (inKairos) kairosClient.putMetric(metricName,t1, System.currentTimeMillis(), value);
				else influxClient.writeData(metricName, value);
				Thread.sleep(sleepPeriod);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			pw.close();
		}
	}

}
