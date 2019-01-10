/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.translate.transform;

import camel.metric.MetricVariable;
import eu.melodic.event.translate.analyze.DAG;
import eu.melodic.event.translate.analyze.DAGNode;
import eu.melodic.event.translate.properties.CamelToEplTranslatorProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Set;

@Slf4j
public class GraphTransformer {
    private CamelToEplTranslatorProperties properties;

    public void transformGraph(DAG dag, CamelToEplTranslatorProperties properties) {
        log.debug("GraphTransformer.transformGraph():  Transforming DAG...");
        if (properties.isPruneMvv()) {
            removeMVV(dag, dag.getRootNode());
        } else {
            log.debug("GraphTransformer.transformGraph():  MVV pruning from DAG is disabled");
        }
        log.debug("GraphTransformer.transformGraph():  Transforming DAG... done");
    }

    // Branch pruning when all nodes in branch are MVVs or Metric Variables calculated using exclusively MVVs or other Metric Variables calculated using MVVs
    protected boolean removeMVV(DAG dag, DAGNode node) {
        log.debug("GraphTransformer.removeMVV():  Checking node {}...", node);

        // first process children (i.e. first prune child MVV's)
        Set<DAGNode> children = dag.getNodeChildren(node);
        log.debug("GraphTransformer.removeMVV():  Initial node children: node={}, children: {}", node, children);
        if (children != null) {
            for (DAGNode child : children) {
                removeMVV(dag, child);
            }
        }

        children = dag.getNodeChildren(node);
        log.debug("GraphTransformer.removeMVV():  Node children after pruning: node={}, children: {}", node, children);

        // check if this node is MVV (i.e. has no child MVV's and is Metric Variable)
        if (node.getElement() != null) {
            if (CollectionUtils.isEmpty(children)) {
                if (node.getElement() instanceof MetricVariable) {
                    // remove from DAG
                    log.debug("GraphTransformer.removeMVV():  Node is MVV: node={}", node);
                    dag.removeNode(node.getElement());
                    log.debug("GraphTransformer.removeMVV():  MVV node pruned: node={}", node);
                    return true;
                }
            }
        }
        // i.e. 'node' is not Metric variable or it has children that are not MVVs
        log.debug("GraphTransformer.removeMVV():  Node is not MVV: node={}", node);
        return false;
    }
}
