package eu.melodic.upperware.mcts_solver.solver.utils.tree_printer;

import cp_wrapper.utils.variable_orderer.VariableOrderer;
import eu.melodic.upperware.mcts_solver.solver.mcts.cp_wrapper.MCTSWrapper;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Node;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.NodeStatisticsImpl;
import org.eclipse.ocl.uml.Variable;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TreePrinter {
    private static final int MAX_DEPTH = 7;
    private static final int MAX_CHILDREN = 5;
    public static void saveTreeDataToFile(Node root, String treeFilePath, String nodesFilePath, MCTSWrapper mctsWrapper) throws IOException {
        saveNodes(root, nodesFilePath, mctsWrapper);
        saveTreeStructure(root, treeFilePath);
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
                ((Integer) node.getValue()).toString(),
                ((Integer)node.getNodeStatistics().getVisitCount()).toString(),
                ((Double)((NodeStatisticsImpl) node.getNodeStatistics()).getAverageFailureDepth()).toString(),
                ((Double)((NodeStatisticsImpl) node.getNodeStatistics()).getMaximalUtility()).toString(),
                mctsWrapper.getNameFromIndex(node.getNodeStatistics().getDepth() - 1)
        );
        return String.join(";", data) + "\n";
    }

    private static void saveTreeStructure(Node root, String treeFilePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(treeFilePath));
        saveTreeStructure(root, writer);
        writer.close();
    }

    private static void saveTreeStructure(Node node, BufferedWriter writer) throws IOException {
        writer.write(getOutgoingEdges(node));
        List<Node> children =  node.getChildren().stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList())
                .subList(0, Math.min(MAX_CHILDREN, node.getChildrenSize()));

        for (Node child : children) {
            saveTreeStructure(child, writer);
        }
    }

    private static String getOutgoingEdges(Node root) {
        if (root.getNodeStatistics().getDepth() >= MAX_DEPTH) {
            return "";
        }
        List<String> edges = root.getChildren().stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList())
                .subList(0, Math.min(MAX_CHILDREN, root.getChildrenSize()))
                .stream()
                .map(child -> edgeHash(root, child))
                .collect(Collectors.toList());
        return  String.join("", edges);
    }

    private static String edgeHash(Node parent,  Node child) {
        return ((Integer) parent.hashCode()).toString() + ";" + ((Integer) child.hashCode()).toString() +"\n";
    }
}
