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

import eu.passage.upperware.commons.model.tools.CPModelTool;
import eu.passage.upperware.commons.model.tools.CdoTool;
import org.apache.log4j.Logger;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.eclipsesource.json.JsonObject;

import eu.paasage.mddb.cdo.client.CDOClient;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.CpFactory;
import eu.paasage.upperware.metamodel.cp.MetricVariable;
import eu.paasage.upperware.metamodel.cp.MetricVariableValue;
import eu.paasage.upperware.metamodel.cp.Solution;
import eu.paasage.upperware.metasolver.exception.MetricMapperException;
import eu.paasage.upperware.metasolver.util.CpModelTool;

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

	/**
	 * Construct an instance with the target CP Model resource id 
	 * */	 
	public Mapper(String resId) {
		// 11Dec15 Mapper now overwrites cp model rather than creating a new version.  One id is sufficient
		this.cpModelId = resId;
	}
	/**
	 * A wrapper for calling the {{@link #mapMetricVariables(HashMap)} method for a first deployment. 
	 * <p>
	 * @return 	a {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>} containing the 
	 * 			new resource ID and the solution timestamp.  
	 * @throws {@link eu.paasage.upperware.metasolver.exception.MetricMapperException <em>MetricMapperException</em>}
	 *             on processing error
	 */
	public JsonObject mapMetricVariables() throws MetricMapperException {
		return this.mapMetricVariables(null);
	}
	/**
	 * Prepare the CP Model for a new deployment. There are no running metrics 
	 * and a default constant value is assigned to each metric variable listed in the constraint model. 
	 * <p>
	 * @param cp		the {@link eu.paasage.upperware.metamodel.cp.ConstraintProblem <em>ConstraintProblem</em>} to solve	
	 * @param solution	the last {@link eu.paasage.upperware.metamodel.cp.Solution <em>Solution</em>} object in the model.
	 * @return	true if the {@link eu.paasage.upperware.metamodel.cp.ConstraintProblem <em>ConstraintProblem</em>} has been modified, else false.
	 */
	private boolean initWithDefaultMetricVariable(ConstraintProblem cp, Solution solution)
	{
		boolean updatedCP=false;
		//Theoretically CP_generator should have set default values for all the metric variable upstream
		for (MetricVariable mv : cp.getMetricVariables()){	
			if (CPModelTool.searchMetricValue(solution, mv) == null){
				// create it
				log.debug("...about to call setConstantValue with solution(" + solution.getTimestamp() + ") and metric variable(" + mv.getId() );
				CpModelTool.setConstantValue(mv, solution); 
				updatedCP = true;
			}
		}
		return updatedCP;
	}
	/**
	 * Update the {@link eu.paasage.upperware.metamodel.cp.MetricVariableValue <em>MetricVariableValue</em>} with the
	 * incoming ones or copy the existing ones from the previous {@link eu.paasage.upperware.metamodel.cp.Solution <em>Solution</em>} object
	 * <p>
	 * @param cp	the target {@link eu.paasage.upperware.metamodel.cp.ConstraintProblem <em>ConstraintProblem</em>} 
	 * @param newSolution	the new {@link eu.paasage.upperware.metamodel.cp.Solution <em>Solution</em>} object used to store the values
	 * @param lastSolution  the last {@link eu.paasage.upperware.metamodel.cp.Solution <em>Solution</em>} object in the cp model
	 * @param metrics	a {@java.util.HashMap <em>HashMap</em>} containing the key value pairs of the incoming metric variables.
	 * @return	true if the {@link eu.paasage.upperware.metamodel.cp.ConstraintProblem <em>ConstraintProblem</em>} has been modified, else false.
	 * @throw 	{@link eu.paasage.upperware.metasolver.exception.MetricMapperException <em>MetricMapperException</em>}
	 *             on processing error
	 */
	private boolean initWithMetrics(ConstraintProblem cp, Solution lastSolution, Solution newSolution, HashMap<String, String> metrics ) throws MetricMapperException
	{
		boolean updatedCP = false;		
		// process the incoming metric variable values
		List<MetricVariable> cp_MVs = cp.getMetricVariables();
		Set<String> metricVariables = metrics.keySet();
		//process the incoming metric variables first
		for (String mvName : metricVariables) { 
			log.debug("the current metric variable is : " + mvName + "...");
			//look for the owner - the metric variable
			MetricVariable currentMV = CpModelTool.getMetricVariable(mvName, cp_MVs);				
			// create the new value using the incoming version
			MetricVariableValue value = CpModelTool.createMVV(currentMV, metrics.get(mvName));
			//
			newSolution.getMetricVariableValue().add(value);
			updatedCP = true;
		}
		//now copy the existing values for those not included in the update
		if(cp_MVs.size() > metrics.size()){ //if there are more metric variables than those incoming
			for(MetricVariable cp_mv : cp_MVs){
				//System.out.println("current metricVariable id is : " + cp_mv.getId() + "....");
				log.debug("current metricVariable id is : " + cp_mv.getId() + "....");
				//is the current cp metric variable in the incoming set
				if(!metricVariables.contains(cp_mv.getId())){
					log.debug("current metric variable(" + cp_mv.getId() + ") is not in the incoming set....");
					//find the existing value (there should always be one, either the initial constant value or the previous runtime value
					MetricVariableValue oldValue = CPModelTool.searchMetricValue(lastSolution, cp_mv);
					//
					if(oldValue != null){
						log.debug("... copying old value to new solution for : " + cp_mv.getId());
						//need to clone a new MetricVariableValue object
						MetricVariableValue newValueObj = CpModelTool.createMVV(cp_mv, oldValue.getValue());
						//
						newSolution.getMetricVariableValue().add(newValueObj);
						updatedCP = true;
					}else{//failed to find the old value
						throw new MetricMapperException("Failed to find existing metric variable value for : " + cp_mv.getId());
					}
				}
			}
		}	
		return updatedCP;
	}
	/**
	 * Update the {@link eu.paasage.upperware.metamodel.cp.MetricVariableValue <em>MetricVariableValue</em>}
	 * <p>
	 * @param metrics	a {@java.util.HashMap <em>HashMap</em>} containing the key value pairs of the incoming metric variables.
	 * @return	a {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>} containing the 
	 * 			new resource ID and the solution timestamp.  
	 * @throws {@link eu.paasage.upperware.metasolver.exception.MetricMapperException <em>MetricMapperException</em>}
	 *             on processing error
	 */
	public JsonObject mapMetricVariables(HashMap<String, String> metrics) throws MetricMapperException {
		JsonObject jObj = new JsonObject(); //keys are "id" (String) and "solution_tmp" (long)

		jObj.add("id", this.cpModelId);
		// start the cdo-client
		CDOClient cdoClient = new CDOClient();
		CdoTool.registerPackages(cdoClient);
		// to overwrite, we need to get the target object w/n a transaction
		CDOTransaction trans = cdoClient.openTransaction();
		//
		try{	
			
			log.info("Reading CDO resId: "+ this.cpModelId);
			CDOResource res = trans.getResource(CdoTool.addCdoPrefix(this.cpModelId));
			log.info("Res: " + res);
			EList<EObject> model_contents = res.getContents(); // may get an empty list		
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
			// 11Dec15 either no last solution or has metrics
			// there is always a last solution, so we rely on the 2nd condition
			log.debug("last solution is null? " + (lastSolution == null));
			log.debug("metrics hm is null? " + (metrics == null));
			//			
			if ((lastSolution == null) || (metrics !=null)){
				log.debug("metrics hm != null ...");
				//only applies to reconfig 
				Solution sol = CpFactory.eINSTANCE.createSolution();
				Long ts = System.currentTimeMillis();
				sol.setTimestamp(ts);
				cp.getSolution().add(sol);
				//chunk up commits to avoid dirty state
				log.debug("Commiting a new empty Solution...");
				trans.commit();
				// need to reload sol ?
				newSolution = sol;
				timestamp = ts;
			} else {
				//11Dec15, this implies the new deployment case. We rely on CP Generator doing the deed
				timestamp = lastSolution.getTimestamp();
			}
			// Completing jObj
			jObj.add("solution_tmp", timestamp); // milp-solver needs this
			//
			// Check if there are some metric variables
			List<MetricVariable> mvs = cp.getMetricVariables();
			if ((mvs == null) || mvs.isEmpty()) {
				// Nothing to do?  but the cp model will always have an empty solution
				log.info("CP model in " + this.cpModelId + " has no Metric Variable entities...");
				trans.close();
				return jObj;
			}	
			//there are metric variables in the CP model
			log.info(mvs.size()	+ " metric variables retrieved from CP model in " + this.cpModelId + "...");
			//
			boolean updatedCP;
			if (metrics == null){ //new deployment, no running metrics
				log.debug("..no metrics HM, calling initWithDefaultMetricVariable....");
				updatedCP = this.initWithDefaultMetricVariable(cp, lastSolution);
			}else{	//reconfig
				log.debug("..have metrics HM, calling initWithMetrics....");
				updatedCP = this.initWithMetrics(cp, lastSolution, newSolution, metrics);
			}	
			if (updatedCP){
				//
				log.debug("Commiting Metric Variable Solution...");
				trans.commit();
				trans.close();
			}
		} catch (CommitException e) {
			throw new MetricMapperException("Error when commiting an empty solution to CDO : " + e);
		}catch(MetricMapperException me){
			throw me;	//re-throw
		}catch(Exception e){
			log.error("Error trying to map metricVariableValues: " + e.getMessage());
			throw new MetricMapperException(e);
		}finally{
			//make sure that it is closed
			if(!trans.isClosed()){
				trans.close();
			}
		}
		return jObj;
	}
	
	// ////////////////////////GETTER & SETTER//////////////////////////////////

	/**
	 * Getter for the {@link #cpModelId <em>cpModelId</em>}
	 * <p>
	 * @return the cpModelId
	 */
	public String getCpModelId() {
		return cpModelId;
	}

	/**
	 * Setter for the {@link #cpModelId <em>cpModelId</em>}
	 * <p>
	 * @param cpModelId
	 *            the cpModelId to set
	 */
	public void setCpModelId(String cpModelId) {
		this.cpModelId = cpModelId;
	}

	// ///////////////////////PRIVATE METHODS/////////////////////////////////

	
}
