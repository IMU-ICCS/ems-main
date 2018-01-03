/* * Copyright (C) 2017 7bulls.com
*
* This Source Code Form is subject to the terms of the
* Mozilla Public License, v. 2.0. If a copy of the MPL
* was not distributed with this file, You can obtain one at
* http://mozilla.org/MPL/2.0/.
*/

package eu.melodic.upperware.utilitygenerator.evaluator;

import com.google.common.collect.Lists;
import eu.melodic.cloudiator.client.model.NodeCandidate;
import eu.melodic.upperware.utilitygenerator.model.Component;
import eu.paasage.upperware.metamodel.cp.Solution;
import eu.paasage.upperware.metamodel.cp.Variable;
import eu.paasage.upperware.metamodel.cp.VariableType;
import eu.paasage.upperware.metamodel.cp.VariableValue;
import eu.paasage.upperware.metamodel.types.impl.IntegerValueUpperwareImpl;
import eu.paasage.upperware.metamodel.types.impl.LongValueUpperwareImpl;
import org.eclipse.emf.common.util.EList;
import solver.variables.IntVar;
import solver.variables.RealVar;

import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

public class Utils {


  /* mapping variables from solution and  cp model */

  static Collection<String> getVariableNames(VariableType type, EList<Variable> variables){
    Collection<String> variableNames = new ArrayList<>();
    for (Variable v: variables){
      if (type.equals(v.getVariableType())){
        variableNames.add(v.getId());
      }
    }
    return variableNames;
  }

  static Collection<String> getVariableNamesForComponent(String componentId, EList<Variable> variables){
    Collection<String> variableNames = new ArrayList<>();
    for (Variable v: variables){
      if (componentId.equals(v.getComponentId())){
        variableNames.add(v.getId());
      }
    }
    return variableNames;

  }

  static Map<String,Integer> getCardinalities(IntVar[] newConfiguration, EList<Variable> variables) {
    //Collection<String>
    Collection<String> cardinalitiesNames = getVariableNames(VariableType.CARDINALITY, variables);
    Map<String, Integer> cardinalities = new HashMap<>();

    for (IntVar v: newConfiguration){
      if (cardinalitiesNames.contains(v.getName())){
        cardinalities.put(v.getName(), v.getValue());
      }
    }
    return cardinalities;
  }


  static List<NodeCandidate> filterNodeCandidates(List<NodeCandidate> nodeCandidates, IntVar[] filteredIntVar,
    RealVar[] filteredRealVar){

    return nodeCandidates; //todo
  }

  /* --------------------------------------------------*/

  //converting actual deployed solution to list of Node Candidates with cardinality
  static Collection<Component> convertActualDeployment(EList<VariableValue> actualSolution, List<NodeCandidate> nodeCandidates){

    String componentId = "";//fixme

    //for one component only
    List<VariableValue> variablesForOneComponent = actualSolution
      .stream()
      .filter(vv -> componentId.equals(vv.getVariable().getComponentId()))
      .collect(Collectors.toList());
    List<NodeCandidate> nodeCandidatesForOneComponent = nodeCandidates;
    int cardinality = 1;

    for (VariableValue vv: variablesForOneComponent) {
      Variable variable = vv.getVariable();
      VariableType type = variable.getVariableType();

      //skąd mam wiedzieć, że RAM to LongValue itd?
      switch (type) {
        case RAM:
          nodeCandidatesForOneComponent = nodeCandidatesForOneComponent
            .stream()
            .filter(nc -> nc.getHardware().getRam().equals(((LongValueUpperwareImpl) vv.getValue()).getValue()))
            .collect(Collectors.toList());
          break;
        case CARDINALITY:
          cardinality = ((IntegerValueUpperwareImpl) vv.getValue()).getValue();
          break;

      }
    }
    return Lists.newArrayList(new Component(findTheCheapestNodeCanidate(nodeCandidatesForOneComponent), cardinality));
  }

  static Solution findLastSolution(EList<Solution> solutions){
    Iterator<Solution> it = solutions.iterator();
    Solution last = it.next();
    while (it.hasNext()){
      last = it.next();
    }
    return last;
  }


  static NodeCandidate findTheCheapestNodeCanidate(List<NodeCandidate> nodeCandidates){
    checkNotNull(nodeCandidates); //fixme - better exception
    NodeCandidate theCheapest = nodeCandidates.get(0);
    for (NodeCandidate nc: nodeCandidates){
      if (theCheapest.getPrice() > nc.getPrice()){
        theCheapest = nc;
      }
    }
    return theCheapest;
  }


}
