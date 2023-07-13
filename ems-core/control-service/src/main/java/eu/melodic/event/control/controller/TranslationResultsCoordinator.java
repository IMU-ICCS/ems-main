/*
 * Copyright (C) 2017-2023 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.control.controller;

import eu.melodic.event.translate.TranslationContext;
import eu.melodic.event.translate.dag.DAGNode;
import eu.melodic.event.translate.model.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TranslationResultsCoordinator {
    private final ControlServiceCoordinator coordinator;

    // ------------------------------------------------------------------------------------------------------------
    // Translation information query methods
    // ------------------------------------------------------------------------------------------------------------

    public List<Monitor> getMonitorsOfAppModel(String appModelId) {
        if (StringUtils.isBlank(appModelId))
            appModelId = coordinator.getCurrentAppModelId();
        TranslationContext _tc = coordinator.getTranslationContextOfAppModel(appModelId);
        if (_tc==null) return Collections.emptyList();
        return new ArrayList<>(_tc.getMON());
    }

    public Set getMetricConstraints(String appModelId) {
        TranslationContext _tc = coordinator.getTranslationContextOfAppModel(appModelId);
        if (_tc==null) return Collections.emptySet();
        return _tc.getMetricConstraints();
    }

    /*public Set<String> getGlobalGroupingMetrics(String appModelId) {
        TranslationContext _tc = coordinator.getTranslationContextOfAppModel(appModelId);
        if (_tc==null) return Collections.emptySet();

        // get all top-level nodes their component metrics
        final Set<DAGNode> nodes = new HashSet<>();
        final Deque<DAGNode> q = new ArrayDeque<>(_tc.getDAG().getTopLevelNodes());
        while (!q.isEmpty()) {
            DAGNode node = q.pop();
            if (node.getGrouping()== Grouping.GLOBAL) {
                nodes.add(node);
                q.addAll(_tc.getDAG().getNodeChildren(node));
            }
        }

        // return metric names
        return nodes.stream()
                .map(DAGNode::getElementName)
                .collect(Collectors.toSet());
    }*/

    public @NonNull Map<String,Object> getSLOMetricDecomposition(String appModelId) {
        List<Object> slos = _getSLOMetricDecomposition(appModelId);
        Map<String,Object> result = new HashMap<>();
        result.put("name", "_");
        result.put("operator", "OR");
        result.put("constraints", slos);
        return result;
    }

    private @NonNull List<Object> _getSLOMetricDecomposition(String appModelId) {
        TranslationContext _tc = coordinator.getTranslationContextOfAppModel(appModelId);
        if (_tc==null) return Collections.emptyList();

        // Get metric and logical constraints
        Map<String, MetricConstraint> mcMap = _tc.getMetricConstraints().stream()
                .collect(Collectors.toMap(MetricConstraint::getName, mc -> mc));
        Map<String, LogicalConstraint> lcMap = _tc.getLogicalConstraints().stream()
                .collect(Collectors.toMap(LogicalConstraint::getName, lc -> lc));
        /*Map<String, TranslationContext.IfThenConstraint> ifMap = _tc.getIfThenConstraints().stream()
                .collect(Collectors.toMap(TranslationContext.IfThenConstraint::getName, ic -> ic));*/

        // Create map of top-level element names and instances
        Set<DAGNode> topLevelNodes = _tc.getDAG().getTopLevelNodes();
        Map<String, DAGNode> topLevelNodesMap = topLevelNodes.stream()
                .collect(Collectors.toMap(DAGNode::getElementName, x -> x));

        // process each SLO
        List<Object> sloMetricDecompositions = new ArrayList<>();
        for (String sloName : _tc.getSLO()) {
            DAGNode node = topLevelNodesMap.get(sloName);
            if (node!=null) {
                // get SLO constraint
                Set<DAGNode> sloConstraintSet = _tc.getDAG().getNodeChildren(node);
                // SLO must contain exactly one constraint
                if (sloConstraintSet.size()==1) {
                    DAGNode sloConstraintNode = sloConstraintSet.iterator().next();
                    // decompose constraint
                    Object decomposition = _decomposeConstraint(_tc, sloConstraintNode, mcMap, lcMap);
                    // cache decomposition
                    sloMetricDecompositions.add(decomposition);
                }
            }
        }

        return sloMetricDecompositions;
    }

    private Object _decomposeConstraint(TranslationContext _tc, DAGNode constraintNode, Map<String, MetricConstraint> mcMap, Map<String, LogicalConstraint> lcMap) {
        NamedElement element = constraintNode.getElement();
        String elementName = constraintNode.getElementName();
        String elementClassName = ((Object)element).getClass().getName();
        if (element instanceof MetricConstraint) {
            return mcMap.get(elementName);
        } else
        if (element instanceof LogicalConstraint) {
            LogicalConstraint lc = lcMap.get(elementName);

            // decompose child constraints
            List<Object> list = new ArrayList<>();
            for (DAGNode node : lc.getConstraintNodes()) {
                Object o = _decomposeConstraint(_tc, node, mcMap, lcMap);
                if (o!=null) list.add(o);
            }

            // create decomposition result
            Map<String,Object> result = new HashMap<>();
            result.put("name", lc.getName());
            result.put("operator", lc.getLogicalOperator());
            result.put("constraints", list);
            return result;
        } else
            log.warn("_decomposeConstraint: Unsupported Constraint type: {} {}", constraintNode.getElementName(), elementClassName);
        return null;
    }

    public @NonNull Set<MetricContext> getMetricContextsForPrediction(String appModelId) {
        log.debug("getMetricContextsForPrediction: BEGIN: {}", appModelId);
        TranslationContext _tc = coordinator.getTranslationContextOfAppModel(appModelId);
        if (_tc==null) {
            log.debug("getMetricContextsForPrediction: END: No Translation Context found for model: {}", appModelId);
            return Collections.emptySet();
        }

        // Process DAG top-level nodes
        Set<DAGNode> topLevelNodes = _tc.getDAG().getTopLevelNodes();
        HashSet<MetricContext> tcMetricsOfTopLevelNodes = new HashSet<>();
        log.debug("getMetricContextsForPrediction: Translation Context found for model: {}", appModelId);

        final Deque<DAGNode> q = topLevelNodes.stream()
                .filter(x ->
                        x.getElement() instanceof ServiceLevelObjective ||
                                x.getElement() instanceof Metric)
                .distinct()
                .collect(Collectors.toCollection(ArrayDeque::new));

        while (!q.isEmpty()) {
            DAGNode node = q.pop();
            if (node.getElement() instanceof MetricContext) {
                tcMetricsOfTopLevelNodes.add(node.getMetricContext());
            } else {
                Set<DAGNode> children = _tc.getDAG().getNodeChildren(node);
                if (children!=null) q.addAll(children);
            }
        }

        log.debug("getMetricContextsForPrediction: END: Metrics of Top-Level nodes of model: model={}, metrics={}", appModelId, tcMetricsOfTopLevelNodes);
        return tcMetricsOfTopLevelNodes;
    }
}
