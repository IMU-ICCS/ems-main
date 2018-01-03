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
import com.google.common.collect.Lists;
import eu.melodic.cloudiator.client.model.NodeCandidate;
import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunction;
import eu.melodic.upperware.utilitygenerator.model.Component;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.MetricVariable;
import eu.paasage.upperware.metamodel.cp.Solution;
import eu.paasage.upperware.metamodel.cp.Variable;
import org.eclipse.emf.common.util.EList;
import solver.variables.IntVar;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static eu.melodic.upperware.utilitygenerator.evaluator.Utils.*;

public abstract class UtilityFunctionEvaluator {

  //private?
  boolean isReconfig;
  Collection<Component> actConfiguration;
  CostUtilityFunction costUtilityFunction;
  EList<Variable> variables;
  EList<MetricVariable> metrics;

  public abstract double evaluate(Collection<Component> newConfiguration);


  UtilityFunctionEvaluator(ConstraintProblem cp){

    this.variables = cp.getVariables();

    this.metrics = cp.getMetricVariables();     // todo convert

    this.isReconfig = !(cp.getSolution().isEmpty());

    if (isReconfig){
      Solution actualSolution = findLastSolution(cp.getSolution()); //assumption: last solution was deployed
      this.actConfiguration = convertActualDeployment(actualSolution.getVariableValue(), getSampleNodeCandidates());
    }
  }

  public double evaluate(IntVar[] newConfigurationInt){
  //public double evaluate(IntVar[] newConfigurationInt, RealVar[] newConfigurationReal){

    //todo: get real node candidates from cache
    List<NodeCandidate> nodeCandidates = getSampleNodeCandidates();

    /*Collection<Component> newConfiguration = new ArrayList<>();
    Map<String, Integer> cardinalitiesForComponent = getCardinalities(newConfigurationInt, variables);

    for (String componentId: cardinalitiesForComponent.keySet()){
      Collection<String> variableNamesForComponent = getVariableNamesForComponent(componentId, variables);

      IntVar[] filteredIntVar = (IntVar[]) Arrays.stream(newConfigurationInt)
        .filter(v -> variableNamesForComponent.contains(v.getName())).toArray();
      //RealVar[] filteredRealVar = (RealVar[]) Arrays.stream(newConfigurationReal)
        //.filter(v -> variableNamesForComponent.contains(v.getName())).toArray();

      List<NodeCandidate> filteredNodeCandidates = filterNodeCandidates(nodeCandidates, filteredIntVar, null);
      NodeCandidate theCheapest = findTheCheapestNodeCanidate(filteredNodeCandidates);

      newConfiguration.add(new Component(theCheapest, cardinalitiesForComponent.get(componentId)));

    }*/


    //return evaluate(newConfiguration);
    return evaluate(Lists.newArrayList(new Component(findTheCheapestNodeCanidate(nodeCandidates), 1)));
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
