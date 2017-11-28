/* Copyright (C) 2015 KYRIAKOS KRITIKOS <kritikos@ics.forth.gr> */

/* This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ 
 */

package eu.paasage.executionware.metric_collector;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.kairosdb.client.builder.TimeUnit;

import eu.paasage.camel.unit.Unit;
import eu.paasage.camel.unit.UnitType;
import eu.paasage.camel.metric.CompositeMetric;
import eu.paasage.camel.metric.CompositeMetricInstance;
import eu.paasage.camel.metric.Metric;
import eu.paasage.camel.metric.MetricApplicationBinding;
import eu.paasage.camel.metric.MetricComponentBinding;
import eu.paasage.camel.metric.MetricFormula;
import eu.paasage.camel.metric.MetricFormulaParameter;
import eu.paasage.camel.metric.MetricInstance;
import eu.paasage.camel.metric.MetricObjectBinding;
import eu.paasage.camel.metric.MetricVMBinding;
import eu.paasage.camel.metric.Schedule;
import eu.paasage.camel.metric.Window;
import eu.paasage.camel.metric.WindowSizeType;
import eu.paasage.camel.type.DoublePrecisionValue;
import eu.paasage.camel.type.FloatsValue;
import eu.paasage.camel.type.IntegerValue;
import eu.paasage.camel.type.SingleValue;
import eu.paasage.executionware.metric_collector.MetricCollector.Mode;
import eu.paasage.executionware.metric_collector.SynchronisedMetricStorage.MeasurementType;
import eu.paasage.executionware.metric_collector.influxdb.InfluxAggregationNode;
import eu.paasage.executionware.metric_collector.influxdb.InfluxDBClient;
import eu.paasage.executionware.metric_collector.kairosdb.KairosAggregationNode;
import eu.paasage.executionware.metric_collector.kairosdb.KairosDbClient;
import eu.paasage.executionware.metric_collector.pubsub.PublicationServer;
import eu.paasage.mddb.cdo.client.CDOClient;

public class MetricHandler implements Runnable{
	
	private CDOID ID;
	private CDOID ecID;
	private CDOClient cl;
	private KairosDbClient kairosClient;
	private InfluxDBClient influxClient;
	private double value;
	private volatile boolean run = true;
	private MeasurementType measurementType;
	private CDOID object,object2;
	private AggregationNode node;
	private long period = 0, aggregationPeriod = 0, sleepPeriod;
	private TimeUnit unit = TimeUnit.SECONDS;
	private boolean inTSDB;
	private Tag tag;
	private MetricCollector.Mode mode;
	private MetricCollector.DBType dbType;
	private PublicationServer server;
	
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(MetricHandler.class);
	
	public MetricHandler(CDOID ID, CDOID ecID, boolean inTSDB, String host, String port, MetricCollector.Mode mode, MetricCollector.DBType dbType, PublicationServer server){
		this.ID = ID;
		this.ecID = ecID;
		this.mode = mode;
		this.dbType = dbType;
		logger.info("dbType is: " + dbType);
		this.server = server;
		cl = new CDOClient();
		logger.info("DBClient should be available at: " + "http://" + host + ":" + port);
		if (dbType == MetricCollector.DBType.KAIROS) kairosClient = new KairosDbClient("http://" + host + ":" + port);
		else if (dbType == MetricCollector.DBType.INFLUX) influxClient = new InfluxDBClient(host,"test");
		this.inTSDB = inTSDB;
		tag = new Tag("user", "kyriakos");
	}

	public void run() {
		// TODO Auto-generated method stub
		CDOView view = cl.openView();
		MetricInstance mi = (MetricInstance)view.getObject(ID);
		String id = mi.getName();
		File f = new File("aggr_measurement_" + id);
		PrintWriter pw = null;
		try{
			pw = new PrintWriter(f);
		}
		catch(Exception e){
			logger.error("Something went wrong while attempting to create a PrintWrite on file with name: " + f.getName(),e);
			//e.printStackTrace();
		}
		setupAggregation((CompositeMetricInstance)mi);
		while (run){
			try{
				Thread.sleep(sleepPeriod);
				if (dbType == MetricCollector.DBType.KAIROS)
					value = ((KairosAggregationNode)node).calculate();
				else if (dbType == MetricCollector.DBType.INFLUX)
					value = ((InfluxAggregationNode)node).calculate();
				if (mode == Mode.GLOBAL && server != null) server.submitValue(id, value);
				if (inTSDB) {
					if (dbType == MetricCollector.DBType.KAIROS)
						kairosClient.putMetric(id,tag, System.currentTimeMillis(), value);
					else if (dbType == MetricCollector.DBType.INFLUX)
						influxClient.writeData(id,value);
				}
				else{
					Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
				}
				//MetricStorageWithBlockingQueue.storeMeasurement(value,ID,ecID,measurementType,object,object2);
				SynchronisedMetricStorage.storeMeasurement(value,ID,ecID,measurementType,object,object2);
				pw.println("" + value);
				pw.flush();
			}
			catch(Exception e){
				//e.printStackTrace();
				if (e instanceof InterruptedException){
					view.close();
					cl.closeSession();
					pw.close();
					return;
				}
				logger.error("Something went wrong while running the main method of the MetricHandler",e);
			}
		}
		view.close();
		cl.closeSession();
		pw.close();
	}
	
	private TimeUnit getUnit(Unit unit){
		UnitType type = unit.getUnit();
		if (type.equals(UnitType.MILLISECONDS)) return TimeUnit.MILLISECONDS;
		if (type.equals(UnitType.SECONDS)) return TimeUnit.SECONDS;
		if (type.equals(UnitType.MINUTES)) return TimeUnit.MINUTES;
		if (type.equals(UnitType.HOURS)) return TimeUnit.HOURS;
		if (type.equals(UnitType.DAYS)) return TimeUnit.DAYS;
		if (type.equals(UnitType.WEEKS)) return TimeUnit.WEEKS;
		return null;
	}
	
	private void setupAggregation(CompositeMetricInstance mi){
		//Fix object and measurement type
		MetricObjectBinding binding = mi.getObjectBinding();
		if (binding instanceof MetricApplicationBinding){
			object = binding.getExecutionContext().getApplication().cdoID();
			measurementType = MeasurementType.APPLICATION_MEASUREMENT; 
		}
		else if (binding instanceof MetricComponentBinding){
			object = ((MetricComponentBinding) binding).getComponentInstance().cdoID();
			measurementType = MeasurementType.COMPONENT_MEASUREMENT; 
		}
		else if (binding instanceof MetricVMBinding){
			object = ((MetricVMBinding) binding).getVmInstance().cdoID();
			measurementType = MeasurementType.VM_MEASUREMENT; 
		}
		
		getComputationPeriod(mi);
		getAggregationPeriod(mi);
		
		Metric metric = mi.getMetric();
		EList<MetricInstance> componentMetrics = mi.getComposingMetricInstances();
		ArrayList<Metric> metrics = getMetricsFromInstances(componentMetrics);
		logger.info("MetricInstances of instance: " + mi.getName() + " are: " + metrics);
		if (dbType == MetricCollector.DBType.KAIROS){
			node = KairosAggregationNode.getKairosAggregationNode(kairosClient, cl, mode, aggregationPeriod, mi.getName(),((CompositeMetric)metric).getFormula(), metrics, componentMetrics, period, unit);
			logger.info("Node is: " + ((KairosAggregationNode)node).toString());
		}
		else if (dbType == MetricCollector.DBType.INFLUX){
			node = InfluxAggregationNode.getInfluxAggregationNode(influxClient, cl, mode, aggregationPeriod, mi.getName(),((CompositeMetric)metric).getFormula(), metrics, componentMetrics, period, unit);
			logger.info("Node is: " + ((InfluxAggregationNode)node).toString());
		}
	}
	
	private void printAggregationNode(AggregationNode node, MetricInstance mi){
		logger.info("Printing AggregationInfo for MetricInstance: " + mi.getName() + " of metric: " + mi.getMetric().getName());
		logger.info(node.toString());
	}
	
	private int findPeriod(TimeUnit unit){
		int period = 1;
		if (unit.equals(TimeUnit.SECONDS)) period = period * 1000;
		else if (unit.equals(TimeUnit.MINUTES)) period = period * 60000;
		else if (unit.equals(TimeUnit.HOURS)) period = period * 3600000;
		return period;
	}
	
	private int findPeriod2(TimeUnit unit){
		int period = 1;
		if (unit.equals(TimeUnit.MINUTES)) period = period * 60;
		else if (unit.equals(TimeUnit.HOURS)) period = period * 3600;
		return period;
	}
	
	private void getComputationPeriod(MetricInstance mi){
		Schedule schedule = mi.getSchedule();
		period = (int)schedule.getInterval();
		unit = getUnit(schedule.getUnit());
		getSleepPeriod(period,unit);
	}
	
	private void getSleepPeriod(long period, TimeUnit unit){
		sleepPeriod = period * findPeriod(unit);
	}
	
	private void getAggregationPeriod(MetricInstance component){
		Window window = component.getWindow();
		if (window != null){
			WindowSizeType sizeType = window.getSizeType();
			if (sizeType.equals(WindowSizeType.MEASUREMENTS_ONLY)){
				long measurementSize = window.getMeasurementSize();
				//if (index == 0) aggregationSize = (int)measurementSize;
				MetricInstance component2 = ((CompositeMetricInstance)component).getComposingMetricInstances().get(0);
				Schedule schedule = component2.getSchedule();
				long interval = schedule.getInterval();
				TimeUnit componentUnit = getUnit(schedule.getUnit());
				aggregationPeriod = measurementSize * interval * findPeriod2(componentUnit);
			}
			else if (sizeType.equals(WindowSizeType.TIME_ONLY)){
				long interval = (int)window.getTimeSize();
				TimeUnit miUnit = getUnit(window.getUnit());
				aggregationPeriod = interval * findPeriod2(miUnit);
			}
		}
		else aggregationPeriod = period;
	}
	
	private ArrayList<Metric> getMetricsFromInstances(EList<MetricInstance> metricInstances){
		ArrayList<Metric> metrics = new ArrayList<Metric>();
		for (MetricInstance mi: metricInstances){
			metrics.add(mi.getMetric());
		}
		return metrics;
	}
	
	private ArrayList<Double> getDoubleArguments(EList<MetricFormulaParameter> params){
		ArrayList<Double> arguments = null;
		for (MetricFormulaParameter param: params){
			if (!((param instanceof Metric) || (param instanceof MetricFormula))){
				if (arguments != null) arguments = new ArrayList<Double>();
				SingleValue value = param.getValue();
				if (value instanceof DoublePrecisionValue)
					arguments.add(((DoublePrecisionValue)value).getValue());
				else if (value instanceof FloatsValue)
					arguments.add((double)((FloatsValue)value).getValue());
				else if (value instanceof IntegerValue)
					arguments.add((double)((DoublePrecisionValue)value).getValue());
			}
		}
		return arguments;
	}
	
	public synchronized void terminate(){
		logger.info("MetricHandler: " + ID + " is about to terminate");
		run = false;
	}
	
	public CDOID getID(){
		return ID;
	}
	
	public CDOID getECID(){
		return ecID;
	}
	
	public synchronized double getValue(){
		return value;
	}
	
	public boolean equals(Object o){
		if (o instanceof MetricHandler){
			MetricHandler mh = (MetricHandler)o;
			if (mh.getID().equals(ID) && mh.getECID().equals(ecID)) return true;
		}
		return false;
	}
	
	public int hashCode(){
		return ID.hashCode() + 2 * ecID.hashCode();  
	}

}
