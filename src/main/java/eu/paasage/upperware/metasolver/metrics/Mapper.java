/*
 * Copyright (c) 2014-2016 UK Science and Technology Facilities Council
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.metasolver.metrics;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.eclipsesource.json.JsonObject;



//import eu.paasage.upperware.cp.cloner.CDOClientExtended;
//import eu.paasage.upperware.cp.cloner.CPCloner;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.MetricVariable;
import eu.paasage.upperware.metamodel.cp.MetricVariableValue;
import eu.paasage.upperware.metamodel.cp.Solution;
import eu.paasage.upperware.metasolver.exception.MetricMapperException;
import eu.paasage.upperware.metasolver.util.CdoTool;
import eu.paasage.upperware.metasolver.util.CpModelTool;
import eu.paasage.upperware.profiler.cp.generator.model.tools.CPModelTool;

/**
 * The metrics Mapper prepares a CP model for submission to the solver. It
 * retrieves the CP model associated with the cloud application managed by
 * PaaSage from the Upperware CDO server and updates each
 * application&#45;specific metric variable with a default value or the current
 * value returned by the PaaaSage metricsCollector component. The metric
 * variables are contained in CP Solution entities.
 * <p>
 * @author Shirley Crompton 
 * org UK Science and Technology Facilities Council
 */
public class Mapper {

	/** log4j logger */
	protected static Logger log = Logger.getLogger(Mapper.class);
	/** the CP Model resource id */
	private String cpModelId;
	/** CPCloner for cloning existing CDO resources 
	private CPCloner cpCloner;
	/** the extended CDO client provided by CPCloner
	private CDOClientExtended xCdoClient; // currently no credentials. If require credentials, need to extend it 
	*/
	/** CDO utilties */
	private CdoTool utils;

	/**
	 * Construct an instance
	 */
	public Mapper() {
	}
	
	/**
	 * Construct an instance with the target CP Model resource id
	 
	public Mapper(String resId) {
		// I think this may change at runtime as rule processor clones the cp_model
		// rather than updating it. The new version will have a new resource id
		// Also, we may need to update the CP model expressions too, this requires creating a new version to avoid 
		// overwriting previous state
		this.cpModelId = resId;
	}*/

	/**
	 * Construct an instance with the target CP Model resource id
	
	public Mapper(String resId) {
		// I think this may change at runtime as rule processor clones the cp_model
		// rather than updating it. The new version will have a new resource id
		// Also, we may need to update the CP model expressions too, this requires creating a new version to avoid 
		// overwriting previous state
		this.cpModelId = resId;
	}*/
	
	/**
	 * This method is used for preparing a CP model for first deployment. There
	 * are no running metrics and a default constant value is assigned to each
	 * metric variable listed in the model. If there are no metric variables in
	 * the CPModel, the solution timestamp value will be 0.
	 * <p>
	 * @param resId
	 *            the target CP Model resource id
	 * @return 	a {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>} containing the 
	 * 			new resource ID and the solution timestamp
	 * @throws MetricMapperException
	 *             on processing error
	 */
	public JsonObject mapMetricVariables(String resId) throws MetricMapperException {
		JsonObject jObj = new JsonObject(); //keys are "id" (String) and "solution_tmp" (long)
		boolean updateCP = false; // commit to CDO flag
		if (this.utils == null) {
			this.utils = CdoTool.getInstance();
		}
		List<EObject> model_contents = null;		
		// start the cdo-client
		try{
			Long timestamp = 0l;
			this.utils.openCDOSession();
			// clone the resource in memory and get the cp model 
			model_contents = this.utils.cloneModel(resId); // cloner may return an empty list		
			ConstraintProblem cp = CpModelTool.getCPModel(model_contents);
			if (cp == null) {
				throw new MetricMapperException(
						"failed to extract ConstraintModel from the resource("
								+ resId + ")");
			}
			//16Nov15 - Daniel R. only supports 3 types of metrics (cost, availability and response time).  He should have created the empty solution
			//to hold the metricVariableValues
			// go ahead
			// 30Nv 15 we always get a solution, now get the solution				
			Solution solution = CPModelTool.searchLastSolution(cp.getSolution());
			// now get the solution
			List<MetricVariable> mvs = cp.getMetricVariables();			
			System.out.println("...about to get metric variables....");
			log.debug("...about to get metric variables....");
			//
			if (mvs == null || mvs.isEmpty()) {
				System.out.println("CP model in " + resId
						+ " has no Metric Variable entities...");
				log.debug("CP model in " + resId
						+ " has no Metric Variable entities...");
				//30Nov15 just need to get the empty solution timestamp
				timestamp = solution.getTimestamp();
			} else {//there are metric variables
				System.out.println(mvs.size()
						+ " metric variables retrived from CP model in " + resId
						+ "...");
				log.debug(mvs.size()
						+ " metric variables retrived from CP model in " + resId
						+ "...");
//				if (solution == null) {
//					// no last solution in model, create one now
//					System.out.println("CP model in " + resId
//							+ " has no Solution entities...");
//					log.debug("CP model in " + resId
//							+ " has no Solution entities...");
//					updateCP = true;
//					solution = CPModelTool.createSolution(cp);
//					// now add the metricVariable with constant value
//					for (MetricVariable mv : mvs) {
//						//
//						solution = CpModelTool.setConstantValue(mv, solution);
//					}
//				} else {// cp generator has created the solution
//					System.out.println("CP model in " + resId
//							+ " already has a Solution entity...");
//					log.debug("CP model in " + resId
//							+ " already has a Solution entity...");
						// match the metricVariables
					for (MetricVariable mv : mvs) {
						if (CPModelTool.searchMetricValue(solution, mv) == null) {
							// create it
							solution = CpModelTool.setConstantValue(mv, solution);
							updateCP = true;
						}// solution got it already, continue
					}
				//}
				timestamp = solution.getTimestamp();
				//jObj.add("solution_tmp", solution.getTimestamp()); // milp-solver needs this
			}//end if there are metric variables
			if (updateCP) {
				System.out.println("updating CP Model( " + resId
						+ ") in CDO...");
				log.debug("updating CP Model( " + resId
						+ ") in CDO...");
				//get a new resource id
				String newId = CpModelTool.getCloneId(CpModelTool.getAppId(model_contents), resId);
				//this.utils.overwriteCPModelinCDO(model_contents, newId);
				this.utils.commitCloneModelToCDO(model_contents, newId);
				jObj.add("id", newId);
			}else{
				System.out.println("no change to CP Model(" + resId
						+ ") ...");
				log.debug("no change to CP Model(" + resId
						+ ") ...");
				//we are using the same model
				jObj.add("id", resId);
			}
			jObj.add("solution_tmp", timestamp); // milp-solver needs this
			// explicitly stop the cdo client
			this.utils.closeCDOSession();
			//
		}catch(MetricMapperException me){
			throw me;	//re-throw
		}catch(Exception e){
			log.error("Error trying to map metricVariableValues for new deployment : " + e.getMessage());
			throw new MetricMapperException(e);
		}
		return jObj;
	}


	/**
	 * This method is used to prepare the CP Model for re&#45;deployment. The CP
	 * model is updated with the metric variable values provided in the input
	 * {@link java.util.HashMap <em>HashMap</em>}
	 * <p>
	 * @param resId
	 *            the target CP Model resource id
	 * @param metrics
	 *            a {@link java.util.HashMap <em>HashMap</em>} of the metric
	 *            variables and associated values to update
	 * @return 	a {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>} containing the 
	 * 			new resource ID and the solution timestamp
	 * @throws MetricMapperException
	 *             on processing error
	 */
	public JsonObject mapMetricVariables(String resId, HashMap<String, String> metrics)
			throws MetricMapperException {
		//CP_generator is not involved in this scenario, I will have to create the empty solution
		if (metrics == null || metrics.isEmpty()) {
			throw new MetricMapperException(
					"No new metric values provided, cannot proceed....");
		}
		if (this.utils == null) {
			this.utils = CdoTool.getInstance();
		}
		JsonObject jObj = new JsonObject(); //keys are "id" (String) and "solution_tmp" (long)
		List<EObject> model_contents;		
		//
		try{
			this.utils.openCDOSession();
			//cloned resource in memory 
			model_contents = this.utils.cloneModel(resId); // cloner return an empty list
			//get the cp model from a the resource cloned in memory
			ConstraintProblem cp = CpModelTool.getCPModel(model_contents);
			if (cp == null) {
				throw new MetricMapperException(
						"failed to extract ConstraintModel from the resource("
								+ resId + ")");
			}
			// the application has been running, so there would be at least one solution
			// create an empty solution
			Solution newSolution = CPModelTool.createSolution(cp);
			jObj.add("solution_tmp", newSolution.getTimestamp());
			// find the latest solution, may need to copy the values
			Solution lastSolution = CPModelTool.searchLastSolution(cp.getSolution()); //there should be old solutions
			//Solution newSolution = CpModelTool.copySolution(solution); //old solution got variableValues which we don't want
			List<MetricVariable> cp_MVs = cp.getMetricVariables();
			//if there is no app-spec metrics, the mapper wouldn't have been called with new metric value/s
			if (cp_MVs == null || cp_MVs.isEmpty()) {
				throw new MetricMapperException(					
						"there is no metric variables in the cpModel(" + resId
								+ "), something is wrong!");
			}
			// process the incoming metric variable values
			Set<String> metricVariables = metrics.keySet();
			for (String mvName : metricVariables) { 
				//look for the owner - the metric variable
				MetricVariable currentMV = CpModelTool.getMetricVariable(mvName, cp_MVs);				
				// create the new value using the incoming version
				MetricVariableValue value = CpModelTool.createMVV(currentMV, metrics.get(mvName));
				newSolution.getMetricVariableValue().add(value);
			}
			//need to copy the existing values for those not included in the update
			if(cp_MVs.size() > metrics.size()){ //if there are more metric variables than those provided
				for(MetricVariable cp_mv : cp_MVs){
					log.debug("current metricVariable id is : " + cp_mv.getId() + "....");
					//is the current cp metric variable in the incoming set
					if(!metricVariables.contains(cp_mv.getId())){
						log.debug("current metric variable(" + cp_mv.getId() + ") is not in the incoming set....");
						//find the existing value (there should always be one, either initial constant value or the previous runtime value
						MetricVariableValue oldValue = CPModelTool.searchMetricValue(lastSolution, cp_mv);
						//
						if(oldValue != null){
							//needs to clone a new MetricVariableValue ob
							MetricVariableValue newValueObj = CpModelTool.createMVV(cp_mv, oldValue.getValue());
							newSolution.getMetricVariableValue().add(newValueObj);
						}
					}
				}				
			}	
			//ready to save to CDO, get a new resource id
			String newId = CpModelTool.getCloneId(CpModelTool.getAppId(model_contents), resId);
			//debug
			//System.out.println("I am here ... ");
			//this.utils.overwriteCPModelinCDO(model_contents, newId);
			this.utils.overwriteCPModelinCDO(model_contents, newId);
			jObj.add("id", newId);
			// explicitly stop the cdo client
			this.utils.closeCDOSession();
		}catch (NumberFormatException nfe) {
			log.error("Error parsing the provided metric variable value...");
			throw new MetricMapperException(nfe.getCause());
		}catch(MetricMapperException me){
			throw me;	//rethrow
		}catch(Exception e){
			log.error("Error trying to map metricVariableValues for re-deployment : " + e.getMessage());
			throw new MetricMapperException(e);
		}
		return jObj;
	}

	// ////////////////////////GETTER & SETTER//////////////////////////////////

	/**
	 * Getter for the {@link #cpModelId <em>cpModelId</em>}
	 * <p>
	 * 
	 * @return the cpModelId
	 */
	public String getCpModelId() {
		return cpModelId;
	}

	/**
	 * Setter for the {@link #cpModelId <em>cpModelId</em>}
	 * <p>
	 * 
	 * @param cpModelId
	 *            the cpModelId to set
	 */
	public void setCpModelId(String cpModelId) {
		this.cpModelId = cpModelId;
	}

	// ///////////////////////PRIVATE METHODS/////////////////////////////////

	
}
