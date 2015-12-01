
/*
 * Copyright (c) 2014-5 UK Science and Technology Facilities Council
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.metasolver;

//package org.ow2.paasage.upperware.metasolver;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import eu.paasage.upperware.metasolver.solutionListener;
import eu.paasage.upperware.metasolver.RPListener;
import eu.paasage.upperware.metasolver.adaptorListener;
import eu.paasage.upperware.metasolver.metricsListener;
import eu.paasage.upperware.metasolver.exception.MetricMapperException;
import eu.paasage.upperware.metasolver.metrics.Mapper;

import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.eclipsesource.json.JsonObject;

//The metasolver is responsible for calling the different solvers in PaaSage.

public class metasolver{
   

	public static void main(String args[]) throws IOException, InterruptedException, MetricMapperException{

		try{
			String CAMELmodID= args[0];
			String CPmodID= args[1];
			int jobID = Integer.parseInt(args[2]);;
			Mapper map = new Mapper();
			/* syc17 26Nov15 aligned with updated mapper
			long mapResult = map.mapMetricVariables(CPmodID);
			*/
			JsonObject jObj = map.mapMetricVariables(CPmodID);
			if(jobID == 1){
			runMILPSolver(jObj.get("id").asString(), jObj.get("solution_tmp").asLong());
			}
			else{
				runCPSolver(CPmodID);
		}
				//now invoke S2D
			//runS2D(CAMELmodID, CPmodID, mapResult);
			//runS2D(CAMELmodID, jObj.get("id").asString(), jObj.get("solution_tmp").asLong());
		}
		catch(Exception e){
			System.out.println("error running metasolver " + e);
		}
	}
	public void invokeMILP(String modID){
		
		try{
//			String modID= args[1];
			Mapper map = new Mapper();
			/* syc17 26Nov15 aligned with updated mapper
			long mapResult = map.mapMetricVariables(modID);
			*/
			JsonObject jObj = map.mapMetricVariables(modID);
			//runMILPSolver("modID", mapResult);
			runMILPSolver(jObj.get("id").asString(), jObj.get("solution_tmp").asLong());
		}
		catch(Exception e){
			System.out.println("error starting metasolver " + e);
		}
	}
	
	//}
	public void startSolving(String CAMELmodel, String CPmodel) throws MetricMapperException{
		System.out.println("starting solving");
					go(CAMELmodel, CPmodel);
					System.out.println("all subscriptions complete");
			}
	public void go(String CAMELmodel, String CPmodel) throws MetricMapperException{
		Mapper map = new Mapper();
		/* 26Nov15 syc17 aigned with uppdated Mapper code
		long mapResult = map.mapMetricVariables(CPmodel); */
		JsonObject jObj = map.mapMetricVariables(CPmodel);
		RPListener rpl = new RPListener();
    	 metricsListener ml = new metricsListener("metricID");		
		 //solutionListener sl = new solutionListener(CAMELmodel, CPmodel, mapResult);
    	 solutionListener sl = new solutionListener(CAMELmodel, jObj.get("id").asString(), jObj.get("solution_tmp").asLong());
			//Currently we only have one solver and this is invoked taking in the ResourceID from the masterscript	

		
		rpl.start();
		System.out.println("rpl done");
		sl.start();
		System.out.println("sl done");
	    ml.start();
		System.out.println("ml done");

	//	al.start();
	
	}

	public static void runMILPSolver(String input, long timestamp){

		try{	
			
			System.out.println("... about to call milp-solver : java -jar milp-solver-assembly.jar " + input +' ' + timestamp + "....");
			Process p1 = Runtime.getRuntime().exec("java -jar milp-solver-assembly.jar " + input +' ' + timestamp);
			//Process p1 = Runtime.getRuntime().exec("java -jar milp-solver-assembly.jar " + input);


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


	public static void runCPSolver(String input){

		try{	
			
			System.out.println("... about to call cp-solver : java -jar cp-solver.jar $PAASAGE_CONFIG_DIR" + input +" ");
			Process p1 = Runtime.getRuntime().exec("java -jar milp-solver-assembly.jar $PAASAGE_CONFIG_DIR" + input + "");
			//Process p1 = Runtime.getRuntime().exec("java -jar milp-solver-assembly.jar " + input);


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

	
	public static void runS2D(String CAMELmodel, String CPmodel, long timestamp){

		try{
			System.out.println("... about to call s2D : java -jar solver-to-deployment-2015.9.1-SNAPSHOT.jar-with-dependencies.jar " + CPmodel + ' ' + CAMELmodel + ' ' +timestamp);
			Process p1 = Runtime.getRuntime().exec("java -jar solver-to-deployment-2015.9.1-SNAPSHOT.jar-with-dependencies.jar " + CPmodel + ' ' + CAMELmodel + ' ' +timestamp);
		
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
