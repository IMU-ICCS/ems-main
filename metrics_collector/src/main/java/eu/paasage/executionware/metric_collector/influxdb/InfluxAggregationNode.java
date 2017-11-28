/* Copyright (C) 2015 KYRIAKOS KRITIKOS <kritikos@ics.forth.gr> */

/* This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ 
 */

package eu.paasage.executionware.metric_collector.influxdb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.kairosdb.client.builder.TimeUnit;

import eu.paasage.camel.metric.CompositeMetric;
import eu.paasage.camel.metric.Metric;
import eu.paasage.camel.metric.MetricFormula;
import eu.paasage.camel.metric.MetricFormulaParameter;
import eu.paasage.camel.metric.MetricFunctionArityType;
import eu.paasage.camel.metric.MetricFunctionType;
import eu.paasage.camel.metric.MetricInstance;
import eu.paasage.camel.type.DoublePrecisionValue;
import eu.paasage.camel.type.FloatsValue;
import eu.paasage.camel.type.IntegerValue;
import eu.paasage.camel.type.SingleValue;
import eu.paasage.executionware.metric_collector.AggregationInfo;
import eu.paasage.executionware.metric_collector.AggregationNode;
import eu.paasage.executionware.metric_collector.MetricCollector;
import eu.paasage.executionware.metric_collector.MetricCollector.Mode;
import eu.paasage.executionware.metric_collector.StatisticsUtils;
import eu.paasage.mddb.cdo.client.CDOClient;

public class InfluxAggregationNode extends AggregationNode{
	private InfluxDBClient client;
	private String valueName;
	
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(InfluxAggregationNode.class);
	
	public InfluxAggregationNode(InfluxDBClient client, CDOClient cl, MetricCollector.Mode mode, AggregationInfo info, String valueName, double value){
		super(cl,mode,info,value);
		logger.info("Info is: " + this.info);
		this.client = client;
		this.valueName = valueName;
	}
	
	private Date getDate(){
		Calendar cal = Calendar.getInstance();
		TimeUnit unit = info.getUnit();
		if (unit.equals(TimeUnit.SECONDS))
			cal.add(Calendar.SECOND,-(int)info.getPeriod());
		else if (unit.equals(TimeUnit.MILLISECONDS))
			cal.add(Calendar.MILLISECOND,-(int)info.getPeriod());
		else if (unit.equals(TimeUnit.MINUTES))
			cal.add(Calendar.MINUTE,-(int)info.getPeriod());
		else if (unit.equals(TimeUnit.HOURS))
			cal.add(Calendar.HOUR,-(int)info.getPeriod());
		else if (unit.equals(TimeUnit.DAYS))
			cal.add(Calendar.DAY_OF_MONTH,-(int)info.getPeriod());
		Date prev = cal.getTime();
		return prev;
	}
	
	public double calculate() throws Exception{
		// I am a Node with no aggregation - just get the latest value of the metric
		CDOView view = null;
		boolean isLocal = (mode == MetricCollector.Mode.LOCAL)?true:false;
		if (!isLocal) view = cl.openView();
		if (info == null){
			logger.info("Constant value: " + value + " encountered");
			return value;
		}
		else{
			MetricFunctionType function = info.getFunction();
			String metricId = info.getMetric();
			MetricFunctionArityType arity = info.getArity();
			logger.info("Got function: " + function + " for metricID: " + metricId + " and arity: " + arity);
			if (arity.equals(MetricFunctionArityType.UNARY) || arity.equals(MetricFunctionArityType.BINARY)){
				if (isLocal){
					logger.info("Querying for values of aggregated metric: " + info.getMetric() + " with period: " + info.getPeriod() + " with unit: " + info.getUnit());
					List<Double> results = client.getQueryResults("select " + valueName + " from " + info.getMetric() + " limit 1");
					if (results != null && !results.isEmpty()){
						logger.info("Size "+ results.size());
						value = results.get(0);
					}
					
				}
				else{
					Date prev = getDate();
					if (arity.equals(MetricFunctionArityType.UNARY)){
						String functionStr = "";
						String queryStr = "";
						if (function == MetricFunctionType.MEAN){
							functionStr = "avg(m.value)";
						}
						else if (function == MetricFunctionType.STD){
							functionStr = "sqrt((sum(m.value*m.value)/count(m.value)) - (avg(m.value) * avg(m.value)))";
						}
						else if (function == MetricFunctionType.MIN){
							functionStr = "min(m.value)";
						}
						else if (function == MetricFunctionType.MAX){
							functionStr = "max(m.value)";
						}
						if (function != MetricFunctionType.MEDIAN && function != MetricFunctionType.MODE && function != MetricFunctionType.DERIVATIVE){
							queryStr = "select " + functionStr + " from Measurement m where m.metricInstance.name='" + metricId + "' and m.measurementTime >= :date1";
							logger.info("CDO: Executing query: " + queryStr);
							Double d = view.createQuery("hql",queryStr).setParameter("date1", prev).getResult(Double.class).get(0);
							value = d.doubleValue();
						}
						else{
							if (function == MetricFunctionType.MEDIAN){
								queryStr = "select m.value from Measurement m where m.metricInstance.name='" + metricId + "' and m.measurementTime >= :date1 order by m.value";
								logger.info("CDO: Executing query: " + queryStr);
								List<Double> d = view.createQuery("hql",queryStr).setParameter("date1", prev).getResult(Double.class);
								value = StatisticsUtils.median(d);
							}
							else if (function == MetricFunctionType.MODE){
								queryStr = "select m.value, count(m.value) as num from Measurement m where m.metricInstance.name='" + metricId + "' and m.measurementTime >= :date1 group by m.value order by num desc";
								logger.info("CDO: Executing query: " + queryStr);
								Object[] res = view.createQuery("hql",queryStr).setParameter("date1", prev).setMaxResults(1).getResult(Object[].class).get(0);
								value = ((Double)res[0]).doubleValue();
							}
							else if (function == MetricFunctionType.DERIVATIVE){
								queryStr = "select ((m2.value-m1.value) / (second(m2.measurementTime-m1.measurementTime))) as val from Measurement m1, Measurement m2  where m1.metricInstance.name='" + metricId + "' and m2.metricInstance.name='" + metricId + "' and m1 <> m2 and m1.measurementTime >= :date1 and m2.measurementTime >= :date1 order by m1.measurementTime ASC, m2.measurementTime DESC";
								logger.info("CDO: Executing query: " + queryStr);
								Double res = view.createQuery("hql",queryStr).setParameter("date1", prev).setMaxResults(1).getResult(Double.class).get(0);
								value = res.doubleValue();
							}
						}
						logger.info("Got value2: " + value);
					}
					else{
						logger.info("Arity is binary");
						ArrayList<AggregationNode> arguments = info.getArguments();
						if (function.equals(MetricFunctionType.PLUS)){
							value = arguments.get(0).calculate() + arguments.get(1).calculate();
							logger.info("The value aggregated with plus is: " + value);
						}
						else if (function.equals(MetricFunctionType.MINUS)){
							value = arguments.get(0).calculate() - arguments.get(1).calculate();
						}
						else if (function.equals(MetricFunctionType.MODULO)){
							value = arguments.get(0).calculate() % arguments.get(1).calculate();
						}
						else if (function.equals(MetricFunctionType.DIV)){
							value = arguments.get(0).calculate() / arguments.get(1).calculate();
						}
						else if (function.equals(MetricFunctionType.TIMES)){
							value = arguments.get(0).calculate() * arguments.get(1).calculate();
						}
						else if (function.equals(MetricFunctionType.PERCENTILE)){
							logger.info("Function is PERCENTILE");
							double percentile = arguments.get(1).calculate();
							logger.info("Percentile2");
							String queryStr = "select m.value from Measurement m where m.metricInstance.name='" + metricId + "' and m.measurementTime >= :date1";
							List<Double> d = view.createQuery("hql",queryStr).setParameter("date1", prev).getResult(Double.class);
							value = StatisticsUtils.percentile(d,percentile);
						}
					}
				}
			}
		}
		if (!isLocal) view.close();
		return value;
	}
	
	private static String getValueName(Metric m){
		String valueName = null;
		if (m instanceof CompositeMetric){
			CompositeMetric cm = (CompositeMetric)m;
			MetricFormula mf = cm.getFormula();
			MetricFunctionType function = mf.getFunction();
			if (function == MetricFunctionType.PLUS || function == MetricFunctionType.MINUS || function == MetricFunctionType.TIMES || function == MetricFunctionType.DIV) valueName = "expr0";
			else valueName = m.getName();
		}
		else valueName = "value";
		return valueName;
	}
	
	private static String getUnit(TimeUnit u){
		if (u.equals(TimeUnit.MILLISECONDS)) return "ms";
		else if (u.equals(TimeUnit.SECONDS)) return "s";
		else if (u.equals(TimeUnit.MINUTES)) return "m";
		else if (u.equals(TimeUnit.HOURS)) return "h";
		else if (u.equals(TimeUnit.DAYS)) return "d";
		else if (u.equals(TimeUnit.WEEKS)) return "w";
		else if (u.equals(TimeUnit.MONTHS)) return "s";
		else if (u.equals(TimeUnit.YEARS)) return "y";
		return "";
	}
	
	private static String getFunctionSymbol(MetricFunctionType function){
		if (function.equals(MetricFunctionType.PLUS)){
			return "+";
		}
		else if (function.equals(MetricFunctionType.MINUS)){
			return "-";
		}
		else if (function.equals(MetricFunctionType.TIMES)){
			return "*";
		}
		else if (function.equals(MetricFunctionType.DIV)){
			return "/";
		}
		return "";
	}
	
	public static InfluxAggregationNode getInfluxAggregationNode(InfluxDBClient client, CDOClient cl, MetricCollector.Mode mode, long aggregationPeriod, String metricInstanceId, MetricFormula mf, ArrayList<Metric> metrics, EList<MetricInstance> metricInstances, long period, TimeUnit unit){
		InfluxAggregationNode node = new InfluxAggregationNode(client,cl,mode,null,null,AggregationNode.NO_VALUE);
		ArrayList<AggregationNode> arguments = new ArrayList<AggregationNode>();
		MetricInstance mi = null;
		String operandId1 = null, operandId2 = null;
		String operandName1 = null, operandName2 = null;
		double operandVal = AggregationNode.NO_VALUE;
		
		for (MetricFormulaParameter param: mf.getParameters()){
			if (param instanceof Metric){
				Metric metric = (Metric)param;
				int position = metrics.indexOf(metric);
				mi = metricInstances.get(position);
				String metricId = mi.getName();
				if (operandId1 == null) operandId1 = metricId;
				else operandId2 = metricId;
				String operandName = getValueName(metric);	
				if (operandName1 == null) operandName1 = operandName;
				else operandName2 = operandName;
				logger.info("Should enter here for Metric: " + metric.getName() + " " + "MetricInstance: " + mi);
				AggregationNode an = new InfluxAggregationNode(client,cl,mode,null,operandName,AggregationNode.NO_VALUE);
				AggregationInfo info = new AggregationInfo(null,aggregationPeriod,unit,metricId,null,null);
				an.setAggregationInfo(info);
				arguments.add(an);
			}
			else if (param instanceof MetricFormula){
				AggregationNode an = getInfluxAggregationNode(client, cl, mode, aggregationPeriod, metricInstanceId,(MetricFormula)param,metrics,metricInstances,aggregationPeriod,unit);
				arguments.add(an);
			}
			else{
				SingleValue value = param.getValue();
				double val = AggregationNode.NO_VALUE;
				if (value instanceof DoublePrecisionValue){
					val = ((DoublePrecisionValue)value).getValue();
				}
				else if (value instanceof FloatsValue){
					val = ((FloatsValue)value).getValue();
				}
				else if (value instanceof IntegerValue){
					val = ((IntegerValue)value).getValue();
				}
				logger.info("Val is: " + val);
				operandVal = val;
				AggregationNode an = new InfluxAggregationNode(client,cl,mode,null,null,val);
				arguments.add(an);
			}
		}
		MetricFunctionType function = mf.getFunction();
		MetricFunctionArityType arity = mf.getFunctionArity();
		if (mode.equals(Mode.LOCAL)){
			if (arity.equals(MetricFunctionArityType.UNARY)){
				String queryHead = "select ";
				String functionCall = null;
				String queryTail = " as " + metricInstanceId + " from " + operandId1 + " group by time(" + aggregationPeriod + getUnit(unit) + ") into " + metricInstanceId;
				if (function.equals(MetricFunctionType.MEAN)){
					functionCall = "MEAN(" + operandName1 + ")";
				}
				else if (function.equals(MetricFunctionType.STD)){
					functionCall = "STDDEV(" + operandName1 + ")";
				}
				else if (function.equals(MetricFunctionType.MAX)){
					functionCall = "MAX(" + operandName1 + ")";
				}
				else if (function.equals(MetricFunctionType.MIN)){
					functionCall = "MIN(" + operandName1 + ")";
				}
				else if (function.equals(MetricFunctionType.DERIVATIVE)){
					functionCall = "DERIVATIVE(" + operandName1 + ")";
				}
				else if (function.equals(MetricFunctionType.MEDIAN)){
					functionCall = "MEDIAN(" + operandName1 + ")";
				}
				else if (function.equals(MetricFunctionType.MODE)){
					functionCall = "MODE(" + operandName1 + ")";
				}
				String query = queryHead + functionCall + queryTail;
				if (function.equals(MetricFunctionType.MINUS)){
					query = queryHead + "-" + operandName1 + " from " + operandId1 + " group by time(" + aggregationPeriod + getUnit(unit) + ") into " + metricInstanceId;
				}
				logger.info("Creating continuous query: " + query);
				client.createContinuousQuery(query);
				node.setValueName(metricInstanceId);
			}
			else if (arity.equals(MetricFunctionArityType.BINARY)){
				String query = null;
				if (operandId1 != null && operandId2 != null){
					node.setValueName("expr0");
					query = "select " + operandName1 + getFunctionSymbol(function) + operandName2 + " from " + operandId1 + " inner join " + operandId2 + " group by time(" + aggregationPeriod + getUnit(unit) + ") into " + metricInstanceId;
				}
				else if (operandId1 != null){
					if (function.equals(MetricFunctionType.PERCENTILE)){
						query = "select PERCENTILE(" + operandName1 + "," + operandName2 + ") from " + operandId1 + " inner join " + operandId2 + " group by time(" + aggregationPeriod + getUnit(unit) + ") into " + metricInstanceId;
						node.setValueName(metricInstanceId);
					}
					else{
						query = "select " + operandName1 + getFunctionSymbol(function) + operandVal + " from " + operandId1 + " group by time(" + aggregationPeriod + getUnit(unit) + ") into " + metricInstanceId;
						node.setValueName("expr0");
					}
				}
				else if (operandId2 != null){
					node.setValueName("expr0");
					query = "select " + operandName1 + getFunctionSymbol(function) + operandVal + " from " + operandId1 + " group by time(" + aggregationPeriod + getUnit(unit) + ") into " + metricInstanceId;
				}
				logger.info("Creating continuous query: " + query);
				client.createContinuousQuery(query);
			}
		}
		AggregationInfo info = null;
		if (mode.equals(Mode.LOCAL))
			info = new AggregationInfo(arguments,aggregationPeriod,unit,metricInstanceId,function,mf.getFunctionArity());
		else
			info = new AggregationInfo(arguments,aggregationPeriod,unit,operandId1,function,mf.getFunctionArity());
		node.setAggregationInfo(info);
		
		return node;
	}
	
	public void setValueName(String valueName){
		this.valueName = valueName;
	}
	
	public String getValueName(){
		return valueName;
	}
	
	public String toString(){
		if (info == null)
			return "KairosAggregationNode(Value=" + value + ", \nNode=null)";
		else
			return "KairosAggregationNode(Value=" + value + ", \nNode='" + info.toString() + "\n)";
	}
}
