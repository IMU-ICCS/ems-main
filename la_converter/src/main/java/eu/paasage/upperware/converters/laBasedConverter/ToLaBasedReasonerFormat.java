/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.converters.laBasedConverter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import eu.passage.upperware.commons.model.tools.CPModelTool;
import eu.passage.upperware.commons.model.tools.ModelTool;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.util.ConcurrentAccessException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import eu.paasage.camel.CamelPackage;
import eu.paasage.camel.deployment.DeploymentPackage;
import eu.paasage.camel.organisation.OrganisationPackage;
import eu.paasage.camel.provider.ProviderPackage;
import eu.paasage.camel.type.TypePackage;
import eu.paasage.upperware.metamodel.application.ApplicationPackage;
import eu.paasage.upperware.metamodel.cp.BooleanDomain;
import eu.paasage.upperware.metamodel.cp.ComparatorEnum;
import eu.paasage.upperware.metamodel.cp.ComparisonExpression;
import eu.paasage.upperware.metamodel.cp.ComposedExpression;
import eu.paasage.upperware.metamodel.cp.ComposedUnaryExpression;
import eu.paasage.upperware.metamodel.cp.ComposedUnaryOperatorEnum;
import eu.paasage.upperware.metamodel.cp.Constant;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.cp.Domain;
import eu.paasage.upperware.metamodel.cp.Expression;
import eu.paasage.upperware.metamodel.cp.GoalOperatorEnum;
import eu.paasage.upperware.metamodel.cp.ListDomain;
import eu.paasage.upperware.metamodel.cp.MetricVariable;
import eu.paasage.upperware.metamodel.cp.MetricVariableValue;
import eu.paasage.upperware.metamodel.cp.MultiRangeDomain;
import eu.paasage.upperware.metamodel.cp.NumericDomain;
import eu.paasage.upperware.metamodel.cp.NumericExpression;
import eu.paasage.upperware.metamodel.cp.NumericListDomain;
import eu.paasage.upperware.metamodel.cp.OperatorEnum;
import eu.paasage.upperware.metamodel.cp.RangeDomain;
import eu.paasage.upperware.metamodel.cp.SimpleUnaryExpression;
import eu.paasage.upperware.metamodel.cp.SimpleUnaryOperatorEnum;
import eu.paasage.upperware.metamodel.cp.Solution;
import eu.paasage.upperware.metamodel.cp.Variable;
import eu.paasage.upperware.metamodel.cp.VariableValue;
import eu.paasage.upperware.metamodel.types.BasicTypeEnum;
import eu.paasage.upperware.metamodel.types.DoubleValueUpperware;
import eu.paasage.upperware.metamodel.types.FloatValueUpperware;
import eu.paasage.upperware.metamodel.types.IntegerValueUpperware;
import eu.paasage.upperware.metamodel.types.LongValueUpperware;
import eu.paasage.upperware.metamodel.types.NumericValueUpperware;
import eu.paasage.upperware.metamodel.types.StringValueUpperware;
import eu.paasage.upperware.metamodel.types.TypesPackage;
import eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage;

import static eu.passage.upperware.commons.MelodicConstants.CDO_SERVER_PATH;


/**
 * Class representing the LABasedReasoner Converver
 * @author danielromero
 *
 */
public class ToLaBasedReasonerFormat 
{
	/*
	 * ATTRIBUTES
	 */
	
	
	//Lists to execute the generation via velocity
	
	//Vars that have a domain initialization
	protected List<LaVariable> varsWithDomainInt; 
	
	//Vars without domain initialization
	protected List<LaVariable> varsWithoutDomainInt; 
	
	//Vars that has multi ranges as domain
	protected List<LaVariable> varsWithMultiRangeDomain; 
	
	//Metric vars that has multi ranges as domain
	protected List<LaVariable> metricVars; 
	
	//Constraints
	protected List<LaConstraint> laConstraints; 
	
	protected List<LaConstraint> equalityLaConstraints; 
	
	protected List<LaConstraint> inequalityLaConstraints; 
	
	protected String objectiveFunctionExp; 
	
	
	/*
	 * Constants
	 */
	
	//Basic types 
	private final static String DOUBLE_TYPE="double"; 
	
	private final static String INT_TYPE="int"; 
	
	private final static String BOOLEAN_TYPE="Boolean"; 
	
	private final static String FLOAT_TYPE="float"; 
	
	private final static String LONG_TYPE="long int"; 
	
	//Domain types
	
	private final static String RANGE_DOMAIN_TYPE="Range"; 
	
	private final static String LIST_DOMAIN_TYPE="List"; 
	
	private final static String NUMERIC_LIST_DOMAIN_TYPE="NumericList"; 
	
	private final static String MULTI_RANGE_DOMAIN_TYPE="MultiRange"; 
	
	//Property keys related to the velocity template
	
	private final static String VARIABLES_WITH_DOMAIN_INIT_KEY="variablesWithDomainInit"; 
	
	private final static String VARIABLES_WITHOUTH_DOMAIN_INIT_KEY="variablesWithoutDomainInit"; 
	
	private final static String VARIABLES_WITH_MULTI_RANGE_DOMAIN_KEY="variablesWithMultiRangeDomain";
	
	private final static String METRIC_VARIABLES_KEY="metricVariables"; 
	
	private final static String CONSTRAINTS_KEY="constraints";
	
	private final static String EQUALITY_CONSTRAINTS_KEY="equalityConstraints"; 
	
	private final static String INEQUALITY_CONSTRAINTS_KEY="inequalityConstraints"; 
	
	
	private final static String UTILITY_FUNCTION__KEY="utilityFunctionExpression"; 
	
	
	//File paths used in the execution with maven 
	
	public final static String VARIABLES_TEMPLATE_FILE_NAME="."+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"templates"+File.separator+"variable.model.vm"; 
	
	public final static String VARIABLES_TEMPLATE_FILE_NAME_JAR=File.separator+"templates"+File.separator+"variable.model.vm"; 
	
	public final static String CONSTRAINTS_TEMPLATE_FILE_NAME="."+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"templates"+File.separator+"constraint.model.vm"; 
	
	public final static String CONSTRAINTS_TEMPLATE_FILE_NAME_JAR=File.separator+"templates"+File.separator+"constraint.model.vm"; 
	
	public final static String NF_CONSTRAINTS_TEMPLATE_FILE_NAME="."+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"templates"+File.separator+"nfConstraint.model.vm"; 
	
	public final static String NF_CONSTRAINTS_TEMPLATE_FILE_NAME_JAR=File.separator+"templates"+File.separator+"nfConstraint.model.vm"; 
	
	
	//Suffixes for the generated files
	
	public final static String VARIABLES_FILE_SUFFIX="Variables.model"; 
	
	public final static String CONSTRAINTS_FILE_SUFFIX="Constraints.model"; 
	
	
	//Comparison operators 
	
	public final static String DIFFERENT= "!="; 
	
	public final static String EQUAL_TO="=="; 
	
	public final static String GREATER_THAN=">"; 
	
	public final static String LESS_THAN="<";
	
	public final static String GREATER_THAN_OR_EQUAL_TO=">="; 
	
	public final static String LESS_THAN_OR_EQUAL_TO="<="; 
	
	//Service types
	public final static int GENERATION=0; 
	
	public final static int ASSIGNMENT=1; 

	
	//Log
	
	public static Logger logger= Logger.getLogger("paasage-converters-log");

	private static final String JAR_LOG4G_PROPERTIES_FILE="."+File.separator+"config"+File.separator+"log4j.properties";
	
	private static final String LOG4G_PROPERTIES_FILE="."+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"config"+File.separator+"log4j.properties";
	

	private static final String VELOCITY_PROPERTIES_FILE="."+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"config"+File.separator+"velocity.properties";
	
	
	static{
		
		configLog();
		loadVelocityProperties(); 
		
	}
	
	/**
	 * Configuration of log4
	 */
	protected static void configLog()
	{
		BasicConfigurator.configure();
		File pFile= selectExistingLog4File(); 
		
		if(pFile.exists())
			PropertyConfigurator.configure(pFile.getAbsolutePath());
	}
	
	/**
	 * Look for a existing log4.properties file. 
	 * @return the log4.properties file
	 */
	protected static File selectExistingLog4File()
	{
		
		File pFile= new File(LOG4G_PROPERTIES_FILE);
		
		if(!pFile.isFile())
			pFile= new File(JAR_LOG4G_PROPERTIES_FILE); 
		
		
		return pFile; 
	}
	
	/**
	 * Load the properties file related to the velocity template tool if the velocity.propeties file exists. In particular, it loads the property defining classloader used to load the velocity template
	 */
	protected static void loadVelocityProperties()
	{
		
		File pFile= new File(VELOCITY_PROPERTIES_FILE);
		
		
		if(pFile.isFile())
		{
			Properties props= new Properties(); 
			
			try {
				props.load(new FileInputStream(pFile));
				
				
				for(Object key:props.keySet())
				{
					
					String keyString= (String) key; 
					
					System.setProperty(keyString, props.getProperty(keyString)); 
					
				}
			} catch (FileNotFoundException e) 
			{
				logger.error("ToLaBasedReasonerFormat- loadVelocityProperties - The velocity properties file does not exsit...");
				
				e.printStackTrace();
			} catch (IOException e) {
				
				logger.error("ToLaBasedReasonerFormat- loadVelocityProperties - Problems reading the velocity properties file...");
				e.printStackTrace();
			}
		}
		
		
		
	}
	
	
	public ToLaBasedReasonerFormat()
	{
		CpPackage.eINSTANCE.eClass(); 
		TypesPackage.eINSTANCE.eClass(); 
		
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
	    Map<String, Object> m = reg.getExtensionToFactoryMap();
	    m.put("*", new XMIResourceFactoryImpl());
	}
	
	/*****
	 * 
	 * Standard conversion
	 * 
	 *****/
	
	
	/**
	 * Generates the variables and constraints used by the LA reasoner as input. Two files are generated:
	 * destDir/appNameVariable.model : Containing the variable definitions
	 *  destDir/appNameConstraint.model : Containing the constraint definitions
	 * @param cp 		The constraint problem model
	 * @param appName	The name of the application related to the cp
	 * @param destDir	The output directory 
	 */
	public void toFormat(ConstraintProblem cp, String appName, File destDir)
	{
		logger.info("ToLaBasedReasonerFormat- toFormat - Init arrays...");
		
		varsWithDomainInt= new ArrayList<LaVariable>(); 
		
		varsWithoutDomainInt = new ArrayList<LaVariable>(); 
		
		varsWithMultiRangeDomain= new ArrayList<LaVariable>(); 
		
		laConstraints= new ArrayList<LaConstraint>(); 
		
		logger.info("ToLaBasedReasonerFormat- toFormat - Building variables...");
		
		EList<Solution> solutions= cp.getSolution();
		
		Solution sol= CPModelTool.searchLastSolution(solutions);
		
		if(sol==null)
		{
			sol= CPModelTool.createSolution(cp);
		}
		
		buildVariables(cp.getVariables(), sol); 
		
		Map velocityVarsMap= new Hashtable();
		
		velocityVarsMap.put(VARIABLES_WITHOUTH_DOMAIN_INIT_KEY, varsWithoutDomainInt); 
		
		velocityVarsMap.put(VARIABLES_WITH_DOMAIN_INIT_KEY, varsWithDomainInt); 
		
		velocityVarsMap.put(VARIABLES_WITH_MULTI_RANGE_DOMAIN_KEY, varsWithMultiRangeDomain); 
		
		File varsTargetFile= new File(destDir, VARIABLES_FILE_SUFFIX); 
		
		File varTemplateFile= getExistingVariableTemplateFile(); 
		
		logger.info("ToLaBasedReasonerFormat- toFormat - Generating variables.model...");
		
		VelocityTool.createFileFromTemplate(velocityVarsMap, varTemplateFile, varsTargetFile);
		
		logger.info("ToLaBasedReasonerFormat- toFormat - variables.model generated!");
		
		logger.info("ToLaBasedReasonerFormat- toFormat - Building constraints...");
		
		buildConstraints(cp.getConstraints());
		
		Map velocityConstraintsMap= new Hashtable();
		
		velocityConstraintsMap.put(CONSTRAINTS_KEY, laConstraints); 
		
		File constraintsTargetFile= new File(destDir, CONSTRAINTS_FILE_SUFFIX); 
		
		File constraintsTemplateFile= getExistingConstraintTemplateFile();
		
		logger.info("ToLaBasedReasonerFormat- toFormat - Generating constraints.model...");
		
		VelocityTool.createFileFromTemplate(velocityConstraintsMap, constraintsTemplateFile, constraintsTargetFile);
		
		logger.info("ToLaBasedReasonerFormat- toFormat - constraints.model generated!");
		
	}
	
	/**
	 * Returns a existing constraint template file
	 * @return The constraint template file
	 */
	protected File getExistingConstraintTemplateFile()
	{
		
		File constraintsTemplate= new File(CONSTRAINTS_TEMPLATE_FILE_NAME); 
		
		if(!constraintsTemplate.isFile())
			constraintsTemplate= new File(CONSTRAINTS_TEMPLATE_FILE_NAME_JAR); 
	
		return constraintsTemplate; 
	}
	
	/**
	 * Builds the constraints for the *Constraint.model file
	 * @param constraints The list of constraints from cp model
	 */
	public void buildConstraints(EList<ComparisonExpression> constraints)
	{
		 
		for(ComparisonExpression constraint: constraints)
		{
			String expression= buildConstraintDeclaration(constraint)+";"; 
			
			LaConstraint laConstraint= new LaConstraint(constraint.getId(), constraint.getId()+"Function", expression); 
			
			laConstraints.add(laConstraint); 
		}
	}
	
	/**
	 * Build the constraint declaration 
	 * @param constraint The cp constraint
	 * @return A string representing the constraint declaration
	 */
	public String buildConstraintDeclaration(ComparisonExpression constraint)
	{
		Expression exp1= constraint.getExp1(); 
		
		Expression exp2= constraint.getExp2(); 
		
		String comparator= getComparatorString(constraint.getComparator());
		
		return buildExpression(exp1)+" "+comparator+" "+buildExpression(exp2); 
		
	}
	
	public void generateCPDescription(File outputDir, String appName, String cpModelFileName)
	{
		
		
		File modelFile= new File(cpModelFileName); 
		
		if(modelFile.isFile())
		{
		
			CpPackage.eINSTANCE.eClass(); 
			TypesPackage.eINSTANCE.eClass(); 
			
			Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		    Map<String, Object> m = reg.getExtensionToFactoryMap();
		    m.put("*", new XMIResourceFactoryImpl());
			
			Resource res= ModelTool.loadModel(modelFile);
			
			ConstraintProblem cp= (ConstraintProblem) res.getContents().get(0); 
			
	
			toFormat(cp, appName, outputDir);
			
			logger.info("ToLaBasedReasoner - generateCPDescription - 3 parameters - Variable and Constraint Models generated!");
		}
		else
		{
			logger.error("ToLaBasedReasoner - generateCPDescription - 3 parameters - You have to specify a valid model file!");
		}
		
		
	}
	
	
	public void generateCPDescription(File outputDir, String appId)
	{
		CDOClientExtended client= new CDOClientExtended(); 
		
		List<EObject> models= client.getResourceContentsWithTransanction(appId); 
		
		
		if(models!=null && models.size()>1)
		{
			ConstraintProblem cp= (ConstraintProblem) models.get(1);
			
			toFormat(cp, appId, outputDir);
		}
		else
			logger.error("ToLaBasedReasoner - generateCPDescription - 2 parameters - The models of the application with ID "+ appId +" does not exist in the CDO repository!");
		
		 
	}
	
	
	/*****
	 * 
	 * NF conversion
	 * 
	 *****/
	
	/**
	 * Generates the variables and constraints used by the LA reasoner as input. Two files are generated:
	 * destDir/appNameVariable.model : Containing the variable definitions
	 *  destDir/appNameConstraint.model : Containing the constraint definitions
	 * @param cp 		The constraint problem model
	 * @param appName	The name of the application related to the cp
	 * @param destDir	The output directory 
	 */
	public void toFormatNF(ConstraintProblem cp, String appName, File destDir)
	{
		logger.info("ToLaBasedReasonerFormat- toFormatNF - Init arrays...");
		
		varsWithDomainInt= new ArrayList<LaVariable>(); 
		
		varsWithoutDomainInt = new ArrayList<LaVariable>(); 
		
		varsWithMultiRangeDomain= new ArrayList<LaVariable>(); 
		
		equalityLaConstraints= new ArrayList<LaConstraint>(); 
		
		inequalityLaConstraints= new ArrayList<LaConstraint>(); 
		
		metricVars= new ArrayList<LaVariable>(); 
		
		objectiveFunctionExp= ""; 
		
		logger.info("ToLaBasedReasonerFormat- toFormatNF - Building variables...");
		
		EList<Solution> solutions= cp.getSolution();
		
		Solution sol= CPModelTool.searchLastSolution(solutions);
		
		if(sol==null)
		{
			sol= CPModelTool.createSolution(cp);
		}
		
		
		buildVariables(cp.getVariables(), sol); 
		
		buildMetricVariables(cp.getMetricVariables(), sol);
		
		buildObjectiveFunction(cp.getGoals().get(0).getExpression(), cp.getGoals().get(0).getGoalType());
		
		Map velocityVarsMap= new Hashtable();
		
		velocityVarsMap.put(VARIABLES_WITHOUTH_DOMAIN_INIT_KEY, varsWithoutDomainInt); 
		
		velocityVarsMap.put(VARIABLES_WITH_DOMAIN_INIT_KEY, varsWithDomainInt); 
		
		velocityVarsMap.put(VARIABLES_WITH_MULTI_RANGE_DOMAIN_KEY, varsWithMultiRangeDomain); 
		
		velocityVarsMap.put(METRIC_VARIABLES_KEY, metricVars); 
		
		logger.info("ToLaBasedReasonerFormat- toFormatNF - OBJECTIVE FUNCTION: "+objectiveFunctionExp);
		
		velocityVarsMap.put(UTILITY_FUNCTION__KEY, objectiveFunctionExp); 
		
		File varsTargetFile= new File(destDir,VARIABLES_FILE_SUFFIX); 
		
		File varTemplateFile= getExistingVariableTemplateFile(); 
		
		logger.info("ToLaBasedReasonerFormat- toFormatNF - Generating variables.model...");
		
		VelocityTool.createFileFromTemplate(velocityVarsMap, varTemplateFile, varsTargetFile);
		
		logger.info("ToLaBasedReasonerFormat- toFormatNF - variables.model generated!");
		
		logger.info("ToLaBasedReasonerFormat- toFormatNF - Building constraints...");
		
		buildConstraintsNF(cp.getConstraints());
		
		Map velocityConstraintsMap= new Hashtable();
		
		velocityConstraintsMap.put(EQUALITY_CONSTRAINTS_KEY, equalityLaConstraints); 
		
		velocityConstraintsMap.put(INEQUALITY_CONSTRAINTS_KEY, inequalityLaConstraints); 
		
		File constraintsTargetFile= new File(destDir, CONSTRAINTS_FILE_SUFFIX); 
		
		File constraintsTemplateFile= getExistingConstraintTemplateFileNF();
		
		logger.info("ToLaBasedReasonerFormat- toFormatNF - Generating constraints.model...");
		
		VelocityTool.createFileFromTemplate(velocityConstraintsMap, constraintsTemplateFile, constraintsTargetFile);
		
		logger.info("ToLaBasedReasonerFormat- toFormatNF - constraints.model generated!");
		
	}
	
	/**
	 * Builds the constraints for the *Constraint.model file
	 * @param constraints The list of constraints from cp model
	 */
	public void buildConstraintsNF(EList<ComparisonExpression> constraints)
	{
		 
		for(ComparisonExpression constraint: constraints)
		{
			buildConstraintDeclarationNF(constraint);
		}
	}
	
	/**
	 * Build the constraint declaration 
	 * @param constraint The cp constraint
	 */
	public void buildConstraintDeclarationNF(ComparisonExpression constraint)
	{
		String declaration= ""; 
		
		Expression exp1= constraint.getExp1(); 
		
		Expression exp2= constraint.getExp2(); 
		
		ComparatorEnum comparator= constraint.getComparator(); 
		
		boolean isExp1Zero= isZeroContants(exp1); 
		
		boolean isExp2Zero= isZeroContants(exp2); 
		
		boolean equality= true;  
		
		
		
		if(isExp1Zero && isExp2Zero)
		{
			logger.warn("ToLaBasedReasonerFormat - buildConstraintDeclarationNF - There two values in cero in the constraint declaration!");
			declaration= "0"; 
			
		}
		else
		{
			String exp1String= buildExpression(exp1); 
			
			String exp2String= buildExpression(exp2); 
			
			if(isExp1Zero)
			{
				declaration= exp2String; 
				if(isComparator(comparator, ComparatorEnum.LESS_THAN_VALUE) || isComparator(comparator, ComparatorEnum.LESS_OR_EQUAL_TO_VALUE))
				{
					equality= false; 
					declaration="-("+declaration+")"; 
					
				}
				
			}
			else if(isExp2Zero)
			{
				declaration= exp1String; 
				
				if(isComparator(comparator, ComparatorEnum.GREATER_THAN_VALUE) || isComparator(comparator, ComparatorEnum.GREATER_OR_EQUAL_TO_VALUE))
				{
					equality= false; 
					declaration="-("+declaration+")"; 
					
				}
			}
			else //Both are not zero
			{
				
				declaration= exp1String+"-("+exp2String+")"; 
				
				if(isComparator(comparator, ComparatorEnum.LESS_THAN_VALUE) || isComparator(comparator, ComparatorEnum.LESS_OR_EQUAL_TO_VALUE))
				{
					equality= false; 
				}
				else if(isComparator(comparator, ComparatorEnum.GREATER_THAN_VALUE) || isComparator(comparator, ComparatorEnum.GREATER_OR_EQUAL_TO_VALUE))
				{
					equality= false; 
					
					declaration= exp2String+"-("+exp1String+")"; 
					
				}
			}
			
		}
		
		declaration+=";"; 
		
		LaConstraint laConstraint= new LaConstraint(constraint.getId(), constraint.getId()+"Function", declaration); 
		
		if(equality)
		{
			equalityLaConstraints.add(laConstraint);
		}
		else
			inequalityLaConstraints.add(laConstraint); 
		
	}
	
	/**
	 * Returns a existing constraint template file
	 * @return The constraint template file
	 */
	protected File getExistingConstraintTemplateFileNF()
	{
		
		File constraintTemplate= new File(NF_CONSTRAINTS_TEMPLATE_FILE_NAME); 

		
		if(!constraintTemplate.isFile())
			constraintTemplate= new File(NF_CONSTRAINTS_TEMPLATE_FILE_NAME_JAR); 
		

		
		return constraintTemplate; 
	}
	
	public void generateCPDescriptionNF(File outputDir, String appName, String cpModelFileName)
	{
		
		
		File modelFile= new File(cpModelFileName); 
		
		if(modelFile.isFile())
		{
		
			CpPackage.eINSTANCE.eClass(); 
			TypesPackage.eINSTANCE.eClass(); 
			
			Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		    Map<String, Object> m = reg.getExtensionToFactoryMap();
		    m.put("*", new XMIResourceFactoryImpl());
			
			Resource res= ModelTool.loadModel(modelFile); 
			
			ConstraintProblem cp= (ConstraintProblem) res.getContents().get(0); 
			
	
			toFormatNF(cp, appName, outputDir);
			
			logger.info("ToLaBasedReasoner - generateCPDescriptionNF - 3 parameters - Variable and Constraint Models generated!");
		}
		else
		{
			logger.error("ToLaBasedReasoner - generateCPDescriptionNF - 3 parameters - You have to specify a valid model file!");
		}
		
		
	}
	
	
	public void generateCPDescriptionNF(File outputDir, String appId)
	{
		CDOClientExtended client= createCDOClient(); 
		
		List<EObject> models= client.getResourceContentsWithTransanction(CDO_SERVER_PATH+appId);
		
		
		if(models!=null && models.size()>1)
		{
			ConstraintProblem cp= (ConstraintProblem) models.get(1);
			
			toFormatNF(cp, appId, outputDir);
		}
		else
			logger.error("ToLaBasedReasoner - generateCPDescriptionNF - 2 parameters - The models of the application with ID "+ appId +" do not exist in the CDO repository!");
		
	}
	
	/*****
	 * 
	 * Shared functions
	 * 
	 *****/
	
	/**
	 * 
	 * @return The CDO client
	 */
	protected CDOClientExtended createCDOClient()
	{
		CpPackage.eINSTANCE.eClass();
		TypesPackage.eINSTANCE.eClass(); 
		ApplicationPackage.eINSTANCE.eClass();
		TypesPaasagePackage.eINSTANCE.eClass(); 
		
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
	    Map<String, Object> m = reg.getExtensionToFactoryMap();
	    m.put("*", new XMIResourceFactoryImpl());
		
		CDOClientExtended client= new CDOClientExtended(); 
		
		client.registerPackage(CpPackage.eINSTANCE);
		client.registerPackage(TypesPackage.eINSTANCE);
		
		client.registerPackage(ApplicationPackage.eINSTANCE);
		client.registerPackage(TypesPaasagePackage.eINSTANCE);
		
		client.registerPackage(TypePackage.eINSTANCE);
				
		client.registerPackage(CamelPackage.eINSTANCE);
		client.registerPackage(ProviderPackage.eINSTANCE);
		
		client.registerPackage(OrganisationPackage.eINSTANCE);
		
		client.registerPackage(DeploymentPackage.eINSTANCE);
		
		

		return client; 
	}
	
	
	/**
	 * Generates the variables to be created in the 
	 * Variable.model file
	 * @param variablesCP The variables defined in the cp model. 
	 * @param sol The solution
	 */
	public void buildVariables(EList<Variable> variablesCP, Solution sol)
	{
		 
		int varsId= 0; 
		
		for(Variable var: variablesCP)
		{
			VariableValue variableValue= CPModelTool.searchVariableValue(sol, var);
			buildVariableDeclaration(var, varsId, variableValue); 
			
			varsId++; 
		}
	}
	
	/**
	 * Generates the metric variables to be created in the *Variable.model file
	 * @param variables The variables defined in the cp model. 
	 * @param sol The solution
	 */
	public void buildMetricVariables(EList<MetricVariable> variables, Solution sol) {
		for(MetricVariable var: variables) {
			MetricVariableValue metricValue= CPModelTool.searchMetricValue(sol, var);
			buildMetricDeclaration(var, metricValue);
		}
	}
	
	/**
	 * Builds the variable declaration by using the LaVariable class
	 * @param var The CP model variable related to the variable being defined
	 * @param id The id of the variable 
	 * @param varValue The variable value
	 */
	public void buildVariableDeclaration(Variable var, int id, VariableValue varValue)
	{
		String name= getValidId(var.getId());
		
		logger.info("ToLaBasedReasonerFormat - buildVariableDeclaration - Creating declaration of var "+id);
		LaVariable laVar= new LaVariable(name, name, "0", false); 

		//TODO QUERY CDO TO GET VALUES FOR VARIABLES ???
		
		if(varValue!=null && varValue.getValue()!=null)
		{
			List<String> vals= CPModelTool.getValueFromNumericValue(varValue.getValue()); 
			laVar.setInitialValue(vals.get(0));
			laVar.setHasInitValue(true);
		}
		
		Domain domain= var.getDomain();
		
		if(domain instanceof NumericDomain)
		{
			NumericDomain numDomain= (NumericDomain) domain; 
			String type= getBasicTypeName(numDomain.getType()); 
			
			

			if(numDomain instanceof RangeDomain)
			{
				RangeDomain rangeDomain= (RangeDomain) numDomain; 
				laVar.setDomainType(buildDomainTypeString(RANGE_DOMAIN_TYPE, type));
				laVar.setDomainInit("{"+getStringValueFromNumericValue(rangeDomain.getFrom())+","+getStringValueFromNumericValue(rangeDomain.getTo())+"}");
				
				varsWithDomainInt.add(laVar); 
			}
			else if(numDomain instanceof NumericListDomain)
			{
				
				NumericListDomain listDomain= (NumericListDomain) numDomain; 
				laVar.setDomainType(buildDomainTypeString(NUMERIC_LIST_DOMAIN_TYPE, type));
				
				String domainInit= "{"; 
				
				for(NumericValueUpperware val: listDomain.getValues())
				{
					String valString= getStringValueFromNumericValue(val); 
					
					if(!domainInit.equals("{"))
						domainInit+=", ";

					domainInit+=valString; 
				}
				
				domainInit+="}"; 
				
				laVar.setDomainInit(domainInit);
				
				
				varsWithDomainInt.add(laVar);
			}
			else if(numDomain instanceof MultiRangeDomain)
			{
				MultiRangeDomain multiRangeDomain= (MultiRangeDomain) numDomain; 
				laVar.setDomainType(buildDomainTypeString(MULTI_RANGE_DOMAIN_TYPE, type));
				
				String domainName= laVar.getName()+MULTI_RANGE_DOMAIN_TYPE; 
				
				String domainTypeDef=  buildDomainTypeString(MULTI_RANGE_DOMAIN_TYPE, type); 
				
				String domainInit= domainTypeDef+" "+domainName+"({"; 
				
				for(RangeDomain range: multiRangeDomain.getRanges())
				{
					String rangeType= getBasicTypeName(range.getType()); 
					
					if(!domainInit.equals(domainTypeDef+" "+domainName+"({"))
						domainInit+=","; 
					
					domainInit+=buildDomainTypeString(RANGE_DOMAIN_TYPE, rangeType)+"("+getStringValueFromNumericValue(range.getFrom())+","+getStringValueFromNumericValue(range.getTo())+")"; 
				}
				
				domainInit+="})"; 
				
				laVar.setDomainInit(domainInit);
				
				varsWithMultiRangeDomain.add(laVar);
			}
			else //just int, double, float or long
			{
				
				laVar.setDomainType(type);
				varsWithoutDomainInt.add(laVar); 
			}
		}
		
		
		else if(domain instanceof ListDomain)
		{
			ListDomain listDomain= (ListDomain) domain; 
			laVar.setDomainType(LIST_DOMAIN_TYPE);
			
			String domainInit= "{"; 
			
			for(StringValueUpperware val: listDomain.getValues())
			{
				if(!domainInit.equals("{"))
					domainInit+=", "+val;
				else
					domainInit+=val; 
			}
			
			domainInit+="}"; 
			
			laVar.setDomainInit(domainInit);
			
			
			varsWithDomainInt.add(laVar);
		}
		
		else if(domain instanceof BooleanDomain)
		{
			laVar.setDomainType(BOOLEAN_TYPE);
			laVar.setHasInitValue(false);
			laVar.setInitialValue("false");
			varsWithoutDomainInt.add(laVar);
		}
		
		
	}
	
	
	/**
	 * Builds the metric declaration by using the LaVariable class
	 * @param metric The CP model metric variable related to the variable being defined
	 * @param metricValue The metric value
	 */
	public void buildMetricDeclaration(MetricVariable metric, MetricVariableValue metricValue)
	{
		String name= getValidId(metric.getId());
		
		logger.info("ToLaBasedReasonerFormat - buildMetricDeclaration - Creating declaration of metric "+name);
		LaVariable metricVar= new LaVariable(name, name); 
		
		String type= getBasicTypeName(metric.getType()); 
		
		metricVar.setDomainType(type);
		
		if(metricValue!=null && metricValue.getValue()!=null)
		{
			List<String> infos= CPModelTool.getValueFromNumericValue(metricValue.getValue()); 
			
			double val= Double.parseDouble(infos.get(0)); 
			
			if(val!=-1)
			{
				metricVar.setInitialValue(infos.get(0));
			}
			else
			{
				//TODO Call the database to get an initial value
			}
			
		}
		else
		{
			//TODO Call the database to get an initial value
		}
		
		metricVars.add(metricVar); 
		
	}
	
	
	/**
	 * Returns the basic type name related to the given enumeration
	 * @param enumeration The enumeration
	 * @return The C++ basic typer name
	 */
	protected String getBasicTypeName(BasicTypeEnum enumeration)
	{
		String type= INT_TYPE; 
		
		if(enumeration.getValue()==BasicTypeEnum.DOUBLE_VALUE)
		{
			type= DOUBLE_TYPE;
		}
		else if(enumeration.getValue()==BasicTypeEnum.FLOAT_VALUE)
		{
			type= FLOAT_TYPE;
		}
		else if(enumeration.getValue()==BasicTypeEnum.LONG_VALUE)
		{
			type= LONG_TYPE;
		}

		return type; 
	}
	
	/**
	 * Returns the string representaining the domain type in LABasedReasoner
	 * @param domainType The domain type
	 * @param basicType The basic type related to the domain type
	 * @return domainType"&lt;basicType&gt;
	 */
	protected String buildDomainTypeString(String domainType, String basicType)
	{
		return domainType+"<"+basicType+">"; 
	}
	
	/**
	 * Return the value of the specified numeric value as a string
	 * @param val The numericValue
	 * @return The value of the numeric value as a string
	 */
	protected String getStringValueFromNumericValue(NumericValueUpperware val)
	{
		String valString=""; 
		
		
		if(val instanceof IntegerValueUpperware)
		{
			IntegerValueUpperware intVal= (IntegerValueUpperware) val; 
			
			valString+= intVal.getValue(); 
		}
		else if(val instanceof FloatValueUpperware)
		{
			FloatValueUpperware floatVal= (FloatValueUpperware) val; 
			
			valString+= floatVal.getValue(); 
		}
		else if(val instanceof LongValueUpperware)
		{
			LongValueUpperware longVal= (LongValueUpperware) val; 
			
			valString+= longVal.getValue(); 
		}
		else
		{
			DoubleValueUpperware doubleVal= (DoubleValueUpperware) val; 
			
			valString+=doubleVal.getValue(); 
		}
		
		return valString; 
		
	}
	
	
	/**
	 * Returns a existing variable template file
	 * @return The variable template file
	 */
	protected File getExistingVariableTemplateFile()
	{
		
		File variablesTemplate= new File(VARIABLES_TEMPLATE_FILE_NAME); 

		
		if(!variablesTemplate.isFile())
			variablesTemplate= new File(VARIABLES_TEMPLATE_FILE_NAME_JAR); 
		

		
		return variablesTemplate; 
	}
				
	protected boolean isZeroContants(Expression exp)
	{
		if(exp instanceof Constant)
		{
			Constant c= (Constant) exp; 
			
			return isZeroValue(c.getValue()); 
		}
		
		return false;
	}
	
	private boolean isZeroValue(NumericValueUpperware value) 
	{
		if(value instanceof IntegerValueUpperware)
		{
			IntegerValueUpperware integerVal= (IntegerValueUpperware) value;  
			
			return integerVal.getValue()==0; 
		}
		else if(value instanceof LongValueUpperware)
		{
			LongValueUpperware longVal= (LongValueUpperware) value;  
			
			return longVal.getValue()==0; 
		}
		else if(value instanceof FloatValueUpperware)
		{
			FloatValueUpperware floatVal= (FloatValueUpperware) value;  
			
			return floatVal.getValue()==0; 
		}
		else if (value instanceof DoubleValueUpperware)
		{
			DoubleValueUpperware doubleVal= (DoubleValueUpperware) value;  
			
			return doubleVal.getValue()==0; 
		}
		
		return false;
	}

	/**
	 * Builds the declaration of an expression for LABasedReasoner
	 * @param exp The cp expression
	 * @return A string representing the expression
	 */
	protected String buildExpression(Expression exp)
	{
		String expString= ""; 
		
		if(exp instanceof ComparisonExpression)
		{
			expString= buildConstraintDeclaration((ComparisonExpression) exp); 
		}
		else if(exp instanceof NumericExpression)
		{
			 NumericExpression ne= (NumericExpression) exp; 
			 
			 expString= buildNumericExpression(ne); 
		}
		
		
		return expString; 
		
	}
	
	/**
	 * Builds the string representing a simple unary expression 
	 * @param sue The simple unary expression
	 * @return The string representing the expression
	 */
	protected String buildSimpleUnaryExpressionValue(SimpleUnaryExpression sue)
	{
		String expString= "log("; 
		String ne= buildNumericExpression(sue.getExpression()); 
		if(sue.getOperator().getValue()==SimpleUnaryOperatorEnum.ABSTRACT_VALUE_VALUE)
		 {
			 expString= ne+" * (("+ne+">0) - ("+ne+"<0))"; 
		 }
		else
		{
			expString+=ne+")"; 
		}
		
		return expString; 
	}
	
	/**
	 * Builds the string representing the numeric expression
	 * @param numExp The numeric expression 
	 * @return the string representing the expression
	 */
	protected String buildNumericExpression(NumericExpression numExp)
	{
		String expString=""; 
		
		if(numExp instanceof Variable)
		{
			expString= buildVariableValue((Variable) numExp); 
		}
		else if (numExp instanceof Constant)
		{
			expString= buildConstantValue((Constant) numExp); 
		}
		else if (numExp instanceof MetricVariable)
		{
			expString= buildMetricValue((MetricVariable) numExp); 
		}
		else if(numExp instanceof SimpleUnaryExpression)
		{
			expString= buildSimpleUnaryExpressionValue((SimpleUnaryExpression) numExp); 
		}
		else if(numExp instanceof ComposedUnaryExpression)
		{
			expString= buildComposedUnaryExpressionValue((ComposedUnaryExpression) numExp); 
		}
		else if(numExp instanceof ComposedExpression)
		{
			expString= buildComposedExpressionValue((ComposedExpression) numExp); 
		}
		
		return expString; 
	}
	
	/**
	 * Builds the string representing the composed unary expression
	 * @param exp The composed unary expression 
	 * @return the string representing the expression
	 */
	protected String buildComposedUnaryExpressionValue(ComposedUnaryExpression exp)
	{
		String expString="pow("; 
		
		String ne= buildNumericExpression(exp.getExpression()); 
		
		if(exp.getOperator().getValue()==ComposedUnaryOperatorEnum.LOG_VALUE_VALUE) //TODO ONLY LOG10 supported, #include <math.h> 
		{
			expString= "log10("+ne+")"; 
		}
		else
		{
			expString+=ne+","+exp.getValue()+")"; 
		}
		
		return expString; 
	}
	
	/**
	 * Builds the string representing the numeric expression
	 * @param exp The composed expression 
	 * @return the string representing the expression
	 */
	protected String buildComposedExpressionValue(ComposedExpression exp)
	{
		String expString=""; 
		
		String op= getOperatorString(exp.getOperator()); 
		
		
		for(NumericExpression numExp:exp.getExpressions())
		{
			if(!expString.equals(""))
				expString+=op; 
			
			String numExpVal= buildNumericExpression(numExp); 
			
			expString+="("+numExpVal+")"; 
		}
		
		
		return expString; 
	}
	
	/**
	 * Builds the string representing the constant
	 * @param constant The constant
	 * @return the string representing the constant
	 */
	protected String buildConstantValue(Constant constant)
	{
		return getStringValueFromNumericValue(constant.getValue()); 
	}
	
	
	/**
	 * Builds the string to retrieve a variable value
	 * @param var The variable
	 * @return the expression to retrieve the variable value
	 */
	protected String buildVariableValue(Variable var)
	{
		return getValidId(var.getId())+".Value()"; 
	}
	
	
	/**
	 * Builds the string to retrieve a metric variable value
	 * @param var The  metric variable
	 * @return the expression to retrieve the metric variable value
	 */
	protected String buildMetricValue(MetricVariable var)
	{
		return getValidId(var.getId())+".Value()"; 
	}
	
	/**
	 * Returns C++ string representing the comparator operator
	 * @param comparator The comparator
	 * @return The string representing the comparator operator
	 */
	protected String getComparatorString(ComparatorEnum comparator)
	{
		String cs= EQUAL_TO; 
		
		if(comparator.getValue()==ComparatorEnum.DIFFERENT_VALUE)
		{
			return DIFFERENT; 
		}
		else if(comparator.getValue()==ComparatorEnum.GREATER_OR_EQUAL_TO_VALUE)
		{
			return GREATER_THAN_OR_EQUAL_TO; 
		}
		else if(comparator.getValue()==ComparatorEnum.GREATER_THAN_VALUE)
		{
			return GREATER_THAN; 
		}
		else if(comparator.getValue()==ComparatorEnum.LESS_OR_EQUAL_TO_VALUE)
		{
			return LESS_THAN_OR_EQUAL_TO; 
		}
		else if(comparator.getValue()==ComparatorEnum.LESS_THAN_VALUE)
		{
			return LESS_THAN; 
		}	
			
		return cs; 	
	}
	
	/**
	 * Returns C++ string representing the comparator operator
	 * @param comparator The comparator
	 * @param referenceComparator Reference comparator
	 * @return The string representing the comparator operator
	 */
	protected boolean isComparator(ComparatorEnum comparator, int referenceComparator)
	{
		return comparator.getValue()==referenceComparator; 
		
	}
	
	/**
	 * Return the C++ string representing the operator 
	 * @param op The operator
	 * @return The string representing the operator
	 */
	protected String getOperatorString(OperatorEnum op)
	{
		String opString= "+"; 
		
		if(op.getValue()== OperatorEnum.DIV_VALUE)
		{
			opString="/"; 
		}
		else if(op.getValue()== OperatorEnum.MINUS_VALUE)
		{
			opString= "-"; 
		}
		else if(op.getValue()== OperatorEnum.TIMES_VALUE)
		{
			opString= "*"; 
		}
		
		return opString; 
	}

	/*****
	 * 
	 * Objective function
	 * 
	 *****/	
	
	/**
	 * Creates the objective function
	 * @param ne The numeric expression
	 * @param goalType The goal type
	 */
	public void buildObjectiveFunction(NumericExpression ne, GoalOperatorEnum goalType)
	{
		objectiveFunctionExp= buildExpression(ne); 
		
		if(goalType.getValue()==GoalOperatorEnum.MAX_VALUE)
			objectiveFunctionExp= "(-1)*"+objectiveFunctionExp; 
		
		
	}
	
	
	/*****
	 * 
	 * Value assignment  
	 * 
	 *****/
	
	/**
	 * Assigns values
	 * @param fileName The file containing values
	 * @param appName The application name
	 */
	public void assignValues(String fileName, String appName)
	{
		File assigmentsFile= new File(fileName); 
		
		if(assigmentsFile.isFile())
		{
			
			CDOClientExtended client= createCDOClient(); 
			
			CDOTransaction trans= client.openTransaction();
			
			Resource res= trans.getResource(CDO_SERVER_PATH+appName); 
			
			ConstraintProblem cp= (ConstraintProblem) res.getContents().get(1); 
			
			Solution sol= CPModelTool.searchLastSolution(cp.getSolution()); //TODO CREATE THE SOLUTION??
			
			if(sol==null)
			{
				sol= CPModelTool.createSolution(cp);
			}
			
			try {
				BufferedReader br= new BufferedReader(new FileReader(assigmentsFile));
				
				String line= br.readLine(); 
				
				while(line!=null)
				{
					
					if(!line.startsWith("#"))
					{	
					
						String[] infos= line.split(" "); //Label and value
						
						if(infos.length==2)
						{
							String originalId= getOriginalId(infos[0]);
							Variable var= searchVariableByName(cp, originalId);
							
							logger.info("ToLaBasedReasonerFormat - assignValues - The variable "+originalId+" will have value "+infos[1]);
							
							if(var!=null)
							{	
								CPModelTool.assignNumericValue(infos[1], var, sol);
							}
							else
							{
								logger.warn("ToLaBasedReasonerFormat - assignValues - The variable "+originalId+" does not exist in the model!");
							}
							
						}
						else
							logger.warn("ToLaBasedReasonerFormat - assignValues - The format of the file "+fileName+" is not correct!");
						
						
					}	
					
					line= br.readLine(); 
					
					
				}
				
				trans.commit();
				
				trans.close();
				
				br.close();
				
				
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			} catch (ConcurrentAccessException e) {
				
				e.printStackTrace();
			} catch (CommitException e) {
				
				e.printStackTrace();
			} 
			
			
		}
		else
			logger.error("ToLaBasedReasonerFormat - assignValues - The file "+fileName+" does not exist. The values can not be assigned!");
		
		
		
	}
	
	

	
	/**
	 * Search a variable with the given name
	 * @param cp The model to search
	 * @param name The variable name
	 * @return The variable or null if it does not exist
	 */
	public Variable searchVariableByName(ConstraintProblem cp, String name)
	{
		for(Variable v: cp.getVariables())
		{
			logger.info("ToLaBasedReasonerFormat - searchVariableByName - VarName "+v.getId());
			if(v.getId().equals(name))
				return v; 
		}
		
		return null; 
	}
	
	/**
	 * Generates a valid id for C++ variables
	 * @param originalId The orinal id
	 * @return All the occurrences of "-" are "__". All the occurrences of "/" are "___" 
	 */
	protected String getValidId(String originalId)
	{
		String validId= originalId;
		
		if(validId.contains("-"))
		{
			validId=validId.replaceAll("-", "__");
		}
		
		if(validId.contains("/"))
		{
			validId=validId.replaceAll("/", "___");
		}
		
		
		return validId;
	}
	
	protected String getOriginalId(String validId)
	{
		String originalId= validId;
		
		if(originalId.contains("___"))
		{
			originalId= originalId.replaceAll("___", "/");
		}
		
		if(originalId.contains("__"))
		{
			originalId= originalId.replaceAll("__", "-");
		}
		
		
		
		return originalId;
	}
	

		
	/**
	 * Main method
	 * @param args[0] Type of service - Generation (0) or assignment (1)
	 * If (type == 0)
	 *	 @param args[1] output directory
	 * 	 @param args[2] application name
	 * 	 @param args[3] cp model file name
	 * else
	 * 	@param args[1] file name- The file containing the assignments following the format "VarName Value"  
	 * 	@param args[2] application name- In the CDO server
	 */
	public static void main(String[] args)
	{
		
		
		if(args.length>=3)
		{
			int typeService= Integer.parseInt(args[0]); 
			
			if(typeService==GENERATION)
			{	
			
				File outDir= new File(args[1]); 
				
				if(outDir.isDirectory())
				{
					
					
					String appName=args[2];
					
					if(!appName.trim().equals(""))
					{
						
						ToLaBasedReasonerFormat converter= new ToLaBasedReasonerFormat();
						
						if(args.length==3)
							converter.generateCPDescriptionNF(outDir, appName);
						else
						{
							String modelFile = args[3]; 
							
							converter.generateCPDescriptionNF(outDir, appName, modelFile);
						}					
					}
					else
					{
						logger.error("ToLaBasedReasoner - main - You have to specify a valid name/id for the application!");
					}
					
					
				}
				else
				{
					logger.error("ToLaBasedReasoner - main - You have to specify a valid output directory!");
				}
			}
			else
			{
				ToLaBasedReasonerFormat converter= new ToLaBasedReasonerFormat();
				
				converter.assignValues(args[1], args[2]);
			}

		}
		else
		{
			logger.error("ToLaBasedReasoner - main - You have to specify at least the service type, the output directory and the name/id of the application"); // and the xmi model path");
		}
		
		System.exit(0);

	}
}
