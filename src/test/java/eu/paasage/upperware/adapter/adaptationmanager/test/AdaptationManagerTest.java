/*
 * Copyright (c) 2014 INRIA, INSA Rennes
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.adaptationmanager.test;

import java.util.List;
import java.util.logging.Level;
import java.util.regex.Pattern;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.github.restdriver.clientdriver.ClientDriverRequest.Method;
import com.github.restdriver.clientdriver.ClientDriverRule;
import com.github.restdriver.clientdriver.RestClientDriver;

import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.upperware.adapter.adaptationmanager.REST.ExecInterfacer;
import eu.paasage.upperware.adapter.adaptationmanager.input.ReasonerInterfacer;
import eu.paasage.upperware.adapter.adaptationmanager.mapping.GraphUtilities;
import eu.paasage.upperware.adapter.adaptationmanager.validation.IValidator;
import eu.paasage.upperware.adapter.adaptationmanager.validation.ValidatorImpl;
import eu.paasage.upperware.adapter.adaptationmanager.actions.Action;
import eu.paasage.upperware.adapter.adaptationmanager.core.Coordinator;
import eu.paasage.upperware.adapter.adaptationmanager.core.ZeroMQPublisher;
import eu.paasage.upperware.adapter.adaptationmanager.core.ZeroMQSubscriber;
import eu.paasage.upperware.plangenerator.PlanGenerator;
import eu.paasage.upperware.plangenerator.exception.ModelComparatorException;
import eu.paasage.upperware.plangenerator.exception.PlanGenerationException;
import eu.paasage.upperware.plangenerator.model.Plan;
import eu.paasage.upperware.plangenerator.model.task.ConfigurationTask;

/**
 * Working version - tested 17th Dec with BeWan on Flexiant
 * @author arnab [dot] sinha [at] inria [dot] fr
 *
 */

@RunWith(JUnit4.class)
public class AdaptationManagerTest

{
	private final String TEST_INPUTFILE_SimpleDeployment = System
			.getProperty("user.dir") + "/src/test/resources/CAMEL_test_SimpleDeployment.xmi";
	
	private final String TEST_INPUTFILE_ReconfigDeployment1 = System
			.getProperty("user.dir") + "/src/test/resources/CAMEL_test_Redeployment1.xmi";
	
	private final String TEST_INPUTFILE_ReconfigDeployment3 = System
			.getProperty("user.dir") + "/src/test/resources/CAMEL_test_Redeployment3.xmi";

	/**
	 * Response Strings for Client Driver
	 */
	private static final int port = 9000;
	private static final String token = "bo6t3h9fl1i6ie7fu2l1k1bafnsr0jv1qo5ampb2qlt1u1q2o12674qoad8qa1sh306d1riffu471nr055tv3ftqm0u07camoo9j2luut1pqm3oosb62sqgheh0aak0bm2266n730sa62eifj78qipj3elipdnt74mc1tas8h1qv60cld5nrn2f8gttfqf5r35ol4b0obcdos";
	
	@Rule
	public ClientDriverRule driver = new ClientDriverRule(port);
	
	@Test
	@Ignore
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
	@Ignore
	public void verifySimpleDeployment(){

		SimpleDeploymentScenario example = new SimpleDeploymentScenario(driver, false);
		
		ReasonerInterfacer currentReasonerInterfacer = null;
		currentReasonerInterfacer = new ReasonerInterfacer(example.getDeploymentXMI(), true);
		
		ExecInterfacer exec = new ExecInterfacer(driver.getBaseUrl());
		IValidator validator = new ValidatorImpl();
		Coordinator c = new Coordinator(currentReasonerInterfacer, exec, validator);
		
		c.deployModelIDThreaded(1);
		System.out.println("End of method verifySimpleDeployment()");
	}
	
	@Test
	//@Ignore
	public void deployTestFileOnCloud(){
		ReasonerInterfacer currentReasonerInterfacer = null;
		currentReasonerInterfacer = new ReasonerInterfacer(TEST_INPUTFILE_SimpleDeployment, true);
		
		ExecInterfacer exec = new ExecInterfacer();
		IValidator validator = new ValidatorImpl();
		Coordinator c = new Coordinator(currentReasonerInterfacer, exec, validator);
		
		c.deployModelIDThreaded(1);
		System.out.println("End of method deployTestFileOnCloud()");
	}
	
	@Test
	@Ignore
	public void deployFromCDOOnCloud(){
		ReasonerInterfacer currentReasonerInterfacer = null;
		currentReasonerInterfacer = new ReasonerInterfacer("CAMEL_676790ec61a8ae90a627eec0323eee12", false);
		
		ExecInterfacer exec = new ExecInterfacer();
		IValidator validator = new ValidatorImpl();
		Coordinator c = new Coordinator(currentReasonerInterfacer, exec, validator);
		
		c.deployModelIDThreaded(1);
		System.out.println("End of method deployFromCDOOnCloud()");
	}
	
	@Test
	@Ignore
	public void testZMQ(){
		ZeroMQPublisher zmpMetric = null;
		ZeroMQPublisher zmpModelreq = new ZeroMQPublisher("ModelReqPub", "newModelArrival", 5551);
		
		ZeroMQSubscriber zmsMetric = new ZeroMQSubscriber("MetricSub", "localhost", "M",5550);
		ZeroMQSubscriber zmsModelreq0 = new ZeroMQSubscriber("ModelReqSub0", "localhost", "M", 5551);
		ZeroMQSubscriber zmsModelreq1;
		
		zmsMetric.start();
		zmsModelreq0.start();
		
		
		for (int i = 0; i < 10000; i++) {
			if(i%2==0)
				if(zmpMetric != null)
					zmpMetric.publishMsg(new String[] {"M" + i + ""});
			else
				zmpModelreq.publishMsg(new String[] {"M" + i + ""});
			if(i==1000){
				zmsModelreq1 = new ZeroMQSubscriber("ModelReqSub1", "localhost", "M", 5551);
				zmsModelreq1.start();
			}
			
			if(i==2000)
				zmpMetric = new ZeroMQPublisher("MetricPub", "newModelArrival", 5550);
		}
	}
	
	@Test
	@Ignore
	public void redeployPlanGeneration(){
		ReasonerInterfacer currentReasonerInterfacer = null;
		ReasonerInterfacer targetReasonerInterfacer = null;

		DeploymentModel currentModel = null;
		DeploymentModel targetModel = null;

		currentReasonerInterfacer = new ReasonerInterfacer(TEST_INPUTFILE_SimpleDeployment, true);
		//targetReasonerInterfacer = new ReasonerInterfacer(TEST_INPUTFILE_ReconfigDeployment1, true);
		targetReasonerInterfacer = new ReasonerInterfacer(TEST_INPUTFILE_ReconfigDeployment3, true);

		//Simple Deployment
/*		currentModel = null;
		targetModel = targetReasonerInterfacer.loadNthFromFile(1);*/
		
		//Redeployment
		currentModel = currentReasonerInterfacer.loadNthFromFile(1);
		targetModel = targetReasonerInterfacer.loadNthFromFile(1);

		System.out.println("!=============================================================================");

		PlanGenerator generator = null;

		if(currentModel != null && targetModel != null)//condition for reconfiguration
			generator = new PlanGenerator();
		else if(currentModel == null && targetModel != null)//condition for simple deployment
			generator = new PlanGenerator(true);

		Plan newPlan = null;

		try {
			newPlan = generator.generate(currentModel, targetModel);
		} catch (PlanGenerationException e) {
			// TODO Auto-generated catch block
			System.out.println("Plan generation exception");
			e.printStackTrace();
		} catch (ModelComparatorException e) {
			// TODO: handle exception
			System.out.println("Model comparator exception");
			e.printStackTrace();
		}
		
		System.out.println("==============================================================================");

		List<ConfigurationTask> tasks = newPlan.getTasks();
		try {
			for(ConfigurationTask task : tasks){
				System.out.println("Class Name " + task.getClass());
				System.out.println("Task Type " + task.getTaskType());
				System.out.println("JSON " + task.getJsonModel().toString());
				for(ConfigurationTask dep : task.getDependencies()){
					System.out.println("Class Name " + dep.getClass() + " " + dep.getTaskType());
					System.out.println("Test key name : " + dep.getName());
					System.out.println("dep JSON " + dep.getJsonModel().toString());
				}
				System.out.println();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		System.out.println("=============================================================================!");
	}
	
	@Test
//	@Ignore
	public void testRedeployment(){

		SimpleDeploymentScenario example = new SimpleDeploymentScenario(driver, true);
		
		ReasonerInterfacer currentReasonerInterfacer = null;
		currentReasonerInterfacer = new ReasonerInterfacer(example.getDeploymentXMI(), true);
		
		ExecInterfacer exec = new ExecInterfacer(driver.getBaseUrl());
		IValidator validator = new ValidatorImpl();
		Coordinator c = new Coordinator(currentReasonerInterfacer, exec, validator);
		
		c.deployModelIDThreaded(1);
		
		RedeploymentScenario3 example3 = new RedeploymentScenario3(driver);
		ReasonerInterfacer redeploymentInterfacer = new ReasonerInterfacer(example3.getDeploymentXMI(), true);
		c.deployModelIDThreaded(1, redeploymentInterfacer);
		System.out.println("End of method testRedeployment()");
	}
	
	@Test
	@Ignore
	public void redeployTestFilesOnCloud(){
		ReasonerInterfacer currentReasonerInterfacer = null;
		ReasonerInterfacer targetReasonerInterfacer = null;
		
		DeploymentModel currentModel = null;
		DeploymentModel targetModel = null;
		
		currentReasonerInterfacer = new ReasonerInterfacer(TEST_INPUTFILE_SimpleDeployment, true);
		targetReasonerInterfacer = new ReasonerInterfacer(TEST_INPUTFILE_ReconfigDeployment3, true);
		
		ExecInterfacer exec = new ExecInterfacer();
		IValidator validator = new ValidatorImpl();
		Coordinator c = new Coordinator(currentReasonerInterfacer, exec, validator);
		
		System.out.println("!=============================================================================");
		System.out.println("Starting SimpledeployTestFileOnCloud()");
		
		c.deployModelIDThreaded(1);
		System.out.println("End of SimpledeployTestFileOnCloud()");
		System.out.println("==============================================================================");
		System.out.println("Starting testFileRedeployment()");
		
		c.deployModelIDThreaded(1, targetReasonerInterfacer);
		System.out.println("End of method testFileRedeployment()");
		System.out.println("=============================================================================!");
	}
}
