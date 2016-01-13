/*
 * Copyright (c) 2014 INRIA, INSA Rennes
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.adaptationmanager.test;

import java.util.logging.Level;
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
import eu.paasage.upperware.adapter.adaptationmanager.core.ZeroMQPublisher;
import eu.paasage.upperware.adapter.adaptationmanager.core.ZeroMQSubscriber;

/**
 * Working version - tested 17th Dec with BeWan on Flexiant
 * @author ArnabSinha
 *
 */

@RunWith(JUnit4.class)
public class AdaptationManagerTest

{
	private final String TEST_INPUTFILE_SimpleDeployment = System
			.getProperty("user.dir") + "/src/test/resources/CAMEL_test_SimpleDeployment.xmi";
	
	/**
	 * Response Strings for Client Driver
	 */
	private static final int port = 9000;
	private static final String token = "bo6t3h9fl1i6ie7fu2l1k1bafnsr0jv1qo5ampb2qlt1u1q2o12674qoad8qa1sh306d1riffu471nr055tv3ftqm0u07camoo9j2luut1pqm3oosb62sqgheh0aak0bm2266n730sa62eifj78qipj3elipdnt74mc1tas8h1qv60cld5nrn2f8gttfqf5r35ol4b0obcdos";
	
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
		
		c.deployModelIDThreaded(1);
		System.out.println("End of method verifySimpleDeployment()");
	}
	
	@Test
	@Ignore
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
		ZeroMQPublisher zmpModelreq = new ZeroMQPublisher("ModelReqPub", 5551);
		
		ZeroMQSubscriber zmsMetric = new ZeroMQSubscriber("MetricSub", "localhost", "M",5550);
		ZeroMQSubscriber zmsModelreq0 = new ZeroMQSubscriber("ModelReqSub0", "localhost", "M", 5551);
		ZeroMQSubscriber zmsModelreq1;
		
		zmsMetric.start();
		zmsModelreq0.start();
		
		
		for (int i = 0; i < 10000; i++) {
			if(i%2==0)
				if(zmpMetric != null)
					zmpMetric.publishMsg("M" + i + "");
			else
				zmpModelreq.publishMsg("M" + i + "");
			if(i==1000){
				zmsModelreq1 = new ZeroMQSubscriber("ModelReqSub1", "localhost", "M", 5551);
				zmsModelreq1.start();
			}
			
			if(i==2000)
				zmpMetric = new ZeroMQPublisher("MetricPub", 5550);
		}
	}
}
