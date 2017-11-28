/* Copyright (C) 2015 KYRIAKOS KRITIKOS <kritikos@ics.forth.gr> */

/* This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ 
 */

package eu.paasage.executionware.metric_collector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.kairosdb.client.builder.DataFormatException;
import org.kairosdb.client.builder.DataPoint;

public class StatisticsUtils {
	
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(StatisticsUtils.class);
	
	public static double mode(List<DataPoint> points) throws DataFormatException{
		HashMap<DataPoint,Integer> results = new HashMap<DataPoint,Integer>();
		DataPoint max = null;
		int maxFreq = 0;
		for (DataPoint dp: points){
			Integer res = results.get(dp);
			if (res == null) {
				res = new Integer(1);
				results.put(dp, res);
				if (maxFreq == 0){
					maxFreq = 1;
					max = dp;
				}
			}
			else{
				res = res + 1;
				if (res > maxFreq){
					max = dp;
					maxFreq = res;
				}
				results.put(dp, res + 1);
			}
		}
		return max.doubleValue();
	}
	
	public static <T> double median(List<T> d){
		double value = 0.0;
		int size = d.size();
		logger.info("Size is: " + size);
		T res = null;
		if (size % 2 == 1){
			res = d.get(size/2);
			if (res instanceof Double){
				value = (Double)res;
			}
			else if (res instanceof DataPoint){
				try{
					value = ((DataPoint)res).doubleValue();
				}
				catch(Exception e){
					logger.error("Something went wrong while attempting to obtain the value from a DataPoint",e);
					//e.printStackTrace();
				}
			}
			logger.info("Got value2: " + value);
		}
		else{
			T res1 = null, res2 = null;
			res1 = d.get(size/2);
			res2 = d.get((size/2) -1 );
			if (res1 instanceof Double){
				value = ((Double)res1 + (Double)res2) / 2.0;
			}
			else if (res1 instanceof DataPoint){
				try{
					value = (((DataPoint)res1).doubleValue() + ((DataPoint)res2).doubleValue()) / 2.0;
				}
				catch(Exception e){
					logger.error("Something went wrong while attempting to get the average from two data points",e);
					//e.printStackTrace();
				}
			}
			
			logger.info("Got value2: " + value);
		}
		return value;
	}
	
	public static <T> double percentile(List<T> d, double percentage){
		int size = d.size();
		logger.info("Size is: " + size);
		Object[] res = d.toArray();
		if (res[0] instanceof DataPoint){
			for (int i = 0; i < size; i++){
				try{
					Double d2 = ((DataPoint)d.get(i)).doubleValue();
					res[i] = d2;
				}
				catch(Exception e){
					logger.info("Something went wrong while attempting to read the value from a data point",e);
					//e.printStackTrace();
				}
			}
			Arrays.sort(res);
		}
		else if (res[0] instanceof Double){
			Arrays.sort(res);
		}
		logger.info("Res is: " + res[0] + " " + res[1] + " " + res[2]);
		double rank = percentage * (size + 1.0);
		logger.info("rank is: " + rank);
		int k = (int)rank;
		double dec = getDecimal(rank);
		logger.info("k is: " + k + " and d is: " + dec);
		if (k == 0){
			return (Double)res[0];
		}
		else if (k >= size){
			return (Double)res[size-1];
		}
		else{
			double v_k = 0.0;
			double v_k1 = 0.0;
			v_k = (Double)res[k-1];
			v_k1 = (Double)res[k];
			return v_k + dec * (v_k1-v_k);
		}
	}
	
	private static double getDecimal(double d){
		String num = "" + d;
		String[] split = num.split("\\.");
		long k_s = Long.parseLong(split[1]);
		double k = k_s / (Math.pow(10, split[1].length()));
		return k;
	}
	
	public static void main(String[] args){
		List<DataPoint> list = new ArrayList<DataPoint>();
		list.add(new DataPoint(System.currentTimeMillis(),91.0));
		list.add(new DataPoint(System.currentTimeMillis(),95.0)); 
		list.add(new DataPoint(System.currentTimeMillis(),99.0));
		list.add(new DataPoint(System.currentTimeMillis(),92.0));
		list.add(new DataPoint(System.currentTimeMillis(),48.0));
		list.add(new DataPoint(System.currentTimeMillis(),46.0));
		list.add(new DataPoint(System.currentTimeMillis(),30.0));
		list.add(new DataPoint(System.currentTimeMillis(),61.0));
		list.add(new DataPoint(System.currentTimeMillis(),19.0));
		list.add(new DataPoint(System.currentTimeMillis(),95.0));
		list.add(new DataPoint(System.currentTimeMillis(),17.0));
		list.add(new DataPoint(System.currentTimeMillis(),56.0));
		/*List<Double> list = new ArrayList<Double>();
		list.add(91.0);
		list.add(95.0); 
		list.add(99.0);
		list.add(92.0);
		list.add(48.0);
		list.add(46.0);
		list.add(30.0);
		list.add(61.0);
		list.add(19.0);
		list.add(95.0);
		list.add(17.0);
		list.add(56.0);*/
	
		double d = percentile(list,0.5);
		logger.info("d is: " + d);
	}
	
}
