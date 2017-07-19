/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.plangenerator.graph;

import eu.paasage.upperware.adapter.plangenerator.tasks.Task;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public interface GraphGenerator<T> {

  DirectedGraph<Task, DefaultEdge> generateConfigGraph(T comparableModel);

  DirectedGraph<Task, DefaultEdge> generateReconfigGraph(T oldComparableModel, T newComparableModel);

}
