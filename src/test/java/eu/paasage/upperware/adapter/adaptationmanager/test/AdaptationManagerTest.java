/*
 * Copyright (c) 2014 INRIA, INSA Rennes
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.adaptationmanager.test;

import java.util.regex.Pattern;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.github.restdriver.clientdriver.ClientDriverRequest.Method;
import com.github.restdriver.clientdriver.ClientDriverRule;
import com.github.restdriver.clientdriver.RestClientDriver;

import eu.paasage.upperware.adapter.adaptationmanager.REST.ExecInterfacer;
import eu.paasage.upperware.adapter.adaptationmanager.input.ReasonerInterfacer;
import eu.paasage.upperware.adapter.adaptationmanager.validation.IValidator;
import eu.paasage.upperware.adapter.adaptationmanager.validation.ValidatorImpl;
import eu.paasage.upperware.adapter.adaptationmanager.core.Coordinator;

/**
 * Working version - tested 17th Dec with BeWan on Flexiant
 * @author ArnabSinha
 *
 */

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
	
	private final String TEST_INPUTFILE_SimpleDeployment = System
			.getProperty("user.dir") + "/src/test/resources/CAMEL_test_SimpleDeployment.xmi";
	
	/**
	 * Response Strings for Client Driver
	 */
	private static final int port = 9000;
	private static final String res = "Created";
	private static final String token = "bo6t3h9fl1i6ie7fu2l1k1bafnsr0jv1qo5ampb2qlt1u1q2o12674qoad8qa1sh306d1riffu471nr055tv3ftqm0u07camoo9j2luut1pqm3oosb62sqgheh0aak0bm2266n730sa62eifj78qipj3elipdnt74mc1tas8h1qv60cld5nrn2f8gttfqf5r35ol4b0obcdos";
	
	/**
	 * Params for CDO server
	 */
	private static final String TEST_APPLICATIONNAME = "Scalarm";
	private static final String TEST_HOST = "54.246.101.26";
	private static final String TEST_PORT = "2036";
	private static final String TEST_REPOSITORYNAME = "repo1";
	private static final String TEST_RESOURCENAME= "Scalarm";
	
	@Rule
	public ClientDriverRule driver = new ClientDriverRule(port);
	
	@Test
//	@Ignore
	public void cloudCredentialsLoad(){
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_LOGIN)
				.withHeader("Content-Type", "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse("{\"createdOn\":1450816014555,\"expiresAt\":1450816314555,\"token\":\""+ token + "\",\"userId\":1}", "application/json")
				.withContentType("application/json")
				.withStatus(200)).anyTimes();
		
		ExecInterfacer exec = new ExecInterfacer(driver.getBaseUrl());
		exec.getCloudUname("flexiant");
		exec.getCloudPass("flexianT");
		exec.getCloudEndpoint("flexIant");
		exec.getCloudUname("OmiStack");
		exec.getCloudPass("Omistack");
		exec.getCloudEndpoint("OmistAk");
	}
	
	@Test
//	@Ignore
	public void verifySimpleDeployment(){
		
		SimpleDeploymentScenario example = new SimpleDeploymentScenario(driver);
		
		ReasonerInterfacer currentReasonerInterfacer = null;
		currentReasonerInterfacer = new ReasonerInterfacer(example.getDeploymentXMI(), true);
		
		ExecInterfacer exec = new ExecInterfacer(driver.getBaseUrl());
		IValidator validator = new ValidatorImpl();
		Coordinator c = new Coordinator(currentReasonerInterfacer, exec, validator);
		
		c.startThreaded();
		System.out.println("End of method verifySimpleDeployment()");
	}
	
	@Test
	@Ignore
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
		
//		currentReasonerInterfacer = new ReasonerInterfacer("CAMEL_676790ec61a8ae90a627eec0323eee12", false);
		//currentReasonerInterfacer = new ReasonerInterfacer("/home/asinha/git/paasadapterOW2OS/adapter/src/test/resources/FullDeploymentBewan-Y3Rev-Christian.xmi", true);
//		currentReasonerInterfacer = new ReasonerInterfacer("/home/asinha/git/paasadapterOW2OS/adapter/src/test/resources/BeWan_Deployment034553.xmi", true);
		currentReasonerInterfacer = new ReasonerInterfacer(TEST_INPUTFILE_SimpleDeployment, true);
		
		ExecInterfacer exec = new ExecInterfacer();
		IValidator validator = new ValidatorImpl();
		Coordinator c = new Coordinator(currentReasonerInterfacer, exec, validator);
		
		c.startThreaded();
		System.out.println("End of method verifyParallelDeployment()");
	}

}
