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

package eu.melodic.upperware.metasolver.util;

import eu.paasage.mddb.cdo.client.exp.CDOClientXImpl;
import eu.paasage.mddb.cdo.client.exp.CDOSessionX;
import eu.paasage.upperware.metamodel.application.ApplicationPackage;
import eu.paasage.upperware.metamodel.cp.*;
import eu.paasage.upperware.metamodel.types.DoubleValueUpperware;
import eu.paasage.upperware.metamodel.types.TypesPackage;
import eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class UtilCpModelImport {


  public static void main(String[] args) {
	System.out.println("BEGIN");
	int op = -1;
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
    protected static String resourceId = "/CRMApp1531746091371_test";

  protected static void testGetCpModel() {
	  try {
          CpPackage.eINSTANCE.eClass();
          CDOClientXImpl cdoClient = new CDOClientXImpl(Arrays.asList(CpPackage.eINSTANCE));

          //CDONet4jSession cdoSession = openSession();
          CDOSessionX session = cdoClient.getSession();
          CDOView cdoView = session.openView();

          // Get CP model
          CDOResource resource = cdoView.getResource(resourceId);
          ConstraintProblem cpModel = (ConstraintProblem) resource.getContents().get(0);

          // Print Delta Utility
          printDeltaUtility(cpModel);

          cdoView.close();
          session.closeSession();

	  } catch (Exception ex) {
		  System.err.println(ex.toString());
		  ex.printStackTrace(System.err);
	  }
  }

  protected static void testUpdateCpModel() {
      CDOSessionX session = null;
      CDOTransaction transaction = null;
	  try {
          CpPackage.eINSTANCE.eClass();
          CDOClientXImpl cdoClient = new CDOClientXImpl(Arrays.asList(CpPackage.eINSTANCE));

          session = cdoClient.getSession();
          transaction = session.openTransaction();

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

          transaction = null;
          session.closeSession();

	  } catch (Exception ex) {
		  System.err.println(ex.toString());
		  ex.printStackTrace(System.err);
      } finally {
          if (transaction != null) {
              transaction.rollback();
              transaction.close();
          }
          if (session != null) {
              session.closeSession();
              session = null;
          }
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
        CDOSessionX session = null;
        CDOTransaction transaction = null;
        CDOTransaction transaction2 = null;

	  try {
		// Initialize CDO classes
		//CpModelTool.init();
		init();
		// ... or the next....
		CpPackage.eINSTANCE.eClass();

          // Load CP model from XMI
          String xmiFile = "meta_solver/src/main/resources/tests_files/CRMApp1531746091371_test.xmi";
		if (args.length>1 && !args[1].trim().isEmpty()) xmiFile = args[1].trim();
		Resource resModel = loadFile(xmiFile);
		String resourceId = UtilCpModelImport.resourceId;
		if (args.length>2 && !args[2].trim().isEmpty()) resourceId = args[2].trim();

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

          // Add DeltaUtility to avoid NullPointerException
		ConstraintProblem cpModel = (ConstraintProblem)resModel.getContents().get(0);
		/*System.out.println( "DeltaUtility: "+cpModel.getDeltaUtility());
		DeltaUtility du = CpFactory.eINSTANCE.createDeltaUtility();
		du.setId("zzzz-du-id-1");
		cpModel.setDeltaUtility(du);
		*/
		cpModel.setDeployedSolutionId(1);
		cpModel.setCandidateSolutionId(2);

          CDOClientXImpl cdoClient = new CDOClientXImpl(Arrays.asList(CpPackage.eINSTANCE));

		// Store in CDO
          session = cdoClient.getSession();
          transaction = session.openTransaction();
		System.out.println( "Saving to : "+resourceId);
		CDOResource resource = transaction.getOrCreateResource(resourceId);

          resource.getContents().clear();
		resource.getContents().add(resModel.getContents().get(0));
		transaction.commit();
		System.out.println( "Saved!" );
          transaction = null;


		// Retrieve CP model from CDO and print it
		System.out.println( "Retrieving from : "+resourceId);
          transaction2 = session.openTransaction();
		CDOResource resource2 = transaction2.getResource(resourceId);
		ConstraintProblem cpModel_2 = (ConstraintProblem)resource2.getContents().get(0);
		ByteArrayOutputStream output2 = new ByteArrayOutputStream();
		cpModel_2.eResource().save(output2, null);
		System.out.println( output2.toString() );
		transaction2.close();
          transaction2 = null;


		// Close CDO session
          session.closeSession();

      } catch (Exception ex) {
          System.err.println(ex.toString());
          ex.printStackTrace(System.err);
      } finally {
          if (transaction != null) {
              transaction.rollback();
              transaction.close();
          }
          if (transaction2 != null) {
              transaction2.rollback();
              transaction2.close();
          }
          if (session != null) {
              session.closeSession();
              session = null;
          }
      }
  }
}