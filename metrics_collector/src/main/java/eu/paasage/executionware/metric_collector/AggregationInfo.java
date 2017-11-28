/* Copyright (C) 2015 KYRIAKOS KRITIKOS <kritikos@ics.forth.gr> */

/* This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ 
 */

package eu.paasage.executionware.metric_collector;

import java.util.ArrayList;

import org.kairosdb.client.builder.TimeUnit;

import eu.paasage.camel.metric.MetricFunctionArityType;
import eu.paasage.camel.metric.MetricFunctionType;

public class AggregationInfo {
	private ArrayList<AggregationNode> arguments;
	private MetricFunctionType function;
	private MetricFunctionArityType arity;
	private long period;
	private TimeUnit unit;
	private String metric;
	
	public AggregationInfo(ArrayList<AggregationNode> arguments, long period, TimeUnit unit, String metric, MetricFunctionType function, MetricFunctionArityType arity){
		this.arguments = arguments;
		this.period = period;
		this.unit = unit;
		this.metric = metric;
		this.function = function;
		this.arity = arity;
	}
	
	public ArrayList<AggregationNode> getArguments(){
		return arguments;
	}
	
	public long getPeriod(){
		return period;
	}
	
	public TimeUnit getUnit(){
		return unit;
	}
	
	public String getMetric(){
		return metric;
	}
	
	public MetricFunctionType getFunction(){
		return function;
	}
	
	public MetricFunctionArityType getArity(){
		return arity;
	}
	
	public String toString(){
		String toReturn = "AggregationInfo(arguments= [";
		if (arguments != null){
			for (AggregationNode node: arguments){
				toReturn += "\n" + node.toString();
			}
		}
		if (function == null)
			toReturn += "\n], Period=" + period + ", Unit=" + unit + ", Metric=" + metric + ")";
		else
			toReturn += "\n], Period=" + period + ", Unit=" + unit + ", Metric=" + metric + ",Function=" + function + ", Arity=" + arity + ")";
		return toReturn;
	}
}
