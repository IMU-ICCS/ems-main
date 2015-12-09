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
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.eclipsesource.json.JsonObject;

import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.CpFactory;
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
	 * Construct an instance -- remove as we need a cpModelId
	 */
//	public Mapper() {
//	}
	
	/**
	 * Construct an instance with the target CP Model resource id */
	 
	public Mapper(String resId) {
		// I think this may change at runtime as rule processor clones the cp_model
		// rather than updating it. The new version will have a new resource id
		// Also, we may need to update the CP model expressions too, this requires creating a new version to avoid 
		// overwriting previous state
		this.cpModelId = resId;
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

	public JsonObject mapMetricVariables() throws MetricMapperException {
		return this.mapMetricVariables(null);
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
	
	private boolean initWithDefaultMetricVariable(ConstraintProblem cp, Solution solution)
	{
		boolean updatedCP=false;
		for (MetricVariable mv : cp.getMetricVariables())
		{
			if (CPModelTool.searchMetricValue(solution, mv) == null)
			{
				// create it
				CpModelTool.setConstantValue(mv, solution); // TO CHECK !!!
				updatedCP=true;
			}
		}
		return updatedCP;
	}
	
	private boolean initWithMetrics(ConstraintProblem cp, Solution lastSolution, Solution newSolution, HashMap<String, String> metrics )
	{
		boolean updatedCP=false;
		
		// process the incoming metric variable values
		List<MetricVariable> cp_MVs = cp.getMetricVariables();
		Set<String> metricVariables = metrics.keySet();
		for (String mvName : metricVariables) { 
			log.debug("the current metric variable is : " + mvName + "...");
			//look for the owner - the metric variable
			MetricVariable currentMV = CpModelTool.getMetricVariable(mvName, cp_MVs);				
			// create the new value using the incoming version
			MetricVariableValue value = CpModelTool.createMVV(currentMV, metrics.get(mvName));
			// TODO: CHECK IF IT DOES NOT ALREAD EXIST
			newSolution.getMetricVariableValue().add(value);
			updatedCP = true;
		}

		//need to copy the existing values for those not included in the update
		if(cp_MVs.size() > metrics.size()){ //if there are more metric variables than those provided
			for(MetricVariable cp_mv : cp_MVs){
				//System.out.println("current metricVariable id is : " + cp_mv.getId() + "....");
				log.debug("current metricVariable id is : " + cp_mv.getId() + "....");
				//is the current cp metric variable in the incoming set
				if(!metricVariables.contains(cp_mv.getId())){
					log.debug("current metric variable(" + cp_mv.getId() + ") is not in the incoming set....");
					//find the existing value (there should always be one, either initial constant value or the previous runtime value
					MetricVariableValue oldValue = CPModelTool.searchMetricValue(lastSolution, cp_mv);
					//
					if(oldValue != null){
						log.debug("... trying to copy old value to new solution for : " + cp_mv.getId());
						//needs to clone a new MetricVariableValue ob
						MetricVariableValue newValueObj = CpModelTool.createMVV(cp_mv, oldValue.getValue());
						// TODO: CHECK IF IT DOES NOT ALREAD EXIST
						newSolution.getMetricVariableValue().add(newValueObj);
						updatedCP = true;
					}
				}
			}
		}	
		return updatedCP;
	}

	public JsonObject mapMetricVariables(HashMap<String, String> metrics) throws MetricMapperException {
		JsonObject jObj = new JsonObject(); //keys are "id" (String) and "solution_tmp" (long)

		jObj.add("id", this.cpModelId);

		if (this.utils == null) {
			this.utils = CdoTool.getInstance();
		}
		// start the cdo-client
		try
		{	
			this.utils.openCDOSession();
			// load the resource in memory and get the cp model 
			CDOTransaction trans = CdoTool.getCDOClient().openTransaction();
			log.info("Reading CDO resId: "+this.cpModelId);
			CDOResource res = trans.getResource(this.cpModelId); 
			log.info("Res: "+res);
			EList<EObject> model_contents = res.getContents(); // cloner may return an empty list		
			ConstraintProblem cp = CpModelTool.getCPModel(model_contents);
			if (cp == null) {
				throw new MetricMapperException("failed to extract ConstraintModel from the resource(" + this.cpModelId + ")");
			}
			// retrieve Solution and its timestamp
			//16Nov15 - Daniel R. only supports 3 types of metrics (cost, availability and response time).  He should have created the empty solution
			//to hold the metricVariableValues
			// go ahead
			// 30Nv 15 we always get a solution, now get the solution				
			Solution lastSolution = CPModelTool.searchLastSolution(cp.getSolution());
			Solution newSolution = null;
			Long timestamp;
			// If not solution, create an empty one
			if ((lastSolution == null) || (metrics!=null))
			{
				Solution sol = CpFactory.eINSTANCE.createSolution();
				Long ts = System.currentTimeMillis();
				sol.setTimestamp(ts);
				cp.getSolution().add(sol);

				try {
					log.debug("Commiting a new empty Solution...");
					trans.commit();
				} catch (CommitException e) {
					throw new MetricMapperException("Error when commiting an empty solution to CDO");
				}
				// need to reload sol ?
				newSolution = sol;
				timestamp = ts;
			} else {
				timestamp = lastSolution.getTimestamp();
			}

			// Completing jObj
			jObj.add("solution_tmp", timestamp); // milp-solver needs this
			
			// Check if there are some metric variables
			List<MetricVariable> mvs = cp.getMetricVariables();
			if ((mvs == null) || mvs.isEmpty()) {
				// Nothing to do?
				log.info("CP model in " + this.cpModelId + " has no Metric Variable entities...");
				trans.close();
				return jObj;
			}			
			
			//there are metric variables
			log.info(mvs.size()	+ " metric variables retrived from CP model in " + this.cpModelId + "...");

			boolean updatedCP;
			if (metrics == null)
				updatedCP = this.initWithDefaultMetricVariable(cp, lastSolution);
			else
				updatedCP = this.initWithMetrics(cp, lastSolution, newSolution, metrics);
				
			if (updatedCP)
			{
				try {
					log.debug("Commiting Metric Variable Solution...");
					trans.commit();
				} catch (CommitException e) {
					throw new MetricMapperException("Error when commiting an empty solution to CDO");
				}				
			}

			trans.close();
		}catch(MetricMapperException me){
			throw me;	//re-throw
		}catch(Exception e){
			log.error("Error trying to map metricVariableValues for new deployment : " + e.getMessage());
			throw new MetricMapperException(e);
		} finally {
			this.utils.closeCDOSession();
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
