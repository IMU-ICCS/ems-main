/* Copyright (C) 2015 KYRIAKOS KRITIKOS <kritikos@ics.forth.gr> */

/* This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ 
 */

package eu.paasage.executionware.metric_collector.kairosdb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.kairosdb.client.builder.Aggregator;
import org.kairosdb.client.builder.AggregatorFactory;
import org.kairosdb.client.builder.DataPoint;
import org.kairosdb.client.builder.TimeUnit;

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
import eu.paasage.executionware.metric_collector.StatisticsUtils;
import eu.paasage.executionware.metric_collector.kairosdb.KairosDbClient;
import eu.paasage.mddb.cdo.client.CDOClient;

public class KairosAggregationNode extends AggregationNode{
	private KairosDbClient client;
	private Aggregator aggregator;
	
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(KairosAggregationNode.class);
	
	public KairosAggregationNode(KairosDbClient client, CDOClient cl, MetricCollector.Mode mode, AggregationInfo info, Aggregator aggregator, double value){
		super(cl,mode,info,value);
		logger.info("Info is: " + this.info);
		this.client = client;
		this.aggregator = aggregator;
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
			if (function == null){
				logger.info("Querying for values of metric: " + info.getMetric() + " with period: " + info.getPeriod() + " with unit: " + info.getUnit());
				if (isLocal) {
					List<DataPoint> aggregated = client.QueryDataPoints(info.getMetric(), (int)info.getPeriod(), -1, info.getUnit());
					if (aggregated != null && !aggregated.isEmpty()){
						logger.info("Size "+ aggregated.size());
						for (DataPoint dp: aggregated){
							logger.info(" Data: "+ dp.toString());
							value = dp.doubleValue();
							logger.info("Got value: " + value);
						}
					}
					else{
						logger.info("Did not get any result!!!");
					}
				}
				else{
					Double d = view.createQuery("hql","select m.value from Measurement m where m.metricInstance.name='" + metricId + "' and m.measurementTime >= all(select m2.measurementTime from Measurement m2 where m2.metricInstance.name='" + metricId + "')").getResult(Double.class).get(0);
					value = d.doubleValue();
					logger.info("Got value2: " + value);
				}
			}
			else{
				MetricFunctionArityType arity = info.getArity();
				if (arity.equals(MetricFunctionArityType.UNARY)){
					KairosAggregationNode operand = (KairosAggregationNode)info.getArguments().get(0);
					if (function.equals(MetricFunctionType.MINUS)){
						return -operand.calculate();
					}
					else{
						if (isLocal){
							long period = info.getPeriod();
							TimeUnit unit = info.getUnit();
							
							if (aggregator != null){
								logger.info("Querying for values of aggregated metric: " + info.getMetric() + " with period: " + info.getPeriod() + " with unit: " + info.getUnit());
								List<DataPoint> aggregated = client.QueryAggregatedDataPoints(info.getMetric(), (int)period , -1 , unit, aggregator);
								if (aggregated != null && !aggregated.isEmpty()){
									logger.info("Size "+ aggregated.size());
									for (DataPoint dp: aggregated){
										logger.info(" Data: "+ dp.toString());
										value = dp.doubleValue();
									}
								}
							}
							//MODE CASE
							else{
								List<DataPoint> points = client.QueryDataPoints(info.getMetric(), (int)info.getPeriod(), -1, info.getUnit());
								if (points != null && !points.isEmpty()){
									logger.info("Mode/Median with Size "+ points.size());
									if (function == MetricFunctionType.MODE)
										value = StatisticsUtils.mode(points);
									else if (function == MetricFunctionType.MEDIAN){
										value = StatisticsUtils.median(points);
									}
								}
							}
							logger.info("Got value: " + value);
						}
						else{
							Date prev = getDate();
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
								Double d = view.createQuery("hql",queryStr).setParameter("date1", prev).getResult(Double.class).get(0);
								value = d.doubleValue();
							}
							else{
								if (function == MetricFunctionType.MEDIAN){
									queryStr = "select m.value from Measurement m where m.metricInstance.name='" + metricId + "' and m.measurementTime >= :date1 order by m.value";
									List<Double> d = view.createQuery("hql",queryStr).setParameter("date1", prev).getResult(Double.class);
									value = StatisticsUtils.median(d);
								}
								else if (function == MetricFunctionType.MODE){
									queryStr = "select m.value, count(m.value) as num from Measurement m where m.metricInstance.name='" + metricId + "' and m.measurementTime >= :date1 group by m.value order by num desc";
									Object[] res = view.createQuery("hql",queryStr).setParameter("date1", prev).setMaxResults(1).getResult(Object[].class).get(0);
									value = ((Double)res[0]).doubleValue();
								}
								else if (function == MetricFunctionType.DERIVATIVE){
									queryStr = "select ((m2.value-m1.value) / (second(m2.measurementTime-m1.measurementTime))) as val from Measurement m1, Measurement m2  where m1.metricInstance.name='" + metricId + "' and m2.metricInstance.name='" + metricId + "' and m1 <> m2 and m1.measurementTime >= :date1 and m2.measurementTime >= :date1 order by m1.measurementTime ASC, m2.measurementTime DESC";
									Double res = view.createQuery("hql",queryStr).setParameter("date1", prev).setMaxResults(1).getResult(Double.class).get(0);
									value = res.doubleValue();
								}
							}
							logger.info("Got value2: " + value);
						}
					}
				}
				else if (arity.equals(MetricFunctionArityType.BINARY)){
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
						AggregationInfo info2 = arguments.get(0).getAggregationInfo();
						logger.info("Metric Id to act upon: " + info2.getMetric());
						if (isLocal){
							List<DataPoint> points = client.QueryDataPoints(info2.getMetric(), (int)info.getPeriod(), -1, info.getUnit());
							if (points != null && !points.isEmpty()){
								logger.info("Percentile with Size "+ points.size());
								value = StatisticsUtils.percentile(points, percentile);
							}
						}
						else{
							logger.info("Percentile2");
							Date prev = getDate();
							String queryStr = "select m.value from Measurement m where m.metricInstance.name='" + metricId + "' and m.measurementTime >= :date1";
							List<Double> d = view.createQuery("hql",queryStr).setParameter("date1", prev).getResult(Double.class);
							value = StatisticsUtils.percentile(d,percentile);
						}
					}
				}
				else if (arity.equals(MetricFunctionArityType.NARY)){
					ArrayList<AggregationNode> arguments = info.getArguments();
					if (function.equals(MetricFunctionType.PLUS)){
						value = 0.0;
						for (AggregationNode node: arguments) value += node.calculate();
					}
					else if (function.equals(MetricFunctionType.TIMES)){
						value = 1.0;
						for (AggregationNode node: arguments) value *= node.calculate();
					}
					else if (function.equals(MetricFunctionType.MINUS) || function.equals(MetricFunctionType.DIV)){
						value = arguments.get(0).calculate();
						if (function.equals(MetricFunctionType.MINUS)){
							for(int i = 1; i < arguments.size(); i++) value -= arguments.get(i).calculate();
						}
						else{
							for(int i = 1; i < arguments.size(); i++) value /= arguments.get(i).calculate();
						}
					}
				}
			}
		}
		if (!isLocal) view.close();
		return value;
	}
	
	public static KairosAggregationNode getKairosAggregationNode(KairosDbClient kairosClient, CDOClient cl, MetricCollector.Mode mode, long aggregationPeriod, String metricInstanceId, MetricFormula mf, ArrayList<Metric> metrics, EList<MetricInstance> metricInstances, long period, TimeUnit unit){
		KairosAggregationNode node = new KairosAggregationNode(kairosClient,cl,mode,null,null,AggregationNode.NO_VALUE);
		ArrayList<AggregationNode> arguments = new ArrayList<AggregationNode>();
		MetricInstance mi = null;
		
		for (MetricFormulaParameter param: mf.getParameters()){
			if (param instanceof Metric){
				Metric metric = (Metric)param;
				int position = metrics.indexOf(metric);
				mi = metricInstances.get(position);
				String metricId = mi.getName();
				logger.info("Should enter here for Metric: " + metric.getName() + " " + "MetricInstance: " + mi);
				AggregationNode an = new KairosAggregationNode(kairosClient,cl,mode,null,null,AggregationNode.NO_VALUE);
				AggregationInfo info = new AggregationInfo(null,aggregationPeriod,unit,metricId,null,null);
				an.setAggregationInfo(info);
				arguments.add(an);
			}
			else if (param instanceof MetricFormula){
				AggregationNode an = getKairosAggregationNode(kairosClient, cl, mode, aggregationPeriod, metricInstanceId,(MetricFormula)param,metrics,metricInstances,aggregationPeriod,unit);
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
				AggregationNode an = new KairosAggregationNode(kairosClient,cl,mode,null,null,val);
				arguments.add(an);
			}
		}
		MetricFunctionType function = mf.getFunction();
		MetricFunctionArityType arity = mf.getFunctionArity();
		Aggregator aggregator = null;
		boolean notSupported = false;
		if (arity.equals(MetricFunctionArityType.UNARY)){
			if (function.equals(MetricFunctionType.MEAN)) aggregator = AggregatorFactory.createAverageAggregator((int)aggregationPeriod, unit);
			else if (function.equals(MetricFunctionType.STD)) aggregator = AggregatorFactory.createStandardDeviationAggregator((int)aggregationPeriod, unit);
			else if (function.equals(MetricFunctionType.MAX)) aggregator = AggregatorFactory.createMaxAggregator((int)aggregationPeriod, unit);
			else if (function.equals(MetricFunctionType.MIN)) aggregator = AggregatorFactory.createMinAggregator((int)aggregationPeriod, unit);
			else if (function.equals(MetricFunctionType.DERIVATIVE)) aggregator = AggregatorFactory.createRateAggregator(unit);
			else if (function.equals(MetricFunctionType.MEDIAN)){
				//aggregator = AggregatorFactory.createCustomAggregator("percentile", "\"percentile\": 0.5");
				notSupported = true;
			}
			else if (function.equals(MetricFunctionType.MODE)) notSupported = true;
			else if (function.equals(MetricFunctionType.PERCENTILE)){
				notSupported = true;
				/*double val = 0.0;
				try{
					val = arguments.get(1).calculate();
				}
				catch(Exception e){
					e.printStackTrace();
				}
				aggregator = AggregatorFactory.createCustomAggregator("percentile", "\"percentile\": " + val);*/
			}
			//Do not forget min and max
		}
		AggregationInfo info = null;
		if (aggregator != null || notSupported)
			info = new AggregationInfo(arguments,aggregationPeriod,unit,mi.getName(),function,mf.getFunctionArity());
		else 
			info = new AggregationInfo(arguments,aggregationPeriod,unit,metricInstanceId,function,mf.getFunctionArity());
		node.setAggregator(aggregator);
		node.setAggregationInfo(info);
		
		return node;
	}
	
	public void setAggregator(Aggregator aggregator){
		this.aggregator = aggregator;
	}
	
	public String toString(){
		if (info == null)
			return "KairosAggregationNode(Value=" + value + ", \nNode=null)";
		else
			return "KairosAggregationNode(Value=" + value + ", \nNode='" + info.toString() + "\n)";
	}
}
