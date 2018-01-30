/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

/*
Remark: Need to run this springboot application with the following parameter:
--spring.config.location=classpath:/config/eu.melodic.upperware.metasolver.properties

This provides application with the properties (in that way can be provided externally)

*/

package eu.melodic.upperware.metasolver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import org.eclipse.emf.cdo.eresource.CDOResource;
//import org.eclipse.emf.common.util.URI;
//import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
//import org.eclipse.emf.ecore.resource.ResourceSet;
//import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
//import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

//import com.eclipsesource.json.JsonObject;
//import eu.paasage.mddb.cdo.client.CDOClient;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
//import eu.paasage.upperware.metamodel.cp.DeltaUtility;
import eu.paasage.upperware.metamodel.cp.CpFactory;
import eu.paasage.upperware.metamodel.cp.Variable;
import eu.paasage.upperware.metamodel.cp.*;
import eu.paasage.upperware.metamodel.types.*;
//import eu.paasage.upperware.metamodel.cp.MetricVariable;
//import eu.paasage.upperware.metasolver.exception.MetricMapperException;
//import eu.paasage.upperware.metasolver.metrics.Mapper;
//import eu.paasage.upperware.metasolver.util.CpModelTool;
//import eu.passage.upperware.commons.model.tools.CdoTool;
//import org.apache.log4j.Logger;
import org.eclipse.emf.cdo.view.CDOView;
//import org.eclipse.emf.common.util.EList;
//import org.eclipse.emf.ecore.EObject;
import java.util.HashMap;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.net4j.CDONet4jSession;
import org.eclipse.emf.cdo.net4j.CDONet4jSessionConfiguration;
import org.eclipse.emf.cdo.net4j.CDONet4jUtil;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.util.ConcurrentAccessException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.net4j.Net4jUtil;
import org.eclipse.net4j.connector.IConnector;
import org.eclipse.net4j.tcp.TCPUtil;
import org.eclipse.net4j.util.container.ContainerUtil;
import org.eclipse.net4j.util.container.IManagedContainer;

// From: eu.paasage.mddb.cdo.client.CDOClient
import org.eclipse.emf.cdo.eresource.EresourcePackage;
import org.eclipse.emf.ecore.EPackage;

import eu.paasage.camel.CamelFactory;
import eu.paasage.camel.CamelModel;
import eu.paasage.camel.CamelPackage;
import eu.paasage.camel.Model;
import eu.paasage.camel.requirement.Requirement;
import eu.paasage.camel.requirement.OptimisationRequirement;
import eu.paasage.camel.requirement.RequirementModel;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.DeploymentPackage;
//import eu.paasage.camel.dsl.CamelDslStandaloneSetup;
import eu.paasage.camel.execution.ExecutionModel;
import eu.paasage.camel.execution.ExecutionPackage;
import eu.paasage.camel.location.Country;
import eu.paasage.camel.location.Location;
import eu.paasage.camel.location.LocationFactory;
import eu.paasage.camel.location.LocationModel;
import eu.paasage.camel.location.LocationPackage;
import eu.paasage.camel.metric.MetricModel;
import eu.paasage.camel.metric.MetricPackage;
import eu.paasage.camel.organisation.CloudProvider;
import eu.paasage.camel.organisation.DataCenter;
import eu.paasage.camel.organisation.ExternalIdentifier;
import eu.paasage.camel.organisation.OrganisationFactory;
import eu.paasage.camel.organisation.OrganisationModel;
import eu.paasage.camel.organisation.OrganisationPackage;
import eu.paasage.camel.organisation.PaaSageCredentials;
import eu.paasage.camel.organisation.Role;
import eu.paasage.camel.organisation.RoleAssignment;
import eu.paasage.camel.organisation.User;
import eu.paasage.camel.organisation.UserGroup;
import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.camel.provider.ProviderPackage;
import eu.paasage.camel.scalability.ScalabilityModel;
import eu.paasage.camel.scalability.ScalabilityPackage;
import eu.paasage.camel.security.SecurityModel;
import eu.paasage.camel.security.SecurityPackage;
import eu.paasage.camel.requirement.RequirementPackage;
import eu.paasage.camel.type.TypeModel;
import eu.paasage.camel.type.TypePackage;
import eu.paasage.camel.unit.UnitModel;
import eu.paasage.camel.unit.UnitPackage;

import eu.paasage.camel.Application;
import eu.paasage.camel.deployment.Component;
import eu.paasage.camel.metric.Metric;
import eu.paasage.camel.metric.MetricContext;
import eu.paasage.camel.metric.Property;

import eu.paasage.upperware.metamodel.application.ApplicationPackage;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.types.TypesPackage;
import eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage;


public class UtilCpModelImport {

  public static void main(String[] args) {
	System.out.println("BEGIN");
	int op = 1;
	if (args.length>0) op = Integer.parseInt(args[0]);
	switch (op) {
	case 1:
		System.out.println("Importing CP model from XMI file into CDO resource: "+resourceId+"...");
		importCpModel(args); break;
	case 2:
		System.out.println("Retrieving CP model from CDO resource: "+resourceId+"...");
		testGetCpModel(); break;
	case 3:
		System.out.println("Updating CP model in CDO resource: "+resourceId+"...");
		testUpdateCpModel(); break;
	default:
		System.out.println("Invalid option. Valid options:");
		System.out.println("  1: import CP model from file");
		System.out.println("  2: retrieve CP model from CDO");
		System.out.println("  3: update CP model in CDO");
	}
	System.out.println("END");
  }
  
	// Connect to CDO
	protected static String connectionStr = "localhost:2036";
	protected static String repoName = "repo1";
	protected static String resourceId = "/models/cp-scalarm--3";
	
  protected static void testGetCpModel() {
	  try {
			CpPackage.eINSTANCE.eClass();
			CDONet4jSession cdoSession = openSession();
			CDOView cdoView = cdoSession.openView();
			
			// Get CP model
			CDOResource resource = cdoView.getResource(resourceId);
			ConstraintProblem cpModel = (ConstraintProblem)resource.getContents().get(0);
			
			// Print Delta Utility
			printDeltaUtility( cpModel );
			
			cdoView.close();
			cdoSession.close();
			
	  } catch (Exception ex) {
		  System.err.println(ex.toString());
		  ex.printStackTrace(System.err);
	  }
  }
  
  protected static void testUpdateCpModel() {
	  try {
			CpPackage.eINSTANCE.eClass();
			CDONet4jSession cdoSession = openSession();
			CDOTransaction transaction = cdoSession.openTransaction();
			
			// Get CP model
			CDOResource resource = transaction.getResource(resourceId);
			ConstraintProblem cpModel = (ConstraintProblem)resource.getContents().get(0);
			
			// Print Delta Utility - BEFORE UPDATE
			System.out.println("-------------  BEFORE UPDATE  --------------");
			printDeltaUtility( cpModel );
			
			// Add new solution to Delta Utility
			System.out.println("-------------      UPDATE     --------------");
/*			DeltaUtility du = cpModel.getDeltaUtility();
			EList<Variable> cpVarList = cpModel.getVariables();
			EList<MetricVariable> cpMVarList = cpModel.getMetricVariables();
			
			long ts = System.currentTimeMillis();
			Solution newSolution = CpFactory.eINSTANCE.createSolution();
			newSolution.setTimestamp( ts );
			EList<VariableValue> vvList  = newSolution.getVariableValue();
			EList<MetricVariableValue> mvvList = newSolution.getMetricVariableValue();
			for (int ii=1; ii<=5; ii++) {
				MetricVariable mvar = CpFactory.eINSTANCE.createMetricVariable();
				mvar.setId( "new-mvar--"+ii );
				mvar.setType( BasicTypeEnum.DOUBLE );
				cpMVarList.add(mvar);
				
				DoubleValueUpperware val = TypesFactory.eINSTANCE.createDoubleValueUpperware();
				val.setValue( 1000*Math.random() );
				
				MetricVariableValue mvv = CpFactory.eINSTANCE.createMetricVariableValue();
				mvv.setVariable(mvar);
				mvv.setValue(val);
				mvvList.add( mvv );
				
				//
				DoubleValueUpperware dZero = TypesFactory.eINSTANCE.createDoubleValueUpperware();
				DoubleValueUpperware dm100 = TypesFactory.eINSTANCE.createDoubleValueUpperware();
				dZero.setValue(0);
				dm100.setValue(-100);
				RangeDomain domain = CpFactory.eINSTANCE.createRangeDomain();
				domain.setFrom(dZero);
				domain.setTo(dm100);
				
				Variable var = CpFactory.eINSTANCE.createVariable();
				var.setId( "new-var--"+ii );
				var.setDomain( domain );
				cpVarList.add(var);
				
				DoubleValueUpperware val2 = TypesFactory.eINSTANCE.createDoubleValueUpperware();
				val2.setValue( -100*Math.random() );
				
				VariableValue vv = CpFactory.eINSTANCE.createVariableValue();
				vv.setVariable(var);
				vv.setValue(val2);
				vvList.add( vv );
			}
			
			Parameter newParam = CpFactory.eINSTANCE.createParameter();
			newParam.setName("new-param--"+ts);
			newParam.setSolution(newSolution);
			du.getSolutions().add( newParam );
			du.setOperator(OperatorEnum.PLUS);
			cpModel.getSolution().add(newSolution);
			
			transaction.commit();
*/			
			// Print Delta Utility - AFTER UPDATE
			System.out.println("-------------  AFTER UPDATE   --------------");
			printDeltaUtility( cpModel );
			
			cdoSession.close();
			
	  } catch (Exception ex) {
		  System.err.println(ex.toString());
		  ex.printStackTrace(System.err);
	  }
  }
  
  protected static void printDeltaUtility(ConstraintProblem cpModel) {
		// Print Delta Utility
/*		DeltaUtility du = cpModel.getDeltaUtility();
		Parameter solSelected = du.getSelectedSolution();
		System.out.println("==== SELECTED SOLUTION ====");
		printSolution( solSelected );
		System.out.println();
		System.out.println("==== SOLUTION LIST ====");
		EList<Parameter> solutions = du.getSolutions();
		for (int i=0, n=solutions.size(); i<n; i++) {
			Parameter sol = solutions.get(i);
			printSolution( sol );
		}*/
  }
  
  protected static void printSolution(Parameter param) {
	  if (param==null) {
		  System.out.println("***** null argument");
		  return;
	  }
	  String name = param.getName();
	  Solution sol = param.getSolution();
	  System.out.println("***** param: name="+name);
	  if (sol==null) return;
	  
	  long ts = sol.getTimestamp();
	  EList<VariableValue> vvList = sol.getVariableValue();
	  EList<MetricVariableValue> mvvList = sol.getMetricVariableValue();
	  
	  System.out.println("***** param : timestamp="+ts);
	  System.out.println("--- Metric Values");
	  for (int i=0, n=vvList.size(); i<n; i++) {
		  VariableValue vv = vvList.get(i);
		  Variable var = vv.getVariable();
		  DoubleValueUpperware v = (DoubleValueUpperware)vv.getValue();
		  System.out.println("  Var.Value:  name="+var.getId()+", value="+v.getValue());
	  }
	  System.out.println("--- Metric Variable Values");
	  for (int i=0, n=mvvList.size(); i<n; i++) {
		  MetricVariableValue mvv = mvvList.get(i);
		  MetricVariable mv = mvv.getVariable();
		  DoubleValueUpperware v = (DoubleValueUpperware)mvv.getValue();
		  System.out.println("  Metric Var.Value:  name="+mv.getId()+", value="+v.getValue());
	  }
  }
  
	public static void init() {
		//
		//log.debug("initialising model ....");
		// initialise the Upperware model packages
		ApplicationPackage.eINSTANCE.eClass();
		TypesPaasagePackage.eINSTANCE.eClass(); 
		TypesPackage.eINSTANCE.eClass(); 
		CpPackage.eINSTANCE.eClass();
//		OntologyPackage.eINSTANCE.eClass();
		// Register the XMI resource factory for the .xmi extension
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*",
				new XMIResourceFactoryImpl());
	}
	
	public static Resource loadFile(String cpModelFilePath) {
		URI uri = URI.createURI(cpModelFilePath);
		ResourceSet resSet = new ResourceSetImpl();
		// load the cpModel xmi file
		Resource resource = resSet.getResource(uri, true);
		EcoreUtil.resolveAll(resSet);
		try {
			resource.load(null);
			for (Resource.Diagnostic diagnostic : resource.getWarnings()) {
				// print the issues
				System.err.println("loading resource(" + cpModelFilePath
						+ ") produced warning : " + diagnostic.toString());
			}
			for (Resource.Diagnostic error : resource.getErrors()) {
				// print the errors
				System.err.println("loading resource(" + cpModelFilePath
						+ ") produced error : " + error.toString());
			}
		} catch (IOException ioe) {
			System.err.println("loading resource(" + cpModelFilePath
					+ ") caused IOException: " + ioe.getMessage());
		} catch (Exception e) {
			System.err.println("loading resource(" + cpModelFilePath
					+ ") caused Exception: " + e.getMessage());
		}
		return resource;

	}
	
  protected static void importCpModel(String args[]) {
	  try {
		// Initialize CDO classes
		//CpModelTool.init();
		init();
		// ... or the next....
		CpPackage.eINSTANCE.eClass();
		
		// Load CP model from XMI
		//String xmiFile = "var/PaaSageConfiguration1ConstraintProblem.xmi";
		String xmiFile = "var/FCR1516954427666.xmi";
		if (args.length>0 && !args[0].trim().isEmpty()) xmiFile = args[0].trim();
		Resource resModel = loadFile(xmiFile);
		
		// Print CP model (XMI)
		java.io.ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();
		resModel.getContents().get(0).eResource().save(output, null);
		System.out.println( output.toString() );
		
		// Print CP model info
		/*ConstraintProblem cpModel_0 = (ConstraintProblem)resModel.getContents().get(0);
		EList<Goal> goals = cpModel_0.getGoals();
		for (int i=0, n=goals.size(); i<n; i++) System.out.printf("\t%s / %f\n", goals.get(i).getId(), goals.get(i).getPriority());
		EList<Variable> vars = cpModel_0.getVariables();
		for (int i=0, n=vars.size(); i<n; i++) System.out.printf("\t%s / [%d..%d]\n", vars.get(i).getId(), ((IntegerValueUpperware)((RangeDomain)vars.get(i).getDomain()).getFrom()).getValue(), ((IntegerValueUpperware)((RangeDomain)vars.get(i).getDomain()).getTo()).getValue());
		*/
		
		CDONet4jSession cdoSession = openSession();
		
		// Add DeltaUtility to avoid NullPointerException
		ConstraintProblem cpModel = (ConstraintProblem)resModel.getContents().get(0);
		/*System.out.println( "DeltaUtility: "+cpModel.getDeltaUtility());
		DeltaUtility du = CpFactory.eINSTANCE.createDeltaUtility();
		du.setId("zzzz-du-id-1");
		cpModel.setDeltaUtility(du);
		*/
		
		// Store in CDO
		System.out.println( "Saving to : "+resourceId);
		CDOTransaction transaction = cdoSession.openTransaction();
		CDOResource resource = transaction.getOrCreateResource(resourceId);
		
		resource.getContents().clear();
		resource.getContents().add(resModel.getContents().get(0));
		transaction.commit();
		System.out.println( "Saved!" );
		
		
		// Retrieve CP model from CDO and print it
		System.out.println( "Retrieving from : "+resourceId);
		CDOTransaction transaction2 = cdoSession.openTransaction();
		CDOResource resource2 = transaction2.getResource(resourceId);
		ConstraintProblem cpModel_2 = (ConstraintProblem)resource2.getContents().get(0);
		ByteArrayOutputStream output2 = new ByteArrayOutputStream();
		cpModel_2.eResource().save(output2, null);
		System.out.println( output2.toString() );
		transaction2.close();
		
		
		// Close CDO session
		cdoSession.close();
		
	  } catch (Exception ex) {
		  System.err.println(ex.toString());
		  ex.printStackTrace(System.err);
	  }
  }
  
  protected static CDONet4jSession openSession() {
		// initialize and activate a container
		final IManagedContainer container = ContainerUtil.createContainer();
		Net4jUtil.prepareContainer(container);
		TCPUtil.prepareContainer(container);
		// CDONet4jUtil.prepareContainer(container);
		container.activate();
		
		// create a Net4j TCP connector
		final IConnector connector = (IConnector) TCPUtil.getConnector(container, connectionStr);

		// create the session configuration
		CDONet4jSessionConfiguration config = CDONet4jUtil
				.createNet4jSessionConfiguration();
		config.setConnector(connector);
		config.setRepositoryName(repoName);

		// setup authentication
		/*boolean cdoRequiresAuthentication = false;
		if (cdoRequiresAuthentication) {
			System.out.printf("** CDO server requires authentication - Username: %s\n", cdoUsername);
			org.eclipse.net4j.util.security.PasswordCredentialsProvider credentialsProvider = new org.eclipse.net4j.util.security.PasswordCredentialsProvider(cdoUsername, cdoPassword);
			config.setCredentialsProvider(credentialsProvider);
		}*/
		
		// create the actual session with the repository
		CDONet4jSession cdoSession = config.openNet4jSession();
		
		// register CAMEL packages
		cdoSession.getPackageRegistry().putEPackage(EresourcePackage.eINSTANCE);
		cdoSession.getPackageRegistry().putEPackage(CamelPackage.eINSTANCE);
		cdoSession.getPackageRegistry().putEPackage(ScalabilityPackage.eINSTANCE);
		cdoSession.getPackageRegistry().putEPackage(DeploymentPackage.eINSTANCE);
		cdoSession.getPackageRegistry().putEPackage(OrganisationPackage.eINSTANCE);
		cdoSession.getPackageRegistry().putEPackage(ProviderPackage.eINSTANCE);
		cdoSession.getPackageRegistry().putEPackage(SecurityPackage.eINSTANCE);
		cdoSession.getPackageRegistry().putEPackage(ExecutionPackage.eINSTANCE);
		cdoSession.getPackageRegistry().putEPackage(TypePackage.eINSTANCE);
		cdoSession.getPackageRegistry().putEPackage(RequirementPackage.eINSTANCE);
		cdoSession.getPackageRegistry().putEPackage(MetricPackage.eINSTANCE);
		cdoSession.getPackageRegistry().putEPackage(UnitPackage.eINSTANCE);
		cdoSession.getPackageRegistry().putEPackage(LocationPackage.eINSTANCE);
		
		return cdoSession;
  }
}