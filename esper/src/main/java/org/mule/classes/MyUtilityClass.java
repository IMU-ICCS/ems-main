package org.mule.classes;

import com.espertech.esper.epl.agg.AggregationSupport;
import com.espertech.esper.epl.agg.AggregationValidationContext;

import com.espertech.esper.epl.agg.aggregator.AggregationMethod;


import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.*;


public class MyUtilityClass extends AggregationSupport {
	private double metricValue;
	private double percentile;
	private double[] Array = new double [5];
	int i=0;
	int k=0;
	double Min;
	double mmin;
	
	
	private Vector<Double> myarr = new Vector<Double>();
	
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
		metricValue=0;
		myarr.clear();
	}

	
	@Override
	public void enter(Object object) {
		// TODO Auto-generated method stub
		if (object == null) { 
            return; 
        } 
		
		//System.out.println(">>>>>   "+object.getClass());
		//System.out.println(">>>>>   "+object.getClass().isArray());
		Object[] oarr = (Object[])object;
				
		metricValue = ((Number) oarr[0]).doubleValue();
		percentile = ((Number) oarr[1]).doubleValue();
		myarr.add(metricValue);
		//process();
	}
	
	
	public  double process() {
		
		//double percentile = 0.5;
		double defValue = 0;
		
		int N = myarr.size();
		if (N==0) return defValue;
		
		int index = (int)Math.round(N * percentile);
		if (index>=N) index = N-1;
		
		Collections.sort(myarr);
		return myarr.get(index);
		
		
	}

	double findMin(double[] input) {
		 return Arrays.stream(input).min().getAsDouble();
		}
	
	public  double calcLowNum(double[] a) {
	    double min=0;
	    
	    min = a[0];
	    int minPos = 0;
	    for (int jj=1, mm=Math.min(5, a.length); jj<mm; jj++) {
	    	if (a[jj]>min) {
	    		min = a[jj];
	    		minPos = jj;
	    	}
	    }
	    k = minPos;
	    

	    return min;	    
	}

	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		
		
		return process();
	}

	@Override
	public Class getValueType() {
		// TODO Auto-generated method stub
		return  Double.class; 
	}

	@Override
	public void leave(Object arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void validate(AggregationValidationContext arg0) {
		// TODO Auto-generated method stub
		
	}

}