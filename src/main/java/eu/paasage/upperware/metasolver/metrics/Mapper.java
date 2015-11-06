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
import org.eclipse.emf.ecore.EObject;

//import eu.paasage.upperware.cp.cloner.CDOClientExtended;
//import eu.paasage.upperware.cp.cloner.CPCloner;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.MetricVariable;
import eu.paasage.upperware.metamodel.cp.MetricVariableValue;
import eu.paasage.upperware.metamodel.cp.Solution;
import eu.paasage.upperware.profiler.cp.generator.model.tools.CPModelTool;
import eu.paasage.upperware.metasolver.exception.MetricMapperException;
import eu.paasage.upperware.metasolver.util.CdoTool;
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
	/** CPCloner for cloning existing CDO resources */
	//private CPCloner cpCloner;
	/** the extended CDO client provided by CPCloner */
	//private CDOClientExtended xCdoClient; // currently no credentials. If
											// require credentials, need to
											// extend it
	/** CDO utilties */
	private CdoTool utils;

	/**
	 * Construct an instance
	 */
	public Mapper() {
	}

	/**
	 * Construct an instance with the target CP Model resource id
	 */
	public Mapper(String resId) {
		// I think this may change at runtime as CP_generate clone the cp_model
		// rather than
		// updating it. The new version will have a new resource id
		this.cpModelId = resId;
	}

	/**
	 * This method is used for preparing a CP model for first deployment. There
	 * are no running metrics and a default constant value is assigned to each
	 * metric variable listed in the model. If there are no metric variables in
	 * the CPModel, the return value will be 0.
	 * <p>
	 * 
	 * @param resId
	 *            the target CP Model resource id
	 * @return long the solution timestamp to use when calling the solver or 0
	 * @throws MetricMapperException
	 *             on processing error
	 */
	public long mapMetricVariables(String resId) throws MetricMapperException {
		//
		long timestamp = 0;
		boolean updateCP = false; // commit to CDO flag
		if (this.utils == null) {
			this.utils = CdoTool.getInstance();
		}
		// start the cdo-client
		this.utils.openCDOSession();
		// get the cp model from a copy of the resource
		List<EObject> model_contents = this.utils.cloneModel(resId); // cloner
																		// may
																		// return
																		// an
																		// empty
																		// list
		ConstraintProblem cp = CpModelTool.getCPModel(model_contents);
		if (cp == null) {
			throw new MetricMapperException(
					"failed to extract ConstraintModel from the resource("
							+ resId + ")");
		}
		// go ahead
		List<MetricVariable> mvs = cp.getMetricVariables();
		//
		if (mvs.isEmpty()) {
			log.debug("CP model in " + resId
					+ " has no Metric Variable entities...");
		} else {
			log.debug(mvs.size()
					+ " metric variables retrived from CP model in " + resId
					+ "...");
			// now get the solution
			Solution solution = CPModelTool
					.searchLastSolution(cp.getSolution());
			if (solution == null) {
				// no solution in model, create one now
				log.debug("CP model in " + resId
						+ " has no Solution entities...");
				updateCP = true;
				solution = CPModelTool.createSolution(cp);
				// now add the metricVariable with constant value
				for (MetricVariable mv : mvs) {
					//
					solution = CpModelTool.setConstantValue(mv, solution);
				}
			} else {// there is a last solution
					// match the metricVariables
				for (MetricVariable mv : mvs) {
					if (CPModelTool.searchMetricValue(solution, mv) == null) {
						// create it
						solution = CpModelTool.setConstantValue(mv, solution);
						updateCP = true;
					}// solution got it already, continue
				}
			}
			timestamp = solution.getTimestamp(); // milp-solver needs this
		}
		if (updateCP) {
			// go ahead an write model back, the new id just has the suffix _1
			this.utils.commitCloneModelToCDO(model_contents, resId + "_1");
		}
		// explicitly stop the cdo client
		this.utils.closeCDOSession();
		//
		return timestamp;
	}

	/**
	 * This method is used to prepare the CP Model for re&#45;deployment. The CP
	 * model is updated with the metric variable values provided in the input
	 * {@link java.util.HashMap <em>HashMap</em>}
	 * <p>
	 * *
	 * 
	 * @param resId
	 *            the target CP Model resource id
	 * @param metrics
	 *            a {@link java.util.HashMap <em>HashMap</em>} of the metric
	 *            variables and associated values to update
	 * @return the solution timestamp to use when calling the solver
	 * @throws MetricMapperException
	 *             on processing error
	 */
	public long mapMetricVariables(String resId, HashMap<String, String> metrics)
			throws MetricMapperException {

		if (metrics == null || metrics.isEmpty()) {
			throw new MetricMapperException(
					"No new metric values provided, cannot proceed....");
		}
		if (this.utils == null) {
			this.utils = CdoTool.getInstance();
		}
		// start the cdo-client
		this.utils.openCDOSession();
		// get the cp model from a copy of the resource
		List<EObject> model_contents = this.utils.cloneModel(resId); // cloner
																		// may
																		// return
																		// an
																		// empty
																		// list
		ConstraintProblem cp = CpModelTool.getCPModel(model_contents);
		if (cp == null) {
			throw new MetricMapperException(
					"failed to extract ConstraintModel from the resource("
							+ resId + ")");
		}
		// the application has been running, so there would be at least one
		// solution
		Solution solution = CPModelTool.searchLastSolution(cp.getSolution());
		// use the old one as a template
		Solution newSolution = CpModelTool.copySolution(solution);
		List<MetricVariable> cp_MVs = cp.getMetricVariables();
		if (cp_MVs == null || cp_MVs.isEmpty()) {
			throw new MetricMapperException(
					"there is no metric variables in the cpModel(" + resId
							+ "), something is wrong!");
		}
		try {
			// process the incoming metric variable values
			Set<String> metricVariables = metrics.keySet();
			for (String mvName : metricVariables) {
				//
				MetricVariable current = CpModelTool.getMetricVariable(mvName, cp_MVs);
				if (current == null) {
					log.error("this metric variable(" + mvName
							+ ") is not in the cpModel(" + resId
							+ "), bypassing this!");
					continue;
				}
				MetricVariableValue theMVV = CPModelTool.searchMetricValue(
						newSolution, current);
				if (theMVV == null) {
					// the variable exists, but not in solution. So create it
					MetricVariableValue value = CpModelTool.createMVV(current,
							metrics.get(mvName));
					newSolution.getMetricVariableValue().add(value);
				} else {
					// update the exsiting value in the solution......
					CpModelTool.updateMetricVariableValue(theMVV, current.getType(), metrics.get(mvName));
					//the metricVariableValue already in the newSolution
				}
			}
		} catch (NumberFormatException nfe) {
			log.error("Error parsing the provided metric variable value...");
			throw new MetricMapperException(nfe.getCause());
		} catch (Exception e) {
			log.error("Error mapping the metric variable values for resId("
					+ resId + " ...");
			throw new MetricMapperException(e.getCause());
		}
		// go ahead an write model back, the new id just has the suffix _1
		this.utils.commitCloneModelToCDO(model_contents, resId + "_1");
		// explicitly stop the cdo client
		this.utils.closeCDOSession();
		//
		return newSolution.getTimestamp();
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
