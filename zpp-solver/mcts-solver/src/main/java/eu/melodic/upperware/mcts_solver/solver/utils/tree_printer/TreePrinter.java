package eu.melodic.upperware.mcts_solver.solver.utils.tree_printer;

import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Node;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.NodeStatisticsImpl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TreePrinter {
    public static void saveTreeDataToFile(Node root, String treeFilePath, String nodesFilePath) throws IOException {
        saveNodes(root, nodesFilePath);
        saveTreeStructure(root, treeFilePath);
    }

    private static void saveNodes(Node root, String nodesFilePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(nodesFilePath));
        saveNode(root, writer);
    }

    private static void saveNode(Node node, BufferedWriter writer) throws IOException {
        writer.write(nodeToString(node));
        for (Node child : node.getChildren()) {
            saveNode(child, writer);
        }
    }

    private static String nodeToString(Node node) {
        List<String> data = Arrays.asList(
                ((Integer) node.hashCode()).toString(),
                ((Integer) node.getValue()).toString(),
                ((Integer)node.getNodeStatistics().getVisitCount()).toString(),
                ((Double)((NodeStatisticsImpl) node.getNodeStatistics()).getAverageFailureDepth()).toString(),
                ((Double)((NodeStatisticsImpl) node.getNodeStatistics()).getMaximalUtility()).toString()
        );
        return String.join(";", data) + "\n";
    }

    private static void saveTreeStructure(Node root, String treeFilePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(treeFilePath));
        saveTreeStructure(root, writer);
    }

    private static void saveTreeStructure(Node node, BufferedWriter writer) throws IOException {
        writer.write(getOutgoingEdges(node));
        for (Node child : node.getChildren()) {
            saveTreeStructure(child, writer);
        }
    }

    private static String getOutgoingEdges(Node root) {
        List<String> edges = root.getChildren()
                .stream()
                .map(child -> edgeHash(root, child))
                .collect(Collectors.toList());
        return String.join("", edges);
    }

    private static String edgeHash(Node parent,  Node child) {
        return ((Integer) parent.hashCode()).toString() + ";" + ((Integer) child.hashCode()).toString() +"\n";
    }
}
