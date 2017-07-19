/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.plangenerator;

import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.upperware.adapter.plangenerator.graph.DefaultGraphGenerator;
import eu.paasage.upperware.adapter.plangenerator.model.ComparableModel;
import eu.paasage.upperware.adapter.plangenerator.converter.CamelModelConverter;
import eu.paasage.upperware.adapter.plangenerator.tasks.Task;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class DefaultPlanGenerator implements PlanGenerator {

  private CamelModelConverter converter;
  private DefaultGraphGenerator generator;

  @Override
  public Plan buildConfigurationPlan(@NonNull DeploymentModel model) {
    log.info("Building configuration plan");
    ComparableModel compModel = converter.toComparableModel(model);
    SimpleDirectedGraph<Task, DefaultEdge> graph = generator.generateConfigGraph(compModel);
    Plan plan = new SimplePlan(format("%s configuration plan", model.getName()), graph);
    log.info("Built plan: {}", plan);
    return plan;
  }

  @Override
  public Plan buildReconfigurationPlan(@NonNull DeploymentModel oldModel, @NonNull DeploymentModel newModel) {
    log.info("Building reconfiguration plan");
    ComparableModel oldCompModel = converter.toComparableModel(oldModel);
    ComparableModel newCompModel = converter.toComparableModel(newModel);
    SimpleDirectedWeightedGraph<Task, DefaultEdge> graph = generator.generateReconfigGraph(oldCompModel, newCompModel);
    Plan plan = new WeightedPlan(format("%s->%s reconfiguration plan", oldModel.getName(), newModel.getName()), graph);
    log.info("Built plan: {}", plan);
    return plan;
  }
}
