package eu.melodic.upperware.mcts_solver.solver.utils.tree_printer;

import eu.melodic.upperware.mcts_solver.solver.mcts.cp_wrapper.MCTSWrapper;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Node;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.NodeStatisticsImpl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class TreePrinter {
    private static final int MAX_DEPTH = 10;
    private static final int MAX_CHILDREN = 2;
    /*
        If set to true chooses tree's subtree which is more or less symmetric - takes top MAX_CHILDREN children of root
        and proceeds recursively.
        If set to false, the tree is prunned to MAX_DEPTH and top MAX_CHILDREN_AT_MAX_DEPTH leaves are chosen. The subtree
        is built bottom.
     */
    private static final boolean SPREAD_MODE = false;
    private static final int MAX_CHILDREN_AT_MAX_DEPTH = 15;
    public static void saveTreeDataToFile(Node root, String treeFilePath, String nodesFilePath, MCTSWrapper mctsWrapper) throws IOException {
        saveNodes(root, nodesFilePath, mctsWrapper);
        if (SPREAD_MODE) {
            saveTreeStructureSpread(root, treeFilePath);
        } else {
            saveTreeStructureGreedy(root, treeFilePath);
        }
    }

    private static void saveNodes(Node root, String nodesFilePath, MCTSWrapper mctsWrapper) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(nodesFilePath));
        saveNode(root, writer, mctsWrapper);
        writer.close();
    }

    private static void saveNode(Node node, BufferedWriter writer, MCTSWrapper mctsWrapper) throws IOException {
        writer.write(nodeToString(node, mctsWrapper));
        for (Node child : node.getChildren()) {
            saveNode(child, writer,mctsWrapper);
        }
    }

    private static String nodeToString(Node node, MCTSWrapper mctsWrapper) {
        List<String> data = Arrays.asList(
                ((Integer) node.hashCode()).toString(),
                getNodeValue(node, mctsWrapper),
                ((Integer)node.getNodeStatistics().getVisitCount()).toString(),
                ((Double)((NodeStatisticsImpl) node.getNodeStatistics()).getAverageFailureDepth()).toString(),
                ((Double)((NodeStatisticsImpl) node.getNodeStatistics()).getMaximalUtility()).toString(),
                mctsWrapper.getNameFromIndex(node.getNodeStatistics().getDepth() - 1)
        );
        return String.join(";", data) + "\n";
    }

    private static void saveTreeStructureSpread(Node root, String treeFilePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(treeFilePath));
        saveTreeStructureSpread(root, writer);
        writer.close();
    }

    private static void saveTreeStructureSpread(Node node, BufferedWriter writer) throws IOException {
        writer.write(getOutgoingEdges(node));
        node.getChildren().stream()
                .sorted(getNodeComparator().reversed())
                .limit(MAX_CHILDREN)
                .forEach(child -> {
                    try {
                        saveTreeStructureSpread(child, writer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    private static String getOutgoingEdges(Node root) {
        if (root.getNodeStatistics().getDepth() >= MAX_DEPTH) {
            return "";
        }
        List<String> edges = root.getChildren().stream()
                .sorted(getNodeComparator().reversed())
                .limit(MAX_CHILDREN)
                .map(child -> edgeHash(root, child))
                .collect(Collectors.toList());
        return  String.join("", edges);
    }

    private static String edgeHash(Node parent,  Node child) {
        return ((Integer) parent.hashCode()).toString() + ";" + ((Integer) child.hashCode()).toString() +"\n";
    }


    private static void saveTreeStructureGreedy(Node root, String treeFilePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(treeFilePath));
        List<Node> atMaxDepth  = getNodesAtDepth(Math.min(MAX_DEPTH, getMaxTreeDepth(root)), root).stream().sorted(getNodeComparator().reversed()).limit(MAX_CHILDREN_AT_MAX_DEPTH).collect(Collectors.toList());
        saveTreeStructureGreedy(atMaxDepth, writer);
        writer.close();
    }

    private static void saveTreeStructureGreedy(List<Node> atMaxDepth, BufferedWriter writer) throws IOException {
        boolean reachedRoot = false;
        HashSet<Node> alreadySaved = new HashSet<>();
        while (!reachedRoot) {
            String edges = atMaxDepth.stream().map(child -> edgeHash(child.getParent(), child)).collect(Collectors.joining(""));
            alreadySaved.addAll(atMaxDepth);
            writer.write(edges);
            atMaxDepth = atMaxDepth.stream().map(Node::getParent).distinct().filter(node -> node.getParent()!= null && !alreadySaved.contains(node)).collect(Collectors.toList());
            if (atMaxDepth.size() == 0 || atMaxDepth.get(0).getParent() == null) {
                reachedRoot = true;
            }
        }
    }

    private static List<Node> getNodesAtDepth(int depth, Node root) {
        List<Node> atDepth = Arrays.asList(root);
        int maxDepth = depth;
        while (depth != 0) {
            atDepth = atDepth.stream().map(Node::getChildren).flatMap(Collection::stream).collect(Collectors.toList());
            depth--;
        }
        /** add leaves at smaller depths **/
        if (maxDepth > 2) {
            atDepth.addAll(getNodesAtDepth(maxDepth-1, root).stream().filter(node->node.getChildrenSize() == 0).collect(Collectors.toList()));
        }
        return atDepth;
    }

    private static int getMaxTreeDepth(Node root) {
        int depth = 0;
        List<Node> atDepth = Arrays.asList(root);
        while (!atDepth.isEmpty()) {
            atDepth = atDepth.stream().map(Node::getChildren).flatMap(Collection::stream).collect(Collectors.toList());
            depth++;
        }
        return depth-1;
    }

    private static Comparator<Node> getNodeComparator() {
        return (o1, o2) -> {
            NodeStatisticsImpl nodeStatistics1 = (NodeStatisticsImpl) o1.getNodeStatistics();
            NodeStatisticsImpl nodeStatistics2 = (NodeStatisticsImpl) o2.getNodeStatistics();
            if (Double.compare(nodeStatistics1.getMaximalUtility(), nodeStatistics2.getMaximalUtility()) != 0.0) {
                return Double.compare(nodeStatistics1.getMaximalUtility(), nodeStatistics2.getMaximalUtility());
            } else if (Double.compare(nodeStatistics1.getAverageFailureDepth(), nodeStatistics2.getAverageFailureDepth()) != 0.0) {
                return Double.compare(nodeStatistics1.getAverageFailureDepth(), nodeStatistics2.getAverageFailureDepth());
            } else {
                return Integer.compare(o1.hashCode(), o2.hashCode());
            }
        };
    }

    private static String getNodeValue(Node node, MCTSWrapper mctsWrapper) {
        if (node.getNodeStatistics().getDepth() == 0) {
            return "";
        } else {
            return  ((Integer) mctsWrapper.getValueFromIndex(node.getValue(), node.getNodeStatistics().getDepth() - 1)).toString();
        }
    }
}
