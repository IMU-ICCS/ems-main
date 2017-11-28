/* Copyright (C) 2015 KYRIAKOS KRITIKOS <kritikos@ics.forth.gr> */

/* This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ 
 */

package eu.paasage.executionware.metric_collector.kairosdb;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.kairosdb.client.HttpClient;
import org.kairosdb.client.builder.Aggregator;
import org.kairosdb.client.builder.AggregatorFactory;
import org.kairosdb.client.builder.DataFormatException;
import org.kairosdb.client.builder.DataPoint;
import org.kairosdb.client.builder.Metric;
import org.kairosdb.client.builder.MetricBuilder;
import org.kairosdb.client.builder.QueryBuilder;
import org.kairosdb.client.builder.TimeUnit;
import org.kairosdb.client.response.GetResponse;
import org.kairosdb.client.response.Queries;
import org.kairosdb.client.response.QueryResponse;
import org.kairosdb.client.response.Response;
import org.kairosdb.client.response.Results;

import eu.paasage.executionware.metric_collector.Tag;
import eu.paasage.executionware.metric_collector.Test;



public class KairosDbClient{
	private String url;
	
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(KairosDbClient.class);
	
	/**
	 * 
	 * @param url The Kairos DB url 
	 */
	public KairosDbClient(String url){
		this.url = url;
	}
	
	private void copyMetric(Metric m1, Metric m2) throws DataFormatException{
		for (DataPoint dp: m1.getDataPoints()) m2.addDataPoint(dp.getTimestamp(), dp.doubleValue());
		m2.addTags(m1.getTags());
	}
	
	/**
	 * 
	 * @param m A Metric that has already been instantiated
	 * @throws IOException when something is wrong while closing the HttpClient
	 * @throws MalformedURLException when a wrong URL is provided
	 * @throws org.kairosdb.client.builder.DataFormatException
	 * Insert a Metric already instantiated
	 * MetricName, Tags and Values should not be null
	 * 
	 */
	public void putMetric(Metric m) throws DataFormatException, IOException, MalformedURLException{
		MetricBuilder builder = MetricBuilder.getInstance();
		Metric m2 = builder.addMetric(m.getName(),m.getType());
		copyMetric(m,m2);
		
		HttpClient client = new HttpClient(this.url);
		try {
			Response response = client.pushMetrics(builder);
			if (response.getErrors().size() >0 ){
				for(String e : response.getErrors())
				System.err.println("Response error: "+ e);
			}
		} catch (URISyntaxException e) {
			logger.error("PaaSage KairosDB Client : Error pushing metric, URI Syntax error",e);
			//e.printStackTrace();
		} catch (IOException e) {
			logger.error("PaaSage KairosDB Client : Error pushing metric, Io Exception",e);
			//e.printStackTrace();
		}
		client.shutdown();
		
	}
	/**
	 * Insert a Metric which is not instantiated
	 * MetricName, Timestamp Tags and Values should not be null
	 * 
	 * @param metricName the name of the metric to instantiate
	 * @param t the tag associated to the metric
	 * @param timestamp the time of measurement
	 * @param value the value of measurement
	 */
	public void putMetric(String metricName, Tag t ,long timestamp, double value ){
		MetricBuilder builder = MetricBuilder.getInstance();
		builder.addMetric(metricName)
				.addDataPoint(timestamp, value)
				.addTag(t.getName(), t.getValue());
		
		HttpClient client = null;
		boolean created = false;
		try {
			client = new HttpClient(this.url);
			created = true;
			Response response = client.pushMetrics(builder);
			if (response.getErrors().size() >0 ){
				for(String e : response.getErrors())
				logger.error("Response error: "+ e);
			}
		}
		catch (MalformedURLException e) {
			logger.error("PaaSage KairosDB Client : Error pushing metric, Malformed URL Exception",e);
			//e.printStackTrace();
		}
		catch (URISyntaxException e) {
			logger.error("PaaSage KairosDB Client : Error pushing metric, URI Syntax error",e);
			//e.printStackTrace();
		} catch (IOException e) {
			logger.error("PaaSage KairosDB Client : Error pushing metric, Io Exception",e);
			//e.printStackTrace();
		}
		if (created){
			try{
				client.shutdown();
			}
			catch(IOException e){
				logger.error("PaaSage KairosDB Client : Error closing the Http client, Io Exception",e);
				//e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Lists all metrics stored in the KairosDB 
	 * @return an ArrayList of their string-based representation of metrics stored in KairosDB
	 * @throws IOException when something is wrong while closing the HttpClient
	 * @throws MalformedURLException when a wrong URL is provided
	 */
	public ArrayList<String> ListAllMetrics() throws IOException, MalformedURLException{
		HttpClient client = new HttpClient(this.url);
		ArrayList<String> metricNames = new ArrayList<String>();
		GetResponse response;
		try {
			response = client.getMetricNames();
			//logger.info("Response Code =" + response.getStatusCode());
			for (String name : response.getResults())
				metricNames.add(name);
		} catch (IOException e) {
			System.err.println("PaaSage TSDB Client Error Listing metic names "+e);
		}
		client.shutdown();
		return metricNames;

	}

	/**
	 * Lists all the tag names stored in the KairosDb and 
	 * @return an ArrayList of their string-based representation of tags associated to metrics
	 * @throws IOException when something is wrong while closing the HttpClient
	 * @throws MalformedURLException when a wrong URL is provided
	 */
	public ArrayList<String> ListAllTags() throws IOException, MalformedURLException{
		HttpClient client = new HttpClient(this.url);
		ArrayList<String> metricTags = new ArrayList<String>();
		GetResponse response;
		try {
			response = client.getTagNames();
			//logger.info("Response Code =" + response.getStatusCode());
			for (String name : response.getResults())
				metricTags.add(name);
		} catch (IOException e) {
			System.err.println("PaaSage TSDB Client Error Listing Tag names "+e);
			e.printStackTrace();
		}
		client.shutdown();
		return metricTags;

	}
	/**
	 * Querying data points is similarly done by using the QueryBuilder class. A query requires a date range. The start date is
	 * required, but the end date defaults to NOW if not specified. The metric(s) that you are querying for is also required.
	 * Optionally, tags may be added to narrow down the search.
	 * 
	 *  * This Query Builder is used with Absolute Dates
	 * for example from now till 2 days ago
	 * 
	 * @param metric the metric in which the query is posed
	 * @param start the starting time period for the query
	 * @param end the ending time period for the query
	 * @param unit the respective time unit for the time period bounds
	 * @return a list of data points/measurements
	 * @throws IOException when something is wrong while closing the HttpClient
	 * @throws MalformedURLException when a wrong URL is provided
	 */
	public List<DataPoint> QueryDataPoints(String metric, int start, int end, TimeUnit unit ) throws IOException, MalformedURLException{
		QueryBuilder builder = QueryBuilder.getInstance();

		if (start != -1 && end != -1 && end > start)
		{
			System.err.print("Start Date should be greater than End Date");
			return null;
		}

		builder.setStart(start, unit)
		.addMetric(metric);
		if (end != -1) builder.setEnd(end,unit);

		HttpClient client = new HttpClient(this.url);
		try {
			QueryResponse response = client.query(builder);
			for(Queries q: response.getQueries()){
				//logger.info("For result R "+ q.getResults());
				Iterator<Results> it = q.getResults().iterator();
				while(it.hasNext()){
					Results tmp = it.next();
//					logger.info("Got Result "+ tmp.getName());
//					logger.info("Data Points List: "+ tmp.getDataPoints());
					return tmp.getDataPoints();	
				}
			}
		} catch (URISyntaxException e) {
			System.err.println("PaaSage TSDB Client Error QueryDataPoints "+e);
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("PaaSage TSDB Client Error QueryDataPoints "+e);
			e.printStackTrace();
		}
		client.shutdown();
		return null;
	}
	
	/*
	 * Querying data points is similarly done by using the QueryBuilder class. A query requires a date range. The start date is
	 * required, but the end date defaults to NOW if not specified. The metric(s) that you are querying for is also required.
	 * Optionally, tags may be added to narrow down the search.
	 *
	 * This Query Builder is used with Absolute Dates
	 * for example from 12/3/2014 to 12/4/2014
	 */
	/**
	 * 
	 * @param metric the metric on which the query is posed
	 * @param start the starting time period of the query
	 * @param end the ending time period of the query
	 * @return a list of data points / measurements
	 * @throws IOException when something is wrong while closing the HttpClient
	 * @throws MalformedURLException when a wrong URL is provided
	 */
	public List<DataPoint> QueryDataPointsAbsolute(String metric, Date start, Date end) throws IOException, MalformedURLException{
		QueryBuilder builder = QueryBuilder.getInstance();
		
		builder.setStart(start)
		.setEnd(end)
		.addMetric(metric);

		HttpClient client = new HttpClient(this.url);
		try {
			QueryResponse response = client.query(builder);
			for(Queries q: response.getQueries()){
				//logger.info("For result R "+ q.getResults());
				Iterator<Results> it = q.getResults().iterator();
				while(it.hasNext()){
					Results tmp = it.next();
					logger.info("Got Result "+ tmp.getName());
					logger.info("Data Points List: "+ tmp.getDataPoints());
					return tmp.getDataPoints();	
				}
			}
		} catch (URISyntaxException e) {
			System.err.println("PaaSage TSDB Client Error QueryDataPoints "+e);
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("PaaSage TSDB Client Error QueryDataPoints "+e);
			e.printStackTrace();
		}
		client.shutdown();
		return null;
	}
	/**
	 * Same as Relative Query Builder plus the aggregator instance
	 * 
	 * 
	 * @param metric the metric on which the query is posed
	 * @param start the starting time period of the query
	 * @param end the ending time period of the query
	 * @param unit the time unit of the time period bounds
	 * @param ag the aggregator (e.g., average/mean) used for the query
	 * @throws IOException when something is wrong while closing the HttpClient
	 * @throws MalformedURLException when a wrong URL is provided
	 * @return a list of aggregated data points on measurements
	 */
	public List<DataPoint> QueryAggregatedDataPoints(String metric, int start, int end, TimeUnit unit, Aggregator ag ) throws IOException, MalformedURLException{
		QueryBuilder builder = QueryBuilder.getInstance();


		if (start != -1 && end != -1 && end > start)
		{
			System.err.print("Start Date should be greater than End Date");
			return null;
		}

		builder.setStart(start, unit)
		.addMetric(metric)
		.addAggregator(ag);
		if (end != -1) builder.setEnd(end,unit);

		HttpClient client = new HttpClient(this.url);
		try {
			QueryResponse response = client.query(builder);
			for(Queries q: response.getQueries()){
				//logger.info("For result R "+ q.getResults());
				Iterator<Results> it = q.getResults().iterator();
				while(it.hasNext()){
					Results tmp = it.next();
//					logger.info("Got Result "+ tmp.getName());
//					logger.info("Data Points List: "+ tmp.getDataPoints());
					return tmp.getDataPoints();	
				}
			}
		} catch (URISyntaxException e) {
			System.err.println("PaaSage TSDB Client Error QueryDataPoints "+e);
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("PaaSage TSDB Client Error QueryDataPoints "+e);
			e.printStackTrace();
		}
		client.shutdown();
		return null;
	}
	
	
	
	public static void main(String [] args) throws URISyntaxException, IOException{
		
		//Tag t1 = new Tag("user", "panos");
		KairosDbClient dbclient = new KairosDbClient("http://localhost:8080");
		try{
			//dbclient.putMetric("workshopTest",t1, System.currentTimeMillis(), 100);
			
			
			logger.info("...Listing Metric Names... ");
			ArrayList<String> metrics = dbclient.ListAllMetrics();
			for(String tmp : metrics)
					logger.info("=> "+ tmp);
			logger.info("\n\n...Listing Metric Tags Names... ");
			
			ArrayList<String> tags = dbclient.ListAllTags();
			
			for(String tmp : tags)
					logger.info("=> "+ tmp);
			 
			//1st aggregation test
			Aggregator ag = AggregatorFactory.createAverageAggregator(1, TimeUnit.MINUTES);
			List<DataPoint> aggregated = dbclient.QueryAggregatedDataPoints("kairosdb.jvm.free_memory", 1 , -1 , TimeUnit.MINUTES, ag);
			if (aggregated != null && !aggregated.isEmpty()){
				logger.info("Size "+ aggregated.size());
				for (DataPoint dp: aggregated){
					logger.info(" Data: "+ dp.toString());
				}
			}
			else logger.info("1. Did not get any aggregated value");
			
			
			Thread.sleep(10000);
			ag = AggregatorFactory.createAverageAggregator(10, TimeUnit.SECONDS);
			aggregated = dbclient.QueryAggregatedDataPoints("myMetric", 10 , -1 , TimeUnit.SECONDS, ag);
			if (aggregated != null && !aggregated.isEmpty()){
				logger.info("Size "+ aggregated.size());
				for (DataPoint dp: aggregated){
					logger.info(" Data: "+ dp.toString());
				}
			}
			else logger.info("2. Did not get any aggregated value");
			Thread.sleep(10000);
			aggregated = dbclient.QueryAggregatedDataPoints("myMetric", 10 , -1 , TimeUnit.SECONDS, ag);
			if (aggregated != null && !aggregated.isEmpty()){
				logger.info("Size "+ aggregated.size());
				for (DataPoint dp: aggregated){
					logger.info(" Data: "+ dp.toString());
				}
			}
			else logger.info("3. Did not get any aggregated value");
			Thread.sleep(10000);
			aggregated = dbclient.QueryAggregatedDataPoints("myMetric", 10 , -1 , TimeUnit.SECONDS, ag);
			if (aggregated != null && !aggregated.isEmpty()){
				logger.info("Size "+ aggregated.size());
				for (DataPoint dp: aggregated){
					logger.info(" Data: "+ dp.toString());
				}
			}
			else logger.info("4. Did not get any aggregated value");
			//es.shutdownNow();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
	}
}
