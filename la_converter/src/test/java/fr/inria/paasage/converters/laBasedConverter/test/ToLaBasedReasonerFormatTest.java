/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package fr.inria.paasage.converters.laBasedConverter.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.passage.upperware.commons.model.tools.CPModelTool;
import eu.passage.upperware.commons.model.tools.ModelTool;
import junit.framework.Assert;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.paasage.upperware.converters.laBasedConverter.CDOClientExtended;
import eu.paasage.upperware.converters.laBasedConverter.ToLaBasedReasonerFormat;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.CpFactory;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.cp.Variable;
import eu.paasage.upperware.metamodel.types.TypesPackage;

public class ToLaBasedReasonerFormatTest 
{
	
	
	protected static String MODEL_FILE_PATH= "."+File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"examples"+File.separator+"scenario3"+File.separator+"cpModel.xmi"; 
	
	protected static String MODEL_FILE_JAR_PATH= File.separator+"examples"+File.separator+"scenario3"+File.separator+"cpModel.xmi";
	
	protected static String VALUES_FILE_PATH= "."+File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"examples"+File.separator+"scenario3"+File.separator+"values.txt";
	
	protected static String VALUES_COMMENTS_FILE_PATH= "."+File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"examples"+File.separator+"scenario3"+File.separator+"valuesWithComments.txt";
	
	protected static String VALUES_FILE_JAR_PATH= File.separator+"examples"+File.separator+"scenario3"+File.separator+"values.txt";
	
	protected static String VALUES_COMMENTS_FILE_JAR_PATH= File.separator+"examples"+File.separator+"scenario3"+File.separator+"valuesWithComments.txt";
	
	
	protected ConstraintProblem cp; 
	
	protected ConstraintProblem cpNF; 
	
	protected String appName; 
	
	protected String appNameNF; 
	
	protected File tempDir;
	
	protected String valuesFileName; 
	
	protected String valuesCommentsFileName; 
	
	protected CDOClientExtended client; 
	
	@BeforeClass
	public static void init()
	{
		CpPackage.eINSTANCE.eClass(); 
		TypesPackage.eINSTANCE.eClass(); 
		
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
	    Map<String, Object> m = reg.getExtensionToFactoryMap();
	    m.put("*", new XMIResourceFactoryImpl());
	}
	
	@Before
	public void loadModelFile()
	{
		appName="conversionTest";
		
		appNameNF="conversionTestNF";
		
		File modelFile= new File(MODEL_FILE_PATH);
		
		if(!modelFile.exists())
		{
			 modelFile= new File(getClass().getClassLoader().getResource(MODEL_FILE_JAR_PATH).getPath()); 
		}
		
		Resource res= ModelTool.loadModel(modelFile);
		
		cp= (ConstraintProblem) res.getContents().get(0); 
		
		Resource resNF= ModelTool.loadModel(modelFile); 
		
		cpNF= (ConstraintProblem) resNF.getContents().get(0); 
		
		
		//Save in CDO
		client= new CDOClientExtended(); 
		client.registerPackage(CpPackage.eINSTANCE);
		client.registerPackage(TypesPackage.eINSTANCE);
		
		ConstraintProblem cpEmpty= CpFactory.eINSTANCE.createConstraintProblem(); 
		
		List<EObject> models= new ArrayList<EObject>(); 
		
		models.add(cpEmpty); 
		models.add(cp); 
		
		client.storeModels(models, appName);
		
		models= client.getResourceContentsWithTransanction(appName);
		
		cpEmpty= (ConstraintProblem) models.get(0);
		cp= (ConstraintProblem) models.get(1);
		
		//Save in CDO NF
		
		ConstraintProblem cpEmptyNF= CpFactory.eINSTANCE.createConstraintProblem(); 
		
		List<EObject> modelsNF= new ArrayList<EObject>(); 
		
		modelsNF.add(cpEmptyNF); 
		modelsNF.add(cpNF); 
		client.storeModels(modelsNF, ToLaBasedReasonerFormat.CDO_SERVER_PATH+appNameNF);
		
		tempDir= new File("."+File.separator+"temp"+File.separator);
		tempDir.mkdirs(); 
	}
	
	
	@Before
	public void selectValuesFile()
	{
		File valuesFile= new File(VALUES_FILE_PATH); 
		
		if(!valuesFile.isFile())
		{	
			valuesFile= new File(getClass().getClassLoader().getResource(VALUES_FILE_JAR_PATH).getPath()); 
			
			if(valuesFile.isFile())
				valuesFileName= VALUES_FILE_JAR_PATH; 
			else
				valuesFile= null; 
		}	
		else
			valuesFileName= VALUES_FILE_PATH; 
		
		File valuesCommentsFile= new File(VALUES_COMMENTS_FILE_PATH); 
		
		if(!valuesCommentsFile.isFile())
		{	
			valuesCommentsFile= new File(getClass().getClassLoader().getResource(VALUES_COMMENTS_FILE_JAR_PATH).getPath()); 
			
			if(valuesCommentsFile.isFile())
				valuesCommentsFileName= VALUES_COMMENTS_FILE_JAR_PATH; 
			else
				valuesCommentsFile= null; 
		}	
		else
			valuesCommentsFileName= VALUES_COMMENTS_FILE_PATH; 
	}

	
	@Test
	public void conversionTest()
	{		
		ToLaBasedReasonerFormat converter= new ToLaBasedReasonerFormat();
		
		converter.toFormat(cp, appName, tempDir);
		
		//TODO add verification cases with generated files
	}

	@Test
	public void conversionCDOTest()
	{		
		ToLaBasedReasonerFormat converter= new ToLaBasedReasonerFormat();
		
		converter.generateCPDescription(tempDir, appName);
		
		//TODO add verification cases with generated files
	}
	
	@Test
	public void conversionTestNF()
	{	
		ToLaBasedReasonerFormat converter= new ToLaBasedReasonerFormat();
		
		converter.toFormatNF(cp, appNameNF, tempDir);
		
		//TODO add verification cases with generated files
	}

	@Test
	public void conversionCDOTestNF()
	{
		ToLaBasedReasonerFormat converter= new ToLaBasedReasonerFormat();
		
		converter.generateCPDescriptionNF(tempDir, appNameNF);
		
		//TODO add verification cases with generated files
	}
	
	@Test
	public void assignValueTest()
	{
		
		Assert.assertNotNull("The values file does not exists!", valuesFileName);
		
		ToLaBasedReasonerFormat converter= new ToLaBasedReasonerFormat();
		
		converter.assignValues( valuesFileName, appNameNF);
		
		List<EObject> resources= client.getResourceContents(ToLaBasedReasonerFormat.CDO_SERVER_PATH+appNameNF); 
		
		ConstraintProblem cp= (ConstraintProblem) resources.get(1); 
		
		Variable var1= converter.searchVariableByName(cp, "app_component_simpleApplicationWar_in_vm_M"); 
		
		Assert.assertNotNull("The variable app_component_simpleApplicationWar_in_vm_M does not exist", var1);
		
		Assert.assertEquals("The value of the variable app_component_simpleApplicationWar_in_vm_M is not correct", "1", CPModelTool.getValueFromVar(var1, cp));
		
		Variable var2= converter.searchVariableByName(cp, "app_component_Tomcat_in_vm_M"); 
		
		Assert.assertNotNull("The variable app_component_Tomcat_in_vm_M does not exist", var2);
		
		Assert.assertEquals("The value of the variable app_component_Tomcat_in_vm_M is not correct", "1", CPModelTool.getValueFromVar(var2, cp));
		
		Variable var3= converter.searchVariableByName(cp, "number_vm_M"); 
		
		Assert.assertNotNull("The variable number_vm_M does not exist", var3);
		
		Assert.assertEquals("The value of the variable number_vm_M is not correct", "50", CPModelTool.getValueFromVar(var3, cp));
			
	}
	
	@Test
	public void assignValueWithCommentsTest()
	{
		
		Assert.assertNotNull("The values file does not exists!", valuesFileName);
		
		ToLaBasedReasonerFormat converter= new ToLaBasedReasonerFormat();
		
		converter.assignValues( valuesCommentsFileName, appNameNF);
		
		List<EObject> resources= client.getResourceContents(ToLaBasedReasonerFormat.CDO_SERVER_PATH+appNameNF); 
		
		ConstraintProblem cp= (ConstraintProblem) resources.get(1); 
		
		Variable var1= converter.searchVariableByName(cp, "app_component_simpleApplicationWar_in_vm_M"); 
		
		Assert.assertNotNull("The variable app_component_simpleApplicationWar_in_vm_M does not exist", var1);
		
		Assert.assertEquals("The value of the variable app_component_simpleApplicationWar_in_vm_M is not correct", "1", CPModelTool.getValueFromVar(var1, cp));
		
		Variable var2= converter.searchVariableByName(cp, "app_component_Tomcat_in_vm_M"); 
		
		Assert.assertNotNull("The variable app_component_Tomcat_in_vm_M does not exist", var2);
		
		Assert.assertEquals("The value of the variable app_component_Tomcat_in_vm_M is not correct", "1", CPModelTool.getValueFromVar(var2, cp));
		
		Variable var3= converter.searchVariableByName(cp, "number_vm_M"); 
		
		Assert.assertNotNull("The variable number_vm_M does not exist", var3);
		
		Assert.assertEquals("The value of the variable number_vm_M is not correct", "50", CPModelTool.getValueFromVar(var3, cp));
			
	}
}
