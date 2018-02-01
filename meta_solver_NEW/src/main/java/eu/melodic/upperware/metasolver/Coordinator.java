/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.metasolver;

import eu.melodic.models.interfaces.metaSolver.ConstraintProblemEnhancementResponse;
import eu.melodic.models.interfaces.metaSolver.SolutionEvaluationResponse;
import eu.melodic.upperware.metasolver.metricvalue.MetricValueMonitorBean;
import eu.melodic.upperware.metasolver.properties.MetaSolverProperties;
import eu.melodic.upperware.metasolver.util.CpModelHelper;

import java.util.Map;

//import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.ConcurrentAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
//@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class Coordinator implements ApplicationContextAware {
  
  //private RestTemplate restTemplate;
  private ApplicationContext applicationContext;
  private MetaSolverProperties properties;
  private double uvThresholdFactor;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
	this.properties = (MetaSolverProperties) applicationContext.getBean(MetaSolverProperties.class);
    this.uvThresholdFactor = properties.getUtilityThresholdFactor();
	log.debug("MetaSolver.Coordinator: setApplicationContext(): configuration={}", properties);
  }
	
  /**
	How can we select the most appropriate solver??
	For R1.5 it will always be CP solver
  */
  public ConstraintProblemEnhancementResponse.DesignatedSolverType selectSolver(String applicationId, String cpModelPath) throws ConcurrentAccessException {
    log.info("MetaSolver.Coordinator: selectSolver(): appId={}, model={}", applicationId, cpModelPath);
    log.warn("MetaSolver.Coordinator: selectSolver(): ** NOTE: CP Solver is ALWAYS selected **");
    log.warn("MetaSolver.Coordinator: selectSolver(): ** NOT IMPLEMENTED **");
	return ConstraintProblemEnhancementResponse.DesignatedSolverType.CPSOLVER;
  }
  
  /**
	Update CP model with current metric variable values
  */
  public void setMetricValuesInCpModel(String applicationId, String cpModelPath) throws ConcurrentAccessException {
    log.info("MetaSolver.Coordinator: setMetricValuesInCpModel(): appId={}, model={}", applicationId, cpModelPath);
	
	// get metric values from metric value registry
	MetricValueMonitorBean monitor = (MetricValueMonitorBean) applicationContext.getBean(MetricValueMonitorBean.class);
	Map<String,String> metricValues = monitor.getMetricValuesRegistry().getMetricValuesAsMap();
    log.debug("MetaSolver.Coordinator: setMetricValuesInCpModel(): Metric values map: {}", metricValues);
	
	// Update CP model with current metric variable values
	CpModelHelper helper = (CpModelHelper) applicationContext.getBean(CpModelHelper.class);
	helper.updateCpModelWithMetricValues(applicationId, cpModelPath, metricValues);
    
	log.info("MetaSolver.Coordinator: setMetricValuesInCpModel(): CP model updated with current MVV's");
  }
  
  /**
    Compare new and (currently) deployed solutions using their utility values.
	- If no deployed solution exists (first deployment) then 'accept' new solution
	- if a deployed solution exists then new solution's utility value must be better 
	  than deployed solution's utility value, at least 'uvThresholdFactor' times
  */
  public SolutionEvaluationResponse.EvaluationResultType evaluateSolution(String applicationId, String cpModelPath) throws ConcurrentAccessException {
    log.info("MetaSolver.Coordinator: evaluateSolution(): appId={}, model={}", applicationId, cpModelPath);
	
	// Update candidate solution
	CpModelHelper helper = (CpModelHelper) applicationContext.getBean(CpModelHelper.class);
	int newCanPos = helper.findAndSetCandidateSolutionIdInCpModel(applicationId, cpModelPath);
	if (newCanPos>=0) log.debug("MetaSolver.Coordinator: candidate solution updated: id={}", newCanPos);
    else if (newCanPos==-1) log.debug("MetaSolver.Coordinator: no candidate solution found");
    else log.debug("MetaSolver.Coordinator: an error occurred while looking for candidate solution");
	
	// Get utility values of new and deployed solutions
	double[] solUv = helper.getSolutionUtilities(applicationId, cpModelPath);
    log.debug("MetaSolver.Coordinator: solUv: {}", solUv);
	
	// check if an error occurred
	if (solUv==null) {
		log.warn("MetaSolver.Coordinator: evaluateSolution(): RETURN ERROR: An error occurred: appId={}, model={}", applicationId, cpModelPath);
		return SolutionEvaluationResponse.EvaluationResultType.ERROR;
	}
	if (solUv[0]==-2) {
		log.warn("MetaSolver.Coordinator: evaluateSolution(): RETURN ERROR: No solutions found in CP model: appId={}, model={}", applicationId, cpModelPath);
		return SolutionEvaluationResponse.EvaluationResultType.ERROR;
	}
	if (solUv[1]==-1) {
		log.warn("MetaSolver.Coordinator: evaluateSolution(): RETURN ERROR: No candidate solution found in CP model: appId={}, model={}", applicationId, cpModelPath);
		return SolutionEvaluationResponse.EvaluationResultType.ERROR;
	}
	
	// check if a solution is deployed. If no solution is deployed accept new solution
	if (solUv[0]<0) {
		log.info("MetaSolver.Coordinator: evaluateSolution(): RETURN POSITIVE: No deployed solution found. Accepting new solution: appId={}, model={}", applicationId, cpModelPath);
		return SolutionEvaluationResponse.EvaluationResultType.POSITIVE;
	}
	
	// a deployed solution exists. We need to compare the utility values of new and deployed solutions
	log.debug("MetaSolver.Coordinator: evaluateSolution(): utility-threshold-factor={} : appId={}, model={}", uvThresholdFactor, applicationId, cpModelPath);
	double depSolUv = solUv[0];
	double newSolUv = solUv[1];
	if (newSolUv > uvThresholdFactor * depSolUv) {
		log.info("MetaSolver.Coordinator: evaluateSolution(): RETURN POSITIVE: New solution is ACCEPTED: appId={}, model={}", applicationId, cpModelPath);
		return SolutionEvaluationResponse.EvaluationResultType.POSITIVE;
	} else {
		log.info("MetaSolver.Coordinator: evaluateSolution(): RETURN NEGATIVE: New solution is NOT ACCEPTED: appId={}, model={}", applicationId, cpModelPath);
		return SolutionEvaluationResponse.EvaluationResultType.NEGATIVE;
	}
  }
  
  /**
	Update deployed and candidate solutions in CP model
	Input:
	  applicationId : application id
	  cpModelPath: the path to CP model resource in CDO server
	  success: indicates whether adapter succeeded to deploy candidate solution or not
	    if SUCCESS
	      deployed Id <-- candidate Id
	      candidate Id <-- -1
	    if ERROR
	      candidate Id <-- -1
	Returns:
	  the new solution positions (int[])
	    int[0] : Index of deployed solution in 'solutions' EList
	    int[1] : Index of candidate solution in 'solutions' EList
	    An index equal to -1 means absence of deployed/candidate solution
	    An index equal to -2 means empty 'solutions' EList
	    An index >=0 indicates the position of the deployed/candidate solution in 'solutions' EList
  */
  public int[] updateSolutionIdsInCpModel(String applicationId, String cpModelPath, boolean success) throws ConcurrentAccessException {
    log.info("MetaSolver.Coordinator: updateSolutionIdsInCpModel(): appId={}, model={}, deploy-success={}", applicationId, cpModelPath, success);
	
	// Update CP model with new solution positions (or Ids)
	CpModelHelper helper = (CpModelHelper) applicationContext.getBean(CpModelHelper.class);
	int[] newPos = helper.updateSolutionIdsInCpModel(applicationId, cpModelPath, success);
    
	log.info("MetaSolver.Coordinator: updateSolutionIdsInCpModel(): Solution Ids have been updated in CP model: deployed-solution-id={}, candidate-solution-id={}", newPos[0], newPos[1]);
	return newPos;
  }
  
}
