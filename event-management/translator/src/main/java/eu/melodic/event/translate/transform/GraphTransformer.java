/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.translate.transform;

import eu.melodic.event.translate.analyze.DAG;
import eu.melodic.event.translate.analyze.DAGNode;
import eu.melodic.event.translate.properties.CamelToEplTranslatorProperties;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GraphTransformer {
	public void transformGraph(DAG dag, CamelToEplTranslatorProperties properties) {
		log.debug("GraphTransformer.transformGraph():  Transforming DAG...");
		//XXX: TODO
		//removeMVV( dag.getRootNode() );
		log.debug("GraphTransformer.transformGraph():  Transforming DAG... done");
	}
	
//XXX: TODO: implement branch pruning when all nodes in branch are MVVs or Metric Variables calculated using exclusively MVVs or other Metric Variables calculated using MVVs
	/*protected boolean removeMVV(DAGNode node) {
		Set<DAGNode> children = node.getNodeChildren();
		boolean foundMVV = false;
		for (DAGNode child : children) {
			boolean isMVV = removeMVV(child);
			foundMVV = foundMVV || wasMVV;
			if (isMVV) {
				// remove 'child' from DAG
			}
		}
		if (node.getElement()!=null) {
			if (children.size()==0 && node.getElement()!=null) {
				if (MetricVariable.class.isAssignableFrom(node.getElement().getClass())) {
					MetricVariable mv = (MetricVariable)node.getElement();
					String formula = mv.getFormula();
					if (formula==null || formula.trim().isEmpty()) {
						return true;
					}
				}
			}
		}
		return false;
	}*/
}
