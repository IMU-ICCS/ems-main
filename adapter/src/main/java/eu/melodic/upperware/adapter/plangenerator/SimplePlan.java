/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.plangenerator;

import eu.melodic.upperware.adapter.plangenerator.tasks.Task;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

@ToString
@AllArgsConstructor
public class SimplePlan implements Plan {

  private String name;
  private SimpleDirectedGraph<Task, DefaultEdge> taskGraph;
  private PlanType type;

  @Override
  public String getName() {
    return name;
  }

  @Override
  public SimpleDirectedGraph<Task, DefaultEdge> getTaskGraph() {
    return taskGraph;
  }

  @Override
  public PlanType getType() {
    return type;
  }
}
