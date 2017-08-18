/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.plangenerator.graph.model;

import lombok.Getter;
import org.jgrapht.graph.SimpleDirectedGraph;

@Getter
public class MelodicGraph<V,E> extends SimpleDirectedGraph<V,E> {

  private Type type;

  public MelodicGraph(Class edgeClass, Type type) {
    super(edgeClass);
    this.type = type;
  }
}
