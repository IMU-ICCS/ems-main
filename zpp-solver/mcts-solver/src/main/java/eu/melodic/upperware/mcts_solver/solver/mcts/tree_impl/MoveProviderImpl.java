package eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl;

import eu.melodic.upperware.mcts_solver.solver.mcts.cp_wrapper.MCTSWrapper;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.MemoryLimiter;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.MoveProvider;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Node;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Path;
import lombok.AllArgsConstructor;
import org.javatuples.Pair;

import java.util.stream.IntStream;

@AllArgsConstructor
public class MoveProviderImpl implements MoveProvider {
    private MCTSWrapper mctsWrapper;
    private MemoryLimiter memoryLimiter;

    @Override
    public Pair<Node, Path> searchAndExpand(Node root) {
        Node current = root;

        current.visit();

        Pair<Node, Path> traversingResult = traverse(current);
        current = traversingResult.getValue0();
        Path path = traversingResult.getValue1();


        Node expanded = expand(current);

        if (current != expanded) {
            current = expanded;
            current.visit();
            path.add(current);
        }

        return new Pair<>(current, path);
    }

    // Traverses tree choosing best nodes. Builds path and returns it alongside last visited node.
    private Pair<Node, Path> traverse(Node current) {
        int depth = 0;
        Path path = new Path();

        // While has been expanded and is not leaf.
        while (depth < this.mctsWrapper.getSize() && current.isExpanded()) {
            current = current.getBestChild();
            depth++;
            current.visit();
            path.add(current);
        }

        return new Pair<>(current, path);
    }

    /* Expands node by adding children to it.
       Returns random child of expanded node.
       If node already had all possible children return it instead.
       */
    private Node expand(Node toExpand) {
        int depth = toExpand.getNodeStatistics().getDepth();
        if (depth >= this.mctsWrapper.getSize()) {
            return toExpand;
        }
        IntStream.range(mctsWrapper.getMinDomainValue(depth), mctsWrapper.getMaxDomainValue(depth) + 1).
                forEach(value -> memoryLimiter.createNode(toExpand, value));

        toExpand.setExpanded();

        return toExpand.getChildren().get(mctsWrapper.generateRandomValue(depth));
    }
}
