/*
 * Copyright (c) 2014 UK Science and Technology Facilities Council
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.plangenerator.test;

import static org.junit.Assert.*;

import java.io.File;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.upperware.plangenerator.DeploymentModelComparator;
import eu.paasage.upperware.plangenerator.ModelComparator;
import eu.paasage.upperware.plangenerator.util.ModelUtil;

/**
 * JUnit test case for {@link eu.paasage.upper.plangenerator.DeploymentModelComparator 
 * <em>DeploymentModelComparator</em>}
 * <p> 
 * @author Shirley Crompton
 * org	UK Science and Technology Facilities Council
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeploymentModelComparatorTest {
	/** logger */
	private final static Logger log = Logger.getLogger(DeploymentModelComparatorTest.class);	
	/** current camel xmi file */
	private static String CUR_CM_FILE = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "bewan-camel-reconfig.xmi";
	/** current cross-referenced provider model file */
	//private static String CUR_PM_FILE = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "upperware-models_fms_1436444254010_GWDG-DE-1436444254477.xmi"; 
	/** target camel xmi file */
	//private static String TAR_CM_FILE = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "test_1_reconfig.xmi";
	/** target cross-referenced provider model file */
	//private static String TAR_PM_FILE = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "upperware-models_fms_1.xmi"; 
	/** current camel model loaded from the CUR_* set of files */
	private static CamelModel camel;	
	/** the model comparator */
	private static DeploymentModelComparator comparator;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		if(comparator != null){
			comparator = null;
		}
		if(camel != null){
			camel = null;
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	/**
	 * test all new deployment
	 */
	@Test
	public void testNewDeployment() {
		try {
			camel = ModelUtil.loadCamelModel(CUR_CM_FILE);
			log.debug("retrieved " + camel.getDeploymentModels().size() + " deploymentmodels...");
			DeploymentModel currentDM = camel.getDeploymentModels().get(1); //first deployment - original
			DeploymentModel targetDM = camel.getDeploymentModels().get(2);  //only name changed
			//
			comparator = new DeploymentModelComparator(currentDM, targetDM);
			//
			comparator.compareModels();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Assert.fail("test failed with error : " + e.getMessage());
		}
	}

}
