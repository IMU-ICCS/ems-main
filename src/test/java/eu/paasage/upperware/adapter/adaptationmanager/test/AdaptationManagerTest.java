/*
 * Copyright (c) 2014 INRIA, INSA Rennes
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.adaptationmanager.test;

import static org.junit.Assert.assertTrue;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.mddb.cdo.client.CDOClient;
import eu.paasage.upperware.adapter.adaptationmanager.REST.ExecInterfacer;

import com.github.restdriver.clientdriver.ClientDriverRule;

import eu.paasage.upperware.adapter.adaptationmanager.input.CDOClientUtil;
import eu.paasage.upperware.adapter.adaptationmanager.input.ReasonerInterfacer;
import eu.paasage.upperware.adapter.adaptationmanager.mapping.GraphUtilities;
import eu.paasage.upperware.adapter.adaptationmanager.plangeneration.ModelComparator;
import eu.paasage.upperware.adapter.adaptationmanager.plangeneration.PlanGenerator;
import eu.paasage.upperware.adapter.adaptationmanager.validation.IValidator;
import eu.paasage.upperware.adapter.adaptationmanager.validation.ValidatorImpl;
import eu.paasage.upperware.adapter.adaptationmanager.actions.Action;
import eu.paasage.upperware.adapter.adaptationmanager.core.Coordinator;

import com.github.restdriver.clientdriver.ClientDriverRequest.Method;
import com.github.restdriver.clientdriver.RestClientDriver;
import com.github.restdriver.clientdriver.capture.BodyCapture;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import eu.paasage.upperware.adapter.adaptationmanager.REST.ExecutionwareError;
import eu.paasage.upperware.plangenerator.model.task.ConfigurationTask;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.CamelPackage;
import eu.paasage.camel.deployment.Component;
import eu.paasage.camel.deployment.InternalComponent;



import eu.paasage.upperware.adapter.adaptationmanager.core.AdaptationManager;
import eu.paasage.upperware.adapter.adaptationmanager.input.MyCDOClient;

import org.eclipse.emf.cdo.view.*;
/* *** COMPLAINING import statements ***
import org.eclipse.emf.ecore.EObject;

import java.util.HashMap;
import java.util.Map;

import eu.paasage.upperware.adapter.adaptationmanager.REST.ExecInterfacer;
import eu.paasage.upperware.adapter.adaptationmanager.actions.Action;
import eu.paasage.upperware.adapter.adaptationmanager.actions.ActionError;
import eu.paasage.upperware.adapter.adaptationmanager.plangeneration.Plan;
import eu.paasage.upperware.adapter.adaptationmanager.input.ReasonerInterfacer;*/

@RunWith(JUnit4.class)
public class AdaptationManagerTest

{
	private static final String TEST_SCALARMiNSTANCE = System.getProperty("user.dir")
			+ "/src/test/resources/ver2_0/ScalarmModelInstance.xmi";
	
	private static final String TEST_SCALARMiNSTANCE_Shirley = System.getProperty("user.dir")
			+ "/src/test/resources/ver2_0/Scalarm_full_Shirley.xmi";
	
	private static final String TEST_SCALARMiNSTANCE_Alessandro = System.getProperty("user.dir")
			+ "/src/test/resources/ver2_0/ScalarmModelInstance_Alessandro.xmi";
	
	private static final String TEST_Kyriakos = System.getProperty("user.dir")
			+ "/src/test/resources/ver2_0/test_kyriakos.xmi";
	
	private static final String TEST1 = System.getProperty("user.dir")
			+ "/src/test/resources/ver2_0/test1.xmi";

	private static final String TEST_INPUTFILE = System.getProperty("user.dir")
			+ "/src/test/resources/Scalarm_full.xmi";
	private static final String TEST_INPUTFILE2 = System
			.getProperty("user.dir") + "/src/test/resources/Scalarm_full2.xmi";
	private static final String TEST_INPUTFILE3 = System
			.getProperty("user.dir") + "/src/test/resources/Scalarm_full3.xmi";
	
	/**
	 * Response Strings for Client Driver
	 */
	private static final String res = "Created";
	private static final String token = "bo6t3h9fl1i6ie7fu2l1k1bafnsr0jv1qo5ampb2qlt1u1q2o12674qoad8qa1sh306d1riffu471nr055tv3ftqm0u07camoo9j2luut1pqm3oosb62sqgheh0aak0bm2266n730sa62eifj78qipj3elipdnt74mc1tas8h1qv60cld5nrn2f8gttfqf5r35ol4b0obcdos";
	
	/**
	 * Params for CDO server
	 */
//	private static final String TEST_APPLICATIONNAME = "Scalarm";
//	private static final String TEST_HOST = "localhost";
//	private static final String TEST_PORT = "2036";
//	private static final String TEST_REPOSITORYNAME = "repo1";
//	private static final String TEST_RESOURCENAME= "Scalarm";
	private static final String TEST_APPLICATIONNAME = "Scalarm";
	private static final String TEST_HOST = "54.246.101.26";
	private static final String TEST_PORT = "2036";
	private static final String TEST_REPOSITORYNAME = "repo1";
	private static final String TEST_RESOURCENAME= "Scalarm";
	
/*	@Rule
	public ClientDriverRule driver = new ClientDriverRule();*/
	
//	@Test
//	@Ignore
//	public void getBewanApp(){
//		CDOClient CDOcl = new CDOClient();
//		//CDOcl.exportModelWithRefRec("enterprise-service-application.xmi_1442060554", "/home/asinha/PaaSage/belgium-workshop/bewanApp/ModelWithRef.xmi", false);
//		CDOcl.exportModel("enterprise-service-application.xmi_1442060554", "/home/asinha/PaaSage/belgium-workshop/bewanApp/ModelWithRef.xmi");
//		//CDOcl.exportModelWithRef("enterprise-service-application.xmi_1442060554", "/home/asinha/PaaSage/belgium-workshop/bewanApp/ModelWithRef.xmi", "ModelWithRef.xmi");
//		CDOcl.closeSession();
//		
//		//CDOClientUtil mycdo = new CDOClientUtil("/home/asinha/PaaSage/belgium-workshop/bewanApp");
//		//mycdo.exportModelWithRefRecToDir("enterprise-service-application.xmi_1442060554", false);
//		//mycdo.tryLoadTwoFiles("/home/asinha/Desktop/test/test.xmi", "/home/asinha/Desktop/test/upperware-models_fms_1436444254010_GWDG-DE-1436444254477.xmi");
//	}
	
//	@Test
//	public void verifyCDOConnection(){
//		CDOClientUtil mycdo = new CDOClientUtil("/home/asinha/Desktop/test");
//		//mycdo.exportModelWithRefRecToDir("test", false);
//		mycdo.tryLoadTwoFiles("/home/asinha/Desktop/test/test.xmi", "/home/asinha/Desktop/test/upperware-models_fms_1436444254010_GWDG-DE-1436444254477.xmi");
//	}
	
//	@Test
//	public void verifyReasonerInterfacerCDO() {
//		
//		CDOClient CDOcl = new CDOClient();
//		CDOcl.exportModelWithRefRec("test", false);
//		CDOcl.exportModel("test", "/home/asinha/git/paasadapterOW2/paasadapter/src/test/resources/ver2_0/test1.xmi");
//		CDOcl.exportModelWithRef("test", "/home/asinha/git/paasadapterOW2/paasadapter/src/test/resources/ver2_0/test2.xmi");
//		CDOcl.closeSession();
//		
////		MyCDOClient cl;
////		// if there is no CDO server, the test passes
////		try {
////			cl = new MyCDOClient(TEST_HOST, TEST_PORT, TEST_REPOSITORYNAME);
////		}
////		catch (Exception e) {
////			return;
////		}
////		// Load TEST_INPUTFILE 
////		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*",
////				new XMIResourceFactoryImpl() {
////					public Resource createResource(URI uri) {
////						XMIResource xmiResource = new XMIResourceImpl(uri);
////						return xmiResource;
////					}
////				});
////
////		final ResourceSet rs = new ResourceSetImpl();
////		rs.getPackageRegistry().put(CamelPackage.eNS_URI,
////				CamelPackage.eINSTANCE);
////		Resource res = rs.getResource(URI.createFileURI(TEST_INPUTFILE), true);
////		EList<EObject> contents = res.getContents();
////		CamelModel cm = (CamelModel) contents.get(0);
////		//Store the model to CDO with the name TEST_RESOURCENAME
////		cl.storeModel(cm, TEST_RESOURCENAME);
////		// Load from CDO with ReasonerInterfacer
////		ReasonerInterfacer r = new ReasonerInterfacer(TEST_HOST, TEST_PORT,TEST_REPOSITORYNAME, TEST_RESOURCENAME);
////		DeploymentModel dm = (DeploymentModel) r.getDeploymentModel(false);
////		EList<InternalComponent> comps = dm.getInternalComponents();
////		boolean found = false;
////		for (InternalComponent c : comps) {
////			String name = c.getName();
////			if ("InformationService".equals(name)) {
////				found = true;
////				break;
////			}
////		}
////		assertTrue(
////				"Deployment model does not contain required internal component",
////				found);
//	}
	
	@Test
	@Ignore
	public void cloudCredentialsLoad(){
		ExecInterfacer exec = new ExecInterfacer();
		exec.getCloudUname("flexiant");
		exec.getCloudPass("flexianT");
		exec.getCloudEndpoint("flexIant");
		exec.getCloudUname("OmiStack");
		exec.getCloudPass("Omistack");
		exec.getCloudEndpoint("OmistAk");
	}
	
	@Test
//	@Ignore
	public void verifyNewPlanGenerator(){
		
		/*ReasonerInterfacer currentReasonerInterfacer = new ReasonerInterfacer(
				TEST_SCALARMiNSTANCE_Alessandro);*/
		ReasonerInterfacer currentReasonerInterfacer = null;
		/*currentReasonerInterfacer = new ReasonerInterfacer(
				TEST1);
		DeploymentModel current = currentReasonerInterfacer.getDeploymentModel(false);*/
		
		
		//CDOClientUtil mycdo = new CDOClientUtil("/home/asinha/Desktop/test");
		//mycdo.exportModelWithRefRecToDir("test", false);
		//DeploymentModel current = mycdo.tryLoadTwoFiles("/home/asinha/Desktop/test/test.xmi", "/home/asinha/Desktop/test/upperware-models_fms_1436444254010_GWDG-DE-1436444254477.xmi");
		
		//currentReasonerInterfacer = new ReasonerInterfacer("/home/asinha/Desktop/test", true, "test");
		
		/*currentReasonerInterfacer.openTransaction();
		DeploymentModel current = currentReasonerInterfacer.getLiveDeploymentModel();
		
		System.out.println("Loaded the CAMEL file successfully");
		
		DirectedGraph<ConfigurationTask, DefaultEdge> taskPlan = GraphUtilities.generatePlanGraph(null, current);
		System.out.println(taskPlan.toString());
		
		System.out.println("=============================================================================");
		
		currentReasonerInterfacer.closeTransaction();
		
		DirectedGraph<Action, DefaultEdge> actionGraph = GraphUtilities.taskGraphToActions(taskPlan);
		System.out.println(actionGraph.toString());*/
		
		
		//ExecInterfacer exec = new ExecInterfacer("http://131.254.13.194:9000");
		
		//currentReasonerInterfacer = new ReasonerInterfacer("test", false);
		//currentReasonerInterfacer = new ReasonerInterfacer("enterprise-service-application.xmi_1442302901", false);
		
		currentReasonerInterfacer = new ReasonerInterfacer("CAMEL_8ddc10704de9799cb890722a75e17f8f", false);
		//currentReasonerInterfacer = new ReasonerInterfacer("CAMEL_7eb47c883d9d43bc32beebb80f93950c", false);
		//currentReasonerInterfacer = new ReasonerInterfacer("/home/asinha/git/paasadapterOW2OS/adapter/src/test/resources/ver2_0/ScalarmModelInstance_Alessandro.xmi", true);
		//currentReasonerInterfacer = new ReasonerInterfacer("/home/asinha/git/paasadapterOW2OS/adapter/src/test/resources/FullDeploymentBewan-Y3Rev-Christian.xmi", true);
//		currentReasonerInterfacer = new ReasonerInterfacer("/home/asinha/git/paasadapterOW2OS/adapter/src/test/resources/BeWan_Deployment034553.xmi", true);
		
		ExecInterfacer exec = new ExecInterfacer();
		IValidator validator = new ValidatorImpl();
		Coordinator c = new Coordinator(currentReasonerInterfacer, exec, validator);
		
		c.startThreaded();
		System.out.println("End of method verifyParallelDeployment()");
		
		
/*		BufferedImage img = GraphUtilities.graphToImage(actionGraph);
		File outputfile = new File("/home/asinha/git/paasadapterOW2/paasadapter/src/test/resources/ver2_0/graph.png");
	    try {
	    	System.out.println(ImageIO.getWriterFormatNames());
			ImageIO.write(img, "jpg", outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//GraphUtilities.viewImage(actionGraph);
		//GraphUtilities.exportImage(actionGraph);
	}
	
/*	@Test
	public void verifyCamel2_0Load(){

		ReasonerInterfacer currentReasonerInterfacer = new ReasonerInterfacer(
				TEST_SCALARMiNSTANCE_Alessandro);
		
		DeploymentModel current = currentReasonerInterfacer.getDeploymentModel(false);
		System.out.println("Loaded the CAMEL file successfully");
	}*/
	
/*	@Test
	public void verifyLoginExecWare() {
//		driver.addExpectation(
//				RestClientDriver.onRequestTo(ExecInterfacer.API_LOGIN)
//						.withMethod(Method.POST), RestClientDriver
//						.giveResponse(token, "application/json").withStatus(200));
//		ExecInterfacer exec = new ExecInterfacer(driver.getBaseUrl());
		ExecInterfacer exec = new ExecInterfacer("http://131.254.13.194:9000");
		try {
			exec.login("john.doe@example.com", "admin");
		} catch (ExecutionwareError e) {
			e.printStackTrace();
		}

	}*/
	
/*//Commented for Shirley's PlanGenerator integration	
   @Test
	public void verifySimpleDeploymentCamel2_0() {
//		driver.addExpectation(
//				RestClientDriver.onRequestTo(ExecInterfacer.SIMPLEDEPLOY_PATH)
//						.withMethod(Method.POST), RestClientDriver
//						.giveResponse(res, "text/plain").withStatus(201));
//		ExecInterfacer exec = new ExecInterfacer(driver.getBaseUrl());
		ReasonerInterfacer reasonerInterfacer = new ReasonerInterfacer(
				TEST_SCALARMiNSTANCE_Alessandro);
		ExecInterfacer exec = new ExecInterfacer("http://131.254.13.194:9000");
		IValidator validator = new ValidatorImpl();
		PlanGenerator planGenerator = new PlanGenerator(true, exec);
		Coordinator c = new Coordinator(reasonerInterfacer, exec, validator,
				planGenerator);
//		try {
//			c.runStep();
//		} finally {
//			c.terminate();
//		}
		c.startThreaded();
		
//		JsonObject jsonObject = exec.getDeployed();
//		assertTrue("JSON file does not contain all components",
//				jsonObject.get("components").asArray().size() == 4);
//		assertTrue("JSON file does not contain all communications", jsonObject
//				.get("communications").asArray().size() == 6);
//		boolean found = false;
//		for (JsonValue value : jsonObject.get("components").asArray()) {
//			if (value.asObject().get("name").asString()
//					.equals("informationServiceInstance")) {
//				found = true;
//				break;
//			}
//		}
//		assertTrue("JSON file does not contain required component", found);
	}*/
	
	
/*	@Test
	public void verifyParallelDeployment(){
		System.out.println("Start of method verifyParallelDeployment()");
		
//		driver.addExpectation(
//				RestClientDriver.onRequestTo(ExecInterfacer.SIMPLEDEPLOY_PATH)
//						.withMethod(Method.POST), RestClientDriver
//						.giveResponse(res, "text/plain").withStatus(201));
		ReasonerInterfacer currentReasonerInterfacer = new ReasonerInterfacer(
				TEST_SCALARMiNSTANCE);
		
		//ReasonerInterfacer currentReasonerInterfacer = new ReasonerInterfacer(TEST_INPUTFILE);
		
		DeploymentModel current = currentReasonerInterfacer.getDeploymentModel(false);
		
		ReasonerInterfacer targetReasonerInterfacer = new ReasonerInterfacer(
				TEST_INPUTFILE3);
		
		ExecInterfacer exec = new ExecInterfacer(driver.getBaseUrl());
		IValidator validator = new ValidatorImpl();
		PlanGenerator planGenerator = new PlanGenerator();
		Coordinator c = new Coordinator(targetReasonerInterfacer, exec, validator,
				planGenerator);
		c.setCurrentModel(current);
		
		c.startThreaded();
		System.out.println("End of method verifyParallelDeployment()");
	}*/
	
/*	
	@Test
	public void verifyExecWareLoginLogout() {
//		driver.addExpectation(
//				RestClientDriver.onRequestTo(ExecInterfacer.LOGIN_PATH)
//						.withMethod(Method.POST), RestClientDriver
//						.giveResponse(res, "text/plain").withStatus(200));
		ExecInterfacer exec = new ExecInterfacer("http://131.254.13.194:9000");
		try {
			exec.login("john.doe@example.com", "admin");
			exec.logout("john.doe@example.com");
		} catch (ExecutionwareError e) {
			e.printStackTrace();
		}

	}
*/
/*
 *** COMMENTED THIS BLOCK FOR PARALLEL DEPLOYMENT code test
 ***

	@Test
	public void verifyReasonerInterfacer() {
		ReasonerInterfacer r = new ReasonerInterfacer(TEST_INPUTFILE);
		DeploymentModel dm = (DeploymentModel) r.getDeploymentModel(false);
		EList<InternalComponent> comps = dm.getInternalComponents();
		boolean found = false;
		for (InternalComponent c : comps) {
			String name = c.getName();
			if ("InformationService".equals(name)) {
				found = true;
				break;
			}
		}
		assertTrue(
				"Deployment model does not contain required internal component",
				found);
	}
	
//	@Test
//	public void verifyReasonerInterfacerCDO() {
//		MyCDOClient cl;
//		// if there is no CDO server, the test passes
//		try {
//			cl = new MyCDOClient(TEST_HOST, TEST_PORT, TEST_REPOSITORYNAME);
//		}
//		catch (Exception e) {
//			return;
//		}
//		// Load TEST_INPUTFILE 
//		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*",
//				new XMIResourceFactoryImpl() {
//					public Resource createResource(URI uri) {
//						XMIResource xmiResource = new XMIResourceImpl(uri);
//						return xmiResource;
//					}
//				});
//
//		final ResourceSet rs = new ResourceSetImpl();
//		rs.getPackageRegistry().put(CamelPackage.eNS_URI,
//				CamelPackage.eINSTANCE);
//		Resource res = rs.getResource(URI.createFileURI(TEST_INPUTFILE), true);
//		EList<EObject> contents = res.getContents();
//		CamelModel cm = (CamelModel) contents.get(0);
//		//Store the model to CDO with the name TEST_RESOURCENAME
//		cl.storeModel(cm, TEST_RESOURCENAME);
//		// Load from CDO with ReasonerInterfacer
//		ReasonerInterfacer r = new ReasonerInterfacer(TEST_HOST, TEST_PORT,TEST_REPOSITORYNAME, TEST_RESOURCENAME);
//		DeploymentModel dm = (DeploymentModel) r.getDeploymentModel();
//		EList<InternalComponent> comps = dm.getInternalComponents();
//		boolean found = false;
//		for (InternalComponent c : comps) {
//			String name = c.getName();
//			if ("InformationService".equals(name)) {
//				found = true;
//				break;
//			}
//		}
//		assertTrue(
//				"Deployment model does not contain required internal component",
//				found);
//	}


	@Test
	public void verifySimpleDeployment() {
		driver.addExpectation(
				RestClientDriver.onRequestTo(ExecInterfacer.SIMPLEDEPLOY_PATH)
						.withMethod(Method.POST), RestClientDriver
						.giveResponse(res, "text/plain").withStatus(201));
		ReasonerInterfacer reasonerInterfacer = new ReasonerInterfacer(
				TEST_INPUTFILE);
		ExecInterfacer exec = new ExecInterfacer(driver.getBaseUrl());
		IValidator validator = new ValidatorImpl();
		PlanGenerator planGenerator = new PlanGenerator();
		Coordinator c = new Coordinator(reasonerInterfacer, exec, validator,
				planGenerator);
		try {
			c.runStep();
		} finally {
			c.terminate();
		}
		//c.startThreaded();
		JsonObject jsonObject = exec.getDeployed();
		assertTrue("JSON file does not contain all components",
				jsonObject.get("components").asArray().size() == 4);
		assertTrue("JSON file does not contain all communications", jsonObject
				.get("communications").asArray().size() == 6);
		boolean found = false;
		for (JsonValue value : jsonObject.get("components").asArray()) {
			if (value.asObject().get("name").asString()
					.equals("informationServiceInstance")) {
				found = true;
				break;
			}
		}
		assertTrue("JSON file does not contain required component", found);
	}
	

	// @Test
	// public void verifyDeployment() {
	// ReasonerInterfacer reasonerInterfacer = new ReasonerInterfacer(
	// TEST_INPUTFILE);
	// ExecInterfacer exec = new ExecInterfacer();
	// IValidator validator = new ValidatorImpl();
	// PlanGenerator planGenerator = new PlanGenerator(false);
	// Coordinator c = new Coordinator(reasonerInterfacer, exec, validator,
	// planGenerator);
	// c.runStep();
	// c.terminate();
	// Map<String, Object> coordState = c.getState();
	// boolean condition = (Boolean) coordState.get("installed");
	// assertTrue("Install action was not invoked", condition);
	// }

	@Test
	public void verifyLogin() {
		driver.addExpectation(
				RestClientDriver.onRequestTo(ExecInterfacer.LOGIN_PATH)
						.withMethod(Method.POST), RestClientDriver
						.giveResponse(res, "text/plain").withStatus(200));
		ExecInterfacer exec = new ExecInterfacer(driver.getBaseUrl());
		try {
			exec.login();
		} catch (ExecutionwareError e) {
			e.printStackTrace();
		}

	}

	// @Test
	// public void verifyComparator() {
	//	ReasonerInterfacer r = new ReasonerInterfacer(TEST_INPUTFILE);
	//	DeploymentModel dm1 = (DeploymentModel) r.getDeploymentModel();
	//	r = new ReasonerInterfacer(TEST_INPUTFILE2);
	//	DeploymentModel dm2 = (DeploymentModel) r.getDeploymentModel();
	//	ModelComparator mc = new ModelComparator(dm1, dm2);
	//	mc.compareModels();
	//	assertTrue(mc.getAddedVMs().size() > 0);
	//  }

	// @Test
	// public void verifyComparator() {
	// ReasonerInterfacer r = new ReasonerInterfacer(TEST_INPUTFILE);
	// DeploymentModel dm1 = (DeploymentModel) r.getDeploymentModel();
	// r = new ReasonerInterfacer(TEST_INPUTFILE2);
	// DeploymentModel dm2 = (DeploymentModel) r.getDeploymentModel();
	// ModelComparator mc = new ModelComparator(dm1, dm2);
	// mc.compareModels();
	//
	// assertTrue(mc.getAddedVMs().size() > 0);
	// }

	@Test
	public void verifyApplicationCreation() {

		JsonObject requestObj = new JsonObject().add("cloudifyName",
				TEST_APPLICATIONNAME).add("displayName", TEST_APPLICATIONNAME);
		JsonObject links = new JsonObject().add("self",
				new JsonObject().add("href", "/api/application/1"));
		JsonObject responseObj = new JsonObject().add("_links", links)
				.add("cloudifyName", TEST_APPLICATIONNAME)
				.add("displayName", TEST_APPLICATIONNAME);
		BodyCapture<?> bodyCapture = null;
		driver.addExpectation(
				RestClientDriver
						.onRequestTo(ExecInterfacer.APPLICATIONCREATION_PATH)
						.withMethod(Method.POST)
						.withBody(requestObj.toString(), "application/json"),
				RestClientDriver.giveResponse(responseObj.toString(),
						"application/hal+json").withStatus(201));
		ExecInterfacer exec = new ExecInterfacer(driver.getBaseUrl());
		String r = null;
		try {
			r = exec.createApplication(TEST_APPLICATIONNAME);
		} catch (ExecutionwareError e) {
			e.printStackTrace();
		}
		assertTrue("Problem in application creation",
				("/api/application/1").equals(r));
	}

	@Test
	public void verifyApplicationInstallation() {

		JsonObject links = new JsonObject().add("application",
				new JsonObject().add("href", "/api/application/1"));
		JsonObject requestObj = new JsonObject().add("_links", links);
		driver.addExpectation(
				RestClientDriver
						.onRequestTo(
								ExecInterfacer.APPLICATIONINSTALLATION_PATH)
						.withMethod(Method.POST)
						.withBody(requestObj.toString(), "application/hal+json"),
				RestClientDriver.giveResponse(res, "application/hal+json")
						.withStatus(200));
		ExecInterfacer exec = new ExecInterfacer(driver.getBaseUrl());
		try {
			exec.installApplication("/api/application/1");
		} catch (ExecutionwareError e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void verifyLogout() {		
		driver.addExpectation(
				RestClientDriver.onRequestTo(ExecInterfacer.LOGOUT_PATH)
						.withMethod(Method.POST), RestClientDriver
						.giveResponse(res, "text/plain").withStatus(200));
		ExecInterfacer exec = new ExecInterfacer(driver.getBaseUrl());
		try {
			exec.logout();
		} catch (ExecutionwareError e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void verifyApplicationRemoval() {

		JsonObject requestObj = new JsonObject().add("cloudifyName", "sensapp")
				.add("displayName", "sensapp");
		JsonObject links = new JsonObject().add("self",
				new JsonObject().add("href", "/api/application/1"));
		JsonObject responseObj = new JsonObject().add("_links", links)
				.add("cloudifyName", "sensapp").add("displayName", "sensapp");
		BodyCapture<?> bodyCapture = null;
		driver.addExpectation(
				RestClientDriver
						.onRequestTo(ExecInterfacer.APPLICATIONCREATION_PATH)
						.withMethod(Method.POST)
						.withBody(requestObj.toString(), "application/json"),
				RestClientDriver.giveResponse(responseObj.toString(),
						"application/hal+json").withStatus(201));
		ExecInterfacer exec = new ExecInterfacer(driver.getBaseUrl());
		String r = null;
		try {
			r = exec.createApplication("sensapp");
		} catch (ExecutionwareError e) {
			e.printStackTrace();
		}
		assertTrue("Problem in application removal",
				("/api/application/1").equals(r));
	}
	
	@Test
	public void verifyApplicationUninstallation() {

		JsonObject links = new JsonObject().add("application",
				new JsonObject().add("href", "/api/application/1"));
		JsonObject requestObj = new JsonObject().add("_links", links);
		driver.addExpectation(
				RestClientDriver
						.onRequestTo(
								ExecInterfacer.APPLICATIONUNINSTALLATION_PATH)
						.withMethod(Method.POST)
						.withBody(requestObj.toString(), "application/hal+json"),
				RestClientDriver.giveResponse(res, "application/hal+json")
						.withStatus(200));
		ExecInterfacer exec = new ExecInterfacer(driver.getBaseUrl());
		try {
			exec.uninstallApplication("/api/application/1");
		} catch (ExecutionwareError e) {
			e.printStackTrace();
		}
	}

	@Test
	public void verifyComponentAddition() {

		JsonObject links = new JsonObject().add("self",
				new JsonObject().add("href", "/api/application/1"));
		JsonObject requestObj = new JsonObject().add("_links", links);
		JsonObject responseObj = new JsonObject().add("_links", links)
				.add("cloudifyName", "sensapp").add("displayName", "sensapp");
		
		driver.addExpectation(
				RestClientDriver
						.onRequestTo(
								ExecInterfacer.COMPONENTADDITION_PATH)
						.withMethod(Method.POST)
						.withBody(requestObj.toString(), "application/hal+json"),
				RestClientDriver.giveResponse(responseObj.toString(),
						"application/hal+json").withStatus(201));
		ExecInterfacer exec = new ExecInterfacer(driver.getBaseUrl());
		try {
			exec.addComponent("/api/application/1");
		} catch (ExecutionwareError e) {
			e.printStackTrace();
		}
	}

	@Test
	public void verifyCommunicationAddition() {

		JsonObject requestObj = new JsonObject().add("Name", "comm1")
						.add("displayName", "comm1");
		JsonObject links = new JsonObject().add("self",
				new JsonObject().add("href", "/api/addcommunication/1"));
		JsonObject responseObj = new JsonObject().add("_links", links);

		BodyCapture<?> bodyCapture = null;
						
		driver.addExpectation(
				RestClientDriver
						.onRequestTo(ExecInterfacer.COMMUNICATIONADDITION_PATH)
						.withMethod(Method.POST)
						.withBody(requestObj.toString(), "application/hal+json"),
				RestClientDriver.giveResponse(responseObj.toString(),
						"application/hal+json").withStatus(201));
		ExecInterfacer exec = new ExecInterfacer(driver.getBaseUrl());
		String r = null;
		try {
			r = exec.addCommunication("comm1");
		} catch (ExecutionwareError e) {
			e.printStackTrace();
		}
		assertTrue("Problem in Communication addition",
						("/api/addcommunication/1").equals(r));
	}
*/
	
	
/*	@Test
	public void verifyComparator() {//BLOCK COMMENTED THIS TEST
		System.out.println("First Comparison:");
		System.out.println("Two similar plans are loading ...");
		ReasonerInterfacer r = new ReasonerInterfacer(TEST_INPUTFILE);
		DeploymentModel dm1 = (DeploymentModel) r.getDeploymentModel(false);
		r = new ReasonerInterfacer(TEST_INPUTFILE2);
		DeploymentModel dm2 = (DeploymentModel) r.getDeploymentModel(false);
		ModelComparator mc = new ModelComparator(dm1, dm2);
		mc.compareModels();
		boolean isDifferent;
		if (mc.getRemovedCommmunications().isEmpty() && mc.getAddedCommunications().isEmpty() && mc.getRemovedComponents().isEmpty() && mc.getAddedComponents().isEmpty() && mc.getRemovedHostings().isEmpty() && mc.getAddedHostings().isEmpty()){
			System.out.println("Current and Target plans are the same as each other!");
			isDifferent = false;
		}
		else{
			isDifferent = true;
			System.out.println("Current and Target Plans are different!");
			System.out.println("            Number of Removed Components     " + mc.getRemovedComponents().size());
			System.out.println("            Number of Added Components     " + mc.getAddedComponents().size());
			System.out.println("            Number of Removed Communications     " + mc.getRemovedCommmunications().size());
			System.out.println("            Number of Added Communications     " + mc.getAddedCommunications().size());
			System.out.println("            Number of Removed Hostings     " + mc.getRemovedHostings().size());
			System.out.println("            Number of Added Hostings     " + mc.getAddedHostings().size());
		}
		assertTrue("Comparator has a problem", isDifferent == false);
		System.out.println("Second Comparison:");
		System.out.println("a new target plan is loaded: Scalaram_full3");
		System.out.println("3 Hostings and 2 Communications are added, 1 Hosting and Communication are removed");

		r = new ReasonerInterfacer(TEST_INPUTFILE);
		dm1 = (DeploymentModel) r.getDeploymentModel(false);
		r = new ReasonerInterfacer(TEST_INPUTFILE3);
		dm2 = (DeploymentModel) r.getDeploymentModel(false);
		mc = new ModelComparator(dm1, dm2);
		mc.compareModels();
		//boolean compare;
		if (mc.getRemovedCommmunications().isEmpty() && mc.getAddedCommunications().isEmpty() && mc.getRemovedComponents().isEmpty() && mc.getAddedComponents().isEmpty() && mc.getRemovedHostings().isEmpty() && mc.getAddedHostings().isEmpty()){
			System.out.println("Current and Target plans are the same as each other!");
		}
		else{
			System.out.println("Current and Target Plans are different!");
			System.out.println("            Number of Removed Components     " + mc.getRemovedComponents().size());
			System.out.println("            Number of Added Components     " + mc.getAddedComponents().size());
			System.out.println("            Number of Removed Communications     " + mc.getRemovedCommmunications().size());
			System.out.println("            Number of Added Communications     " + mc.getAddedCommunications().size());
			System.out.println("            Number of Removed Hostings     " + mc.getRemovedHostings().size());
			System.out.println("            Number of Added Hostings     " + mc.getAddedHostings().size());
		}

//		System.out.println("****************************************************");
//		
//		MyCDOClient mycl = new MyCDOClient("localhost","2036", "repo1");
//		EObject model1, model2;
//		
//		model1 = mycl.loadModel("src/test/resources/Scalarm_full.xmi");
//		mycl.MYstoreModel(model1, "MyTest1");
//		
//
//		
//		model2 = mycl.loadModel("src/test/resources/Scalarm_full3.xmi");
//		mycl.MYstoreModel(model2, "MyTest1");
//		
//		ReasonerInterfacer MyreasonerInterfacer = new ReasonerInterfacer("localhost", "2036" , "repo1", "MyTest1");
//		DeploymentModel Dmodel1 = MyreasonerInterfacer.getCurrentFromCDO();// DeploymentModel();
//				
//		MyreasonerInterfacer = new ReasonerInterfacer("localhost", "2036" , "repo1", "MyTest1");
//		DeploymentModel Dmodel2 = MyreasonerInterfacer.getDeploymentModel();
//		
//		mc = new ModelComparator(Dmodel1, Dmodel2);
//		mc.compareModels();
//		//boolean compare;
//		if (mc.getRemovedCommmunications().isEmpty() && mc.getAddedCommunications().isEmpty() && mc.getRemovedComponents().isEmpty() && mc.getAddedComponents().isEmpty() && mc.getRemovedHostings().isEmpty() && mc.getAddedHostings().isEmpty()){
//			System.out.println("Current and Target plans are the same as each other!");
//		}
//		else{
//			System.out.println("Current and Target Plans are different!");
//			System.out.println("            Number of Removed Components     " + mc.getRemovedComponents().size());
//			System.out.println("            Number of Added Components     " + mc.getAddedComponents().size());
//			System.out.println("            Number of Removed Communications     " + mc.getRemovedCommmunications().size());
//			System.out.println("            Number of Added Communications     " + mc.getAddedCommunications().size());
//			System.out.println("            Number of Removed Hostings     " + mc.getRemovedHostings().size());
//			System.out.println("            Number of Added Hostings     " + mc.getAddedHostings().size());
//		}
		
			
		assertTrue("Comparator has a problem", mc.getRemovedCommmunications().size() == 1 && mc.getRemovedHostings().size() == 1 && mc.getAddedCommunications().size() == 2 && mc.getAddedHostings().size() == 3 );
	}*/
	
	
/*
	@Test
	public void verifyListener() {
		System.out.println("Verify Listener Test is starting ...");
		
		AdaptationManager.runListener();
		
		MyCDOClient mycl = new MyCDOClient("localhost","2036", "repo1");
		EObject model;
		model = mycl.loadModel("src/test/resources/Scalarm_full.xmi");
		mycl.storeModel(model, "am");

		assertTrue("Listener has a problem", AdaptationManager.flag == true);
	}

	@Test
	public void verifyVMAddition() {

		JsonObject requestObj = new JsonObject().add("Name", "vm1")
				.add("displayName", "vm1");
		JsonObject links = new JsonObject().add("self",
				new JsonObject().add("href", "/api/addvm/1"));
		JsonObject responseObj = new JsonObject().add("_links", links)
				.add("Name", "vm1").add("displayName", "VM1");
		BodyCapture<?> bodyCapture = null;
		driver.addExpectation(
				RestClientDriver
						.onRequestTo(ExecInterfacer.VMADDITION_PATH)
						.withMethod(Method.POST)
						.withBody(requestObj.toString(), "application/hal+json"),
				RestClientDriver.giveResponse(responseObj.toString(),
						"application/hal+json").withStatus(201));
		ExecInterfacer exec = new ExecInterfacer(driver.getBaseUrl());
		String r = null;
		try {
			r = exec.addVM("vm1");
		} catch (ExecutionwareError e) {
			e.printStackTrace();
		}
		assertTrue("Problem in vm addition",
				("/api/addvm/1").equals(r));
	}
	
	@Test
	public void verifyVMRemoval() {

		JsonObject links = new JsonObject().add("vm1",
				new JsonObject().add("href", "/api/removevm/vm1"));
		JsonObject requestObj = new JsonObject().add("_links", links);
		driver.addExpectation(
				RestClientDriver
						.onRequestTo(
								ExecInterfacer.VMREMOVAL_PATH)
						.withMethod(Method.POST)
						.withBody(requestObj.toString(), "application/hal+json"),
				RestClientDriver.giveResponse(res, "application/hal+json")
						.withStatus(200));
		ExecInterfacer exec = new ExecInterfacer(driver.getBaseUrl());
		try {
			exec.removeVM("/api/removevm/vm1");
		} catch (ExecutionwareError e) {
			e.printStackTrace();
		}
	}
*/
/*	@Test
	public void verifyDaemon() {
		int noOfActions = 0;
		driver.addExpectation(
				RestClientDriver.onRequestTo(ExecInterfacer.SIMPLEDEPLOY_PATH)
						.withMethod(Method.POST), RestClientDriver
						.giveResponse(res, "text/plain").withStatus(201));
		
		ReasonerInterfacer reasonerInterfacer = new ReasonerInterfacer(
				TEST_INPUTFILE);
				
		ExecInterfacer exec = new ExecInterfacer(driver.getBaseUrl());
		IValidator validator = new ValidatorImpl();
		PlanGenerator planGenerator = new PlanGenerator();
		Coordinator c = new Coordinator(reasonerInterfacer, exec, validator,
				planGenerator);
		try {
			c.runStep();
			c.terminate();		
							
			MyCDOClient mycl = new MyCDOClient("localhost","2036", "repo1");
			EObject model;
			model = mycl.loadModel("src/test/resources/Scalarm_full3.xmi");
			mycl.storeModel(model, "NewScalarm");
			
			if (c.flag){
				System.out.println("New Event is detected!");
				ReasonerInterfacer MyreasonerInterfacer = new ReasonerInterfacer("localhost", "2036" , "repo1", "NewScalarm");
				Map<String, Object> outputMap = new HashMap<String, Object>();
				DeploymentModel targetModel = MyreasonerInterfacer.getDeploymentModel();
				Plan plan = planGenerator.generate(c.getCurrentModel(), targetModel);
				noOfActions = plan.getActions().size();
			}
			else{
				System.out.println("Unable to detect the new event");
			}
			
		} finally {
			c.terminate();
		}
		
		assertTrue("Adaptation Manager has a problem", noOfActions == 7);
	}
	*/
}
