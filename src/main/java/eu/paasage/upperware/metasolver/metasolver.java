
/*
 * Copyright (c) 2014-5 UK Science and Technology Facilities Council
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.metasolver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

import eu.paasage.camel.type.TypePackage;
import eu.paasage.mddb.cdo.client.CDOClient;
import eu.paasage.upperware.metamodel.application.ApplicationPackage;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.cp.MetricVariable;
import eu.paasage.upperware.metamodel.types.TypesPackage;
import eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage;
import eu.paasage.upperware.metasolver.exception.MetricMapperException;
import eu.paasage.upperware.metasolver.solutionListener;
import eu.paasage.upperware.metasolver.RPListener;
import eu.paasage.upperware.metasolver.metricsListener;
import eu.paasage.upperware.metasolver.metrics.Mapper;

import org.apache.log4j.Logger;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.eclipsesource.json.JsonObject;

//The metasolver is responsible for calling the different solvers in PaaSage.

public class metasolver{

	private static Logger log = Logger.getLogger(metasolver.class);

	public void doWork(String CPmodID, String metricFile, int solver) throws MetricMapperException, IOException
	{
		Mapper map = new Mapper();
		/* syc17 26Nov15 aligned with updated mapper
		long mapResult = map.mapMetricVariables(CPmodID);
		 */
		CDOClient cdoClient = new CDOClient();
		cdoClient.registerPackage(ApplicationPackage.eINSTANCE);
		cdoClient.registerPackage(CpPackage.eINSTANCE);
		cdoClient.registerPackage(TypesPackage.eINSTANCE);
		cdoClient.registerPackage(TypesPaasagePackage.eINSTANCE);
		cdoClient.registerPackage(TypePackage.eINSTANCE);
		CDOView cdoView = cdoClient.openView();

		log.info("Reading CP model from CDO...");
		EList<EObject> contentsPC = cdoView.getResource(CPmodID).getContents();
		log.info("Extracting models...");
		//		PaasageConfiguration paasageConfiguration = (PaasageConfiguration) contentsPC.get(0);
		ConstraintProblem cp = (ConstraintProblem) contentsPC.get(1);

		HashMap<String, String> mets=null;
		// Handling Metric passed as input file (to be updated for reading from CDO)
		if(metricFile!=null){
			//get Metrics
			mets = readFile1(metricFile);
			//check Metrics
			boolean check = checkMets(mets, cp);
			if (check)
				System.out.println("MetricFile validated");
			else
				System.out.println("MetricFile not validated. Proceeding anyway");
		}
		log.info("Closing CDO...");
		cdoView.close();
		cdoClient.closeSession();
		

		log.info("Mapping metric variables...");
		JsonObject jObj;
		if (mets==null)
			jObj = map.mapMetricVariables(CPmodID);
		else {
			jObj = map.mapMetricVariables(CPmodID, mets);
		}

		if (true) {
			log.info("NOT INVOKING SOLVER");
			return;
		}

		log.info("Invoking a solver...");
		long timeStamp = jObj.get("solution_tmp").asLong();
		switch(solver) {
		case 1: runMILPSolver(jObj.get("id").asString(), timeStamp); break;
		case 2: runCPSolver(CPmodID, timeStamp); break;
		default: log.fatal("Unkown solver id. Should be 1 (MILP) or 2 (CP)");
		}	 

		log.info("Solver done");
	}

	public static void main(String args[]) throws IOException, InterruptedException, MetricMapperException{

		try{
			//			String CAMELmodID= args[0]; // useless ??
			String CPmodID= args[1];
			String metricFile=null;
			int solver=2; // default CP_SOLCER

			if (args.length==4) metricFile=args[3];
			if (args.length>=3) solver=Integer.parseInt(args[2]);

			metasolver ms = new metasolver();
			ms.doWork(CPmodID, metricFile, solver);

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

	public void startSolving(String CAMELmodel, String CPmodel) throws MetricMapperException{
		System.out.println("starting solving");
		go(CAMELmodel, CPmodel);
		System.out.println("all subscriptions complete");
	}

	public void go(String CAMELmodel, String CPmodel) throws MetricMapperException{
		Mapper map = new Mapper();
		/* 26Nov15 syc17 aligned with updated Mapper code
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
			String cmd="java -jar milp-solver-assembly.jar " + input +' ' + timestamp;
			System.out.println("... about to call milp-solver: "+cmd);
			Process p1 = Runtime.getRuntime().exec(cmd);
			//Process p1 = Runtime.getRuntime().exec("java -jar milp-solver-assembly.jar " + input);
			System.out.println("... about to call milp-solver: "+cmd+" DONE");

			// you can pass the system command or a script to exec command. here i used uname -a system command
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p1.getInputStream()));

			BufferedReader stdError = new BufferedReader(new InputStreamReader(p1.getErrorStream()));

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


	public static void runCPSolver(String input, long timestamp)
	{
		try
		{
			String cmd = "java -jar cp-solver-assembly.jar CDO " + input + " " + timestamp;
			System.out.println("... about to call cp-solver: "+cmd);
			Process p1 = Runtime.getRuntime().exec(cmd);
			System.out.println("... about to call cp-solver: "+cmd+" DONE");
			//Process p1 = Runtime.getRuntime().exec("java -jar milp-solver-assembly.jar " + input);

			// you can pass the system command or a script to exec command. here i used uname -a system command
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p1.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p1.getErrorStream()));

			// read the output from the command
			String s1="";
			StringBuilder sb = new StringBuilder(); 
			while (p1.isAlive() && (s1 = stdInput.readLine()) != null) {
				log.info("ReadI: "+s1);
				sb.append(s1);
				sb.append("\n");
			}

			while (p1.isAlive() && (s1 = stdError.readLine()) != null) {
				log.info("ReadE: "+s1);
				sb.append(s1);
				sb.append("\n");
			}

			s1= sb.toString();
			log.info(" output = " + s1);

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

	public static void runS2D(String CAMELmodel, String CPmodel, long timestamp)
	{
		try
		{
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
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Managing Metrics
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// Returns an hashmap filled with Metric variable/value read from a file
	public static HashMap<String, String> readFile1(String fin) throws IOException
	{
		FileInputStream fis = new FileInputStream(fin);
		HashMap<String, String> tt = new HashMap<String, String>();

		//Construct BufferedReader from InputStreamReader
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		//		int i=0;
		String d = null;
		while ((d = br.readLine()) != null) {	
			String[] t = d.split(":");				
			tt.put(t[0],t[1]);	
			//			i++;
		}

		br.close();
		return tt;
	}
	
	// return true if all variables in mets exist in CP
	public static boolean checkMets(HashMap<String, String> mets, ConstraintProblem cp)
	{
		EList<MetricVariable> mvs = cp.getMetricVariables();
		for(String  id : mets.keySet()) {
			log.info("Checking entry mv: "+id);

			boolean res=false;
			for(MetricVariable mv: mvs)
			{
				if (mv.getId().equals(id)) {
					res=true;
					break;
				}
			}
			if (!res)
			{
				log.fatal("Did not find "+id+" in MetricVariable!!");
				return false;
			}
		}
		return true;
	}

}
