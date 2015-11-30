/*
 * Copyright (c) 2014-2016 UK Science and Technology Facilities Council
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.metasolver.util;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import eu.paasage.upperware.metamodel.application.ApplicationPackage;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.CpFactory;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.cp.MetricVariable;
import eu.paasage.upperware.metamodel.cp.MetricVariableValue;
import eu.paasage.upperware.metamodel.cp.Solution;
import eu.paasage.upperware.metamodel.types.BasicTypeEnum;
import eu.paasage.upperware.metamodel.types.DoubleValueUpperware;
import eu.paasage.upperware.metamodel.types.FloatValueUpperware;
import eu.paasage.upperware.metamodel.types.IntegerValueUpperware;
import eu.paasage.upperware.metamodel.types.LongValueUpperware;
import eu.paasage.upperware.metamodel.types.NumericValueUpperware;
import eu.paasage.upperware.metamodel.types.TypesFactory;
import eu.paasage.upperware.metamodel.types.TypesPackage;
import eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage;
import fr.inria.paasage.saloon.camel.ontology.OntologyPackage;

/**
 * A utiltiy to load a CP model from an xmi file.
 * <p> 
 * @author Shirley Crompton 
 * org UK Science and Technology Facilities Council
 */
public final class CpModelTool {
	/** log4j message logger */
	protected static Logger log = Logger.getLogger(CpModelTool.class);

	/**
	 * Initialise the model
	 */
	public static void init() {

		log.debug("initialising model ....");
		// initialise the Upperware model packages
		ApplicationPackage.eINSTANCE.eClass();
		TypesPaasagePackage.eINSTANCE.eClass(); 
		TypesPackage.eINSTANCE.eClass(); 
		CpPackage.eINSTANCE.eClass();
		OntologyPackage.eINSTANCE.eClass();
		
		
		
		// Register the XMI resource factory for the .xmi extension
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*",
				new XMIResourceFactoryImpl());
		
	}
	

	/**
	 * Load the provided xmi file
	 * <p>
	 * 
	 * @param cpModelFilePath
	 *            path to the xmi file
	 * @return the {@link org.eclipse.emf.ecore.resource.Resource
	 *         <em>Resource</em>} representation of the model or a null object
	 *         if in error
	 */
	public static Resource loadFile(String cpModelFilePath) {
		init();
		// File file = new File(cpModelFilePath);
		// uri = URI.createURI(file.getAbsolutePath()); //got Malform URI error
		// if using absolute path
		URI uri = URI.createURI(cpModelFilePath);
		// debug
		log.debug("the file uri :" + cpModelFilePath);
		// get a new resource set
		ResourceSet resSet = new ResourceSetImpl();
		// load the cpModel xmi file
		Resource resource = resSet.getResource(uri, true);
		EcoreUtil.resolveAll(resSet);
		try {
			resource.load(null);
			for (Resource.Diagnostic diagnostic : resource.getWarnings()) {
				// log the issues
				log.warn("loading resource(" + cpModelFilePath
						+ ") produced warning : " + diagnostic.toString());
			}
			for (Resource.Diagnostic error : resource.getErrors()) {
				// log the errors
				log.error("loading resource(" + cpModelFilePath
						+ ") produced error : " + error.toString());
			}
		} catch (IOException ioe) {
			log.error("loading resource(" + cpModelFilePath
					+ ") caused IOException: " + ioe.getMessage());
		} catch (Exception e) {
			log.error("loading resource(" + cpModelFilePath
					+ ") caused Exception: " + e.getMessage());
		}
		return resource;

	}

	/**
	 * Extract the {@link eu.paasage.upperware.metamodel.cp.ConstraintProblem
	 * <em>ConstraintProblem</em>}
	 * <p>
	 * 
	 * @param res
	 *            the {@link org.eclipse.emf.ecore.resource.Resource
	 *            <em>Resource</em>} representation of the CP model
	 * @return the extracted
	 *         {@link eu.paasage.upperware.metamodel.cp.ConstraintProblem
	 *         <em>ConstraintProblem</em>} or null if in error
	 */
	public static ConstraintProblem getCP(Resource res) {
		ConstraintProblem cp = null;
		// get the config
		List<EObject> its = res.getContents();
		for (EObject obj : its) {
			// debug
			log.debug("Current object from the resource is: "
					+ obj.getClass().getName());
			// if(obj instanceof PaasageConfiguration){
			// PaasageConfiguration config = (PaasageConfiguration) obj;
			// log.debug("found config(" + config.getId() + ")...");
			// }else
			if (obj instanceof ConstraintProblem) {
				cp = (ConstraintProblem) obj;
				log.debug("found cp problem with " + cp.getSolution().size()
						+ " solution/s...");
			}
		}
		return cp;
	}

	/**
	 * Extract the
	 * {@link eu.paasage.upperware.metamodel.application.PaasageConfiguration
	 * <em>PaasageConfiguration</em>}
	 * <p>
	 * 
	 * @param res
	 *            the {@link org.eclipse.emf.ecore.resource.Resource
	 *            <em>Resource</em>} representation of the CP model
	 * @return the extracted
	 *         {@link eu.paasage.upperware.metamodel.application.PaasageConfiguration
	 *         <em>PaasageConfiguration</em>} or null if in error
	 */
	public static PaasageConfiguration getAppConfig(Resource res) {
		PaasageConfiguration config = null;
		// get the config
		List<EObject> its = res.getContents();
		for (EObject obj : its) {
			// debug
			log.debug("Current object from the resource is: "
					+ obj.getClass().getName());
			if (obj instanceof PaasageConfiguration) {
				config = (PaasageConfiguration) obj;
				log.debug("found config(" + config.getId() + ")...");
				// }else
				// if(obj instanceof ConstraintProblem){
				// cp = (ConstraintProblem) obj;
				// log.debug("found cp problem with " + cp.getSolution().size()
				// + " solution/s...");
				// }
			}
		}
		return config;
	}
	/**
	 * Extract the {@link eu.paasage.upperware.metamodel.application.PaasageConfiguration <em>PaasageConfiguration</em>}
	 * entity from the provided model.
	 * <p>
	 * @param model
	 *            the resource model represented by a {@link java.util.List
	 *            <em>List</em>} of {@link org.eclipse.emf.ecore.EObject
	 *            <em>EObject</em>}
	 * @return	the extracted {@link eu.paasage.upperware.metamodel.application.PaasageConfiguration <em>PaasageConfiguration</em>}
	 * 			entity or null
	 */
	public static PaasageConfiguration getAppConfig(List<EObject> model){
		PaasageConfiguration config = null;
		if (model.isEmpty()) {
			log.error("Cannot get cofiguration from an empty model .....");
			return config;
		}
		try {
			for (EObject obj : model) {
				if (obj instanceof eu.paasage.upperware.metamodel.application.impl.PaasageConfigurationImpl) {
					config = (PaasageConfiguration) obj;
					break;
				}
			}
		} catch (Exception e) {
			log.error("Error getting paasage configuration from the EObject list : "
					+ e.getMessage());
		}
		return config;
	}
	/**
	 * Extract the {@link eu.paasage.upperware.metamodel.cp.ConstraintProblem
	 * <em>ConstraintProblem</em>} entity from the provided model.
	 * <p>
	 * @param model
	 *            the resource model represented by a {@link java.util.List
	 *            <em>List</em>} of {@link org.eclipse.emf.ecore.EObject
	 *            <em>EObject</em>}
	 * @return a populated
	 *         {@link eu.paasage.upperware.metamodel.cp.ConstraintProblem
	 *         <em>ConstraintProblem</em>} object if successful, else an empty
	 *         {@link eu.paasage.upperware.metamodel.cp.ConstraintProblem
	 *         <em>ConstraintProblem</em>} object or null.
	 */
	public static ConstraintProblem getCPModel(List<EObject> model) {
		ConstraintProblem cpModel = null;
		if (model.isEmpty()) {
			log.error("Cannot get CPModel from an empty model .....");
			return cpModel;
		}
		try {
			for (EObject obj : model) {
				if (obj instanceof eu.paasage.upperware.metamodel.cp.impl.ConstraintProblemImpl) {
					log.debug("found constraint problem....");
					cpModel = (ConstraintProblem) obj;
					break;
				}
			}
		} catch (Exception e) {
			log.error("Error getting CP Model from the EObject list : "
					+ e.getMessage());
		}
		return cpModel;
	}
	/**
	 * Extract the identifier from the provided model.
	 * <p>
	 * @param model
	 *            the resource model represented by a {@link java.util.List
	 *            <em>List</em>} of {@link org.eclipse.emf.ecore.EObject
	 *            <em>EObject</em>}
	 * @return	the identifier {@link java.lang.String <em>String</em>} or null.
	 */
	public static String getAppId(List<EObject> model){
		PaasageConfiguration config = getAppConfig(model);
		if(config != null){
			return config.getId();
		}else{
			return null;
		}
	}
	/**
	 * Get a identifier for the clone CP model by stripping the numeric suffix from the original
	 * resId and then add 1 to it.  This method assumes that the resId is based on the paasage configuration id.
	 * <p>
	 * @param appId		the {@link eu.paasage.upperware.metamodel.application.PaasageConfiguration <em>PaasageConfiguration</em>} identifier
	 * @param resId		the CP model resource path identifier in the CDO server
	 * @return	a new resource path identifier for storing the model
	 */
	public static String getCloneId(String appId, String resId){
		//this is for the cdo resource path, not the actual paasageConfiguration.id
		String cloneId = "";
		//e.g. appId = openFoam1448464846493 and resId = openFoam1448464846493V1, suffix = V1
		String suffix = resId.substring(appId.length()); 
		if(suffix != null && !suffix.isEmpty()){
			//find the last portion which is only number, e.g. 1
			for(int i = suffix.length()-1; i >=0; i--){
				char character = suffix.charAt(i);
				log.debug("suffix char at " + i + " is " + suffix.charAt(i));
				if(Character.isLetter(character) || character == '_'){
					//stop here
					log.debug("The numeric part of the suffix is : " + suffix.substring(i+1));
					int version = Integer.parseInt(suffix.substring(i+1))+1;
					log.debug("the version is : " + version);
					cloneId = appId + suffix.substring(0, i+1) + version;
					log.debug("the computed cloneId is " + cloneId);
					break;
				}
			}
		}else{
			//the two Strings are the same
			cloneId = appId + "v1";
		}
		return cloneId;
	}

	/**
	 * Set a constant metric variable value to the metric variable in the
	 * solution.
	 * <p>
	 * @param mv
	 *            the target
	 *            {@link eu.paasage.upperware.metamodel.cp.MetricVariable
	 *            <em>MetricVariable</em>}
	 * @param solution
	 *            the target {@link eu.paasage.upperware.metamodel.cp.Solution
	 *            <em>Solution</em>} object
	 * @return	the updated {@link eu.paasage.upperware.metamodel.cp.Solution
	 *            <em>Solution</em>} object
	 */
	public static Solution setConstantValue(MetricVariable mv, Solution solution) {
		// debug
		log.debug("adding a constant MetricVariableValue of 1 to MetricVariable("
				+ mv.getId()
				+ ") in Solution("
				+ solution.getTimestamp()
				+ ")....");

		// metricVariable can be of type: int, double, float, long
		String constantValue = "";
		if(mv.getType() == BasicTypeEnum.INTEGER){
			constantValue = Integer.toString(1);
		}else if(mv.getType() == BasicTypeEnum.DOUBLE){
			constantValue = Double.toString(1.0);
		}else if(mv.getType() == BasicTypeEnum.LONG){
			constantValue = Long.toString(1l);
		}else{
			constantValue = Float.toString(1f);
		}			
		MetricVariableValue value = createMVV(mv, constantValue);
		
//		DoubleValueUpperware defaultValue = TypesFactory.eINSTANCE
//				.createDoubleValueUpperware();
//		defaultValue.setValue(1);
//		//
//		MetricVariableValue value = CpFactory.eINSTANCE
//				.createMetricVariableValue();
//		value.setVariable(mv);
//		value.setValue(defaultValue);
		solution.getMetricVariableValue().add(value);
		//debug
		System.out.println("the solution now has : " + solution.getMetricVariableValue().size() + " metricVariableValues");
		return solution;
	}

	/**
	 * Make a copy of the provided solution. Update its timestamp to current.
	 * <p>
	 * 
	 * @param sol
	 *            the original
	 *            {@link eu.paasage.upperware.metamodel.cp.Solution
	 *            <em>Solution</em>} object
	 * @return the copied the target
	 *         {@link eu.paasage.upperware.metamodel.cp.Solution
	 *         <em>Solution</em>} object
	 */
	public static Solution copySolution(Solution sol) {

		Solution newSol = EcoreUtil.copy(sol); // not sure if this works????
		newSol.setTimestamp(System.currentTimeMillis());
		return newSol;

	}

	/**
	 * Validate if the provided metric variable id exists in the CP model.
	 * <p>
	 * 
	 * @param id
	 *            name of the metric variable
	 * @param list
	 *            a {@link java.util.List <em>List</em>} of the
	 *            {@link eu.paasage.upperware.metamodel.cp.MetricVariable
	 *            <em>MetricVariable</em>} in the CP model.
	 * @return true if exists, else false
	 */
	public static boolean metricVariableExists(String id, List<MetricVariable> list) {
		//
		for (MetricVariable mv : list) {
			if (mv.getId().equals(id)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Get the {@link eu.paasage.upperware.metamodel.cp.MetricVariable
	 * <em>MetricVariable</em>} from the CP model by the provided ID.
	 * <p>
	 * 
	 * @param id
	 *            name of the metric variable
	 * @param list
	 *            a {@link java.util.List <em>List</em>} of the
	 *            {@link eu.paasage.upperware.metamodel.cp.MetricVariable
	 *            <em>MetricVariable</em>} in the CP model.
	 * @return the target
	 *         {@link eu.paasage.upperware.metamodel.cp.MetricVariable
	 *         <em>MetricVariable</em>} or null if not found.
	 */
	public static MetricVariable getMetricVariable(String id,
			List<MetricVariable> list) {
		//
		//
		for (MetricVariable mv : list) {
			if (mv.getId().equals(id)) {
				return mv;
			}
		}
		return null;
	}
	/**
	 * Create a {@link eu.paasage.upperware.metamodel.cp.MetricVariableValue
	 * <em>MetricVariableValue</em>} for the provided
	 * {@link eu.paasage.upperware.metamodel.cp.MetricVariable
	 * <em>MetricVariable</em>} object.
	 * <p> 
	 * @param current
	 *            the target
	 *            {@link eu.paasage.upperware.metamodel.cp.MetricVariable
	 *            <em>MetricVariable</em>} object.
	 * @param value  the target value to set
	 * @return the generated
	 *         {@link eu.paasage.upperware.metamodel.cp.MetricVariableValue
	 *         <em>MetricVariableValue</em>} object.
	 */
	public static MetricVariableValue createMVV(MetricVariable current, NumericValueUpperware value){		
		//
		MetricVariableValue mvv = CpFactory.eINSTANCE
				.createMetricVariableValue();
		mvv.setVariable(current);
		//debug
		log.debug("createMVV : the mvv variable: " + mvv.getVariable().getId());
		// int, double, float or long	
		if(value instanceof DoubleValueUpperware){
			DoubleValueUpperware doubleValue = TypesFactory.eINSTANCE
					.createDoubleValueUpperware();
			doubleValue.setValue(((DoubleValueUpperware) value).getValue());
			mvv.setValue(doubleValue);
		}else if(value instanceof FloatValueUpperware){
			FloatValueUpperware floatValue = TypesFactory.eINSTANCE
					.createFloatValueUpperware();
			floatValue.setValue(((FloatValueUpperware) value).getValue());
			mvv.setValue(floatValue);
		}else if(value instanceof LongValueUpperware){
			LongValueUpperware longValue = TypesFactory.eINSTANCE
					.createLongValueUpperware();
			longValue.setValue(((LongValueUpperware) value).getValue());			
			mvv.setValue(longValue);
		}else{//default integer
			IntegerValueUpperware intValue = TypesFactory.eINSTANCE
					.createIntegerValueUpperware();
			intValue.setValue(((IntegerValueUpperware) value).getValue());
			mvv.setValue(intValue);
		}		
		return mvv;		
	}

	/**
	 * Create a {@link eu.paasage.upperware.metamodel.cp.MetricVariableValue
	 * <em>MetricVariableValue</em>} for the provided
	 * {@link eu.paasage.upperware.metamodel.cp.MetricVariable
	 * <em>MetricVariable</em>} object.
	 * <p> 
	 * @param current
	 *            the target
	 *            {@link eu.paasage.upperware.metamodel.cp.MetricVariable
	 *            <em>MetricVariable</em>} object.
	 * @param string
	 *            {@link java.lang.String <em>String</em>} representation of the
	 *            value
	 * @return the generated
	 *         {@link eu.paasage.upperware.metamodel.cp.MetricVariableValue
	 *         <em>MetricVariableValue</em>} object.
	 * @throws {@link java.lang.NumberFormatException
	 *         <em>NumberFormatException</em>} on error of parsing the input
	 *         {@link java.lang.String <em>String</em>} value
	 */
	public static MetricVariableValue createMVV(MetricVariable current, String string)
			throws NumberFormatException {
		//
		MetricVariableValue mvv = CpFactory.eINSTANCE
				.createMetricVariableValue();
		mvv.setVariable(current);
		//debug
		log.debug("createMVV : the mvv variable: " + mvv.getVariable().getId());
		// int, double, float or long
		BasicTypeEnum type = current.getType();
		switch (type) {
		case DOUBLE:
			DoubleValueUpperware doubleValue = TypesFactory.eINSTANCE
					.createDoubleValueUpperware();
			doubleValue.setValue(Double.parseDouble(string));
			mvv.setValue(doubleValue);
			break;
		case FLOAT:
			FloatValueUpperware floatValue = TypesFactory.eINSTANCE
					.createFloatValueUpperware();
			floatValue.setValue(Float.parseFloat(string));
			mvv.setValue(floatValue);
			break;
		case LONG:
			LongValueUpperware longValue = TypesFactory.eINSTANCE
					.createLongValueUpperware();
			longValue.setValue(Long.parseLong(string));			
			mvv.setValue(longValue);
			break;
		default: // integer
			IntegerValueUpperware intValue = TypesFactory.eINSTANCE
					.createIntegerValueUpperware();
			intValue.setValue(Integer.parseInt(string));
			mvv.setValue(intValue);
			break;
		}
		return mvv;
	}
	/**
	 * Update a {@link eu.paasage.upperware.metamodel.cp.MetricVariableValue
	 * <em>MetricVariableValue</em>} object with the provided {@link java.lang.String <em>String</em>} 
	 * representation of the value
	 * <p>
	 * @param mvv	the {@link eu.paasage.upperware.metamodel.cp.MetricVariableValue <em>MetricVariableValue</em>} to update
	 * @param type	the {@link eu.paasage.upperware.metamodel.types.BasicTypeEnum <em>BasicTypeEnum</em>} indicating the intended format of value object
	 * @param value	 {@link java.lang.String <em>String</em>} representation of the
	 *            value
	 * @return	the updated
	 *         {@link eu.paasage.upperware.metamodel.cp.MetricVariableValue
	 *         <em>MetricVariableValue</em>} object.
	 * @throws {@link java.lang.NumberFormatException
	 *         <em>NumberFormatException</em>} on error of parsing the input
	 *         {@link java.lang.String <em>String</em>} value
	 */
	public static MetricVariableValue updateMetricVariableValue(MetricVariableValue mvv, BasicTypeEnum type, String value) throws NumberFormatException{
		// int, double, float or long
		switch (type) {
		case DOUBLE:
			DoubleValueUpperware doubleValue = TypesFactory.eINSTANCE
					.createDoubleValueUpperware();
			doubleValue.setValue(Double.parseDouble(value));
			mvv.setValue(doubleValue);
			break;
		case FLOAT:
			FloatValueUpperware floatValue = TypesFactory.eINSTANCE
					.createFloatValueUpperware();
			floatValue.setValue(Float.parseFloat(value));
			mvv.setValue(floatValue);
			break;
		case LONG:
			LongValueUpperware longValue = TypesFactory.eINSTANCE
					.createLongValueUpperware();
			longValue.setValue(Long.parseLong(value));
			mvv.setValue(longValue);
			break;
		default: // integer
			IntegerValueUpperware intValue = TypesFactory.eINSTANCE
					.createIntegerValueUpperware();
			intValue.setValue(Integer.parseInt(value));
			mvv.setValue(intValue);
			break;
		}
		return mvv;
	}
	////////////////////////private methods//////////////////////
	


}
