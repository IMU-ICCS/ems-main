/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.cp.cloner.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.util.ConcurrentAccessException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.Before;
import org.junit.Test;

import eu.paasage.camel.CamelModel;
import eu.paasage.upperware.cp.cloner.CDOClientExtended;
import eu.paasage.upperware.cp.cloner.CPCloner;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.cp.BooleanDomain;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.Variable;
import eu.paasage.upperware.metamodel.tool.UpperwareModelTool;

public class ClonerTest 
{
	protected static String CP_MODEL_FILE_PATH= "."+File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"examples"+File.separator+"sensApp"+File.separator+"sensAppCP.xmi"; 
	
	protected static String CONFIG_MODEL_FILE_PATH= "."+File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"examples"+File.separator+"sensApp"+File.separator+"sensAppPaasageConfig.xmi"; 
	
	protected static String CAMEL_MODEL_FILE_PATH= "."+File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"examples"+File.separator+"scalarm"+File.separator+"Scalarm.xmi"; 
	
	protected static String CAMEL_MODEL_FILE_JAR_PATH= File.separator+"examples"+File.separator+"scalarm"+File.separator+"Scalarm.xmi"; 
	
	protected static String CP_MODEL_FILE_JAR_PATH= File.separator+"examples"+File.separator+"sensApp"+File.separator+"sensAppCP.xmi";
	
	protected static String CONFIG_MODEL_FILE_JAR_PATH= File.separator+"examples"+File.separator+"sensApp"+File.separator+"sensAppPaasageConfig.xmi";
	
	protected static String CP_ID= "sensAppTestOriginal"; 
	
	protected static String CP_MODEL_ID=  CPCloner.CDO_SERVER_PATH+CP_ID; 
	
	protected static String CP_MODEL_COPY_ID=  CPCloner.CDO_SERVER_PATH+"sensAppTestCopy";
	
	protected static String CAMEL_ID= "camelTestOriginal"; 
	
	protected static String CAMEL_MODEL_ID=  CPCloner.CDO_SERVER_PATH+CAMEL_ID; 
	
	protected static String CAMEL_MODEL_COPY_ID=  CPCloner.CDO_SERVER_PATH+"camelTestCopy";
	
	protected static String MODEL_PATH=  "."+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"models"+File.separator; 
	
	protected static String FUNCTION_MODEL_PATH=MODEL_PATH+"FunctionTypes.xmi"; 
	
	protected static String OS_MODEL_PATH=MODEL_PATH+"OperatingSystems.xmi"; 
	
	protected static String PROVIDER_TYPES_MODEL_PATH=MODEL_PATH+"ProviderTypes.xmi"; 
	
	protected static String PROVIDER_TYPES_ID="providerTypesCloner"; 
	
	protected static String OS_ID="osCloner"; 
	
	protected static String FUNCTION_TYPES_ID="functionTypesCloner";
	
	
	
	private CDOClientExtended extended= CPCloner.createCDOClient(); 
	
	@Before
	public void storeModels()
	{
		File cpModelFile= new File(CP_MODEL_FILE_PATH);
		
		if(!cpModelFile.exists())
		{
			cpModelFile= new File(getClass().getClassLoader().getResource(CP_MODEL_FILE_JAR_PATH).getPath()); 
		}
		
		File configModelFile= new File(CONFIG_MODEL_FILE_PATH);
		
		if(!configModelFile.exists())
		{
			configModelFile= new File(getClass().getClassLoader().getResource(CONFIG_MODEL_FILE_JAR_PATH).getPath()); 
		}
		
		File camelModelFile= new File(CAMEL_MODEL_FILE_PATH); 
		
		if(!camelModelFile.exists())
		{
			camelModelFile= new File(getClass().getClassLoader().getResource(CAMEL_MODEL_FILE_JAR_PATH).getPath()); 
		}
		
		
		Resource resCP= UpperwareModelTool.loadModel(cpModelFile);
		
		System.out.println("***************************************Resources "+resCP.getContents().size());
		

		List<EObject> objects= new ArrayList<EObject>(); 
				
		objects.add(resCP.getContents().get(0)); 
		
		extended.storeModelOverwritten(objects, CP_MODEL_ID);
	
		
		Resource resCamel= UpperwareModelTool.loadModel(camelModelFile); 
	
		extended.storeModelOverwritten(resCamel.getContents().get(0), CAMEL_MODEL_ID);
		
		
	}
	
	@Test
	public void cloneCPModelTestCDO()
	{
		
		CPCloner cloner= new CPCloner();
		
		cloner.cloneModel(CP_MODEL_ID, CP_MODEL_COPY_ID);
		
		List<EObject> contents= extended.getResourceContents(CP_MODEL_ID); 
		
		ConstraintProblem cp= null; 
		
		PaasageConfiguration pc= null; 
				
		for(EObject obj:contents)
		{
			if(obj instanceof ConstraintProblem)
				cp= (ConstraintProblem) obj; 
			else if(obj instanceof PaasageConfiguration)
				pc= (PaasageConfiguration) obj; 
		}
				
		List<EObject> contentsCopy= extended.getResourceContents(CP_MODEL_COPY_ID); 
		
		ConstraintProblem cpCopy= null; 
		
		PaasageConfiguration pcCopy= null; 
		
		for(EObject obj:contentsCopy)
		{
			if(obj instanceof ConstraintProblem)
				cpCopy= (ConstraintProblem) obj; 
			else if(obj instanceof PaasageConfiguration)
				pcCopy= (PaasageConfiguration) obj; 
		}
		
		
		
		Assert.assertEquals("The number of aux expressions is not correct", cp.getAuxExpressions().size(), cpCopy.getAuxExpressions().size());
		
		Assert.assertEquals("The number of constants is not correct", cp.getConstants().size(), cpCopy.getConstants().size());
		
		Assert.assertEquals("The number of constraints is not correct", cp.getConstraints().size(), cpCopy.getConstraints().size());
		
		Assert.assertEquals("The number of metrics is not correct", cp.getMetricVariables().size(), cpCopy.getMetricVariables().size());
		
		Assert.assertEquals("The number of variables is not correct", cp.getVariables().size(), cpCopy.getVariables().size());
		
		Assert.assertEquals("The number of goals is not correct", cp.getGoals().size(), cpCopy.getGoals().size());
		
		if(pc!=null)
			Assert.assertEquals("The number of components is not correct", pc.getComponents().size(),  pcCopy.getComponents().size());
		
		
	}
	
	@Test
	public void cloneCPModelTest()
	{
		
		CPCloner cloner= new CPCloner();
		
		List<EObject> contentsCopy=cloner.cloneModel(CP_MODEL_ID);
		
		List<EObject> contents= extended.getResourceContents(CP_MODEL_ID); 
		
		ConstraintProblem cp= null; 
		
		PaasageConfiguration pc= null; 
				
		for(EObject obj:contents)
		{
			if(obj instanceof ConstraintProblem)
				cp= (ConstraintProblem) obj; 
			else if(obj instanceof PaasageConfiguration)
				pc= (PaasageConfiguration) obj; 
		}
						
		ConstraintProblem cpCopy= null; 
		
		PaasageConfiguration pcCopy= null; 
		
		for(EObject obj:contentsCopy)
		{
			if(obj instanceof ConstraintProblem)
				cpCopy= (ConstraintProblem) obj; 
			else if(obj instanceof PaasageConfiguration)
				pcCopy= (PaasageConfiguration) obj; 
		}
		
		
		
		Assert.assertEquals("The number of aux expressions is not correct", cp.getAuxExpressions().size(), cpCopy.getAuxExpressions().size());
		
		Assert.assertEquals("The number of constants is not correct", cp.getConstants().size(), cpCopy.getConstants().size());
		
		Assert.assertEquals("The number of constraints is not correct", cp.getConstraints().size(), cpCopy.getConstraints().size());
		
		Assert.assertEquals("The number of metrics is not correct", cp.getMetricVariables().size(), cpCopy.getMetricVariables().size());
		
		Assert.assertEquals("The number of variables is not correct", cp.getVariables().size(), cpCopy.getVariables().size());
		
		Assert.assertEquals("The number of goals is not correct", cp.getGoals().size(), cpCopy.getGoals().size());
		
		if(pc!=null)
			Assert.assertEquals("The number of components is not correct", pc.getComponents().size(),  pcCopy.getComponents().size());
		
		
	}
	
	@Test
	public void cloneCamelModelTest()
	{
		
		CPCloner cloner= new CPCloner();
		
		List<EObject> contentsCopy=cloner.cloneModel(CAMEL_MODEL_ID);
		
		List<EObject> contents= extended.getResourceContents(CAMEL_MODEL_ID); 
		
		CamelModel model= (CamelModel) contents.get(0); 
		
		CamelModel copy= (CamelModel) contentsCopy.get(0); 
			
		Assert.assertEquals("The number of deployment model is not correct", model.getDeploymentModels().size(),  copy.getDeploymentModels().size());
		
		Assert.assertEquals("The number of provider models is not correct", model.getProviderModels().size(),  copy.getProviderModels().size());
		
		Assert.assertEquals("The number of organization models is not correct", model.getOrganisationModels().size(),  copy.getOrganisationModels().size());
		
	}
	
	@Test
	public void deleteTest()
	{
		CPCloner cloner= new CPCloner();
		
		cloner.cloneModel(CP_MODEL_ID, "deleteTest");
		
		ConstraintProblem obj= (ConstraintProblem) extended.getResourceContents("deleteTest").get(0); 
		
		System.out.println("*************************************Before... "+obj.getVariables().size());
		
		extended.deleteObject(obj.cdoDirectResource().cdoID());
		System.out.println("*************************************DOMAIN... "+obj.getVariables().get(0).getDomain());
	}

}
