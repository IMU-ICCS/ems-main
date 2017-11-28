/* Copyright (C) 2015 KYRIAKOS KRITIKOS <kritikos@ics.forth.gr> */

/* This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ 
 */

package eu.paasage.executionware.metric_collector;

import eu.paasage.mddb.cdo.client.CDOClient;

public abstract class AggregationNode {
	protected AggregationInfo info;
	protected CDOClient cl;
	protected MetricCollector.Mode mode;
	protected double value;
	public static final double NO_VALUE = -1000000000;
	
	public AggregationNode(CDOClient cl, MetricCollector.Mode mode, AggregationInfo info, double value){
		this.cl = cl;
		this.mode = mode;
		this.info = info;
		this.value = value;
	}
	
	public void setAggregationInfo(AggregationInfo info){
		this.info = info;
	}
	
	public AggregationInfo getAggregationInfo(){
		return info;
	}
	
	public void setValue(double value){
		this.value = value;
	}
	
	public abstract double calculate() throws Exception;
	
	public abstract String toString();
}
