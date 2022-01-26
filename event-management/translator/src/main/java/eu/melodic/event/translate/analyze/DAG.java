/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.translate.analyze;

import camel.core.NamedElement;
import eu.melodic.event.translate.TranslationContext;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.io.ComponentNameProvider;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.ExportException;
import org.jgrapht.io.GraphExporter;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class DAG {
    // Graph-related fields
    private TranslationContext _TC;
    private DirectedAcyclicGraph<DAGNode, DAGEdge> _graph;
    private DAGNode _root;
    private Map<NamedElement, DAGNode> _namedElementToNodesMapping;
    private Map<String, DAGNode> _nameToNodesMapping;

    public DAG() {
        // let everything 'null'
    }

    public DAG(TranslationContext _TC) {
        this._TC = _TC;
        _graph = new DirectedAcyclicGraph<>(DAGEdge.class);
        _root = new DAGNode();
        _graph.addVertex(_root);
        _namedElementToNodesMapping = new HashMap<>();
        _nameToNodesMapping = new HashMap<>();
    }

    public DAGNode getRootNode() {
        return _root;
    }

    public Set<DAGNode> getTopLevelNodes() {
        log.info("DAG.getTopLevelNodes()");
        Set<DAGNode> children = _graph.outgoingEdgesOf(_root).stream().map(edge -> edge.getTarget()).collect(java.util.stream.Collectors.toSet());
        log.info("DAG.getTopLevelNodes(): top-level-nodes={}", children);
        return children;
    }

    public boolean isTopLevelNode(DAGNode node) {
        Set<DAGNode> parents = getParentNodes(node);
        Iterator<DAGNode> it = parents.iterator();
        while (it.hasNext()) {
            if (it.next() == _root) return true;
        }
        return false;
    }

    public Set<DAGNode> getLeafNodes() {
        Iterator<DAGNode> it = _graph.iterator();
        Set<DAGNode> leafs = new HashSet<DAGNode>();
        it.forEachRemaining(node -> {
            if (node != _root && _graph.outgoingEdgesOf(node).isEmpty()) {
                leafs.add(node);
            }
        });
        return leafs;
    }

    public Set<DAGNode> getParentNodes(DAGNode node) {
        Set<DAGEdge> edges = _graph.incomingEdgesOf(node);
        return edges.stream().map(edge -> edge.getSource()).collect(Collectors.toSet());
    }

    public Set<DAGNode> getNodeChildren(DAGNode node) {
        try {
            //log.info("DAG.getNodeChildren(): node={}", node);
            Set<DAGNode> children = _graph.outgoingEdgesOf(node).stream().map(edge -> edge.getTarget()).collect(java.util.stream.Collectors.toSet());
            //log.info("DAG.getNodeChildren(): parent={}, children={}", node, children);
            return children;
        } catch (IllegalArgumentException iae) {
            log.warn("DAG.getNodeChildren(): Node not in DAG: node={}", node);
            return null;
        }
    }

    // ====================================================================================================================================================
    // Add node methods

    public DAGNode addTopLevelNode(NamedElement elem) {
        return addTopLevelNode(elem, null);
    }

    public DAGNode addTopLevelNode(NamedElement elem, String effectiveFullName) {
        if (elem == null) throw new IllegalArgumentException("DAG.addTopLevelNode(): Argument #1 cannot be null");

        log.debug("DAG.addTopLevelNode(): top-level-element={}, effective-full-name={}", elem.getName(), effectiveFullName);
        DAGNode node = _namedElementToNodesMapping.get(elem);
        log.debug("DAG.addTopLevelNode(): cached-node={}", node);
        if (node != null && effectiveFullName != null && !effectiveFullName.trim().isEmpty() && !node.getName().equals(effectiveFullName)) {
            log.debug("DAG.addTopLevelNode(): Cached-node has different full-name than effective-full-name. A new node will be created: {} != {}", node.getName(), effectiveFullName);
            node = null;
        }
        boolean newNode = false;
        if (node == null) {
            String fullName = (effectiveFullName == null || effectiveFullName.trim().isEmpty()) ? _TC.getFullName(elem) : effectiveFullName.trim();

            if (!_nameToNodesMapping.containsKey(fullName)) {

                node = new DAGNode(elem, fullName);
                newNode = _graph.addVertex(node);
                if (newNode) log.info("DAG.addTopLevelNode(): Element added in DAG: {}", node.getName());
                else log.info("DAG.addTopLevelNode(): Element already in DAG and replaced: {}", node.getName());

                _namedElementToNodesMapping.put(elem, node);
                if (_nameToNodesMapping.put(node.getName(), node) != null) {
                    log.warn("DAG.addTopLevelNode(): _nameToNodesMapping: {}", _nameToNodesMapping);
                    throw new RuntimeException("Element name already exists in DAG: " + node.getName());
                }

            } else {
                node = _nameToNodesMapping.get(fullName);
                newNode = _graph.addVertex(node);
                if (newNode) log.info("DAG.addTopLevelNode()-2: Element added in DAG: {}", node.getName());
                else log.info("DAG.addTopLevelNode()-2: Element already in DAG and replaced: {}", node.getName());

                _namedElementToNodesMapping.put(elem, node);
            }
        } else {
            log.info("DAG.addTopLevelNode(): Element already in DAG: {}", node.getName());
        }

        DAGEdge edge = new DAGEdge();
        boolean newEdge = _graph.addEdge(_root, node, edge);
        if (newNode) log.info("DAG.addTopLevelNode(): Element set as Top-Level in DAG: {}", node.getName());
        else log.info("DAG.addTopLevelNode(): Element is already set as Top-Level in DAG: {}", node.getName());

        return node;
    }

    public DAGNode addNode(NamedElement parent, NamedElement elem) {
        if (parent == null) throw new IllegalArgumentException("DAG.addNode(): Argument #1 'parent' cannot be null");
        if (elem == null) throw new IllegalArgumentException("DAG.addNode(): Argument #2 'elem' cannot be null");

        log.debug("DAG.addNode(): parent={}, element={}", parent.getName(), elem.getName());
        DAGNode node = _namedElementToNodesMapping.get(elem);
        log.debug("DAG.addNode(): cached-node={}", node);
        boolean newNode = false;
        if (node == null) {
            String fullName = _TC.getFullName(elem);

            if (!_nameToNodesMapping.containsKey(fullName)) {

                node = new DAGNode(elem, fullName);
                newNode = _graph.addVertex(node);
                if (newNode) log.info("DAG.addNode(): Element added in DAG: {}", node.getName());
                else log.info("DAG.addNode(): Element already in DAG and replaced: {}", node.getName());

                _namedElementToNodesMapping.put(elem, node);
                if (_nameToNodesMapping.put(node.getName(), node) != null) {
                    log.warn("DAG.addNode(): _nameToNodesMapping: {}", _nameToNodesMapping);
                    throw new RuntimeException("Element name already exists in DAG: " + node.getName());
                }

            } else {
                node = _nameToNodesMapping.get(fullName);
                newNode = _graph.addVertex(node);
                if (newNode) log.info("DAG.addNode()-2: Element added in DAG: {}", node.getName());
                else log.info("DAG.addNode()-2: Element already in DAG and replaced: {}", node.getName());

                _namedElementToNodesMapping.put(elem, node);
            }
        } else {
            log.info("DAG.addNode(): Element already in DAG: {}", node.getName());
        }

        DAGNode parentNode = _namedElementToNodesMapping.get(parent);
        DAGEdge edge = new DAGEdge();
        boolean newEdge = _graph.addEdge(parentNode, node, edge);
        if (newNode) log.info("DAG.addNode(): Edge added in DAG: {} --> {} ", parent.getName(), node.getName());
        else log.info("DAG.addNode(): Edge is already in DAG: {} --> {}", parent.getName(), node.getName());

        return node;
    }

    // ====================================================================================================================================================
    // Remove node method

    public DAGNode removeNode(NamedElement elem) {
        if (elem == null) throw new IllegalArgumentException("DAG.removeNode(): Argument cannot be null");

        // check if children nodes exist
        DAGNode node = _namedElementToNodesMapping.get(elem);
        if (node == null) {
            log.warn("DAG.removeNode(): Element not found (_namedElementToNodesMapping): {}", elem.getName());
            return null;
        }
        Set<DAGEdge> edges = _graph.outgoingEdgesOf(node);
        if (edges != null && edges.size() > 0)
            throw new RuntimeException("Element being removed has children: " + node.getName());

        // remove node from DAG
        if (node != null) {
            _graph.removeVertex(node);        // This also removes edges touching this node
            _namedElementToNodesMapping.remove(elem);
            log.error("DAG.removeNode(): Element removed from DAG: {}", node.getName());
        } else {
            log.error("DAG.removeNode(): Element not found in DAG: {}", node.getName());
        }

        return node;
    }

    // ====================================================================================================================================================
    // Add/Remove edge methods

    public DAGEdge addEdge(NamedElement elemFrom, NamedElement elemTo) {
        if (elemFrom == null)
            throw new IllegalArgumentException("DAG.addEdge(): Argument #1 'elemFrom' cannot be null");
        if (elemTo == null) throw new IllegalArgumentException("DAG.addEdge(): Argument #2 'elemTo' cannot be null");

        Iterator<DAGNode> it = _graph.iterator();
        DAGNode nodeFrom = null;
        DAGNode nodeTo = null;
        while (it.hasNext() && (nodeFrom == null || nodeTo == null)) {
            DAGNode node = it.next();
            if (node.getElement() == elemFrom) nodeFrom = node;
            if (node.getElement() == elemTo) nodeTo = node;
        }
        if (nodeFrom != null && nodeTo != null) {
            DAGEdge edge = new DAGEdge();
            boolean newEdge = _graph.addEdge(nodeFrom, nodeTo, edge);
            if (newEdge) log.info("DAG.addEdge(): Edge added in DAG: {} --> {} ", elemFrom.getName(), elemTo.getName());
            else log.info("DAG.addEdge(): Edge is already in DAG: {} --> {}", elemFrom.getName(), elemTo.getName());
            return edge;
        } else {
            throw new RuntimeException(String.format("Adding edge FAILED: elem-from=%s -> elem-to=%s. Node not found in DAG: node-from=%s --> node-to=%s",
                    elemFrom.getName(), elemTo.getName(), (nodeFrom != null ? nodeFrom.getName() : null), (nodeTo != null ? nodeTo.getName() : null)));
        }
    }

    public DAGEdge addEdge(String elemFrom, String elemTo) {
        if (elemFrom == null)
            throw new IllegalArgumentException("DAG.addEdge(): Argument #1 'elemFrom' cannot be null");
        if (elemTo == null) throw new IllegalArgumentException("DAG.addEdge(): Argument #2 'elemTo' cannot be null");
        log.info("DAG.addEdge(): Adding edge in DAG: {} --> {} ", elemFrom, elemTo);

        Iterator<DAGNode> it = _graph.iterator();
        DAGNode nodeFrom = null;
        DAGNode nodeTo = null;
        while (it.hasNext() && (nodeFrom == null || nodeTo == null)) {
            DAGNode node = it.next();
            if (elemFrom.equals(node.getName())) nodeFrom = node;
            if (elemTo.equals(node.getName())) nodeTo = node;
        }
        if (nodeFrom != null && nodeTo != null) {
            DAGEdge edge = new DAGEdge();
            boolean newEdge = _graph.addEdge(nodeFrom, nodeTo, edge);
            if (newEdge) log.info("DAG.addEdge(): Edge added in DAG: {} --> {} ", elemFrom, elemTo);
            else log.info("DAG.addEdge(): Edge is already in DAG: {} --> {}", elemFrom, elemTo);
            return edge;
        } else {
            throw new RuntimeException(String.format("Adding edge FAILED: elem-from=%s -> elem-to=%s. Node not found in DAG: node-from=%s --> node-to=%s",
                    elemFrom, elemTo, (nodeFrom != null ? nodeFrom.getName() : null), (nodeTo != null ? nodeTo.getName() : null)));
        }
    }

    public DAGEdge removeEdge(NamedElement elemFrom, NamedElement elemTo) {
        if (elemFrom == null)
            throw new IllegalArgumentException("DAG.removeEdge(): Argument #1 'elemFrom' cannot be null");
        if (elemTo == null) throw new IllegalArgumentException("DAG.removeEdge(): Argument #2 'elemTo' cannot be null");

        Iterator<DAGNode> it = _graph.iterator();
        DAGNode nodeFrom = null;
        DAGNode nodeTo = null;
        while (it.hasNext() && (nodeFrom == null || nodeTo == null)) {
            DAGNode node = it.next();
            if (node.getElement() == elemFrom) nodeFrom = node;
            if (node.getElement() == elemTo) nodeTo = node;
        }
        if (nodeFrom != null && nodeTo != null) {
            DAGEdge deletedEdge = _graph.removeEdge(nodeFrom, nodeTo);
            if (deletedEdge != null)
                log.info("DAG.removeEdge(): Edge removed from DAG: {} --> {} ", elemFrom.getName(), elemTo.getName());
            else log.info("DAG.removeEdge(): Edge not found in DAG: {} --> {}", elemFrom.getName(), elemTo.getName());
            return deletedEdge;
        } else {
            throw new RuntimeException(String.format("Removing edge FAILED: elem-from=%s -> elem-to=%s. Node not found in DAG: node-from=%s --> node-to=%s",
                    elemFrom.getName(), elemTo.getName(), (nodeFrom != null ? nodeFrom.getName() : null), (nodeTo != null ? nodeTo.getName() : null)));
        }
    }

    public DAGEdge removeEdge(DAGNode nodeFrom, DAGNode nodeTo) {
        if (nodeFrom == null)
            throw new IllegalArgumentException("DAG.removeEdge(): Argument #1 'nodeFrom' cannot be null");
        if (nodeTo == null) throw new IllegalArgumentException("DAG.removeEdge(): Argument #2 'nodeTo' cannot be null");

        DAGEdge deletedEdge = _graph.removeEdge(nodeFrom, nodeTo);
        if (deletedEdge != null)
            log.info("DAG.removeEdge(): Edge removed from DAG: {} --> {} ", nodeFrom.getElementName(), nodeTo.getElementName());
        else
            log.info("DAG.removeEdge(): Edge not found in DAG: {} --> {}", nodeFrom.getElementName(), nodeTo.getElementName());
        return deletedEdge;
    }

    // ====================================================================================================================================================
    // Traverse graph methods

    public void traverseDAG(java.util.function.Consumer<? super DAGNode> action) {
        log.info("DAG.traverseDAG(): Traversing graph: Begin");
        _graph.iterator().forEachRemaining(action);
        log.info("DAG.traverseDAG(): Traversing graph: End");
    }

    // ====================================================================================================================================================
    // Export methods

    public String exportToDot() throws ExportException {
        // use helper classes to define how vertices should be rendered,
        // adhering to the DOT language restrictions
        ComponentNameProvider<DAGNode> vertexIdProvider =
                new ComponentNameProvider<DAGNode>() {
                    public String getName(DAGNode node) {
                        return "NODE_" + node.getId();
                    }
                };
        ComponentNameProvider<DAGNode> vertexLabelProvider =
                new ComponentNameProvider<DAGNode>() {
                    public String getName(DAGNode node) {
                        if (node.element != null) {
                            //return String.format("%s\n[%s]", node.getName(), (node.getGrouping()==null ? "?" : node.getGrouping().toString()));
                            if (node.getGrouping() != null) {
                                return String.format("%s\n[%s]", node.getName(), "" + node.getGrouping());
                            } else {
                                return node.getName();
                            }
                        } else {
                            return "<ROOT>";
                        }
                    }
                };

        GraphExporter<DAGNode, DAGEdge> exporter = new DOTExporter<>(vertexIdProvider, vertexLabelProvider, null);
        Writer writer = new StringWriter();
        exporter.exportGraph(_graph, writer);
        return writer.toString();
    }

    public void exportDAG(String baseFileName, String[] exportFormats, int imageWidth) {
        try {
            if (!checkExportConfiguration(baseFileName, exportFormats, imageWidth)) return;

            // Export DAG in DOT format (can be viewd with GraphViz tool)
            String dot = exportToDot();
            log.debug("DAG.exportDAG(): Results of exportToDot(): Graph in DOT format:\n{}", dot);

            // Export DOT into specified formats and save to file(s)
            exportDAG(dot, baseFileName, exportFormats, imageWidth);

        } catch (Exception ex) {
            log.error("DAG.exportDAG(): Graph export FAILED: ", ex);
        }
    }

    public void exportDAG(String dot, String baseFileName, String[] exportFormats, int imageWidth) {
        try {
            if (!checkExportConfiguration(baseFileName, exportFormats, imageWidth)) return;

            // Export DOT into specified formats and save to file(s)
            MutableGraph mg = Parser.read(dot);
            for (String f : exportFormats) {
                Format fmt = Format.valueOf(f.toUpperCase());
                String exportFile = baseFileName + "." + f;
                Graphviz.fromGraph(mg).width(imageWidth).render(fmt).toFile(new File(exportFile));
                log.info("DAG.exportDAG(): Graph exported in {} format: {}", fmt, exportFile);
            }

        } catch (Exception ex) {
            log.error("DAG.exportDAG(): Graph export FAILED: ", ex);
        }
    }

    protected boolean checkExportConfiguration(String baseFileName, String[] exportFormats, int imageWidth) {
        // check export configuration
        if (exportFormats == null || exportFormats.length == 0) {
            log.warn("DAG.checkExportConfiguration(): No export formats specified for Graph export: {}", Arrays.toString(exportFormats));
            return false;
        }
        if (imageWidth < 1) {
            log.warn("DAG.checkExportConfiguration(): Invalid image width for Graph export: {}", imageWidth);
            return false;
        }
        return true;
    }

    public String toString() {
        return _graph!=null ? _graph.toString() : null;
    }
}
