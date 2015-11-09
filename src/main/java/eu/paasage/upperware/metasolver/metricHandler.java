package eu.paasage.upperware.metasolver;

import java.util.Enumeration;
import java.util.Hashtable;

//this class is called by the metrics collector to invoke solvers
//the class looks at the new metric and time it was recieved before making a decision to invoke a solver
public class metricHandler {

	Enumeration metricID;
	String str;
	Hashtable metric = new Hashtable();
	Hashtable time = new Hashtable();


public int metricTable(String metricName, double metricVal, double timestamp){
//add time comparison later
	//int sep = gapCalc(metricName, timestamp);
int comp = metricCompare(metricName, metricVal);
if (comp == 0){
System.out.println("same metric so ignoring");
}
else {
	System.out.println("new metric so writing");
	metric.put(metricName, metricVal);

}
return 0;

	}



	public int metricCompare(String metricName, double metricVal){
		int res;
			if (metric.isEmpty()){
				
				System.out.println("table empty so writing");
				res =1;
				 System.out.println("Metric compare = " + res);
					 
				return res;

			}
			
			double metVal1 = (double) metric.get(metricName);
			 if (metVal1 == metricVal){
				res = 0;
				 System.out.println("Metric compare = " + res);
					 
				return res;

			 }
			 else{
				res = 1;
				 System.out.println("Metric compare = " + res);
					
				return res;

			 }
			 	
	}
	public int gapCalc(String metricName, double timeStamp){

		return 0;
	}


}
