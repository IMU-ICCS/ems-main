/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.plangenerator;

import camel.deployment.DeploymentInstanceModel;
import eu.melodic.upperware.adapter.plangenerator.converter.CamelModelConverter;
import eu.melodic.upperware.adapter.plangenerator.graph.DefaultGraphGenerator;
import eu.melodic.upperware.adapter.plangenerator.model.ComparableModel;
import eu.melodic.upperware.adapter.plangenerator.tasks.Task;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
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
  public Plan buildConfigurationPlan(@NonNull DeploymentInstanceModel model) {
    log.info("Building configuration plan");
    ComparableModel compModel = converter.toComparableModel(model);
    SimpleDirectedGraph<Task, DefaultEdge> graph = generator.generateGraph(compModel);
    Plan plan = new SimplePlan(format("%s configuration plan", model.getName()), graph);
    log.info("Built plan: {}", plan);
    return plan;
  }

  @Override
  public Plan buildReconfigurationPlan(@NonNull DeploymentInstanceModel oldModel, @NonNull DeploymentInstanceModel newModel) {
    log.info("Building reconfiguration plan");
    ComparableModel oldCompModel = converter.toComparableModel(oldModel);
    ComparableModel newCompModel = converter.toComparableModel(newModel);
    SimpleDirectedGraph<Task, DefaultEdge> graph = generator.generateGraph(newCompModel, oldCompModel);
    Plan plan = new SimplePlan(format("%s->%s reconfiguration plan", oldModel.getName(), newModel.getName()), graph);
    log.info("Built plan: {}", plan);
    return plan;
  }
}
