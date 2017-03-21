/*
 * Copyright (c) 2014-2016 UK Science and Technology Facilities Council
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.metasolver.test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.CpFactory;
import eu.paasage.upperware.metamodel.cp.MetricVariable;
import eu.paasage.upperware.metamodel.cp.MetricVariableValue;
import eu.paasage.upperware.metamodel.cp.Solution;
import eu.paasage.upperware.metamodel.types.BasicTypeEnum;
import eu.paasage.upperware.metamodel.types.DoubleValueUpperware;
import eu.paasage.upperware.metamodel.types.FloatValueUpperware;
import eu.paasage.upperware.metamodel.types.IntegerValueUpperware;
import eu.paasage.upperware.metamodel.types.LongValueUpperware;
import eu.paasage.upperware.metasolver.util.CpModelTool;

/**
 * JUnit for the {@link CpModelTool <em>CpModelTool</em>}
 * <p>
 * @author Shirley Crompton 
 * org UK Science and Technology Facilities Council
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CpModelToolTest {
	/** CPModel file path */
	public static String cpModelFilePath = "src" + File.separator + "test" + File.separator + "resources" + File.separator +"openFoam-cp.xmi";
	/** CP Model */
	public static Resource cpModel = null;
	/** PaasageConfiguration object*/
	public static PaasageConfiguration config = null;
	/** ConstraintProblem object */
	public static ConstraintProblem cp = null;
	/** CP solution */
	public static Solution newSolution = null;
	/** CP solution */
	public static Solution oldSolution = null;
	/** Log4j message logger */
	protected static Logger log = Logger.getLogger("CPModelToolTest.class");
	

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
		if(cpModelFilePath != null){
			cpModelFilePath = null;
		}
		if(cpModel != null){
			cpModel = null;
		}
		if(config != null){
			config = null;
		}
		if(cp != null){
			cp = null;
		}
		if(newSolution != null){
			newSolution = null;
		}
		if(oldSolution != null){
			oldSolution = null;
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
	 * Test loading the CP model from an xmi file.
	 */
	@Test
	public void testALoadModelFile() {
		log.debug("loading CP model file from " + cpModelFilePath);
		cpModel = CpModelTool.loadFile(cpModelFilePath);	
		assertNotNull("Failed to load the model file from "+ cpModelFilePath,cpModel);
	}
	/**
	 * Test extracting the PassageConfiguration object from the loaded CP Model
	 */
	@Test
	public void testBGetPaasageConf(){		
		config = CpModelTool.getAppConfig(cpModel);
		assertNotNull("Filed to extract the PassageConfiguration object from the cpModel!", config);
		assertEquals("The config id is incorrect!","OpenFoamApplication1445419635068",config.getId());
	}
	/**
	 * Test extracting the constraint problem object from the loaded CP Model
	 */
	@Test
	public void testCGetConstraintProblem(){
		cp = CpModelTool.getCP(cpModel);
		assertNotNull("Failed to extract the constraint problem obj from the cpModel!", cp);
		assertEquals("Incorrect number of solution in constraint problem!", 1, cp.getSolution().size());
	}
	/**
	 * Test extracting metric variables from the constraint problem
	 */
	@Test
	public void testDGetAndExistsMetricVariables(){
		List<MetricVariable> mvs = cp.getMetricVariables();
		//should be 2 in the file
		assertEquals("Incorrect nummber of metric variables in the ConstraintProblem!",2,mvs.size());
		if(!(CpModelTool.metricVariableExists("ResponseTimeMetric_DE_Omistack_SmallLinux_Ubuntu 14.04__SmallLinuxVM_PROFILE_Omistack-EU-1445419635179",mvs)
				&& CpModelTool.metricVariableExists("ResponseTimeMetric_EU_Omistack_SmallLinux_Ubuntu 14.04__SmallLinuxVM_PROFILE_Omistack-EU-1445419635179",mvs))){
			fail("Fail to match existing metric variable!");
		}
		assertEquals("Failed to get the correct metric variable!","ResponseTimeMetric_DE_Omistack_SmallLinux_Ubuntu 14.04__SmallLinuxVM_PROFILE_Omistack-EU-1445419635179", CpModelTool.getMetricVariable("ResponseTimeMetric_DE_Omistack_SmallLinux_Ubuntu 14.04__SmallLinuxVM_PROFILE_Omistack-EU-1445419635179",mvs).getId());
		assertEquals("Failed to get the correct metric variable!","ResponseTimeMetric_EU_Omistack_SmallLinux_Ubuntu 14.04__SmallLinuxVM_PROFILE_Omistack-EU-1445419635179",CpModelTool.getMetricVariable("ResponseTimeMetric_EU_Omistack_SmallLinux_Ubuntu 14.04__SmallLinuxVM_PROFILE_Omistack-EU-1445419635179", mvs).getId());
	}
	/**
	 * Test copy solution method
	 */
	@Test
	public void testECopySolution(){
		oldSolution = cp.getSolution().get(0);
		newSolution = CpModelTool.copySolution(oldSolution);
		//
		assertNotNull("Failed to copy solution!",newSolution);
		assertTrue(newSolution.getMetricVariableValue().size() == oldSolution.getMetricVariableValue().size());
		assertTrue(newSolution.getTimestamp() != oldSolution.getTimestamp());	//timestamp should be different
		assertTrue(newSolution.getVariableValue().size() == oldSolution.getVariableValue().size());
		//debug
//		List<MetricVariableValue> mvvs = newSolution.getMetricVariableValue();
//		for(MetricVariableValue mv : mvvs){//get cdoid causes npe, and mv.getVariable().getId() returns null
//			System.out.println("Variable: " + mv.getVariable().getId() + ": value = " + mv.getValue().toString()/* + ", cdoid = " + mv.cdoID().toString()*/ + "\n");
//		}
	}
	/**
	 * test create metric variable value method
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testFCreateMetricVariableValue(){
		//int
		MetricVariable intMV = CpFactory.eINSTANCE.createMetricVariable();
		intMV.setId("int");
		intMV.setType(BasicTypeEnum.INTEGER);
		MetricVariableValue intMVV = CpModelTool.createMVV(intMV, "1");
		assertTrue("Incorrect metric variable!",intMVV.getVariable().equals(intMV));
		assertTrue("Incorrect metric variable value type!",intMVV.getValue() instanceof IntegerValueUpperware);
		assertEquals("Incorrect metric variable value!", 1, ((IntegerValueUpperware) intMVV.getValue()).getValue());
		//double
		MetricVariable doubleMV = CpFactory.eINSTANCE.createMetricVariable();
		doubleMV.setId("double");
		doubleMV.setType(BasicTypeEnum.DOUBLE);
		MetricVariableValue doubleMVV = CpModelTool.createMVV(doubleMV, "100.1");
		assertTrue("Incorrect metric variable!",doubleMVV.getVariable().equals(doubleMV));
		assertTrue("Incorrect metric variable value type!",doubleMVV.getValue() instanceof DoubleValueUpperware);
		assertEquals("Incorrect metric variable value!", 100.1, ((DoubleValueUpperware) doubleMVV.getValue()).getValue(),0);
		//long
		long l = System.currentTimeMillis();
		MetricVariable longMV = CpFactory.eINSTANCE.createMetricVariable();
		longMV.setId("long");
		longMV.setType(BasicTypeEnum.LONG);
		MetricVariableValue longMVV = CpModelTool.createMVV(longMV, String.valueOf(l));
		assertTrue("Incorrect metric variable!",longMVV.getVariable().equals(longMV));
		assertTrue("Incorrect metric variable value type!",longMVV.getValue() instanceof LongValueUpperware);
		assertEquals("Incorrect metric variable value!", l, ((LongValueUpperware) longMVV.getValue()).getValue(),0);
		//float
		float f = 10.4f;
		MetricVariable floatMV = CpFactory.eINSTANCE.createMetricVariable();
		floatMV.setId("float");
		floatMV.setType(BasicTypeEnum.FLOAT);
		MetricVariableValue floatMVV = CpModelTool.createMVV(floatMV, String.valueOf(f));
		assertTrue("Incorrect metric variable!",floatMVV.getVariable().equals(floatMV));
		assertTrue("Incorrect metric variable value type!",floatMVV.getValue() instanceof FloatValueUpperware);
		assertEquals("Incorrect metric variable value!", f, ((FloatValueUpperware) floatMVV.getValue()).getValue(),0);
		
	}
	/**
	 * Test update an existing metric variable value
	 */
	//@Ignore
	@Test
	public void testGUpdateMetricVariableValue(){
		MetricVariableValue mvv = newSolution.getMetricVariableValue().get(0);
		//System.out.println("mvv is linked to this metric variable " + mvv.getVariable().getId());
		CpModelTool.updateMetricVariableValue(mvv, BasicTypeEnum.DOUBLE, "2.0");
		DoubleValueUpperware dValue = (DoubleValueUpperware) newSolution.getMetricVariableValue().get(0).getValue();
		assertTrue("Failed to update the first metric variable's value!",dValue.getValue()==2.0);
	}
	/**
	 * test set constant metric variable value method
	 */
	//@Ignore
	@Test
	public void testHSetConstantMetricVariableValue(){
		MetricVariable mv = CpFactory.eINSTANCE.createMetricVariable();
		mv.setId("test");
		mv.setType(BasicTypeEnum.LONG);
		Long l = 1l;	
		oldSolution = CpModelTool.setConstantValue(mv, oldSolution);
		List<MetricVariableValue> mvvs = oldSolution.getMetricVariableValue();
		boolean found = false;
		for(MetricVariableValue mvv : mvvs){
			String id = mvv.getVariable().getId();
			if(id!= null && id.equals("test")){
				assertEquals("Incorrect constant value!", l, ((LongValueUpperware) mvv.getValue()).getValue(),0);
				found = true;
				break;
			}
		}
		assertTrue("Failed to add the metric variable",found);
	}
	/**
	 * Test parsing the existing resource id to get the version number portion and increment that.
	 */
	@Test
	public void testIGetCloneId(){
		String cloneId = CpModelTool.getCloneId("OpenFoamApplication1447777579963", "upperware-models/OpenFoamApplication1447777579963_2");
		assertEquals("Error getting cloned id 1, got : " + cloneId, "OpenFoamApplication1447777579963_3", cloneId );
		cloneId =  CpModelTool.getCloneId("OpenFoamApplication1447777579963", "upperware-models/OpenFoamApplication1447777579963");
		assertEquals("Error getting cloned id 2, got : " + cloneId, "OpenFoamApplication1447777579963v1", cloneId );
	}
}
