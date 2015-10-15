
/*
 * Copyright (c) 2014-5 UK Science and Technology Facilities Council
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.metasolver;

//package org.ow2.paasage.upperware.metasolver;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

//The metasolver is responsible for calling the different solvers in PaaSage.

public class metasolver{


//Currently we only have one solver and this is invoked taking in the ResourceID from the masterscript	
public static void main(String args[]) throws IOException, InterruptedException{
	
	
	
	//first we subscribe
	
	//Rule Processor
	if (args[0].contains("withZEROMQ")){
		RPListener rpl = new RPListener("metricID");
		rpl.run();
		}
				
	//Solvers
	if (args[0].contains("withZEROMQ")){
	solutionListener sl = new solutionListener("metricID");
	sl.run();
	}
	
	//Adaptor
	if (args[0].contains("withZEROMQ")){
		adaptorListener sl = new adaptorListener("metricID");
		sl.run();
		}
	
	//MetricsCollector
	if (args[0].contains("withZEROMQ")){
		metricsListener sl = new metricsListener("metricID");
		sl.run();
		}
	
	try{
	
	//		Process p1 = Runtime.getRuntime().exec("java -jar milp-solver-2015.04-SNAPSHOT-assembly.jar " + args[1]);
			Process p1 = Runtime.getRuntime().exec("java -jar milp-solver-assembly.jar " + args[1]);
			 
		     // you can pass the system command or a script to exec command. here i used uname -a system command
		             BufferedReader stdInput = new BufferedReader(new
		             InputStreamReader(p1.getInputStream()));

		     BufferedReader stdError = new BufferedReader(new
		             InputStreamReader(p1.getErrorStream()));

		     // read the output from the command
		     String s1="";
		     StringBuilder sb = new StringBuilder(); 
		     while ((s1 = stdInput.readLine()) != null) {
		        
		         sb.append(s1);
		            sb.append("\n");
		     }

		     while ((s1 = stdError.readLine()) != null) {
		         
		         sb.append(s1);
		            sb.append("\n");
		     }

		     
		     s1= sb.toString();
		System.out.println(" output = " + s1);
		     
		     if (s1.length() > 1 || Integer.parseInt(s1) > 0){
		    	 
		    //		Process p2 = Runtime.getRuntime().exec("./LAStart " + args[1]);
                    String s2 = "";
    	     while ((s2 = stdInput.readLine()) != null) {
    	    	 
    	         System.out.println("Std OUT: "+s2);
    	     }

    	     while ((s2 = stdError.readLine()) != null) {
    	         System.out.println("Std ERROR : "+s2);
    	     }

     }

 } catch (IOException e) {

     e.printStackTrace();
 }
	
}
}