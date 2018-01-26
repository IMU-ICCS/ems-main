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

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class Coordinator implements ApplicationContextAware {
  
  private RestTemplate restTemplate;
  private MetaSolverProperties properties;
  private ApplicationContext applicationContext;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }
	
  /*XXX: TODO:
	How can we select the most appropriate solver??
  */
  public ConstraintProblemEnhancementResponse.DesignatedSolverType selectSolver(String applicationId, String cpModelPath) {
    log.info("MetaSolver.Coordinator: selectSolver(): appId={}, model={}", applicationId, cpModelPath);
    log.warn("MetaSolver.Coordinator: selectSolver(): ** NOTE: CP Solver is ALWAYS selected **");
    log.warn("MetaSolver.Coordinator: selectSolver(): ** NOT IMPLEMENTED **");
	return ConstraintProblemEnhancementResponse.DesignatedSolverType.CPSOLVER;
  }
  
  /**
	Update CP model with current metric variable values
  */
  public void setMetricValuesInCpModel(String applicationId, String cpModelPath) {
    log.info("MetaSolver.Coordinator: setMetricValuesInCpModel(): appId={}, model={}", applicationId, cpModelPath);
    log.warn("MetaSolver.Coordinator: setMetricValuesInCpModel(): ** NOT IMPLEMENTED **");
	
	// get metric values from metric value registry
	MetricValueMonitorBean monitor = (MetricValueMonitorBean) applicationContext.getBean(MetricValueMonitorBean.class);
	Map<String,String> metricValues = monitor.getMetricValuesRegistry().getMetricValuesAsMap();
    log.debug("MetaSolver.Coordinator: setMetricValuesInCpModel(): Metric values map: {}", metricValues);
	
	// Update CP model with current metric variable values
	CpModelHelper helper = (CpModelHelper) applicationContext.getBean(CpModelHelper.class);
	helper.updateCpModelWithMetricValues(applicationId, cpModelPath, metricValues);
  }
  
  /*XXX: TODO:
	IF NO deployed solution EXIST (ie first deployment) THEN
		RETURN: POSITIVE (ie accept the evaluated solution)
	ELSE
	IF a deployed solution EXIST (ie adaptation??) THEN
		retrieve from CDO the utility values of the deployed solution
		retrieve from CDO the utility values of the new solution
		compare solution utility values
		RETURN: POSITIVE if the utility value of the new solution is better at least by 10% (configurable) than the utility value of deployed solution
				NEGATIVE else
		Utility values (both for deployed and new solution) will be stored in CP model by Solvers
	END-IF
	
	NOTE:
	- The adapter/executionware should notify Metasolver & Solvers if/when new solution has been deployed?
	- Is it meaningful to periodically (or after a trigger event) check in CDO for a different deployed solution?
	  (Ie deployed solution changed without Metasolver being notified?)
  */
  public SolutionEvaluationResponse.EvaluationResultType evaluateSolution(String applicationId, String cdoModelsPath) {
    log.info("MetaSolver.Coordinator: evaluateSolution(): appId={}, model={}", applicationId, cdoModelsPath);
    log.warn("MetaSolver.Coordinator: evaluateSolution(): ** NOTE: POSITIVE is ALWAYS returned **");
    log.warn("MetaSolver.Coordinator: evaluateSolution(): ** NOT IMPLEMENTED **");
	//CpModelHelper helper = (CpModelHelper) applicationContext.getBean(CpModelHelper.class);
	return SolutionEvaluationResponse.EvaluationResultType.POSITIVE;
  }
  
}
