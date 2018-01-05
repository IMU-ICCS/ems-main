/* * Copyright (C) 2017 7bulls.com
*
* This Source Code Form is subject to the terms of the
* Mozilla Public License, v. 2.0. If a copy of the MPL
* was not distributed with this file, You can obtain one at
* http://mozilla.org/MPL/2.0/.
*/

package eu.melodic.upperware.utilitygenerator.evaluator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.melodic.cloudiator.client.model.NodeCandidate;
import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunction;
import eu.melodic.upperware.utilitygenerator.model.Component;
import eu.paasage.upperware.metamodel.cp.*;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.common.util.EList;
import solver.variables.IntVar;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static eu.melodic.upperware.utilitygenerator.evaluator.Utils.*;

@Slf4j
public abstract class UtilityFunctionEvaluator {

    boolean isReconfig;
  Collection<Component> actConfiguration;
  CostUtilityFunction costUtilityFunction;
  EList<MetricVariable> metrics;
  private EList<Variable> variables;

  public abstract double evaluate(Collection<Component> newConfiguration);


  UtilityFunctionEvaluator(ConstraintProblem cp){

    this.variables = cp.getVariables();
    log.info("Creating Utility Function Evaluator from Constraint Problem");
    log.info("Variables from CP");
    for (Variable v: variables){
      log.info("{}, type: {}", v.getId(), v.getVariableType() );
    }

    this.metrics = cp.getMetricVariables();     // todo convert

    this.isReconfig = !(cp.getSolution().isEmpty());

    if (isReconfig){
      log.info("isReconfig is false");
      Solution actualSolution = findLastSolution(cp.getSolution()); //assumption: last solution was deployed
      this.actConfiguration = convertActualDeployment(actualSolution.getVariableValue(), getSampleNodeCandidates());
    }
  }

  public double evaluate(IntVar[] newConfigurationInt){
  //public double evaluate(IntVar[] newConfigurationInt, RealVar[] newConfigurationReal){ todo
    log.info("Evaluating solution:");
    int i=0;
    //debug
    for (IntVar var: newConfigurationInt){
      if (i<variables.size()){
        log.info("{} value = {}", var.getName(), var.getValue());
      }
      i++;
    }

    //todo: get real node candidates from cache
    List<NodeCandidate> nodeCandidates = getSampleNodeCandidates();

    Collection<Component> newConfiguration = new ArrayList<>();
    Map<String, Integer> cardinalitiesForComponent = getCardinalitiesForComponent(newConfigurationInt, variables);

    for (String componentId: cardinalitiesForComponent.keySet()){

      log.info("Filtering NC for component: {}", componentId);

      String providerId = getVariableNameForComponent(componentId, VariableType.PROVIDER, variables);

      Collection<String> variableNamesForComponent = getVariableNamesForComponent(componentId, variables);

      Collection<IntVar> filteredIntVar = Arrays.stream(newConfigurationInt)
        .filter(v -> variableNamesForComponent.contains(v.getName())).collect(Collectors.toList());
      //RealVar[] filteredRealVar = (RealVar[]) Arrays.stream(newConfigurationReal)
        //.filter(v -> variableNamesForComponent.contains(v.getName())).toArray();

      List<NodeCandidate> filteredNodeCandidates = filterNodeCandidates(nodeCandidates, filteredIntVar, null);
      NodeCandidate theCheapest = findTheCheapestNodeCanidate(filteredNodeCandidates);

      newConfiguration.add(new Component(theCheapest, cardinalitiesForComponent.get(componentId)));

    }


    return evaluate(newConfiguration);
    //return evaluate(Lists.newArrayList(new Component(findTheCheapestNodeCanidate(nodeCandidates), 1)));
  }


  /* ------------------------------------------------ only for tests -------------------*/

  UtilityFunctionEvaluator(Collection<Component> actConfiguration, boolean isReconfig,
    CostUtilityFunction costUtilityFunction){

    this.isReconfig = isReconfig;
    if (isReconfig){
      this.actConfiguration = actConfiguration;
    }
    this.costUtilityFunction = costUtilityFunction;
  }

  private List<NodeCandidate> getSampleNodeCandidates() {
    File file = new File(getClass().getClassLoader().getResource("test/nodeCandidates.json").getFile());
    try {
      return new ObjectMapper().readValue(file, new TypeReference<List<NodeCandidate>>(){});
    } catch (IOException e) {
      System.out.println(e);
    }
    return Collections.emptyList();
  }




}
