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
	//driver.addExpectation(onRequestTo("/blah"), giveEmptyResponse().withStatus(404));
	
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
	public void verifyDeployment(){
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_LOGIN)
				.withHeader("Content-Type", "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse("{\"createdOn\":1450816014555,\"expiresAt\":1450816314555,\"token\":\""+ token + "\",\"userId\":1}", "application/json")
				.withStatus(200)).anyTimes();
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_APPLICATION)
				.withHeader("Content-Type", "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(ClientDriverStrings.RESP_POST_APP, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_APPLICATIONINSTANCE)
				.withHeader("Content-Type", "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(ClientDriverStrings.RESP_POST_APPInst, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_CLOUD)
				.withMethod(Method.GET),
				RestClientDriver
				.giveResponse(ClientDriverStrings.RESP_GET_CLOUD, "application/json")
				.withStatus(200)).anyTimes();
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_LOCATION)
				.withMethod(Method.GET),
				RestClientDriver
				.giveResponse(ClientDriverStrings.RESP_GET_LOCATION, "application/json")
				.withStatus(200)).anyTimes();
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_HARDWARE)
				.withMethod(Method.GET),
				RestClientDriver
				.giveResponse(ClientDriverStrings.RESP_GET_HARDWARE, "application/json")
				.withStatus(200)).anyTimes();
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_IMAGE)
				.withMethod(Method.GET),
				RestClientDriver
				.giveResponse(ClientDriverStrings.RESP_GET_IMAGE, "application/json")
				.withStatus(200)).anyTimes();
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_IMAGE+"/158")
				.withMethod(Method.GET),
				RestClientDriver
				.giveResponse(ClientDriverStrings.RESP_GET_IMAGE_158, "application/json")
				.withStatus(200)).anyTimes();
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_VIRTUALMACHINETEMPLATE)
				.withHeader("Content-Type", "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(ClientDriverStrings.RESP_POST_VMT1, "application/json")
				.withStatus(200)).times(1);

		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_VIRTUALMACHINETEMPLATE)
				.withHeader("Content-Type", "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(ClientDriverStrings.RESP_POST_VMT2, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_LIFECYCLECOMPONENT)
				.withHeader("Content-Type", "application/json")
				.withBody(ClientDriverStrings.PATTERN_LCCOMP1, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(ClientDriverStrings.RESP_POST_LCCOMP1, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_LIFECYCLECOMPONENT)
				.withHeader("Content-Type", "application/json")
				.withBody(ClientDriverStrings.PATTERN_LCCOMP2, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(ClientDriverStrings.RESP_POST_LCCOMP2, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_LIFECYCLECOMPONENT)
				.withHeader("Content-Type", "application/json")
				.withBody(ClientDriverStrings.PATTERN_LCCOMP3, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(ClientDriverStrings.RESP_POST_LCCOMP3, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_APPLICATIONCOMPONENT)
				.withHeader("Content-Type", "application/json")
				.withBody(ClientDriverStrings.PATTERN_AC1, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(ClientDriverStrings.RESP_POST_AC1, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_APPLICATIONCOMPONENT)
				.withHeader("Content-Type", "application/json")
				.withBody(ClientDriverStrings.PATTERN_AC2, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(ClientDriverStrings.RESP_POST_AC2, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_APPLICATIONCOMPONENT)
				.withHeader("Content-Type", "application/json")
				.withBody(ClientDriverStrings.PATTERN_AC3, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(ClientDriverStrings.RESP_POST_AC3, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_PORTREQ)
				.withHeader("Content-Type", "application/json")
				.withBody(ClientDriverStrings.PATTERN_PORTREQ1, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(ClientDriverStrings.RESP_POST_PORTREQ1, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_PORTREQ)
				.withHeader("Content-Type", "application/json")
				.withBody(ClientDriverStrings.PATTERN_PORTREQ4, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(ClientDriverStrings.RESP_POST_PORTREQ4, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_PORTPROV)
				.withHeader("Content-Type", "application/json")
				.withBody(ClientDriverStrings.PATTERN_PORTPROV2, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(ClientDriverStrings.RESP_POST_PORTPROV2, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_PORTPROV)
				.withHeader("Content-Type", "application/json")
				.withBody(ClientDriverStrings.PATTERN_PORTPROV3, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(ClientDriverStrings.RESP_POST_PORTPROV3, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_PORTPROV)
				.withHeader("Content-Type", "application/json")
				.withBody(ClientDriverStrings.PATTERN_PORTPROV5, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(ClientDriverStrings.RESP_POST_PORTPROV5, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_COMMUNICATION)
				.withHeader("Content-Type", "application/json")
				.withBody(ClientDriverStrings.PATTERN_COMM1, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(ClientDriverStrings.RESP_POST_COMM1, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_COMMUNICATION)
				.withHeader("Content-Type", "application/json")
				.withBody(ClientDriverStrings.PATTERN_COMM2, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(ClientDriverStrings.RESP_POST_COMM2, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_VIRTUALMACHINE)
				.withHeader("Content-Type", "application/json")
				.withBody(ClientDriverStrings.PATTERN_VIRTUALMACHINE65536, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(ClientDriverStrings.RESP_POST_VIRTUALMACHINE65536, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_VIRTUALMACHINE)
				.withHeader("Content-Type", "application/json")
				.withBody(ClientDriverStrings.PATTERN_VIRTUALMACHINE65537, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(ClientDriverStrings.RESP_POST_VIRTUALMACHINE65537, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_VIRTUALMACHINE)
				.withHeader("Content-Type", "application/json")
				.withBody(ClientDriverStrings.PATTERN_VIRTUALMACHINE65538, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(ClientDriverStrings.RESP_POST_VIRTUALMACHINE65538, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_VIRTUALMACHINE+"/65536")
				.withMethod(Method.GET),
				RestClientDriver
				.giveResponse(ClientDriverStrings.RESP_POST_VIRTUALMACHINE65536, "application/json")
				.withStatus(200)).anyTimes();
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_VIRTUALMACHINE+"/65537")
				.withMethod(Method.GET),
				RestClientDriver
				.giveResponse(ClientDriverStrings.RESP_POST_VIRTUALMACHINE65537, "application/json")
				.withStatus(200)).anyTimes();
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_VIRTUALMACHINE+"/65538")
				.withMethod(Method.GET),
				RestClientDriver
				.giveResponse(ClientDriverStrings.RESP_POST_VIRTUALMACHINE65538, "application/json")
				.withStatus(200)).anyTimes();
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_INSTANCE)
				.withHeader("Content-Type", "application/json")
				.withBody(ClientDriverStrings.PATTERN_INSTANCE65539, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(ClientDriverStrings.RESP_POST_INSTANCE65539, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_INSTANCE)
				.withHeader("Content-Type", "application/json")
				.withBody(ClientDriverStrings.PATTERN_INSTANCE65540, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(ClientDriverStrings.RESP_POST_INSTANCE65540, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_INSTANCE)
				.withHeader("Content-Type", "application/json")
				.withBody(ClientDriverStrings.PATTERN_INSTANCE65541, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(ClientDriverStrings.RESP_POST_INSTANCE65541, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_INSTANCE+"/65539")
				.withMethod(Method.GET),
				RestClientDriver
				.giveResponse(ClientDriverStrings.RESP_POST_INSTANCE65539, "application/json")
				.withStatus(200)).anyTimes();
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_INSTANCE+"/65540")
				.withMethod(Method.GET),
				RestClientDriver
				.giveResponse(ClientDriverStrings.RESP_POST_INSTANCE65540, "application/json")
				.withStatus(200)).anyTimes();
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_INSTANCE+"/65541")
				.withMethod(Method.GET),
				RestClientDriver
				.giveResponse(ClientDriverStrings.RESP_POST_INSTANCE65541, "application/json")
				.withStatus(200)).anyTimes();
		
		
		
		ReasonerInterfacer currentReasonerInterfacer = null;
		currentReasonerInterfacer = new ReasonerInterfacer("/home/asinha/PaaSage/christmasCDOBackup/CAMEL_676790ec61a8ae90a627eec0323eee12.xmi", true);
		
		ExecInterfacer exec = new ExecInterfacer(driver.getBaseUrl());
		IValidator validator = new ValidatorImpl();
		Coordinator c = new Coordinator(currentReasonerInterfacer, exec, validator);
		
		c.startThreaded();
		System.out.println("End of method verifyParallelDeployment()");
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
		currentReasonerInterfacer = new ReasonerInterfacer("/home/asinha/PaaSage/christmasCDOBackup/CAMEL_676790ec61a8ae90a627eec0323eee12.xmi", true);
		
		ExecInterfacer exec = new ExecInterfacer();
		IValidator validator = new ValidatorImpl();
		Coordinator c = new Coordinator(currentReasonerInterfacer, exec, validator);
		
		c.startThreaded();
		System.out.println("End of method verifyParallelDeployment()");
	}

}
