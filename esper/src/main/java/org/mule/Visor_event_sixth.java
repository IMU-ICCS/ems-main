package org.mule;

import java.util.HashMap;
import java.util.List;
import java.util.*;

public class Visor_event_sixth {
	
	
	private double metricValue;
	private String vmName;
	private String cloudName;
	private String componentName;	
	private int    level;
	private long   timestamp;
	
	

	
	public double getMetricValue(){
		return metricValue;
	}
	
	public void setMetricValue(double metricValue){
		this.metricValue = metricValue;
	}
	
	public String  getvmName(){
		return vmName;
	}
	
	public void setvmName(String vmName){
		this.vmName = vmName;
	}
		
	
	public String getCloudName(){
		return cloudName;
	}
	
	public void setCloudName(String cloudName) {
		this.cloudName = cloudName;
	}
	
	public String getComponentName(){
		return componentName;
	}
	
	public void setcomponentName(String componentName) {
		this.componentName = componentName;
	}
	
	public int getLevel(){
		return level;
		
	}
	public void setLevel(int level){
		this.level =level;
	}
	
	public long getTimestamp(){
	   return timestamp;	
	}
	
	public void setTimestamp(long timestamp){
		this.timestamp=timestamp;		
	}	

}
