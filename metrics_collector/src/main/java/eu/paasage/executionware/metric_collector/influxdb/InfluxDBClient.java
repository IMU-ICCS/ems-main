/* Copyright (C) 2015 KYRIAKOS KRITIKOS <kritikos@ics.forth.gr> */

/* This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ 
 */

				package eu.paasage.executionware.metric_collector.influxdb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDB.LogLevel;
import org.influxdb.dto.Database;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Serie;
import org.influxdb.impl.InfluxDBImpl;

import eu.paasage.executionware.metric_collector.Test;


public class InfluxDBClient {
	
	private InfluxDB influxDB;
	private String dbName, ip;
	
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(InfluxDBClient.class);
	
	public InfluxDBClient(String ip, String dbName){
		this.ip = ip;
		this.dbName = dbName;
		initDB();
	}
	
	private void initDB(){
		Logger.getLogger("com.sun.jersey").setLevel(Level.OFF);
		String userName = "root";
		String passwd = "root";
		influxDB = new InfluxDBImpl("http://" + ip + ":8086", userName, passwd);
		influxDB.setLogLevel(LogLevel.NONE);
		boolean influxDBstarted = true;
		try{
			do {
				Pong response;
				try {
					/*response = influxDB.ping();
					if (response.getStatus().equalsIgnoreCase("ok")) {
						influxDBstarted = true;
					}*/
				} catch (Exception e) {
					e.printStackTrace();
				}
				Thread.sleep(100L);
			} while (!influxDBstarted);
		}
		catch(Exception e){
			if (!(e instanceof InterruptedException)) logger.error("Something went wrong while sleeping in initDB",e);
			//e.printStackTrace();
		}
		influxDB.setLogLevel(LogLevel.FULL);
		//String logs = CharStreams.toString(new InputStreamReader(containerLogsStream, Charsets.UTF_8));
		logger.info("##################################################################################");
		//logger.info("CContainer Logs: \n" + logs);
		//logger.info("#  Connected to InfluxDB Version: " + influxDB.version() + " #");
		logger.info("##################################################################################");
		
		//Create DB and read DBs
		//dbName = "test" + System.currentTimeMillis();
		//dbName = "test";
		//influxDB.deleteDatabase(dbName);
		//logger.info("Database deleted");
		createDatabase(dbName);
	}
	
	public void createDatabase(String dbName){
		List<Database> dbs = influxDB.describeDatabases();
		boolean exists = false;
		for(Database db: dbs){
			if (db.getName().equals(dbName)){
				exists = true;
				break;
			}
		}
		if (!exists){
			influxDB.createDatabase(dbName);
			logger.info("Database created");
		}
		List<Database> result = influxDB.describeDatabases();
		if (result != null){
			if (result.isEmpty()){
				logger.info("No DB was generated!!!");
			}
			else{
				for (Database db: result){
					logger.info("Got DB: " + result);
				}
			}
		}
	}
	
	public void createContinuousQuery(String query){
		influxDB.query(dbName, query, TimeUnit.SECONDS);
	}
	
	public List<Double> getQueryResults(String query){
		List<Double> results = new ArrayList<Double>();
		List<Serie> result = influxDB.query(dbName, query, TimeUnit.SECONDS);
		for (Serie serie: result){
			logger.info("Got value: " + serie.getRows());
			List<Map<String,Object>> rows = serie.getRows();
			for (Map<String,Object> row: rows){
				for (String s: row.keySet()){
					if (!s.equals("time") && !s.equals("sequence_number")){
						Double value = (Double)row.get(s);
						results.add(value);
					}
				}
			}
		}
		return results;
	}
	
	public void writeData(String metricName, double value){
		Serie serie1 = new Serie.Builder(metricName)
        .columns("time", "value")
        .values(System.currentTimeMillis(), value)
        .build();
		influxDB.write(dbName, TimeUnit.MILLISECONDS, serie1);
	}
	
	public static void main(){
		InfluxDBClient client = new InfluxDBClient("localhost","test");
		client.writeData("lala", 1.0);
		client.writeData("lala", 2.0);
		client.writeData("lala", 3.0);
		client.createContinuousQuery("select MEAN(value) from lala group by time(10s) into lala_1");
	}
}