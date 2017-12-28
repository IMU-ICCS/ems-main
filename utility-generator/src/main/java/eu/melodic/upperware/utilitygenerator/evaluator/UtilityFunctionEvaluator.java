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
import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunctionExample;
import eu.melodic.upperware.utilitygenerator.model.Component;
import solver.variables.IntVar;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class UtilityFunctionEvaluator {

  boolean isReconfig;
  Collection<Component> actConfiguration;
  CostUtilityFunction costUtilityFunction;


  //fixme: get things from cp model: metrics, which function, variables, aktDeployment z pola solution - jeśli go nie
  // ma to ustaw flagę na że to konfiguracja, a nie rekonfiguracja
  UtilityFunctionEvaluator(Collection<Component> actConfiguration, boolean isReconfig,
    CostUtilityFunction costUtilityFunction){
    this.isReconfig = isReconfig;
    if (isReconfig){
      this.actConfiguration = actConfiguration;
    }
    this.costUtilityFunction = costUtilityFunction;
  }

  UtilityFunctionEvaluator(Collection<Component> actConfiguration, boolean isReconfig){

    this.isReconfig = isReconfig;
    if (isReconfig){
      this.actConfiguration = actConfiguration;
    }
    this.costUtilityFunction = new CostUtilityFunctionExample(isReconfig);
  }

  public abstract double evaluate(Collection<Component> newConfiguration);


  //fixme: Variable[], nie IntVar[]
  public double evaluate(IntVar[] newConfiguration){

    //fixme: get real node candidates from cache
    List<NodeCandidate> nodeCandidates = getSampleNodeCandidates();

    NodeCandidate theCheapest = findTheCheapestNodeCanidate(nodeCandidates);
    //System.out.println("cheapest " + theCheapest);

    //fixme: wyciągnij z solution cardinality dla każdego komponentu
    int cardinality = 3;

    //fixme: muszę wiedzieć ile jest komponentów

    return evaluate(Lists.newArrayList(new Component(theCheapest, cardinality)));
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

  private NodeCandidate findTheCheapestNodeCanidate(List<NodeCandidate> nodeCandidates){
    checkNotNull(nodeCandidates); //fixme - lepszy błąd
    NodeCandidate theCheapest = nodeCandidates.get(0);
    for (NodeCandidate nc: nodeCandidates){
      if (theCheapest.getPrice() > nc.getPrice()){
        theCheapest = nc;
      }
    }
    return theCheapest;
  }

}
